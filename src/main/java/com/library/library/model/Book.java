package com.library.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Book {
    private Long id;
    private String title;
    private List<Long> authorIds = new ArrayList<>();
    private List<String> authorNames = new ArrayList<>();
    private Long currentReaderId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean borrowed = false;
}