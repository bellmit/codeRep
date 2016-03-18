package com.guyu.data.dao.impl;

import org.springframework.stereotype.Repository;

import com.guyu.data.bean.User;
import com.guyu.data.dao.IUserDao;

@Repository
public class UserDaoImpl extends BaseDaoImpl implements IUserDao {

	private IUserDao userDao;

	@Override
	public void setSelf(Object proxyBean) {
		this.userDao = (IUserDao) proxyBean;
	}

	@Override
	public User saveUser(User user) {
		super.save(user);
		return user;
	}

	@Override
	public User getUserByOpenId(String openId) {

		return super.get(User.class, "openId = ?", openId);
	}

	@Override
	public User updateUser(User user) {
		// super.update(user);
		return user;
	}

	@Override
	public User storeUser(User user) {
		super.update(user);
		return user;
	}

	@Override
	public User getUser(Integer uid) {
		User u = super.get(User.class, uid);
		return u;
	}

	@Override
	public void cleanUserCache(Integer uid) {

	}

	@Override
	public void cleanUserCacheByOpenId(String openId) {

	}

	@Override
	public void cleanAll() {

	}

}