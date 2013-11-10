package com.remmylife.gui.tools;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageBytes
{
	public static BufferedImage bytes2BufferedImage(byte[] bytes)
	{
		if(bytes == null)
		{
			return null;
		}
		
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(in);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		return image;
	}
	
	public static byte[] bufferedImage2Bytes(BufferedImage bimage)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try
		{
			ImageIO.write(bimage, "gif", bos);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		return bos.toByteArray();
	}
	
	public static BufferedImage getBufferedImage(String filePath)
	{
		BufferedImage bImage = null;
		try
		{
		    bImage = ImageIO.read(new File(filePath));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		return bImage;
	}
}
