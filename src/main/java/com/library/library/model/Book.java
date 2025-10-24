package com.library.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.time.LocalDate;

@Data
public class Book
{
    private Long id;
    private String title;
    private Long authorId;
    private String authorName;
    @JsonIgnore
    private Reader currentReader;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean borrowed = false;
}