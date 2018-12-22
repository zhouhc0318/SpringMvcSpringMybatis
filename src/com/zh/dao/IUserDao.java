package com.zh.dao;

import com.zh.pojo.User;

public interface IUserDao {
	User findUserById(String id);
}
