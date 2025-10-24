package com.library.library.controller;

import com.library.library.dto.BorrowRequest;
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
    public ResponseEntity<Book> addBook(@RequestParam Long authorId, @RequestParam String authorName, @RequestParam String title) {
        Book book = bookService.addBook(authorId, authorName, title);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestParam Long authorId, @RequestParam String authorName, @RequestParam String title) {
        Book updated = bookService.updateBook(id, authorId, authorName, title);
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