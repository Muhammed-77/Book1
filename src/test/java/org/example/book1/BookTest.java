package org.example.book1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookTest {

    @Test
    void testGettersAndSetters() {
        Book book = new Book();

        // Test title
        book.setTitle("Test Book");
        assertEquals("Test Book", book.getTitle());

        // Test author
        book.setAuthor("Test Author");
        assertEquals("Test Author", book.getAuthor());

        // Test price
        book.setPrice(19.99);
        assertEquals(19.99, book.getPrice());

        // Test ID
        book.setId(1L);
        assertEquals(1L, book.getId());
    }
}