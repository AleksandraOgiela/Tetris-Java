package tetris;
/**
 * Klasa reprezentująca  klocek 
 * @author Aleksandra Jarczyk Aleksandra Ogiela
 */
public class Chock {
	
    /**
     *Tablica typu boolean 4 x 4,
     * ustala kształt klocków(tam gdzie true wypełnienie pola, false brak wypełnienia)
     */
    public boolean[][] tab = new boolean[4][4];
    /**
     * pomocnicza tablica typu boolean 4 x 4 realizująca obrót klocków
     */
	private boolean[][] tabE = new boolean[4][4];
        /**
         * bieżący klocek
         */
	byte aChock;
	/**
         * Konstruktor klasy Chock
         */
	Chock()
	{
		setChock((byte) 0);
	}
	/**
         * Metoda ustalala bieżący klocek oraz zmienia jego położenie podstawiając do kolumn pomocniczej tablicy tab wiersze tablicy CHOCKS
         * @param k numer koloru i klocka
         */
	public void setChock(byte k)
	{
		aChock = k;
		for (byte x=0; x<4; x++)
			for (byte y=0; y<4; y++)
				tab[y][x] = Chocks.CHOCKS[aChock][x][y];// podstawiamy do kolumn pomocniczej tablicy tab wiersze tablicy CHOCKS 
	}
	/**
         * Metoda realizująca obrót klocka przypisując do kolejnych kolumn tablicy tab  kolumny tablicy tabE w odwrotnej kolejności (odbicie symetryczne)
         */
	public void turn()
	{
		for (byte x=0; x<4; x++) for (byte y=0; y<4; y++) tabE[x][y] = tab[x][y];//przypisujemy elementom pomocniczej tablicy tabE elementy tablicy tab
		for (byte x=0; x<4; x++) for (byte y=0; y<4; y++) tab[3-y][x] = tabE[x][y];//do kolejnych kolumn tablicy tab wstawiamy kolumny tabE w odwrotnej kolejności (odbicie symetryczne)
	}
	/**
         * Metoda która uniemożliwia obrót klocka.
         */
	public void undoTurn()
	{
		for (byte x=0; x<4; x++) for (byte y=0; y<4; y++) tab[x][y] = tabE[x][y];// do tab przypisujemy elementy tabE w niezmienionej formie 
	}

}
