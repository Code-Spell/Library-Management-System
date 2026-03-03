package net.codespell.assignments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private String name;
    private Map<String, Book> books; // ISBN -> Book
    private Map<String, Member> members; // MemberId -> Member
    private List<String> transactionHistory;

    public Library(String name) {
        this.name = name;
        this.books = new HashMap<>();
        this.members = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getTotalBooks() {
        return books.size();
    }

    public int getTotalMembers() {
        return members.size();
    }

    // Book management
    public boolean addBook(Book book) {
        if (book == null || books.containsKey(book.getIsbn())) {
            return false;
        }
        books.put(book.getIsbn(), book);
        transactionHistory.add("Added book: " + book.getTitle());
        return true;
    }

    public boolean removeBook(String isbn) {
        Book book = books.remove(isbn);
        if (book != null) {
            transactionHistory.add("Removed book: " + book.getTitle());
            return true;
        }
        return false;
    }

    public Book findBookByIsbn(String isbn) {
        return books.get(isbn);
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    // Member management
    public boolean addMember(Member member) {
        if (member == null || members.containsKey(member.getMemberId())) {
            return false;
        }
        members.put(member.getMemberId(), member);
        transactionHistory.add("Added member: " + member.getName());
        return true;
    }

    public boolean removeMember(String memberId) {
        Member member = members.get(memberId);
        if (member != null && member.getBorrowedBooksCount() == 0) {
            members.remove(memberId);
            transactionHistory.add("Removed member: " + member.getName());
            return true;
        }
        return false;
    }

    public Member findMemberById(String memberId) {
        return members.get(memberId);
    }

    // Borrowing and returning
    public boolean borrowBook(String isbn, String memberId) {
        Book book = books.get(isbn);
        Member member = members.get(memberId);

        if (book == null) {
            System.out.println("Book not found!");
            return false;
        }

        if (member == null) {
            System.out.println("Member not found!");
            return false;
        }

        if (!book.isAvailable()) {
            System.out.println("Book is already borrowed!");
            return false;
        }

        if (!member.canBorrowMoreBooks()) {
            System.out.println("Member has reached borrowing limit!");
            return false;
        }

        if (book.borrowBook(memberId) && member.borrowBook(isbn)) {
            String transaction = String.format("Book '%s' borrowed by %s", book.getTitle(), member.getName());
            transactionHistory.add(transaction);
            System.out.println(transaction);
            return true;
        }

        return false;
    }

    public boolean returnBook(String isbn) {
        Book book = books.get(isbn);
        if (book == null || book.isAvailable()) {
            System.out.println("Book not found or not borrowed!");
            return false;
        }

        String memberId = book.getBorrowedBy();
        Member member = members.get(memberId);

        if (book.returnBook() && (member == null || member.returnBook(isbn))) {
            String transaction = String.format("Book '%s' returned", book.getTitle());
            transactionHistory.add(transaction);
            System.out.println(transaction);
            return true;
        }

        return false;
    }

    // Statistics and display
    public int getAvailableBookCount() {
        int count = 0;
        for (Book book : books.values()) {
            if (book.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    public void displayStatus() {
        System.out.println("Library: " + name);
        System.out.println("Total books: " + books.size());
        System.out.println("Available books: " + getAvailableBookCount());
        System.out.println("Total members: " + members.size());
        
        System.out.println("\nBooks:");
        for (Book book : books.values()) {
            System.out.println("  " + book);
        }
        
        System.out.println("\nMembers:");
        for (Member member : members.values()) {
            System.out.println("  " + member);
        }
    }

    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
}