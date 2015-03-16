/**
 * output package name
 */
package com.kingdee.eas.fdc.market.report;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import bsh.This;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.IBasicRender;
import com.kingdee.bos.ctrl.kdf.util.render.SimpleTextRender;
import com.kingdee.bos.ctrl.kdf.util.style.Style;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.AccountStageCollection;
import com.kingdee.eas.fdc.basedata.AccountStageInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.CostAccountListUI;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.basedata.util.CostAccountHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MarketAccountViewReportUI extends AbstractMarketAccountViewReportUI {
	private static final Logger logger = CoreUIObject.getLogger(MarketAccountViewReportUI.class);
	String sign = "";
	Map map = new HashMap();
	/**
	 * output class constructor
	 */
	public MarketAccountViewReportUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new com.kingdee.eas.fdc.market.report.MarketAccountViewReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return null;
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.framework.report.client.CommRptBaseUI#onLoad()
	 */
	public void onLoad() throws Exception {
		setShowDialogOnLoad(true);
		super.onLoad();
		initTable();
		initTree();
	}

	public void addAllColunm(){
		this.tblMain.addHeadRow();
		addOneColumn("id","ID");
		addOneColumn("number","科目代码");
		addOneColumn("name","科目名称");
		addOneColumn("level","级次");
		addOneColumn("isLeaf","是否叶子节点");
		addOneColumn("parent.id","父节点");
		addOneColumn("curProject.id","所在的工程项目");
		addOneColumn("fullOrgUnit.id","所在的组织");
		
		if(params.getObject("yearOrMonth")!=null&&params.getObject("yearOrMonth").equals("year")){
			sign = (String)params.getObject("yearOrMonth");
			//表格添加年列
			if(params.getObject("beginYear")!=null&&params.getObject("endYear")!=null){
				addYearColumn(Integer.parseInt(params.getObject("beginYear").toString()),Integer.parseInt(params.getObject("endYear").toString()));
			}
		}
		if(params.getObject("yearOrMonth")!=null&&params.getObject("yearOrMonth").equals("month")){
			sign = (String)params.getObject("yearOrMonth");
			//表格添加月份列
			if(params.getObject("beginYear")!=null&&params.getObject("endYear")!=null
					&&params.getObject("beginMonth")!=null&&params.getObject("endMonth")!=null){
				addMonthColumn(Integer.parseInt(params.getObject("beginYear").toString()),Integer.parseInt(params.getObject("endYear").toString())
						,Integer.parseInt(params.getObject("beginMonth").toString()),Integer.parseInt(params.getObject("endMonth").toString()));
			}
		}
		//合计列
		IColumn column = this.tblMain.addColumn();
		column.setKey("total");
		this.tblMain.getHeadRow(0).getCell("total").setValue("合计");
		column.getStyleAttributes().setNumberFormat("%r-[=]{#,##0.00}n");
		column.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setBackground(new Color(255,255,218));
	}
	
	public void addOneColumn(String key,String name){
		IColumn column = this.tblMain.addColumn();
		column.setKey(key);
		this.tblMain.getHeadRow(0).getCell(key).setValue(name);
		if(key.equals("number")||key.equals("name")){
			column.getStyleAttributes().setHided(false);
		}else{
			column.getStyleAttributes().setHided(true);
		}
	}
	
	public void addYearColumn(int beginYear,int endYear){
		for(int i=beginYear;i<=endYear;i++){
			IColumn column = this.tblMain.addColumn();
			String columnKey = i+"year";
			String columnName = i+"年";
			column.setKey(columnKey);
			this.tblMain.getHeadRow(0).getCell(columnKey).setValue(columnName);
			column.getStyleAttributes().setNumberFormat("%r-[=]{#,##0.00}n");
			column.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
		}
	}
	public void addMonthColumn(int beginYear,int endYear,int beginMonth,int endMonth){
		int monthS = 1;
		int monthE = 12;
		for(int i=beginYear;i<=endYear;i++){
			if(i == beginYear){
				monthS = beginMonth;
			}else{
				monthS = 1;
			}
			if(i == endYear){
				monthE = endMonth;
			}
			for(int j = monthS;j<=monthE;j++ ){
				IColumn column = this.tblMain.addColumn();
				String columnKey = i+"year"+j+"month";
				String columnName = i+"年"+j+"月";
				column.setKey(columnKey);
				this.tblMain.getHeadRow(0).getCell(columnKey).setValue(columnName);
				column.getStyleAttributes().setNumberFormat("%r-[=]{#,##0.00}n");
				column.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
			}
		}
	}
	
	public void addRow(IRow row,List list,String flag){
		BigDecimal total = new BigDecimal("0.00");
		for(int i=0;i<list.size();i++){
			if(flag.equals("year")){
				Map mapDetial = (Map)list.get(i);
				BigDecimal amount = FDCHelper.toBigDecimal(mapDetial.get("yearAmount"));
				if(mapDetial.get("year")!=null){
					row.getCell(mapDetial.get("year")+"year").setValue(amount);
					total = total.add(amount);
				}
			}
			if(flag.equals("month")){
				Map mapDetial = (Map)list.get(i);
				BigDecimal amount = FDCHelper.toBigDecimal(mapDetial.get("monthAmount"));
				if(mapDetial.get("year")!=null&&mapDetial.get("month")!=null){
					row.getCell(mapDetial.get("year")+"year"+mapDetial.get("month")+"month").setValue(amount);
					total = total.add(amount);
				}
			}
		}
		row.getCell("total").setValue(total);
	}
	protected void initTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		projectTreeBuilder.setDevPrjFilter(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		this.treeMain.setSelectionRow(0);
	}

	protected void initTable() {
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		tblMain.checkParsed();
		tblMain.getDataRequestManager().addDataRequestListener(this);
		tblMain.getDataRequestManager().setDataRequestMode(1);
		enableExportExcel(tblMain);
		// 设置垂直滚动条
		getTableForPrintSetting().setScrollStateVertical(KDTStyleConstants.SCROLL_STATE_SHOW);
		// 设置水平滚动条
		getTableForPrintSetting().setScrollStateHorizon(KDTStyleConstants.SCROLL_STATE_SHOW);
	}

	protected void query() {
		getTableForPrintSetting().removeColumns();
		getTableForPrintSetting().removeRows();
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kingdee.eas.fdc.market.report.AbstractMarketAccountViewReportUI#
	 * treeMain_valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj!=null&&obj instanceof OrgStructureInfo) {
//				params.setObject("fullOrgUnit", treeNode.getUserObject());
//				params.setObject("curProject", null);
				map.put("fullOrgUnit", treeNode.getUserObject());
				map.put("curProject", null);
			}else 
			if(treeNode !=null &&treeNode.getUserObject() instanceof CurProjectInfo){
//				params.setObject("fullOrgUnit", null);
//				params.setObject("curProject", treeNode.getUserObject());//curProject.id
				map.put("fullOrgUnit", null);
				map.put("curProject", treeNode.getUserObject());
			}
			query();
		}
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	public void tableDataRequest(KDTDataRequestEvent arg0) {
		addAllColunm();
		
		
		tblMain.getTreeColumn().setDepth(4);
		tblMain.getTreeColumn().setOrientation(KDTStyleConstants.UP);
		if(map.get("fullOrgUnit")==null&&map.get("curProject")==null){
			return;
		}
//		if(params.getObject("fullOrgUnit")==null&&params.getObject("curProject")==null){
//			return;
//		}
//		Map mapPara = new HashMap();
//		mapPara.put("params", params);
		map.put("beginYear", params.getObject("beginYear"));
		map.put("endYear", params.getObject("endYear"));
		map.put("beginMonth", params.getObject("beginMonth"));
		map.put("endMonth", params.getObject("endMonth"));
		map.put("yearOrMonth", params.getObject("yearOrMonth"));
		try {
			Map map1 = MarketAccountViewReportFacadeFactory.getRemoteInstance().getTableList(map);
			if(map1!=null&&map1.get("list")!=null){				
				List list = (ArrayList)map1.get("list");
				if(list.size()>0){
					for(int i=0;i<list.size();i++){
						Map detialMap = (Map)list.get(i);
						IRow row = this.tblMain.addRow();
						row.getCell("id").setValue(detialMap.get("id"));
						row.getCell("number").setValue(detialMap.get("number"));
						row.getCell("name").setValue(detialMap.get("name"));
						row.getCell("level").setValue(detialMap.get("level"));
						row.getCell("isLeaf").setValue(detialMap.get("isLeaf"));
						row.getCell("parent.id").setValue(detialMap.get("parent.id"));
						row.getCell("curProject.id").setValue(detialMap.get("curProject.id"));
						row.getCell("fullOrgUnit.id").setValue(detialMap.get("fullOrgUnit.id"));
						if(detialMap.get("yearList")!=null){
							List yearList = (List)detialMap.get("yearList");
							if(yearList.size()>0){
								addRow(row,yearList,"year");
							}
						}
						if(detialMap.get("monthList")!=null){
							List monthList = (List)detialMap.get("monthList");
							if(monthList.size()>0){
								addRow(row,monthList,"month");
							}
						}
						if(detialMap.get("level")!=null){
							row.setTreeLevel(Integer.parseInt((String)detialMap.get("level")));
						}
					}
					this.tblMain.setRowCount(list.size());
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		//合计行
		IRow row = this.tblMain.addRow();
		row.getCell("number").setValue("合计");
		row.getStyleAttributes().setBackground(new Color(255,255,218));
		this.tblMain.setRowCount(this.tblMain.getRowCount()+1);
		
		
		for(int i=this.tblMain.getColumn("fullOrgUnit.id").getColumnIndex()+1;i<this.tblMain.getColumnCount();i++){
			BigDecimal columnTotal = new BigDecimal("0.00");
			for(int j=0;j<this.tblMain.getRowCount();j++){
				IRow row1 = this.tblMain.getRow(j);
				if(row1.getCell(i).getValue()!=null&&row1.getCell("isLeaf").getValue().toString().equals("1")
						&&row1.getCell(i).getValue()!=null){
					columnTotal = columnTotal.add((BigDecimal)(row1.getCell(i).getValue()));
				}
			}
			row.getCell(i).setValue(columnTotal);
		}
		
		//上级科目汇总new Color(15789529)
		for(int i=this.tblMain.getColumn("fullOrgUnit.id").getColumnIndex()+1;i<this.tblMain.getColumnCount();i++){
			BigDecimal columnTotal = new BigDecimal("0.00");
			for(int j=this.tblMain.getRowCount()-1;j>=0;j--){
				IRow row1 = this.tblMain.getRow(j);
				if(row1.getCell("isLeaf").getValue()!=null&&row1.getCell("isLeaf").getValue().toString().equals("0")){
					for(int k=0;k<this.tblMain.getRowCount();k++){
						IRow row2 = this.tblMain.getRow(k);
						if(row1.getCell("id").getValue()!=null&&row2.getCell("parent.id").getValue()!=null
								&&row1.getCell("id").getValue().toString().equals(row2.getCell("parent.id").getValue().toString())
								&&row2.getCell(i).getValue()!=null){
							columnTotal = columnTotal.add((BigDecimal)(row2.getCell(i).getValue()));
						}
					}
					if(columnTotal.compareTo(new BigDecimal(0.00))!=0){
						row1.getCell(i).setValue(columnTotal);
						row1.getStyleAttributes().setBackground(new Color(0xF0EDD9));
						columnTotal = new BigDecimal("0.00");
					}
				}
			}
		}
	}
	
    /* 拿到当前组织的所有下级组织的ID
	    * @param applyUnitId		父组织单元Id
	    * @return orgUnitSet		返回组织单元Id集合
	    * @throws BOSException
	    * @throws EASBizException 
	    */
	   public static Set getAllChildUnitByParentUnit(String applyUnitId)
				throws BOSException, EASBizException {
		   	Set orgUnitSet = new HashSet();
		   	
		   	AdminOrgUnitInfo applyUnit = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(applyUnitId));
		   	
		   	EntityViewInfo entityView = new EntityViewInfo();
		   	FilterInfo filter = new FilterInfo();
		   	filter.getFilterItems().add(new FilterItemInfo("longNumber", "%"+applyUnit.getLongNumber()+"%", CompareType.LIKE));
		   	if(applyUnit.getNumber().equals("B")){//房地产
			    	filter.getFilterItems().add(new FilterItemInfo("level", "4", CompareType.EQUALS));
		   	}else{
			    	filter.getFilterItems().add(new FilterItemInfo("level", "5", CompareType.EQUALS));
		   	}
		   	filter.getFilterItems().add(new FilterItemInfo("IsCompanyOrgUnit", "1", CompareType.EQUALS));
		
		   	entityView.setFilter(filter);
		   	
			AdminOrgUnitCollection collection = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitCollection(entityView);
			for (int index = 0; index < collection.size(); index ++)
				orgUnitSet.add(collection.get(index).getId().toString());
			
			orgUnitSet.add(applyUnitId);
			
			return orgUnitSet;
	   }
	
}