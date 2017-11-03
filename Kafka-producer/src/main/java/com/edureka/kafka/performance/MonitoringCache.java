package com.edureka.kafka.performance;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoringCache {

	public static enum Caches {
		PRODUCT_EVENT;
	}

	private static final Logger logger = LoggerFactory.getLogger(MonitoringCache.class);

	private static final Map<Caches, MethodStats> monitoringCache = new ConcurrentHashMap<Caches, MethodStats>();

	static {
		monitoringCache.put(Caches.PRODUCT_EVENT, new MethodStats(Caches.PRODUCT_EVENT));
	}

	public static void updateStats(Caches methodName, long elapsedTime, long count) {
		MethodStats stats = monitoringCache.get(methodName);
		String x = "";
		stats.eventCount.addAndGet(count);
		stats.totalTime.addAndGet(elapsedTime);
		if (elapsedTime > stats.maxTime.get()) {
			stats.maxTime.set(elapsedTime);
		}
		if (stats.eventCount.get() % MethodStats.statLogFrequency == 0) {
			long avgTime = stats.totalTime.get() / stats.eventCount.get();
			long timeTaken = (stats.totalTime.get() - stats.lastTotalTime.get());
			long runningAvg = timeTaken / MethodStats.statLogFrequency;
			stats.lastTotalTime = stats.totalTime;
			x = "method: " + methodName + ", event count = " + stats.eventCount.get() + ", last Total Time = "
					+ stats.lastTotalTime.get() + ", avgTime = " + avgTime + ", runningAvg per 1000 = " + runningAvg
					+ ", maxTime = " + stats.maxTime + ", totalTime = " + stats.totalTime + " time taken = "
					+ (timeTaken);
			if (!x.isEmpty())
				logger.warn(x);
		}
	}
}
