package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("banner")
public class BannerController {
    //注入Service
    @Autowired
    private BannerService bannerService;

    @RequestMapping("selectAll")
    public Map<String,Object>selectAll(Integer page,Integer rows){
        Map<String, Object> map = bannerService.selectAll(page, rows);
        return map;
    }
   @RequestMapping("edit")
    public Map<String,Object> edit(String oper, Banner banner,HttpServletRequest request){
        Map<String,Object> map= new HashMap<>();
        try {
            if ("add".equals(oper)){
                //加入添加方法
                String id = bannerService.add(banner);
                map.put("message",id);
            }
            //修改方法
            if ("edit".equals(oper)){
               bannerService.edit(banner);
            }
            if ("del".equals(oper)){
                //删除对应对象的id 并且用request传递到显示界面
              bannerService.del(banner.getId(),request);
            }
            map.put("status",true);

        } catch (Exception e) {
            e.printStackTrace();
            //如果添加失败
            map.put("status",false);
            map.put("message",e.getMessage());

        }
        return map;
    }
    //文件上传
    @RequestMapping("upload")
    public Map<String,Object>upload(MultipartFile cover, String id, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        try {
            //transferTo 移动到   getRealPath相对路径  getOriginalFilename拿到原来的文件名
            //文件上传
            cover.transferTo(new File(request.getServletContext().getRealPath("/banner/img"),cover.getOriginalFilename()));
            //修banner对象中cover属性
            Banner banner = new Banner();
            //存进图片的id
            banner.setId(id);
            //获取原文件名
            banner.setCover(cover.getOriginalFilename());
            System.out.println(banner);
            //调用修改方法，失败或者成功
            bannerService.edit(banner);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            //失败就为false
            map.put("status",false);
        }
        return map;
    }
}
