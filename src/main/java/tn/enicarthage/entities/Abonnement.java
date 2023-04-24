package tn.enicarthage.entities;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Abonnement implements Serializable  {
	@Id
private int idabonnement;
private int dureeabonnement;
@Temporal(TemporalType.DATE)
private Date dateabonnement;
public Abonnement(int idabonnement, int dureeabonnement, Date dateabonnement, String cleabonnement, boolean etatcle,
		Etudiant etudiant) {
	super();
	this.idabonnement = idabonnement;
	this.dureeabonnement = dureeabonnement;
	this.dateabonnement = dateabonnement;
	this.cleabonnement = cleabonnement;
	this.etatcle = etatcle;
	this.etudiant = etudiant;
}
private String cleabonnement;
private boolean etatcle;
public String getCleabonnement() {
	return cleabonnement;
}
public void setCleabonnement(String cleabonnement) {
	this.cleabonnement = cleabonnement;
}
@OneToOne
private Etudiant etudiant;
public int getIdabonnement() {
	return idabonnement;
}
public Abonnement() {
	super();
	// TODO Auto-generated constructor stub
}
public void setIdabonnement(int idabonnement) {
	this.idabonnement = idabonnement;
}

public Etudiant getEtudiant() {
	return etudiant;
}
public void setEtudiant(Etudiant etudiant) {
	this.etudiant = etudiant;
}
public Date getDureeabonnement() {
	return this.dateabonnement;
}
public void setDureeabonnement(Date dureeabonnement) {
	this.dateabonnement = dureeabonnement;
}
public Date getDateabonnement() {
	return dateabonnement;
}
public void setDateabonnement(Date dateabonnement) {
	this.dateabonnement = dateabonnement;
}
public boolean isEtatcle() {
	return etatcle;
}
public void setEtatcle(boolean etatcle) {
	this.etatcle = etatcle;
}
public void setDureeabonnement(int dureeabonnement) {
	this.dureeabonnement = dureeabonnement;
}

}
