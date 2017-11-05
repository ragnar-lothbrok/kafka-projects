package com.sparkdemo.file.Impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sparkdemo.file.api.IFileReader;
import com.sparkdemo.kafka.configuration.KafkaProperties;
import com.sparkdemo.kafka.service.ProducerService;
import com.sparkdemo.model.TransactionDto;

@Service
public class FileReaderService implements IFileReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileReaderService.class);

	@Autowired
	private KafkaProperties kafkaProperties;
	
	@Autowired
	private ProducerService producerService;

	@PostConstruct
	public void init() {
		List<TransactionDto> transactionDtos = getDataFromFile();
		if(transactionDtos != null && transactionDtos.size() > 0){
			producerService.dispatch(transactionDtos);
		}
	}

	@Override
	public List<TransactionDto> getDataFromFile() {
		LOGGER.info("File name {} " + kafkaProperties.getFileName());
		List<TransactionDto> transactionDtos = new ArrayList<TransactionDto>();
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		SimpleDateFormat parser = new SimpleDateFormat("dd/mm/yy");
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(kafkaProperties.getFileName());
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			br.readLine();
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] transactionData = line.split(cvsSplitBy);
				transactionDtos.add(new TransactionDto(parser.parse(transactionData[0]), transactionData[1],
						Float.parseFloat(transactionData[2]), transactionData[3], transactionData[4],
						transactionData[5], transactionData[6], transactionData[7], parser.parse(transactionData[8]),
						parser.parse(transactionData[9]), Float.parseFloat(transactionData[10]),
						Float.parseFloat(transactionData[11]), UUID.randomUUID().toString()));
			}

		} catch (Exception e) {
			LOGGER.error("Exception occured while reading file {} ".trim(), e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					LOGGER.error("Exception occured while closing reader stream {} ".trim(), e);
				}
			}
		}
		return transactionDtos;
	}

}
