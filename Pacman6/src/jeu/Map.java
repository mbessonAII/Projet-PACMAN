package jeu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Map extends JPanel{
	//0 : noir
	//M : mur
	//. : Bille
	//S : superbille
	//a : accessible par tous mais vide
	//f accessible qu'aux fantômes
	//p : porte boite fantômes
	//1,2,3,4 : emplacements de départ des fantômes
	//P : emplacement pacman initialisation
	
	//http://images.google.fr/imgres?imgurl=http%3A%2F%2Fmcpeuniverse.s3.amazonaws.com%2Fwp-content%2Fuploads%2F2015%2F01%2FPac-Man.png&imgrefurl=http%3A%2F%2Fmcpeuniverse.com%2Fmaps%2Fpac-man-mini-game%2F&h=441&w=441&tbnid=EQmm3O12MyJogM%3A&vet=1&docid=w-WoGS965AihjM&ei=112LWK3BF4aja4fGtfAK&tbm=isch&iact=rc&uact=3&dur=191&page=0&start=0&ndsp=41&ved=0ahUKEwjtiJ-FyeLRAhWG0RoKHQdjDa4QMwgeKAIwAg&bih=974&biw=1920
	
	public boolean startScareTimer = false;
	
	String map= ".....M.......M....."
			+ 	"SMMM.M.MMMMM.M.MMMS"
			+	".MMM.M.MMMMM.M.MMM."
			+	"..................."
			+	"MM.M.MMM.M.MMM.M.MM"//5
			+	"0M.M.M0M.M.M0M.M.M0"
			+	"MM.M.MMM.M.MMM.M.MM"
			+	"TT.M.....M.....M.TT"
			+	"MM.MMMaMMMMMaMMM.MM"
			+	"0M.MMMaMMMMMaMMM.M0"//10
			+	"0M.aaaaaaaaaaaaa.M0"
			+	"0M.MMMaMMpMMaMMM.M0"
			+	"0M.MaaaM1f2MaaaM.M0"
			+	"0M.MaMaM3f4MaMaM.M0"
			+	"MM.MaMaMMMMMaMaM.MM"//15
			+	"TT.aaMaaaaaaaMaa.TT"
			+	"MM.MMMMMaMaMMMMM.MM"
			+	"0M.....aaMaa.....M0"
			+	"0M.MMM.MMMMM.MMM.M0"
			+	"MM.MMM.MMMMM.MMM.MM"//20
			+	".........P........."
			+	".MMM.MMM.M.MMM.MMM."
			+	".M0M.M...M...M.M0M."
			+	"SM0M.M.MMMMM.M.M0MS"
			+	".MMM.M.MMMMM.M.MMM."//25
			+	"...................";
	
	private int largeurMap = 168;
    private int hauteurMap = 220;
    private int marge = 2;
    
    private int xImg1 = marge*2;
    private int yImg1 = marge*2;
    
    private int xImg2 = 176;
    private int yImg2 = marge * 2;
	
	public BufferedImage img;//image entière
	public BufferedImage[] sprites = new BufferedImage[6];
	
	//vars billes
	// - 4 au début de ligne pour positionner un personnage (le symétrique est à +5 vers le bas ou la droite)
	// - 5 au deb de ligne pour avoir le contour (vers le haut ou vers la gauche / +6 vers le bas ou la droite)
	int largeurLigne = 8;
	int hauteurLigne = 8;
	int nbLignes = 26, nbColonnes = 19;
	int debPremiereLigneX = 11;
	int debPremiereLigneY = 9;

	public Ball[][] balls = new Ball[nbLignes][nbColonnes];
	Position[][] allCoordinates = new Position[nbLignes][nbColonnes];
	public void initCoordinatesTab(){
		for(int i = 0; i<nbLignes; i++){
			for(int j = 0; j<nbColonnes; j++)
			{
				allCoordinates[i][j] = new Position(debPremiereLigneX + j*largeurLigne, debPremiereLigneY + i*hauteurLigne);
			}
		}
	}
	
	
	
	public void initTabBilles(){
		for(int i = 0; i<nbLignes; i++){
			for(int j = 0; j<nbColonnes; j++)
			{
				////set positions
				balls[i][j] = new Ball(debPremiereLigneX + j*largeurLigne, debPremiereLigneY + i*hauteurLigne);
				balls[i][j].posOnMap.set(i, j);
				
				char cara = map.charAt(associatedCharIndex(i,j));
				//indique si la balle est effectivement présente
				if(cara == '.' || cara == 'S')
				{	
					balls[i][j].eaten = false;
				}
				else
				{
					balls[i][j].eaten = true;
				}
			}
		}
	}
	
	
	
	//retourne l'équivalent des index de balles[] pour la chaine map[]
	public int associatedCharIndex(int i, int j)
	{
		int res = 0;
		res = i * (nbColonnes ) + j;
		return res;
	}
	
	
	//retourne l'équivalent de l'index de map[] pour le tableau balles[]
	public Position associatedTabIndex(int index)
	{
		Position p = new Position(0,0);
		int i = index/nbColonnes;
		int j = index - i*nbColonnes;
		p.set(i, j);
		return p;
	}
	
	public Pacman pacman = new Pacman();
	
	
	public void loadImg(String path)
	{
		try {
			FileInputStream input = new FileInputStream(path);
			img = ImageIO.read(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadImg(InputStream is)
	{
		try {
			img = ImageIO.read(is);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	//ne se fait qu'une fois pour charger les images
	public void loadMapSprites()
	{
		sprites[0] = img.getSubimage(xImg1, yImg1, largeurMap, hauteurMap);
		//sprites[1] = img.getSubimage(xImg2, yImg2, largeurMap, hauteurMap);
		//imgFinale = img.getSubimage(xImg2, yImg2, largeurMap, hauteurMap);
		//sprites[2] = img.getSubimage(xImg3, yImg3, largeurMap, hauteurMap);
		//sprites[3] = img.getSubimage(xImg2, yImg2, largeurMap, hauteurMap);
		//sprites[4] = img.getSubimage(xImg2, yImg2, largeurMap, hauteurMap);
		//sprites[5] = img.getSubimage(xImg2, yImg2, largeurMap, hauteurMap);
	}
	
	public void loadPacmanSprites()
	{
		/*int i=0;
		for (Rectangle rect : pacman.rectSprites) {
			pacman.sprites[i] = img.getSubimage(pacman.rectSprites[i].x, pacman.rectSprites[i].y, pacman.rectSprites[i].width, pacman.rectSprites[i].height);
			i++;
		}*/
		InterfaceGestionSprite.loadSprites(img, Pacman.rectSprites, pacman.sprites);
	}
	
	public Fantome inky = new Fantome();//bleu
	public Fantome blinky = new Fantome();//rouge
	public Fantome pinky = new Fantome();//rose
	public Fantome clyde = new Fantome();//orange
	
	public void initFantomesPostions()//ok
	{
		int i = map.indexOf('1')/nbColonnes;
		int j = map.indexOf('1') - i * nbColonnes;
		inky.posOnMap.set(i, j);
		inky.pos.set((allCoordinates[i][j].getX() - 4 )*zoom, (allCoordinates[i][j].getY() - 3)*zoom);
		
		
		i = map.indexOf('2')/nbColonnes;
		j = map.indexOf('2') - i * nbColonnes;
		blinky.posOnMap.set(i, j);
		blinky.pos.set((allCoordinates[i][j].getX() - 4 )*zoom, (allCoordinates[i][j].getY() - 3)*zoom);
		
		i = map.indexOf('3')/nbColonnes;
		j = map.indexOf('3') - i * nbColonnes;
		pinky.posOnMap.set(i, j);
		pinky.pos.set((allCoordinates[i][j].getX() - 4 )*zoom, (allCoordinates[i][j].getY() - 3)*zoom);
		
		i = map.indexOf('4')/nbColonnes;
		j = map.indexOf('4') - i * nbColonnes;
		clyde.posOnMap.set(i, j);
		clyde.pos.set((allCoordinates[i][j].getX() - 4 )*zoom, (allCoordinates[i][j].getY() - 3)*zoom);
	}
	
	public void initFantomesPostions(Fantome f)//ok
	{
		int i = 0;
		int j = 0;
		
		if(f.equals(inky))
		{
			i = map.indexOf('1')/nbColonnes;
			j = map.indexOf('1') - i * nbColonnes;
			inky.posOnMap.set(i, j);
			inky.pos.set((allCoordinates[i][j].getX() - 4 )*zoom, (allCoordinates[i][j].getY() - 3)*zoom);
			
			inky.setDirCourante(Direction.NONE);
		}
		else if(f.equals(blinky))
		{
			i = map.indexOf('2')/nbColonnes;
			j = map.indexOf('2') - i * nbColonnes;
			blinky.posOnMap.set(i, j);
			blinky.pos.set((allCoordinates[i][j].getX() - 4 )*zoom, (allCoordinates[i][j].getY() - 3)*zoom);
			blinky.setDirCourante(Direction.NONE);
		}
		else if(f.equals(pinky))
		{
			i = map.indexOf('3')/nbColonnes;
			j = map.indexOf('3') - i * nbColonnes;
			pinky.posOnMap.set(i, j);
			pinky.pos.set((allCoordinates[i][j].getX() - 4 )*zoom, (allCoordinates[i][j].getY() - 3)*zoom);
			pinky.setDirCourante(Direction.NONE);
		}
		else if(f.equals(clyde))
		{
			i = map.indexOf('4')/nbColonnes;
			j = map.indexOf('4') - i * nbColonnes;
			clyde.posOnMap.set(i, j);
			clyde.pos.set((allCoordinates[i][j].getX() - 4 )*zoom, (allCoordinates[i][j].getY() - 3)*zoom);
			clyde.setDirCourante(Direction.NONE);
		}
	}
	
	public void initPacmanPostion()//ok
	{
		int i = map.indexOf('P')/nbColonnes;
		int j = map.indexOf('P') - i * nbColonnes;
		pacman.posOnMap.set(i, j);
		pacman.pos.set((allCoordinates[i][j].getX() - 4 )*zoom, (allCoordinates[i][j].getY() - 3)*zoom);
		//pacman.setDirCourante(Direction.NONE);
	}
	
	public void loadFantomesSprites()
	{
		InterfaceGestionSprite.loadSprites(img, Fantome.rectSpritesBleu, inky.sprites);
		InterfaceGestionSprite.loadSprites(img, Fantome.rectSpritesRose, pinky.sprites);
		InterfaceGestionSprite.loadSprites(img, Fantome.rectSpritesRouge, blinky.sprites);
		InterfaceGestionSprite.loadSprites(img, Fantome.rectSpritesOrange, clyde.sprites);
		InterfaceGestionSprite.loadSprites(img, Fantome.rectSpritesScared, Fantome.spritesScared);
	}
	
	public boolean isDirectionAvailable(Personnage perso, Direction dir)
	{
		char cara=' ';//initialisé à espace
		boolean toReturn = false;
		switch(dir)
		{
			case LEFT:
				if(perso.posOnMap.getY() - 1 < 0)
				{
					cara = map.charAt(associatedCharIndex(perso.posOnMap.getX(), nbColonnes - 1));
					if(cara == 'T')
					{
						//System.out.println("tunnel");
					}
					else
						return false;
				}
				else
					cara = map.charAt(associatedCharIndex(perso.posOnMap.getX(), perso.posOnMap.getY() - 1));
				
				break;
			case RIGHT:
				if(perso.posOnMap.getY() + 1 >= nbColonnes)
				{
					cara = map.charAt(associatedCharIndex(perso.posOnMap.getX(), 0));
					if(cara == 'T')
					{
						System.out.println("tunnel");
					}
					else
						return false;
				}
				cara = map.charAt(associatedCharIndex(perso.posOnMap.getX(), (perso.posOnMap.getY()+1)%nbColonnes));
				break;
			case DOWN:
				if(perso.posOnMap.getX() + 1 >= nbLignes)
				{
					
					return false;
				}
					
				cara = map.charAt(associatedCharIndex(perso.posOnMap.getX() + 1, perso.posOnMap.getY()));
				break;
			case UP:
				if(perso.posOnMap.getX() - 1 < 0)
					return false;
				cara = map.charAt(associatedCharIndex(perso.posOnMap.getX() - 1, perso.posOnMap.getY()));
				break;
			case NONE:
				break;
			default:
				break;
		}
		
		if(perso.isAutorisedEmplacement(String.valueOf(cara)))
		{
			toReturn = true;
		}
		return toReturn;
	}
	
	public boolean fantomeAI(Personnage persoToMove, Personnage persoToFollow){
		
		Direction[] prefs = new Direction[4];// = Direction.NONE, pref3 = Direction.NONE, pref4 = Direction.NONE;
		
		prefs[0] = Direction.NONE;
		prefs[1] = Direction.NONE;
		prefs[2] = Direction.NONE;
		
		//on détermine la direction à prendre
		int deltaX = persoToFollow.posOnMap.getX() - persoToMove.posOnMap.getX();
		int deltaY = persoToFollow.posOnMap.getY() - persoToMove.posOnMap.getY();
		
		if(Math.abs(deltaX) > Math.abs(deltaY))
		{
			if(deltaX < 0)
			{
				prefs[0] = Direction.UP;
				prefs[3] = Direction.DOWN;
			}
			else 
			{
				prefs[0] = Direction.DOWN;
				prefs[3] = Direction.UP;
			}
			
			if(deltaY < 0){
				prefs[1] = Direction.LEFT;
				prefs[2] = Direction.RIGHT;
			}
			else{
				prefs[1] = Direction.RIGHT;
				prefs[2] = Direction.LEFT;
			}
		}
		else{
			if(deltaX < 0)
			{
				prefs[1] = Direction.UP;
				prefs[2] = Direction.DOWN;
			}
			else 
			{
				prefs[1] = Direction.DOWN;
				prefs[2] = Direction.UP;
			}
			
			if(deltaY < 0){
				prefs[0] = Direction.LEFT;
				prefs[3] = Direction.RIGHT;
			}
			else{
				prefs[0] = Direction.RIGHT;
				prefs[3] = Direction.LEFT;
			}
		}
		
		for(Direction d : prefs){
			boolean bOk = true;
			if(persoToMove.getDirCourante() == Direction.UP && d == Direction.DOWN)
			{
				bOk = false;
			}
			else if(persoToMove.getDirCourante() == Direction.DOWN && d == Direction.UP)
			{
				bOk = false;
			}
			else if(persoToMove.getDirCourante() == Direction.RIGHT && d == Direction.LEFT)
			{
				bOk = false;
			}
			else if(persoToMove.getDirCourante() == Direction.LEFT && d == Direction.RIGHT)
			{
				bOk = false;
			}
						
			if(bOk)
			{
				persoToMove.setDirDemandee(d);
				if(updateDir(persoToMove)){
					return true;
				}
			}
			
			
		}
		System.out.println(persoToMove.getDirCourante());
		
		return false;
	}
	
	public boolean fantomeAI(Personnage persoToMove, Position posToFollow){
		
		Direction[] prefs = new Direction[4];// = Direction.NONE, pref3 = Direction.NONE, pref4 = Direction.NONE;
		
		prefs[0] = Direction.NONE;
		prefs[1] = Direction.NONE;
		prefs[2] = Direction.NONE;
		
		int deltaX = posToFollow.getX() - persoToMove.posOnMap.getX();
		int deltaY = posToFollow.getY() - persoToMove.posOnMap.getY();
		
		if(Math.abs(deltaX) > Math.abs(deltaY))
		{
			if(deltaX < 0)
			{
				prefs[0] = Direction.UP;
				prefs[3] = Direction.DOWN;
			}
			else 
			{
				prefs[0] = Direction.DOWN;
				prefs[3] = Direction.UP;
			}
			
			if(deltaY < 0){
				prefs[1] = Direction.LEFT;
				prefs[2] = Direction.RIGHT;
			}
			else{
				prefs[1] = Direction.RIGHT;
				prefs[2] = Direction.LEFT;
			}
		}
		else{
			if(deltaX < 0)
			{
				prefs[1] = Direction.UP;
				prefs[2] = Direction.DOWN;
			}
			else 
			{
				prefs[1] = Direction.DOWN;
				prefs[2] = Direction.UP;
			}
			
			if(deltaY < 0){
				prefs[0] = Direction.LEFT;
				prefs[3] = Direction.RIGHT;
			}
			else{
				prefs[0] = Direction.RIGHT;
				prefs[3] = Direction.LEFT;
			}
		}
		
		for(Direction d : prefs){
			boolean bOk = true;
			if(persoToMove.getDirCourante() == Direction.UP && d == Direction.DOWN)
			{
				bOk = false;
			}
			else if(persoToMove.getDirCourante() == Direction.DOWN && d == Direction.UP)
			{
				bOk = false;
			}
			else if(persoToMove.getDirCourante() == Direction.RIGHT && d == Direction.LEFT)
			{
				bOk = false;
			}
			else if(persoToMove.getDirCourante() == Direction.LEFT && d == Direction.RIGHT)
			{
				bOk = false;
			}
						
			if(bOk)
			{
				persoToMove.setDirDemandee(d);
				if(updateDir(persoToMove)){
					return true;
				}
			}
			
		}
		//System.out.println(persoToMove.getDirCourante());
		
		return false;
	}
	
	public boolean updateDir(Personnage perso)
	{	

		//si la direction demandée est possible alors on l'affecte à la direction courante
		if(isDirectionAvailable(perso, perso.getDirDemandee()))
		{
			perso.setDirCourante(perso.getDirDemandee());
			return true;
		}
		
		//si la direction courante est impossible
		if(!isDirectionAvailable(perso, perso.getDirCourante()))
		{
			perso.setDirCourante(Direction.NONE);
			
		}
		
		return false;
		
	}
	
	
	public boolean isTherePersoCollision(Personnage perso1, Personnage perso2){
		return (perso1.posOnMap.getX() == perso2.posOnMap.getX() && perso1.posOnMap.getY() == perso2.posOnMap.getY());
	}
	
	public void bougerSelonDirection(Personnage perso)
	{
		//-4 ok pour x par rapport à la balle
		//- 3 ok en y par rapport à la balle
		
		int i = perso.posOnMap.getX();
		int j = perso.posOnMap.getY();
		switch(perso.getDirCourante())
		{
			case UP:
				i--;
				break;
			case DOWN:
				i++;
				break;
			case RIGHT:
				j++;
				break;
			case LEFT:
				j--;
				break;
			case NONE:
				break;
			default:
				break;
		}
		
		i%=nbLignes;
		j%=nbColonnes;
		
		if(j<0)
			j = nbColonnes - 1;
		
		perso.posOnMap.set(i, j);
		if((perso instanceof Pacman) && map.charAt(associatedCharIndex(i, j)) == 'S' && balls[i][j].eaten == false)
		{
			inky.scared = true;
			blinky.scared = true;
			clyde.scared = true;
			pinky.scared = true;
			startScareTimer = true;
		}
			
		perso.pos.set((allCoordinates[perso.posOnMap.getX()%nbLignes][perso.posOnMap.getY()%nbColonnes].getX() - 4 )*zoom, (allCoordinates[perso.posOnMap.getX()%nbLignes][perso.posOnMap.getY()%nbColonnes].getY() - 3)*zoom);
		
		if(perso instanceof Pacman)
		{
			balls[i][j].eaten = true;
			animate();
		}
	}
	

	
	private int indexSprites = 0;
	private int xPaintCoordinate = 0;
	private int yPaintCoordinate = 0;
	private int zoom = 3;
	//pour changer de sprite
	public void setIndexSprites(int i){
		this.indexSprites = i;
	}
	
	public void setZoom(int coef){
		this.zoom = coef;
	}
	public int getZoom()
	{
		return this.zoom;
	}
	
	int tempi = 0,tempy = 0;
	
	
	public void animate()
	{
		//pac
		pacman.animate();
		//fantômes
		clyde.animate();
		blinky.animate();
		inky.animate();
		pinky.animate();
	}
	
	
	public void persoCollisionHandler(){
		
		if(isTherePersoCollision(pacman, inky)){
			if(!inky.scared)
			{
				initPacmanPostion();
			}
			initFantomesPostions(inky);
		}
		if(isTherePersoCollision(pacman, blinky)){
			if(!blinky.scared)
			{
				initPacmanPostion();
			}
			initFantomesPostions(blinky);
		}
		if(isTherePersoCollision(pacman, pinky)){
			if(!pinky.scared)
			{
				initPacmanPostion();	
			}
			initFantomesPostions(pinky);
		}
		if(isTherePersoCollision(pacman, clyde)){
			if(!clyde.scared)
			{
				initPacmanPostion();	
			}
			initFantomesPostions(clyde);
		}
			
	}
	
	
	@Override
    public void paintComponent(Graphics g){
        
        //carte
        g.drawImage(sprites[indexSprites], xPaintCoordinate, yPaintCoordinate, sprites[indexSprites].getWidth()*zoom, sprites[indexSprites].getHeight()*zoom, this);
        
        gestionEntreeDirection();
              
        //effacer les billes mangées
        g.setColor(Color.BLACK);//ok
        for(int i = 0; i<nbLignes; i++)
		{
			for(int j = 0; j<nbColonnes; j++)
			{
				char c = map.charAt(associatedCharIndex(i, j));
				if(balls[i][j].eaten)
				{
					if(c == '.')
					{
				        g.fillRect((allCoordinates[i%nbLignes][j%nbColonnes].getX())*zoom, (allCoordinates[i%nbLignes][j%nbColonnes].getY())*zoom, 2*zoom, 2*zoom);//ok
					}
					else if(c=='S')
					{
				        g.fillRect((allCoordinates[i%nbLignes][j%nbColonnes].getX() - 2)*zoom, (allCoordinates[i%nbLignes][j%nbColonnes].getY() - 2)*zoom, 6*zoom, 6*zoom);//ok
					}
				}
			}
		}
        
        g.drawImage(pacman.getImgToPrint(), pacman.getPosition().getX(), pacman.getPosition().getY(), Pacman.tailleimg*zoom, Pacman.tailleimg*zoom, this);
        
        drawFantomes(g);
        
    }
	
	public void gestionEntreeDirection(){
		switch(pacman.getDirCourante())
		{
			case UP:
				pacman.setRotation(Math.toRadians(-90.0));//-90.0
				pacman.setDirDemandee(Direction.UP);
				break;
			case DOWN:
				pacman.setRotation(Math.toRadians(90.0));//90.0
				pacman.setDirDemandee(Direction.DOWN);
				break;
			case LEFT:
				pacman.setRotation(Math.toRadians(180.0));//180.0
				pacman.setDirDemandee(Direction.LEFT);
				break;
			case RIGHT:
				pacman.setRotation(Math.toRadians(0.0));//0.0
				pacman.setDirDemandee(Direction.RIGHT);
				break;
			case NONE:
				pacman.setDirDemandee(Direction.NONE);
				break;
			default:
				break;
		}
	}
	
	
	public void drawFantomes(Graphics g)
	{
		g.drawImage(inky.getImgToPrint(), inky.getPosition().getX(), inky.getPosition().getY(), Fantome.tailleimg*zoom, Fantome.tailleimg*zoom, this);
		g.drawImage(pinky.getImgToPrint(), pinky.getPosition().getX(), pinky.getPosition().getY(), Fantome.tailleimg*zoom, Fantome.tailleimg*zoom, this);
		g.drawImage(blinky.getImgToPrint(), blinky.getPosition().getX(), blinky.getPosition().getY(), Fantome.tailleimg*zoom, Fantome.tailleimg*zoom, this);
		g.drawImage(clyde.getImgToPrint(), clyde.getPosition().getX(), clyde.getPosition().getY(), Fantome.tailleimg*zoom, Fantome.tailleimg*zoom, this);
	}
	
	public void setPaintCoordinates(int posX, int posY){
        xPaintCoordinate = posX;
        yPaintCoordinate = posY;
    }

}
