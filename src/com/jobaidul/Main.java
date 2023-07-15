package com.jobaidul;

import java.io.*;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static  Database database;
    static int librariansID = -1;
    public static void main(String[] args) {
        database = new Database();
        if (loadDatabase() == false) return;
        while (true) {
            int role = login();
            if (role == 0) break;
            else if (role == 1) {
                AdminsRole adminsRole = new AdminsRole(database);
                adminsRole.play();
            }
            else if (role == 2) {
                LibrariansRole librariansRole = new LibrariansRole(database, librariansID);
                librariansRole.play();
            }
            Clrscr.main(null);
        }
        saveDatabase();
    }
    static boolean saveDatabase() {
        ObjectOutputStream saveToFile = null;
        try {
            saveToFile = new ObjectOutputStream(new FileOutputStream("database.dat"));

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Couldn't create the database!");
            return false;
        }

        try {
            saveToFile.writeObject(database);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("couldn't save the database");
            return false;
        }
        try {
            if (saveToFile != null)
                saveToFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    static boolean loadDatabase(){
        ObjectInputStream readFromFile = null;
        FileInputStream file = null;

        try {
            file = new FileInputStream("Database.dat");
        } catch (FileNotFoundException e) {
            System.out.println("Creating new database!");
            boolean successful = saveDatabase();
            if (successful) System.out.println("Database Created successfully");
            return successful;
        }
        try {
            readFromFile = new ObjectInputStream(file);
        } catch (IOException e) {
            System.err.println("Couldn't open the database");
            e.printStackTrace();
            return false;
        }
        try {
            database = (Database) readFromFile.readObject();
        } catch (IOException e) {
            System.err.println("Couldn't load the database");
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try {
            if (readFromFile != null)
                readFromFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return  true;
    }
    static  int login()
    {
        //Scanner scan = new Scanner(System.in);
        while (true){
            System.out.println("Log in as");
            System.out.println("0. Close");
            System.out.println("1. Admin");
            System.out.println("2. Librarian");

            int choice; boolean invalid = false;
            if (scan.hasNextInt())
            {
                choice = scan.nextInt();
                if (choice == 0) return 0;
                else if (choice == 1) {
                    if (admin_login()) return 1;
                }
                else if (choice == 2) {
                    if (librarian_login()) return 2;
                }
                else invalid = true;
            }
            else {
                scan.nextLine();
                invalid = true;
            }

            Clrscr.main(null);
            if (invalid) System.out.println("Invalid input");

        }
    }
    static boolean admin_login()
    {
        Clrscr.main(null);
        while (true)
        {
            System.out.println("Admin Log in   (default Password '123')");
            System.out.printf("Enter password: ");
            scan.nextLine();
            String password = scan.nextLine();

            if (database.isAdmin(new Admin(password))) return true;
            else
            {
                while (true)
                {
                    System.out.println("Invalid Password");
                    System.out.println("0. return to previous menu");
                    System.out.println("1. Try Again");

                    int choice;
                    if (scan.hasNextInt())
                    {
                        choice = scan.nextInt();
                        if (choice == 0) return false;
                        else if (choice == 1) break;
                    }
                    else scan.nextLine();
                    Clrscr.main(null);
                }
            }
        }
    }
    static boolean librarian_login()
    {
        Clrscr.main(null);
        while (true)
        {
            System.out.println("Librarian Log in");
            while (true)
            {
                System.out.printf("Enter ID: ");
                if (scan.hasNextInt() == false) {
                    scan.nextLine();
                    System.out.println("invalid input!");
                }
                else break;;
            }
            int ID = scan.nextInt();
            System.out.printf("Enter password: ");
            scan.nextLine();
            String passwrd = scan.nextLine();
            if (database.isLibrarian(new Librarian(ID, null, passwrd))) {
                librariansID = ID;
                return true;
            }
            else
            {
                while (true)
                {
                    System.out.println("Invalid Password");
                    System.out.println("0. return to previous menu");
                    System.out.println("1. Try Again");

                    int choice;
                    if (scan.hasNextInt())
                    {
                        choice = scan.nextInt();
                        scan.nextLine();
                        if (choice == 0) return false;
                        else if (choice == 1) break;
                    }
                    Clrscr.main(null);
                }
            }
        }
    }
}