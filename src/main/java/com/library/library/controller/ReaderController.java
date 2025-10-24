package com.library.library.controller;

import com.library.library.model.Reader;
import com.library.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/readers")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @PostMapping
    public ResponseEntity<Reader> addReader(@RequestParam String name) {
        return ResponseEntity.ok(readerService.addReader(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReader(@PathVariable Long id) {
        Reader reader = readerService.getReader(id);
        return reader != null ? ResponseEntity.ok(reader) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reader> updateReader(@PathVariable Long id, @RequestParam String name) {
        Reader updated = readerService.updateReader(id, name);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable Long id) {
        readerService.deleteReader(id);
        return ResponseEntity.ok().build();
    }
}