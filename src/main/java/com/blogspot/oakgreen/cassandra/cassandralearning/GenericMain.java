package com.blogspot.oakgreen.cassandra.cassandralearning;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hom.EntityManagerImpl;

import com.blogspot.oakgreen.cassandra.cassandralearning.domain.ParentDomain;

public class GenericMain<KeyClass, DomainClass extends ParentDomain> {

	protected static Cluster cluster = HFactory.getOrCreateCluster(
			"Test Cluster", "localhost:9160");
	protected static Keyspace keyspace = HFactory.createKeyspace("Learning",
			cluster);
	protected EntityManagerImpl em = null;
	
    private final Class<DomainClass> domainClass; 
    private final Class<KeyClass> keyClass;

	public GenericMain(Class<KeyClass> keyClass, Class<DomainClass> domainClass) {
		this.keyClass = keyClass;
		this.domainClass = domainClass;
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
	
	public DomainClass get(KeyClass key) throws Exception {
		DomainClass result = em.find(domainClass, key);
		return result;
	}
}
