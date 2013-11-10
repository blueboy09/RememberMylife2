package com.remmylife.gui;

import com.remmylife.manager.ManagerAccess;

public class ManagerFactory 
{
	private static ManagerAccess managerAccess = null;
	
	public static ManagerAccess getManager()
	{
		if(managerAccess == null)
		{
			managerAccess = new ManagerAccess();
		}
		return managerAccess;
	}
}
