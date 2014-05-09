package components;

import com.sun.lwuit.Command;
import com.sun.lwuit.Component;

public class MPDialogModeless extends MPDialog
{
	public MPDialogModeless(String _text)
	{
		super(_text);
	}

	public MPDialogModeless(String _text, Object _connect)
	{
		super(_text, _connect);
	}

	public MPDialogModeless(Component[] _cmps, Object _connect, Command[] _cmds)
	{
		super(_cmps, _connect, _cmds);
	}

	public void showTimeless()
	{
		showModeless();
	}

	public void showTimed(int _ms)
	{
		showModeless();
		setTimeout(_ms);
	}

	public void disposeWithDelay(int ms)
	{
		setTimeout(ms);
	}
}
