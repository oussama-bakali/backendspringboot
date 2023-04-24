package tn.enicarthage.repositories;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import tn.enicarthage.entities.Etudiant;
import tn.enicarthage.entities.Livre;
import tn.enicarthage.entities.Role;

public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {

Etudiant findByidutilisateur(int id);
@Query("SELECT e from Etudiant e where e.role =  :role")
List<Etudiant> getetudiantsbyrole( Role role);
@Query ("select u from Etudiant u where u.email=:email")
Etudiant getetudiantbymail(String email);
@Modifying
@Query("UPDATE Etudiant e SET e.role = :role WHERE e.idutilisateur = :id")
void  updateEtudiantRole(@Param("role") Role role, @Param("id")Long id);
@Modifying
@Query("UPDATE Etudiant e SET e.role =:role WHERE e.idutilisateur=:id")
void premiumEtudiant(int id,Role role);


}
