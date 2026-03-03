package net.codespell.assignments;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private boolean isAvailable;
    private String borrowedBy;

    public Book(String title, String author, String isbn, int publicationYear) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.isAvailable = true;
        this.borrowedBy = null;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    // Business methods
    public boolean borrowBook(String memberId) {
        if (isAvailable) {
            this.isAvailable = false;
            this.borrowedBy = memberId;
            return true;
        }
        return false;
    }

    public boolean returnBook() {
        if (!isAvailable) {
            this.isAvailable = true;
            this.borrowedBy = null;
            return true;
        }
        return false;
    }

    public int getBookAge() {
        return java.time.Year.now().getValue() - publicationYear;
    }

    @Override
    public String toString() {
        return String.format("Book{title='%s', author='%s', isbn='%s', year=%d, available=%s}",
                title, author, isbn, publicationYear, isAvailable ? "Yes" : "No");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}