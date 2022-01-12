package com.zrgj.dao;

import com.zrgj.pojo.User;

public interface UserDao {
    User findByUserName(String username);
}
