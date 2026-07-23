// Day 28: Fetching big data segments safely using Spring Data Pagination
// Topic: API Performance Optimization & Pagination

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCatalogService {

    private final ProductRepository productRepository;

    public Page<Product> getPaginatedProducts(int pageNumber, int pageSize, String sortByField) {
        // Create a safe pageable configuration rule sorting by descending order
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortByField).descending());
        
        // Fetches only a tiny subset (e.g. 10 items) instead of extracting the full Oracle table
        return productRepository.findAll(pageable);
    }
}
