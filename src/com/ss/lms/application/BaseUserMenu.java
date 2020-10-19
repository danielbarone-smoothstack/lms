package com.ss.lms.application;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.ss.lms.constants.Constants;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Branch;
import com.ss.lms.service.BaseUserService;

public abstract class BaseUserMenu {
	
	private String userType;
	protected HashMap<Integer, Callable> options;
	protected static Scanner scan;
	
	BaseUserMenu(String userType, Scanner scan) {
		this.userType = Constants.getColor("header", userType);
		this.options = new HashMap<Integer, Callable>();
		this.scan = scan;
	}

	public abstract boolean driver();

	public Branch getBranchSelection(List<Branch> branchObjects) {
		// Get branch list w/ branch name and address
		List<String> branches = branchObjects.stream().map(branch -> branch.toString()).collect(Collectors.toList());;
		
		// prompt options
		int selection = promptOptions(branches);
		if (selection == -1) {
			return new Branch(-1, null, null);
		} else if (selection == 0) {
			return new Branch(0, null, null);
		}
		// return selected branch ID
		return branchObjects.get(selection - 1);
	}

	public Book getBookSelection(List<Book> bookObjects) {
		List<String> books = bookObjects.stream().map(book -> book.toString()).collect(Collectors.toList());

		int selection = promptOptions(books);
		if (selection == -1) {
			return new Book(-1, null);
		} else if (selection == 0) {
			return new Book(0, null);
		}
		return bookObjects.get(selection - 1);
	}

	private void printGetSelection() {
		System.out.println(Constants.LINE_BREAK);
		System.out.print(Constants.MAKE_SELECTION);
	}

	public static void printMainMenu() {
		System.out.println(Constants.MENU_LINE_BREAK);
		System.out.println(Constants.WELCOME_MESSAGE);
		System.out.println(Constants.LINE_BREAK);
		System.out.println(Constants.USER_TYPES);
		System.out.println(Constants.LINE_BREAK);
		System.out.print(Constants.MAKE_SELECTION);
	}
	
	public void printMenuHeader() {
		System.out.println("\n");
		System.out.println(Constants.MENU_LINE_BREAK);
		System.out.println(userType + " " + Constants.MENU);
		System.out.println(Constants.LINE_BREAK);
	}

	public void printSubMenu(String subMenu) {
		System.out.println("\n");
		System.out.println(subMenu);
		System.out.println(Constants.LINE_BREAK);
	}

	public static Integer promptOptions(List<String> options) {
		do {
			int option = 1;
			for (String description : options) {
				System.out.println(option + ") " + description);
				option++;
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
					return -1;
				}
				System.out.println(Constants.INCORRECT_INPUT);
				continue;
			}

			// Check go previous menu
			if (selection == prevMenuOption) {
				System.out.println(Constants.RETURNING);
				return 0;

			// Proceed w/ a function
			} else if (selection >= 1 && selection < options.size() + 2) {
				return selection;
			
			} else {
				System.out.println(Constants.INCORRECT_INPUT);
			}
		} while (true);
	}

	public boolean promptOptions(String[] options, HashMap<Integer, Callable> optionFuncs) {

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
			printGetSelection();
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
