package io;

import java.io.IOException;
import com.sun.lwuit.Component;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;

/**
 * @author alan
 * 
 */
public class InfiniteProgressIndicator extends Label
{
	private Image[]	angles;
	private int		angle;

	public InfiniteProgressIndicator()
	{
		try
		{
			final Image image = Image.createImage("/wait-circle.png");
			final Image fourtyFiveDeg = image.rotate(45);
			angles = new Image[] { image, fourtyFiveDeg, image.rotate(90), fourtyFiveDeg.rotate(90), image.rotate(180), fourtyFiveDeg.rotate(180), image.rotate(270), fourtyFiveDeg.rotate(270) };
			getStyle().setBgTransparency(0);
			setIcon(image);
			getStyle().setAlignment(Component.CENTER);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	public void initComponent()
	{
		getComponentForm().registerAnimated(this);
	}

	public boolean animate()
	{
		angle++;
		setIcon(angles[Math.abs(angle % angles.length)]);
		return true;
	}
}
