package com.kingdee.eas.fdc.schedule.client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ui.face.CoreUIObject;

public class KDImagePanel extends KDPanel implements MouseListener{
	private CoreUIObject ui;
	private File file = null;
	private int tabIndex = 0;

	private Image image = null;

	public KDImagePanel(CoreUIObject ui,int tabIndex) {
		this.ui = ui;
		this.tabIndex = tabIndex;
		this.addMouseListener(this);
	}

	public void paint(Graphics arg0) {
		super.paint(arg0);
		arg0.draw3DRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, true);
		if (image != null) {
			arg0.drawImage(image, 1, 1, this);
		} else {
			arg0.drawString("Ã»ÓÐÍ¼Æ¬", 250, 250);
		}
	}

	public void repaint() {
		super.repaint();
	}

	public void setImageFile(File file) {
		if (file != null) {
			this.file = file;
			String fileName = file.getAbsolutePath();
			Toolkit tl = getToolkit();//file.length();
			image = tl.getImage(fileName);
			if (image != null) {
//				Dimension dimension = new Dimension(image.getWidth(this) + 20,
//						image.getHeight(this) + 20);
				int width = image.getWidth(this) + 10000;
				int height = image.getHeight(this) + 3000;
				Dimension dimension = new Dimension(width,height);
				this.setPreferredSize(dimension.getSize());
			}
		} else {
			image = null;
			Dimension dimension = new Dimension(this.getWidth(), this
					.getHeight());
			this.setPreferredSize(dimension);
		}
		repaint();
	}

	public File getImageFile() {
		return file;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public void mouseClicked(MouseEvent e) {
	
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
	    if (e.getSource() == this && e.getButton() == MouseEvent.BUTTON3) 
	     { 
	         MyPopupMenu popup = new MyPopupMenu(this); 
	         popup.show((Component)this, e.getX(), e.getY()); 
	     } 
	    
	}

	public void mouseReleased(MouseEvent e) {
		
	}
	
    public void actionSavePic_actionPerformed() 
    {
    	try {
			String strFullPath = FileUtil.getSelectFile(ui,true,"jpg");
			
	        byte b[] = null;
	        FileInputStream fis = null;
	        fis = new FileInputStream(file);
	        FileChannel fc = fis.getChannel();
	        long size = fc.size();
	        b = new byte[(new Long(size)).intValue()];
	        MappedByteBuffer mbb = fc.map(java.nio.channels.FileChannel.MapMode.READ_ONLY, 0L, size);
	        mbb.get(b);
	        mbb.clear();
	        fc.close();
	        fis.close();
	        
	        File filePic = null;
			if (b != null) {
				try {				

					filePic = new File(strFullPath);					
			        FileOutputStream fos = new FileOutputStream(filePic);

			        fos.write(b);		            
			        fos.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}finally{

				}					
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	    	
    }
	
	public class MyPopupMenu extends JPopupMenu implements ActionListener 
	{ 
	    JMenuItem savePic; 
	    KDImagePanel useRightButton; 

	    public MyPopupMenu(KDImagePanel urb) 
	     { 
	         useRightButton = urb; 

	         savePic = new JMenuItem("Í¼Æ¬Áí´æÎª");        

	         savePic.addActionListener(this); 	        
	         add(savePic); 	        
	     } 

	    public void actionPerformed(ActionEvent e) 
	     { 
	        if (e.getSource() == savePic) 
	         { 
	        	useRightButton.actionSavePic_actionPerformed();
	         } 	       
	     } 
	} 
}
