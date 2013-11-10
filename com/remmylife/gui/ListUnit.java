package com.remmylife.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

import java.util.Date;
import java.text.SimpleDateFormat;

import com.remmylife.diary.*;
import com.remmylife.gui.tools.BufferedImageBytes;

public class ListUnit extends BGPanel implements MouseListener
{
	Diary diary = null;
	User user = null;
	
	ImageIcon headIcon = new ImageIcon("img/smallhead.png");
	ImageIcon backgroundIcon = new ImageIcon("img/listunit.png");
	ImageIcon shareIcon = new ImageIcon("img/share.png");
	ImageIcon viewIcon = new ImageIcon("img/view.png");
	ImageIcon textIcon = new ImageIcon("img/text.png");
	ImageIcon voiceIcon = new ImageIcon("img/voice.png");
	ImageIcon imageIcon = new ImageIcon("img/image.png");
	
	JLabel backgroundIconLabel = new JLabel(backgroundIcon);
	JLabel shareIconLabel = new JLabel(shareIcon);
	JLabel viewIconLabel = new JLabel(viewIcon);
	JLabel shareLabel = new JLabel("分享");
	JLabel viewLabel = new JLabel("查看");
	JLabel textIconLabel = new JLabel(textIcon);
	JLabel voiceIconLabel = new JLabel(voiceIcon);
	JLabel imageIconLabel = new JLabel(imageIcon); 
	JLabel headIconLabel = new JLabel();
	JLabel nameLabel = new JLabel();
	JLabel contentLabel = new JLabel();
	JLabel dateLabel = new JLabel();
	
	public ListUnit(Diary diary)
	{
		super();
		this.diary = diary;
		init();
	}
	
	private void init()
	{
		int userId = diary.getUserid();
		user = new User();
		user.setUserID(userId);
		user = ManagerFactory.getManager().getUser(user);
		
		this.setPreferredSize(new Dimension(270, 80));
		this.setLayout(null);
		
		shareIconLabel.setBounds(160, 58, 20, 20);
		shareIconLabel.addMouseListener(this);
		this.add(shareIconLabel);
		shareLabel.setBounds(180, 54, 30, 30);
		this.add(shareLabel);
		if(diary.getUserid() != user.getUserID())
		{
			shareLabel.setVisible(false);
			shareIconLabel.setVisible(false);
		}
		
		viewIconLabel.setBounds(200, 58, 20, 20);
		viewIconLabel.addMouseListener(this);
		this.add(viewIconLabel);
		viewLabel.setBounds(220, 54, 30, 30);
		this.add(viewLabel);
		
		if(user.getHeadportrait() != null)
		{
			BufferedImage bImage = BufferedImageBytes.bytes2BufferedImage(user.getHeadportrait());
//			headIcon = new ImageIcon(bImage);
			headIcon = new ImageIcon("img/smallhead.png");
		}
		headIconLabel = new JLabel(headIcon);
		headIconLabel.setBounds(0, 5, 40, 40);
		this.add(headIconLabel);
		nameLabel.setText(user.getNickName());
		nameLabel.setBounds(40, 15, 50, 30);
		this.add(nameLabel);
		
		switch(diary.getType())
		{
			case TEXT_DIARY:
				textIconLabel.setBounds(0, 40, 40, 40);
		        this.add(textIconLabel);
		        break;
			case VOICE_DIARY:
				voiceIconLabel.setBounds(0, 40, 40, 40);
				this.add(voiceIconLabel);
				break;
			case IMAGE_DIARY:
				imageIconLabel.setBounds(0, 40, 40, 40);
				this.add(imageIconLabel);
				break;
		}
		
		contentLabel.setBounds(40, 35, 180, 30);
		contentLabel.setText(diary.getTitle());
		this.add(contentLabel);
		
		dateLabel.setBounds(40, 54, 150, 30);
		dateLabel.setText(getDateString(diary.getCreateDate()));
		this.add(dateLabel);
		
		backgroundIconLabel.setBounds(0, 20, 250, 60);
		add(backgroundIconLabel);

		this.setOpaque(false);
	}
	
	public Diary getDiary()
	{
		return diary;
	}
	
	public String getDateString(Date date)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	private void alert(String message)
	{
		JOptionPane.showMessageDialog(this, message, "提示", JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		Object o = e.getSource();
		if((JLabel)o == shareIconLabel)
		{
			diary = ManagerFactory.getManager().getDiary(diary);
			diary.setShared(true);
			ManagerFactory.getManager().saveDiary(diary);
		}
		
		if((JLabel)o == viewIconLabel)
		{
			diary = ManagerFactory.getManager().getDiary(diary);
			DiaryDisplay diaryDisplay = new DiaryDisplay(diary);
			Container c = this;
			while(c.getParent() != null)
			{
				c = c.getParent();
			}
			MainWindow frame = (MainWindow)c;
			frame.setPanel(diaryDisplay);
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
		((Component)(e.getSource())).setCursor(
				Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
	}
}
