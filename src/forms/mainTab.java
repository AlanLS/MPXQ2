package forms;

import lcl.L10nConstants;
import lcl.L10nResources;
import main.GlobalData;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Label;
import com.sun.lwuit.Tabs;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import components.MPForm;
import components.MPListContainer;

public class mainTab extends MPForm
{
	protected final int		TABS_UNINITTED		= -1;
	protected final int		TABS_CONVERSATIONS	= 0;
	protected final int		TABS_CONTACTS		= 1;
	protected final int		TABS_GROUPS			= 2;
	protected final int		TABS_APPSGAMES		= 3;
	protected final int		TABS_COUNT			= 4;
	protected int			TABS_State			= TABS_UNINITTED;
	//
	private Tabs			tabs				= null;
	protected L10nResources	rsrc				= GlobalData.getRsrc();											;
	private final Command	cmdBack				= new Command(rsrc.getString(L10nConstants.keys.COMMAND_BACK));
	//
	// so we have an action to listen
	private final Button	btnCnv				= (new Button(" CNV "));
	private final Button	btnCnt				= (new Button(" CNT "));
	private final Button	btnGrp				= (new Button(" GRP "));
	private final Button	btnAps				= (new Button(" APS "));
	ActionListener			lastListener		= null;

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
		btnCnv.addActionListener(this);
		btnCnt.addActionListener(this);
		btnGrp.addActionListener(this);
		btnAps.addActionListener(this);
		tabs.addTab(btnCnv, new ContConversations(this));// new Label("Conversation"));
		tabs.addTab(btnCnt, new ContContacts(this));
		tabs.addTab(btnGrp, new ContStartGroupChat(this));
		tabs.addTab(btnAps, new Label("Apps and Games"));
		addComponent(tabs);
		tabs.showTabs();
		tabs.getTabComponentAt(0);
		tabs.setSelectedIndex(0);
		//showConversations();
	}

	private void showConversations()
	{
		removeAllCommands();
		if (lastListener != null)
		{
			removeCommandListener(lastListener);
		}
		((MPListContainer) tabs.getTabComponentAt(0)).initializeThis();
		System.out.println(">>>>NEW CONVERSATION LIST!");
		setBackCommand(cmdBack);
		revalidate();
		repaint();
	}

	private void showContacts()
	{
		removeAllCommands();
		if (lastListener != null)
		{
			removeCommandListener(lastListener);
		}
		((ContContacts) tabs.getTabComponentAt(1)).initializeThis();
		System.out.println(">>>>NEW CONTACT LIST!");
		setBackCommand(cmdBack);
		revalidate();
		repaint();
	}

	private void showFormStartGroupChat()
	{
		removeAllCommands();
		if (lastListener != null)
		{
			removeCommandListener(lastListener);
		}
		((ContStartGroupChat) tabs.getTabComponentAt(2)).initializeThis();
		System.out.println(">>>>NEW START GROUP CHAT!");
		setBackCommand(cmdBack);
		revalidate();
		repaint();
	}

	public void actionPerformed(ActionEvent arg0)
	{
		final Component cmp = arg0.getComponent();
		final Command cmd = arg0.getCommand();
		System.out.println("cmd= " + cmd + "  cmp= " + cmp);
		if (cmp != null)
		{
			if (cmp == btnCnv)
			{
				showConversations();
			}
			else if (cmp == btnCnt)
			{
				showContacts();
			}
			else if (cmp == btnGrp)
			{
				showFormStartGroupChat();
			}
			else if (cmp == btnAps)
			{
			}
			else
			{
				switch (tabs.getSelectedIndex())
				{
					case 0:
					case 1:
					case 2:
					{
						((MPListContainer) tabs.getSelectedComponent()).actionPerformed(arg0);
						break;
					}
					case 3:
					{
						// ((ListConversations)tabs.getSelectedComponent()).actionPerformed(arg0);
						break;
					}
					default:
					{
						break;
					}
				}
			}
		}
	}

	public void selectionChanged(int arg0, int arg1)
	{
		// TODO Auto-generated method stub
	}
}
