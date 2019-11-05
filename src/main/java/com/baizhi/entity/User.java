package com.baizhi.entity;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;
@Data
@Accessors(chain = true)
public class User {
    // @Excel 此注解是easypoi，用于导出表格用
    @Id
    @Excel(name = "编号")
    private String id;
    @Excel(name = "用户名")
    private String username;
    private String password;
    private String salt;//盐
    @Excel(name = "昵称")
    private String nickname;//昵称
    @Excel(name = "电话")
    private String phone;//电话
    @Excel(name = "省份")
    private String province;//省
    @Excel(name = "城市")
    private String city;//城市
    @Excel(name = "签名")
    private String sign;//签名
    @Excel(name = "照片",type=2)
    private String photo;//照片
    @Excel(name = "性别")
    private String sex;//性别
    //时间序列化
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //后边加上format指定输出到表格的格式
    @Excel(name = "创建时间",format = "yyyy-MM-dd")
    private Date createDate;//创建时间
    private String starId;//明星Id
}
