package com.jobaidul;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

class Password implements Serializable {
    String password;
    Password() {
        password = hash("123");
    }
    Password(String password) {
        this.password = hash(password);
    }
    void  setPassword(String password) {
        this.password = hash(password);
    }
    String hash(String s) {
        //
        return s;
    }
    boolean equals(Password pass) {
        return  password.equals(pass.password);
    }
}
class Admin implements Serializable {
    Password password;
    Admin() {
        password = new Password("123");
    }
    Admin(String password){
        this.password = new Password(password);
    }
    Password getPassword() { return password; }
    void setPassword(String password) { this.password = new Password(password); }
}
class Librarian implements Serializable {
    String name; Password password; int ID; // primary key
    Librarian() {
        ID = -1; name = null; password = new Password("123");
    }
    Librarian(int ID, String name, String password) {
        this.ID = ID; this.name = name; this.password = new Password(password);
    }
    String getName() { return  name; }
    int getID() { return  ID; }
    Password getPassword() { return  password; }

    void setName(String name) { this.name = name; }
    void setID (int ID) { this.ID = ID;};
    void setPassword (String password) { this.password = new Password(password); }
}
class Member implements Serializable {
    String name; int ID; // primary key
    Member() {
        ID = -1; name = null;
    }
    Member(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }
    int getID() { return  ID; }
    String getName() { return  name; }

    void setID(int ID) { this.ID = ID; }
    void setName(String name) { this.name = name; }
}
class Book implements Serializable {
    int ID, quantity; String ISBN, name, author;
    Book() {
        ID = -1; name = null; author = null; quantity = 0; ISBN = null;
    }
    Book(int ID, String name, String author, int quantity, String ISBN) {
        this.ID = ID;
        this.name = name;
        this.author = author;
        this.quantity = quantity;
        this.ISBN = ISBN;
    }
    int getID() { return  ID; }
    String getName() { return  name; }
    String getAuthor() { return  author; }
    int getQuantity() { return quantity; }
    String getISBN(){ return  ISBN; }

    void setID(int ID) { this.ID = ID; }
    void setName(String name) { this.name = name; }
    void setAuthor(String author) { this.author = author; }
    void setQuantity(int quantity) { this.quantity = quantity; }
    void  setISBN(String ISBN) { this.ISBN = ISBN; }
}
class Card implements Serializable {
    Date date; Member member; Book book; Librarian librarian;
    Card(Book book, Member member, Librarian librarian) {
        date = new Date();
        this.member = member;
        this.book = book;
        this.librarian = librarian;
    }
    Card() {
        date = new Date();
    }
    Date getDate() { return  date; }
    Book getBook() { return  book; }
    Member getMember() { return member; }
    Librarian getLibrarian() { return librarian; }

    //void setDate(Date date) { this.date = date; }
    void setBook(Book book) { this.book = book; }
    void setMember(Member member) { this.member = member; }
    void setLibrarian(Librarian librarian) { this.librarian = librarian; }
}
class IDs implements Serializable {
    int id;
    IDs() {
        id = 1;
    }
    int getNewID() {
        return  id++;
    }
}
public class Database implements Serializable {
    Admin admin;
    ArrayList<Librarian> librarians;
    ArrayList<Book> bookList;
    ArrayList<Member> memberList;
    ArrayList<Card> issueCard, returnCard;
    IDs lib_ids, mem_ids, book_ids;
    Database() {
        admin = new Admin();
        librarians = new ArrayList<Librarian>();
        bookList = new ArrayList<Book>();
        memberList = new ArrayList<Member>();
        issueCard = new ArrayList<Card>();
        returnCard = new ArrayList<Card>();
        lib_ids = new IDs();
        mem_ids = new IDs();
        book_ids = new IDs();
    }
    int issueBook(int bookID, int memberID, int librariansID) {
        int i;
        for (i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getID() == bookID) {
                break;
            }
        }
        if (i >= bookList.size()) return -1;
        if (bookList.get(i).getQuantity() <= 0) return -2;
        int j;
        for (j = 0; j < memberList.size(); j++) {
            if (memberList.get(j).getID() == memberID) {
                break;
            }
        }
        if (j >= memberList.size()) return -3;

