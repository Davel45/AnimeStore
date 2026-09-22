package com.animestore.dto;

import com.animestore.model.AnimeProduct;
import com.animestore.model.Category;

import java.math.BigDecimal;

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

    public AnimeProductResponse() {
    }

    public AnimeProductResponse(Long id, String title, String franchise, Category category,
                                String characterName, BigDecimal price, Integer stock,
                                Integer releaseYear, Double rating) {
        this.id = id;
        this.title = title;
        this.franchise = franchise;
        this.category = category;
        this.characterName = characterName;
        this.price = price;
        this.stock = stock;
        this.releaseYear = releaseYear;
        this.rating = rating;
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
                product.getRating()
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
}
