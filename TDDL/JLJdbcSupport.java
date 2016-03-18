package com.jl.net.framework.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class JLJdbcSupport extends JdbcTemplate {
    public Connection getConnect() throws SQLException{
    	//return this.getConnect();
    	return this.getDataSource().getConnection();
    }
}
