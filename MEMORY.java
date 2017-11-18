/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import java.util.*;

/**
 *
 * @author Anup Kumar
 */
public class MEMORY {

    static String[] MEM = new String[256];
    static String z;
    static String XC;
    static int y;

    //static String[] mn = new String[]
    //MEMORY method is used to access the memory
    public static String MEMORY(String X, int Y, String Z) {
        String x = X;
        y = Y;
        z = Z;
        if (x.equalsIgnoreCase("Read")) {
            XC = READ();
        } else if (x.equalsIgnoreCase("Write")) {
            WRITE();
        } else if (x.equalsIgnoreCase("Dump")) {
            DUMP();
        }
        return XC;
    }//the READ function reads values from Memory 

    public static String READ() {

        z = MEM[y];
        return z;
    }//the WRITE function writes values TO Memory

    public static void WRITE() {

        MEM[y] = z;
    }//DUMP prints the Instructions from the memory

    public static void DUMP() {
        int size = MEM.length;
        int count = 0;
        int r = 8;
        System.out.print("00000000   ");
        for (int i = 0; i < size; i++) {
            count++;

            System.out.print(" ");
            System.out.print(String.format("%8s", MEM[i]).replace(" ", "0"));

            if (count == 7) {
                System.out.println("");
                count = 0;
                System.out.print(String.format("%08X", r & 0xFFFFF) + "   ");
                r = r + 8;
            }

        }

    }

}
