package com.guyu.data.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface ICoreBaseDao {

	<T> T get(Class<T> clazz, Serializable id);

	<T> T get(Class<T> clazz, String where, Object... args);

	Object get(String hql, Object... args);

	<T> T save(T entity);

	<T> void update(T entity);

	<T> void delete(T entity);

	List find(String hql, Object... args);

	List<Map<String, Object>> queryForMap(String hql, Object... args);

	<T> List<T> find(Class<T> clazz);

	<T> List<T> find(Class<T> clazz, String where, Object... values);

	void executeUpdate(String hql, Object... args);

	public <T> void batchSave(List<T> list);

	public <T> void batchUpdate(List<T> list);

}
