package com.org.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.org.model.OrderDetails;

public class OrderDetailsStroedProcedure extends StoredProcedure{
	
public OrderDetailsStroedProcedure(JdbcTemplate jdbcTemplate, String procName)
{
	super(jdbcTemplate,procName);
	declareParameter(new SqlParameter("P_TYP_ORDER_OBJ",Types.ARRAY, "TYP_ORDER_OBJ"));
	declareParameter(new SqlParameter("P_FILE_NAME", Types.VARCHAR));
	//to declare out parameter use below syntax
	declareParameter(new SqlOutParameter("P_OUT_STATUS", Types.VARCHAR));
	compile();
}

public void insertOrderData(List<OrderDetails> orderDetails)
{
	Map<String , Object> inParams=new HashMap<>();
	inParams.put("P_TYP_ORDER_OBJ", new OrderType(orderDetails.toArray(new OrderDetails[orderDetails.size()])));
	inParams.put("P_FILE_NAME", "xyz");
	
}
}
