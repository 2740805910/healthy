package com.zrgj.service;

import com.zrgj.entity.PageResult;
import com.zrgj.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {

//  添加套餐
    void add(Setmeal setmeal, Integer[] checkItemIds);

    //分页
    PageResult queryPage(Integer currentPage, Integer pageSize, String queryString);


    //查询所有套餐
    List<Setmeal> findAll();

    //关联查询套餐的检查组，检查项
    Setmeal findByID(Integer id);

    //统计套餐数据
    List<Map<String, Object>> findStemealCount();
}
