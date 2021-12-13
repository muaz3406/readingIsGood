package com.muaz.readingIsGood.service;

import com.muaz.readingIsGood.entity.Book;
import com.muaz.readingIsGood.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void shouldAddBook() {
        Book book = new Book();
        bookService.addBook(book);
        verify(bookRepository).save(book);
    }

    @Test
    public void shouldAddBookList() {
        Book book = new Book();
        List<Book> bookList = Collections.singletonList(book);
        bookService.addBookList(bookList);

        verify(bookRepository).saveAll(bookList);
    }
}
