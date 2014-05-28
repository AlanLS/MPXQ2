package forms;

import lcl.L10nConstants;
import com.sun.lwuit.Command;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import components.MPForm;
import components.MPGrowTextArea;

public class FormTandC extends MPForm
{
	private final Command	cancelCommand	= new Command(rsrc.getString(L10nConstants.keys.COMMAND_CANCEL));
	private final Command	iAgreeCommand	= new Command(rsrc.getString(L10nConstants.keys.COMMAND_IAGREE));

	public FormTandC()
	{
		super(rsrc.getString(L10nConstants.keys.TRM_TERMSANDCONDITIONS));
	}

	public void show()
	{
		super.show();
		showTandC();
	}

	protected void onShowCompleted()
	{
		super.onShowCompleted();
	}

	private void showTandC()
	{
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		//final Container cntnr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		// cntnr.addComponent(BorderLayout.CENTER, innercntnr);
		//
		final MPGrowTextArea tandc = new MPGrowTextArea(rsrc.getString(L10nConstants.keys.TRM_TERMS, new String[] { "\r\n\r\n", "\r\n\r\n" }));
		tandc.setScrollVisible(true);
		addComponent(tandc);
		//replaceContent(getContentPane(), cntnr, null);
		setBackCommand(cancelCommand);
		setDefaultCommand(iAgreeCommand);
		addCommand(iAgreeCommand);
	}

	public void actionPerformed(final ActionEvent arg0)
	{
		super.actionPerformed(arg0);
		if (cmd != null)
		{
			if (cmd == cancelCommand)
			{
				new FormSplash().show();
			}
			else if (cmd == iAgreeCommand)
			{
				new FormOOBE().show();
			}
		}
		else if (cmp != null)
		{
		}
	}
	//
}
