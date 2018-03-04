package com.org.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.core.support.AbstractSqlTypeValue;

import com.org.model.OrderDetails;

import oracle.jdbc.driver.OracleConnection;

public class OrderType extends AbstractSqlTypeValue{
	private OrderDetails [] orderDetailsArray;
	
	public OrderType(OrderDetails[] orderDetailsArray) {
		// TODO Auto-generated constructor stub
		this.orderDetailsArray=orderDetailsArray;
	}
	
	@Override
	protected Object createTypeValue(Connection con, int sqlType, String typeName) throws SQLException {
		// TODO Auto-generated method stub
		OracleConnection oracleConnection=con.unwrap(OracleConnection.class);
		return oracleConnection.createOracleArray(typeName, orderDetailsArray);
	}

}
