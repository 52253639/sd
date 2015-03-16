package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;

import com.kingdee.eas.fdc.market.DocumentSubjectInfo;



public class XPage extends XJPanel{
	private XDocument xDocument;
	private String header;//页眉
	private String footer;//页脚
	private Font hfFont;//页眉页脚的字体
	
	
	//原始的空间
	private List rL;
	//剩余的空间
	private List rR;
	//已经被占用的区域
	private List rN;
	//已经被占用的区域对应的原题目号
	private List rN2dN;
	//原始的题目
	private List dN;//序号应该是从小到大，但现在还没有此限制
	//当前的指针
	private int dNPointer;//表示增加部分的Index
	private int beSelectedSubjectNumber;
	private Color beSelectedColor;
	
	private Rectangle marge;
	
	private int column;
	

	public XPage(XDocument xDocument){
		super(null);
		this.xDocument = xDocument;
		this.setBackground(Color.WHITE);
		this.addMouseListener(new XMouseListenerPerXPage(this));
	}
	
	public void init(){
		double length = marge.getWidth();
		int columnWidth = (new Double(length/column)).intValue();
		rL = new ArrayList();
		Rectangle[] rLs = new Rectangle[column];
		for(int i=0 ; i<column ; i++){
			rLs[i] = new Rectangle(marge.x + (new Double(i*length/column)).intValue(),marge.y,0,marge.height);
		}
		rLs[column - 1].width = columnWidth;
		for(int i=column - 2 ; i>=0 ; i--){
			rLs[i].width = rLs[i+1].x - 1 - rLs[i].x;
		}
		for(int i=0 ; i<column ; i++){
			rL.add(rLs[i]);
		}
		rN = new ArrayList();
		dN = new ArrayList();
		dNPointer = 0;//指向
		rN2dN = new ArrayList();
		for(int i=0 ; i<rL.size() ; i++){
			rN.add(null);
			rN2dN.add(null);
		}
		rR = new ArrayList();
		for(int i=0 ; i<rL.size() ; i++){
			rR.add(new Rectangle((Rectangle)rL.get(i)));
		}
		//
		add(createHF(header,hfFont,true));
		add(createHF(footer,hfFont,false));
		//
	}
	
	public void clickAtPoint(Point point,int clickCount,boolean isRigth){
		if(xDocument != null){
			int number = getNumberOfDN(point);
			if(isRigth){
				//右击选择
				xDocument.selectedSubjectNumber2(number);
			}else{
				//左击选择
				xDocument.selectedSubjectNumber1(number);
			}
		}
	}
	
	public void setHeaderFooter(String header,String footer,Font hfFont){
		this.header = header;
		this.footer = footer;
		this.hfFont = hfFont;
	}
	
	/**
	 * 创建页眉、页脚
	 * @param str 描述性的文字
	 * @param font 字体属性
	 * @param isHeader 是否页眉，true：页眉；false：页脚
	 * @return
	 */
	public XJLabel createHF(String str,Font font,boolean isHeader){
		XJLabel l = new XJLabel(str);
		l.setFont(font);
		l.setHorizontalAlignment(SwingConstants.CENTER);
		l.setVerticalAlignment(SwingConstants.CENTER);
		if(isHeader){
			l.setBounds(0, 0,this.getSize().width, this.getMarge().y);
		}else{
			l.setBounds(0, this.getMarge().y + this.getMarge().height,this.getSize().width, this.getSize().height - this.getMarge().y - this.getMarge().height);
		}
		return l;
	}
	
	/**
	 * 根据题目信息生成题目控件
	 * @param buttonAndTestList List 将生成的控件组合保存到这个 List 中
	 * @param dSubject DocumentSubjectInfo 题目信息
	 * 
	 * @return XDimension 题目控件
	 */	
	public XDimension createXDimension(List buttonAndTestList,DocumentSubjectInfo dSubject){
		double length = marge.getWidth();
		int columnWidth = (new Double(length/column)).intValue();
		XDimension d = new XDimension(columnWidth*dSubject.getColumnCount()-1,0,dSubject.getColumnCount(),dSubject.getSubjectNumber());
		d.setDSubject(buttonAndTestList,dSubject);
		return d;
	}
	
	public void addXDimension(XDimension newD){
		dN.add(newD);
		reAddAllXDimension();
	}
	
	public int reAddAllXDimension(){
		return reAddAllXDimension(-1);
	}
	
