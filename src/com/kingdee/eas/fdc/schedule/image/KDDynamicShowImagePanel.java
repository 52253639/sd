/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.image;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.kingdee.bos.ctrl.swing.KDImageIcon;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.util.StringUtils;

/***
 * 设置动态图展示的各个图层 自定义panel 可直接放置到相应panel中使用
 * 
 * @author 周勇
 * @version 1.0
 * 
 */
public class KDDynamicShowImagePanel extends KDPanel implements
		ImageSelectChangedLisener {
	/***
	 * 原始图的显示层
	 */
	protected com.kingdee.eas.fdc.schedule.image.KDImagePanel mainImgPanel;
	/**
	 * 缩略图浮动层的背景层
	 */
	protected com.kingdee.bos.ctrl.swing.KDPanel smallImgsMainPanel;
	/**
	 * 展示原图的Label
	 */
	// protected com.kingdee.bos.ctrl.swing.KDLabel mainImgLabel;
	protected com.kingdee.bos.ctrl.swing.KDImageIcon mainImgIcon;
	/**
	 * 缩略图的浮动层
	 */
	protected com.kingdee.bos.ctrl.swing.KDPanel flowImgPanel;
	protected BufferedImage pointer = null;
	protected BufferedImage loading = null;
	protected com.kingdee.bos.ctrl.swing.KDLabel lPointer = new com.kingdee.bos.ctrl.swing.KDLabel();
	protected Map imgs = null;
	protected Map grayImgs = null;
	protected Map imgLabels = null;
	protected com.kingdee.bos.ctrl.swing.KDLabel lastFocus = null;

	int leftEnd = -140;
	int rightEnd = 50;
	private boolean isRuning = false;
	private boolean flag = true;
	int anchor = 0;
	private int x, y;// 面板位置坐标
	private boolean stop;// 控制面板的移动
	private boolean zoomStop = true;
	private int xLength = 0;
	private ImageSelectChangedLisener changeEvent = null;
	private ImageMouseListener imgMouseListener = null;
	private int mainScale = 100;

	private int mainImgAnchorX, mainImgAnchorY;

	private Map imgNames;
	
	public BufferedImage getLoading() {
		return loading;
	}

	public void setLoading(BufferedImage loading) {
		mainImgPanel.setImage(loading, false);
	}

	public BufferedImage getPointer() {
		return pointer;
	}

	/***
	 * 设置指针图 规格：width=88 height=58
	 * 
	 * @param pointer
	 */
	public void setPointer(BufferedImage pointer) {
		this.pointer = pointer;
	}

	public void addImageChangeLisener(ImageSelectChangedLisener change) {
		if (imgMouseListener != null) {
			imgMouseListener.addImageChangeLisener(change);
		}
	}

	/**
	 * 新增取得当前浏览大图
	 * 
	 * @return
	 */
	public Image getBigIMG() {
		if (mainImgPanel == null || mainImgPanel.getImage() == null) {
			return null;
		} else {
			return mainImgPanel.getImage();
		}
	}

	/***
	 * 设置缩略图的集合，并生成对应的灰度图 缩略图的规格：width=78,height=42 初始化图片显示 明确key值从1开始
	 * 
	 * @param imgs
	 *            缩略图片的集合Map<Integer,BufferedImage>
	 *            [(1,value1),(2,value2),(3,value3)...]
	 */
	public void setImgageData(Map images) {
		if (images == null)
			return;
		imgs = images;
		// 每次重新选择数节点，set缩略图时，需要清空原缩略图map和pnl，否则新加的图片会加到旧图片之后
		// 此处每次调用都new一次map，并且将pnl所有子控件删除，最后刷新pnl
		if (grayImgs == null) {
			grayImgs = new HashMap();
		} else {
			grayImgs.clear();
		}
		if (imgLabels == null) {
			imgLabels = new HashMap();
		} else {
			imgLabels.clear();
		}
		flowImgPanel.removeAll();
		/**
		 * 当项目下微缩图为空时isRuning被设为true，停止了鼠标事件监听(管)
		 */
		isRuning = false;
		imgMouseListener = new ImageMouseListener(mainImgPanel, lPointer, imgs,
				grayImgs, loading, this);

		if (imgs != null) {
			int size = imgs.size();
			flowImgPanel
					.setBounds(10, 5, (size) * 83, flowImgPanel.getHeight());
			x = 10;
			y = 5;
			leftEnd = (flowImgPanel.getWidth() + 100 - smallImgsMainPanel
					.getWidth())
					* -1;
			rightEnd = 100;
			for (int i = 1; i <= size; i++) {
				Integer key = Integer.valueOf(i);
				BufferedImage img = (BufferedImage) imgs.get(key);
				if(img == null){
					continue;
				}
				GrayFilter grayFilter = new GrayFilter();
				BufferedImage gImg = grayFilter.filter(img, null);
				grayImgs.put(key, gImg);
				KDImageIcon ic = new KDImageIcon();
				ic.setImage(gImg);
				KDLabel l = new KDLabel();
				l.setIcon(ic);
				l.setName(String.valueOf(i));
				l.setOpaque(false);
				l.addMouseListener(imgMouseListener);
				flowImgPanel.add(l);
				//l.setToolTipText(String.valueOf(i));
				l.setBounds(10 + 83 * (i - 1), 16, gImg.getWidth(), gImg
						.getHeight());
				l.setBorder(BorderFactory.createLineBorder(Color.darkGray));
				imgLabels.put(key, l);

			}

			KDImageIcon i = new KDImageIcon();
			i.setImage(pointer);
			lPointer.setIcon(i);
			lPointer.setLocation(0, 0);
			lPointer.setBounds(6, lPointer.getY(), pointer.getWidth(), pointer
					.getHeight());
			flowImgPanel.add(lPointer);
		}
		flowImgPanel.updateUI();
		// moveTo(1);
		// flowImgPanel.repaint();
	}

	/**
	 * output class constructor
	 */
	public KDDynamicShowImagePanel() {
		super();
		jbInit();
		initUIContentLayout();
		addLisener();
	}

	/**
	 * output jbInit method
	 */
	private void jbInit() {
		this.mainImgPanel = new com.kingdee.eas.fdc.schedule.image.KDImagePanel();
		this.mainImgPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		this.smallImgsMainPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
		this.flowImgPanel = new com.kingdee.bos.ctrl.swing.KDPanel();
		this.mainImgPanel.setName("mainImgPanel");
		this.smallImgsMainPanel.setName("smallImgsMainPanel");
		this.flowImgPanel.setName("flowImgPanel");
		this.smallImgsMainPanel.setBorder(BorderFactory
				.createLineBorder(Color.gray));
	}

	/**
	 * output initUIContentLayout method
	 */
	public void initUIContentLayout() {
		this.setBounds(new Rectangle(10, 10, 491, 617));
		this.setLayout(new KDLayout());
		this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 491, 617));
		//mainImgPanel.setBounds(new Rectangle(10, 12, 472, 504));
		this.add(mainImgPanel, new KDLayout.Constraints(10, 12, 472, 504,
				KDLayout.Constraints.ANCHOR_TOP
						| KDLayout.Constraints.ANCHOR_BOTTOM
						| KDLayout.Constraints.ANCHOR_LEFT
						| KDLayout.Constraints.ANCHOR_RIGHT));
		smallImgsMainPanel.setBounds(new Rectangle(10, 524, 470, 82));
		this.add(smallImgsMainPanel, new KDLayout.Constraints(10, 524, 470, 82,
				KDLayout.Constraints.ANCHOR_BOTTOM
						| KDLayout.Constraints.ANCHOR_LEFT
						| KDLayout.Constraints.ANCHOR_RIGHT));
		//mainImgPanel.setLayout(null);
		smallImgsMainPanel.setLayout(new FlowLayout());
		flowImgPanel.setBounds(new Rectangle(10, 5, 450, 70));
		smallImgsMainPanel.add(flowImgPanel, null);
		flowImgPanel.setLayout(new FlowLayout());

	}

	public void addLisener() {
		smallImgsMainPanel.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				JPanel o = (JPanel) e.getSource();
				Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
				o.setCursor(cursor);
				anchor = e.getX();
				x = flowImgPanel.getX();
			}

			public void mouseReleased(MouseEvent e) {
				JPanel o = (JPanel) e.getSource();
				Cursor cursor = Cursor.getDefaultCursor();
				o.setCursor(cursor);
				anchor = e.getX();
				x = flowImgPanel.getX();
			}
		});
		smallImgsMainPanel.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent arg0) {
				stop = true;
				final int mx = arg0.getX();
				int targetX = x + mx - anchor;
				if (targetX > rightEnd || targetX < leftEnd)
					return;

				flowImgPanel.setLocation(x + mx - anchor, flowImgPanel.getY());
			}

			public void mouseMoved(MouseEvent arg0) {
				final int mx = arg0.getX();
				if (mx > (smallImgsMainPanel.getWidth() - 50) || mx < 50) {
					if (mx < 50)
						flag = false;
					else
						flag = true;
					if (!isRuning) {
						new Thread(new Runnable() {// 定义一个线程匿名内部内
									public void run() {
										isRuning = true;
										stop = false;
										move();
									}
								}).start();
					}
				} else {
					stop = true;
					isRuning = false;
				}

			}
		});
	}

	/***
	 * 移动浮动缩略图层
	 */
	private void move() {
		x = flowImgPanel.getX();
		while (!stop) {
			if ((flowImgPanel.getX() > rightEnd && flag)
					|| (flowImgPanel.getX() < leftEnd && !flag)) {// 判断面板是否移出窗口
				stop = true;// 停止移动
				isRuning = false;
				x = flowImgPanel.getX();
				break;
			}
			x += flag ? 10 : -10;// 每次横向移动10
			flowImgPanel.setLocation(x, flowImgPanel.getY());// 重新定位
			try {
				Thread.sleep(20);// 移动延迟时间，毫秒数
			} catch (Exception ex) {
			}
		}
	}

	/***
	 * 移动到指定的图片，供外部调用
	 * 
	 * @param i
	 */
	public void moveTo(int i) {
		int startX = flowImgPanel.getX();
		// int nowInt = Math.abs(startX-10)/83;
		// xLength = ((i-nowInt-1)*83);

		// 移动图片不以当前X值判断所在的i定位，因为可能图片张数较小，X值较大时还是第一张
		// 现在改为以第i张图片相对于面板正中心距离判断需移动长度
		xLength = (startX + (i - 1) * 83)
				- (smallImgsMainPanel.getWidth() - 83) / 2;

		final int toImg = i;

		if (xLength > 0)
			flag = false;
		else
			flag = true;

		// if (!isRuning) {
		new Thread(new Runnable() {// 定义一个线程匿名内部内
					public void run() {
						isRuning = true;
						stop = false;
						if (Math.abs(xLength) > 5)
							moveLength(flag, Math.abs(xLength));
						imgMouseListener.moveToImg(imgLabels.get(Integer
								.valueOf(toImg)));
					}
				}).start();
		// }

	}

	/**
	 * 向左，向右移动固定距离
	 * 
	 * @param flag
	 *            右=true，左=false
	 * @param xLength
	 */
	private void moveLength(boolean flag, int xLength) {
		x = flowImgPanel.getX();
		while (!stop) {
			if (xLength <= 0) {// 判断面板是否移出窗口
				stop = true;// 停止移动
				isRuning = false;
				x = flowImgPanel.getX();
				break;
			}
			x += flag ? 10 : -10;// 每次横向移动10
			xLength -= 10;
			flowImgPanel.setLocation(x, flowImgPanel.getY());// 重新定位
			try {
				Thread.sleep(5);// 移动延迟时间，毫秒数
			} catch (Exception ex) {
			}
		}
	}

	public BufferedImage getOriginalImg(int i) {
		return null;
	}

	public void imageSelectChanged(int i) {
		mainScale = 100;
		
		/* modified by zhaoqin for R140925-0004 on 2014/11/12 */
		String desc = (String)getImgNames().get(i);
		if(!StringUtils.isEmpty(desc)) {
			mainImgPanel.setDesc(desc.substring(0, desc.lastIndexOf(".")));
		}
	}
	
	/* modified by zhaoqin for R140925-0004 on 2014/11/12 */
	public void setImgNames(Map imgNames) {
		this.imgNames = imgNames;
	}
	
	public Map getImgNames() {
		if(null == imgNames)
			imgNames = new HashMap();		
		return imgNames;
	}

	/**
	 * 清空图片展示
	 * <p>
	 * 
	 * @author emanon
	 */
	public void clearAll() {
		// Map imgs = new HashMap();
		// Map grayImgs = new HashMap();
		// Map imgLabels = new HashMap();
		flowImgPanel.removeAll();
		flowImgPanel.updateUI();
		mainImgPanel.getMainImgLabel().setBounds(new Rectangle(302, 186, 1, 1));
		mainImgPanel.setImage(new BufferedImage(1, 1,
				BufferedImage.TYPE_INT_BGR));
		mainImgPanel.setDesc(null);
	}
}
