package com.ss.lms.application;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.ss.lms.constants.Constants;
import com.ss.lms.service.BorrowerService;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Branch;

public class BorrowerMenu extends BaseUserMenu implements Callable {

	private BorrowerService service;
	private Borrower borrower;
	private Integer menu;

	BorrowerMenu(Scanner scan) {
		super("Borrower", scan);
		menu = 0;
	}

	public Object call() throws Exception {
		switch(menu) {
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
		System.out.println(Constants.CHECKOUT_BRANCH);
		Branch branch = getBranchSelection(service);
		// Exit program
		if (branch.getBranchId() == -1) {
			return false;
			// Return to previous menu
		} else if(branch.getBranchId() == 0) {
			return true;
		}

		// Get book selection
		System.out.println(Constants.CHECKOUT_BOOK);
		Book book = getBookSelection(branch);
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
		return service.checkoutBook(book.getBookId(), branch.getBranchId(), borrower.getCardNo()); 
	}

	public boolean returnBook() throws Exception {
		System.out.println(Constants.RETURN_BRANCH);
		Branch branch = getBranchSelection(service);
		
		// Exit program if user typed 'quit'
		if (branch.getBranchId() == -1) {
			return false;
			// Return to previous menu if user selected prev menu opt
		} else if(branch.getBranchId() == 0) {
			return true;
		}

		// Get list of book options to display to user
		System.out.println(Constants.RETURN_BOOK);
		List<Book> bookObjs = service.getBookSelection(branch, borrower);
		List<String> books = bookObjs.stream().map(book -> book.toString()).collect(Collectors.toList());

		int selection = promptOptions(books);
		// if user typed quit, exit program
		if (selection == -1) {
			return false;
		// Return to prev menu if invalid or selected prev menu
		} else if (selection == 0) {
			return true;
		}

		// Get the user's book selection
		Book book = bookObjs.get(selection - 1);
		
		// Perform the return book functionality
		return service.returnBook(book.getBookId(), branch.getBranchId(), borrower.getCardNo()); 
	}

	public Borrower getBorrower() {
		String selection;
		int cardNo = -1;
		do {
			System.out.print(Constants.CARD_NUMBER_PROMPT);
			selection = scan.nextLine();
			try {
				cardNo = Integer.parseInt(selection);
				Borrower borrower = service.checkCardNoValid(cardNo);
				if (borrower.getCardNo() != -1) {
					return borrower;
				}
			} catch (Exception e) {
				if (selection == "quit") {
					return new Borrower(-1, null, null, null);
				}
			}
			System.out.println(Constants.TRY_AGAIN);
		} while (true);
	}

	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}

	public void setService(BorrowerService service) {
		this.service = service;
	}
	
	public void setMenu(Integer menu) {
		this.menu = menu;
	}

	@Override
	public boolean driver() {
		printMenuHeader();

		service = new BorrowerService();
		borrower = getBorrower();

		if (borrower.getCardNo() == -1) {
			return false;
		}

		BorrowerMenu chkBook = new BorrowerMenu(scan);
		chkBook.setBorrower(borrower);
		chkBook.setService(service);
		chkBook.setMenu(1); 
		BorrowerMenu retBook =  new BorrowerMenu(scan);
		retBook.setBorrower(borrower);
		retBook.setService(service);
		retBook.setMenu(2);

		options.put(1, chkBook);
		options.put(2, retBook);

		return promptOptions(Constants.MAIN_BORROWER_OPTIONS, options);
	}
}