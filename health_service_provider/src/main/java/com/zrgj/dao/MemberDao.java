package com.zrgj.dao;

import com.zrgj.pojo.Member;

public interface MemberDao {
    Member findByTelephone(String telephone);

    void add(Member member);

    //根据日期统计会员数量




    //统计运营数据



    Integer findBymemberCountByMonth(String m);

    public Integer findMemberCountByDate(String date);
    public Integer findMemberCountAfterDate(String date);
    public Integer findMemberTotalCount();
}
