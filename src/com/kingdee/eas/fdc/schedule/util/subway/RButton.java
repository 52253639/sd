package com.kingdee.eas.fdc.schedule.util.subway;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

 class RButton extends KDSubwayItemButton{
	  //红、黄、绿按钮、黑边。。。
	 
	  //蓝
	  private Color BLUE = new Color(79, 129, 189);
	  //默认是深蓝边
	  private Color DARK_BULE =new Color(56, 93, 138);
	  //是否显示红边
	  private boolean isRedRim = false;
	  
	  private Color TAN=new Color(196, 189, 151);
	  
	  private Color Background= BLUE;
	  
	  public RButton(boolean isRedRim) {		  
//	    super();
	    // 这些声明把按钮扩展为一个圆而不是一个椭圆。
	    Dimension size = getPreferredSize();
	    size.width = size.height = Math.max(size.width, size.height);
	    setPreferredSize(size);
	    //这个调用使JButton不画背景，而允许我们画一个圆的背景。
	    setContentAreaFilled(false);
	    this.setBackground(Background);
	    this.isRedRim = isRedRim;
	    this.setOpaque(false);
	    setEnabled(false);
	  }
	  
	  public RButton() {		  
//		    super();
		    // 这些声明把按钮扩展为一个圆而不是一个椭圆。
		    Dimension size = getPreferredSize();
		    size.width = size.height = Math.max(size.width+5, size.height+5);
		    setPreferredSize(size);
		    //这个调用使JButton不画背景，而允许我们画一个圆的背景。
		    setContentAreaFilled(false);
		    this.setBackground(Background);
//		    this.isRedRim = isRedRim;
		    this.setOpaque(false);
		    setEnabled(false);
	  }
	  
	  //显示html提示
	  public RButton(Color bgColor) {		  
		    super(true);
		    Background=bgColor;
		    // 这些声明把按钮扩展为一个圆而不是一个椭圆。
		    Dimension size = getPreferredSize();
		    size.width = size.height = Math.max(size.width+5, size.height+5);
		    setPreferredSize(size);
		    //这个调用使JButton不画背景，而允许我们画一个圆的背景。
		    setContentAreaFilled(false);
//		    this.setBackground(bgColor);
//		    this.isRedRim = isRedRim;
		    this.setOpaque(false);
		    setEnabled(false);

	  }
	  
	  // 画圆的背景和标签
	  protected void paintComponent(Graphics g) {
//		  super.paintComponent(g);
	    if (getModel().isArmed()) {
	      // 你可以选一个高亮的颜色作为圆形按钮类的属性
	      g.setColor(Background);
	    }
	    else {
	      g.setColor(Background);
//	      g.setColor(getBackground());
	    }
	    g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
	    //这个调用会画一个标签和焦点矩形。
	  }
	
	  // 用简单的弧画按钮的边界。
	  protected void paintBorder(Graphics g) {
		  if(isRedRim){			  
			  g.setColor(Color.RED);
		  }else{
			  g.setColor(DARK_BULE);
		  }		  
	    g.drawOval(0, 0, getSize().width - 2 , getSize().height - 2);
//	    this.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200),2));
//	    this.setBorder(new LineBorder(new Color(0,0,0),6));//设置边框


	  }

	  // 侦测点击事件
	  Shape shape;
	  
	  public boolean contains(int x, int y) {
	    // 如果按钮改变大小，产生一个新的形状对象。
	    if (shape == null || !shape.getBounds().equals(getBounds())) {
	      shape = new Ellipse2D.Float(0, 0,getWidth(), getHeight());
	    }
	    return shape.contains(x, y);
	  }
	
//	  // 测试程序
//	  public static void main(String[] args) {
//	    // 产生一个带‘Jackpot’标签的按钮。
//	    JButton button = new RButton(true);
//	//    ImageIcon ic = new ImageIcon("E://clientForMssql//Icons//item_group.gif");
//	//    JButton button2 = new JButton(ic);
//	    JButton button2 = new JButton("hh");
//	    JButton button3 = new RButton();
////	    button.setBackground(BLUE);
//	    // 产生一个框架以显示这个按钮。
//	    JFrame frame = new JFrame();
//	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	    //frame.getContentPane().setBackground(Color.GRAY);
//	    frame.getContentPane().add(button);
//	    frame.getContentPane().add(button2);
//	    frame.getContentPane().add(button3);
//	    frame.getContentPane().setLayout(new FlowLayout());
//	    frame.setSize(200, 200);
//	    frame.setVisible(true);
//	  }
//获取屏幕分辨率
//		    public static void main(String[] args) {
//		       Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//		       System.out.println("Width: " + dim.width);
//		       System.out.println("Height: " + dim.height);
//		    }
		
}
 


