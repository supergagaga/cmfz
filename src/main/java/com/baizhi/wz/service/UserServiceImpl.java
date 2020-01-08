package com.baizhi.wz.service;

import com.baizhi.wz.dao.UserDao;
import com.baizhi.wz.entity.MapDto;
import com.baizhi.wz.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public void insertUser(User user) {
        userDao.insert(user);
    }

    @Override
    public void deleteUser(String[] id) {
        userDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public void UpdateUser(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryByPage(int currentPage, int pageSize) {
        HashMap hashMap=new HashMap();
        int count=userDao.selectCount(null);
        hashMap.put("page",currentPage);
        hashMap.put("records",count);
        int total=count%pageSize==0?count/pageSize:count/pageSize+1;
        hashMap.put("total",total);
        List<User> rows=userDao.selectByRowBounds(null,new RowBounds((currentPage-1)*pageSize,pageSize));
        hashMap.put("rows",rows);
        return hashMap;
    }

    @Override
    public Integer queryBySexAndDay(String sex, Integer day) {
        Integer count=userDao.queryBySexAndDay(sex,day);
        return count;
    }

    @Override
    public List<MapDto> queryBySexGetLocation(String sex) {
        List<MapDto> mapDtos=userDao.queryBySexGetLocation(sex);
        return mapDtos;
    }

    @Override
    public Map login(String phone, String password) {
        User user=new User();
        user.setPhone(phone);
        User user1=userDao.selectOne(user);
        HashMap hashMap=new HashMap();
        if(user1==null){
            hashMap.put("status",-200);
            hashMap.put("message","用户名不存在");
        }else if(user1.getPassword().equals(password)){
            hashMap.put("status",200);
            hashMap.put("user",user1);
        }else{
            hashMap.put("status",-200);
            hashMap.put("message","密码错误");
        }
        return hashMap;
    }
}
