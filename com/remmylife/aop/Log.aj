package com.remmylife.aop;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import com.remmylife.manager.*;
import com.remmylife.gui.ManagerFactory;
import com.remmylife.diary.*;

public aspect Log {
	
	private static final int ACTION_ADD    = 0;
	private static final int ACTION_UPDATE = 1;
	private static final int ACTION_DELETE = 2;
	
	pointcut saveDiary(Diary diary) : 
		call (* ManagerAccess.saveDiary(Diary)) 
		&& args(diary);
	void around(Diary diary) : saveDiary(diary) {
		if (diary.getId() < 1)
			logAction(diary, ACTION_ADD);
		else {
			logAction(diary, ACTION_UPDATE);
			logChange(diary);
		}
	}
	
	pointcut deleteDiary(Diary diary) : 
		call (* ManagerAccess.deleteDiary(Diary)) 
		&& args(diary);
	void around(Diary diary) : deleteDiary(diary) {
		logAction(diary, ACTION_DELETE);
	}
	
	private void logAction(Diary diary, int action) {
		String output = "[" + getTimeString() + "] ";
		switch (action) {
		case ACTION_ADD:
			output += "   ADD ";
			break;
		case ACTION_UPDATE:
			output += "UPDATE ";
			break;
		case ACTION_DELETE:
			output += "DELETE ";
			break;
		default:
			break;
		}
		output += "DIARY (title : " + diary.getTitle() + ")";
		//System.out.println(output);
		try {
			println("log/log.txt", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void logChange(Diary diary) { 
		 String filename = "log/update.txt", output;
		 Diary oldDiary = ManagerFactory.getManager().getDiary(diary); // 怎样获得ManagerAccess的对象？
		 if (!diary.getTitle().equals(oldDiary.getTitle())) {
		 	output = "title : " + oldDiary.getTitle() + " -> " + diary.getTitle();
		 	try
		 	{
		 		println(filename, output);
		 	}
		 	catch(Exception e){}
		 }
		 if(!diary.getTextContent().equals(oldDiary.getTextContent())) {
		 	output = " text : " + oldDiary.getTextContent() + " -> " + diary.getTextContent();
		 	try
		 	{
		 		println(filename, output);
		 	}
		 	catch(Exception e){}
		 }
	}
	
	private String getTimeString() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(date);
	}
	
	private void println(String file, String text) throws IOException {
		FileWriter fileWriter = new FileWriter(file, true);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println(text);
		printWriter.close();
		fileWriter.close();
	}
}