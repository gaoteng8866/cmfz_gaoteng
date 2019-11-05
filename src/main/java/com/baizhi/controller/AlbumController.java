package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
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
@RequestMapping("album")
public class AlbumController {
   //注入Session
    @Autowired
   private AlbumService albumService;
    //查看所有专辑
    @RequestMapping("selectAll")
    public Map<String,Object>selectAll(Integer page,Integer rows){
        //1.调用方法
        Map<String, Object> map = albumService.selectAll(page, rows);
        return map;
    }
    //编辑
    @RequestMapping("edit")
    public Map<String,Object>edit(String oper, Album album){
       //1.建立一个map
        Map<String,Object>map= new HashMap<>();
        try {
            if("add".equals(oper)){
                String id = albumService.add(album);
                map.put("message",id);
            }
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }

        return map;
    }
    //图片上传
    @RequestMapping("upload")
    public Map<String,Object>upload(MultipartFile cover, String id, HttpServletRequest request){
        System.out.println(id+"-----"+cover.toString());
        System.out.println("进入方法");
        //1.建立一个map文件
        Map<String,Object>map=new HashMap<>();

        //2.getRealPath相对路径  getOriginalFilename拿到原来的文件名
        try {
            cover.transferTo(new File(request.getServletContext().getRealPath("/album/img"),cover.getOriginalFilename()));
            //3.修改album对象中cover属性
            Album album = new Album();
            //4.存进图片的id
            album.setId(id);
            //5.获取原文件名
            album.setCover(cover.getOriginalFilename());

            //6.调用方法，把原文件的路径改为自己在项目中的路径
            albumService.edit(album);
            //7.把状态改为true存进map内
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
            return  map;
    }

}
