package com.animestore.repository;

import com.animestore.model.AnimeProduct;
import com.animestore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeProductRepository extends JpaRepository<AnimeProduct, Long> {

    List<AnimeProduct> findByTitleContainingIgnoreCase(String title);

    List<AnimeProduct> findByFranchiseContainingIgnoreCase(String franchise);

    List<AnimeProduct> findByCategory(Category category);

    @Query("SELECT p FROM AnimeProduct p WHERE " +
           "(:title IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:franchise IS NULL OR LOWER(p.franchise) LIKE LOWER(CONCAT('%', :franchise, '%'))) AND " +
           "(:category IS NULL OR p.category = :category)")
    List<AnimeProduct> searchProducts(@Param("title") String title,
                                      @Param("franchise") String franchise,
                                      @Param("category") Category category);
}
