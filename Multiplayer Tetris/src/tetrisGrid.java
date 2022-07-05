//Alex Coombs
//APCS G Block
//APCS Quarter 4 project
//Date started: April 5th
//Date finished: June 7th
import java.awt.Graphics2D;
import java.awt.Image;

public class tetrisGrid {

	private int width;
	private int height;
	int[][] grid;
	private static int squareSize = 25;
	private Image[] tetrisBlocks;
	int[][] shape;
	int type = -1;
	tetrisTester tet = new tetrisTester();
	int rotPos = 0;
	int shade = 0;
	int shiffy = 0;
	Pieces p;
	int lowPoint1 = 2;
	int lowPoint2 = 2;
	int lowPoint3 = 2;
	int allLeft = 0;
	int counter;
	int prevShift = 9;
	int straight;
	int counter2 = 0;
	int tall1 = 0;
	int tall2 = 0;
	int tall3 = 0;
	int counter3 = 0;
	int allRight = 1;
	int prevcount = 0;
	int score = 0;
	int goBack = 0;
	int botRow = 0;
	int[][] prevShape;

	public tetrisGrid(int w, int h, Image[] tetris)
	{
		//seting up the game board
	this.width = w / squareSize;
	this.height = h / squareSize;
	grid = new int[height][width];
	
	for(int i = 0; i < height; i++)
	{
		for(int x = 0; x < width; x++)
		{
			grid[i][x] = -1;
		}
	}
	tetrisBlocks = tetris;
	}
	
	public void drawGrid(Graphics2D graph)
	{
		//displaying the game board
		for(int i = 0; i < height; i++)
		{
			for(int x = 0; x < width; x++)
			{
				if(grid[i][x] != -1)
				{
					graph.drawImage(tetrisBlocks[grid[i][x]], (x)*squareSize, (i)*squareSize, 25, 25, null);
				}
			}
		}
	}
	
	public void randomPiece() {
		//creating a piece to use
		allLeft = 0;
		lowPoint1 = 2;
		lowPoint2 = 2;
		lowPoint3 = 2;
		rotPos = 0;
		type = -1;
		//piece color
		int color = (int) (Math.random() * 7);
		shade = color;
		
		//piece type
		int piece = (int) (Math.random() * 7);
		
		type = piece;
		
		if(shape != null)
		{
		for(int i = 0; i < shape.length; i++)
		{
			for(int x = shape.length; x < shape.length; x++)
			{
				shape[i][x] = -1;
			}
		}
		}
		
		
		//different pieces
		if(piece == 0)
		{

		p = new zigzagLeft();
		shape =	p.createpiece(color);
		}
		
		if(piece == 1)
		{
	
		p = new ZigZagRight();
		shape =  p.createpiece(color);
		}
		
		if(piece == 2)
		{

		p = new LLeft();
		shape =  p.createpiece(color);
		}

		if(piece == 3)
		{

		p = new LRight();
		shape =  p.createpiece(color);
		}

		if(piece == 4)
		{

		p = new staight();
		shape =  p.createpiece(color);
		}
		
		if(piece == 5)
		{

		p = new T();
		shape = p.createpiece(color);
		}
	
		if(piece == 6)
		{

		p = new square();
		shape = p.createpiece(color);
		allLeft = 1;
		}
		prevShape = shape;
			tetrisTester.newPiece = 1;
	}
				
	//determine whether there is a complete line
	public void fullLine()
	{
		int count = 0;
		for(int i = 1; i < height; i++)
		{
			count = 0;
			for(int x = 0; x < width; x++)
			{
				if(grid[i][x] != -1)
				{
					count++;
				}
				if(count == width-4)
				{
					deleteLine(i);
					score++;
					break;
				}
			}
		}
	}
	
	public void deleteLine(int row)
	{
		//gets rid of the line
		for(int i = row; i > 0 ; i--)
		{
			for(int x = 0; x < width; x++)
			{
				grid[i][x] = grid[i-1][x];
			}
		}
	}
	
