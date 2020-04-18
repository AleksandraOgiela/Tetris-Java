package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * Klasa reprezentująca tablicę
 * @author Aleksandra Jarczyk Aleksandra Ogiela
 */
@SuppressWarnings("serial")
public class Board extends ACanvas implements MouseListener, KeyListener{
    //MouseListener - interfejs zdarzeń myszy. Odpowiedzialny za kliknięcia i pojawianie się kursora.
    //KeyListener - interfejs zdarzeń klawiatury.
    /**
     * szerokość ramki
     */
	final static short WIDTH = Chocks.size * 10;
        /**
         * wysokość ramki
         */
	final static short HEIGHT = Chocks.size * 20;
	/**
         * tablica reprezentująca tablicę do gry wraz ze ścianami
         */
	byte[][] tab = new byte[12][22];//stworzenie tablicy ze ścianami
       
	/**
         * nowy klocek
         */
	Chock chock = new Chock();
        /**
         * współrzędna x położenia klocka 
         */
	byte chockX;
        /**
         * współrzędna y położenia klocka 
         */
        byte chockY;
	/**
         * zmienna będzie informować czy został wciśnięty klawisz lewy
         */
	boolean kLeft;
        /**
         * zmienna będzie informować czy został wciśnięty klawisz prawy
         */
        boolean kRight;
        /**
         * zmienna będzie informować czy został wciśnięty klawisz górny
         */
        boolean kUp;
        /**
         * zmienna będzie informować czy został wciśnięty klawisz dolny
         */
        boolean kDown;
	/**
         * szybkość
         */
	short speed;
        /**
         * szybkość maksymalna
         */
        short speedMax;
        /**
         * klawisz szybkości
         */
	boolean speedKey;
	
	
	/**
         * zmienna będzie informować czy gra się rozpoczęła
         */
	boolean gamePlay;
        /**
         * zmienna odpowiadająca za  pauzę
         */
        boolean pause;
        /**
         * kolor
         */
	Color sColor;
	
        
        /**
         * Konstruktor klasy Board
         * Tworzy górną, dolną, prawą i lewą ścianę.
         * Ustala współrzędną x i y tam gdzie zaczyna  się bieżący klocek.
         */
	Board() {
		super(WIDTH, HEIGHT);addMouseListener(this);addKeyListener(this);
		
		for (byte x=0; x<12; x++) {tab[x][0]=1;tab[x][21]=1;}// tworzenie ściany górnej i dolnej (przypisanie wartości 1 w polach pierwszego i ostatniego wiersza)
		for (byte y=0; y<22; y++) {tab[0][y]=1;tab[11][y]=1;}//tworzenie prawej i lewej ściany, również przypisanie 1 do pól skrajnie prawej i lewej kolumny 
		chockX = 4; // ustalenie współrzędnej x tam gdzie zaczyna  sie bieżący klocek 
		chockY = 0; // ustalenie współrzędnej y gdzie zaczyna  się bieżacy klocek  
		speedMax = (short) (21 - Tetris.level);//ustalenie szybkości
		gamePlay = false;// gra jeszcze się nie rozpoczęła
		pause = false;// nie wybrano pauzy 
		graphic.setFont(new Font("System", Font.BOLD, 30));//ustalenie czcionki
		sColor = Color.WHITE;// koloru
	}
/**
 * implementacja drawImage,
 * rysuje tablicę i bieżący klocek,
 * realizuje ruch bieżącego klocka w dół jeśli ten jeszcze nie jest przeszkodą,
 * jeśli klocek jest już przeszkodą ustala nowy klocek,
 * w przypadku pauzy lub końca gry wypisuje na ekranie odpowiedni komunikat
 */
	@Override
	public void drawImage() {
		if (gamePlay) // jeśli gra sie rozpoczęła
		{
			key();
			cmpBoard(); 
			drawBoard();// rysuje tablice
			drawChock(chockX, chockY);// rysuje bieżący klocek 
			if (!pause)// jeśli nie  pauza 
			{
				if (speed<speedMax) speed++;// jeśli szybkość mniejsza niz szybkośc max-> speed++
                                else 
					{
						speed=0;//zerujemy szybkość
						if (isChockBoard(chockX, (byte)(chockY+1))) chockY++;// jeśli klocek jeszcze nie jest ścianką to przesuwaamy sie w dół o 1 pole
                                                else
						{
							chockFinish();// jesli klocek jest ścianką to 
							newChock();// ustalamy nowy klocek 
						}
					}
			}
			else// jeśli pauza
			{
				graphic.setColor(sColor);
				graphic.drawString("PAUZA", 75, 495);
			}
	} 
		else // jesli koniec gry 
		{
			graphic.setColor(Chocks.COLOR[0]);
			graphic.fillRect(0, 0, WIDTH, HEIGHT);// nowa ramka współrzędne lewego górnego rogu 0,0 ; początkowa szerokość i wysokość
			graphic.setColor(sColor);
			graphic.drawString("TETRIS", 70, 50);// napis początkowy gry 
                        graphic.drawString("KLIK to START", 25,130);// napis początkowy gry 
			
			
		}
	}
	/**
         * Metoda ustala klocek next jako bieżący i losuje nowy klocek next,
         * ustala szybkość
         */
	public void newChock()
	{
		
		chockX = 4; 
		chockY = 0;
		speedMax = (short) (20 - Tetris.level);
		if (speedMax<0) speedMax=0;
		chock.setChock(Tetris.next.chock);// ustala kolocek next jako bieżący klocek, 
		Tetris.next.draw_lots();// losuje klocek next
		
	}
	/**
         * Metoda,która ustala końcowe położenie klocka 
         */
	public void chockFinish()
	{
		for (byte xx=0; xx<4; xx++)
			for (byte yy=0; yy<4; yy++)
				if (chock.tab[xx][yy]) tab[xx+chockX][yy+chockY]=(byte)(chock.aChock+1);
	}
	/**
         * Funkcja rysująca tablice do gry
         */
        
