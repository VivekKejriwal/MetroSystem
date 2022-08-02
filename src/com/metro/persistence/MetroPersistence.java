package com.metro.persistence;

import java.util.List;

import com.metro.beans.User;

public interface MetroPersistence {

	public List<Integer> getAllUsers();
	public boolean createUser(User user);
}
