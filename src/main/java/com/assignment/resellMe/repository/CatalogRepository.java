package com.assignment.resellMe.repository;

import com.assignment.resellMe.model.Brand;
import com.assignment.resellMe.model.Catalog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    Page<Catalog> findAllByBrand(Brand brand, Pageable pageable);
}
