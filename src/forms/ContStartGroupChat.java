/**
 * 
 */
package forms;

import lcl.L10nConstants;
import main.GlobalData;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import components.MPContainer;
import components.MPForm;
import components.MPGrowTextArea;
import components.StyleHelpers;

/**
 * @author Alan
 *
 */
public class ContStartGroupChat extends MPContainer implements ActionListener
{
	private Button	btnCrtGrp	= null;
	private Button	btnCrtSht	= null;

	public ContStartGroupChat(MPForm _parentForm)
	{
		super(_parentForm);
		parentForm.setDefaultCommand(null);
		parentForm.addCommandListener(this);
		((mainTab) parentForm).lastListener = this;
		//
		//parentForm.addCommand(cmdSearch);
		//parentForm.addCommand(cmdSettings);
		//parentForm.addCommand(cmdDelete);
		//parentForm.addCommand(cmdProfile);
		//parentForm.addCommand(cmdNewChat);
		//
		//
		//
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		StyleHelpers.removeMargins(this);
		StyleHelpers.removePadding(this);
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

	public void actionPerformed(ActionEvent arg0)
	{
		final Component cmp = arg0.getComponent();
		final Command cmd = arg0.getCommand();
		System.out.println("cmd= " + cmd + "  cmp= " + cmp);
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
