package ru.kazachkov.bookkeeping.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
	private int id;

	@NotEmpty(message = "Название книги не должно быть пустым")
	@Size(min = 2, max = 100, message = "Название книги должно быть от 2 до 100 символов длиной")
	private String name;

	@NotEmpty(message = "Автор не должен быть пустым")
	@Size(min = 2, max = 100, message = "Имя автора должно быть от 2 до 100 символов длиной")
	private String author;

	@Min(value = 1000, message = "год должен быть больше, чем 1000")
	private int year;

	public Book(String name, String author, int year) {
		this.name = name;
		this.author = author;
		this.year = year;

	}

	public Book(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}


	public int getYear() {
		return year;
	}


}
