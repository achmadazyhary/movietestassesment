package com.example.movietestassesment.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponses {
    
    private Integer currentPage;

    private Integer totalPage;

    private Integer size;
}
