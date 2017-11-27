package com.edureka.cassandra.java.client.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.edureka.kafka.dto.Product;

@Service
public class ProductRepository {

	private static final String TABLE_NAME = "products_v2";

	@Autowired
	private Session session;

	public ProductRepository(Session session) {
		this.session = session;
	}

	/**
	 * Insert a row in the table products.
	 * 
	 * @param product
	 */
	public void insertProduct(Product product) {
		StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME)
				.append("(pogid, brand, description, category,subcategory,supc,sellercode,country,size) ").append("VALUES (")
				.append(product.getPogId()).append(", '").append(product.getBrand()).append("', '")
				.append(product.getDescription()).append("', '").append(product.getCategory()).append("', '")
				.append(product.getSubCategory()).append("', '").append(product.getSupc()).append("', '")
				.append(product.getSellerCode()).append("', '").append(product.getCountry()).append("', '").append(product.getSize()).append("');");

		final String query = sb.toString();
		session.execute(query);
	}

	/**
	 * Select Product by id.
	 * 
	 * @return
	 */
	public Product selectByPogId(long pogId) {
		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME).append(" WHERE pogId = ")
				.append(pogId).append(";");

		final String query = sb.toString();

		ResultSet rs = session.execute(query);

		List<Product> products = new ArrayList<Product>();

		for (Row r : rs) {
			Product s = new Product(r.getLong("pogid"), r.getString("brand"), r.getString("description"),
					r.getString("category"), r.getString("subcategory"), r.getFloat("price"), r.getString("country"),
					r.getString("sellercode"), r.getString("supc"));
			products.add(s);
		}

		return products.get(0);
	}

	/**
	 * Select all products from products
	 * 
	 * @return
	 */
	public List<Product> selectAll() {
		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME);

		final String query = sb.toString();
		ResultSet rs = session.execute(query);

		List<Product> products = new ArrayList<Product>();

		for (Row r : rs) {
			Product product = new Product(r.getLong("pogid"), r.getString("brand"), r.getString("description"),
					r.getString("category"), r.getString("subcategory"), r.getFloat("price"), r.getString("country"),
					r.getString("sellercode"), r.getString("supc"));
			products.add(product);
		}
		return products;
	}

	/**
	 * Delete a Product by pogid.
	 */
	public void deleteProductByPogId(long pogId) {
		StringBuilder sb = new StringBuilder("DELETE FROM ").append(TABLE_NAME).append(" WHERE pogid = ").append(pogId)
				.append(";");

		final String query = sb.toString();
		session.execute(query);
	}

}
