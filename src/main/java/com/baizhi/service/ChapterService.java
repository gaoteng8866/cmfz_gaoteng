package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    //查询章节表，带分页
    Map<String,Object>selectAll(Integer page,Integer rows,String albumId);
    //添加章节
    String add(Chapter chapter);
    //修改章节
    void edit(Chapter chapter);
}
