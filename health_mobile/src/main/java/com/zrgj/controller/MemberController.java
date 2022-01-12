package com.zrgj.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.zrgj.constant.MessageConstant;
import com.zrgj.constant.RedisMessageConstant;
import com.zrgj.entity.Result;

import com.zrgj.pojo.Member;
import com.zrgj.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    //手机快速登录
    @RequestMapping("/login")
    public Result  login(@RequestBody Map map, HttpServletResponse response){
     //获取表单的手机号，验证码
        String telephone=(String) map.get("telephone");
        String validateCode=(String) map.get("validateCode");
        String codeRedis=jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_LOGIN);
        //校验手机验证码
        if(codeRedis==null || !codeRedis.equals(validateCode)){
            //验证不通过
            return  new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }else{
            //验证码输入正确
            //根据当前的手机号查询会员，判断当前手机号是否注册了
            Member member =memberService.findByTelephone(telephone);
            if(member==null){
                //当前手机号没有注册
                member=new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }

            //已经注册，直接登录，使用cookie储存当前账号
            Cookie cookie=new Cookie("login_member_telephone",telephone);
            //设置生命周期
            cookie.setMaxAge(60*60*24*30);//有效期30天
            cookie.setPath("/");//设置cookie的路径
            response.addCookie(cookie);//添加cookie到浏览器

            //把当前账号存到redis缓存
            String jeson=JSON.toJSON(member).toString();//使用jeson把对象转成jeson然后再转字符串
            jedisPool.getResource().setex(telephone,60*30,jeson);

            return  new Result(true,MessageConstant.LOGIN_SUCCESS);

        }






    }
}
