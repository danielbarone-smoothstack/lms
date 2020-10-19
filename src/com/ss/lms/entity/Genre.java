package com.ss.lms.entity;

public class Genre {

	private Integer genre_id;
	private String genre_name;
	
	public Genre(Integer genre_id, String genre_name) {
		this.genre_id = genre_id;
		this.genre_name = genre_name;
	}

	@Override
	public String toString() {
		return genre_name;
	}

	public Integer getGenreId() {
		return genre_id;
	}
	
	public void setGenreId(Integer genre_id) {
		this.genre_id = genre_id;
	}
	
	public String getGenreName() {
		return genre_name;
	}
	
	public void setGenreName(String genre_name) {
		this.genre_name = genre_name;
	}
}
