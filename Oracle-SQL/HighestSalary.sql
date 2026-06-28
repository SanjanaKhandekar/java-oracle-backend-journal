-- Day 2: Fetching the highest-paid employee in every department
-- Topic: Oracle SQL Analytical Functions (ROW_NUMBER)

SELECT employee_id, department_id, salary
FROM (
    SELECT employee_id, department_id, salary,
           ROW_NUMBER() OVER (PARTITION BY department_id ORDER BY salary DESC) as ranking
    FROM employees
) 
WHERE ranking = 1;

-- Benefit: Much faster and optimized compared to using traditional nested GROUP BY subqueries.
