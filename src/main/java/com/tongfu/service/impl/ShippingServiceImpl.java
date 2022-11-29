package com.tongfu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongfu.dao.*;
import com.tongfu.email.EmailEntity;
import com.tongfu.email.EmailSend;
import com.tongfu.email.MailInfo;
import com.tongfu.email.MailSendUtils;
import com.tongfu.entity.*;
import com.tongfu.service.AreaService;
import com.tongfu.service.DeliveryCorpService;
import com.tongfu.service.ShippingService;
import com.tongfu.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.*;


@Service
public class ShippingServiceImpl implements ShippingService {


	@Autowired
	private ShippingDao shippingDao;

	@Autowired
	private ShippingItemDao shippingItemDao;

	@Autowired
	private AreaDao areaDao;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private DeliveryCorpDao deliveryCorpDao;

	@Autowired
	private OrderItemDao orderItemDao;

	@Autowired
	private OrderLogDao orderLogDao;

	@Autowired
	private ProductActivationCodeDao productActivationCodeDao;


	//阿里云发件人账户
	@Value("${aliyunSendEmailAccount}")
	private String aliyunSendEmailAccount;

	//阿里云发件人密码
	@Value("${aliyunSendEmailPassword}")
	private String aliyunSendEmailPassword;


	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Shipping record) {
		return 0;
	}

	@Override
	public int insertSelective(Shipping record) {
		return shippingDao.insertSelective(record);
	}

	@Override
	@Transient
	public String insertSelective(String params) {

		try {
			JSONObject jsonObject = JSON.parseObject(params);
			Boolean logistics = jsonObject.getBoolean("logistics");

			JSONArray shippingItems = jsonObject.getJSONArray("shippingItems");//发货项
			String operator = jsonObject.getString("operator");//发货人姓名
			String operatorPhone = jsonObject.getString("operator_phone");//发货人电话
			Long operatorAreaId = jsonObject.getLong("operator_area_id");//发货人地区
			String operatorAddress = jsonObject.getString("operator_address");//发货人详细地址
			Long deliveryCorpId = jsonObject.getLong("deliveryCorpId");//物流公司id
			BigDecimal weight = jsonObject.getBigDecimal("weight");//总重量
			BigDecimal freight = jsonObject.getBigDecimal("freight");//运费
			String trackingNo = jsonObject.getString("tracking_no");//运单号
			Long orderId = jsonObject.getLong("orderId");//订单id
//		String memo = jsonObject.getString("memo");
			Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
			if (!logistics){//如果不使用物流 直接发送 激活码
				Order order = orderDao.selectByPrimaryKey(orderId);//订单
				Shipping shipping = new Shipping();
				shipping.setCreateDate(new Date());
				shipping.setModifyDate(new Date());
				shipping.setFreight(new BigDecimal(0));
				order.setFreight(new BigDecimal(0));
				shipping.setMemo(order.getMemo());
				shipping.setConsignee(admin.getUsername());
				shipping.setOperator(admin.getName());
//				shipping.setOperatorPhone(operatorPhone);
				shipping.setShippingMethod("邮件配送");
				shipping.setSn("FHD"+System.currentTimeMillis());
				shipping.setTrackingNo("");//运单号
				shipping.setZipCode("");
				shipping.setOrders(orderId);
				shipping.setWeight(new BigDecimal(0));
				shippingDao.insertSelective(shipping);
//				邮件配送 给用户邮箱发送激活码

				List<Map<String,Object>> shippingItemList = new ArrayList<>();
				Map<String,Object> shipping_item = new HashMap<>();
				shipping_item.put("shipping",shipping.getId());
				Boolean flag = true;
				for (Object shippingItem : shippingItems) {
					JSONObject data = JSONObject.parseObject(shippingItem.toString());
					Map<String,Object> shipping_item_map = new HashMap<>();
					Long orderItemId = data.getLong("orderItemId");
					Integer quantity =  data.getInteger("quantity");
					OrderItem orderItem = orderItemDao.selectByPrimaryKey(orderItemId);
					orderItem.setReturnQuantity(quantity);
					orderItem.setShippedQuantity(orderItem.getQuantity()-quantity);//未发货数量 等于购买数量减去已发货数量
					orderItemDao.updateByPrimaryKeySelective(orderItem);
					shipping_item_map.put("sn",orderItem.getSn());
					shipping_item_map.put("quantity",quantity);
					shipping_item_map.put("name",orderItem.getName());
					shipping_item_map.put("createDate",new Date());
					shipping_item_map.put("modifyDate",new Date());
					shipping_item_map.put("isDelete",false);
					shippingItemList.add(shipping_item_map);
					Long productPurchaseId = orderItem.getProductPurchaseId();//购买方式id
					Map query_map = new HashMap();
					query_map.put("productPurchaseId",productPurchaseId);
					query_map.put("status",1);
					List<ProductActivationCode> notSale = productActivationCodeDao.getNotSale(query_map);//查询未出售的激活码给用户发送邮件
					String content = "你购买的“"+orderItem.getFullName()+"”产品，购买规格“"+orderItem.getPurchaseName()+"”，购买数量"+quantity+",以下是您的激活码:";
					for (int i = 0; i < quantity; i++) {//循环给用户发送邮件
						content+=","+notSale.get(i).getCode();
						ProductActivationCode productActivationCode = notSale.get(i);
						productActivationCode.setStatus(2l);
						productActivationCodeDao.updateByPrimaryKeySelective(productActivationCode);
					}
					//163邮箱发送邮件
//					EmailEntity email = new EmailEntity();
//					email.setUserName(emailUserName);
//					email.setPassword(emailPassword);
//					email.setHost(emailHost);
//					email.setPort(Integer.valueOf(emailPort));
//					email.setFromAddress(emailFromAddress);
//					email.setToAddress(order.getEmail());
//					String subject = "发货通知";
////					email.attachFile(staticPath+product.getOntrialPackage()); // 往邮件中添加附件
//					email.setContext(content);
//					email.setSubject(subject);
//					email.setContextType("text/html;charset=utf-8");
//					boolean flags = EmailSend.EmailSendTest(email);
					MailInfo mailInfo = new MailInfo(aliyunSendEmailAccount,
							aliyunSendEmailPassword,
							order.getEmail(), "surgi-plan",
							order.getConsignee(), "发货通知", content,null);
					boolean flags = MailSendUtils.sendEmail(mailInfo);
					System.err.println("邮件发送结果=="+flag);

				}
				shipping_item.put("shippingItemList",shippingItemList);
				shippingItemDao.insertSelectiveMap(shipping_item);


				Map<String,Object> query_map = new HashMap<>();
				query_map.put("orderId",orderId);
				List<OrderItem> orderItems = orderItemDao.getOrderItems(query_map);
				for (OrderItem orderItem : orderItems) {
					if (orderItem.getReturnQuantity()<orderItem.getQuantity()){
						flag = false;
						break;
					}
				}
				String content="";
				if (flag){
					order.setShippingStatus(2);
					order.setShippingMethodName("线上发货");
					order.setShippingMethod(1L);
					content = "已发货";
				}else {
					order.setShippingStatus(1);
					order.setShippingMethodName("线上发货");
					order.setShippingMethod(1L);
					content = "部分已发货";
				}
				order.setOrderStatus(1);
				orderDao.updateByPrimaryKeySelective(order);
				OrderLog orderLog = new OrderLog();
				orderLog.setCreateDate(new Date());
				orderLog.setModifyDate(new Date());
				orderLog.setOperator(admin.getUsername());
				orderLog.setOperatorName(admin.getName());
				orderLog.setContent("发货操作，邮件发货，"+content);
				orderLog.setIsDeleted(false);
				orderLog.setType(3);
				orderLog.setOrders(orderId);
				orderLogDao.insertSelective(orderLog);
				return ResultUtil.success();

			}

			Area operatorArea = areaDao.selectByPrimaryKey(operatorAreaId);//发货人地区
			Order order = orderDao.selectByPrimaryKey(orderId);//订单
			Shipping shipping = new Shipping();
			shipping.setCreateDate(new Date());
			shipping.setModifyDate(new Date());
			shipping.setAreaName(order.getAreaName());
			shipping.setAddress(order.getAddress());
			shipping.setConsignee(admin.getName());
			shipping.setReceivingName(order.getConsignee());
			shipping.setReceivingPhone(order.getPhone());
			shipping.setReceivingEmail(order.getEmail());
			DeliveryCorp deliveryCorp = deliveryCorpDao.selectByPrimaryKey(deliveryCorpId);
			shipping.setDeliveryCorp(deliveryCorp.getName());
			shipping.setDeliveryCorpCode(deliveryCorp.getCode());
			shipping.setDeliveryCorpUrl(deliveryCorp.getUrl());
			shipping.setFreight(freight);
			order.setFreight(freight);
			shipping.setMemo(order.getMemo());
			shipping.setOperator(operator);
			shipping.setOperatorPhone(operatorPhone);
			shipping.setShippingMethod(deliveryCorp.getName()+"配送");
			shipping.setSn("FHD"+System.currentTimeMillis());
			shipping.setTrackingNo(trackingNo);//运单号
			shipping.setZipCode("");
			shipping.setOrders(orderId);
			shipping.setOperatorArea(operatorArea.getFullName());
			shipping.setOperatorAddress(operatorAddress);
			shipping.setWeight(weight);
			shippingDao.insertSelective(shipping);


			List<Map<String,Object>> shippingItemList = new ArrayList<>();
			Map<String,Object> shipping_item = new HashMap<>();
			shipping_item.put("shipping",shipping.getId());
			for (Object shippingItem : shippingItems) {
				JSONObject data = JSONObject.parseObject(shippingItem.toString());
				Map<String,Object> shipping_item_map = new HashMap<>();
				Long orderItemId = data.getLong("orderItemId");
				Integer quantity =  data.getInteger("quantity");
				OrderItem orderItem = orderItemDao.selectByPrimaryKey(orderItemId);
				orderItem.setReturnQuantity(quantity);
				orderItem.setShippedQuantity(orderItem.getQuantity()-quantity);//未发货数量 等于购买数量减去已发货数量
				shipping_item_map.put("sn",orderItem.getSn());
				shipping_item_map.put("quantity",quantity);
				shipping_item_map.put("name",orderItem.getName());
				shipping_item_map.put("createDate",new Date());
				shipping_item_map.put("modifyDate",new Date());
				shipping_item_map.put("isDelete",false);
				shipping_item_map.put("shipping",shipping.getId());
				shippingItemList.add(shipping_item_map);
				orderItemDao.updateByPrimaryKeySelective(orderItem);
			}
			shipping_item.put("shippingItemList",shippingItemList);
			shippingItemDao.insertSelectiveMap(shipping_item);

			Boolean flag = true;
			Map<String,Object> query_map = new HashMap<>();
			query_map.put("orderId",orderId);
			List<OrderItem> orderItems = orderItemDao.getOrderItems(query_map);
			for (OrderItem orderItem : orderItems) {
				if (orderItem.getReturnQuantity()<orderItem.getQuantity()){
					flag = false;
					break;
				}
			}
			String content="";
			if (flag){
				order.setShippingStatus(2);
				order.setShippingMethodName("线下发货");
				order.setShippingMethod(2L);
				content = "已发货";
			}else {
				order.setShippingStatus(1);
				order.setShippingMethodName("线下发货");
				order.setShippingMethod(2L);
				content = "部分已发货";
			}
			order.setOrderStatus(1);
			orderDao.updateByPrimaryKeySelective(order);
			OrderLog orderLog = new OrderLog();
			orderLog.setCreateDate(new Date());
			orderLog.setModifyDate(new Date());
			orderLog.setOperator(admin.getUsername());
			orderLog.setOperatorName(operator);
			orderLog.setContent("发货操作"+content);
			orderLog.setIsDeleted(false);
			orderLog.setType(3);
			orderLog.setOrders(orderId);
			orderLogDao.insertSelective(orderLog);
			return ResultUtil.success();
		} catch (RuntimeException e) {
			e.printStackTrace();
//			throw new RuntimeException("操作失败");
			return  ResultUtil.error("操作失败");
		}

	}

	@Override
	public Shipping selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Shipping record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Shipping record) {
		return 0;
	}
}
