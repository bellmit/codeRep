package com.guyu.data.dao;

import java.util.List;

import org.freyja.cache.annotation.CacheListAdd;
import org.freyja.cache.annotation.CacheListReplace;
import org.freyja.cache.annotation.CacheSave;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.guyu.core.spring.aop.BeanSelfAware;
import com.guyu.data.bean.UserProperty;

public interface IUserPropertyDao extends IBaseDao, BeanSelfAware {

	@CacheListReplace(value = "UserProperty", key = "#up.uid+'list'")
	@CachePut(value = "UserProperty", key = "#up.pid + 'b' + #up.uid")
	public UserProperty updateUserProperty(UserProperty up);

	@CacheListAdd(value = "UserProperty", key = "#up.uid+'list'")
	@CacheSave(value = "UserProperty", key = "pid + 'b' + uid ")
	public UserProperty saveUserProperty(UserProperty up);

	@Cacheable(value = "UserProperty", key = "#uid+'list'")
	public List<UserProperty> getUserPropertys(Integer uid);

	@Cacheable(value = "UserProperty", key = "#pid + 'b' + #uid")
	public UserProperty getUserProperty(Integer pid, Integer uid);

}
