package ru.kazachkov.bookkeeping.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kazachkov.bookkeeping.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {

	JdbcTemplate jdbcTemplate;

	@Autowired
	public PersonDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Person> getPeople() {
		return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
	}

	public void save(Person person) {
		jdbcTemplate.update("INSERT INTO Person (name, birthYear) VALUES (?, ?)", person.getName(), person.getBirthYear());
	}

	public Person getPersonById(int id) {
		return jdbcTemplate.query("SELECT * FROM Person where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
				.stream().findAny().orElse(null);
	}

	public void update(int id, Person person) {
		jdbcTemplate.update("UPDATE person SET name=?, birthYear=? where id=?", person.getName(), person.getBirthYear(), id);
	}

	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM person where id=?", id);
	}
}
