// Day 21: Ensuring data integrity using transactional rollback across multiple tables
// Topic: Spring Data JPA @Transactional Management & Atomic Operations

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderProcessingService {

    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;
    private final AuditLogRepository auditLogRepository;

    // Constructor Injection
    public OrderProcessingService(OrderRepository orderRepository, 
                                  InventoryRepository inventoryRepository, 
                                  AuditLogRepository auditLogRepository) {
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
        this.auditLogRepository = auditLogRepository;
    }

    // rollbackFor ensures any RuntimeException triggers a full database rollback
    @Transactional(rollbackFor = Exception.class) 
    public void processOrder(Order order, Long productId, int quantity) {
        // 1. Save main order entry into the primary table
        orderRepository.save(order);

        // 2. Update child table inventory count
        int updatedRows = inventoryRepository.deductStock(productId, quantity);
        if (updatedRows == 0) {
            // Explicitly throw an exception to force the database to revert Step 1 automatically
            throw new RuntimeException("Stock unavailable. Rolling back entire transaction.");
        }

        // 3. Log data modifications into a separate structural audit trail table
        auditLogRepository.logAction("ORDER_PLACED", "User created order ID: " + order.getId());
    }
}

// Benefit: Prevents orphaned records. If Step 2 or Step 3 fails, the @Transactional 
// annotation completely undoes Step 1. This ensures you never end up with a saved order 
// that didn't actually deduct stock, keeping your transactional data perfectly accurate.
