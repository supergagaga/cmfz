package com.baizhi.wz.service;

import com.baizhi.wz.entity.Banner;
import com.baizhi.wz.entity.BannerDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface BannerService {
    public List<Banner> queryAll();
    public Banner queryOne(String id);
    public void add(Banner banner);
    public void update(Banner banner);
    public void delete(String[] id);
    public BannerDto queryByPage(int curPage, int pageSize);
    public Map uploadBanner(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request);
}
