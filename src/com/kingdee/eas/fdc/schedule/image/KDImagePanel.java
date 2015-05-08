package com.kingdee.eas.fdc.schedule.image;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPanel;

public class KDImagePanel extends KDPanel {
	protected com.kingdee.bos.ctrl.swing.KDLabel mainImgLabel;
	public com.kingdee.bos.ctrl.swing.KDLabel getMainImgLabel() {
		return mainImgLabel;
	}
	
	protected com.kingdee.bos.ctrl.swing.KDLabel descImgLabel; //图片说明

	protected com.kingdee.bos.ctrl.swing.KDImageIcon mainImgIcon; 
	
	private  int mainScale = 100;
	
	private int mainImgAnchorX , mainImgAnchorY;
	private boolean stop = true;
	
	
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	public boolean isStop() {
		return stop;
	}

	private int newW ;
	private int newH ;
	
	private int newX ;
	private int newY ;
    public KDImagePanel() {
    	jbInit();        
        initUIContentLayout();
        addLisener();
    }
    /**
     * output jbInit method
     */
    private void jbInit()
    {
        this.mainImgLabel = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.mainImgLabel.setName("mainImgLabel");
        this.mainImgIcon = new com.kingdee.bos.ctrl.swing.KDImageIcon();
        this.descImgLabel = new KDLabel();
        this.descImgLabel.setName("descImgLabel");
    }


    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout() {
    	/* modified by zhaoqin for R140925-0004 on 2014/11/12 */
    	this.setBounds(new Rectangle(10, 10, 472, 504));
		this.setLayout(new KDLayout());
		this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 472, 504));
		mainImgLabel.setBounds(new Rectangle(302, 186, 100, 19));
		this.add(mainImgLabel, null);
		mainImgLabel.setIcon(mainImgIcon);

		descImgLabel.setBounds(new Rectangle(10, 475, 452, 19));
		this.add(descImgLabel, new KDLayout.Constraints(10, 475, 452, 19,
				KDLayout.Constraints.ANCHOR_BOTTOM
				| KDLayout.Constraints.ANCHOR_LEFT
				| KDLayout.Constraints.ANCHOR_RIGHT));
	}
    public void addLisener(){
    	mainImgLabel.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				
			}

			public void mouseEntered(MouseEvent e) {
				
			}

			public void mouseExited(MouseEvent e) {
				mainImgLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(MouseEvent e) {
				mainImgAnchorX = e.getX();
				mainImgAnchorY = e.getY();
				mainImgLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			public void mouseReleased(MouseEvent e) {
				mainImgLabel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			}});
    	
    	mainImgLabel.addMouseMotionListener(new MouseMotionListener(){

			public void mouseDragged(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int startX = mainImgLabel.getX();
				int startY = mainImgLabel.getY();
				mainImgLabel.setLocation(startX+x-mainImgAnchorX, startY+y-mainImgAnchorY);
			}

			public void mouseMoved(MouseEvent e) {
				
			}});
    	mainImgLabel.addMouseWheelListener(new MouseWheelListener(){

			public void mouseWheelMoved(MouseWheelEvent e) {
				if(stop==false)
					return;
				ImageIcon mainImgIcon = (ImageIcon)(mainImgLabel.getIcon());
				BufferedImage img = (BufferedImage) mainImgIcon.getImage();
				int oldW = mainImgLabel.getWidth();
				int oldH = mainImgLabel.getHeight();
				int oldX = mainImgLabel.getX();
				int oldY = mainImgLabel.getY();
				if(e.getWheelRotation()<0)
					mainScale +=  8;
				else
				{	
					if(oldW<200)
						return;
					mainScale -=  8;
				}
				newW = img.getWidth()*mainScale/100;
				newH = img.getHeight()*mainScale/100;
				
				newX = oldX - (newW-oldW)/2;
				newY = oldY - (newH-oldH)/2;
				if(stop == true){
					stop = false;
					new Thread(new Runnable() {// 定义一个线程匿名内部类
						public void run() {
							dynamicImageBounds(newX,newY,newW,newH);
						}
					}).start();
				}
			}
    	});
    }
    public void dynamicImageBounds(int nx,int ny,int nw ,int nh){
    	if(nw==0||nh==0)
    		return;
    	int x = mainImgLabel.getX();
    	int y = mainImgLabel.getY();
    	int w = mainImgLabel.getWidth();
    	int h = mainImgLabel.getHeight();
    	
    	int sx = x,sy = y,sw=w,sh = h;
    	int xInc = nx - x;
    	int yInc = ny - y;
    	
    	if(xInc==0||yInc==0)
    		return;
    	double ix ,iy;//x 及 y 的增量
    	if(Math.abs(xInc)>Math.abs(yInc)){
    		iy = 1;
    		ix = (double)Math.abs(xInc)/(double)Math.abs(yInc);
    	}else{
    		ix = 1;
    		iy = (double)Math.abs(yInc)/(double)Math.abs(xInc);
    	}
    	int count = 0;
    	while(!stop){
    		if(xInc>0){
    			xInc --;
    			x = (int)((double)sx + (double)count * ix);
    			w = (int)((double)sw - (double)count * 2 * ix);
    			if(x>nx)
    				x = nx;
    			if(w<nw)
    				w = nw;
    		}else if(xInc<0){
    			xInc ++;
    			x = (int)((double)sx - (double)count * ix);
    			w = (int)((double)sw + (double)count * 2 * ix);
    			if(x<nx)
    				x = nx;
    			if(w>nw)
    				w = nw;
    		}
    		if(yInc>0){
    			yInc --;
    			y = (int)((double)sy + (double)count * iy);
    			h = (int)((double)sh - (double)count * 2 * iy);
    			if(y>ny)
    				y = ny;
    			if(h<nh)
    				h = nh;
    		}else if(yInc<0){
    			yInc ++;
    			y = (int)((double)sy - (double)count * iy);
    			h = (int)((double)sh + (double)count * 2 * iy);
    			if(y<ny)
    				y = ny;
    			if(h>nh)
    				h = nh;
    		}
    		count++;
    		mainImgLabel.setBounds(x, y, w, h);
    		if(xInc==0&&yInc==0){
    			stop = true;
    			break;
    		}
    		try {
				Thread.sleep(2);//移动延迟时间，毫秒数
			}
			catch (Exception ex) {
			}	
    	}
    }
    public void setImage(BufferedImage img){
    	setImage(img,false);
	}
    public void setImage(BufferedImage img,boolean dynamic){
    	if(img==null)
    		return;
    	ImageIcon mainImgIcon = (ImageIcon)(mainImgLabel.getIcon());
		int newWidth = img.getWidth();
		int newHeight = img.getHeight();
		/* modified by zhaoqin for R140925-0004 on 2015/01/16 start */
		int getHeight = this.getHeight() - 39;
		//if(img.getWidth()>this.getWidth()||img.getHeight()>this.getHeight()){
		if(img.getWidth()>this.getWidth()||img.getHeight()>getHeight){
			double imgD = 0.0;
			imgD = (double)img.getWidth()/(double)img.getHeight();
			double standardWidth = this.getWidth();
			//double standardHeight = this.getHeight();
			double standardHeight = getHeight;
			double standardD = standardWidth / standardHeight;
			if(imgD<standardD){
				//newHeight = getHeight;
				newHeight = getHeight;
				newWidth = (int)(imgD * standardHeight);
			}else{
				newWidth = this.getWidth();
				newHeight = (int)(standardWidth/imgD);
			}
		}
		int x = (this.getWidth()-newWidth)/2;
		//int y = (this.getHeight()-newHeight)/2;
		int y = (getHeight-newHeight)/2;
		mainImgIcon.setImage(img);
		mainScale = (int)(((double)newWidth / (double)img.getWidth()) * 100);
		
		if(dynamic){
			//mainImgLabel.setBounds(this.getWidth()/2,this.getHeight()/2,10,10);
			mainImgLabel.setBounds(this.getWidth()/2,getHeight/2,10,10);
			/* modified by zhaoqin for R140925-0004 on 2015/01/16 end */
			
			try {
				Thread.sleep(10);//移动延迟时间，毫秒数
			}
			catch (Exception ex) {
			}	
			stop = false;
			newX = x;
			newY = y;
			newW = newWidth;
			newH = newHeight;
			new Thread(new Runnable() {// 定义一个线程匿名内部类
				public void run() {
					dynamicImageBounds(newX,newY,newW,newH);
				}
			}).start();
		}else{
			mainImgLabel.setBounds(x, y, newWidth, newHeight);
		}
    }
    public void setOpaque(boolean isOpaque){
    	super.setOpaque(isOpaque);
    	if(mainImgLabel!=null)
    		this.mainImgLabel.setOpaque(isOpaque);
    }
    
    public BufferedImage getImage(){
    	return (BufferedImage)mainImgIcon.getImage();
    }

    /* modified by zhaoqin for R140925-0004 on 2014/11/12 */
    public void setDesc(String desc) {
		this.descImgLabel.setText(desc);
		this.descImgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.descImgLabel.setToolTipText(null);
		this.mainImgLabel.setToolTipText(desc);
	}
}
