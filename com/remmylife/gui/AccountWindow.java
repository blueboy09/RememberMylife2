package com.remmylife.gui;

import java.util.Date;
import java.util.Calendar;

import com.remmylife.diary.*;

public class AccountWindow extends RegisterWindow
{
	public AccountWindow(User user)
	{
		super();
		this.user = user;
		init();
	}
	
	private void init()
	{
		userNameText.setText(user.getUserName());
		userNameText.setEditable(false);
		nickNameText.setText(user.getNickName());
		pwdText.setText(user.getPassword());
		confirmPwdText.setText(user.getPassword());
		Date birthday = user.getBirthday();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(birthday);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		yearText.setText(String.valueOf(year));
		monthText.setText(String.valueOf(month));
		dayText.setText(String.valueOf(day));
		ageLabel.setVisible(true);
		ageText.setVisible(true);
		calendar.setTime(new Date());
		ageText.setText(String.valueOf(calendar.get(Calendar.YEAR) - year));
		
		if(user.getSex() == Sex.male)
		{
			sexSelector.setSelectedItem("ÄÐ");
		}
		else
		{
			sexSelector.setSelectedItem("Å®");
		}
	}
}
