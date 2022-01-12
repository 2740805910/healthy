package com.zrgj.dao;

import com.zrgj.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {


    //根据日期来查询有没有相同的日期数据
    Long findCountByDate(Date orderDate);

    //修改
    void editorderSettingDate(OrderSetting orderSetting);

    //添加
    void add(OrderSetting orderSetting);

    //查询日历的预约人数，已预约人数
    List<OrderSetting> getOrderSettingByMonth(Map map);

    //根据日期来查询预约设置
    OrderSetting findByorderDate(Date date);


    //修改Reservations数据
    void editReservationsByOrderDate(OrderSetting orderSetting);
}
