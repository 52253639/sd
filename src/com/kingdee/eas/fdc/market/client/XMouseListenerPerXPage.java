package com.kingdee.eas.fdc.market.client;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class XMouseListenerPerXPage implements MouseListener {
	private XPage xPage;
	
	
	public XMouseListenerPerXPage(XPage xPage){
		this.xPage = xPage;
	}
	

	public void mouseClicked(MouseEvent e) {
		xPage.clickAtPoint(e.getPoint(),e.getClickCount(),e.getButton() == MouseEvent.BUTTON3);
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}
}
