package tn.enicarthage.entities;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

@Entity

public class GestionneurBibliotheque extends Utilisateur {
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<Etudiant> etudiant;
	private String badgegestionneurbib;
	private String email;
	
	public GestionneurBibliotheque(Set<Etudiant> etudiant, String badgegestionneurbib,String email) {
		super();
		this.etudiant = etudiant;
		this.badgegestionneurbib = badgegestionneurbib;
		this.email=email;
	}
	public Set<Etudiant> getEtudiant() {
		return etudiant;
	}
	
	public GestionneurBibliotheque() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setEtudiant(Set<Etudiant> etudiant) {
		this.etudiant = etudiant;
	}
	public String getBadgegestionneurbib() {
		return badgegestionneurbib;
	}
	public void setBadgegestionneurbib(String badgegestionneurbib) {
		this.badgegestionneurbib = badgegestionneurbib;
	}
	
}
