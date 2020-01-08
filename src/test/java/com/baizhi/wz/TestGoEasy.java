package com.baizhi.wz;

import com.baizhi.wz.dao.UserDao;
import com.baizhi.wz.entity.MapDto;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGoEasy {
    @Autowired
    UserDao userDao;
    @Test
    public void test(){
        GoEasy goEasy = new GoEasy( "http://hangzhou.goeasy.io", "BC-26723bbc45c047538396794c5d42c167");
        goEasy.publish("cmfz", "Hello, GoEasy!");
    }

    @Test
    public void testMapDto(){
        Integer integer = userDao.queryBySexAndDay("1", 1);
        System.out.println("integer = " + integer);
    }

}
