package jeu;

import java.util.Random;

public class Position {
	private int x = 0;
	private int y = 0;
	
	//vérifier la syntaxe constructeur
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	
	public void set(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	//utiliser posOnMap arg0 : coin supérieur gauche arg1: coin inférieur droit
	public static Position getRandom(Position min, Position max){
		Position posTemp = new Position(0,0);
		Random randomGenerator = new Random();
	    
		int x = 0, y = 0;
		
		x = randomGenerator.nextInt(max.getX() - min.getX()) + min.getX();
		y = randomGenerator.nextInt(max.getY() - min.getY()) + min.getY();
		
		posTemp.set(x, y);
		
		return posTemp;
	}
	
	public static Position getOnceRandomButNotIfPrev(Personnage perso, Position prev){
		Position posTemp = Position.getRandom(new Position(0,0), new Position(25,18));
		if(perso.posOnMap.getX() == prev.getX() && perso.posOnMap.getY() == prev.getY())
			return prev;
		
		return posTemp;
	}
	
}
