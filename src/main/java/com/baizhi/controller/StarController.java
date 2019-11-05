package com.baizhi.controller;

import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("star")
public class StarController {
    //注入Sercice
    @Autowired
    private StarService starService;

    //查看所有明星
    @RequestMapping("selectAll")
    @ResponseBody
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        //调用事务控制方法
        Map<String, Object> map = starService.selectAll(page, rows);
        return map;
    }

    //添加一个明星
    @RequestMapping("edit")
    @ResponseBody
    public Map<String, Object> edit(String oper, Star star) {
        //1.建立一个mapper集合
        Map<String, Object> map = new HashMap<>();
        try {
            if ("add".equals(oper)) {
                //加入添加方法
                String id = starService.add(star);
                map.put("message", id);
            }
            map.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            //如果添加失败
            map.put("status", false);
            map.put("message", e.getMessage());
        }
        return map;
    }
    //文件上传
    @RequestMapping("upload")
    @ResponseBody
    public Map<String,Object>upload(MultipartFile photo, String id, HttpServletRequest request){
        //1.建立一个map集合
        Map<String,Object>map=new HashMap<>();
        //2.文件移动到文件夹 getRealPath相对路径  getOriginalFilename拿到原来的文件名
        try {
            photo.transferTo(new File(request.getServletContext().getRealPath("/star/img"),photo.getOriginalFilename()));
            //修改star 对象中cover属性
            Star star = new Star();
            //存进图片的id，要不没有办法在前台取值
            star.setId(id);
            //获取原文件名
            star.setPhoto(photo.getOriginalFilename());
            //调用修改方法
            System.out.println(star.toString());
            //调用方法，把原文件的路径改为自己在项目中的路径
            starService.edit(star);
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
    //专辑的jsp调用该方法进行下拉列表展示所有明星
    @RequestMapping("getAllStarForSelect")
    public void getAllStarForSelect(HttpServletResponse response) throws IOException {
        List<Star> list = starService.getAllStarForSelect();
        String str = "<select>";
        for (Star star : list) {
            str += "<option value="+star.getId()+">"+star.getNickname()+"</option>";
        }
        str += "</select>";
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(str);
        /*
        <select>
            <option value='1'>张三</opiton>
            <option value='2'>李四</opiton>
            <option value='1'>张三</opiton>
        </select>
         */

    }

}
