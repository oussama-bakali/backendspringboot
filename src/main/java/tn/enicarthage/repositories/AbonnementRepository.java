package tn.enicarthage.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import tn.enicarthage.entities.Abonnement;
@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement,Long> {
@Query("select u from Abonnement u where  u.cleabonnement =:cle")
Abonnement findAbonnementById(String cle);
@Query("select u from Abonnement u where u.cleabonnement =:cle and etatcle=true")
Abonnement getactivatedcles(String cle);

@Query("select u from Abonnement u where u.cleabonnement =:cle and etatcle=false")
Abonnement usedcles(String cle);


@Query("select u.cleabonnement from Abonnement u  ")
List <String> getclesabonnement();
@Modifying
@Query("update Abonnement u set u.etatcle=false where cleabonnement =:cle")
void updateetatcle(String  cle); 
@Modifying
@Query("update Abonnement u set u.etudiant.idutilisateur =:id where cleabonnement =:cle ")
void addetudiant(String cle, int id);
@Modifying
@Query("update Abonnement u set u.dateabonnement =:date where cleabonnement =:cle ")
void updatedate(Date date,String cle);

}
