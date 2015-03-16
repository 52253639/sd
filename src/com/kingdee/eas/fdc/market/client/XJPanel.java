package com.kingdee.eas.fdc.market.client;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class XJPanel extends JPanel implements XPrint{

	public XJPanel() {
		super();
	}

	public XJPanel(boolean arg0) {
		super(arg0);
	}

	public XJPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
	}

	public XJPanel(LayoutManager arg0) {
		super(arg0);
	}

	
	
	public void xPrint(int rX,int rY,Graphics graphics) {
		Component[] comps = getComponents();
		for(int i=0 ; i<comps.length ; i++){
			Component comp = comps[i];
			if(comp instanceof XPrint){
				((XPrint)comp).xPrint(rX+getX(),rY+getY(),graphics);
			}
		}
	}

}
