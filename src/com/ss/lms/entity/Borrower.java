package com.ss.lms.entity;

import java.util.List;

public class Borrower {
  private Integer cardNo;
  private String name;
  private String address;
  private String phone;
  private List<Book> books;

  public Borrower(Integer cardNo, String name, String address, String phone) {
    this.cardNo = cardNo;
    this.name = name;
    this.address = address;
    this.phone = phone;
  }

  @Override
	public String toString() {
    return name;
  }

  public Integer getCardNo() {
    return cardNo;
  }

  public void setCardNo(Integer cardNo) {
    this.cardNo = cardNo;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

}
