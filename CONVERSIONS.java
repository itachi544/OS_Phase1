/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import static system.CPU.p;

/**
 *
 * @author Anup Kumar
 */
//All the conversions are places in this calss
public class CONVERSIONS {
    //conversion from hexadecimal to binary is done in this method

    public static String HEXBIN(String X) {
        long value = Long.parseLong(X, 16);
        String value1 = Long.toBinaryString(value);
        for (int i = value1.length(); i < 32; i++) {
            value1 = "0".concat(value1);
        }
        return value1;
    }  //conversion from binary to hexadecimal is done in this method     

    public static String BINHEX(String Y) {
        int g = Integer.parseInt(Y, 2);
        String val = Integer.toString(g, 16);
        val = String.format("%2s", val).replace(" ", "0");
        return val;
    }
//conversion from binary to decimal is done in this method     

    public static int BINDEC(String Z) {
        int g = Integer.parseInt(Z, 2);
        return g;
    }      //conversion from decimal to binary is done in this method     

    public static String DECBIN(int Z) {
        String g = Integer.toBinaryString(Z);
        return g;
    }      //conversion from  hexadecimal to decimal is done in this method     

    public static int HEXDEC(String S) {
        int dec = Integer.parseInt(S.trim(), 16);
        return dec;
    }
    //conversion from decimal to hexadecimal is done in this method        

    public static String DECHEX(int X) {
        String value1 = Integer.toHexString(X);
        return value1;
    }
}
