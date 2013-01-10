package com.blogspot.oakgreen.cassandra.cassandralearning;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hom.EntityManagerImpl;

import com.blogspot.oakgreen.cassandra.cassandralearning.domain.ParentDomain;

public class GenericMain<DomainClass extends ParentDomain> {

	protected static Cluster cluster = HFactory.getOrCreateCluster(
			"Test Cluster", "localhost:9160");
	protected static Keyspace keyspace = HFactory.createKeyspace("Learning",
			cluster);
	protected EntityManagerImpl em = null;

	public GenericMain(Class<DomainClass> domainClass) {
		em = new EntityManagerImpl(keyspace, domainClass.getPackage().getName());
	}

	public void save(DomainClass obj) throws Exception {
		try {
			em.persist(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
