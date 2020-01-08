package com.baizhi.wz.service;

import com.baizhi.wz.entity.Chapter;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public interface ChapterService {
    public Map queryById(Integer page,Integer rows,String albumId);
    public void addChapter(Chapter chapter,String albumId);
    public void deleteChapter(String[] id);
    public void updateChapter(Chapter chapter);
    public Map uploadChapter(MultipartFile url, String chapterId, HttpSession session, HttpServletRequest request) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException;
    public void downloadChapter(String url, HttpServletResponse response, HttpSession session) throws IOException;
}
