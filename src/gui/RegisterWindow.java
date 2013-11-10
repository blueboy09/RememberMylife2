package com.remmylife.gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.regex.*;
import java.util.Date;
import java.util.Calendar;
import java.io.File;

import com.remmylife.diary.*;
import com.remmylife.gui.tools.*;

public class RegisterWindow extends BGPanel implements MouseListener
{
	User user = new User();
	String[] sexes = new String[]{"男", "女"};
	BufferedImage headImage = BufferedImageBytes.getBufferedImage("img/head.png");
	
	JLabel headLabel = new JLabel(new ImageIcon(headImage));//头像
	JLabel setHeadLabel = new JLabel("头像设置");
	JLabel userNameLabel = new JLabel("注册名（邮箱）：");
	JLabel nickNameLabel = new JLabel("昵      称：");
	JLabel pwdLabel = new JLabel("密      码：");
	JLabel confirmPwdLabel = new JLabel("确认密码：");
	JLabel sexLabel = new JLabel("性       别：");
	JLabel birthdayLabel = new JLabel("生      日：");
	JLabel ageLabel = new JLabel("年      龄：");
	JLabel constellationLabel = new JLabel("星      座：");
	JLabel confirmLabel = new JLabel(new ImageIcon("img/ok.png"));
	JLabel cancelLabel = new JLabel(new ImageIcon("img/giveup.png"));
	
	OpaqueTextField userNameText = new OpaqueTextField(20);
	OpaqueTextField nickNameText = new OpaqueTextField(20);
	OpaqueTextField pwdText = new OpaqueTextField(20);
	OpaqueTextField confirmPwdText = new OpaqueTextField(20);
	OpaqueTextField ageText = new OpaqueTextField(20);
	OpaqueTextField constellationText = new OpaqueTextField(20);
	
	OpaqueTextField yearText = new OpaqueTextField(10);
	OpaqueTextField monthText = new OpaqueTextField(10);
	OpaqueTextField dayText = new OpaqueTextField(10);
	JComboBox sexSelector = new JComboBox(sexes);
	
	JLabel yearLabel = new JLabel("年");
	JLabel monthLabel = new JLabel("月");
	JLabel dayLabel = new JLabel("日");
	
	public RegisterWindow()
	{
		super();
		init();
	}
	
	private void init()
	{
		this.setPreferredSize(new Dimension(MainWindow.WIDTH, MainWindow.HEIGHT));
		this.setBackground("img/account.jpg");
		this.setLayout(null);
		
		headLabel.setBounds(20, 40, 80, 80);
		this.add(headLabel);
		setHeadLabel.setBounds(35, 120, 80, 20);
		this.add(setHeadLabel);
		
		userNameLabel.setBounds(35, 180, 110, 20);
		this.add(userNameLabel);
		userNameText.setBounds(145, 180, 200, 20);
		this.add(userNameText);
		
		nickNameLabel.setBounds(35, 220, 110, 20);
		this.add(nickNameLabel);
		nickNameText.setBounds(145, 220, 200, 20);
		this.add(nickNameText);
		
		pwdLabel.setBounds(35, 260, 110, 20);
		this.add(pwdLabel);
		pwdText.setBounds(145, 260, 200, 20);
		this.add(pwdText);
		
		confirmPwdLabel.setBounds(35, 300, 110, 20);
		this.add(confirmPwdLabel);
		confirmPwdText.setBounds(145, 300, 200, 20);
		this.add(confirmPwdText);
		
		sexLabel.setBounds(35, 340, 110, 20);
		this.add(sexLabel);
		sexSelector.setBounds(145, 340, 50, 20);
		this.add(sexSelector);
		
		birthdayLabel.setBounds(35, 380, 110, 20);
		this.add(birthdayLabel);
		yearText.setBounds(145, 380, 60, 20);
		yearLabel.setBounds(205, 380, 25, 20);
		monthText.setBounds(220, 380, 50, 20);
		monthLabel.setBounds(270, 380, 25, 20);
		dayText.setBounds(285, 380, 50, 20);
		dayLabel.setBounds(335, 380, 25, 20);
		this.add(yearText);
		this.add(yearLabel);
		this.add(monthText);
		this.add(monthLabel);
		this.add(dayText);
		this.add(dayLabel);
		
		ageLabel.setBounds(35, 420, 110, 20);
		this.add(ageLabel);
		ageText.setBounds(145, 420, 200, 20);
		ageText.setEditable(false);
		this.add(ageText);
		ageLabel.setVisible(false);
		ageText.setVisible(false);
		
		constellationLabel.setBounds(35, 460, 110, 20);
		this.add(constellationLabel);
		constellationText.setBounds(145, 460, 200, 20);
		constellationText.setEditable(false);
		this.add(constellationText);
		constellationLabel.setVisible(false);
		constellationText.setVisible(false);
		
		confirmLabel.setBounds(200, 520, 64, 64);
		confirmLabel.addMouseListener(this);
		this.add(confirmLabel);
		cancelLabel.setBounds(280, 520, 64, 64);
		cancelLabel.addMouseListener(this);
		this.add(cancelLabel);
	}
	
