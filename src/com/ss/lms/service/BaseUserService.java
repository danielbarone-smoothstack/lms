package com.ss.lms.service;

import java.util.List;

import java.sql.Connection;
import java.sql.SQLException;

import com.ss.lms.dao.BranchDAO;
import com.ss.lms.entity.Branch;

public abstract class BaseUserService {

  public ConnectionUtil conUtil;
  
  BaseUserService() {
	  conUtil = new ConnectionUtil();
  }

  public List<Branch> getBranches(String searchString) {
	  try(Connection conn = conUtil.getConnection()) {
		  BranchDAO bdao = new BranchDAO(conn);
		  if (searchString != null) {
			  return bdao.readAllBranches();
		  } else {
			  return bdao.readAllBranches();
		  }
	  } catch (ClassNotFoundException | SQLException e) {
		  e.printStackTrace();
		  return null;
	  }
  }	
}
