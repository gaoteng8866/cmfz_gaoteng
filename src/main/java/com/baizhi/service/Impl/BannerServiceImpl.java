package com.baizhi.service.Impl;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    //dao层注入
    @Autowired
    private BannerDao bannerDao;
    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        //new一个banner
        Banner banner = new Banner();
        //new一个rowBounds，查询数据库 page起始条数，rows每页展示的条数
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //把数据库中的数据按照几条展示在一页中，存进list集合中
        List<Banner> list = bannerDao.selectByRowBounds(banner,rowBounds);
        //一共多少条数据
        int count = bannerDao.selectCount(banner);
        //创建一个map集合
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        //解释代码如果count除以rows==0，则count除以rows，“：”代表如果不等于，则count除以条数再加一
        map.put("total",count%rows==0?count/rows:count/rows+1);//总共有几页
        map.put("records",count);//总共有多少条数据
        return map;
    }
    //添加
    @Override
    public String add(Banner banner) {
        //1.使用UUID，生成id
        banner.setId(UUID.randomUUID().toString());
        //2.创建一个系统时间
        banner.setCreateDate(new Date());
        //3.使用方法进行数据添加
        int i = bannerDao.insertSelective(banner);
        //4.进行if判断
        if (i==0){
            throw new RuntimeException("添加失败");
        }
        //添加因为是生成的UUID，里面包含图片，为了使图片找到添加的id对应的uplope方法内的图片位置
        return banner.getId();
    }
   //修改
    @Override
    public void edit(Banner banner) {
        //如果图片为空值，则会设置一个空值进行提交
        if ("".equals((banner.getCover()))){
            banner.setCover(null);
        }
        try {
            //修改上传文件的地址，改为自己写的路径地址
            bannerDao.updateByPrimaryKeySelective(banner);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }

    }
    //删除
    @Override
    public void del(String id, HttpServletRequest request) {
        //获取对象的id
        Banner banner = bannerDao.selectByPrimaryKey(id);
        //删除对象id的方法
        int i = bannerDao.deleteByPrimaryKey(id);
        //进行判断
        if (i == 0){
            throw new RuntimeException("删除失败");
        }else{
            //获取对象id所对应的图片
            String cover = banner.getCover();
            //找到它的原文件名下的图片
            File file = new File(request.getServletContext().getRealPath("/banner/img/"), cover);
            //一个布尔类型的进行判断
            boolean b = file.delete();
            //删除失败图片，在控制台打出
            if(b == false){
                throw new RuntimeException("删除轮播图失败");
            }
        }
    }


}
