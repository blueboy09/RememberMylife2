package com.remmylife.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;

public class ImageLabel extends JLabel {

	public ImageLabel(String img) {
        this(new ImageIcon(img));
    }
    
    public ImageLabel(String img,String note) {
        this(getImageIcon(img,120,120));
    }	
    public ImageLabel(ImageIcon icon) {
        setIcon(icon);
        //setMargin(new Insets(0,0,0,0));
        setIconTextGap(0);
        setBorder(new MatteBorder(5, 5,5, 5, new Color(0,0,200)));
        setBorder(null);
        setText(null);
        setSize(icon.getImage().getWidth(null),icon.getImage().getHeight(null));
       
    }
    
    public ImageLabel(ImageIcon icon,int width, int height){
    	icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
    	setIcon(icon);
        setIconTextGap(0);
        setBorder(new MatteBorder(2, 2,2,2, new Color(255,255,255)));        
        setSize(icon.getImage().getWidth(null),icon.getImage().getHeight(null));        
    }
    
    public ImageLabel(ImageIcon icon,String note) {
        setIcon(icon);
        //setMargin(new Insets(0,0,0,0));
        setIconTextGap(0);
        //setBorderPainted(false);
        setBorder(new MatteBorder(5, 5,5, 5, new Color(0,0,200)));
        setText(note);
        setSize(icon.getImage().getWidth(null),icon.getImage().getHeight(null));
       
    }
    
    public static  ImageIcon getImageIcon(String path, int width, int height) {
    	  if (width == 0 || height == 0) {
    	   return new ImageIcon(path);
    	  }
    	  ImageIcon icon = new ImageIcon(path);
    	  icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
    	  return icon;
    }

}
