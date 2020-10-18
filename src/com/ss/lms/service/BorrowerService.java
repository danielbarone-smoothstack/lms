package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

import com.ss.lms.entity.Borrower;
import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.entity.Loan;
import com.ss.lms.dao.LoanDAO;

public class BorrowerService extends BaseUserService {

  public Borrower checkCardNoValid(int cardNo) {
    try (Connection conn = conUtil.getConnection()){
    	
      BorrowerDAO bdao = new BorrowerDAO(conn);
      List<Borrower> borrowers = bdao.readAllBorrowersByCardNo(cardNo);
      
      if (borrowers.size() == 1) {
        return borrowers.get(0);
      } else {
        return new Borrower(-1, null, null, null);
      }
    } catch (Exception e) {
      return new Borrower(-1, null, null, null);
    }
  }

  public boolean checkoutBook(Integer bookId, Integer branchId, Integer cardNo) throws SQLException {
    Date date = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DATE, 7);
    Date due = c.getTime();

    Timestamp dateOut = new Timestamp(date.getTime());
    Timestamp dueDate = new Timestamp(due.getTime());
    
    Connection conn = null;
    try { 
    	conn = conUtil.getConnection();
    	LoanDAO loanDAO = new LoanDAO(conn);
    	Loan loan = new Loan(bookId, branchId, cardNo, dateOut, dueDate);
    	loanDAO.addLoan(loan);
    	conn.commit();
    	
    } catch (ClassNotFoundException | SQLException e) {
    	e.printStackTrace();
		if (conn != null) {
			conn.rollback();
		}
		return false;
    } finally {
    	if (conn != null) {
    		conn.close();
    	}
    }
    return true;
  }
}