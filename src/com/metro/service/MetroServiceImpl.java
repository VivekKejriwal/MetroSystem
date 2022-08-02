package com.metro.service;

import java.util.List;

import com.metro.beans.User;
import com.metro.persistence.MetroPersistence;
import com.metro.persistence.MetroPersistenceImpl;

public class MetroServiceImpl implements MetroService{

	MetroPersistence metroPersistence = new MetroPersistenceImpl();

	@Override
	public boolean userExists(int id) {
		List<Integer> users = metroPersistence.getAllUsers();
		for (Integer user_id : users)
			if (user_id == id)
				return true;

		return false;
	}

	@Override
	public boolean setUserDetails(User user) {
		return metroPersistence.createUser(user);
	}


}
