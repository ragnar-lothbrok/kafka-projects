package com.edureka.kafka;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeywordBolt extends BaseRichBolt {

	private final static Logger LOGGER = LoggerFactory.getLogger(KeywordBolt.class);

	private static final long serialVersionUID = 1L;

	private OutputCollector _collector;

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("message"));
	}

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this._collector = collector;
	}

	public void execute(Tuple input) {
		LOGGER.info("Keyword Consumed  = {} ", new String((byte[]) input.getValues().get(0)));
		this._collector.ack(input);
	}

}
