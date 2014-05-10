package components;

import com.sun.lwuit.Command;
import com.sun.lwuit.TextField;

public class MPTextFieldNoPrompt extends TextField
{
	protected  Command	installCommands(Command clear, Command t9) 
	{
		return null;
	}

	protected  void	removeCommands(Command clear, Command t9, Command originalClear) 
	{
		
	}
	
	
	
}
