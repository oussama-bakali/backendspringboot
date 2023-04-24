package tn.enicarthage.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tn.enicarthage.entities.Livre;
import tn.enicarthage.services.BookService;

@RestController
public class BookController {
	@Autowired
	public BookService bookservice;
	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping("/books")
	public List <Livre>GetAllBooks(){
	return bookservice.GetAllBooks();	
	}
	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping("/premiumBooks")
	public List<Livre> getpremiumbooks(){
		return bookservice.PremiumBooks();
	}
	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping("/nonpremiumbooks")
	public List<Livre> nonpremiumbooks(){
		return bookservice.nonPremiumBooks();
	}
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/addbook")
	public ResponseEntity<String> addBook(@RequestBody Livre e) {
		return bookservice.addBook(e);
	}
	

}
