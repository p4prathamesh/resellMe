package com.assignment.resellMe.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaginationRequest implements Serializable {
    private int page;
    private int size;
    private String sortBy;
}
