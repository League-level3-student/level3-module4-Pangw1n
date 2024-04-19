package _03_Hangman;

import javax.swing.JOptionPane;

public class Hangman {
	public static void main(String[] args) {
		new Hangman().run();
	}
	
	public void run()
	{
		int rounds = -1;
		while (rounds < 0 || rounds >= Utilities.getTotalWordsInFile("dictionary.txt"))
		{
			rounds = Integer.parseInt(JOptionPane.showInputDialog("Input the number of rounds (1 -" + Utilities.getTotalWordsInFile("dictionary.txt") + ")")) - 1; 
		}
		
		
	}
}
