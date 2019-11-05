package com.baizhi.controller;

import com.baizhi.util.SecurityCode;
import com.baizhi.util.SecurityImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RequestMapping("code")
@Controller
public class SecurityController {
    //方法名
    @RequestMapping("getCode")
     public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
         //1.生成验证码字符
        String securityCode = SecurityCode.getSecurityCode();
        //2.控制台打印生成验证码字符
        System.out.println(securityCode);
        //3.获取Session参数，获取Session对象
        HttpSession session = request.getSession();
        //4.设置生成的验证码字符标记
        session.setAttribute("securityCode",securityCode);
        //5.接收生成验证码的字符生成的字符
        BufferedImage image = SecurityImage.createImage(securityCode);
        //6.设置图片响应类型为png结尾
        response.setContentType("image/png");
        //7.设置一个输出流，并且获取Session对象、
        ServletOutputStream outputStream = response.getOutputStream();
        //8.IO用write进行输出
        ImageIO.write(image,"png",outputStream);
    }

}
