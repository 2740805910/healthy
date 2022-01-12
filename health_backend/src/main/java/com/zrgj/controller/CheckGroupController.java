package com.zrgj.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zrgj.constant.MessageConstant;
import com.zrgj.entity.PageResult;
import com.zrgj.entity.QueryPageBean;
import com.zrgj.entity.Result;
import com.zrgj.pojo.CheckGroup;
import com.zrgj.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;



    //查询所有
    @RequestMapping("/findAll")
    public  Result findAll(){
        List<CheckGroup> checkGroups=checkGroupService.findAll();
        if(checkGroups!=null && checkGroups.size()>0){
            return  new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroups);
        }

        return  new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }

    //添加
    @RequestMapping("/add")
    public Result  add(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds){

        try {
            checkGroupService.add(checkGroup,checkItemIds);
        }catch (Exception e ){
            //添加失败
            return  new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }

        //添加成功
        return  new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }


    //分页查询
    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkGroupService.queryPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(), queryPageBean.getQueryString()

        );

        return pageResult;
    }

    //id查询
    @RequestMapping("/findById")
    public  Result findAll(Integer id){

        System.out.println(id);
        try {

            //查询成功
            CheckGroup checkGroup=checkGroupService.findById(id);
            return  new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //查询失败

        return  new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }

    //通过检查组查询关联的检查项
    @RequestMapping("/findCheckItaemIdsByCheckGroupId")
    public  Result findCheckItaemIdsByCheckGroupId( Integer id){

        try {
            //成功
            List<Integer>  checkItemIds=checkGroupService.findCheckItaemIdsByCheckGroupId(id);
            return  new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
        }catch (Exception e){
            //失败
            e.printStackTrace();
            return  new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }


    }

    //修改保存
    @RequestMapping("/edit")
    public  Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds){

        try {
            //修改成功
            checkGroupService.edit(checkGroup,checkItemIds);

        }catch (Exception e){
            //失败
            return  new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return  new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }


}
