package components;

import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.BoxLayout;

public class MPTextAreaWithTitle extends Container
{
	private boolean					titleAtTop	= true;
	private final Label				lTextField	= new Label();
	private final MPBorderlessLabel	lTitle		= new MPBorderlessLabel();

	public MPTextAreaWithTitle()
	{
		initMPTextAreaWithTitle();
	}

	public MPTextAreaWithTitle(String _title, String _text)
	{
		initMPTextAreaWithTitle();
		setTitleAndText(_title, _text);
	}

	private void initMPTextAreaWithTitle()
	{
		setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		setFocusable(false);
		lTextField.setUIID("TextArea");
		lTextField.setPreferredH(40);
		lTextField.setPreferredW(Display.getInstance().getDisplayWidth());
		//StyleHelpers.setBorder(lTextField, MPForm.blckBorder);
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
			StyleHelpers.setMargin(lTextField, 0, 5);
			StyleHelpers.setPadding(lTextField, 5, 5);
			addComponent(lTextField);
		}
		else
		{
			StyleHelpers.setMargin(lTextField, 5, 0);
			StyleHelpers.setPadding(lTextField, 5, 5);
			addComponent(lTextField);
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
		lTextField.setText(_text);
	}

	public void setTitleAndText(String _title, String _text)
	{
		lTitle.setText(_title);
		lTextField.setText(_text);
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

	public Label getlTextField()
	{
		return lTextField;
	}

	public MPBorderlessLabel getlTitle()
	{
		return lTitle;
	}
}
