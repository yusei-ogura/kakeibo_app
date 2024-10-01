SELECT
    category_id,
    name,
    created_at,
    updated_at,
    delete_flg
FROM
    kakeibo.category
WHERE
    category_id = /* categoryId */1
    AND delete_flg = false