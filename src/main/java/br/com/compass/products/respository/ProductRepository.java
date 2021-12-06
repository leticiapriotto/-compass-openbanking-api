package br.com.compass.products.respository;

import br.com.compass.products.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM Product WHERE (:q IS NULL OR (UPPER(name) LIKE UPPER(CONCAT('%', :q, '%'))"
            + "OR UPPER(description) LIKE UPPER(CONCAT('%', :q, '%'))))"
            + "AND (:minPrice IS NULL OR price >= :minPrice)"
            + "AND (:maxPrice IS NULL OR price <= :maxPrice)", nativeQuery = true)
    Page<Product> findBySearch(Pageable pageable, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, @Param("q") String q);

}
