package com.library.library.controller;

import com.library.library.dto.*;
import com.library.library.model.Book;
import com.library.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody CreateBookRequest request) {
        Book book = bookService.addBook(request.getAuthorIds(), request.getAuthorNames(), request.getTitle());
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<java.util.Collection<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks().values());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody UpdateBookRequest request) {
        Book updated = bookService.updateBook(id, request.getAuthorIds(), request.getAuthorNames(), request.getTitle());
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/borrow")
    public ResponseEntity<Book> borrowBook(@RequestBody BorrowRequest request) {
        Book book = bookService.borrowBook(request.getBookId(), request.getReaderId());
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Book> returnBook(@PathVariable Long id) {
        Book book = bookService.returnBook(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.badRequest().build();
    }
}