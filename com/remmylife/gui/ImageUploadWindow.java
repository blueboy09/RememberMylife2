package com.remmylife.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class ImageUploadWindow extends JFrame implements MouseListener {
	
	private JFrame myFrame = new JFrame();
	private JPanel myPanel = new JPanel();
	private JPanelWithBg toolBar = new JPanelWithBg("img\\bar1.png",400,50);
	private JPanelWithBg ImagePanel = new JPanelWithBg("img\\imageupload.jpg",400,400);
	private JPanelWithBg saveBar = new JPanelWithBg("img\\bar2.png",400,50);
	
	private JFileChooser fileChooser = new JFileChooser();
	private PhotoFilter photoFilter = null;
	private ImageButton uploadButton =new ImageButton("img\\upload.png");
	
	
	private Box allbox = Box.createVerticalBox();
	private Box box[] = null;
	private int rows = 0,cols = 0;
	
	private JScrollPane scrollImage = null;
	
	private ImageButton save = new ImageButton("img\\save.png");
	private ImageButton cancel = new ImageButton("img\\cancel.png");
	
	private ArrayList<BufferedImage> imageList = new ArrayList<BufferedImage>();
	
	public  ImageUploadWindow() {
		this.init();
	}

	private void init() {
		
		photoFilter = new PhotoFilter();
		photoFilter.addExtension(".jpg");
		photoFilter.addExtension(".jpeg");
		//photoFilter.addExtension("png");
		//photoFilter.addExtension("gif");
		photoFilter.setDescription("图片文件(*.jpg,*.jpeg)");
		fileChooser.setFileFilter(photoFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setMultiSelectionEnabled(true);
		
		toolBar.add(uploadButton);
		uploadButton.addMouseListener(this);
		
		box =  new Box[10];
		for(int i=0;i<=9;i++)
			box[i] = new Box(BoxLayout.LINE_AXIS);
		
		//ImageLabel image1 = new ImageLabel("img//head.png","我爱北京天安门");
		//box[0].add(image1);
		allbox.add(box[0]);
		
		scrollImage = new JScrollPane(allbox);
		scrollImage.setPreferredSize(new Dimension(400, 390));
		scrollImage.setOpaque(false);
		scrollImage.getViewport().setOpaque(false);
		scrollImage.setBorder(BorderFactory.createEmptyBorder());
		ImagePanel.add(scrollImage);
		
		save.addMouseListener(this);
		cancel.addMouseListener(this);
		saveBar.setLayout(new FlowLayout(FlowLayout.CENTER, 30,5));
		saveBar.setOpaque(false);
		saveBar.add(save);
		saveBar.add(cancel);
		
		toolBar.setPreferredSize(new Dimension(400, 45));
		ImagePanel.setPreferredSize(new Dimension(400, 400));
		saveBar.setPreferredSize(new Dimension(400, 50));
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		myPanel.add(toolBar);
		myPanel.add(ImagePanel);
		myPanel.add(saveBar);
		myFrame.add(myPanel);
		
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setSize(400,500);
		myFrame.setTitle("ImageUpload");
		myFrame.pack();
		myFrame.setVisible(false);
	}
	
	
	class PhotoFilter extends FileFilter
	{
		ArrayList<String> extensions = new ArrayList<String>();
		String description = "";
		
		public void setDescription(String description)
		{
			this.description = description;
		}
		
		public String getDescription()
		{
			return description;
		}
		
		public void addExtension(String suffix)
		{
			if(!suffix.startsWith("."))
			{
				suffix = "." + suffix;
			}
			extensions.add(suffix.toLowerCase());
		}
		
		public boolean accept(File f)
		{
			if(f.isDirectory())
			{
				return true;
			}
			
			String name = f.getName().toLowerCase();
			for(String extension : extensions)
			{
				if(name.endsWith(extension))
				{
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if(e.getSource() == uploadButton){
			int result = fileChooser.showDialog(this, "选择图片");
			if(result == JFileChooser.APPROVE_OPTION)
			{
				File[] files = fileChooser.getSelectedFiles();
				for(File file : files)
				{
					addPhoto(file);
				}
			}
		}else if(e.getSource() == save){
			myFrame.setVisible(false);
		}else if(e.getSource() == cancel){
			imageList.clear();
			myFrame.setVisible(false);
		}
		
	}

	public void addPhoto(File file)
	{
		BufferedImage image = null;
		try
		{
		    image = ImageIO.read(new FileInputStream(file.getAbsolutePath()));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		addPhoto(image);
	}
	
	public void addPhoto(BufferedImage image)
	{
		if(image != null)
		{
			imageList.add(image);
			System.out.println(image.toString());
			ImageIcon imageIcon = new ImageIcon(image);
			ImageLabel photoLabel = new ImageLabel(imageIcon,120,120);
			
			if(cols % 3 == 0){				
				box[rows].add(photoLabel);				
				rows ++;
				cols++;
			}else{
				box[rows-1].add(photoLabel);
				cols++;
			}
			
			if((cols + 1)%3 == 0){
				allbox.add(box[rows]);
			}
			ImagePanel.revalidate();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<BufferedImage> getImageList(){
		return imageList;
	}

	public void setItsVisible(boolean b) {
		myFrame.setVisible(b);
		
	}
	
}
