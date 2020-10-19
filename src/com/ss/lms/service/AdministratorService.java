package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.lms.constants.Constants;
import com.ss.lms.dao.AuthorDAO;
import com.ss.lms.dao.GenreDAO;
import com.ss.lms.dao.PublisherDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.dao.BorrowerDAO;
import com.ss.lms.dao.BranchDAO;
import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Borrower;
import com.ss.lms.entity.Branch;
import com.ss.lms.entity.Genre;
import com.ss.lms.entity.Publisher;

public class AdministratorService extends BaseUserService {

	public ConnectionUtil conUtil = new ConnectionUtil();

	public String addBook(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = conUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			AuthorDAO adao = new AuthorDAO(conn);
			GenreDAO gdao = new GenreDAO(conn);
			if (book.getTitle() != null && book.getTitle().length() > 45) {
				return "Book Title cannot be empty and should be 45 char in length";
			}
			book.setBookId(bdao.addBookWithPk(book));
			for (Author a : book.getAuthors()) {
				adao.addBookAuthors(book.getBookId(), a.getAuthorId());
			}
			for (Genre g : book.getGenres()) {
				gdao.addBookGenres(book.getBookId(), g.getGenreId());
			}
			// Do the same for genres/branche etc.

			conn.commit();
			return "Book added sucessfully";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				conn.rollback();
			}
			return "Unable to add book - contact admin.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public boolean addGenre(Genre genre) throws SQLException {
		Connection conn = null;
		try {
			conn = conUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			gdao.addGenre(genre);
			conn.commit();
			return true;
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
	}

