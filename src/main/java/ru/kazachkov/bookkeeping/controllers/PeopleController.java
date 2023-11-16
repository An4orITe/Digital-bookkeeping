package ru.kazachkov.bookkeeping.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kazachkov.bookkeeping.dao.PersonDao;
import ru.kazachkov.bookkeeping.models.Person;
import ru.kazachkov.bookkeeping.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {

	private final PersonDao personDao;
	private final PersonValidator personValidator;

	@Autowired
	public PeopleController(PersonDao personDao, PersonValidator personValidator) {
		this.personDao = personDao;
		this.personValidator = personValidator;
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
	public String createNewPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
		personValidator.validate(person, bindingResult);
		if (bindingResult.hasErrors()) {
			return "people/new";
		}
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
	public String updatePerson(@ModelAttribute("person") @Valid Person person, @PathVariable("id") int id,
							   BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "people/edit";
		}
		personDao.update(id, person);
		return "redirect:/people";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		personDao.delete(id);
		return "redirect:/people";
	}
}
