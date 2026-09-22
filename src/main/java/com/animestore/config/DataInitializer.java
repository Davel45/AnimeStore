package com.animestore.config;

import com.animestore.model.AnimeProduct;
import com.animestore.model.Category;
import com.animestore.repository.AnimeProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AnimeProductRepository repository;

    public DataInitializer(AnimeProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        repository.saveAll(Arrays.asList(
                new AnimeProduct("Naruto Uzumaki Sage Mode Figure", "Naruto Shippuden", Category.FIGURE,
                        "Naruto Uzumaki", new BigDecimal("89.99"), 15, 2021, 4.9),
                new AnimeProduct("Roronoa Zoro Enma Sword Replica", "One Piece", Category.COSPLAY,
                        "Roronoa Zoro", new BigDecimal("119.50"), 8, 2022, 4.8),
                new AnimeProduct("Attack on Titan Manga Season 1 Box Set", "Attack on Titan", Category.MANGA,
                        "Eren Yeager", new BigDecimal("64.00"), 25, 2020, 5.0),
                new AnimeProduct("Gojo Satoru Infinity Nendoroid", "Jujutsu Kaisen", Category.NENDOROID,
                        "Gojo Satoru", new BigDecimal("55.00"), 30, 2023, 4.9),
                new AnimeProduct("Tanjiro Kamado Hanafuda Earrings", "Demon Slayer", Category.ACCESSORY,
                        "Tanjiro Kamado", new BigDecimal("19.99"), 50, 2021, 4.7),
                new AnimeProduct("Chainsaw Man Metallic Art Poster", "Chainsaw Man", Category.POSTER,
                        "Denji", new BigDecimal("24.50"), 40, 2023, 4.6)
        ));
    }
}
