package net.codespell.assignments;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Library Management System");
        System.out.println("=========================");
        
        // Create a library
        Library library = new Library("Central Library");
        
        // Add some books
        Book book1 = new Book("1984", "George Orwell", "123456789", 1949);
        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", "987654321", 1960);
        Book book3 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "456789123", 1925);
        
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        
        // Create members
        Member member1 = new Member("M001", "Alice Johnson", "alice@email.com");
        Member member2 = new Member("M002", "Bob Smith", "bob@email.com");
        
        library.addMember(member1);
        library.addMember(member2);
        
        // Demonstrate borrowing
        System.out.println("\nBorrowing books:");
        library.borrowBook("123456789", "M001");
        library.borrowBook("987654321", "M001");
        library.borrowBook("456789123", "M002");
        
        // Try to borrow an already borrowed book
        library.borrowBook("123456789", "M002");
        
        // Show library status
        System.out.println("\nLibrary Status:");
        library.displayStatus();
        
        // Return a book
        System.out.println("\nReturning a book:");
        library.returnBook("123456789");
        
        // Show updated status
        System.out.println("\nUpdated Library Status:");
        library.displayStatus();
    }
}