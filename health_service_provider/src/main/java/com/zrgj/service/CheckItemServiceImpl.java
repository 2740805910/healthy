package com.zrgj.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zrgj.dao.CheckItemDao;
import com.zrgj.entity.PageResult;
import com.zrgj.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

   @Autowired
   private CheckItemDao checkItemDao;

   //添加
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }


    //分页查询
    @Override
    public PageResult queryPage(Integer currentPage, Integer pageSize, String queryString) {

        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page=checkItemDao.startByPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //ID删除
    @Override
    public void delete(Integer id) {
    //查询当前id是否关联其他数据
        Long count=checkItemDao.findCountByCheckItem(id);
        if(count>0){
            throw  new RuntimeException("当前检查项关联了数据，不能删除！");
        }
        //如果没有关联，直接删除
        checkItemDao.deleteByID(id);

    }



    //id查询
    @Override
    public CheckItem findById(Integer id) {
        return  checkItemDao.findById(id);
    }


    //编辑
    @Override
    public void edit(CheckItem checkItem) {

        checkItemDao.edit(checkItem);

    }

    //查询所有
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }


}
