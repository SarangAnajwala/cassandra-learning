package com.blogspot.oakgreen.cassandra.cassandralearning.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.Id;

@Entity
@Table(name = "RowKeyDate")
public class RowKeyDate implements ParentDomain {

	@Id
	@Column(name="rkDate")
	private Date rkDate = null;
	
	@Column(name="colDate1")
	private Date colDate1 = null;
	
	@Column(name="colDate2")
	private Date colDate2 = null;
	
	@Column(name="colDate3")
	private Date colDate3 = null;

	public RowKeyDate(Date rkDate, Date colDate1, Date colDate2, Date colDate3) {
		super();
		this.rkDate = rkDate;
		this.colDate1 = colDate1;
		this.colDate2 = colDate2;
		this.colDate3 = colDate3;
	}

	public Date getRkDate() {
		return rkDate;
	}

	public void setRkDate(Date rkDate) {
		this.rkDate = rkDate;
	}

	public Date getColDate1() {
		return colDate1;
	}

	public void setColDate1(Date colDate1) {
		this.colDate1 = colDate1;
	}

	public Date getColDate2() {
		return colDate2;
	}

	public void setColDate2(Date colDate2) {
		this.colDate2 = colDate2;
	}

	public Date getColDate3() {
		return colDate3;
	}

	public void setColDate3(Date colDate3) {
		this.colDate3 = colDate3;
	}

	@Override
	public String toString() {
		return "RowKeyDate: rkDate=" + rkDate + "\n\t colDate1=" + colDate1
				+ "\n\t colDate2=" + colDate2 + "\n\t colDate3=" + colDate3;
	}

}
