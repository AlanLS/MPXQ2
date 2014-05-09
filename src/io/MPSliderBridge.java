package io;

import java.util.Vector;
import com.sun.lwuit.Slider;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import components.StyleHelpers;

/**
 * @author alan
 * 
 */
public class MPSliderBridge extends Slider
{
	private MPConnectionRequest[]	sources;

	/**
	 * Default constuctor
	 */
	public MPSliderBridge()
	{
		bindProgress((MPConnectionRequest[]) null, this);
	}

	/**
	 * Allows binding progress to an arbitrary slider
	 * 
	 * @param source
	 *            the source connection request
	 * @param s
	 *            the slider
	 */
	public static void bindProgress(final MPConnectionRequest source, final Slider s)
	{
		if (source == null)
		{
			bindProgress((MPConnectionRequest[]) null, s);
		}
		else
		{
			bindProgress(new MPConnectionRequest[] { source }, s);
		}
	}

	/**
	 * Allows binding progress to an arbitrary slider
	 * 
	 * @param sources
	 *            the source connection request (null for all network activity)
	 * @param s
	 *            the slider
	 */
	public static void bindProgress(final MPConnectionRequest[] sources, final Slider s)
	{
		Vector v = null;
		int portions = 1000;
		if (sources != null)
		{
			v = new Vector();
			for (int iter = 0; iter < sources.length; iter++)
			{
				v.addElement(sources[iter]);
			}
			portions = portions / sources.length;
		}
		final Vector sourceVec = v;
		// final int portionPerSource = portions;
		MPNetworkManager.getInstance().addProgressListener(new ActionListener()
		{
			// private float currentLength;
			// private int soFar;
			/**
			 * @inheritDoc
			 */
			public void actionPerformed(ActionEvent evt)
			{
				if (sources != null)
				{
					if (!sourceVec.contains(evt.getSource()))
					{
						return;
					}
				}
				// final KNetworkEvent e = (KNetworkEvent) evt;
				s.setInfinite(true);
				/*
				 * switch (e.getProgressType()) { case
				 * KNetworkEvent.PROGRESS_TYPE_COMPLETED: if (s.isInfinite() ==
				 * false) { s.setInfinite(false);
				 * s.setProgress(s.getMaxValue()); soFar += portionPerSource;
				 * s.setProgress(soFar); } if (sources != null) {
				 * KNetworkManager.getInstance().removeProgressListener(this); }
				 * break; case KNetworkEvent.PROGRESS_TYPE_INITIALIZING:
				 * s.setInfinite(true); break; case
				 * KNetworkEvent.PROGRESS_TYPE_INPUT: case
				 * KNetworkEvent.PROGRESS_TYPE_OUTPUT: if (e.getLength() > 0) {
				 * currentLength = e.getLength(); s.setMaxValue(1000);
				 * s.setInfinite(false); float sentReceived =
				 * e.getSentReceived(); sentReceived = (sentReceived /
				 * currentLength) * portionPerSource; s.setProgress((int)
				 * sentReceived + soFar); //s.setProgress(e.getSentReceived());
				 * //s.setMaxValue(e.getLength()); } else { s.setInfinite(true);
				 * } break; }
				 */
			}
		});
	}

	/**
	 * Displays progress only for the source object, every other object in the
	 * queue before completion will produce an infinite progress. After 100% the
	 * progress will no longer move.
	 * 
	 * @param source
	 *            the request whose progress should be followed
	 */
	public MPSliderBridge(MPConnectionRequest source)
	{
		if (source != null)
		{
			sources = new MPConnectionRequest[] { source };
		}
		bindProgress(sources, this);
	}

	/**
	 * Allows displaying progress of multiple requests being sent
	 * 
	 * @param sources
	 *            the requests whose progress should be followed
	 */
	public MPSliderBridge(MPConnectionRequest[] sources)
	{
		this.sources = sources;
		StyleHelpers.setFgColor(this, (0xff0000));
		bindProgress(sources, this);
	}
}
