package jeu;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;

import jeu.ImagePane;
import serveur.Client;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
import javax.print.attribute.Size2DSyntax;

import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class Main{
	public static boolean bUp = false, bDown = false, bLeft = false, bRight = false;
	
	//String ipAddress = "127.0.0.1";
	//int port = 9632;
	
	//Client cl = new Client(ipAddress, port);
	
	public static Map map1;
	
	static Position prev = new Position(0,0);
	static Position prevClyde = new Position(0,0);
	static Position prevInky = new Position(0,0);
	static Position prevPinky = new Position(0,0);
	public static void main(String[] args) {
		JFrame fen = new JFrame ("Ordinateur Qui Parle");
		//Définit un titre pour notre fenêtre
		fen.setTitle("Ma première fenêtre Java");

		//fen.setSize(850,600);

		map1 = new Map();

		map1.loadImg(Main.class.getResourceAsStream("/sprites.png"));
		map1.loadMapSprites();
		map1.loadPacmanSprites();
		map1.loadFantomesSprites();
		map1.initTabBilles();
		map1.initCoordinatesTab();
		
		map1.setIndexSprites(0);
		map1.setPaintCoordinates(0, 0);
		map1.initFantomesPostions();
		map1.initPacmanPostion();
		map1.paintComponents(map1.getGraphics());
		fen.setContentPane(map1);
		fen.addKeyListener(windowKeyListener);
		map1.requestFocus();

		map1.setZoom(3);
		fen.setSize(map1.sprites[0].getWidth()*map1.getZoom() +16, map1.sprites[0].getHeight()*map1.getZoom() + 39);

		fen.setLocationRelativeTo(null);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setVisible(true); 
		int i = 0,j = 6*map1.getZoom();
		//tant qu'il reste des billes
		while(!Ball.areAllEaten(map1.balls))
		{
			
			
			if(true)
			{
				if(bUp)
				{
					map1.pacman.setDirDemandee(Direction.UP);
				}
				else if(bDown)
				{
					map1.pacman.setDirDemandee(Direction.DOWN);

				}
				else if(bRight)
				{
					map1.pacman.setDirDemandee(Direction.RIGHT);

				}
				else if(bLeft)
				{
					map1.pacman.setDirDemandee(Direction.LEFT);
				}
				
			
				//Vérifie que la dir demandée est possible, si oui on l'affecte à la dir courante
				map1.updateDir(map1.pacman);
				//arg0 follow arg1
				prev = Ball.getARandomBallButNotIfPrev(map1.balls, map1.balls[prev.getX()][prev.getY()]);
				
				//AI fantomes
				prevClyde = Position.getRandom(new Position(0,0), new Position(25,18));
				while(!map1.clyde.isAutorisedEmplacement(String.valueOf(map1.map.charAt(map1.associatedCharIndex(prevClyde.getX(), prevClyde.getY()))))){
					prevClyde = Position.getRandom(new Position(0,0), new Position(25,18));
				}
				prevInky = Position.getRandom(new Position(0,0), new Position(25,18));
				while(!map1.inky.isAutorisedEmplacement(String.valueOf(map1.map.charAt(map1.associatedCharIndex(prevInky.getX(), prevInky.getY()))))){
					prevInky = Position.getRandom(new Position(0,0), new Position(25,18));
				}
				prevPinky = Position.getRandom(new Position(0,0), new Position(25,18));
				while(!map1.pinky.isAutorisedEmplacement(String.valueOf(map1.map.charAt(map1.associatedCharIndex(prevPinky.getX(), prevPinky.getY()))))){
					prevPinky = Position.getRandom(new Position(0,0), new Position(25,18));
				}
				map1.fantomeAI(map1.clyde, prevClyde);
				map1.fantomeAI(map1.inky, prevInky);
				map1.fantomeAI(map1.pinky, prevPinky);
				map1.fantomeAI(map1.blinky, map1.pacman);
				//map1.fantomeAI(map1.pacman, prev);
				//System.out.println(map1.map.charAt(map1.associatedCharIndex(map1.pacman.posOnMap.getX(), map1.pacman.posOnMap.getY())));
				
				//Bouger les persos
				
				map1.bougerSelonDirection(map1.pacman);
				map1.persoCollisionHandler();
				
				map1.bougerSelonDirection(map1.clyde);
				map1.bougerSelonDirection(map1.blinky);
				map1.bougerSelonDirection(map1.pinky);
				map1.bougerSelonDirection(map1.inky);
				map1.persoCollisionHandler();
				//Super pouvoir pacman (quand une super bille est mangée)
				//il faut trouver un moyen que tout marche bien meme si on mage une bille et que le prec timer est pas terminé
				if(map1.startScareTimer)
				{
					map1.startScareTimer = false;
					Timer t = new Timer(3500, actionListener1);
					//t.setInitialDelay()
					System.out.println("Timer demarré");
					t.setRepeats(false);
					t.start();
					
				}
				fen.getContentPane().repaint();
				
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private static ActionListener actionListener1 = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			map1.inky.scared = false;
			map1.blinky.scared = false;
			map1.clyde.scared = false;
			map1.pinky.scared = false;
		};
	};
	
	private static KeyListener windowKeyListener = new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyChar() == 'z' || e.getKeyCode() == 38)
			{
				bUp = true;
				bDown = false;
				bLeft = false;
				bRight = false;
			}
			else if(e.getKeyChar() == 'q' || e.getKeyCode() == 37)
			{
				bLeft = true;
				bDown = false;
				bUp = false;
				bRight = false;
				
			}
			else if(e.getKeyChar() == 's' || e.getKeyCode() == 40)
			{
				bDown = true;
				bLeft = false;
				bUp = false;
				bRight = false;
			}
			else if(e.getKeyChar() == 'd'|| e.getKeyCode() == 39)
			{
				bRight = true;
				bDown = false;
				bUp = false;
				bLeft = false;
			}
		}
	};

}
