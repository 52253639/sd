/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;

import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;

import com.kingdee.eas.basedata.assistant.PeriodCollection;
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;


import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceCollection;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceFactory;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum;


import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.ICoreBase;

import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class HistoryMonthSellReportUI extends AbstractHistoryMonthSellReportUI
{
	private static final Logger logger = CoreUIObject.getLogger(HistoryMonthSellReportUI.class);

	private Map rowByIdsMap = null;

	private Set comanyIdSet = null;
	private Set sellProIdSet = null;
	private Set proOrBuildIdSet = null;
	
	private boolean showByBuilding = false;
	
	private boolean showByProductType = false;
	//是否有月份的月结
	private boolean isJanHaveData = false;

	//年初的计算期间的开始日期
	private Date janBeginDate = null;
	//当前期间的结束日期
	private Date thisEndDate = null;
	
	//存储在 一月份 进行过 月结的 项目
	Set janSet = new HashSet();
	

	public HistoryMonthSellReportUI() throws Exception
	{
		super();
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return null;
	}

	protected String getEditUIName()
	{
		return null;
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception
	{
	}

	protected boolean initDefaultFilter()
	{
		return true;
	}

	protected String getKeyFieldName()
	{
		return "PBID";
	}

	private CommonQueryDialog commonQueryDialog = null;

	protected CommonQueryDialog initCommonQueryDialog()
	{
		if (commonQueryDialog != null)
		{
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	private HistoryMonthSellReportFilterUI filterUI = null;

	private HistoryMonthSellReportFilterUI getFilterUI()
	{
		if (this.filterUI == null)
		{
			try
			{
				this.filterUI = new HistoryMonthSellReportFilterUI();
				this.filterUI.onLoad();
			} catch (Exception e)
			{
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	public void onLoad() throws Exception
	{
		super.onLoad();

		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.actionRefresh.setVisible(false);

		this.actionEdit.setEnabled(false);


	//	this.actionLocate.setVisible(false);
		this.menuEdit.setVisible(false);

	}
	protected String[] getLocateNames()
	{
		return new String[]{"companyName"};
	}
	

	//设置一月份是否进行过结算
	public void setJanHaveData(String id)
	{
		try
		{
			PeriodInfo info = PeriodFactory.getRemoteInstance().getPeriodInfo(new ObjectUuidPK(BOSUuid.read(id)));
			
			this.thisEndDate = info.getEndDate();
			int periodYear = info.getPeriodYear();
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add( new FilterItemInfo("periodYear",new Integer(periodYear)));
			filter.getFilterItems().add(new FilterItemInfo("periodNumber",new Integer(1)));
			
			PeriodCollection coll = PeriodFactory.getRemoteInstance().getPeriodCollection(view);
			if(coll.size() == 1)
			{
				PeriodInfo period = coll.get(0);
				
				this.janBeginDate = period.getBeginDate();
				
				
				view = new EntityViewInfo();
				filter = new FilterInfo();
				view.setFilter(filter);
				
				filter.getFilterItems().add(new FilterItemInfo("period",period.getId()));
				filter.getFilterItems().add(new FilterItemInfo("operateType",SaleBalanceTypeEnum.BALANCE_VALUE));
				
				SaleBalanceCollection balColl = SaleBalanceFactory.getRemoteInstance().getSaleBalanceCollection(view);
				
				for(int i = 0; i < balColl.size(); i ++)
				{
					SaleBalanceInfo bal = balColl.get(i);
					String sId = bal.getSellProject() == null? null:bal.getSellProject().getId().toString();
					
					janSet.add(sId);
				}
				
				//this.isJanHaveData = SaleBalanceFactory.getRemoteInstance().exists(filter);
			}
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	protected void execQuery()
	{
		// this.tblMain.checkParsed();
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);

		if (this.tblMain.getRowCount() > 0)
			this.tblMain.removeRows();

		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		
		showByProductType = filterMap.get("showByProductType") == null ? false
				: (((Integer) filterMap.get("showByProductType")).intValue() > 0 ? true	: false);
		showByBuilding = filterMap.get("showByBuilding") == null ? false
				: (((Integer) filterMap.get("showByBuilding")).intValue() > 0 ? true : false);
		String id = (String)filterMap.get("balanceId");
		
		if (!showByProductType && !showByBuilding)
			return;

		this.setJanHaveData(id);
		
		String showByType = "FProductTypeId";
		if (showByBuilding)
		{
			showByType = "FbuildingId";
			this.tblMain.getColumn("productType").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("buildingName").getStyleAttributes().setHided(false);
		} else
		{
			this.tblMain.getColumn("productType").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("buildingName").getStyleAttributes().setHided(true);
		}

		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		
		String baseFilter = " (ORGUNIT.FNumber = '"
				+ saleOrg.getNumber().trim()
				+ "' or ORGUNIT.FLongNumber like '"
				+ saleOrg.getLongNumber().trim()
				+ "!%' or ORGUNIT.FLongNumber like '%!"
				+ saleOrg.getLongNumber().trim() + "!%') ";
		

		try
		{
			// Table1 查询出： 总套数、预售建筑面积、实测建筑面积
			intTable1(baseFilter, showByType);
			// -- 已售套数、已售面积、应收合同总价 、 应收（退 ）面积差
			intTable2(baseFilter, showByType, "all"); // 累计
			intTable2(baseFilter, showByType, "month"); // 按月
			intTable2(baseFilter, showByType, "year"); // 按年
			// 销售回款 //按揭款[回款] //已收（退 ）面积差
			intTable3(baseFilter, showByType, "all"); // 累计
			intTable3(baseFilter, showByType, "month"); // 按月
			intTable3(baseFilter, showByType, "year"); // 按年

			this.dealInitData(baseFilter, showByType, "all");
			this.dealInitData(baseFilter, showByType, "year");

			Map comanyIdNameMap = getCompayIdNameMap();
			Map sellProIdNameMap = getSellProIdNameMap();
			Map proOrBuildNameMap = getProductOrBuildIdNameMap(showByBuilding);
			// 填充公司名、项目名、产品类型名 和 楼栋名 comanyIdSet sellProIdSet proOrBuildIdSet
			// 统计数据 、 填充 ‘欠收房款’列
			for (int i = 0; i < this.tblMain.getRowCount(); i++)
			{
				IRow row = this.tblMain.getRow(i);
				String OID = (String) row.getCell("OID").getValue();
				String SID = (String) row.getCell("SID").getValue();
				String PBID = (String) row.getCell("PBID").getValue();


				// 填充名称
				row.getCell("companyName").setValue(comanyIdNameMap.get(OID));
				row.getCell("sellProName").setValue(sellProIdNameMap.get(SID));
				if (showByBuilding)
				{
					row.getCell("buildingName").setValue(proOrBuildNameMap.get(PBID));
				}
				else
				{					
					row.getCell("productType").setValue(proOrBuildNameMap.get(PBID));
				}

				// 填充 ‘欠收房款’列
				BigDecimal allPriceSum = new BigDecimal(0);
				if (row.getCell("allPriceSum").getValue() != null)
				{
					allPriceSum = (BigDecimal) row.getCell("allPriceSum").getValue();
				}
				BigDecimal resvSum = new BigDecimal(0);
				if (row.getCell("allResvSum").getValue() != null)
				{
					resvSum = (BigDecimal) row.getCell("allResvSum").getValue();
				}
				row.getCell("allArrearage").setValue(allPriceSum.subtract(resvSum));
			}

			this.tblMain.getColumn(0).setGroup(true);
			this.tblMain.getColumn(1).setGroup(true);
			this.tblMain.getGroupManager().setTotalize(true);
			this.tblMain.getColumn(0).setStat(true);
			this.tblMain.getColumn(1).setStat(true);

			// 获取总计行的模板（总计行的分组级别为－1）
			IRow row0 = (IRow) tblMain.getGroupManager().getStatRowTemplate(-1);
			row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			row0.getCell(0).setValue("总计:");
			for (int i = 4; i < this.tblMain.getColumnCount(); i++)
			{
				row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
			}
			row0 = (IRow) tblMain.getGroupManager().getStatRowTemplate(0);
			row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			row0.getCell(0).setValue("公司合计：");
			for (int i = 4; i < this.tblMain.getColumnCount(); i++)
			{
				row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
			}
			row0 = (IRow) tblMain.getGroupManager().getStatRowTemplate(1);
			row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			row0.getCell(1).setValue("项目合计：");
			for (int i = 4; i < this.tblMain.getColumnCount(); i++)
			{
				row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
			}

			// 生成树
			tblMain.getGroupManager().toTreeColumn();
			// 重新调整布局并刷新
			tblMain.reLayoutAndPaint();

			this.tblMain.getGroupManager().group();

		} catch (BOSException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	// Table1 查询出： 总套数、预售建筑面积、实测建筑面积
	private void intTable1(String baseFilter, String showByType)throws BOSException, SQLException
	{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,");
		builder.appendSql("sum (RoomPty.FroomCount) AS totalRoomcount, ");
		builder.appendSql("Sum(case when RoomPty.FpreArea is null then 0 else RoomPty.FpreArea end) AS buildingArea,");
		builder.appendSql("Sum(case when RoomPty.FactArea is null then 0 else RoomPty.FactArea end) AS actureBuildArea ");
		if(this.showByBuilding)
		{
			builder.appendSql("FROM T_DAY_RoomBld AS RoomPty ");
		}
		else
		{
			builder.appendSql("FROM T_DAY_RoomPty AS RoomPty ");
		}
		
		builder.appendSql(" inner join T_SHE_SaleBalance as sb on roompty.FSaleBalanceId = sb.fid ");
		
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON sb.FSellProjectID = SELLPROJECT.FID ");
		
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID ");
		
		builder.appendSql("where ");
		builder.appendSql(baseFilter);
		
		this.appendBalanceFilterSql(builder, "RoomPty.FperiodId");
	
		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID, "+showByType);
		builder.appendSql(" ORDER BY ORGUNIT.FID, SELLPROJECT.FID, "+showByType);
		
		logger.info("Table1 查询出： 总套数、预售建筑面积、实测建筑面积****");
		logger.info(builder.getSql());
		
		rowByIdsMap = new HashMap();
		comanyIdSet = new HashSet();
		sellProIdSet = new HashSet();
		proOrBuildIdSet = new HashSet();
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next())
		{
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int totalRoomcount = tableSet.getInt("totalRoomcount");
			BigDecimal buildingArea = tableSet.getBigDecimal("buildingArea");
			BigDecimal actureBuildArea = tableSet
					.getBigDecimal("actureBuildArea");

			IRow row = this.tblMain.addRow();
			row.getCell("OID").setValue(OID);
			row.getCell("SID").setValue(SID);
			row.getCell("PBID").setValue(PBID);
			row.getCell("totalRoomcount").setValue(new Integer(totalRoomcount));
			row.getCell("buildingArea").setValue(buildingArea);
			row.getCell("actureBuildArea").setValue(actureBuildArea);
			String idStr = (OID == null ? "" : OID) + (SID == null ? "" : SID)
					+ (PBID == null ? "" : PBID);
			rowByIdsMap.put(idStr, row);

			comanyIdSet.add(OID);
			sellProIdSet.add(SID);
			proOrBuildIdSet.add(PBID);
		}
	}

	// -- 已售套数、已售面积、应收合同总价 、（应收（退 ）面积差 ） --- （本月或本年的只是多个时间范围)
	// byType 为all代表累计，month代表按月，year代表按年
	private void intTable2(String baseFilter, String showByType, String byType)
			throws BOSException, SQLException
	{
		if (byType == null || "all month year".indexOf(byType) < 0)
			return;
		
		String[] columnNames = { byType + "RoomCount", byType + "AreaSum", byType + "PriceSum",byType + "CompensateSum" };
		FDCSQLBuilder builder = new FDCSQLBuilder();
		
	
			builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID,"	+ showByType + " AS PBID,");
			builder.appendSql("sum (PurchasePty.FCount) AS " + columnNames[0]+ ", ");
			builder.appendSql("Sum(case when PurchasePty.FArea is null then 0 else PurchasePty.FArea end) AS "	+ columnNames[1] + ",");
			builder.appendSql("Sum(case when PurchasePty.FAmount is null then 0 else PurchasePty.FAmount end) AS "	+ columnNames[2] + " , ");
			builder.appendSql("Sum(case when PurchasePty.FCosate is null then 0 else PurchasePty.FCosate end) AS "+ columnNames[3] + " ");
			if (this.showByBuilding)
			{
				builder.appendSql("FROM T_DAY_PurchaseBld AS PurchasePty ");
			} else
			{
				builder.appendSql("FROM T_DAY_PurchasePty AS PurchasePty ");
			}

			builder.appendSql("inner join T_DAY_MainTable as MainTable on MainTable.fid = PurchasePty.FDayMainId ");

			builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON MainTable.FSellProjectID = SELLPROJECT.FID ");
			builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID ");
			builder.appendSql("where ");
			builder.appendSql(baseFilter);

			this.appendDateFilterSql(builder, "MainTable.FDayNum",byType);

			builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID, "+ showByType);
		

			logger.info("Table2 查询出：已售套数、已售面积、应收合同总价 、（应收（退 ）面积差 ）****");
			logger.info(builder.getSql());

		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next())
		{
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int roomcount = tableSet.getInt(columnNames[0]);
			BigDecimal areaSum = tableSet.getBigDecimal(columnNames[1]);
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames[2]);
			BigDecimal areaCompensateAmount = tableSet.getBigDecimal(columnNames[3]);

			String idStr = (OID == null ? "" : OID) + (SID == null ? "" : SID)
					+ (PBID == null ? "" : PBID);
			IRow row = (IRow) rowByIdsMap.get(idStr);
			if (row != null)
			{
				row.getCell(columnNames[0]).setValue(new Integer(roomcount));
				row.getCell(columnNames[1]).setValue(areaSum);
				row.getCell(columnNames[2]).setValue(priceSum);
				row.getCell(columnNames[3]).setValue(areaCompensateAmount);
			}
		}
	}

	// -- 销售回款 、 按揭款[回款] 、已收（退 ）面积差 （本月或本年的只是多个时间范围)
	// byType 为all代表累计，month代表按月，year代表按年
	private void intTable3(String baseFilter, String showByType, String byType)
			throws BOSException, SQLException
	{
		if (byType == null || "all month year".indexOf(byType) < 0)
			return;

		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID ,");
	//	builder.appendSql(" Sum (PurchasePty.FPreconcertMoney + PurchasePty.FEarnestMoney + PurchasePty.FHouseAmount + PurchasePty.FFisrtAmount + PurchasePty.FCompensateAmount + PurchasePty.FLoanAmount + PurchasePty.FAccFundAmount + PurchasePty.FRefundment) as saleSum ,");
		
		builder.appendSql(" Sum (PurchasePty.FPreconcertMoney) as FPreconcertMoney ,");
		builder.appendSql(" Sum (PurchasePty.FEarnestMoney) as FEarnestMoney, ");
		builder.appendSql("  Sum (PurchasePty.FHouseAmount) as FHouseAmount , ");
		builder.appendSql("  Sum (PurchasePty.FFisrtAmount) as FFisrtAmount  , ");
		builder.appendSql("  Sum (PurchasePty.FCompensateAmount) as FCompensateAmount  , ");
		builder.appendSql("  Sum (PurchasePty.FLoanAmount) as FLoanAmount,   ");
		builder.appendSql("  Sum (PurchasePty.FAccFundAmount) as FAccFundAmount  , ");
		builder.appendSql("  Sum (PurchasePty.FRefundment) as FRefundment ,  ");

		
		builder.appendSql(" Sum ( PurchasePty.FCompensateAmount ) as buchaSum ");
		
		if(showByBuilding)
		{
			builder.appendSql(" from T_DAY_PurchaseBld as PurchasePty ");
		}
		else
		{
			builder.appendSql(" from T_DAY_PurchasePty as PurchasePty ");		
		}
		
		builder.appendSql(" inner join T_DAY_MainTable as MainTable on MainTable.Fid = PurchasePty.FDayMainId ");
		
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON MainTable.FSellProjectID = SELLPROJECT.FID ");
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID ");

		builder.appendSql("where ");
		builder.appendSql(baseFilter);
	
	
		this.appendDateFilterSql(builder, "MainTable.FDayNum",byType);

		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"	+ showByType);

		logger.info("Table3 查询出：销售回款 、 按揭款[回款] 、已收（退 ）面积差****");
		logger.info(builder.getSql());

		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next())
		{
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			
		/*	BigDecimal FAccFundAmount = tableSet.getBigDecimal("FAccFundAmount");
			if(FAccFundAmount == null)
				FAccFundAmount = FDCHelper.ZERO;
			
			BigDecimal FCompensateAmount22 = tableSet.getBigDecimal("FCompensateAmount22");
			if(FCompensateAmount22 == null)
				FCompensateAmount22 = FDCHelper.ZERO;
			*/
			
			BigDecimal FPreconcertMoney = tableSet.getBigDecimal("FPreconcertMoney");
			if(FPreconcertMoney == null)
				FPreconcertMoney = FDCHelper.ZERO;
			
			BigDecimal FEarnestMoney = tableSet.getBigDecimal("FEarnestMoney");
			if(FEarnestMoney == null)
				FEarnestMoney = FDCHelper.ZERO;
			
			BigDecimal FHouseAmount = tableSet.getBigDecimal("FHouseAmount");
			if(FHouseAmount == null)
				FHouseAmount = FDCHelper.ZERO;
			
			BigDecimal FFisrtAmount = tableSet.getBigDecimal("FFisrtAmount");
			if(FFisrtAmount == null)
				FFisrtAmount = FDCHelper.ZERO;
			
			
			BigDecimal FCompensateAmount = tableSet.getBigDecimal("FCompensateAmount");
			if(FCompensateAmount == null)
				FCompensateAmount = FDCHelper.ZERO;
			
			BigDecimal FLoanAmount = tableSet.getBigDecimal("FLoanAmount");
			if(FLoanAmount == null)
				FLoanAmount = FDCHelper.ZERO;
			
			BigDecimal FAccFundAmount = tableSet.getBigDecimal("FAccFundAmount");
			if(FAccFundAmount == null)
				FAccFundAmount = FDCHelper.ZERO;
			
			BigDecimal FRefundment = tableSet.getBigDecimal("FRefundment");
			if(FRefundment == null)
				FRefundment = FDCHelper.ZERO;
			
			
			
		
			BigDecimal saleSum = FPreconcertMoney.add(FEarnestMoney).add(FHouseAmount).add(FFisrtAmount).add(FCompensateAmount)
			.add(FLoanAmount).add(FAccFundAmount).add(FRefundment);
			BigDecimal gongjiSum = FAccFundAmount.add(FLoanAmount);
				//tableSet.getBigDecimal("gongjiSum");
			BigDecimal buchaSum = tableSet.getBigDecimal("buchaSum");

			String idStr = (OID == null ? "" : OID) + (SID == null ? "" : SID)	+ (PBID == null ? "" : PBID);
			IRow row = (IRow) rowByIdsMap.get(idStr);
			if (row == null)
				continue;
		
			addDecimalValueToCell(row, byType + "ResvSum", saleSum);

			addDecimalValueToCell(row, byType + "LoanSum", gongjiSum);
	
			addDecimalValueToCell(row, byType + "CompensateInSum", buchaSum);

		}
	}

	//处理初始数据
	private void dealInitData(String baseFilter,String showByType,String byType) throws BOSException, SQLException
	{	
		if("month".equalsIgnoreCase(byType))
			return;
		String[] columnNames = {byType+"RoomCount",byType+"AreaSum",byType+"PriceSum",byType+"CompensateSum",byType+"CompensateInSum",byType+"ResvSum",byType + "LoanSum"};
		FDCSQLBuilder builder = new FDCSQLBuilder();
		
		if("year".equalsIgnoreCase(byType))
		{
			if(this.isJanHaveData)
			{
				//return;
			}
			builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,");
			builder.appendSql("sum(data.FyearRoomCount) AS "+columnNames[0]+", ");
			builder.appendSql("Sum(case when data.FyearAreaAmount is null then 0 else data.FyearAreaAmount end) AS "+columnNames[1]+","); 
			builder.appendSql("Sum(case when data.FyearContractAmount is null then 0 else data.FyearContractAmount end) AS "+columnNames[2]+" ");
			builder.appendSql(",Sum(case when data.FyearCosateAmount is null then 0 else data.FyearCosateAmount end) AS "+columnNames[3]+" ");
			builder.appendSql(",Sum(case when data.FyearCstRsvAmount is null then 0 else data.FyearCstRsvAmount end) AS "+columnNames[4]+" ");
			builder.appendSql(",Sum(case when data.FyearRsvAmount is null then 0 else data.FyearRsvAmount end) AS "+columnNames[5]+" ");
			builder.appendSql(",Sum(case when FyearLoanAmount is null then 0 else FyearLoanAmount end) AS "+columnNames[6]+" ");
			
			if (this.showByBuilding)
			{
				builder.appendSql("FROM T_DAY_InitDataBld  AS data ");
			} else
			{
				builder.appendSql("FROM T_DAY_InitDataPty  AS data ");
			}
			builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON data.FSellProjectID = SELLPROJECT.FID "); 
			builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID ");
			builder.appendSql("where ");
			builder.appendSql(baseFilter);
		}
		else if("all".equalsIgnoreCase(byType))
		{

			builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,");
			builder.appendSql("sum(data.FinitRoomCount) AS "+columnNames[0]+", ");
			builder.appendSql("Sum(case when data.FinitAreaAmount is null then 0 else data.FinitAreaAmount end) AS "+columnNames[1]+","); 
			builder.appendSql("Sum(case when data.FinitContractAmount is null then 0 else data.FinitContractAmount end) AS "+columnNames[2]+" ");
			builder.appendSql(",Sum(case when data.FinitCosateAmount is null then 0 else data.FinitCosateAmount end) AS "+columnNames[3]+" ");
			builder.appendSql(",Sum(case when data.FinitCstRsvAmount is null then 0 else data.FinitCstRsvAmount end) AS "+columnNames[4]+" ");
			builder.appendSql(",Sum(case when data.FinitRsvAmount is null then 0 else data.FinitRsvAmount end) AS "+columnNames[5]+" ");
			builder.appendSql(",Sum(case when FinitLoanAmount is null then 0 else FinitLoanAmount end) AS "+columnNames[6]+" ");
			
			if (this.showByBuilding)
			{
				builder.appendSql("FROM T_DAY_InitDataBld  AS data ");
			} else
			{
				builder.appendSql("FROM T_DAY_InitDataPty  AS data ");
			}
			builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON data.FSellProjectID = SELLPROJECT.FID "); 
			builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID ");
			builder.appendSql("where ");
			builder.appendSql(baseFilter);
		
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
			BigDecimal compensateSum = tableSet.getBigDecimal(columnNames[3]);
			BigDecimal compensateInSum = tableSet.getBigDecimal(columnNames[4]);
			BigDecimal resvSum = tableSet.getBigDecimal(columnNames[5]);
			BigDecimal loanSum = tableSet.getBigDecimal(columnNames[6]);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			IRow row = (IRow)rowByIdsMap.get(idStr);
			if(row!=null)  {
				
				if("year".equalsIgnoreCase(byType))
				{
					if(!janSet.contains(SID))
					{
					addDecimalValueToCell(row,columnNames[0],new BigDecimal(roomcount));
					addDecimalValueToCell(row,columnNames[1],areaSum);
					addDecimalValueToCell(row,columnNames[2],priceSum);
					addDecimalValueToCell(row,columnNames[3],compensateSum);
					addDecimalValueToCell(row,columnNames[4],compensateInSum);
					addDecimalValueToCell(row,columnNames[5],resvSum);
					addDecimalValueToCell(row,columnNames[6],loanSum);
					}
				}
				else
				{
					addDecimalValueToCell(row,columnNames[0],new BigDecimal(roomcount));
					addDecimalValueToCell(row,columnNames[1],areaSum);
					addDecimalValueToCell(row,columnNames[2],priceSum);
					addDecimalValueToCell(row,columnNames[3],compensateSum);
					addDecimalValueToCell(row,columnNames[4],compensateInSum);
					addDecimalValueToCell(row,columnNames[5],resvSum);
					addDecimalValueToCell(row,columnNames[6],loanSum);
				}
				
				
			}
		}
	}	
	
	
	
	
	private Map getProductOrBuildIdNameMap(boolean showByBuilding)
			throws SQLException, BOSException
	{
		Map idNameMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		if (showByBuilding)
			builder.appendSql("select fid,fname_l2 from T_SHE_Building ");
		else
			builder.appendSql("select fid,fname_l2 from T_FDC_ProductType ");
		if (proOrBuildIdSet != null && proOrBuildIdSet.size() > 0)
		{
			builder.appendSql(" where ");
			builder.appendParam("fid", proOrBuildIdSet.toArray());
		} else
		{
			return idNameMap;
		}

		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next())
		{
			String fid = tableSet.getString("fid");
			String name = tableSet.getString("fname_l2");
			idNameMap.put(fid, name);
		}
		return idNameMap;
	}

	private Map getSellProIdNameMap() throws SQLException, BOSException
	{
		Map idNameMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid,fname_l2 from T_SHE_SellProject ");
		if (sellProIdSet != null && sellProIdSet.size() > 0)
		{
			builder.appendSql(" where ");
			builder.appendParam("fid", sellProIdSet.toArray());
		} else
		{
			return idNameMap;
		}
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next())
		{
			String fid = tableSet.getString("fid");
			String name = tableSet.getString("fname_l2");
			idNameMap.put(fid, name);
		}
		return idNameMap;
	}

	private Map getCompayIdNameMap() throws SQLException, BOSException
	{
		Map idNameMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid,fname_l2 from T_ORG_BaseUnit ");
		if (comanyIdSet != null && comanyIdSet.size() > 0)
		{
			builder.appendSql(" where ");
			builder.appendParam("fid", comanyIdSet.toArray());
		} else
		{
			return idNameMap;
		}
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next())
		{
			String fid = tableSet.getString("fid");
			String name = tableSet.getString("fname_l2");
			idNameMap.put(fid, name);
		}
		return idNameMap;
	}

	private void addDecimalValueToCell(IRow row, String cellName,
			BigDecimal addValue)
	{
		if (addValue == null)
			return;
		BigDecimal cellValue = new BigDecimal(0);
		Object cellObject = row.getCell(cellName).getValue();
		if (cellObject instanceof Integer)
			cellValue = new BigDecimal(((Integer) cellObject).intValue());
		else if (cellObject instanceof BigDecimal)
			cellValue = (BigDecimal) cellObject;

		row.getCell(cellName).setValue(cellValue.add(addValue));
	}
	
	public void appendBalanceFilterSql(FDCSQLBuilder builder, String proName) 
	{
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		
		String id = (String)filterMap.get("balanceId");
		
		if (id != null) 
		{
			builder.appendSql(" and " + proName + " = ? ");
				
			builder.addParam(id);
			
		}
	
	}

	
	public void appendDateFilterSql(FDCSQLBuilder builder, String proName,String byType) 
	{
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		
		Date beginDate = (Date)filterMap.get("beginDate");
		Date endDate = (Date)filterMap.get("endDate");
		

		if (beginDate != null) 
		{
			if ("month".equalsIgnoreCase(byType))
			{
				builder.appendSql(" and " + proName + " >= ? ");
				String dateStr = FDCDateHelper.formatDate(beginDate);
				builder.addParam(new Integer(dateStr));
			}
			else if("year".equalsIgnoreCase(byType))
			{
				builder.appendSql(" and " + proName + " >= ? ");
				String dateStr = FDCDateHelper.formatDate(this.janBeginDate);
				builder.addParam(new Integer(dateStr));
			}
			else if("all".equalsIgnoreCase(byType))
			{
				
			}
			
		}
	
		if (endDate != null) 
		{
			if ("month".equalsIgnoreCase(byType))
			{
				builder.appendSql(" and " + proName + " < ? ");
				String endDateStr = FDCDateHelper.formatDate(FDCDateHelper.getNextDay(endDate));
				builder.addParam(new Integer(endDateStr));
			}
			else if("year".equalsIgnoreCase(byType))
			{
				builder.appendSql(" and " + proName + " < ? ");
				String endDateStr = FDCDateHelper.formatDate(FDCDateHelper.getNextDay(this.thisEndDate));
				builder.addParam(new Integer(endDateStr));
			}
			else if("all".equalsIgnoreCase(byType))
			{

				builder.appendSql(" and " + proName + " < ? ");
				String endDateStr = FDCDateHelper.formatDate(FDCDateHelper.getNextDay(this.thisEndDate));
				builder.addParam(new Integer(endDateStr));
			
			}
		}
	}
	
	/**
	 * 解决右键导出的中断问题
	 */
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}

}