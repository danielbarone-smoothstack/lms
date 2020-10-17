package com.ss.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> {

	public void addBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_borrower (cardNo, name, address, phone) VALUES (?,?,?,?)", new Object[] { 
			borrower.getCardNo(), borrower.getName(), borrower.getAddress(), borrower.getPhone() 
		});
	}
	
	public List<Borrower> readAllBorrowersByCardNo(Integer cardNo) throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_borrower WHERE cardNo = ?", new Object[] {cardNo});
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<Borrower>();
		while (rs.next()) {
			borrowers.add(new Borrower(rs.getInt("cardNo"), rs.getString("name"), rs.getString("address"), rs.getString("phone")));
			//also populate...
			
		}
		System.out.println(borrowers.size());
		return borrowers;
	}
}
