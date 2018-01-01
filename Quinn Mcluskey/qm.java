

/**
 *
 * @author rishabh
 */
import java.util.*;

public class qm {

    private static void removeS1() {
        for (int i = 0; i < fi.size(); i++) {
            for (int j = 0; j < fi.size(); j++) {
                int no = 0, n1 = 0, n2 = 0;
                if (i == j) {
                    continue;
                }
                for (int k = 0; k < 16; k++) {
                    if (fi.get(i).contain(fi.get(j).n[k]) && fi.get(j).n[k] != null) {
                        no++;//System.out.println("II");
                    }
                }
                for (int k = 0; k < 16; k++) {
                    if (fi.get(j).n[k] != null) {
                        n1++;
                    }
                }
                for (int k = 0; k < 16; k++) {
                    if (fi.get(i).n[k] != null) {
                        n2++;
                    }
                }
                if (n1 == no && n2 == n1) {
                    o2.add(fi.get(j));
                    fi.remove(fi.get(j));
                    j--;//System.out.println("GG");
                }
            }
            o2.add(fi.get(i));
            fi.remove(fi.get(i));
            i--;
        }
    }

    private static void removeEx() {
        boolean c = true;//System.out.println("YYY");
        for (int i = 0; i < fi.size(); i++) {
            for (int j = 0; j < mta.length; j++) {
                if (fi.get(i).contain(Integer.parseInt(mta[j]))) {
                    c = false;//System.out.println("Hello");
                }
            }
            if (c) {
                fi.remove(fi.get(i));
                i--;
            }
        }

    }
    Integer n[] = new Integer[16];
    String[] a = new String[4];
    boolean checked;
    static int[][] m;
    static ArrayList<qm> mt = new ArrayList<>();
    static ArrayList<qm> l0 = new ArrayList<>();
    static ArrayList<qm> l1 = new ArrayList<>();
    static ArrayList<qm> l2 = new ArrayList<>();
    static ArrayList<qm> l3 = new ArrayList<>();
    static ArrayList<qm> l4 = new ArrayList<>();
    static ArrayList<qm> r1 = new ArrayList<>();
    static ArrayList<qm> r0 = new ArrayList<>();
    static ArrayList<qm> r3 = new ArrayList<>();
    static ArrayList<qm> r2 = new ArrayList<>();
    static ArrayList<qm> w0 = new ArrayList<>();
    static ArrayList<qm> w1 = new ArrayList<>();
    static ArrayList<qm> w2 = new ArrayList<>();
    static ArrayList<qm> s0 = new ArrayList<>();
    static ArrayList<qm> s1 = new ArrayList<>();
    static ArrayList<qm> fi = new ArrayList<>();
    static ArrayList<qm> fin = new ArrayList<>();
    static ArrayList<qm> o1 = new ArrayList<>();
    static ArrayList<qm> o2 = new ArrayList<>();

    static String[] mta;

