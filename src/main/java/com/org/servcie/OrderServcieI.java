package com.org.servcie;

import java.util.List;

import com.org.model.OrderDetails;

public interface OrderServcieI {
public void saveOrder(List<OrderDetails> orderDetails);
}
