package com.zrgj.service;

import com.zrgj.entity.Result;

import java.util.Map;

public interface OrderService {


    //预约提交
    Result order(Map map) throws Exception;

    public Map findById(Integer id) throws Exception;
}
