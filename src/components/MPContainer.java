package components;

import lcl.L10nResources;
import main.GlobalData;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;

public abstract class MPContainer extends Container implements ActionListener
{
	protected Component				cmp			= null;
	protected Command				cmd			= null;
	protected MPForm				parentForm	= null;
	protected static L10nResources	rsrc		= GlobalData.getRsrc();

	public MPContainer(MPForm _parentForm)
	{
		parentForm = _parentForm;
	}

	public void actionPerformed(final ActionEvent arg0)
	{
		cmp = arg0.getComponent();
		cmd = arg0.getCommand();
		System.out.println("cmd= " + cmd + "  cmp= " + cmp);
	}
}
