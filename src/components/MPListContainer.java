package components;

import com.sun.lwuit.List;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.events.SelectionListener;

public abstract class MPListContainer extends MPContainer implements FocusListener, SelectionListener
{
	protected List	theList	= null;

	public MPListContainer(MPForm _parentForm)
	{
		super(_parentForm);
	}

	public abstract void initializeThis();
}
