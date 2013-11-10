package com.remmylife.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Date;

import com.remmylife.diary.*;

public class DiaryListWindow extends BGPanel implements MouseListener
{
	private User user = null;
	private boolean self = true;
	
	ImageIcon headIcon = new ImageIcon("img/head.png");
	ImageIcon writeIcon = new ImageIcon("img/write.png");
	ImageIcon searchIcon = new ImageIcon("img/search.png");
	ImageIcon sortIcon = new ImageIcon("img/sort.png");
	
	JLabel headIconLabel = new JLabel(headIcon);
	JLabel writeIconLabel = new JLabel(writeIcon);
	JLabel searchIconLabel = new JLabel(searchIcon);
	JLabel sortIconLabel = new JLabel(sortIcon);
	JLabel nameLabel = new JLabel();
	JLabel writeLabel = new JLabel("写日志");
	JLabel searchLabel = new JLabel("查找");
	JLabel sortLabel = new JLabel("排序");
	JLabel ownDiaryLabel = new JLabel("自己的日志");
	JLabel othersDiaryLabel = new JLabel("朋友的日志");
	
	SearchWindow searchWindow = new SearchWindow(this);
	
	Box box = Box.createVerticalBox();
	ArrayList<ListUnit> unitList = new ArrayList<ListUnit>();
	
	public DiaryListWindow(User user)
	{
		super();
		this.user = user;
		init();
	}
	
	private void init()
	{	
		this.setPreferredSize(new Dimension(MainWindow.WIDTH, MainWindow.HEIGHT));
		this.setBackground("img/list.jpg");
		this.setLayout(null);
		
		headIconLabel.setBounds(20, 90, 80, 80);
		headIconLabel.addMouseListener(this);
		this.add(headIconLabel);
		nameLabel.setBounds(40, 160, 80, 30);
		nameLabel.setText(user.getNickName());
		this.add(nameLabel);
		
		writeIconLabel.setBounds(20, 210, 80, 80);
		writeIconLabel.addMouseListener(this);
		this.add(writeIconLabel);
		writeLabel.setBounds(40, 270, 80, 30);
		this.add(writeLabel);
		
		searchIconLabel.setBounds(20, 320, 80, 80);
		searchIconLabel.addMouseListener(this);
		this.add(searchIconLabel);
		searchLabel.setBounds(40, 390, 80, 30);
		this.add(searchLabel);
		
		sortIconLabel.setBounds(20, 440, 80, 80);
		sortIconLabel.addMouseListener(this);
		this.add(sortIconLabel);
		sortLabel.setBounds(40, 510, 80, 30);
		this.add(sortLabel);
		/*
		ListUnit unit1 = new ListUnit(com.remmylife.head.DiaryType.IMAGE_DIARY);
		ListUnit unit2 = new ListUnit(com.remmylife.head.DiaryType.TEXT_DIARY);
		ListUnit unit3 = new ListUnit(com.remmylife.head.DiaryType.VOICE_DIARY);
		ListUnit unit4 = new ListUnit(com.remmylife.head.DiaryType.IMAGE_DIARY);
		ListUnit unit5 = new ListUnit(com.remmylife.head.DiaryType.IMAGE_DIARY);
		ListUnit unit6 = new ListUnit(com.remmylife.head.DiaryType.IMAGE_DIARY);
		ListUnit unit7 = new ListUnit(com.remmylife.head.DiaryType.IMAGE_DIARY);
		ListUnit unit8 = new ListUnit(com.remmylife.head.DiaryType.IMAGE_DIARY);
		ListUnit unit9 = new ListUnit(com.remmylife.head.DiaryType.IMAGE_DIARY);
		ListUnit unit10 = new ListUnit(com.remmylife.head.DiaryType.IMAGE_DIARY);
		ListUnit unit11 = new ListUnit(com.remmylife.head.DiaryType.IMAGE_DIARY);
		ListUnit unit12 = new ListUnit(com.remmylife.head.DiaryType.IMAGE_DIARY);
		*/
		
		/*
		box.add(unit1);
		box.add(unit2);
		box.add(unit3);
		box.add(unit4);
		box.add(unit5);
		box.add(unit6);
		box.add(unit7);
		box.add(unit8);
		box.add(unit9);
		box.add(unit10);
		box.add(unit11);
		box.add(unit12);
		*/
		
		JScrollPane unitPane = new JScrollPane(box);
		unitPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		unitPane.setOpaque(false);
	    unitPane.getViewport().setOpaque(false);
	    unitPane.setBorder(BorderFactory.createEmptyBorder());
	    unitPane.setBounds(130, 60, 265, 480);
		this.add(unitPane);
		ownDiaryLabel.setBounds(130, 30, 80, 30);
		ownDiaryLabel.addMouseListener(this);
		othersDiaryLabel.setBounds(220, 30, 80, 30);
		othersDiaryLabel.addMouseListener(this);
		this.add(ownDiaryLabel);
		this.add(othersDiaryLabel);
		
		ArrayList<Diary> diaryList = ManagerFactory.getManager().getDiaryList(user, self);
		setDiaryList(diaryList);
	}
	
