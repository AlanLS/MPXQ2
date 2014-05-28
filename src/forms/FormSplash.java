package forms;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.plaf.Style;

public class FormSplash extends Form
{
	private Timer		timer;
	private TimerTask	timerTask;
	private Image		image;

	public FormSplash()
	{
		super();
		try
		{
			image = Image.createImage("/logo.png").scaled(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		setLayout(new BorderLayout());
		final Label l = new Label(image);
		//l.getStyle().setAlignment(Component.CENTER);
		addComponent(BorderLayout.CENTER, l);
	}

	public void show()
	{
		super.show();
	}

	protected void onShowCompleted()
	{
		super.onShowCompleted();
		timer = new Timer();
		timerTask = new NextScreenTask();
		((NextScreenTask) timerTask).f = this;
		timer.schedule(timerTask, 3000);
	}

	public class NextScreenTask extends TimerTask
	{
		public Form	f	= null;

		public void run()
		{
			timer.cancel();
			timer = null;
			cancel();
			//new mainTab().show();
			final Style s = f.getStyle();
			s.setBgImage(null);
			f.repaint();
			new FormPMD1().show();
			//new mainTab().show();
			/*
			 * // if (ApplicationData.getCASSessionToken() != null) { // we have
			 * a previous session ApplicationData.getAccessToken();
			 * Storage.getInstance().flushStorageCache(); final MsgPlusCloud kk
			 * = ApplicationData.getCloudData(); if (kk != null) {
			 * ApplicationData.getAccountInfo(); if
			 * (UploadedRMS.recordStoreExists()) { // new
			 * UploadAutoUploads().loadAutoUploads(); new UploadFiles(); } }
			 * ApplicationData.trackerAddToQueue(new
			 * PageView("/logon_withToken")); new FormMainMenu().show(); } else
			 * { final int idx = ApplicationData.getServerIdx();
			 * Storage.getInstance().clearStorage();
			 * ApplicationData.setServerIdx(idx); new FormConnect().show(); }
			 */
		}
	}
	//
}
