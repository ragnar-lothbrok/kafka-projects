package com.edureka.kafka.performance;

import java.util.concurrent.atomic.AtomicLong;

import com.edureka.kafka.performance.MonitoringCache.Caches;

public class MethodStats {

	public Caches methodName;
	public AtomicLong startTime = new AtomicLong();
	public AtomicLong totalTime = new AtomicLong();
	public AtomicLong lastTotalTime = new AtomicLong();
	public AtomicLong maxTime = new AtomicLong();
	public static long statLogFrequency = 1000;
	public AtomicLong eventCount = new AtomicLong();

	public MethodStats(Caches methodName) {
		this.methodName = methodName;
	}
}
