package com.baizhi.service;


import com.baizhi.entity.Admin;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {
   // inputCode 验证码
   void login(Admin admin, String inputCode, HttpServletRequest request );
   //发送短信验证码
   void sendMessage(String code,String phone) throws Exception;
}
