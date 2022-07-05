//Alex Coombs
//APCS G Block
//APCS Quarter 4 project
//Date started: April 5th
//Date finished: June 7th
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	//loads in tetris blocks as seperate images
	public static Image[] LoadImage(String path, int width) throws IOException
	{
		BufferedImage load = ImageIO.read(ImageLoader.class.getResource(path));
		Image[] Images = new Image[load.getWidth()/width];
		for(int i = 0; i < Images.length; i++)
		{
			Images[i] = load.getSubimage(i*width, 0, width, width);
		}
		return Images;
	}
	
}