    static String con(qm x) {
        String g = "";
        if (x.a[0].equals("1")) {
            g = g + "A";
        }
        if (x.a[0].equals("0")) {
            g = g + "A'";
        }
        if (x.a[1].equals("1")) {
            g = g + "B";
        }
        if (x.a[1].equals("0")) {
            g = g + "B'";
        }
        if (x.a[2].equals("1")) {
            g = g + "C";
        }
        if (x.a[2].equals("0")) {
            g = g + "C'";
        }
        if (x.a[3].equals("1")) {
            g = g + "D";
        }
        if (x.a[3].equals("0")) {
            g = g + "D'";
        }
        return g;
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("CAUTION : This Program works for 4 variables only");
        System.out.println("Enter the minTerms separated by comma(no spaces)");
        String minTermsStr = sc.nextLine();
        String dontcareStr, minTermsActual = minTermsStr.toString();
        String[] minTermsAct = minTermsActual.split(",|\\n");
        System.out.println("Are there any dont care terms??(Press y for YES)");
        char f = sc.next().charAt(0);
        if (f == 'Y' || f == 'y') {
            System.out.println("Enter the dont care terms");
            dontcareStr = sc.next();
            minTermsAct = minTermsStr.split(",");
            System.out.println(minTermsStr.length());
            minTermsStr = minTermsStr + "," + dontcareStr;
            System.out.println(minTermsStr.length());
        }
        String[] minTermsArr = minTermsStr.split(",|\\n");

        //minTermsArr >>all m+dc terms 
        //minTermsAct >>actual min terms
        for (int i = 0; i < minTermsArr.length; i++) {
            qm a1 = new qm();
            a1.n[0] = Integer.parseInt(minTermsArr[i]);
            String bin = toBinary(a1.n[0]);
            for (int j = 0; j < 4; j++) {
                a1.a[j] = bin.charAt(j) + "";
            }
            mt.add(a1);
            /*for(int j=0;j<4;j++){
             System.out.print(a1.n[j]);
             }
             System.out.print("");
             for(int j=0;j<4;j++){
             System.out.print(a1.a[j]);
             }
             System.out.println("");*/
        }

        for (int i = 0; i < mt.size(); i++) {
            int n = check1(mt.get(i));
            if (n == 0) {
                l0.add(mt.get(i));
            }
            if (n == 1) {
                l1.add(mt.get(i));
            }
            if (n == 2) {
                l2.add(mt.get(i));
            }
            if (n == 3) {
                l3.add(mt.get(i));
            }
            if (n == 4) {
                l4.add(mt.get(i));
            }
        }
        for (int i = 0; i < l0.size(); i++) {
            for (int j = 0; j < l1.size(); j++) {
                if (l0.get(i).equals(l1.get(j))) {
                    continue;
                }
                if (l0.get(i).check(l1.get(j)) == 3) {
                    int same = l0.get(i).getD(l1.get(j));
                    qm ne = new qm();
                    ne.n[0] = l0.get(i).n[0];
                    ne.n[1] = l1.get(j).n[0];
                    ne.a[0] = l0.get(i).a[0];
                    ne.a[1] = l0.get(i).a[1];
                    ne.a[2] = l0.get(i).a[2];
                    ne.a[3] = l0.get(i).a[3];
                    ne.a[same] = "X";
                    l0.get(i).checked = true;
                    l1.get(j).checked = true;
                    r0.add(ne);
                }
            }
        }
        for (int i = 0; i < l1.size(); i++) {
            for (int j = 0; j < l2.size(); j++) {
                if (l1.get(i).equals(l2.get(j))) {
                    continue;
                }
                if (l1.get(i).check(l2.get(j)) == 3) {
                    int same = l1.get(i).getD(l2.get(j));
                    qm ne = new qm();
                    ne.n[0] = l1.get(i).n[0];
                    ne.n[1] = l2.get(j).n[0];
                    ne.a[0] = l1.get(i).a[0];
                    ne.a[1] = l1.get(i).a[1];
                    ne.a[2] = l1.get(i).a[2];
                    ne.a[3] = l1.get(i).a[3];
                    ne.a[same] = "X";
                    l1.get(i).checked = true;
                    l2.get(j).checked = true;
                    r1.add(ne);
                }
            }
        }
        for (int i = 0; i < l2.size(); i++) {
            for (int j = 0; j < l3.size(); j++) {
                if (l2.get(i).equals(l3.get(j))) {
                    continue;
                }
                if (l2.get(i).check(l3.get(j)) == 3) {
                    int same = l2.get(i).getD(l3.get(j));
                    qm ne = new qm();
                    ne.n[0] = l2.get(i).n[0];
                    ne.n[1] = l3.get(j).n[0];
                    ne.a[0] = l2.get(i).a[0];
                    ne.a[1] = l2.get(i).a[1];
                    ne.a[2] = l2.get(i).a[2];
                    ne.a[3] = l2.get(i).a[3];
                    ne.a[same] = "X";
                    l2.get(i).checked = true;
                    l3.get(j).checked = true;
                    r2.add(ne);
                }
            }
        }
        for (qm l31 : l3) {
            for (qm l41 : l4) {
                if (l31.equals(l41)) {
                    continue;
                }
                if (l31.check(l41) == 3) {
                    int same = l31.getD(l41);
                    qm ne = new qm();
                    ne.n[0] = l31.n[0];
                    ne.n[1] = l41.n[0];
                    ne.a[0] = l31.a[0];
                    ne.a[1] = l31.a[1];
                    ne.a[2] = l31.a[2];
                    ne.a[3] = l31.a[3];
                    ne.a[same] = "X";
                    l31.checked = true;
                    l41.checked = true;
                    r3.add(ne);
                }
            }
        }
        for (qm r01 : r0) {
            for (qm r11 : r1) {
                if (r01.check(r11) == 3) {
                    int same = r01.getD(r11);
                    qm ne = new qm();
                    ne.n[0] = r01.n[0];
                    ne.n[1] = r01.n[1];
                    ne.n[2] = r11.n[0];
                    ne.n[3] = r11.n[1];
                    ne.a[0] = r01.a[0];
                    ne.a[1] = r01.a[1];
                    ne.a[2] = r01.a[2];
                    ne.a[3] = r01.a[3];
                    ne.a[same] = "X";
                    r01.checked = true;
                    r11.checked = true;
                    w0.add(ne);
                }
            }
        }
        for (qm r11 : r1) {
            for (qm r21 : r2) {
                if (r11.check(r21) == 3) {
                    int same = r11.getD(r21);
                    qm ne = new qm();
                    ne.n[0] = r11.n[0];
                    ne.n[1] = r11.n[1];
                    ne.n[2] = r21.n[0];
                    ne.n[3] = r21.n[1];
                    ne.a[0] = r11.a[0];
                    ne.a[1] = r11.a[1];
                    ne.a[2] = r11.a[2];
                    ne.a[3] = r11.a[3];
                    ne.a[same] = "X";
                    r11.checked = true;
                    r21.checked = true;
                    w1.add(ne);
                }
            }
        }
        for (qm r21 : r2) {
            for (qm r31 : r3) {
                if (r21.check(r31) == 3) {
                    int same = r21.getD(r31);
                    qm ne = new qm();
                    ne.n[0] = r21.n[0];
                    ne.n[1] = r21.n[1];
                    ne.n[2] = r31.n[0];
                    ne.n[3] = r31.n[1];
                    ne.a[0] = r21.a[0];
                    ne.a[1] = r21.a[1];
                    ne.a[2] = r21.a[2];
                    ne.a[3] = r21.a[3];
                    ne.a[same] = "X";
                    r21.checked = true;
                    r31.checked = true;
                    w2.add(ne);
                }
            }
        }
        for (qm w01 : w0) {
            for (qm w11 : w1) {
                if (w01.check(w11) == 3) {
                    int same = w01.getD(w11);
                    qm ne = new qm();
                    ne.n[0] = w01.n[0];
                    ne.n[1] = w01.n[1];
                    ne.n[2] = w01.n[2];
                    ne.n[3] = w01.n[3];
                    ne.n[4] = w11.n[0];
                    ne.n[5] = w11.n[1];
                    ne.n[6] = w11.n[2];
                    ne.n[7] = w11.n[3];
                    ne.a[0] = w01.a[0];
                    ne.a[1] = w01.a[1];
                    ne.a[2] = w01.a[2];
                    ne.a[3] = w01.a[3];
                    ne.a[same] = "X";
                    w01.checked = true;
                    w11.checked = true;
                    s0.add(ne);
                }
            }
        }
        for (qm w11 : w1) {
            for (qm w21 : w2) {
                if (w11.check(w21) == 3) {
                    int same = w11.getD(w21);
                    qm ne = new qm();
                    ne.n[0] = w11.n[0];
                    ne.n[1] = w11.n[1];
                    ne.n[2] = w11.n[2];
                    ne.n[3] = w11.n[3];
                    ne.n[4] = w21.n[0];
                    ne.n[5] = w21.n[1];
                    ne.n[6] = w21.n[2];
                    ne.n[7] = w21.n[3];
                    ne.a[0] = w11.a[0];
                    ne.a[1] = w11.a[1];
                    ne.a[2] = w11.a[2];
                    ne.a[3] = w11.a[3];
                    ne.a[same] = "X";
                    w21.checked = true;
                    w11.checked = true;
                    s1.add(ne);
                }
            }
        }
        for (qm s01 : s0) {
            for (qm s11 : s1) {
                if (s01.check(s11) == 3) {
                    System.out.println("1\nValue is always one");
                    System.exit(0);
                }
            }
        }
        for (qm ax : l0) {
            if (ax.checked == false) {
                fi.add(ax);
            }
        }
        for (qm ax : l1) {
            if (ax.checked == false) {
                fi.add(ax);
            }
        }
        for (qm ax : l2) {
            if (ax.checked == false) {
                fi.add(ax);
            }
        }
        for (qm ax : l3) {
            if (ax.checked == false) {
                fi.add(ax);
            }
        }
        for (qm ax : l4) {
            if (ax.checked == false) {
                fi.add(ax);
            }
        }
        for (qm ax : r0) {
            if (ax.checked == false) {
                fi.add(ax);
            }
        }
        for (qm ax : r1) {
            if (ax.checked == false) {
                fi.add(ax);
            }
        }
        for (qm ax : r2) {
            if (ax.checked == false) {
                fi.add(ax);
            }
        }
        for (qm ax : r3) {
            if (ax.checked == false) {
                fi.add(ax);
            }
        }
        for (qm ax : w0) {
            if (ax.checked == false) {
                fi.add(ax);
            }
        }
        for (qm ax : w1) {
            if (ax.checked == false) {
                fi.add(ax);
            }
        }
        for (qm ax : w2) {
            if (ax.checked == false) {
                fi.add(ax);
            }
        }
        for (qm ax : s0) {
            if (ax.checked == false) {
                fi.add(ax);
            }
        }
        for (qm ax : s1) {
            if (ax.checked == false) {
                fi.add(ax);
            }
        }
        for (int kk = 0; kk < fi.size(); kk++) {
            for (int i = 0; i < fi.size(); i++) {
                for (int j = i; j < fi.size(); j++) {
                    qm ax = fi.get(i);
                    qm az = fi.get(j);
                    if (ax == null || az == null) {
                        continue;
                    }

                    if (i == j) {
                        continue;
                    }
                    if (ax.a[0].equalsIgnoreCase(az.a[0])) {
                        if (ax.a[1].equalsIgnoreCase(az.a[1])) {
                            if (ax.a[2].equalsIgnoreCase(az.a[2])) {
                                if (ax.a[3].equalsIgnoreCase(az.a[3])) {
                                    fi.set(j, ax);
                                    //System.out.println("YY");
                                }
                            }
                        }
                    }
                }
            }
        }

        for (int cc = 0; cc < fi.size(); cc++) {
            for (int i = 0; i < fi.size(); i++) {
                for (int j = i; j < fi.size(); j++) {
                    if (i == j) {
                        continue;
                    }
                    qm ax = fi.get(i);
                    qm az = fi.get(j);
                    if (ax == az) {
                        fi.remove(az);
                    }
                }
            }
        }

        for (int kk = 0; kk < fi.size(); kk++) {
            for (int i = 0; i < fi.size(); i++) {
                for (int j = i; j < fi.size(); j++) {
                    qm ax = fi.get(i);
                    qm az = fi.get(j);
                    if (ax == null || az == null) {
                        continue;
                    }
                    String dd = az.a[0] + "" + az.a[1] + "" + az.a[2] + "" + az.a[3];
                    String dz = ax.a[0] + "" + ax.a[1] + "" + ax.a[2] + "" + ax.a[3];
                    if (dd.equals(dz)) {
                        fi.set(j, az);

                    }

                }
            }
        }
        for (int cc = 0; cc < fi.size(); cc++) {
            for (int i = 0; i < fi.size(); i++) {
                for (int j = i; j < fi.size(); j++) {
                    if (i == j) {
                        continue;
                    }
                    qm ax = fi.get(i);
                    qm az = fi.get(j);
                    if (ax == az) {
                        fi.remove(az);
                    }
                }
            }
        }
        System.out.println("\n\n\n\n----------------------------------\nThe Prime Implicants are:-\n----------------------------------");
        for (qm ss : fi) {
            for (int i = 0; i < 16; i++) {
                if (ss.n[i] != null) {
                    System.out.print(ss.n[i] + ",");
                }
            }
            System.out.print("\t\t\t");
            for (int i = 0; i < 4; i++) {
                System.out.print(ss.a[i] + ",");
            }
            System.out.println("");
        }
        mta = minTermsAct;

      //System.out.println(fi.size());
        for (int i = 0; i < 15; i++) {
            removeEss();
            removeEss();
            removeld();
        }
        removeEx();

        /*  System.out.println("print fin");
         for(qm ss:fin){
         for(int i=0;i<16;i++){
         if(ss.n[i]!=null)System.out.print(ss.n[i]+",");
         }
         System.out.print("\t\t\t");
         for(int i=0;i<4;i++){
         System.out.print(ss.a[i]+",");
         }
         System.out.println("");
         }
         System.out.println("print fi");
         for(qm ss:fi){
         for(int i=0;i<16;i++){
         if(ss.n[i]!=null)System.out.print(ss.n[i]+",");
         }
         System.out.print("\t\t\t");
         for(int i=0;i<4;i++){
         System.out.print(ss.a[i]+",");
         }
         System.out.println("");
         }*/
        /* if(fi.size()!=0){
         removeS();
         }
         if(fi.size()!=0){
         removeS1();
         }*/
        /* System.out.println("Printing size fi fin o1");
         System.out.println(fi.size());
         System.out.println(fin.size());
         System.out.println(o1.size());*/
        if (fin.size() != 0) {
            System.out.print("\n\n\n\n\nThe reduced expression is :-  ");
            for (int i = 0; i < fin.size(); i++) {
                System.out.print(con(fin.get(i)) + "+");
            }
        }
        if (fi.size() != 0) {
            System.out.print(" { ");
            for (qm aaa : fi) {
                System.out.print(con(aaa) + " + ");
            }
            System.out.print(" }");
        }
        if (fin.size() == 0) {
            System.out.println("There are many Answers to this case");
            System.out.println("One of them is :- A'C + A'B + AC' + A'B'");
            System.exit(0);
        }
        /*if(o1.size()!=0){
         System.out.print("{ ");
         for(int i=0;i<o1.size();i++){
         System.out.print(con(o1.get(i))+"+");
         }
         System.out.print("}");
         }
         if(o2.size()!=0){
         System.out.print("+{ ");
         for(int i=0;i<o2.size();i++){
         System.out.print(con(o2.get(i))+"+");
         }
         System.out.print("}");
         }*/

    }

