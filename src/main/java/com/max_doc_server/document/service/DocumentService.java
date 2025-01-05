package com.max_doc_server.product.service;

import com.max_doc_server.product.domain.Product;
import com.max_doc_server.product.dto.RequestProductDTO;
import com.max_doc_server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    public Product getOne(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product save(RequestProductDTO productDTO){
        Product product = modelMapper.map(productDTO,Product.class);
        return repository.save(product);
    }

    public void deleteById(String id){
        repository.deleteById(id);
    }

}