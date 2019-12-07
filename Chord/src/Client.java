
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rishabh
 */
class Node implements Runnable {

    int ID;
    String IP;
    int port;
//    Socket sock;
    Node predecessor;
    finger[] FingerTable;
    List<Integer> keys;

    Node() {

    }

    /**
     *
     * @param a
     * @param b
     * @param c
     * @param mode 00 - (), 01 - (], 10 - [), 11 - []
     * @return
     */
    public static boolean belongTo(int a, int b, int c, int mode) {
        ArrayList<Integer> arr = new ArrayList<>();

        for (int i = a + 1; i % 8 != b; i++) {
            arr.add(i % 8);
        }
        switch (mode) {
            case 00:
                break;
            case 01:
                arr.add(b);
                break;
            case 10:
                arr.add(a);
                break;
            case 11:
                arr.add(a);
                arr.add(b);
                break;
        }
        if (arr.indexOf(c) == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * called to get FingerTable[0].finger
     *
     * @return
     */
    synchronized public Node getSuccessor() {
        return this.FingerTable[0].finger;
    }

    synchronized public void setSuccessor(Node x) {
        this.FingerTable[0].finger = x;
        System.out.println(Thread.currentThread().getName() + ": Successor set to " + getSuccessor().toString());
    }

    synchronized public void askForKeys(Node x) {
        try {
            Socket sock = new Socket(x.IP, x.port);
            PrintWriter pw = new PrintWriter(sock.getOutputStream());
            Scanner sc = new Scanner(sock.getInputStream());
            pw.println("getKeys");
            pw.println(this.ID);
            pw.flush();
            String line = sc.nextLine();
            System.out.println(Thread.currentThread().getName() + ": got response of askForKeys, keys are : " + line);
            if(!line.equals("null")){
                String [] arr = line.split(" ");
                for (int i = 0; i < arr.length; i++) {
                    this.keys.add(Integer.parseInt(arr[i]));
                }
            sc.close();
            pw.close();
            sock.close();
            }
        } catch (Exception e) {
            System.out.println("Error in ask for keys");
            e.printStackTrace();
        }
    }

    synchronized void create() {
        FingerTable = new finger[3];
        for (int i = 0; i < 3; i++) {
            FingerTable[i] = new finger();
        }
        predecessor = null;
        setSuccessor(this);
        keys = Collections.synchronizedList(new LinkedList());
        for (int i = 1; i <= 50; i++) {
            keys.add(i);
        }
    }


    synchronized void join(Node m) {
        predecessor = null;
        FingerTable = new finger[3];
        for (int i = 0; i < 3; i++) {
            FingerTable[i] = new finger();
        }
        FingerTable[1].finger.ID = -1;
        FingerTable[2].finger.ID = -1;
        setSuccessor(m.remotefindSuccessor(this.ID + ""));
        keys = Collections.synchronizedList(new LinkedList());
        askForKeys(this.getSuccessor());
    }

    public String getKeys(int id) {
        String ret = "";
        int x;
        for (int i = 0 ; i < keys.size();i++) {
            x = keys.get(i);
            if(Node.belongTo(this.ID, id, keys.get(i)%8, 01)){
                ret = ret + " " + x;
                keys.remove(i);
                i = i - 1;
            }
        }
        if(!ret.equals(" ")){
            return ret.substring(1);
        } else {
            return "null";
        }
    }
    
    synchronized void stablize() {
        System.out.println(Thread.currentThread().getName() + ": Running Stablize");
        Node x = this.getSuccessor().remoteGetPredecessor();
        if (x == null) {
            System.out.println(Thread.currentThread().getName() + ": Successor's predecessor is null, going for notfy()");
            this.getSuccessor().remoteNotify(this.toString());
            return;
        }
        if (Node.belongTo(this.ID, this.getSuccessor().ID, x.ID, 00)) {
//        if (x.ID > this.ID && x.ID < this.getSuccessor().ID) {
            this.setSuccessor(x);
            System.out.println(Thread.currentThread().getName() + ": Update successor to " + this.getSuccessor().toString());
        }
        System.out.println(Thread.currentThread().getName() + ": Notify() called with successor = " + this.getSuccessor().toString());
        this.getSuccessor().remoteNotify(this.toString());
    }

    synchronized Node remoteGetPredecessor() {
        System.out.println(Thread.currentThread().getName() + ": remoteGetPredecessor called");
        try {
            Socket sock = new Socket(this.IP, this.port);
            Scanner sc = new Scanner(sock.getInputStream());
            PrintWriter pw = new PrintWriter(sock.getOutputStream());
            pw.println("getPredecessor");
            pw.flush();
            String inp = sc.nextLine();
            System.out.println(Thread.currentThread().getName() + ": Remote node told predecessor to be : " + inp);
            if (inp.equals("null")) {
                sc.close();
                sock.close();
                return null;
            }
            Node x = fromString(inp);
            pw.close();
            sc.close();
            sock.close();
            return x;
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + ": Exception in remoteGetPredecessor, returning null.");
            return null;
        }
    }

    synchronized void remoteNotify(String s) {
        try {
            System.out.println(Thread.currentThread().getName() + ": Sending remoteNotify request to " + this.toString());
            Socket sock = new Socket(this.IP, this.port);
            PrintWriter pw = new PrintWriter(sock.getOutputStream());
            pw.println("notify");
            pw.println(s);
            pw.flush();
            pw.close();
            sock.close();
            System.out.println(Thread.currentThread().getName() + ": Finished sending remoteNotify()");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(Thread.currentThread().getName() + ": remoteNotify() interrupted, returning");
            return;
        }
    }

    synchronized void notify(String s) {
        Node x = fromString(s);
        System.out.println(Thread.currentThread().getName() + ": Notify request from " + x.toString());
        //star
        if (this.predecessor == null || (Node.belongTo(this.predecessor.ID, this.ID, x.ID, 00))) {
//        if (this.predecessor == null || (x.ID > this.predecessor.ID && x.ID < this.ID)) {
            this.predecessor = x;
            System.out.println(Thread.currentThread().getName() + ": Predecessor changed to " + x.toString());
        }
    }

    Node fromString(String s) {
        Node x = new Node();
        String[] arr = s.split("\t");
        x.ID = Integer.parseInt(arr[0]);
        x.port = Integer.parseInt(arr[1]);
        x.IP = arr[2];
        return x;
    }

    @Override
    public String toString() {
        return this.ID + "\t" + this.port + "\t" + this.IP;
    }

    public Node remotefindSuccessor(String s) {
        try {
            System.out.println(Thread.currentThread().getName() + ": Running remotefindSuccesor on " + s);
            Socket x = new Socket(this.IP, this.port);
            Scanner sc = new Scanner(x.getInputStream());
            PrintWriter pw = new PrintWriter(x.getOutputStream());
            pw.println("findSuccessor");
            pw.println(s);
            pw.flush();
            String inp = sc.nextLine();
            pw.close();
            Node ret = fromString(inp);
            sc.close();
            x.close();
            System.out.println(Thread.currentThread().getName() + ": returning " + ret.toString() + " from remotefidSuccessor()");
            return ret;
        } catch (IOException ex) {
            System.out.println(Thread.currentThread().getName() + ": RemotefindSuccessor interrupted, returning null");
            return null;
        }
    }

    synchronized public Node findSuccessor(String s) {
        System.out.println(Thread.currentThread().getName() + ": Running findSuccessor for " + s);
        int id = Integer.parseInt(s);
        if (Node.belongTo(this.ID, this.getSuccessor().ID, id, 01)) {
//        if (id > this.ID && id <= this.getSuccessor().ID) {
            System.out.println(Thread.currentThread().getName() + ": Successor found, returning " + getSuccessor().toString());
            return getSuccessor();
        } else {
            Node m = closest_preceding_node(s);
            if (m.ID == this.ID) {
                return this;
            }
            Node x = m.remotefindSuccessor(s);
            System.out.println(Thread.currentThread().getName() + ": Contacted other nodes, returning " + x.toString());
            return x;
        }
    }

    synchronized public Node closest_preceding_node(String s) {
        int id = Integer.parseInt(s);
        for (int i = 2; i >= 0; i--) {
            int fig = this.FingerTable[i].finger.ID;
            if (fig == -1) {
                continue;
            }
            if (Node.belongTo(this.ID, id, fig, 00)) {
//            if (fig > this.ID && fig < id) {
                return this.FingerTable[i].finger;
            }
        }
        return this;
    }

    synchronized public void fix_fingers(int next) {
        this.FingerTable[next].finger = this.findSuccessor((this.ID + (int) (Math.pow(2, next))) % 8 + "");
    }

    synchronized public void check_predecesor() {
        try {
            Socket sock = new Socket(this.predecessor.IP, this.predecessor.port);
            sock.close();
        } catch (Exception e) {
            this.predecessor = null;
        }
    }

    @Override
    public void run() {
        int next = 0;
        while (true) {
            try {
                System.out.println(Thread.currentThread().getName() + ": Called stabilize");
                this.stablize();
                //take care of stablize function.
                System.out.println(Thread.currentThread().getName() + ": Called fix fingers");
                this.fix_fingers(next);
                next = next + 1;
                if (next > 2) {
                    next = 0;
                }
                //take care of fiz fingers, save next here and also inceremet it here and check for next > 2
                System.out.println(Thread.currentThread().getName() + ": Called check predecessor");
                this.check_predecesor();
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                continue;
            }
        }
    }
}

public class Client implements Runnable {

