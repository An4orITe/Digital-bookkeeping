package ru.kazachkov.bookkeeping.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kazachkov.bookkeeping.models.Book;
import ru.kazachkov.bookkeeping.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public BookDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public List<Book> getBooks() {
		return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
	}

	public void save(Book book) {
		jdbcTemplate.update("INSERT INTO book (name, author, year) VALUES (?, ?, ?)",
				book.getName(), book.getAuthor(), book.getYear());
	}

	public Book getBookById(int id) {
		return jdbcTemplate.query("SELECT * FROM Book where id=?", new BeanPropertyRowMapper<>(Book.class), new Object[]{id})
				.stream().findAny().orElse(null);
	}

	public void update(int id, Book book) {
		jdbcTemplate.update("UPDATE book SET name=?, author=?, year=? where id=?", book.getName(),
				book.getAuthor(), book.getYear(), id);
	}

	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM book where id=?", id);
	}

	public Optional<Person> getPersonBook(int id) {
		return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id WHERE Book.id=?",
				new BeanPropertyRowMapper<>(Person.class), new Object[]{id}).stream().findAny();
	}


	public void freeBook(int id) {
		jdbcTemplate.update("UPDATE book SET person_id = NULL WHERE id=?", id);
	}

	public void assignPerson(int id, Person person) {
		jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", person.getId(), id);
	}
}
