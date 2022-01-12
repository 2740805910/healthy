package com.zrgj.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.zrgj.constant.MessageConstant;
import com.zrgj.constant.RedisMessageConstant;
import com.zrgj.entity.Result;
import com.zrgj.pojo.Order;
import com.zrgj.service.OrderService;
import com.zrgj.util.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        //获取表单输入的验证码
         String telephone =(String) map.get("telephone");
         //从缓存中获取验证码

        String validateCode = (String) map.get("validateCode");
        String codeRedis=jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_ORDER);
        //校验手机验证码
        if(codeRedis==null || !codeRedis.equals(validateCode)){
          //验证不通过
            return  new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        Result result=null;

       //验证通过
        //调用预约服务
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result=orderService.order(map);
        }catch (Exception e){
            e.printStackTrace();
            return result;
        }

        if( result.isFlag()){
            //成功了 获取预约时间
            String orderDate=(String) map.get("orderDate");

            try {
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderDate);
            }catch (Exception e){
                e.printStackTrace();

            }


        }

        return  result;

    }


    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Map map = orderService.findById(id);
            //查询预约信息成功
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            //查询预约信息失败
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }


}
