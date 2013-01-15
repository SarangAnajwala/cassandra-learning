package com.blogspot.oakgreen.cassandra.cassandralearning;

import java.util.Calendar;
import java.util.Date;

import com.blogspot.oakgreen.cassandra.cassandralearning.domain.RowKeyDate;

/**
 * This program is written to test how does default ordering (by rowkey) works
 * when rowkey is of 'DateType'. The results are positive - the sorting is done 
 * as simple string sort.
 * 
 * Sample result:
 * [default@Learning] list rowKeyDate;
 * Using default limit of 100
 * Using default column limit of 100
 * -------------------
 * RowKey: 2013-01-01 14:31:15+0530
 * => (column=colDate1, value=2013-01-01 14:23:15+0530, timestamp=1357894875778007)
 * => (column=colDate2, value=2013-01-01 14:22:15+0530, timestamp=1357894875778004)
 * => (column=colDate3, value=2013-01-01 14:21:15+0530, timestamp=1357894875778006)
 * => (column=rkDate, value=2013-01-01 14:31:15+0530, timestamp=1357894875778005)
 * -------------------
 * RowKey: 2013-01-02 14:31:15+0530
 * => (column=colDate1, value=2013-01-02 14:13:15+0530, timestamp=1357894875778003)
 * => (column=colDate2, value=2013-01-02 14:12:15+0530, timestamp=1357894875778000)
 * => (column=colDate3, value=2013-01-02 14:11:15+0530, timestamp=1357894875778002)
 * => (column=rkDate, value=2013-01-02 14:31:15+0530, timestamp=1357894875778001)
 * -------------------
 * RowKey: 2013-01-03 14:31:15+0530
 * => (column=colDate1, value=2013-01-03 14:03:15+0530, timestamp=1357894875106002)
 * => (column=colDate2, value=2013-01-03 14:02:15+0530, timestamp=1357894875075000)
 * => (column=colDate3, value=2013-01-03 14:01:15+0530, timestamp=1357894875106001)
 * => (column=rkDate, value=2013-01-03 14:31:15+0530, timestamp=1357894875106000)
 * -------------------
 * RowKey: 2013-01-28 14:31:38+0530
 * => (column=colDate1, value=2013-01-28 14:23:38+0530, timestamp=1357894898871007)
 * => (column=colDate2, value=2013-01-28 14:22:38+0530, timestamp=1357894898871005)
 * => (column=colDate3, value=2013-01-28 14:21:38+0530, timestamp=1357894898871006)
 * => (column=rkDate, value=2013-01-28 14:31:38+0530, timestamp=1357894898871004)
 * -------------------
 * RowKey: 2013-01-29 14:31:38+0530
 * => (column=colDate1, value=2013-01-29 14:13:38+0530, timestamp=1357894898871003)
 * => (column=colDate2, value=2013-01-29 14:12:38+0530, timestamp=1357894898871001)
 * => (column=colDate3, value=2013-01-29 14:11:38+0530, timestamp=1357894898871002)
 * => (column=rkDate, value=2013-01-29 14:31:38+0530, timestamp=1357894898871000)
 * -------------------
 * RowKey: 2013-01-30 14:31:38+0530
 * => (column=colDate1, value=2013-01-30 14:03:38+0530, timestamp=1357894898825002)
 * => (column=colDate2, value=2013-01-30 14:02:38+0530, timestamp=1357894898825000)
 * => (column=colDate3, value=2013-01-30 14:01:38+0530, timestamp=1357894898825001)
 * => (column=rkDate, value=2013-01-30 14:31:38+0530, timestamp=1357894898809000)
 * 
 * 6 Rows Returned.
 * Elapsed time: 76 msec(s).
 * 
 * @author Sarang Anajwala (http://OakGreen.blogspot.com
 * 
 */
public class RowKeyDateMain extends GenericMain<Date, RowKeyDate> {

	public RowKeyDateMain() {
		super(Date.class, RowKeyDate.class);
	}

	public static void main(String[] args) {
		RowKeyDateMain main = new RowKeyDateMain();
		Calendar cal = Calendar.getInstance();
		Date rkDt = null;
		Date cDt1 = null;
		Date cDt2 = null;
		Date cDt3 = null;
		//Calendar tmp = cal;
		for (int i=0; i < 3; i++) {
			cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, (3 - i));
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
