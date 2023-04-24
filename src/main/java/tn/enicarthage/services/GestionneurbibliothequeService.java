package tn.enicarthage.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.persistence.NamedQuery;
import tn.enicarthage.entities.Etudiant;
import tn.enicarthage.entities.GestionneurBibliotheque;
import tn.enicarthage.entities.Role;
import tn.enicarthage.repositories.AbonnementRepository;
import tn.enicarthage.repositories.EtudiantRepository;
import tn.enicarthage.repositories.GestionneurBibliothequeRepository;
import tn.enicarthage.security.AuthenticationResponse;
import tn.enicarthage.security.JwtTokenUtil;
import tn.enicarthage.useful.Authentify;
import tn.enicarthage.useful.SendEmail;
@Service
public class GestionneurbibliothequeService {
	
	@Autowired
	EtudiantRepository etudiants;
	@Autowired 
	GestionneurBibliothequeRepository administrateur;
	@Autowired 
	AbonnementRepository abonnement;
	@Autowired
	SendEmail sendemail;
	@Autowired 
	JwtTokenUtil jwtutil;
public void sendmessage(String email,String subject,String body) {
		
		SimpleMailMessage msg =new SimpleMailMessage();
		msg.setFrom("ousso3527@gmail.com");
		msg.setTo(email);
		
		msg.setText(body);
		msg.setSubject(subject);
		
		this.sendemail.getJavamail().send(msg);
	}
	
	public List<String> getabonnements(){
		return  abonnement.getclesabonnement();
	}
	public List<Etudiant>getetudiants(){
		return etudiants.findAll();
	}
	
	public void updateEtudiantRole(Role role, Long id) {
		etudiants.updateEtudiantRole(role, id);
	}
	public List<Etudiant> getetudiantsbyrole(Role role){
		return etudiants.getetudiantsbyrole(role);
	}
	public GestionneurBibliotheque getadministrateur() {
		return administrateur.getgestionneur();
	}
	public ResponseEntity<String> login( @RequestBody Authentify g) {
		GestionneurBibliotheque gestionneur=administrateur.findByidutilisateur(g.getId())  ;
		System.out.println(g.getId());
		
		if (gestionneur==null) {
			return new ResponseEntity<>("{\"message\":\"user doesnt exist verify again\"}",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (gestionneur.getPassword().equals(g.getPassword())) {
			String token = jwtutil.generateToken(gestionneur.getNom(),gestionneur.getBadgegestionneurbib(),gestionneur.getIdutilisateur());
			return new ResponseEntity(new AuthenticationResponse(token),HttpStatus.OK);
		}
		return new ResponseEntity<>("{\"message\":\"id or password incorrect\"}",HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
