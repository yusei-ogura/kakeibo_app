package com.example.kakeibo.dao;

import com.example.kakeibo.entity.ExpenseEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@Dao
@ConfigAutowireable
public interface ExpenseDao {

    /** 指定された年と月の支出を取得する */
    @Select
    List<ExpenseEntity> selectByYearAndMonth(int year, int month);
}
