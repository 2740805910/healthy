package com.zrgj.controller;


import com.zrgj.constant.MessageConstant;
import com.zrgj.constant.RedisMessageConstant;
import com.zrgj.entity.Result;
import com.zrgj.util.SMSUtils;
import com.zrgj.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    //不需要业务层
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){

        //获取随机验证码
        Integer code=ValidateCodeUtils.generateValidateCode(4);
        try {
            //通过短信平台发送手机验证码
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println(telephone);
        System.out.println(code);
        //发送成功，我们把验证码存到redis缓存   jedisPool.getResource().setex():设置缓存存活时间
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,5*60,code.toString());
        return  new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);

    }

    @RequestMapping("/send4Login")
    public  Result  send4Login(String telephone){
        //获取随机验证码
        Integer code=ValidateCodeUtils.generateValidateCode(6);

        try {
            //通过短信平台发送手机验证码
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        //发送成功，我们把验证码存到redis缓存   jedisPool.getResource().setex():设置缓存存活时间
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_LOGIN,5*60,code.toString());
        return  new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);

    }



}
