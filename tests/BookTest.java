import net.codespell.assignments.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    private Book book;
    
    @BeforeEach
    public void setUp() {
        book = new Book("Test Title", "Test Author", "1234567890", 2020);
    }
    
    @Test
    public void testBookCreation() {
        assertEquals("Test Title", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals("1234567890", book.getIsbn());
        assertEquals(2020, book.getPublicationYear());
        assertTrue(book.isAvailable());
        assertNull(book.getBorrowedBy());
    }
    
    @Test
    public void testBorrowBook() {
        assertTrue(book.borrowBook("M001"));
        assertFalse(book.isAvailable());
        assertEquals("M001", book.getBorrowedBy());
        
        // Cannot borrow again when already borrowed
        assertFalse(book.borrowBook("M002"));
        assertEquals("M001", book.getBorrowedBy());
    }
    
    @Test
    public void testReturnBook() {
        // First borrow the book
        book.borrowBook("M001");
        assertFalse(book.isAvailable());
        
        // Then return it
        assertTrue(book.returnBook());
        assertTrue(book.isAvailable());
        assertNull(book.getBorrowedBy());
        
        // Cannot return when not borrowed
        assertFalse(book.returnBook());
    }
    
    @Test
    public void testGetBookAge() {
        int currentYear = java.time.Year.now().getValue();
        int expectedAge = currentYear - 2020;
        assertEquals(expectedAge, book.getBookAge());
    }
    
    @Test
    public void testSetters() {
        book.setTitle("New Title");
        book.setAuthor("New Author");
        book.setPublicationYear(2021);
        
        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
        assertEquals(2021, book.getPublicationYear());
    }
    
    @Test
    public void testEquals() {
        Book sameBook = new Book("Different Title", "Different Author", "1234567890", 2019);
        Book differentBook = new Book("Test Title", "Test Author", "0987654321", 2020);
        
        assertEquals(book, sameBook); // Same ISBN
        assertNotEquals(book, differentBook); // Different ISBN
        assertNotEquals(book, null);
        assertNotEquals(book, "Not a book");
    }
    
    @Test
    public void testHashCode() {
        Book sameBook = new Book("Different Title", "Different Author", "1234567890", 2019);
        assertEquals(book.hashCode(), sameBook.hashCode());
    }
    
    @Test
    public void testToString() {
        String toString = book.toString();
        assertTrue(toString.contains("Test Title"));
        assertTrue(toString.contains("Test Author"));
        assertTrue(toString.contains("1234567890"));
        assertTrue(toString.contains("2020"));
        assertTrue(toString.contains("Yes")); // Available
    }
}