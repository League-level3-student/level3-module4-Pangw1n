package _00_IntroToStacks;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class _02_TextUndoRedo implements KeyListener {
    /* 
     * Create a JFrame with a JPanel and a JLabel.
     * 
     * Every time a key is pressed, add that character to the JLabel. It should
     * look like a basic text editor.
     * 
     * Make it so that every time the BACKSPACE key is pressed, the last
     * character is erased from the JLabel.
     * 
     * Save that deleted character onto a Stack of Characters.
     * 
     * Choose a key to be the Undo key. Make it so that when that key is
     * pressed, the top Character is popped  off the Stack and added back to
     * the JLabel.
     */

	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	JLabel label = new JLabel();
	
	String text = "";
	
	Stack<Character> textStack = new Stack<Character>();
	Stack<Character> recentlyDeleted = new Stack<Character>(); 
	
	public static void main(String[] args) {
		new _02_TextUndoRedo().run();
	}
	
	public void run()
	{
		frame.add(panel);
		panel.add(label);
		frame.setVisible(true);
		frame.addKeyListener(this);
		frame.setPreferredSize(new Dimension(400, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getKeyCode());
		if (arg0.getKeyCode() == 8)
		{
			if (text.length() > 0)
			{
				recentlyDeleted.push(text.charAt(text.length() - 1));
				text = text.substring(0, text.length() - 1);
				label.setText(text);
			}
		}
		else if (arg0.getKeyCode() == 157)
		{
			if (recentlyDeleted.size() > 0)
			{
				text = text + recentlyDeleted.pop();
				label.setText(text);
			}
		}
		else if (Character.isDefined(arg0.getKeyChar()))
		{
			text = text + arg0.getKeyChar();
			label.setText(text);
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
