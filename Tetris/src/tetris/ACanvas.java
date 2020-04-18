package tetris;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Klasa dziedzicząca klasę Canvas,
 *  dziedziczy po klasie Displayable, zadaniem klasy Canvas jest przenieść obraz z obiektu klasy Graphics na ekran
 * @author Aleksandra Jarczyk Aleksandra Ogiela
 */
@SuppressWarnings("serial")
public abstract class ACanvas extends Canvas {
	//Klasa Canvas jest klasą dziedziczącą po klasie Displayable. Zadaniem klasy Canvas jest przenieść obraz z obiektu klasy Graphics na ekran
	
        BufferedImage image;
	Graphics2D graphic;
	/**
         * Konstruktor klasy ACanvas
         * @param width szerokość
         * @param height wysokość
         */
	ACanvas(short width, short height)
	{
		super();
		setSize(width, height);
                
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                
		graphic = (Graphics2D) image.getGraphics();
	}
	/**
         * Metoda drawImage
         */
	public abstract void drawImage();
	/**
         * Metoda wyświetlająca oraz usuwająca na ekranie grafikę 
         */
	private void naEkran()
	{
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();//usuniecie grafiki
	}
	/**
         * Metoda zawierająca  kod, który będzie uruchamiany w  wątku.
         */
	public void run()
	{
		drawImage();
		naEkran();
	}

}