	private int reAddAllXDimension(int toSubjectNumber){
		//int returnValue = 0;//返回值，-1表示无法增加，0表示无可用空间，1表示增加完成
		int firstFreeIndex = 0;
		for(;firstFreeIndex < rR.size() && rR.get(firstFreeIndex)==null;firstFreeIndex++);
		for(;firstFreeIndex < rR.size() && dNPointer < dN.size() && ( toSubjectNumber < 0 || ((XDimension)dN.get(dNPointer)).getSubjectNumber() <= toSubjectNumber );){
			XDimension newD = (XDimension)dN.get(dNPointer);
			if(!rearrange(newD.getXWidthMultiple(),firstFreeIndex) ){
				//增加一个空行
				rR.set(firstFreeIndex, null);
				firstFreeIndex++;
			}else{
				for(firstFreeIndex = 0;firstFreeIndex < rR.size() && rR.get(firstFreeIndex)==null;firstFreeIndex++);
				if(firstFreeIndex+1>rR.size()) return 1; 
				
				Rectangle rr = (Rectangle)rR.get(firstFreeIndex);
				int minH = Math.min(rr.height,newD.height);
				if(newD.getMinSplitHeight() > minH){
					for(int i=0 ; i<newD.getXWidthMultiple() ; i++,firstFreeIndex++){
						ifTrueThenSet(true,rR,firstFreeIndex,null);
					}
				}else if(newD.height > minH){
					//如果待增加的区域有剩余，则需要生成新的区域
					XDimension theNewDimension = newD.split(0, minH, minH);
					if(theNewDimension == null){
						theNewDimension = new XDimension(0,0,newD.width,minH,newD.getXWidthMultiple(),newD.getSubjectNumber());
					}
					dN.add((dNPointer),theNewDimension);
				}else{
					ifTrueThenSet(rN.get(firstFreeIndex) == null,rN,firstFreeIndex,ArrayList.class);
					ifTrueThenSet(rN2dN.get(firstFreeIndex)==null,rN2dN,firstFreeIndex,ArrayList.class);
					newD.xAxis = rr.x;
					newD.yAxis = rr.y;
					//
					((List)rN.get(firstFreeIndex)).add(newD);//增加相同宽度的区域，它是newD的一部分
					addXCellToPanel(newD);
					//
					((List)rN2dN.get(firstFreeIndex)).add(new Integer(dNPointer));
					for(int i=0 ; i<newD.getXWidthMultiple() ; i++){
						if(minH == rr.height){
							ifTrueThenSet(true,rR,firstFreeIndex+i,null);
						} else {
							Rectangle temp_r = (Rectangle)rR.get(firstFreeIndex + i);
							int temp_r_h = temp_r.y + temp_r.height - newD.yAxis - newD.height - 1;
							temp_r.setBounds(temp_r.x,newD.yAxis + newD.height + 1, temp_r.width,temp_r_h);
						}
					}
					dNPointer++;
				}
			}
		}
		updateUI();
		return 1;
	}
	
