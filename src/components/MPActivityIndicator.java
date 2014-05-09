package components;

import java.io.IOException;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;
import com.sun.lwuit.Painter;
import com.sun.lwuit.animations.Animation;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.geom.Rectangle;

public class MPActivityIndicator implements Animation, Painter
{
	private static MPActivityIndicator	theInstance		= null;
	private int							wid, hei, x, y;
	private Form						parentForm		= null;
	private int							imgwid, imghei;
	private int							currimg			= 0;
	private static Image[]				indicatorImages	= null;

	public static MPActivityIndicator getInstance()
	{
		if (theInstance == null)
		{
			try
			{
				final Image mpIndicatorImage = Image.createImage("/Activity.png");
				indicatorImages = new Image[] { mpIndicatorImage, mpIndicatorImage.rotate(30), mpIndicatorImage.rotate(60), mpIndicatorImage.rotate(90), mpIndicatorImage.rotate(120), mpIndicatorImage.rotate(150), mpIndicatorImage.rotate(180), mpIndicatorImage.rotate(210), mpIndicatorImage.rotate(240), mpIndicatorImage.rotate(270), mpIndicatorImage.rotate(300), mpIndicatorImage.rotate(330) };
			}
			catch (final IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (MPActivityIndicator.theInstance == null)
		{
			MPActivityIndicator.theInstance = new MPActivityIndicator();
			try
			{
				MPActivityIndicator.theInstance.imgwid = indicatorImages[0].getWidth();
				MPActivityIndicator.theInstance.imghei = indicatorImages[0].getHeight();
			}
			catch (final Exception e)
			{
				System.out.println("Unable to load image");
			}
		}
		return MPActivityIndicator.theInstance;
	}

	public boolean animate()
	{
		if (parentForm.getGlassPane() != null)
		{
			parentForm.repaint((wid / 2) - (imgwid / 2), (hei / 2) - (imghei / 2), (wid / 2) + (imgwid / 2), (hei / 2) + (imghei / 2));
			return true;
		}
		return false;
	}

	public void paint(final Graphics g)
	{
		if (parentForm.getGlassPane() != null)
		{
			g.drawImage(indicatorImages[currimg], (x + (wid / 2)) - (imgwid / 2), (y + (hei / 2)) - (imghei / 2));
			currimg++;
			if (currimg == indicatorImages.length)
			{
				currimg = 0;
			}
		}
	}

	public void paint(final Graphics g, final Rectangle rect)
	{
		final Dimension d = rect.getSize();
		x = rect.getX();
		y = rect.getY();
		hei = d.getHeight();
		wid = d.getWidth();
	}

	public void installPane(final Form _parentForm)
	{
		uninstallPane();
		if (_parentForm != null)
		{
			parentForm = _parentForm;
			parentForm.setGlassPane(MPActivityIndicator.getInstance());
			parentForm.registerAnimated(MPActivityIndicator.getInstance());
			// parentForm.revalidate();
		}
	}

	public void uninstallPane()
	{
		if (parentForm != null)
		{
			parentForm.setGlassPane(null);
			parentForm.deregisterAnimated(MPActivityIndicator.getInstance());
			if (Display.getInstance().getCurrent() == parentForm)
			{
				parentForm.repaint();
			}
		}
	}

	public Form getParentForm()
	{
		return parentForm;
	}
}