/*
 * NAME : PHUTANE ANUP KUMAR
 * COURSE NUMBER : CS5323 Design and Implementation of OS - 2
 * Assignment Title : STEP - 1(OS-2 Project)
 * DATE : 2/25/2016
 *
 * A Brief Description of the Class :
 * Ths System class reads the data from the file. 
 *It passes the Starting address and Trace switch value to th eLoader. 
 *It calls the Loader to load the instructions into the Memory.
 *The Loader calls the CPU to execute the instructions.
 */
package system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Math.ceil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Anup Kumar
 */
public class SYSTEM {

    static int iCount;
    static int i = 0, h = 0;
    static int j = 0;
    static Scanner scan;
    static CharSequence a, b, c, d;
    static File file;
    static int nop = 0;
    static int Read = 0;
    static int jobid = 0;
    static List<String> bw = new ArrayList<>();
    static int m = 0;
    static boolean[] diskpages = new boolean[4096];
    static List<String> mem = new ArrayList<>();
    static List<String> inst = new ArrayList<>();
    static List<String> input = new ArrayList<>();
    static List<String> job = new ArrayList<>();
    static int index = 0;
    static int currentRead = 0;
    static int inputpages = 0;
    static String stline;
    static int optlines = 0;
    static int noofwords = 0;
    static String lstline;
    static String[] BaseAddress;
    static String[] DISP;
    static String[] Disk = new String[4096];
    static int indexes;
    static String line = "";
    static int outpages = 0;
    static int ind = 0;
    static int Trace;
    static int cur = 0;
    static int counter = 0;
    static int num = 0;
    static int datapages = 0;
    static int totalpagesreq = 0;
    static int totalpages = 256;
    static boolean flg = false;
    static boolean flag = true;
    static boolean fg = false;
    static int line_Number = 1;
    static String no_Of_Pages;
    static String out_pages;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException {

        Arrays.fill(diskpages, false);
        Arrays.fill(Disk, null);
        String data = null;
        String list = null;
        String inputFile = args[0];
        if (args.length == 0) {
            inputFile = args[0];
        } else {
            if (args[0].contains(".txt")) {
                inputFile = args[0];
            } else {
                inputFile = args[0] + ".txt";
            }
        }
        file = new File(inputFile);
        scan = new Scanner(file);
        Disk_Manager();

    }

