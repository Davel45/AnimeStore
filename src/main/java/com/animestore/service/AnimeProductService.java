package com.animestore.service;

import com.animestore.dto.AnimeProductRequest;
import com.animestore.dto.AnimeProductResponse;
import com.animestore.exception.ResourceNotFoundException;
import com.animestore.model.AnimeProduct;
import com.animestore.model.Category;
import com.animestore.repository.AnimeProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnimeProductService {

    private final AnimeProductRepository repository;

    public AnimeProductService(AnimeProductRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<AnimeProductResponse> getAllProducts() {
        return repository.findAll().stream()
                .map(AnimeProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnimeProductResponse getProductById(Long id) {
        AnimeProduct product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anime product", id));
        return AnimeProductResponse.fromEntity(product);
    }

    @Transactional(readOnly = true)
    public List<AnimeProductResponse> searchProducts(String title, String franchise, Category category) {
        String cleanTitle = (title != null && !title.trim().isEmpty()) ? title.trim() : null;
        String cleanFranchise = (franchise != null && !franchise.trim().isEmpty()) ? franchise.trim() : null;

        if (cleanTitle == null && cleanFranchise == null && category == null) {
            return getAllProducts();
        }

        if (cleanTitle != null && cleanFranchise == null && category == null) {
            return repository.findByTitleContainingIgnoreCase(cleanTitle).stream()
                    .map(AnimeProductResponse::fromEntity)
                    .collect(Collectors.toList());
        }

        if (cleanFranchise != null && cleanTitle == null && category == null) {
            return repository.findByFranchiseContainingIgnoreCase(cleanFranchise).stream()
                    .map(AnimeProductResponse::fromEntity)
                    .collect(Collectors.toList());
        }

        if (category != null && cleanTitle == null && cleanFranchise == null) {
            return repository.findByCategory(category).stream()
                    .map(AnimeProductResponse::fromEntity)
                    .collect(Collectors.toList());
        }

        return repository.searchProducts(cleanTitle, cleanFranchise, category).stream()
                .map(AnimeProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public AnimeProductResponse createProduct(AnimeProductRequest request) {
        AnimeProduct product = new AnimeProduct(
                request.getTitle().trim(),
                request.getFranchise().trim(),
                request.getCategory(),
                request.getCharacterName() != null ? request.getCharacterName().trim() : null,
                request.getPrice(),
                request.getStock(),
                request.getReleaseYear(),
                request.getRating()
        );

        AnimeProduct saved = repository.save(product);
        return AnimeProductResponse.fromEntity(saved);
    }

    public AnimeProductResponse updateProduct(Long id, AnimeProductRequest request) {
        AnimeProduct product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anime product", id));

        product.setTitle(request.getTitle().trim());
        product.setFranchise(request.getFranchise().trim());
        product.setCategory(request.getCategory());
        product.setCharacterName(request.getCharacterName() != null ? request.getCharacterName().trim() : null);
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setReleaseYear(request.getReleaseYear());
        product.setRating(request.getRating());

        AnimeProduct updated = repository.save(product);
        return AnimeProductResponse.fromEntity(updated);
    }

    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Anime product", id);
        }
        repository.deleteById(id);
    }
}
