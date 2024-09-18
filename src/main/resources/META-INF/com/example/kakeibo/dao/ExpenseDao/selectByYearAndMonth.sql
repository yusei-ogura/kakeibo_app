SELECT 
    expense_id,
    amount,
    category_id,
    memo,
    payment_date,
    created_at,
    updated_at
FROM
    kakeibo.expense
WHERE
    YEAR(payment_date) = /* year */'2024'
    AND MONTH(payment_date) = /* month */'8'