package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {
    //查看信息
    Map<String,Object>selectAll(Integer page,Integer rows);
    //添加
    void add(Article article);
    //修改
    void edit(Article article);
    //删除
    void del(String id);
}
