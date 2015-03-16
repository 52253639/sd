package com.kingdee.eas.fdc.market.client;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import com.kingdee.eas.fdc.market.DocumentSubjectInfo;

public class XDimension {
	public int xAxis=0;
	public int yAxis=0;
    public int width=0;//固定数值，当改变宽度的时候要注意改变高度，改变XCell的个数等等
    public int height=-1;//根据XCell来增加高度
	
	private int xWidthMultiple = 0;//宽度系数
	private int subjectNumber = 0;//题号
	List rowCellList; 

	/**
	 * @param width 宽度
	 * @param height 高度
	 * @param widthMutiple 宽度倍数
	 * @param subjectNumber 题号
	 */
	public XDimension(int width, int height,int widthMutiple,int subjectNumber){
		this(0,0,width, height,widthMutiple,subjectNumber);
	}

	/**
	 * 
	 * @param xAxis X坐标
	 * @param yAxis y坐标
	 * @param width 宽度
	 * @param height 高度
	 * @param widthMutiple 宽度倍数
	 * @param subjectNumber 必须大于等于0,因为要根据题号决定增加的幅度
	 */
	public XDimension(int xAxis,int yAxis,int width, int height,int widthMutiple,int subjectNumber) {
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.width = width;
		this.height = height;
		this.xWidthMultiple = widthMutiple;
		this.subjectNumber = subjectNumber;
	}
	
	
	public void setDSubject(List buttonAndTestList,DocumentSubjectInfo subject){
		//清空控件
		getXCellList().clear();
		//高度
		height = -1;
		JComponent[] newCells;
		try {
			newCells = XCellFactory.createXCell(buttonAndTestList,subject, width);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("无法解析数据:" +subject.getTopic()+ subject.getXCellCreter());
		}
		for(int i=0 ; i<newCells.length ; i++){
			this.addXCell(newCells[i]);
		}
	}
	
	public int getMinSplitHeight(){
		if(getXCellCount() > 0){
			return getXCell(0).getHeight();
		}
		return 0;
	}
	
	/**
	 * @param minHeigth 分割出去的高度下限
	 * @param heigth 分割出去的推荐高度
	 * @param maxHeigth 分割出去的高度上限
	 * @return 被分割出来的部分.注意，被分割出来的部分的坐标是：0，0
	 */
	public XDimension split(int minHeigth,int height,int maxHeight){
		int maxMin = 0;
		XDimension xD= new XDimension(xAxis,yAxis,width,0,xWidthMultiple,subjectNumber);
		while( getXCellCount() > 0){
			JComponent iCell = removeXCell(0); 
			xD.addXCell(iCell);
			if(xD.height == height){
				break;
			}else if(xD.height < height){
				maxMin = xD.height;
			}else{
				//比较谁距离height近
				if(height - maxMin < xD.height - maxHeight || xD.height > maxHeight ){
					xD.removeXCell(xD.getXCellCount() - 1);
					addXCell(0, iCell);
				}
				break;
			}
		}
		if(xD.getXCellCount() > 0){
			return xD;
		}else{		
			return null;
		}
	}
	
	public void unit(XDimension dimension){
		//应该检查其宽度
		for(int i=0 ; i<dimension.getXCellCount() ; i++){
			this.addXCell(dimension.getXCell(i));
		}
	}
	

	
	public int getXCellCount(){
		return getXCellList().size();
	}
	
	public JComponent getXCell(int index){
		JComponent re = (JComponent)getXCellList().get(index);
		int xHeight = index>0?-1:0;
		for(int i=0 ; i<index ; i++){
			xHeight ++;
			xHeight += ((JComponent)getXCellList().get(i)).getHeight();
		}
		re.setBounds(this.xAxis, this.yAxis + xHeight, re.getWidth(), re.getHeight());
		return re;
	}
	
	public JComponent removeXCell(int index){
		JComponent xCell = (JComponent)(getXCellList().remove(index));
		int sub = xCell.getHeight() + 1;
		height -= sub;
		return xCell;
	}
	
	public void addXCell(int index,JComponent xCell){
		height ++;
		height += xCell.getHeight();
		getXCellList().add(index, xCell);

	}
	
	public void addXCell(JComponent xCell){
		addXCell(getXCellCount(),xCell);
	}

	private List getXCellList() {
		if(rowCellList == null){
			rowCellList = new ArrayList();
		}
		return rowCellList;
	}
	
	public int getXWidthMultiple() {
		return xWidthMultiple;
	}

	public int getSubjectNumber() {
		return subjectNumber;
	}

}
