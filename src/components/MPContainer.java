package components;

import lcl.L10nResources;
import main.GlobalData;
import com.sun.lwuit.Container;

public abstract class MPContainer extends Container
{
	protected MPForm				parentForm	= null;
	protected static L10nResources	rsrc		= GlobalData.getRsrc();

	public MPContainer(MPForm _parentForm)
	{
		parentForm = _parentForm;
	}
}
