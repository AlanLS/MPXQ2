/**
 * 
 */
package forms;

import java.util.Timer;
import java.util.TimerTask;
import lcl.L10nConstants;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import components.MPActivityIndicator;
import components.MPBorderlessLabel;
import components.MPForm;
import components.MPGrowTextArea;
import components.MPTextField;
import components.MPTextFieldWithTitle;
import components.StyleHelpers;

/**
 * @author Alan
 *
 */
public class FormPMD2 extends MPForm
{
	protected final int				PMD2_UNINITTED		= -1;
	protected final int				PMD2_REGISTERING_1	= 0;
	protected final int				PMD2_REGISTERING_2	= 1;
	protected final int				PMD2_ACTIVATE		= 2;
	protected final int				PMD2_COUNT			= 3;
	protected int					PMD2_State			= PMD2_UNINITTED;
	protected Container[]			containers			= new Container[PMD2_COUNT];
	//
	private MPTextFieldWithTitle	tfPinNo				= null;
	private MPBorderlessLabel		lTryAgain			= null;
	//
	private final Command			cancelCommand		= new Command(getRsrc().getString(L10nConstants.keys.COMMAND_CANCEL));
	protected String				PINNo				= null;

	// private Command cmdLogin = null;
	public FormPMD2()
	{
		super(getRsrc().getString(L10nConstants.keys.REG_MESSAGEPLUS));
	}

	public void show()
	{
		super.show();
	}

	protected void onShowCompleted()
	{
		super.onShowCompleted();
		if (PMD2_State == PMD2_UNINITTED)
		{
			showRegisteringScreen1();
			final Timer t = new Timer();
			t.schedule(new RegTimerTask(this, t), 3000);
			//
		}
	}

	private void showRegisteringScreen1()
	{
		if (PMD2_State != PMD2_REGISTERING_1)
		{
			MPActivityIndicator.getInstance().uninstallPane();
			removeAllCommands();
			PMD2_State = PMD2_REGISTERING_1;
			if (containers[PMD2_REGISTERING_1] == null)
			{
				//final Container cntnr = new Container(new BorderLayout());
				final Container cntnr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
				//cntnr.addComponent(BorderLayout.CENTER, innercntnr);
				//
				cntnr.addComponent(new MPBorderlessLabel(getRsrc().getString(L10nConstants.keys.REG_REGISTERING)));
				//
				lTryAgain = new MPBorderlessLabel(getRsrc().getString(L10nConstants.keys.REG_TRYINGAGAIN));
				cntnr.addComponent(lTryAgain);
				//
				containers[PMD2_REGISTERING_1] = cntnr;
			}
			//
			lTryAgain.setVisible(false);
			replaceContent(getContentPane(), containers[PMD2_REGISTERING_1], null);
			MPActivityIndicator.getInstance().installPane(this);
			setBackCommand(cancelCommand);
			revalidate();
			repaint();
		}
	}

	private void showRegisteringScreen2()
	{
		if (PMD2_State != PMD2_REGISTERING_2)
		{
			MPActivityIndicator.getInstance().uninstallPane();
			removeAllCommands();
			PMD2_State = PMD2_REGISTERING_2;
			lTryAgain.setVisible(true);
			MPActivityIndicator.getInstance().installPane(this);
			setBackCommand(cancelCommand);
			revalidate();
			repaint();
		}
	}

