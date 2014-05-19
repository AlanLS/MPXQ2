package components;

import java.util.Hashtable;
import com.sun.lwuit.events.DataChangedListener;
import com.sun.lwuit.util.EventDispatcher;

public class MPHashtable extends Hashtable
{
	private final EventDispatcher	dataListener	= new EventDispatcher();

	protected void addDataChangedListener(DataChangedListener l)
	{
		dataListener.addListener(l);
	}

	protected void removeDataChangedListener(DataChangedListener l)
	{
		dataListener.removeListener(l);
	}

	protected void fireDataChangedEvent(final int status, final int index)
	{
		dataListener.fireDataChangeEvent(index, status);
	}
}
