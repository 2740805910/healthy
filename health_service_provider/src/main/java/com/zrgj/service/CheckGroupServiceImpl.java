package com.zrgj.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zrgj.dao.CheckGroupDao;
import com.zrgj.entity.PageResult;
import com.zrgj.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService  {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {

        //添加检查组数据
        checkGroupDao.add(checkGroup);
        //关联添加检查项
        System.out.println(checkItemIds);
        setCheckGroupAndCheckItem(checkGroup.getId(),checkItemIds);
    }





    //分页
    @Override
    public PageResult queryPage(Integer currentPage, Integer pageSize, String queryString) {

        PageHelper.startPage(currentPage,pageSize);
       Page<CheckGroup> page =checkGroupDao.startByPage(queryString);
        return  new PageResult(page.getTotal(),page.getResult());
    }

    //ID查询
    @Override
    public CheckGroup findById(Integer id) {


        return checkGroupDao.findById(id);
    }


    //通过检查组查询关联的检查项
    @Override
    public List<Integer> findCheckItaemIdsByCheckGroupId(Integer id) {
        return  checkGroupDao.findCheckItaemIdsByCheckGroupId(id);

    }

    //修改保存
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkItemIds) {
        //清理检查组关联的检查项
        checkGroupDao.deleteAssication(checkGroup.getId());
       //重新给检查组添加检查项
        setCheckGroupAndCheckItem(checkGroup.getId(),checkItemIds);
        //保存检查组
        checkGroupDao.edit(checkGroup);
    }

    //查询所有
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }


    private void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkItemIds) {

        //判断checkItemIds
        if(checkItemIds!=null && checkItemIds.length>0){
            for(Integer checkItemId :checkItemIds){
                Map<String,Integer> map=new HashMap<>();
                map.put("checkgroup_id",checkGroupId);
                map.put("checkItem_id",checkItemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }

    }
}
