import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */





// just do the pipelining to provide the input to the program.. otherwise provide input in the terminal.
public class astar {
    
    static public void pln(String s){
        System.out.println(""+s);
    }
    
    public static void main(String[] args){
           //to find 
        Scanner std = new Scanner(System.in);
        pln("No. of test cases: ");
        int tcase = std.nextInt();
        for(int hht=0;hht<tcase;hht++){
            HashMap<String, config> hm = new HashMap<>();
            pln("Input starting configuration as a string");
            String startstate = std.next();
            pln("Input heuristic type (number): ");
            int htype = std.nextInt();
            config sconfig = new config(startstate);
            sconfig.emptyblock = startstate.indexOf("0");
    //        pln("eblock "+sconfig.emptyblock);
            if(sconfig.emptyblock==-1){
                pln("Please insert correct input");
                return;
            }
            HashMap<String, Boolean> visited = new HashMap<>();
            PriorityQueue<config> minHeap = new PriorityQueue<>(100000, new Comparator<config>(){
               public int compare(config a, config b){
                   return a.fval-b.fval;
               } 
            });
            pln("Input the ending configuration");
            String closestate = std.next();
            minHeap.add(sconfig);
            hm.put(startstate, sconfig);
            boolean isSuc = false;
            while(minHeap.isEmpty()==false){
                config tmp = minHeap.peek();
    //            pln(""+tmp.val+" "+tmp.fval);
                String aa="", bb="",cc="",dd="";
                char[] tm = tmp.val.toCharArray();
                if(tmp.val.equals(closestate)){
                    isSuc = true;
                    minHeap.poll();
                    break;
                }
                int rw = tmp.emptyblock/3;
                int col = tmp.emptyblock%3;

            // for swapping with left element if applicable.    
                if(col-1>=0 && ((tmp.val.equals(startstate)) || tmp.swap!=tmp.emptyblock-1)){
    //                pln("case 1");
                    tm[tmp.emptyblock] = tm[tmp.emptyblock-1];
                    tm[tmp.emptyblock-1]='0';
                    String thg1 = "";
                    for(int fd=0;fd<9;fd++){
                        thg1 = thg1 + tm[fd];
                    }
    //                pln(""+tm[0]+" "+thg1);
                    config left = new config(thg1);
                    left.parent = minHeap.peek();
                    left.gval = tmp.gval+1;
                    if(htype==1) left.hval = 0;
                    else if(htype==2) left.hval = heuristic2(thg1, closestate);
                    else if(htype==3) left.hval = heuristic3(thg1, closestate);
                    else left.hval  = heuristic4(thg1, closestate);

                    left.fval = left.gval + left.hval;
                    left.swap = tmp.emptyblock;
                    left.emptyblock = tmp.emptyblock-1;
                    if(hm.containsKey(thg1) && hm.get(thg1).fval <= left.fval ){

                    }
                    else{
                        if(minHeap.contains(hm.get(thg1))){
                            minHeap.remove(hm.get(thg1));
                        }
    //                    minHeap.add(left);
                        aa=thg1;
                        hm.put(thg1, left);
                    }
                    tm[tmp.emptyblock-1]=tm[tmp.emptyblock];
                    tm[tmp.emptyblock] = '0';


                }

                // for swapping with right element if applicable.
                if((col+1)<3  && ((tmp.val.equals(startstate)) || tmp.swap!=tmp.emptyblock+1)){
    //                pln("case 2");
                    tm[tmp.emptyblock] = tm[tmp.emptyblock+1];
                    tm[tmp.emptyblock+1]='0';
                    String thg1 = "";
                    for(int fd=0;fd<9;fd++){
                        thg1 = thg1 + tm[fd];
                    }
                    config left = new config(thg1);
                    left.parent = minHeap.peek();
                    left.gval = tmp.gval+1;
                    if(htype==1) left.hval = 0;
                    else if(htype==2) left.hval = heuristic2(thg1, closestate);
                    else if(htype==3) left.hval = heuristic3(thg1, closestate);
                    else left.hval  = heuristic4(thg1, closestate);
                    left.fval = left.gval + left.hval;
                    left.swap = tmp.emptyblock;
                    left.emptyblock = tmp.emptyblock+1;
                    if(hm.containsKey(thg1) && hm.get(thg1).fval <= left.fval ){

                    }
                    else{
                        if(minHeap.contains(hm.get(thg1))){
                            minHeap.remove(hm.get(thg1));
                        }
    //                    minHeap.add(left);
                        bb=thg1;
                        hm.put(thg1, left);
                    }
                    tm[tmp.emptyblock+1]=tm[tmp.emptyblock];
                    tm[tmp.emptyblock] = '0';


                }

                // for swapping with upper element if applicable.
                if(rw-1>=0  && ((tmp.val.equals(startstate)) || tmp.swap!=tmp.emptyblock-3)){
    //                pln("case 3");
                    tm[tmp.emptyblock] = tm[tmp.emptyblock-3];
                    tm[tmp.emptyblock-3]='0';
                    String thg1 = "";
                    for(int fd=0;fd<9;fd++){
                        thg1 = thg1 + tm[fd];
                    }
                    config left = new config(thg1);
                    left.parent = minHeap.peek();
                    left.gval = tmp.gval+1;
                    if(htype==1) left.hval = 0;
                    else if(htype==2) left.hval = heuristic2(thg1, closestate);
                    else if(htype==3) left.hval = heuristic3(thg1, closestate);
                    else left.hval  = heuristic4(thg1, closestate);
                    left.fval = left.gval + left.hval;
                    left.swap = tmp.emptyblock;
                    left.emptyblock = tmp.emptyblock-3;
                    if(hm.containsKey(thg1) && hm.get(thg1).fval <= left.fval ){

                    }
                    else{
                        if(minHeap.contains(hm.get(thg1))){
                            minHeap.remove(hm.get(thg1));
                        }
    //                    minHeap.add(left);
                        cc=thg1;
                        hm.put(thg1, left);
                    }
                    tm[tmp.emptyblock-3]=tm[tmp.emptyblock];
                    tm[tmp.emptyblock] = '0';


                }

            // for swapping with lower element if applicable.    
                if(rw+1<3 && ((tmp.val.equals(startstate)) || tmp.swap!=tmp.emptyblock+3)){
    //                pln("case 4");
                    tm[tmp.emptyblock] = tm[tmp.emptyblock+3];
                    tm[tmp.emptyblock+3]='0';
                    String thg1 = "";
                    for(int fd=0;fd<9;fd++){
                        thg1 = thg1 + tm[fd];
                    }
                    config left = new config(thg1);
                    left.parent = minHeap.peek();
                    left.gval = tmp.gval+1;
                    if(htype==1) left.hval = 0;
                    else if(htype==2) left.hval = heuristic2(thg1, closestate);
                    else if(htype==3) left.hval = heuristic3(thg1, closestate);
                    else left.hval  = heuristic4(thg1, closestate);
                    left.fval = left.gval + left.hval;
                    left.swap = tmp.emptyblock;
                    left.emptyblock = tmp.emptyblock+3;
                    if(hm.containsKey(thg1) && hm.get(thg1).fval <= left.fval ){

                    }
                    else{
                        if(minHeap.contains(hm.get(thg1))){
                            minHeap.remove(hm.get(thg1));
                        }
    //                    minHeap.add(left);
                        dd=thg1;
                        hm.put(thg1, left);
                    }
                    tm[tmp.emptyblock+3]=tm[tmp.emptyblock];
                    tm[tmp.emptyblock] = '0';


                }
               minHeap.poll();
               if(!aa.equals("")) minHeap.add(hm.get(aa));
               if(!bb.equals("")) minHeap.add(hm.get(bb));
               if(!cc.equals("")) minHeap.add(hm.get(cc));
               if(!dd.equals("")) minHeap.add(hm.get(dd));

            }


            if(isSuc==true){
                System.out.println("******* Successfully Found Solution *******");
                pln("Start State: "+sconfig.val);
                pln("End State: "+closestate);
                pln("Optimal Path: ");
                optimalPath(hm.get(closestate));
                pln("Optimal Cost of the Path: "+(hm.get(closestate).gval));
                pln("Total No. of States Explored: "+ hm.size());
                pln("Contents of Open List: ");
                while(minHeap.isEmpty()==false){
                    String ss = minHeap.peek().val;
                    hm.remove(ss);
                    System.out.print("< "+ss+" >, ");
                    minHeap.poll();
                }
                pln("");

                pln("Contents of Close List: ");
                for(String s : hm.keySet()){
                    System.out.print("< "+s+" >, ");
                }
                pln("");
            }
            else{
                pln("********** Failed to find answer!! No Answer Exist  *********");
                pln("Start State: "+sconfig.val);
                pln("Goal State: "+closestate);

            }
            pln("");
            pln("");
        }
                
    }
    

// heuristic 2: getting Number of tiles displaced from goal configuration.
    
