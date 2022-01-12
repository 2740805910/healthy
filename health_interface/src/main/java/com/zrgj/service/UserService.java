package com.zrgj.service;

import com.zrgj.pojo.User;

public interface UserService {


    User findByUserName(String username);
}
