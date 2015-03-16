package com.kingdee.eas.fdc.market.client;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTextArea;
import javax.swing.text.Document;

public class XJTextArea extends JTextArea implements XPrint{

	public XJTextArea() {
		super();
	}

	public XJTextArea(Document doc, String text, int rows, int columns) {
		super(doc, text, rows, columns);
	}

	public XJTextArea(Document doc) {
		super(doc);
	}

	public XJTextArea(int rows, int columns) {
		super(rows, columns);
	}

	public XJTextArea(String text, int rows, int columns) {
		super(text, rows, columns);
	}

	public XJTextArea(String text) {
		super(text);
	}

	public void xPrint(int rX,int rY,Graphics graphics) {
		Font oFont = graphics.getFont();
		graphics.setFont(getFont());
		graphics.drawLine(rX+getX(),rY+getY()+getHeight(),rX+getX()+getWidth(),rY+getY()+getHeight());
		graphics.setFont(oFont);
	}

}
