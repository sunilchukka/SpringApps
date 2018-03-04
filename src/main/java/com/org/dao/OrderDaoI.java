package com.org.dao;

import java.util.List;
import com.org.model.OrderDetails;

public interface OrderDaoI {

	public void saveOrder(List<OrderDetails> orderDetails);	
}
