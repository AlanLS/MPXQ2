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
import components.MPBorderlessLabel;
import components.MPForm;
import components.MPGrowTextArea;
import components.MPTextField;
import components.StyleHelpers;

/**
 * @author Alan
 * 
 */
public class FormOOBE extends MPForm
{
	protected final int		OOBE_UNINITTED	= -1;
	protected final int		OOBE_WELCOME	= 0;
	protected final int		OOBE_YOURNAME	= 1;
	protected final int		OOBE_TAKECHOOSE	= 2;
	protected final int		OOBE_CHOOSE		= 3;
	protected final int		OOBE_TAKE		= 4;
	protected final int		OOBE_SHOWPIC	= 5;
	protected final int		OOBE_COUNT		= 6;
	protected int			OOBE_State		= OOBE_UNINITTED;
	//protected Container[]			containers		= new Container[OOBE_COUNT];
	//
	private final Command	cmdNext			= new Command(rsrc.getString(L10nConstants.keys.COMMAND_NEXT));
	private final Command	cmdOK			= new Command(rsrc.getString(L10nConstants.keys.COMMAND_OK));
	private final Command	cmdUse			= new Command(rsrc.getString(L10nConstants.keys.COMMAND_USE));
	private final Command	cmdBack			= new Command(rsrc.getString(L10nConstants.keys.COMMAND_BACK));
	//private CheckBox				cbCheckbox		= null;
	//private MPTextFieldWithTitle	tfYourName		= null;
	//private Button					btnTake			= null;
	//private Button					btnSelect		= null;
	private String			userName		= null;
	private Image			userImage		= null;

	//private Button					btnImage		= null;
	/**
	 * 
	 */
	public FormOOBE()
	{
		// TODO Auto-generated constructor stub
		setScrollable(false);
	}

	// private Command cmdLogin = null;
	public void show()
	{
		super.show();
	}

	protected void onShowCompleted()
	{
		super.onShowCompleted();
		if (OOBE_State == OOBE_UNINITTED)
		{
			showWelcomeScreen();
		}
	}

	private void showWelcomeScreen()
	{
		if (OOBE_State != OOBE_WELCOME)
		{
			removeAllCommands();
			OOBE_State = OOBE_WELCOME;
			final Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
			final Label l = new Label("");
			//l.setPreferredH(Display.getInstance().getDisplayHeight() / 3);
			l.setPreferredW(Display.getInstance().getDisplayWidth());
			c.addComponent(l);
			//
			final MPGrowTextArea gtaWelcome = new MPGrowTextArea(rsrc.getString(L10nConstants.keys.OOBE_WELCOMETO));
			c.addComponent(gtaWelcome);
			//
			final Container bottomCntnr = new Container(new BorderLayout());
			final CheckBox cbCheckbox = new CheckBox();
			bottomCntnr.addComponent(BorderLayout.WEST, cbCheckbox);
			//
			final MPGrowTextArea gtaShortcut = new MPGrowTextArea(rsrc.getString(L10nConstants.keys.OOBE_MAKESHORTCUT));
			bottomCntnr.addComponent(BorderLayout.CENTER, gtaShortcut);
			//
			c.addComponent(bottomCntnr);
			//
			// bottomCntnr.addComponent(BorderLayout.SOUTH, new
			// pageIndicator(0));
			//
			//containers[OOBE_WELCOME] = cntnr;
			//
			replaceContent(getContentPane(), c, null);
			addCommand(cmdNext);
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
			final Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
			//final Container bottomCntnr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
			//
			final Label l = new Label("");
			l.setPreferredH(Display.getInstance().getDisplayHeight() / 3);
			l.setPreferredW(Display.getInstance().getDisplayWidth());
			l.setFocusable(true);
			c.addComponent( l);
			//
			final MPBorderlessLabel lblName = new MPBorderlessLabel(rsrc.getString(L10nConstants.keys.OOBE_YOURNAME));
			StyleHelpers.setMargin(lblName, 5, 0);
			StyleHelpers.setPadding(lblName, 1, 1);
			c.addComponent(lblName);
			//
			final MPTextField tfYourName = new MPTextField();//tfPhnNmbr.getTfTextField();
			StyleHelpers.setMargin(tfYourName, 0, 5);
			StyleHelpers.setPadding(tfYourName, 5, 5);
			tfYourName.setFocusable(true);
			tfYourName.setHint(rsrc.getString(L10nConstants.keys.OOBE_YOURNAME));
			tfYourName.setName("tfYourName");
			tfYourName.addActionListener(this);
			c.addComponent(tfYourName);
			//
			final Label ll = new Label("");
			ll.setPreferredH(Display.getInstance().getDisplayHeight() / 3);
			ll.setPreferredW(Display.getInstance().getDisplayWidth());
			ll.setFocusable(true);
			c.addComponent(ll);

			replaceContent(getContentPane(), c, null);
			tfYourName.requestFocus();
			addCommand(cmdNext);
			revalidate();
			repaint();
		}
	}

