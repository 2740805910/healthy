package com.zrgj.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.zrgj.constant.MessageConstant;
import com.zrgj.entity.Result;
import com.zrgj.pojo.OrderSetting;
import com.zrgj.service.OrderSettingService;
import com.zrgj.util.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

//文件上传设置
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

   //远程调用业务层
    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result  upload(@RequestParam("excelFile")MultipartFile excelFile){
        try {
            //使用POI读取表格数据
            List<String[]> list=POIUtils.readExcel(excelFile);
            if(list!=null &&list.size()>0){
                 List<OrderSetting> orderSettings=new ArrayList<>();
                 for(String[] strings:list){

                     //先创建对象封装数据
                     OrderSetting orderSetting  =new OrderSetting(new Date(strings[0]),Integer.parseInt(strings[1]));
                     orderSettings.add(orderSetting);
                 }
                orderSettingService.add(orderSettings);

            }
        } catch (IOException e) {
            e.printStackTrace();
            //添加失败
            return  new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

        //添加成功
        return  new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    //根据页面日期查询日历数据
    @RequestMapping("/getOrderSettingByMonth")
    public  Result getOrderSettingByMonth(String date){
        //参数格式2022-2-12

        try {
            List<Map> list=orderSettingService.getOrderSettingByMonth(date);

            return  new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);

        } catch (Exception e) {
            e.printStackTrace();

            return  new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }


    }

    //修改预约设置
    @RequestMapping("/editNumberByDate")
    public  Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        System.out.println(orderSetting);
        try {
            orderSettingService.editNumberByDate(orderSetting);
            //修改成功
            return  new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            //失败
            return  new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }

    }



}
