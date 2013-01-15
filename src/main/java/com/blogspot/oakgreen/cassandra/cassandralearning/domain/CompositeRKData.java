package com.blogspot.oakgreen.cassandra.cassandralearning.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.beans.AbstractComposite.Component;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.Id;

@Entity
@Table(name="composite_rk_data")
public class CompositeRKData implements ParentDomain {

	@Id
	private Composite compositeRk = null;
	
	@Column(name="c1")
	private String c1 = null;
	@Column(name="c2")
	private String c2 = null;
	@Column(name="c3")
	private String c3 = null;
	
	public CompositeRKData() {
		super();
	}

	public CompositeRKData(Composite compositeRk, String c1, String c2,
			String c3) {
		super();
		this.compositeRk = compositeRk;
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
	}
	
	public Composite getCompositeRk() {
		return compositeRk;
	}
	public void setCompositeRk(Composite compositeRk) {
		this.compositeRk = compositeRk;
	}
	public String getC1() {
		return c1;
	}
	public void setC1(String c1) {
		this.c1 = c1;
	}
	public String getC2() {
		return c2;
	}
	public void setC2(String c2) {
		this.c2 = c2;
	}
	public String getC3() {
		return c3;
	}
	public void setC3(String c3) {
		this.c3 = c3;
	}

	@Override
	public String toString() {
		Composite composite = this.getCompositeRk();
//		composite.getComponent;
		String cStr = "";
		boolean start = true;
		for (Component<?> c : composite.getComponents()) {
			if (!start) {
				cStr = cStr + ":";
			} else {
				start = false;
			}
			cStr = cStr + c.getValue(StringSerializer.get());
			//System.out.println(c.getValue(StringSerializer.get()));
		}
 		return "CompositeRKData [compositeRk=" + cStr + ", c1=" + c1
				+ ", c2=" + c2 + ", c3=" + c3 + "]";
	}
	
}
