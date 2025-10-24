package com.library.library.service;

import com.library.library.model.Fine;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FineService {

    private Map<Long, Fine> fines = new HashMap<>();
    private AtomicLong idCounter = new AtomicLong(1);

    public Fine addFine(Long readerId, Long bookId, double amount) {
        Fine fine = new Fine();
        fine.setId(idCounter.getAndIncrement());
        fine.setReaderId(readerId);
        fine.setBookId(bookId);
        fine.setAmount(amount);
        fines.put(fine.getId(), fine);
        return fine;
    }

    public Fine getFine(Long id) {
        return fines.get(id);
    }

    public Fine updateFine(Long id, Fine updatedFine) {
        if (fines.containsKey(id)) {
            updatedFine.setId(id);
            fines.put(id, updatedFine);
            return updatedFine;
        }
        return null;
    }

    public void deleteFine(Long id) {
        fines.remove(id);
    }

    public void markFineAsPaid(Long id) {
        Fine fine = fines.get(id);
        if (fine != null) {
            fine.setPaid(true);
        }
    }
}