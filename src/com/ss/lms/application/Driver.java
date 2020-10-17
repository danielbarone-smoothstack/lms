package com.ss.lms.application;

import com.ss.lms.dao.AuthorDAO;
import com.ss.lms.entity.Author;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.ss.lms.constants.Constants;

public class Driver {

	public static Integer entryPrompt() {
		Scanner scan = new Scanner(System.in);

		System.out.println(Constants.MENU_LINE_BREAK);
	    System.out.println(Constants.WELCOME_MESSAGE);
	    System.out.println(Constants.LINE_BREAK);
	    System.out.println(Constants.USER_TYPES);
		System.out.println(Constants.LINE_BREAK);
		System.out.print(Constants.MAKE_SELECTION);

		String userType = scan.nextLine();
		int selection;
		try {
			selection = Integer.parseInt(userType);
			return selection;
		} catch (Exception e) {
			if (userType.equals("quit")) {
				System.out.println(Constants.QUIT_MESSAGE);
				return -1;
			}
			return 0;
		}
	}

	public static void main(String[] args) {

		HashMap<Integer, BaseUserMenu> userTypes = new HashMap<Integer, BaseUserMenu>();

		userTypes.put(1, new BorrowerMenu());
		
		boolean exitLMS = false;
		do {
			int userType = entryPrompt();
			
			switch(userType) {
			case -1:
				exitLMS = true;
				break;
			case 0:
				System.out.println(Constants.INCORRECT_INPUT);
			default:
				if (userType >= 1 && userType <= userTypes.size()) {
					boolean continueLMS = userTypes.get(userType).driver();
					if (continueLMS == false) {
						System.out.println(Constants.QUIT_MESSAGE);
						exitLMS = true;
						break;
					}
				} else {
					System.out.println(Constants.INCORRECT_INPUT);
				}
			}
			
		} while (exitLMS == false);
		
	}
}
