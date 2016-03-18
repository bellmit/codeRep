package com.dengliang.myspringmvc.framework.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;


/**
 * 
 * 新架构共用组件和spring结合
 */
public  class MyDaoSupport extends MyJdbcTemplate {
	private static Logger LOGGER = Logger.getLogger(MyDaoSupport.class);

	/**
	 * 构造函数
	 * 
	 */
	public MyDaoSupport() {
	}

	/**
	 * 该方法用来更新数据库的资料
	 * 
	 * @param sql String SQL语句
	 * @return int 返回值为0时，表示更新失败
	 * @exception SQLException  数据库操作失败
	 */
	public int executeUpdate(final String sql) throws SQLException {
		return super.update(sql);
	}

	/**
	 * 根据输入的sql查询出结果集 结果将以List<Map.的方式返回
	 * 
	 * @param sql
	 * @param limit
	 * @return 结果集
	 */
	public List<Map<String, Object>> queryAllList(final String sql,
			final int limit) {
		super.setMaxRows(limit);
		return super.queryForList(sql);
	}

	/**
	 * 根据输入的sql查询出结果集，结果将以Map的方式返回，如果没有结果，返回null

	 * @param sql   Sql语句
	 * @return Map数组
	 * @throws SQLException 查询数据错误
	 * 
	 */
	public Map queryMap(final String sql) throws SQLException {
		super.setMaxRows(1);
		List<Map<String, Object>> listmap = this.queryForList(sql);
		if (listmap != null && listmap.size() == 1) {
			return listmap.get(0);
		}
		return null;
	}

	/**
	 * 根据传入的Sql进行查询，查询结果为Entity,如果没有结果，回传null;
	 * 
	 * @param sql String SQL语句
	 * @param index int 转换为对象的形式
	 * @return Object 查对对象
	 * @throws SQLException 查询数据库错误   转换对象错误
	 */
	@SuppressWarnings("unchecked")
	public Object queryObj(final String sql, final int index)
			throws SQLException, Exception {
		return super.query(sql, new ResultSetExtractor() {
			public Object extractData(ResultSet resultset) throws SQLException,
					DataAccessException {
				Object obj = null;
				try {
					if (resultset.next()) {
						switch (index) {
						case 1:
							obj = getObjFromRS1(resultset);
							break;
						case 2:
							obj = getObjFromRS2(resultset);
							break;
						case 3:
							obj = getObjFromRS3(resultset);
							break;
						case 4:
							obj = getObjFromRS4(resultset);
							break;
						case 5:
							obj = getObjFromRS5(resultset);
							break;
						case 6:
							obj = getObjFromRS6(resultset);
							break;
						case 7:
							obj = getObjFromRS7(resultset);
							break;
						default:
							obj = getObjFromRS1(resultset);
							break;
						}
					}
				} catch (Exception e) {
					LOGGER.fatal("queryObjPsmt error" + sql, e);
					throw new DBException("queryObjPsmt error" + sql, e);
				}
				return obj;
			}
		});
	}

