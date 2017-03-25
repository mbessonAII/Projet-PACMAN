package jeu;

import java.util.Random;

public class Ball {
	Position pos;
	boolean eaten;
	static int value = 10;//de combien on va incrémenter le score
	static String imgPath;
	public Position posOnMap;
	
	
	public Ball()
	{
		pos = new Position(0, 0);
		posOnMap = new Position(0, 0);
		eaten = false;
	}
	
	public Ball(int x, int y)
	{
		pos = new Position(x, y);
		posOnMap = new Position(0, 0);
		eaten = false;
	}
	
	public static Position getABall(Ball[][] balls){
		for (Ball[] b1 : balls) {
            // Iterating over one dimension
            for (Ball b : b1) {
            	if(!b.eaten){
            		//System.out.println("x"+b.posOnMap.getX() + "y"+b.posOnMap.getY());
            		return b.posOnMap;
            	}
            }
        }
		return null;
	}
	public static Position getARandomBall(Ball[][] balls){
		Position posTemp = Position.getRandom(new Position(0,0), new Position(25,18));
		while(balls[posTemp.getX()][posTemp.getY()].eaten)
		{
			posTemp = Position.getRandom(new Position(0,0), new Position(25,18));
		}
		return posTemp;
	}
	public static Position getARandomBallButNotIfPrev(Ball[][] balls, Ball prev){
		Position posTemp = Position.getRandom(new Position(0,0), new Position(25,18));
		if(!prev.eaten)
			return prev.posOnMap;
		while(balls[posTemp.getX()][posTemp.getY()].eaten)
		{
			posTemp = Position.getRandom(new Position(0,0), new Position(25,18));
		}
		
		return posTemp;
	}
	
	public static boolean areAllEaten(Ball[][] balls){
		//int i = 0;
		for (Ball[] b1 : balls) {
            // Iterating over one dimension
			//i++;
			//System.out.println("i: "+i);
            for (Ball b : b1) {
            	if(!b.eaten){
            		//System.out.println("x"+b.posOnMap.getX() + "y"+b.posOnMap.getY());
            		return false;
            	}
            }
            
        }
		return true;
	}
}
