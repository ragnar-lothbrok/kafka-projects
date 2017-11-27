package com.edureka.mysql.models;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "product_inventory")
public class ProductPriceInventory implements Serializable {

	private static final long serialVersionUID = -8396339253207219404L;

	@Id
	@Embedded
	private ProductKey productKey;

	private float price;

	private long quantity;

	public ProductKey getProductKey() {
		return productKey;
	}

	public void setProductKey(ProductKey productKey) {
		this.productKey = productKey;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public ProductPriceInventory() {

	}

	public ProductPriceInventory(ProductKey productKey, float price, long quantity) {
		super();
		this.productKey = productKey;
		this.price = price;
		this.quantity = quantity;
	}

}
