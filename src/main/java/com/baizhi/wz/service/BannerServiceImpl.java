package com.baizhi.wz.service;

import com.baizhi.wz.dao.BannerDao;
import com.baizhi.wz.entity.Banner;
import com.baizhi.wz.entity.BannerDto;
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
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Banner> queryAll() {
        List<Banner> banners = bannerDao.selectAll();
        return banners;
    }

    @Override
    public Banner queryOne(String id) {
        Banner banner1 = new Banner();
        banner1.setId(id);
        Banner banner = bannerDao.selectOne(banner1);
        return banner;
    }

    @Override
    public void add(Banner banner) {

        bannerDao.insert(banner);


    }

    @Override
    public void update(Banner banner) {

        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @Override
    public void delete(String[] id) {
        bannerDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public BannerDto queryByPage(int curPage, int pageSize) {
       //创建dto对象
        BannerDto bannerDto = new BannerDto();
        //设置当前页
        bannerDto.setPage(curPage);
        //设置总行数
        int count = bannerDao.selectCount(null);
        bannerDto.setRecords(count);
        //设置总页数
        bannerDto.setTotal(count%pageSize==0?count/pageSize:count/pageSize+1);
        //计算下标
        int index = (curPage-1)*pageSize;
        //调用dao层的分页查方法 获取当前页数据
        List<Banner> banners = bannerDao.selectByRowBounds(null, new RowBounds(index, pageSize));
        //设置当前页的所有数据
        bannerDto.setRows(banners);
        return bannerDto;
    }

    @Override
    public Map uploadBanner(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request) {
        HashMap hashMap=new HashMap();
        //获取真实路径
        String realPath=session.getServletContext().getRealPath("/upload/img/");
        //判断文件夹是否存在
        File file=new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String http= HttpUtil.getHttp(url,request,"/upload/img/");
        System.out.println(http);
        //文件上传 工具类完成
        //更新数据库信息
        Banner banner=new Banner();
        banner.setId(bannerId);
        System.out.println(banner.getId());
        banner.setUrl(http);
        System.out.println(banner.getUrl());
        bannerDao.updateByPrimaryKeySelective(banner);
        System.out.println(banner.getUrl());
        System.out.println(banner);
        hashMap.put("status",200);
        return hashMap;


    }


}
