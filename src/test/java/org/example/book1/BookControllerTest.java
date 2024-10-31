package org.example.book1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll(); // Clean the database before each test
    }

    @Test
    void testListBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookList"));
    }

    @Test
    void testAddBook() throws Exception {
        mockMvc.perform(post("/books/add")
                        .param("title", "New Book")
                        .param("author", "New Author")
                        .param("price", "29.99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"));
    }

    @Test
    void testUpdateBook() throws Exception {
        Book book = new Book();
        book.setTitle("Existing Book");
        book.setAuthor("Existing Author");
        book.setPrice(15.99);
        bookRepository.save(book);

        mockMvc.perform(post("/books/edit/{id}", book.getId())
                        .param("title", "Updated Book")
                        .param("author", "Updated Author")
                        .param("price", "19.99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"));
    }

    @Test
    void testDeleteBook() throws Exception {
        Book book = new Book();
        book.setTitle("Book to Delete");
        book.setAuthor("Author");
        book.setPrice(9.99);
        bookRepository.save(book);

        mockMvc.perform(get("/books/delete/{id}", book.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"));
    }
}