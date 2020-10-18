package com.ss.lms.application;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.ss.lms.constants.Constants;
import com.ss.lms.service.BorrowerService;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Branch;

class CheckOutBook implements Callable { 

	BorrowerService bService;
	Scanner scan;
	Borrower borrower;

	CheckOutBook(Scanner scan, BorrowerService bService, Borrower borrower) {
		this.scan = scan;
		this.bService = bService;
		this.borrower = borrower;
	}

	public Object call() throws Exception { 
		// Get branch selection
		System.out.println(Constants.CHECKOUT_BRANCH);
		Branch branch = BaseUserMenu.getBranchSelection(bService);
		// Exit program
		if (branch.getBranchId() == -1) {
			return false;
			// Return to previous menu
		} else if(branch.getBranchId() == 0) {
			return true;
		}

		// Get book selection
		System.out.println(Constants.CHECKOUT_BOOK);
		Book book = BaseUserMenu.getBookSelection(branch);
		// Exit program
		if (book.getBookId() == -1) {
			return false;
			// Return to previous menu
		} else if (book.getBookId() == 0) {
			return true;
		}

		// Check num copies > 0
		System.out.println(book.getBookId());
		System.out.println(book.getTitle());
		System.out.println(book.getNoOfCopies());
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
		return bService.checkoutBook(book.getBookId(), branch.getBranchId(), borrower.getCardNo()); 
	}
} 

class ReturnBook implements Callable { 

	BorrowerService bService;
	Scanner scan;
	Borrower borrower;

	ReturnBook(Scanner scan, BorrowerService bService, Borrower borrower) {
		this.scan = scan;
		this.bService = bService;
		this.borrower = borrower;
	}
	public Object call() throws Exception { 
		System.out.println(Constants.RETURN_BRANCH);
		Branch branch = BaseUserMenu.getBranchSelection(bService);
		// Exit program
		if (branch.getBranchId() == -1) {
			return false;
			// Return to previous menu
		} else if(branch.getBranchId() == 0) {
			return true;
		}

		System.out.println(Constants.RETURN_BOOK);
		List<Book> bookObjs = bService.getBookSelection(branch, borrower);
		List<String> books = bookObjs.stream().map(book -> book.toString()).collect(Collectors.toList());

		int selection = BaseUserMenu.promptOptions(books);
		if (selection == -1) {
			return false;
		} else if (selection == 0) {
			return true;
		}

		Book book = bookObjs.get(selection - 1);

		return bService.returnBook(book.getBookId(), branch.getBranchId(), borrower.getCardNo()); 
	}
}

public class BorrowerMenu extends BaseUserMenu {

	private BorrowerService bService;
	private Callable checkoutBook;
	private Callable returnBook;

	BorrowerMenu(Scanner scan) {
		super("Borrower", scan);
	}

	public Borrower getBorrower() {
		String selection;
		int cardNo = -1;
		do {
			System.out.print(Constants.CARD_NUMBER_PROMPT);
			selection = scan.nextLine();
			try {
				cardNo = Integer.parseInt(selection);
				Borrower borrower = bService.checkCardNoValid(cardNo);
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

	public boolean driver() {
		bService = new BorrowerService();

		printMenuHeader();

		Borrower borrower = getBorrower();
		if (borrower.getCardNo() == -1) {
			return false;
		}

		checkoutBook = new CheckOutBook(scan, bService, borrower);
		returnBook = new ReturnBook(scan, bService, borrower);

		options.put(1, checkoutBook);
		options.put(2, returnBook);

		return promptOptions(Constants.MAIN_BORROWER_OPTIONS, options);
	}
}