    static void removeS() {
        for (int i = 0; i < fi.size(); i++) {
            for (int j = 0; j < fi.size(); j++) {
                int no = 0, n1 = 0, n2 = 0;
                if (i == j) {
                    continue;
                }
                for (int k = 0; k < 16; k++) {
                    if (fi.get(i).contain(fi.get(j).n[k]) && fi.get(j).n[k] != null) {
                        no++;//System.out.println("II");
                    }
                }
                for (int k = 0; k < 16; k++) {
                    if (fi.get(j).n[k] != null) {
                        n1++;
                    }
                }
                for (int k = 0; k < 16; k++) {
                    if (fi.get(i).n[k] != null) {
                        n2++;
                    }
                }
                if (n1 == no && n2 == n1) {
                    o1.add(fi.get(j));
                    fi.remove(fi.get(j));
                    j--;//System.out.println("GG");
                }
            }
            o1.add(fi.get(i));
            fi.remove(fi.get(i));
        }

    }

    static void removeld() {
        for (int i = 0; i < fi.size(); i++) {
            for (int j = 0; j < fi.size(); j++) {
                int no = 0, n1 = 0, n2 = 0;
                if (i == j) {
                    continue;
                }
                for (int k = 0; k < 16; k++) {
                    if (fi.get(i).contain(fi.get(j).n[k])
                            && fi.get(j).n[k] != null) {
                        no++;//System.out.println("II");
                    }
                }
                for (int k = 0; k < 16; k++) {
                    if (fi.get(j).n[k] != null) {
                        n1++;
                    }
                }
                for (int k = 0; k < 16; k++) {
                    if (fi.get(i).n[k] != null) {
                        n2++;
                    }
                }
                if (n1 == no && n2 > n1) {
                    fi.remove(fi.get(j));
                    j--;//System.out.println("GG");
                }
            }
        }
    }

