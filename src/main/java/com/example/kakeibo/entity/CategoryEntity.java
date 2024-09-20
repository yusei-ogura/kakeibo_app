package com.example.kakeibo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import java.time.LocalDateTime;

/**
 * カテゴリーEntity
 */
@Entity
@Table(name = "kakeibo.category")
public class CategoryEntity {

    /** カテゴリーID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;

    /** カテゴリー名 */
    @Column(name = "name")
    private String name;

    /** 作成日 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** 更新日 */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** 削除フラグ */
    @Column(name = "delete_flg")
    private boolean deleteFlg;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleteFlg() {
        return deleteFlg;
    }

    public void setDeleteFlg(boolean deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

}