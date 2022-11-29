package com.tongfu.service.impl;

import com.tongfu.dao.OrderDao;
import com.tongfu.dao.OrderItemDao;
import com.tongfu.entity.Order;
import com.tongfu.entity.OrderItem;
import com.tongfu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderItemDao orderItemDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Order record) {
		return 0;
	}

	@Override
	public int insertSelective(Order record) {
		return 0;
	}

	@Override
	public Order selectByPrimaryKey(Long id) {
		return orderDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Order record) {
		return orderDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Order record) {
		return 0;
	}

	@Override
	public int getMonthCountOrder(Map<String, Object> query_map) {
		return orderDao.getMonthCountOrder(query_map);
	}

	@Override
	public int getWeekCountOrder(Map<String, Object> query_map) {
		return orderDao.getWeekCountOrder(query_map);
	}

	@Override
	public int getDayCountOrder(Map<String, Object> query_map) {
		return orderDao.getDayCountOrder(query_map);
	}

	@Override
	public List<Map<String, Object>> getAll(Map<String, Object> query_map) {
		return orderDao.getAll(query_map);
	}

	//计算订单小计
	@Override
	public BigDecimal getPrice(Map<String, Object> query_map) {
		BigDecimal price = new BigDecimal(0);
		List<OrderItem> orderItems = orderItemDao.getOrderItems(query_map);
			for (OrderItem orderItem : orderItems) {
				if (orderItem.getPrice() != null&& orderItem.getQuantity() != null) {
					price = price.add(orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
				}
			}
		return price;
	}

	@Override
	public BigDecimal getAmount(Map<String, Object> query_map) {
		BigDecimal amount = this.getPrice(query_map);
		Order order = this.selectByPrimaryKey(Long.valueOf(String.valueOf(query_map.get("orderId"))));
		if (order.getFee() != null) {//支付手续费
			amount = amount.add(order.getFee());
		}
		if (order.getFreight() != null) {//运费
			amount = amount.add(order.getFreight());
		}
		if (order.getPromotionDiscount() != null) {//促销折扣
			amount = amount.multiply(order.getPromotionDiscount());
		}
		if (order.getCouponDiscount() != null) {//减免金额
			amount = amount.subtract(order.getCouponDiscount());
		}
		if (order.getOffsetAmount() != null) {//调整金额
			amount = amount.add(order.getOffsetAmount());
		}
		if (order.getTax() != null) {//税金
			amount = amount.add(order.getTax());
		}
		return amount.compareTo(new BigDecimal(0)) > 0 ? amount : new BigDecimal(0);
	}

	@Override
	public Integer getQuantity(Map<String, Object> query_map) {
		Integer quantity = 0;
		List<OrderItem> orderItems = orderItemDao.getOrderItems(query_map);
		for (OrderItem orderItem : orderItems) {
			if (orderItem.getQuantity() != null) {
				quantity = quantity + orderItem.getQuantity();
			}
		}
		return quantity;
	}

	@Override
	public List<Map<String, Object>> getStatistics(Map<String, Object> query_map) {
		return orderDao.getStatistics(query_map);
	}

	@Override
	public List<Map<String, Object>> getHistogramStatistics(Map<String, Object> query_map) {
		return orderDao.getHistogramStatistics(query_map);
	}

	@Override
	public List<Map> getMonthData(Map<String, Object> query_map) {
		return orderDao.getMonthData(query_map);
	}

	@Override
	public List<Map> getOrderQuantity(Map query_map) {
		return orderDao.getOrderQuantity(query_map);
	}

	@Override
	public List<Map> getOrderPrice(Map query_map) {
		return orderDao.getOrderPrice(query_map);
	}

	@Override
	public List<Map> getOrderPayment(Map query_map) {
		return orderDao.getOrderPayment(query_map);
	}

	@Override
	public List<Map> getOrderShipping(Map query_map) {
		return orderDao.getOrderShipping(query_map);
	}

	@Override
	public List<Map> getYearOrder(Map query_map) {
		return orderDao.getYearOrder(query_map);
	}

	@Override
	public List<BigDecimal> thatYearOrder(Map query_map) {
		return orderDao.thatYearOrder(query_map);
	}

	@Override
	public List<Map> getamountOrder(Map query_map) {
		return orderDao.getamountOrder(query_map);
	}

	@Override
	public List<Integer> getPaymentMethod(Map query_map) {
		return orderDao.getPaymentMethod(query_map);
	}

	@Override
	public List<BigDecimal> getPaymentAmount(Map query_map) {
		return orderDao.getPaymentAmount(query_map);
	}

	@Override
	public List<Map> getOrderSource(Map query_map) {
		return orderDao.getOrderSource(query_map);
	}

	@Override
	public List<Map> getOrderSourcePie(Map query_map) {
		return orderDao.getOrderSourcePie(query_map);
	}

	@Override
	public List<Map> getYearOrderSum(Map query_map) {
		return orderDao.getYearOrderSum(query_map);
	}

	@Override
	public List<Map> productOrderProportions(Map query_map) {
		return orderDao.productOrderProportions(query_map);
	}

	@Override
	public List<Map> getOrderSourceByCity(Map query_map) {
		return orderDao.getOrderSourceByCity(query_map);
	}

	@Override
	public List<Map> getOrderSourceCityOrderByDesc(Map query_map) {
		return orderDao.getOrderSourceCityOrderByDesc(query_map);
	}

	@Override
	public List<Map> getProductQuantityOrderByDesc(Map query_map) {
		return orderDao.getProductQuantityOrderByDesc(query_map);
	}

	@Override
	public List<Map> getThisWeek(Map query_map) {
		return orderDao.getThisWeek(query_map);
	}

	@Override
	public List<Map> getLastWeek(Map query_map) {
		return orderDao.getLastWeek(query_map);
	}

	@Override
	public List<Map> getThisWeekConversionRate(Map query_map) {
		return orderDao.getThisWeekConversionRate(query_map);
	}

	@Override
	public List<Map> getLastWeekConversionRate(Map query_map) {
		return orderDao.getLastWeekConversionRate(query_map);
	}

	@Override
	public List<Map> getHospitalOrderBy(Map query_map) {
		return orderDao.getHospitalOrderBy(query_map);
	}

	@Override
	public List<Map> getOrderprovince(Map query_map) {
		return orderDao.getOrderprovince(query_map);
	}

	@Override
	public List<Map> getSumOrderAmountPaid(Map query_map) {
		return orderDao.getSumOrderAmountPaid(query_map);
	}

	@Override
	public List<Map> getComparativeAnalysisOfSales(Map query_map) {
		return orderDao.getComparativeAnalysisOfSales(query_map);
	}

	@Override
	public List<Map> getCountOrders(Map query_map) {
		return orderDao.getCountOrders(query_map);
	}

	@Override
	public List<Map> getCountOrderOfSales(Map query_map) {
		return orderDao.getCountOrderOfSales(query_map);
	}

	@Override
	public List<Map> getCountRefurnsOrder(Map query_map) {
		return orderDao.getCountRefurnsOrder(query_map);
	}

	@Override
	public List<Map> getOrderHospital(Map query_map) {
		return orderDao.getOrderHospital(query_map);
	}

	@Override
	public List<Map> getOrderArea(Map query_map) {
		return orderDao.getOrderArea(query_map);
	}


}
