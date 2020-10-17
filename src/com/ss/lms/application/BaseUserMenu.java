package com.ss.lms.application;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.Callable;

import com.ss.lms.constants.Constants;

public abstract class BaseUserMenu {
	
	String userType;
	
	BaseUserMenu(String userType) {
		this.userType = Constants.getColor("header", userType);
	}

	public void getSelection() {
		System.out.println(Constants.LINE_BREAK);
		System.out.println(Constants.MAKE_SELECTION);
	}
	
	public void printMenu() {
		System.out.println("\n");
		System.out.println(Constants.MENU_LINE_BREAK);
		System.out.println(userType + " " + Constants.MENU);
		System.out.println(Constants.LINE_BREAK);
	}
	
	public abstract boolean driver();

	public String promptOptions(String[] options) {
		Scanner scan = new Scanner(System.in);
		do {
			int option;
			for (option = 1; option <= options.length; option++) {
				System.out.println(option + ") " + options[option]);
			}

			if (option == 1) {
				System.out.println(Constants.NO_DATA);
			}

			// Set previous menu option
			int prevMenuOption = option;
			System.out.println(prevMenuOption + ") " + Constants.PREVIOUS_MENU);

			// Grab selection
			String selectionString = scan.nextLine();

			// Ensure selection is integer
			Integer selection;
			try {
				selection = Integer.parseInt(selectionString);
			} catch (Exception e) {
				if (selectionString.equals("quit")) {
					return "false";
				}
				System.out.println(Constants.INCORRECT_INPUT);
				continue;
			}

			// Check go previous menu
			if (selection == prevMenuOption) {
				System.out.println(Constants.RETURNING);
				return "true";

			// Proceed w/ a function
			} else if (selection >= 1 && selection < options.length + 2) {
				return selection.toString();
			
			} else {
				System.out.println(Constants.INCORRECT_INPUT);
			}
		} while (true);
	}

	public boolean promptOptions(String[] options, HashMap<Integer, Callable> optionFuncs) {
		Scanner scan = new Scanner(System.in);

		do {
			int option = 1;
			for (int i = 0; i < options.length; i++) {
				System.out.println(option + ") " + options[i]);
				option++;
			}

			if (option == 1) {
				System.out.println(Constants.NO_DATA);
			}

			// Set previous menu option
			int prevMenuOption = option;
			System.out.println(prevMenuOption + ") " + Constants.PREVIOUS_MENU);

			// Grab selection
			getSelection();
			String selectionString = scan.nextLine();

			// Ensure selection is integer
			int selection;
			try {
				selection = Integer.parseInt(selectionString);
			} catch (Exception e) {
				if (selectionString.equals("quit")) {
					return false;
				}
				System.out.println(Constants.INCORRECT_INPUT);
				continue;
			}

			// Check go previous menu
			if (selection == prevMenuOption) {
				System.out.println(Constants.RETURNING);
				return true;
			// Proceed w/ a function
			} else if (selection >= 1 && selection < options.length + 2) {
				Callable<?> func = optionFuncs.get(selection);
				boolean contPrompting;
				try {
					contPrompting = (boolean)func.call();
					if (contPrompting == false) {
						return false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else {
				System.out.println(Constants.INCORRECT_INPUT);
			}
		} while (true);
	}
	
}
