SELECT
    category_id,
    name,
    created_at,
    updated_at,
    delete_flg
FROM
    kakeibo.category
WHERE
    delete_flg = FALSE
