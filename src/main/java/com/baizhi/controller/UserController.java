package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("user")
@RestController
public class UserController {
    //注入Servier
    @Autowired
    private UserService userService;
    //明星管理模块，用户（粉丝）明星下边对应的粉丝
    @RequestMapping("selectUsersByStarId")
    public Map<String,Object>selectUsersByStarId(Integer page,Integer rows,String starId){
        return userService.selectUsersByStarId(page,rows,starId);
    }
    //用户管理模块，查看所有用户
    @RequestMapping("selectAll")
    public Map<String,Object>selectAll(Integer page,Integer rows){
             return userService.selectAll(page,rows);
    }
    //导出用户数据
    @RequestMapping("export")
    public void  export(HttpServletResponse response, HttpServletRequest request){
        //准备数据
        List<User> list = userService.export(request);
        //利用工具类
        Workbook workbook = ExcelExportUtil.exportBigExcel(new ExportParams("用户数据信息", "用户"),com.baizhi.entity.User.class, list);
        //日期转换
        String fileName = "用户报表("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+").xls";
        //处理中文乱码
        try {
            fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
            //设置 response
            //设置消息类型
            response.setContentType("application/vnd.ms-excel");
            //设置标题,居中
            response.setHeader("content-disposition","attachment;filename="+fileName);
            //读出数据
            workbook.write(response.getOutputStream());
            //关流
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //折线图
    @RequestMapping("graph")
    public Map<String,Object> graph(){
        Map<String, Object> map = new HashMap<>();
        //方法调用，男女设定，传入前台
        Integer[] integers = userService.FindMouthBySex("男");
        map.put("man",integers);
        Integer[] women = userService.FindMouthBySex("女");
        map.put("woman",women);
        return map;
    }
}
