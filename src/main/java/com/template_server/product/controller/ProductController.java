package com.template_server.product.controller;

import com.template_server.product.domain.Product;
import com.template_server.product.dto.RequestProductDTO;
import com.template_server.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable String id){
        Product responseDTO = service.getOne(id);
        if(Objects.nonNull(responseDTO)){
            return ResponseEntity.ok(responseDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping()
    public ResponseEntity save(@RequestBody RequestProductDTO body){
        Product responseDTO = service.save(body);
        if(Objects.nonNull(responseDTO)){
            return ResponseEntity.ok(responseDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id){
        service.deleteById(id);
    }

    @GetMapping()
    public List<Product> getAll(){
        return service.getAll();
    }

}

