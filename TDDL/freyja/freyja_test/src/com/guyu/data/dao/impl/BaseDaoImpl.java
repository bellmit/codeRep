package com.guyu.data.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.guyu.data.core.dao.FreyjaBaseDaoImpl;
import com.guyu.data.dao.IBaseDao;

@Repository("baseDao")
public class BaseDaoImpl extends FreyjaBaseDaoImpl implements IBaseDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> findBySql(String sql) {

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		return list;
	}

}