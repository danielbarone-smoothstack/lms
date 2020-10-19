package com.ss.lms.application;

import java.util.concurrent.Callable;
import java.util.HashMap;
import java.util.Scanner;

import com.ss.lms.constants.Constants;
import com.ss.lms.entity.Branch;
import com.ss.lms.service.LibrarianService;

public class LibrarianMenu extends BaseUserMenu implements Callable {

	private LibrarianService lService;
	private Integer menu;
	private Branch branch;

	LibrarianMenu(Scanner scan) {
		super("Librarian", scan);
		menu = 0;
	}

	public Object call() throws Exception {
		switch(menu) {
			case 1:
				return editLibrary();
			case 2:
				return addCopies();
			default:
				return mainMenu();
		}
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public void setService(LibrarianService lService) {
		this.lService = lService;
	}

	public void setMenu(Integer menu) {
		this.menu = menu;
	}

	public boolean mainMenu() {
		boolean contSubMenu = true;
		do {
			System.out.println(Constants.SELECT_BRANCH);
			setBranch(getBranchSelection(lService));
			// Check if user wants to quit
			if (branch.getBranchId() == -1) {
				return false;
			// Check if user wants prev menu
			} else if (branch.getBranchId() == 0) {
				return true;
			}

			LibrarianMenu editLibrary = new LibrarianMenu(scan);
			editLibrary.setBranch(branch);
			editLibrary.setService(lService);
			editLibrary.setMenu(1);
			LibrarianMenu addCopies = new LibrarianMenu(scan);
			addCopies.setBranch(branch);
			addCopies.setService(lService);
			addCopies.setMenu(2);

			options = new HashMap<Integer, Callable>();
			options.put(1, editLibrary);
			options.put(2, addCopies);

			contSubMenu = promptOptions(Constants.LIB3_OPTIONS, options);
		} while (contSubMenu);
		return false;
	}

	public boolean addCopies() {
		System.out.println("Add Copies");
		return true;
	}

	public boolean editLibrary() {
		System.out.println("Edit Lib");
		return true;
	}

	@Override
	public boolean driver() {
		printMenuHeader();
		lService = new LibrarianService();
		options.put(1, this);
		return promptOptions(Constants.LIB1_OPTIONS, options);
	}
}