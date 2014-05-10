package components;

import com.sun.lwuit.TextArea;

public class MPTextField extends TextArea
{
	/**
	 * 
	 */
	public MPTextField()
	{
		super();
		initKTextField();
	}

	/**
	 * @param text
	 */
	public MPTextField(final String text)
	{
		super(text);
		initKTextField();
	}

	/**
	 * @param rows
	 * @param columns
	 */
	public MPTextField(final int rows, final int columns)
	{
		super(rows, columns);
		initKTextField();
	}

	/**
	 * @param text
	 * @param maxSize
	 */
	public MPTextField(final String text, final int maxSize)
	{
		super(text, maxSize);
		initKTextField();
	}

	/**
	 * @param rows
	 * @param columns
	 * @param constraint
	 */
	public MPTextField(final int rows, final int columns, final int constraint)
	{
		super(rows, columns, constraint);
		initKTextField();
	}

	/**
	 * @param text
	 * @param rows
	 * @param columns
	 */
	public MPTextField(final String text, final int rows, final int columns)
	{
		super(text, rows, columns);
		initKTextField();
	}

	/**
	 * @param text
	 * @param rows
	 * @param columns
	 * @param constraint
	 */
	public MPTextField(final String text, final int rows, final int columns, final int constraint)
	{
		super(text, rows, columns, constraint);
		initKTextField();
	}

	private void initKTextField()
	{
		setSingleLineTextArea(true);
		setPreferredH(40);
		setGrowByContent(false);
		setEditable(true);
	}

	public void setHandlesInput(boolean _handlesInput)
	{
		super.setHandlesInput(_handlesInput);
	}

	public void clear()
	{
		setText("");
	}
}
