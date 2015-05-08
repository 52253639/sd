package com.kingdee.eas.fdc.schedule.image;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
/***
 * 
 * 此类为动态展示效果图的鼠标事件类
 * 
 * 目的：实现鼠标放置在缩略图上时，移动指针图，到相应的缩略图上，并显示大图
 * @author  周勇
 *
 */
class ImageMouseListener implements MouseListener{
	KDImagePanel mainImgPanel = null;
	JLabel lPointer = null;
	Map imgs = null;
	Map grayImgs = null;
	JLabel lastFocus = null;
	BufferedImage loading = null;
	String imgName = null;
	public String getImgName() {
		return imgName;
	}

	private List imageChangeListeners = new ArrayList();
	/***
	 * 
	 * @param pl 传入显示原始图的panel，主要是要根据panel的大小，设置图形居中显示
	 * @param iL 传入显示原始图的label，主要是为了显示图片
	 * @param p  传入显示指针图的label，用于显示指针图
	 * @param i  传入缩略图的Map<Integer,BufferedImage> [(1,value1),(2,value2),(3,value3)]
	 * @param gi 传入缩略图的灰度图集合，方式同上，key及value值相对应
	 * @param loadimg 传入loading图
	 * @param change 传入接口，需提供具体的实例类，实现获取原始图的方法，及可以调用外部事件方法
	 */
	ImageMouseListener(KDImagePanel pl,JLabel p,Map i,Map gi, BufferedImage loadimg,ImageSelectChangedLisener change){
		mainImgPanel = pl;
//		mainImgLabel = iL;
		lPointer = p;
		imgs = i;
		grayImgs = gi;
		loading = loadimg;
		imageChangeListeners.add(change);
	}
	public void addImageChangeLisener(ImageSelectChangedLisener change){
		imageChangeListeners.add(change);
	}
	/***
	 * 将指针图移动到执行的label上，并加载原始图
	 * @param o
	 */
	public void moveToImg(Object o){
		changeImg(o);
		movePointer(o);
	}
	/***
	 * 变换缩略图及灰度图，显示原始图
	 * @param o
	 */
	public void changeImg(Object o) {
		/***
		 * 如果触发的还是同一个图片，不进行任何操作
		 */
		if(o == lastFocus)
			return;
    	if(o instanceof JLabel){
    		JLabel lo = (JLabel)o;
    		if(o!=null&&lo.getIcon() instanceof ImageIcon){
    			ImageIcon iio = (ImageIcon)lo.getIcon();
    			if(iio!=null){
    				if(lo.isEnabled()&&lo.getX()-lPointer.getX()==4)
    					lastFocus = lo;
    				else
    				{    					
    					if(lastFocus!=null)
    					{
    						lastFocus.setOpaque(false);
    						((ImageIcon)(lastFocus.getIcon())).setImage((BufferedImage)(grayImgs.get(Integer.valueOf(lastFocus.getName()))));
    					}
    					if(!lo.isOpaque())
    					{
    						((ImageIcon)(lo.getIcon())).setImage((BufferedImage)(imgs.get(Integer.valueOf(lo.getName()))));
    						mainImgRepaint(loading);
    						imgName = lo.getName();
    						
    						new Thread(new Runnable() {// 定义一个线程匿名内部类
    							public void run() {
    								if(imageChangeListeners!=null){
    									int i = Integer.valueOf(imgName).intValue();
    									for(Iterator it = imageChangeListeners.iterator();it.hasNext();){
    										ImageSelectChangedLisener changeEvent = (ImageSelectChangedLisener)it.next();
    										BufferedImage img = changeEvent.getOriginalImg(i); 
        									if(img!=null)
        										mainImgRepaint(img);
        									changeEvent.imageSelectChanged(i);
    									}
    									
    								}
    							}
    						}).start();
    					}
    					else
    						((ImageIcon)(lo.getIcon())).setImage((BufferedImage)(grayImgs.get(Integer.valueOf(lo.getName()))));
    					lo.setOpaque(!lo.isOpaque());
						
    				}
    			}
    		}
    	}
    	
	}
	/***
	 * 根据图片的大小，居中显示图片
	 * @param img
	 */
	public void mainImgRepaint(BufferedImage img){
		if(img==loading)
		{
			mainImgPanel.setImage(img, false);
		}
		else
		{
			if(mainImgPanel.isStop()){
				mainImgPanel.setImage(img, true);
			}else{
				try{
					mainImgPanel.setStop(true);
					Thread.sleep(2);
					mainImgPanel.setImage(img, true);
				}catch(Exception ex){
					
				}
			}
			
		}
	}
	/***
	 * 移动指针图，到对应的缩略图上
	 * @param o
	 */
	public void movePointer(Object o){
    	int start_x = lPointer.getX();
    	int taget_x = 0;
		try {
			taget_x = ((JLabel)o).getX();
		} catch (NullPointerException e) {
			return;
		}
    	boolean pointerStop = false;
    	while(!pointerStop){
			if((taget_x-start_x<=4)&&(taget_x-start_x>=3)){//判断面板是否移到指定位置
				pointerStop = true;//停止移动
				((JLabel)o).setOpaque(true);
				if(lastFocus!=null&&lastFocus!=o){
					lastFocus.setOpaque(false);
					((ImageIcon)(lastFocus.getIcon())).setImage((BufferedImage)(grayImgs.get(Integer.valueOf(lastFocus.getName()))));
					lastFocus = (JLabel)o;
				}
				break;
			}
			start_x+=(start_x+4<=taget_x)?2:-2;
			lPointer.setLocation(start_x,3);//重新定位
			try {
				Thread.sleep(1);//移动延迟时间，毫秒数
			}
			catch (Exception ex) {
			}	
		}
    }

	public void mouseClicked(MouseEvent e) {
		
		
	}

	public void mouseEntered(MouseEvent e) {
		final Object o = e.getSource();
		new Thread(new Runnable(){
	    	public void run(){
	    		changeImg(o);
	    		movePointer(o);
	   	     }
			
		}).start(); 
	}

	public void mouseExited(MouseEvent e) {
		final Object o = e.getSource();
		new Thread(new Runnable(){
	    	public void run(){
	    		changeImg(o);
	   	     }
			
		}).start(); 
	}

	public void mousePressed(MouseEvent e) {
		
		
	}

	public void mouseReleased(MouseEvent e) {
		
		
	}
	
};
