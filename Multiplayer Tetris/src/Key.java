import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
//Alex Coombs
//APCS G Block
//APCS Quarter 4 project
//Date started: April 5th
//Date finished: June 7th
public class Key implements KeyListener{
	tetrisTester tetris;
	
	public Key(tetrisTester tetrisTester) {
		tetris = tetrisTester;
	}

	//what to do when certain keys are pressed
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_W)
		{
			//rotate when W is pressed
		tetris.rotate = true;
		}
		else if(key == KeyEvent.VK_A)
		{
			//move left when A is pressed 
			tetris.left = true;	
		}
		else if(key == KeyEvent.VK_D)
		{
			//move right is D is pressed
			tetris.right = true;
	
		}
		else if(key == KeyEvent.VK_S)
		{
			//move down if S is pressed
			tetris.down = true;
		}
	}

	//can't actually get rid of these bc of the interface I use
	public void keyTyped(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_W)
		{
			
		}
		else if(key == KeyEvent.VK_A)
		{
		}
		else if(key == KeyEvent.VK_D)
		{
		}
		else if(key == KeyEvent.VK_S)
		{
		}
		
	}

	//what to do when certain keys are released
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_W)
		{//stop rotating when W is released
			tetris.rotate = false;
		}
		else if(key == KeyEvent.VK_A)
		{
			//stop moving left is A is released
	tetris.left = false;
		}
		else if(key == KeyEvent.VK_D)
		{
			//stop moving right when D is released
			tetris.right = false;
		}
		else if(key == KeyEvent.VK_S)
		{
			//stop moving down at a faster pace when D is released
			tetris.down = false;
		}
		
	}
}
