package serveur;
import java.io.Serializable; 
import java.awt.Graphics;
import java.awt.FontMetrics;

public class Message implements Serializable
{

/* Constructeur ***************************************************************/
	
	public Message(String chateur, String mes) {
	
		this.chateur = chateur;
		this.mes = mes;	
	}
	

/* Méthodes *******************************************************************/
	
	public int ecrisMoi(Graphics g, int absc, int ordo) {
		
		g.drawString((chateur + " : " + mes), absc, ordo);
		
		
		FontMetrics fm = g.getFontMetrics();
		return fm.stringWidth ((chateur + " : " + mes));
	}	
	
	
/* Variables ******************************************************************/

	private String chateur;
	private String mes;		
}