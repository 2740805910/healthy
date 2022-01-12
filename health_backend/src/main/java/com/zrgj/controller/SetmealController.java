package com.zrgj.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.zrgj.constant.MessageConstant;
import com.zrgj.entity.PageResult;
import com.zrgj.entity.QueryPageBean;
import com.zrgj.entity.Result;
import com.zrgj.pojo.Setmeal;
import com.zrgj.service.SetmealService;
import com.zrgj.util.QiniuUtils;
import com.zrgj.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    //业务层
    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;
    //文件上传



    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile){
        try {
            //获取上传文件的原始文件名称
            String  originalFilename=imgFile.getOriginalFilename();
            int lastIndexOf= originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffer=originalFilename.substring(lastIndexOf-1);
            //随机文件名称UUID
            String fileName =UUID.randomUUID().toString()+suffer;
            //使用七牛云完成文件上传
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //上传成功

            //将上传图片名称存入Redis，基于Redis的Set集合存储
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return   new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            //文件上传失败
            return  new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }

    }

    //添加套餐
    @RequestMapping("/add")
    public  Result  add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {

            setmealService.add(setmeal,checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
           //添加成功
        return  new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = setmealService.queryPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(), queryPageBean.getQueryString()

        );

        return pageResult;
    }



}
