package tn.enicarthage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class GestionBibliothequeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionBibliothequeApplication.class, args);
	}

}
