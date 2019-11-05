package com.baizhi.service.Impl;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    //注入dao
    @Autowired
    private UserDao userDao;
    @Override
    public Map<String, Object> selectUsersByStarId(Integer page, Integer rows, String starId) {
        //1.new一个对象
        User user = new User();
        //2.根据明星的id，找到对应的id,存进去
        user.setStarId(starId);
        //3.一行呈现的数据
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        //调用dao层方法,查询
        List<User> list = userDao.selectByRowBounds(user, rowBounds);
        System.out.println(list);
        //查询数据库一共多少条数据
        int count = userDao.selectCount(user);
        //创建一个map集合，利用key，拿到值
        Map<String, Object> map = new HashMap<>();
        //起始条数
        map.put("page",page);
        //一行呈现多少数据
        map.put("rows",list);
        //一共有几页,实现页数
        map.put("total",count%rows==0?count/rows:count/rows+1);
        //一共多少数据
        map.put("records",count);
        return map;
    }

    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        //建立一个对象
        User user = new User();
        //查询数据库 page起始条数，rows每页展示的条数
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        //用集合的形式把一行展示的数据存进去
        List<User> list = userDao.selectByRowBounds(user, rowBounds);
        //求出一共多少数据
        int count = userDao.selectCount(user);
        //创建一个map集合把数据存进去
        Map<String, Object> map = new HashMap<>();
        //起始条数
        map.put("page",page);
        //一行展示的数据
        map.put("rows",list);
        //总共的页数
        map.put("total",count%rows==0?count/rows:count/rows+1);
        //总共的数据
        map.put("records",count);
        return map;
    }
    //导出数据
    @Override
    public List<User> export(HttpServletRequest request) {
        //因图片在前台存的是.jpg格式，需要获取它所在的路径
        List<User> users = userDao.selectAll();
        //开始遍历找到对应的图片，然后
        users.forEach(li->
                {
                    String path = request.getSession().getServletContext().getRealPath("/star/img");
                    //再把找到的图片存入
                    li.setPhoto(path+"/"+li.getPhoto());
                }
        );
        return users;
    }
    //折线图
    public Integer[] FindMouthBySex(String sex){
        //使用map集合去以键对值的形式
        Map<String ,Integer>map = new HashMap<>();
        //设置一个数组的长度
        Integer[] count= new Integer[12];
        //Example，例子里的属性
        Example example = new Example(User.class);
        //此方法构建一个实例
        Example.Criteria criteria = example.createCriteria();
        //andEqualTo 对比数据
        criteria.andEqualTo("sex",sex);
        List<User> users = userDao.selectByExample(example);
        //有用户进入该方法
        //用户数量计数器开始遍历
        users.forEach(u -> {
            //得到的值是以数组形式，下标从0开始
            int month = u.getCreateDate().getMonth()+1;
            System.out.println(month+"月");
            //用map集合建立时所有月份时，所有月份内的数据都是空的，发现第一个用户时就存为1
            if( map.get(month+"月") == null){
                //遍历注册用户所在的月份，有用户则为1
                System.out.println(month+"月1人");
                map.put(month+"月",1);
            }else{
                //如果这个月份已经有用户数据，则在该月份用户的数量上加1
                int i = map.get(month + "月") ;
                System.out.println(month+"月"+i+"人");
                map.put(month+"月",i+1);
            }
        });
       ///以数组下标的形式开始取参
        for (int i =0 ;i<12;i++){
            if(map.get((i+1)+"月")==null){
                //求用户的数量
                count[i]=0;
            }else {
                Integer integer = map.get((i+1) + "月");
                count[i]=integer;
            }

        }
        //第二种方法，需要制定固定的月份 例  Integer i[] = {1,2,3,6}
        /*for (int i =0;i<count.length;i++){
            count[i]=map.get(i+"月");
        }*/
        return count;
    }

}
