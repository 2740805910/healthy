package com.zrgj.dao;

import com.zrgj.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {


    List<Order> findByCondtion(Order order);
    void   add(Order order);



    public Map findById4Detail(Integer id);
    //统计运营数据
    public Integer findOrderCountByDate(String date);
    public Integer findOrderCountAfterDate(String date);
    public Integer findVisitsCountByDate(String date);
    public Integer findVisitsCountAfterDate(String date);


    List<Map> findHotsetmeal();
}
