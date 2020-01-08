package com.baizhi.wz.service;

import com.baizhi.wz.dao.GuruDao;
import com.baizhi.wz.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GuruServiceImpl implements GuruService {

    @Autowired
    private GuruDao guruDao;

    @Transactional(propagation = Propagation.REQUIRED ,readOnly = true)
    @Override
    public List<Guru> queryAll() {
        List<Guru> gurus=guruDao.selectAll();
        return gurus;
    }
}
