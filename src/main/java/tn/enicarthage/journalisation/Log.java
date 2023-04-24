package tn.enicarthage.journalisation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class Log {
	@After("execution(* tn.enicarthage.services.EtudiantService.*(..))")
	public void firstafteremethodlogging(JoinPoint joinPoint) {
	String name = joinPoint.getSignature().getName();
	
	System.out.println("after EtudiantService methods:" + joinPoint.getSignature());
}
	@After("execution(* tn.enicarthage.services.GestionneurbibliothequeService.*(..))")
	public void Secondaftermethodlog(JoinPoint joinPoint) {
	String name = joinPoint.getSignature().getName();
	
	System.out.println("After Gestionneurbibliothequeservice methods" + joinPoint.getSignature());
}	
	@Before("execution(* tn.enicarthage.services.EtudiantService.*(..))")
	public void firstbeforeemethodlogging(JoinPoint joinPoint) {
	String name = joinPoint.getSignature().getName();
	
	System.out.println("beforeEtudiantService methods:" + joinPoint.getSignature());
}
	@Before("execution(* tn.enicarthage.services.GestionneurbibliothequeService.*(..))")
	public void Secondbeforemethodlog(JoinPoint joinPoint) {
	String name = joinPoint.getSignature().getName();
	
	System.out.println("before Gestionneurbibliothequeservice methods" + joinPoint.getSignature());
}	
}
