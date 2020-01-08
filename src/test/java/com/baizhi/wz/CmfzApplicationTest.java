package com.baizhi.wz;

import com.baizhi.wz.dao.AdminDao;
import com.baizhi.wz.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmfzApplicationTest {

    @Autowired
    AdminDao adminDao;
    @Test
    public void test(){
        List<Admin> admins = adminDao.selectAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }
}
