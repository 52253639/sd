package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.LineStyle;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.Position;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;

/**
 * 表设置的宽度，
 * */
public class TableDrawManager {

	public static final String COL_KEY_PREFIX = "colKey";
	private static final LineStyle LIINE_STYLE = LineStyle.DOUBLE_LINE_A;
	private KDTable table;
	public static final int VIEW_MODEL = 0;
	public static final int EDIT_MODEL = 1;
	
	
	public TableDrawManager(){
	}
	public TableDrawManager(KDTable table){
		this.table = table;
	}
	
	public void setTable(KDTable table) {
		this.table = table;
	}

	public KDTable getTable() {
		return table;
	}
	
	/**
	 * 设置这些点组成的图形的边框
	 * @param list 点的集合List<Point>
	 * */
	public void setBorder(List list) {
		setBorder(list, null,null);
	}
	
	/**
	 * 设置这些点组成的图形的边框,并设置颜色
	 * @param list 点的集合List<Point>
	 * */
	public void setBorder(List list, Color bkColor,Color boarderColor) {
		for(int i=0; i<list.size(); i++){
			Point p = (Point) list.get(i);
			
			ICell cell = table.getCell(p.x, p.y);
			if(cell == null){
				continue;
			}
			
			Point upP = new Point(p.x - 1, p.y);
			Point downP = new Point(p.x + 1, p.y);
			Point leftP = new Point(p.x, p.y - 1);
			Point rightP = new Point(p.x, p.y + 1);
			
//			LineStyle topLineStyle = LIINE_STYLE;
//			LineStyle dowmLineStyle = LIINE_STYLE;
//			LineStyle leftLineStyle = LIINE_STYLE;
//			LineStyle rightLineStyle = LIINE_STYLE;
			
			if(!list.contains(upP)){				
				if(table.getCell(upP.x, upP.y) != null){
					table.getCell(upP.x, upP.y).getStyleAttributes().setBorderLineStyle(Position.BOTTOM, LIINE_STYLE);
					table.getCell(upP.x, upP.y).getStyleAttributes().setBorderColor(Position.BOTTOM,boarderColor);
				}else{
					cell.getStyleAttributes().setBorderLineStyle(Position.TOP, LIINE_STYLE);
					cell.getStyleAttributes().setBorderColor(Position.TOP,boarderColor);
				}
			}
			if(!list.contains(downP)){				
				if(table.getCell(downP.x, downP.y) != null){
					table.getCell(downP.x, downP.y).getStyleAttributes().setBorderLineStyle(Position.TOP, LIINE_STYLE);
					table.getCell(downP.x, downP.y).getStyleAttributes().setBorderColor(Position.TOP, boarderColor);
				}else{
					cell.getStyleAttributes().setBorderLineStyle(Position.BOTTOM, LIINE_STYLE);
					cell.getStyleAttributes().setBorderColor(Position.BOTTOM, boarderColor);
				}
			}
			if(!list.contains(leftP)){
				if(table.getCell(leftP.x, leftP.y) != null){
					table.getCell(leftP.x, leftP.y).getStyleAttributes().setBorderLineStyle(Position.RIGHT, LIINE_STYLE);
					table.getCell(leftP.x, leftP.y).getStyleAttributes().setBorderColor(Position.RIGHT, boarderColor);
				}else{
					cell.getStyleAttributes().setBorderLineStyle(Position.LEFT, LIINE_STYLE);
					cell.getStyleAttributes().setBorderColor(Position.LEFT, boarderColor);
				}
			}
			if(!list.contains(rightP)){
				if(table.getCell(rightP.x, rightP.y) != null){
					table.getCell(rightP.x, rightP.y).getStyleAttributes().setBorderLineStyle(Position.LEFT, LIINE_STYLE);
					table.getCell(rightP.x, rightP.y).getStyleAttributes().setBorderColor(Position.LEFT, boarderColor);
				}else{
					cell.getStyleAttributes().setBorderLineStyle(Position.RIGHT, LIINE_STYLE);
					cell.getStyleAttributes().setBorderColor(Position.RIGHT, boarderColor);
				}
			}
			
			if(bkColor != null){
				cell.getStyleAttributes().setBackground(bkColor);
			}
		}
	}


	
	/**
	 * 取消这些点集合的边框 ,并清除其背景
	 * */
	public void clearBorder(List list){
		for(int i=0; i<list.size(); i++){
			Point p = (Point) list.get(i);
			
			ICell cell = table.getCell(p.x, p.y);
			if(cell == null){
				continue;
			}
			
			if(table.getCell(p.x -1, p.y) != null){
				table.getCell(p.x -1, p.y).getStyleAttributes().setBorderLineStyle(Position.BOTTOM, LineStyle.NULL_LINE);
			}else{
				cell.getStyleAttributes().setBorderLineStyle(Position.TOP, LineStyle.NULL_LINE);
			}
			
			if(table.getCell(p.x +1, p.y) != null){
				table.getCell(p.x +1, p.y).getStyleAttributes().setBorderLineStyle(Position.TOP, LineStyle.NULL_LINE);
			}else{
				cell.getStyleAttributes().setBorderLineStyle(Position.BOTTOM, LineStyle.NULL_LINE);
			}
			
			if(table.getCell(p.x, p.y-1) != null){
				table.getCell(p.x, p.y-1).getStyleAttributes().setBorderLineStyle(Position.RIGHT, LineStyle.NULL_LINE);
			}else{
				cell.getStyleAttributes().setBorderLineStyle(Position.LEFT, LineStyle.NULL_LINE);
			}
			
			if(table.getCell(p.x, p.y+1) != null){
				table.getCell(p.x, p.y+1).getStyleAttributes().setBorderLineStyle(Position.LEFT, LineStyle.NULL_LINE);
			}else{
				cell.getStyleAttributes().setBorderLineStyle(Position.RIGHT, LineStyle.NULL_LINE);
			}			
			
//			cell.getStyleAttributes().setBorderLineStyle(Position.BOTTOM, LineStyle.NULL_LINE);
			cell.getStyleAttributes().setBackground(Color.WHITE);
//			cell.getStyleAttributes().setBorderLineStyle(Position.TOP, LineStyle.NULL_LINE);
			cell.getStyleAttributes().setBackground(Color.WHITE);
//			cell.getStyleAttributes().setBorderLineStyle(Position.RIGHT, LineStyle.NULL_LINE);
			cell.getStyleAttributes().setBackground(Color.WHITE);
//			cell.getStyleAttributes().setBorderLineStyle(Position.LEFT, LineStyle.NULL_LINE);
			cell.getStyleAttributes().setBackground(Color.WHITE);
		}
	}
	
