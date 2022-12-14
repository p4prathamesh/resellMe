package com.assignment.resellMe.service;

import com.assignment.resellMe.model.Brand;
import com.assignment.resellMe.model.Catalog;
import com.assignment.resellMe.model.Product;
import com.assignment.resellMe.repository.BrandRepository;
import com.assignment.resellMe.repository.CatalogRepository;
import com.assignment.resellMe.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Async
    public CompletableFuture<Product> saveProduct(MultipartFile multipartFile, Product product, String fileName){
        Product productSaved = null;
        logger.info("saving list of products", " "+ Thread.currentThread().getName());
        try{
            //file path storage in db and file stored in resources
            String path = "src/main/resources/"+fileName+".jpg";
            File file = new File(path);
            try (OutputStream os = new FileOutputStream(file)) {
                os.write(multipartFile.getBytes());
            }
            product.setImagePath(path);
            productSaved = productRepository.save(product);
            return CompletableFuture.completedFuture(productSaved);
        }catch (Exception e){
            logger.error("Error in image storage: ", e.getMessage());
            return CompletableFuture.completedFuture(productSaved);
        }
    }

    public Catalog saveCatalog(String catalogJson, MultipartFile[] files){
        try {
            //to read json
            Catalog catalog = objectMapper.readValue(catalogJson, Catalog.class);
            if (catalog != null) {
                //input ProductList
                List<Product> productList = catalog.getProductList();
                //saved ProductList
                List<Product> savedProductList = new ArrayList<>();
                //code to save brand
                Brand brand = catalog.getBrand();
                Brand savedBrand = brandRepository.save(brand);
                //code to save product
                if (productList != null && productList.size() > 0) {
                    for (Product product : productList) {
                        for (MultipartFile multipartFile : files) {
                            String fileName = multipartFile.getOriginalFilename();
                            if (fileName.equalsIgnoreCase(product.getImageName())) {
                                //thread to store image
                                CompletableFuture<Product> completableFutureProduct = saveProduct(multipartFile, product, fileName);
                                Product savedProduct = completableFutureProduct.get();
                                savedProductList.add(savedProduct);
                                break;
                            }
                        }
                    }
                }
                //code to save catalog
                catalog.setBrand(brand);
                catalog.setProductList(savedProductList);
                return catalogRepository.save(catalog);
            }
        }catch (Exception e){
            logger.error("Error in saving catalog :", e.getMessage());
            return null;
        }
        return null;
    }

}
