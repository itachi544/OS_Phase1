/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import java.lang.reflect.Array;
import java.util.*;

/**
 *
 * @author Anup Kumar
 */
public class PCB {

    static List<Integer> jid = new ArrayList<>();
    static int curread = 0;
    static int curwrite = 0;
    static int id;
    static List<Integer> outpt = new ArrayList<>();
    static List<String> trace = new ArrayList<>();
    static List<Integer> pages = new ArrayList<>();
    static List<List> meminpages = new ArrayList<>();
    static List<Integer> pc = new ArrayList<>(Collections.nCopies(150, 0));
    static List<Integer> uppc = new ArrayList<>();
    static List<Integer> ea = new ArrayList<>();
    static List<Integer> jclock = new ArrayList<>();
    static List<Integer> read = new ArrayList<>();
    static List<Integer> write = new ArrayList<>(Collections.nCopies(160, 0));
    static List<Integer> instwords = new ArrayList<>();
    static List<Integer> setpagesinst = new ArrayList<>();
    static List<Integer> inputwords = new ArrayList<>();
    static List<Integer> endpage = new ArrayList<>();
    static List<Integer> startpage = new ArrayList<>();
    static List<Integer> otptpage = new ArrayList<>();
    static List<Integer> otptstartpage = new ArrayList<>();
    static List<Integer> otptendpage = new ArrayList<>();
    static List<Integer> flag = new ArrayList<>(Collections.nCopies(160, 0));
    static List<Integer> inpendpage = new ArrayList<>();
    static List<Integer> inpstartpage = new ArrayList<>();
    static ArrayList<String[]> register = new ArrayList<String[]>();
    static List<Integer> inputpages = new ArrayList<>();
    static int[] arr = new int[100];
    static List<String> input = new ArrayList<>();
    static List<Integer> wrds = new ArrayList<>();

    public PCB() {
    }

    static void setflag(int id) {
        flag.add(id);
    }

    static int getflag(int id) {
        return flag.get(id);
    }

    static void setinputpages(int id) {
        inputpages.add(id);
    }//no of input pages

    static void setjclock(int id) {
        jclock.add(id);
    }//clock

    static int getjclock(int id) {
        return jclock.get(id);
    }

    static void setpc(int id) {
        pc.add(id);
    }

    static int getpc(int id) {

        return pc.get(id);
    }

    static void setuppc(int id) {
        uppc.add(id);
    }

    static int getuppc(int id) {

        return uppc.get(id);
    }

    public void setjobid(int id) {
        jid.add(id);

    }

    static void setread(int id) {

        read.add(id);

    }

    static int getread(int id) {
        return read.get(id);
    }

    static void setwrite(int id) {

        write.add(id);
    }

    static int getwrite(int id) {
        return write.get(id);
    }

    public void setinstwords(int id) {
        instwords.add(id);
    }//no of words of instructions

    static int getinstwords(int id) {
        return instwords.get(id);
    }

    static void setinputwords(int id) {
        inputwords.add(id);
    }// no of input words

    static int getinputwords(int id) {
        return inputwords.get(id);
    }

    public void setstartpage(int id) {
        startpage.add(id);
        //System.out.println("inst start page  "+startpage + startpage.size());
    }//inst start index

    static int getstartpage(int id) {

        return startpage.get(id);

    }

    static void setoutputstartpage(int id) {

        otptstartpage.add(id);
    }//output start page

    static int getoutputpagesstart(int id) {

        return otptstartpage.get(id);

    }

    static void setoutputpages(int id) {
        otptpage.add(id);
        //System.out.println("output pages"+otptpage);
    }//no of output pages

    static int getoutputpage(int id) {
        return otptpage.get(id);
    }

    static void setoutputendpage(int id) {

        otptendpage.add(id);
    }

    static void setpagesinst(int id) {

        setpagesinst.add(id);
    }//no of pages for instructions

    static int getpagesinst(int id) {
        return setpagesinst.get(id);
    }

    static int getoutputpagesend(int id) {
        return otptendpage.get(id);
    }

    public void setinpstartpage(int id) {
        inpstartpage.add(id);
    }

    static int getinpstartpage(int id) {

        return inpstartpage.get(id);

    }

    public void settrace(String tr) {
        trace.add(tr);
    }

    static int gettrace(int tr) {
        int h = Integer.parseInt(trace.get(tr));

        return h;
    }

    static int getjobid(int id) {
        return jid.get(id);
    }
}
