// Day 10: Writing Native SQL Queries inside Spring Repositories to handle Oracle specific syntax
// Topic: Spring Data JPA @Query Annotation & Parameter Binding

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Using Native Query syntax to execute raw Oracle SQL logic directly
    // This allows us to use custom table naming schemes and explicit sorting columns smoothly
    @Query(value = "SELECT * FROM TBL_PRODUCTS WHERE prod_price > :minPrice ORDER BY prod_name DESC", nativeQuery = true)
    List<Product> findExpensiveProducts(@Param("minPrice") double minPrice);
}
