package main;

import lcl.L10nResources;

public class GlobalData
{
	private static GlobalData	theInstance	= null;

	public static GlobalData getInstance()
	{
		if (GlobalData.theInstance == null)
		{
			GlobalData.theInstance = new GlobalData();
		}
		return GlobalData.theInstance;
	}

	// --------------------------------------------------------------
	private static String	locale	= new String("en-US");

	public static String getLocale()
	{
		return GlobalData.locale;
	}

	public static void setLocale(String locale)
	{
		GlobalData.locale = locale;
	}

	// ---------------------------------------------------------------
	private static L10nResources	rsrc	= L10nResources.getL10nResources(locale);

	public static L10nResources getRsrc()
	{
		return rsrc;
	}

	// public static void setRsrc(L10nResources rsrc)
	// {
	// GlobalData.rsrc = rsrc;
	// }
	// -------------------------------------------------------------------
	private static boolean	isDebug	= false;

	public static void setDebug(boolean isDebug)
	{
		GlobalData.isDebug = isDebug;
	}

	public static boolean isDebug()
	{
		return isDebug;
	}

	// -------------------------------------------------------------------
	private static String	userPhoneNumber	= "";

	public static String getUserPhoneNumber()
	{
		return userPhoneNumber;
	}

	public static void setUserPhoneNumber(String userPhoneNumber)
	{
		GlobalData.userPhoneNumber = userPhoneNumber;
	}

	// ----------------------------------------------------------
	protected GlobalData()
	{
		init();
	}

	public void init()
	{
	}
}
