// Day 7: Mapping an Enterprise Entity with Oracle Database Sequence Generation
// Topic: Spring Data JPA Entity Mapping & Primary Key Strategies

import jakarta.persistence.*;

@Entity
@Table(name = "TBL_PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_seq_gen")
    @SequenceGenerator(name = "prod_seq_gen", sequenceName = "SEQ_PRODUCT_ID", allocationSize = 1)
    private Long id;

    @Column(name = "prod_name", nullable = false, length = 100)
    private String name;

    @Column(name = "prod_price", precision = 10, scale = 2)
    private double price;

    // Default Constructor
    public Product() {}

    // Parameterized Constructor
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