    static void removeEss() {
        int n[] = new int[mta.length];
        for (int i = 0; i < n.length; i++) {
            n[i] = 0;
        }
        for (int i = 0; i < mta.length; i++) {
            for (int j = 0; j < fi.size(); j++) {
                if (fi.get(j).contain(Integer.parseInt(mta[i]))) {
                    n[i]++;
                }
            }
        }

        for (int i = 0; i < n.length; i++) {
            if (n[i] == 1) {
                for (int j = 0; j < fi.size(); j++) {
                    if (fi.get(j).contain(Integer.parseInt(mta[i]))) {
                        fin.add(fi.get(j));
                        qm ccc = fi.get(j);
                        fi.remove(ccc);//System.out.println("REmoving");
                        for (int k = 0; k < fi.size(); k++) {
                            for (int kk = 0; kk < 16; kk++) {
                                for (int ll = 0; ll < 16; ll++) {
                                    fi.get(k).removed(ccc.n[ll]);
                                }
                            }
                        }

                    }
                }

            }
        }

    }

    int getD(qm x) {
        int n1 = 0;
        if (this.a[0].equals(x.a[0]) == false) {
            n1 = 0;
        } else if (this.a[1].equals(x.a[1]) == false) {
            n1 = 1;
        } else if (this.a[2].equals(x.a[2]) == false) {
            n1 = 2;
        } else if (this.a[3].equals(x.a[3]) == false) {
            n1 = 3;
        }
        /*if(this.a[4]==x.a[4])n1++;
         if(this.a[5]==x.a[5])n1++;
         if(this.a[6]==x.a[6])n1++;
         if(this.a[7]==x.a[7])n1++;*/
        return n1;
    }

