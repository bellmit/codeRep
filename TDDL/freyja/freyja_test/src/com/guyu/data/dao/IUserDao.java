package com.guyu.data.dao;

import org.freyja.cache.annotation.CacheSave;
import org.freyja.cache.annotation.CacheSet;
import org.freyja.cache.annotation.Caches;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import com.guyu.core.spring.aop.BeanSelfAware;
import com.guyu.data.bean.User;

public interface IUserDao extends IBaseDao, BeanSelfAware {

	@Caches(save = { @CacheSave(value = "User", key = "id"),
			@CacheSave(value = "User", key = "openId") })
	public User saveUser(User user);

	@Caching(put = { @CachePut(value = "User", key = "#user.id"),
			@CachePut(value = "User", key = "#user.openId") })
	public User updateUser(User user);

	/** 存储到数据库User */
	@Caching(put = { @CachePut(value = "User", key = "#user.id"),
			@CachePut(value = "User", key = "#user.openId") })
	public User storeUser(User user);

	@Cacheable(value = "User", key = "#openId")
	@CacheSet(value = "User", key = "id")
	public User getUserByOpenId(String openId);

	@Cacheable(value = "User", key = "#uid")
	@CacheSet(value = "User", key = "openId")
	public User getUser(Integer uid);

	@CacheEvict(value = "User", key = "#uid")
	public void cleanUserCache(Integer uid);

	@CacheEvict(value = "User", key = "#openId")
	public void cleanUserCacheByOpenId(String openId);

	@CacheEvict(value = "User", allEntries = true)
	public void cleanAll();

}
