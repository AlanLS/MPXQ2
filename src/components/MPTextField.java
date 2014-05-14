package components;

import com.sun.lwuit.TextField;

public class MPTextField extends TextField
{
	public MPTextField()
	{
		super();
		initMPTextField();
	}

	public MPTextField(int columns)
	{
		super(columns);
		initMPTextField();
	}

	public MPTextField(String text)
	{
		super(text);
		initMPTextField();
	}

	public MPTextField(String text, int columns)
	{
		super(text, columns);
		initMPTextField();
	}

	private void initMPTextField()
	{
		setSingleLineTextArea(true);
		setPreferredH(40);
		setGrowByContent(false);
		setEditable(true);
		StyleHelpers.setBorder(this, MPForm.blckBorder);
	}
}
