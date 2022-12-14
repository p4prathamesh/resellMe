package com.assignment.resellMe.controller;

import com.assignment.resellMe.model.Brand;
import com.assignment.resellMe.model.Catalog;
import com.assignment.resellMe.model.Product;
import com.assignment.resellMe.repository.BrandRepository;
import com.assignment.resellMe.repository.CatalogRepository;
import com.assignment.resellMe.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/brands")
public class BrandsController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/upload/catalogs", method = RequestMethod.POST)
    public ResponseEntity uploadCatalogs(@RequestParam(value = "file") MultipartFile[] files, @RequestParam(value = "catalog") String catalogJson) {
        try {
            productService.saveCatalog(catalogJson, files);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return null;
        }
    }
}
