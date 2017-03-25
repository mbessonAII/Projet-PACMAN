package jeu;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Fantome extends Personnage{
	
	public static char idEmplacementsAutorises[] =  {'a', '.', 'S', 'T', 'f', '1', '2', '3', '4', 'p', 'P'};//autorisé vide, bille, superbille, tunnel
	
	boolean scared;//si le fantome est apeuré ou non
	
	double scaredRemainingTime;
	static int largeurSprite = 537 - 524 + 1;
	static int hauteurSprite = 25 - 13 + 1;
	static int longueurEntre2Sprites = 540 - 524;//a partir du début du sprite
	static int hauteurEntre2Sprites = 29 - 13;//a partir du haut du sprite
	public static Rectangle[] rectSpritesRouge = new Rectangle[]{
			new Rectangle(524, 13, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites, 13, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 2, 13, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 3, 13, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 4, 13, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 5, 13, largeurSprite, hauteurSprite)
	};
	public static Rectangle[] rectSpritesRose = new Rectangle[]{
			new Rectangle(524, 13 + hauteurEntre2Sprites, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites, 13 + hauteurEntre2Sprites, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 2, 13 + hauteurEntre2Sprites, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 3, 13 + hauteurEntre2Sprites, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 4, 13 + hauteurEntre2Sprites, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 5, 13 + hauteurEntre2Sprites, largeurSprite, hauteurSprite)
	};
	public static Rectangle[] rectSpritesBleu = new Rectangle[]{
			new Rectangle(524, 13 + hauteurEntre2Sprites * 2, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites, 13 + hauteurEntre2Sprites * 2, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 2, 13 + hauteurEntre2Sprites * 2, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 3, 13 + hauteurEntre2Sprites * 2, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 4, 13 + hauteurEntre2Sprites * 2, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 5, 13 + hauteurEntre2Sprites * 2, largeurSprite, hauteurSprite)
	};
	public static Rectangle[] rectSpritesOrange = new Rectangle[]{
			new Rectangle(524, 13 + hauteurEntre2Sprites * 3, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites, 13 + hauteurEntre2Sprites * 3, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 2, 13 + hauteurEntre2Sprites * 3, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 3, 13 + hauteurEntre2Sprites * 3, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 4, 13 + hauteurEntre2Sprites * 3, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 5, 13 + hauteurEntre2Sprites * 3, largeurSprite, hauteurSprite)
	};
	public static Rectangle[] rectSpritesScared = new Rectangle[]{
			new Rectangle(524+ longueurEntre2Sprites * 6, 13, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 7, 13, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 8, 13, largeurSprite, hauteurSprite),
			new Rectangle(524 + longueurEntre2Sprites * 9, 13, largeurSprite, hauteurSprite)
	};

	public BufferedImage[] sprites = new BufferedImage[6];
	public static BufferedImage[] spritesScared = new BufferedImage[4];
	
	static int tailleimg = 9;//ok 9
	
	public Fantome()
	{
		super();
		super.dirCourante=Direction.NONE;
		super.dirDemandee=Direction.NONE;
		super.imgPath="";//définir l'image propre au fantôme
		super.name="";
		super.alive=false;
		super.delayToRevive = 0.0d;
		super.pos.set(0,0);
		super.speed=0.0f;
		super.value=0;//fixer la valeur
		super.score=0;
		super.setAutorisedEmplacements("a" + "." + "S" + "T" + "f" +/* "1" + "2" + "3" + "4" +*/ "p" + "P");
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		
		//Select the right sprite for current direction
		if(!this.scared)
		{
			switch(this.getDirCourante()){
			case UP:
				this.setOffsetSprites(2);
				break;
			case DOWN:
				this.setOffsetSprites(4);
				break;
			case LEFT:
				this.setOffsetSprites(0);
				break;
			case RIGHT:
				this.setOffsetSprites(0);
				break;
			case NONE:
				break;
			default:
				break;
				
			}
		}
		else{
			this.setOffsetSprites(0);
		}
		
		
		//Update the index of the sprite to print
		this.setIndexSprite((this.getIndexSprite()+1)%2 + this.getOffsetSprites());
	}
	@Override
	public BufferedImage getImgToPrint() {
		// TODO Auto-generated method stub
		if(!scared)
		{
			//flip horizontal du sprite pour avoir "gauche" (il n'y a que le sprite "droite" à l'origine)
			if(this.getDirCourante()== Direction.LEFT)
			{
				return InterfaceGestionSprite.flip(this.sprites[this.getIndexSprite()]);
			}
			return this.sprites[this.getIndexSprite()];
		}
		
		return Fantome.spritesScared[this.getIndexSprite()];
	}
}
