-- Day 29: Caching heavy cross-table summary metrics using Oracle Materialized Views
-- Topic: Enterprise Database Query Caching & Aggregations

CREATE MATERIALIZED VIEW mv_monthly_sales_summary
BUILD IMMEDIATE
REFRESH COMPLETE ON DEMAND
AS
SELECT p.category_id,
       COUNT(o.order_id) AS total_orders_placed,
       SUM(o.transaction_amount) AS total_revenue_generated,
       TRUNC(o.order_date, 'MM') AS sales_month
FROM tbl_orders o
JOIN tbl_products p ON o.product_id = p.id
GROUP BY p.category_id, TRUNC(o.order_date, 'MM');

-- Benefit: Frontend reports fetch compiled numbers instantly from this cached view 
-- instead of running costly 3-way table joins over and over again.
