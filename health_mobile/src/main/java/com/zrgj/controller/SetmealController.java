package com.zrgj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zrgj.constant.MessageConstant;
import com.zrgj.entity.Result;
import com.zrgj.pojo.Setmeal;
import com.zrgj.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {


    @Reference
    private SetmealService setmealService;
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){


          try{
              List<Setmeal> list =setmealService.findAll();
              return  new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list );
          }catch (Exception e){
              e.printStackTrace();
              return  new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
          }

    }

    //通过套餐ID查询检查组  关联查询检查项
       @RequestMapping("/findById")
    public  Result  findById(Integer id){

        try {
            Setmeal setmeal=setmealService.findByID(id);
            return  new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }


       }


}
