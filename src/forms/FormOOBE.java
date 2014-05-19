/**
 * 
 */
package forms;

import lcl.L10nConstants;
import com.sun.lwuit.Button;
import com.sun.lwuit.CheckBox;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import components.MPForm;
import components.MPGrowTextArea;
import components.MPTextField;
import components.MPTextFieldWithTitle;
import components.StyleHelpers;

/**
 * @author Alan
 * 
 */
public class FormOOBE extends MPForm
{
	protected final int				OOBE_UNINITTED	= -1;
	protected final int				OOBE_WELCOME	= 0;
	protected final int				OOBE_YOURNAME	= 1;
	protected final int				OOBE_TAKECHOOSE	= 2;
	protected final int				OOBE_CHOOSE		= 3;
	protected final int				OOBE_TAKE		= 4;
	protected final int				OOBE_SHOWPIC	= 5;
	protected final int				OOBE_COUNT		= 6;
	protected int					OOBE_State		= OOBE_UNINITTED;
	protected Container[]			containers		= new Container[OOBE_COUNT];
	//
	private CheckBox				cbCheckbox		= null;
	// private MPTextFieldNoPrompt tfYourName = null;
	private MPTextFieldWithTitle	tfYourName		= null;
	private final Command			cmdNext			= new Command(rsrc.getString(L10nConstants.keys.COMMAND_NEXT));
	private final Command			cmdOK			= new Command(rsrc.getString(L10nConstants.keys.COMMAND_OK));
	private final Command			cmdUse			= new Command(rsrc.getString(L10nConstants.keys.COMMAND_USE));
	private final Command			cmdBack			= new Command(rsrc.getString(L10nConstants.keys.COMMAND_BACK));
	// private Label lblChoose = null;
	// private final Label lblImage = null;
	private Button					btnTake			= null;
	private Button					btnSelect		= null;
	private String					userName		= null;
	private Image					userImage		= null;
	private Button					dummy			= null;

	private class pageIndicator extends Container
	{
		pageIndicator(int idx)
		{
			final Container c = new Container(new BoxLayout(BoxLayout.X_AXIS));
			for (int i = 0; i < 3; ++i)
			{
				c.addComponent(new Label((idx == i) ? "O" : "-"));
			}
			StyleHelpers.setAlignment(c, Component.CENTER);
			addComponent(c);
		}
	}

	/**
	 * 
	 */
	public FormOOBE()
	{
		// TODO Auto-generated constructor stub
	}

	// private Command cmdLogin = null;
	public void show()
	{
		super.show();
		if (OOBE_State == OOBE_UNINITTED)
		{
			showWelcomeScreen();
		}
	}

	protected void onShowCompleted()
	{
		super.onShowCompleted();
	}

	private void showWelcomeScreen()
	{
		if (OOBE_State != OOBE_WELCOME)
		{
			removeAllCommands();
			OOBE_State = OOBE_WELCOME;
			if (containers[OOBE_WELCOME] == null)
			{
				final Container cntnr = new Container(new BorderLayout());
				final Container bottomCntnr = new Container(new BorderLayout());
				//
				final Label l = new Label("");
				l.setPreferredH(Display.getInstance().getDisplayHeight() / 3);
				cntnr.addComponent(BorderLayout.NORTH, l);
				final MPGrowTextArea gtaWelcome = new MPGrowTextArea(rsrc.getString(L10nConstants.keys.OOBE_WELCOMETO));
				bottomCntnr.addComponent(BorderLayout.NORTH, gtaWelcome);
				//
				final MPGrowTextArea gtaShortcut = new MPGrowTextArea(rsrc.getString(L10nConstants.keys.OOBE_MAKESHORTCUT));
				bottomCntnr.addComponent(BorderLayout.CENTER, gtaShortcut);
				//
				cbCheckbox = new CheckBox();
				bottomCntnr.addComponent(BorderLayout.WEST, cbCheckbox);
				//
				cntnr.addComponent(BorderLayout.CENTER, bottomCntnr);
				//
				// bottomCntnr.addComponent(BorderLayout.SOUTH, new
				// pageIndicator(0));
				//
				containers[OOBE_WELCOME] = cntnr;
			}
			//
			replaceContent(getContentPane(), containers[OOBE_WELCOME], null);
			setDefaultCommand(cmdNext);
			setBackCommand(cmdBack);
			cbCheckbox.requestFocus();
			revalidate();
			repaint();
		}
	}

	private void showYourNameScreen()
	{
		if (OOBE_State != OOBE_YOURNAME)
		{
			removeAllCommands();
			OOBE_State = OOBE_YOURNAME;
			if (containers[OOBE_YOURNAME] == null)
			{
				final Container cntnr = new Container(new BorderLayout());
				final Container bottomCntnr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
				//
				final Label l = new Label("");
				l.setPreferredH(Display.getInstance().getDisplayHeight() / 3);
				cntnr.addComponent(BorderLayout.NORTH, l);
				//
				tfYourName = new MPTextFieldWithTitle(rsrc.getString(L10nConstants.keys.OOBE_YOURNAME), "");
				TextField.setUseNativeTextInput(false);
				final MPTextField tf = tfYourName.getTfTextField();
				tf.setHint(rsrc.getString(L10nConstants.keys.OOBE_YOURNAME));
				tf.addActionListener(this);
				tfYourName.setSelectCommandText(rsrc.getString(L10nConstants.keys.COMMAND_OK));
				bottomCntnr.addComponent(tfYourName);
				//
				cntnr.addComponent(BorderLayout.CENTER, bottomCntnr);
				//
				// cntnr.addComponent(BorderLayout.SOUTH, new pageIndicator(1));
				//
				containers[OOBE_YOURNAME] = cntnr;
			}
			//
			replaceContent(getContentPane(), containers[OOBE_YOURNAME], null);
			setSelectCommand(cmdNext);
			revalidate();
			repaint();
		}
	}

