package com.ss.lms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Loan;

public class LoanDAO extends BaseDAO<Loan> {

	public void addLoan(Loan loan) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate, dateIn) VALUES (?,?,?,?)", new Object[] { 
			loan.getBookId(), loan.getBranchId(), loan.getCardNo(), loan.getDateOut(), loan.getDueDate(), loan.getDateIn() 
		});
	}

	@Override
	public List<Loan> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}

