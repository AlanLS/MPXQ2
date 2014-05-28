package forms;

import lcl.L10nConstants;
import lcl.L10nResources;
import main.GlobalData;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Tabs;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import components.MPForm;

public class mainTab extends MPForm
{
	protected final int			TABS_UNINITTED		= -1;
	protected final int			TABS_CONVERSATIONS	= 0;
	protected final int			TABS_CONTACTS		= 1;
	protected final int			TABS_GROUPS			= 2;
	protected final int			TABS_APPSGAMES		= 3;
	protected final int			TABS_COUNT			= 4;
	protected int				TABS_State			= TABS_UNINITTED;
	//
	private Tabs				tabs				= null;
	protected L10nResources		rsrc				= GlobalData.getRsrc();											;
	private final Command		cmdBack				= new Command(rsrc.getString(L10nConstants.keys.COMMAND_BACK));
	//
	// so we have an action to listen
	private final Button		btnCnv				= (new Button(" CNV "));
	private final Button		btnCnt				= (new Button(" CNT "));
	private final Button		btnGrp				= (new Button(" GRP "));
	private final Button		btnAps				= (new Button(" APS "));
	ActionListener				lastListener		= null;
	private final Container[]	panes				= new Container[TABS_COUNT];

	public mainTab()
	{
		super();
		setLayout(new BorderLayout());
		//
		cleanAllPanes();
	}

	public void show()
	{
		super.show();
		setScrollable(false);
		//
		tabs = new Tabs();
		tabs.setScrollable(false);
		btnCnv.addActionListener(this);
		btnCnt.addActionListener(this);
		btnGrp.addActionListener(this);
		btnAps.addActionListener(this);
		tabs.addTab(btnCnv, panes[TABS_CONVERSATIONS]);//new ContConversations(this));// new Label("Conversation"));
		tabs.addTab(btnCnt, panes[TABS_CONTACTS]);//new ContContacts(this));
		tabs.addTab(btnGrp, panes[TABS_GROUPS]);//new ContStartGroupChat(this));
		tabs.addTab(btnAps, panes[TABS_APPSGAMES]);//new Label("Apps and Games"));
		addComponent(BorderLayout.CENTER, tabs);
		tabs.showTabs();
		tabs.getTabComponentAt(0);
		tabs.setSelectedIndex(0);
	}

	protected void onShowCompleted()
	{
		super.onShowCompleted();
		System.out.println("ShowCompleted");
		if (TABS_State == TABS_UNINITTED)
		{
			showConversations();
		}
	}

	private void cleanAllPanes()
	{
		final int sz = panes.length;
		for (int i = 0; i < sz; ++i)
		{
			final Container c = panes[i];
			if (c != null)
			{
				c.removeAll();
				c.setScrollable(false);
				c.setLayout(new BorderLayout());
			}
			else
			{
				panes[i] = new Container(new BorderLayout());
			}
		}
	}

	private void showConversations()
	{
		TABS_State = TABS_CONVERSATIONS;
		removeAllCommands();
		cleanAllPanes();
		addCommand(cmdBack);
		ContConversations cc = new ContConversations(this);
		panes[0].addComponent(BorderLayout.CENTER, cc);
		System.out.println(">>>>NEW CONVERSATION LIST!");
		revalidate();
		repaint();
	
	}

	private void showContacts()
	{
		TABS_State = TABS_CONTACTS;
		removeAllCommands();
		cleanAllPanes();
		setBackCommand(cmdBack);
		panes[1].addComponent(BorderLayout.CENTER, new ContContacts(this));
		System.out.println(">>>>NEW CONTACT LIST!");
		revalidate();
		repaint();
	}

	private void showFormStartGroupChat()
	{
		TABS_State = TABS_GROUPS;
		removeAllCommands();
		cleanAllPanes();
		setBackCommand(cmdBack);
		panes[2].addComponent(BorderLayout.CENTER, new ContStartGroupChat(this));
		System.out.println(">>>>NEW START GROUP CHAT!");
		revalidate();
		repaint();
	}

	public void actionPerformed(ActionEvent arg0)
	{
		super.actionPerformed(arg0);
		if (cmp != null)
		{
			if (cmp == btnCnv)
			{
				showConversations();
				return;
			}
			else if (cmp == btnCnt)
			{
				showContacts();
				return;
			}
			else if (cmp == btnGrp)
			{
				showFormStartGroupChat();
				return;
			}
			else if (cmp == btnAps)
			{
				return;
			}
		}
	}
}
