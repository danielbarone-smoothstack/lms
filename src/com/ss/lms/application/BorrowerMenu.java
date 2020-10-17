package com.ss.lms.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;

import com.ss.lms.constants.Constants;
import com.ss.lms.dao.BorrowerDAO;

class CheckOutBook implements Callable { 
	int cardNo;
	CheckOutBook(int cardNo) {
		this.cardNo = cardNo;
	}
    public Object call() throws Exception { 
    	System.out.println(cardNo + "hell yeah");
        return true; 
    } 
} 

class ReturnBook implements Callable { 
	int cardNo;
	ReturnBook(int cardNo) {
		this.cardNo = cardNo;
	}
    public Object call() throws Exception { 
    	System.out.println(cardNo + "hell yeah");
        return true; 
    } 
} 

public class BorrowerMenu extends BaseUserMenu {
	
	BorrowerMenu() {
		super("Borrower");
	}

	public Integer checkCardValid() {
		Scanner scan = new Scanner(System.in);
		boolean cardNumValid = false;
		int validCardNo;
		String cardNo;
		do {
			System.out.println(Constants.CARD_NUMBER_PROMPT);
			cardNo = scan.nextLine();
			try {
				validCardNo = Integer.parseInt(cardNo);
				BorrowerDAO bdao = new BorrowerDAO();
				List<com.ss.lms.entity.Borrower> borrowers = bdao.readAllBorrowersByCardNo(validCardNo);
				System.out.println(borrowers.size());
				if (borrowers.size() > 0) {
					return validCardNo;
				}
			} catch (Exception e) {
				if (cardNo == "quit") {
					return -1;
				}
			}
			
			System.out.println(Constants.TRY_AGAIN);
			
		} while (cardNumValid == false);
		return -1;
	}

	public boolean driver() {
		printMenu();

		int cardNo = checkCardValid();

		if (cardNo == -1) {
			return false;
		}

		HashMap<Integer, Callable> borrowerOptions = new HashMap<Integer, Callable>();
		
		Callable callb = new CheckOutBook(cardNo);
		Callable callb2 = new CheckOutBook(cardNo);

		borrowerOptions.put(1, callb);
		borrowerOptions.put(2, callb2);


		return promptOptions(Constants.MAIN_BORROWER_OPTIONS, borrowerOptions);

	}
}
