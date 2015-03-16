package com.kingdee.eas.fdc.market.client;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class XJRadioButton extends JRadioButton implements XPrint{

	public XJRadioButton() {
		super();
	}

	public XJRadioButton(Action arg0) {
		super(arg0);
	}

	public XJRadioButton(Icon arg0, boolean arg1) {
		super(arg0, arg1);
	}

	public XJRadioButton(Icon arg0) {
		super(arg0);
	}

	public XJRadioButton(String arg0, boolean arg1) {
		super(arg0, arg1);
	}

	public XJRadioButton(String arg0, Icon arg1, boolean arg2) {
		super(arg0, arg1, arg2);
	}

	public XJRadioButton(String arg0, Icon arg1) {
		super(arg0, arg1);
	}

	public XJRadioButton(String arg0) {
		super(arg0);
	}

	public void xPrint(int rX,int rY,Graphics graphics) {
		Font oFont = graphics.getFont();
		graphics.setFont(getFont());
		graphics.drawOval(rX+getX()+getWidth()-9,rY+getY()+((getHeight()-8)/2),8,8);
		graphics.setFont(oFont);
	}

}
