package com.blogspot.oakgreen.cassandra.cassandralearning;

import java.util.Calendar;
import java.util.Date;

import com.blogspot.oakgreen.cassandra.cassandralearning.domain.RowKeyDate;

/**
 * This program is written to test how does default ordering (by rowkey) works
 * when rowkey is of 'DateType'. The results show that one MUST NOT use DateType
 * for rowkey as the ordering gets all messed up!
 * 
 * Sample result:
 * [default@Learning] list RowkeyDate;
 * Using default limit of 100
 * Using default column limit of 100
 * -------------------
 * RowKey: 2013-01-13 02:23:13+0530
 * => (column=colDate1, value=2013-01-13 02:23:13+0530, timestamp=1357851193528004)
 * => (column=colDate2, value=2013-01-13 02:22:13+0530, timestamp=1357851193528007)
 * => (column=colDate3, value=2013-01-13 02:21:13+0530, timestamp=1357851193528005)
 * => (column=rkDate, value=2013-01-13 02:23:13+0530, timestamp=1357851193528006)
 * -------------------
 * RowKey: 2013-01-29 02:22:13+0530
 * => (column=colDate1, value=2013-01-29 02:13:13+0530, timestamp=1357851133853003)
 * => (column=colDate2, value=2013-01-29 02:12:13+0530, timestamp=1357851133853001)
 * => (column=colDate3, value=2013-01-29 02:11:13+0530, timestamp=1357851133853002)
 * => (column=rkDate, value=2013-01-29 02:22:13+0530, timestamp=1357851133853000)
 * -------------------
 * RowKey: 2013-01-01 02:21:13+0530
 * => (column=colDate1, value=2013-01-01 02:23:13+0530, timestamp=1357851073094006)
 * => (column=colDate2, value=2013-01-01 02:22:13+0530, timestamp=1357851073094004)
 * => (column=colDate3, value=2013-01-01 02:21:13+0530, timestamp=1357851073094007)
 * => (column=rkDate, value=2013-01-01 02:21:13+0530, timestamp=1357851073094005)
 * -------------------
 * RowKey: 2013-01-30 02:22:13+0530
 * => (column=colDate1, value=2013-01-30 02:03:13+0530, timestamp=1357851133806003)
 * => (column=colDate2, value=2013-01-30 02:02:13+0530, timestamp=1357851133806001)
 * => (column=colDate3, value=2013-01-30 02:01:13+0530, timestamp=1357851133806002)
 * => (column=rkDate, value=2013-01-30 02:22:13+0530, timestamp=1357851133806000)
 * -------------------
 * RowKey: 2013-01-02 02:21:13+0530
 * => (column=colDate1, value=2013-01-02 02:13:13+0530, timestamp=1357851073094002)
 * => (column=colDate2, value=2013-01-02 02:12:13+0530, timestamp=1357851073094000)
 * => (column=colDate3, value=2013-01-02 02:11:13+0530, timestamp=1357851073094003)
 * => (column=rkDate, value=2013-01-02 02:21:13+0530, timestamp=1357851073094001)
 * -------------------
 * RowKey: 2013-01-15 02:23:13+0530
 * => (column=colDate1, value=2013-01-15 02:03:13+0530, timestamp=1357851193481000)
 * => (column=colDate2, value=2013-01-15 02:02:13+0530, timestamp=1357851193481003)
 * => (column=colDate3, value=2013-01-15 02:01:13+0530, timestamp=1357851193481001)
 * => (column=rkDate, value=2013-01-15 02:23:13+0530, timestamp=1357851193481002)
 * -------------------
 * 
 * @author Sarang Anajwala (http://OakGreen.blogspot.com
 * 
 */
public class RowKeyDateMain extends GenericMain<RowKeyDate> {

	public RowKeyDateMain(Class<RowKeyDate> domainClass) {
		super(domainClass);
	}

	public static void main(String[] args) {
		RowKeyDateMain main = new RowKeyDateMain(RowKeyDate.class);
		Calendar cal = Calendar.getInstance();
		Date rkDt = null;
		Date cDt1 = null;
		Date cDt2 = null;
		Date cDt3 = null;
		//Calendar tmp = cal;
		for (int i=0; i < 3; i++) {
			cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, (3*5 - i));
			//cal.set(Calendar.MINUTE, 0);
			rkDt = cal.getTime();
			cal.set(Calendar.MINUTE, (10*i + 1));
			cDt3 = cal.getTime();
			cal.set(Calendar.MINUTE, (10*i + 2));
			cDt2 = cal.getTime();
			cal.set(Calendar.MINUTE, (10*i + 3));
			cDt1 = cal.getTime();
			RowKeyDate rkd = new RowKeyDate(rkDt, cDt1, cDt2, cDt3);
			System.out.println(rkd.toString());
			try {
				main.save(rkd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
