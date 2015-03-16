package com.kingdee.eas.fdc.market.client;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;

public class XJCheckBox extends JCheckBox implements XPrint{

	public XJCheckBox() {
		super();
	}

	public XJCheckBox(Action a) {
		super(a);
	}

	public XJCheckBox(Icon icon, boolean selected) {
		super(icon, selected);
	}

	public XJCheckBox(Icon icon) {
		super(icon);
	}

	public XJCheckBox(String text, boolean selected) {
		super(text, selected);
	}

	public XJCheckBox(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
	}

	public XJCheckBox(String text, Icon icon) {
		super(text, icon);
	}

	public XJCheckBox(String text) {
		super(text);
	}

	public void xPrint(int rX,int rY,Graphics graphics) {
		Font oFont = graphics.getFont();
		graphics.setFont(getFont());
		graphics.drawRect(rX+getX()+getWidth()-9,rY+getY()+((getHeight()-8)/2),8,8);
		graphics.setFont(oFont);
	}
}
