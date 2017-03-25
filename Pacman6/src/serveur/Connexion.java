package serveur;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.lang.Runnable;

public class Connexion implements Runnable {

/********************************************** D�claration du constructeur : */


	public Connexion(Socket lui, Communicateur moi) {
      
		try {
			
			this.moi = moi;   
			output = new ObjectOutputStream(lui.getOutputStream());
			input = new ObjectInputStream(lui.getInputStream());
		
		} catch(Exception e) {
			
			System.out.println("Erreur de connexion : " + e);	
		}
		
		Thread t = new Thread(this);
		t.start();		
	}	
	
	
/************************************************* D�claration des m�thodes : */


	public void run() {
		
		try {
		
			while(true) {
				
				Object recu = input.readObject();
				moi.traiteMessage(recu);
			}
		} catch(Exception e) {
		
			System.out.println("Erreur de lecture : " + e);	
		}
	}

	
	public void Envoie(Object O) {
		
		try {
			output.writeObject(O);
		      
		} catch(Exception e) {
		    	
			System.out.println("Erreur d'�criture : " + e);	
		}
	}
		

/************************************************ D�claration des variables : */
	
	
	private Communicateur moi;
	private ObjectOutputStream output;
	private ObjectInputStream input;
}