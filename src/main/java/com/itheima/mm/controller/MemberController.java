package com.itheima.mm.controller;

import com.itheima.framework.Controller;
import com.itheima.framework.RequestMapping;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.WxMember;
import com.itheima.mm.service.MemberService;
import com.itheima.mm.utils.DateUtils;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 包名:com.itheima.mm.controller
 *
 * @author Leevi
 * 日期2020-11-05  14:26
 */
@Controller
public class MemberController {
    private MemberService memberService = new MemberService();
    @RequestMapping("/member/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1. 获取请求参数
            WxMember wxMember = JsonUtils.parseJSON2Object(request, WxMember.class);

            //2. 调用业务层的方法进行登录,根据昵称查询用户信息
            WxMember loginMember = memberService.findByNickname(wxMember.getNickName());
            //3. 判断当前用户是否注册过
            if (loginMember == null) {
                //用户之前未注册,我们要进行注册，调用业务层的方法，将wxMember的信息存储到数据库中
                //手动设置createTime
                wxMember.setCreateTime(DateUtils.parseDate2String(new Date()));
                //添加的时候可以获取自增长id
                memberService.addMember(wxMember);

                loginMember = wxMember;
            }

            //4. 保存用户的登录状态: 将当前用户的id返回给客户端，客户端以后每次访问我的服务器都将这个id带过来
            Map resultMap = new HashMap<>();
            resultMap.put("token",loginMember.getId());
            resultMap.put("userInfo",loginMember);

            JsonUtils.printResult(response,new Result(true,"登录成功",resultMap));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response,new Result(false,"登录失败"));
        }
    }
}
