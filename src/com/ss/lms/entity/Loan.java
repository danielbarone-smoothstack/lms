package com.ss.lms.entity;

import java.sql.Timestamp;
import java.util.List;

public class Loan {
	private Integer bookId;
	private Integer branchId;
	private Integer cardNo;
	private Timestamp dateOut;
	private Timestamp dueDate;
	private Timestamp dateIn;

	public Loan(Integer bookId, Integer branchId, Integer cardNo, Timestamp dateOut, Timestamp dueDate) {
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;
		this.dateOut = dateOut;
		this.dueDate = dueDate;
		this.dateIn = null;
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

}
