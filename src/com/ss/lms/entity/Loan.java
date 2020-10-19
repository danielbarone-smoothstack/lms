package com.ss.lms.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.ss.lms.constants.Constants;

public class Loan {
	private Integer bookId;
	private Integer branchId;
	private Integer cardNo;
	private Timestamp dateOut;
	private Timestamp dueDate;
	private Timestamp dateIn;

	private Book book;
	private Borrower borrower;
	private Branch branch;

	public Loan(Integer bookId, Integer branchId, Integer cardNo, Timestamp dateOut, Timestamp dueDate, Timestamp dateIn) {
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;
		this.dateOut = dateOut;
		this.dueDate = dueDate;
		this.dateIn = dateIn;
	}

	@Override
	public String toString() {
		return "\"" + book.getTitle() + "\" borrowed by " + borrower.getName() + " from "
			+ branch.getBranchName() + " (" + Constants.getColor("red", new SimpleDateFormat("MM/dd/yyyy").format(dueDate)) + ")"; 
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Integer getCardNo() {
		return cardNo;
	}

	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}

	public Timestamp getDateOut() {
		return dateOut;
	}

	public void setDateOut(Timestamp dateOut) {
		this.dateOut = dateOut;
	}

	public Timestamp getDueDate() {
		return dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}

	public Timestamp getDateIn() {
		return dateIn;
	}

	public void setDateIn(Timestamp dateIn) {
		this.dateIn = dateIn;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Borrower getBorrower() {
		return borrower;
	}

	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

}
