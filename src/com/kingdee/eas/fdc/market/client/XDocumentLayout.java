package com.kingdee.eas.fdc.market.client;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.List;

public class XDocumentLayout implements LayoutManager2 {

    /**
     * Constructs a border layout with the horizontal gaps
     * between components.
     * The horizontal gap is specified by <code>hgap</code>.
     *
     * @see #getHgap()
     * @see #setHgap(int)
     *
     * @serial
     */
	int hgap;
	
	List xpageList = new ArrayList();
	
	
	public XDocumentLayout(){
		this(0);
	}
	
	/**
	 * 页面之间的边距
	 * @param hgap
	 */
	public XDocumentLayout(int hgap){
		this.hgap = hgap;
	}
	
	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp instanceof XPage){
			xpageList.add((XPage)comp);
		}else{
			throw new IllegalArgumentException("非XPage类型的控件.");
		}
	}

	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	public void invalidateLayout(Container target) {

	}

	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	public void addLayoutComponent(String name, Component comp) {
		addLayoutComponent(comp,name);
	}

	public void layoutContainer(Container parent) {
		synchronized(parent.getTreeLock()){
			//Insets pInsets = parent.getInsets();
			Dimension pSize = parent.getSize();
			int y = 0;
			for(int i=0 ; i<xpageList.size() ; i++){
				XPage xp = (XPage)xpageList.get(i);
				y += hgap;
				int x = (Math.max(pSize.width,xp.getWidth()) - xp.getWidth()) / 2;
				xp.setLocation(x, y);
				y += xp.getHeight();
			}
		}
	}

	public Dimension minimumLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			Dimension d = new Dimension(0,0);
			for(int i = 0 ; i<xpageList.size() ; i++){
				XPage p = (XPage)xpageList.get(i);
				d.height += p.getMinimumSize().height + hgap;
				d.width = Math.max(d.width, p.getMinimumSize().width);
			}
			return d;
		}
	}

	public Dimension preferredLayoutSize(Container parent) {
		synchronized(parent.getTreeLock()){
			Dimension d = new Dimension(0,0);
			for(int i=0; i<xpageList.size(); i++){
				XPage p = (XPage)xpageList.get(i);
				d.width = Math.max(d.width, p.getWidth());
				d.height += p.getHeight() + hgap;
			}
			return d;
		}
	}

	public void removeLayoutComponent(Component comp) {
		synchronized(comp.getTreeLock()){
			xpageList.remove(comp);
		}
	}

}
