package com.animestore.dto;

import com.animestore.model.Category;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class AnimeProductRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Franchise name is required")
    private String franchise;

    @NotNull(message = "Category is required")
    private Category category;

    private String characterName;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price cannot be negative")
    private BigDecimal price;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    @Min(value = 1980, message = "Year must be 1980 or later")
    private Integer releaseYear;

    @DecimalMin(value = "0.0", message = "Rating cannot be less than 0.0")
    @DecimalMax(value = "5.0", message = "Rating cannot be greater than 5.0")
    private Double rating;

    public AnimeProductRequest() {
    }

    public AnimeProductRequest(String title, String franchise, Category category, String characterName,
                               BigDecimal price, Integer stock, Integer releaseYear, Double rating) {
        this.title = title;
        this.franchise = franchise;
        this.category = category;
        this.characterName = characterName;
        this.price = price;
        this.stock = stock;
        this.releaseYear = releaseYear;
        this.rating = rating;
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
