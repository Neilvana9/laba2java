package com.library.library.service;

import com.library.library.model.Reader;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReaderService {

    private Map<Long, Reader> readers = new HashMap<>();
    private AtomicLong idCounter = new AtomicLong(1);

    public Reader addReader(String name) {
        Reader reader = new Reader();
        reader.setId(idCounter.getAndIncrement());
        reader.setName(name);
        readers.put(reader.getId(), reader);
        return reader;
    }

    public Reader getReader(Long id) {
        return readers.get(id);
    }

    public Reader updateReader(Long id, String name) {
        Reader reader = readers.get(id);
        if (reader != null) {
            reader.setName(name);
            return reader;
        }
        return null;
    }

    public void deleteReader(Long id) {
        readers.remove(id);
    }
}