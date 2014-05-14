package components;

import java.util.Hashtable;
import lcl.L10nResources;
import main.GlobalData;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;

abstract public class MPForm extends Form implements ActionListener, FocusListener
{
	protected Hashtable				localExtras;
	protected Component				focusedComponent		= null;
	protected Component				priorFocusedComponent	= null;
	protected Component				cmp						= null;
	protected Command				cmd						= null;
	protected Class					priorClass				= null;
	protected String				priorTitle				= null;
	CommonTransitions				transitIn				= null;								// CommonTransitions.createFade(125);
	private static L10nResources	rsrc					= GlobalData.getRsrc();
	public final static Border		yllwBorder				= Border.createLineBorder(3, 0xFFFF00);	// yellow
	public final static Border		blckBorder				= Border.createLineBorder(1, 0x000000);	// yellow
	public final static Border		nrmlBorder				= Border.createLineBorder(2, 0xdadada);

	//protected final static Label dummyContainer = new Label();
	public MPForm(String title)
	{
		super(title);
		initThis();
	}

	public MPForm()
	{
		super();
		initThis();
	}

	private void initThis()
	{
		if (Display.getInstance().isTouchScreenDevice())
		{
			// if (Display.getInstance().getDisplayHeight() >
			// Display.getInstance().getDisplayWidth())
			{
				final Button[] mb = getMenuBar().getSoftButtons();
				for (int i = 0; i < mb.length; ++i)
				{
					final Button b = mb[i];
					Style ms = b.getUnselectedStyle();
					ms.setPadding(Component.TOP, 10);
					ms.setPadding(Component.BOTTOM, 10);
					b.setUnselectedStyle(ms);
					//
					ms = b.getSelectedStyle();
					ms.setPadding(Component.TOP, 10);
					ms.setPadding(Component.BOTTOM, 10);
					b.setSelectedStyle(ms);
					//
					ms = b.getPressedStyle();
					ms.setPadding(Component.TOP, 10);
					ms.setPadding(Component.BOTTOM, 10);
					b.setPressedStyle(ms);
					b.refreshTheme();
				}
				getMenuBar().refreshTheme();
				/*
				 * Style ms = getMenuBar().getUnselectedStyle();
				 * ms.setPadding(Component.TOP, 10);
				 * ms.setPadding(Component.BOTTOM, 10);
				 * getMenuBar().setUnselectedStyle(ms); // ms =
				 * getMenuBar().getSelectedStyle(); ms.setPadding(Component.TOP,
				 * 10); ms.setPadding(Component.BOTTOM, 10);
				 * getMenuBar().setSelectedStyle(ms); // ms =
				 * getMenuBar().getPressedStyle(); ms.setPadding(Component.TOP,
				 * 10); ms.setPadding(Component.BOTTOM, 10);
				 * getMenuBar().setPressedStyle(ms);
				 */}
		}
		System.gc();
		System.out.println("NEW CLASS= " + getClass().toString());
		//addComponent(dummyContainer);
		removeCommand(getBackCommand());
		setCyclicFocus(false);
		addCommandListener(this);
		addFocusListener(this);
		// ApplicationData.setChecklist(false);
		StyleHelpers.setAlignment(getTitleComponent(), Component.LEFT);
		setTransitionInAnimator(transitIn);
	}

	public void show()
	{
		super.show();
	}

	/*
	 * protected void onShowCompleted() { super.onShowCompleted(); System.gc();
	 * }
	 */
	public void focusGained(final Component arg0)
	{
		focusedComponent = arg0;
		System.out.println("Focus is on= " + focusedComponent.getName());
	}

	public void focusLost(final Component arg0)
	{
		priorFocusedComponent = arg0;
	}

	public void actionPerformed(final ActionEvent arg0)
	{
		cmp = arg0.getComponent();
		cmd = arg0.getCommand();
		System.out.println("cmd= " + cmd + "  cmp= " + cmp);
	}

	protected void replaceContent(final Container c, final Component newComponent, final String constraint)
	{
		Component cmp = null;
		if (c.getComponentCount() > 0)
		{
			cmp = c.getComponentAt(0);
		}
		if (cmp != null)
		{
			c.replace(cmp, newComponent, transitIn);
		}
		else
		{
			if (constraint == null)
			{
				c.addComponent(newComponent);
			}
			else
			{
				c.addComponent(constraint, newComponent);
			}
		}
	}

	/*
	 * public void goBackward() { try { //final Class cls = (Class)
	 * KForm.extras.get(KeyStrings.priorFormClass); //final String ttl =
	 * (String) KForm.extras.get(KeyStrings.priorFormTitle); final KForm f =
	 * (KForm) cls.newInstance(); f.localExtras.put(KeyStrings.title, ttl);
	 * f.show(); } catch (final InstantiationException e) { e.printStackTrace();
	 * } catch (final IllegalAccessException e) { e.printStackTrace(); } }
	 */
	protected void onShowCompleted()
	{
		super.onShowCompleted();
		System.gc();
	}

	public void removeAllCommands()
	{
		int cnt = getCommandCount();
		while (cnt > 0)
		{
			--cnt;
			removeCommand(getCommand(cnt));
		}
	}

	public void keyReleased(final int keyCode)
	// public void keyPressed(int keyCode)
	{
		System.out.println("PRESSED " + keyCode);
		// int key = Display.getInstance().getGameAction(keyCode);
		super.keyReleased(keyCode);
		//
		// final Component c =
		// (Display.getInstance().getCurrent().getFocused());
		// if (c != null)
		// {
		// if (c.getParent() != null)
		// {
		// if (c.getParent().getClass() == KGridList.class)
		// {
		// c.keyReleased(keyCode);
		// }
		// }
		// }
	}

	public static L10nResources getRsrc()
	{
		return rsrc;
	}

	public static void setRsrc(L10nResources rsrc)
	{
		MPForm.rsrc = rsrc;
	}
}
