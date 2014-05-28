package components;

import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.events.SelectionListener;

public abstract class MPListContainer extends MPContainer implements FocusListener, SelectionListener, ActionListener
{
	protected List	theList	= null;
	Component		cmp		= null;
	Command			cmd		= null;

	public MPListContainer(MPForm _parentForm)
	{
		super(_parentForm);
	}

	public void actionPerformed(final ActionEvent arg0)
	{
		cmp = arg0.getComponent();
		cmd = arg0.getCommand();
		System.out.println("cmd= " + cmd + "  cmp= " + cmp);
	}
}
