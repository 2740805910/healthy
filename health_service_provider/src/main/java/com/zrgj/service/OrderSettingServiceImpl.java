package com.zrgj.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zrgj.dao.OrderSettingDao;
import com.zrgj.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements  OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> orderSettings) {

        //先判断
        if(orderSettings!=null && orderSettings.size()>0){

            for(OrderSetting orderSetting:orderSettings){
                //检查日期是否存在，如果存在我们就修改
                Long  count=orderSettingDao.findCountByDate(orderSetting.getOrderDate());
                if(count>0){
                    //日期存在
                    orderSettingDao.editorderSettingDate(orderSetting);
                }else{

                    orderSettingDao.add(orderSetting);
                }


            }




        }


    }

    //获取日历数据
    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String dateBegin = date + "-1";//2019-3-1
        String dateEnd = date + "-31";//2019-3-31
        Map map = new HashMap();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());//获得日期（几号）
            orderSettingMap.put("number",orderSetting.getNumber());//可预约人数
            orderSettingMap.put("reservations",orderSetting.getReservations());//已预约人数
            data.add(orderSettingMap);
        }
        return data;
    }


    //修改预约设置
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
              //先查询如果设置了预约，我们就修改
        Long count=orderSettingDao.findCountByDate(orderSetting.getOrderDate());
            if(count>0){
                orderSettingDao.editorderSettingDate(orderSetting);
            }else {
              //如果没有设置预约人数，我们就保存
                orderSettingDao.add(orderSetting);
            }




    }
}
