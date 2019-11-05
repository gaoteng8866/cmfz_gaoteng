package com.baizhi.service.Impl;

import com.baizhi.dao.StarDao;
import com.baizhi.entity.Star;
import com.baizhi.entity.User;
import com.baizhi.service.StarService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class StarServiceImpl implements StarService {
    //注入dao
    @Autowired
    private StarDao starDao;
    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        //1.new一个对象
        Star star = new Star();
        //2.一行呈现多少数据
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //3.把数据库中的数据按照几条展示在一页中，存进list集合中
        List<Star> list = starDao.selectByRowBounds(star, rowBounds);
        System.out.println(list);
        //4.查询一共多少条数据
        int count = starDao.selectCount(star);
        //5.把数据放到Map集合里
        Map<String, Object> map = new HashMap<>();
        //起始条数
        map.put("page",page);
        //一行呈现出多少条数据存进map
        map.put("rows",list);
        //一共有几页，total,解释代码如果count除以rows==0，则count除以rows，“：”代表如果不等于，则count除以条数再加一
        map.put("total",count%rows==0?count/rows:count/rows+1);
        //呈现总共多少数据
        map.put("records",count);
        return map;
    }
   //添加
    @Override
    public String add(Star star) {
        //1.生成一个id
        star.setId(UUID.randomUUID().toString());
        //2.插入
        int i = starDao.insertSelective(star);
        //进行判断
        if (i==0){
            throw new RuntimeException("插入失败");
        }
        return star.getId();
    }
    //修改图片地址
    @Override
    public void edit(Star star) {
        //修改上传文件的地址，改为自己写的路径地址
        int i = starDao.updateByPrimaryKeySelective(star);
        if (i==0){
        throw new RuntimeException("修改失败");
        }
    }
    //为专辑中获取明星的名字
    @Override
    public List<Star> getAllStarForSelect() {
        List<Star> list = starDao.selectAll();
        return list;
    }

}
