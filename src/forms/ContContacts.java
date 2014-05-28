package forms;

import java.util.Hashtable;
import java.util.Random;
import lcl.L10nConstants;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.list.GenericListCellRenderer;
import components.Fonts;
import components.MPBorderlessLabel;
import components.MPForm;
import components.MPListContainer;
import components.StyleHelpers;

public class ContContacts extends MPListContainer
{
	private List	theList			= null;
	private Command	cmdSendMsg		= null;
	private Command	cmdAddToShout	= null;
	private Command	cmdAddToGroup	= null;

	public ContContacts(mainTab _parentForm)
	{
		super(_parentForm);
		setLayout(new BorderLayout());
		setScrollable(false);
		StyleHelpers.removeMargins(this);
		StyleHelpers.removePadding(this);
		cmdSendMsg = new Command(rsrc.getString(L10nConstants.keys.COMMAND_SENDMESSAGE));
		cmdAddToGroup = new Command(rsrc.getString(L10nConstants.keys.COMMAND_ADDTOGROUP));
		cmdAddToShout = new Command(rsrc.getString(L10nConstants.keys.COMMAND_ADDTOSHOUT));
		parentForm.addCommandListener(this);
		((mainTab) parentForm).lastListener = this;
		//
		parentForm.addCommand(cmdAddToShout);
		parentForm.addCommand(cmdAddToGroup);
		parentForm.addCommand(cmdSendMsg);
		//
		theList = new List(createGenericData());
		StyleHelpers.removeMargins(theList);
		StyleHelpers.removePadding(theList);
		theList.setFocusable(true);
		theList.addActionListener(this);
		addComponent(BorderLayout.CENTER, theList);
		theList.requestFocus();
		theList.setRenderer(new GenericListCellRenderer(createRendererContainer(true), createRendererContainer(false)));
		//
	}

	private Container createRendererContainer(boolean highlight)
	{
		/*		final Container c = new Container(new BoxLayout(BoxLayout.X_AXIS));
				c.setUIID("ListRenderer");
				//StyleHelpers.removeMargins(c);
				StyleHelpers.removePadding(c);
				//
				final Image img = Image.createImage(40, 40, 0x777777);
				final Label lblImg = new Label(img);
				lblImg.setName("Image_fixed");
				StyleHelpers.removeMargins(lblImg);
				StyleHelpers.removePadding(lblImg);
				c.addComponent(lblImg);
			
				final MPBorderlessLabel lblName = new MPBorderlessLabel();
				lblName.setName("Name");
				StyleHelpers.setFont(lblName, Fonts.SmallBoldFont);
				//cntnrText.addComponent(BorderLayout.NORTH, lblName);
				c.addComponent(lblName);
				
				return c;
			}
			*/
		final Container c = new Container(new BorderLayout());
		c.setUIID("ListRenderer");
		c.setScrollable(false);
		StyleHelpers.removePadding(c);
		//
		final Image img = Image.createImage(40, 40, 0x777777);
		final Label lblImg = new Label(img);
		lblImg.setName("Image_fixed");
		StyleHelpers.removeMargins(lblImg);
		//StyleHelpers.removePadding(lblImg);
		//
		c.addComponent(BorderLayout.WEST, lblImg);
		//
		final Container cntnrText = new Container(new BorderLayout());
		//StyleHelpers.removeMargins(cntnrText);
		StyleHelpers.removePadding(cntnrText);
		//
		final MPBorderlessLabel lblName = new MPBorderlessLabel();
		lblName.setPreferredW(Display.getInstance().getDisplayWidth());
		StyleHelpers.removeMargins(lblName);
		StyleHelpers.removePadding(lblName);
		lblName.setName("Name");
		StyleHelpers.setFont(lblName, Fonts.SmallBoldFont);
		cntnrText.addComponent(BorderLayout.CENTER, lblName);
		//
		c.addComponent(BorderLayout.CENTER, cntnrText);
		final MPBorderlessLabel lblType = new MPBorderlessLabel();
		int tpbtm = lblType.getStyle().getMargin(Component.TOP);
		StyleHelpers.setMargin(lblType, tpbtm, tpbtm, 0, 0);
		tpbtm = lblType.getStyle().getPadding(Component.TOP);
		StyleHelpers.setPadding(lblType, tpbtm, tpbtm, 0, 0);
		StyleHelpers.setFgColor(lblType, 0xB0B0B0);
		StyleHelpers.setAlignment(lblType, Component.RIGHT);
		lblType.setName("Type");
		c.addComponent(BorderLayout.EAST, lblType);
		//
		/*final MPBorderlessLabel lblAttch = new MPBorderlessLabel();
		tpbtm = lblAttch.getStyle().getMargin(Component.TOP);
		StyleHelpers.setMargin(lblAttch, tpbtm, tpbtm, 0, 0);
		tpbtm = lblAttch.getStyle().getPadding(Component.TOP);
		StyleHelpers.setPadding(lblAttch, tpbtm, tpbtm, 0, 0);
		StyleHelpers.setFgColor(lblAttch, 0xB0B0B0);
		StyleHelpers.setAlignment(lblAttch, Component.CENTER);
		lblAttch.setText("A");
		cntnrTimeAttch.addComponent(lblAttch);
		//
		c.addComponent(BorderLayout.EAST, cntnrTimeAttch);
		*///
		if (highlight)
		{
			StyleHelpers.setBorder(c, MPForm.yllwBorder);
		}
		else
		{
			StyleHelpers.setBorder(c, null);
		}
		return c;
	}

