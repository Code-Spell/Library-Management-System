package net.codespell.assignments;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private String memberId;
    private String name;
    private String email;
    private List<String> borrowedBooks;
    private int maxBooksAllowed;

    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
        this.maxBooksAllowed = 3; // Default limit
    }

    // Getters
    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getBorrowedBooks() {
        return new ArrayList<>(borrowedBooks); // Return copy to maintain encapsulation
    }

    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMaxBooksAllowed(int maxBooksAllowed) {
        this.maxBooksAllowed = maxBooksAllowed;
    }

    // Business methods
    public boolean canBorrowMoreBooks() {
        return borrowedBooks.size() < maxBooksAllowed;
    }

    public boolean borrowBook(String isbn) {
        if (canBorrowMoreBooks() && !borrowedBooks.contains(isbn)) {
            borrowedBooks.add(isbn);
            return true;
        }
        return false;
    }

    public boolean returnBook(String isbn) {
        return borrowedBooks.remove(isbn);
    }

    public int getBorrowedBooksCount() {
        return borrowedBooks.size();
    }

    public boolean hasBorrowedBook(String isbn) {
        return borrowedBooks.contains(isbn);
    }

    @Override
    public String toString() {
        return String.format("Member{id='%s', name='%s', email='%s', borrowed=%d/%d}",
                memberId, name, email, borrowedBooks.size(), maxBooksAllowed);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Member member = (Member) obj;
        return memberId.equals(member.memberId);
    }

    @Override
    public int hashCode() {
        return memberId.hashCode();
    }
}