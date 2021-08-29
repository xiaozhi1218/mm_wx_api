package com.itheima.mm.dao;

import com.itheima.mm.pojo.Tag;

import java.util.List;

/**
 * 包名:com.itheima.mm.dao
 *
 * @author Leevi
 * 日期2020-11-07  12:15
 */
public interface TagDao {
    List<Tag> findTagListByQuestionId(int questionId);
}
