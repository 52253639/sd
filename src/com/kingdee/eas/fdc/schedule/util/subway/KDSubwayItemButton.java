package com.kingdee.eas.fdc.schedule.util.subway;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;

/**
 * 地铁图节点
 * <p>
 * 一个节点实际上是一个按钮，鼠标移入时显示明细信息，移出时隐藏<br>
 * 明细信息的展示，由渲染器KDSubwayItemRenderer决定<br>
 * 渲染器根据明细值返回任意面板，然后将面板展示出来
 * 
 * @author emanon
 * 
 */
public class KDSubwayItemButton extends JButton {
	
	private static final long serialVersionUID = -2027361468833294534L;

	// 弹出窗口
	private JDialog dlgDetail = null;
	// 明细面板
	private JComponent comptDetail = null;
	// 渲染器
	private KDSubwayItemRenderer renderer = null;
	// 明细值
	private Object detail = null;
	// 明细值
	private boolean  isExecutStage = false;
	
	public KDSubwayItemButton() {
//		addMouseListener(new MouseListener() {
//
//			public void mouseClicked(MouseEvent arg0) {
//				showDetail();
//			}
//
//			public void mouseEntered(MouseEvent arg0) {
//				showDetail();
//			}
//
//			public void mouseExited(MouseEvent arg0) {
//				unShowDetail();
//			}
//
//			public void mousePressed(MouseEvent arg0) {
//				showDetail();
//			}
//
//			public void mouseReleased(MouseEvent arg0) {
//				unShowDetail();
//			}
//		});
	    setEnabled(false);

	}
	public KDSubwayItemButton(boolean isExecutStage) {
		
		this.isExecutStage =isExecutStage;
		
		if(this.isExecutStage){
		
			addMouseListener(new MouseListener() {
				
				public void mouseClicked(MouseEvent arg0) {
//					showDetail();					
				}
				
				public void mouseEntered(MouseEvent arg0) {
					showDetail();
				}
				
				public void mouseExited(MouseEvent arg0) {
					unShowDetail();
				}
				
				public void mousePressed(MouseEvent arg0) {
//					showDetail();
				}
				
				public void mouseReleased(MouseEvent arg0) {
//					unShowDetail();
				}
			});
		}
	    setEnabled(false);
	}
	public void setRenderer(KDSubwayItemRenderer renderer) {
		this.renderer = renderer;
	}

	public KDSubwayItemRenderer getRenderer() {
		return renderer;
	}

	public void setDetail(Object detail) {
		this.detail = detail;
	}

	public Object getDetail() {
		return detail;
	}
//	  // 用简单的弧画按钮的边界。
//	protected void paintBorder(Graphics g) {
//		g.setColor(new Color(56, 93, 138));
//	}
	/**
	 * 展示明细信息<br>
	 * 根据renderer，将内容面板置入Dialog内，并展示
	 */
	private void showDetail() {		
		if (renderer != null) {
			comptDetail = renderer.getSubwayRendererCompt(this, detail);
			if (comptDetail != null) {
				dlgDetail = new JDialog();
				dlgDetail.setUndecorated(true);
				Dimension preferredSize = comptDetail.getPreferredSize();
				// dlgDetail.setPreferredSize(preferredSize);
				dlgDetail.setSize(preferredSize);
				Point location = this.getLocationOnScreen();
				dlgDetail.setBounds(location.x, location.y + this.getHeight() + 2, preferredSize.width, preferredSize.height);
				dlgDetail.add(comptDetail);
				dlgDetail.setVisible(true);
			}
		}
	}

	/**
	 * 隐藏明细信息
	 */
	private void unShowDetail() {
		if (dlgDetail == null) {
			return;
		}
		dlgDetail.setVisible(false);
		comptDetail = null;
	}

}
