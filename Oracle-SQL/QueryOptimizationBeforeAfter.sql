-- Day 22: Optimizing query lookup times from a slow sequential scan to a fast index search
-- Topic: Oracle Performance Tuning & Function-Based Indexes

-- =========================================================================
-- BEFORE OPTIMIZATION (Slow - Causes full table scans over millions of records)
-- =========================================================================
SELECT employee_id, first_name, email 
FROM hr_employees 
WHERE UPPER(email) = 'RAHUL.DEV@GMAIL.COM';

-- Reason for Slowness: Even if the 'email' column has a standard index, applying 
-- the UPPER() function on it forces the Oracle engine to completely bypass that index, 
-- causing an expensive Full Table Scan (FTS) across the entire storage layer.


-- =========================================================================
--  THE FIX: Create an Oracle Function-Based Index
-- =========================================================================
CREATE INDEX idx_emp_email_upper ON hr_employees (UPPER(email));


-- =========================================================================
--  AFTER OPTIMIZATION (Blazing Fast - Switched to Index Range Scan)
-- =========================================================================
SELECT employee_id, first_name, email 
FROM hr_employees 
WHERE UPPER(email) = 'RAHUL.DEV@GMAIL.COM';

-- Execution Plan Metrics:
-- Operation switched from "TABLE ACCESS FULL" to "INDEX RANGE SCAN"
-- Cost dropped by over 90% | Read Blocks reduced drastically | Response Time < 5ms.
