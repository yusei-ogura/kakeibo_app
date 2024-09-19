package com.example.kakeibo.dao;

import com.example.kakeibo.entity.ExpenseEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;
import java.util.Optional;

@Dao
@ConfigAutowireable
public interface ExpenseDao {

    /** 指定された年と月の支出を取得する */
    @Select
    List<ExpenseEntity> selectByYearAndMonth(int year, int month);

    /** 支出を登録する */
    @Insert
    int insert(ExpenseEntity entity);

    /** 支出IDで支出を取得する */
    @Select
    Optional<ExpenseEntity> selectById(Integer expenseId);

    /** 支出を更新する */
    @Update
    int update(ExpenseEntity entity);

    /** 支出を削除する */
    @Delete
    int delete(ExpenseEntity entity);
}
