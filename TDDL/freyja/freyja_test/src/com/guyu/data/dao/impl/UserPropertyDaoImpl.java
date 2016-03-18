package com.guyu.data.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.guyu.data.bean.UserProperty;
import com.guyu.data.dao.IUserPropertyDao;

@Repository
public class UserPropertyDaoImpl extends BaseDaoImpl implements
		IUserPropertyDao {

	private IUserPropertyDao userPropertyDao;

	@Override
	public void setSelf(Object proxyBean) {
		this.userPropertyDao = (IUserPropertyDao) proxyBean;
	}

	@Override
	public UserProperty getUserProperty(Integer pid, Integer uid) {

		List<UserProperty> list = userPropertyDao.getUserPropertys(uid);

		for (UserProperty up : list) {

			if (up.getPid() == pid.intValue()) {
				return up;
			}

		}
		return null;

		// UserProperty up = super.get(UserProperty.class,
		// "uid = ? and pid = ?",
		// uid, pid);
		//
		// return up;
	}

	@Override
	public UserProperty updateUserProperty(UserProperty up) {
		// 保存在缓存中，不update到数据库 集中update

		up.setVersion(1);
		super.update(up);
		return up;
	}

	@Override
	public List<UserProperty> getUserPropertys(Integer uid) {

		List<UserProperty> list = super
				.find(UserProperty.class, "uid = ?", uid);
		return list;
	}

	@Override
	public UserProperty saveUserProperty(UserProperty up) {

		super.save(up);

		return up;
	}

}