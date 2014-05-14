package forms;

import com.sun.lwuit.Component;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.Tabs;
import com.sun.lwuit.events.FocusListener;

public class mainTab extends Form implements FocusListener
{
	protected final int	TABS_UNINITTED		= -1;
	protected final int	TABS_CONVERSATIONS	= 0;
	protected final int	TABS_CONTACTS		= 1;
	protected final int	TABS_GROUPS			= 2;
	protected final int	TABS_APPSGAMES		= 3;
	protected final int	TABS_COUNT			= 4;
	protected int		TABS_State			= TABS_UNINITTED;
	//
	private Tabs		tabs				= null;

	public void show()
	{
		super.show();
		if (TABS_State == TABS_UNINITTED)
		{
			setupTabs();
		}
	}

	protected void onShowCompleted()
	{
		super.onShowCompleted();
	}

	private void setupTabs()
	{
		setScrollable(false);
		tabs = new Tabs();
		tabs.addTab(" CNV ", new ListConversations());//new Label("Conversation"));
		tabs.addTab(" CNT ", new Label("Contacts"));
		tabs.addTab(" GRP ", new Label("Groups"));
		tabs.addTab(" APS ", new Label("Apps and Games"));
		addComponent(tabs);
		tabs.showTabs();
		tabs.setSelectedIndex(0);
		revalidate();
		repaint();
	}

	public void focusGained(Component arg0)
	{
		// TODO Auto-generated method stub
	}

	public void focusLost(Component arg0)
	{
		// TODO Auto-generated method stub
	}
}
