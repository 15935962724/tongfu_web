package com.tongfu.service.impl;

import com.tongfu.dao.AdDao;
import com.tongfu.dao.BrandDao;
import com.tongfu.entity.Ad;
import com.tongfu.entity.Brand;
import com.tongfu.service.AdService;
import com.tongfu.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class BrandServiceImpl implements BrandService {



    @Autowired
    BrandDao brandDao;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(Brand record) {
        return 0;
    }

    @Override
    public int insertSelective(Brand record) {
        return brandDao.insertSelective(record);
    }

    @Override
    public Brand selectByPrimaryKey(Long id) {
        return brandDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Brand record) {
        return brandDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(Brand record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Brand record) {
        return 0;
    }

    @Override
    public List<Brand> getAll(Map<String, Object> query_map) {
        return brandDao.getAll(query_map);
    }
}
