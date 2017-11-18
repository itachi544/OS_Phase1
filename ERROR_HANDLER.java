/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

/**
 *
 * @author Anup Kumar
 */
public class ERROR_HANDLER {

    public static void ERROR(String S) {

        switch (S) {
            case "A":
                System.out.println("Invslid OPCODE");
                //catches the number format exception
                break;
            case "B":
                try {
                    if ((CPU.x > 12) || (CPU.x != (long) CPU.x)) {
                        CPU.flag = true;
                    }
                    CPU.flag1 = false;
                } //catches the number format exception
                catch (NumberFormatException n) {
                    CPU.xc = "NumberFormatException";
                }
                break;
            case "C":
                if (CPU.f.delete()) {
                    System.out.println("");
                }
                break;
            case "E":
                System.err.println("DIvide by Zero Exception");
                try {
                    int d = 0;
                    int a = 4 / d;
                } catch (ArithmeticException e) { // 
                    System.out.println("Division by zero.");
                }
                break;
            case "F":
                try {
                    if (CPU.EA > 256) {

                        CPU.flag = true;

                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Index out of bounds");
                }
                break;
            case "G":
                try {
                    if (CPU.pc > 1000) {
                        CPU.flag = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Suspected infinte loop");
                }
                break;
        }
    }
}
