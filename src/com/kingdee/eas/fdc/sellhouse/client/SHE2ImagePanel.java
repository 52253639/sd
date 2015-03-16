package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

import com.kingdee.bos.ctrl.kdf.data.logging.Logger;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

public class SHE2ImagePanel extends KDPanel {
	File file = null;
	byte[] imgData = null;

	private Image image = null;

	public SHE2ImagePanel() {

	}

	public void paint(Graphics arg0) {
		super.paint(arg0);
	}
	
	protected   void   paintComponent(Graphics   g)   {  
		super.paintComponent(g);
		if (image != null) {
			g.draw3DRect(0, 0, image.getWidth(this), image.getHeight(this), true);
			g.drawImage(image,   0,   0,   image.getWidth(this),   image.getHeight(this),   this); 
		}
    }
	
	public void repaint() {
		super.repaint();
	}

	
	public void setImageFile(File file){
		try {				
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] buffer = new byte[fileInputStream.available()];
			fileInputStream.read(buffer);
			fileInputStream.close();
			
			setImageData(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void setImageData(byte[] imgData){
		if (imgData != null) {
			this.imgData = imgData;
			image = Toolkit.getDefaultToolkit().createImage(this.imgData);
			image   =   new ImageIcon(image).getImage();  
			if (image != null) {
				Dimension dimension = new Dimension(image.getWidth(this) , image.getHeight(this) );
				this.setPreferredSize(dimension);
			}else{
				Dimension dimension = new Dimension(this.getWidth(), this.getHeight());
				this.setPreferredSize(dimension);
			}
//			repaint();
		}else{
			this.imgData = null;
			image = null;
		}
		
	}

	public byte[] getImageData() {
		return imgData;
	}


}
