// Day 24: Isolated REST controller testing without booting the whole network layer
// Topic: JUnit 5 & Mockito MockMvc API Validation

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AdminController.class) // Focuses test context strictly on the web controller layer
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService; // Bypasses actual database and service layer execution logic

    @Test
    void shouldReturnProductDetailsSuccessfully() throws Exception {
        // Arrange: Setup mock data returns
        Product mockProduct = new Product("Laptop", 75000.00);
        mockProduct.setId(101L);
        when(productService.getProductById(101L)).thenReturn(mockProduct);

        // Act & Assert: Simulate a GET request and verify response payload properties
        mockMvc.perform(get("/api/v1/products/101")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(75000.00));
    }
}

// Benefit: Drastically cuts down testing time. WebMvcTest along with Mockito allows 
// you to test input/output mappings, HTTP statuses, and JSON validation rules in 
// milliseconds without starting an actual local application server or an Oracle instance.
