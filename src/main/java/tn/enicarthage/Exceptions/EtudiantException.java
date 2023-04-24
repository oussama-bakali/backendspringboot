package tn.enicarthage.Exceptions;

public class EtudiantException extends Exception {
	public EtudiantException() {
		super("user with this mail is already existed");
	}

}
