package com.zrgj.service;

import com.zrgj.pojo.Member;

import java.util.List;

public interface MemberService {


    Member findByTelephone(String telephone);

    void add(Member member);

    //根据月份统计会员数量
    List<Integer> findBymemberCountByMonth(List<String> month);
}
