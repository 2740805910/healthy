package com.zrgj.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zrgj.dao.MemberDao;
import com.zrgj.pojo.Member;
import com.zrgj.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;


    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {

        if( member.getPassword() !=null){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }


        memberDao.add(member);
    }


    //根据月份统计会员数量
    @Override
    public List<Integer> findBymemberCountByMonth(List<String> month) {
        List<Integer> count=new ArrayList<>();
        for(String m:month){
            m=m +".31";  //2021.1.31
              Integer c=memberDao.findBymemberCountByMonth(m);
              count.add(c);
        }


        return count;
    }
}
