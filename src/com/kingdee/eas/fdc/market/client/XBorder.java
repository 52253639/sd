package com.kingdee.eas.fdc.market.client;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class XBorder extends EmptyBorder {

	public XBorder(int top, int left, int bottom, int right) {
		super(top, left, bottom, right);
		
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		
		super.paintBorder(c, g, x, y, width, height);
	}
	
	

	
}
