package tn.enicarthage.useful;

public class Abonnementverifier {
private int id;
public Abonnementverifier() {
	super();
	// TODO Auto-generated constructor stub
}
public Abonnementverifier(int id, String cle) {
	super();
	this.id = id;
	this.cle = cle;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCle() {
	return cle;
}
public void setCle(String cle) {
	this.cle = cle;
}
private String cle;

}
