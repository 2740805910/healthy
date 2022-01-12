package com.zrgj.service;

import com.zrgj.entity.PageResult;
import com.zrgj.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {


    void add(CheckGroup checkGroup, Integer[] checkItemIds);

    //分页查询
    PageResult queryPage(Integer currentPage, Integer pageSize, String queryString);

    //id查询
    CheckGroup findById(Integer id);

    //通过检查组查询关联的检查项
    List<Integer> findCheckItaemIdsByCheckGroupId(Integer id);

    //修改保存
    void edit(CheckGroup checkGroup, Integer[] checkItemIds);

    //查询所有
    List<CheckGroup> findAll();
}
