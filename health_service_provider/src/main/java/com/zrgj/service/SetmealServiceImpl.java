package com.zrgj.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zrgj.dao.SetmealDao;
import com.zrgj.entity.PageResult;
import com.zrgj.pojo.CheckGroup;
import com.zrgj.pojo.Setmeal;
import com.zrgj.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private SetmealDao setmealDao;
    //新增套餐
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        if(checkgroupIds != null && checkgroupIds.length > 0){
            //绑定套餐和检查组的多对多关系
            setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        }

        //将图片名称保存到Redis
        savePic2Redis(setmeal.getImg());
    }
    //将图片名称保存到Redis
    private void savePic2Redis(String pic){
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);
    }

    @Override
    public PageResult queryPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = setmealDao.startByPage(queryString);
        return  new PageResult(page.getTotal(),page.getResult());
    }


    //查询所有
    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }


    //关联查询套餐的检查组，检查项
    @Override
    public Setmeal findByID(Integer id) {
        return setmealDao.findByID(id);
    }


    //统计套餐运营数据
    @Override
    public List<Map<String, Object>> findStemealCount() {
        return setmealDao.findStemealCount();
    }

    //绑定套餐和检查组的多对多关系
    private void setSetmealAndCheckGroup(Integer id, Integer[] checkgroupIds) {
        System.out.println(checkgroupIds);
        for (Integer checkgroupId : checkgroupIds) {
            Map<String,Integer> map = new HashMap<>();
            map.put("setmeal_id",id);
            map.put("checkgroup_id",checkgroupId);
            setmealDao.setSetmealAndCheckGroup(map);
        }
    }







}
