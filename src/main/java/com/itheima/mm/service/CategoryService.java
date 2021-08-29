package com.itheima.mm.service;

import com.itheima.mm.dao.CatalogDao;
import com.itheima.mm.dao.CompanyDao;
import com.itheima.mm.dao.QuestionDao;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 包名:com.itheima.mm.service
 *
 * @author Leevi
 * 日期2020-11-07  09:17
 */
public class CategoryService {

    public List<Map> findCategoryList(Map parameterMap) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        //进行判断categoryType的值
        String categoryType = parameterMap.get("categoryType") + "";
        List<Map> resultList = null;
        if (categoryType.equals("101")) {
            //这是刷题
            //判断到底是按照学科二级目录查询还是按照企业查询
            String categoryKind = parameterMap.get("categoryKind") + "";
            if (categoryKind.equals("1")) {
                //按照二级目录查询
                CatalogDao catalogDao = sqlSession.getMapper(CatalogDao.class);
                //获取当前学科的所有二级目录，以及每个二级目录的总题目数、已完成题目数
                resultList = catalogDao.findCatalogList(parameterMap);
            }else if (categoryKind.equals("2")){
                //按照企业查询
                CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
                //获取当前城市的所有企业，以及每一个企业的总题目数、已完成题目数
                resultList = companyDao.findCompanyList(parameterMap);
            }else {
                //按照方向查询（不做实现）
            }
        }
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return resultList;
    }

    public Map findQuestionList(Map parameterMap) throws IOException {
        String categoryType = parameterMap.get("categoryType") + "";
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        Map resultMap = null;
        if (categoryType.equals("101")) {
            //刷题
            //1. 查询当前二级目录/企业的信息
            CatalogDao catalogDao = sqlSession.getMapper(CatalogDao.class);
            resultMap = catalogDao.findDetail(parameterMap);

            //2. 查询当前二级目录或者当前企业的所有题目
            QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);
            List<Question> questionList = questionDao.findQuestionListByCategoryId(parameterMap);
            resultMap.put("items",questionList);
        }
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return resultMap;
    }
}
