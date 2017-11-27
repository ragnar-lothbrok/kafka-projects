package com.edureka.kafka.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edureka.mysql.Dao.ProductPriceInventoryDao;
import com.edureka.mysql.models.ProductKey;
import com.edureka.mysql.models.ProductPriceInventory;

@Transactional
@Service
public class ProductPriceInventoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductPriceInventoryService.class);

	@Autowired
	private ProductPriceInventoryDao productPriceInventoryDao;

	public ProductPriceInventory savePriceInventory(float price, long inventory, long pogId, String supc) {
		LOGGER.info("inside update price and inventory.");
		ProductPriceInventory productPriceInventory = productPriceInventoryDao.findByPogIdAndSupc(pogId, supc);
		if (productPriceInventory != null) {
			productPriceInventory.setPrice(price);
			productPriceInventory.setQuantity(inventory);
		} else {
			productPriceInventory = new ProductPriceInventory(new ProductKey(pogId, supc), price, inventory);
		}
		productPriceInventoryDao.save(productPriceInventory);
		return productPriceInventory;
	}
}
