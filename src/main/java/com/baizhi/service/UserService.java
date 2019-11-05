package com.baizhi.service;

import com.baizhi.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {
    //明星管理模块，需要用户分页和对应明星id展示粉丝
    Map<String,Object> selectUsersByStarId(Integer page,Integer rows,String starId);
    //查询所有用户
    Map<String,Object>selectAll(Integer page,Integer rows);
    //导出数据
    List<User>export(HttpServletRequest request);
    //折线图
    Integer[] FindMouthBySex(String sex);
}
