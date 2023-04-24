package tn.enicarthage.entities;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class GestionneurStock extends Utilisateur implements Serializable  {
@OneToMany(cascade=CascadeType.ALL)
public Set<Livre> livres;
public String badge;
}
