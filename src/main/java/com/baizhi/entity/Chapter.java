package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Chapter {
    @Id
    private String id;
    private String name;//文件名
    private String singer;//歌手
    private String size;//大小
    private String duration;//时长
    //时间序列化
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;//创建时间
    private String albumId;//专辑外键

}
