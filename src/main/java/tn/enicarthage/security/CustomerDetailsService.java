package tn.enicarthage.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tn.enicarthage.repositories.EtudiantRepository;

@Service
public class CustomerDetailsService implements UserDetailsService {
tn.enicarthage.entities.Etudiant usedetails;

EtudiantRepository userrepo;
	@Override 
	   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		this.usedetails=userrepo.findByidutilisateur(Integer.parseInt(username));
		System.out.println("OK2");
		if (!Objects.isNull(usedetails)) {
			List<GrantedAuthority>roles=new ArrayList<>();
			System.out.println("OK1");
			roles.add(new SimpleGrantedAuthority(usedetails.getRole().name()));
			
			
			return new User(Integer.toString(usedetails.getIdutilisateur()),usedetails.getPassword(), usedetails.getAuthorities());
			//return new User(usedetails.getUsername(),usedetails.getPassword(), roles);
		  } else { 
		         throw new UsernameNotFoundException("User not found with username: " + username); 
		      } 
		}
		
	public tn.enicarthage.entities.Etudiant getuser() {
		return usedetails;
	}
	   } 