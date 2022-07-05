
public class zigzagLeft extends Pieces{

	//zigzag piece
	int[][] piece = new int[3][3];
	tetrisGrid tetris;

	public int[][] createpiece(int n) {
	
		for(int i = 0; i < piece.length; i++)
		{
			for(int x = 0; x < piece.length; x++)
			{
				piece[i][x] = -1;
			}
		}
		
		piece[1][0] = n;
		piece[1][1] = n;
		piece[2][1] = n;
		piece[2][2] = n;
		
		return piece;
	}
	
	public int[][] rotate(int rotPos, int n) {
		//different rotations
		for(int i = 0; i < 3; i++)
		{
			for(int x = 0; x < 3; x++)
			{
				piece[i][x] = -1;
			}
		}
		
		if(rotPos %4 == 0)
		{
			piece[1][0] = n;
			piece[1][1] = n;
			piece[2][1] = n;
			piece[2][2] = n;
		}
		if(rotPos %4 == 1)
		{
			piece[0][1] = n;
			piece[1][1] = n;
			piece[1][2] = n;
			piece[2][2] = n;

		}
		if(rotPos %4 == 2)
		{
			piece[1][0] = n;
			piece[1][1] = n;
			piece[2][1] = n;
			piece[2][2] = n;
		}
		if(rotPos %4 == 3)
		{
			piece[0][1] = n;
			piece[1][1] = n;
			piece[1][2] = n;
			piece[2][2] = n;

		}
		return piece;
		
	}

}
