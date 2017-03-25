package jeu;

import java.awt.image.BufferedImage;
import java.util.TimerTask;

public class Personnage implements InterfaceGestionSprite {

	
	public boolean bUP = false, bDOWN = false, bRIGHT = false, bLEFT = false;
	public Direction dirCourante;
	public Direction dirDemandee;
	
	private String autorisedEmplacements = "";
	public void setAutorisedEmplacements(String str){
		this.autorisedEmplacements = str;
	}
	public String getAutorisedEmplacements(){
		return this.autorisedEmplacements;
	}
	
	public boolean isAutorisedEmplacement(CharSequence c){
		return this.autorisedEmplacements.contains(c);
	}
	
	String imgPath;//lien de l'img pour le perso, faire un tab pour les animations
	String name;
	boolean alive;//mangé ou pas
	double delayToRevive;//temps restant pour retourner sur le plateau après avoir été mangé (secondes)
	
	//créer la classe position
	protected Position pos;//à envoyer à la classe map, c'est elle qui va gérer le déplacement
	protected Position posOnMap;
	
	float speed;//vitesse du perso
	int value;//si on mange le perso cette valeur est ajoutée au score
	int score;//score du perso (à voir si on le met pas uniquement pour PACMAN)
	
	int indexSprites;
	int offsetSprites;
	
	public void setOffsetSprites(int i){
		this.offsetSprites = i;
	}
	
	public int getOffsetSprites(){
		return offsetSprites;
	}
	
	public Personnage()
	{
		dirCourante=Direction.NONE;
		dirDemandee=Direction.NONE;
		imgPath="";
		name="";
		alive=false;
		delayToRevive = 0.0d;
		pos = new Position(0,0);
		posOnMap = new Position(0,0);
		speed=0.0f;
		value=0;
		score=0;
		indexSprites = 0;
		offsetSprites = 0;
	}
	
	public Position getPosition()
	{
		return pos;
	}
	
	public void setIndexSprite(int i)
	{
		this.indexSprites = i;
	}
	
	public int getIndexSprite()
	{
		return this.indexSprites;
	}
	
	public Direction getDirCourante()
	{
		return dirCourante;
	}
	public Direction getDirDemandee()
	{
		return dirDemandee;
	}
	
	public void setDirCourante(Direction dir)
	{
		dirCourante = dir;
	}
	public void setDirDemandee(Direction dir)
	{
		dirDemandee = dir;
	}
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}*/
	
	
	public /*boolean*/ void isDirDemandeeAvailable()
	{
		
		if(this.dirDemandee == Direction.DOWN)
		{
			//prochaine position autorisée ?
			//oui 	-> this.dirCourante.set(x, y);
			//	 	-> return true;
			//non 	-> return false;
		}
		else if(this.dirDemandee == Direction.UP)
		{
		}
		else if(this.dirDemandee == Direction.RIGHT)
		{
		}
		else if(this.dirDemandee == Direction.LEFT)
		{
		}
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BufferedImage getImgToPrint() {
		// TODO Auto-generated method stub
		return null;
	}
}
