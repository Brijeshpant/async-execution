package com.brij.service.impl;

import com.brij.model.Product;
import com.brij.service.ProductService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ProductServiceImpl implements ProductService {

    Set<Product> products = new HashSet<Product>();

    @Override
    public Product creteProduct(int id, String name) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Product product = new Product(id);
        product.setName(name);
        products.add(product);
        return product;
    }

    @Override
    public Set<Product> getProducts() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!this.products.isEmpty()) {
            return this.products;
        }
        throw new RuntimeException("No product found");
    }

    @Override
    public Product getProduct(int productId) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Optional<Product> optionalProduct = products.stream().filter(product -> (product.getId() == productId)).findFirst();
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new RuntimeException("Product not found");
    }
}