package com.metro.presentation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.metro.beans.User;
import com.metro.exceptions.InsufficientBalance;
import com.metro.exceptions.InvalidStationException;
import com.metro.service.MetroService;
import com.metro.service.MetroServiceImpl;

public class MetroPresentationImpl implements MetroPresentation {
	private MetroService metroService = new MetroServiceImpl();

	@Override
	public void login() {
		System.out.println("-----------Welcome to the Metro System User Login Page-----------");
		System.out.println("1. Sign up if you are a new user");
		System.out.println("2. Log in if already an exsting user");
		System.out.println("3. To exit");
	}
	
	@Override
	public void loginMenu(int choice) {
		Scanner sc = new Scanner(System.in);
		switch (choice) {
		case 1:
			User user = new User();
			user.setUserId(0);
			System.out.println("Enter your first name:");
			user.setFirstName(sc.nextLine());
			System.out.println("Enter your last name");
			user.setLastName(sc.nextLine());
			System.out.println("Enter your email");
			user.setEmail(sc.nextLine());
			if(metroService.emailCheck(user.getEmail())) {
				System.out.println("Email already exists, please login or sign up with a different email");
				break;
			}
			System.out.println("Enter your address");
			user.setAddress(sc.nextLine());
			System.out.println("Enter your phone number");
			user.setPhoneNumber(Long.parseLong(sc.nextLine()));
			System.out.println("Enter new password");
			user.setPassword(sc.nextLine());

			if (metroService.setUserDetails(user)) {
				System.out.println("Successfuly created new metro card");
				System.out.println("Your card id is: " + user.getCard().getCardId());
			} else {
				System.out.println("Something went wrong!!");
			}

			break;
		case 2:
			System.out.println("Enter your email");
			String email=sc.nextLine();
			System.out.println("Enter your password");
			String pass=sc.nextLine();
			if(!metroService.checkCredentials(email, pass)) {
				System.out.println("Wrong credentials, please enter correct email or password");
			}
			else {
				System.out.println("Successfully logged in.");
				int userId=metroService.getUserId(email,pass);
				while(chooseMenu(userId)!=4);
			}
			break;
		case 3:
			System.out.println("Thank you for using public metro for transport");
			System.exit(0);
		default:
			System.out.println("Sorry you have entered the wrong choice");
			break;
			
		}
	}
	
	
	/*=======================================Metro main menu================================================*/
	
	
	@Override
	public int chooseMenu(int userId) {
		System.out.println("---------Welcome to Metro Services---------");
		System.out.println("1. Swipe in to the station");
		System.out.println("2. Add balance to you card account");
		System.out.println("3. Check balance of you card account");
		System.out.println("4. To log out");
		System.out.println("-------Enter your choice-------");
		
		Scanner sc=new Scanner(System.in);
		return menu(Integer.parseInt(sc.nextLine()),userId);
	}

	@Override
	public int menu(int choice,int userId) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		switch (choice) {
		case 1:
			if (!metroService.userExists(userId)) {
				System.out.println("Invalid User Id");
				break;
			}
			try {
				List<String> stations = metroService.getAllStations();
				System.out.println("Enter source station from the list below");
				stations.stream().forEach(System.out::println);
				String src = sc.nextLine();
				LocalDateTime swipeInTime =  metroService.swipeIn(src, userId);
				System.out.println("You have successfully swiped in at the station " + src);
				System.out.println("Enter destination station from the list below. Destination must be ahead of source");
				stations.stream().forEach(System.out::println);
				String dest = sc.nextLine();
				
				while(stations.indexOf(dest)<stations.indexOf(src)) {
					System.out.println("Destination must be ahead of source. Please enter correct destination again!");
					dest = sc.nextLine();
				}
				if(!metroService.swipeOut(src,dest,userId,swipeInTime))
					System.out.println("Swipe Out Failed! Please Swipe In again!");
				else
					System.out.println("You have successfully swiped out with card balance as "+metroService.getBalance(userId));

			} catch (InsufficientBalance | InvalidStationException e) {
				System.out.println(e.getMessage());
			}

			break;
		case 2:
			if (!metroService.userExists(userId)) {
				System.out.println("Invalid User Id");
				break;
			}
			System.out.println("Enter amount you want to add");
			double amount = Double.parseDouble(sc.nextLine());
			if(amount<=0){
				System.out.println("Invalid Amount!! Amount must be positive");
				break;
			}
			if(metroService.addBalance(userId,amount)) {
				System.out.println("Balance added Successfully!");
				System.out.println("Your current balance is "+metroService.getBalance(userId));
			}
			else {
				System.out.println("Something went wrong!");
			}

			break;
		case 3:
			if (!metroService.userExists(userId)) {
				System.out.println("Invalid User Id");
				break;
			}
			System.out.println("Current balance of your card is: " + metroService.getBalance(userId));
			break;
		case 4:
			System.out.println("Successfully logged out");
			return 4;
		default:
			System.out.println("Sorry you have entered the wrong choice");
			break;
		}

		return 0;
	}

}
