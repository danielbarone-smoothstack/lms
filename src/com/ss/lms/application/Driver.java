package com.ss.lms.application;

import java.util.HashMap;
import java.util.Scanner;

import com.ss.lms.constants.Constants;

public class Driver {

	public static void entryPrompt(Scanner scan) {
		HashMap<Integer, BaseUserMenu> userTypes = new HashMap<Integer, BaseUserMenu>();
		
		userTypes.put(1, new LibrarianMenu(scan, 0));
		userTypes.put(2, new AdministratorMenu(scan, 0));
		userTypes.put(3, new BorrowerMenu(scan, 0));

		boolean exitLMS = false;
		do {
			BaseUserMenu.printMainMenu();
			String userType = scan.nextLine();
			int selection;
			try {
				selection = Integer.parseInt(userType);
				if (selection >= 1 && selection <= userTypes.size()) {
					exitLMS = !userTypes.get(selection).driver();
				} else {
					System.out.println(Constants.INCORRECT_INPUT);
				}
			} catch (Exception e) {
				if (userType.equals("quit")) {
					exitLMS = true;
				}
			}
		} while (exitLMS == false);
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		entryPrompt(scan);
		System.out.println(Constants.QUIT_MESSAGE);
		scan.close();
	}
}
