package com.remmylife.gui;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.remmylife.diary.*;
import com.remmylife.gui.tools.Recorder;

import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.util.ArrayList;


public class DiaryDisplay extends JPanel implements MouseListener
{
	private Diary diary;
	private int width = MainWindow.WIDTH;
	private int height = MainWindow.HEIGHT;
	private ImageIcon background;
	private JTextField diaryTitle = new JTextField(36);
	private JTextArea diaryText = new JTextArea();
	private JLabel title = new JLabel();
	
	private JPanelWithBg northPanel = new JPanelWithBg("img//top1.png",400,141);
	private JPanelWithBg centerPanel = new JPanelWithBg("img//center1.png",400,215);
	private JPanelWithBg southPanel = new JPanelWithBg("img//bottom3.png",400,240);
	private JPanelWithBg footerPanel = new JPanelWithBg("img//footer.png",400,44);
	
	ImageLabel imageList[] = null;
	
	ImageLabel image = new ImageLabel("img//null.png","我爱北京天安门");
	
	private Box allbox = Box.createVerticalBox();
	private Box box[] = null;
	private JScrollPane scrollText = null;
	private JScrollPane scrollImage = null;
	
	private ImageButton play=new ImageButton("img//play.png");
	private ImageButton pause=new ImageButton("img//pause1.png");
	
	private ImageButton back=new ImageButton("img//back.png");
	private ImageButton comment=new ImageButton("img//right.png");
	
	private CommentWindow commentPlay = null; 
	private boolean isCommentPlayShow = false;

	DiaryDisplay(Diary diary)
	{
		this.diary = diary;
		this.init();
	}
	
	private void init()
	{
		this.setSize(width, height);
		diaryTitle.setEditable(false);
		diaryTitle.setBorder(new MatteBorder(2, 2,2, 2, new Color(50,105,50)));
		diaryTitle.setFont(new Font("楷体",Font.BOLD,18));
		diaryTitle.setOpaque(false);
		diaryTitle.setBorder(BorderFactory.createEmptyBorder());
		if(diary != null){
			String date = diary.getCreateDate().toString();
			//String weather = diary.getWeather().toString();
			title.setText("    "+date);
			diaryTitle.setText(diary.getTitle());
		}else{
			title.setText("      2013年10月14日  星期一  14:20\r\n");
			diaryTitle.setText("无题");
		}
		title.setFont(new Font("楷体",Font.BOLD,15));
		title.setForeground(new Color(50,50,255));
		northPanel.setLayout(new BorderLayout());
		northPanel.add(title,BorderLayout.SOUTH);
		
		diaryText.setRows(8);
		diaryText.setColumns(40);
		diaryText.setAutoscrolls(true);
		diaryText.setLineWrap(true);
		diaryText.setBorder(BorderFactory.createEmptyBorder());
		diaryText.setOpaque(false);
		diaryText.setBackground(new Color(135,197,111));
		diaryText.setFont(new Font("楷体",Font.BOLD,16));
		diaryText.setForeground(new Color(50,50,50));
		diaryText.setEditable(false);
		if(diary == null){			
			diaryText.setText("他很懒，什么都没有写！！！");			
		}
		
		scrollText = new JScrollPane(diaryText);
		scrollText.setBorder(BorderFactory.createEmptyBorder());
		scrollText.setOpaque(false);
		scrollText.getViewport().setOpaque(false);
		centerPanel.add(diaryTitle);
		centerPanel.add(scrollText);
		if(diary != null){
			if(diary.getType() == DiaryType.IMAGE_DIARY){
				
				diaryText.setText(((ImageDiary)diary).getNote());			
				
				byte[] images = ((ImageDiary)diary).getImages();
				
				ArrayList<BufferedImage> list = null;
				if(images != null)
					list = ImageStorage.getImages(images);
				
				
				if(list !=null && list.size() > 0){
					imageList = new ImageLabel[list.size()];
					for(int i=0;i<list.size();i++){
						ImageIcon imageIcon = new ImageIcon(list.get(i));
						imageList[i] = new ImageLabel(imageIcon,120,120);				
					}					
					int rows = imageList.length/3+1;
					box =  new Box[rows];
					for(int j = 0;j< rows;j++){
						box[j] =  new Box(BoxLayout.LINE_AXIS);
						for(int i=j*3;i<(j+1)*3;i++){
							if(i<imageList.length)
								box[j].add(imageList[i]);
							else
								box[j].add(image);
						}
					}
					
					for(int j = 0;j< rows;j++){
						allbox.add(box[j]);
					}
				}
				scrollImage = new JScrollPane(allbox);
				scrollImage.setPreferredSize(new Dimension(380, 235));
				scrollImage.setOpaque(false);
				scrollImage.getViewport().setOpaque(false);
				
				southPanel.add(scrollImage);
				
			}else if(diary.getType() == DiaryType.VOICE_DIARY){
				diaryText.setText(((VoiceDiary)diary).getNote());
				play.addMouseListener(this);
				southPanel.add(play);	
			}else if(diary.getType() == DiaryType.TEXT_DIARY){
				diaryText.setText(((TextDiary)diary).getText());				
			}
		}
		back.addMouseListener(this);
		comment.addMouseListener(this);
		footerPanel.add(comment);
		footerPanel.add(back);	
			
        northPanel.setPreferredSize(new Dimension(width, 141));
        centerPanel.setPreferredSize(new Dimension(width, 215));
        southPanel.setPreferredSize(new Dimension(width, 240));
        footerPanel.setPreferredSize(new Dimension(width, 44));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
    	this.add(northPanel);
		this.add(centerPanel);
        this.add(southPanel); 
        this.add(footerPanel); 
        
       
        
        if(diary != null){
        	User user = new User();
            user.setUserID(diary.getUserid());
            user = ManagerFactory.getManager().getUser(user);
        	commentPlay = new CommentWindow(diary,user);
        }
        else commentPlay = new CommentWindow();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == play){			
			Recorder recorder = new Recorder();
			recorder.playRecord(((VoiceDiary)diary).getVoice());
		}else if(e.getSource() == comment){
			commentPlay.setItsVisible(!commentPlay.getItsVisible());
		}else if(e.getSource() == back){
			Container c = this;
			while(c.getParent() != null)
			{
				c = c.getParent();
			}
			MainWindow frame = (MainWindow)c;
			User user = new User();
            user.setUserID(diary.getUserid());
            user = ManagerFactory.getManager().getUser(user);
			frame.setPanel(new DiaryListWindow(user));
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
	
}
