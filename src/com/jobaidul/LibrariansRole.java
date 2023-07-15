package com.jobaidul;

import java.util.Scanner;

public class LibrariansRole {
    Database database; int librariansID;
    Scanner scan = new Scanner(System.in);
    LibrariansRole(Database database, int librariansID) {
        this.database = database;
        this.librariansID = librariansID;
    }
    void play() {
        Clrscr.main(null);
        while (true) {
            System.out.println("Librarian Section");
            System.out.println("0. log out");
            System.out.println("1. Add Book");
            System.out.println("2. View Book list");
            System.out.println("3. Issue Book");
            System.out.println("4. View Issued Book");
            System.out.println("5. Return Book");
            System.out.println("6. Add Member");
            System.out.println("7. View Member list");

            boolean invalid = false;
            if (scan.hasNextInt()) {
                int choice = scan.nextInt();
                if (choice == 0) return;
                else if (choice == 1) addBook();
                else if (choice == 2) viewBookList();
                else if (choice == 3) issueBook();
                else if (choice == 4) viewIssuedBook();
                else if (choice == 5) returnBook();
                else if (choice == 6) addMember();
                else if (choice == 7) viewMemberList();
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
    void addBook() {
        Clrscr.main(null);
        System.out.println("Add new book");
        System.out.printf("Enter book name: ");
        scan.nextLine();
        String name = scan.nextLine();
        System.out.printf("Enter Author name: ");
        String author = scan.nextLine();
        System.out.printf("Enter ISBN number: ");
        String ISBN = scan.nextLine();
        int quantity = 1;
        while (true) {
            System.out.printf("Enter quantity: ");
            if (scan.hasNextInt()) {
                quantity = scan.nextInt();
                break;
            }
            else {
                scan.nextLine();
                System.out.println("Invalid input! Enter an integer");
            }
        }

        if (database.addBook(name, author, quantity, ISBN)) {
            System.out.println("Book added successfully.");

            Main.saveDatabase();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    void viewBookList() {
        Clrscr.main(null);
        System.out.println("Book List (sorted according to their name)");
        database.showBookList();
        System.out.printf("Press Enter to return to the Librarian section...");
        scan.nextLine();  scan.nextLine();
    }
    void issueBook() {
        Clrscr.main(null);
        int bookID=-1, memberID=-1;
        System.out.println("Issue a book");
        while (true){
            System.out.printf("Enter book ID: ");
            if (scan.hasNextInt()) {
                bookID = scan.nextInt();
                break;
            }
            else {
                System.out.println("Invalid Input. Enter an integer");
                scan.nextLine();
            }
        }
        while (true){
            System.out.printf("Enter member ID: ");
            if (scan.hasNextInt()) {
                memberID = scan.nextInt();
                break;
            }
            else {
                System.out.println("Invalid Input. Enter an integer");
                scan.nextLine();
            }
        }
        int res = database.issueBook(bookID, memberID, librariansID);
        if (res == 1) {
            System.out.println("Book issued successfully");
            Main.saveDatabase();
        }
        else {
            if (res == -1) System.out.println("No such Book exist!");
            else if (res == -2) System.out.println("No copy of this book is available right now");
            else if (res == -3) System.out.println("No such member exist!");
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    void viewIssuedBook(){
        Clrscr.main(null);
        System.out.println("List of issued books");
        database.showIssuedBooks();
        System.out.printf("Press Enter to return to the Librarian section...");
        scan.nextLine();  scan.nextLine();
    }
    void returnBook(){
        Clrscr.main(null);
        int bookID=-1, memberID=-1;
        System.out.println("Return a book");
        while (true){
            System.out.printf("Enter book ID: ");
            if (scan.hasNextInt()) {
                bookID = scan.nextInt();
                break;
            }
            else {
                System.out.println("Invalid Input. Enter an integer");
                scan.nextLine();
            }
        }
        while (true){
            System.out.printf("Enter member ID: ");
            if (scan.hasNextInt()) {
                memberID = scan.nextInt();
                break;
            }
            else {
                System.out.println("Invalid Input. Enter an integer");
                scan.nextLine();
            }
        }
        if (database.returnBook(bookID, memberID)) {
            System.out.println("Book Returned successfully");
            Main.saveDatabase();
        }
        else {
            System.out.println("No such Book issued before!");
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    void addMember() {
        Clrscr.main(null);
        System.out.println("Add New Member");
        System.out.printf("Enter name of the new Member: ");
        scan.nextLine();
        String name = scan.nextLine();

        Member newMember = database.addMember(name);
        if (newMember != null) {
            System.out.println("Member added Successfully.");
            System.out.println("Name: "+newMember.getName());
            System.out.println("ID: "+newMember.getID());

            Main.saveDatabase();
            System.out.printf("\nPress Enter to return to the Librarian section...");
            scan.nextLine();  //scan.nextLine();
        }
    }
    void viewMemberList() {
        Clrscr.main(null);
        System.out.println("List of Members (sorted according to their name)");
        database.showMemberList();
        System.out.printf("Press Enter to return to the Librarian section...");
        scan.nextLine();  scan.nextLine();
    }
}
