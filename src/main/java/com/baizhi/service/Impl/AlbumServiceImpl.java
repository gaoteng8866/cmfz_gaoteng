package com.baizhi.service.Impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Star;
import com.baizhi.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    //注入dao层
    @Autowired
    private AlbumDao albumDao;
    //注入明星的dao层
    @Autowired
    private StarDao starDao;
    //查看所有
    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        //1.创建一个对象
        Album album = new Album();
        //2.每页展示的条数
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        //3.查到数据将对象的各个属性和每页展示条数放进list集合内
        List<Album> list = albumDao.selectByRowBounds(album, rowBounds);
        //利用for循环找到对应的明星昵称，回显到专辑
        for (Album a : list) {
            Star star = starDao.selectByPrimaryKey(a.getStarId());
            //再把获得昵称存进去
            a.setStar(star);
        }
        //4.调用dao层方法，查询数据总数
        int count = albumDao.selectCount(album);
        //5.创建一个map集合。把数据存进去
        Map<String,Object>map = new HashMap<>();
        //起始条数
        map.put("page",page);
        //每页展示的条数
        map.put("rows",list);
        //总页数
        map.put("total",count%rows==0?count/rows:count/rows+1);
        //总数据
        map.put("records",count);
        return map;
    }
    //添加
    @Override
    public String add(Album album) {
        System.out.println("进入添加方法");
        //1.生成一个UUID
        album.setId(UUID.randomUUID().toString());
        //2.生成一个专辑数，初始化为0
        album.setCount(0);
        //3.添加时间
        album.setCreateDate(new Date());
        //4.调用dao层方法插入
        int i = albumDao.insertSelective(album);
        //5.判断成功或者失败
        if (i==0){
            throw new RuntimeException("添加专辑失败");
        }
        return album.getId();
    }
   //修改图片地址
    @Override
    public void edit(Album album) {
        if("".equals(album.getCover())){
            album.setCover(null);
        }
        //修改上传文件的地址，改为自己写的路径地址
        int i = albumDao.updateByPrimaryKeySelective(album);
        //判断
        if (i==0){
            throw new RuntimeException("修改专辑失败");
        }
    }
    //为章节根据主键查一个，修改专辑中的数量，上传成功+1
    @Override
    public Album selectOne(String id) {
        Album album = albumDao.selectByPrimaryKey(id);
        return album;
    }

}
