package com.itheima.mm.dao;

import com.itheima.mm.pojo.WxMember;

import java.util.Map;

/**
 * 包名:com.itheima.mm.dao
 *
 * @author Leevi
 * 日期2020-11-05  14:41
 */
public interface MemberDao {
    WxMember findByNickname(String nickName);

    void addMember(WxMember wxMember);

    WxMember findById(Integer id);

    void updateWxMember(WxMember wxMember);

    Map findByMemberIdAndQuestionId(Map parameterMap);

    void addMemberQuestion(Map parameterMap);

    void updateMemberQuestion(Map parameterMap);

    Map findMemberCenter(Integer id);
}
