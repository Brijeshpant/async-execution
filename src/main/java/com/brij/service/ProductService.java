package com.brij.service;

import com.brij.model.Product;

import java.util.Set;

public interface ProductService {
    Product getProduct(int product);
    Product creteProduct(int id, String name);
    Set<Product> getProducts();
}