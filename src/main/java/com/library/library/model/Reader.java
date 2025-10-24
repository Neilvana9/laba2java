package com.library.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Reader
{
    private Long id;
    private String name;
    @JsonIgnore
    private List<Long> borrowedBookIds = new ArrayList<>();
}