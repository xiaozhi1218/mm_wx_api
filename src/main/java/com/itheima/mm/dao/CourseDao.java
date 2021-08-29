package com.itheima.mm.dao;

import com.itheima.mm.pojo.Course;

import java.util.List;

/**
 * 包名:com.itheima.mm.dao
 *
 * @author Leevi
 * 日期2020-11-05  11:48
 */
public interface CourseDao {
    List<Course> findList();
}