	private void drawBoard()
	{
		for (byte x=1; x<11; x++)
			for (byte y=1; y<21; y++) //tablica do gry
			{
				graphic.setColor(Chocks.COLOR[tab[x][y]]);
				graphic.fillRect((x*Chocks.size)-Chocks.size, (y*Chocks.size)-Chocks.size, Chocks.size, Chocks.size);//wypełnienie każdego prostokąta kolorem
				graphic.setColor(Color.BLACK);//kolor dla ramki 
				if (tab[x][y] > 0)//ramka
					graphic.drawRect((x * Chocks.size) - Chocks.size, (y * Chocks.size) - Chocks.size, Chocks.size - 1, Chocks.size-1);//rysowanie prostokąta o wymiarach taich jak dwa ostatnie argumenty i współrzędnych lewego górnego rogu takich jak dwa pierwsze argumenty  
			}
	}
        /**
         * Metoda rysująca bieżący klocek 
         * @param x współrzędna x położenia bieżącego klocka, numer kolumny tablicy 
         * @param y współrzędna y położenia bieżącego klocka, numer wiersza tablicy
         * @param k numer koloru
         */
	private void drawCube(byte x, byte y, byte k)
	{
		graphic.setColor(Chocks.COLOR[k]);//ustalenie koloru klocka 
		graphic.fillRect((x*Chocks.size)-Chocks.size, (y*Chocks.size)-Chocks.size, Chocks.size, Chocks.size);
		graphic.setColor(Color.WHITE);// ustalam kolor kratki na bieżacym klocku
		graphic.drawRect((x*Chocks.size)-Chocks.size, (y*Chocks.size)-Chocks.size, Chocks.size-1, Chocks.size-1);// -1 żeby podziałklocka na małe kwadraciki był widoczny
	}
	/**
         * Metoda sprawdzająca czy zapełniony jest cały wiersz
         * @param y współrzędna y czyli numer sprawdzanego wiersza
         * @return false-gdy chociaż w jednym z 10 klocków jest wartość 0 , true-gdy cały wiersz jest zapełniony
         */
        
