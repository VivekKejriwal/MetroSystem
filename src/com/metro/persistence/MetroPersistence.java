package com.metro.persistence;

import java.util.List;

import com.metro.beans.User;

public interface MetroPersistence {

	public List<User> getAllUsers();
	public boolean createUser(User user);
}
