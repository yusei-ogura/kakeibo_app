SELECT 
    e.expense_id,
    e.amount,
    e.category_id,
    e.memo,
    e.payment_date,
    e.created_at,
    e.updated_at
FROM
    kakeibo.expense e
INNER JOIN
    kakeibo.category c ON e.category_id = c.category_id
WHERE
    e.payment_date >= '2024-08-01'
    AND e.payment_date < '2024-09-01'
    AND c.delete_flg = false