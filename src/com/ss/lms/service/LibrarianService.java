package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.ss.lms.constants.Constants;
import com.ss.lms.dao.BranchDAO;
import com.ss.lms.entity.Book;
import com.ss.lms.entity.Branch;

public class LibrarianService extends BaseUserService {

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

  public boolean updateNoOfCopies(Branch branch, Book book, Integer noOfCopies) throws SQLException {
    Connection conn = null;
    try { 
      conn = conUtil.getConnection();
      BranchDAO branchDAO = new BranchDAO(conn);
      branchDAO.updateNoOfCopies(branch, book, noOfCopies);
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
}
