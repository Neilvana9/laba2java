package com.library.library.controller;

import com.library.library.model.Book;
import com.library.library.service.AnalyticsService;
import com.library.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/popular")
    public ResponseEntity<List<Book>> getPopularBooks(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(bookRepository.findTopByOrderByBorrowCountDesc(limit));
    }

    @GetMapping("/highly-rated")
    public ResponseEntity<List<Book>> getHighlyRatedBooks(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(bookRepository.findTopByOrderByAverageRatingDesc(limit));
    }

    @GetMapping("/analytics")
    public ResponseEntity<Map<String, Object>> getLibraryAnalytics() {
        return ResponseEntity.ok(analyticsService.getLibraryAnalytics());
    }
}