CREATE SCHEMA IF NOT EXISTS kakeibo;
SET SCHEMA kakeibo;

DROP TABLE IF EXISTS expense;

CREATE TABLE kakeibo.expense (
    expense_id INT PRIMARY KEY, -- 主キー
    amount INT NOT NULL, -- 支出額
    category_id INT, -- 外部キー（Categoryテーブル）
    memo VARCHAR(150), -- メモ
    payment_date DATE NOT NULL, -- 支払い日
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 作成日時
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新日時
);