	public void rotate(Graphics2D graph, int count, int shift)
	{//rotates piece
		prevShape = shape;
		int[][] temp = new int[3][3];
		rotPos++;
		int same = 0;
			temp = p.rotate(rotPos, shade);
			//makes sure the rotation is legal
			if((allLeft == 1) && (grid[count][shift-1] != -1 || grid[count+1][shift-1] != -1 || grid[count+2][shift-1] != -1))
			{
				rotPos--;
				same++;
			}
			else if((allRight == 1) && (grid[count][shift+3] != -1 || grid[count+1][shift+3] != -1 || grid[count+2][shift+3] != -1))
			{
			rotPos--;
			same++;
			}
			temp = p.rotate(rotPos, shade);
		if(same == 0)
		{
		for(int i = 0; i < 3; i++)
		{
			for(int x = 0; x < 3; x++)
			{
				grid[i+count][x+shift] = -1;
				grid[i+count][x+shift] = shape[i][x];
			}
		}
		}
		shape = temp;
	}
	
	public void drop(int count, int shift, int shiffy)
	{
		//drops piece 
		int out = 0;
		
		for(int i = 3; i >= 1; i--)
		{
			for(int x = 2; x >= 0; x--)
			{
				//get rid of piece trail
				if(grid[i+count-1][x+prevShift] != -1 && (shape[i-1][x] != -1))
				{
						grid[i+count-1][x+prevShift] = -1;
				}
				//to test for an unside down L
				if(tall3 == 1 && lowPoint2-count == 0 && grid[lowPoint2+2][x+shift] == -1 
						&& grid[lowPoint2+3][x+shift] != -1 && x == 1 && prevShift == shift)
				{
					grid[lowPoint2+1][shift+2] = -1;
					grid[lowPoint2][shift+2] = -1;
					grid[lowPoint2][shift+1] = -1;
					
					if(grid[lowPoint3+2][shift+2] != -1 || count+6 == 27)
					{
						grid[lowPoint2+1][shift+2] = shape[0][1];
						grid[lowPoint2+1][shift+1] = shape[0][1];
						grid[lowPoint2+2][shift+2] = shape[0][1];
						out = 1;
						break;
					}
					grid[lowPoint2+2][shift+1] = shape[0][1];
					grid[lowPoint2+2][shift+2] = shape[0][1];
					grid[lowPoint2+4][shift+2] = shape[2][2];
					out = 1;
					break;
					
				}
				if(tall2 == 1 && lowPoint3-count == 0 && grid[lowPoint3+2][shift+2] == -1 
						&& grid[lowPoint3+3][shift+2] != -1 && x == 2 && prevShift == shift)
				{
					grid[lowPoint3][shift+1] = -1;
					grid[lowPoint3][shift+2] = -1;
					grid[lowPoint3+1][shift+1] = -1;
					
					
					if(grid[lowPoint2+2][shift+1] != -1 || count+6 == 27)
					{
						grid[lowPoint3+1][shift+2] = shape[0][1];
						grid[lowPoint3+1][shift+1] = shape[0][1];
						grid[lowPoint2+1][shift+1] = shape[0][1];
						out = 1;
						break;
					}
					grid[lowPoint3+2][shift+2] = shape[0][1];
					grid[lowPoint3+3][shift+1] = shape[0][1];
					grid[lowPoint3+4][shift+1] = shape[2][1];
					out = 1;
					break;
					
				}
				//standard piece drop
				if(grid[i+count][x+shift] == -1 && shape[i-1][x] != -1)
				{
				grid[i+count][x+shift] = shape[i-1][x];
				}
				//get rid of peice trail when moving in a direction
				if(x == 0 && shiffy > 0)
				{
					for(int z = 1; z <= shiffy; z++)
					{
						if(allLeft ==0 && allRight ==0)
						{
				grid[i+count][x+shift-z] = -1;
				grid[i+count-1][x+shift-z] = -1;
						}
					}
				}
				if(x == 2 && shiffy < 0)
				{
				for(int z = 1; z <= -1*shiffy; z++)
					{
					if(allRight == 0)
					{
						grid[i+count][x+shift+z] = -1;
						grid[i+count-1][x+shift+z] = -1;
					}
					}
				}
			}
			if(out == 1)
				break;
			}
		prevShift = shift;
		prevcount = count;
		}
		

