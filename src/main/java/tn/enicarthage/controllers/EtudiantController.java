package tn.enicarthage.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.transaction.Transactional;

import tn.enicarthage.Exceptions.EtudiantException;
import tn.enicarthage.entities.Etudiant;
import tn.enicarthage.entities.GestionneurStock;
import tn.enicarthage.entities.Livre;
import tn.enicarthage.entities.Role;
import tn.enicarthage.security.CustomerDetailsService;
import tn.enicarthage.services.BookService;
import tn.enicarthage.services.EtudiantService;
import tn.enicarthage.useful.Abonnementverifier;
import tn.enicarthage.useful.SendEmail;

@RestController
public class EtudiantController {
	@Autowired
	EtudiantService etudiantservice;
	@Autowired
	BookService bookservice;
	@Autowired 
	 SendEmail sendmail;
	 @Autowired
	    private AuthenticationManager authenticationManager;
	  @Autowired
	    private tn.enicarthage.security.JwtTokenUtil jwtTokenutil;

	    @Autowired
	    private CustomerDetailsService userDetailsService;
	@CrossOrigin(origins="http://localhost:4200")
@PostMapping("/registeretudiant")
@ResponseBody
public ResponseEntity<?> registeretudiant(@RequestBody Etudiant e) {
try {
	etudiantservice.registeretudiant(e);
	//String success="registredsuccessfuly";
	String message ="l etudiant with id "+e.getIdutilisateur()+"nom "+e.getNom()+""+e.getPrenom()+" s est registré";
	this.sendmail.sendmessage("dinar6454@gmail.com", "activation of user", message);
	return new ResponseEntity<>("{\"message\":\"registersuccessfuly\"}",HttpStatus.OK);
	
	
	
	//return ResponseEntity.ok().build();
}
catch (EtudiantException ex) {
	return new ResponseEntity<>("{\"message\":\"etudiant exists\"}",HttpStatus.CONFLICT);
}
		
		
	
	
	
}
	@CrossOrigin(origins="http://localhost:4200")
@PostMapping("/loginetudiant")
@ResponseBody
public ResponseEntity<String> loginetudiant(@RequestBody Etudiant e) {
	return etudiantservice.login(e);
}
	@CrossOrigin(origins="http://localhost:4200")	
@GetMapping("/etudiant/{id}")
public ResponseEntity<Etudiant> getetudiantbyid(@PathVariable int id){
	return etudiantservice.getetudiantbyid(id);
}
@CrossOrigin(origins="http://localhost:4200")
@GetMapping("/verifycle/{cle}")
public ResponseEntity<String>verifycle(@PathVariable String cle){
	
	return etudiantservice.verifycle(cle);
}
@Modifying
@Transactional
@CrossOrigin(origins="http://localhost:4200")
@PostMapping("/activateabonnement")
public ResponseEntity<String>activateabonnement(  @RequestBody Abonnementverifier abonnementverifier){
	return etudiantservice.activateabonnement(abonnementverifier.getCle(), abonnementverifier.getId());
}
@CrossOrigin(origins="http://localhost:4200")
@PostMapping("/{id}/{reference}/buybook")
public ResponseEntity<String>buybook(@PathVariable int id,@PathVariable int reference){
	
	return this.etudiantservice.buybookbyetudiant(id, reference);
}
@CrossOrigin(origins="http://localhost:4200")
@PreAuthorize("hasAuthority('PREMIUM')")
@PostMapping("/{id}/{reference}/buypremiumbook")
public ResponseEntity<String>premiumbook(@PathVariable int id,@PathVariable int reference){
	return this.etudiantservice.buypremiumbook(id, reference);
}
@CrossOrigin(origins="http://localhost:4200")
@PostMapping("/{id}/{reference}/buynormalbook")
public ResponseEntity<String>normalbook(@PathVariable int id,@PathVariable int reference){
	return this.etudiantservice.buynonpremiumbook(id, reference);
}
}
