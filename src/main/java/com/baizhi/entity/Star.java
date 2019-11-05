package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;
@Data
@Accessors(chain = true)
public class Star {
    //@Id用来确定找主键用，算通用mapper一个特性
    @Id
    private String id;
    private String nickname;//昵称
    private String realname;//真实姓名
    private String photo;//照片
    private String sex;//性别
    //时间序列化
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bir;
}
