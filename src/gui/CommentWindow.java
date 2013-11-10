package com.remmylife.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileFilter;

import com.remmylife.diary.Comment;
import com.remmylife.diary.Diary;
import com.remmylife.diary.User;

public class CommentWindow extends JFrame implements MouseListener {
	
	private Diary diary;
	private User user;
	
	private JFrame myFrame = new JFrame();
	private JPanel myPanel = new JPanel();
	private JPanel toolBar = new JPanel();
	private JPanelWithBg CenterPanel = new JPanelWithBg("img\\aa.jpg",400,400);
	private JPanel editCommentPanel = new JPanel();
		
	private Box  allComment = Box.createVerticalBox();
	
	
	private JScrollPane scrollComment = null;
	
	private JLabel title = new JLabel("看看大家都说什么了~");
	
	
	private JTextArea newComment = new JTextArea();
	private JScrollPane scrollNewComent = null;
	private JButton addComment = new JButton("评论");
	private ArrayList<Comment> commentList = new ArrayList<Comment>();
	private int isEmpty = 0;
	
	public  CommentWindow() {
		this.init();
	}
	
	public  CommentWindow(Diary diary,User user) {
		this.diary= diary;
		this.user = user;
		this.commentList = ManagerFactory.getManager().getComment(diary);
		this.init();
	}

	
	private void init() {

		myFrame.setVisible(false);
		
		title.setLayout(null);
		title.setFont(new Font("幼圆",Font.BOLD,18));
		title.setForeground(Color.white);
		toolBar.setBackground(Color.BLACK);
		toolBar.add(title);
		title.setLocation(30, 20);
		

		if(commentList.size() == 0){
			CommentPanel commentPanel = new CommentPanel("img\\comment.png",400,100);
			allComment.add(commentPanel);
		}else{
			isEmpty = 1;
			for(int i =0;i<commentList.size();i++){
				CommentPanel commentPanel = new CommentPanel("img\\comment.png",400,100,commentList.get(i));
				allComment.add(commentPanel);
			}
		}
		
		scrollComment = new JScrollPane(allComment);
		scrollComment.setPreferredSize(new Dimension(400, 390));
		scrollComment.setOpaque(false);
		scrollComment.getViewport().setOpaque(false);
		scrollComment.setBorder(BorderFactory.createEmptyBorder());
		
		CenterPanel.add(scrollComment);
		
		newComment.setRows(2);
		newComment.setColumns(30);
		newComment.setAutoscrolls(true);
		newComment.setLineWrap(true);
		newComment.setBorder(BorderFactory.createEmptyBorder());
		//newComment.setOpaque(false);
		newComment.setBackground(new Color(255,255,255));
		newComment.setFont(new Font("楷体",Font.BOLD,16));
		newComment.setForeground(new Color(0,0,0));
		//newComment.setEditable(false);
		
		scrollNewComent = new JScrollPane(newComment);
		scrollNewComent.setPreferredSize(new Dimension(280, 80));
		scrollNewComent.setOpaque(false);
		scrollNewComent.getViewport().setOpaque(false);
		scrollNewComent.setBorder(BorderFactory.createEmptyBorder());
		
		addComment.setPreferredSize(new Dimension(100, 80));
		
		addComment.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				String myComment = newComment.getText();
				if(myComment.equals("") || myComment == null){
					alert("您还什么都没写呢.");
					return;
				}else{
					ManagerFactory.getManager().saveComment(diary, user, myComment);
					if(isEmpty == 0){
						allComment.removeAll();
						isEmpty = 1;
					}
					newComment.setText("");
					
					CommentPanel commentPanel = new CommentPanel("img\\null.png",400,100,user.getNickName(),myComment);
					allComment.add(commentPanel);
					allComment.revalidate();
					
					
				}
				setItsVisible(false);
				setItsVisible(true);
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
			
		});
		
		editCommentPanel.setSize(400, 100);
		editCommentPanel.setBackground(Color.black);
		editCommentPanel.add(scrollNewComent);
		editCommentPanel.add(addComment);
		
		toolBar.setPreferredSize(new Dimension(400, 45));
		CenterPanel.setPreferredSize(new Dimension(400, 400));
		editCommentPanel.setPreferredSize(new Dimension(400, 100));
		
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		myPanel.add(toolBar);
		myPanel.add(CenterPanel);
		myPanel.add(editCommentPanel);
		myFrame.add(myPanel);
		
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setSize(400,550);
		myFrame.setTitle("Comments");
		myFrame.pack();
	}
	
	public static void main(String[] args)
	{
		CommentWindow cw = new CommentWindow();
		cw.setItsVisible(true);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
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
	
	public void setItsVisible(boolean b) {
		myFrame.setVisible(b);		
	}
	
	public boolean getItsVisible() {
		return myFrame.isVisible();		
	}
	
	private void alert(String message)
	{
		JOptionPane.showMessageDialog(this, message, "提示", JOptionPane.ERROR_MESSAGE);
	}
	
	
	public class CommentPanel extends JPanelWithBg{

		//private JLabel name = new JLabel();
		//private JLabel content = new JLabel();
		
		private JTextField name = new JTextField(8);
		private JTextArea content = new JTextArea();
		private JScrollPane scrollComment = null;
		private Comment comment = new Comment();
		private User user = new User();
		private String username,commenttext;
		public CommentPanel(String imgpath, int width, int height, Comment comment) {
			super(imgpath, width, height);
			this.comment = comment;
			this.init(1);
		}
		public CommentPanel(String imgpath, int width, int height) {
			super(imgpath, width, height);
			this.init(0);
		}
		
		public CommentPanel(String imgpath, int width, int height,String username,String commenttext) {
			super(imgpath, width, height);
			this.username = username;
			this.commenttext = commenttext;
			this.init(2);
		}
		private void init(int k) {
			if(k==1){
				user.setUserID(comment.getUserid());
				user = ManagerFactory.getManager().getUser(user);
				name.setText(user.getNickName());
				content.setText(comment.getContent());
			}else if(k==0){
				name.setText("");
				content.setText("还没有任何评论哦！");
			}else if(k==2){
				name.setText(this.username);
				content.setText(this.commenttext);
			}
			name.setEditable(false);
			//name.setBorder(new MatteBorder(2, 2,2, 2, new Color(50,105,50)));
			name.setFont(new Font("楷体",Font.BOLD,18));
			name.setForeground(new Color(255,255,255));
			name.setOpaque(false);
			name.setBorder(BorderFactory.createEmptyBorder());
			
			content.setRows(2);
			content.setColumns(30);
			content.setAutoscrolls(true);
			content.setLineWrap(true);
			content.setBorder(BorderFactory.createEmptyBorder());
			content.setOpaque(false);
			content.setBackground(new Color(135,197,111));
			content.setFont(new Font("楷体",Font.BOLD,16));
			content.setForeground(new Color(255,255,255));
			content.setEditable(false);
			
			//name.setText("liyi:");
			//content.setText("不要熬夜了啊不要熬夜了啊不要熬夜了啊不要熬夜了啊不要熬夜了啊不要熬夜了啊不要熬夜了啊不要熬夜了啊");
			
			scrollComment = new JScrollPane(content);
			scrollComment.setPreferredSize(new Dimension(280, 80));
			scrollComment.setOpaque(false);
			scrollComment.getViewport().setOpaque(false);
			scrollComment.setBorder(BorderFactory.createEmptyBorder());
			
			
			
			this.add(name);
			this.add(scrollComment);			
			
			name.setBounds(0, 0, 80, 30);
			scrollComment.setBounds(100, 0, 280, 80);
		}
		
	
	}
	
}
