package components;

import io.MPConnectionRequest;
import io.MPSliderBridge;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.BoxLayout;

public class MPDialog extends Dialog
{
	MPGrowTextArea	taBody	= null;
	Command[]		cmds	= null;

	private MPDialog()
	{
		StyleHelpers.setBgTransparency(getContentPane(), 255);
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
	}

	public MPDialog(Container ccc, String okText)
	{
		this();
		addComponent(ccc);
		setDefaultCommand(new Command(okText));
	}

	public MPDialog(String _text)
	{
		this();
		taBody = new MPGrowTextArea(_text);
		StyleHelpers.setFont(taBody, Fonts.StandardFont);
		taBody.setEditable(false);
		taBody.setFocusable(false);
		addComponent(taBody);
	}

	public MPDialog(String _text, Object _connect)
	{
		this(_text);
		//
		addConnect(_connect);
	}

	public MPDialog(Component[] _cmps, Object _connect, Command[] _cmds)
	{
		this();
		for (int i = 0; i < _cmps.length; ++i)
		{
			addComponent(_cmps[i]);
		}
		//
		for (int i = 0; i < _cmps.length; ++i)
		{
			addCommand(_cmds[i]);
		}
		//
		addConnect(_connect);
	}

	public void addConnect(Object _connect)
	{
		if (_connect != null)
		{
			final MPSliderBridge bs = new MPSliderBridge((MPConnectionRequest) _connect);
			bs.setRenderPercentageOnTop(false);
			bs.setRenderValueOnTop(false);
			addComponent(new Label(" "));
			addComponent(bs);
		}
	}

	public void showTimeless()
	{
		show();
	}

	public void showTimed(int _ms)
	{
		setTimeout(_ms);
		show();
	}

	public void disposeWithDelay(int ms)
	{
		setTimeout(ms);
	}
}
