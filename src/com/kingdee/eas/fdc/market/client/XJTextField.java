package com.kingdee.eas.fdc.market.client;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class XJTextField extends JTextField implements XPrint{

	public XJTextField() {
		super();
	}

	public XJTextField(Document doc, String text, int columns) {
		super(doc, text, columns);
	}

	public XJTextField(int columns) {
		super(columns);
	}

	public XJTextField(String text, int columns) {
		super(text, columns);
	}

	public XJTextField(String text) {
		super(text);
	}

	public void xPrint(int rX,int rY,Graphics graphics) {
		Font oFont = graphics.getFont();
		graphics.setFont(getFont());
		graphics.drawLine(rX+getX(),rY+getY()+getHeight(),rX+getX()+getWidth(),rY+getY()+getHeight());
		graphics.setFont(oFont);
	}

}
