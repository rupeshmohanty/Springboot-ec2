package com.deployapplication.demo.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deployapplication.demo.model.Product;
import com.deployapplication.demo.repo.ProductRepository;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    /**
     * Method to get all products
     * @return List of products
     */
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> productList = new ArrayList<>();
            productRepository.findAll().forEach(productList::add);

            if(productList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get product by id
     * @param id product id
     * @return Product
     */
    @GetMapping("/getProductById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> productObj = productRepository.findById(id);

        if(productObj.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(productObj.get(), HttpStatus.OK);
        }
    }

    /**
     * Method to add product
     * @param product Product
     * @return Product
     */
    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            Product productObj = new Product();
            productObj.setProductName(product.getProductName());
            productObj.setProductDescription(product.getProductDescription());
            productObj.setPrice(product.getPrice());
            productObj.setCreatedDate(new Date());

            productObj = productRepository.save(productObj);
            return new ResponseEntity<>(productObj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to update product details
     * @param id product id
     * @param product Product
     * @return Product
     */
    @PostMapping("/updateProduct/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Optional<Product> productObj = productRepository.findById(id);

            if(productObj.isPresent()) {
                Product updateProduct = productObj.get();
                updateProduct.setProductName(product.getProductName());
                updateProduct.setProductDescription(product.getProductDescription());
                updateProduct.setPrice(product.getPrice());

                updateProduct = productRepository.save(updateProduct);
                return new ResponseEntity<>(updateProduct, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to delete book by id
     * @param id book id
     * @return HttpStatus
     */
    @DeleteMapping("/deleteProductById/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to delete all books
     * @return HttpStatus
     */
    @DeleteMapping("/deleteAllProducts")
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        try {
            productRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