	private boolean isLine(byte y)
	{
		for (byte x=1; x<11; x++) {if (tab[x][y]==0) return false;}
		return true;
	}
	/**
         * Metoda sprawdza czy cała tablica jest zapełniona,sprawdza tylko pierwszy wiersz od góry
         * @return false-gdy tablica nie jest zapełniona, true-gdy tablica jest zapełniona.
         */
        private boolean isFull()
	{
		for (byte x=1; x<11; x++) {if (tab[x][1]!=0) return true;}
		return false;
	}
	/**
         * Metoda, która po każdej zdobytej lini zwiększa jej liczbę o jeden, zwiększa liczbę punktów dodając do niej iloczyn aktualnego poziomu gry i liczby 10,
         *  ustala zmianę poziomu jeśli ilość zdobytych lini będzie równa wartości aktualnego poziomu do kwadratu
         * @param y aktualny cały wiersz.
         */
	private void setLine(byte y)
	{
		
		for (byte x=1; x<11; x++) tab[x][y]=8;
		Tetris.lines++;Tetris.lLines.setText(String.valueOf(Tetris.lines));//zmiana napisu z nową liczbą zdobytych lini
		Tetris.scores+=(Tetris.level * 10);// do liczby punktów dodawany jest iloczyn aktualnego poziomu i liczby 10.
		Tetris.lScores.setText(String.valueOf(Tetris.scores));// zmiana napisu z nową liczbą zdobytych punktów
		if (Tetris.lines==(Tetris.level*Tetris.level)) //jeśli ilość zdobytych lini będzie równa wartości aktualnego poziomu do kwadratu
		{
			Tetris.level++;// zwiększamy poziom o 1
			Tetris.lLevel.setText(String.valueOf(Tetris.level));// zmiana napisu z nową wartością poziomu
			if (speedMax>0) speedMax--;
		} 
	}
        /**
         * Metoda, która po skasowaniu całego ułożonego wiersza przesuwa wszystkie wiersze ponad nim w dół oraz zeruje pierwszy wiersz
         * @param y numer bieżącego wiersza tablicy
         */
	private void downBoard(byte y)
	{
		for (byte ty=y; ty>1; ty--)// w każdym wierszu powyżej wiersza bieżącego z numerem y
			for (byte x=1; x<11; x++) tab[x][ty]=tab[x][ty-1]; // zapisujemy wiersz znajdujący się powyżej
		for (byte x=1; x<11; x++) tab[x][1]=0;//pierwszy wiersz zerujemy
	}
	/**
         * Metoda wywołująca metodę powodującą przesunięcie części tablicy znajdującej się ponad linią zapełnioną 10 klockami,
         * sprawdza czy po przesunięciu częsci tablicy w dół nie utworzy się inny zapełniony wiersz,
         * sprawdza czy cała tablica jest zapełniona, jeśli tak zatrzymuje grę ustanawia poziom, zeruje liczbę punktów i zdobytych  całych liń, czyści całą tablicę. 
         */
	private void cmpBoard()
	{
		for (byte y=1; y<21; y++)
		{
			if (tab[1][y]==8) downBoard(y);
			if (isLine(y)) setLine(y);
		}
		if (isFull())
		{
			gamePlay = false;
			Tetris.level=1;
			Tetris.lines=0;
			Tetris.scores=0;
			chockX = 4; 
			chockY = 0;
			speedMax = (short) (21 - Tetris.level);
			for (byte x=1; x<11; x++) for (byte y=1; y<21; y++) tab[x][y]=0;
		}
	}
	/**
         * Metoda rysująca klocek w odpowieniej lokalizacji w zależności od wartośći x i y
         * @param x współrzędna położenia x klocka
         * @param y współrzędna położenia y klocka
         */
	public void drawChock(byte x, byte y)
	{
		for (byte tx=0; tx<4; tx++)
			for (byte ty=0; ty<4; ty++)
				if (chock.tab[tx][ty]) drawCube((byte)(tx+x),(byte) (ty+y), (byte) (chock.aChock+1));
	}
/**
Metoda wywoływana, gdy następuje kliknięcie, czyli wciśnięcie i zwolnienie przycisku myszy, obie te operacje muszą zajść w jednym położeniu.
 * @param e obiekt KeyEvent, parametr z referencją na zdarzenie-kliknięcie klawisza myszy
 */
	@Override
	public void mouseClicked(MouseEvent e) {}
/**
 * Metoda wywoływana, gdy zostaje wciśnięty przycisk myszy: rozpoczyna grę, lub ustala pauzę.
 * @param e obiekt KeyEvent, parametr z referencją na zdarzenie-naciśnięcie klawisza myszy
 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (!gamePlay) //jeśli gra jeszcze się nie rozpoczęła
		{
			gamePlay=true;// rozpoczynamy grę 
			Tetris.lScores.setText(String.valueOf(Tetris.scores));//wypisanie liczby punktów
			Tetris.lLines.setText(String.valueOf(Tetris.lines));//wypisanie liczby lini
			Tetris.lLevel.setText(String.valueOf(Tetris.level));// wypisanie poziomu
		}else// jeśli gra toczy się 
		pause=!pause;// PAUZA
	}
/**
 * Metoda wywoływana, gdy następuje zwolnienie przycisku myszy
 * @param e obiekt KeyEvent, parametr z referencją na zdarzenie-zwolnienie klawisza myszy
 */
	@Override
	public void mouseReleased(MouseEvent e) {}
/**
 * Metoda wywoływana, gdy kursor pojawia się w obszarze nasłuchującym zdarzenia
 * @param e obiekt KeyEvent, parametr z referencją na zdarzenie-kursor pojawia się w obszarze nasłuchującym
 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}
/**
 * Metoda wywoływana, gdy kursor opuszcza obszar nasłuchujący zdarzenia
 * @param e obiekt KeyEvent, parametr z referencją na zdarzenie-kursor opuszcza obszar nasłuchujący
 */
	@Override
	public void mouseExited(MouseEvent e) {
	}
/**
 * Metoda wywoływana, gdy wprowadzono znak z klawiatury
 * @param e obiekt KeyEvent, parametr z referencją na zdarzenie- wprowadzono znak z klawiatury
 */
	@Override
	public void keyTyped(KeyEvent e) {}
/**
 * Metoda, która jeśli następuje wcisniecie danego klawisza przypisuje zmiennym odpowiadającym za ruch w górę, dół, prawo, lewo wartość true 
 * @param e obiekt KeyEvent, parametr z referencją na zdarzenie-wciśnięcie odpowiedniego klawisza
 */
	@SuppressWarnings("static-access")
	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if (k==e.VK_UP) kUp = true;
		if (k==e.VK_DOWN) kDown = true;
		if (k==e.VK_LEFT) kLeft = true;
		if (k==e.VK_RIGHT) kRight = true;
	}
