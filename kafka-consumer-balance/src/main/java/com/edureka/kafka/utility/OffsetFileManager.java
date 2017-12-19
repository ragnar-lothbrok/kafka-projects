package com.edureka.kafka.utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OffsetFileManager {

	private static final Logger logger = LoggerFactory.getLogger(OffsetFileManager.class);

	private String prefix;

	public OffsetFileManager(String storagePrefix) {
		this.prefix = storagePrefix;
	}

	public void saveOffsetInOffsetManager(String topic, int partition, long offset) {
		try {
			FileWriter writer = new FileWriter(storageName(topic, partition), false);

			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(offset + "");
			bufferedWriter.flush();
			bufferedWriter.close();

		} catch (Exception e) {
			logger.error("exception occured while wriring file = {} ", e);
		}
	}

	@SuppressWarnings("resource")
	public long readOffsetFromoffsetManager(String topic, int partition) {
		try {
			Stream<String> stream = Files.lines(Paths.get(storageName(topic, partition)));
			return Long.parseLong(stream.collect(Collectors.toList()).get(0)) + 1;
		} catch (Exception e) {
			logger.error("exception occured while reading file = {} ", e);
		}
		return 0;
	}

	private String storageName(String topic, int partition) {
		return prefix + "-" + topic + "-" + partition;
	}

}
