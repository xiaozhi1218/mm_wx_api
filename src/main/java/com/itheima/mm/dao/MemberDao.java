package com.itheima.mm.dao;

import com.itheima.mm.pojo.WxMember;

/**
 * 包名:com.itheima.mm.dao
 *
 * @author Leevi
 * 日期2020-11-05  14:41
 */
public interface MemberDao {
    WxMember findByNickname(String nickName);

    void addMember(WxMember wxMember);

}
