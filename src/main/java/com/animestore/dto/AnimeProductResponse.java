package com.animestore.dto;

import com.animestore.model.AnimeProduct;
import com.animestore.model.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AnimeProductResponse {

    private Long id;
    private String title;
    private String franchise;
    private Category category;
    private String characterName;
    private BigDecimal price;
    private Integer stock;
    private Integer releaseYear;
    private Double rating;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;
    private Long version;

    public AnimeProductResponse() {
    }

    public AnimeProductResponse(Long id, String title, String franchise, Category category,
                                String characterName, BigDecimal price, Integer stock,
                                Integer releaseYear, Double rating,
                                LocalDateTime createdDate, String createdBy,
                                LocalDateTime lastModifiedDate, String lastModifiedBy,
                                Long version) {
        this.id = id;
        this.title = title;
        this.franchise = franchise;
        this.category = category;
        this.characterName = characterName;
        this.price = price;
        this.stock = stock;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastModifiedDate = lastModifiedDate;
        this.lastModifiedBy = lastModifiedBy;
        this.version = version;
    }

    public static AnimeProductResponse fromEntity(AnimeProduct product) {
        return new AnimeProductResponse(
                product.getId(),
                product.getTitle(),
                product.getFranchise(),
                product.getCategory(),
                product.getCharacterName(),
                product.getPrice(),
                product.getStock(),
                product.getReleaseYear(),
                product.getRating(),
                product.getCreatedDate(),
                product.getCreatedBy(),
                product.getLastModifiedDate(),
                product.getLastModifiedBy(),
                product.getVersion()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