/**
 * Metoda, która jeśli następuje zwolnienie danego klawisza przypisująca zmiennym odpowiadającym za ruch w górę, dół, prawo, lewo wartość false
 * @param e obiekt KeyEvent, parametr z referencją na zdarzenie -zwolnienie odpowiedniego klawisza
 */
	@SuppressWarnings("static-access")
	@Override
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		if (k==e.VK_UP) kUp = false;
		if (k==e.VK_DOWN) kDown = false;
		if (k==e.VK_LEFT) kLeft = false;
		if (k==e.VK_RIGHT) kRight = false;
	}
	/**
         * Metoda sprawdza czy dany ruuch o jedną kratkę sprawi że klocek stanie się przeszkodą
         * @param x współrzędna x położenia klocka
         * @param y współrzędna y położenia klocka
         * @return false jeśli klocek staje sie przeszkodą, czyli jeśli konkretny ruch sprawi że klocek dotknie dolnej ścianki lub klocków które już wcześniej stały sie przeszkodami, true- jeśli klocek jeszcze ścianką nie jest.
         */
	private boolean isChockBoard(byte x, byte y)
	{
		for (byte xx=0; xx<4; xx++)
			for (byte yy=0; yy<4; yy++)
				if (chock.tab[xx][yy] && tab[xx+x][yy+y]>0) return false;
		return true;
	}
	/**
         * Metoda, która zwraca wartość boolean true/false w zależności czy ruch o jeden w lewo sprawi że klocek stanie się przeszkodą lub nie 
         * @return false- jeśli po przewidywanym ruchu o jeden w lewo kolcek stanie się przeszkodą, true-jeśli po tym ruchu klocek nie stanie sie przeszodą 
         */
	private boolean moveLeft()
	{
		if (!isChockBoard((byte)(chockX-1), chockY)) return false;//jeśli po przewidywanym ruchu o jednow w lewo kocek stanie sie przeszkodą zwraca false
		return true;
	}
        /**
         * Metoda, która zwraca wartość boolean true/false w zależności czy ruch o jeden w prawo sprawi że klocek stanie się przeszkodą lub nie 
         * @return false- jeśli po przewidywanym ruchu o jeden w prawo kolcek stanie się przeszkodą, true-jeśli po tym ruchu klocek nie stanie sie przeszodą.
         */
	private boolean moveRight()
	{
		if (!isChockBoard((byte)(chockX+1), chockY)) return false;
		return true;
	}
	/**
         * Metoda która w zależośći od naciśniętego  przycisku oraz od warości zwracanych funkcji określających czy dany ruch jest możliwy wykonuję ten ruch.
         */
	private void key()
	{
		speedKey=!speedKey;
		if (kUp && speedKey) {chock.turn();if (!isChockBoard(chockX, chockY)) chock.undoTurn();}//jeśli naciśnięto klawisz górny obrót klocka, jesli klocek staje sie przeszkodą 
		if (kLeft && moveLeft()) chockX--;//jeśli wciśnięto lewy klawisz i ruch w lewo nie spowoduje zamiany klocka w przeszkodę to przesunięcie w lewo o jeden 
		if (kRight && moveRight()) chockX++;//jeśli wciśnięto prawy klawisz i ruch w prawo nie spowoduje zamiany klocka w przeszkodę to przesunięcie w prawo o jeden 
		if (kDown && speedMax>0) {speedMax=0;}
	}

}

