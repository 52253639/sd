/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.QuitRoomCollection;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MonthSellReportUI extends AbstractMonthSellReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(MonthSellReportUI.class);
    
    private Map rowByIdsMap = null;
    
    private Set comanyIdSet = null;
    private Set sellProIdSet = null;
    private Set proOrBuildIdSet = null;
    
    private boolean isAuditDate = false;
    
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
		boolean chkIsShowSpecial = filterMap.get("chkIsShowSpecial")==null?false:(((Integer)filterMap.get("chkIsShowSpecial")).intValue()>0?true:false);
		
		String showByType = "PRODUCTTYPE.FID";
		if(showByBuilding)	 {
			showByType = "BUILDING.FID";
			this.tblMain.getColumn("productType").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("buildingName").getStyleAttributes().setHided(false);
		}else{
			this.tblMain.getColumn("productType").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("buildingName").getStyleAttributes().setHided(true);
		}
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		String baseFilter = " (ORGUNIT.FNumber = '"+saleOrg.getNumber().trim()+"' or ORGUNIT.FLongNumber like '"+saleOrg.getLongNumber().trim()+"!%' or ORGUNIT.FLongNumber like '%!"+saleOrg.getLongNumber().trim()+"!%') ";
				baseFilter += " and ROOM.FIsForSHE = 1 ";		
		if(!isIncludeAttach) baseFilter += " and Room.FHouseProperty != '"+ HousePropertyEnum.ATTACHMENT_VALUE +"'";
		
		
		try {
			//Table1  查询出：  总套数、预售建筑面积、实测建筑面积    
			intTable1(baseFilter,showByType);
			//-- 已售套数、已售面积、应收合同总价  、 应收（退 ）面积差
			intTable2(baseFilter,showByType,"all");  //累计
			intTable2(baseFilter,showByType,"month");  //按月
			intTable2(baseFilter,showByType,"year");  //按年
			//销售回款     //按揭款[回款]   //已收（退 ）面积差
			intTable3(baseFilter,showByType,"all");	//累计      
			intTable3(baseFilter,showByType,"month");	//按月
			intTable3(baseFilter,showByType,"year");	//按年
		
			if(chkIsShowSpecial) {   //特殊业务处理
				this.dealForQuitRoom(baseFilter, showByType, "month");
				this.dealForQuitRoom(baseFilter, showByType, "year");
				this.dealForAdjustRoom(baseFilter, showByType, "month");
				this.dealForAdjustRoom(baseFilter, showByType, "year");
				this.dealForPurchaseChange(baseFilter, showByType, "month");
				this.dealForPurchaseChange(baseFilter, showByType, "year");
				this.dealForChangeRoom(baseFilter, showByType, "month");
				this.dealForChangeRoom(baseFilter, showByType, "year");
			}			
			
		    Map comanyIdNameMap = getCompayIdNameMap();
		    Map sellProIdNameMap = getSellProIdNameMap();
		    Map proOrBuildNameMap = getProductOrBuildIdNameMap(showByBuilding);
			//填充公司名、项目名、产品类型名 和 楼栋名    comanyIdSet sellProIdSet  proOrBuildIdSet
		    //统计数据    、  填充 ‘欠收房款’列 
			for(int i=0;i<this.tblMain.getRowCount();i++) {
				IRow row = this.tblMain.getRow(i);
				String OID = (String)row.getCell("OID").getValue();
				String SID = (String)row.getCell("SID").getValue();
				String PBID = (String)row.getCell("PBID").getValue();
				//String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
				
				//填充名称
				row.getCell("companyName").setValue(comanyIdNameMap.get(OID));
				row.getCell("sellProName").setValue(sellProIdNameMap.get(SID));				
				if(showByBuilding)
					row.getCell("buildingName").setValue(proOrBuildNameMap.get(PBID));
				else
					row.getCell("productType").setValue(proOrBuildNameMap.get(PBID));
				
				//填充 ‘欠收房款’列 
				BigDecimal allPriceSum = new BigDecimal(0);
					if(row.getCell("allPriceSum").getValue()!=null) allPriceSum = (BigDecimal)row.getCell("allPriceSum").getValue();
				BigDecimal resvSum = new BigDecimal(0);
					if(row.getCell("allResvSum").getValue()!=null) resvSum = (BigDecimal)row.getCell("allResvSum").getValue();
				row.getCell("allArrearage").setValue(allPriceSum.subtract(resvSum));		
			}
			
			//this.tblMain.getGroupManager().setGroup(true);
			this.tblMain.getColumn(0).setGroup(true);
			this.tblMain.getColumn(1).setGroup(true);
			this.tblMain.getGroupManager().setTotalize(true);
			this.tblMain.getColumn(0).setStat(true);
			this.tblMain.getColumn(1).setStat(true);		
			
			// 获取总计行的模板（总计行的分组级别为－1）
			IRow row0 = (IRow)tblMain.getGroupManager().getStatRowTemplate(-1);
			row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			row0.getCell(0).setValue("总计:");
			for(int i=4;i<this.tblMain.getColumnCount();i++)
				row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
			row0 = (IRow)tblMain.getGroupManager().getStatRowTemplate(0);
			row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			row0.getCell(0).setValue("公司合计：");
			for(int i=4;i<this.tblMain.getColumnCount();i++)
				row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
			row0 = (IRow)tblMain.getGroupManager().getStatRowTemplate(1);
			row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			row0.getCell(1).setValue("项目合计：");
			for(int i=4;i<this.tblMain.getColumnCount();i++)
				row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
			
			// 生成树
			tblMain.getGroupManager().toTreeColumn();
			//重新调整布局并刷新
			tblMain.reLayoutAndPaint();
			
			this.tblMain.getGroupManager().group();
			
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	
	
	//Table1  查询出：  总套数、预售建筑面积、实测建筑面积    
	private void intTable1(String baseFilter,String showByType) throws BOSException, SQLException {	
		FDCSQLBuilder builder = new FDCSQLBuilder();		
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,");
		builder.appendSql("Count(ROOM.FID) AS totalRoomcount, ");
		builder.appendSql("Sum(case when ROOM.FBuildingArea is null then 0 else ROOM.FBuildingArea end) AS buildingArea,"); 
		builder.appendSql("Sum(case when ROOM.FActualBuildingArea is null then 0 else ROOM.FActualBuildingArea end) AS actureBuildArea "); 
		builder.appendSql("FROM T_SHE_Room AS ROOM ");
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID "); 
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID "); 
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID "); 
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID "); 
		builder.appendSql("where ");
		builder.appendSql(baseFilter);
		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType);
		builder.appendSql(" ORDER BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType);
		
		rowByIdsMap = new HashMap();
	    comanyIdSet = new HashSet();
	    sellProIdSet = new HashSet();
	    proOrBuildIdSet = new HashSet();
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int totalRoomcount = tableSet.getInt("totalRoomcount");
			BigDecimal buildingArea = tableSet.getBigDecimal("buildingArea");
			BigDecimal actureBuildArea = tableSet.getBigDecimal("actureBuildArea");
			
			IRow row = this.tblMain.addRow();
			row.getCell("OID").setValue(OID);
			row.getCell("SID").setValue(SID);
			row.getCell("PBID").setValue(PBID);
			row.getCell("totalRoomcount").setValue(new Integer(totalRoomcount));
			row.getCell("buildingArea").setValue(buildingArea);
			row.getCell("actureBuildArea").setValue(actureBuildArea);
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			rowByIdsMap.put(idStr, row);
			
			comanyIdSet.add(OID);
			sellProIdSet.add(SID);
			proOrBuildIdSet.add(PBID);
		}
	}
	
	//-- 已售套数、已售面积、应收合同总价 、（应收（退 ）面积差 ）  --- （本月或本年的只是多个时间范围)  
	//byType 为all代表累计，month代表按月，year代表按年
	private void intTable2(String baseFilter,String showByType,String byType) throws BOSException, SQLException {
		if(byType==null || "all month year".indexOf(byType)<0) return;	

		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		Date beginMonthDate = filterMap.get("BeginMonthDate")==null?null:new Date(((Timestamp)filterMap.get("BeginMonthDate")).getTime());
		Date endMonthDate = filterMap.get("EndMonthDate")==null?null:new Date(((Timestamp)filterMap.get("EndMonthDate")).getTime());
		Date beginYearDate = filterMap.get("BeginYearDate")==null?null:new Date(((Timestamp)filterMap.get("BeginYearDate")).getTime());
		Date endYearDate = filterMap.get("EndYearDate")==null?null:new Date(((Timestamp)filterMap.get("EndYearDate")).getTime());
		boolean isPreIntoSale = filterMap.get("isPreIntoSale")==null?false:(((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false);	
	
		String dataCompareParam = "PURCHASE.FToSaleDate";
		if(!isPreIntoSale) dataCompareParam = "PURCHASE.FToPurchaseDate";
		
		String[] columnNames = {byType+"RoomCount",byType+"AreaSum",byType+"PriceSum",byType+"CompensateSum"};
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,");
		builder.appendSql("Count(ROOM.FID) AS "+columnNames[0]+", ");
		builder.appendSql("Sum(case when ROOM.FBuildingArea is null then 0 else ROOM.FBuildingArea end) AS "+columnNames[1]+","); 
		builder.appendSql("Sum(case when PURCHASE.FContractTotalAmount is null then 0 else PURCHASE.FContractTotalAmount end) AS "+columnNames[2]+" ");
		builder.appendSql(",Sum(case when ROOM.FAreaCompensateAmount is null then 0 else ROOM.FAreaCompensateAmount end) AS "+columnNames[3]+" ");
		builder.appendSql("FROM T_SHE_Room AS ROOM ");
		builder.appendSql("left JOIN T_SHE_Purchase AS PURCHASE ON ROOM.FLastPurchaseID = PURCHASE.FID ");
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID "); 
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID "); 
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID "); 
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID ");
		builder.appendSql("where ");
		builder.appendSql(baseFilter);
		if(isPreIntoSale) {
			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PREPURCHASE_VALUE+"','"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"')");
		}else{
			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"')");
		}
		builder.appendSql(" AND ROOM.FIsForSHE=1 ");
		if(byType.equals("month") || byType.equals("year")) {
			builder.appendSql(" and "+dataCompareParam + ">= ? ");
			builder.appendSql(" and "+dataCompareParam + "< ? ");
			if(byType.equals("month")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
				builder.addParam(FDCDateHelper.getSqlDate(endMonthDate));
			}else if(byType.equals("year")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder.addParam(FDCDateHelper.getSqlDate(endYearDate));
			}
		}
		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType);

		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int roomcount = tableSet.getInt(columnNames[0]);
			BigDecimal areaSum = tableSet.getBigDecimal(columnNames[1]);
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames[2]);
			BigDecimal areaCompensateAmount = tableSet.getBigDecimal(columnNames[3]);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			IRow row = (IRow)rowByIdsMap.get(idStr);
			if(row!=null)  {
				row.getCell(columnNames[0]).setValue(new Integer(roomcount));
				row.getCell(columnNames[1]).setValue(areaSum);
				row.getCell(columnNames[2]).setValue(priceSum);					
				row.getCell(columnNames[3]).setValue(areaCompensateAmount);
			}
		}
	}
    
    
    
	//-- 销售回款   、  按揭款[回款] 、已收（退 ）面积差    （本月或本年的只是多个时间范围)  
	//byType 为all代表累计，month代表按月，year代表按年
	private void intTable3(String baseFilter,String showByType,String byType) throws BOSException, SQLException {
		if(byType==null || "all month year".indexOf(byType)<0) return;

		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		Date beginMonthDate = filterMap.get("BeginMonthDate")==null?null:new Date(((Timestamp)filterMap.get("BeginMonthDate")).getTime());
		Date endMonthDate = filterMap.get("EndMonthDate")==null?null:new Date(((Timestamp)filterMap.get("EndMonthDate")).getTime());
		Date beginYearDate = filterMap.get("BeginYearDate")==null?null:new Date(((Timestamp)filterMap.get("BeginYearDate")).getTime());
		Date endYearDate = filterMap.get("EndYearDate")==null?null:new Date(((Timestamp)filterMap.get("EndYearDate")).getTime());
		boolean isPreIntoSale = filterMap.get("isPreIntoSale")==null?false:(((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false);	
			
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,MoneyDefine.FMoneyType,");
		builder.appendSql("Sum(case when FDCEntry.FAmount is null then 0 else FDCEntry.FAmount end) AS resvSum ");
		builder.appendSql("FROM T_SHE_FDCReceiveBillEntry AS FDCEntry ");
		builder.appendSql("LEFT OUTER JOIN T_CAS_ReceivingBill AS CAS ON FDCEntry.FReceivingBillID = CAS.FID "); 
		builder.appendSql("LEFT OUTER JOIN T_SHE_FDCReceiveBill AS FDC ON CAS.FFDCReceivebillID = FDC.FID "); 
		builder.appendSql("inner JOIN T_SHE_Room AS ROOM ON FDC.FRoomID = ROOM.FID "); 
		builder.appendSql("LEFT OUTER JOIN T_SHE_Purchase AS PURCHASE ON ROOM.FID = PURCHASE.FRoomId "); 		
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID ");
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID ");
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID ");
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID ");
		builder.appendSql("LEFT OUTER JOIN T_SHE_MoneyDefine AS MoneyDefine ON FDCEntry.FMoneyDefineID = MoneyDefine.FID ");
		builder.appendSql("where ROOM.FIsForSHE = 1 ");
		//builder.appendSql(baseFilter);
		builder.appendSql(" and FDC.fpurchaseid = PURCHASE.FID ");
		//财务回款 跟房间状态无关 ，跟附属于房产无关 ；跟认购单的状态无关	by jeegan 20090901
		//builder.appendSql(" and PURCHASE.FPurchaseState not in('"+PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE+"','"+PurchaseStateEnum.QUITROOMBLANKOUT_VALUE+"','"+PurchaseStateEnum.NOPAYBLANKOUT_VALUE+"','"+PurchaseStateEnum.MANUALBLANKOUT_VALUE+"') ");
//		if(isPreIntoSale) {    //
//			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PREPURCHASE_VALUE+"','"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"')");
//		}else{
//			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"')");
//		}
		if(byType.equals("month")) {
			builder.appendSql(" and CAS.FBizDate >= ? ");
			builder.appendSql(" and CAS.FBizDate < ? " );
			builder.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
			builder.addParam(FDCDateHelper.getSqlDate(endMonthDate));
		}else if(byType.equals("year")) {	//按年的sql执行起来很奇怪，居然要查询很久还会执行完，因而换种方式来做 
/*				builder.appendSql(" and CAS.FBizDate >= ? ");
				builder.appendSql(" and CAS.FBizDate < ? " );
				builder.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder.addParam(FDCDateHelper.getSqlDate(endYearDate));*/
			DateFormat FORMAT_YEAR = new SimpleDateFormat("yyyy");
			builder.appendSql(" and year(CAS.FBizDate) = "+FORMAT_YEAR.format(beginYearDate));
		}
		
		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType+",MoneyDefine.FMoneyType");		
	
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			BigDecimal resvSum = tableSet.getBigDecimal("resvSum");
			String fMoneyType = tableSet.getString("FMoneyType");
			if(resvSum.compareTo(new BigDecimal(0))==0) continue;
			if(fMoneyType==null || fMoneyType.trim().equals("")) continue;
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			IRow row = (IRow)rowByIdsMap.get(idStr);			
			if(row==null) continue;
			//(销售回款)		:   预收款(改成了预订金)、定金、楼款、首期、补差款、按揭款、公积金、退款
			String resvMTypeStr = MoneyTypeEnum.PRECONCERTMONEY_VALUE +","+ MoneyTypeEnum.EARNESTMONEY_VALUE +","+ MoneyTypeEnum.HOUSEAMOUNT_VALUE +","
								   + MoneyTypeEnum.FISRTAMOUNT_VALUE +","+ MoneyTypeEnum.COMPENSATEAMOUNT_VALUE +"," + MoneyTypeEnum.LOANAMOUNT_VALUE +","+ MoneyTypeEnum.ACCFUNDAMOUNT_VALUE +","+ MoneyTypeEnum.REFUNDMENT_VALUE;
			//按揭款[回款]   :   按揭款、公积金
			String loanMTypeStr = MoneyTypeEnum.LOANAMOUNT_VALUE +","+ MoneyTypeEnum.ACCFUNDAMOUNT_VALUE;
			//补差款   ：		
			if(resvMTypeStr.indexOf(fMoneyType)>=0)	 
				addDecimalValueToCell(row,byType + "ResvSum",resvSum);
			if(loanMTypeStr.indexOf(fMoneyType)>=0)
				addDecimalValueToCell(row,byType + "LoanSum",resvSum);
			if(fMoneyType.equals(MoneyTypeEnum.COMPENSATEAMOUNT_VALUE))
				addDecimalValueToCell(row,byType + "CompensateInSum",resvSum);
			if(byType.equals("all")) {
				if(fMoneyType.equals(MoneyTypeEnum.EARNESTMONEY_VALUE))	//定金
					addDecimalValueToCell(row,"preconcertMoney",resvSum);
			}
			
		}
	}
	
	
	
	private Map getProductOrBuildIdNameMap(boolean showByBuilding) throws SQLException, BOSException {
		Map idNameMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		if(showByBuilding)
			builder.appendSql("select fid,fname_l2 from T_SHE_Building ");
		else
			builder.appendSql("select fid,fname_l2 from T_FDC_ProductType ");
		if(proOrBuildIdSet!=null && proOrBuildIdSet.size()>0) {
			builder.appendSql(" where ");
			builder.appendParam("fid",proOrBuildIdSet.toArray());
		}else{
			return idNameMap;
		}
		
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String fid = tableSet.getString("fid");
			String name = tableSet.getString("fname_l2");
			idNameMap.put(fid, name);
		}		
		return idNameMap;
	}
	
	private Map getSellProIdNameMap() throws SQLException, BOSException {
		Map idNameMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid,fname_l2 from T_SHE_SellProject ");
		if(sellProIdSet!=null && sellProIdSet.size()>0) {
			builder.appendSql(" where ");
			builder.appendParam("fid",sellProIdSet.toArray());		
		}else{
			return idNameMap;
		}
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String fid = tableSet.getString("fid");
			String name = tableSet.getString("fname_l2");
			idNameMap.put(fid, name);
		}		
		return idNameMap;
	}
	
	private Map getCompayIdNameMap() throws SQLException, BOSException {
		Map idNameMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid,fname_l2 from T_ORG_BaseUnit ");
		if(comanyIdSet!=null && comanyIdSet.size()>0) {
			builder.appendSql(" where ");
			builder.appendParam("fid",comanyIdSet.toArray());		
		}else{
			return idNameMap;
		}
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String fid = tableSet.getString("fid");
			String name = tableSet.getString("fname_l2");
			idNameMap.put(fid, name);
		}		
		return idNameMap;
	}
	
	
	
	private void addDecimalValueToCell(IRow row,String cellName,BigDecimal addValue) {
		if(addValue==null) return; 
		BigDecimal cellValue = new BigDecimal(0); 
		Object cellObject = row.getCell(cellName).getValue();
		if(cellObject instanceof Integer)
			cellValue = new BigDecimal(((Integer)cellObject).intValue());
		else if(cellObject instanceof BigDecimal)
			cellValue = (BigDecimal)cellObject;

		row.getCell(cellName).setValue(cellValue.add(addValue));
	}
	
	
	
	
	//特殊业务  退房和变更     只对按月和按年的处理 （ 已售套数、已售面积、应收合同总价  、 应收（退 ）面积差）
	//退房 --特殊处理  （ 已售套数、已售面积、应收合同总价  、 应收（退 ）面积差）
	//分两步操作 1：退房单的认购单对应的认购或销售时间在指定范围内的做 + 操作
	//			2: 退房日期在指定范围内的做 - 操作
	private void dealForQuitRoom(String baseFilter,String showByType,String byType) throws BOSException, SQLException {	
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		Date beginMonthDate = filterMap.get("BeginMonthDate")==null?null:new Date(((Timestamp)filterMap.get("BeginMonthDate")).getTime());
		Date endMonthDate = filterMap.get("EndMonthDate")==null?null:new Date(((Timestamp)filterMap.get("EndMonthDate")).getTime());
		Date beginYearDate = filterMap.get("BeginYearDate")==null?null:new Date(((Timestamp)filterMap.get("BeginYearDate")).getTime());
		Date endYearDate = filterMap.get("EndYearDate")==null?null:new Date(((Timestamp)filterMap.get("EndYearDate")).getTime());
		boolean isPreIntoSale = filterMap.get("isPreIntoSale")==null?false:(((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false);	
	
		String dataCompareParam = "PURCHASE.FToSaleDate";
		if(!isPreIntoSale) dataCompareParam = "PURCHASE.FToPurchaseDate";
		
		String[] columnNames = {byType+"RoomCount",byType+"AreaSum",byType+"PriceSum",byType+"CompensateSum"};
		FDCSQLBuilder builder = new FDCSQLBuilder();
		FDCSQLBuilder builder2 = new FDCSQLBuilder();
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,");
		builder.appendSql("Count(ROOM.FID) AS "+columnNames[0]+", ");
		builder.appendSql("Sum(case when ROOM.FBuildingArea is null then 0 else ROOM.FBuildingArea end) AS "+columnNames[1]+","); 
		builder.appendSql("Sum(case when PURCHASE.FContractTotalAmount is null then 0 else PURCHASE.FContractTotalAmount end) AS "+columnNames[2]+" ");
		builder.appendSql(",Sum(case when ROOM.FAreaCompensateAmount is null then 0 else ROOM.FAreaCompensateAmount end) AS "+columnNames[3]+" ");
		builder.appendSql("FROM T_SHE_Room AS ROOM ");
		builder.appendSql("INNER JOIN T_SHE_QuitRoom AS QUITROOM ON ROOM.FID = QUITROOM.FRoomID ");
		builder.appendSql("left outer JOIN T_SHE_Purchase AS PURCHASE ON QUITROOM.FPurchaseID = PURCHASE.FID ");
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID "); 
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID "); 
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID "); 
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID ");
		builder.appendSql("where ");
		builder.appendSql(baseFilter);
		builder.appendSql(" and QUITROOM.fstate = '"+FDCBillStateEnum.AUDITTED_VALUE+"'");
		if(!isPreIntoSale)
			builder.appendSql(" and PURCHASE.FToPurchaseDate is not null ");
		
		builder2.appendSql(builder.getSql());
		if(byType.equals("month") || byType.equals("year")) {
			builder.appendSql(" and "+dataCompareParam + ">= ? ");
			builder.appendSql(" and "+dataCompareParam + "< ? ");
			if(byType.equals("month")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
				builder.addParam(FDCDateHelper.getSqlDate(endMonthDate));
			}else if(byType.equals("year")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder.addParam(FDCDateHelper.getSqlDate(endYearDate));
			}
		}		
		builder.appendSql(" AND ROOM.FIsForSHE=1 ");
		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType);
		
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int roomcount = tableSet.getInt(columnNames[0]);
			BigDecimal areaSum = tableSet.getBigDecimal(columnNames[1]);
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames[2]);
			BigDecimal areaCompensateAmount = tableSet.getBigDecimal(columnNames[3]);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			IRow row = (IRow)rowByIdsMap.get(idStr);
			if(row!=null)  {
				addDecimalValueToCell(row,columnNames[0],new BigDecimal(roomcount));
				addDecimalValueToCell(row,columnNames[1],areaSum);
				addDecimalValueToCell(row,columnNames[2],priceSum);
				addDecimalValueToCell(row,columnNames[3],areaCompensateAmount);
			}
		}
		
		//第二步
		if(byType.equals("month") || byType.equals("year")) {
			
			if(this.isAuditDate)
			{
				builder2.appendSql(" and QUITROOM.FAuditTime >= ? ");
				builder2.appendSql(" and QUITROOM.FAuditTime < ? ");
			}
			else
			{
			builder2.appendSql(" and QUITROOM.FQuitDate >= ? ");
			builder2.appendSql(" and QUITROOM.FQuitDate < ? ");
			}
			if(byType.equals("month")) {
				builder2.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
				builder2.addParam(FDCDateHelper.getSqlDate(endMonthDate));
			}else if(byType.equals("year")) {
				builder2.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder2.addParam(FDCDateHelper.getSqlDate(endYearDate));
			}
		}
		builder2.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType);
		
		tableSet = builder2.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int roomcount = tableSet.getInt(columnNames[0]);
			BigDecimal areaSum = tableSet.getBigDecimal(columnNames[1]);
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames[2]);
			BigDecimal areaCompensateAmount = tableSet.getBigDecimal(columnNames[3]);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			IRow row = (IRow)rowByIdsMap.get(idStr);
			if(row!=null)  {
				addDecimalValueToCell(row,columnNames[0],new BigDecimal(-roomcount));
				addDecimalValueToCell(row,columnNames[1],areaSum.negate());
				addDecimalValueToCell(row,columnNames[2],priceSum.negate());
				addDecimalValueToCell(row,columnNames[3],areaCompensateAmount.negate());
			}
		}
		
		
		
	}	
	
	//调整--和退房一样处理  by Pope
	private void dealForAdjustRoom(String baseFilter,String showByType,String byType) throws BOSException, SQLException {	
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		Date beginMonthDate = filterMap.get("BeginMonthDate")==null?null:new Date(((Timestamp)filterMap.get("BeginMonthDate")).getTime());
		Date endMonthDate = filterMap.get("EndMonthDate")==null?null:new Date(((Timestamp)filterMap.get("EndMonthDate")).getTime());
		Date beginYearDate = filterMap.get("BeginYearDate")==null?null:new Date(((Timestamp)filterMap.get("BeginYearDate")).getTime());
		Date endYearDate = filterMap.get("EndYearDate")==null?null:new Date(((Timestamp)filterMap.get("EndYearDate")).getTime());
		boolean isPreIntoSale = filterMap.get("isPreIntoSale")==null?false:(((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false);	
	
		String dataCompareParam = "PURCHASE.FToSaleDate";
		if(!isPreIntoSale) dataCompareParam = "PURCHASE.FToPurchaseDate";
		
		String[] columnNames = {byType+"RoomCount",byType+"AreaSum",byType+"PriceSum",byType+"CompensateSum"};
		FDCSQLBuilder builder = new FDCSQLBuilder();
		FDCSQLBuilder builder2 = new FDCSQLBuilder();
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,");
		builder.appendSql("Count(ROOM.FID) AS "+columnNames[0]+", ");
		builder.appendSql("Sum(case when ROOM.FBuildingArea is null then 0 else ROOM.FBuildingArea end) AS "+columnNames[1]+","); 
		builder.appendSql("Sum(case when PURCHASE.FContractTotalAmount is null then 0 else PURCHASE.FContractTotalAmount end) AS "+columnNames[2]+" ");
		builder.appendSql(",Sum(case when ROOM.FAreaCompensateAmount is null then 0 else ROOM.FAreaCompensateAmount end) AS "+columnNames[3]+" ");
		builder.appendSql("FROM T_SHE_Room AS ROOM ");
		builder.appendSql("left outer JOIN T_SHE_Purchase AS PURCHASE ON PURCHASE.Fid = PURCHASE.FRoomId ");
		builder.appendSql("INNER JOIN T_SHE_BillAdjust AS BillAdjust ON PURCHASE.FID = BillAdjust.FPurchaseID ");		
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID "); 
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID "); 
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID "); 
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID ");
		builder.appendSql("where ");
		builder.appendSql(baseFilter);
		builder.appendSql(" and BillAdjust.fstate = '"+FDCBillStateEnum.AUDITTED_VALUE+"'");
		if(!isPreIntoSale)
			builder.appendSql(" and PURCHASE.FToPurchaseDate is not null ");
		
		builder2.appendSql(builder.getSql());
		if(byType.equals("month") || byType.equals("year")) {
			builder.appendSql(" and "+dataCompareParam + ">= ? ");
			builder.appendSql(" and "+dataCompareParam + "< ? ");
			if(byType.equals("month")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
				builder.addParam(FDCDateHelper.getSqlDate(endMonthDate));
			}else if(byType.equals("year")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder.addParam(FDCDateHelper.getSqlDate(endYearDate));
			}
		}		
		builder.appendSql(" AND ROOM.FIsForSHE=1 ");
		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType);
		
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int roomcount = tableSet.getInt(columnNames[0]);
			BigDecimal areaSum = tableSet.getBigDecimal(columnNames[1]);
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames[2]);
			BigDecimal areaCompensateAmount = tableSet.getBigDecimal(columnNames[3]);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			IRow row = (IRow)rowByIdsMap.get(idStr);
			if(row!=null)  {
				addDecimalValueToCell(row,columnNames[0],new BigDecimal(roomcount));
				addDecimalValueToCell(row,columnNames[1],areaSum);
				addDecimalValueToCell(row,columnNames[2],priceSum);
				addDecimalValueToCell(row,columnNames[3],areaCompensateAmount);
			}
		}
		
		//第二步
		if(byType.equals("month") || byType.equals("year")) {
			
			if(this.isAuditDate)
			{
				builder2.appendSql(" and BillAdjust.FAuditTime >= ? ");
				builder2.appendSql(" and BillAdjust.FAuditTime < ? ");
			}
			else
			{
			builder2.appendSql(" and BillAdjust.FBizDate >= ? ");
			builder2.appendSql(" and BillAdjust.FBizDate < ? ");
			}
			if(byType.equals("month")) {
				builder2.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
				builder2.addParam(FDCDateHelper.getSqlDate(endMonthDate));
			}else if(byType.equals("year")) {
				builder2.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder2.addParam(FDCDateHelper.getSqlDate(endYearDate));
			}
		}
		builder2.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType);
		
		tableSet = builder2.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int roomcount = tableSet.getInt(columnNames[0]);
			BigDecimal areaSum = tableSet.getBigDecimal(columnNames[1]);
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames[2]);
			BigDecimal areaCompensateAmount = tableSet.getBigDecimal(columnNames[3]);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			IRow row = (IRow)rowByIdsMap.get(idStr);
			if(row!=null)  {
				addDecimalValueToCell(row,columnNames[0],new BigDecimal(-roomcount));
				addDecimalValueToCell(row,columnNames[1],areaSum.negate());
				addDecimalValueToCell(row,columnNames[2],priceSum.negate());
				addDecimalValueToCell(row,columnNames[3],areaCompensateAmount.negate());
			}
		}
	}	
	
	//换房单——和退房一样处理
	private void dealForChangeRoom(String baseFilter,String showByType,String byType) throws BOSException, SQLException {	
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		Date beginMonthDate = filterMap.get("BeginMonthDate")==null?null:new Date(((Timestamp)filterMap.get("BeginMonthDate")).getTime());
		Date endMonthDate = filterMap.get("EndMonthDate")==null?null:new Date(((Timestamp)filterMap.get("EndMonthDate")).getTime());
		Date beginYearDate = filterMap.get("BeginYearDate")==null?null:new Date(((Timestamp)filterMap.get("BeginYearDate")).getTime());
		Date endYearDate = filterMap.get("EndYearDate")==null?null:new Date(((Timestamp)filterMap.get("EndYearDate")).getTime());
		boolean isPreIntoSale = filterMap.get("isPreIntoSale")==null?false:(((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false);
		
		String dataCompareParam = "PURCHASE.FToSaleDate";
		if(!isPreIntoSale) dataCompareParam = "PURCHASE.FToPurchaseDate";
		
		String[] columnNames = {byType+"RoomCount",byType+"AreaSum",byType+"PriceSum",byType+"CompensateSum"};
		FDCSQLBuilder builder = new FDCSQLBuilder();
		FDCSQLBuilder builder2 = new FDCSQLBuilder();
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,");
		builder.appendSql("Count(ROOM.FID) AS "+columnNames[0]+", ");
		builder.appendSql("Sum(case when ROOM.FBuildingArea is null then 0 else ROOM.FBuildingArea end) AS "+columnNames[1]+","); 
		builder.appendSql("Sum(case when PURCHASE.FContractTotalAmount is null then 0 else PURCHASE.FContractTotalAmount end) AS "+columnNames[2]+" ");
		builder.appendSql(",Sum(case when ROOM.FAreaCompensateAmount is null then 0 else ROOM.FAreaCompensateAmount end) AS "+columnNames[3]+" ");
		builder.appendSql("FROM T_SHE_Room AS ROOM ");
		builder.appendSql("INNER JOIN T_SHE_ChangeRoom AS CHANGEROOM ON ROOM.FID = CHANGEROOM.FOLDRoomID ");
		builder.appendSql("left outer JOIN T_SHE_Purchase AS PURCHASE ON CHANGEROOM.FOldPurchaseID = PURCHASE.FID ");
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID "); 
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID "); 
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID "); 
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID ");
		builder.appendSql("where ");
		builder.appendSql(baseFilter);
		builder.appendSql(" and CHANGEROOM.fstate = '"+FDCBillStateEnum.AUDITTED_VALUE+"'");
		if(!isPreIntoSale)
			builder.appendSql(" and PURCHASE.FToPurchaseDate is not null ");
		
		builder2.appendSql(builder.getSql());
		if(byType.equals("month") || byType.equals("year")) {
			builder.appendSql(" and "+dataCompareParam + ">= ? ");
			builder.appendSql(" and "+dataCompareParam + "< ? ");
			if(byType.equals("month")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
				builder.addParam(FDCDateHelper.getSqlDate(endMonthDate));
			}else if(byType.equals("year")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder.addParam(FDCDateHelper.getSqlDate(endYearDate));
			}
		}		
		builder.appendSql(" AND ROOM.FIsForSHE=1 ");
		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType);
		
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int roomcount = tableSet.getInt(columnNames[0]);
			BigDecimal areaSum = tableSet.getBigDecimal(columnNames[1]);
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames[2]);
			BigDecimal areaCompensateAmount = tableSet.getBigDecimal(columnNames[3]);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			IRow row = (IRow)rowByIdsMap.get(idStr);
			if(row!=null)  {
				addDecimalValueToCell(row,columnNames[0],new BigDecimal(roomcount));
				addDecimalValueToCell(row,columnNames[1],areaSum);
				addDecimalValueToCell(row,columnNames[2],priceSum);
				addDecimalValueToCell(row,columnNames[3],areaCompensateAmount);
			}
		}
		
		//第二步
		if(byType.equals("month") || byType.equals("year")) {
			
			if(this.isAuditDate)
			{
				builder2.appendSql(" and CHANGEROOM.FAuditTime >= ? ");
				builder2.appendSql(" and CHANGEROOM.FAuditTime < ? ");
			}
			else
			{
			builder2.appendSql(" and CHANGEROOM.FChangeDate >= ? ");
			builder2.appendSql(" and CHANGEROOM.FChangeDate < ? ");
			}
			if(byType.equals("month")) {
				builder2.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
				builder2.addParam(FDCDateHelper.getSqlDate(endMonthDate));
			}else if(byType.equals("year")) {
				builder2.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder2.addParam(FDCDateHelper.getSqlDate(endYearDate));
			}
		}
		builder2.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType);
		
		tableSet = builder2.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int roomcount = tableSet.getInt(columnNames[0]);
			BigDecimal areaSum = tableSet.getBigDecimal(columnNames[1]);
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames[2]);
			BigDecimal areaCompensateAmount = tableSet.getBigDecimal(columnNames[3]);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			IRow row = (IRow)rowByIdsMap.get(idStr);
			if(row!=null)  {
				addDecimalValueToCell(row,columnNames[0],new BigDecimal(-roomcount));
				addDecimalValueToCell(row,columnNames[1],areaSum.negate());
				addDecimalValueToCell(row,columnNames[2],priceSum.negate());
				addDecimalValueToCell(row,columnNames[3],areaCompensateAmount.negate());
			}
		}
	}
	
	//变更单--特殊处理   （ 应收合同总价 ，好像只对这个有影响 ）
	private void dealForPurchaseChange(String baseFilter,String showByType,String byType) throws BOSException, SQLException {	
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		Date beginMonthDate = filterMap.get("BeginMonthDate")==null?null:new Date(((Timestamp)filterMap.get("BeginMonthDate")).getTime());
		Date endMonthDate = filterMap.get("EndMonthDate")==null?null:new Date(((Timestamp)filterMap.get("EndMonthDate")).getTime());
		Date beginYearDate = filterMap.get("BeginYearDate")==null?null:new Date(((Timestamp)filterMap.get("BeginYearDate")).getTime());
		Date endYearDate = filterMap.get("EndYearDate")==null?null:new Date(((Timestamp)filterMap.get("EndYearDate")).getTime());
		boolean isPreIntoSale = filterMap.get("isPreIntoSale")==null?false:(((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false);	
	
		String dataCompareParam = "PURCHASE.FToSaleDate";
		if(!isPreIntoSale) dataCompareParam = "PURCHASE.FToPurchaseDate";
		
		String columnNames = byType+"PriceSum";
		FDCSQLBuilder builder = new FDCSQLBuilder();
		FDCSQLBuilder builder2 = new FDCSQLBuilder();
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,");
		builder.appendSql("Sum(PurchaseChange.FNewContractAmount - PurchaseChange.FOldContractAmount) AS "+columnNames+" ");
		builder.appendSql("FROM T_SHE_Room AS ROOM ");		
		builder.appendSql("INNER JOIN T_SHE_Purchase AS PURCHASE ON ROOM.FID = PURCHASE.FRoomID ");
		builder.appendSql("INNER JOIN T_SHE_PurchaseChange AS PurchaseChange ON PURCHASE.FID = PurchaseChange.FPurchaseID ");
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID "); 
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID "); 
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID "); 
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID ");
		builder.appendSql("where ");
		builder.appendSql(baseFilter);
		builder.appendSql(" and PurchaseChange.fstate = '"+FDCBillStateEnum.AUDITTED_VALUE+"'");
		if(!isPreIntoSale)
			builder.appendSql(" and PURCHASE.FToPurchaseDate is not null ");
		
		builder2.appendSql(builder.getSql());
		if(byType.equals("month") || byType.equals("year")) {
			builder.appendSql(" and "+dataCompareParam + ">= ? ");
			builder.appendSql(" and "+dataCompareParam + "< ? " );
			if(byType.equals("month")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
				builder.addParam(FDCDateHelper.getSqlDate(endMonthDate));
			}else if(byType.equals("year")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder.addParam(FDCDateHelper.getSqlDate(endYearDate));
			}
		}
		builder.appendSql(" AND ROOM.FIsForSHE=1 ");
		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType);		
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			IRow row = (IRow)rowByIdsMap.get(idStr);
			if(row!=null)  {
				addDecimalValueToCell(row,columnNames,priceSum.negate());
			}
		}
		
		//第二步
		if(byType.equals("month") || byType.equals("year")) {
			
			if(this.isAuditDate)
			{
				builder2.appendSql(" and PurchaseChange.FAuditTime >= ? ");
				builder2.appendSql(" and PurchaseChange.FAuditTime < ? " );
			}
			else
			{
			builder2.appendSql(" and PurchaseChange.FChangeDate >= ? ");
			builder2.appendSql(" and PurchaseChange.FChangeDate < ? " );
			}
			if(byType.equals("month")) {
				builder2.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
				builder2.addParam(FDCDateHelper.getSqlDate(endMonthDate));
			}else if(byType.equals("year")) {
				builder2.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder2.addParam(FDCDateHelper.getSqlDate(endYearDate));
			}
		}
		builder2.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType);
		tableSet = builder2.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			IRow row = (IRow)rowByIdsMap.get(idStr);
			if(row!=null)  {
				addDecimalValueToCell(row,columnNames,priceSum);
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}