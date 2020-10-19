package com.ss.lms.application;

import java.util.concurrent.Callable;
import java.util.HashMap;
import java.util.Scanner;

import com.ss.lms.constants.Constants;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Branch;
import com.ss.lms.service.LibrarianService;

public class LibrarianMenu extends BaseUserMenu implements Callable<Boolean> {

	private LibrarianService service;
	private Branch branch;

	LibrarianMenu(Scanner scan, Integer menu) {
		super("Librarian", scan, menu);
		this.service = new LibrarianService();
	}

	LibrarianMenu(Scanner scan, Integer menu, LibrarianService service, Branch branch) {
		super("Librarian", scan, menu);
		this.service = service;
		this.branch = branch;
	}

	public Boolean call() throws Exception {
		switch(getMenu()) {
			case 1:
				return editLibrary();
			case 2:
				return addCopies();
			default:
				return mainMenu();
		}
	}

	public boolean addCopies() {
		printSubMenu(Constants.PICK_BOOK);
		Book book = getBookSelection(branch.getBooks());
		
		if (book.getBookId() == -1) {
			return false;
		} else if (book.getBookId() == 0) {
			return true;
		}

		System.out.println(Constants.EXISTING_NUM_COPIES + book.getNoOfCopies());
		System.out.print(Constants.ENTER_NEW_NUM_COPIES);
		String numCopies = scan.nextLine();
		int newNoOfCopies;
		try {
			newNoOfCopies = Integer.parseInt(numCopies);
			return service.updateNoOfCopies(branch, book, newNoOfCopies);
		} catch (Exception e) {
			if (numCopies.equals("quit")) {
				return false;
			}
			System.out.println(Constants.INCORRECT_INPUT);
			return true;
		}
	}

	public boolean editLibrary() {		
		String subMenuMsg = "You have chosen to update the branch with\nBranch ID: "
			+ branch.getBranchId() + " and Branch Name: " + branch.getBranchName()
			+ ".\nEnter 'quit' at any prompt to cancel operation.";
		printSubMenu(subMenuMsg);

		System.out.print(Constants.NEW_BRANCH_NAME);
		String newName = scan.nextLine();
		if (newName.equals("quit")) {
			return true;
		} else if (newName.length() == 0 || newName.equals("N/A")) {
			newName = branch.getBranchName();
		}
		System.out.print(Constants.NEW_BRANCH_ADDRESS);
		String newAddress = scan.nextLine();
		if (newAddress.equals("quit")) {
			return true;
		} else if (newAddress.length() == 0 || newAddress.equals("N/A")) {
			newAddress = branch.getBranchAddress();
		} 

		// No change, return.
		if (newName.equals(branch.getBranchName()) && newAddress.equals(branch.getBranchAddress())) {
			return true;
		}
		
		// Update branch details
		Branch updatedBranch = new Branch(branch.getBranchId(), newName, newAddress);
		try {
			service.updateBranch(updatedBranch);
			branch.setBranchName(updatedBranch.getBranchName());
			branch.setBranchAddress(updatedBranch.getBranchAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public void setService(LibrarianService service) {
		this.service = service;
	}

	public boolean mainMenu() {
		boolean contSubMenu = true;
		
		do {
			System.out.println(Constants.SELECT_BRANCH);
			setBranch(getBranchSelection(service.getBranches(null)));

			// Check if user wants to quit
			if (branch.getBranchId() == -1) {
				return false;
			// Check if user wants prev menu
			} else if (branch.getBranchId() == 0) {
				return true;
			}

			LibrarianMenu editLibrary = new LibrarianMenu(scan, 1, service, branch);
			LibrarianMenu addCopies = new LibrarianMenu(scan, 2, service, branch);

			options = new HashMap<Integer, Callable<Boolean>>();
			options.put(1, editLibrary);
			options.put(2, addCopies);

			contSubMenu = promptOptions(Constants.LIB3_OPTIONS, options);
		} while (contSubMenu);
		return false;
	}

	@Override
	public boolean driver() {
		printMenuHeader();
		options.put(1, this);
		return promptOptions(Constants.LIB1_OPTIONS, options);
	}
}