	/**
	 * 初始化Table的行列数目及行列的宽度
	 * @param rowCount
	 * @param colCount
	 * @param rowHeight
	 * @param colWidth
	 * @param model 察看或者编辑模式,用来控制是否现实网格线
	 * */
	public void initTable(int rowCount, int colCount, int rowHeight, int colWidth, int model) {
		if(table == null){
			throw new RuntimeException("table is null.");
		}
		table.removeColumns();		
		
		for(int i=0; i<colCount; i++){
			IColumn col = table.addColumn();
			col.setKey(COL_KEY_PREFIX + i);
			col.setWidth(colWidth);
		}
		
		IRow headRow = table.addHeadRow();
		for(int i=0; i<colCount; i++){
			headRow.getCell(i).setValue(String.valueOf(i+1));
		}
		
		for(int i=0; i<rowCount; i++){
			IRow row = table.addRow();
			row.setHeight(rowHeight);
		}
		
		if(model == VIEW_MODEL){
			table.setHorizonGridLineVisible(false);
			table.setVerticalGridLineVisible(false);
			
			table.setVerticalHeadGridLineVisible(false);
			table.setHorizonHeadGridLineVisible(false);	
		}
	}
	
	/**
	 * 初始化Table的行列及每行每列的宽度,支持每行的宽度不一样的情况
	 * @deprecated 暂不实现
	 * */
	public void initTable(int rowCount, int colCount, int[] rowHeight, int[] ColWidth) {
		// TODO 自动生成方法存根
	}
	
	/**
	 * 设置是否显示网格线
	 * @param model 取值为 #VIEW_MODEL #EDIT_MODEL，VIEW模式不显示网格线
	 * */
	public void setModel(int model){
		boolean isEditModel = model == EDIT_MODEL;
		table.setHorizonGridLineVisible(isEditModel);
		table.setVerticalGridLineVisible(isEditModel);
		
		table.setVerticalHeadGridLineVisible(isEditModel);
		table.setHorizonHeadGridLineVisible(isEditModel);	
	}
	
