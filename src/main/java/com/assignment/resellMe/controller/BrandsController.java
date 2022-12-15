package com.assignment.resellMe.controller;

import com.assignment.resellMe.model.Catalog;
import com.assignment.resellMe.service.ProductService;
import com.assignment.resellMe.service.ResponseMessageService;
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

    @Autowired
    private ResponseMessageService responseMessageService;

    @RequestMapping(value = "/upload/catalog", method = RequestMethod.POST)
    public ResponseEntity uploadCatalog(@RequestParam(value = "file") MultipartFile[] files, @RequestParam(value = "catalog") String catalogJson) {
        try {
            Catalog catalog = productService.saveCatalog(catalogJson, files);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseMessageService
                            .generateMessage(catalog, 200));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseMessageService
                            .generateMessage(e.getMessage(), 500));
        }
    }

}
