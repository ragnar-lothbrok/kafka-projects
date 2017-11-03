package com.edureka.kafka.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edureka.kafka.dto.Product;

public class FileUtility {

	private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);

	public static List<Product> readFile(String filePath) {
		List<Product> productList = new ArrayList<Product>();
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);

			String sCurrentLine;
			sCurrentLine = br.readLine();
			while ((sCurrentLine = br.readLine()) != null) {
				String split[] = sCurrentLine.split(",");
				Product product = new Product(Long.parseLong(split[0]), split[1], split[2], split[3], split[4],
						split[5], split[6], Float.parseFloat(split[7]), Long.parseLong(split[8]), split[9], split[10],
						split[12], Long.parseLong(split[11]));
				productList.add(product);
			}
		} catch (IOException e) {
			logger.error("Exception occured while reading file = {} ", e);
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {

				logger.error("Exception occured while releasing file resources = {} ", ex);

			}
		}
		return productList;
	}
}
