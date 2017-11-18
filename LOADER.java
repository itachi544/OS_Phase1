/*
 * To change this license header, choose L  
 public static void LOADER(String SA,int T) {
 icense Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Math.ceil;
import java.lang.reflect.Array;
import static system.SYSTEM.m;

/**
 *
 * @author Anup Kumar
 */
public class LOADER {

    static CharSequence istinst;
    static CharSequence ndinst;
    static CharSequence rdinst;
    static int CJPCB = 0;
    static String d;
    static CharSequence fthinst;
    static List<Integer> job_idmem = new ArrayList<>();
    static List<Integer> pagesmem = new ArrayList<>();
    static List<Integer> endinstmem = new ArrayList<>();
    static List<Integer> startinstmem = new ArrayList<>();
    static List<Integer> pages = new ArrayList<>();
    static List<Integer> arrays = new ArrayList<>();
    static List<Integer> pm = new ArrayList<>();
    static boolean indirect = false;
    static int index = 0;
    static boolean[] check = new boolean[256];
    static int inds = 0;
    static int o = 0;
    static int y = 0, z = 0;
    static int in = 0;
    static int x = 0;
    static int w = 0;
    static int job_id = 0;
    static int g = 0;
    static int i = 0, j = 0;
    static int cnt = 0;
    static boolean flg = true;
    static boolean fgi = false;
    static boolean flags = true;
    static Queue<Integer> Ready = new LinkedList<Integer>();
    static Queue<Integer> blocked = new LinkedList<Integer>();

    public static void LOADER(int SA, int TS) throws IOException {

        Arrays.fill(check, false);
        checkmem();
        queue();
    }

    public static void checkmem() {
        while (w < PCB.jid.size()) {
            cnt++;
            if (cnt == 5) {
                break;
            }
            int t;
            int r;
            job_id = PCB.getjobid(w);
            int gm = PCB.getinstwords(job_id);
            if (gm % 16 == 0) {
                gm = gm;
            } else {
                gm = gm + ((16 * PCB.getpagesinst(job_id) - gm));
            }
            for (i = 0, j = 0, r = 0; i < 256; i++) {
                if (check[i] == false) {
                    j++;
                    if (j == gm - 1) {
                        fgi = true;
                        break;
                    }
                }
            }
            while (fgi == true) {
                int[] ar = new int[16 * PCB.getpagesinst(job_id)];
                gm = PCB.getinstwords(job_id);
                if (gm % 16 == 0) {
                    gm = gm;
                } else {
                    gm = gm + ((16 * PCB.getpagesinst(job_id) - gm));
                }
                in = PCB.getstartpage(job_id);
                int fg = in;
                for (i = 0; i < 256; i++) {
                    if (check[i] == false) {
                        if (in == fg) {
                            startinstmem.add(i);
                        }
                        String inp = SYSTEM.Disk[in];
                        MEMORY.MEM[i] = inp;
                        int yu = 0;
                        in++;
                        check[i] = true;
                        if (i == gm + g - 1) {
                            job_idmem.add(job_id);
                            endinstmem.add(i);
                          //    int start = endinstmem.get(job_id) - (PCB.getpagesinst(job_id) * 16);
                            //  startinstmem.add(start);
                            g = g + endinstmem.get(job_id);
                            ReadyQueue(job_id);
                            w++;
                            break;
                        }
                    }
                }
                fgi = false;
            }

        }
        cnt = 0;

    }

    public static void dequeue(int x) throws IOException {
        Ready.add(x);
    }

    public static void queue() throws IOException {
        CJPCB = LOADER.Ready.remove();
        PCB.flag.add(1);
        y = PCB.getpc(CJPCB);
        z = PCB.gettrace(CJPCB);
        CPU.CPU(y, z);

    }

    public static int getjob() {
        CJPCB = LOADER.Ready.remove();
        return CJPCB;
    }

    public static void ReadyQueue(int id) {
        Ready.add(id);
    }

    public static void rem_job(int id) {
        int[] are = new int[PCB.getpagesinst(id)];
        int last = endinstmem.get(id);
        int fd = 16 * PCB.getpagesinst(id);
        for (int i = fd - 1; i >= 0; i--) {
            check[i] = false;
        }
        checkmem();

    }

    public static void memman(int id) {
        int d = PCB.getstartpage(id);
        int fg = PCB.getpagesinst(id) + PCB.inputpages.get(id) + PCB.getoutputpage(id);
        for (int i = PCB.getstartpage(id); i < fg * 16; i++) {
            SYSTEM.Disk[i] = null;
        }
    }

    public LOADER() {
    }

    public static void storeotpt(int id) {

        int g = PCB.getoutputpagesstart(id);
        int h = PCB.getoutputpagesend(id);
        for (int i = g; i < h; i++) {

            String gg = SYSTEM.Disk[i];

        }

    }

}
