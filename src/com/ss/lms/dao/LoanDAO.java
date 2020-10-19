package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Branch;
import com.ss.lms.entity.Loan;

public class LoanDAO extends BaseDAO<Loan> {
	
	public LoanDAO(Connection conn) {
		super(conn);
	}

	public void addLoan(Loan loan) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate, dateIn) VALUES (?,?,?,?,?,?)", new Object[] { 
			loan.getBookId(), loan.getBranchId(), loan.getCardNo(), loan.getDateOut(), loan.getDueDate(), null 
		});
	}

	public void updateLoan(Integer bookId, Integer branchId, Integer cardNo, Timestamp dateIn) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book_loans SET dateIn = ? WHERE bookId = ? AND branchId = ? and cardNo = ?", 
			new Object[] { dateIn, bookId, branchId, cardNo}
		);
	}

	public void extendDueDate(Loan loan) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book_loans SET dueDate = ? WHERE bookId = ? AND branchId = ? and cardNo = ?", 
			new Object[] { loan.getDueDate(), loan.getBookId(), loan.getBranchId(), loan.getCardNo()}
		);
	}

	public List<Loan> readAllLoans() throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_book_loans", null);
	}

	@Override
	public List<Loan> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Loan> loans = new ArrayList<>();
		BookDAO bookDAO = new BookDAO(conn);
		BorrowerDAO borrowerDAO = new BorrowerDAO(conn);
		BranchDAO branchDAO = new BranchDAO(conn);

		while (rs.next()) {
			Loan loan = new Loan(rs.getInt("bookId"), rs.getInt("branchId"), rs.getInt("cardNo"), rs.getTimestamp("dateOut"), rs.getTimestamp("dueDate"), rs.getTimestamp("dateIn"));
			
			List<Book> bookList = bookDAO.read("SELECT * FROM tbl_book WHERE bookId = ?", new Object[] { loan.getBookId() } );
			loan.setBook(bookList.get(0));
			List<Borrower> borrowerList = borrowerDAO.read("SELECT * FROM tbl_borrower WHERE cardNo = ?", new Object[] { loan.getCardNo() } );
			loan.setBorrower(borrowerList.get(0));
			List<Branch> branchList = branchDAO.read("SELECT * FROM tbl_library_branch WHERE branchId = ?", new Object[] { loan.getBranchId() } );
			loan.setBranch(branchList.get(0));
			if (loan.getDateIn() == null) {
				loans.add(loan);
			}
		}
		return loans;
	}
}