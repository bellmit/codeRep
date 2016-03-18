package com.guyu.data.core.dao;

import org.freyja.jdbc.object.DbResult;
import org.freyja.jdbc.sharding.FreyjaShardingStrategy;

public class MyShardingStrategy extends FreyjaShardingStrategy {

	@Override
	public DbResult getShardingTableName(String tableName, Object value) {

		return super.getShardingTableName(tableName, value);
	}

	@Override
	public DbResult getShardingTableNameById(String tableName, Object idValue) {
		return super.getShardingTableNameById(tableName, idValue);
	}
}
