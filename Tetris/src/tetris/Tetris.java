package tetris;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Main
 * @author Aleksandra Jarczyk Aleksandra Ogiela
 */
@SuppressWarnings("serial")
public class Tetris extends JPanel implements Runnable {

	/**
         * nowy obiekt klasy Tetris
         */
	static Tetris tetris = new Tetris();//utworzenie obiektu klasy Tetris 
        /**
         * utworzenie okna 
         */
	static JFrame okno = new JFrame("Tetris");//tworzenie nowego okna
        /**
         * Utworzenie obiektu klasy Thread przekazując obiekt (tetris) wcześniej utworzonej klasy jako parametr konstruktora klasy Thread.(konsekwencja korzystania z interfejsu Runnable)
         */
	static Thread thread = new Thread(tetris);
	     /**
              * utworzenie nowej tablicy
              */          
	static Board board = new Board();//tworzenie nowej tablicy
        /**
         * utworzenie nowego obiektu klasy Next
         */
	static Next next = new Next();//utworzenie nowego obiektu klasy Next
	/**
         * zmienna informująca czy gra się rozpoczęła
         */
	boolean start = false;
        /**
         * składnik wartości wait w metodzie run
         */
	short op =50;
	/**
         * ilości zdobytych lini
         */
	static int lines = 0;
        /**
         * ilości zdobytych punktów
         */
        static int scores = 0;
        /**
         *  poziom
         */
        static int level = 1;// ustalenie początkowych wartości dla ilości zdobytych lini, punktów i poziomu gry
        /**
         * zmienna odpowiadająca za napis numeru ilości punktów
         */
	static JLabel lScores;
        /**
         * zmienna odpowiadająca za napis numeru ilości lini
         */
        static JLabel lLines;
        /**
         * zmienna odpowiadająca za napis numeru levelu
         */
        static JLabel lLevel;
        /**
         * zmienna odpowiadająca za napis "PUNKTY"
         */
        static JLabel lScore2;
        /**
         * zmienna odpowiadająca za napis "LINIE"
         */
        static JLabel lLines2;
        /**
         * zmienna odpowiadająca za napis "POZIOM"
         */
        static JLabel lLevel2;
        /**
         * zmienna odpowiadająca za kolor napisu dla punktów
         */
	static Color kScores = new Color(255,128,255);
        /**
         * zmienna odpowiadająca za kolor napisu dla lini
         */
	static Color kLines = new Color(255,255,0);
        /**
         * zmienna odpowiadająca za kolor napisu dla poziomu
         */
	static Color kLevel = new Color(0,255,255);
	/**
         * czcionka 1
         */
	static Font f1;
        /**
         * czcionka 2
         */
        static Font  f2;
	/**
         * konstruktor klasy Tetris
         * ustala kolor okna, oraz pzrypisuje zmiennym rodzaj czcionki.
         */
	Tetris()
	{
		super();
		setBackground(new Color(50,50,50));//kolor okna 
		setLayout(null);
		start = true;
		f1 = new Font("System", Font.BOLD, 20);
		f2 = new Font("System", Font.BOLD, 20);
	}
	
	
	/**
         * main 
         * 
         * @param args 
         */
	public static void main(String[] args) {
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//niezbędne, aby program kończył się kiedy użytkownik spróbuje zamknąć okienko
		okno.add(tetris);//dodanie okna
		okno.setSize(388,555);//rozmiar okna
		okno.setLocationRelativeTo(null);// centrowanie okna w obszarze pulpitu 
		okno.setResizable(false);//uniemożliwienie użytkownikowi zmiany rozmiaru ramki

		lScores = new JLabel("0", JLabel.LEFT);
		lScores.setForeground(kScores);lScores.setFont(f1);
		lScores.setBounds(310, 220, 100, 20);tetris.add(lScores);
		lScore2 = new JLabel("PUNKTY:", JLabel.LEFT);
		lScore2.setBounds(270, 190, 100, 20);lScore2.setForeground(kScores);
		lScore2.setFont(f2);tetris.add(lScore2);
		
		lLines = new JLabel("0", JLabel.LEFT);
		lLines.setForeground(kLines);lLines.setFont(f1);
		lLines.setBounds(310, 150, 100, 20);tetris.add(lLines);
		lLines2 = new JLabel("LINIE:", JLabel.LEFT);
		lLines2.setBounds(290, 120, 100, 20);lLines2.setForeground(kLines);
		lLines2.setFont(f2);tetris.add(lLines2);
		
		lLevel = new JLabel("1", JLabel.LEFT);
		lLevel.setForeground(kLevel);lLevel.setFont(f1);
		lLevel.setBounds(310, 80, 100, 20);tetris.add(lLevel);
		lLevel2 = new JLabel("POZIOM:", JLabel.LEFT);
		lLevel2.setBounds(270, 50, 100, 20);lLevel2.setForeground(kLevel);
		lLevel2.setFont(f2);tetris.add(lLevel2);
		
		board.setLocation(10, 10);tetris.add(board);//ustalenie lokalizacji i dodanie tablicy
		next.setLocation(270, 350);tetris.add(next);//dodanie next
		okno.setVisible(true);//widoczność okna 
		thread.start();//uruchomienie wątku wykonując metodę start() klasy Thread.
	}


/**
 * implementacja metody run (instrukcje wykonane po uruchomieniu wątku)
 */
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		long wait = 0, time_start, time_round;
		while (start)
		{
			time_start = System.nanoTime();
				board.run();
				next.run();
			time_round = System.nanoTime() - time_start;
			wait = op - time_round / 1000000;
			if (wait<=0) wait = 5;
			try {thread.sleep(wait);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

}
