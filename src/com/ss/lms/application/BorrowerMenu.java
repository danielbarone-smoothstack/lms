package com.ss.lms.application;

import java.util.Scanner;
import java.util.concurrent.Callable;

import com.ss.lms.constants.Constants;
import com.ss.lms.service.BorrowerService;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Branch;

public class BorrowerMenu extends BaseUserMenu implements Callable<Boolean> {

	private BorrowerService service;
	private Borrower borrower;

	BorrowerMenu(Scanner scan, Integer menu) {
		super("Borrower", scan, menu);
		this.service = new BorrowerService();
	}

	BorrowerMenu(Scanner scan, Integer menu, BorrowerService service, Borrower borrower) {
		super("Borrower", scan, menu);
		this.service = service;
		this.borrower = borrower;
	}

	public Boolean call() throws Exception {
		switch(getMenu()) {
			case 1:
				return checkoutBook();
			case 2:
				return returnBook();
			default:
				return true;
		}
	}

	public boolean checkoutBook() throws Exception {
		// Get branch selection
		printSubMenu(Constants.CHECKOUT_BRANCH);
		Branch branch = getBranchSelection(service.getBranches(null));
		// Exit program
		if (branch.getBranchId() == -1) {
			return false;
			// Return to previous menu
		} else if(branch.getBranchId() == 0) {
			return true;
		}

		// Get book selection
		printSubMenu(Constants.CHECKOUT_BOOK);
		Book book = getBookSelection(branch.getBooks());
		// Exit program
		if (book.getBookId() == -1) {
			return false;
			// Return to previous menu
		} else if (book.getBookId() == 0) {
			return true;
		}

		// Check num copies > 0
		if (book.getNoOfCopies() == 0) {
			System.out.println(Constants.NO_COPIES);
			return true;
		}

		// Check book not already on loan
		if (borrower.getBooks().contains(book)) {
			System.out.println(Constants.ALREADY_BORROWED);
			return true;
		}

		// checkout the book
		return service.checkoutBook(book, branch, borrower.getCardNo()); 
	}

	public boolean returnBook() throws Exception {
		printSubMenu(Constants.RETURN_BRANCH);
		Branch branch = getBranchSelection(service.getBranches(null));
		
		// Exit program if user typed 'quit'
		if (branch.getBranchId() == -1) {
			return false;
			// Return to previous menu if user selected prev menu opt
		} else if(branch.getBranchId() == 0) {
			return true;
		}

		// Get list of book options to display to user
		printSubMenu(Constants.RETURN_BOOK);
		// Get the user's book selection
		Book book = getBookSelection(service.getBooks(branch, borrower));

		// if user typed quit, exit program
		if (book.getBookId() == -1) {
			return false;
		// Return to prev menu if invalid or selected prev menu
		} else if (book.getBookId() == 0) {
			return true;
		}
		
		// Perform the return book functionality
		return service.returnBook(book.getBookId(), branch.getBranchId(), borrower.getCardNo()); 
	}

	public Borrower getBorrower() {
		String selection;
		int cardNo = -1;
		do {
			System.out.print(Constants.CARD_NUMBER_PROMPT);
			selection = scan.nextLine();
			
			printSubMenu("Select an option");
			try {
				cardNo = Integer.parseInt(selection);
				Borrower borrower = service.checkCardNoValid(cardNo);
				if (borrower.getCardNo() != -1) {
					return borrower;
				}
			} catch (Exception e) {
				if (selection.equals("quit")) {
					return new Borrower(-1, null, null, null);
				}
			}
			System.out.println(Constants.TRY_AGAIN);
		} while (true);
	}

	public void setService(BorrowerService service) {
		this.service = service;
	}

	@Override
	public boolean driver() {
		printMenuHeader();
		
		borrower = getBorrower();
		if (borrower.getCardNo() == -1) {
			return false;
		}

		BorrowerMenu chkBook = new BorrowerMenu(scan, 1, service, borrower);
		BorrowerMenu retBook =  new BorrowerMenu(scan, 2, service, borrower);

		options.put(1, chkBook);
		options.put(2, retBook);

		return promptOptions(Constants.MAIN_BORROWER_OPTIONS, options);
	}
}