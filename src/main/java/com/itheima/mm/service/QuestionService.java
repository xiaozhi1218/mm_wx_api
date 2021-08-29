package com.itheima.mm.service;

import com.alibaba.fastjson.JSONArray;
import com.itheima.mm.dao.MemberDao;
import com.itheima.mm.pojo.WxMember;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Arrays;
import java.util.Map;

/**
 * 包名:com.itheima.mm.service
 *
 * @author Leevi
 * 日期2020-11-08  08:51
 */
public class QuestionService {
    public void saveQuestionSubmit(Map parameterMap) throws Exception {
        SqlSession sqlSession = null;
        try {
            //1. 处理tag
            boolean answerIsRight = (boolean) parameterMap.get("answerIsRight");
            Integer tag = null;
            //1.1 判断是简答题还是选择题: 判断answerResult是否为null
            if (parameterMap.get("answerResult") == null) {
                //简答题
                if (answerIsRight){
                    tag = 2;
                }else {
                    tag = 3;
                }
            }else {
                //选择题
                if (answerIsRight){
                    tag = 0;
                }else {
                    tag = 1;
                }
                //单独处理answerResult，将其转换成一个字符串
                JSONArray jsonArray = (JSONArray) parameterMap.get("answerResult");
                //将JsonArray转换成字符串
                Object[] strArray = jsonArray.toArray();
                //将strArray转成字符串
                String answerResult = Arrays.toString(strArray);
                parameterMap.put("answerResult",answerResult);
            }
            parameterMap.put("tag",tag);

            //2. 判断该用户之前是否做过这道题
            sqlSession = SqlSessionFactoryUtils.openSqlSession();
            MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
            Map resultMap = memberDao.findByMemberIdAndQuestionId(parameterMap);
            if (resultMap == null) {
                //说明用户之前没有做过这道题
                //则添加数据
                memberDao.addMemberQuestion(parameterMap);
            }else {
                //说明用户之前做过这道题
                //则修改数据
                memberDao.updateMemberQuestion(parameterMap);
            }

            //3. 修改t_wx_member表的信息
            //3.1 根据用户的id获取wxMember
            WxMember wxMember = memberDao.findById((int) parameterMap.get("memberId"));
            wxMember.setLastCategoryType(Integer.parseInt(parameterMap.get("categoryType") + ""));
            wxMember.setLastCategoryKind(Integer.parseInt(parameterMap.get("categoryKind") + ""));
            wxMember.setLastCategoryId(Integer.parseInt(parameterMap.get("categoryID") + ""));
            wxMember.setLastQuestionId(Integer.parseInt((Integer) parameterMap.get("id") + ""));
            memberDao.updateWxMember(wxMember);
            SqlSessionFactoryUtils.commitAndClose(sqlSession);
        } catch (Exception e) {
            e.printStackTrace();
            SqlSessionFactoryUtils.rollbackAndClose(sqlSession);
            throw e;
        }
    }
}
