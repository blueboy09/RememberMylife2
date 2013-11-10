package com.remmylife.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *Apr 3, 2010 12:15:25 PM
 *作者：hyh
 *说明：
 */
public class JPanelWithBg extends JPanel {

 private BufferedImage img;
 private int width;
 private int height;
 public JPanelWithBg(String imgpath,int width, int height){
	this.width = width;
	this.height = height;
	this.setSize(width, height);
	try {
		img=ImageIO.read(new File(imgpath));
	} catch (IOException e) {
		e.printStackTrace();
	}  
 }

 @Override//这个方法是用来画Panel组件的
 protected void paintComponent(Graphics g) {
	 g.drawImage(img, 0, 0, width, height,null);
 }
}