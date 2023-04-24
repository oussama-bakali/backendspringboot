package tn.enicarthage.entities;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;


@Entity
@Table(name="Livre")
public class Livre implements Serializable  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int reference;
	@Column(name="codeabar")
private String codeabar;
	public Livre(int reference, String codeabar, String auteur, int nbpages, 
			 String imagelivre, String fichlivre, boolean premium) {
		super();
		this.reference = reference;
		this.codeabar = codeabar;
		this.auteur = auteur;
		this.nbpages = nbpages;
		this.etudiant = etudiant;
		this.gestionneurstock = gestionneurstock;
		this.imagelivre = imagelivre;
		this.fichlivre = fichlivre;
		this.premium = premium;
	}
	public Livre() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Column(name="auteur")
private String auteur;
	@Column(name="nbpages")
private int nbpages;
	@ManyToMany(cascade=CascadeType.ALL,mappedBy="Livres")
	private Set< Etudiant> etudiant;
	@ManyToOne(cascade=CascadeType.ALL)
	private GestionneurStock gestionneurstock;
	private String imagelivre;
	private String fichlivre;
	private boolean premium;
public boolean isPremium() {
		return premium;
	}
	public void setPremium(boolean premium) {
		this.premium = premium;
	}
public String getImagelivre() {
		return imagelivre;
	}
	public void setImagelivre(String imagelivre) {
		this.imagelivre = imagelivre;
	}
	public String getFichlivre() {
		return fichlivre;
	}
	public void setFichlivre(String fichlivre) {
		this.fichlivre = fichlivre;
	}
public int getReference() {
	return reference;
}
public void setReference(int reference) {
	this.reference = reference;
}
public String getCodeabar() {
	return codeabar;
}
public void setCodeabar(String codeabar) {
	this.codeabar = codeabar;
}
public String getAuteur() {
	return auteur;
}
public void setAuteur(String auteur) {
	this.auteur = auteur;
}
public int getNbpages() {
	return nbpages;
}
public void setNbpages(int nbpages) {
	this.nbpages = nbpages;
}


}
