package com.zrgj.dao;

import com.github.pagehelper.Page;
import com.zrgj.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {


    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    //分页查询
    Page<CheckGroup> startByPage(String queryString);

    //id查询
    CheckGroup findById(Integer id);


    //通过检查组查询关联的检查项
    List<Integer> findCheckItaemIdsByCheckGroupId(Integer id);

    //删除检查组关联的检查项
    void deleteAssication(Integer id);

    //修改保存
    void edit(CheckGroup checkGroup);

    //查询所有
    List<CheckGroup> findAll();


    //查询关联的检查组
    List<CheckGroup>  findByCheckGroupsId(Integer stemealID);

}
