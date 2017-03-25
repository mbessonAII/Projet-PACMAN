package serveur;
/** Cette interface permet de "typer" les objets "Client" et "Serveur" afin * de créer des connexions entre eux. */ 

public interface Communicateur {
	
	public void traiteMessage(Object inMessage) throws java.io.IOException;
}