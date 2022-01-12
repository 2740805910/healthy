package com.zrgj.dao;

import com.github.pagehelper.Page;
import com.zrgj.pojo.CheckItem;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

public interface CheckItemDao {

//添加
    public void add(CheckItem checkItem);


    //分页查询
    Page<CheckItem> startByPage(String queryString);

    //查询是否关联其他表
    Long findCountByCheckItem(Integer checkItemID);

    //删除
    void deleteByID(Integer id);




    //id查询
    CheckItem findById(Integer id);

    //编辑
    void edit(CheckItem checkItem);

//查询所有
    List<CheckItem> findAll();

    //根据检查组关联查询检查项
   List<CheckItem> findCheckItems(Integer checkgroupId);

}
