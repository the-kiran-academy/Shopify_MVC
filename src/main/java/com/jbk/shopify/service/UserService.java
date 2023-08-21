package com.jbk.shopify.service;

import java.util.List;

import com.jbk.shopify.entity.User;

public interface UserService {
	
	public Object  loginProcess(User user);
	public boolean addUser(User user);
	public boolean deleteUser(String usename);
	public User getUser(String username);
	public boolean updateUser(User user);
	public List<User> getAllUser();

}
