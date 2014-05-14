package forms;

import java.util.Hashtable;
import lcl.L10nConstants;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.events.SelectionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.GenericListCellRenderer;
import components.Fonts;
import components.MPBorderlessLabel;
import components.MPForm;
import components.StyleHelpers;

public class ListConversations extends Container implements ActionListener, FocusListener, SelectionListener
{
	private List		theList		= null;
	private Container	cntrHello	= null;

	public ListConversations()
	{
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		StyleHelpers.removeMargins(this);
		StyleHelpers.removePadding(this);
		cntrHello = new Container(new BorderLayout());
		cntrHello.setUIID("ListRenderer");
		StyleHelpers.removeMargins(cntrHello);
		StyleHelpers.removePadding(cntrHello);
		cntrHello.setFocusable(true);
		//
		final Container cntnrImg = new Container(new BorderLayout());
		StyleHelpers.removeMargins(cntnrImg);
		StyleHelpers.removePadding(cntnrImg);
		final Image img = Image.createImage(40, 40, 0x777777);
		final Label lblImg = new Label(img);
		StyleHelpers.removeMargins(lblImg);
		StyleHelpers.removePadding(lblImg);
		cntnrImg.addComponent(BorderLayout.CENTER, lblImg);
		cntrHello.addComponent(BorderLayout.WEST, cntnrImg);
		//
		final Container cntnrText = new Container(new BorderLayout());
		StyleHelpers.removeMargins(cntnrText);
		StyleHelpers.removePadding(cntnrText);
		final MPBorderlessLabel lblName = new MPBorderlessLabel();
		lblName.setText(MPForm.getRsrc().getString(L10nConstants.keys.CONV_HELLO));
		StyleHelpers.setFont(lblName, Fonts.SmallBoldFont);
		cntnrText.addComponent(BorderLayout.NORTH, lblName);
		//
		final MPBorderlessLabel lblMsg = new MPBorderlessLabel();
		lblMsg.setText(MPForm.getRsrc().getString(L10nConstants.keys.CONV_WELCOME));
		cntnrText.addComponent(BorderLayout.SOUTH, lblMsg);
		//
		cntrHello.addComponent(BorderLayout.CENTER, cntnrText);
		addComponent(cntrHello);
		//
		theList = new List(createGenericData());
		StyleHelpers.removeMargins(theList);
		StyleHelpers.removePadding(theList);
		theList.setFocusable(true);
		addComponent(theList);
		theList.requestFocus();
		//theList.setRenderer(new ConvInboxCellRenderer(createRendererContainer(), createRendererContainer()));
		theList.setRenderer(new GenericListCellRenderer(createRendererContainer(), createRendererContainer()));
		// TODO Auto-generated constructor stub
	}

	private Container createRendererContainer()
	{
		final Container c = new Container(new BorderLayout());
		c.setUIID("ListRenderer");
		StyleHelpers.removeMargins(c);
		StyleHelpers.removePadding(c);
		//
		final Container cntnrImg = new Container(new BorderLayout());
		StyleHelpers.removeMargins(cntnrImg);
		StyleHelpers.removePadding(cntnrImg);
		final Image img = Image.createImage(40, 40, 0x777777);
		final Label lblImg = new Label(img);
		//lblImg.setFocusable(true);
		lblImg.setName("Image_fixed");
		StyleHelpers.removeMargins(lblImg);
		StyleHelpers.removePadding(lblImg);
		cntnrImg.addComponent(BorderLayout.CENTER, lblImg);
		c.addComponent(BorderLayout.WEST, cntnrImg);
		//
		/*		final Container cntnrCnt = new Container(new BorderLayout());
				StyleHelpers.removeMargins(cntnrCnt);
				StyleHelpers.removePadding(cntnrCnt);
				final MPBorderlessLabel lblCnt = new MPBorderlessLabel("1/4");
				//lblCnt.setFocusable(true);
				lblCnt.setName("Count_fixed");
				StyleHelpers.removeMargins(lblCnt);
				StyleHelpers.removePadding(lblCnt);
				cntnrCnt.addComponent(BorderLayout.CENTER, lblCnt);
				c.addComponent(BorderLayout.EAST, cntnrCnt);
		*///
		final Container cntnrText = new Container(new BorderLayout());
		StyleHelpers.removeMargins(cntnrText);
		StyleHelpers.removePadding(cntnrText);
		final MPBorderlessLabel lblName = new MPBorderlessLabel();
		//lblName.setFocusable(true);
		lblName.setName("Name");
		StyleHelpers.setFont(lblName, Fonts.SmallBoldFont);
		cntnrText.addComponent(BorderLayout.NORTH, lblName);
		//
		final MPBorderlessLabel lblMsg = new MPBorderlessLabel();
		//lblMsg.setFocusable(true);
		lblMsg.setName("Message");
		cntnrText.addComponent(BorderLayout.SOUTH, lblMsg);
		//
		c.addComponent(BorderLayout.CENTER, cntnrText);
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
		// TODO Auto-generated method stub
	}

	public void focusLost(Component arg0)
	{
		// TODO Auto-generated method stub
	}

	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
	}
}
