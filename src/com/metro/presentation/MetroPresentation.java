package com.metro.presentation;

public interface MetroPresentation {
	void login();
	void loginMenu(int choice);
	int chooseMenu(int userId);
	int menu(int choice, int userId);
}
