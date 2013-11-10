package com.remmylife.gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.util.Calendar;

import com.remmylife.diary.*;
import com.remmylife.gui.tools.BufferedImageBytes;

public class MainWindow extends JFrame
{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 640;
	
	public MainWindow()
	{
		init();
	}
	
	public MainWindow(JPanel panel)
	{
		init();
		setPanel(panel);
	}
	
	private void init()
	{
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		try
		{
			this.setIconImage(ImageIO.read(new File("img/logo-whole.jpg")));
		}
		catch(Exception e){}
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		int w=(screenSize.width-WIDTH)/2;
		int h=(screenSize.height-HEIGHT)/2;
		setLocation(w,h);
		this.pack();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void setPanel(JPanel panel)
	{
		this.setContentPane(panel);
		revalidate();
	}
	
	public static void main(String[] args)
	{
		MainWindow mainWindow = new MainWindow();
		User user = new User();
		mainWindow.setPanel(new DiaryListWindow(user));
	}
}