	private void showChoosePictureScreen()
	{
		if (OOBE_State != OOBE_TAKECHOOSE)
		{
			removeAllCommands();
			OOBE_State = OOBE_TAKECHOOSE;
			if (containers[OOBE_TAKECHOOSE] == null)
			{
				final Container cntnr = new Container(new BorderLayout());
				final Container bottomCntnr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
				final int wide = Display.getInstance().getDisplayWidth() / 2;
				userImage = Image.createImage(wide, wide, 0xFF0000);
				final Label lblImage = new Label(userImage);
				StyleHelpers.setAlignment(lblImage, Component.CENTER);
				lblImage.setFocusable(false);
				bottomCntnr.addComponent(lblImage);
				//
				// Label l = new Label("");
				// l.setPreferredH(Display.getInstance().getDisplayHeight() /
				// 3);
				// cntnr.addComponent(BorderLayout.NORTH, l);
				//
				btnTake = new Button(rsrc.getString(L10nConstants.keys.OOBE_TAKEAPHOTO));
				btnTake.addActionListener(this);
				StyleHelpers.setAlignment(btnTake, Component.CENTER);
				bottomCntnr.addComponent(btnTake);
				//
				btnSelect = new Button(rsrc.getString(L10nConstants.keys.OOBE_SELECTYOURPHOTO));
				StyleHelpers.setAlignment(btnSelect, Component.CENTER);
				btnSelect.addActionListener(this);
				bottomCntnr.addComponent(btnSelect);
				//
				cntnr.addComponent(BorderLayout.CENTER, bottomCntnr);
				//
				// cntnr.addComponent(BorderLayout.SOUTH, new pageIndicator(2));
				//
				containers[OOBE_TAKECHOOSE] = cntnr;
			}
			//
			replaceContent(getContentPane(), containers[OOBE_TAKECHOOSE], null);
			setSelectCommand(cmdOK);
			btnTake.requestFocus();
			revalidate();
			repaint();
		}
	}

	private void showShowPictureScreen()
	{
		if (OOBE_State != OOBE_SHOWPIC)
		{
			removeAllCommands();
			OOBE_State = OOBE_SHOWPIC;
			if (containers[OOBE_SHOWPIC] == null)
			{
				// final Container cntnr = new Container(new BorderLayout());
				final Container cntnr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
				//
				final Label lblImage = new Label(userImage);
				StyleHelpers.setAlignment(lblImage, Component.CENTER);
				lblImage.setFocusable(false);
				lblImage.setSelectCommandText(rsrc.getString(L10nConstants.keys.COMMAND_USE));
				cntnr.addComponent(lblImage);
				//
				final Label l = new Label(userName);
				l.setFocusable(false);
				StyleHelpers.setAlignment(l, Component.CENTER);
				l.setSelectCommandText(rsrc.getString(L10nConstants.keys.COMMAND_USE));
				cntnr.addComponent(l);
				dummy = new Button();
				dummy.setUIID("Label");
				dummy.setFocusable(true);
				dummy.setSelectCommandText(rsrc.getString(L10nConstants.keys.COMMAND_OK));
				dummy.addActionListener(this);
				cntnr.addComponent(dummy);
				//
				// cntnr.addComponent(BorderLayout.CENTER, bottomCntnr);
				// cntnr.addComponent(BorderLayout.SOUTH, new pageIndicator(2));
				//
				containers[OOBE_SHOWPIC] = cntnr;
			}
			//
			replaceContent(getContentPane(), containers[OOBE_SHOWPIC], null);
			dummy.requestFocus();
			revalidate();
			repaint();
		}
	}

	public void actionPerformed(final ActionEvent arg0)
	{
		super.actionPerformed(arg0);
		if (cmd != null)
		{
			System.out.println(cmd.getCommandName());
			if (cmd == cmdNext)
			{
				if (OOBE_State == OOBE_WELCOME)
				{
					showYourNameScreen();
				}
				else if (OOBE_State == OOBE_YOURNAME)
				{
					showChoosePictureScreen();
				}
			}
			else if (cmd == cmdOK)
			{
				if (OOBE_State == OOBE_TAKECHOOSE)
				{
					showShowPictureScreen();
				}
			}
			else if (cmd == cmdBack)
			{
				showWelcomeScreen();
			}
		}
		else if (cmp != null)
		{
			MPTextField tf;
			if (cmp == btnTake)
			{
				showShowPictureScreen();
			}
			else if (cmp == btnSelect)
			{
				showShowPictureScreen();
			}
			else if (cmp == (tf = tfYourName.getTfTextField()))
			{
				if ((tf.getText() != null) && (tf.getText().length() > 0))
				{
					userName = tf.getText();
					showChoosePictureScreen();
				}
			}
			else if (cmp == dummy)
			{
				if (OOBE_State == OOBE_SHOWPIC)
				{
					new mainTab().show();
					showShowPictureScreen();
				}
			}
		}
	}
}
