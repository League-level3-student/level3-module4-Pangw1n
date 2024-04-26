package _03_Hangman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Hangman implements KeyListener {
	JFrame frame;
	JPanel panel;
	JLabel label;
	JLabel guessedLetters;
	
	String word;
	int lives;
	Stack<String> stack;
	Stack<Character> guessedList;
	Stack<Character> wrongList;
	Boolean gameStart;
	
	public static void main(String[] args) {
		new Hangman().run();
	}
	
	public void run()
	{
		gameStart = false;
		int rounds = 0;
		while (rounds < 1 || rounds > Utilities.getTotalWordsInFile("dictionary.txt"))
		{
			rounds = Integer.parseInt(JOptionPane.showInputDialog("Input the number of rounds (1 -" + Utilities.getTotalWordsInFile("dictionary.txt") + ")")); 
		}
		
		stack = new Stack<String>();
		for (int i = 0; i < rounds; i++)
		{
			String selectedword = "";
			while (true)
			{
				selectedword = Utilities.readRandomLineFromFile("dictionary.txt");
				if (!stack.contains(selectedword))
				{
					break;
				}
			}
			stack.push(selectedword);
		}
		
		frame = new JFrame();
		panel = new JPanel();
		frame.add(panel);
		label = new JLabel();
		guessedLetters = new JLabel();
		panel.add(label);
		panel.add(guessedLetters);
		frame.addKeyListener(this);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		
		startRound();
	}
	
	void startRound()
	{
		guessedList = new Stack<Character>();
		wrongList = new Stack<Character>();
		lives = 10;
		word = stack.pop();
		gameStart = true;
		update();
	}
	
	void update()
	{
		String placeholder = "";
		for (int i = 0; i < word.length(); i++)
		{
			if (guessedList.contains(word.charAt(i)))
			{
				placeholder += word.charAt(i);
			}
			else
			{
				placeholder += "_";
			}
		}
		label.setText("Word: " + placeholder);
		
		String wrongLetters = "";
		for (int i = 0; i < wrongList.size(); i++)
		{
			wrongLetters += wrongList.get(i);
		}
		guessedLetters.setText("Guessed Letters:" + wrongLetters);
		frame.pack();
		checkWin();
	}

	void checkWin()
	{
		for (int i = 0; i < word.length(); i++)
		{
			if (!guessedList.contains(word.charAt(i)))
			{
				return;
			}
		}
		
		gameStart = false;
		JOptionPane.showMessageDialog(null, "You guessed the word: " + word);
		startRound();
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (!gameStart)
		{
			return;
		}
		if (Character.isDefined(arg0.getKeyChar()) && Character.isAlphabetic(arg0.getKeyChar()))
		{
			if (guessedList.contains(arg0.getKeyChar()) || wrongList.contains(arg0.getKeyChar()))
			{
				return;
			}
			
			if (word.indexOf(arg0.getKeyChar()) != -1)
			{
				guessedList.add(arg0.getKeyChar());
			}
			else
			{
				wrongList.add(arg0.getKeyChar());
			}
			update();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
