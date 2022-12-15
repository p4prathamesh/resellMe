package com.assignment.resellMe.service;

import com.assignment.resellMe.model.Brand;
import com.assignment.resellMe.model.Catalog;
import com.assignment.resellMe.model.request.PaginationRequest;
import com.assignment.resellMe.repository.BrandRepository;
import com.assignment.resellMe.repository.CatalogRepository;
import com.assignment.resellMe.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResellerService {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public Page<Catalog> getAllCatalogsOfBrand(String brandName, PaginationRequest paginationRequest){
        //to find brand object
        Brand brand = brandRepository.findByBrandName(brandName);
        //to find all catalogs with brandName
        if(brand!=null){
            Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize(), Sort.by(paginationRequest.getSortBy()).descending());
            return catalogRepository.findAllByBrand(brand, pageable);
        }
        return null;
    }

    public List<Catalog> getAllBrandsByCatalogsSorted(){
        List<Catalog> catalogList = new ArrayList<>();
        //to find brand object
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by("createdDate").descending());
        Page<Brand> brandsInDesc = brandRepository.findAll(pageable);
        if(brandsInDesc!=null && brandsInDesc.getContent().size()>0){
            List<Brand> brandList = brandsInDesc.getContent();
            for(Brand brand: brandList){
                //to find top 1 catalog of selected brand
                Pageable pageableForCatalog = PageRequest.of(0, 1, Sort.by("postTime").descending());
                Page<Catalog> catalogs = catalogRepository.findAllByBrand(brand, pageableForCatalog);
                if(catalogs!=null && catalogs.getContent().size()>0) {
                    catalogList.add(catalogs.getContent().get(0));
                }
            }
        }
        return catalogList;
    }
}
