package com.tongfu.dao;

import com.tongfu.entity.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int getMonthCountOrder(Map<String,Object> query_map);

    int getWeekCountOrder(Map<String,Object> query_map);

    int getDayCountOrder(Map<String,Object> query_map);

    List<Map<String,Object>> getAll(Map<String,Object> query_map);

    List<Map<String,Object>> getStatistics(Map<String,Object> query_map);

    List<Map<String,Object>> getHistogramStatistics(Map<String,Object> query_map);

    List<Map> getMonthData(Map<String,Object> query_map);

    List<Map> getOrderQuantity(Map query_map);//今年vs去年订单量

    List<Map> getOrderPrice(Map query_map);//今年vs去年订单金额

    List<Map> getOrderPayment(Map query_map);//支付方式占比

    List<Map> getOrderShipping(Map query_map);//发货占比

    List<BigDecimal> thatYearOrder (Map query_map);

    List<Map> getYearOrder(Map query_map);//查询订单产生的所有年份

    List<Map> getamountOrder(Map query_map);//根据支付方式分组 订单量 和订单金额

    List<Integer> getPaymentMethod(Map query_map);

    List<BigDecimal> getPaymentAmount(Map query_map);

    List<Map> getOrderSource(Map query_map); //订单来源(地图展示)

    List<Map> getOrderSourcePie(Map query_map); //订单来源(饼状图展示)

    List<Map> getYearOrderSum(Map query_map);

    List<Map> productOrderProportions(Map query_map);

    List<Map> getOrderSourceByCity(Map query_map);

    List<Map> getOrderSourceCityOrderByDesc(Map query_map);

    List<Map> getProductQuantityOrderByDesc(Map query_map);

    List<Map> getThisWeek(Map query_map);//本周订单量+订单金额

    List<Map> getLastWeek(Map query_map);//上周订单量+订单金额

    List<Map> getThisWeekConversionRate(Map query_map);//本周订单转化率

    List<Map> getLastWeekConversionRate(Map query_map);//上周订单转化率

    List<Map> getHospitalOrderBy(Map query_map);//医院使用排名

    List<Map> getOrderprovince(Map query_map); //销售地区分析

    List<Map> getSumOrderAmountPaid(Map query_map); //销售额分析

    List<Map> getComparativeAnalysisOfSales(Map query_map);

    List<Map> getCountOrders(Map query_map);//订单分析

    List<Map> getCountOrderOfSales(Map query_map);//订单对比分析

    List<Map> getCountRefurnsOrder(Map query_map);//退货分析

    List<Map> getOrderHospital(Map query_map);//医院排名分析

    List<Map> getOrderArea(Map query_map);




}