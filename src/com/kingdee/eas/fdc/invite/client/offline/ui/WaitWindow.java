package com.kingdee.eas.fdc.invite.client.offline.ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JWindow;

import com.kingdee.eas.fdc.invite.client.offline.util.ResourceHelper;

public class WaitWindow extends JWindow {

	private Image bgImage=ResourceHelper.getImage("wait_coffee01.png");
	public WaitWindow(){
		this.setSize(85,74);
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(new Point(d.width/2,d.height/2));
		this.setVisible(true);
		this.setIgnoreRepaint(true);
	}
	
	public void paint(Graphics g) {
        g.drawImage(bgImage, 0, 0, 85, 74, this);
	}
	
    public void dispose() {
    	super.dispose();
    }
    
	public static void main(String[] args) {
		new WaitWindow();
	}

}
