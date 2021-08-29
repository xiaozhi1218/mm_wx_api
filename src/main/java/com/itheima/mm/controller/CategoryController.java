package com.itheima.mm.controller;

import com.itheima.framework.Controller;
import com.itheima.framework.RequestMapping;
import com.itheima.mm.entity.Result;
import com.itheima.mm.service.CategoryService;
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
 * 日期2020-11-07  09:14
 */
@Controller
public class CategoryController {
    private CategoryService categoryService = new CategoryService();
    @RequestMapping("/category/list")
    public void categoryList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1. 获取请求头，并且从请求头中获取用户的id
            String authorization = request.getHeader("Authorization");
            Integer id = Integer.valueOf(authorization.substring(7));

            //2. 获取请求参数
            Map parameterMap = JsonUtils.parseJSON2Object(request, Map.class);
            //3. 将用户的id存储到parameterMap中
            parameterMap.put("id",id);

            //3. 调用业务层的方法查询数据
            List<Map> resultList = categoryService.findCategoryList(parameterMap);

            JsonUtils.printResult(response,new Result(true,"查询题库分类列表成功",resultList));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"查询题库分类列表失败"));
        }
    }

    @RequestMapping("/question/list")
    public void questionList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1. 获取请求头，并且从请求头中获取用户的id
            String authorization = request.getHeader("Authorization");
            Integer id = Integer.valueOf(authorization.substring(7));
            //2. 获取请求参数
            Map parameterMap = JsonUtils.parseJSON2Object(request, Map.class);
            //3. 将id存储到parameterMap
            parameterMap.put("id",id);

            //4. 调用业务层的方法查询
            Map resultMap = categoryService.findQuestionList(parameterMap);
            JsonUtils.printResult(response,new Result(true,"查询题目列表成功",resultMap));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"查询题目列表失败"));
        }
    }
}
