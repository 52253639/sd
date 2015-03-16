/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.MonthSellReportFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class MonthSellReportUI extends AbstractMonthSellReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(MonthSellReportUI.class);
    
    private Map rowByIdsMap = null;
    
   // private Set comanyIdSet = null;
   // private Set sellProIdSet = null;
   // private Set proOrBuildIdSet = null;
    
    private boolean isAuditDate = false;
    
    private boolean isFirstTime = true;
    
    
    private Set allBuildingIds = null; // 所包含楼栋id
    
    public MonthSellReportUI() throws Exception
    {
        super();
        
        RoomDisplaySetting setting= new RoomDisplaySetting();
    	this.isAuditDate = setting.getBaseRoomSetting().isAuditDate();
    }

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}
	public void actionExport_actionPerformed(ActionEvent e) throws Exception{
		try{
			super.actionExport_actionPerformed(e);
		}
		catch(BOSException exc){
			
		}
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
	}
    
	protected boolean initDefaultFilter() {
		return true;
	}
	
	protected String getKeyFieldName() {
		return "PBID";
	}
	
	private CommonQueryDialog commonQueryDialog = null;
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	private MonthSellReportFilterUI filterUI = null;

	private MonthSellReportFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new MonthSellReportFilterUI();
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		//this.actionRefresh.setVisible(false);
		this.actionLocate.setVisible(false);
		this.menuEdit.setVisible(false);
		
	}
	
	
	
	protected void execQuery() {
		//this.tblMain.checkParsed();
		
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		
		
		if(this.tblMain.getRowCount()>0) 
			this.tblMain.removeRows();
		
		
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		Date beginMonthDate = filterMap.get("BeginMonthDate")==null?null:new Date(((Timestamp)filterMap.get("BeginMonthDate")).getTime());
		Date endMonthDate = filterMap.get("EndMonthDate")==null?null:new Date(((Timestamp)filterMap.get("EndMonthDate")).getTime());
		if(beginMonthDate==null || endMonthDate==null) return;
		Date beginYearDate = filterMap.get("BeginYearDate")==null?null:new Date(((Timestamp)filterMap.get("BeginYearDate")).getTime());
		Date endYearDate = filterMap.get("EndYearDate")==null?null:new Date(((Timestamp)filterMap.get("EndYearDate")).getTime());
		if(beginYearDate==null || endYearDate==null) return;		
		//boolean isPreIntoSale = filterMap.get("isPreIntoSale")==null?false:(((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false);	
		boolean isIncludeAttach = filterMap.get("isIncludeAttach")==null?false:(((Integer)filterMap.get("isIncludeAttach")).intValue()>0?true:false);
		boolean showByProductType = filterMap.get("showByProductType")==null?false:(((Integer)filterMap.get("showByProductType")).intValue()>0?true:false);
		boolean showByBuilding = filterMap.get("showByBuilding")==null?false:(((Integer)filterMap.get("showByBuilding")).intValue()>0?true:false);
		if(!showByProductType && !showByBuilding) return;
		//boolean chkIsShowSpecial = filterMap.get("chkIsShowSpecial")==null?false:(((Integer)filterMap.get("chkIsShowSpecial")).intValue()>0?true:false);
		//chkIsShowSpecial = true;
		filterMap.put("isAuditDate", Boolean.valueOf(this.isAuditDate));
		//filterMap.put("chkIsShowSpecial", new Integer(1));
		
		
		String showByType = "PRODUCTTYPE.FID";
		if(showByBuilding)	 {
			showByType = "BUILDING.FID";
			this.tblMain.getColumn("productType").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("buildingName").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("subarea.name").getStyleAttributes().setHided(false);
		}else{
			this.tblMain.getColumn("productType").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("buildingName").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("subarea.name").getStyleAttributes().setHided(true);
		}
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		filterMap.put("saleOrg", saleOrg);
		if(allBuildingIds==null){ //没有初始化
			boolean isbizunit = SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit();
			try {
				this.kDTree1.setModel(SHEHelper.getUnitTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys,isbizunit));
			
				this.kDTree1.expandAllNodes(true, (TreeNode) this.kDTree1.getModel().getRoot());
		
				this.getAllBuildingIds((TreeNode)this.kDTree1.getModel().getRoot());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(allBuildingIds.size() == 0) {//所选节点下没有数据
				this.tblMain.removeRows();
				return;
		}
		filterMap.put("allBuildingIds", allBuildingIds);
		
		
		
		String baseFilter = " (ORGUNIT.FNumber = '"+saleOrg.getNumber().trim()+"' or ORGUNIT.FLongNumber like '"+saleOrg.getLongNumber().trim()+"!%' or ORGUNIT.FLongNumber like '%!"+saleOrg.getLongNumber().trim()+"!%') ";
				baseFilter += " and ROOM.FIsForSHE = 1 ";		
		if(!isIncludeAttach) baseFilter += " and Room.FHouseProperty != '"+ HousePropertyEnum.ATTACHMENT_VALUE +"'";
		
		 Map comanyIdNameMap = new HashMap();;
		 Map sellProIdNameMap = new HashMap();;
		 Map proOrBuildNameMap = new HashMap();
		 Map subAreaMap = new HashMap();
		try {
			Map allDataMap  = MonthSellReportFacadeFactory.getRemoteInstance().getAllData(filterMap);
			if(allDataMap==null){
				return ;
			}
			rowByIdsMap = (Map)allDataMap.get("rowByIdsMap");
			//comanyIdSet = (Set)allDataMap.get("comanyIdSet");
			//sellProIdSet = (Set)allDataMap.get("sellProIdSet");
			//proOrBuildIdSet = (Set)allDataMap.get("proOrBuildIdSet");
			
			comanyIdNameMap = (Map)allDataMap.get("comanyIdNameMap");
			sellProIdNameMap = (Map)allDataMap.get("sellProIdNameMap");
			proOrBuildNameMap = (Map)allDataMap.get("proOrBuildNameMap");
			subAreaMap = (Map)allDataMap.get("subAreaMap");
			
			Iterator it = rowByIdsMap.keySet().iterator();
			
			boolean isbizunit = SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit();
			String orgUnitName ="";
			if(isbizunit){
				orgUnitName =  SysContext.getSysContext().getCurrentSaleUnit().getName();
			}
			for(int i=0;i<rowByIdsMap.size();i++){
				this.tblMain.addRow();
			}
			
			while(it.hasNext()){
				String key = (String)it.next();
				Map rowMap = (Map)rowByIdsMap.get(key);
				IRow row  = initRowBymap(rowMap);
			
				String OID = (String)row.getCell("OID").getValue();
				String SID = (String)row.getCell("SID").getValue();
				String PBID = (String)row.getCell("PBID").getValue();
				
				//填充名称
				if(isbizunit){
					row.getCell("companyName").setValue(orgUnitName);
				}else{
					row.getCell("companyName").setValue(comanyIdNameMap.get(OID));
				}
				row.getCell("sellProName").setValue(sellProIdNameMap.get(SID));				
				if(showByBuilding){
					row.getCell("buildingName").setValue(proOrBuildNameMap.get(PBID));
					row.getCell("subarea.name").setValue(subAreaMap.get(PBID));
				}
				else
					row.getCell("productType").setValue(proOrBuildNameMap.get(PBID));
				
				//填充 ‘欠收房款’列 
				/*BigDecimal allPriceSum = new BigDecimal(0);
					if(row.getCell("allPriceSum").getValue()!=null) allPriceSum = (BigDecimal)row.getCell("allPriceSum").getValue();
				BigDecimal resvSum = new BigDecimal(0);
					if(row.getCell("allResvSum").getValue()!=null) resvSum = (BigDecimal)row.getCell("allResvSum").getValue();
					if(allPriceSum.subtract(resvSum).compareTo(FDCHelper.ZERO)>0){
						row.getCell("allArrearage").setValue(allPriceSum.subtract(resvSum));		
					}else{
						row.getCell("allArrearage").setValue(FDCHelper.ZERO);
					}
				
				BigDecimal monthPriceSum = new BigDecimal(0);
				if(row.getCell("monthPriceSum").getValue()!=null) monthPriceSum = (BigDecimal)row.getCell("monthPriceSum").getValue();
				BigDecimal monthresvSum = new BigDecimal(0);
				if(row.getCell("monthResvSum").getValue()!=null) monthresvSum = (BigDecimal)row.getCell("monthResvSum").getValue();
				if(monthPriceSum.subtract(monthresvSum).compareTo(FDCHelper.ZERO)>0){
					row.getCell("monthArrearage").setValue(monthPriceSum.subtract(monthresvSum));
				}else{
					row.getCell("monthArrearage").setValue(FDCHelper.ZERO);
				}
				
				BigDecimal yearPriceSum = new BigDecimal(0);
				if(row.getCell("allPriceSum").getValue()!=null) yearPriceSum = (BigDecimal)row.getCell("yearPriceSum").getValue();
				BigDecimal yearresvSum = new BigDecimal(0);
				if(row.getCell("yearResvSum").getValue()!=null) yearresvSum = (BigDecimal)row.getCell("yearResvSum").getValue();
					
				if(yearPriceSum.subtract(yearresvSum).compareTo(FDCHelper.ZERO)>0){
					row.getCell("yearArrearage").setValue(yearPriceSum.subtract(yearresvSum));	
				}else{
					row.getCell("yearArrearage").setValue(FDCHelper.ZERO);
					
				}*/
				/*BigDecimal allPriceSum = new BigDecimal(0);
					if(row.getCell("allPriceSum").getValue()!=null) allPriceSum = (BigDecimal)row.getCell("allPriceSum").getValue();
				BigDecimal resvSum = new BigDecimal(0);
					resvSum =  rowMap.get("allResvSum_all")==null ?FDCHelper.ZERO:(BigDecimal)rowMap.get("allResvSum_all");
				//if(row.getCell("allResvSum").getValue()!=null) resvSum = (BigDecimal)row.getCell("allResvSum").getValue();
					if(allPriceSum.subtract(resvSum).compareTo(FDCHelper.ZERO)>0){
						row.getCell("allArrearage").setValue(allPriceSum.subtract(resvSum));		
					}else{
						row.getCell("allArrearage").setValue(FDCHelper.ZERO);
					}
				
				BigDecimal monthPriceSum = new BigDecimal(0);
				if(row.getCell("monthPriceSum").getValue()!=null) monthPriceSum = (BigDecimal)row.getCell("monthPriceSum").getValue();
				BigDecimal monthresvSum = new BigDecimal(0);
				monthresvSum =  rowMap.get("monthResvSum_all")==null ?FDCHelper.ZERO:(BigDecimal)rowMap.get("monthResvSum_all");
				//if(row.getCell("monthResvSum").getValue()!=null) monthresvSum = (BigDecimal)row.getCell("monthResvSum").getValue();
				if(monthPriceSum.subtract(monthresvSum).compareTo(FDCHelper.ZERO)>0){
					row.getCell("monthArrearage").setValue(monthPriceSum.subtract(monthresvSum));
				}else{
					row.getCell("monthArrearage").setValue(FDCHelper.ZERO);
				}
				
				BigDecimal yearPriceSum = new BigDecimal(0);
				
				if(row.getCell("allPriceSum").getValue()!=null) yearPriceSum = (BigDecimal)row.getCell("yearPriceSum").getValue();
				BigDecimal yearresvSum = new BigDecimal(0);
				//if(row.getCell("yearResvSum").getValue()!=null) yearresvSum = (BigDecimal)row.getCell("yearResvSum").getValue();
				yearPriceSum =  rowMap.get("yearResvSum_all")==null ?FDCHelper.ZERO:(BigDecimal)rowMap.get("yearResvSum_all");	
				if(yearPriceSum.subtract(yearresvSum).compareTo(FDCHelper.ZERO)>0){
					row.getCell("yearArrearage").setValue(yearPriceSum.subtract(yearresvSum));	
				}else{
					row.getCell("yearArrearage").setValue(FDCHelper.ZERO);
					
				}*/
				
				
				
			}
			
			
			
			//不需要求合计的列 //TODO 写到常量里去
			String unTotalCol = "companyName,sellProName,subarea.name,productType,buildingName";
			
			//重置分组管理器
			this.tblMain.getGroupManager().reInitialize();
			
			//列分组
			this.tblMain.getColumn(0).setGroup(true);
			this.tblMain.getColumn(0).setStat(true);
			this.tblMain.getColumn(1).setGroup(true);
			this.tblMain.getColumn(1).setStat(true);	
			
			//是否显示总计
			///数据上有多销售部时 显示总计行 否则不显示  
			/*int orgNum =0;
			String orgId =null;
			for(int i=0;i<this.tblMain.getRowCount();i++){
				String neworgId =  (String)this.tblMain.getCell(i, "OID").getValue();
				if(neworgId!=null && !"".equals(neworgId) && !neworgId.equals(orgId)){
					orgId = neworgId;
					orgNum++;
				}
				if(orgNum>1){
					this.tblMain.getGroupManager().setTotalize(true);
					break;
				}
			}*/
			if(!isbizunit){
				this.tblMain.getGroupManager().setTotalize(true);
			}
			
			
			
			// 获取总计行的模板（总计行的分组级别为－1）
			IRow row0 = (IRow)tblMain.getGroupManager().getStatRowTemplate(-1);
			row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			row0.getCell(0).setValue("总计:");
			
			for(int i=0;i<this.tblMain.getColumnCount();i++){
				if(unTotalCol.indexOf(this.tblMain.getColumn(i).getKey())>=0){
					continue;
				}
				row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
			}
			
			//获取合计行的模板
			row0 = (IRow)tblMain.getGroupManager().getStatRowTemplate(0);
			row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			row0.getCell(0).setValue("合计:");
			for(int i=0;i<this.tblMain.getColumnCount();i++){
				if(unTotalCol.indexOf(this.tblMain.getColumn(i).getKey())>=0){
					continue;
				}
				row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
			}
			
			//获取项目合计行的模板
			row0 = (IRow)tblMain.getGroupManager().getStatRowTemplate(1);
			row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			if(showByBuilding)	 {
				row0.getCell("buildingName").setValue("项目合计:");
			}else{
				row0.getCell("productType").setValue("项目合计:");
			}
			
			
			for(int i=0;i<this.tblMain.getColumnCount();i++){
				if(unTotalCol.indexOf(this.tblMain.getColumn(i).getKey())>=0){
					continue;
				}
				row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
			}
			
			//重置分组管理器后 先分组在生成树 而后刷新 
			this.tblMain.getGroupManager().group();
			// 生成树
			tblMain.getGroupManager().toTreeColumn();
			//重新调整布局并刷新
			tblMain.reLayoutAndPaint();

		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	protected void kDTree1_valueChanged(TreeSelectionEvent e) throws Exception {
		super.kDTree1_valueChanged(e);
		//this.tblMain.removeHeadRow(1);
		allBuildingIds = new HashSet();
		int unitNumber = 0; // 单元过滤，代表无单元过滤
		TreeNode buildingNode = (TreeNode) this.kDTree1.getLastSelectedPathComponent();
		// allBuildingIds = "null";
		if (buildingNode != null) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) buildingNode;
			if (thisNode.getUserObject() != null) {
				if (thisNode.getUserObject() instanceof BuildingInfo) {
					BuildingInfo building = (BuildingInfo) thisNode
					.getUserObject();
					// allBuildingIds += "," + building.getId().toString();
					allBuildingIds.add(building.getId().toString());
				} else if (thisNode.getUserObject() instanceof Integer) { // 已作废
					DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode) thisNode
					.getParent();
					BuildingInfo parentBuilding = (BuildingInfo) parentNode
					.getUserObject();
					allBuildingIds.add(parentBuilding.getId().toString());
					unitNumber = ((Integer) thisNode.getUserObject())
					.intValue();
				} else if (thisNode.getUserObject() instanceof BuildingUnitInfo) { //
					DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode) thisNode
					.getParent();
					BuildingInfo parentBuilding = (BuildingInfo) parentNode
					.getUserObject();
					allBuildingIds.add(parentBuilding.getId().toString());
					unitNumber = ((BuildingUnitInfo) thisNode.getUserObject()).getSeq();
				} else {
					this.getAllBuildingIds(buildingNode);
				}
			}
		} else {
			this.kDTree1.expandAllNodes(true, (TreeNode) this.kDTree1
					.getModel().getRoot());
			this.getAllBuildingIds((TreeNode) this.kDTree1.getModel()
					.getRoot());
		}

		if (allBuildingIds.size() == 0) {
			this.tblMain.removeRows();
			return;
		}
	
		this.execQuery();
	}


	
	
	
	protected IRow initRowBymap(Map map){
		Integer rowindex = (Integer)map.get("rowindex");
		IRow  row = null;
		//if(rowindex==null){
		//	row = this.tblMain.addRow();
		//}else{
			row = this.tblMain.getRow(rowindex.intValue());
		if(row==null){
			 row = this.tblMain.addRow();
		}
		
		row.getCell("OID").setValue(map.get("OID"));
		row.getCell("SID").setValue(map.get("SID"));
		row.getCell("PBID").setValue(map.get("PBID"));
		row.getCell("totalRoomcount").setValue(map.get("totalRoomcount"));
		row.getCell("buildingArea").setValue(map.get("buildingArea"));
		row.getCell("actureBuildArea").setValue(map.get("actureBuildArea"));
		String [] byType = {"all","month","year"};
		String[] columnNames = {"RoomCount","AreaSum","PriceSum","CompensateSum","PreconcertMoney","Arrearage","AccFundAmount"};
		String[] sums = {"ResvSum","LoanSum","CompensateInSum"};
		
		String colName = null;
		for(int i=0;i<byType.length;i++){
			for(int j=0;j<columnNames.length;j++){
				colName = byType[i]+columnNames[j];
				row.getCell(colName).setValue(map.get(colName));
			}
		}
		
		for(int i=0;i<byType.length;i++){
			for(int j=0;j<sums.length;j++){
				colName = byType[i]+sums[j];
				row.getCell(colName).setValue(map.get(colName));
			}
		}
		
		//row.getCell("preconcertMoney").setValue(map.get("preconcertMoney"));
		
		return row;
	}
	/**
	 * 查询所有的楼栋id
	 * 
	 * @param treeNode
	 */
	private void getAllBuildingIds(TreeNode treeNode) {
		if(allBuildingIds==null){
			allBuildingIds = new HashSet();
		}
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		if (thisNode.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) thisNode.getUserObject();
			allBuildingIds.add(building.getId().toString());
			// allBuildingIds += "," + building.getId().toString();
		}

		if (!treeNode.isLeaf()) {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllBuildingIds(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}
}