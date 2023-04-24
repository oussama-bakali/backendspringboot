package tn.enicarthage.security;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String jwtToken;

    public AuthenticationResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getToken() {
        return this.jwtToken;
    }
}