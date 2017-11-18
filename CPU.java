/* Brief Description of CPU SubSystem
 *The CPU is called by the loader.It takes the instruction from the main memory and executes it 1 by 1.
 *It contains a Instruction format which contains the details of the instruction.
 *A program counter is implemented which shows the address of the next instructio to be executed.
 *A switch case is implemented to perform the execution of the desired instruction.
 *A Trace file is generated if required.
 *A output file also is generated showung the results of the terminated job.
 *A scanner method is used to get the input from the keyboard.
 *
 */
package system;

import java.io.File;
import java.util.*;
import java.math.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Locale;
import static system.SYSTEM.Disk;

/**
 *
 * @author Anup Kumar
 */
public class CPU {

    static String[] IR = new String[31];
    static String[] Reg = new String[16];
    static String[] jreg = new String[16];
    static PrintWriter p = null;
    static PrintWriter op = null;
    static int ba;
    static String pc1;
    static int re = 0;
    static boolean sig = true;
    static String sol;
    static String OP1;
    static String x1;
    static String xc;
    static String x2;
    static String x3;
    static int pc;
    static PrintStream con;
    static int EA;
    static int dp;
    static String inst;
    static String memval;
    static int reg1_index = 0;
    static String OP = "";
    static int dy = 0;
    static int I;
    static int n;
    static String A;
    static String binary = "";
    static String B;
    static int curjob = 0;
    static Long x;
    static int reg_index = 0;
    static String indirect_addr;
    static String DADDR;
    static int DADDRE;
    static boolean flag = false;
    static boolean flag1 = true;
    static File f;
    static int value = 0;
    static boolean indirect = false;
    static int clock = 0;
    static int jclock = 0;
    static int j;
//the CPU executes all the Instructions

