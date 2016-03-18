package com.guyu.data.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.freyja.jdbc.core.FreyjaJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FreyjaBaseDaoImpl implements ICoreBaseDao {

	@Autowired
	private FreyjaJdbcTemplate freujaTemplate;
	
	private static Logger logger = Logger.getLogger(FreyjaBaseDaoImpl.class);
	
	@Override
	public Object get(String hql, Object... args) {
		List list = freujaTemplate.query(hql, args);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public List<Map<String, Object>> queryForMap(String hql, Object... args) {
		return freujaTemplate.queryForMap(hql, args);
	}

	@Override
	public <T> T save(final T t) {
		return freujaTemplate.save(t);
	}

	@Override
	public <T> void update(final T t) {

		freujaTemplate.update(t);
	}

	@Override
	public <T> void delete(T entity) {
		freujaTemplate.delete(entity);

	}

	@Override
	public List find(String hql, Object... args) {
		return freujaTemplate.query(hql, args);
	}

	@Override
	public <T> List<T> find(Class<T> clazz) {
		return freujaTemplate.query("select * from " + clazz.getSimpleName());
	}

	@Override
	public <T> T get(Class<T> clazz, Serializable id) {
		return freujaTemplate.get(clazz, id);
	}

	@Override
	public <T> List<T> find(Class<T> clazz, String where, Object... values) {
		List<T> list = freujaTemplate.query(
				"select * from " + clazz.getSimpleName() + " where " + where,
				values);
		return list;
	}

	@Override
	public <T> T get(Class<T> clazz, String where, Object... values) {
		List list = find(clazz, where, values);
		if (list.size() == 0) {
			return null;
		}
		return (T) list.get(0);
	}

	@Override
	public void executeUpdate(String hql, Object... args) {
		freujaTemplate.execute(hql, args);
	}

	@Override
	public <T> void batchSave(List<T> list) {
		try{
		freujaTemplate.batchSave(list);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public <T> void batchUpdate(List<T> list) {
		freujaTemplate.batchUpdate(list);
	}

}