	public boolean addPublisher(Publisher publisher) throws SQLException {
		Connection conn = null;
		try {
			conn = conUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.addPublisher(publisher);
			conn.commit();
			return true;
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
	}

	public boolean addBorrower(Borrower borrower) throws SQLException {
		Connection conn = null;
		try {
			conn = conUtil.getConnection();
			BorrowerDAO bDAO = new BorrowerDAO(conn);
			bDAO.addBorrower(borrower);
			conn.commit();
			return true;
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
	}

	public boolean addBranch(Branch branch) throws SQLException {
		Connection conn = null;
		try {
			conn = conUtil.getConnection();
			BranchDAO bDAO = new BranchDAO(conn);
			bDAO.addBranch(branch);
			conn.commit();
			return true;
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
	}

	public boolean deleteGenre(Genre genre) throws SQLException {
		Connection conn = null;
    try { 
      conn = conUtil.getConnection();
      GenreDAO genreDAO = new GenreDAO(conn);
      genreDAO.deleteGenre(genre);
      conn.commit();
      System.out.println(Constants.SUCCESSFUL_DELETE);
      return true;
      
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      if (conn != null) {
        conn.rollback();
      }
      System.out.println(Constants.FAILED_DELETE);
      return false;

    } finally {
      if (conn != null) {
        conn.close();
      }
    }
	}
	
	public boolean deletePublisher(Publisher publisher) throws SQLException {
		Connection conn = null;
    try { 
      conn = conUtil.getConnection();
      PublisherDAO pdao = new PublisherDAO(conn);
      pdao.deletePublisher(publisher);
      conn.commit();
      System.out.println(Constants.SUCCESSFUL_DELETE);
      return true;
      
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      if (conn != null) {
        conn.rollback();
      }
      System.out.println(Constants.FAILED_DELETE);
      return false;

    } finally {
      if (conn != null) {
        conn.close();
      }
    }
	}

	public boolean deleteBorrower(Borrower borrower) throws SQLException {
		Connection conn = null;
    try { 
      conn = conUtil.getConnection();
      BorrowerDAO borrowerDAO = new BorrowerDAO(conn);
      borrowerDAO.deleteBorrower(borrower);
      conn.commit();
      System.out.println(Constants.SUCCESSFUL_DELETE);
      return true;
      
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      if (conn != null) {
        conn.rollback();
      }
      System.out.println(Constants.FAILED_DELETE);
      return false;

    } finally {
      if (conn != null) {
        conn.close();
      }
    }
	}
	
	public boolean deleteBranch(Branch branch) throws SQLException {
		Connection conn = null;
    try { 
      conn = conUtil.getConnection();
      BranchDAO branchDAO = new BranchDAO(conn);
      branchDAO.deleteBranch(branch);
      conn.commit();
      System.out.println(Constants.SUCCESSFUL_DELETE);
      return true;
      
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      if (conn != null) {
        conn.rollback();
      }
      System.out.println(Constants.FAILED_DELETE);
      return false;

    } finally {
      if (conn != null) {
        conn.close();
      }
    }
	}

	public boolean updateBranch(Branch branch) throws SQLException {
    Connection conn = null;
    try { 
      conn = conUtil.getConnection();
      BranchDAO branchDAO = new BranchDAO(conn);
      branchDAO.updateBranch(branch);
      conn.commit();
      System.out.println(Constants.SUCCESSFUL_UPDATE);
      return true;
      
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      if (conn != null) {
        conn.rollback();
      }
      System.out.println(Constants.FAILED_UPDATE);
      return false;

    } finally {
      if (conn != null) {
        conn.close();
      }
    }
  }

	public boolean updateGenre(Genre genre) throws SQLException {
    Connection conn = null;
    try { 
      conn = conUtil.getConnection();
      GenreDAO genreDAO = new GenreDAO(conn);
      genreDAO.updateGenre(genre);
      conn.commit();
      System.out.println(Constants.SUCCESSFUL_UPDATE);
      return true;
      
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      if (conn != null) {
        conn.rollback();
      }
      System.out.println(Constants.FAILED_UPDATE);
      return false;

    } finally {
      if (conn != null) {
        conn.close();
      }
    }
	}
	
	public boolean updatePublisher(Publisher publisher) throws SQLException {
    Connection conn = null;
    try { 
      conn = conUtil.getConnection();
      PublisherDAO publisherDAO = new PublisherDAO(conn);
      publisherDAO.updatePublisher(publisher);
      conn.commit();
      System.out.println(Constants.SUCCESSFUL_UPDATE);
      return true;
      
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      if (conn != null) {
        conn.rollback();
      }
      System.out.println(Constants.FAILED_UPDATE);
      return false;

    } finally {
      if (conn != null) {
        conn.close();
      }
    }
	}

	public boolean updateBorrower(Borrower borrower) throws SQLException {
    Connection conn = null;
    try { 
      conn = conUtil.getConnection();
      BorrowerDAO borrowerDAO = new BorrowerDAO(conn);
      borrowerDAO.updateBorrower(borrower);
      conn.commit();
      System.out.println(Constants.SUCCESSFUL_UPDATE);
      return true;
      
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      if (conn != null) {
        conn.rollback();
      }
      System.out.println(Constants.FAILED_UPDATE);
      return false;

    } finally {
      if (conn != null) {
        conn.close();
      }
    }
	}
	
	public boolean addBookCopies(Branch branch, Book book, Integer noOfCopies) throws SQLException {
    Connection conn = null;
    try { 
      conn = conUtil.getConnection();
      BranchDAO branchDAO = new BranchDAO(conn);
      branchDAO.addBookCopies(branch, book.getBookId(), noOfCopies);
      conn.commit();
      System.out.println(Constants.SUCCESSFUL_UPDATE);
      return true;
      
    } catch (ClassNotFoundException | SQLException e){
      e.printStackTrace();
      if (conn != null) {
        conn.rollback();
      }
      System.out.println(Constants.FAILED_UPDATE);
      return false;

    } finally {
      if (conn != null) {
        conn.close();
      }
    }
  }

	public List<Author> getAuthors(String searchString) {
	  try(Connection conn = conUtil.getConnection()) {
			AuthorDAO adao = new AuthorDAO(conn);
			
		  if (searchString != null) {
			  return adao.readAllAuthors();
		  } else {
			  return adao.readAllAuthors();
		  }
	  } catch (ClassNotFoundException | SQLException e) {
		  e.printStackTrace();
		  return null;
	  }
	}

	public List<Borrower> getBorrowers(String searchString) {
	  try(Connection conn = conUtil.getConnection()) {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			
		  if (searchString != null) {
			  return bdao.readAllBorrowers();
		  } else {
			  return bdao.readAllBorrowers();
		  }
	  } catch (ClassNotFoundException | SQLException e) {
		  e.printStackTrace();
		  return null;
	  }
  }
	
	public List<Genre> getGenres(String searchString) {
	  try(Connection conn = conUtil.getConnection()) {
			GenreDAO gdao = new GenreDAO(conn);
			
		  if (searchString != null) {
			  return gdao.readAllGenres();
		  } else {
			  return gdao.readAllGenres();
		  }
	  } catch (ClassNotFoundException | SQLException e) {
		  e.printStackTrace();
		  return null;
	  }
	}
	
	public List<Publisher> getPublishers(String searchString) {
	  try(Connection conn = conUtil.getConnection()) {
			PublisherDAO pdao = new PublisherDAO(conn);
			
		  if (searchString != null) {
			  return pdao.readAllPublishers();
		  } else {
			  return pdao.readAllPublishers();
		  }
	  } catch (ClassNotFoundException | SQLException e) {
		  e.printStackTrace();
		  return null;
	  }
  }

	public List<Book> getBooks(String searchString) {
		try(Connection conn = conUtil.getConnection()) {
			BookDAO bdao = new BookDAO(conn);
			if (searchString != null) {
				return bdao.readAllBooksByName(searchString);
			} else {
				return bdao.readAllBooks();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
