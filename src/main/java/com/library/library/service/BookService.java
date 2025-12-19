package com.library.library.service;

import com.library.library.model.Book;
import com.library.library.model.Reader;
import com.library.library.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private Map<Long, Book> books = new HashMap<>();
    private AtomicLong idCounter = new AtomicLong(1);
    private static final double FINE_RATE_PER_DAY = 100.0;

    @Autowired
    private AuthorService authorService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private FineService fineService;

    public Book addBook(List<Long> authorIds, List<String> authorNames, String title) {
        for (Long authorId : authorIds) {
            if (authorService.getAuthor(authorId) == null) {
                return null;
            }
        }

        if (authorIds.size() != authorNames.size()) {
            return null;
        }

        Book book = new Book();
        book.setId(idCounter.getAndIncrement());
        book.setAuthorIds(authorIds);
        book.setAuthorNames(authorNames);
        book.setTitle(title);
        books.put(book.getId(), book);
        return book;
    }

    public Book getBook(Long id) {
        return books.get(id);
    }

    public Book updateBook(Long id, List<Long> authorIds, List<String> authorNames, String title) {
        Book book = books.get(id);
        if (book == null) {
            return null;
        }

        for (Long authorId : authorIds) {
            if (authorService.getAuthor(authorId) == null) {
                return null;
            }
        }

        if (authorIds.size() != authorNames.size()) {
            return null;
        }

        book.setAuthorIds(authorIds);
        book.setAuthorNames(authorNames);
        book.setTitle(title);
        return book;
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
        book.setCurrentReaderId(readerId);
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

        Reader reader = readerService.getReader(book.getCurrentReaderId());
        if (reader != null) {
            reader.getBorrowedBookIds().remove(bookId);
        }

        LocalDate today = LocalDate.now();
        if (today.isAfter(book.getDueDate())) {
            long daysOverdue = today.toEpochDay() - book.getDueDate().toEpochDay();
            double fineAmount = daysOverdue * FINE_RATE_PER_DAY;
            fineService.addFine(reader.getId(), bookId, fineAmount);
        }

        book.setBorrowed(false);
        book.setCurrentReaderId(null);
        book.setBorrowDate(null);
        book.setDueDate(null);


        return book;
    }

    public Map<Long, Book> getAllBooks() {
        return new HashMap<>(books);
    }
}