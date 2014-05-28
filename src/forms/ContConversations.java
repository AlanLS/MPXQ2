package forms;

import java.util.Hashtable;
import lcl.L10nConstants;
import main.GlobalData;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.GenericListCellRenderer;
import components.Fonts;
import components.MPBorderlessLabel;
import components.MPForm;
import components.MPListContainer;
import components.StyleHelpers;

public class ContConversations extends MPListContainer
{
	private Container		cntrHello	= null;
	private Command			cmdNewChat	= null;
	private Command			cmdProfile	= null;
	private Command			cmdDelete	= null;
	private Command			cmdSettings	= null;
	private Command			cmdSearch	= null;
	private Command			cmdSelect	= null;
	private final Button	btnHello	= null;

	public ContConversations(mainTab _parentForm)
	{
		super(_parentForm);
		setScrollable(false);
		setLayout(new BorderLayout());
		setFocusable(false);
		//addFocusListener(this);
		cmdNewChat = new Command(rsrc.getString(L10nConstants.keys.COMMAND_NEWCHAT));
		cmdProfile = new Command(rsrc.getString(L10nConstants.keys.COMMAND_PROFILE));
		cmdDelete = new Command(rsrc.getString(L10nConstants.keys.COMMAND_DELETE));
		cmdSettings = new Command(rsrc.getString(L10nConstants.keys.COMMAND_SETTINGS));
		cmdSearch = new Command(rsrc.getString(L10nConstants.keys.COMMAND_SEARCH));
		cmdSelect = new Command(rsrc.getString(L10nConstants.keys.COMMAND_SELECT));
		//deInitializeThis();
		//
		parentForm.addCommandListener(this);
		((mainTab) parentForm).lastListener = this;
		//
		parentForm.addCommand(cmdSearch);
		parentForm.addCommand(cmdSettings);
		parentForm.addCommand(cmdDelete);
		parentForm.addCommand(cmdProfile);
		parentForm.addCommand(cmdNewChat);
		//
		//setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		StyleHelpers.removeMargins(this);
		StyleHelpers.removePadding(this);
		//
		cntrHello = new Container(new BorderLayout());
		cntrHello.setUIID("ListRenderer");
		StyleHelpers.removeMargins(cntrHello);
		StyleHelpers.removePadding(cntrHello);
		StyleHelpers.setBgColor(cntrHello, 0xFFFFFF);
		cntrHello.setFocusable(true);
		cntrHello.addFocusListener(this);
		final Image img = Image.createImage(40, 40, 0x777777);
		final Button btnHello = new Button(img);
		btnHello.setUIID("Label");
		cntrHello.setLabelForComponent(btnHello);
		StyleHelpers.setBorder(btnHello, null);
		StyleHelpers.removeMargins(btnHello);
		StyleHelpers.removePadding(btnHello);
		btnHello.setFocusable(true);
		btnHello.addFocusListener(this);
		//cntrHello.setLeadComponent(btnHello);
		btnHello.addActionListener(this);
		cntrHello.addComponent(BorderLayout.WEST, btnHello);
		//
		final Container cntnrText = new Container(new BorderLayout());
		StyleHelpers.removeMargins(cntnrText);
		StyleHelpers.removePadding(cntnrText);
		StyleHelpers.setBgTransparency(cntnrText, 255);
		final MPBorderlessLabel lblName = new MPBorderlessLabel();
		lblName.setText(GlobalData.getRsrc().getString(L10nConstants.keys.CONV_HELLO));
		StyleHelpers.setFont(lblName, Fonts.SmallBoldFont);
		cntnrText.addComponent(BorderLayout.NORTH, lblName);
		//
		final MPBorderlessLabel lblMsg = new MPBorderlessLabel();
		lblMsg.setText(GlobalData.getRsrc().getString(L10nConstants.keys.CONV_WELCOME));
		cntnrText.addComponent(BorderLayout.SOUTH, lblMsg);
		//
		cntrHello.addComponent(BorderLayout.CENTER, cntnrText);
		addComponent(BorderLayout.NORTH, cntrHello);
		revalidate();
		//
		final Container cntnrList = new Container(new BorderLayout());
		cntnrList.setScrollable(false);
		//cntnrList.setScrollableX(false);
		theList = new List(createGenericData());
		theList.setRenderer(new GenericListCellRenderer(createRendererContainer(true), createRendererContainer(false)));
		theList.setSelectedIndex(0);
		StyleHelpers.removeMargins(theList);
		StyleHelpers.removePadding(theList);
		theList.setFocusable(true);
		theList.addActionListener(this);
		//theList.setFixedSelection(List.);
		cntnrList.addComponent(BorderLayout.NORTH, theList);
		addComponent(BorderLayout.CENTER, cntnrList);
		//
//		cntrHello.setLeadComponent(btnHello);
		cntrHello.setLeadComponent(btnHello);
		cntrHello.requestFocus();
		revalidate();
		repaint();
	}

