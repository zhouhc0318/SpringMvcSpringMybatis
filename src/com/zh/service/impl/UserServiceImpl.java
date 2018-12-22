package com.zh.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zh.dao.IUserDao;
import com.zh.pojo.User;
import com.zh.service.IUserService;
import com.zh.util.annotation.DynamicDataSourceAnnotation;


@DynamicDataSourceAnnotation
@Service("userService")
public class UserServiceImpl implements IUserService {

	@Resource
    private IUserDao userDao;
	@Override
	public User getUserById(String id) {
		 return userDao.findUserById(id);
	}

}
