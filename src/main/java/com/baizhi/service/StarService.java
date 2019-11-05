package com.baizhi.service;

import com.baizhi.entity.Star;
import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface StarService {
        //查询所有明星，分页展示
        Map<String,Object> selectAll(Integer page,Integer rows);
        //添加
        String add(Star star);
        //修改
        void edit(Star star);
        //专辑中获取明星的名字
        List<Star>getAllStarForSelect();
}
