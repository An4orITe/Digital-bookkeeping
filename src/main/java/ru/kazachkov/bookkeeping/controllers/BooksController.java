package ru.kazachkov.bookkeeping.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kazachkov.bookkeeping.dao.BookDao;
import ru.kazachkov.bookkeeping.dao.PersonDao;
import ru.kazachkov.bookkeeping.models.Book;
import ru.kazachkov.bookkeeping.models.Person;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

	private final BookDao bookDao;
	private final PersonDao personDao;

	@Autowired
	public BooksController(BookDao bookDao, PersonDao personDao) {
		this.bookDao = bookDao;
		this.personDao = personDao;
	}

	@GetMapping
	public String showAllBooks(Model model){
		model.addAttribute("books", bookDao.getBooks());
		return "books/all";
	}

	@GetMapping("/new")
	public String newBook(@ModelAttribute("book") Book book) {
		return "books/new";
	}

	@PostMapping()
	public String createNewBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "books/new";
		}
		bookDao.save(book);
		return "redirect:/books";
	}

	@GetMapping("/{id}")
	public String showBook(Model model, @PathVariable("id")int id, @ModelAttribute("person") Person person){
		model.addAttribute("book", bookDao.getBookById(id));
		Optional<Person> personBook = bookDao.getPersonBook(id);
		if (personBook.isPresent()) {
			model.addAttribute("owner", personBook.get());
		} else {
			model.addAttribute("people", personDao.getPeople());
		}
		System.out.println(model.asMap());
		return "books/book";
	}

	@GetMapping("/{id}/edit")
	public String editBook(@PathVariable("id") int id, Model model){
		model.addAttribute("book", bookDao.getBookById(id));
		return "books/edit";
	}

	@PatchMapping("/{id}")
	public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "books/edit";
		}
		bookDao.update(id, book);
		return "redirect:/books";
	}

	@DeleteMapping("/{id}")
	public String deleteBook(@PathVariable("id") int id) {
		bookDao.delete(id);
		return "redirect:/books";
	}

	@PatchMapping("/{id}/free")
	public String freeBook(@PathVariable("id") int id) {
		bookDao.freeBook(id);
		return "redirect:/books/" + id;
	}

	@PatchMapping("/{id}/assign")
	public String assignPerson(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
		bookDao.assignPerson(id, person);
		return "redirect:/books/" +id;
	}
}
