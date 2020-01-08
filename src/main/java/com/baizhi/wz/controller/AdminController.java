package com.baizhi.wz.controller;

import com.baizhi.wz.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;


    @RequestMapping("login")
    @ResponseBody
    public Map login(String username, String password){
        Map map=adminService.queryOne(username,password);
        return map;
    }

    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("username");
        return "jsp/login";
    }
}