    ServerSocket server;
    Node node;
    //keys array

    Client() {
        node = new Node();
        newPort();
//        getIP();
        node.IP = "localhost";
    }

    public void getIP() {
        try {
            InetAddress IP = InetAddress.getLocalHost();
            this.node.IP = IP.getHostAddress();
            System.out.println(Thread.currentThread().getName() + ": IP is" + this.node.IP);
        } catch (UnknownHostException e) {
            this.node.IP = "localhost";
        }
    }

    public void newPort() {
        int port = 0, maximum = 9999, minimum = 1000;
        Random rn = new Random();
        while (true) {
            int n = maximum - minimum + 1;
            int i = rn.nextInt() % n;
            port = minimum + i;
            try {
                this.server = new ServerSocket(port);
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + ": Cannot get port " + port);
                continue;
            }
            System.out.println(Thread.currentThread().getName() + ": Finalized port : " + port);
            this.node.port = port;
//            this.node.IP = this.server.getInetAddress().getHostAddress();
//            System.out.println("IP is : " + this.server.getInetAddress().getHostAddress());
            break;
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("main ");
        Client me = new Client();
        System.out.println(Thread.currentThread().getName() + ": Enter the node ID:");
        Scanner sc = new Scanner(System.in);
        me.node.ID = Integer.parseInt(sc.nextLine());
        if (me.node.ID == 0) {
            me.node.create();
            Thread t = new Thread(me.node);
            t.setName("Node Thread ");
            t.start();
            t = new Thread(me);
            t.setName("listener ");
            t.start();
        } else {
//            me.node.create();
//            me.node.predecessor = null;
            System.out.println(Thread.currentThread().getName() + ": Enter ID of node to be joined : ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.println(Thread.currentThread().getName() + ": Enter ip of node to be joined : ");
            String host = sc.nextLine();
            System.out.println(Thread.currentThread().getName() + ": Enter port of the node : ");
            int port = Integer.parseInt(sc.nextLine());
            me.node.join(me.node.fromString(id + "\t" + port + "\t" + host));
            System.out.println("finger[0] = " + me.node.FingerTable[0].finger + "\n"
                    + "finger[1] = " + me.node.FingerTable[1].finger + "\n"
                    + "finger[2] = " + me.node.FingerTable[2].finger + "\n"
                    + "predecessor = " + me.node.predecessor);
            Thread t = new Thread(me.node);
            t.setName("Node Thread ");
            t.start();
            t = new Thread(me);
            t.setName("listener ");
            t.start();
        }
        while (true) {
            String inp = sc.nextLine();
            if (inp.equals("1")) {
                System.out.println("Node no. : " + me.node.ID + " predecossor : " + me.node.predecessor + "\tsuccessor : " + me.node.getSuccessor());
                System.out.println((me.node.ID + 1) % 8 + "\t" + "[" + (me.node.ID + 1) % 8 + ", " + (me.node.ID + 1 + 1) % 8 + ")" + "\t" + me.node.FingerTable[0].finger.ID + "\n"
                        + (me.node.ID + 2) % 8 + "\t" + "[" + (me.node.ID + 2) % 8 + ", " + (me.node.ID + 2 + 2) % 8 + ")" + "\t" + me.node.FingerTable[1].finger.ID + "\n"
                        + (me.node.ID + 4) % 8 + "\t" + "[" + (me.node.ID + 4) % 8 + ", " + (me.node.ID + 4 + 4) % 8 + ")" + "\t" + me.node.FingerTable[2].finger.ID);
                System.out.println("Keys : " + me.node.keys);
            }
        }

    }

    @Override
    public void run() {
        Scanner sc = null;
        PrintWriter out = null;
        while (true) {
            Socket s = null;
            try {
                s = this.server.accept();
            } catch (Exception e) {
            }
            try {
                sc = new Scanner(s.getInputStream());
                out = new PrintWriter(s.getOutputStream());
                String inp = sc.nextLine();
                String x = null;
                switch (inp) {
                    case "findSuccessor":
                        System.out.println(Thread.currentThread().getName() + ": new findSuccessor request received from");
                        x = sc.nextLine();
                        x = this.node.findSuccessor(x).toString();
                        out.println(x);
                        out.flush();
                        break;
                    case "notify":
                        System.out.println(Thread.currentThread().getName() + ": new notify request received");
                        x = sc.nextLine();
                        this.node.notify(x);
                        break;
                    case "getPredecessor":
                        System.out.println(Thread.currentThread().getName() + ": new getPredecessor request received");
                        out.println(this.node.predecessor);
                        out.flush();
                        break;
                    case "getKeys":
                        System.out.println(Thread.currentThread().getName() + ": new getKeys request received");
                        out.println(this.node.getKeys(Integer.parseInt(sc.nextLine())));
                        out.flush();
                        break;
                }
                out.close();
                sc.close();
                s.close();
            } catch (Exception e) {
                continue;
            }
        }

    }
}

class finger {

    Node finger;

    finger() {
        finger = new Node();
    }
}
