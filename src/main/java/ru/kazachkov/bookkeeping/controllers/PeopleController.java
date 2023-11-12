package ru.kazachkov.bookkeeping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kazachkov.bookkeeping.dao.PersonDao;
import ru.kazachkov.bookkeeping.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {

	PersonDao personDao;

	@Autowired
	public PeopleController(PersonDao personDao) {
		this.personDao = personDao;
	}

	@GetMapping
	public String showAllPeople(Model model) {
		model.addAttribute("people", personDao.getPeople());
		return "people/all";
	}

	@GetMapping("/new")
	public String newPerson(@ModelAttribute("person") Person person) {
		return "/people/new";
	}

	@PostMapping()
	public String createNewPerson(@ModelAttribute("person") Person person) {
		personDao.save(person);
		return "redirect:/people";
	}
}
