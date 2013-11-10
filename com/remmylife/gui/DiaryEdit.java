package com.remmylife.gui;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.JTextComponent;

import com.remmylife.diary.*;

import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DiaryEdit extends JPanel implements MouseListener
{
	private User user = null;
	
	private int width = 400;
	private int height = 640;
	private Diary diary = null;
	private DiaryType type;
	private byte[] images;
	private byte[] voices;
	private ImageIcon background;
	
	private JTextField diaryTitle = new JTextField(36);
	private JTextArea diaryText = new JTextArea();    
	
	private JPanelWithBg northPanel = new JPanelWithBg("img//top.png",400,167);
	private JPanelWithBg centerPanel = new JPanelWithBg("img//center.png",400,270);
	private JPanelWithBg southPanel = new JPanelWithBg("img//bottom.png",400,203);
	
	private ImageButton pic=new ImageButton("img//pic.png");
	private ImageButton voice=new ImageButton("img//voice.png");
	private ImageButton video=new ImageButton("img//video.png");
	private ImageButton post=new ImageButton("img//post.png");
	private ImageButton back=new ImageButton("img//logo.png");
	ImageUploadWindow  iu = null;
	VoiceRecordWindow  vr = null;
	
	DiaryEdit(User user, DiaryType type)
	{
		this.user = user;
		this.type = type;
		this.init();
	}
	
	DiaryEdit(Diary diary,User user, DiaryType type)
	{
		this.diary = diary;
		this.user = user;
		this.type = type;
		this.init();
	}
	
	private void init()
	{
		this.setSize(width, height);
		diaryTitle.setBorder(new MatteBorder(2, 2,2, 2, new Color(50,105,50)));
		diaryTitle.setFont(new Font("幼圆",Font.BOLD,18));
		diaryTitle.setOpaque(false);
		//this.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));			
		diaryText.setRows(10);
		diaryText.setColumns(40);
		diaryText.setAutoscrolls(true);
		diaryText.setLineWrap(true);
		diaryText.setBorder(new MatteBorder(2, 2,2, 2, new Color(50,105,50)));
		diaryText.setOpaque(false);
		diaryText.setBackground(new Color(135,197,111));	
		diaryText.setFont(new Font("幼圆",Font.BOLD,16));
		diaryText.setForeground(new Color(0,0,0));
		JScrollPane scrollText = new JScrollPane(diaryText);
		scrollText.setOpaque(false);
		scrollText.getViewport().setOpaque(false);
		centerPanel.add(diaryTitle);
		centerPanel.add(scrollText);
		
		southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30,5));
		pic.setText("图片");
		voice.setText("语音");
		video.setText("视频");
		post.setText("发布");
		back.setText("返回");
		
		pic.addMouseListener(this);
		voice.addMouseListener(this);
		video.addMouseListener(this);
		post.addMouseListener(this);
		back.addMouseListener(this);
		
		switch(type){
			case TEXT_DIARY:				
				break;
			case IMAGE_DIARY:
				southPanel.add(pic);
				break;
			case VOICE_DIARY:
				southPanel.add(voice);
				break;
			default:
				break;
		}
		southPanel.add(post);
		southPanel.add(back);
		
        northPanel.setPreferredSize(new Dimension(width, 167));
        centerPanel.setPreferredSize(new Dimension(width, 270));
        southPanel.setPreferredSize(new Dimension(width, 203));
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
    	this.add(northPanel);
		this.add(centerPanel);
        this.add(southPanel); 

		iu = new ImageUploadWindow();	
		vr = new VoiceRecordWindow();
		
	}

	/*
	protected void paintComponent(Graphics g) {   
		try {
			BufferedImage img=ImageIO.read(new File(this.getClass().getResource("/bg.png").getPath()));
			//g.drawImage(img, 0, 0, 400, 640,null);
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	 */

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == pic){
			iu.setLocation(this.getLocation().x+this.width, this.getLocation().y);
			iu.setItsVisible(true);
		}else if(e.getSource() == voice){
			vr.setLocation(this.getLocation().x+this.width, this.getLocation().y);
			vr.setItsVisible(true);
		}else if(e.getSource() == back){
			Container c = this;
			while(c.getParent() != null)
			{
				c = c.getParent();
			}
			MainWindow frame = (MainWindow)c;
			frame.setPanel(new DiaryListWindow(user));
		}else if(e.getSource() == post){
			String title= diaryTitle.getText();
			if(title.equals("") || title == null){
				 alert("标题不能为空");
				 return;
			}else if(title.length() > 20){
				 alert("标题不能超过20个字符");
				 return;
			}			
			
			switch(type){
				case TEXT_DIARY:
					TextDiary newDiary = new TextDiary();
					Date date = new Date();
					newDiary.setCreateDate(date);
					newDiary.setLastSaveDate(date);
					newDiary.setWeather(Weather.SUNNY);
					newDiary.setType(DiaryType.TEXT_DIARY);
					newDiary.setTitle(diaryTitle.getText());
					newDiary.setText(diaryText.getText());
					newDiary.setUserid(user.getUserID());
					ManagerFactory.getManager().saveDiary(newDiary);
					break;
				case IMAGE_DIARY:
					
					if(iu.getImageList().size() > 0){
						images = ImageStorage.getDataInBytes(iu.getImageList());
						if(images == null)
							System.out.println("null");
						else
							System.out.println("hah");
					}
					iu.dispose();					
					
					ImageDiary newDiary1 = new ImageDiary(images, diaryText.getText());
					newDiary1.setTitle(diaryTitle.getText());
					Date date1 = new Date();
					newDiary1.setCreateDate(date1);
					newDiary1.setLastSaveDate(date1);
					newDiary1.setWeather(Weather.SUNNY);
					newDiary1.setType(DiaryType.IMAGE_DIARY);
					newDiary1.setUserid(user.getUserID());
					ManagerFactory.getManager().saveDiary(newDiary1);
					System.out.println(images.toString());
					break;
				case VOICE_DIARY:
					voices = vr.getAudioData();
					VoiceDiary newDiary2 = new VoiceDiary();
					newDiary2.setTitle(diaryTitle.getText());
					newDiary2.setNote(diaryText.getText());
					Date date2 = new Date();
					newDiary2.setCreateDate(date2);
					newDiary2.setLastSaveDate(date2);
					newDiary2.setWeather(Weather.SUNNY);
					newDiary2.setType(DiaryType.VOICE_DIARY);
					newDiary2.setVoice(voices);
					newDiary2.setUserid(user.getUserID());
				    ManagerFactory.getManager().saveDiary(newDiary2);
					break;
				default:
					break;				
			
			}
			Container c = this;
			while(c.getParent() != null)
			{
				c = c.getParent();
			}
			MainWindow frame = (MainWindow)c;
			frame.setPanel(new DiaryListWindow(user));
		}
		
	}

	public static String GetNowDate(){   
	    String temp_str="";   
	    Date dt = new Date();   
	    //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制   
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");   
	    temp_str=sdf.format(dt);   
	    return temp_str;   
	}
	
	private void alert(String message)
	{
		JOptionPane.showMessageDialog(this, message, "提示", JOptionPane.ERROR_MESSAGE);
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
	
}
