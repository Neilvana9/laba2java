package com.library.library.controller;

import com.library.library.dto.CreateFineRequest;
import com.library.library.dto.UpdateFineRequest;
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
    public ResponseEntity<Fine> addFine(@RequestBody CreateFineRequest request) {
        Fine fine = fineService.addFine(request.getReaderId(), request.getBookId(), request.getAmount());
        return fine != null ? ResponseEntity.ok(fine) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fine> getFine(@PathVariable Long id) {
        Fine fine = fineService.getFine(id);
        return fine != null ? ResponseEntity.ok(fine) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<java.util.Collection<Fine>> getAllFines() {
        return ResponseEntity.ok(fineService.getAllFines().values());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fine> updateFine(@PathVariable Long id, @RequestBody UpdateFineRequest request) {
        Fine updated = fineService.updateFine(id, request.getReaderId(), request.getBookId(), request.getAmount());
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFine(@PathVariable Long id) {
        fineService.deleteFine(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<Fine> markFineAsPaid(@PathVariable Long id) {
        Fine fine = fineService.markFineAsPaid(id);
        return fine != null ? ResponseEntity.ok(fine) : ResponseEntity.notFound().build();
    }
}