package com.baizhi.wz.entity;

import com.alibaba.excel.context.AnalysisContext;

import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.wz.dao.BannerDao;
import com.baizhi.wz.util.MyWebAware;

import java.util.ArrayList;

public class BannerDataListener extends AnalysisEventListener<Banner> {

    ArrayList arrayList = new ArrayList();

    @Override
    public void invoke(Banner banner, AnalysisContext analysisContext) {
        arrayList.add(banner);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        BannerDao bannerDao = (BannerDao) MyWebAware.getBeanByClass(BannerDao.class);
        bannerDao.insertList(arrayList);
    }
}