	/**
	 * 将选中的单元格绘制边框，并将选中的这些点的集合返回
	 * */
	public List setBorderOfSelectedCells(Color bkColor,Color borderColor) {
		List list = getSelectPointList();
		setBorder(list, bkColor,borderColor);
		return list;
	}

	
	//获得所选单元格的最大范围集合
	public List getMaxNameList(List nameList) {
		if(nameList==null) return null;
		
		int minRowNum = -1;
		int minColNum = -1;
		int maxRowNum = 0;
		int maxColNum = 0;
		for(int i=0;i<nameList.size();i++) {
			Point point = (Point)nameList.get(i);
			if(minRowNum<0 ||(int)point.getX()<minRowNum)
				minRowNum = (int)point.getX();
			if((int)point.getX()>maxRowNum) 
				maxRowNum = (int)point.getX();				
			if(minColNum<0 || (int)point.getY()<minColNum)
				minColNum = (int)point.getY();
			if((int)point.getY()>maxColNum)
				maxColNum = (int)point.getY();	
		}
		
		nameList.clear();
		for(int i=minRowNum;i<=maxRowNum;i++) {
			for(int j=minColNum;j<=maxColNum;j++){
				nameList.add(new Point(i,j));
			}			
		}
		return nameList;
	}
	
	
	private List setNameOfNameList(List nameList,String name,Color bkColor,Color nameColor){
		//合并所选单元格的最大范围集合
		int minRowNum = -1;
		int minColNum = -1;
		int maxRowNum = 0;
		int maxColNum = 0;
		for(int i=0;i<nameList.size();i++) {
			Point point = (Point)nameList.get(i);
			if(minRowNum<0 ||(int)point.getX()<minRowNum)
				minRowNum = (int)point.getX();
			if((int)point.getX()>maxRowNum) 
				maxRowNum = (int)point.getX();				
			if(minColNum<0 || (int)point.getY()<minColNum)
				minColNum = (int)point.getY();
			if((int)point.getY()>maxColNum)
				maxColNum = (int)point.getY();	
		}
		
		nameList.clear();
		for(int i=minRowNum;i<=maxRowNum;i++) {
			for(int j=minColNum;j<=maxColNum;j++){
				nameList.add(new Point(i,j));
			}			
		}		
		
		KDTMergeManager mm = this.table.getMergeManager();
		mm.mergeBlock(minRowNum,minColNum,maxRowNum,maxColNum);
		
		setTextName(minRowNum, minColNum, name,nameColor);
		ICell cell = table.getCell(minRowNum,minColNum);
		if(cell != null)  {
			//cell.getStyleAttributes().setBackground(bkColor);
			cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
			
			cell.getStyleAttributes().setFontSize(5*(maxRowNum-minRowNum+1));
		}
		
		return nameList;
	}
	
	
	/**
	 * 设置选中的单元格的名称  
	 * @param name 要显示的名称
	 * */
	public List setNameOfSelectedCells(String name,Color bkColor,Color nameColor){
		List list = getSelectPointList();
		if(list.isEmpty()){
			return null;
		}	
		return setNameOfNameList(list,name,bkColor,nameColor);		
	}
	
	
	/**
	 * 将选中的点集合返回
	 * @return List<Point> 选中的点的集合
	 * */
	public List getSelectPointList(){
		List list = new ArrayList();
		for (int i = 0; i < this.table.getSelectManager().size(); i++) {
			KDTSelectBlock block = this.table.getSelectManager().get(i);
			if (block == null) {
				return list;
			}
			for (int row = block.getBeginRow(); row <= block.getEndRow(); row++) {
				for (int col = block.getBeginCol(); col <= block
						.getEndCol(); col++) {
					list.add(new Point(row, col));
//					ICell cell = this.table.getCell(row, col);
//					if (cell == null) {
//						continue;
//					}
				}
			}
		}
		return list;
	}
	
	public void setTextName(int x, int y, String name,Color nameColor) {
		ICell cell = table.getCell(x, y);
		if(cell != null)  {
			cell.setValue(name);
			
			if(nameColor!=null)
				cell.getStyleAttributes().setFontColor(nameColor);
		}		
	}

	public void setTextName(List plist, String name,Color bkColor,Color nameColor) {
		if(plist.isEmpty()) return;
		
		setNameOfNameList(plist,name,bkColor,nameColor);
	}

	
	/**
	 * 将点集合选中
	 * @param list List<Point>
	 * */
	public void setSelected(List list) {
		for(int i=0; i<list.size(); i++){
			Point p = (Point) list.get(i);
			ICell cell = table.getCell(p.x, p.y);
			if(cell != null){
				table.getSelectManager().add(p.x, p.y, p.x,p.y);
			}
		}		
	}
	