	/**
	 * 根据传入的Sql进行查询，查询结果为Entity,如果没有结果，回传null;
	 * 
	 * @param object   Object 传入对象
	 * @param sql    String SQL语句
	 * @param index   int 对象转换方式
	 * @return Object 查询结果
	 * @throws SQLException   查询数据库错误
	 * @throws Exception   转换对象时出错
	 */
	@SuppressWarnings("unchecked")
	public Object queryObjPsmt(final Object object, final String sql,
			final int index) throws SQLException, Exception {
		return super.query(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				prepareQuery(pstmt, object);
			}
		}, new ResultSetExtractor() {
			public Object extractData(ResultSet resultset) throws SQLException,
					DataAccessException {
				Object obj = null;
				try {
					if (resultset.next()) {
						switch (index) {
						case 1:
							obj = getObjFromRS1(resultset);
							break;
						case 2:
							obj = getObjFromRS2(resultset);
							break;
						case 3:
							obj = getObjFromRS3(resultset);
							break;
						case 4:
							obj = getObjFromRS4(resultset);
							break;
						case 5:
							obj = getObjFromRS5(resultset);
							break;
						case 6:
							obj = getObjFromRS6(resultset);
							break;
						case 7:
							obj = getObjFromRS7(resultset);
							break;
						default:
							obj = getObjFromRS1(resultset);
							break;
						}
					}
				} catch (Exception e) {
					LOGGER.fatal("queryObjPsmt error" + sql, e);
					throw new DBException("queryObjPsmt error" + sql, e);
				}
				return obj;
			}
		});
	}

	/**
	 * 查询数据，返回为LIST
	 * 
	 * @param sql      String SQL语句
	 * @param index    int LIST中对象的转换方式
	 * @return List 查询结果
	 * @throws SQLException     查询错误
	 * @throws Exception     转换对象错误
	 */
	public List queryObjAll(final String sql, final int index)
			throws SQLException, Exception {
		return this.queryObjAll(sql, -1, index);
	}

	/**
	 * 查询数据
	 * 
	 * @param sql      SQL语句
	 * @param limit      数据库查询对象
	 * @param index      对象转换方式
	 * @return List 查询结果
	 * @throws SQLException        查询数据库错误
	 * @throws Exception     操作失败
	 */
	@SuppressWarnings("unchecked")
	public List queryObjAll(final String sql, final int limit, final int index)
			throws SQLException, Exception {
		return  (List)super.query(sql, new ResultSetExtractor() {
			public Object extractData(ResultSet resultset) throws SQLException,
					DataAccessException {
				List list = new ArrayList();
				Object obj = null;
				try {
					while (resultset.next()) {
						switch (index) {
						case 1:
							obj = getObjFromRS1(resultset);
							break;
						case 2:
							obj = getObjFromRS2(resultset);
							break;
						case 3:
							obj = getObjFromRS3(resultset);
							break;
						case 4:
							obj = getObjFromRS4(resultset);
							break;
						case 5:
							obj = getObjFromRS5(resultset);
							break;
						case 6:
							obj = getObjFromRS6(resultset);
							break;
						case 7:
							obj = getObjFromRS7(resultset);
							break;
						default:
							obj = getObjFromRS1(resultset);
							break;
						}
						list.add(obj);
					}

				} catch (Exception e) {
					LOGGER.fatal("queryObjAll error" + sql, e);
					throw new DBException("queryObjAll error" + sql, e);
				}
				return list;
			}
		});
	}

	/**
	 * 该方法用来将获取的ResultSet转变为相对应的VO，此方法需要DAO复写
	 * 
	 * @param resultset     ResultSet 查询结果集
	 * @return Object 转换后的对象
	 * @throws SQLException      操作数据库失败
	 * @throws Exception        操作失败
	 */
	protected Object getObjFromRS1(ResultSet resultset) throws SQLException,
			Exception {
		throw new UnsupportedOperationException(
				"getObjFromRS1 not Implemented yet !");
	}

	/**
	 * 该方法用来将获取的ResultSet转变为相对应的VO，此方法需要DAO复写
	 * 
	 * @param resultset       ResultSet 查询结果集
	 * @return Object 转换后的对象
	 * @throws SQLException     操作数据库失败
	 * @throws Exception       操作失败
	 */
	protected Object getObjFromRS2(ResultSet resultset) throws SQLException,
			Exception {
		throw new UnsupportedOperationException(
				"getObjFromRS2 not Implemented yet !");
	}

	/**
	 * 该方法用来将获取的ResultSet转变为相对应的VO，此方法需要DAO复写
	 * 
	 * @param resultset         ResultSet 查询结果集
	 * @return Object 转换后的对象
	 * @throws SQLException        操作数据库失败
	 * @throws Exception       操作失败
	 */
	protected Object getObjFromRS3(ResultSet resultset) throws SQLException,
			Exception {
		throw new UnsupportedOperationException(
				"getObjFromRS3 not Implemented yet !");
	}

	/**
	 * 该方法用来将获取的ResultSet转变为相对应的VO，此方法需要DAO复写
	 * 
	 * @param resultset
	 *            ResultSet 查询结果集
	 * @return Object 转换后的对象
	 * @throws SQLException
	 *             操作数据库失败
	 * @throws Exception
	 *             操作失败
	 */
	protected Object getObjFromRS4(ResultSet resultset) throws SQLException,
			Exception {
		throw new UnsupportedOperationException(
				"getObjFromRS4 not Implemented yet !");
	}

	/**
	 * 该方法用来将获取的ResultSet转变为相对应的VO，此方法需要DAO复写
	 * 
	 * @param resultset       ResultSet 查询结果集
	 * @return Object 转换后的对象
	 * @throws SQLException         操作数据库失败
	 * @throws Exception        操作失败
	 */
	protected Object getObjFromRS5(ResultSet resultset) throws SQLException,
			Exception {
		throw new UnsupportedOperationException(
				"getObjFromRS5 not Implemented yet !");
	}

	/**
	 * 该方法用来将获取的ResultSet转变为相对应的VO，此方法需要DAO复写
	 * 
	 * @param resultset       ResultSet 查询结果集
	 * @return Object 转换后的对象
	 * @throws SQLException      操作数据库失败
	 * @throws Exception        操作失败
	 */
	protected Object getObjFromRS6(ResultSet resultset) throws SQLException,
			Exception {
		throw new UnsupportedOperationException(
				"getObjFromRS6 not Implemented yet !");
	}

	/**
	 * 该方法用来将获取的ResultSet转变为相对应的VO，此方法需要DAO复写
	 * 
	 * @param resultset      ResultSet 查询结果集
	 * @return Object 转换后的对象
	 * @throws SQLException          操作数据库失败
	 * @throws Exception       操作失败
	 */
	protected Object getObjFromRS7(ResultSet resultset) throws SQLException,
			Exception {
		throw new UnsupportedOperationException(
				"getObjFromRS7 not Implemented yet !");
	}

	/* 以下是为了进行预处理语句进行的操作 */
	/**
	 * 新增预处理语句定义，由子类覆写
	 * 
	 * @return 新增预处理的SQL语句
	 * @throws SQLException        新增错误
	 */
	protected String getCreatePreSql() throws SQLException {
		throw new UnsupportedOperationException(
				"getCreatePreSql not Implemented yet ! (executing addBatch..)");
	}

	/**
	 * 修改预处理语句定义，由子类覆写
	 * 
	 * @return 修改的预处理语句
	 * @throws SQLException      操作失败
	 */
	protected String getUpdatePreSql() throws SQLException {
		throw new UnsupportedOperationException(
				"getUpdatePreSql not Implemented yet ! (executing addBatch..)");
	}

	/**
	 * 删除预处理语句定义，由子类覆写
	 * 
	 * @return 删除预处理语句
	 * @throws SQLException       操作失败
	 */
	protected String getDeletePreSql() throws SQLException {
		throw new UnsupportedOperationException(
				"getDeletePreSql not Implemented yet ! (executing addBatch..)");
	}

	/**
	 * 查询预处理语句定义，由子类覆写
	 * 
	 * @return 查询预处理的SQL语句
	 * @throws SQLException         查询数据错误
	 */
	protected String getQueryPreSql() throws SQLException, Exception {

		throw new UnsupportedOperationException(
				"getDeletePreSql not Implemented yet ! (executing addBatch..)");
	}

	/**
	 * 新增预处理语句值的定义，由子类覆写
	 * 
	 * @param obj       新增对象
	 * @throws SQLException         操作失败
	 */
	protected void prepareCreate(PreparedStatement pstmt, Object obj)
			throws SQLException{
		throw new UnsupportedOperationException(
				"prepareCreate not Implemented yet ! (executing addBatch..)");

	}

	/**
	 * 修改预处理语句值的定义，由子类覆写
	 * 
	 * @param obj     修改对象
	 * @throws SQLException    操作失败
	 */
	protected void prepareUpdate(PreparedStatement pstmt, Object obj)
			throws SQLException {
		throw new UnsupportedOperationException(
				"prepareUpdate not Implemented yet ! (executing addBatch..)");

	}

	/**
	 * 删除预处理语句值的定义，由子类覆写
	 * 
	 * @param obj        删除对象
	 * @throws SQLException   操作失败
	 */
	protected void prepareDelete(PreparedStatement pstmt, Object obj)
			throws SQLException{
		throw new UnsupportedOperationException(
				"prepareDelete not Implemented yet ! (executing addBatch..)");

	}

	/**
	 * 查询预处理语句值的定义，由子类覆写
	 * 
	 * @param obj       查询对象
	 * @throws SQLException           操作失败
	 */
	protected void prepareQuery(PreparedStatement pstmt, Object obj)
			throws SQLException {
		throw new UnsupportedOperationException(
				"prepareDelete not Implemented yet ! (executing addBatch..)");

	}

	/**
	 * 批处理-新增方法
	 * 
	 * @param list
	 * @throws SQLException
	 */
	public int[] createBatch(final List list) throws SQLException {
		String sql=this.getCreatePreSql();
		return super.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt, int i) throws SQLException{
					prepareCreate(pstmt, list.get(i));
			}
			@Override
			public int getBatchSize() {
		            return list.size();
		    }
		});
	}
	/**
	 * 批处理-更新
	 * 
	 * @param list
	 * @throws SQLException
	 */
	public int[] updateBatch(final List list) throws SQLException {
		String sql=this.getUpdatePreSql();
		return super.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt, int i) throws SQLException{
					prepareUpdate(pstmt, list.get(i));
			}
			@Override
			public int getBatchSize() {
		            return list.size();
		    }
		});
	}
	/**
	 * 批处理-删除
	 * 
	 * @param list
	 * @throws SQLException
	 */
	public int[] deleteBatch(final List list) throws SQLException {
		String sql=this.getDeletePreSql();
		return super.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt, int i) throws SQLException{
					prepareDelete(pstmt, list.get(i));
			}
			@Override
			public int getBatchSize() {
		            return list.size();
		    }
		});
	}
}
