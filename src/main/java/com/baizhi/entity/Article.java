package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Article {
    @Id
    private String id;
    private String title;//标题
    private String author;//作者
    private String brief;//摘要
    private String content;//内容
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;//创建时间
}
