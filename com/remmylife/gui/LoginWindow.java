package com.remmylife.gui;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;

import com.remmylife.diary.*;

public class LoginWindow extends JFrame
{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 240;
	
	private JLabel userNameLabel = new JLabel("用户名: ");
	private JTextField userNameField = new JTextField(20);
	private JLabel passwordLabel = new JLabel("密   码: ");
	private JTextField passwordField = new JTextField(20);
	private JButton loginButton = new JButton("登陆");
	private JButton registerButton = new JButton("注册");
	
	public LoginWindow()
	{
		init();
	}
	
	private void init()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		BGPanel logoPanel = new BGPanel("img/login1.png");
		
		JPanel userNamePanel = new JPanel();
		userNamePanel.add(userNameLabel);
		userNamePanel.add(userNameField);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		
		BGPanel fieldPanel = new BGPanel("img/login2.png");
		fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
		fieldPanel.add(userNamePanel);
		fieldPanel.add(passwordPanel);
		
		BGPanel buttonPanel = new BGPanel("img/login3.png");
		LoginButtonListener loginButtonListener = new LoginButtonListener();
		loginButton.addActionListener(loginButtonListener);
		registerButton.addActionListener(loginButtonListener);
		buttonPanel.add(loginButton);
		buttonPanel.add(registerButton);
		
		logoPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT * 1 / 3));
		fieldPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT * 1 / 3));
		buttonPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT * 1 / 3));
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.add(logoPanel);
		this.add(fieldPanel);
		this.add(buttonPanel);
		
		userNamePanel.setOpaque(false);
		passwordPanel.setOpaque(false);
		fieldPanel.setOpaque(false);
		buttonPanel.setOpaque(false);
		
		try
		{
			this.setIconImage(ImageIO.read(new File("img/logo-whole.png")));
		}
		catch(Exception e){}
		this.setTitle("Login");
		this.setSize(WIDTH, HEIGHT);
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize=this.getSize();
		int w=(screenSize.width-frameSize.width)/2;
		int h=(screenSize.height-frameSize.width)/2;
		setLocation(w,h);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setBackground(ImageIcon icon)
	{
		JLabel label = new JLabel(icon);
		label.setBounds(0, 0, WIDTH, HEIGHT);
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		JPanel panel = (JPanel)this.getContentPane();
		panel.setOpaque(false);
	}
	
	private void alert(String message)
	{
		JOptionPane.showMessageDialog(this, message, "提示", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void main(String[] args)
	{
		new LoginWindow();
	}
	
	class LoginButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton button = (JButton)e.getSource();
			if(button == loginButton)
			{
				String userName = userNameField.getText().trim();
				String password = passwordField.getText().trim();
				if(userName.equals(""))
				{
					alert("用户名不能为空");
				}
				
				else if(password.equals(""))
				{
					alert("密码不能为空");
				}
				
				else
				{
					if(ManagerFactory.getManager().loginByName(userName, password))
					{
						System.out.println("登陆成功");
						dispose();
						User user = new User();
						user.setUserName(userName);
						user = ManagerFactory.getManager().getUser(user);
						new MainWindow(new DiaryListWindow(user));
					}
					else
					{
						alert("用户名和密码不匹配");
					}
				}
			}
			
			else if(button == registerButton)
			{
				dispose();
				new MainWindow(new RegisterWindow());
			}
		}
	}
}
