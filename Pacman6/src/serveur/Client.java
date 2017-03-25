package serveur;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Client implements Communicateur {

/********************************************** Déclaration du constructeur : */


	public Client(String IPServeur, int port/*, panDessin pan, panText pan2*/) {
		
		this.IPServeur = IPServeur;
		this.port = port;
		//this.pan = pan;
		//this.pan2 = pan2;
		creationClient();
	}	
	

/************************************************* Déclaration des méthodes : */


	public void creationClient() {
		
		
		// Création du socket {serveur}.
		try {
			
			Socket s = new Socket(IPServeur, port);
			
			// Création de la connection {Serveur, moi}.
			cnx = new Connexion(s, this);
			
			System.out.println("Vous etes connecte au serveur ...");
			
		} catch (Exception e) {
			
			System.out.println("Erreur lors de la création du client : " + e);
		}
	}
	
	
	public void traiteMessage(Object O)	{	
		
		try	{
	
	
			
			if (Class.forName("Figure").isAssignableFrom(O.getClass())) {
				
				System.out.println("Figure recue : " + O);
				//pan.getList().addLast((Figure) O);
				//pan.repaint();
			
			} else {
				
		

				if (Class.forName("Message").isAssignableFrom(O.getClass())) {
					
					System.out.println("Message recu : " + O);
					//pan2.getList().addLast((Message) O);
					//pan2.repaint();	
				
				} else {
				

						
					idClient = Integer.parseInt((String) O);
					
					System.out.println("Le serveur vous attribue l'identifiant " + idClient + ".");
				}				
			}
			
		} catch (Exception e) {
		
			System.out.println("Objet recu non identifié !");
		}	
	}
	
	
	public Connexion getCnx() {
		
		return cnx;
	}
	
	public int getIdClient() {
		
		return idClient;
	}
    

/************************************************ Déclaration des variables : */


	private String IPServeur;
	private int port;
	private Connexion cnx;
	//private panDessin pan;
	//private panText pan2;
	private int idClient;
}