//Alex Coombs
//APCS G Block
//APCS Quarter 4 project
//Date started: April 5th
//Date finished: June 7th
public class square extends Pieces {
//square piece
int[][] piece = new int[3][3];
	
	public int[][] createpiece(int n) {
		
		for(int i = 0; i < piece.length; i++)
		{
			for(int x = 0; x < piece.length; x++)
			{
				piece[i][x] = -1;
			}
		}
		
		piece[1][1] = n;
		piece[2][1] = n;
		piece[2][2] = n;
		piece[1][2] = n;
		
		
		return piece;
	}
public int[][] rotate(int rotPos, int n) {
		return piece;
	}
}
