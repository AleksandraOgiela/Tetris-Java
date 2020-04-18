package tetris;

import java.awt.Color;
import java.util.Random;
/**
 * Klasa realizująca losowanie kolejnego klocka
 * @author Aleksandra Jarczyk Aleksandra Ogiela
 */
@SuppressWarnings("serial")
public class Next extends ACanvas {
/**
 * klocek
 */
	byte chock;
        /**
         * zmienna w której będą zapisane numery wylosowanych klocków
         */
	private Random los = new Random();
	/**
         *  Konstruktor klasy Next
         */
	Next() {
		super((byte)100, (byte)100);
		draw_lots();
	}
/**
 * implementacja metody drawImage, rysowanie klocka
 */
	@Override
	public void drawImage() {
		drawChock();
	}
	/**
         * Metoda losująca kolejny klocek z 7 możliwych do wylosowania,
         * nextInt(6)+1-generuje liczby z zakresu od zera do 6 oraz dodaje do nich jeden.
         */
	public void draw_lots()
	{
		chock = (byte)(los.nextInt(6)+1);// Generuje liczby z zakresu od zera do  6 oraz dodaje do nich jeden.
	}
	/**
         * Metoda rysująca następny klocek
         * @param x współrzędna x
         * @param y współrzędna y
         * @param k numer koloru i klocka
         */
	private void drawCube(byte x, byte y, byte k)
	{
		graphic.setColor(Chocks.COLOR[k]);
		graphic.fillRect(x*Chocks.size, y*Chocks.size, Chocks.size, Chocks.size);
		graphic.setColor(Color.BLACK);// kolor kratki na klocku next
		graphic.drawRect(x*Chocks.size, y*Chocks.size, Chocks.size-1, Chocks.size-1);
	}
/**
 * Funkcja rysująca ramkę dla następnego klocka 
 */
	private void drawChock()
	{
		graphic.setColor(Chocks.COLOR[0]);
		graphic.fillRect(0, 0, 4*Chocks.size, 4*Chocks.size);
		for (byte tx=0; tx<4; tx++)
			for (byte ty=0; ty<4; ty++)
				if (Chocks.CHOCKS[chock][ty][tx]) drawCube(tx,ty, (byte) (chock+1));
	}


}
