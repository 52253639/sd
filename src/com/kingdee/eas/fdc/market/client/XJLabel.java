package com.kingdee.eas.fdc.market.client;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class XJLabel extends JLabel implements XPrint{

	public XJLabel() {
		super();
	}

	public XJLabel(Icon arg0, int arg1) {
		super(arg0, arg1);
	}

	public XJLabel(Icon arg0) {
		super(arg0);
	}

	public XJLabel(String arg0, Icon arg1, int arg2) {
		super(arg0, arg1, arg2);
	}

	public XJLabel(String arg0, int arg1) {
		super(arg0, arg1);
	}

	public XJLabel(String arg0) {
		super(arg0);
	}

	/**
	 * @see XPrint
	 */
	public void xPrint(int rX,int rY,Graphics graphics) {
		Font oFont = graphics.getFont();
		graphics.setFont(getFont());
		FontMetrics fm = getFontMetrics(getFont());
		String text = getText();
		int x = rX+getX();
		int h = fm.getHeight()/3;//进行微调，3这个值是经过测试得出的值，可能不适用于更多情况。
		int y = rY+getY()+fm.getHeight() - h;
		if(getHorizontalAlignment() == SwingConstants.CENTER){
			x += ((getWidth()-fm.stringWidth(text))/2);
		}else if(getHorizontalAlignment() == SwingConstants.RIGHT){
			x += (getWidth()-fm.stringWidth(text));
		}
		graphics.drawString(text,x,y);
		graphics.setFont(oFont);
	}
}
