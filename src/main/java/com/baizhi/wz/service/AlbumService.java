package com.baizhi.wz.service;

import com.baizhi.wz.entity.Album;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AlbumService {
    public Map queryByPage(Integer page, Integer rows);
    public Album queryOne(String albumId);
    public void deleteById(String[] id);
    public void updateAlbum(Album album);
    public void addAlbum(Album album);
    public Map uploadAlbum(MultipartFile cover,String albumId,HttpSession session,HttpServletRequest request);



}
