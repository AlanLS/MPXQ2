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
	/*
	 * static final int QWERTY_SYMBOL_KEY = -54;
	 * 
	 * 
	 * public KTextField() { super(); initKTextField(); }
	 * 
	 * public KTextField(final int columns) { super(columns); initKTextField();
	 * }
	 * 
	 * public KTextField(final String text) { super(text); initKTextField(); }
	 * 
	 * public KTextField(final String text, final int columns) { super(text,
	 * columns); initKTextField(); }
	 * 
	 * private void initKTextField() { //setLeftAndRightEditingTrigger(false);
	 * //setUseSoftkeys(false); setReplaceMenu(true);
	 * setConstraint(TextArea.NON_PREDICTIVE); };
	 * 
	 * // public boolean isUseSoftkeys() // { // return false; // } protected
	 * boolean isEditingEndTrigger(final int keyCode) { return false; }
	 * 
	 * protected boolean isEditingTrigger(final int keyCode) { return false; }
	 * 
	 * protected boolean isSelectableInteraction() { return false; }
	 * 
	 * protected boolean isSymbolDialogKey(int keyCode) { return ((keyCode ==
	 * getDefaultSymbolDialogKey()) || (keyCode == QWERTY_SYMBOL_KEY)); }
	 * 
	 * public void pointerReleased(int x, int y) { // prevent a drag operation
	 * from going into edit mode<br /> if (isDragActivated()) {
	 * super.pointerReleased(x, y); } else { if (isEditable() && isEnabled())//
	 * && !isCellRenderer()) { Display.getInstance().editString(this,
	 * getMaxSize(), getConstraint(), getText()); } } }
	 */
}