	private void showChoosePictureScreen()
	{
		if (OOBE_State != OOBE_TAKECHOOSE)
		{
			getFocused().setFocus(false);
			removeAllCommands();
			OOBE_State = OOBE_TAKECHOOSE;
			final Container c = new Container(new BorderLayout());
			final Container bottomCntnr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
			//StyleHelpers.setBgTransparency(bottomCntnr, 200);
			//StyleHelpers.setBgColor(bottomCntnr, 0xFFFFFF);
			final int wide = Display.getInstance().getDisplayWidth() / 2;
			final int high = Display.getInstance().getDisplayHeight() / 2;
			userImage = Image.createImage(wide, high, 0xFF0000);
			final Label lblImage = new Label(userImage);
			lblImage.setPreferredW(wide * 2);
			StyleHelpers.setAlignment(lblImage, Component.CENTER);
			lblImage.setFocusable(false);
			bottomCntnr.addComponent(lblImage);
			//
			// Label l = new Label("");
			// l.setPreferredH(Display.getInstance().getDisplayHeight() /
			// 3);
			// cntnr.addComponent(BorderLayout.NORTH, l);
			//
			final Button btnTake = new Button(rsrc.getString(L10nConstants.keys.OOBE_TAKEAPHOTO));
			btnTake.setName("btnTake");
			btnTake.addActionListener(this);
			btnTake.addFocusListener(this);
			StyleHelpers.setAlignment(btnTake, Component.CENTER);
			bottomCntnr.addComponent(btnTake);
			//
			final Button btnSelect = new Button(rsrc.getString(L10nConstants.keys.OOBE_SELECTYOURPHOTO));
			btnSelect.setName("btnSelect");
			btnSelect.addActionListener(this);
			btnSelect.addFocusListener(this);
			StyleHelpers.setAlignment(btnSelect, Component.CENTER);
			bottomCntnr.addComponent(btnSelect);
			//
			c.addComponent(BorderLayout.CENTER, bottomCntnr);
			//
			// cntnr.addComponent(BorderLayout.SOUTH, new pageIndicator(2));
			//
			//containers[OOBE_TAKECHOOSE] = cntnr;
			//
			replaceContent(getContentPane(), c, null);
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
			final Container c = new Container(new BorderLayout());
			final Container bottomCntnr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
			//StyleHelpers.setBgTransparency(bottomCntnr, 200);
			//StyleHelpers.setBgColor(bottomCntnr, 0xFFFFFF);
			final int wide = Display.getInstance().getDisplayWidth() / 2;
			final int high = Display.getInstance().getDisplayHeight() / 2;
			final Label lblImage = new Label(userImage);
			lblImage.setPreferredW(wide * 2);
			StyleHelpers.setAlignment(lblImage, Component.CENTER);
			lblImage.setFocusable(false);
			bottomCntnr.addComponent(lblImage);
			//
			// Label l = new Label("");
			// l.setPreferredH(Display.getInstance().getDisplayHeight() /
			// 3);
			// cntnr.addComponent(BorderLayout.NORTH, l);
			//
			final Label blName = new Label(userName);
			StyleHelpers.setAlignment(blName, Component.CENTER);
			bottomCntnr.addComponent(blName);
			//
			c.addComponent(BorderLayout.CENTER, bottomCntnr);
			//
			replaceContent(getContentPane(), c, null);
			addCommand(cmdUse);
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
			else if (cmd == cmdUse)
			{
				new mainTab().show();
			}
		}
		else if (cmp != null)
		{
			final String cmpName = cmp.getName();
			if (cmpName != null)
			{
				if (cmpName.equals("btnTake"))
				{
					showShowPictureScreen();
				}
				else if (cmpName.equals("btnSelect"))
				{
					showShowPictureScreen();
				}
				else if (cmpName.equals("tfYourName"))
				{
					final TextField tf = (TextField) cmp;
					if ((tf.getText() != null) && (tf.getText().length() > 0))
					{
						userName = tf.getText();
					}
				}
			}
		}
	}
}
