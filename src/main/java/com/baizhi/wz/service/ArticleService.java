package com.baizhi.wz.service;

import com.baizhi.wz.entity.Article;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface ArticleService {

    public void insertArticle(Article article);
    public void removeArticle(String[] id);
    public void updateArticle(Article article);
    public Map queryByPage(int page,int pageSize);
}
