package tn.enicarthage.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.enicarthage.entities.Livre;
@Repository
public interface LivreRepository extends JpaRepository<Livre,Long> {
	@Query("SELECT u from Livre u")
   public   List<Livre> getAllLivre();
	@Modifying
	@Query("UPDATE Livre u SET u.fichlivre=:name WHERE u.reference=:id")
     public Livre updateLivrefile(@Param("id")int id ,@Param("name") String name);
	@Query("select u from Livre u where u.premium=1")
	public List<Livre> Premiumbooks();
	@Query("select u from Livre u where u.premium=0")
	List<Livre> nonPremiumbooks();
	@Query("select u from Livre u where u.reference=:reference")
	public Livre getlivrebyid(int reference);
	@Query("select u from Livre u where u.reference=:reference and u.premium=0")
	Livre nonPremiumbook(int reference);
	@Query("select u from Livre u where u.reference=:reference and u.premium=1")
	public Livre Premiumbook(int reference);
}
