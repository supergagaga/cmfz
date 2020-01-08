package com.baizhi.wz.service;

import com.baizhi.wz.dao.AdminDao;
import com.baizhi.wz.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private HttpSession session;
    @Override
    public Map queryOne(String username, String password) {
        HashMap hashMap=new HashMap();
        Admin admin = new Admin();
        admin.setUsername(username);
        Admin admin1 = adminDao.selectOne(admin);
        if(admin1==null){
            hashMap.put("status",400);
            hashMap.put("msg","用户不存在");
        }else if(!admin1.getPassword().equals(password)){
            hashMap.put("status",400);
            hashMap.put("msg","密码错误");
        }else{
            hashMap.put("status",200);
            session.setAttribute("username",admin1);

        }
        return hashMap;
    }
}
