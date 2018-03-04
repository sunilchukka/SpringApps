package com.org.servcie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.dao.OrderDaoI;
import com.org.model.OrderDetails;
@Service
public class OrderServiceImpl implements OrderServcieI{
	
	@Autowired
	private OrderDaoI orderDao;
	@Override
	public void saveOrder(List<OrderDetails> orderDetails) {
		// TODO Auto-generated method stub
		orderDao.saveOrder(orderDetails);
		
	}

}