	private void showActivate()
	{
		if (PMD2_State != PMD2_ACTIVATE)
		{
			MPActivityIndicator.getInstance().uninstallPane();
			setTitle(getRsrc().getString(L10nConstants.keys.ACT_ACTIVATE));
			removeAllCommands();
			PMD2_State = PMD2_ACTIVATE;
			if (containers[PMD2_ACTIVATE] == null)
			{
				final Container cntnr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
				//
				final MPGrowTextArea gtaActivateText = new MPGrowTextArea(getRsrc().getString(L10nConstants.keys.ACT_SENTSMS));
				gtaActivateText.setFocusable(false);
				//StyleHelpers.setMargin(gtaActivateText, 5, 5);
				StyleHelpers.setPadding(gtaActivateText, 0, 0);
				cntnr.addComponent(gtaActivateText);
				tfPinNo = new MPTextFieldWithTitle(getRsrc().getString(L10nConstants.keys.ACT_ENTERPINNUMBER), "");
				TextField.setUseNativeTextInput(false);
				final MPTextField tf = tfPinNo.getTfTextField();
				tf.setHint(getRsrc().getString(L10nConstants.keys.ACT_ENTERPINNUMBER));
				tf.addActionListener(this);
				tfPinNo.setSelectCommandText(getRsrc().getString(L10nConstants.keys.COMMAND_OK));
				cntnr.addComponent(tfPinNo);
				/*
				
				final MPBorderlessLabel blPinNumberTitle = new MPBorderlessLabel(rsrc.getString(L10nConstants.keys.ACT_ENTERPINNUMBER));
				blPinNumberTitle.setFocusable(false);
				StyleHelpers.setMargin(blPinNumberTitle, 5, 0);
				StyleHelpers.setPadding(blPinNumberTitle, 0, 0);
				cntnr.addComponent(blPinNumberTitle);
				//
				tfPinNo = new MPTextFieldNoPrompt();
				tfPinNo.setText("");
				tfPinNo.setHint(rsrc.getString(L10nConstants.keys.ACT_ENTERPINNUMBER));
				StyleHelpers.setBorder(tfPinNo, blckBorder);
				StyleHelpers.setMargin(tfPinNo, 0, 5);
				StyleHelpers.setPadding(tfPinNo, 4, 4);
				tfPinNo.addActionListener(this);
				cntnr.addComponent(tfPinNo);
				tfPinNo.requestFocus();
				//
				  
				 */
				containers[PMD2_ACTIVATE] = cntnr;
			}
			//	
			replaceContent(getContentPane(), containers[PMD2_ACTIVATE], null);
			tfPinNo.requestFocus();
			setBackCommand(cancelCommand);
			revalidate();
			repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sun.lwuit.events.ActionListener#actionPerformed(com.sun.lwuit.events
	 * .ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0)
	{
		super.actionPerformed(arg0);
		if (cmd != null)
		{
			if (cmd == cancelCommand)
			{
				MPActivityIndicator.getInstance().uninstallPane();
				new FormSplash().show();
			}
		}
		else if (cmp != null)
		{
			if (cmp == tfPinNo.getTfTextField())
			{
				if ((tfPinNo.getTfTextField().getText() != null) && (tfPinNo.getTfTextField().getText().length() > 0))
				{
					MPActivityIndicator.getInstance().uninstallPane();
					new FormTandC().show();
				}
			}
		}
	}

	//////////////////////temp
	public class RegTimerTask extends TimerTask
	{
		FormPMD2	frm	= null;
		Timer		tmr	= null;

		public RegTimerTask(FormPMD2 _frm, Timer _tmr)
		{
			frm = _frm;
			tmr = _tmr;
		}

		public void run()
		{
			System.out.println("Timer hit");
			tmr.cancel();
			tmr = null;
			if (frm.PMD2_State == frm.PMD2_REGISTERING_1)
			{
				System.out.println("Timer hit in PMD2_REGISTERING_1");
				frm.showRegisteringScreen2();
				final Timer t = new Timer();
				t.schedule(new RegTimerTask(frm, t), 6000);
			}
			else if (frm.PMD2_State == frm.PMD2_REGISTERING_2)
			{
				System.out.println("Timer hit in PMD2_REGISTERING_2");
				frm.showActivate();
			}
			else
			{
				System.out.println("Timer hit in NOTHING, state = " + frm.PMD2_State);
			}
			cancel();
		}
	}
}
