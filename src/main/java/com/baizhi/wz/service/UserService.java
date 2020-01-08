package com.baizhi.wz.service;

import com.baizhi.wz.entity.MapDto;
import com.baizhi.wz.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public void insertUser(User user);
    public void deleteUser(String[] id);
    public void UpdateUser(User user);
    public Map queryByPage(int currentPage, int pageSize);
    public Integer queryBySexAndDay(String sex,Integer day);
    public List<MapDto> queryBySexGetLocation(String sex);
    public Map  login(String String,String password);

}
