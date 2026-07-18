-- Day 23: Automated data modification tracking using Oracle Row-Level Triggers
-- Topic: PL/SQL Triggers & System Data Auditing

-- 1. Create a structured history table to hold old vs new records
CREATE TABLE product_price_history (
    log_id NUMBER GENERATED ALWAYS AS IDENTITY,
    product_id NUMBER,
    old_price NUMBER(10,2),
    new_price NUMBER(10,2),
    changed_by VARCHAR2(50),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_price_history PRIMARY KEY (log_id)
);

-- 2. Build the automated row-level logging trigger
CREATE OR REPLACE TRIGGER trg_audit_product_price
AFTER UPDATE OF prod_price ON TBL_PRODUCTS
FOR EACH ROW
BEGIN
    -- Capture background updates instantly only when the price values change
    IF :OLD.prod_price <> :NEW.prod_price THEN
        INSERT INTO product_price_history (product_id, old_price, new_price, changed_by)
        VALUES (:OLD.id, :OLD.prod_price, :NEW.prod_price, USER);
    END IF;
END;
/

-- Benefit: Enforces perfect data accountability. Even if an external administrator 
-- updates a price manually outside of the Spring Boot Java app, this internal database 
-- trigger instantly logs who did it and when, creating a bulletproof security audit trail.
