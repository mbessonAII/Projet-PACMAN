package jeu;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
//lien pour explication sprites :
//http://stackoverflow.com/questions/621835/how-to-extract-part-of-this-image-in-java
//oc
//https://openclassrooms.com/courses/apprenez-a-programmer-en-java/notre-premiere-fenetre
public class ImagePane extends JPanel {
     
    private static final long   serialVersionUID    = 1L;
       
    private BufferedImage img;
    private BufferedImage sprites;
    private int largeurMap = 168;
    private int hauteurMap = 220;
    private int marge = 2;
    
    private int xImg1 = marge*2;
    private int yImg1 = marge*2;
    
    private int xImg2 = 176;
    private int yImg2 = marge * 2;
    
    private int xImgBas = marge;
    private int yImgBas = 228-marge;
    
    
    public ImagePane(String str) {
    	try {
    		FileInputStream input = new FileInputStream(str);
    		img = ImageIO.read(input);
    		this.setSize(img.getWidth(), img.getHeight());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void paintComponent(Graphics g){
        
        int coef = 3;
        sprites = img.getSubimage(xImg2, yImg2, largeurMap, hauteurMap);
        //g.drawImage(sprites, 0, 0, this);
        g.drawImage(sprites, 2, 0, sprites.getWidth()*coef, sprites.getHeight()*coef, this);
        //Pour une image de fond
        //g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);                
      }          
     
}