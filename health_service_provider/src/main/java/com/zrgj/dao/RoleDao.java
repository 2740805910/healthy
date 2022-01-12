package com.zrgj.dao;

import com.zrgj.pojo.Role;

import java.util.Set;

public interface RoleDao {
    Set<Role> findByUserId(int userId);
}
