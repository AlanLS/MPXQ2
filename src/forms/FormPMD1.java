/**
 * 
 */
package forms;

import lcl.L10nConstants;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import components.MPBorderlessLabel;
import components.MPForm;
import components.MPGrowTextArea;
import components.MPTextAreaWithTitle;
import components.MPTextField;
import components.MPTextFieldWithTitle;
import components.StyleHelpers;

/**
 * @author Alan
 * 
 */
public class FormPMD1 extends MPForm
{
	protected final int				PMD1_UNINITTED	= -1;
	protected final int				PMD1_ENTRY		= 0;
	protected final int				PMD1_CORRECT	= 1;
	protected final int				PMD1_COUNT		= 2;
	protected int					PMD1_State		= PMD1_UNINITTED;
	protected Container[]			containers		= new Container[PMD1_COUNT];
	//
	private final Label				lCountryRegion	= null;
	//private MPTextFieldWithTitle	tfPhoneNumber		= null;
	private MPTextAreaWithTitle		tfPhnNmbrVrfy	= null;
	private MPTextFieldWithTitle	tfPhnNmbr		= null;
	private Button					l				= null;
	//
	private final Command			okCommand		= new Command(getRsrc().getString(L10nConstants.keys.COMMAND_OK));
	private final Command			changeCommand	= new Command(getRsrc().getString(L10nConstants.keys.COMMAND_CHANGE));
	private final Command			exitCommand		= new Command(getRsrc().getString(L10nConstants.keys.COMMAND_EXIT));
	protected String				userPhoneNumber	= "";

	public FormPMD1()
	{
		super(getRsrc().getString(L10nConstants.keys.VYN_NUMBERTITLE));
	}

	public void show()
	{
		super.show();
		if (PMD1_State == PMD1_UNINITTED)
		{
			showEntryScreen();
		}
	}

	protected void onShowCompleted()
	{
		super.onShowCompleted();
	}

	private void showEntryScreen()
	{
		if (PMD1_State != PMD1_ENTRY)
		{
			removeAllCommands();
			PMD1_State = PMD1_ENTRY;
			if (containers[PMD1_ENTRY] == null)
			{
				TextField.setUseNativeTextInput(false);
				final Container cntnr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
				//
				final MPBorderlessLabel blEnterPhoneNumber = new MPBorderlessLabel(getRsrc().getString(L10nConstants.keys.VYN_ENTERYOURNUMBER));
				StyleHelpers.setMargin(blEnterPhoneNumber, 0, 0);
				StyleHelpers.setPadding(blEnterPhoneNumber, 1, 1);
				cntnr.addComponent(blEnterPhoneNumber);
				//
				final MPTextFieldWithTitle tfCntryRgn = new MPTextFieldWithTitle(getRsrc().getString(L10nConstants.keys.VYN_COUNTRYREGION), "");
				tfCntryRgn.getTfTextField().setHint(getRsrc().getString(L10nConstants.keys.VYN_COUNTRYREGION));
				tfCntryRgn.setFocusable(false);
				cntnr.addComponent(tfCntryRgn);
				//
				tfPhnNmbr = new MPTextFieldWithTitle(getRsrc().getString(L10nConstants.keys.VYN_PHONENUMBER), userPhoneNumber);
				TextField.setUseNativeTextInput(false);
				final MPTextField tf = tfPhnNmbr.getTfTextField();
				tf.setHint(getRsrc().getString(L10nConstants.keys.VYN_ENTERNUMBER));
				tf.setInputMode("123");
				tf.setConstraint(TextArea.PHONENUMBER);
				tf.addActionListener(this);
				tfPhnNmbr.setSelectCommandText(getRsrc().getString(L10nConstants.keys.COMMAND_OK));
				cntnr.addComponent(tfPhnNmbr);
				//
				final MPGrowTextArea gtaVerifyText = new MPGrowTextArea(getRsrc().getString(L10nConstants.keys.VYN_TEXT));
				gtaVerifyText.setFocusable(false);
				StyleHelpers.setMargin(gtaVerifyText, 5, 0);
				StyleHelpers.setPadding(gtaVerifyText, 0, 0);
				cntnr.addComponent(gtaVerifyText);
				//
				containers[PMD1_ENTRY] = cntnr;
			}
			//
			replaceContent(getContentPane(), containers[PMD1_ENTRY], null);
			setBackCommand(exitCommand);
			setDefaultCommand(null);
			tfPhnNmbr.getTfTextField().requestFocus();
			getSelectCommand().setEnabled(false);
			revalidate();
			repaint();
		}
	}

	private void showVerifyScreen()
	{
		if (PMD1_State != PMD1_CORRECT)
		{
			removeAllCommands();
			PMD1_State = PMD1_CORRECT;
			if (containers[PMD1_CORRECT] == null)
			{
				final Container cntnr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
				//
				tfPhnNmbrVrfy = new MPTextAreaWithTitle(getRsrc().getString(L10nConstants.keys.VYN_NUMBERCORRECT), userPhoneNumber);
				tfPhnNmbrVrfy.setSelectCommandText(getRsrc().getString(L10nConstants.keys.COMMAND_OK));
				tfPhnNmbrVrfy.setTitleAtTop(false);
				cntnr.addComponent(tfPhnNmbrVrfy);
				l = new Button();
				l.setUIID("Label");
				l.setFocusable(true);
				l.setSelectCommandText(getRsrc().getString(L10nConstants.keys.COMMAND_OK));
				l.addActionListener(this);
				cntnr.addComponent(l);
				containers[PMD1_CORRECT] = cntnr;
			}
			replaceContent(getContentPane(), containers[PMD1_CORRECT], null);
			setBackCommand(changeCommand);
			l.requestFocus();
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
			if (cmd == okCommand)
			{
				if (PMD1_State == PMD1_ENTRY)
				{
					if (isEntered())
					{
						tfPhnNmbr.setFocus(false);
						showVerifyScreen();
					}
				}
				else if (PMD1_State == PMD1_CORRECT)
				{
					if (isEntered())
					{
						new FormPMD2().show();
					}
				}
			}
			else if (cmd == changeCommand)
			{
				showEntryScreen();
			}
			else if (cmd == exitCommand)
			{
				Display.getInstance().exitApplication();
			}
		}
		else if (cmp != null)
		{
			if (cmp == tfPhnNmbr.getTfTextField())
			{
				userPhoneNumber = tfPhnNmbr.getTfTextField().getText();
				if (isEntered())
				{
					showVerifyScreen();
				}
			}
			else if (cmp.getClass() == Button.class)
			{
				if (isEntered())
				{
					new FormPMD2().show();
				}
			}
		}
	}

	private boolean isEntered()
	{
		return ((tfPhnNmbr.getTfTextField().getText() != null) && (tfPhnNmbr.getTfTextField().getText().length() > 0));
	}
	/*private void showHideOK()
	{
		final int cnt = getCommandCount();
		for (int i = 0; i < cnt; ++i)
		{
			if (getCommand(i) == okCommand)
			{
				if (isEntered())
				{
				}
				else
				{
					removeCommand(okCommand);
				}
				repaint();
				return;
			}
		}
		if (isEntered())
		{
			addCommand(okCommand, 0);
			repaint();
		}
	}
	*/
}
