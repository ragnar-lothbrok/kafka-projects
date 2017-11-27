package com.edureka.mysql.Dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edureka.mysql.models.ProductPriceInventory;

@Repository
public interface ProductPriceInventoryDao extends CrudRepository<ProductPriceInventory, Long> {

	@Query("select p from ProductPriceInventory p where pogid=:pogid or supc=:supc")
	ProductPriceInventory findByPogIdAndSupc(@Param("pogid") long pogId, @Param("supc") String supc);

}
