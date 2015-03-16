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

public class SHEImagePanel extends KDPanel {
	File file = null;
	byte[] imgData = null;

	private Image image = null;

	public SHEImagePanel() {

	}

	public void paint(Graphics arg0) {
		super.paint(arg0);
		arg0.draw3DRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, true);
		if (image != null) {
			arg0.drawImage(image, 1, 1, this);
		} else {
			arg0.drawString("没有导入图片", 250, 250);
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
				Dimension dimension = new Dimension(image.getWidth(this) + 2, image.getHeight(this) + 2);
				this.setPreferredSize(dimension);
			}else{
				Dimension dimension = new Dimension(this.getWidth(), this.getHeight());
				this.setPreferredSize(dimension);
			}
			repaint();
		}else{
			this.imgData = null;
			image = null;
		}
		
	}

	public byte[] getImageData() {
		return imgData;
	}


}
