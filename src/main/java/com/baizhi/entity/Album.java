package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Album {
    @Id
    private String id;
    private String name;//专辑名字
    private String cover;//专辑封面
    private Integer count;//章节数
    private String brief;//专辑简介
    //进行序列化
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;//创建时间
    private String starId;//明星的Id，需要根据明星的id，获取所有明星
    private Star star;//根据对象里的属性，获取明星的名字

}
