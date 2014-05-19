package components;

import com.sun.lwuit.Command;

public class MPTextFieldNoPrompt extends MPTextField
{
	public MPTextFieldNoPrompt()
	{
		super();
	}

	public MPTextFieldNoPrompt(int columns)
	{
		super(columns);
	}

	public MPTextFieldNoPrompt(String text)
	{
		super(text);
	}

	public MPTextFieldNoPrompt(String text, int columns)
	{
		super(text, columns);
	}

	protected Command installCommands(Command clear, Command t9)
	{
		return super.installCommands(clear, null);
	}
	// protected void removeCommands(Command clear, Command t9, Command
	// originalClear)
	// {
	//
	// }
}
