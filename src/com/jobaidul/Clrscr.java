package com.jobaidul;

//import java.io.IOException;

public class Clrscr {
    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("");
        /*try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.println("");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
