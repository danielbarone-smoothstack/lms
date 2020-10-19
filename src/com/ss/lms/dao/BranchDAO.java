package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Branch;

public class BranchDAO extends BaseDAO {

	public BranchDAO(Connection conn) {
		super(conn);
	}

	public List<Branch> readAllBranches() throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_library_branch", null);
	}

	@Override
	public List<Branch> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		
		List<Branch> branches = new ArrayList<>();
		BookDAO bookDAO = new BookDAO(conn);
		
		while (rs.next()) {
			Branch branch = new Branch(rs.getInt("branchId"), rs.getString("branchName"), rs.getString("branchAddress"));
			List<Book> books = bookDAO.read(
				"SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_copies WHERE branchId = ?)", new Object[] { branch.getBranchId() }
			);

			for (Book book : books) {

				book.setNoOfCopies(
					bookDAO.read(
						"SELECT tbook.bookId, tbook.title, tcopies.noOfCopies FROM tbl_book tbook, tbl_book_copies tcopies WHERE tbook.bookId=tcopies.bookId AND tcopies.bookId=? AND tcopies.branchId=?",
							new Object[] { book.getBookId(), branch.getBranchId() }
					).get(0).getNoOfCopies()
				);
			}

			branch.setBooks(books);
			branches.add(branch);
		}
		
		return branches;
	}

}