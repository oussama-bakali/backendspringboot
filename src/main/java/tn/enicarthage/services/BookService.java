package tn.enicarthage.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.enicarthage.entities.Livre;
import tn.enicarthage.repositories.LivreRepository;

@Service
public class BookService {
@Autowired
LivreRepository livrerepository;
public List <Livre>GetAllBooks(){
	return livrerepository.getAllLivre();
}
public List <Livre> PremiumBooks(){
	return livrerepository.Premiumbooks();
}
public List<Livre> nonPremiumBooks(){
	return livrerepository.nonPremiumbooks();
}
public ResponseEntity<String> addBook(Livre e){
	this.livrerepository.save(e);
	return new ResponseEntity<>("{\"message\":\"Book added successfuly\"}",HttpStatus.ACCEPTED);
}
public Livre getlivrebyid(int reference) {
	return livrerepository.getlivrebyid(reference);
}

}
