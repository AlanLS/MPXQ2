package io;

import main.Constants;
import main.GlobalData;
import com.sun.lwuit.Display;
import components.MPDialog;

public class MPHTTPErrors
{
	public static void HandleHTTPWarning(final MPNetworkEvent _nevt)
	{
		printError(_nevt);
		MPDialog kd = null;
		if (GlobalData.isDebug())
		{
			final StringBuffer sb = new StringBuffer();
			sb.append(_nevt.getMessage()).append(Constants.spaceChar).append(_nevt.getActualResponseCode()).append(Constants.spaceChar).append(_nevt.getConnectionRequest().getUrl()).append(Constants.spaceChar).append(_nevt.getStringResponse());
			kd = new MPDialog(sb.toString());
			kd.showTimed(8000);
		}
		else
		{
			kd = new MPDialog("There was a connection problem. This is a warning only.");
			kd.showTimed(4000);
		}
		kd.dispose();
	}

	public static void HandleHTTPError(final MPNetworkEvent _nevt)
	{
		printError(_nevt);
		MPDialog kd = null;
		if (GlobalData.isDebug())
		{
			final StringBuffer sb = new StringBuffer();
			sb.append(_nevt.getMessage()).append(Constants.spaceChar).append(_nevt.getActualResponseCode()).append(Constants.spaceChar).append(_nevt.getConnectionRequest().getUrl()).append(Constants.spaceChar).append(_nevt.getStringResponse());
			kd = new MPDialog(sb.toString());
			kd.showTimed(8000);
		}
		else
		{
			kd = new MPDialog("There was an unrecoverable connection problem. Exiting Klink");
			kd.showTimed(4000);
		}
		kd.dispose();
		Display.getInstance().exitApplication();
	}

	private static void printError(MPNetworkEvent _nevt)
	{
		System.out.println("\nHTTP ERROR");
		System.out.println(_nevt.getMessage() + Constants.spaceChar + _nevt.getActualResponseCode());
		System.out.println(_nevt.getConnectionRequest().getUrl());
		System.out.println(_nevt.getStringResponse());
		/*
		 * final Hashtable ht = _nevt.getConnectionRequest().getUserHeaders();
		 * final Enumeration e = ht.keys(); while (e.hasMoreElements()) { final
		 * String key = (String) e.nextElement() + ':'; System.out.println(key +
		 * ht.get(key)); }
		 */
		//
	}
}
