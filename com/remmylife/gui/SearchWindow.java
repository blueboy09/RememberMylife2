package com.remmylife.gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.remmylife.diary.*;

public class SearchWindow extends JFrame implements MouseListener
{
	public static final int WIDTH = 500;
	public static final int HEIGHT = 60;
	
	private String[] searchTypes = new String[]{
			"内容",
			"标题",
			"日期(年/月/日)", 
			"日记类型"};
	
	private String[] diaryTypes = new String[]{"文本", "语音", "图片"};
	
	BGPanel bgPanel = new BGPanel("img/searchbg.png");
	JComboBox searchTypeSelector = new JComboBox(searchTypes);
	OpaqueTextField textField = new OpaqueTextField(20);
	JButton searchButton = new JButton("搜索");
	
	DiaryListWindow diaryListWindow = null;
	
	public SearchWindow()
	{
		init();
		this.setVisible(true);
	}
	
	public SearchWindow(DiaryListWindow diaryListWindow)
	{
		this.diaryListWindow = diaryListWindow;
		init();
	}
	
	private void init()
	{	try
    	{
	    	this.setIconImage(ImageIO.read(new File("img/logo-whole.jpg")));
    	}
     	catch(Exception e){}
		bgPanel.setLayout(new BoxLayout(bgPanel, BoxLayout.X_AXIS));
		searchTypeSelector.setOpaque(false);
		bgPanel.add(searchTypeSelector);
		bgPanel.add(textField);
		
		JPanel buttonPanel = new JPanel();
		searchButton.addMouseListener(this);
		buttonPanel.add(searchButton);
		buttonPanel.setOpaque(false);
		bgPanel.add(buttonPanel);
		
		this.add(bgPanel);
//		SubstanceLookAndFeel.setSkin(new MistAquaSkin());
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		int w=(screenSize.width-WIDTH)/2;
		this.setLocation(w,0);
		this.pack();
		this.setResizable(false);
	}
	
	private void alert(String message)
	{
		JOptionPane.showMessageDialog(this, message, "提示", JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		Object o = e.getSource();
		if((JButton)o == searchButton)
		{
			if(!(new SearchAssertion().startAssert()))
			{
				return;
			}
			String content = textField.getText().trim();
			ArrayList<Diary> diaryList = null;
			String searchType = (String)searchTypeSelector.getSelectedItem();
			if(searchType.equals(searchTypes[0]))
			{
				diaryList = ManagerFactory.getManager().searchByContent(
						content, diaryListWindow.getUser(), diaryListWindow.isSelf());
			}
			
			else if(searchType.equals(searchTypes[1]))
			{
				diaryList = ManagerFactory.getManager().searchByTitle(
						content, diaryListWindow.getUser(), diaryListWindow.isSelf());
			}
			
			else if(searchType.equals(searchTypes[2]))
			{
				int index1 = content.indexOf('/');
			    int index2 = content.indexOf('/', index1 + 1);
        		String yearString = content.substring(0, index1);
		    	String monthString = content.substring(index1 + 1, index2);
		    	String dayString = content.substring(index2 + 1);
		    	int year = Integer.parseInt(yearString);
		    	int month = Integer.parseInt(monthString) - 1;
		    	int day = Integer.parseInt(dayString);
		    	Calendar calendar = Calendar.getInstance();
		    	calendar.set(year, month, day);
				diaryList = ManagerFactory.getManager().searchByDate(
						calendar.getTime(), diaryListWindow.getUser(), diaryListWindow.isSelf());
				System.out.println(calendar.getTime());
				System.out.println(diaryList.size());
			}
			
			else if(searchType.equals(searchTypes[3]))
			{
				DiaryType diaryType = DiaryType.TEXT_DIARY;
				if(content.equals(diaryTypes[0]))
				{
					diaryType = DiaryType.TEXT_DIARY;
				}
				else if(content.equals(diaryTypes[1]))
				{
					diaryType = DiaryType.VOICE_DIARY;
				}
				else if(content.equals(diaryTypes[2]))
				{
					diaryType = DiaryType.IMAGE_DIARY;
				}
				
				diaryList = ManagerFactory.getManager().searchByType(
						diaryType, diaryListWindow.getUser(), diaryListWindow.isSelf());
			}
			
			diaryListWindow.setDiaryList(diaryList);
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
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
	
	public static void main(String[] args)
	{
		new SearchWindow();
	}
	
	class SearchAssertion
	{
		String searchType = (String)searchTypeSelector.getSelectedItem();
		String content = textField.getText().trim();
		
		public boolean startAssert()
		{
			try
			{
				if(searchType.equals(searchTypes[0]) || searchType.equals(searchTypes[1]))
				{
					assert isContentLengthValid() : "字串长度必须在1到20之间";
				}
				
				else if(searchType.equals(searchTypes[2]))
				{
					assert isDateValid() : "日期格式错误，必须为 年/月/日";
				}
				
				else if(searchType.equals(searchTypes[3]))
				{
					assert isDiaryTypeValid() : "日记类型只能为如下几种： 文本   语音   图片";
				}
			}
			catch(AssertionError ae)
			{
				alert(ae.getMessage());
				return false;
			}
			return true;
		}
		
		boolean isContentLengthValid()
		{
			return content.length() <= 20 && content.length() >= 1;
		}
		
		boolean isDateValid()
		{
			if(content.equals(""))
			{
				return false;
			}
			System.out.println(1);
			final String pattern1 = "^[0-9]+/[0-9]+/[0-9]+$";  
		    final Pattern pattern = Pattern.compile(pattern1);  
		    final Matcher mat = pattern.matcher(content);
		    if(!mat.find())
		    {
		    	return false;
		    }
		    System.out.println(1);
		    int index1 = content.indexOf('/');
		    int index2 = content.indexOf('/', index1 + 1);
		    int year, month, day;
		    Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
		    try
		    {
		    	String yearString = content.substring(0, index1);
		    	String monthString = content.substring(index1 + 1, index2);
		    	String dayString = content.substring(index2 + 1);
		    	year = Integer.parseInt(yearString);
		    	month = Integer.parseInt(monthString);
		    	day = Integer.parseInt(dayString);
		    }
		    catch(Exception e)
		    {
		    	return false;
		    }
		    System.out.println(1);
		    if(year < 1900 || year > calendar.get(Calendar.YEAR))
			{
				return false;
			}
		    System.out.println(1);
			if(month <= 0 || month > 12)
			{
				return false;
			}
			System.out.println(1);
			if(day < 0 || day > getMaxDay(year, month))
			{
				return false;
			}
			System.out.println(1);			
			return true;
		}
		
		boolean isDiaryTypeValid()
		{
			for(String s : diaryTypes)
			{
				if(s.equals(content))
				{
					return true;
				}
			}
			return false;
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
