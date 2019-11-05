package com.baizhi.service.Impl;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    //注入dao
    @Autowired
    private ChapterDao chapterDao;
    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows,String albumId) {
       //1.new出一个对象
        Chapter chapter = new Chapter();
        //2.根据专辑的id找到对应的章节数
        chapter.setAlbumId(albumId);
        //3.查询数据库一行的数据、
        RowBounds rowBounds = new RowBounds((page-1) * rows, rows);
        //4.调用方法存进去
        List<Chapter> list = chapterDao.selectByRowBounds(chapter, rowBounds);
        //5.查询数据库一共多少条数据
        int count = chapterDao.selectCount(chapter);
        //6.建立map集合把数据存进去
        Map<String, Object> map = new HashMap<>();
        //起始条数
        map.put("page",page);
        //一行内呈现的数据
        map.put("rows",list);
        //总共的页数
        map.put("total",count%rows==0?count/rows:count/rows+1);
        //总共的数据
        map.put("records",count);
        return map;
    }
     //添加完章节
    @Override
    public String add(Chapter chapter) {
        //1.生成UUID
        chapter.setId(UUID.randomUUID().toString());
        //2.时间生成
        chapter.setCreateDate(new Date());
        //3.调用方法
        int i = chapterDao.insertSelective(chapter);
        //4.进行判断
        if (i==0){
            throw  new RuntimeException("添加章节失败");
        }
        return chapter.getId();
    }
    //修改章节，从0变为1
    @Override
    public void edit(Chapter chapter) {
        //1.进行添加
        int i = chapterDao.updateByPrimaryKeySelective(chapter);
        //2.判断
        if (i==0){
            throw new RuntimeException("修改章节失败");
        }

    }
}
