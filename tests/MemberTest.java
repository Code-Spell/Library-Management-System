import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class MemberTest {
    private Member member;
    
    @BeforeEach
    public void setUp() {
        member = new Member("M001", "John Doe", "john@example.com");
    }
    
    @Test
    public void testMemberCreation() {
        assertEquals("M001", member.getMemberId());
        assertEquals("John Doe", member.getName());
        assertEquals("john@example.com", member.getEmail());
        assertEquals(0, member.getBorrowedBooksCount());
        assertEquals(3, member.getMaxBooksAllowed()); // Default limit
        assertTrue(member.canBorrowMoreBooks());
    }
    
    @Test
    public void testBorrowBook() {
        assertTrue(member.borrowBook("ISBN001"));
        assertEquals(1, member.getBorrowedBooksCount());
        assertTrue(member.hasBorrowedBook("ISBN001"));
        
        // Borrow another book
        assertTrue(member.borrowBook("ISBN002"));
        assertEquals(2, member.getBorrowedBooksCount());
        
        // Cannot borrow same book twice
        assertFalse(member.borrowBook("ISBN001"));
        assertEquals(2, member.getBorrowedBooksCount());
    }
    
    @Test
    public void testBorrowingLimit() {
        // Borrow up to the limit
        assertTrue(member.borrowBook("ISBN001"));
        assertTrue(member.borrowBook("ISBN002"));
        assertTrue(member.borrowBook("ISBN003"));
        assertEquals(3, member.getBorrowedBooksCount());
        assertFalse(member.canBorrowMoreBooks());
        
        // Cannot borrow more than limit
        assertFalse(member.borrowBook("ISBN004"));
        assertEquals(3, member.getBorrowedBooksCount());
    }
    
    @Test
    public void testReturnBook() {
        // First borrow some books
        member.borrowBook("ISBN001");
        member.borrowBook("ISBN002");
        assertEquals(2, member.getBorrowedBooksCount());
        
        // Return a book
        assertTrue(member.returnBook("ISBN001"));
        assertEquals(1, member.getBorrowedBooksCount());
        assertFalse(member.hasBorrowedBook("ISBN001"));
        assertTrue(member.hasBorrowedBook("ISBN002"));
        
        // Cannot return book not borrowed
        assertFalse(member.returnBook("ISBN003"));
        assertEquals(1, member.getBorrowedBooksCount());
    }
    
    @Test
    public void testGetBorrowedBooks() {
        member.borrowBook("ISBN001");
        member.borrowBook("ISBN002");
        
        List<String> borrowedBooks = member.getBorrowedBooks();
        assertEquals(2, borrowedBooks.size());
        assertTrue(borrowedBooks.contains("ISBN001"));
        assertTrue(borrowedBooks.contains("ISBN002"));
        
        // Ensure it returns a copy (encapsulation)
        borrowedBooks.add("ISBN003");
        assertEquals(2, member.getBorrowedBooksCount()); // Original unchanged
    }
    
    @Test
    public void testSetters() {
        member.setName("Jane Doe");
        member.setEmail("jane@example.com");
        member.setMaxBooksAllowed(5);
        
        assertEquals("Jane Doe", member.getName());
        assertEquals("jane@example.com", member.getEmail());
        assertEquals(5, member.getMaxBooksAllowed());
    }
    
    @Test
    public void testCustomBorrowingLimit() {
        member.setMaxBooksAllowed(1);
        
        assertTrue(member.borrowBook("ISBN001"));
        assertFalse(member.canBorrowMoreBooks());
        assertFalse(member.borrowBook("ISBN002"));
        assertEquals(1, member.getBorrowedBooksCount());
    }
    
    @Test
    public void testEquals() {
        Member sameMember = new Member("M001", "Different Name", "different@email.com");
        Member differentMember = new Member("M002", "John Doe", "john@example.com");
        
        assertEquals(member, sameMember); // Same member ID
        assertNotEquals(member, differentMember); // Different member ID
        assertNotEquals(member, null);
        assertNotEquals(member, "Not a member");
    }
    
    @Test
    public void testHashCode() {
        Member sameMember = new Member("M001", "Different Name", "different@email.com");
        assertEquals(member.hashCode(), sameMember.hashCode());
    }
    
    @Test
    public void testToString() {
        String toString = member.toString();
        assertTrue(toString.contains("M001"));
        assertTrue(toString.contains("John Doe"));
        assertTrue(toString.contains("john@example.com"));
        assertTrue(toString.contains("0/3")); // borrowed/max format
    }
}