	private void alert(String message)
	{
		JOptionPane.showMessageDialog(this, message, "提示", JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if((JLabel)e.getSource() == confirmLabel)
		{
			if(new MyAssertion().startAssert())
			{
				Container c = this;
				while(c.getParent() != null)
				{
					c = c.getParent();
				}
				MainWindow frame = (MainWindow)c;
				
				String userName = userNameText.getText().trim();
				String nickName = nickNameText.getText().trim();
				String password = pwdText.getText().trim();
				String confirmPassword = confirmPwdText.getText().trim();
				Sex sex = ((String)sexSelector.getSelectedItem()).equals("男")? Sex.male : Sex.female;
				int year = Integer.parseInt(yearText.getText().trim());
				int month = Integer.parseInt(monthText.getText().trim());
				int day = Integer.parseInt(dayText.getText().trim());
				byte[] imageBytes = BufferedImageBytes.bufferedImage2Bytes(headImage);
				user.setUserName(userName);
				user.setNickName(nickName);
				user.setPassword(password);
				user.setSex(sex);
				Calendar calendar = Calendar.getInstance();
				calendar.set(year, month, day);
				user.setBirthday(calendar.getTime());
				user.setHeadportrait(imageBytes);
				if(ManagerFactory.getManager().saveUser(user))
				{
	    			user = ManagerFactory.getManager().getUser(user);
				}
				
				if(user.getUserID() == 0)
				{
					frame.dispose();
					new LoginWindow();
				}
				else
				{
		    		frame.setPanel(new DiaryListWindow(user));
				}
			}
		}
		else if((JLabel)e.getSource() == cancelLabel)
		{
			Container c = this;
			while(c.getParent() != null)
			{
				c = c.getParent();
			}
			MainWindow frame = (MainWindow)c;
			if(user.getUserID() == 0)
			{
				frame.dispose();
				new LoginWindow();
			}
			else
			{
				frame.setPanel(new DiaryListWindow(user));
			}
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
	
	class MyAssertion
	{
		String userName = userNameText.getText().trim();
		String nickName = nickNameText.getText().trim();
		String password = pwdText.getText().trim();
		String confirmPassword = confirmPwdText.getText().trim();
		Sex sex = ((String)sexSelector.getSelectedItem()).equals("男")? Sex.male : Sex.female;
		String yearString = yearText.getText().trim();
		String monthString = monthText.getText().trim();
		String dayString = dayText.getText().trim();
		
		boolean startAssert()
		{
			try
			{
				assert isUserNameValid() : "用户名必须为有效的邮箱格式";
				assert isNickNameValid() : "昵称不能为空";
				assert isPasswordValid() : "密码不能为空";
				assert isPasswordRepeated() : "密码和确认密码不一致";
				assert isBirthdayValid() : "出生年月日填写无效";
			}
			catch(AssertionError ae)
			{
				alert(ae.getMessage());
				return false;
			}
			return true;
		}
		
		boolean isUserNameValid()
		{  
			if(userName.equals(""))
			{
				return false;
			}
	        final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";  
	        final Pattern pattern = Pattern.compile(pattern1);  
	        final Matcher mat = pattern.matcher(userName);  
	        if (!mat.find()) {  
	            return false; 
	        }  
	        return true;
		}
		
		boolean isNickNameValid()
		{
			return !nickName.equals("");
		}
		
		boolean isPasswordValid()
		{
			return !password.equals("");
		}
		
		boolean isPasswordRepeated()
		{
			return password.equals(confirmPassword);
		}
		
		boolean isBirthdayValid()
		{
			int year, month, day;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Date date = new Date();
			try
			{
				year = Integer.parseInt(yearString);
				month = Integer.parseInt(monthString);
				day = Integer.parseInt(dayString);
			}
			catch(Exception e)
			{
				return false;
			}
			
			if(year < 1900 || year > calendar.get(Calendar.YEAR))
			{
				return false;
			}
			
			if(month <= 0 || month > 12)
			{
				return false;
			}
			
			if(day <= 0 || day > getMaxDay(year, month))
			{
				return false;
			}
						
			return true;
		}
		
		public int getMaxDay(int year, int month)
		{
			switch(month)
			{
			case 1:
				return 31;
			case 2:
				if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
				{
					return 29;
				}
				else
				{
					return 28;
				}
			case 3:
				return 31;
			case 4:
				return 30;
			case 5:
				return 31;
			case 6:
				return 30;
			case 7:
				return 31;
			case 8:
				return 31;
			case 9:
				return 30;
			case 10:
				return 31;
			case 11:
				return 30;
			case 12:
				return 31;
				
			}
			return 30;
		}
	}
}
