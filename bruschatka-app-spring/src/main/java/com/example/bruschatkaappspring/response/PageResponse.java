package com.example.bruschatkaappspring.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {
    private List clients;
    private long overallElements;
    private long totalPages;
}
