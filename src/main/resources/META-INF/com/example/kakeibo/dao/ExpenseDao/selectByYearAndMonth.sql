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
    YEAR(e.payment_date) = /* year */'2024'
    AND MONTH(e.payment_date) = /* month */'8'
    AND c.delete_flg = false