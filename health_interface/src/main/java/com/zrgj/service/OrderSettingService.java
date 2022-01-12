package com.zrgj.service;

import com.zrgj.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {


    //添加
    void add(List<OrderSetting> orderSettings);

    //获取日历设置
    List<Map> getOrderSettingByMonth(String data);

    //修改预约设置
    void editNumberByDate(OrderSetting orderSetting);
}