	private int reAddAll(int suggestdHeight,int maxXColumnWidth,int toSubjectNumber){
		int originalFirstFreeIndex = 0;//原始的第一个空区域
		for(;originalFirstFreeIndex < rR.size() && rR.get(originalFirstFreeIndex)==null;originalFirstFreeIndex++);
		int firstFreeIndex = originalFirstFreeIndex;//当前的空区域
		int realSuggestHeigth = suggestdHeight;//当前空区域剩余的推荐高度
		int minRHeigth = ((Rectangle)rR.get(firstFreeIndex)).height;
		//下面是将所有的块增加到空区域内，以推荐高度为限制
		int temp_lastPoint = -1;//用来计算当前的point被调整的次数
		while(dNPointer < dN.size() && firstFreeIndex<rR.size() && ( toSubjectNumber < 0 || ((XDimension)dN.get(dNPointer)).getSubjectNumber() <= toSubjectNumber )){
			if(realSuggestHeigth <= 0){
				//当前行没有剩余空间，那么前进
				firstFreeIndex ++;
				realSuggestHeigth = suggestdHeight;
			}else{
				XDimension newD = (XDimension)dN.get(dNPointer);
				Rectangle rFree = (Rectangle)rR.get(firstFreeIndex);
				int minH = Math.max(Math.min(realSuggestHeigth,newD.height),newD.getMinSplitHeight());
				if(newD.height > minH && temp_lastPoint != dNPointer){
					//记录此点将被验证
					temp_lastPoint = dNPointer;
					//如果待增加的区域有剩余，则需要生成新的区域
					XDimension theNewDimension = newD.split(0, minH, rFree.height);
					if(theNewDimension == null){
						theNewDimension = new XDimension(0,0,newD.width,minH,newD.getXWidthMultiple(),newD.getSubjectNumber());
					}
					if(newD.height < 0){
						dN.remove(dNPointer);
					}
					dN.add((dNPointer),theNewDimension);
				}else{
					realSuggestHeigth -= minH;
					ifTrueThenSet(rN.get(firstFreeIndex) == null,rN,firstFreeIndex,ArrayList.class);
					ifTrueThenSet(rN2dN.get(firstFreeIndex)==null,rN2dN,firstFreeIndex,ArrayList.class);
					newD.xAxis = rFree.x;
					newD.yAxis = rFree.y;
					//
					((List)rN.get(firstFreeIndex)).add(newD);
					addXCellToPanel(newD);
					//
					((List)rN2dN.get(firstFreeIndex)).add(new Integer(dNPointer));
					rFree.setBounds(rFree.x,(rFree.y + newD.height + 1), rFree.width,(rFree.height - newD.height - 1));
					minRHeigth = Math.min(rFree.height, minRHeigth);
					dNPointer ++;
				}
			}
		}
		if(firstFreeIndex - originalFirstFreeIndex >= maxXColumnWidth){
			return -1;
		}else{
			for(int i=originalFirstFreeIndex ; originalFirstFreeIndex!=firstFreeIndex && i<=firstFreeIndex ; i++){
				if(minRHeigth == 0){
					rR.set(i,null);
				}else{
					Rectangle temp = (Rectangle)rR.get(i);
					temp.y = temp.y + temp.height - minRHeigth;
					temp.height = minRHeigth;
				}
			}
			return 1;
		}
	}
	
