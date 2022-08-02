package com.metro.client;

import java.util.Scanner;

import com.metro.presentation.MetroPresentation;
import com.metro.presentation.MetroPresentationImpl;

public class ClientMain {

	public static void main(String[] args) {
		MetroPresentation metroPresentation = new MetroPresentationImpl();
		Scanner sc = new Scanner(System.in);
		while (true) {
			metroPresentation.chooseMenu();
			int choice = sc.nextInt();
			metroPresentation.menu(choice);
		}
	}

}
