package jeu;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Pacman extends Personnage{
	boolean superPowerActivated;// = false
	double superPowerRemainingTime;
	double rotate = 0;
	static int tailleimg = 9;//ok 9
	
	
	public void move()
	{
		if(this.dirCourante == Direction.DOWN)
		{
			//prochaine position autorisées ?
			//this.pos.set(x, y);
		}
		else if(this.dirCourante == Direction.UP)
		{
			//prochaine position autorisées ?
			//this.pos.set(x, y);
		}
		else if(this.dirCourante == Direction.RIGHT)
		{
			//prochaine position autorisées ?
			//this.pos.set(x, y);
		}
		else if(this.dirCourante == Direction.LEFT)
		{
			//prochaine position autorisées ?
			//this.pos.set(x, y);
		}
	}
	
	public void setRotation(double angle)
	{
		rotate = angle;
	}
	
	public double getRotation()
	{
		return rotate;
	}
	
	static Rectangle[] rectSprites = new Rectangle[]{
			new Rectangle(630, 52, 642-629, 64-51),
			new Rectangle(647, 52, 642-629, 64-51),
			new Rectangle(663, 52, 642-629, 64-51)//si l'image est décalée, rajouter 1 au 1er param ça devrait corriger
	};

	public static BufferedImage[] sprites = new BufferedImage[3];
	//int score; //dans la classe personnage
	
	public Pacman()
	{
		super();
		super.dirCourante=Direction.NONE;
		super.dirDemandee=Direction.NONE;
		super.imgPath="";//définir l'image propre au pacman
		super.name="Pacman";//défini direct ici car i lpeut pas changer de nom
		super.alive=true;//réservé au moteur de jeu
		super.delayToRevive = 0.0d;
		super.pos = new Position(0,0);
		super.speed=0.0f;
		super.value=0;//fixer la valeur
		super.score=0;
		super.setAutorisedEmplacements("a" + "." + "S" + "T" + "P");
	}
	@Override
	public void animate() {
		this.setIndexSprite((this.getIndexSprite()+1)%2);
	}
	@Override
	public BufferedImage getImgToPrint() {
		// TODO Auto-generated method stub
		return InterfaceGestionSprite.rotate(Pacman.sprites[this.getIndexSprite()], this.getRotation());
	}
}
