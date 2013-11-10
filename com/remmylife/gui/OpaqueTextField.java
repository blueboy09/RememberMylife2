package com.remmylife.gui;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class OpaqueTextField extends JTextField
{
	public OpaqueTextField()
	{
		super();
		init();
	}
	
	public OpaqueTextField(int num)
	{
		super(num);
		init();
	}
	
	private void init()
	{
		this.setOpaque(false);
//		this.setBorder(BorderFactory.createEmptyBorder());
	}
	
	public void setBorderVisible(boolean b)
	{
		if(!b)
		{
			this.setBorder(BorderFactory.createEmptyBorder());
		}
	}
}
