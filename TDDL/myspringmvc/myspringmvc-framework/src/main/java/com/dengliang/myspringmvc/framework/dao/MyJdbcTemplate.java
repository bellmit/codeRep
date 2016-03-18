package com.dengliang.myspringmvc.framework.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class MyJdbcTemplate extends JdbcTemplate{
	/**
	 * 注入数据源(本意是只接收taobaodatasource,但为了结合不用tddl,采用此方法)
	 * @param tgroupds TGroupDataSource
	 * @throws SQLException
	 */
	public void setTgroupds(DataSource tgroupds) throws SQLException {
		super.setDataSource(tgroupds);
	}
}