    public static void CPU(int X, int Y) throws IOException {
        n = Y;
        pc = X;
           // EA =0;

        //Trace file is generated
        f = new File("Trace.txt");
        FileWriter fw = new FileWriter(f);
        p = new PrintWriter(fw);
        //Output file is generated
        op = new PrintWriter(new FileWriter("Execution Profile.txt", false));
        p.write("          PC      " + "                   INSTRUCTION      " + "                        CONTENT OF A BEFORE EXECUTION     " + "       CONTENT OF A AFTER EXECUTION   " + "                          EA     ");
        p.println("\n");
        jclock = 0;
        //while (flag == false) 
        {
            if (OP.contains("of") || OP.contains("10") || jclock == 40) {
                PCB.setuppc(pc);
                jreg = Reg;
                PCB.register.add(jreg);
                PCB.flag.set(j, 1);
                LOADER.dequeue(j);
                j = LOADER.getjob();
                if (PCB.flag.get(j) == 0) {
                    pc = LOADER.startinstmem.get(j);//Program Counter
                    jclock = 0;
                } else {
                    pc = PCB.getuppc(j);
                    Reg = PCB.register.get(j);
                    jclock = 0;
                }

            }
            curjob = j;
            inst = MEMORY.MEMORY("READ", pc, "");
            binary = CONVERSIONS.HEXBIN(inst);//Instruction
            indirect_addr = binary.substring(0, 1);//A
            OP = binary.substring(1, 8);//OP Code
            OP = CONVERSIONS.BINHEX(OP);
            A = binary.substring(8, 12);
            reg1_index = reg_index;
            reg_index = CONVERSIONS.BINDEC(A);
            B = binary.substring(12, 16);
            B = CONVERSIONS.BINHEX(B);
            DADDR = binary.substring(16, 32);
            DADDRE = CONVERSIONS.BINDEC(DADDR);
            if (indirect_addr.equals("1") && Integer.parseInt(B, 16) == 0) {
                EA = CONVERSIONS.HEXDEC(MEMORY.MEMORY("READ", DADDRE, ""));
            } else if ((indirect_addr.equals("0") && Integer.parseInt(B, 16) == 0)) {
                EA = DADDRE;
            } else if ((indirect_addr.equals("0") && Integer.parseInt(B, 16) > 0)) {

                EA = Integer.parseInt(Reg[Integer.parseInt(B, 16)]) + DADDRE;
            } else if (indirect_addr.equals("1") && Integer.parseInt(B, 16) == 0) {
                EA = Integer.parseInt(Reg[Integer.parseInt(B, 16)]) + Integer.parseInt(MEMORY.MEMORY("READ", DADDRE, memval));
            }
            if (EA > 256) {
                ERROR_HANDLER.ERROR("F");
            }
            if (pc > 1000) {
                ERROR_HANDLER.ERROR("G");
            }

//Switch case is used to execute the instruction
            switch (OP) {
                case "00"://HALT
                    // flag = true;
                    pc++;
                    pc1 = String.format("%08X", pc & 0xFFFFF);
                    x1 = String.format("%08X", reg1_index & 0xFFFFF);
                    x2 = String.format("%08X", reg_index & 0xFFFFF);
                    x3 = String.format("%08X", EA & 0xFFFFF);
                    OP1 = String.format("%8s", OP).replace(" ", "0");
                    p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                    p.println("\n");
                    clock++;
                    jclock++;
                    LOADER.rem_job(curjob);
                    LOADER.storeotpt(curjob);
                    LOADER.memman(curjob);
                    int j = LOADER.getjob();
                    if (PCB.flag.get(j) == 0) {
                        pc = LOADER.startinstmem.get(j);//Program Counter
                        jclock = 0;
                    } else {
                        pc = PCB.getuppc(j);
                        Reg = PCB.register.get(j);
                    }

                    break;
                case "0f": //READ
                    int r = 0;
                    int s = PCB.getinpstartpage(curjob);
                    while (r < PCB.getinputwords(curjob)) {
                        String se = SYSTEM.Disk[s];
                        int y = EA;
                        String h = String.format("%8s", Integer.toBinaryString(y)).replace(' ', '0');

                        List<String> g = split(h, 4);
                        String h1 = g.get(0);
                        String h2 = g.get(1);
                        int d1 = Integer.parseInt(h1, 2);
                        int d2 = Integer.parseInt(h2, 2);
                        if (d1 == 0) {
                            y = LOADER.startinstmem.get(CPU.curjob) + d2;
                        } else if (d1 == 1) {
                            y = (LOADER.startinstmem.get(CPU.curjob) + 16) + d2;
                        } else if (d1 == 2) {
                            y = (LOADER.startinstmem.get(CPU.curjob) + 32) + d2;
                        } else if (d1 == 3) {
                            y = (LOADER.startinstmem.get(CPU.curjob) + 48) + d2;
                        }
                        MEMORY.MEMORY("WRITE", y + r, se);
                        s++;;
                        r++;
                        if (r == 4) {
                            break;
                        }
                    }
                    pc++;
                    pc1 = String.format("%08X", pc & 0xFFFFF);
                    x1 = String.format("%08X", reg1_index & 0xFFFFF);
                    x2 = String.format("%08X", reg_index & 0xFFFFF);
                    OP1 = String.format("%8s", OP).replace(" ", "0");
                    x3 = String.format("%08X", EA & 0xFFFFF);
                    p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                    p.println("\n");
                    clock = clock + 10;
                    jclock = jclock + 10;
                    break;
                case "01": //LOAD
                    Reg[reg_index] = MEMORY.MEMORY("READ", EA, "");
                    pc++;
                    pc1 = String.format("%08X", pc & 0xFFFFF);
                    x1 = String.format("%08X", reg1_index & 0xFFFFF);
                    x2 = String.format("%08X", reg_index & 0xFFFFF);
                    x3 = String.format("%08X", EA & 0xFFFFF);
                    OP1 = String.format("%8s", OP).replace(" ", "0");
                    p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                    p.println("\n");
                    clock++;
                    jclock++;
                    break;
                case "02"://Store
                    MEMORY.MEMORY("WRITE", EA, Reg[reg_index]);
                    pc++;
                    x1 = String.format("%08X", reg1_index & 0xFFFFF);
                    x2 = String.format("%08X", reg_index & 0xFFFFF);
                    x3 = String.format("%08X", EA & 0xFFFFF);
                    pc1 = String.format("%08X", pc & 0xFFFFF);
                    OP1 = String.format("%8s", OP).replace(" ", "0");
                    p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                    p.println("\n");
                    clock++;
                    jclock++;
                    break;
                case "03"://ADD
                    int x = Integer.parseInt(CONVERSIONS.HEXBIN(Reg[reg_index]), 2);
                    int y = CONVERSIONS.HEXDEC(MEMORY.MEMORY("READ", EA, ""));
                    x = x + y;
                    Reg[reg_index] = CONVERSIONS.DECHEX(x);
                    x1 = String.format("%08X", reg1_index & 0xFFFFF);
                    x2 = String.format("%08X", reg_index & 0xFFFFF);
                    x3 = String.format("%08X", EA & 0xFFFFF);
                    pc++;
                    p.write("\r");
                    OP1 = String.format("%8s", OP).replace(" ", "0");
                    pc1 = String.format("%08X", pc & 0xFFFFF);
                    p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                    p.println("\n");
                    clock++;
                    jclock++;
                    break;
                case "04"://Subtract
                    x = (int) Long.parseLong(CONVERSIONS.HEXBIN(Reg[reg_index]), 2);

                    y = CONVERSIONS.BINDEC(CONVERSIONS.HEXBIN(MEMORY.MEMORY("READ", EA, "")));
                    x = x - y;
                    Reg[reg_index] = CONVERSIONS.DECHEX(x);
                    pc++;
                    pc1 = String.format("%08X", pc & 0xFFFFF);
                    x1 = String.format("%08X", reg1_index & 0xFFFFF);
                    x2 = String.format("%08X", reg_index & 0xFFFFF);
                    x3 = String.format("%08X", EA & 0xFFFFF);
                    OP1 = String.format("%8s", OP).replace(" ", "0");
                    p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                    p.println("\n");
                    clock++;
                    jclock++;
                    break;
                case "05"://Multiply
                    x = Integer.parseInt(CONVERSIONS.HEXBIN(Reg[reg_index]), 2);
                    y = CONVERSIONS.BINDEC(CONVERSIONS.HEXBIN(MEMORY.MEMORY("READ", EA, "")));
                    x = x * y;
                    Reg[reg_index] = CONVERSIONS.BINHEX(CONVERSIONS.DECBIN(x));
                    pc++;
                    pc1 = String.format("%08X", pc & 0xFFFFF);
                    x1 = String.format("%08X", reg1_index & 0xFFFFF);
                    x2 = String.format("%08X", reg_index & 0xFFFFF);
                    x3 = String.format("%08X", EA & 0xFFFFF);
                    OP1 = String.format("%8s", OP).replace(" ", "0");
                    p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                    p.println("\n");
                    clock = clock + 2;
                    jclock = jclock + 2;
                    break;
                case "06"://Divide
                    x = Integer.parseInt(CONVERSIONS.HEXBIN(Reg[reg_index]), 2);
                    y = CONVERSIONS.BINDEC(CONVERSIONS.HEXBIN(MEMORY.MEMORY("READ", EA, "")));
                    x = x / y;
                    if (y == 0) {
                        ERROR_HANDLER.ERROR("E");
                    }
                    Reg[reg_index] = CONVERSIONS.BINHEX(CONVERSIONS.DECBIN(x));
                    pc++;
                    pc1 = String.format("%08X", pc & 0xFFFFF);
                    x1 = String.format("%08X", reg1_index & 0xFFFFF);
                    x2 = String.format("%08X", reg_index & 0xFFFFF);
                    x3 = String.format("%08X", EA & 0xFFFFF);
                    OP1 = String.format("%8s", OP).replace(" ", "0");
                    p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                    p.println("\n");
                    clock = clock + 2;
                    jclock = jclock + 2;
                    break;
                case "07"://Shift Left
                    x = Integer.parseInt(CONVERSIONS.HEXBIN(Reg[reg_index]), 2);
                    y = CONVERSIONS.BINDEC(CONVERSIONS.HEXBIN(MEMORY.MEMORY("READ", EA, "")));
                    x = x << y;
                    Reg[reg_index] = CONVERSIONS.BINHEX(CONVERSIONS.DECBIN(x));
                    pc++;
                    pc1 = String.format("%08X", pc & 0xFFFFF);
                    x1 = String.format("%08X", reg1_index & 0xFFFFF);
                    x2 = String.format("%08X", reg_index & 0xFFFFF);
                    x3 = String.format("%08X", EA & 0xFFFFF);
                    OP1 = String.format("%8s", OP).replace(" ", "0");
                    p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                    p.println("\n");
                    clock++;
                    jclock++;
                    break;
                case "08"://Shift Right
                    x = Integer.parseInt(CONVERSIONS.HEXBIN(Reg[reg_index]), 2);
                    y = CONVERSIONS.BINDEC(CONVERSIONS.HEXBIN(MEMORY.MEMORY("READ", EA, "")));
                    x = x >> y;
                    Reg[reg_index] = CONVERSIONS.BINHEX(CONVERSIONS.DECBIN(x));
                    pc++;
                    pc1 = String.format("%08X", pc & 0xFFFFF);
                    x1 = String.format("%08X", reg1_index & 0xFFFFF);
                    x2 = String.format("%08X", reg_index & 0xFFFFF);
                    x3 = String.format("%08X", EA & 0xFFFFF);
                    OP1 = String.format("%8s", OP).replace(" ", "0");
                    p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                    p.println("\n");
                    clock++;
                    jclock++;
                    break;
                case "09"://Branch on Minus
                    x = Integer.parseInt(CONVERSIONS.HEXBIN(Reg[reg_index]), 2);
                    if (x < 0) {
                        pc = EA;
                        pc1 = String.format("%08X", pc & 0xFFFFF);
                        p.write("\r");
                        p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                        p.println("\n");
                    } else {
                        pc++;
                    }
                    pc1 = String.format("%08X", pc & 0xFFFFF);
                    x1 = String.format("%08X", reg1_index & 0xFFFFF);
                    x2 = String.format("%08X", reg_index & 0xFFFFF);
                    x3 = String.format("%08X", EA & 0xFFFFF);
                    OP1 = String.format("%8s", OP).replace(" ", "0");
                    p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                    p.println("\n");
                    clock++;
                    jclock++;
                    break;
                case "0a"://Branch on Plus
                    x = (int) Long.parseLong(CONVERSIONS.HEXBIN(Reg[reg_index]), 2);
                    // x = CONVERSIONS.HEXDEC(Reg[reg_index]);
                    // x = Integer.parseInt(Reg[reg_index], 16);
                    //int giventop = Integer.decode(Reg[reg_index]);
                    if (x > 0) {
                        pc = EA;
                    } else {
                        pc++;
                    }
                    pc1 = String.format("%08X", pc & 0xFFFFF);
                    x1 = String.format("%08X", reg1_index & 0xFFFFF);
                    x2 = String.format("%08X", reg_index & 0xFFFFF);
                    x3 = String.format("%08X", EA & 0xFFFFF);
                    OP1 = String.format("%8s", OP).replace(" ", "0");
                    p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                    p.println("\n");
                    clock++;
                    jclock++;
                    break;
                case "0b"://Branch on Zero
                    //   x = Integer.parseInt(CONVERSIONS.HEXBIN(Reg[reg_index]), 2);
                    x = (int) Long.parseLong(CONVERSIONS.HEXBIN(Reg[reg_index]), 2);
                    if (x == 0) {
                        pc = EA;
                        pc1 = String.format("%08X", pc & 0xFFFFF);
                        p.write("\r");
                        x1 = String.format("%08X", reg1_index & 0xFFFFF);
                        x2 = String.format("%08X", reg_index & 0xFFFFF);
                        x3 = String.format("%08X", EA & 0xFFFFF);
                        OP1 = String.format("%8s", OP).replace(" ", "0");
                        p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                        p.printf("\n");
                        clock++;
                        jclock++;
                    } else {
                        pc++;
                        pc1 = String.format("%08X", pc & 0xFFFFF);
                        x1 = String.format("%08X", reg1_index & 0xFFFFF);
                        x2 = String.format("%08X", reg_index & 0xFFFFF);
                        x3 = String.format("%08X", EA & 0xFFFFF);
                        OP1 = String.format("%8s", OP).replace(" ", "0");
                        p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                        p.println("\n");
                    }
                    clock++;
                    jclock++;
                    break;
                case "0c"://Branch and Link
                    (Reg[reg_index]) = MEMORY.MEMORY("READ", pc, "");
                    pc = EA;
                    p.write("\r");
                    pc1 = String.format("%08X", pc & 0xFFFFF);
                    x1 = String.format("%08X", reg1_index & 0xFFFFF);
                    x2 = String.format("%08X", reg_index & 0xFFFFF);
                    x3 = String.format("%08X", EA & 0xFFFFF);
                    OP1 = String.format("%8s", OP).replace(" ", "0");
                    p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                    p.println("\n");
                    clock = clock + 2;
                    jclock = jclock + 2;
                    break;
                case "10"://Write
                    int yd = 0;
                    int po = PCB.otptstartpage.get(curjob);
                    y = EA;
                    String h = String.format("%8s", Integer.toBinaryString(y)).replace(' ', '0');
                    List<String> g = split(h, 4);
                    String h1 = g.get(0);
                    String h2 = g.get(1);
                    int d1 = Integer.parseInt(h1, 2);
                    int d2 = Integer.parseInt(h2, 2);
                    if (d1 == 0) {
                        y = LOADER.startinstmem.get(CPU.curjob) + d2;
                    } else if (d1 == 1) {
                        y = (LOADER.startinstmem.get(CPU.curjob) + 16) + d2;
                    } else if (d1 == 2) {
                        y = (LOADER.startinstmem.get(CPU.curjob) + 32) + d2;
                    } else if (d1 == 3) {
                        y = (LOADER.startinstmem.get(CPU.curjob) + 48) + d2;
                    }
                    EA = y;
                    String hj = MEMORY.MEMORY("READ", EA + 0, "");
                    SYSTEM.Disk[po] = hj;
                    po++;
                    hj = MEMORY.MEMORY("READ", EA + 1, "");
                    SYSTEM.Disk[po] = hj;
                    po++;
                    hj = MEMORY.MEMORY("READ", EA + 2, "");
                    SYSTEM.Disk[po] = hj;
                    po++;
                    hj = MEMORY.MEMORY("READ", EA + 3, "");
                    SYSTEM.Disk[po] = hj;
                    po++;
                    PCB.otptstartpage.set(curjob, po);
                    p.println("\n");
                    pc++;
                    pc1 = String.format("%08X", pc & 0xFFFFF);
                    x1 = String.format("%08X", reg1_index & 0xFFFFF);
                    x2 = String.format("%08X", reg_index & 0xFFFFF);
                    x3 = String.format("%08X", EA & 0xFFFFF);
                    OP1 = String.format("%8s", OP).replace(" ", "0");
                    p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                    clock = clock + 10;
                    jclock = jclock + 10;
                    break;
                case "11"://Dump Memory
                    MEMORY.MEMORY("DUMP", 0, null);
                    pc++;
                    pc1 = String.format("%08X", pc & 0xFFFFF);
                    p.write("\r");
                    x1 = String.format("%08X", reg1_index & 0xFFFFF);
                    x2 = String.format("%08X", reg_index & 0xFFFFF);
                    x3 = String.format("%08X", EA & 0xFFFFF);
                    OP1 = String.format("%8s", OP).replace(" ", "0");
                    p.printf("\t" + pc1 + " \t\t\t" + OP1 + " \t\t\t\t\t" + x1 + " \t\t\t\t" + x2 + "\t\t\t\t\t" + x3);
                    clock++;
                    jclock++;
                    break;
                default:
                    ERROR_HANDLER.ERROR("A");
            }
        }
        Exec();
//The output file contains the following
        op.write("Cumulative Job Identification number  =   1\n");
        op.printf("\r\n");
        if (flag = true) {
            if (flag1 = false) {
                op.write("Execution =  " + "abnormal");
                op.write("Type of Error :" + xc);
            } else if (flag1 = true) {
                op.write("Execution =  " + " normal");
            }
        }
        op.printf("\r\n");
        String sol1 = String.format("%8s", sol).replace(" ", "0");
        op.write("Output (HEX) = \n" + sol1);
        op.printf("\r\n");
        String clock1 = String.format("%08X", clock & 0xFFFFF);
        op.write("CLock Value (HEX) = " + clock1);
        op.printf("\r\n");
        op.write("Run Time (DEC)  =  " + clock);
        op.printf("\r\n");
        p.close();
        op.close();
        if (n != 1) {
            ERROR_HANDLER.ERROR("C");
        }
    }

    public static List<String> split(String text, int size) {
        List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;

    }

    public static void Exec() throws IOException {

        f = new File("Execution Profile.txt");
        FileWriter fl = new FileWriter(f);
        p = new PrintWriter(fl);
        //Output file is generated
        op = new PrintWriter(new FileWriter("Execution Profile.txt", false));

    }
}
