package com.edureka.kafka.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edureka.kafka.dto.Transaction;

public class FileUtility {

	private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);

	private static SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yy hh:mm");

	public static List<Transaction> readFile(String filePath) {
		List<Transaction> productList = new ArrayList<Transaction>();
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);

			String sCurrentLine;
			sCurrentLine = br.readLine();
			while ((sCurrentLine = br.readLine()) != null) {
				String split[] = sCurrentLine.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
				Transaction product = null;
				try {
					System.out.println(sCurrentLine);
					product = new Transaction(sdf.parse(split[0]), split[1], Float.parseFloat(split[2]), split[3],
							split[4], split[5], split[6], split[7], sdf.parse(split[8]), sdf.parse(split[9]),
							Float.parseFloat(split[10]), Float.parseFloat(split[11]), UUID.randomUUID().toString());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
