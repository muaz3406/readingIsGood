package com.muaz.readingIsGood.controller;

import com.muaz.readingIsGood.entity.Book;
import com.muaz.readingIsGood.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/bookService")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(path = "/addBook")
    public ResponseEntity<Book> create(Book book) {
        try {
            return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/addBookList")
    public ResponseEntity<List> create(@RequestBody List<Book> bookList) {
        try {
            return new ResponseEntity<>(bookService.addBookList(bookList), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
