/**
 * 
 */
package components;

import com.sun.lwuit.Image;
import com.sun.lwuit.Label;

/**
 * @author Alan
 * 
 */
public class MPBorderlessLabel extends Label
{
	final public static String	BORDERLESSLABEL	= "BorderlessLabel";
	private boolean				selectable		= false;

	public MPBorderlessLabel()
	{
		super();
		setSpecific();
	}

	public MPBorderlessLabel(Image icon)
	{
		super(icon);
		setSpecific();
	}

	public MPBorderlessLabel(String text)
	{
		super(text);
		setSpecific();
	}

	protected void setSpecific()
	{
		// setPreferredH(32);
		setUIID(BORDERLESSLABEL);
		selectable = false;
		StyleHelpers.setFont(this, Fonts.SmallFont);
	}

	public void setSelectable(boolean _selectable)
	{
		selectable = _selectable;
	}

	protected boolean isSelectableInteraction()
	{
		return selectable;
	}

	public void changeTextColor(int _col)
	{
		StyleHelpers.setFgColor(this, _col);
	}
}
