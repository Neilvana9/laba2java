package com.library.library.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateBookRequest {
    private List<Long> authorIds;
    private List<String> authorNames;
    private String title;
}