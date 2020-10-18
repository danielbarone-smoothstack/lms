package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> {

	public BorrowerDAO(Connection conn) {
		super(conn);
	}

	public void addBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_borrower (cardNo, name, address, phone) VALUES (?,?,?,?)", new Object[] { 
			borrower.getCardNo(), borrower.getName(), borrower.getAddress(), borrower.getPhone() 
		});
	}
	
	public List<Borrower> readAllBorrowersByCardNo(Integer cardNo) throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_borrower WHERE cardNo = ?", new Object[] { cardNo });
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Borrower> borrowers = new ArrayList<Borrower>();
		BookDAO bookDAO = new BookDAO(conn);

		while (rs.next()) {
			Borrower borrower = new Borrower(rs.getInt("cardNo"), rs.getString("name"), rs.getString("address"), rs.getString("phone"));
			
			List<Book> books = bookDAO.read(
				"SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_loans WHERE cardNo = ?)", new Object[] { borrower.getCardNo() }
			);
			borrower.setBooks(books);
			borrowers.add(borrower);
		}
		System.out.println(borrowers.size());
		return borrowers;
	}
}
