package com.itheima.mm.service;

import com.itheima.mm.dao.CatalogDao;
import com.itheima.mm.dao.CompanyDao;
import com.itheima.mm.dao.MemberDao;
import com.itheima.mm.pojo.Catalog;
import com.itheima.mm.pojo.Company;
import com.itheima.mm.pojo.WxMember;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

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

    public void updateWxMember(WxMember wxMember) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
        memberDao.updateWxMember(wxMember);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
    }

    public WxMember findById(Integer id) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
        WxMember wxMember = memberDao.findById(id);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return wxMember;
    }

    public Map findMemberCenter(Integer id) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
        Map resultMap = memberDao.findMemberCenter(id);

        //还缺少一个categoryTitle
        Integer categoryKind = (Integer) resultMap.get("categoryKind");
        Integer categoryId = (Integer) resultMap.get("categoryId");
        if (categoryKind != null) {
            if (categoryKind == 1) {
                //按照二级目录查询
                CatalogDao catalogDao = sqlSession.getMapper(CatalogDao.class);
                Catalog catalog = catalogDao.findById(categoryId);
                resultMap.put("categoryTitle",catalog.getName());
            }else {
                //按照企业查询
                CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
                Company company = companyDao.findById(categoryId);
                resultMap.put("categoryTitle",company.getShortName());
            }
        }

        //重新封装响应结果
        Map returnMap = new HashMap();
        returnMap.put("answerCount",resultMap.get("answerCount"));
        Map lastAnswerMap = new HashMap();
        lastAnswerMap.put("categoryID",resultMap.get("categoryId"));
        lastAnswerMap.put("categoryTitle",resultMap.get("categoryTitle"));
        lastAnswerMap.put("categoryKind",resultMap.get("categoryKind"));
        lastAnswerMap.put("categoryType",resultMap.get("categoryType"));
        lastAnswerMap.put("lastQuestionId",resultMap.get("lastQuestionId"));
        returnMap.put("lastAnswer",lastAnswerMap);

        Map categoryMap = new HashMap();
        categoryMap.put("cityID",resultMap.get("cityID"));
        categoryMap.put("cityName",resultMap.get("cityName"));
        categoryMap.put("subjectID",resultMap.get("subjectID"));
        returnMap.put("category",categoryMap);

        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return returnMap;
    }
}
