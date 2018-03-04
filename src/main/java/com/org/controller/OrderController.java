package com.org.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.org.model.OrderDetails;
import com.org.servcie.OrderServcieI;

@RestController
public class OrderController {
	
	@Autowired
	private OrderServcieI orderServcie;
	
	
	@RequestMapping(value="/saveOrder", consumes="application/json", method=RequestMethod.POST)
	public ResponseEntity<String> saveOrderData(@RequestBody List<OrderDetails> orderDetails)
	{
		orderServcie.saveOrder(orderDetails);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
