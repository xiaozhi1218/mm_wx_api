package com.itheima.mm.service;

import com.itheima.mm.dao.MemberDao;
import com.itheima.mm.pojo.WxMember;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * 包名:com.itheima.mm.service
 *
 * @author Leevi
 * 日期2020-11-05  14:30
 */
public class MemberService {

    public WxMember findByNickname(String nickName) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
        WxMember wxMember = memberDao.findByNickname(nickName);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return wxMember;
    }

    public void addMember(WxMember wxMember) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
        memberDao.addMember(wxMember);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
    }
}
