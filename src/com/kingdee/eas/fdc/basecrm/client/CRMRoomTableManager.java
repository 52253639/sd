package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.SysUtil;

public class CRMRoomTableManager {
	
	KDTable tblMain;	//表
	SellProjectCollection filterSpColl;	//项目范围过滤条件
	BuildingCollection filterBuildColl;	//楼栋范围过滤条件
	BuildingUnitCollection filterUnitColl; //单元范围过滤条件
	
	RoomCollection roomToShowColl = null;	//待显示的房间集合
	
	//-----------------------------------------------
	Set rowFloorNumSet = new HashSet();	//楼层集合 <Integer>	
								//可从中获取到表体中明细行的个数
	Map colKey2RoomCollMap = new HashMap();		//表的列的key值（楼栋id+(","+单元id)+","+"序号"） --> 此列显示的所有房间对象的集合
								//可从列的key值数量获得表体中明细列的个数
	//-----------------------------------------------
	
	private Map orgUnitId2ObjMap = new HashMap();	//组织id与组织对象的对应关系
	private Map sellProId2ObjMap = new HashMap();	//项目id与项目对象的对应关系
	private Map buildingId2ObjMap = new HashMap();	//楼栋id与楼栋对象的对应关系
	private Map buildUnitId2ObjMap = new HashMap();	//单元id与单元对象的对应关系

	
	public KDTable getTblMain() {
		return tblMain;
	}
	public void setTblMain(KDTable tblMain) {
		this.tblMain = tblMain;
	}
	public SellProjectCollection getFilterSpColl() {
		return filterSpColl;
	}
	public void setFilterSpColl(SellProjectCollection filterSpColl) {
		this.filterSpColl = filterSpColl;
	}
	public BuildingCollection getFilterBuildColl() {
		return filterBuildColl;
	}
	public void setFilterBuildColl(BuildingCollection filterBuildColl) {
		this.filterBuildColl = filterBuildColl;
	}
	public BuildingUnitCollection getFilterUnitColl() {
		return filterUnitColl;
	}
	public void setFilterUnitColl(BuildingUnitCollection filterUnitColl) {
		this.filterUnitColl = filterUnitColl;
	}
	public CRMRoomTableManager(KDTable tblMain,
			SellProjectCollection filterSpColl,
			BuildingCollection filterBuildColl,
			BuildingUnitCollection filterUnitColl) {
		super();
		this.tblMain = tblMain;
		this.filterSpColl = filterSpColl;
		this.filterBuildColl = filterBuildColl;
		this.filterUnitColl = filterUnitColl;
	}
	

