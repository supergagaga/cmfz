package com.baizhi.wz.dao;

import com.baizhi.wz.entity.Banner;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;


public interface BannerDao extends Mapper<Banner>, InsertListMapper<Banner>, DeleteByIdListMapper<Banner,String> {
}