	/**
	 * 将点集合选中,并给单元格设置房间对象
	 * @param list List<Point>
	 * */
	public void setSelected(List list,RoomInfo room) {
		for(int i=0; i<list.size(); i++){
			Point p = (Point) list.get(i);
			ICell cell = table.getCell(p.x, p.y);
			if(cell != null){
				table.getSelectManager().add(p.x, p.y, p.x,p.y);
				if(room!=null) cell.setUserObject(room);
			}
		}		
	}	

	
	/**
	 * 绘制元素的边框
	 * @param elem
	 */
	public void showElementBorder(PlanisphereElementEntryInfo elem) {
		byte[] lineData = elem.getOutLineLocationData();
		List lineList = CommerceHelper.ByteArrayToListObject(lineData);			
		setBorder(lineList,new Color(elem.getOutLineBackColor()),new Color(elem.getOutLineColor()));
	}
	
	
	/**
	 * 绘制元素的名称  -- 暂且只在一个单元个上绘制
	 * @param elem
	 */
	public void showElementName(PlanisphereElementEntryInfo elem) {
		byte[] nameData = elem.getNameLocationData();
		List nameList = CommerceHelper.ByteArrayToListObject(nameData);
		if(nameList.isEmpty()) return;

		setTextName(nameList, elem.getName(),new Color(elem.getNameBackColor()),new Color(elem.getNameColor()));
	}
	
	
	/**
	 * 绘制某个元素 (轮廓+名称)
	 * @param elem 元素
	 * */
	public void showElement(PlanisphereElementEntryInfo elem){
			showElementBorder(elem);			
			showElementName(elem);
	}
	
	
	/**
	 * 绘制元素集合中每个元素 (轮廓+名称)
	 * @param elementColl 元素集合
	 * */
	public void showElementCollection(PlanisphereElementEntryCollection elementColl){	
		for(int i=0; i<elementColl.size(); i++){
			PlanisphereElementEntryInfo elem = elementColl.get(i);
			showElement(elem);
		}
	}
	

	
	/**
	 * 选中一个元素的轮廓(轮廓内的所有点)
	 * */
	public void setSelectedBorder(PlanisphereElementEntryInfo elem){
		byte[] pos = elem.getOutLineLocationData();
		setSelected(CommerceHelper.ByteArrayToListObject(pos));
	}	
	
	/**
	 * 选中一个元素的名称(名称内的所有点)
	 */
	public void setSelectedName(PlanisphereElementEntryInfo elem){
		byte[] pos = elem.getNameLocationData();
		setSelected(CommerceHelper.ByteArrayToListObject(pos));
	}	

	
	/**
	 * 清除该元素的边框 ,并清除其背景
	 * @param elem 元素对象
	 * */
	public void clearElementBorder(PlanisphereElementEntryInfo elem){
		byte[] pos = elem.getOutLineLocationData();
		 clearBorder(CommerceHelper.ByteArrayToListObject(pos));
		 
	}	
	
	
	/**
	 * 清除元素的名称 ,并清除其背景 ,并才分合并的单元格
	 * @param elem 元素对象
	 * */
	public void clearElementName(PlanisphereElementEntryInfo elem){
		List nameList = CommerceHelper.ByteArrayToListObject(elem.getNameLocationData());
		if(nameList.isEmpty()) return;
		Point p = (Point)nameList.get(0);
		setTextName(nameList, "",Color.WHITE,Color.WHITE);

		//合并所选单元格的最大范围集合
		int minRowNum = -1;
		int minColNum = -1;
		int maxRowNum = 0;
		int maxColNum = 0;
		for(int i=0;i<nameList.size();i++) {
			Point point = (Point)nameList.get(i);
			if(minRowNum<0 || (int)point.getX()<minRowNum)
				minRowNum = (int)point.getX();
			if((int)point.getX()>maxRowNum) 
				maxRowNum = (int)point.getX();				
			if(minColNum<0 || (int)point.getY()<minColNum)
				minColNum = (int)point.getY();
			if((int)point.getY()>maxColNum)
				maxColNum = (int)point.getY();				
		}
		this.table.getMergeManager().splitBlock(minRowNum,minColNum,maxRowNum,maxColNum);
	}	
	
	
	/**
	 * 清除某个元素 (轮廓+名称)
	 * @param elem 元素对象
	 * */
	public void clearElement(PlanisphereElementEntryInfo elem){
		clearElementBorder(elem);
		clearElementName(elem);
	}
	
	
	/**
	 * 元素的名称范围是否在轮廓范围中
	 */
	public boolean isNameInOutLineList(List nameList,List outLineList) {
		boolean isIn = true;
		for(int i=0;i<nameList.size();i++) {
			Point point = (Point)nameList.get(i);
			if(!outLineList.contains(point))
				return false;
		}
		return isIn;
	}
	
	
	/**
	 * 元素轮廓是否存在重叠的部分
	 */
	public boolean isExistSuperpose(List outLineList,List allOutLineList) {
		boolean isSuperpose = false;
		for(int i=0;i<outLineList.size();i++) {
			Point point = (Point)outLineList.get(i);
			if(allOutLineList.contains(point))
				return true;
		}		
		return isSuperpose;
	}
	
	
	
	
	
	
	
	
	
}
