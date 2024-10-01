package com.example.kakeibo.dao;

import com.example.kakeibo.entity.CategoryEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;
import java.util.Optional;

@Dao
@ConfigAutowireable
public interface CategoryDao {

    /** カテゴリーIDに紐付くカテゴリーを取得する */
    @Select
    Optional<CategoryEntity> selectById(int categoryId);

    /** すべてのカテゴリーを取得する */
    @Select
    List<CategoryEntity> selectAll();

    /** カテゴリーを登録する */
    @Insert
    int insert(CategoryEntity category);

    /** カテゴリーを削除する */
    @Update
    int updateDeleteFlg(CategoryEntity category);

}
