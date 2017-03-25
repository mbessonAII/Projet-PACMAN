package jeu;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public interface InterfaceGestionSprite {
	
	public void animate();
	
	//envoie l'image a afficher, plus besoin de traitements sur l'image reçue
	public BufferedImage getImgToPrint();
	
	public BufferedImage[] tab = null;
	
	public static BufferedImage flip(BufferedImage input)
	{
		// Flip the image horizontally
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		
		tx.translate(-input.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		input = op.filter(input, null);
		
		return input;
	}
	
	public static void loadSprites(BufferedImage imgSource, Rectangle[] grid, BufferedImage[] sprites){
		int i=0;
		for (Rectangle rect : grid){
			sprites[i] = imgSource.getSubimage(grid[i].x, grid[i].y, grid[i].width, grid[i].height);
			i++;
		}
	}
	
	public static BufferedImage rotate(BufferedImage image, double angle) {
	    double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
	    int w = image.getWidth(), h = image.getHeight();
	    int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
	    GraphicsConfiguration gc = getDefaultConfiguration();
	    BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
	    Graphics2D g = result.createGraphics();
	    g.translate((neww - w) / 2.0, (newh - h) / 2.0);//changé 2 par 2.0 pour avoir un meilleur rendu
	    g.rotate(angle, w / 2.0, h / 2.0);
	    g.drawRenderedImage(image, null);
	    g.dispose();
	    return result;
	}

	public static GraphicsConfiguration getDefaultConfiguration() {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gd = ge.getDefaultScreenDevice();
	    return gd.getDefaultConfiguration();
	}
}
