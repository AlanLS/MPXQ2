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
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import components.MPBorderlessLabel;
import components.MPForm;
import components.MPGrowTextArea;
import components.MPTextAreaWithTitle;
import components.MPTextField;
import components.StyleHelpers;

/**
 * @author Alan
 * 
 */
public class FormPMD1 extends MPForm
{
	protected final int			PMD1_UNINITTED	= -1;
	protected final int			PMD1_ENTRY		= 0;
	protected final int			PMD1_CORRECT	= 1;
	protected int				PMD1_State		= PMD1_UNINITTED;
	//
	private final Label			lCountryRegion	= null;
	// private MPTextFieldWithTitle tfPhoneNumber = null;
	private MPTextAreaWithTitle	tfPhnNmbrVrfy	= null;
	//private MPTextFieldWithTitle	tfPhnNmbr		= null;
	private final Button		l				= null;
	//
	private final Command		okCommand		= new Command(rsrc.getString(L10nConstants.keys.COMMAND_OK));
	private final Command		changeCommand	= new Command(rsrc.getString(L10nConstants.keys.COMMAND_CHANGE));
	private final Command		exitCommand		= new Command(rsrc.getString(L10nConstants.keys.COMMAND_EXIT));
	protected String			userPhoneNumber	= "";

	public FormPMD1()
	{
		super(rsrc.getString(L10nConstants.keys.VYN_NUMBERTITLE));
	}

	public void show()
	{
		super.show();	
	}

	protected void onShowCompleted()
	{
		super.onShowCompleted();
		
		if (PMD1_State == PMD1_UNINITTED)
		{
			showEntryScreen();
		}
	}

	private void showEntryScreen()
	{
		if (PMD1_State != PMD1_ENTRY)
		{
			removeAllCommands();
			PMD1_State = PMD1_ENTRY;
			//if (containers[PMD1_ENTRY] == null)
			final Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
			//
			//final MPBorderlessLabel blEnterPhoneNumber = new MPBorderlessLabel(rsrc.getString(L10nConstants.keys.VYN_ENTERYOURNUMBER));
			//StyleHelpers.setMargin(blEnterPhoneNumber, 0, 0);
			//StyleHelpers.setPadding(blEnterPhoneNumber, 1, 1);
			//c.addComponent(blEnterPhoneNumber);
			//
			final MPBorderlessLabel lblCode = new MPBorderlessLabel(rsrc.getString(L10nConstants.keys.VYN_COUNTRYREGION));
			StyleHelpers.setMargin(lblCode, 5, 0);
			StyleHelpers.setPadding(lblCode, 1, 1);
			c.addComponent(lblCode);
			//
			final MPTextField tfCntryRgn = new MPTextField();
			tfCntryRgn.setHint(rsrc.getString(L10nConstants.keys.VYN_COUNTRYREGION));
			tfCntryRgn.setFocusable(false);
			c.addComponent(tfCntryRgn);
			//
			//final MPTextFieldWithTitle tfCntryRgn = new MPTextFieldWithTitle(rsrc.getString(L10nConstants.keys.VYN_COUNTRYREGION), "");
			//tfCntryRgn.getTfTextField().setHint(rsrc.getString(L10nConstants.keys.VYN_COUNTRYREGION));
			//tfCntryRgn.setFocusable(false);
			//c.addComponent(tfCntryRgn);
			//
			final MPBorderlessLabel lblNmbr = new MPBorderlessLabel(rsrc.getString(L10nConstants.keys.VYN_PHONENUMBER));
			StyleHelpers.setMargin(lblNmbr, 5, 0);
			StyleHelpers.setPadding(lblNmbr, 1, 1);
			c.addComponent(lblNmbr);
			//
			final MPTextField tf = new MPTextField();//tfPhnNmbr.getTfTextField();
			StyleHelpers.setMargin(tf, 0, 5);
			StyleHelpers.setPadding(tf, 5, 5);
			tf.setFocusable(true);
			tf.setHint(rsrc.getString(L10nConstants.keys.VYN_ENTERNUMBER));
			tf.setInputMode("123");
			tf.setConstraint(TextArea.PHONENUMBER);
			tf.setName("PHNNMBR");
			tf.addActionListener(this);
			//tfPhnNmbr.setSelectCommandText(rsrc.getString(L10nConstants.keys.COMMAND_OK));
			c.addComponent(tf);
			//
			final MPGrowTextArea gtaVerifyText = new MPGrowTextArea(rsrc.getString(L10nConstants.keys.VYN_TEXT));
			gtaVerifyText.setFocusable(true);
			StyleHelpers.setMargin(gtaVerifyText, 5, 0);
			StyleHelpers.setPadding(gtaVerifyText, 0, 0);
			c.addComponent(gtaVerifyText);
			//
			Label ll =new Label();
			ll.setPreferredH(Display.getInstance().getDisplayHeight() / 3);
			ll.setPreferredW(Display.getInstance().getDisplayWidth());
			ll.setFocusable(true);
			c.addComponent(ll);
			//containers[PMD1_ENTRY] = cntnr;
			//
			replaceContent(getContentPane(), c, null);
			//setDefaultCommand(null);
			addCommand(okCommand);
			tf.requestFocus();
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
			//if (containers[PMD1_CORRECT] == null)
			final Container c = new Container(new BorderLayout());
			//
			tfPhnNmbrVrfy = new MPTextAreaWithTitle(rsrc.getString(L10nConstants.keys.VYN_NUMBERCORRECT), userPhoneNumber);
			tfPhnNmbrVrfy.setSelectCommandText(rsrc.getString(L10nConstants.keys.COMMAND_OK));
			tfPhnNmbrVrfy.setTitleAtTop(false);
			c.addComponent(BorderLayout.CENTER, tfPhnNmbrVrfy);
			
			Label ll = new Label();
			ll.setPreferredH(Display.getInstance().getDisplayHeight());
			ll.setPreferredW(Display.getInstance().getDisplayWidth());
			ll.setFocusable(true);
			
			tfPhnNmbrVrfy.setFocusable(true);
			replaceContent(getContentPane(), c, null);
			addCommand(okCommand);
			addCommand(changeCommand);
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
			final String cmpName = cmp.getName();
			if (cmpName != null)
			{
				if (cmp.getName().equals("PHNNMBR"))
				{
					userPhoneNumber = ((MPTextField) cmp).getText();
					//if (isEntered())
					//{
					//	showVerifyScreen();
					//}
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
	}

	private boolean isEntered()
	{
		return ((userPhoneNumber != null) && (userPhoneNumber.length() > 0));
	}
	/*
	 * private void showHideOK() { final int cnt = getCommandCount(); for (int i
	 * = 0; i < cnt; ++i) { if (getCommand(i) == okCommand) { if (isEntered()) {
	 * } else { removeCommand(okCommand); } repaint(); return; } } if
	 * (isEntered()) { addCommand(okCommand, 0); repaint(); } }
	 */
}
