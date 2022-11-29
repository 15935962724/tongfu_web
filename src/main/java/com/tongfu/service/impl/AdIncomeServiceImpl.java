package com.tongfu.service.impl;


import com.tongfu.dao.AdIncomeDao;
import com.tongfu.entity.AdIncome;
import com.tongfu.service.AdIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service
public class AdIncomeServiceImpl implements AdIncomeService {

    @Autowired
    private AdIncomeDao adIncomeDao;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(AdIncome record) {
        return 0;
    }

    @Override
    public int insertSelective(AdIncome record) {
        return adIncomeDao.insertSelective(record);
    }

    @Override
    public AdIncome selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(AdIncome record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(AdIncome record) {
        return 0;
    }

    @Override
    public Map getMonthCountAdPrice(Map<String, Object> query_map) {
        return adIncomeDao.getMonthCountAdPrice(query_map);
    }

    @Override
    public Map getWeekCountAdPrice(Map<String, Object> query_map) {
        return adIncomeDao.getWeekCountAdPrice(query_map);
    }

    @Override
    public Map getDayCountAdPrice(Map<String, Object> query_map) {
        return adIncomeDao.getDayCountAdPrice(query_map);
    }

    @Override
    public List<Map> getMonthData(Map<String, Object> query_map) {
        return adIncomeDao.getMonthData(query_map);
    }

    @Override
    public List<Map> getWeekData(Map query_map) {
        return adIncomeDao.getWeekData(query_map);
    }

    @Override
    public Map getWeekCountData(Map query_map) {
        return adIncomeDao.getWeekCountData(query_map);
    }

}
