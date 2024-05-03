package _03_Hangman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Hangman implements KeyListener {
	JFrame frame;
	JPanel panel;
	JLabel progress;
	JLabel label;
	JLabel guessedLetters;
	
	String word;
	int lives;
	Stack<String> stack;
	Stack<Character> guessedList;
	Stack<Character> wrongList;
	Boolean gameStart;
	
	int round;
	int totalRounds;
	
	public static void main(String[] args) {
		new Hangman().run();
	}
	
	public void run()
	{
		gameStart = false;
		int rounds = 0;
		while (rounds < 1 || rounds > Utilities.getTotalWordsInFile("dictionary.txt"))
		{
			String input = JOptionPane.showInputDialog("Input the number of rounds (1 -" + Utilities.getTotalWordsInFile("dictionary.txt") + ")"); 
			try
			{
				rounds = Integer.parseInt(input);
			}
			catch (final NumberFormatException e)
			{
				if (input == null)
				{
					System.exit(0);
				}
				else
				{
					continue;			
				}
			}
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
		round = 0;
		totalRounds = stack.size();
		
		
		frame = new JFrame();
		panel = new JPanel();
		frame.add(panel);
		progress = new JLabel();
		label = new JLabel();
		guessedLetters = new JLabel();
		panel.add(progress);
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
		round = totalRounds - stack.size();
		if (stack.size() == 0)
		{
			gameStart = false;
			JOptionPane.showMessageDialog(null, "You Win");
			frame.dispose();
			run();
		}
		guessedList = new Stack<Character>();
		wrongList = new Stack<Character>();
		lives = 10;
		word = stack.pop();
		progress.setText(round + "/" + totalRounds);
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
		label.setText("Word: " + placeholder + " Lives: " + lives);
		
		String wrongLetters = "";
		for (int i = 0; i < wrongList.size(); i++)
		{
			wrongLetters += wrongList.get(i);
		}
		guessedLetters.setText("Guessed Letters:" + wrongLetters);
		frame.pack();
		if (lives == 0)
		{
			gameStart = false;
			JOptionPane.showMessageDialog(null, "Game Over\nThe word was: " + word);
			frame.dispose();
			run();
		}
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
				lives--;
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
