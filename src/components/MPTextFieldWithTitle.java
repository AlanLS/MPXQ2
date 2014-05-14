package components;

import com.sun.lwuit.Container;
import com.sun.lwuit.layouts.BoxLayout;

public class MPTextFieldWithTitle extends Container
{
	private boolean						titleAtTop	= true;
	private final MPTextFieldNoPrompt	tfTextField	= new MPTextFieldNoPrompt();
	private final MPBorderlessLabel		lTitle		= new MPBorderlessLabel();

	public MPTextFieldWithTitle()
	{
		initMPTextFieldWithTitle();
	}

	public MPTextFieldWithTitle(String _title, String _text)
	{
		initMPTextFieldWithTitle();
		setTitleAndText(_title, _text);
	}

	private void initMPTextFieldWithTitle()
	{
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		setFocusable(false);
		lTitle.setFocusable(false);
		tfTextField.setFocusable(true);
		layoutComponents();
	}

	private void layoutComponents()
	{
		if (titleAtTop)
		{
			StyleHelpers.setMargin(lTitle, 5, 0);
			StyleHelpers.setPadding(lTitle, 1, 1);
			addComponent(lTitle);
			//
			StyleHelpers.setMargin(tfTextField, 0, 5);
			StyleHelpers.setPadding(tfTextField, 5, 5);
			addComponent(tfTextField);
		}
		else
		{
			StyleHelpers.setMargin(tfTextField, 5, 0);
			StyleHelpers.setPadding(tfTextField, 5, 5);
			addComponent(tfTextField);
			//
			StyleHelpers.setMargin(lTitle, 0, 5);
			StyleHelpers.setPadding(lTitle, 1, 1);
			addComponent(lTitle);
		}
	}

	public void setTitle(String _title)
	{
		lTitle.setText(_title);
	}

	public void setText(String _text)
	{
		tfTextField.setText(_text);
	}

	public void setTitleAndText(String _title, String _text)
	{
		lTitle.setText(_title);
		tfTextField.setText(_text);
	}

	public boolean isTitleAtTop()
	{
		return titleAtTop;
	}

	public void setTitleAtTop(boolean _titleAtTop)
	{
		if (titleAtTop != _titleAtTop)
		{
			titleAtTop = _titleAtTop;
			removeAll();
			layoutComponents();
			revalidate();
			repaint();
		}
	}

	public MPTextField getTfTextField()
	{
		return tfTextField;
	}

	public MPBorderlessLabel getlTitle()
	{
		return lTitle;
	}
}