	private Container createRendererContainer(boolean highlight)
	{
		final Container c = new Container(new BorderLayout());
		c.setUIID("ListRenderer");
		StyleHelpers.removeMargins(c);
		StyleHelpers.removePadding(c);
		//
		final Image img = Image.createImage(40, 40, 0x777777);
		final Label lblImg = new Label(img);
		lblImg.setName("Image_fixed");
		StyleHelpers.removeMargins(lblImg);
		StyleHelpers.removePadding(lblImg);
		//
		c.addComponent(BorderLayout.WEST, lblImg);
		//
		/*
		 * final Container cntnrCnt = new Container(new BorderLayout());
		 * StyleHelpers.removeMargins(cntnrCnt);
		 * StyleHelpers.removePadding(cntnrCnt); final MPBorderlessLabel lblCnt
		 * = new MPBorderlessLabel("1/4"); //lblCnt.setFocusable(true);
		 * lblCnt.setName("Count_fixed"); StyleHelpers.removeMargins(lblCnt);
		 * StyleHelpers.removePadding(lblCnt);
		 * cntnrCnt.addComponent(BorderLayout.CENTER, lblCnt);
		 * c.addComponent(BorderLayout.EAST, cntnrCnt);
		 *///
		final Container cntnrText = new Container(new BorderLayout());
		StyleHelpers.removeMargins(cntnrText);
		StyleHelpers.removePadding(cntnrText);
		final MPBorderlessLabel lblName = new MPBorderlessLabel();
		lblName.setName("Name");
		StyleHelpers.setFont(lblName, Fonts.SmallBoldFont);
		cntnrText.addComponent(BorderLayout.NORTH, lblName);
		//
		final MPBorderlessLabel lblMsg = new MPBorderlessLabel();
		lblMsg.setName("Message");
		cntnrText.addComponent(BorderLayout.SOUTH, lblMsg);
		//
		c.addComponent(BorderLayout.CENTER, cntnrText);
		//
		final Container cntnrTimeAttach = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		StyleHelpers.removeMargins(cntnrTimeAttach);
		StyleHelpers.removePadding(cntnrTimeAttach);
		//
		final MPBorderlessLabel lblTime = new MPBorderlessLabel();
		int tpbtm = lblTime.getStyle().getMargin(Component.TOP);
		StyleHelpers.setMargin(lblTime, tpbtm, tpbtm, 0, 0);
		tpbtm = lblTime.getStyle().getPadding(Component.TOP);
		StyleHelpers.setPadding(lblTime, tpbtm, tpbtm, 0, 0);
		StyleHelpers.setFgColor(lblTime, 0xB0B0B0);
		StyleHelpers.setAlignment(lblTime, Component.RIGHT);
		lblTime.setText("13:22");
		cntnrTimeAttach.addComponent(lblTime);
		//
		final MPBorderlessLabel lblAttach = new MPBorderlessLabel();
		tpbtm = lblAttach.getStyle().getMargin(Component.TOP);
		StyleHelpers.setMargin(lblAttach, tpbtm, tpbtm, 0, 0);
		tpbtm = lblAttach.getStyle().getPadding(Component.TOP);
		StyleHelpers.setPadding(lblAttach, tpbtm, tpbtm, 0, 0);
		StyleHelpers.setFgColor(lblAttach, 0xB0B0B0);
		StyleHelpers.setAlignment(lblAttach, Component.CENTER);
		lblAttach.setText("A");
		cntnrTimeAttach.addComponent(lblAttach);
		//
		c.addComponent(BorderLayout.EAST, cntnrTimeAttach);
		if (highlight)
		{
			StyleHelpers.setBorder(c, MPForm.yllwBorder);
		}
		else
		{
			StyleHelpers.setBorder(c, null);
		}
		//
		return c;
	}