    static int heuristic2(String s, String g){
        int cnt = 0;
        for(int i=0;i<9;i++){
            if(s.charAt(i)!='0' && s.charAt(i)!=g.charAt(i)){
                cnt++;
            }
        }
        return cnt;
    }
    
    
    
// heuristic 3: getting Manhattan distance of  a configuration.    
    static int heuristic3 (String s, String g){
        int dist = 0;
        for(int i=0;i<9;i++){
            if(s.charAt(i)=='0'){
                
            }
            else{
                int tt = g.indexOf(""+s.charAt(i));
                
                int r1 = tt/3;
                int c1 = tt%3;
                int r2 = i/3;
                int c2 = i%3;
                dist = dist + Math.abs(r1-r2) + Math.abs(c1-c2);
            }
        }
        return dist;
    }
    
    static int heuristic4(String s, String g){
        HashMap<Integer, Integer> sr = new HashMap<>();
        sr.put(0,1);
        sr.put(1,2);
        sr.put(2,5);
        sr.put(3,0);
        sr.put(4,-1);
        sr.put(5,8);
        sr.put(6,3);
        sr.put(7,6);
        sr.put(8,7);
        int count = heuristic3(s, g);
        int nilsson = 0; 
        for(int i=0; i<s.length(); i++)
        {
            if(sr.get(i) == -1)
            {
                nilsson += 1;
            }
            else
            {
                String a = s.charAt(i)+"";
                for(int j=0; j<g.length(); j++)
                {
                    String b = g.charAt(j)+"";
                    if(b.equals(a))
                    {
                        if(sr.get(j) == -1)
                        {
                            nilsson +=2;
                        }
                        else
                        {
                            String succ_a = s.charAt(sr.get(i))+"";
                            String succ_b = g.charAt(sr.get(j))+"";
                            if(!succ_a.equals(succ_b))
                            {
                                nilsson +=2; 
                            }
                        }
                    }
                }
            }
        }
//        pln(""+(count + (3*nilsson)));
        return count + (3*nilsson);
    }
    
    static void optimalPath(config fl){
        ArrayList<IntegerPair> res = new ArrayList<>();
        config tt = fl;
        int cnt=0;
        while(tt.parent!=null){
            cnt++;
            IntegerPair ff = new IntegerPair(tt.swap+1, tt.emptyblock+1);
            res.add(ff);
            tt = tt.parent;
        }
        for(int i=res.size()-1;i>=0;i--){
            System.out.print("( "+res.get(i).a+" <-> "+res.get(i).b+" ), ");
        }
        pln("");
    }
}

class IntegerPair{
    int a;
    int b;
    
    public IntegerPair(int c, int d){
        a=c;
        b=d;
    }
}

class config{
    config parent=null;
    String val="";
    int fval=0;
    int swap = 0;
    int emptyblock = 0;
    int gval = 0;
    int hval  =0;
    
    
    public config(String v){
        val = v;
    }
}
