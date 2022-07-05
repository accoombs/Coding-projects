import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class tetrisTester extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	public static final int width = 531;
	public static final int height = 715;
	private Image[] tetrisBlocks = new Image[7];
	boolean rotate;
	Key key;
	int count = 0;
	int secPassed = 0;
	int speed = 500;
	Timer time = new Timer();
	boolean down = false;
	public static int newPiece = 0;
	int shift = 9;
	int shiffy = 0;
	boolean left = false;
	boolean drops;
	int goRight = 0;
	int goLeft = 0;
	int secPassed2 = 0;
	int secPassed3 = 0;
	//setting up the various timers that are needed
	Timer time3 = new Timer();
	TimerTask task3 = new TimerTask(){
		
		public void run()
			{
				secPassed3++;
			}
		};
		Timer time2 = new Timer();
		TimerTask task2 = new TimerTask(){
			
			public void run()
				{
					secPassed2++;
				}
			};
	TimerTask task = new TimerTask(){
	
	public void run()
		{
			secPassed++;
		}
	};
	tetrisGrid tetrisGrid;
	boolean right = false;
	
	public static void main(String args[])
	{
		System.out.println("*PRESS 'W' TO ROTATE*");
		System.out.println("*PRESS 'A' TO MOVE RIGHT*");
		System.out.println("*PRESS 'D' TO MOVE LEFT*");
		System.out.println("*PRESS 'S' TO MOVE DOWNWARDS AT A FASTER PACE*");
		JFrame frame = new JFrame();
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		JMenuBar menu = new JMenuBar();
		menu.setBounds(0, 0, width, 50);
		
		//option to end game early
		JMenuItem EndGame = new JMenuItem("End Game");
		EndGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("end game");
				System.exit(0);
			}
		});
	
		//setting up the J-frame
		tetrisTester tm = new tetrisTester();
		tm.setBounds(0, 50, width, height-50);
		frame.setLayout(null);
		frame.add(tm);
		frame.add(menu);
		menu.add(EndGame);
		frame.setVisible(true);
		tm.start();
	}
	
	public void start() 
	{
	Thread t = new Thread(this);
	t.setPriority(Thread.MAX_PRIORITY);
	time.scheduleAtFixedRate(task, 0, speed);
	time2.scheduleAtFixedRate(task2, 0, speed);
	time3.scheduleAtFixedRate(task3, 0, 10000);
	t.start();
	}

	public void run() {
		init();
	boolean running = true;
	//the loop that runs the game
	while(running == true)
	{
		tetrisGrid.shiffy = this.shiffy;
		BufferStrategy buff = getBufferStrategy();
		if(buff == null)
		{
			createBufferStrategy(3);
			continue;
		}
		//creating the graphics
		Graphics2D graph = (Graphics2D) buff.getDrawGraphics();
		render(graph);
		update(graph);
		buff.show();
		//cases for moving right
		if(right == true && left == false && rotate == false)
		{
		goRight++;
		}
		if(goRight == 1 && rotate == false && down == false)
		{
			if(shift < 16 && tetrisGrid.allRight == 0)
			shift++;
			else if(shift < 17 && tetrisGrid.allRight == 1)
			shift++;
			if(shiffy < 7)
			shiffy++;
			
			right = false;
			right = false;
			goRight++;
		}
		//cases for moving left
		if(left == true && right == false && rotate == false)
		{
			goLeft++;
		}
		//change firstBlock methods to do all of the blocks within the 3 block span of the shape array
		if(goLeft == 1 && rotate == false)
		{
			if(shift > 2 && tetrisGrid.allLeft == 0 && tetrisGrid.straight == 0)
			{
				shift--;
			}
			else if(shift > 1 && (tetrisGrid.allLeft == 1 || tetrisGrid.straight == 1))
			{
				shift--;
			}
			if(shiffy > -7)
			{
				shiffy--;
			}
			
			left = false;
			left = false;
			goLeft++;
		}
		//drop the piece
		drop(graph);
		if(secPassed2 == 1)
		{
			goLeft = 0;
			goRight = 0;
			secPassed2 = 0;
			System.out.println("Score: " + tetrisGrid.score);
		}
	}
	}
	
	public void render(Graphics2D graph)
	{
		//displaying the board
		graph.setColor(Color.GRAY);
		graph.fillRect(50,0, width-105, 75);
		graph.setColor(Color.lightGray);
		graph.fillRect(50, 75, width-105, 100);
		graph.setColor(Color.GRAY);
		graph.fillRect(50, 100, width-105, height);
		tetrisGrid.drawGrid(graph);
	}
	public void update(Graphics2D graph)
	{
		//things added as the program goes on
		tetrisGrid.fullLine();
		if(newPiece == 0)
		{
		shift = 9;
		tetrisGrid.randomPiece();
		}
		}
	
	public void init()
	{
		//setting up keys
		addKeyListener(new Key(this));
		requestFocus();
		//getting the blocks
		try {
		tetrisBlocks = ImageLoader.LoadImage("/TetrisBlock.png",25);
		}
		catch(IOException e)
		{
			System.out.println("error cannot load in TetrisBlock.png");
			System.exit(1);
		}
		//setting up the grid
		tetrisGrid = new tetrisGrid(width, height, tetrisBlocks);
		
	}
	
	public void drop(Graphics2D graph)
	{
		//cases for dropping
		//standard drop call
		if(rotate && down == false && left == false && right == false && shift == tetrisGrid.prevShift)
		{
			tetrisGrid.rotate(graph, count, shift);
			rotate = false;
		}
		if(secPassed == 1 && down == false)
		{
			drops = tetrisGrid.droppable(count, graph, shift);
			if(drops == true && down == false)
			{
				if(shiffy > 8)
					shiffy = 8;
				if(shiffy < -8)
					shiffy = -8;
				if(tetrisGrid.goBack == 1)
				{
					shift = tetrisGrid.prevShift;
				}
			tetrisGrid.drop(count, shift, shiffy);
			shiffy = 0;
			}
			else
			{
				count= 0;
			}
			secPassed = 0;
			count++;
		}
		//higher speed drop call
		else if(down == true)
		{
			drops = tetrisGrid.droppable(count, graph, shift);
			if(drops == true)
			{
				if(shiffy > 8)
					shiffy = 8;
				if(shiffy < -8)
					shiffy = -8;
				if(tetrisGrid.goBack == 1)
				{
					shift = tetrisGrid.prevShift;
				}
			tetrisGrid.drop(count, shift, shiffy);
			shiffy = 0;
			}
			else
			{
				count= 0;
			}
			secPassed = 0;
			count++;
			//add a delay to manage drop speeds
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	}
		
}
