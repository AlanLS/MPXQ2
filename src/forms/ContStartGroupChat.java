/**
 * 
 */
package forms;

import lcl.L10nConstants;
import main.GlobalData;
import com.sun.lwuit.Button;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.layouts.BoxLayout;
import components.MPContainer;
import components.MPForm;
import components.MPGrowTextArea;

/**
 * @author Alan
 *
 */
public class ContStartGroupChat extends MPContainer
{
	private Button	btnCrtGrp	= null;
	private Button	btnCrtSht	= null;

	public ContStartGroupChat(MPForm _parentForm)
	{
		super(_parentForm);
	}

	public void initializeThis()
	{
		deInitializeThis();
		//
		parentForm.addCommandListener(this);
		((mainTab) parentForm).lastListener = this;
		//
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		//final MPBorderlessLabel lbl = new MPBorderlessLabel("");
		//lbl.setPreferredH(20);
		//addComponent(lbl);
		//
		final MPGrowTextArea lblMsg = new MPGrowTextArea(GlobalData.getRsrc().getString(L10nConstants.keys.GRP_YOUNEEDTOADD));
		lblMsg.setFocusable(false);
		addComponent(lblMsg);
		//
		btnCrtGrp = new Button(GlobalData.getRsrc().getString(L10nConstants.keys.GRP_CREATEAGROUP));
		btnCrtGrp.addActionListener(this);
		addComponent(btnCrtGrp);
		//
		btnCrtSht = new Button(GlobalData.getRsrc().getString(L10nConstants.keys.GRP_CREATEASHOUTLIST));
		btnCrtSht.addActionListener(this);
		addComponent(btnCrtSht);
		//
		btnCrtGrp.requestFocus();
	}

	public void deInitializeThis()
	{
		removeAll();
		System.gc();
	}

	public void actionPerformed(ActionEvent arg0)
	{
		super.actionPerformed(arg0);
		if (cmd != null)
		{
			System.out.println("GROUPCHAT_ " + cmd.getCommandName());
		}
		else if (cmp != null)
		{
			if (cmp == btnCrtGrp)
			{
				new FormContacts().show();
			}
			else if (cmp == btnCrtSht)
			{
				new FormContacts().show();
			}
		}
	}
}
