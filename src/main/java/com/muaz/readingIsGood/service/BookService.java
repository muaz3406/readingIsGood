package com.muaz.readingIsGood.service;

import com.muaz.readingIsGood.entity.Book;
import com.muaz.readingIsGood.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> addBookList(List<Book> bookList) {
        return (List<Book>) bookRepository.saveAll(bookList);
    }

}