	public void goBackPoint(int toThisDnPointer){
		while(toThisDnPointer >= 0 && dNPointer > toThisDnPointer){
			dNPointer--;
			for(int column = (rN2dN.size() - 1); column >=0 ; column--){
				List columnL = (List)rN2dN.get(column);
				int row = (columnL==null)?-1:(columnL.size()-1);
				if(row < 0 || ((Integer)columnL.get(row)).compareTo(new Integer(dNPointer))<0){
					continue;
				}
				for(; row>=0; row--){
					Integer rowInteger = (Integer)columnL.get(row);
					if(rowInteger.intValue() == dNPointer){
						Rectangle theOriginal = (Rectangle)rL.get(column);//原始的区域
						//
						XDimension theRectangle = (XDimension)((List)rN.get(column)).remove(row);//被占用的区域
						removeXCellFromPanel(theRectangle);
						//
						columnL.remove(row);
						Rectangle theFreeRectangle = new Rectangle(theRectangle.xAxis, theRectangle.yAxis, theRectangle.width, (theOriginal.y + theOriginal.height- theRectangle.yAxis ));//还原后形成的新空白区域
						rR.remove(column);
						rR.add(column,theFreeRectangle);
					}else if(rowInteger.intValue() < dNPointer){
						break;
					} 
				}
				ifTrueThenSet(((List)rN2dN.get(column)).size()==0,rN2dN,column,null);
				ifTrueThenSet(((List)rN.get(column)).size() == 0,rN,column,null);
			}
		}
		for(int integratePoint = dNPointer ; integratePoint < dN.size() - 1 ; integratePoint++){
			XDimension dOne = (XDimension)dN.get(integratePoint);
			XDimension dTwo = (XDimension)dN.get(integratePoint + 1);
			if(dOne.getSubjectNumber() == dTwo.getSubjectNumber()){
				dOne.unit(dTwo);
				dN.remove(integratePoint + 1);
				integratePoint--;
			}
		}
	}

	
	/**
	 * 从指定的列调整出指定宽度的空区域
	 * @param xWidth 宽度
	 * @param columnIndex 列，从此列开始向左调整
	 * @return 是否调整完成
	 */
	private boolean rearrange(int xWidth,int columnIndex){
		boolean isOK = (xWidth == 1);//如果宽度为1，那么不需要任何调整就直接返回true
		if(isOK){
			return isOK;
		}else{
			int lastSubjectNumber = getNumberOfDN(dNPointer - 1);//记录题目号
			int firstFreeColumn = columnIndex, originalFirstFreeColumn = columnIndex;
			//第一次执行到这里，尚未进行后退操作，那么如果当前要增加的区域和刚刚增加完成的区域属于同一Subject，此时如果空间够用那么直接返回TRUE，否则FALSE
			if(dNPointer < dN.size() && lastSubjectNumber == getNumberOfDN(dNPointer)){
				//属于同一Subject。进行空间是否充足的判断
				int searchColumn = firstFreeColumn;
				for( ;searchColumn < rR.size() && ((Rectangle)rR.get(firstFreeColumn)).y == ((Rectangle)rR.get(searchColumn)).y && searchColumn < firstFreeColumn + xWidth ; searchColumn++ );
				if((searchColumn - firstFreeColumn) >= xWidth){
					isOK = true;
				}else{
					isOK = false;
				}
				return isOK;
			}
			int minPointer = dNPointer;//maxPointer = dNPointer;
			//循环后退的次数不能超过新块儿的宽度
			boolean flag = true;
			while(flag && originalFirstFreeColumn - firstFreeColumn < xWidth){
				int searchColumn = firstFreeColumn;
				for( ;searchColumn < rR.size() && ((Rectangle)rR.get(firstFreeColumn)).y == ((Rectangle)rR.get(searchColumn)).y && searchColumn < firstFreeColumn + xWidth ; searchColumn++ );
				if((searchColumn - firstFreeColumn) >= xWidth){
					flag = false;//终止循环，因为已经找到合适的宽度了
					//虽然找到了合适的宽度，但还是需要按指定长度进行调整的
					//但是，如果上一个题号跟当前要增加的题号相同，那么不能进行调整。
					int xDimensionLong = 0;//总长度
					for(int i=dNPointer ; i<dN.size() && ((XDimension)dN.get(i)).getSubjectNumber() <= lastSubjectNumber ; i++){
						xDimensionLong += ((XDimension)dN.get(i)).height;
					}
					int suggestedHight = (xDimensionLong + xWidth-1) / xWidth;
					//按照给出的推荐高度进行填充，如果reAddAll返回1，表示填充成功
					isOK = suggestedHight==0?true:reAddAll(suggestedHight,xWidth,lastSubjectNumber) == 1;
				} else {
					if(dNPointer == 0 || ((XDimension)dN.get(dNPointer - 1)).getXWidthMultiple() > 1){
						//遇到宽度大于1的不继续后退，调整失败
						flag = false;
					} else {
						goBackPoint(dNPointer - 1);//会合并同题号的区域
						minPointer--;
						for(firstFreeColumn = 0 ;firstFreeColumn<rR.size() && rR.get(firstFreeColumn) == null ; firstFreeColumn++);
					}
				}
			}
			if(!isOK){
				//还原所有变更
				goBackPoint(minPointer);
				reAddAllXDimension(lastSubjectNumber);
			}
			return isOK;
		}
	}

	
	public List getLeavings(){
		
		List xdl = new ArrayList();
		while(dNPointer < dN.size()){
			xdl.add(dN.remove(dNPointer));
		}
		return xdl;
		
	}
	
	private int getNumberOfDN(int point){
		if(point < 0){
			return 0;
		}else{
			XDimension d = (XDimension)dN.get(point);
			return d.getSubjectNumber();
		}
	}
	
	private int getNumberOfDN(Point xyPoint){
		for(int i=0 ; i<dNPointer ; i++){
			XDimension d = (XDimension)dN.get(i);
			if(isInTheDimension(d, xyPoint)){
				return d.getSubjectNumber();
			}
		}
		return 0;
	}
	
	/**
	 * 判断一个点是否在指定的区域内
	 * @param d 区域
	 * @param p 点坐标
	 */
	private boolean isInTheDimension(XDimension d,Point p){
		return (d.xAxis <= p.x && d.yAxis <= p.y && d.xAxis + d.width >= p.x && d.yAxis + d.height >= p.y);
	}
	
