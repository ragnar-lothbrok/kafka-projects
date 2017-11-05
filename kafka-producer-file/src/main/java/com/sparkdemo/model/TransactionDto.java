package com.sparkdemo.model;

import java.io.Serializable;
import java.util.Date;

public class TransactionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date transactionDate;
	private String product;
	private Float price;
	private String paymentType;
	private String name;
	private String city;
	private String state;
	private String country;
	private Date accountCreated;
	private Date lastLogin;
	private Float latitude;
	private Float longitude;
	private String transactionId;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getAccountCreated() {
		return accountCreated;
	}

	public void setAccountCreated(Date accountCreated) {
		this.accountCreated = accountCreated;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public TransactionDto(Date transactionDate, String product, Float price, String paymentType, String name,
			String city, String state, String country, Date accountCreated, Date lastLogin, Float latitude,
			Float longitude, String transactionId) {
		super();
		this.transactionDate = transactionDate;
		this.product = product;
		this.price = price;
		this.paymentType = paymentType;
		this.name = name;
		this.city = city;
		this.state = state;
		this.country = country;
		this.accountCreated = accountCreated;
		this.lastLogin = lastLogin;
		this.latitude = latitude;
		this.longitude = longitude;
		this.transactionId = transactionId;
	}

	public TransactionDto() {

	}

	@Override
	public String toString() {
		return "TransactionDto [transactionDate=" + transactionDate + ", product=" + product + ", price=" + price
				+ ", paymentType=" + paymentType + ", name=" + name + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", accountCreated=" + accountCreated + ", lastLogin=" + lastLogin
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", transactionId=" + transactionId + "]";
	}

}