	private static Hashtable[] createGenericData()
	{
		final Hashtable[] data = new Hashtable[6];
		data[0] = new Hashtable();
		data[0].put("Name", "John McDonough");
		data[0].put("Message", "This is a test message.");
		//
		data[1] = new Hashtable();
		data[1].put("Name", "David Chang");
		data[1].put("Message", "If you can read this, your nose is too damn close.");
		//
		data[2] = new Hashtable();
		data[2].put("Name", "Carl Wright");
		data[2].put("Message", "To be or not to be, that is the question.");
		//
		data[3] = new Hashtable();
		data[3].put("Name", "Surendranath");
		data[3].put("Message", "Ipsum lorem");
		//
		data[4] = new Hashtable();
		data[4].put("Name", "Sasi-Hakunamatata");
		data[4].put("Message", "Once upon a midnight dreary, while I pondered weak and weary");
		//
		data[5] = new Hashtable();
		data[5].put("Name", "Alan VeryLongNameHere Shapiro");
		data[5].put("Message", "'Twas brillig and the slithy toves did gyre and gimble in the wabe");
		return data;
	}

	public void selectionChanged(int arg0, int arg1)
	{
		// TODO Auto-generated method stub
	}

	public void focusGained(Component arg0)
	{
		//super.focusGained();
		System.out.println(">>>> " + arg0 + " ||| " + arg0.getName());
		if (arg0 == cntrHello)// || (arg0 instanceof Button))
		{
			StyleHelpers.setBorder(cntrHello, MPForm.yllwBorder);
			/*	parentForm.removeAllCommands();
				parentForm.addCommand(cmdSelect);
				parentForm.addCommand(cmdSearch);
				parentForm.addCommand(cmdSettings);
				parentForm.addCommand(cmdDelete);
				parentForm.addCommand(cmdProfile);
				parentForm.addCommand(cmdNewChat);
				*/
			//parentForm.revalidate();
			//parentForm.repaint();
		}
	}

	public void focusLost(Component arg0)
	{
		//super.focusLost();
		System.out.println("Focus lost on= " + arg0);
		if ((arg0 == cntrHello) || (arg0.getParent() == cntrHello))
		{
			StyleHelpers.setBorder(cntrHello, null);
			/*	parentForm.removeCommand(cmdSelect);
				parentForm.revalidate();
				parentForm.repaint();
			*/}
	}

	public void actionPerformed(ActionEvent arg0)
	{
		final Component cmp = arg0.getComponent();
		final Command cmd = arg0.getCommand();
		System.out.println("cmd= " + cmd + "  cmp= " + cmp);
		if (cmp != null)
		{
			if (arg0.getComponent().getParent() == cntrHello)
			{
				System.out.println("hello container selected");
			}
		}
		else if (cmd != null)
		{
			if (cmd == cmdSelect)
			{
			}
			System.out.println("CONVERSATIONS_ " + cmd.getCommandName());
		}
	}

	public void initializeThis()
	{
		// TODO Auto-generated method stub
	}
}
