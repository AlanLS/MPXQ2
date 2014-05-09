package forms;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.layouts.BorderLayout;

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
			image = Image.createImage("/logo.png");
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	public void show()
	{
		super.show();
		setLayout(new BorderLayout());
		// final Label l = new Label(image);
		setBgImage(image);
		// l.getStyle().setAlignment(Component.CENTER);
		// addComponent(BorderLayout.CENTER, l);
	}

	protected void onShowCompleted()
	{
		super.onShowCompleted();
		//
		/*
		 * FileConnection testDir = null; try { testDir = (FileConnection)
		 * Connector.open(KeyStrings.memorycardPath, Connector.READ); } catch
		 * (final IOException e) { } finally { if (testDir != null) { try {
		 * testDir.close(); } catch (final IOException e) { } } } try { testDir
		 * = (FileConnection)
		 * Connector.open(System.getProperty("fileconn.dir.photos"),
		 * Connector.READ); } catch (final IOException e) { } finally { if
		 * (testDir != null) { try { testDir.close(); } catch (final IOException
		 * e) { } } } if (ApplicationData.isAutosyncAllowed() == false) {
		 * ApplicationData.setPhotoAndVideoSyncing(false); }
		 */
		timer = new Timer();
		timerTask = new NextScreenTask();
		timer.schedule(timerTask, 3000);
	}

	public class NextScreenTask extends TimerTask
	{
		public void run()
		{
			timer.cancel();
			timer = null;
			cancel();
			new FormPMD1().show();
			/*
			 * // if (ApplicationData.getCASSessionToken() != null) { // we have
			 * a previous session ApplicationData.getAccessToken();
			 * Storage.getInstance().flushStorageCache(); final MsgPlusCloud kk =
			 * ApplicationData.getCloudData(); if (kk != null) {
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
