package com.itheima.mm.service;

import com.itheima.mm.dao.CourseDao;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * 包名:com.itheima.mm.service
 *
 * @author Leevi
 * 日期2020-11-05  11:46
 */
public class CourseService {

    public List<Course> findCourseList() throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
        List<Course> courseList = courseDao.findList();

        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return courseList;
    }
}