	public boolean droppable(int count, Graphics2D graph, int shift) {
		// test if piece can be dropped 
		int triangleLeft = 0;
		int triangleRight = 0;
		counter = 0;
		counter2 = 0;
		straight = 0;
		allLeft = 0;
		tall1 = 0;
		tall2 = 0;
		tall3 = 0;
		allRight = 0;
		counter3 = 0;
		goBack = 0;
		botRow = 0;
		//getting information to use when deciding if a piece can be dropped
		for(int i = 0; i < 3; i++)
		{
			for(int x = 0; x < 3; x++)
			{
				if(x == 0)
				{
					if(shape[i][x] != -1)
					{
						lowPoint1 = i+count;
						if(i == 0 && shape[i][x] != -1)
							tall1++;
					}
					else
						counter++;
				}
					if(x == 1)
					{
						if(shape[i][x] != -1)
							lowPoint2 = i+count;
						if(i == 0 && shape[i][x] != -1)
							tall2++;
					}
						if(x == 2)
						{
							if(shape[i][x] != -1)
							{
								lowPoint3 = i+count;
								counter2++;
							}
							else
								botRow++;
							if(shape[i][x] == -1)
								counter3++;
							if(i == 0 && shape[i][x] != -1)
								tall3++;
							
						}
			}
		}
		//special shapes that need an exception
		if(shape[2][2] != -1 && shape[1][2] != -1 && shape[1][1] != -1)
		{
			triangleRight = 3;

		}
		if(shape[2][1] != -1 && shape[1][1] != -1 && shape[1][0] != -1)
		{
			triangleRight = 2;	
	
		}
		if(shape[0][1] != -1 && shape[0][2] != -1 && shape[1][2] != -1)
		{
			triangleRight = 3;
	
		}
		if(shape[1][0] != -1 && shape[2][0] != -1 && shape[1][1] != -1)
		{
			triangleLeft = 1;
	
		}
		if(shape[1][1] != -1 && shape[0][1] != -1 && shape[2][0] != -1)
		{
			triangleLeft = 2;	

		}
		if(shape[0][1] != -1 && shape[0][0] != -1 && shape[1][0] != -1)
		{
			triangleLeft = 1;

		}
		if(shape[2][1] != -1 && shape[1][1] != -1 && shape[1][2] != -1)
		{
			triangleLeft = 2;

		}
		if(shape[0][1] != -1 && shape[0][2] != -1 && shape[1][1] != -1)
		{
			triangleLeft = 2;

		}
		
		if(counter3 == 3)
		{
			allRight = 1;
		}
		if(counter2 == 3)
			straight = 1;
		if(counter == 3)
			allLeft = 1;
		drawGrid(graph);
		for(int i = 3; i >= 1; i--)
		{
			for(int x = 2; x >= 0; x--)
			{
				// test and correct for sideways collisions
		if(shift > prevShift && (firstBlockRight(count+i+1, prevShift) <= shift || grid[i+count+2][shift+3] != -1 || grid[i+count+1][shift+3] != -1 || grid[i+count][shift-1] != -1) 
				&&( grid[i+count+2][shift+2] != -1) && allRight == 0 && allRight == 0)
		{
			shift = prevShift;
			goBack = 1;
		}
		else if(shift < prevShift && (firstBlockLeft(count+i+1, prevShift) >= shift || grid[i+count+2][shift-1] != -1 || grid[i+count+1][shift-1] != -1 || grid[i+count][shift-1] != -1) 
				&&  grid[i+count+2][shift] != -1 && allLeft == 0 && allRight == 0)
		{
			shift = prevShift;
			goBack = 1;
		}
		else if(shift < prevShift && (firstBlockLeft(count+i+1, prevShift) >= prevShift || grid[i+count+1][shift] != -1 || grid[i+count+2][shift] != -1 || grid[i+count][shift+1] != -1) 
				&&  grid[i+count+1][shift+1] != -1 && allLeft == 1)
		{
			shift = prevShift;
			goBack = 1;
		}
		else if(shift > prevShift && (firstBlockRight(count+i+1, prevShift) <= prevShift || grid[i+count+1][shift+2] != -1 || grid[i+count+2][shift+2] != -1 || grid[i+count][shift-1] != -1) 
				&& grid[i+count+2][shift+1] != -1 && allRight == 1)
		{
			shift = prevShift;
			goBack = 1;
		}
			}
		}
		//standard drop case
		if((count+6 >= 28 || ((grid[lowPoint1+1][shift] != -1 || grid[lowPoint2+1][shift+1] != -1 || grid[lowPoint3+1][shift+2] != -1)) && allLeft == 0 && allRight == 0))
		{
			if(triangleRight == 2)
			{
				if((grid[lowPoint2+1][shift] == -1 && (grid[lowPoint2+1][shift+1] == -1 && grid[lowPoint3+1][shift+2] == -1)) && count+6 < 28 && prevShift != shift)
				{
					
					return true;
				}
			}
			if(triangleRight == 3)
			{
				if(grid[lowPoint3+1][shift+1] == -1 && grid[lowPoint2+1][shift] == -1 && grid[lowPoint3+1][shift+2] == -1 && count+6 < 28 && prevShift != shift)
				{
					
					return true;
				}
			}
			if(triangleLeft == 2)
			{
				if(grid[lowPoint2+1][shift+2] == -1 && grid[lowPoint1+1][shift] == -1 && grid[lowPoint2+1][shift+1] == -1 && count+6 < 28 && prevShift != shift)
				{
				
					return true;
				}
			}
			if(triangleLeft == 1)
			{
				if(grid[lowPoint1+1][shift+1] == -1 && grid[lowPoint1+1][shift] == -1 && grid[lowPoint3+1][shift+2] == -1 && count+6 < 28 && prevShift != shift)
				{
					
					return true;
				}
			}
			if(triangleRight == 3)
			{
				if(grid[lowPoint3+1][shift+1] == -1 && grid[lowPoint1+1][shift] == -1 && grid[lowPoint3+1][shift+2] == -1 && count+6 < 28 && prevShift != shift)
				{
					return true;
				}
			}
			//test for a game over
			if(count == prevcount)
			{
				System.out.println("GAME OVER");
				System.out.println("YOU SCORED " + score + " POINTS");
				System.out.println("thanks for playing :)");
			System.exit(0);
			}
			prevcount = count;
			tetrisTester.newPiece = 0;
			return false;
		}
		//drop case if the piece does not have a first collumn
		else if((count+6 >= 28 || (grid[lowPoint2+1][shift+1] != -1 || grid[lowPoint3+1][shift+2] != -1)) && allLeft == 1 && allRight == 0)
		{
			if(triangleRight == 3)
			{
				if(grid[lowPoint3+1][shift+1] == -1 && grid[lowPoint3+1][shift+2] == -1 && count+6 < 28 && prevShift != shift && lowPoint2 == lowPoint3-1)
				{
					
					return true;
				}
				else if(grid[lowPoint3+1][shift+1] == -1 && grid[lowPoint3+1][shift+2] == -1 && count+6 < 28 && prevShift != shift && lowPoint2 == lowPoint3-2)
				{
					
					return true;
				}
			}
			if(triangleLeft == 2)
			{
				if(grid[lowPoint2+1][shift+1] == -1 && grid[lowPoint2+1][shift] == -1 && count+6 <= 28 && prevShift != shift && lowPoint3 == lowPoint2-1)
				{
					
					return true;
				}
				else if((grid[lowPoint2+1][shift+1] == -1 && grid[lowPoint2+1][shift] == -1) && count+6 <= 28 && prevShift != shift && lowPoint3 == lowPoint2-2)
				{
					
					return true;
				}
			}

			if(count == prevcount)
			{
			System.out.println("GAME OVER");
			System.out.println("YOU SCORED " + score + " POINTS");
			System.out.println("thanks for playing :)");
			System.exit(0);
			}
			prevcount = count;
			tetrisTester.newPiece = 0;
			return false;
		}
		else if((allRight == 1) && count+6 <= 28 && grid[lowPoint2+1][shift+1] != -1)
		{
			//the straight piece needs special exceptions in some cases
			if(count == prevcount)
			{
			System.out.println("GAME OVER");
			System.out.println("YOU SCORED " + score + " POINTS");
			System.out.println("thanks for playing :)");
			System.exit(0);
			}
			prevcount = count;
			tetrisTester.newPiece = 0;
			return false;
		}
		return true;
	}
	
	public int firstBlockLeft(int count, int shift)
	{
		//returns the first block to the left of the piece
		if(allLeft != 1 && allRight != 1)
		{
			for(int i = shift-1; i > 0; i--)
			{
				if(grid[count][i] != -1)
				{
					return i;
				}
		}
		}
			else if(allLeft == 1)
			{
				for(int i = shift; i > 0; i--)
				{
					if(grid[count][i] != -1)
					{
						return i;
					}
			}
			}
		return 0;
	}
	
	public int firstBlockRight(int count, int shift)
	{
		//returns first block to the right of the piece
		if(allRight != 1)
		{
		for(int i = shift+2; i < 18; i++)
		{
			if(grid[count][i] != -1)
			{
				return i;
			}
		}
	}
		else
		{
			for(int i = shift+1; i > 0; i--)
			{
				if(grid[count][i] != -1)
				{
					return i;
				}
		}
		}
		return 18;
	}
}
