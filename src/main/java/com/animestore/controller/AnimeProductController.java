package com.animestore.controller;

import com.animestore.dto.AnimeProductRequest;
import com.animestore.dto.AnimeProductResponse;
import com.animestore.model.Category;
import com.animestore.service.AnimeProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class AnimeProductController {

    private final AnimeProductService service;

    public AnimeProductController(AnimeProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AnimeProductResponse>> getAllProducts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String franchise,
            @RequestParam(required = false) Category category) {
        return ResponseEntity.ok(service.searchProducts(title, franchise, category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimeProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<AnimeProductResponse> createProduct(
            @Valid @RequestBody AnimeProductRequest request,
            UriComponentsBuilder uriBuilder) {
        AnimeProductResponse created = service.createProduct(request);
        URI location = uriBuilder.path("/api/products/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimeProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody AnimeProductRequest request) {
        return ResponseEntity.ok(service.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
    }
}
