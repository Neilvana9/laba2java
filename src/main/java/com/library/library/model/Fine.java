package com.library.library.model;

import lombok.Data;

@Data
public class Fine {
    private Long id;
    private Long readerId;
    private Long bookId;
    private double amount;
    private boolean paid = false;
}