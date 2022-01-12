package com.zrgj.dao;

import com.github.pagehelper.Page;
import com.zrgj.pojo.CheckGroup;
import com.zrgj.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {


    //添加套餐
    void add(Setmeal setmeal);

//关联添加检查组
    void setSetmealAndCheckGroup(Map<String, Integer> map);

    //分页
    Page<CheckGroup> startByPage(String queryString);

    //查询所有
    List<Setmeal> findAll();

    ////关联查询套餐的检查组，检查项
    Setmeal findByID(Integer id);

    //统计套餐运营数据
    List<Map<String, Object>> findStemealCount();
}
