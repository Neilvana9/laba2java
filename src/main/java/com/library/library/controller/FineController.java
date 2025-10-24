package com.library.library.controller;

import com.library.library.model.Fine;
import com.library.library.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fines")
public class FineController {

    @Autowired
    private FineService fineService;

    @PostMapping
    public ResponseEntity<Fine> addFine(@RequestParam Long readerId, @RequestParam Long bookId, @RequestParam double amount) {
        Fine fine = fineService.addFine(readerId, bookId, amount);
        return fine != null ? ResponseEntity.ok(fine) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fine> getFine(@PathVariable Long id) {
        Fine fine = fineService.getFine(id);
        return fine != null ? ResponseEntity.ok(fine) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fine> updateFine(@PathVariable Long id, @RequestParam Long readerId, @RequestParam Long bookId, @RequestParam double amount) {
        Fine updated = fineService.updateFine(id, new Fine());
        if (updated != null) {
            updated.setReaderId(readerId);
            updated.setBookId(bookId);
            updated.setAmount(amount);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFine(@PathVariable Long id) {
        fineService.deleteFine(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<Fine> markFineAsPaid(@PathVariable Long id) {
        fineService.markFineAsPaid(id);
        return ResponseEntity.ok(fineService.getFine(id));
    }
}