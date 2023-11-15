package ru.kazachkov.bookkeeping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

	@GetMapping("/{id}")
	public String showPerson(@PathVariable("id") int id, Model model) {
		model.addAttribute("person", personDao.getPersonById(id));
		model.addAttribute("personBooks", personDao.getPersonBooksByPersonId(id));
		return "people/person";
	}

	@GetMapping("/{id}/edit")
	public String editPerson(@PathVariable("id") int id, Model model) {
		model.addAttribute("person", personDao.getPersonById(id));
		return "people/edit";
	}

	@PatchMapping("/{id}")
	public String updatePerson(@ModelAttribute("person")Person person, @PathVariable("id") int id) {
		personDao.update(id, person);
		return "redirect:/people";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		personDao.delete(id);
		return "redirect:/people";
	}
}
