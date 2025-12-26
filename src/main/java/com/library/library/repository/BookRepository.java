package com.library.library.repository;

import com.library.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId")
    List<Book> findByAuthorsId(Long authorId);

    @Query("SELECT b FROM Book b WHERE b.currentReader.id = :readerId")
    List<Book> findByCurrentReaderId(Long readerId);

    @Query("SELECT b FROM Book b WHERE b.borrowed = true")
    List<Book> findBorrowedBooks();

    @Query("SELECT b FROM Book b ORDER BY b.borrowCount DESC")
    List<Book> findTopByOrderByBorrowCountDesc(int limit);

    @Query("SELECT b FROM Book b ORDER BY b.averageRating DESC")
    List<Book> findTopByOrderByAverageRatingDesc(int limit);

    @Query("SELECT b FROM Book b WHERE b.borrowed = false")
    List<Book> findAllByBorrowedFalse();

    @Query("SELECT b FROM Book b WHERE b.actualReturnDate IS NOT NULL")
    List<Book> findByActualReturnDateIsNotNull();

    @Query("SELECT b FROM Book b WHERE b.actualReturnDate IS NOT NULL AND b.actualReturnDate <= b.dueDate")
    List<Book> findBooksReturnedOnTime();

    @Query("SELECT COUNT(b) FROM Book b WHERE b.actualReturnDate IS NOT NULL AND b.actualReturnDate > b.dueDate")
    long countBooksReturnedOverdue();

    @Query("SELECT COUNT(b) FROM Book b WHERE b.actualReturnDate IS NOT NULL AND b.actualReturnDate <= b.dueDate")
    long countBooksReturnedOnTime();

    @Query("SELECT COUNT(b) FROM Book b WHERE b.actualReturnDate IS NOT NULL")
    long countBooksReturned();

    @Query("SELECT COUNT(b) FROM Book b WHERE b.borrowed = true")
    long countBorrowedBooks();

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :genre, '%'))")
    List<Book> findByTitleContainingIgnoreCase(String genre);
}