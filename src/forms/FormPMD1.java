/**
 * 
 */
package forms;

import lcl.L10nConstants;
import main.GlobalData;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
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
import components.StyleHelpers;

/**
 * @author Alan
 * 
 */
public class FormPMD1 extends MPForm
{
	protected final int		PMD1_UNINITTED		= -1;
	protected final int		PMD1_ENTRY			= 0;
	protected final int		PMD1_CORRECT		= 1;
	protected final int		PMD1_COUNT			= 2;
	protected int			PMD1_State			= PMD1_UNINITTED;
	protected Container[]	containers			= new Container[PMD1_COUNT];
	//
	private Label			lCountryRegion		= null;
	private TextField		tfPhoneNumber		= null;
	private Button			lPhoneNumberVerify	= null;
	//
	private final Command	okCommand			= new Command(rsrc.getString(L10nConstants.keys.COMMAND_OK));
	//private final Command		okValidCommand			= new Command(rsrc.getString(L10nConstants.keys.COMMAND_OK));
	private final Command	changeCommand		= new Command(rsrc.getString(L10nConstants.keys.COMMAND_CHANGE));
	private final Command	exitCommand			= new Command(rsrc.getString(L10nConstants.keys.COMMAND_EXIT));
	protected String		userPhoneNumber		= "";

	// private Command cmdLogin = null;
	public FormPMD1()
	{
		super(rsrc.getString(L10nConstants.keys.VYN_NUMBERTITLE));
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
				final Container cntnr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
				//
				final MPBorderlessLabel blEnterPhoneNumber = new MPBorderlessLabel(rsrc.getString(L10nConstants.keys.VYN_ENTERYOURNUMBER));
				StyleHelpers.setMargin(blEnterPhoneNumber, 0, 0);
				StyleHelpers.setPadding(blEnterPhoneNumber, 1, 1);
				cntnr.addComponent(blEnterPhoneNumber);
				//
				final MPBorderlessLabel blCountryRegionTitle = new MPBorderlessLabel(rsrc.getString(L10nConstants.keys.VYN_COUNTRYREGION));
				blCountryRegionTitle.setFocusable(false);
				StyleHelpers.setMargin(blCountryRegionTitle, 5, 0);
				StyleHelpers.setPadding(blCountryRegionTitle, 1, 1);
				cntnr.addComponent(blCountryRegionTitle);
				//
				lCountryRegion = new Label(GlobalData.getLocale());
				lCountryRegion.setFocusable(false);
				StyleHelpers.setBorder(lCountryRegion, blckBorder);
				StyleHelpers.setMargin(lCountryRegion, 0, 5);
				StyleHelpers.setPadding(lCountryRegion, 7, 7);
				cntnr.addComponent(lCountryRegion);
				//
				final MPBorderlessLabel blPhoneNumberTitle = new MPBorderlessLabel(rsrc.getString(L10nConstants.keys.VYN_PHONENUMBER));
				blPhoneNumberTitle.setFocusable(false);
				StyleHelpers.setMargin(blPhoneNumberTitle, 5, 0);
				StyleHelpers.setPadding(blPhoneNumberTitle, 0, 0);
				cntnr.addComponent(blPhoneNumberTitle);
				//
				tfPhoneNumber = new TextField();
				TextField.setUseNativeTextInput(false);
				//tfPhoneNumber = new TextField();
				tfPhoneNumber.setText(userPhoneNumber);
				tfPhoneNumber.setInputMode("123");
				//private static String[] defaultInputModeOrder = {"Abc", "ABC", "abc", "123"};
				tfPhoneNumber.setHint(rsrc.getString(L10nConstants.keys.VYN_ENTERNUMBER));
				tfPhoneNumber.setConstraint(TextArea.PHONENUMBER);
				StyleHelpers.setBorder(tfPhoneNumber, blckBorder);
				StyleHelpers.setMargin(tfPhoneNumber, 0, 5);
				StyleHelpers.setPadding(tfPhoneNumber, 4, 4);
				tfPhoneNumber.addActionListener(this);
				cntnr.addComponent(tfPhoneNumber);
				tfPhoneNumber.requestFocus();
				//
				final MPGrowTextArea gtaVerifyText = new MPGrowTextArea(rsrc.getString(L10nConstants.keys.VYN_TEXT));
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
			tfPhoneNumber.requestFocus();
			//showHideOK();
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
				lPhoneNumberVerify = new Button(userPhoneNumber);
				lPhoneNumberVerify.setUIID("Label");
				lPhoneNumberVerify.setSelectCommandText(rsrc.getString(L10nConstants.keys.COMMAND_OK));
				lPhoneNumberVerify.addActionListener(this);
				StyleHelpers.setAlignment(lPhoneNumberVerify, Component.LEFT);
				StyleHelpers.setBorder(lPhoneNumberVerify, blckBorder);
				StyleHelpers.setMargin(lPhoneNumberVerify, 10, 10);
				StyleHelpers.setPadding(lPhoneNumberVerify, 7, 7);
				cntnr.addComponent(lPhoneNumberVerify);
				//
				final MPBorderlessLabel blNumberCorrect = new MPBorderlessLabel(rsrc.getString(L10nConstants.keys.VYN_NUMBERCORRECT));
				cntnr.addComponent(blNumberCorrect);
				//
				containers[PMD1_CORRECT] = cntnr;
			}
			replaceContent(getContentPane(), containers[PMD1_CORRECT], null);
			setBackCommand(changeCommand);
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
						showVerifyScreen();
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
			if (cmp == lPhoneNumberVerify)
			{
				if (PMD1_State == PMD1_CORRECT)
				{
					new FormPMD2().show();
				}
			}
			else if (cmp == tfPhoneNumber)
			{
				userPhoneNumber = tfPhoneNumber.getText();
				if (isEntered())
				{
					showVerifyScreen();
				}
				//showHideOK();
			}
		}
	}

	private boolean isEntered()
	{
		return ((userPhoneNumber != null) && (userPhoneNumber.length() > 0));
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
