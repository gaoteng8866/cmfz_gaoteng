package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumService {
    //查看所有，带分页
    Map<String,Object>selectAll(Integer page,Integer rows);
    //添加
    String add(Album album);
    //修改
    void edit(Album album);
    //为章节根据主键查一个，修改专辑中的数量，上传成功+1
    Album selectOne(String id);
}