	/**
	 * 显示
	 */
	public void paint(Graphics g) {
		super.paint(g);
		//分割线
		if(column > 1){
			Color old = g.getColor();
			g.setColor(new Color(80,80,80));
			for(int i=column-2 ; i>=0 ; i--){
				Rectangle r = (Rectangle)rL.get(i);
				g.drawLine(r.x + r.width, r.y,r.x + r.width, r.y + r.height);
			}
			g.setColor(old);
		}
		//被选题目
		for(int i=0 ;i<getRN().size() ; i++){
			paintXDimension((List)getRN().get(i),beSelectedColor,g);
		}
	}
	/**
	 * 按指定演示显示区域
	 * @param rL 区域列表
	 * @param c 颜色
	 * @param g 系统提供的画笔
	 */
	private void paintXDimension(List rL,Color c,Graphics g){
		if(rL == null || c == null){
			return;
		}
		Color oldC = g.getColor();
		g.setColor(c);
		for(int i=0 ;i<rL.size(); i++){
			XDimension r = (XDimension)rL.get(i);
			if(r.getSubjectNumber() == beSelectedSubjectNumber){
				g.setColor(c);
				g.drawRect(r.xAxis, r.yAxis, r.width, r.height);
			}
		}
		g.setColor(oldC);
	}
	
	private void addXCellToPanel(XDimension xDimension){
		for(int i=0 ; i<xDimension.getXCellCount() ; i++){
			add(xDimension.getXCell(i));
		}
	}
	
	private void removeXCellFromPanel(XDimension xDimension){
		for(int i=0 ; i<xDimension.getXCellCount() ; i++){
			remove(xDimension.getXCell(i));
		}
	}
	
	/**
	 * 获取本页最小的题目编号
	 * @return
	 */
	public int getMinSubjectNumber(){
		int min = -1;
		if(dN.size() > 0){
			min = ((XDimension)dN.get(0)).getSubjectNumber();
		}
		return min;
	}
	/**
	 * 获取本页最大的题目编号
	 * @return 题目编号
	 */
	public int getMaxSubjectNumber(){
		int max = -1;
		if(dN.size() > 0){
			max = ((XDimension)dN.get(dN.size()-1)).getSubjectNumber();
		}
		return max;
	}
	/**
	 * 根据题目编号，获取该题目在本页的排列序号
	 * @param subjectNumber 题目编号，在全文的编号
	 * @return 序号，未找到则返回-1
	 */
	public int getPointerBySubjectNumber(int subjectNumber){
		int po = -1;
		for(int i=0 ; i<dN.size() ;i++){
			if(((XDimension)dN.get(i)).getSubjectNumber() == subjectNumber){
				po = i;
				break;
			}
		}
		return po;
	}
	
	
	/**
	 * @param list 清空这个List
	 * @param index 从这个index开始到末尾,从0开始到list.size() - 1；
	 */
	public void emptyList(List list,int index){
		if(index == 0){
			list.clear();
		}else{
			while(list.size() > index){
				list.remove(index);
			}
		}
	}
	/**
	 * 设置列表值，根据flag参数将值设置到列表指定的位置
	 * @param flag 判断是否设置值
	 * @param list 列表
	 * @param index 列表索引
	 * @param setter 值
	 */
	public void ifTrueThenSet(boolean flag,List list,int index,Class setter){
		if(flag){
			list.remove(index);
			try {
				if(setter != null){
					list.add(index,setter.newInstance());
				}else{
					list.add(index,null);
				}
			} catch (Exception e) {
				throw new NullPointerException(setter.getName() + "can't be implementd");
			}
		}
	}

	/**
	 * 被占用区域列表
	 */
	public List getRN() {
		return rN;
	}

	/**
	 * 剩余的空间列表
	 */
	public List getRR() {
		return rR;
	}

	/**
	 * @return 本页的列数
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * 设置本页的分割列数
	 * @param column 列数
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	/**
	 * 获取本页的区域
	 */
	public Rectangle getMarge() {
		return marge;
	}

	/**
	 * 设置本页的区域
	 */
	public void setMarge(Rectangle marge) {
		this.marge = marge;
	}
	
	/**
	 * 获取被选中的题目编号
	 * @return 题目编号
	 */
	public int getBeSelectedSubjectNumber() {
		return beSelectedSubjectNumber;
	}

	/**
	 * 设置被选中的题目编号，及被选中后显示的颜色
	 * @param beSelectedSubjectNumber 题目编号
	 * @param beSelectedColor 显示颜色
	 */
	public void setBeSelectedSubjectNumberAndColor(int beSelectedSubjectNumber,Color beSelectedColor) {
		this.beSelectedSubjectNumber = beSelectedSubjectNumber;
		this.beSelectedColor = beSelectedColor;
	}
	
	public void print(Graphics g) {
		super.print(g);
	}

}
