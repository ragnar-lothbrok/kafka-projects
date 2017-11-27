package com.edureka.kafka.performance;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoringCache {

	public static enum Caches {
		PRODUCT_EVENT
	}

	private static final Logger logger = LoggerFactory.getLogger(MonitoringCache.class);

	private static final Map<Caches, MethodStats> monitoringCache = new HashMap<Caches, MethodStats>();

	static {
		for (Caches caches : Caches.values()) {
			monitoringCache.put(caches, new MethodStats(caches));
		}
	}

	public static void updateStats(Caches methodName, long count) {
		MethodStats stats = monitoringCache.get(methodName);
		String x = "";
		if (stats.eventCount.get() == 0) {
			stats.startTime.set(System.currentTimeMillis());
		}
		stats.eventCount.addAndGet(count);
		if (stats.eventCount.get() % 1000 == 0) {
			x = "method: " + methodName + ", event count = " + stats.eventCount.get() + " Time taken = "
					+ (System.currentTimeMillis() - stats.startTime.get());
			logger.warn(x);
		}
	}
}
