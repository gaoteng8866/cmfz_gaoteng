package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("chapter")
public class ChapterController {
    //注入service
    @Autowired
    private ChapterService chapterService;
    //注入专辑的事务控制
    @Autowired
    private AlbumService albumService;
    //查看所有章节
    @RequestMapping("selectAll")
    public Map<String,Object>selectAll(Integer page,Integer rows,String albumId){
        //1.调用方法
        Map<String, Object> map = chapterService.selectAll(page, rows, albumId);
        return map;
    }
    //添加章节
    @RequestMapping("edit")
    public Map<String,Object> edit(Chapter chapter,String oper){
           Map<String,Object>map = new HashMap<>();
           try {
               //调用添加方法
               if ("add".equals(oper)){
                   String id = chapterService.add(chapter);
                   map.put("status",true);
                   map.put("message",id);
               }
           }catch (Exception e){
               e.printStackTrace();
               map.put("status",false);
               map.put("message",e.getMessage());
           }
           return map;
    }

    //修改章节
    @RequestMapping("upload")
    public Map<String,Object>upload(MultipartFile name,String id, String albumId, HttpServletRequest request) throws EncoderException {
        //1.map集合存进去
        Map<String, Object> map = new HashMap<>();
        //处理文件上传
        try {
            File file = new File(request.getServletContext().getRealPath("album/music"),name.getOriginalFilename());
            name.transferTo(file);
            //1.修改文件名称 new一个对象
            Chapter chapter = new Chapter();
            //存入文件的id
            chapter.setId(id);
            //存入它的名字
            chapter.setName(name.getOriginalFilename());
            //2.文件大小  pom引入了文件大小的依赖
            //2.1 获取大小
            BigDecimal size = new BigDecimal(name.getSize());
            //2.2文件格式
            BigDecimal mod = new BigDecimal(1024);
            //2.3把格式转为MB  Scale 音节，规模
            BigDecimal realSize = size.divide(mod).divide(mod).setScale(2, BigDecimal.ROUND_CEILING);
            chapter.setSize(realSize+"MB");
            //3.时长 new一个编码器
            Encoder encoder = new Encoder();
            //3.1 设置一个持续时间
            long duration = encoder.getInfo(file).getDuration();
            //3.2换算时间
            chapter.setDuration(duration/1000/60+":"+duration/1000%60);
            chapterService.edit(chapter);
            //4修改专辑的数量
            Album album = albumService.selectOne(albumId);
            //4.1它的总数
            album.setCount(album.getCount()+1);
             //4.2调用方法
            albumService.edit(album);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
}
