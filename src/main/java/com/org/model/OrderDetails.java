package com.org.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderDetails {
	@JsonProperty("CUST_NAME")
	private String custName;
	@JsonProperty(value="CUST_ID")
	private long custId;
	@JsonProperty(value="CUST_ADDRESS")
	private String custAddress;
	@JsonProperty(value="ORDER_ID")
	private long orderId;
	// Default format is yyyy-MM-dd
	@JsonProperty(value="ORDER_DATE")
	private LocalDate OrderDate;
	// Deault format is yyyy-MM-dd
	@JsonProperty(value="BASELINE_DATE")
	private LocalDate baseLineDate;
	// Default format is yyyy-MM-dd
	@JsonProperty(value="DELIVERY_DATE")
	private LocalDate deliveryDate;
	@JsonProperty(value="AMOUNT")
	private double amount;
	@JsonProperty(value="TAX")
	private double tax;
	@JsonProperty(value="REGION")
	private String region;
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public long getCustId() {
		return custId;
	}
	public void setCustId(long custId) {
		this.custId = custId;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public LocalDate getOrderDate() {
		return OrderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		OrderDate = orderDate;
	}
	public LocalDate getBaseLineDate() {
		return baseLineDate;
	}
	public void setBaseLineDate(LocalDate baseLineDate) {
		this.baseLineDate = baseLineDate;
	}
	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	@Override
	public String toString() {
		return "OrderDetails [custName=" + custName + ", custId=" + custId + ", custAddress=" + custAddress
				+ ", orderId=" + orderId + ", OrderDate=" + OrderDate + ", baseLineDate=" + baseLineDate
				+ ", deliveryDate=" + deliveryDate + ", amount=" + amount + ", tax=" + tax + ", region=" + region + "]";
	}
	
	
	
	
}
