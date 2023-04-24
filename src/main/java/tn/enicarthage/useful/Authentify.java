package tn.enicarthage.useful;

public class Authentify {
	private int id;
	private String password;
	public Authentify(int id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	public Authentify() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