	public RoomCollection getShowRoomCollection() {
		RoomCollection retRoomColl = new RoomCollection();
		
		boolean isNOFilter = true;		//是否传递了过滤条件
		String viewSql = "select *,building.*,buildUnit.*,building.sellProject.*,building.sellProject.orgUnit.* ";
		if(filterUnitColl!=null && filterUnitColl.size()>0) {
			viewSql += " where buildUnit.id in ("+getIdStringFromColl(filterUnitColl)+") ";
			isNOFilter = false;
		}else if(filterBuildColl!=null && filterBuildColl.size()>0){
			viewSql += " where building.id in ("+getIdStringFromColl(filterBuildColl)+") ";
			isNOFilter = false;
		}else if(filterSpColl!=null && filterSpColl.size()>0) {  //
			viewSql += " where building.sellProject.id in ("+getIdStringFromColl(filterSpColl)+") ";
			isNOFilter = false;
		}
		if(isNOFilter) return retRoomColl; 
		
		try {
			viewSql += " order by building.sellProject.orgUnit.number,building.sellProject.number,building.number,buildUnit.number ";
			retRoomColl = RoomFactory.getRemoteInstance().getRoomCollection(viewSql);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		roomToShowColl = retRoomColl; 
		return retRoomColl;
	}

	
	//从待显示的房间数据中获取表格显示需要的一些数据
	private void initTableInfo() {
		if(roomToShowColl.size()==0) return;
		
		for (int i = 0; i < roomToShowColl.size(); i++) {
			RoomInfo roomInfo = roomToShowColl.get(i);
			
			BuildingUnitInfo buildUnitInfo = roomInfo.getBuildUnit();
			BuildingInfo buildingInfo = roomInfo.getBuilding();
			SellProjectInfo sellProInfo = buildingInfo.getSellProject();
			FullOrgUnitInfo orgUnitInfo = sellProInfo.getOrgUnit();
			if(!buildUnitId2ObjMap.containsKey(buildUnitInfo.getId().toString()))
				buildUnitId2ObjMap.put(buildUnitInfo.getId().toString(), buildUnitInfo);
			if(!buildingId2ObjMap.containsKey(buildingInfo.getId().toString()))
				buildingId2ObjMap.put(buildingInfo.getId().toString(),buildingInfo);
			if(!sellProId2ObjMap.containsKey(sellProInfo.getId().toString()))
				sellProId2ObjMap.put(sellProInfo.getId().toString(), sellProInfo);
			if(!orgUnitId2ObjMap.containsKey(orgUnitInfo.getId().toString()))
				orgUnitId2ObjMap.put(orgUnitInfo.getId().toString(), orgUnitInfo);
			
			//表的列的key值（楼栋id+(","+单元id)+","+"序号"） --> 此列显示的所有房间对象的集合
			String colKey = buildingInfo.getId() + (buildUnitInfo==null?"":(","+buildUnitInfo.getId())) + "," + roomInfo.getSerialNumber();
			RoomCollection colRoomColl = (RoomCollection)colKey2RoomCollMap.get(colKey);
			if(colRoomColl==null) {
				colRoomColl = new RoomCollection();
				colRoomColl.add(roomInfo);
				colKey2RoomCollMap.put(colKey, colRoomColl);
			}else{
				colRoomColl.add(roomInfo);
			}
			
			if(roomInfo.getSerialNumber()!=roomInfo.getEndSerialNumber()) {
				colKey = buildingInfo.getId() + (buildUnitInfo==null?"":(","+buildUnitInfo.getId())) + "," + roomInfo.getEndSerialNumber();
				colRoomColl = (RoomCollection)colKey2RoomCollMap.get(colKey);
				if(colRoomColl==null) {
					colRoomColl = new RoomCollection();
					colRoomColl.add(roomInfo);
					colKey2RoomCollMap.put(colKey, colRoomColl);
				}else{
					colRoomColl.add(roomInfo);
				}
			}
			
			int floorCount = buildingInfo.getFloorCount();
			if(!rowFloorNumSet.contains(new Integer(floorCount))) {
				for (int j = 1; j <= floorCount; j++) {
					rowFloorNumSet.add(new Integer(j));
				}
			}
			int subFloorCount = buildingInfo.getSubFloorCount();
			if(!rowFloorNumSet.contains(new Integer(subFloorCount))) {
				for (int j = subFloorCount; j < 0; j++) {
					rowFloorNumSet.add(new Integer(j));
				}
			}
		}

	}
	
	//创建表头和表体
	private void createRoomTableView() {
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		this.tblMain.getStyleAttributes().setWrapText(true);
		this.tblMain.setHorizonGridLineVisible(true);
		this.tblMain.setVerticalGridLineVisible(true);
		this.tblMain.setVerticalHeadGridLineVisible(true);
		this.tblMain.setHorizonHeadGridLineVisible(true);
		this.tblMain.getIndexColumn().getStyleAttributes().setHided(true);
		this.tblMain.removeColumns();
		this.tblMain.removeHeadRows();
		this.tblMain.removeRows();
		
		IColumn indexColumn = this.tblMain.addColumn();	//楼层
		indexColumn.setKey("floor");
		indexColumn.getStyleAttributes().setLocked(false);
		indexColumn.getStyleAttributes().setBackground(Color.ORANGE);	
		indexColumn.setWidth(50);
		
		Iterator colIter = colKey2RoomCollMap.keySet().iterator();	//列
		while(colIter.hasNext()) {
			String colKey = (String)colIter.next();
			IColumn addColumn = this.tblMain.addColumn();
			addColumn.setKey(colKey);
		}

		Object[] rowFloorNumObjs = rowFloorNumSet.toArray();   //行
		Arrays.sort(rowFloorNumObjs);
		for (int i = rowFloorNumObjs.length-1; i >= 0 ; i--) {
			Integer floorNum = (Integer)rowFloorNumObjs[i];
			IRow addRow = this.tblMain.addRow();
			addRow.getCell("floor").setValue(floorNum.intValue()+"层");
		}
		
		createHeadRowOfTable();					//创建表头,填写列名
		mergeHeadRowOfTable();					//为表头写上名称，同行合并，上下合并(遍历检查上下左右)
	}
	
	
	/**
	 * 合并表头相同的单元格		
	 * 合并原则，从左下角开始，遍历每个单元格，与右上角,右侧和上侧的单元格比较，若相同则分别对角合并、向右合并、向上合并
	 */
	private void mergeHeadRowOfTable() {  
		Set hasDoneCellSet = new HashSet();			//已经做过合并检查的单元格 < 行号+","+列号>
		int headRowCount = this.tblMain.getHeadRowCount();
		int headColCount = this.tblMain.getColumnCount();
		for (int i = headRowCount-1; i >=0 ; i--) {
			for (int j = 0; j < headColCount; j++) {
				ICell currcell = this.tblMain.getHeadRow(i).getCell(j);
				String hasDoneKeyStr = i+","+j;
				if(hasDoneCellSet.contains(hasDoneKeyStr)) continue;
				
				ICell oppositeCell = getTheSameCell(currcell, "opposite");
				if(oppositeCell!=null) {
					int oppRowIndex = oppositeCell.getRowIndex();
					int oppColIndex = oppositeCell.getColumnIndex();
					for(int k=i;k<=oppRowIndex;k--)
						for(int l=j;l<=oppColIndex;l++) {
							hasDoneKeyStr = k+","+l;
							hasDoneCellSet.add(hasDoneKeyStr);
						}
					this.tblMain.getHeadMergeManager().mergeBlock(oppRowIndex, j, i, oppColIndex);
				}else{
					ICell rightCell = getTheSameCell(currcell, "right");
					if(rightCell!=null) {
						int rightColIndex = rightCell.getColumnIndex();
						for (int k = j; k <=rightColIndex; k++) {
							hasDoneKeyStr = i+","+k;
							hasDoneCellSet.add(hasDoneKeyStr);
						}
						this.tblMain.getHeadMergeManager().mergeBlock(i, j, i, rightColIndex);
					}else{
						ICell upCell = getTheSameCell(currcell, "up");
						if(upCell!=null) {
							int upRowIndex = upCell.getRowIndex();
							for (int k = i; k <=upRowIndex; k--) {
								hasDoneKeyStr = k+","+j;
								hasDoneCellSet.add(hasDoneKeyStr);
							}
							this.tblMain.getHeadMergeManager().mergeBlock(upRowIndex, j, i, j);
						}
					}					
				}
			}
		}		
	}

	//typeStr : opposite , right ,up 
	private ICell getTheSameCell(ICell curCell,String typeStr) {  //获得对角最远端的相同内容的单元格  与右上角,右侧和上侧的单元格比较
		if(typeStr==null) return null;
		int colIndex = curCell.getColumnIndex();
		int rowIndex = curCell.getRowIndex();
		String curCellObj = (String)curCell.getUserObject();
		ICell sameCell = null;
		ICell compareCell = null;
		if(typeStr.equals("opposite")) 
			this.tblMain.getCell(rowIndex - 1, colIndex + 1);
		else if(typeStr.equals("right")) 
			this.tblMain.getCell(rowIndex, colIndex + 1);
		else if(typeStr.equals("up")) 
			this.tblMain.getCell(rowIndex - 1, colIndex);
		
		while(compareCell!=null) {
			String cellObj = (String)compareCell.getUserObject();
			if(curCellObj.equals(cellObj)) {
				sameCell = compareCell;
			}else{
				return sameCell;
			}
		}
		return null;
	}
	

	
	
	
	
	private void createBuildAndUnitRow() {
		boolean isShowBuildUnit = false;
		if(buildUnitId2ObjMap.size()>0)	isShowBuildUnit = true;		
		IRow buildRow = this.tblMain.addHeadRow();
		if(isShowBuildUnit){	//存在单元行 （楼栋行 + 单元行）
			IRow unitRow = this.tblMain.addHeadRow();				
			
			Iterator iter = colKey2RoomCollMap.keySet().iterator();
			while(iter.hasNext()) {
				String colkey = (String)iter.next();
				String[] keyValues = colkey.split(",");		//key值（楼栋id+ (","+单元id) +","+"序号"）
				String buildIdKey = keyValues[0];
				if(keyValues.length==3) {	//有单元列
					String unitIdKey = keyValues[1];
					BuildingUnitInfo buildUnitInfo = (BuildingUnitInfo)buildUnitId2ObjMap.get(unitIdKey);
					if(buildUnitInfo!=null)	{							
						unitRow.getCell(colkey).setValue(buildUnitInfo.getName());
						unitRow.getCell(colkey).setUserObject(buildUnitInfo.getId().toString());
					}
					BuildingInfo buildingInfo = (BuildingInfo)buildingId2ObjMap.get(buildIdKey);
					if(buildingInfo!=null) {
						buildRow.getCell(colkey).setValue(buildingInfo.getName());
						buildRow.getCell(colkey).setUserObject(buildingInfo.getId().toString());
					}
				}else if(keyValues.length==2){  //无单元列
					BuildingInfo buildingInfo = (BuildingInfo)buildingId2ObjMap.get(buildIdKey);
					if(buildingInfo!=null) {
						buildRow.getCell(colkey).setValue(buildingInfo.getName());
						buildRow.getCell(colkey).setUserObject(buildingInfo.getId().toString());
						unitRow.getCell(colkey).setValue(buildingInfo.getName());
						unitRow.getCell(colkey).setUserObject(buildingInfo.getId().toString());
					}
				}
			}				
		}else{					//仅仅只有楼栋行
			Iterator iter = colKey2RoomCollMap.keySet().iterator();
			while(iter.hasNext()) {
				String colkey = (String)iter.next();
				String[] keyValues = colkey.split(",");		//key值（楼栋id+","+"序号"）
				if(keyValues.length!=2) {
					FDCMsgBox.showError("表列的key值创建错误，请检查！");
					SysUtil.abort();
				}
				String buildIdKey = keyValues[0];
				BuildingInfo buildingInfo = (BuildingInfo)buildingId2ObjMap.get(buildIdKey);
				if(buildingInfo!=null) {
					buildRow.getCell(colkey).setValue(buildingInfo.getName());
					buildRow.getCell(colkey).setUserObject(buildingInfo.getId().toString());
				}
			}
		}	
	}
	
	private void createHeadRowOfTable(){	//表头的行数
		if(filterUnitColl!=null) {	//仅仅有单元行
			IRow headRow = this.tblMain.addHeadRow();
			Iterator iter = colKey2RoomCollMap.keySet().iterator();
			while(iter.hasNext()) {
				String colkey = (String)iter.next();
				String[] keyValues = colkey.split(",");		//key值（楼栋id+ ","+单元id +","+"序号"）
				if(keyValues.length!=3) {
					FDCMsgBox.showError("表列的key值创建错误，请检查！");
					SysUtil.abort();
				}
				String unitIdKey = keyValues[1];
				BuildingUnitInfo buildUnitInfo = (BuildingUnitInfo)buildUnitId2ObjMap.get(unitIdKey);
				if(buildUnitInfo!=null) {
					headRow.getCell(colkey).setValue(buildUnitInfo.getName());
					headRow.getCell(colkey).setUserObject(buildUnitInfo.getId().toString());	//用户表头的合并单元格
				}
			}
		}else if(filterBuildColl!=null){
			createBuildAndUnitRow();
		}else if(filterSpColl!=null){
			IRow orgUnitRow = null;		//若有多个组织，则显示组织行
			if(orgUnitId2ObjMap.size()>0)  
				orgUnitRow = this.tblMain.addHeadRow();
				
			IRow sellProRow = this.tblMain.addHeadRow();
			 
			createBuildAndUnitRow();
			
			Iterator iter = colKey2RoomCollMap.keySet().iterator();
			while(iter.hasNext()) {
				String colkey = (String)iter.next();
				String[] keyValues = colkey.split(",");		
				String buildIdKey = keyValues[0];
				BuildingInfo buildInfo = (BuildingInfo)buildingId2ObjMap.get(buildIdKey);
				if(buildInfo!=null) {
					SellProjectInfo sellProInfo = (SellProjectInfo)sellProId2ObjMap.get(buildInfo.getSellProject().getId().toString());
					if(sellProInfo!=null) {
						sellProRow.getCell(colkey).setValue(sellProInfo.getName());
						sellProRow.getCell(colkey).setUserObject(sellProInfo.getId().toString());	//用户表头的合并单元格
						if(orgUnitRow!=null) {
							FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)orgUnitId2ObjMap.get(sellProInfo.getId().toString());
							if(orgUnitInfo!=null){
								orgUnitRow.getCell(colkey).setValue(orgUnitInfo.getName());
								orgUnitRow.getCell(colkey).setUserObject(orgUnitInfo.getId().toString());
							}
						}
					}
				}
			}
		}
	}	
	
	
	
	public void showRoomTable(){
		getShowRoomCollection();
		initTableInfo();
		createRoomTableView();
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private String getIdStringFromColl(IObjectCollection objColl){
		String retStr = "";
		if(objColl==null || objColl.size()==0) return retStr;
		Iterator iter = objColl.iterator();
		while(iter.hasNext()){
			Object obj = iter.next();
			if(obj instanceof CoreBaseInfo) {
				retStr += ",'" + ((CoreBaseInfo)obj).getId() + "'";
			}
		}
		if(!retStr.equals("")) retStr = retStr.replaceFirst(",", "");
		return retStr;
	}
	
}
