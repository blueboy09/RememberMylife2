package com.remmylife.gui;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class BGPanel extends JPanel 
{
	Image im = null;
	public BGPanel()
	{
		super();
	}
	
	public BGPanel(String filePath)
	{
		super();
		try
		{
			im = ImageIO.read(new File(filePath));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public void setBackground(String filePath)
	{
		try
		{
			im = ImageIO.read(new File(filePath));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		this.revalidate();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(im, 0, 0, getWidth(), getHeight(), null);
	}
}
