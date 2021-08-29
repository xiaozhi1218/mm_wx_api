package com.itheima.mm.controller;

import com.itheima.framework.Controller;
import com.itheima.framework.RequestMapping;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.service.CourseService;
import com.itheima.mm.service.DictService;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 包名:com.itheima.mm.controller
 *
 * @author Leevi
 * 日期2020-11-05  10:08
 */
@Controller
public class CommonsController {
    private DictService dictService = new DictService();
    private CourseService courseService = new CourseService();
    @RequestMapping(value = "/common/citys")
    public void citys(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1. 获取请求参数
            Map parameterMap = JsonUtils.parseJSON2Object(request, Map.class);

            //2.调用业务层的方法查询城市信息
            Map resultMap = dictService.findCityList(parameterMap);

            JsonUtils.printResult(response,new Result(true,"查询城市信息成功",resultMap));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"查询城市信息失败"));
        }
    }
    @RequestMapping("/common/courseList")
    public void courseList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1. 调用业务层的方法，查询学科列表
            List<Course> courseList = courseService.findCourseList();
            JsonUtils.printResult(response,new Result(true,"查询学科列表成功",courseList));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"查询学科列表失败"));
        }

    }
}
