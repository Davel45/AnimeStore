package com.animestore.controller;

import com.animestore.dto.AnimeProductRequest;
import com.animestore.dto.AnimeProductResponse;
import com.animestore.model.Category;
import com.animestore.service.AnimeProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPERADMIN')")
    @GetMapping
    public ResponseEntity<List<AnimeProductResponse>> getAllProducts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String franchise,
            @RequestParam(required = false) Category category) {
        return ResponseEntity.ok(service.searchProducts(title, franchise, category));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPERADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AnimeProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
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

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AnimeProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody AnimeProductRequest request) {
        return ResponseEntity.ok(service.updateProduct(id, request));
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
    }

    // Role-based access endpoints with annotations
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello/admin")
    public String helloAdmin() {
        return "Hello Admin!";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello/user")
    public String helloUser() {
        return "Hello User!";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPERADMIN')")
    @GetMapping("/hello/man")
    public String helloMan() {
        return "Hello, AnimeStore Member!";
    }
}
