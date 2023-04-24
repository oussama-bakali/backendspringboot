package tn.enicarthage.security;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}

	private String password;

    

    public AuthenticationRequest(int idutilisateur, String password) {
    this.setId(idutilisateur);
        this.setPassword(password);
    }

    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
