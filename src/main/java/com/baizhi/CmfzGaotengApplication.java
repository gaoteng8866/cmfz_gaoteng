package com.baizhi;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import tk.mybatis.spring.annotation.MapperScan;
//@SpringBootApplication   等价于
//@Configuration           项目启动时自动配置spring 和 springmvc 初始搭建
//@EnableAutoConfiguration 自动与项目中集成的第三方技术进行集成
// @ComponentScan		   扫描入口类所在子包以及子包后代包中注解
@SpringBootApplication
//开始进行dao扫描
@MapperScan("com.baizhi.dao")
public class CmfzGaotengApplication {
    //启动springboot应用的项目
    public static void main(String[] args) {
        SpringApplication.run(CmfzGaotengApplication.class, args);
    }
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // 1. 需要定义一个converter转换消息的对象
        FastJsonHttpMessageConverter fasHttpMessageConverter = new FastJsonHttpMessageConverter();
        // 2. 添加fastjson的配置信息，比如:是否需要格式化返回的json的数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        // 3. 在converter中添加配置信息
        fasHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fasHttpMessageConverter;
        return new HttpMessageConverters(converter);
    }


}

