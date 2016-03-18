package com.guyu.core.spring.datasource;

import org.freyja.jdbc.ds.DbContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {

		Integer dbNo = DbContextHolder.getDbNum();
//		DbContextHolder.setDbNum(-1);
		return dbNo;
	}

}