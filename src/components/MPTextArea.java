package components;

import com.sun.lwuit.TextArea;

/**
 * 
 */
/**
 * @author Alan
 * 
 */
public class MPTextArea extends TextArea
{
	/**
	 * 
	 */
	public MPTextArea()
	{
		super();
		makeKTextArea();
	}

	/**
	 * @param text
	 */
	public MPTextArea(final String text)
	{
		super(text);
		makeKTextArea();
	}

	/**
	 * @param rows
	 * @param columns
	 */
	public MPTextArea(final int rows, final int columns)
	{
		super(rows, columns);
		makeKTextArea();
	}

	/**
	 * @param text
	 * @param maxSize
	 */
	public MPTextArea(final String text, final int maxSize)
	{
		super(text, maxSize);
		makeKTextArea();
	}

	/**
	 * @param rows
	 * @param columns
	 * @param constraint
	 */
	public MPTextArea(final int rows, final int columns, final int constraint)
	{
		super(rows, columns, constraint);
		makeKTextArea();
	}

	/**
	 * @param text
	 * @param rows
	 * @param columns
	 */
	public MPTextArea(final String text, final int rows, final int columns)
	{
		super(text, rows, columns);
		makeKTextArea();
	}

	/**
	 * @param text
	 * @param rows
	 * @param columns
	 * @param constraint
	 */
	public MPTextArea(final String text, final int rows, final int columns, final int constraint)
	{
		super(text, rows, columns, constraint);
		makeKTextArea();
	}

	private void makeKTextArea()
	{
		setEditable(false);
	}

	protected boolean isSelectableInteraction()
	{
		return false;
	}

	public void setHandlesInput(boolean handlesInput)
	{
		super.setHandlesInput(false);
	}
}
