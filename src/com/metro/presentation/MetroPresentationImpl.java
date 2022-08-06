package com.metro.presentation;

import java.time.LocalDateTime;
import java.util.Scanner;

import com.metro.beans.User;
import com.metro.exceptions.InsufficientBalance;
import com.metro.exceptions.InvalidStationException;
import com.metro.service.MetroService;
import com.metro.service.MetroServiceImpl;

public class MetroPresentationImpl implements MetroPresentation {
	private MetroService metroService = new MetroServiceImpl();

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
			int user_id = Integer.parseInt(sc.nextLine());
			if (metroService.userExists(user_id)) {
				System.out.println("User Already Exist");
				break;
			}
			user.setUserId(user_id);
			System.out.println("Enter your first name:");
			user.setFirstName(sc.nextLine());
			System.out.println("Enter your last name");
			user.setLastName(sc.nextLine());
			System.out.println("Enter your address");
			user.setAddress(sc.nextLine());
			System.out.println("Enter your phone number");
			user.setPhoneNumber(Long.parseLong(sc.nextLine()));

			if (metroService.setUserDetails(user)) {
				System.out.println("Successfuly created new metro card");
				System.out.println("Your card id is: " + user.getCard().getCardId());// user.getCard().getCardId());
			} else {
				System.out.println("Something went wrong!!");
			}

			break;
		case 2:
			System.out.println("Enter your User id");
			int userId = Integer.parseInt(sc.nextLine());
			if (!metroService.userExists(userId)) {
				System.out.println("Invalid User Id");
				break;
			}
			try {
				System.out.println("Enter source station");
				String src = sc.nextLine();
				LocalDateTime swipeInTime =  metroService.swipeIn(src, userId);
				System.out.println("You have successfully swiped in at the station " + src);
				System.out.println("Enter destination station");
				String dest = sc.nextLine();
				if(!metroService.swipeOut(src,dest,userId,swipeInTime))
					System.out.println("Swipe Out Failed! Please Swipe In again!");
				else
					System.out.println("You have successfully swiped out with card balance as "+metroService.getBalance(userId));

			} catch (InsufficientBalance | InvalidStationException e) {
				System.out.println(e.getMessage());
			}

			break;
		case 3:
			System.out.println("Enter your User id");
			int UID = Integer.parseInt(sc.nextLine());
			if (!metroService.userExists(UID)) {
				System.out.println("Invalid User Id");
				break;
			}
			System.out.println("Enter amount you want to add");
			double amount = Double.parseDouble(sc.nextLine());
			if(amount<=0){
				System.out.println("Invalid Amount!! Amount must be positive");
				break;
			}
			if(metroService.addBalance(UID,amount)) {
				System.out.println("Balance added Successfully!");
				System.out.println("Your current balance is "+metroService.getBalance(UID));
			}
			else {
				System.out.println("Something went wrong!");
			}

			break;
		case 4:
			System.out.println("Enter your User id");
			int uid = Integer.parseInt(sc.nextLine());
			if (!metroService.userExists(uid)) {
				System.out.println("Invalid User Id");
				break;
			}
			System.out.println("Current balance of your card is: " + metroService.getBalance(uid));
			break;
		case 5:
			System.out.println("Thank you for using public metro for transport");
			System.exit(0);
		default:
			System.out.println("Sorry you have entered the wrong choice");
			break;
		}

	}

}
