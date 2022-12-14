package com.assignment.resellMe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Catalog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catalogId;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Product> productList;

    @Column
    private String catalogTitle;

    @Column(columnDefinition="TEXT")
    private String catalogDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandId", referencedColumnName = "brandId")
    private Brand brand;
}
