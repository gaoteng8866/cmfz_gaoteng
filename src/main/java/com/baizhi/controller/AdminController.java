package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RequestMapping("admin")
//@RestController: 就是@Controller+@ResponseBody组合，支持RESTful访问方 式，返回结果都是json字符串。
@Controller
public class AdminController {
      //注入Service
      @Autowired
      private AdminService adminService;

      @RequestMapping("login")
      @ResponseBody
      public Map<String,Object>login(Admin admin, String inputCode, HttpServletRequest request){
          //测试有没有进入
          System.out.println("进入到登录方法");
          //1.建立一个map集合
          Map<String, Object> map = new HashMap<>();
          try {
              System.out.println("验证开始");
              adminService.login(admin,inputCode,request);
              System.out.println("成功验证结束");
              map.put("status",true);
          } catch (Exception e) {
              System.out.println(e.getMessage());
              //如果验证不通过，输出错误信息
              map.put("status",false);
              System.out.println(e.getMessage());
              map.put("message",e.getMessage());
          }
             return map;
      }
      //安全退出
     @RequestMapping("execte")
      public String execte(HttpServletRequest request){
          //获取Session对象
          HttpSession s = request.getSession(true);
          //销毁Session
          s.invalidate();
          //请求重定向
           return "redirect:/login/login.jsp";
      }
      //收集验证码
     @RequestMapping("phoneMessage")
       public void phoneMessage(String code,String phone,HttpServletRequest request){
           try {
               //生成随机数验证码
               Random random = new Random();
               for (int i = 1 ; i < 6 ; i++){
                   code += random.nextInt(10);
               }
               adminService.sendMessage(code,phone);
               System.out.println("========"+phone);
               request.getSession().setAttribute("code",code);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
}