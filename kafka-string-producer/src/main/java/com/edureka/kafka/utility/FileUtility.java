package com.edureka.kafka.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtility {

	private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);

	public static List<String> readFile(String filePath) {
		List<String> productList = new ArrayList<String>();
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);

			String sCurrentLine;
			sCurrentLine = br.readLine();
			while ((sCurrentLine = br.readLine()) != null) {
				productList.add(sCurrentLine);
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
