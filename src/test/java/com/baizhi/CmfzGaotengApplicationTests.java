package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CmfzGaotengApplicationTests {
    //1.注入dao
    @Autowired
    private AdminDao adminDao;

    @Test
    void contextLoads() {
        List<Admin> admins = adminDao.selectAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

}
