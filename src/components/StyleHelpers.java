package components;

import com.sun.lwuit.Component;
import com.sun.lwuit.Font;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.plaf.Style;

public class StyleHelpers
{
	public static void setPadding(final Component cmp, final int top, final int bottom)
	{
		Style cmpStyle = cmp.getUnselectedStyle();
		cmpStyle.setPadding(Component.TOP, top);
		cmpStyle.setPadding(Component.BOTTOM, bottom);
		cmp.setUnselectedStyle(cmpStyle);
		cmpStyle = cmp.getSelectedStyle();
		cmpStyle.setPadding(Component.TOP, top);
		cmpStyle.setPadding(Component.BOTTOM, bottom);
		cmp.setSelectedStyle(cmpStyle);
		cmpStyle = cmp.getPressedStyle();
		cmpStyle.setPadding(Component.TOP, top);
		cmpStyle.setPadding(Component.BOTTOM, bottom);
		cmp.setPressedStyle(cmpStyle);
	}

	public static void setPadding(final Component cmp, final int top, final int bottom, final int left, final int right)
	{
		Style cmpStyle = cmp.getUnselectedStyle();
		cmpStyle.setPadding(Component.TOP, top);
		cmpStyle.setPadding(Component.BOTTOM, bottom);
		cmpStyle.setPadding(Component.LEFT, left);
		cmpStyle.setPadding(Component.RIGHT, right);
		cmp.setUnselectedStyle(cmpStyle);
		cmpStyle = cmp.getSelectedStyle();
		cmpStyle.setPadding(Component.TOP, top);
		cmpStyle.setPadding(Component.BOTTOM, bottom);
		cmpStyle.setPadding(Component.LEFT, left);
		cmpStyle.setPadding(Component.RIGHT, right);
		cmp.setSelectedStyle(cmpStyle);
		cmpStyle = cmp.getPressedStyle();
		cmpStyle.setPadding(Component.TOP, top);
		cmpStyle.setPadding(Component.BOTTOM, bottom);
		cmpStyle.setPadding(Component.LEFT, left);
		cmpStyle.setPadding(Component.RIGHT, right);
		cmp.setPressedStyle(cmpStyle);
	}

	public static void setMargin(final Component cmp, final int top, final int bottom)
	{
		Style cmpStyle = cmp.getUnselectedStyle();
		cmpStyle.setMargin(Component.TOP, top);
		cmpStyle.setMargin(Component.BOTTOM, bottom);
		cmp.setUnselectedStyle(cmpStyle);
		cmpStyle = cmp.getSelectedStyle();
		cmpStyle.setMargin(Component.TOP, top);
		cmpStyle.setMargin(Component.BOTTOM, bottom);
		cmp.setSelectedStyle(cmpStyle);
		cmpStyle = cmp.getPressedStyle();
		cmpStyle.setMargin(Component.TOP, top);
		cmpStyle.setMargin(Component.BOTTOM, bottom);
		cmp.setPressedStyle(cmpStyle);
	}

	public static void setMargin(final Component cmp, final int top, final int bottom, final int left, final int right)
	{
		Style cmpStyle = cmp.getUnselectedStyle();
		cmpStyle.setMargin(Component.TOP, top);
		cmpStyle.setMargin(Component.BOTTOM, bottom);
		cmpStyle.setMargin(Component.LEFT, left);
		cmpStyle.setMargin(Component.RIGHT, right);
		cmp.setUnselectedStyle(cmpStyle);
		cmpStyle = cmp.getSelectedStyle();
		cmpStyle.setMargin(Component.TOP, top);
		cmpStyle.setMargin(Component.BOTTOM, bottom);
		cmpStyle.setMargin(Component.LEFT, left);
		cmpStyle.setMargin(Component.RIGHT, right);
		cmp.setSelectedStyle(cmpStyle);
		cmpStyle = cmp.getPressedStyle();
		cmpStyle.setMargin(Component.TOP, top);
		cmpStyle.setMargin(Component.BOTTOM, bottom);
		cmpStyle.setMargin(Component.LEFT, left);
		cmpStyle.setMargin(Component.RIGHT, right);
		cmp.setPressedStyle(cmpStyle);
	}

	public static void setBorder(final Component cmp, final Border brdr)
	{
		cmp.getSelectedStyle().setBorder(brdr);
		cmp.getUnselectedStyle().setBorder(brdr);
		cmp.getPressedStyle().setBorder(brdr);
	}

	//public static void setBorder(final Component cmp, final Border b)
	//{
	//	cmp.getSelectedStyle().setBorder(b);
	//	cmp.getUnselectedStyle().setBorder(b);
	//	cmp.getPressedStyle().setBorder(b);
	//}
	public static void setFont(final Component cmp, final Font f)
	{
		cmp.getSelectedStyle().setFont(f);
		cmp.getUnselectedStyle().setFont(f);
		cmp.getPressedStyle().setFont(f);
	}

	public static void setAlignment(final Component cmp, final int align)
	{
		cmp.getSelectedStyle().setAlignment(align);
		cmp.getUnselectedStyle().setAlignment(align);
		cmp.getPressedStyle().setAlignment(align);
	}

	public static void setBgColor(final Component cmp, final int color)
	{
		cmp.getSelectedStyle().setBgColor(color);
		cmp.getUnselectedStyle().setBgColor(color);
		cmp.getPressedStyle().setBgColor(color);
	}

	public static void setBgColor(final Component cmp, final int colorS, final int colorUS, final int colorPR)
	{
		cmp.getSelectedStyle().setBgColor(colorS);
		cmp.getUnselectedStyle().setBgColor(colorUS);
		cmp.getPressedStyle().setBgColor(colorPR);
	}

	public static void setBgColor(final Component cmp, final int[] color)
	{
		StyleHelpers.setBgColor(cmp, color[0], color[1], color[2]);
	}

	public static void setFgColor(final Component cmp, final int color)
	{
		cmp.getSelectedStyle().setFgColor(color);
		cmp.getUnselectedStyle().setFgColor(color);
		cmp.getPressedStyle().setFgColor(color);
	}

	public static void setFgColor(final Component cmp, final int colorS, final int colorUS, final int colorPR)
	{
		cmp.getSelectedStyle().setFgColor(colorS);
		cmp.getUnselectedStyle().setFgColor(colorUS);
		cmp.getPressedStyle().setFgColor(colorPR);
	}

	public static void setFgColor(final Component cmp, final int[] color)
	{
		StyleHelpers.setFgColor(cmp, color[0], color[1], color[2]);
	}

	public static void setBgTransparency(final Component cmp, final int value)
	{
		cmp.getSelectedStyle().setBgTransparency(value);
		cmp.getUnselectedStyle().setBgTransparency(value);
		cmp.getPressedStyle().setBgTransparency(value);
	}

	public static void setBgTransparency(final Component cmp, final int valueS, final int valueUS, final int valuePR)
	{
		cmp.getSelectedStyle().setBgTransparency(valueS);
		cmp.getUnselectedStyle().setBgTransparency(valueUS);
		cmp.getPressedStyle().setBgTransparency(valuePR);
	}
}
