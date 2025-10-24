package com.library.library.service;

import com.library.library.model.Book;
import com.library.library.model.Reader;
import com.library.library.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {

    private Map<Long, Book> books = new HashMap<>();
    private AtomicLong idCounter = new AtomicLong(1);

    @Autowired
    private AuthorService authorService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private FineService fineService;

    public Book addBook(Long authorId, String authorName, String title) {
        Author author = authorService.getAuthor(authorId);
        if (author == null) {
            return null;
        }
        Book book = new Book();
        book.setId(idCounter.getAndIncrement());
        book.setAuthorId(authorId);
        book.setAuthorName(authorName);
        book.setTitle(title);
        books.put(book.getId(), book);
        return book;
    }

    public Book getBook(Long id) {
        return books.get(id);
    }

    public Book updateBook(Long id, Long authorId, String authorName, String title) {
        Book book = books.get(id);
        if (book != null && authorService.getAuthor(authorId) != null) {
            book.setAuthorId(authorId);
            book.setAuthorName(authorName);
            book.setTitle(title);
            return book;
        }
        return null;
    }

    public void deleteBook(Long id) {
        books.remove(id);
    }

    public Book borrowBook(Long bookId, Long readerId) {
        Book book = books.get(bookId);
        Reader reader = readerService.getReader(readerId);

        if (book == null || reader == null || book.isBorrowed()) {
            return null;
        }

        book.setBorrowed(true);
        book.setCurrentReader(reader);
        book.setBorrowDate(LocalDate.now());
        book.setDueDate(LocalDate.now().plusDays(30));
        reader.getBorrowedBookIds().add(bookId);

        return book;
    }

    public Book returnBook(Long bookId) {
        Book book = books.get(bookId);
        if (book == null || !book.isBorrowed()) {
            return null;
        }

        Reader reader = book.getCurrentReader();
        if (reader != null) {
            reader.getBorrowedBookIds().remove(bookId);
        }

        LocalDate today = LocalDate.now();
        if (today.isAfter(book.getDueDate())) {
            long daysOverdue = today.toEpochDay() - book.getDueDate().toEpochDay();
            double fineAmount = daysOverdue * 100;
            fineService.addFine(reader.getId(), bookId, fineAmount);
        }

        book.setBorrowed(false);
        book.setCurrentReader(null);
        book.setBorrowDate(null);
        book.setDueDate(null);

        return book;
    }
}