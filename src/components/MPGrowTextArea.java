package components;

import com.sun.lwuit.TextArea;

public class MPGrowTextArea extends TextArea
{
	final public static String	GROWTEXTAREA	= "GrowTextArea";

	public MPGrowTextArea()
	{
		super();
		makeMeGrowable();
	}

	public MPGrowTextArea(String text)
	{
		super(text);
		makeMeGrowable();
	}

	public MPGrowTextArea(int rows, int columns)
	{
		super(rows, columns);
		makeMeGrowable();
	}

	public MPGrowTextArea(String text, int maxSize)
	{
		super(text, maxSize);
		makeMeGrowable();
	}

	public MPGrowTextArea(int rows, int columns, int constraint)
	{
		super(rows, columns, constraint);
		makeMeGrowable();
	}

	public MPGrowTextArea(String text, int rows, int columns)
	{
		super(text, rows, columns);
		makeMeGrowable();
	}

	public MPGrowTextArea(String text, int rows, int columns, int constraint)
	{
		super(text, rows, columns, constraint);
		makeMeGrowable();
	}

	private void makeMeGrowable()
	{
		setUIID(GROWTEXTAREA);
		setGrowByContent(true);
		setEditable(false);
		setRows(2);
		StyleHelpers.setFont(this, Fonts.SmallFont);
	}

	protected boolean isSelectableInteraction()
	{
		return false;
	}
}
