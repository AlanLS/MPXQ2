/**
 * 
 */
package main;

import java.io.IOException;
import javax.microedition.midlet.MIDlet;
import com.sun.lwuit.Display;
import com.sun.lwuit.List;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;
import forms.FormSplash;

/**
 * @author Alan
 * 
 */
public class MessagePlus extends MIDlet
{
	public void startApp()
	{
		// ApplicationData.setTheMidlet(this);
		Display.init(this);
		//Display.getInstance().setTouchScreenDevice(false);
		//Display.getInstance().setPureTouch(false);
		//
		Display.getInstance().setThirdSoftButton(true);
		GlobalData.getInstance();
		System.out.println("Start");
		//
		// if (!Storage.isInitialized())
		// {
		/*
		 * Storage.init("MPPersist"); // } Storage.getInstance(); try { final
		 * String[] ss = Storage.getInstance().listEntries();
		 * System.out.println("Storage:"); for (int i = 0; i < ss.length; ++i) {
		 * final Object obj =
		 * Storage.getInstance().readObject(ss[i].toString()); if (obj != null)
		 * { System.out.println(ss[i].toString() + " = " + obj.toString()); }
		 * else { // Storage.getInstance().clearStorage(); } }
		 * System.out.println("End Storage"); } catch (final Exception e) { }
		 */
		/*
		 * String txt = (
		 * "To be, or not to be, that is the question: Whether 'tis Nobler in the mind to sufferThe Slings and Arrows of outrageous Fortune, Or to take Arms against a Sea of troubles, And by opposing end them: to die, to sleep No more; and by a sleep, to say we end The Heart-ache, and the thousand Natural shocks That Flesh is heir to? 'Tis a consummation Devoutly to be wished. To die to sleep, To sleep, perchance to Dream"
		 * ); String encr = Crypt.encrypting(txt); if (encr != null) {
		 * //System.out.println(Utils.byteArrayToHexString(encr));
		 * System.out.println("\n>>>>>\n" + encr); }
		 */
		// defaults to 10
		// ApplicationData.getImageCache().setCacheSize(12);
		// // ApplicationData.getImageCache().setStorageCacheSize(0);
		// System.out.println("\nimageCache.getStorageCacheSize= " +
		// ApplicationData.getImageCache().getStorageCacheSize());
		// System.out.println("imageCache.getCacheSize= " +
		// ApplicationData.getImageCache().getCacheSize() + "\n");
		//
		// ApplicationData.setExpiresIn(0);
		//
		Resources res = null;
		//UIManager.getInstance().getLookAndFeel().setDefaultFormTintColor(0xffffffff);
		try
		{
			res = com.sun.lwuit.util.Resources.open("/Blank.res");
			UIManager.getInstance().setThemeProps(res.getTheme(res.getThemeResourceNames()[0]));
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			res = null;
		}
		// KNetworkManager.getInstance().start();
		// various Object static settings
		//
		TextField.setReplaceMenuDefault(true);
		TextField.setUseNativeTextInput(true);
		TextField.setQwertyAutoDetect(true);
		//
		List.setDefaultIgnoreFocusComponentWhenUnfocused(true);
		//
		// ApplicationData.setMidletversion(getAppProperty("MIDlet-Version"));
		new FormSplash().show();
		//new mainTab().show();
	}

	public void pauseApp()
	{
	}

	public void destroyApp(final boolean unconditional)
	{
		System.out.println("DESTROY APP");
		// Storage.getInstance().flushStorageCache();
		// KNetworkManager.getInstance().shutdown();
		// UploadedRMS.getInstance().closeTheStore();
		// BulkRMS.getInstance().clearTheStore();
		// BulkRMS.getInstance().closeTheStore();
		// ApplicationData.trackerFlush();
	}

	public void actionPerformed(final ActionEvent arg0)
	{
		notifyDestroyed();
	}
}
