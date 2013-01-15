package com.blogspot.oakgreen.cassandra.cassandralearning;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.serializers.CompositeSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.beans.AbstractComposite.ComponentEquality;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;

import org.apache.log4j.Logger;

import com.blogspot.oakgreen.cassandra.cassandralearning.domain.CompositeRKData;

public class CompositeRKMain extends GenericMain<Composite, CompositeRKData> {
	private static final Logger LOGGER = Logger
			.getLogger(CompositeRKMain.class);
	
	public static final String cfName = "composite_rk_data"; 

	public CompositeRKMain() {
		super(Composite.class, CompositeRKData.class);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// save();
		//get();
		//multiget();
		//save_ConstantValueInCompositeRK();
		//multiget_ConstantValueInCompositeRK();
		save_LessThan(Long.class);
		multiget_LessThan(Long.class);
	}

	private static void save() {
		CompositeRKMain main = new CompositeRKMain();
		int rowId = 10;
		Composite rk = getComposite(rowId, Long.class);
		CompositeRKData obj = new CompositeRKData(rk, "c" + rowId + "-1", "c"
				+ rowId + "-2", "c" + rowId + "-3");
		// obj.setC3(null);
		try {
			main.save(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void save_ConstantValueInCompositeRK() {
		CompositeRKMain main = new CompositeRKMain();
		
		for (int rowId = 101; rowId < 105; rowId++) {
			Composite rk = getComposite_ConstantValueInCompositeRK(rowId, String.class, 99);
			CompositeRKData obj = new CompositeRKData(rk, "c" + rowId + "-1",
					"c" + rowId + "-2", "c" + rowId + "-3");
			// obj.setC3(null);
			try {
				main.save(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void save_LessThan(Class<?> type) {
		CompositeRKMain main = new CompositeRKMain();
		
		for (int rowId = 201; rowId < 205; rowId++) {
			Composite rk = getComposite_SaveLessThan(rowId, type, 200);
			CompositeRKData obj = new CompositeRKData(rk, "c" + rowId + "-1",
					"c" + rowId + "-2", "c" + rowId + "-3");
			// obj.setC3(null);
			try {
				main.save(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void get() {
		CompositeRKMain main = new CompositeRKMain();
		int rowId = 1;
		Class<?> type = String.class;
		Composite rk = getComposite(rowId, type);
		// CompositeRKData obj = new CompositeRKData(rk,
		// "c"+rowId+"-1","c"+rowId+"-2","c"+rowId+"-3");
		// obj.setC3(null);
		try {
			CompositeRKData obj = main.get(rk);
			LOGGER.info("RowId-" + type.getSimpleName() + rowId + ", Obj-"
					+ obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void multiget() {
		CompositeRKMain main = new CompositeRKMain();
		List<Integer> rowIds = new ArrayList<Integer>();
		rowIds.add(3);
		rowIds.add(4);
		rowIds.add(6);
		
		Class<?> type = String.class;
		//Composite rk = getComposite(rowId, type);
		// CompositeRKData obj = new CompositeRKData(rk,
		// "c"+rowId+"-1","c"+rowId+"-2","c"+rowId+"-3");
		// obj.setC3(null);
		List<Composite> keyList = new ArrayList<Composite>();
		for (Integer i : rowIds) {
			keyList.add(getComposite(i, type));
		}
		
		try {
			QueryResult<Rows<Composite, String, String>> qr = main.executeMultiGetQuery(cfName, keyList);
			List<CompositeRKData> objList = main.parseQueryResultRows(qr.get());
			int i = 0;
			for (CompositeRKData obj : objList) {
				LOGGER.info("RowId-" + type.getSimpleName() + rowIds.get(i++)
						+ ", Obj-" + obj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void multiget_ConstantValueInCompositeRK() {
		CompositeRKMain main = new CompositeRKMain();
		List<Integer> rowIds = new ArrayList<Integer>();
		rowIds.add(110);
		rowIds.add(112);
		rowIds.add(113);
		
		Class<?> type = String.class;
		List<Composite> keyList = new ArrayList<Composite>();
		for (Integer i : rowIds) {
			keyList.add(getComposite_ConstantValueInCompositeRK(i, type, 99));
		}
		
		try {
			QueryResult<Rows<Composite, String, String>> qr = main.executeMultiGetQuery(cfName, keyList);
			List<CompositeRKData> objList = main.parseQueryResultRows(qr.get());
			int i = 0;
			for (CompositeRKData obj : objList) {
				LOGGER.info("RowId-" + type.getSimpleName() + rowIds.get(i++)
						+ ", Obj-" + obj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void multiget_LessThan(Class<?> type) {
		CompositeRKMain main = new CompositeRKMain();
		List<Integer> rowIds = new ArrayList<Integer>();
		rowIds.add(202);
		rowIds.add(203);
		rowIds.add(204);
		
		//Class<?> type = String.class;
		//Composite rk = getComposite(rowId, type);
		// CompositeRKData obj = new CompositeRKData(rk,
		// "c"+rowId+"-1","c"+rowId+"-2","c"+rowId+"-3");
		// obj.setC3(null);
		List<Composite> keyList = new ArrayList<Composite>();
//		for (Integer i : rowIds) {
//			keyList.add(getComposite_GetLessThan(i, type, 200));
//		}
		keyList.add(getComposite_GetLessThan(204, type, 200));
		
		try {
			QueryResult<Rows<Composite, String, String>> qr = main.executeMultiGetQuery(cfName, keyList);
			List<CompositeRKData> objList = main.parseQueryResultRows(qr.get());
			int i = 0;
			for (CompositeRKData obj : objList) {
				LOGGER.info("RowId-" + type.getSimpleName() + rowIds.get(i++)
						+ ", Obj-" + obj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private QueryResult<Rows<Composite, String, String>> executeMultiGetQuery(
			String columnFamily, List<Composite> keyList) {
		MultigetSliceQuery<Composite, String, String> multigetSlicesQuery = HFactory
				.createMultigetSliceQuery(keyspace, CompositeSerializer.get(),
						StringSerializer.get(), StringSerializer.get());
		multigetSlicesQuery.setColumnFamily(columnFamily);
		multigetSlicesQuery.setKeys(keyList);
		multigetSlicesQuery.setRange(null, null, false, Integer.MAX_VALUE);
		QueryResult<Rows<Composite, String, String>> qResults = multigetSlicesQuery
				.execute();
		return qResults;
	}

	// Done with testing
	private QueryResult<OrderedRows<Composite, String, String>> executeRangeQuery(
			String columnFamily, Composite startComposite,
			Composite endComposite, String secondaryIndex) {
		RangeSlicesQuery<Composite, String, String> rangeSlicesQuery = HFactory
				.createRangeSlicesQuery(keyspace, CompositeSerializer.get(),
						StringSerializer.get(), StringSerializer.get());
		rangeSlicesQuery.setColumnFamily(columnFamily);
		rangeSlicesQuery.setKeys(startComposite, endComposite);
		// if (secondaryIndex != null) {
		// rangeSlicesQuery.addEqualsExpression(DConst.SEC_ID,
		// ByteBuffer.wrap(secondaryIndex.getBytes()));
		// rangeSlicesQuery.setReturnKeysOnly();
		// } else {
		// rangeSlicesQuery.setRange(null, null, false, Integer.MAX_VALUE);
		// }
		QueryResult<OrderedRows<Composite, String, String>> result = rangeSlicesQuery
				.execute();
		return result;
	}

	private List<CompositeRKData> parseQueryResultRows(Rows<Composite, String, String> rows) {
		List<CompositeRKData> result = new ArrayList<CompositeRKData>();
		CompositeRKData obj = null;
		for (Row<Composite, String, String> row : rows) {
			ColumnSlice<String, String> cs = row.getColumnSlice();
			String c1 = cs.getColumnByName("c1") != null ? cs.getColumnByName(
					"c1").getValue() : null;
			String c2 = cs.getColumnByName("c2") != null ? cs.getColumnByName(
					"c2").getValue() : null;
			String c3 = cs.getColumnByName("c3") != null ? cs.getColumnByName(
					"c3").getValue() : null;
			obj = new CompositeRKData(row.getKey(), c1, c2, c3);
			result.add(obj);
			LOGGER.debug(obj);
		}
		return result;
	}

	private static Composite getComposite(int rowId, Class<?> type) {
		Composite c = new Composite();
		if (type.equals(String.class)) {
			c.addComponent(0, "p" + rowId + "-1", StringSerializer.get(),
					ComparatorType.UTF8TYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(1, "p" + rowId + "-2", StringSerializer.get(),
					ComparatorType.UTF8TYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(2, "p" + rowId + "-3", StringSerializer.get(),
					ComparatorType.UTF8TYPE.getTypeName(),
					ComponentEquality.EQUAL);
		} else if (type.equals(Long.class)) {
			c.addComponent(0, String.valueOf(rowId * 10 + 1),
					StringSerializer.get(),
					ComparatorType.LONGTYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(1, String.valueOf(rowId * 10 + 2),
					StringSerializer.get(),
					ComparatorType.LONGTYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(2, String.valueOf(rowId * 10 + 3),
					StringSerializer.get(),
					ComparatorType.LONGTYPE.getTypeName(),
					ComponentEquality.EQUAL);
		}
		return c;
	}
	
	private static Composite getComposite_ConstantValueInCompositeRK(int rowId, Class<?> type, long constantValue) {
		Composite c = new Composite();
		if (type.equals(String.class)) {
			c.addComponent(0, "p" + rowId + "-1", StringSerializer.get(),
					ComparatorType.UTF8TYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(1, String.valueOf(constantValue), StringSerializer.get(),
					ComparatorType.UTF8TYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(2, "p" + rowId + "-3", StringSerializer.get(),
					ComparatorType.UTF8TYPE.getTypeName(),
					ComponentEquality.EQUAL);
		} else if (type.equals(Long.class)) {
			c.addComponent(0, String.valueOf(rowId * 10 + 1),
					StringSerializer.get(),
					ComparatorType.LONGTYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(1, String.valueOf(constantValue),
					StringSerializer.get(),
					ComparatorType.LONGTYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(2, String.valueOf(rowId * 10 + 3),
					StringSerializer.get(),
					ComparatorType.LONGTYPE.getTypeName(),
					ComponentEquality.EQUAL);
		}
		return c;
	}
	
	private static Composite getComposite_SaveLessThan(int rowId, Class<?> type, int constVal) {
		Composite c = new Composite();
		//int constVal = 200;
		if (type.equals(String.class)) {
			c.addComponent(0, "p" + constVal, StringSerializer.get(),
					ComparatorType.UTF8TYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(1, "p" + constVal, StringSerializer.get(),
					ComparatorType.UTF8TYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(2, "p" + rowId, StringSerializer.get(),
					ComparatorType.UTF8TYPE.getTypeName(),
					ComponentEquality.EQUAL);
		} else if (type.equals(Long.class)) {
			c.addComponent(0, String.valueOf(constVal),
					StringSerializer.get(),
					ComparatorType.LONGTYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(1, String.valueOf(constVal),
					StringSerializer.get(),
					ComparatorType.LONGTYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(2, String.valueOf(rowId),
					StringSerializer.get(),
					ComparatorType.LONGTYPE.getTypeName(),
					ComponentEquality.EQUAL);
		}
		return c;
	}
	
	private static Composite getComposite_GetLessThan(int rowId, Class<?> type, int constVal) {
		Composite c = new Composite();
		//int constVal = 200;
		if (type.equals(String.class)) {
			c.addComponent(0, "p" + constVal, StringSerializer.get(),
					ComparatorType.UTF8TYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(1, "p" + constVal, StringSerializer.get(),
					ComparatorType.UTF8TYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(2, "p" + rowId, StringSerializer.get(),
					ComparatorType.UTF8TYPE.getTypeName(),
					ComponentEquality.EQUAL);
		} else if (type.equals(Long.class)) {
			c.addComponent(0, String.valueOf(constVal),
					StringSerializer.get(),
					ComparatorType.LONGTYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(1, String.valueOf(constVal),
					StringSerializer.get(),
					ComparatorType.LONGTYPE.getTypeName(),
					ComponentEquality.EQUAL);
			c.addComponent(2, String.valueOf(rowId),
					StringSerializer.get(),
					ComparatorType.LONGTYPE.getTypeName(),
					ComponentEquality.EQUAL);
		}
		return c;
	}
}
