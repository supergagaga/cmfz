package com.baizhi.wz.service;

import com.baizhi.wz.dao.ChapterDao;
import com.baizhi.wz.entity.Chapter;
import com.baizhi.wz.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.RowBounds;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterDao chapterDao;
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public Map queryById(Integer page, Integer rows, String albumId) {

        Chapter chapter=new Chapter();
        chapter.setAlbumId(albumId);
        int count = chapterDao.selectCount(chapter);
        int total=count%rows==0?count/rows:count/rows+1;
        List<Chapter> chapters=chapterDao.selectByRowBounds(chapter,new RowBounds((page-1)*rows,rows));
        HashMap hashMap=new HashMap();
        hashMap.put("records",count);
        hashMap.put("page",page);
        hashMap.put("total",total);
        hashMap.put("rows",chapters);
        return hashMap;
    }

    @Override
    public void addChapter(Chapter chapter,String albumId) {

        chapterDao.insert(chapter);

    }

    @Override
    public void deleteChapter(String[] id) {
        chapterDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public void updateChapter(Chapter chapter) {

        chapterDao.updateByPrimaryKeySelective(chapter);
    }


    @Override
    public Map uploadChapter(MultipartFile url, String chapterId, HttpSession session, HttpServletRequest request) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        HashMap hashMap=new HashMap();
        String realPath=request.getServletContext().getRealPath("/upload/music/");
        File file=new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String http= HttpUtil.getHttp(url,request,"/upload/music/");
        Chapter chapter=new Chapter();
        chapter.setId(chapterId);
        chapter.setUrl(http);
        Double size=Double.valueOf(url.getSize()/1024/1024);
        chapter.setSize(size);
        String[] split=http.split("/");
        String name=split[split.length-1];
        AudioFile read= AudioFileIO.read(new File(realPath,name));
        MP3AudioHeader audioHeader=(MP3AudioHeader) read.getAudioHeader();
        int trackLength=audioHeader.getTrackLength();
        String time=trackLength/60+"分"+trackLength%60+"秒";
        chapter.setTime(time);
        System.out.println(chapter);

        chapterDao.updateByPrimaryKeySelective(chapter);

        hashMap.put("status",200);
        return hashMap;

    }

    @Override
    @RequestMapping("/downloadChapter")
    public void downloadChapter(String url, HttpServletResponse response, HttpSession session) throws IOException {
        String[] split=url.split("/");
        String realPath=session.getServletContext().getRealPath("/upload/music");
        String name=split[split.length-1];
        File file=new File(realPath,name);
        response.setHeader("Content-Disposition","attachment;filename="+name);
        ServletOutputStream outputStream=response.getOutputStream();
        FileUtils.copyFile(file,outputStream);
    }
}
