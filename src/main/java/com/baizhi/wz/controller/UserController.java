package com.baizhi.wz.controller;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.wz.entity.User;
import com.baizhi.wz.service.UserService;
import com.baizhi.wz.util.HttpUtil;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/queryByPage")
    public Map queryByPage(int page,int rows){
        Map map=userService.queryByPage(page,rows);
        return  map;
    }

    @RequestMapping("/editUser")
    public Map editUser(String[] id, User user, HttpServletRequest request,String oper){
        HashMap hashMap=new HashMap();
        if (oper.equals("add")){
            String userId= UUID.randomUUID().toString();
            user.setId(userId);
            hashMap.put("status",200);
            hashMap.put("userId",userId);
            userService.insertUser(user);
        }else if(oper.equals("edit")){
            user.setPhoto(null);
            hashMap.put("userId",user.getId());
            userService.UpdateUser(user);
        }else{
            userService.deleteUser(id);
        }
        GoEasy goEasy=new GoEasy("http://rest-hangzhou.goeasy.io", "BC-55d66a57351245cd891a8397a5d42cef");
        Map map=queryBySexAndDay();
        String s= JSONObject.toJSONString(map);
        goEasy.publish("cmfz",s);
        return hashMap;
    }

    @RequestMapping("/upload")
    public Map upload(MultipartFile photo, String userId, HttpServletRequest request, HttpSession session){
        //获取真实路径
        String realPath=session.getServletContext().getRealPath("/upload/userImg");
        //判断文件是否存在
        File file=new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }

        String uri= HttpUtil.getHttp(photo,request,"/upload/userImg/");
        User user=new User ();
        user.setId(userId);
        user.setPhoto(uri);
        userService.UpdateUser(user);
        HashMap hashMap=new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }

    @RequestMapping("/queryBySexAndDay")
    public Map queryBySexAndDay(){
        HashMap hashMap=new HashMap();
        ArrayList manList=new ArrayList();
        manList.add(userService.queryBySexAndDay("0",1));
        manList.add(userService.queryBySexAndDay("0",7));
        manList.add(userService.queryBySexAndDay("0",30));
        manList.add(userService.queryBySexAndDay("0",365));
        ArrayList womenList=new ArrayList();
        womenList.add(userService.queryBySexAndDay("1",1));
        womenList.add(userService.queryBySexAndDay("1",7));
        womenList.add(userService.queryBySexAndDay("1",30));
        womenList.add(userService.queryBySexAndDay("1",365));
        hashMap.put("man",manList);
        hashMap.put("women",womenList);
        return hashMap;
    }
    @RequestMapping("/queryBySexGetLocation")
    public Map queryBySexGetLocation(){
        HashMap hashMap=new HashMap();
        List manList=userService.queryBySexGetLocation("0");
        List womenList=userService.queryBySexGetLocation("1");
        hashMap.put("man",manList);
        hashMap.put("women",womenList);
        return hashMap;
    }

    @RequestMapping("/login")
    public Map login(String phone,String password){
        Map map = userService.login(phone,password);
        return map;
    }
}
