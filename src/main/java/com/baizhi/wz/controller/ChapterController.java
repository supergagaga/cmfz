package com.baizhi.wz.controller;

import com.baizhi.wz.entity.Album;
import com.baizhi.wz.entity.Chapter;
import com.baizhi.wz.service.AlbumService;
import com.baizhi.wz.service.ChapterService;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;

    @RequestMapping("/queryById")
    public Map queryById(Integer page, Integer rows, String albumId){
        Map map=chapterService.queryById(page,rows,albumId);
        return map;
    }

    @RequestMapping("edit")
    public Map edit(String oper, Chapter chapter, String[] id, String albumId){
        HashMap hashMap=new HashMap();
        if(oper.equals("add")){
            String chapterId= UUID.randomUUID().toString();
            hashMap.put("chapterId",chapterId);
            hashMap.put("status",200);
            Album album=albumService.queryOne(albumId);

            chapter.setId(chapterId);
            chapter.setAlbumId(albumId);
            chapterService.addChapter(chapter,albumId);
            long count =album.getCount()+1;
            album.setCount(count);
        }else if(oper.equals("edit")){
            hashMap.put("chapterId",chapter.getId());
            hashMap.put("status",200);
            chapter.setUrl(null);
            chapterService.updateChapter(chapter);
        }else{
            chapterService.deleteChapter(id);
        }
        return hashMap;
    }

    @RequestMapping("/uploadChapter")
    public Map uploadChapter(MultipartFile url, String chapterId, HttpSession session, HttpServletRequest request) throws ReadOnlyFileException, IOException, TagException, InvalidAudioFrameException, CannotReadException {
        Map map=chapterService.uploadChapter(url,chapterId,session,request);
        return map;
    }
    @RequestMapping("/downloadChapter")
    public void downloadChapter(String url, HttpServletResponse response, HttpSession session) throws IOException {
        chapterService.downloadChapter(url,response,session);
    }

}
