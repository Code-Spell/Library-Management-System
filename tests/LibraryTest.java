import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class LibraryTest {
    private Library library;
    private Book book1;
    private Book book2;
    private Member member1;
    private Member member2;
    
    @BeforeEach
    public void setUp() {
        library = new Library("Test Library");
        book1 = new Book("Book One", "Author One", "ISBN001", 2020);
        book2 = new Book("Book Two", "Author Two", "ISBN002", 2021);
        member1 = new Member("M001", "Alice", "alice@example.com");
        member2 = new Member("M002", "Bob", "bob@example.com");
    }
    
    @Test
    public void testLibraryCreation() {
        assertEquals("Test Library", library.getName());
        assertEquals(0, library.getTotalBooks());
        assertEquals(0, library.getTotalMembers());
        assertEquals(0, library.getAvailableBookCount());
    }
    
    @Test
    public void testAddBook() {
        assertTrue(library.addBook(book1));
        assertEquals(1, library.getTotalBooks());
        assertEquals(1, library.getAvailableBookCount());
        
        // Cannot add null book
        assertFalse(library.addBook(null));
        assertEquals(1, library.getTotalBooks());
        
        // Cannot add duplicate ISBN
        Book duplicateIsbn = new Book("Different Title", "Different Author", "ISBN001", 2022);
        assertFalse(library.addBook(duplicateIsbn));
        assertEquals(1, library.getTotalBooks());
    }
    
    @Test
    public void testRemoveBook() {
        library.addBook(book1);
        assertEquals(1, library.getTotalBooks());
        
        assertTrue(library.removeBook("ISBN001"));
        assertEquals(0, library.getTotalBooks());
        
        // Cannot remove non-existent book
        assertFalse(library.removeBook("ISBN999"));
    }
    
    @Test
    public void testFindBookByIsbn() {
        library.addBook(book1);
        
        Book found = library.findBookByIsbn("ISBN001");
        assertEquals(book1, found);
        
        Book notFound = library.findBookByIsbn("ISBN999");
        assertNull(notFound);
    }
    
    @Test
    public void testFindBooksByAuthor() {
        library.addBook(book1);
        library.addBook(book2);
        Book book3 = new Book("Another Book", "Author One", "ISBN003", 2019);
        library.addBook(book3);
        
        List<Book> authorOneBooks = library.findBooksByAuthor("Author One");
        assertEquals(2, authorOneBooks.size());
        assertTrue(authorOneBooks.contains(book1));
        assertTrue(authorOneBooks.contains(book3));
        
        List<Book> notFound = library.findBooksByAuthor("Unknown Author");
        assertEquals(0, notFound.size());
    }
    
    @Test
    public void testAddMember() {
        assertTrue(library.addMember(member1));
        assertEquals(1, library.getTotalMembers());
        
        // Cannot add null member
        assertFalse(library.addMember(null));
        assertEquals(1, library.getTotalMembers());
        
        // Cannot add duplicate member ID
        Member duplicateId = new Member("M001", "Different Name", "different@email.com");
        assertFalse(library.addMember(duplicateId));
        assertEquals(1, library.getTotalMembers());
    }
    
    @Test
    public void testRemoveMember() {
        library.addMember(member1);
        assertEquals(1, library.getTotalMembers());
        
        // Can remove member with no borrowed books
        assertTrue(library.removeMember("M001"));
        assertEquals(0, library.getTotalMembers());
        
        // Cannot remove member with borrowed books
        library.addMember(member1);
        library.addBook(book1);
        library.borrowBook("ISBN001", "M001");
        
        assertFalse(library.removeMember("M001"));
        assertEquals(1, library.getTotalMembers());
    }
    
    @Test
    public void testFindMemberById() {
        library.addMember(member1);
        
        Member found = library.findMemberById("M001");
        assertEquals(member1, found);
        
        Member notFound = library.findMemberById("M999");
        assertNull(notFound);
    }
    
    @Test
    public void testBorrowBook() {
        library.addBook(book1);
        library.addMember(member1);
        
        // Successful borrowing
        assertTrue(library.borrowBook("ISBN001", "M001"));
        assertFalse(book1.isAvailable());
        assertEquals("M001", book1.getBorrowedBy());
        assertTrue(member1.hasBorrowedBook("ISBN001"));
        
        // Cannot borrow already borrowed book
        assertFalse(library.borrowBook("ISBN001", "M002"));
        
        // Cannot borrow non-existent book
        assertFalse(library.borrowBook("ISBN999", "M001"));
        
        // Cannot borrow for non-existent member
        assertFalse(library.borrowBook("ISBN002", "M999"));
    }
    
    @Test
    public void testBorrowBookWithLimit() {
        library.addMember(member1);
        member1.setMaxBooksAllowed(1);
        
        // Add two books
        library.addBook(book1);
        library.addBook(book2);
        
        // Borrow first book successfully
        assertTrue(library.borrowBook("ISBN001", "M001"));
        
        // Cannot borrow second book due to limit
        assertFalse(library.borrowBook("ISBN002", "M001"));
    }
    
    @Test
    public void testReturnBook() {
        library.addBook(book1);
        library.addMember(member1);
        library.borrowBook("ISBN001", "M001");
        
        // Successful return
        assertTrue(library.returnBook("ISBN001"));
        assertTrue(book1.isAvailable());
        assertNull(book1.getBorrowedBy());
        assertFalse(member1.hasBorrowedBook("ISBN001"));
        
        // Cannot return non-existent book
        assertFalse(library.returnBook("ISBN999"));
        
        // Cannot return already available book
        assertFalse(library.returnBook("ISBN001"));
    }
    
    @Test
    public void testGetAvailableBookCount() {
        library.addBook(book1);
        library.addBook(book2);
        assertEquals(2, library.getAvailableBookCount());
        
        library.addMember(member1);
        library.borrowBook("ISBN001", "M001");
        assertEquals(1, library.getAvailableBookCount());
        
        library.returnBook("ISBN001");
        assertEquals(2, library.getAvailableBookCount());
    }
    
    @Test
    public void testTransactionHistory() {
        List<String> history = library.getTransactionHistory();
        assertEquals(0, history.size());
        
        library.addBook(book1);
        library.addMember(member1);
        library.borrowBook("ISBN001", "M001");
        library.returnBook("ISBN001");
        
        history = library.getTransactionHistory();
        assertEquals(4, history.size());
        assertTrue(history.get(0).contains("Added book"));
        assertTrue(history.get(1).contains("Added member"));
        assertTrue(history.get(2).contains("borrowed by"));
        assertTrue(history.get(3).contains("returned"));
        
        // Ensure it returns a copy (encapsulation)
        history.clear();
        assertEquals(4, library.getTransactionHistory().size());
    }
}