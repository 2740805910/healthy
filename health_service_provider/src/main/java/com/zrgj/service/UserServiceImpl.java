package com.zrgj.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zrgj.dao.PermissionDao;
import com.zrgj.dao.RoleDao;
import com.zrgj.dao.UserDao;
import com.zrgj.pojo.Permission;
import com.zrgj.pojo.Role;
import com.zrgj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findByUserName(String username) {

        User user=userDao.findByUserName(username);
        //去数据库查询用户关联的角色
        int userId=user.getId();

        Set<Role>  roles=roleDao.findByUserId(userId);

        for(Role role:roles){
            Integer roleId=role.getId();
            Set<Permission> permissions= permissionDao.findRoleId(roleId);
            role.setPermissions(permissions);
        }
        user.setRoles(roles);

        System.out.println(user);
        return user;
    }
}
