package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("article")
public class ArticleController {
    //注入Service
    @Autowired
    private ArticleService articleService;
    //查看所有
    @RequestMapping("selectAll")
    public Map<String,Object>selectAll(Integer page,Integer rows){
        Map<String, Object> map = articleService.selectAll(page, rows);
        return map;
    }
    //编辑
    @RequestMapping("add")
    public Map<String,Object>add(Article article){
        System.out.println("!!!");
      Map<String,Object>map = new HashMap<>();
        try {
                //添加方法
                articleService.add(article);
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            //操作失败
            //如果添加失败
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
    //修改
    @RequestMapping("edit")
    public Map<String,Object>edit(Article article){
        System.out.println("1111");
        Map<String,Object>map = new HashMap<>();
        try {
                //修改方法
                articleService.edit(article);

            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            //操作失败
            //如果添加失败
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
    //删除
    @RequestMapping("del")
    public Map<String,Object>del(Article article){
        Map<String,Object>map = new HashMap<>();
        try {
            //删除方法
            articleService.del(article.getId());
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            //操作失败
            //如果添加失败
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }

    // KindEditor 编辑器文件上传的方法
    @RequestMapping("upload")
    public Map<String,Object>upload(MultipartFile articleImg, HttpServletRequest request){
        //  {"error":0,"url":"\/ke4\/attached\/W020091124524510014093.jpg"}  error,代表没有错误为0，有几个错误就显示1或者....
        //  url：图片的路径
        //1.建立一个map集合
        Map<String,Object>map = new HashMap<>();
        //2.图片空间
        File file = new File(request.getServletContext().getRealPath("article/img"),articleImg.getOriginalFilename());
        try {
            //3.把图片移动到相应的file空间
            articleImg.transferTo(file);
            //4.如果没有错误就显示值为0
            map.put("error",0);
            //4.1 写网络路径
            map.put("url","http://localhost:8989/cmfz/article/img/"+articleImg.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            //5.如果显示错误，代表为1
            map.put("error",1);
        }
        return map;
    }
    //图片空间
    @RequestMapping("browse")
    public Map<String,Object>browse(HttpServletRequest request){
        //1.建立一个map集合存进去
        Map<String,Object>map = new HashMap<>();
        //2.图片路径
        File file = new File(request.getServletContext().getRealPath("article/img"));
        //3.文件类型的数组，里面都是图片
        File[] files = file.listFiles();
        //4.用list集合进行有序排列
        List<Object> list = new ArrayList<>();
        //5.进行数组for循环
        for (File img : files) {
            //解析JSON代码后得出的对图片的判断结果
            Map<String, Object> imgObject = new HashMap<>();
            //1.是否是一个文件夹
            imgObject.put("is_dir",false);
            //2.是否是文件
            imgObject.put("has_file",false);
            //3.文件的大小
            imgObject.put("filesize",img.length());
            //4.是否是一个图片
            imgObject.put("is_photo",true);
            //5.文件是什么类型，img.getName获取文件后缀
            imgObject.put("filetype", FilenameUtils.getExtension("img.getName"));
            //6.图片的名字
            imgObject.put("filename",img.getName());
            //7.当前图片上传的时间
            imgObject.put("datetime","2018-06-07 00:36:39");
            //8.将数据判定完毕存放进集合内,开始循环数据找出存档的图片
            list.add(imgObject);
        }
        //把找到的图片存进去
        map.put("file_list",list);
        //合计数 list.size()，包含的次数，分了几部判定
        map.put("total_count",list.size());
        //图片路径
        map.put("current_url","http://localhost:8989/cmfz/article/img/");
        return map;
    }
}
