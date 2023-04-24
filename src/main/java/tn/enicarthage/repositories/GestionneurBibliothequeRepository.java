package tn.enicarthage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.enicarthage.entities.GestionneurBibliotheque;
import tn.enicarthage.entities.Role;

@Repository
public interface GestionneurBibliothequeRepository extends JpaRepository<GestionneurBibliotheque,Long> {
@Query("select u from GestionneurBibliotheque u")
public GestionneurBibliotheque getgestionneur();
public GestionneurBibliotheque findByidutilisateur(int id);
}
