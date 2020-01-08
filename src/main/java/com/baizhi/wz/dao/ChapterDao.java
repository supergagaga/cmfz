package com.baizhi.wz.dao;


import com.baizhi.wz.entity.Chapter;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface ChapterDao extends Mapper<Chapter>, InsertListMapper<Chapter>, DeleteByIdListMapper<Chapter,String> {
}
