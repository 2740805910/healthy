package com.zrgj.dao;

import com.zrgj.pojo.Permission;

import java.util.Set;

public interface PermissionDao {
    Set<Permission> findRoleId(Integer roleId);
}
