package com.jobaidul;

import java.util.Scanner;

public class AdminsRole {
    Database database;
    Scanner scan = new Scanner(System.in);
    AdminsRole(Database database) {
        this.database = database;
    }
    void play() {
        Clrscr.main(null);
        while (true) {
            System.out.println("Admin Section");
            System.out.println("0. log out");
            System.out.println("1. Change Password");
            System.out.println("2. View librarian");
            System.out.println("3. Add librarian");
            System.out.println("4. Delete librarian");

            boolean invalid = false;
            if (scan.hasNextInt()) {
                int choice = scan.nextInt();
                if (choice == 0) return;
                else if (choice == 1) changePassword();
                else if (choice == 2) viewLibrarians();
                else if (choice == 3) addLibrarian();
                else if (choice == 4) deleteLibrarian();
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
    void viewLibrarians() {
        Clrscr.main(null);
        System.out.println("List of Librarians (sorted according to their name)");
        database.showLibrariansData();
        System.out.printf("Press Enter to return to the admin section...");
        scan.nextLine();  scan.nextLine();
    }
    void addLibrarian() {
        Clrscr.main(null);
        scan.nextLine();
        System.out.println("Add Librarian");
        System.out.printf("Enter name of the Librarian: ");
        String name = scan.nextLine();
        System.out.printf("Enter password for the Librarian: ");
        String password = scan.nextLine();

        Librarian newLibrarian = database.addLibrarian(name, password);
        if (newLibrarian != null) {
            System.out.println("Librarian added Successfully.");
            System.out.println("Name: "+newLibrarian.getName());
            System.out.println("ID: "+newLibrarian.getID());

            Main.saveDatabase();
            System.out.printf("\nPress Enter to return to the admin section...");
            scan.nextLine();  //scan.nextLine();
        }
    }
    void deleteLibrarian() {
        while (true) {
            System.out.printf("Enter Librarian's ID: ");
            if (scan.hasNextInt()) {
                int ID = scan.nextInt();
                Librarian librarian = database.deleteLibrarian(ID);
                if (librarian == null) System.out.println("No such Librarian exists!");
                else {
                    System.out.printf("Librarian: (ID)");
                    System.out.println(librarian.getID()+"    (name)"+librarian.getName());
                    System.out.println("Deleted successfully.");

                    Main.saveDatabase();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
            else {
                scan.nextLine();
                Clrscr.main(null);
                System.out.println("Invalid input! Enter an integer.");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    void changePassword() {
        while (true) {
            System.out.printf("Enter current Password: ");
            scan.nextLine();
            String currentPassword = scan.nextLine();
            if (database.isAdmin(new Admin(currentPassword))) {
                System.out.printf("Enter new Password: ");
                String newPassword = scan.nextLine();
                database.changeAdmin(newPassword);

                System.out.println("Password changed Successfully.");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ;
            }
            else {
                while (true)
                {
                    System.out.println("Password didn't matched!");
                    System.out.println("0. return to previous menu");
                    System.out.println("1. Try Again");

                    int choice;
                    if (scan.hasNextInt())
                    {
                        choice = scan.nextInt();
                        if (choice == 0) return ;
                        else if (choice == 1) break;
                    }
                    else scan.nextLine();
                    Clrscr.main(null);
                }
            }
        }
    }
}