package com.metro.presentation;

import java.util.Scanner;

import com.metro.beans.User;
import com.metro.exceptions.InsufficientBalance;
import com.metro.exceptions.MyException;
import com.metro.service.MetroService;

public class MetroPresentation implements MetroPresentationInterface {
	MetroService metroService;

	@Override
	public void chooseMenu() {
		// TODO Auto-generated method stub
		System.out.println("1. Register here if you are a new user");
		System.out.println("2. Swipe in to the station");
		System.out.println("3. Add balance to you card account");
		System.out.println("4. Check balance of you card account");
		System.out.println("5. To checkout");
	}

	@Override
	public void menu(int choice) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		switch (choice) {
		case 1:
			User user = new User();
			System.out.println("Enter id");
			user.setUserId(Integer.parseInt(sc.next()));
			System.out.println("Enter your first name:");
			user.setFirstName(sc.next());
			System.out.println("Enter your last name");
			user.setLastName(sc.next());
			System.out.println("Enter your address");
			user.setAddress(sc.next());
			System.out.println("Enter your phone number");
			user.setPhoneNumber(Long.parseLong(sc.next()));

			System.out.println("Successfuly created new metro card");
			System.out.println("Your card id is: ");// user.getCard().getCardId());
			
			break;
		case 2:
			System.out.println("Enter your metro card id");
			int metroId = sc.nextInt();
			boolean check = metroService.checkId(metroId);
			if (check) {
				boolean balanceCheck = metroService.checkBalance(metroId);
				try {
					if (balanceCheck) {
						System.out.println("Enter source station");
						String st=sc.next();
						boolean station=metroService.swipeIn(st);
						if(station) {
							System.out.println("You have successfully swiped in at the station "+st);
						}else {
							System.out.println("Wrong input, Station does not exist");
						}
					} else {
						throw new InsufficientBalance();
					}
				} catch (InsufficientBalance insufficientBalance) {
					System.out.println(insufficientBalance.getMessage());
				}
			} else {
				System.out.println("Wrong id inserted");
			}
			
			break;
		case 3:
			System.out.println("Enter your metro card id");

			break;
		case 4:
			User userbalance = new User();
			System.out.println("Enter your metro card id");
			System.out.println("Current balance of your card is: " + userbalance.getCard().getBalance());
			break;
		case 5:
			System.out.println(" ");
			System.exit(0);
		default:
			System.out.println("Sorry you have entered the wrong choice");
			break;
		}

	}

}
