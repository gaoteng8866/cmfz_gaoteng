package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;
@Data
@Accessors(chain = true)
public class Banner {
    @Id
    private String id;
    private String name;
    private String cover;
    private String description;
    private String status;
    //序列化
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createDate;

}
