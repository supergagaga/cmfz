package com.baizhi.wz.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.wz.entity.Banner;
import com.baizhi.wz.entity.BannerDataListener;
import com.baizhi.wz.entity.BannerDto;
import com.baizhi.wz.service.BannerService;
import com.baizhi.wz.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;


    @RequestMapping("/queryAll")
    public List<Banner> queryAll(){
        List<Banner> banners=bannerService.queryAll();
        return banners;
    }
    @RequestMapping("/queryOne")
    public Banner queryOne(String id){
        Banner banner=bannerService.queryOne(id);
        return banner;
    }


    @RequestMapping("/save")
    @ResponseBody
    public Map save(Banner banner,String oper,String[] id){
        HashMap hashMap = new HashMap();
        if(oper.equals("add")) {
            String bannerId = UUID.randomUUID().toString();
            hashMap.put("bannerId",bannerId);
            hashMap.put("status",200);
            banner.setId(bannerId);
            bannerService.add(banner);
        }else if(oper.equals("edit")){
            hashMap.put("bannerId",banner.getId());
            banner.setUrl(null);
            bannerService.update(banner);
        }else{
            bannerService.delete(id);
        }
        return hashMap;
    }
//第一个参数 当前页 第二个参数 当前页有多少行
    @RequestMapping("/queryByPage")
    @ResponseBody
    public BannerDto queryByPage(int page,int rows){

        BannerDto bannerDto = bannerService.queryByPage(page, rows);

        return  bannerDto;
    }
    @RequestMapping("/uploadBanner")
    public Map uploadBanner(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request){
        Map map = bannerService.uploadBanner(url, bannerId, session, request);
        return map;
    }

    @RequestMapping("/exportBanner")
    public void exportBanner(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        List<Banner> banners = bannerService.queryAll();
        for (Banner banner : banners) {
            String[] split = banner.getUrl().split("/");
            String url = split[split.length-1];
            banner.setUrl(url);
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + new Date().getTime()+".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        EasyExcel.write(outputStream,Banner.class).sheet("banner").doWrite(banners);
    }

    @RequestMapping("/importBanner")
    public Map importBanner(MultipartFile inputBanner,HttpServletRequest request){
        String http= HttpUtil.getHttp(inputBanner,request,"/upload/importBanner/");
        String s=http.split("/")[http.split("/").length-1];
        String realPath=request.getSession().getServletContext().getRealPath("/upload/importBanner/");
        String path=realPath+s;
        EasyExcel.read(path,Banner.class,new BannerDataListener()).sheet().doRead();
        HashMap hashMap=new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }

}