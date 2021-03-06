package com.zrgj.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.zrgj.constant.MessageConstant;
import com.zrgj.entity.PageResult;
import com.zrgj.entity.QueryPageBean;
import com.zrgj.entity.Result;
import com.zrgj.pojo.CheckItem;
import com.zrgj.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    //编写一个添加方法
   @Reference
   private CheckItemService checkItemService;
   // 新增


   @RequestMapping("/add")
   public Result add(@RequestBody CheckItem checkItem) {
       try {
           //成功
           checkItemService.add(checkItem);
       } catch (Exception e) {
           //失败
           e.printStackTrace();
           return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
       }
       return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
   }

    //分页查询
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")//权限校验
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkItemService.queryPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(), queryPageBean.getQueryString()

        );

        return pageResult;
    }

    //删除

    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    @RequestMapping("/delete")
    public Result delete(Integer id) {


        System.out.println("id:......." + id);
        try {
            //成功了
            checkItemService.delete(id);
        } catch (RuntimeException e) {
            //失败
            return new Result(false, e.getMessage());

        } catch (Exception e) {
            //失败
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);

        }

        //成功了
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

  //IDC查询
    @RequestMapping("/findById")
    public  Result findById( Integer id){

        try {

            //查询成功
            CheckItem checkItem=checkItemService.findById( id);
            return  new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //查询失败

        return  new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);

    }

    //编辑

    @RequestMapping("/edit")
    public  Result edit(@RequestBody CheckItem checkItem){

        try {

            checkItemService.edit(checkItem);
        } catch (Exception e) {
            //修改失败
           return  new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        //修改成功
        return  new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }


   //查询所有
    @RequestMapping("/findAll")
    public  Result findAll(){
        List<CheckItem> checkItems=checkItemService.findAll();
        if(checkItems!=null && checkItems.size()>0){
            return  new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItems);
        }
        return  new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);

    }






}
