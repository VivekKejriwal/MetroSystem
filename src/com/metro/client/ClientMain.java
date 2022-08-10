package com.metro.client;

import java.util.Scanner;

import com.metro.presentation.MetroPresentation;
import com.metro.presentation.MetroPresentationImpl;

public class ClientMain {

	public static void main(String[] args) {
		MetroPresentation metroPresentation = new MetroPresentationImpl();
		Scanner sc = new Scanner(System.in);
		System.out.println("------------Welcome to Metro service----------");
		while (true) {
			metroPresentation.chooseMenu();
			int choice = 0;
			try {
				choice = Integer.parseInt(sc.next());
			} catch (Exception e) {
				System.out.println("Invalid input! please enter a number.");
				System.out.println();
				continue;
			}
			metroPresentation.menu(choice);
		}
	}

}
