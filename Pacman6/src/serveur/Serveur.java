package serveur;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.lang.Runnable;

public class Serveur implements Communicateur, Runnable {

/********************************************** D�claration du constructeur : */


	public Serveur(int port, int nbCli) {
		
		this.port = port;
		this.nbCli = nbCli;
		lFigure = new LinkedList();
		lMessages = new LinkedList();
		creationServeur();
	}	
	
	
/************************************************* D�claration des m�thodes : */

	
	public void creationServeur() {
	
		try {
	
			serv = new ServerSocket(port, nbCli);

		} catch (Exception e) {
			
			System.out.println("Erreur dans la cr�ation du serveur : " + e);	
		}
		
		Thread t = new Thread(this);
		t.start();				
	}
	
		
	public void run()
	{
		lCli = new LinkedList();
		
		try {

			while (true) {
			
				System.out.println("En attente d'un client ...");
				
				Socket cli = serv.accept();
				
				System.out.println("Client accepte ...");
				
				cnx = new Connexion(cli, this);
				
				System.out.println("Client connecte ...\n");
				
				// On renvoie l'identifiant du client {Utile lorsqu'il d�sire
				// Changer de serveur et donc se retirer de celui-ci}.
				
				cnx.Envoie((Object) (new Integer (lCli.size()).toString()));

				// Si un client se connecte en retard, il doit r�cup�rer le 
				// Travail d�j� fait, nous lui envoyons donc les figures.
				
				for (int i = 0; i < lFigure.size(); i ++) {
					
					cnx.Envoie(lFigure.get(i));	
				}
				
				// Puis les messages.
				
				for (int i = 0; i < lMessages.size(); i ++) {
					
					cnx.Envoie(lMessages.get(i));	
				}		
							
				// Puis on ajoute le client {Ou plut�t sa connection} � la liste.
				
				lCli.add(cnx);
			}
			
		} catch(Exception e) {
		
			System.out.println("Erreur lors de la connexion d'un client : " + e);	
		}
	}
	
	
	public synchronized void traiteMessage(Object O) {
		
		try	{
	
			// Si l'objet peut-�tre assign� � une figure.
						
			if (Class.forName("Figure").isAssignableFrom(O.getClass())) {
				
				lFigure.addLast(O);
				
				for (int i = 0; i < lCli.size(); i ++) {
				
					((Connexion)lCli.get(i)).Envoie(O);	
				}
			
			} else {
				
				// Si l'objet peut-�tre assign� � un message.

				if (Class.forName("Message").isAssignableFrom(O.getClass())) {
					
					lMessages.addLast(O);
					
					for (int i = 0; i < lCli.size(); i ++) {
					
						((Connexion)lCli.get(i)).Envoie(O);	
					}
				
				} else {
				
					// Sinon, c'est un String, nous enlevons alors le client de
					// La liste.
					
					int rang = Integer.parseInt((String) O);
					
					lCli.remove(rang);
					
					System.out.println("Le client " + rang + " s'est deconnecte ...");
					
					// Nous r�-indexons ensuite la liste des client en leur
					// Envoyant un nouvel identifiant.
					
					for (int i = 0; i < lCli.size(); i ++) {
					
						((Connexion)lCli.get(i)).Envoie((Object) (new Integer (i).toString()));	
					}
					
					System.out.println("Re-indexation des client ...");					 			
				}
			}
						
		} catch (Exception e) {
		
			System.out.println("Objet recu non identifi� !");	
		}
	}
	
	
/************************************************ D�claration des variables : */

	private int port;
	private int nbCli;
	private ServerSocket serv;
	private Connexion cnx;
	private LinkedList lCli;
	private LinkedList lFigure;
	private LinkedList lMessages;
}