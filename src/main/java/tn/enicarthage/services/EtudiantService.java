package tn.enicarthage.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.persistence.EntityNotFoundException;
import tn.enicarthage.Exceptions.EtudiantException;
import tn.enicarthage.entities.Abonnement;
import tn.enicarthage.entities.Etudiant;
import tn.enicarthage.entities.Livre;
import tn.enicarthage.entities.Role;
import tn.enicarthage.repositories.AbonnementRepository;
import tn.enicarthage.repositories.EtudiantRepository;
import tn.enicarthage.repositories.LivreRepository;
import tn.enicarthage.security.AuthenticationResponse;
import tn.enicarthage.security.JwtTokenUtil;
import tn.enicarthage.useful.SendEmail;

@Service
public class EtudiantService {
@Autowired
EtudiantRepository etudiantrepository;
@Autowired
  AbonnementRepository abonnementrepo;
@Autowired
LivreRepository livrerepo;
@Autowired
SendEmail sendmail;
@Autowired 
JwtTokenUtil jwtutil;
public Etudiant etudiantbyid(int id) {
	return etudiantrepository.findByidutilisateur(id);
}
public void registeretudiant(Etudiant e) throws EtudiantException {
	Optional <Etudiant> etudiant=etudiantrepository.findById((long)e.getIdutilisateur());
	Etudiant et=etudiantrepository.getetudiantbymail(e.getEmail());
	
	if (etudiant.isPresent()|| et!=null) {
		throw new EtudiantException();
	}
	e.setRole(Role.NONACTIVATED);
	e.setGestionneur(null);
etudiantrepository.save(e);	
String message="Hello sir thank you for registring on our website ,you coordinates to signing up onour website ,your coordinates are "+e.getIdutilisateur()+""+e.getPassword()+"thank you for your trust";
this.sendmail.sendmessage(e.getEmail(), "registration at Bibliotheque", message);

}
public ResponseEntity<String> login(@RequestBody Etudiant e) {
	Etudiant etudiant=etudiantrepository.findByidutilisateur(e.getIdutilisateur());
	if (etudiant==null) {
		return new ResponseEntity<>("{\"message\":\"user doesnt exist verify again\"}",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	if (etudiant.getPassword().equals(e.getPassword())) {
		System.out.println(etudiant.getRole().name()+"noob");
		etudiant.setRole(etudiant.getRole());
		String token = jwtutil.generateToken(e.getNom(),etudiant.getRole().name(),etudiant.getIdutilisateur());
		return new ResponseEntity(new AuthenticationResponse(token),HttpStatus.OK);
	//	return new ResponseEntity<>("{\"message\":\"loginsuccessfuly\"}",HttpStatus.OK);
	}
	return new ResponseEntity<>("{\"message\":\"id or password incorrect\"}",HttpStatus.INTERNAL_SERVER_ERROR);
}
public ResponseEntity<Etudiant> getetudiantbyid(long id) {
	Optional <Etudiant> e =etudiantrepository.findById(id);
	return ResponseEntity.ok(e.get());
}
public ResponseEntity<String>verifycle(String cle){
	//System.out.println(cle);
	if (abonnementrepo.usedcles(cle)!=null) {
		return new ResponseEntity<>("{\"message\":\"cle used\"}",HttpStatus.CONFLICT);
	}
	if (abonnementrepo.getactivatedcles(cle)==null) {
		return new ResponseEntity<>("{\"message\":\"cle not found\"}",HttpStatus.NOT_FOUND);
		
	}
	return new ResponseEntity<>("{\"message\":\"cle found\"}",HttpStatus.OK);
}
public ResponseEntity<String>activateabonnement( String cle, int id){
	Etudiant e=etudiantrepository.findByidutilisateur(id);
	if (abonnementrepo.getactivatedcles(cle)!=null) {
this.etudiantrepository.premiumEtudiant(id, Role.PREMIUM);	
this.abonnementrepo.updateetatcle(cle);
this.abonnementrepo.addetudiant(cle, id);
Abonnement abo=this.abonnementrepo.findAbonnementById(cle);
LocalDate date=LocalDate.now().plusDays(60);
Date dateabonnement=Date.valueOf(date);
this.abonnementrepo.updatedate(dateabonnement, cle);


		return new ResponseEntity<>("{\"message\":\"Congrats you are a premium member\"}",HttpStatus.OK);
	}
	return new ResponseEntity<>("{\"message\":\"key is used or invalid try later\"}",HttpStatus.NOT_FOUND);
}
public ResponseEntity<String> buybookbyetudiant(int id,int reference) {
	Livre l=this.livrerepo.getlivrebyid(reference);
	Set<Livre> livres=new HashSet<>();
	
	livres.add(l);
	
	
	
	Etudiant e=this.etudiantrepository.getById((long)id);
	if (!e.getLivres().isEmpty()) {
		Set<Livre> set=e.getLivres();
		
		set.add(l);
		e.setLivres(set);
		this.etudiantrepository.save(e);
		System.out.println(l.getAuteur());
	}
	else {
		e.setLivres(livres);
		this.etudiantrepository.save(e);
	}
	//
//	
	
	
	
	
	if (livres.isEmpty()) {
		return new ResponseEntity<>("{\"message\":\"empty\"}",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	return new ResponseEntity<>("{\"messagesuccess\":\"Congrats you bought a book\"}",HttpStatus.ACCEPTED);
}
public ResponseEntity<String>buypremiumbook(int id,int reference){
Set<Livre> livres=new HashSet<>();
	
	
	Livre l=this.livrerepo.getlivrebyid(reference);
	if (l==null) {
		return new ResponseEntity<>("{\"messagefail\":\"this book doesnt exist\"}",HttpStatus.BAD_REQUEST);
	}
	if (l.isPremium()) {
		livres.add(l);
	}
	
	Etudiant e=this.etudiantrepository.getById((long)id);
	if (e.getRole()==Role.PREMIUM&&!e.getLivres().isEmpty()) {
Set<Livre> set=e.getLivres();
		
		set.add(l);
		e.setLivres(set);
		this.etudiantrepository.save(e);
	}
	if (e.getRole()==Role.PREMIUM&&e.getLivres().isEmpty()) {
		e.setLivres(livres);
		this.etudiantrepository.save(e);
				
			}
	if (e.getRole()!=Role.PREMIUM) {
		return new ResponseEntity<>("{\"messagefail\":\"it s a book for premium user\"}",HttpStatus.BAD_REQUEST);
	}
	sendmail.sendmessage(e.getEmail(),"reception d unpremium book", "tu as recu un premium book de reference"+l.getReference());
	return new ResponseEntity<>("{\"messagesuccess\":\"Congrats you bought a premium book\"}",HttpStatus.ACCEPTED);
}
public ResponseEntity<String>buynonpremiumbook(int id,int reference){
Set<Livre> livres=new HashSet<>();
	
	
	Livre l=this.livrerepo.getlivrebyid(reference);
	if (l==null) {
		return new ResponseEntity<>("{\"messagefail\":\"this book doesnt exist\"}",HttpStatus.BAD_REQUEST);
	}
	if (!l.isPremium()) {
		livres.add(l);
	}
	
	Etudiant e=this.etudiantrepository.getById((long)id);
	if (!e.getLivres().isEmpty()) {
		Set<Livre> set=e.getLivres();
		
		set.add(l);
		e.setLivres(set);
		this.etudiantrepository.save(e);
		System.out.println(l.getAuteur());
	}
	else {
		e.setLivres(livres);
		this.etudiantrepository.save(e);
	}
	sendmail.sendmessage(e.getEmail(),"reception d un book normal", "tu as recu un  booknormal a fin d obtenir plus de livres activer votre abonnement");
	return new ResponseEntity<>("{\"messagesuccess\":\"Congrats you bought a normal book\"}",HttpStatus.ACCEPTED);
}
}
