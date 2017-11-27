package com.edureka.cassandra.java.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.LatencyAwarePolicy;
import com.datastax.driver.core.policies.LoadBalancingPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;

/**
 * 
 * This is an implementation of a simple Java client.
 *
 */

@Component
public class CassandraConnector {

	private static final Logger LOG = LoggerFactory.getLogger(CassandraConnector.class);

	@Value("${topic.datacenter.name}")
	private String DATA_CENTER;

	@Value("${topic.keyspace.name}")
	private String KEY_SPACE;

	@Value("${topic.locationurl.name}")
	private String LOCATION_URLS;

	@Bean
	public Session getSession() {
		LoadBalancingPolicy loadBalancingPolicy;
		DCAwareRoundRobinPolicy.Builder policyBuilder = DCAwareRoundRobinPolicy.builder();
		policyBuilder.withLocalDc(DATA_CENTER);
		loadBalancingPolicy = policyBuilder.build();
		loadBalancingPolicy = new TokenAwarePolicy(loadBalancingPolicy);
		loadBalancingPolicy = LatencyAwarePolicy.builder(loadBalancingPolicy).build();

		Builder builder = Cluster.builder().addContactPoints(LOCATION_URLS.split(","))
				.withLoadBalancingPolicy(DCAwareRoundRobinPolicy.builder().build());

		PoolingOptions poolingOptions = new PoolingOptions();
//		poolingOptions.setMaxConnectionsPerHost(HostDistance.REMOTE, 2);
		poolingOptions.setMaxConnectionsPerHost(HostDistance.LOCAL, 8);

		if (poolingOptions != null) {
			builder.withPoolingOptions(poolingOptions);
		}

		Session session  = null;
		
		try {
			Cluster cluster = builder.build();
			session = cluster.connect(KEY_SPACE);
			LOG.info("New cluster created");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}

}
