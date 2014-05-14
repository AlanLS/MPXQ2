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
		makeMPTextArea();
	}

	/**
	 * @param text
	 */
	public MPTextArea(final String text)
	{
		super(text);
		makeMPTextArea();
	}

	/**
	 * @param rows
	 * @param columns
	 */
	public MPTextArea(final int rows, final int columns)
	{
		super(rows, columns);
		makeMPTextArea();
	}

	/**
	 * @param text
	 * @param maxSize
	 */
	public MPTextArea(final String text, final int maxSize)
	{
		super(text, maxSize);
		makeMPTextArea();
	}

	/**
	 * @param rows
	 * @param columns
	 * @param constraint
	 */
	public MPTextArea(final int rows, final int columns, final int constraint)
	{
		super(rows, columns, constraint);
		makeMPTextArea();
	}

	/**
	 * @param text
	 * @param rows
	 * @param columns
	 */
	public MPTextArea(final String text, final int rows, final int columns)
	{
		super(text, rows, columns);
		makeMPTextArea();
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
		makeMPTextArea();
	}

	private void makeMPTextArea()
	{
		setSingleLineTextArea(true);
		setPreferredH(40);
		setGrowByContent(false);
		setEditable(false);
		StyleHelpers.setBorder(this, MPForm.blckBorder);
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
