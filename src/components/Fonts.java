package components;

import com.sun.lwuit.Font;

public class Fonts
{
	static final public int		StandardFontID		= 1;
	static final public int		StandardFontBoldID	= Fonts.StandardFontID + 1;
	static final public int		SmallFontID			= Fonts.StandardFontID + 2;
	static final public int		SmallFontBoldID		= Fonts.StandardFontID + 3;
	final public static Font	SmallFont			= Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
	final public static Font	SmallBoldFont		= Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL);
	final public static Font	StandardFont		= Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
	final public static Font	StandardBoldFont	= Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);

	protected Fonts()
	{
	};

	public static Font getFont(final int fntID)
	{
		if (fntID == Fonts.StandardFontBoldID)
		{
			return Fonts.StandardBoldFont;
		}
		else if (fntID == Fonts.SmallFontID)
		{
			return Fonts.SmallFont;
		}
		else if (fntID == Fonts.SmallFontBoldID)
		{
			return Fonts.SmallBoldFont;
		}
		return Fonts.StandardFont;
	}
}