    boolean contain(Integer x) {
        boolean g = false;
        for (int i = 0; i < 16; i++) {
            if (x == this.n[i]) {
                g = true;
                break;
            }
        }
        return g;

    }

    void removed(Integer x) {
        if (x == null) {
            return;
        }
        for (int i = 0; i < 16; i++) {
            if (x == this.n[i]) {
                this.n[i] = null;
                return;
            }
        }
    }

    static String toBinary(int n) {
        int quotient = n, remainder;
        String result = "";
        while (quotient != 0) {
            remainder = quotient % 2;
            quotient = quotient / 2;
            result = Integer.toString(remainder) + result;
        }
        if (result.length() == 3) {
            result = "0" + result;
        }
        if (result.length() == 0) {
            result = "0000";
        }
        if (result.length() == 2) {
            result = "00" + result;
        }
        if (result.length() == 1) {
            result = "000" + result;
        }
        return result;
    }

    int check(qm x) {
        int n1 = 0;
        if (this.a[0].equals(x.a[0])) {
            n1++;
        }
        if (this.a[1].equals(x.a[1])) {
            n1++;
        }
        if (this.a[2].equals(x.a[2])) {
            n1++;
        }
        if (this.a[3].equals(x.a[3])) {
            n1++;
        }
        /*if(this.a[4]==x.a[4])n1++;
         if(this.a[5]==x.a[5])n1++;
         if(this.a[6]==x.a[6])n1++;
         if(this.a[7]==x.a[7])n1++;*/
        return n1;
    }

    static int check1(qm x) {
        int n = 0;
        for (int i = 0; i < 4; i++) {
            if (x.a[i].equalsIgnoreCase("1")) {
                n++;
            }
        }
        return n;
    }
}