	public void setDiaryList(ArrayList<Diary> diaryList)
	{
		unitList.clear();
		for(Diary diary : diaryList)
		{
			ListUnit listUnit = new ListUnit(diary);
			unitList.add(listUnit);
		}
		box.removeAll();
		box.revalidate();
		for(ListUnit listUnit : unitList)
		{
			box.add(listUnit);
		}
		box.revalidate();
	}
	
	public User getUser()
	{
		return user;
	}
	
	public boolean isSelf()
	{
		return self;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		Object o = e.getSource();
		if((JLabel)o == searchIconLabel)
		{
			searchWindow.setVisible(true);
		}
		else if((JLabel)o == sortIconLabel)
		{
			ArrayList<Diary> diaryList = ManagerFactory.getManager().sortByDate(user, self);
			setDiaryList(diaryList);
		}
		else if((JLabel)o == ownDiaryLabel)
		{
			self = true;
			ownDiaryLabel.setForeground(Color.RED);
			othersDiaryLabel.setForeground(Color.BLACK);
			ArrayList<Diary> diaryList = ManagerFactory.getManager().getDiaryList(user, self);
			setDiaryList(diaryList);
		}
		else if((JLabel)o == othersDiaryLabel)
		{
			self = false;

			othersDiaryLabel.setForeground(Color.RED);
			ownDiaryLabel.setForeground(Color.BLACK);
			ArrayList<Diary> diaryList = ManagerFactory.getManager().getDiaryList(user, self);
			setDiaryList(diaryList);
		}
		else if((JLabel)o == writeIconLabel)
		{
			DiaryType diaryType = new DiaryTypeSelector().getDiaryType();
			if(diaryType == null)
			{
				return;
			}
			DiaryEdit diaryEdit =  new  DiaryEdit(user, diaryType);
			Container c = this;
			while(c.getParent() != null)
			{
				c = c.getParent();
			}
			MainWindow frame = (MainWindow)c;
			frame.setPanel(diaryEdit);
		}
		else if((JLabel)o == headIconLabel)
		{
			Container c = this;
			while(c.getParent() != null)
			{
				c = c.getParent();
			}
			MainWindow frame = (MainWindow)c;
			frame.setPanel(new AccountWindow(user));
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
	
	public class DiaryTypeSelector
	{
		JFrame frame = new JFrame("");
		JDialog dialog  = new JDialog(frame, "选择日记类型", true);
		JButton bn1 = new JButton("文本");
		JButton bn2 = new JButton("语音");
		JButton bn3 = new JButton("图片");
		DiaryType diaryType = DiaryType.TEXT_DIARY;
		public DiaryTypeSelector()
		{
			frame.setVisible(false);
			bn1.addActionListener(listener);
			bn2.addActionListener(listener);
			bn3.addActionListener(listener);
//			dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
			dialog.getContentPane().add(bn1, BorderLayout.CENTER);
			dialog.getContentPane().add(bn2, BorderLayout.NORTH);
			dialog.getContentPane().add(bn3, BorderLayout.SOUTH);
			dialog.setSize(135, 100);
			dialog.setVisible(true);
		}
		public DiaryType getDiaryType()
		{
			return diaryType;
		}
		ActionListener listener = new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(e.getActionCommand().equals("文本"))
				{
					diaryType = DiaryType.TEXT_DIARY;
				}
				else if(e.getActionCommand().equals("语音"))
				{
					diaryType = DiaryType.VOICE_DIARY;
				}
				else if(e.getActionCommand().equals("图片"))
				{
					diaryType = DiaryType.IMAGE_DIARY;
				}
				dialog.dispose();
				frame.dispose();
			}
		};
	}
}