	private static Hashtable[] createGenericData()
	{
		final Random r = new Random();
		final Hashtable[] data = new Hashtable[12];
		data[0] = new Hashtable();
		data[0].put("Name", "John McDonough");
		data[0].put("Type", (r.nextLong() & 1) == 0 ? "m+" : "sms");
		//
		data[1] = new Hashtable();
		data[1].put("Name", "David Chang");
		data[1].put("Type", (r.nextLong() & 1) == 0 ? "m+" : "sms");
		//
		data[2] = new Hashtable();
		data[2].put("Name", "Carl Wright");
		data[2].put("Type", (r.nextLong() & 1) == 0 ? "m+" : "sms");
		//
		data[3] = new Hashtable();
		data[3].put("Name", "Surendranath");
		data[3].put("Type", (r.nextLong() & 1) == 0 ? "m+" : "sms");
		//
		data[4] = new Hashtable();
		data[4].put("Name", "Sasi-Hakunamatata");
		data[4].put("Type", (r.nextLong() & 1) == 0 ? "m+" : "sms");
		//
		data[5] = new Hashtable();
		data[5].put("Name", "Alan VeryLongNameHere Shapiro");
		data[5].put("Type", (r.nextLong() & 1) == 0 ? "m+" : "sms");
		data[6] = new Hashtable();
		data[6].put("Name", "John McDonough");
		data[6].put("Type", (r.nextLong() & 1) == 0 ? "m+" : "sms");
		//
		data[7] = new Hashtable();
		data[7].put("Name", "David Chang");
		data[7].put("Type", (r.nextLong() & 1) == 0 ? "m+" : "sms");
		//
		data[8] = new Hashtable();
		data[8].put("Name", "Carl Wright");
		data[8].put("Type", (r.nextLong() & 1) == 0 ? "m+" : "sms");
		//
		data[9] = new Hashtable();
		data[9].put("Name", "Surendranath");
		data[9].put("Type", (r.nextLong() & 1) == 0 ? "m+" : "sms");
		//
		data[10] = new Hashtable();
		data[10].put("Name", "Sasi-Hakunamatata");
		data[10].put("Type", (r.nextLong() & 1) == 0 ? "m+" : "sms");
		//
		data[11] = new Hashtable();
		data[11].put("Name", "Alan VeryLongNameHere Shapiro");
		data[11].put("Type", (r.nextLong() & 1) == 0 ? "m+" : "sms");
		return data;
	}

	public void selectionChanged(int arg0, int arg1)
	{
		System.out.println("Selection Change From " + arg0 + " to " + arg1);
	}

	public void focusGained(Component arg0)
	{
		//StyleHelpers.setBorder(arg0, MPForm.yllwBorder);
		System.out.println("Focus Gain" + arg0);
	}

	public void focusLost(Component arg0)
	{
		//StyleHelpers.setBorder(arg0, null);
		System.out.println("Focus Lost" + arg0);
	}

	public void actionPerformed(ActionEvent arg0)
	{
		final Component cmp = arg0.getComponent();
		final Command cmd = arg0.getCommand();
		System.out.println("cmd= " + cmd + "  cmp= " + cmp);
		if (cmp == theList)
		{
			System.out.println("instanceof  " + theList.getSelectedItem().getClass());
			if (theList.getSelectedItem() instanceof Hashtable)
			{
				final Hashtable ht = (Hashtable) theList.getSelectedItem();
				Object bln = ht.get("ChkBx");
				System.out.println("cb instanceof  " + (bln == null ? bln : bln.getClass()));
				if ((bln != null) && (bln instanceof Boolean))
				{
					final boolean b = ((Boolean) bln).booleanValue();
					bln = new Boolean(!b);
					ht.remove("ChkBx");
					ht.put("ChkBx", bln);
				}
			}
		}
	}
}
