package com.baizhi.service.Impl;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    //注入dao层
    @Autowired
    private ArticleDao articleDao;
    //查看所有
    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        //new一个对象
        Article article = new Article();
        //查询一行的数据
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //调用查询方法
        List<Article> list = articleDao.selectByRowBounds(article, rowBounds);
        //查询总数
        int count = articleDao.selectCount(article);
        //建立一个map集合存储
        Map<String,Object> map = new HashMap<>();
        //起始条数
        map.put("page",page);
        //一行呈现的数据
        map.put("rows",list);
        //总页数
        map.put("total",count%rows==0?count/rows:count/rows+1);
        //总数据
        map.put("records",count);
        return map;
    }
    //添加
    @Override
    public void add(Article article) {
        System.out.println("进入添加方法");
        //添加id
        article.setId(UUID.randomUUID().toString());
        //添加生成时间
        article.setCreateDate(new Date());
        //调用方法插入
        int i = articleDao.insertSelective(article);
        //判断是否为空
        if (i==0){
            throw new RuntimeException("插入失败");
        }
    }
    //修改
    @Override
    public void edit(Article article) {
        //调用方法修改
       articleDao.updateByPrimaryKeySelective(article);

    }
    //删除
    @Override
    public void del(String id) {
        //1.根据id删除
        articleDao.deleteByPrimaryKey(id);
    }
}
