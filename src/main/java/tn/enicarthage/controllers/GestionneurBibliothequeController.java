package tn.enicarthage.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import tn.enicarthage.entities.Etudiant;
import tn.enicarthage.entities.GestionneurBibliotheque;
import tn.enicarthage.entities.Role;
import tn.enicarthage.services.GestionneurbibliothequeService;
import tn.enicarthage.useful.Authentify;
import tn.enicarthage.useful.RoleDeserializer;
import tn.enicarthage.useful.UserPDFExporter;

@RestController
public class GestionneurBibliothequeController {
	@Autowired
	GestionneurbibliothequeService gestionneurservice;
	
	@CrossOrigin(origins="http://localhost:4200")
@GetMapping("/gestionneur/Etudiants")
public ResponseEntity<List<Etudiant>> getEtudiants() {
	
	try {
		return new ResponseEntity<>(gestionneurservice.getetudiants(),HttpStatus.OK);
	}
    catch(Exception e) {
    	return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
	 
}
	@CrossOrigin(origins="http://localhost:4200")	
@GetMapping("/admin")
public ResponseEntity<GestionneurBibliotheque> getdetails(){
	return new ResponseEntity<>(gestionneurservice.getadministrateur(),HttpStatus.ACCEPTED);
}

@Modifying
@Transactional
@CrossOrigin(origins="http://localhost:4200")
@PutMapping("/{id}/{role}")
public ResponseEntity<String> updateEtudiantRole(@PathVariable("id") Long id, @JsonDeserialize(using = RoleDeserializer.class)@PathVariable("role") Role role) {
    gestionneurservice.updateEtudiantRole(role, id);
   // return ResponseEntity.ok().build();
    return new ResponseEntity<>("{\"message\":\"etudiant with" +id+ "role modified\"}",HttpStatus.ACCEPTED);
}
@CrossOrigin(origins="http://localhost:4200")
@GetMapping("/cles")
public List<String> getcles(){
	return gestionneurservice.getabonnements();
}
@CrossOrigin(origins="http://localhost:4200")
@PostMapping("/loginadmin")
public ResponseEntity<String>logingestionneur(@RequestBody Authentify g){
	return gestionneurservice.login(g);
}
@CrossOrigin(origins="http://localhost:4200")
@GetMapping("/etudiants/{role}")
List<Etudiant> getetudiants(@JsonDeserialize(using = RoleDeserializer.class) @PathVariable("role") Role role){
	return gestionneurservice.getetudiantsbyrole(role);
}
@CrossOrigin(origins="http://localhost:4200")
@GetMapping("/admin/export/pdf")
public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
    response.setContentType("application/pdf");
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    String currentDateTime = dateFormatter.format(new Date());
     
    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
    response.setHeader(headerKey, headerValue);
     
    List<Etudiant> listUsers = gestionneurservice.getetudiants();
     
    UserPDFExporter exporter = new UserPDFExporter(listUsers);
    exporter.export(response);
     
}

}
