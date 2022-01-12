package com.zrgj.service;

import com.zrgj.entity.PageResult;
import com.zrgj.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {


    //添加
    void add(CheckItem checkItem);

    //分页查询
    PageResult queryPage(Integer currentPage, Integer pageSize, String queryString);

    //id删除
    void delete(Integer id);




    //id查询
    CheckItem findById(Integer id);

    //修改
    void edit(CheckItem checkItem);

//查询所有
    List<CheckItem> findAll();
}
