package tetris;

import java.awt.Color;
/**
 * Klasa reprezentująca kształty klocków oraz ich kolory
 * @author Aleksandra Jarczyk Aleksandra Ogiela
 */
public class Chocks {
    /**
     * rozmiar, który bedzie używany w ustaleniu rozmiaru poszczególnych części tablicy do gry 
     */
	final static short size = 25;
        /**
         * kolory kloców
         */
	final static Color[] COLOR = {new Color(90,89,90), Color.ORANGE, Color.BLUE, Color.GREEN, Color.RED, Color.CYAN, Color.YELLOW, Color.PINK, Color.WHITE};
        /**
         * tablice typów klocków
         */
	final static boolean[][][] CHOCKS = 
		{
				{
					{false, false, false, false},	//....
					{true , true , true , false},	//###.
					{false, false, true , false},	//..#.
					{false, false, false, false}	//....
				},
				{
					{false, false, false, false},	//....
					{true , true , true , false},	//###.
					{false, true , false, false},	//.#..
					{false, false, false, false}	//....
				},
				{
					{false, false, false, false},	//....
					{false, false, true , false},	//..#.
					{true , true , true , false},	//###.
					{false, false, false, false}	//....
				},
				{
					{false, false, false, false},	//....
					{true , true , true , true },	//####
					{false, false, false, false},	//....
					{false, false, false, false}	//....
				},
				{
					{false, false, false, false},	//....
					{false, true , true , false},	//.##.
					{false, true , true , false},	//.##.
					{false, false, false, false}	//....
				},
				{
					{false, false, false, false},	//....
					{false, true , true , false},	//.##.
					{true , true , false, false},	//##..
					{false, false, false, false}	//....
				},
				{
					{false, false, false, false},	//....
					{true , true , false, false},	//##..
					{false, true , true , false},	//.##.
					{false, false, false, false}	//....
				}
		};
        /**
         * Konstruktor klasy Chocks
         */
        Chocks()
        {
            
        }
}
