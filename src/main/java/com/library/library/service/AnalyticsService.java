package com.library.library.service;

import com.library.library.model.Book;
import com.library.library.model.Reader;
import com.library.library.repository.BookRepository;
import com.library.library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReaderRepository readerRepository;


    public double getAverageReadingDuration() {
        List<Book> returnedBooks = bookRepository.findByActualReturnDateIsNotNull();
        if (returnedBooks.isEmpty()) {
            return 0.0;
        }

        long totalReadingDays = 0;
        int validBooks = 0;

        for (Book book : returnedBooks) {
            if (book.getBorrowDate() != null && book.getActualReturnDate() != null) {
                long days = ChronoUnit.DAYS.between(book.getBorrowDate(), book.getActualReturnDate());
                totalReadingDays += days;
                validBooks++;
            }
        }

        return validBooks > 0 ? (double) totalReadingDays / validBooks : 0.0;
    }

    public double getOnTimeReturnPercentage() {
        long onTimeReturns = bookRepository.countBooksReturnedOnTime();
        long totalReturns = bookRepository.countBooksReturned();

        return totalReturns > 0 ? (double) onTimeReturns / totalReturns * 100 : 0.0;
    }

    public List<Reader> getTopReaders(int limit) {
        return readerRepository.findAll().stream()
                .sorted((r1, r2) -> Integer.compare(
                        r2.getBorrowedBooks() != null ? r2.getBorrowedBooks().size() : 0,
                        r1.getBorrowedBooks() != null ? r1.getBorrowedBooks().size() : 0
                ))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Map<String, Object> getLibraryAnalytics() {
        try {
            Map<String, Object> analytics = new HashMap<>();
            analytics.put("averageReadingDuration", getAverageReadingDuration());
            analytics.put("onTimeReturnPercentage", getOnTimeReturnPercentage());
            analytics.put("topReaders", getTopReaders(5));
            analytics.put("totalBooks", bookRepository.count());
            analytics.put("totalReaders", readerRepository.count());
            analytics.put("totalBorrowedBooks", bookRepository.countBorrowedBooks());
            analytics.put("totalReturnedBooks", bookRepository.countBooksReturned());
            analytics.put("totalOnTimeReturns", bookRepository.countBooksReturnedOnTime());
            analytics.put("totalOverdueReturns", bookRepository.countBooksReturnedOverdue());
            return analytics;
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to calculate analytics: " + e.getMessage());
            return errorResponse;
        }
    }

    public List<Book> getMostBorrowedBooks(int limit) {
        return bookRepository.findTopByOrderByBorrowCountDesc(limit);
    }
}