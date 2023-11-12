package ru.kazachkov.bookkeeping.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kazachkov.bookkeeping.models.Person;

import java.util.List;

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
		jdbcTemplate.update("INSERT INTO Person (name, birthyear) VALUES (?, ?)", person.getName(), person.getBirthYear());
	}
}
