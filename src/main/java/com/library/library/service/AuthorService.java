package com.library.library.service;

import com.library.library.model.Author;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AuthorService {
    private Map<Long, Author> authors = new HashMap<>();
    private AtomicLong idCounter = new AtomicLong(1);

    public Author addAuthor(String name) {
        Author author = new Author();
        author.setId(idCounter.getAndIncrement());
        author.setName(name);
        authors.put(author.getId(), author);
        return author;
    }

    public Author getAuthor(Long id) {
        return authors.get(id);
    }

    public Author updateAuthor(Long id, String name) {
        Author author = authors.get(id);
        if (author != null) {
            author.setName(name);
            return author;
        }
        return null;
    }

    public Map<Long, Author> getAuthors() {
        return new HashMap<>(authors);
    }

    public void deleteAuthor(Long id) {
        authors.remove(id);
    }
}
