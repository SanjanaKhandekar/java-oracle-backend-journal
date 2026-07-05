-- Day 9: Correlated Subqueries vs EXISTS Clause Optimization
-- Topic: Finding departments that have at least one high-earning employee (> 100,000)

SELECT department_id, department_name 
FROM departments d
WHERE EXISTS (
    SELECT 1 
    FROM employees e 
    WHERE e.department_id = d.department_id 
      AND e.salary > 100000
);

-- Performance Tip: Traditional inner joins force the database to combine and sort 
-- massive duplicate rows across both tables into memory. The EXISTS clause optimizes this 
-- by causing the Oracle engine to stop scanning the table the exact millisecond it finds 
-- the very first matching record, saving significant server read I/O.
