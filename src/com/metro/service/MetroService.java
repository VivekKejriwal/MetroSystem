package com.metro.service;

import com.metro.beans.User;

public interface MetroService {
	public boolean userExists(int id);
	public boolean setUserDetails(User user);
}
