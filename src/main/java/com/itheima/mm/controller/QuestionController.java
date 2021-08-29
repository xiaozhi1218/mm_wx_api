package com.itheima.mm.controller;

import com.itheima.framework.Controller;
import com.itheima.framework.RequestMapping;
import com.itheima.mm.entity.Result;
import com.itheima.mm.service.QuestionService;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 包名:com.itheima.mm.controller
 *
 * @author Leevi
 * 日期2020-11-08  08:49
 */
@Controller
public class QuestionController {
    private QuestionService questionService = new QuestionService();
    @RequestMapping("/question/submit")
    public void submit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //0. 从请求头中获取当前用户的id
            String authorization = request.getHeader("Authorization");
            Integer id = Integer.valueOf(authorization.substring(7));
            //1. 获取请求参数
            Map parameterMap = JsonUtils.parseJSON2Object(request, Map.class);

            //将用户的id存储到parameterMap中
            parameterMap.put("memberId",id);
            //2. 调用业务层的方法，保存答题信息
            questionService.saveQuestionSubmit(parameterMap);
            JsonUtils.printResult(response,new Result(true,"答案提交成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"答案提交失败"));
        }

    }
}
