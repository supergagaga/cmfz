package com.baizhi.wz.service;

import com.baizhi.wz.dao.AlbumDao;
import com.baizhi.wz.entity.Album;
import com.baizhi.wz.util.HttpUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {


    @Autowired
    private AlbumDao albumDao;

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public Map queryByPage(Integer page, Integer rows) {

        int i=albumDao.selectCount(null);
        Integer total=i%rows==0?i/rows:i/rows+1;
        List<Album> albums=albumDao.selectByRowBounds(null,new RowBounds((page-1)*rows,rows));
        HashMap hashMap=new HashMap();
        hashMap.put("records",i);
        hashMap.put("total",total);
        hashMap.put("rows",albums);
        hashMap.put("page",page);
        return hashMap;
    }

    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    @Override
    public Album queryOne(String albumId) {
        Album album1=new Album();
        album1.setId(albumId);
        Album album=albumDao.selectOne(album1);
        return album;
    }

    @Override
    public void deleteById(String[] id) {

        albumDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public void updateAlbum(Album album) {
        albumDao.updateByPrimaryKeySelective(album);

    }

    @Override
    public void addAlbum(Album album) {

        albumDao.insert(album);

    }

    @Override
    public Map uploadAlbum( MultipartFile cover,String albumId,HttpSession session,HttpServletRequest request) {
        System.out.println("lllllllllllS");
        HashMap hashMap=new HashMap();
        String realPath=session.getServletContext().getRealPath("/upload/albumImg/");
        File file=new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }

        String http= HttpUtil.getHttp(cover,request,"/upload/albumImg/");
        Album album=new Album();
        System.out.println(http);
        System.out.println(albumId);
        album.setId(albumId);
        album.setCover(http);
        albumDao.updateByPrimaryKeySelective(album);
        hashMap.put("status",200);
        return hashMap;


    }
}
