package com.baizhi.wz.controller;



import com.baizhi.wz.entity.Album;
import com.baizhi.wz.entity.Chapter;
import com.baizhi.wz.service.AlbumService;
import com.baizhi.wz.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/queryByPage")
    public Map queryByPage(Integer page,Integer rows){
        Map map= albumService.queryByPage(page,rows);
        return map;
    }

    @RequestMapping("/edit")
    public Map edit(String oper, Album album,String[] id){
        HashMap hashMap=new HashMap();
        if(oper.equals("add")){
            String albumId= UUID.randomUUID().toString();
            hashMap.put("albumId",albumId);
            System.out.println(albumId);
            hashMap.put("status",200);
            album.setId(albumId);
            albumService.addAlbum(album);

        }else if(oper.equals("edit")){
            hashMap.put("albumId",album.getId());
            hashMap.put("status",200);
            album.setCover(null);
            albumService.updateAlbum(album);

        }else{
            albumService.deleteById(id);
        }
        return hashMap;
    }

    @RequestMapping("/uploadAlbum")
    public Map uploadAlbum(MultipartFile cover,String albumId,HttpSession session,HttpServletRequest request){
        Map map=albumService.uploadAlbum(cover,albumId,session,request);
        return map;
    }
//专辑详情接口
    public Map albumMsg(String uid,String id){
        HashMap hashMap=new HashMap();
        Album album=albumService.queryOne(id);
        return null;

    }


}
