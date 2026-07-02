-- Day 6: Combining Employee Data with Department Managers and handling Null locations
-- Topic: SQL Outer Joins and COALESCE function in Oracle Database

SELECT e.employee_id, 
       e.first_name || ' ' || e.last_name AS employee_name,
       d.department_name,
       COALESCE(d.location_name, 'Remote / Unassigned') AS work_location
FROM employees e
LEFT JOIN departments d ON e.department_id = d.department_id
ORDER BY d.department_name ASC;

-- Benefit: Prevents full-stack applications from throwing NullPointerExceptions 
-- by replacing blank fields with a default string directly at the database level.
