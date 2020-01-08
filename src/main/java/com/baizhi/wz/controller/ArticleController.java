package com.baizhi.wz.controller;

import com.baizhi.wz.entity.Article;
import com.baizhi.wz.service.ArticleService;
import com.baizhi.wz.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/queryByPage")
    public Map queryByPage(Integer page, Integer rows){
        Map map=articleService.queryByPage(page,rows);
        return map;
    }

    @RequestMapping("/uploadImg")
    public Map uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request){
        HashMap hashMap=new HashMap();
        String realPath=session.getServletContext().getRealPath("/upload/articleImg/");
        File file=new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        try{
            String http= HttpUtil.getHttp(imgFile,request,"/upload/articleImg/");
            hashMap.put("error",0);
            hashMap.put("url",http);
        }catch(Exception e){
            hashMap.put("error",1);
            e.printStackTrace();
        }
        return hashMap;
    }

    @RequestMapping("/showAllImg")
    public Map showAllImg(HttpServletRequest request,HttpSession session){
        HashMap hashMap = new HashMap();
        hashMap.put("current_url",request.getContextPath()+"/upload/articleImg/");
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        hashMap.put("total_count",files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("filetype",extension);
            fileMap.put("filename",name);
            // 通过字符串拆分获取时间戳
            String time = name.split("_")[0];
            // 创建SimpleDateFormat对象 指定yyyy-MM-dd hh:mm:ss 样式
            //  simpleDateFormat.format() 获取指定样式的字符串(yyyy-MM-dd hh:mm:ss)
            // format(参数)  参数:时间类型   new Date(long类型指定时间)long类型  现有数据:字符串类型时间戳
            // 需要将String类型 转换为Long类型 Long.valueOf(str);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        return hashMap;
    }

    @RequestMapping("insertArticle")
    public Map insertArticle(HttpServletRequest request,Article article, MultipartFile inputfile,HttpSession session){
        HashMap hashMap = new HashMap();
//        String realPath=session.getServletContext().getRealPath("/upload/articleImg/");
//        File file=new File(realPath);
//        if(!file.exists()){
//            file.mkdirs();
//        }


        // {id="",}
        if (article.getId()==null||"".equals(article.getId())){
            // insert
            String id = UUID.randomUUID().toString();
            String http = HttpUtil.getHttp(inputfile, request, "/upload/articleImg/");
            article.setId(id);
            article.setImg(http);
            article.setCreateDate(new Date());
            article.setPublishDate(new Date());
            articleService.insertArticle(article);
        }else{
            // update
            article.setImg(null);
            articleService.updateArticle(article);
        }
        hashMap.put("status",200);
        return hashMap;
    }

    @RequestMapping("deleteArticle")
    public String deleteArticle(String[] id){
        articleService.removeArticle(id);
        return "redirect:/article/queryByPage";
    }
}