        int k;
        for (k = 0; k < librarians.size(); k++) {
            if (librarians.get(k).getID() == librariansID) {
                break;
            }
        }
        Book book = new Book(bookList.get(i).getID(), new String(bookList.get(i).getName()),
                new String(bookList.get(i).getAuthor()),
                bookList.get(i).getQuantity(), new String(bookList.get(i).getISBN()));
        Member member = new Member(memberList.get(j).getID(), new String(memberList.get(j).getName()));
        Librarian librarian = new Librarian(librarians.get(k).getID(), new String(librarians.get(k).getName()),
                new String("123"));

        Card card = new Card(book, member, librarian);
        addIssueCard(card);
        bookList.get(i).setQuantity(bookList.get(i).getQuantity()-1);
        return 1;
    }
    boolean addIssueCard(Card card) {
        issueCard.add(card);
        return true;
    }
    boolean returnBook(int bookID, int memberID) {
        int i;
        for (i = 0; i < issueCard.size(); i++) {
            if (issueCard.get(i).getBook().getID() == bookID && issueCard.get(i).getMember().getID() == memberID) {
                break;
            }
        }
        if (i >= issueCard.size()) return false;
        issueCard.remove(i);
        for (i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getID() == bookID) {
                bookList.get(i).setQuantity(bookList.get(i).getQuantity()+1);
                break;
            }
        }
        return true;
    }
    boolean isLibrarian(Librarian librarian) {
        for (Librarian lib:
             librarians) {
            if (lib.getID() == librarian.getID() && lib.getPassword().equals(librarian.getPassword())) return true;
        }
        return false;
    }
    boolean isMember(int ID) {
        for (Member mem:
             memberList) {
            if (mem.getID() == ID) return true;
        }
        return false;
    }
    boolean isAdmin(Admin admin) {
        return this.admin.getPassword().equals(admin.getPassword());
    }
    boolean changeAdmin(String password) {
        admin.setPassword(password);
        return true;
    }
    Librarian addLibrarian(String name, String password) {
        int i;
        for (i = 0; i < librarians.size(); i++) {
            if (librarians.get(i).getName().compareTo(name) > 0) {
                break;
            }
        }
        librarians.add(i, new Librarian(lib_ids.getNewID() , name, password));
        return librarians.get(i);
    }
    Librarian deleteLibrarian(int ID) {
        for (int i = 0; i < librarians.size(); i++) {
            if (librarians.get(i).getID() == ID) {
                return librarians.remove(i);
            }
        }
        return null;
    }
    Member addMember(String name) {
        int i;
        for (i = 0; i < memberList.size(); i++) {
            if (memberList.get(i).getName().compareTo(name) > 0) {
                break;
            }
        }
        memberList.add(i, new Member(mem_ids.getNewID() , name));
        return memberList.get(i);
    }
    boolean addBook(String name, String author, int quantity, String ISBN) {
        int i;
        for (i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getName().compareTo(name) > 0) {
                break;
            }
        }
        bookList.add(i, new Book(book_ids.getNewID() , name, author, quantity, ISBN));
        return true;
    }

    void printNchar(char c, int n) {
        while (n-- > 0) System.out.printf("%c", c);
    }
    void fixedLenthPrint(String s, int n) {
        for (int i = 0; i < (n-s.length())/2; i++) System.out.printf("%c", ' ');
        System.out.printf("%s", s.substring(0, Math.min(s.length(), n)));
        for (int i = 0; i < (n-s.length()+1)/2; i++) System.out.printf("%c", ' ');
    }
    void showLibrariansData() {
        printNchar('_', 100); System.out.println("");

        System.out.printf("|"); fixedLenthPrint("ID", 10);
        System.out.printf("|"); fixedLenthPrint("Name", 87); System.out.println("|");
        System.out.printf("|"); printNchar('_', 10);
        System.out.printf("|"); printNchar('_', 87); System.out.println("|");

        for (Librarian librarian:
             librarians) {
            System.out.printf("|"); fixedLenthPrint(Integer.toString(librarian.getID()), 10);
            System.out.printf("|"); fixedLenthPrint(librarian.getName(), 87); System.out.println("|");
            System.out.printf("|"); printNchar('_', 10);
            System.out.printf("|"); printNchar('_', 87); System.out.println("|");
        }
    }
    void showMemberList() {
        printNchar('_', 100); System.out.println("");

        System.out.printf("|"); fixedLenthPrint("ID", 10);
        System.out.printf("|"); fixedLenthPrint("Name", 87); System.out.println("|");
        System.out.printf("|"); printNchar('_', 10);
        System.out.printf("|"); printNchar('_', 87); System.out.println("|");

        for (Member member:
             memberList) {
            System.out.printf("|"); fixedLenthPrint(Integer.toString(member.getID()), 10);
            System.out.printf("|"); fixedLenthPrint(member.getName(), 87); System.out.println("|");
            System.out.printf("|"); printNchar('_', 10);
            System.out.printf("|"); printNchar('_', 87); System.out.println("|");
        }
    }
    void showBookList() {
        printNchar('_', 155); System.out.println("");

        System.out.printf("|"); fixedLenthPrint("ID", 5);
        System.out.printf("|"); fixedLenthPrint("Name", 80);
        System.out.printf("|"); fixedLenthPrint("Author", 39);
        System.out.printf("|"); fixedLenthPrint("Quantity", 8);
        System.out.printf("|"); fixedLenthPrint("ISBN", 17); System.out.println("|");
        System.out.printf("|"); printNchar('_', 5);
        System.out.printf("|"); printNchar('_', 80);
        System.out.printf("|"); printNchar('_', 39);
        System.out.printf("|"); printNchar('_', 8);
        System.out.printf("|"); printNchar('_', 17); System.out.println("|");

        for (Book book:
             bookList) {
            System.out.printf("|"); fixedLenthPrint(Integer.toString(book.getID()), 5);
            System.out.printf("|"); fixedLenthPrint(book.getName(), 80);
            System.out.printf("|"); fixedLenthPrint(book.getAuthor(), 39);
            System.out.printf("|"); fixedLenthPrint(Integer.toString(book.getQuantity()), 8);
            System.out.printf("|"); fixedLenthPrint(book.getISBN(), 17); System.out.println("|");
            System.out.printf("|"); printNchar('_', 5);
            System.out.printf("|"); printNchar('_', 80);
            System.out.printf("|"); printNchar('_', 39);
            System.out.printf("|"); printNchar('_', 8);
            System.out.printf("|"); printNchar('_', 17); System.out.println("|");
        }
    }
    void showIssuedBooks() {
        printNchar('_', 146); System.out.println("");

        System.out.printf("|"); fixedLenthPrint("Member ID", 9);
        System.out.printf("|"); fixedLenthPrint("Book ID", 9);
        System.out.printf("|"); fixedLenthPrint("Book Name", 80);
        System.out.printf("|"); fixedLenthPrint("Date", 43); System.out.println("|");
        System.out.printf("|"); printNchar('_', 9);
        System.out.printf("|"); printNchar('_', 9);
        System.out.printf("|"); printNchar('_', 80);
        System.out.printf("|"); printNchar('_', 43); System.out.println("|");

        for (Card card:
             issueCard) {
            System.out.printf("|"); fixedLenthPrint(Integer.toString(card.getMember().getID()), 9);
            System.out.printf("|"); fixedLenthPrint(Integer.toString(card.getBook().getID()), 9);
            System.out.printf("|"); fixedLenthPrint(card.getBook().getName(), 80);
            System.out.printf("|"); fixedLenthPrint(card.getDate().toString(), 43); System.out.println("|");
            System.out.printf("|"); printNchar('_', 9);
            System.out.printf("|"); printNchar('_', 9);
            System.out.printf("|"); printNchar('_', 80);
            System.out.printf("|"); printNchar('_', 43); System.out.println("|");
        }
    }
}
