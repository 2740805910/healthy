package com.zrgj.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zrgj.pojo.Permission;
import com.zrgj.pojo.Role;
import com.zrgj.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private  UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询数据库，是否存在账号
        User user =userService.findByUserName(username);
          if(user==null){
              return  null;
          }
           //如果有账号,获取账号关联的角色
        List<GrantedAuthority> list=new ArrayList<>();
        Set<Role> roles=user.getRoles();
          for(Role role:roles){
              list.add(new SimpleGrantedAuthority(role.getKeyword()));

              Set<Permission> permissions = role.getPermissions();
              for(Permission permission : permissions){
                  //授权
                  list.add(new SimpleGrantedAuthority(permission.getKeyword()));
              }
          }

        org.springframework.security.core.userdetails.User user1=new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);

        return user1;
    }
}
