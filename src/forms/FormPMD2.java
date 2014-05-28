/**
 * 
 */
package forms;

import java.util.Timer;
import java.util.TimerTask;
import lcl.L10nConstants;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import components.MPActivityIndicator;
import components.MPBorderlessLabel;
import components.MPForm;
import components.MPGrowTextArea;
import components.MPTextField;
import components.StyleHelpers;

/**
 * @author Alan
 * 
 */
public class FormPMD2 extends MPForm
{
	protected final int			PMD2_UNINITTED		= -1;
	protected final int			PMD2_REGISTERING_1	= 0;
	protected final int			PMD2_REGISTERING_2	= 1;
	protected final int			PMD2_ACTIVATE		= 2;
	protected final int			PMD2_COUNT			= 3;
	protected int				PMD2_State			= PMD2_UNINITTED;
	protected Container[]		containers			= new Container[PMD2_COUNT];
	//
	private MPTextField			tfPinNo				= null;
	private MPBorderlessLabel	lTryAgain			= null;
	//
	private final Command		cancelCommand		= new Command(rsrc.getString(L10nConstants.keys.COMMAND_CANCEL));
	private final Command		nextCommand			= new Command(rsrc.getString(L10nConstants.keys.COMMAND_NEXT));
	protected String			PINNo				= null;

	// private Command cmdLogin = null;
	public FormPMD2()
	{
		super(rsrc.getString(L10nConstants.keys.REG_MESSAGEPLUS));
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
			t.schedule(new RegTimerTask(this, t), 2000);
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
			// final Container cntnr = new Container(new BorderLayout());
			final Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
			// cntnr.addComponent(BorderLayout.CENTER, innercntnr);
			//
			c.addComponent(new MPBorderlessLabel(rsrc.getString(L10nConstants.keys.REG_REGISTERING)));
			//
			lTryAgain = new MPBorderlessLabel(rsrc.getString(L10nConstants.keys.REG_TRYINGAGAIN));
			c.addComponent(lTryAgain);
			//
			//
			lTryAgain.setVisible(false);
			replaceContent(getContentPane(), c, null);
			MPActivityIndicator.getInstance().installPane(this);
			setBackCommand(cancelCommand);
			setDefaultCommand(null);
			//addCommand(okCommand);
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
			setDefaultCommand(null);
			revalidate();
			repaint();
		}
	}

	private void showActivate()
	{
		if (PMD2_State != PMD2_ACTIVATE)
		{
			MPActivityIndicator.getInstance().uninstallPane();
			setTitle(rsrc.getString(L10nConstants.keys.ACT_ACTIVATE));
			removeAllCommands();
			PMD2_State = PMD2_ACTIVATE;
			final Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
			//
			final MPGrowTextArea gtaActivateText = new MPGrowTextArea(rsrc.getString(L10nConstants.keys.ACT_SENTSMS));
			gtaActivateText.setFocusable(false);
			// StyleHelpers.setMargin(gtaActivateText, 5, 5);
			StyleHelpers.setPadding(gtaActivateText, 0, 0);
			c.addComponent(gtaActivateText);
			//
			final MPBorderlessLabel lblNmbr = new MPBorderlessLabel(rsrc.getString(L10nConstants.keys.ACT_ENTERPINNUMBER));
			StyleHelpers.setMargin(lblNmbr, 5, 0);
			StyleHelpers.setPadding(lblNmbr, 1, 1);
			c.addComponent(lblNmbr);
			//
			//MPTextFieldWithTitle tf = new MPTextFieldWithTitle(rsrc.getString(L10nConstants.keys.VYN_PHONENUMBER), userPhoneNumber);
			tfPinNo = new MPTextField();//tfPhnNmbr.getTfTextField();
			//
			StyleHelpers.setMargin(tfPinNo, 0, 5);
			StyleHelpers.setPadding(tfPinNo, 5, 5);
			////TextField.setUseNativeTextInput(false);
			tfPinNo.setHint(rsrc.getString(L10nConstants.keys.ACT_ENTERPINNUMBER));
			tfPinNo.addActionListener(this);
			c.addComponent(tfPinNo);
			//
			final MPBorderlessLabel dummy = new MPBorderlessLabel();
			dummy.setFocusable(true);
			c.addComponent(dummy);
			/*
			 * 
			 * final MPBorderlessLabel blPinNumberTitle = new
			 * MPBorderlessLabel
			 * (rsrc.getString(L10nConstants.keys.ACT_ENTERPINNUMBER));
			 * blPinNumberTitle.setFocusable(false);
			 * StyleHelpers.setMargin(blPinNumberTitle, 5, 0);
			 * StyleHelpers.setPadding(blPinNumberTitle, 0, 0);
			 * cntnr.addComponent(blPinNumberTitle); // tfPinNo = new
			 * MPTextFieldNoPrompt(); tfPinNo.setText("");
			 * tfPinNo.setHint(rsrc
			 * .getString(L10nConstants.keys.ACT_ENTERPINNUMBER));
			 * StyleHelpers.setBorder(tfPinNo, blckBorder);
			 * StyleHelpers.setMargin(tfPinNo, 0, 5);
			 * StyleHelpers.setPadding(tfPinNo, 4, 4);
			 * tfPinNo.addActionListener(this); cntnr.addComponent(tfPinNo);
			 * tfPinNo.requestFocus(); //
			 */
			//
			replaceContent(getContentPane(), c, null);
			tfPinNo.requestFocus();
			addCommand(nextCommand);
			addCommand(cancelCommand);
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
			if (cmd == nextCommand)
			{
				if ((tfPinNo.getText() != null) && (tfPinNo.getText().length() > 0))
				{
					MPActivityIndicator.getInstance().uninstallPane();
					new FormTandC().show();
				}
			}
			else if (cmd == cancelCommand)
			{
				MPActivityIndicator.getInstance().uninstallPane();
				new FormSplash().show();
			}
		}
		else if (cmp != null)
		{
			//if (cmp == tfPinNo.getTfTextField())
			//{
			//	if ((tfPinNo.getTfTextField().getText() != null) && (tfPinNo.getTfTextField().getText().length() > 0))
			//	{
			//		MPActivityIndicator.getInstance().uninstallPane();
			//		new FormTandC().show();
			//	}
			//}
		}
	}

	// ////////////////////temp
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
				t.schedule(new RegTimerTask(frm, t), 2000);
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
