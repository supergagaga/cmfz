package com.baizhi.wz.service;

import com.baizhi.wz.dao.ArticleDao;
import com.baizhi.wz.entity.Article;
import com.baizhi.wz.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    private ArticleDao articleDao;


    @Override
    public void insertArticle(Article article) {
        articleDao.insert(article);
    }

    @Override
    public void removeArticle(String[] id) {
        articleDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public void updateArticle(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryByPage(int page, int pageSize) {
        HashMap hashMap=new HashMap();
        int count=articleDao.selectCount(null);
        hashMap.put("page",page);
        hashMap.put("records",count);
        hashMap.put("total",count%pageSize==0?count/pageSize:count/pageSize+1);
        List<Article> articles=articleDao.selectByRowBounds(null,new RowBounds((page-1)*pageSize,pageSize));
        hashMap.put("rows",articles);
        return hashMap;
    }
}