    public static void Disk_Manager() throws IOException {
        try {  // Reads the input file
            PCB pcb = new PCB();
            for (int yu = 0; yu < line_Number; yu++) {
                line = scan.nextLine();
            }
            while (scan.hasNextLine()) {
                while (flag == true) {
                    if (!line.contains("**JOB")) {
                        System.out.println("MISSING **JOB");
                        while (!scan.nextLine().contains("**FIN")) {

                        }
                        continue;
                    } else {
                        no_Of_Pages = line.substring(6, 8);
                        out_pages = line.substring(9, 11);//output pages in hex
                        line = scan.nextLine();
                    }
                    String words = line.substring(3);
                    double now = Integer.decode("0x" + words);//pages for inst
                    now = Math.ceil(now / 16.0);//no of words
                    int giventop = Integer.decode("0X" + no_Of_Pages);//total no pages = data + input
                    double out_p = Integer.decode("0X" + out_pages);
                    out_p = Math.ceil(out_p / 4.0);//output pages
                    int data_pages = (int) (giventop - now);//input pages
                    outpages = (int) out_p;
                    PCB.setoutputpages(outpages);
                    line = scan.nextLine();
                    line_Number++;
                    int r = 0;
                    currentRead = 0;
                    Read = 0;
                    inst.clear();
                    input.clear();
                    while (line.substring(2, 2) != " " && line.length() > 7) {
                        bw = split(line, 8);
                        currentRead = currentRead + bw.size();
                        for (i = 0; i < bw.size(); i++) {
                            inst.add(bw.get(i));
                        }
                        line = scan.nextLine();
                        line_Number++;
                        if ((line.substring(2, 2) == " ") || line.length() == 4 || line.length() < 8 || line.contains("FIN") || line.contains("JOB")) {
                            break;
                        }
                    }
                    for (i = 0, j = 0; i < 4096; i++) {
                        if (diskpages[i] == false) {

                            double top = Math.ceil(currentRead / 16.0);
                            int tops = (int) top;
                            j++;
                            if (j == (tops * 16) - 1) {
                                fg = true;
                                break;
                            }

                        }
                    }

                    while (fg == true) {
                        double top = Math.ceil(currentRead / 16.0);
                        int tops = (int) top;

                        for (i = 0, j = 0, r = 0; i < 4096; i++) {

                            if (diskpages[i] == false) {
                                if (r == 0) {
                                    pcb.setstartpage(i);
                                }
                                String hg = inst.get(r);
                                Disk[i] = hg;
                                r++;
                                j++;
                                diskpages[i] = true;
                                if (j == currentRead) {
                                    if (currentRead % 16 == 0) {
                                    } else {
                                        int l = 0;
                                        i++;
                                        while (l < (((16 * tops) - currentRead))) {
                                            Disk[i] = null;;
                                            l++;
                                            i++;
                                            diskpages[i] = true;
                                        }
                                    }
                                    PCB.setpagesinst(tops);
                                    pcb.setinstwords(currentRead);
                                    pcb.setjobid(jobid);
                                    jobid++;
                                    break;

                                }
                            }
                        }

                        for (i = 0, j = 0, r = 0; i < 4096; i++) {
                            if (diskpages[i] == false) {

                                if (r == 0) {
                                    PCB.setoutputstartpage(i);
                                }
                                r++;
                                Disk[i] = null;
                                diskpages[i] = true;
                                j++;
                                if (j == outpages * 16) {
                                    PCB.setoutputendpage(i);
                                    break;
                                }
                            }
                        }
                        fg = false;
                    }

                    if (line.length() > 5) {
                        System.out.println("EITHER WRONG TRACE BIT OR PROGRAM TOO LONG");
                        while (!scan.nextLine().contains("**FIN")) {
                        }
                    } else {
                        if (line.length() == 4) {
                            String trace = line.substring(3, 4);
                            pcb.settrace(trace);
                        }
                    }
                    line = scan.nextLine();
                    line_Number++;
                    if (!line.contains("**DATA")) {
                        System.out.println("**DATA MISSING");
                        while (!scan.nextLine().contains("**FIN")) {

                        }
                    } else {
                        line = scan.nextLine();
                        line_Number++;
                        if (line.contains("**FIN")) {
                            PCB.setinputwords(0);
                            PCB.setinputpages(0);
                            pcb.setinpstartpage(0);

                        }
                        while (line.substring(2, 2) != " " && line.length() > 7) {
                            bw = split(line, 8);
                            Read = Read + bw.size();
                            for (i = 0; i < bw.size(); i++) {
                                input.add(bw.get(i));
                            }
                            line = scan.nextLine();
                            if ((line.substring(2, 2) == " ") || line.length() == 4 || line.length() < 8 || line.contains("FIN") || line.contains("JOB")) {

                                if (input.size() != 0) {

                                    for (i = 0, j = 0, r = 0; i < 4096; i++) {
                                        if (diskpages[i] == false) {
                                            j++;
                                            double top = Math.ceil(Read / 16.0);
                                            int tops = (int) top;

                                            if (j == (tops * 16) - 1) {
                                                fg = true;
                                                break;
                                            }

                                        }
                                    }
                                }
                            }
                        }
                        while (fg == true) {
                            double top = ceil(Read / 16.0);
                            int tops = (int) top;
                            for (i = 0, r = 0, j = 0; i < 4096; i++) {
                                if (diskpages[i] == false) {
                                    j++;
                                    if (r == 0) {
                                        pcb.setinpstartpage(i);
                                    }
                                    Disk[i] = (String) input.get(r++);
                                    diskpages[i] = true;
                                    if (j == Read) {
                                        if (Read % 16 == 0) {
                                        } else {
                                            int l = 0;
                                            while (l < (((16 * tops) - Read))) {
                                                Disk[i] = null;;
                                                i++;
                                                l++;
                                                diskpages[i] = true;
                                            }
                                        }
                                        PCB.setinputpages(tops);
                                        pcb.setinputwords(Read);
                                        break;
                                    }
                                }
                            }
                            fg = false;

                        }

                    }

                    if (!line.contains("**FIN")) {
                        while (!scan.nextLine().contains("**JOB")) {
                        }
                    }

                    if (!scan.hasNextLine()) {
                        LOADER loader = new LOADER();
                        LOADER.LOADER(0, 0);//Loader is called with starting address and trace switch as arguments.
                        flag = false;
                    } else if (ind == 4096) {
                        line_Number = line_Number - (currentRead + Read);
                        LOADER loader = new LOADER();
                        LOADER.LOADER(0, 0);//Loader is called with starting address and trace switch as arguments.
                    } else {
                        line = scan.nextLine();
                        line_Number++;
                    }
                }

            }

        } catch (FileNotFoundException ex) {
            System.out.println("Exception : " + ex.getMessage());
        }

    }

    public static List<String> split(String text, int size) {
        List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;

    }
}
