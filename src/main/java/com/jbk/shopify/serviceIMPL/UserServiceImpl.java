package com.jbk.shopify.serviceIMPL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbk.shopify.dao.UserDao;
import com.jbk.shopify.entity.User;
import com.jbk.shopify.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao dao;

	@Override
	public Object loginProcess(User user) {

		return dao.loginProcess(user);
	}

	@Override
	public boolean addUser(User user) {

		return dao.addUser(user);
	}

	@Override
	public boolean deleteUser(String usename) {
		return dao.deleteUser(usename);
	}

	@Override
	public User getUser(String username) {
		return dao.getUser(username);
	}

	@Override
	public boolean updateUser(User user) {

		return dao.updateUser(user);
	}

	@Override
	public List<User> getAllUser() {
		
		return dao.getAllUser();
	}

}
