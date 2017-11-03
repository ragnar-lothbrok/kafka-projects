package com.edureka.kafka.constants;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public interface KafkaConstants {

	public final static Integer BATCHSIZE = 20;

	public final static Integer ACCOUNT_LIMIT = 20;

	public final static Integer CHECKSUM_LIMIT = 12;

	public final static Integer READBATCHSIZE = 50;

	public final static Float MIN_BALANCE = 100000f;

	public final static Float MAX_BALANCE = 2999999999f;

	public final static Long MIN_ACCOUNT = 1l;

	public final static Long MAX_ACCOUNT = 100000l;

	public final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");

	public final DecimalFormat decimalFormat = new DecimalFormat("#.00");
	
	public final String PREFIX = "gauge.response.shadowfaxproducer.creditcard.";
}
