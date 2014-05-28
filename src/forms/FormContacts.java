package forms;

import java.util.Hashtable;
import lcl.L10nConstants;
import com.sun.lwuit.CheckBox;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.SelectionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.list.ListModel;
import components.Fonts;
import components.MPBorderlessLabel;
import components.MPForm;
import components.MPTextField;
import components.StyleHelpers;

public class FormContacts extends MPForm implements SelectionListener
{
	private List				theList		= null;
	private final MPTextField	txtArea		= new MPTextField();
	private final Command		cmdDone		= new Command(rsrc.getString(L10nConstants.keys.COMMAND_DONE));
	private final Command		cmdSelect	= new Command("");

	public FormContacts()
	{
		super();
		setTheTitleCount();
	}

	public void show()
	{
		super.show();
		final Container c = new Container(new BorderLayout());
		//
		theList = new List(createModelData());
		theList.addActionListener(this);
		theList.addSelectionListener(this);
		theList.setRenderer(new ContactChecklistCellRenderer(createRendererContainer(), createRendererContainer()));
		theList.setPreferredH((getContentPane().getHeight() - (txtArea.getPreferredH() * 2)));
		theList.setFocusable(true);
		theList.addFocusListener(this);
		c.addComponent(BorderLayout.CENTER, theList);
		c.addComponent(BorderLayout.SOUTH, txtArea);
		replaceContent(getContentPane(), c, null);
		setTheTitleCount();
		//
	}

	protected void onShowCompleted()
	{
		super.onShowCompleted();
		System.out.println("ShowCOmpl");
		//
	}

	private void setTheTitleCount()
	{
		String str = null;
		int cnt = 0;
		int sz = 0;
		if (theList != null)
		{
			final ListModel lm = theList.getModel();
			if (lm != null)
			{
				sz = lm.getSize();
				for (int i = 0; i < sz; ++i)
				{
					//System.out.println(">>" + lm.getItemAt(i) + "   " + lm.getItemAt(i).getClass());
					final Hashtable ht = (Hashtable) lm.getItemAt(i);
					final Object b = ht.get("ChkBx");
					if (b != null)
					{
						System.out.println("ChkBx = " + b.toString());
						if (b.toString().equalsIgnoreCase("true"))
						{
							cnt++;
						}
					}
				}
			}
		}
		if (cnt > 0)
		{
			str = "(" + cnt + ')';
			removeCommand(cmdDone);
			addCommand(cmdDone);
		}
		else
		{
			str = "";
			removeCommand(cmdDone);
		}
		setTitle(rsrc.getString(L10nConstants.keys.GRP_SELECTCONTACTS, new String[] { str }));
	}

	private Container createRendererContainer()
	{
		final Container c = new Container(new BorderLayout());
		c.setUIID("ListRenderer");
		StyleHelpers.removePadding(c);
		//
		final MPBorderlessLabel lblName = new MPBorderlessLabel();
		lblName.setName("Name");
		StyleHelpers.setFont(lblName, Fonts.SmallBoldFont);
		c.addComponent(BorderLayout.CENTER, lblName);
		//
		final CheckBox cb = new CheckBox();
		cb.setName("ChkBx");
		c.addComponent(BorderLayout.WEST, cb);
		//
		//final MPBorderlessLabel lblMsg = new MPBorderlessLabel();
		// lblMsg.setFocusable(true);
		//lblMsg.setName("Message");
		//cntnrText.addComponent(BorderLayout.SOUTH, lblMsg);
		//
		//c.addComponent(BorderLayout.CENTER, cntnrText);
		return c;
	}

	private static Hashtable[] createModelData()
	{
		final Hashtable[] data = new Hashtable[12];
		data[0] = new Hashtable();
		data[0].put("Name", "John McDonough");
		data[0].put("ChkBx", Boolean.FALSE);
		//
		data[1] = new Hashtable();
		data[1].put("Name", "David Chang");
		data[1].put("ChkBx", Boolean.FALSE);
		//
		data[2] = new Hashtable();
		data[2].put("Name", "Carl Wright");
		data[2].put("ChkBx", Boolean.FALSE);
		//
		data[3] = new Hashtable();
		data[3].put("Name", "Surendranath");
		data[3].put("ChkBx", Boolean.FALSE);
		//
		data[4] = new Hashtable();
		data[4].put("Name", "Sasi-Hakunamatata");
		data[4].put("ChkBx", Boolean.FALSE);
		//
		data[5] = new Hashtable();
		data[5].put("Name", "Alan VeryLongNameHere Shapiro");
		data[5].put("ChkBx", Boolean.FALSE);
		//
		data[6] = new Hashtable();
		data[6].put("Name", "John McDonough II");
		data[6].put("ChkBx", Boolean.FALSE);
		//
		data[7] = new Hashtable();
		data[7].put("Name", "David Chang II");
		data[7].put("ChkBx", Boolean.FALSE);
		//
		data[8] = new Hashtable();
		data[8].put("Name", "Carl Wright II");
		data[8].put("ChkBx", Boolean.FALSE);
		//
		data[9] = new Hashtable();
		data[9].put("Name", "Surendranath II");
		data[9].put("ChkBx", Boolean.FALSE);
		//
		data[10] = new Hashtable();
		data[10].put("Name", "Sasi-Hakunamatata II");
		data[10].put("ChkBx", Boolean.FALSE);
		//
		data[11] = new Hashtable();
		data[11].put("Name", "Alan Shapiro II");
		data[11].put("ChkBx", Boolean.FALSE);
		//for (int i=0; i< data.length; ++i)
		//{
		//	Hashtable ht = data[i];
		//	ht.put("$navigation", cmdSelect);
		//}
		return data;
	}

	public void selectionChanged(int arg0, int arg1)
	{
		System.out.println("Selection Changed arg0= " + arg0 + "  arg1= " + arg1);
	}

	public void actionPerformed(final ActionEvent arg0)
	{
		super.actionPerformed(arg0);
		if (cmd != null)
		{
			if (cmd == cmdDone)
			{
				new mainTab().show();
			}
		}
		else if (cmp != null)
		{
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
						setTheTitleCount();
					}
				}
				revalidate();
				repaint();
			}
		}
	}

	public void focusLost(final Component arg0)
	{
		setDefaultCommand(null);
		//setTheTitleCount();
		//revalidate();
	}

	public void focusGained(final Component arg0)
	{
		super.focusGained(arg0);
		System.out.println("FocusedContacts");
		if (arg0 == theList)
		{
			setDefaultCommand(null);
			//revalidate();
			//setTheTitleCount();
		}
	}
}
