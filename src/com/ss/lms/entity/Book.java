
package com.ss.lms.entity;

import java.util.List;

/**
 * @author ppradhan
 *
 */
public class Book {
	private Integer bookId;
	private String title;
	private Integer noOfCopies;
	private List<Author> authors;
	private List<Genre> genres;
//	private List<Branch> branches;
	private Publisher publisher;

	public Book(Integer bookId, String title) {
		super();
		this.bookId = bookId;
		this.title = title;
	}

	@Override
	public String toString() {
		String authorString = "";
		int authorCount = authors.size();
		for (int i = 0; i < authorCount; i++) {
			if (authorCount == 1) {
				authorString = authors.get(i).getAuthorName();
				break;
			}
			if (i == authorCount - 1) {
				authorString = authorString + ", and " + authors.get(i).getAuthorName();
				break;
			}
			authorString += authors.get(i).getAuthorName();
			authorString += ", ";
		}
		return title + " by " + authorString;
	}
	
	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	public List<Genre> getGenres() {
		return genres;
	}
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	public Integer getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Integer noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
}

