package com.itheima.mm.dao;

import com.itheima.mm.pojo.Question;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.itheima.mm.dao
 *
 * @author Leevi
 * 日期2020-11-07  09:19
 */
public interface QuestionDao {
    List<Question> findQuestionListByCategoryId(Map parameterMap);
}
