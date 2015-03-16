package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;

import javax.swing.SwingUtilities;

import com.kingdee.bos.ctrl.swing.KDFrame;
import com.kingdee.bos.ctrl.swing.KDLayout;

public class ShowRoomDetailInfoFrame extends KDFrame {

	public ShowRoomDetailInfoFrame() {
		super();
		initUI();
	}
	private void initUI() {
		setUndecorated(true);
		this.setLayout(new BorderLayout());
		this.setBounds(0, 0, 150,145);
	}
	public ShowRoomDetailInfoFrame(GraphicsConfiguration gc) {
		super(gc);
		initUI();
	}
	public ShowRoomDetailInfoFrame(String title) {
		super(title);
		initUI();
	}
	public ShowRoomDetailInfoFrame(String title, GraphicsConfiguration gc) {
		super(title,gc);
		initUI();
	}
	public void show() {
		super.show();
	}
	
	public void show(int x, int y ){
		this.pack();
		int achorX = x;
		int achorY = y;
		Dimension dimension =  this.getToolkit().getScreenSize();
		int height = y + this.getHeight();
		int width  = x + this.getWidth();
		if(height > dimension.getHeight()){//大于就是下面显示不全就要显示在上面
			achorY = y -this.getHeight();
		}
		if(width > dimension.getWidth()){
			achorX = x -this.getWidth();
		}
		this.setLocation(achorX, achorY);
		this.show();
	}
}
