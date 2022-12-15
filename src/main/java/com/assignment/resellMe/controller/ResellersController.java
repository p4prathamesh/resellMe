package com.assignment.resellMe.controller;

import com.assignment.resellMe.model.Catalog;
import com.assignment.resellMe.model.request.PaginationRequest;
import com.assignment.resellMe.service.ResellerService;
import com.assignment.resellMe.service.ResponseMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/resellers")
public class ResellersController {

    @Autowired
    private ResellerService resellerService;

    @Autowired
    private ResponseMessageService responseMessageService;

    @RequestMapping(value = "/get-all-catalogs/{brandName}", method = RequestMethod.POST)
    public ResponseEntity getAllCatalogsOfBrand(@PathVariable(name = "brandName") String brandName, @RequestBody PaginationRequest paginationRequest) {
        try {
            Page<Catalog> catalogList = resellerService.getAllCatalogsOfBrand(brandName, paginationRequest);
            if(catalogList!=null) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(responseMessageService
                                .generateMessage(catalogList, 200));
            }else{
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(responseMessageService
                                .generateMessage(null, 500));
            }
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseMessageService
                            .generateMessage(e.getMessage(), 500));
        }
    }

    @RequestMapping(value = "/get-all-brands", method = RequestMethod.POST)
    public ResponseEntity getAllBrandsWithLatestCatalog() {
        try {
            List<Catalog> catalogList = resellerService.getAllBrandsByCatalogsSorted();
            if(catalogList!=null) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(responseMessageService
                                .generateMessage(catalogList, 200));
            }else{
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(responseMessageService
                                .generateMessage(null, 500));
            }
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseMessageService
                            .generateMessage(e.getMessage(), 500));
        }
    }

}
