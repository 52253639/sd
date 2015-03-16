/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyReportParameter;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class TenancyTermUI extends AbstractTenancyTermUI
{
	private static final Logger logger = CoreUIObject.getLogger(TenancyTermUI.class);
	Map rowMap = new HashMap();

	private TenancyStatRptFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;

	protected boolean initDefaultFilter() {
		return true;
	}

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

	private TenancyStatRptFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new TenancyStatRptFilterUI(this, this.actionOnLoad);
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	public TenancyTermUI() throws Exception
	{
		super();
	}

	public void storeFields()
	{
		super.storeFields();
	}

	public void onLoad() throws Exception {
		this.tblMain.checkParsed();
		FDCClientHelper.addSqlMenu(this, this.menuFile);
		super.onLoad();
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setEnabled(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.menuEdit.setVisible(false);
		execQuery();
	}

	private void initTable() {
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		for (int i = 1; i < this.tblMain.getColumnCount(); i++) {
			tblMain.getColumn(i).getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
		}
		tblMain.getColumn("tenancyCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("termNewCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("termContinueCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("termExpandCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("termQuitCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("termChangeNameCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("dayCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("weekCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("monthCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("seasonCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("yearCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
	}

	protected void execQuery()
	{
		initTable();
		try
		{
			fillData();
		} catch (Exception e)
		{
			super.handUIException(e);
		}
		Date beginDate = DateTimeUtils.truncateDate(this.getBeginQueryDate());
		Date endDate = DateTimeUtils.truncateDate(this.getEndQueryDate());
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		if (para.isNotNull("isShowAll") && !para.getBoolean("isShowAll"))
		{
			if (beginDate == null && endDate == null)
			{
				this.tblMain.getHeadRow(0).getCell(1).setValue("查询区间数据(全部显示)");
			}
			else if(beginDate == null && endDate != null)
			{
				Calendar cal = new GregorianCalendar();
				cal.setTime(endDate);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE));
				DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
				String des = "查询区间数据(" 	+ FORMAT_DAY.format(cal.getTime()) + "之前)";
				this.tblMain.getHeadRow(0).getCell(1).setValue(des);
			}
			else if(beginDate != null && endDate == null)
			{
				Calendar cal = new GregorianCalendar();
				cal.set(Calendar.DATE, cal.get(Calendar.DATE));
				DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
				String des = "查询区间数据(" + FORMAT_DAY.format(beginDate) + "之后)";
				this.tblMain.getHeadRow(0).getCell(1).setValue(des);
			}
			else
			{
				Calendar cal = new GregorianCalendar();
				cal.setTime(endDate);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE));
				DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
				String des = "查询区间数据(" + FORMAT_DAY.format(beginDate) + "到"
				+ FORMAT_DAY.format(cal.getTime()) + ")";
				this.tblMain.getHeadRow(0).getCell(1).setValue(des);
			}
		} else
		{
			this.tblMain.getHeadRow(0).getCell(1).setValue("查询区间数据(全部显示)");
		}
	}

	private void fillData() throws BOSException, SQLException
	{
		try {
			fillProjectAndBuilding();
		} catch (Exception e) {
			logger.error(e.getMessage());
			handleException(e);
		}
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			for (int j = 1; j < this.tblMain.getColumnCount(); j++) {
				this.tblMain.getCell(i, j).setValue(null);
			}
		}

		//区间新租，续租，扩组套数(从合同房间分录里找，并且实际交房日期不能为空)
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
		.appendSql("select building.fid buildingID,sum(room.FbuildingArea) buildingArea,FTenRoomState FTenancyState,count(*) totalCount from T_TEN_TenancyRoomEntry ten "+
				" left join t_she_room room on ten.FRoomID = room.fid "+
		        " left join t_she_building building on room.FBuildingID = building.fid ");
		builder.appendSql(" where FActDeliveryRoomDate is not null ");
		this.appendFilterSql(builder, "FActDeliveryRoomDate");
		builder.appendSql("group by building.fid,FTenRoomState ");
		IRowSet termTenSet = null;
		try {
			termTenSet = builder.executeQuery();
			Map newTenancyMap = new HashMap();
			Map newTenancyAreaMap = new HashMap();
			Map conTenancyAreaMap = new HashMap();
			Map enlargTenAreaMap = new HashMap();
			Map conTenaycyMap = new HashMap();
			Map enlargTenMap = new HashMap();						
			while (termTenSet.next()) {				
				String buildingID = termTenSet.getString("buildingID");
				String tenState = termTenSet.getString("FTenancyState");
				int totalCount = termTenSet.getInt("totalCount");
				BigDecimal buildingArea = termTenSet.getBigDecimal("buildingArea");
				//				if(newTenancyMap.get(buildingID)==null || conTenaycyMap.get(buildingID)==null ||enlargTenMap.get(buildingID)==null
				//			       || newTenancyAreaMap.get(buildingID)==null || conTenancyAreaMap.get(buildingID)==null || enlargTenAreaMap.get(buildingID)==null) {
				if(tenState !=null && (TenancyStateEnum.NEWTENANCY_VALUE).equals(tenState))
				{
					this.setCountArea(buildingID,totalCount, buildingArea, newTenancyMap, newTenancyAreaMap);
				}
				else if(tenState !=null && (TenancyStateEnum.CONTINUETENANCY_VALUE).equals(tenState))
				{
					this.setCountArea(buildingID,totalCount, buildingArea, conTenaycyMap, conTenancyAreaMap);
				}else if(tenState !=null && (TenancyStateEnum.ENLARGETENANCY_VALUE).equals(tenState))
				{
					this.setCountArea(buildingID,totalCount, buildingArea, enlargTenMap, enlargTenAreaMap);
				}
				//				}
				//				else
				//				{
				//					if(tenState!=null && (TenancyStateEnum.NEWTENANCY_VALUE).equals(tenState))
				//					{
				//						int newCount = ((Integer)newTenancyMap.get(buildingID)).intValue();
				//						BigDecimal newArea = (BigDecimal)newTenancyAreaMap.get(buildingID);
				//						newTenancyMap.put(buildingID, newArea.add(buildingArea));
				//						newTenancyMap.put(buildingID,new Integer(newCount+totalCount));
				//					}else if(tenState !=null && (TenancyStateEnum.CONTINUETENANCY_VALUE).equals(tenState))
				//					{
				//						int continueCount = ((Integer)conTenaycyMap.get(buildingID)).intValue();
				//						BigDecimal  continueArea = (BigDecimal)conTenancyAreaMap.get(buildingID);
				//						conTenancyAreaMap.put(buildingID, continueArea.add(buildingArea));
				//						conTenaycyMap.put(buildingID,new Integer(continueCount+totalCount));
				//					}else if(tenState !=null && (TenancyStateEnum.ENLARGETENANCY_VALUE).equals(tenState))
				//					{
				//						int enlargCount = ((Integer)enlargTenMap.get(buildingID)).intValue();
				//						BigDecimal enlargArea = (BigDecimal)enlargTenAreaMap.get(buildingID);
				//						enlargTenAreaMap.put(buildingID, enlargArea.add(buildingArea));
				//						enlargTenMap.put(buildingID,new Integer(enlargCount+totalCount));
				//					}
				//				}
			}
			Set keySet = rowMap.keySet();
			Iterator iterator = keySet.iterator();
			while(iterator.hasNext()) {
				Map newTenancyCellMap = new HashMap();
				Map conTenancyCellMap = new HashMap();
				Map enlargTenCellMap = new HashMap();
				Map tenancyCellMap = new HashMap();
				String buidIdStr = (String)iterator.next();
				int count = 0;
				BigDecimal area = new BigDecimal(0);
				Integer termNewCount = newTenancyMap.get(buidIdStr)==null?new Integer(0):(Integer)newTenancyMap.get(buidIdStr);
				BigDecimal termNewArea = newTenancyAreaMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)newTenancyAreaMap.get(buidIdStr);
				Integer termExpandCount = enlargTenMap.get(buidIdStr)==null?new Integer(0):(Integer)enlargTenMap.get(buidIdStr);
				BigDecimal termExpandArea = enlargTenAreaMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)enlargTenAreaMap.get(buidIdStr);
				Integer termContinueCount = conTenaycyMap.get(buidIdStr)==null?new Integer(0):(Integer)conTenaycyMap.get(buidIdStr);
				BigDecimal termContinueArea = conTenancyAreaMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)conTenancyAreaMap.get(buidIdStr);
				IRow thisRow = (IRow)rowMap.get(buidIdStr);
				count = termNewCount.intValue()+termExpandCount.intValue()+termContinueCount.intValue();
				area = termNewArea.add(termExpandArea).add(termContinueArea);

				String newTenStr = TenancyHelper.setCellValue(termNewCount, termNewArea,newTenancyCellMap);

				String expandStr = TenancyHelper.setCellValue(termExpandCount, termExpandArea,enlargTenCellMap);

				String conTenancyStr = TenancyHelper.setCellValue(termContinueCount, termContinueArea,conTenancyCellMap);

				String tenancyCountStr = TenancyHelper.setCellValue(new Integer(count), area,tenancyCellMap);

				//Integer tenancyCount = count==0?null:new Integer(count);
				thisRow.getCell("termNewCount").setValue(newTenStr);
				thisRow.getCell("termNewCount").setUserObject(newTenancyCellMap);
				thisRow.getCell("termExpandCount").setValue(expandStr);
				thisRow.getCell("termExpandCount").setUserObject(enlargTenCellMap);
				thisRow.getCell("termContinueCount").setValue(conTenancyStr);
				thisRow.getCell("termContinueCount").setUserObject(conTenancyCellMap);
				thisRow.getCell("tenancyCount").setValue(tenancyCountStr);
				thisRow.getCell("tenancyCount").setUserObject(tenancyCellMap);
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			handleException(e);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			handleException(e);
		}

		//区间退租套数(在合同房间分录里找，以实际退租日期为准)
		builder = new FDCSQLBuilder();
		builder
		.appendSql("select building.fid buildingID,count(*) totalCount from T_TEN_TenancyRoomEntry ten "+
				" left join t_she_room room on ten.FRoomID = room.fid "+
		" left join t_she_building building on room.FBuildingID = building.fid ");
		builder.appendSql(" where FActQuitTenDate is not null ");
		this.appendFilterSql(builder, "FActQuitTenDate");
		builder.appendSql("group by building.fid ");
		IRowSet termQuitTenSet = null;
		try {
			termQuitTenSet = builder.executeQuery();
			Map quitTenMap = new HashMap();
			while (termQuitTenSet.next()) {
				String buildingID = termQuitTenSet.getString("buildingID");
				int totalCount = termQuitTenSet.getInt("totalCount");
				if(quitTenMap.get(buildingID)==null) {
					
						quitTenMap.put(buildingID,new Integer(totalCount));
					
				}else
				{
					
						int newCount = ((Integer)quitTenMap.get(buildingID)).intValue();
						quitTenMap.put(buildingID,new Integer(newCount+totalCount));
					
				}
				Set keySet = rowMap.keySet();
				Iterator iterator = keySet.iterator();
				while(iterator.hasNext()) {
					String buidIdStr = (String)iterator.next();
					IRow thisRow = (IRow)rowMap.get(buidIdStr);
					thisRow.getCell("termQuitCount").setValue(quitTenMap.get(buidIdStr));
				}
			}
		}catch (BOSException e) {
			logger.error(e.getMessage());
			handleException(e);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			handleException(e);
		}

		//区间转名套数(从合同房间分录里找，以合同状态来区分转名套数)
		builder = new FDCSQLBuilder();
		builder.appendSql("select building.fid buildingID,tenBill.FTenancyType tenancyType,count(*) totalCount from T_TEN_TenancyRoomEntry ten"+
				" left join t_she_room room on ten.FRoomID = room.fid "+
				" left join t_she_building building on room.FBuildingID = building.fid "+
		" left join T_TEN_TenancyBill tenBill on ten.FTenancyID = tenBill.fid ");
		builder.appendSql(" where FActDeliveryRoomDate is not null ");
		this.appendFilterSql(builder, "FActDeliveryRoomDate");
		builder.appendSql(" group by building.fid,tenBill.FTenancyType ");
		IRowSet termChangeSet = null;
		try {
			termChangeSet = builder.executeQuery();
			Map changeMap = new HashMap();
			while (termChangeSet.next()) {
				String buildingID = termChangeSet.getString("buildingID");
				String tenancyType = termChangeSet.getString("tenancyType");
				int totalCount = termChangeSet.getInt("totalCount");
				if(changeMap.get(buildingID)==null) {
					if((TenancyContractTypeEnum.CHANGENAME_VALUE).equals(tenancyType))
					{
						changeMap.put(buildingID,new Integer(totalCount));
					}
				}else
				{
					if((TenancyContractTypeEnum.CHANGENAME_VALUE).equals(tenancyType))
					{
						int newCount = ((Integer)changeMap.get(buildingID)).intValue();
						changeMap.put(buildingID,new Integer(newCount+totalCount));
					}
				}
				Set keySet = rowMap.keySet();
				Iterator iterator = keySet.iterator();
				while(iterator.hasNext()) {
					String buidIdStr = (String)iterator.next();
					IRow thisRow = (IRow)rowMap.get(buidIdStr);
					thisRow.getCell("termChangeNameCount").setValue(changeMap.get(buidIdStr));
				}
			}
		}catch (BOSException e) {
			logger.error(e.getMessage());
			handleException(e);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			handleException(e);
		}

		//楼栋总面积
		builder = new FDCSQLBuilder();
		builder
		.appendSql("select fbuildingID, sum(fbuildingArea) buildingArea from t_she_room room " +
		" where room.FIsForTen=1 group by fbuildingID ");
		IRowSet buildSet = null;
		buildSet = builder.executeQuery();
		Map buildingMap  = new HashMap();
		while(buildSet.next())
		{
			String buildingID = buildSet.getString("fbuildingID");
			BigDecimal buildingArea = buildSet.getBigDecimal("buildingArea");
			buildingMap.put(buildingID, buildingArea);
			Set keySet = rowMap.keySet();
			Iterator iterator = keySet.iterator();
			while(iterator.hasNext()) {
				String buidIdStr = (String)iterator.next();
				BigDecimal allArea = buildingMap.get(buidIdStr)==null?null:(BigDecimal)buildingMap.get(buidIdStr);
				IRow thisRow = (IRow)rowMap.get(buidIdStr);
				thisRow.getCell("allArea").setValue(allArea);
			}
		}

		//查询时间
		Date beginDate = DateTimeUtils.truncateDate(this.getBeginQueryDate());
		Date endDate = DateTimeUtils.truncateDate(this.getEndQueryDate());
		beginDate = FDCDateHelper.getDayBegin(beginDate);
		endDate = FDCDateHelper.getDayBegin(endDate);

		builder = new FDCSQLBuilder();
		builder
		.appendSql("select building.fid buildingID,room.fbuildingArea buildingArea,ten.fstartDate startDate,ten.fenddate endDate " +
				" from T_TEN_TenancyRoomEntry ten "+
				" left join t_she_room room on ten.FRoomID = room.fid "+
		" left join t_she_building building on room.FBuildingID = building.fid ");
		builder.appendSql(" where ten.FStartDate is not null and ten.fenddate is not null");
		//this.appendFilterSql2(builder, "FStartDate","FEndDate");
		IRowSet tenRateSet = null;
		tenRateSet = builder.executeQuery();
		Map tenRateMap = new HashMap();
		while (tenRateSet.next()) {
			String buildingID = tenRateSet.getString("buildingID");
			BigDecimal buildingArea = tenRateSet.getBigDecimal("buildingArea");
			buildingArea = buildingArea==null?new BigDecimal(0):buildingArea;
			Date tenStratDate = tenRateSet.getDate("startDate");
			Date tenEndDate = tenRateSet.getDate("endDate");		
			tenStratDate = FDCDateHelper.getDayBegin(tenStratDate);
			tenEndDate = FDCDateHelper.getDayBegin(tenEndDate);
			BigDecimal tenRate = new BigDecimal(0);
			if(tenRateMap.get(buildingID)==null)
			{
				Date end = this.minDate(endDate, tenEndDate);
				Date start = this.maxDate(beginDate,tenStratDate);
				if(!end.before(start))
				{
					BigDecimal allArea = new BigDecimal(0);
					if(buildingMap.get(buildingID)!=null)
					{
						allArea = (BigDecimal)buildingMap.get(buildingID);
					}
					allArea = allArea.compareTo(new BigDecimal(0))==0?new BigDecimal(1):allArea;
					int tenDays = TenancyHelper.getDistantDays(start, end);
					int allDays = TenancyHelper.getDistantDays(beginDate, endDate);
					tenRate = buildingArea.divide(allArea, 10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).multiply(new BigDecimal(tenDays)).divide(new BigDecimal(allDays), 10,BigDecimal.ROUND_HALF_UP);
					tenRateMap.put(buildingID, tenRate);
				}
			}else
			{
				Date end = this.minDate(endDate, tenEndDate);
				Date start = this.maxDate(beginDate,tenStratDate);
				if(!end.before(start))
				{
					BigDecimal allArea = new BigDecimal(0);
					if(buildingMap.get(buildingID)!=null)
					{
						allArea = (BigDecimal)buildingMap.get(buildingID);
					}
					allArea = allArea.compareTo(new BigDecimal(0))==0?new BigDecimal(1):allArea;
					int tenDays = TenancyHelper.getDistantDays(start, end);
					int allDays = TenancyHelper.getDistantDays(beginDate, endDate);
					tenRate = buildingArea.divide(allArea, 10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).multiply(new BigDecimal(tenDays)).divide(new BigDecimal(allDays), 10,BigDecimal.ROUND_HALF_UP);
					BigDecimal oldTenRate = (BigDecimal)tenRateMap.get(buildingID);
					tenRateMap.put(buildingID, tenRate.add(oldTenRate));
				}
			}
//			Set keySet = rowMap.keySet();
//			Iterator iterator = keySet.iterator();
//			while(iterator.hasNext()) {
//				String buidIdStr = (String)iterator.next();
//				BigDecimal newtenRate = tenRateMap.get(buidIdStr)==null?null:(BigDecimal)tenRateMap.get(buidIdStr);
//				IRow thisRow = (IRow)rowMap.get(buidIdStr);
//				thisRow.getCell("avgTenRate").setValue(newtenRate);
//			}
		}
		//按租金类型进行统计，默认为建筑租金
		//因为现在可以不定租走业务，所以租金类型必须根据合同房间分录的成交租金类型来统计，租金取合同成交租金单价是成交租金单价
		builder = new FDCSQLBuilder();
		builder.appendSql("select building.fid buildingID,ten.FRentType rentType,sum(room.fbuildingArea) buildingArea,sum(ten.FDealRoomRentPrice) rentPrice, " +
				" sum(ten.FDealRoomRent) standardRent,count(*) totalCount from T_TEN_TenancyRoomEntry ten "+
				" left join t_she_room room on ten.FRoomID = room.fid "+ 
		        " left join t_she_building building on room.FBuildingID = building.fid ");
		builder.appendSql("where ten.FActDeliveryRoomDate is not null");
		this.appendFilterSql(builder, "FActDeliveryRoomDate");
		builder.appendSql(" group by building.fid,ten.FRentType");
		IRowSet roomRentTypeSet = null;
		roomRentTypeSet = builder.executeQuery();
		Map byDayMap = new HashMap();
		Map byWeekMap = new HashMap();
		Map byMonthMap = new HashMap();
		Map bySeasonMap = new HashMap();
		Map byYearMap = new HashMap();
		Map byDayRentMap = new HashMap();
		Map byWeekRentMap = new HashMap();
		Map byMonthRentMap = new HashMap();
		Map bySeasonRentMap = new HashMap();
		Map byYearRentMap = new HashMap();
		Map byDayStrandardRentMap = new HashMap();
		Map byWeekStrandardRentMap = new HashMap();
		Map byMonthStrandardRentMap = new HashMap();
		Map bySeasonStrandardRentMap = new HashMap();
		Map byYearStrandardRentMap = new HashMap();
		Map byDayAreaMap = new HashMap();
		Map byWeekAreaMap = new HashMap();
		Map byMonthAreaMap = new HashMap();
		Map bySeasonAreaMap = new HashMap();
		Map byYearAreaMap = new HashMap();    
		while(roomRentTypeSet.next())
		{       	
			String buildingID = roomRentTypeSet.getString("buildingID");
			String rentType = roomRentTypeSet.getString("rentType");
			BigDecimal buildingArea = roomRentTypeSet.getBigDecimal("buildingArea");
			BigDecimal rentPrice = roomRentTypeSet.getBigDecimal("rentPrice");
			BigDecimal standardRent = roomRentTypeSet.getBigDecimal("standardRent");
			int totalCount = roomRentTypeSet.getInt("totalCount");
			//if(byDayMap.get(buildingID)==null || byMonthMap.get(buildingID)==null ||bySeasonMap.get(buildingID)==null ||byYearMap.get(buildingID)==null) {
			if(rentType !=null && (RentTypeEnum.RENTBYDAY_VALUE).equals(rentType))
			{
				this.setRentCountArea(buildingID, totalCount, buildingArea, rentPrice, 
						standardRent, byDayMap, byDayAreaMap, byDayRentMap, byDayStrandardRentMap);
			}else if(rentType !=null && (RentTypeEnum.RENTBYWEEK_VALUE).equals(rentType))
			{
				this.setRentCountArea(buildingID, totalCount, buildingArea, rentPrice, 
						standardRent, byWeekMap, byWeekAreaMap, byWeekRentMap, byWeekStrandardRentMap);
			}
			else if(rentType !=null && (RentTypeEnum.RENTBYMONTH_VALUE).equals(rentType))
			{
				this.setRentCountArea(buildingID, totalCount, buildingArea, rentPrice, 
						standardRent, byMonthMap, byMonthAreaMap, byMonthRentMap, byMonthStrandardRentMap);
			}else if(rentType !=null && (RentTypeEnum.RENTBYQUARTER_VALUE).equals(rentType))
			{
				this.setRentCountArea(buildingID, totalCount, buildingArea, rentPrice, 
						standardRent, bySeasonMap, bySeasonAreaMap, bySeasonRentMap, bySeasonStrandardRentMap);
			}else if(rentType !=null && (RentTypeEnum.RENTBYYEAR_VALUE).equals(rentType))
			{
				this.setRentCountArea(buildingID, totalCount, buildingArea, rentPrice, 
						standardRent, byYearMap, byYearAreaMap, byYearRentMap, byYearStrandardRentMap);

			}
			//}
			//			else
			//			{
			//				if(rentType!=null && (RentTypeEnum.RENTBYDAY_VALUE).equals(rentType))
			//				{
			//					int byDayCount = ((Integer)byDayMap.get(buildingID)).intValue();
			//					BigDecimal byDayRentPrice = (BigDecimal)byDayRentMap.get(buildingID);
			//					BigDecimal byDayStrandardRent = (BigDecimal)byDayStrandardRentMap.get(buildingID);
			//					BigDecimal byDayArea = (BigDecimal)byDayAreaMap.get(buildingID);
			//					byDayMap.put(buildingID,new Integer(byDayCount+totalCount));
			//					byDayRentMap.put(buildingID,byDayRentPrice.add(rentPrice));
			//					byDayStrandardRentMap.put(buildingID, byDayStrandardRent.add(standardRent));
			//					byDayAreaMap.put(buildingID, byDayArea.add(buildingArea));
			//				}else if(rentType !=null && (RentTypeEnum.RENTBYWEEK_VALUE).equals(rentType))
			//				{
			//					int byWeekCount = ((Integer)byWeekMap.get(buildingID)).intValue();
			//					BigDecimal byWeekArea = (BigDecimal)byWeekAreaMap.get(buildingID);
			//					BigDecimal byWeekRentPrice = (BigDecimal)byWeekRentMap.get(buildingID);
			//					BigDecimal byWeekStrandardRent = (BigDecimal)byWeekStrandardRentMap.get(buildingID);
			//					byWeekMap.put(buildingID,new Integer(byWeekCount+totalCount));
			//					byWeekRentMap.put(buildingID,byWeekRentPrice.add(rentPrice));
			//					byWeekStrandardRentMap.put(buildingID, byWeekStrandardRent.add(standardRent));
			//					byWeekAreaMap.put(buildingID, byWeekArea.add(buildingArea));
			//				}
			//				else if(rentType !=null && (RentTypeEnum.RENTBYMONTH_VALUE).equals(rentType))
			//				{
			//					int byMonthCount = ((Integer)byMonthMap.get(buildingID)).intValue();
			//					BigDecimal byMonthRentPrice = (BigDecimal)byMonthRentMap.get(buildingID);
			//					BigDecimal byMonthStrandardRent = (BigDecimal)byMonthStrandardRentMap.get(buildingID);
			//					byMonthMap.put(buildingID,new Integer(byMonthCount+totalCount));
			//					byMonthRentMap.put(buildingID,byMonthRentPrice.add(rentPrice));
			//					byMonthStrandardRentMap.put(buildingID, byMonthStrandardRent.add(standardRent));
			//					BigDecimal byMonthArea = (BigDecimal)byMonthAreaMap.get(buildingID);
			//					byMonthAreaMap.put(buildingID, byMonthArea.add(buildingArea));
			//				}else if(rentType !=null && (RentTypeEnum.RENTBYQUARTER_VALUE).equals(rentType))
			//				{
			//					int bySeasonCount = ((Integer)bySeasonMap.get(buildingID)).intValue();
			//					BigDecimal bySeasonRentPrice = (BigDecimal)bySeasonRentMap.get(buildingID);
			//					BigDecimal bySeasonStrandardRent = (BigDecimal)bySeasonStrandardRentMap.get(buildingID);
			//					bySeasonMap.put(buildingID,new Integer(bySeasonCount+totalCount));
			//					bySeasonRentMap.put(buildingID,bySeasonRentPrice.add(rentPrice));
			//					bySeasonStrandardRentMap.put(buildingID, bySeasonStrandardRent.add(standardRent));
			//					BigDecimal bySeasonArea = (BigDecimal)bySeasonAreaMap.get(buildingID);
			//					bySeasonAreaMap.put(buildingID, bySeasonArea.add(buildingArea));
			//				}else if(rentType !=null && (RentTypeEnum.RENTBYYEAR_VALUE).equals(rentType))
			//				{
			//					int byYearCount = ((Integer)byYearMap.get(buildingID)).intValue();
			//					BigDecimal byYearRentPrice = (BigDecimal)byYearRentMap.get(buildingID);
			//					BigDecimal byYearStrandardRent = (BigDecimal)byYearStrandardRentMap.get(buildingID);
			//					byYearMap.put(buildingID,new Integer(byYearCount+totalCount));
			//					byYearRentMap.put(buildingID,byYearRentPrice.add(rentPrice));
			//					byYearStrandardRentMap.put(buildingID, byYearStrandardRent.add(standardRent));
			//					BigDecimal byYearArea = (BigDecimal)byYearAreaMap.get(buildingID);
			//					byYearAreaMap.put(buildingID, byYearArea.add(buildingArea));
			//				}
			//			}
		}
		Set keySet = rowMap.keySet();
		Iterator iterator = keySet.iterator();
		while(iterator.hasNext()) {
			Map byDayCellMap = new HashMap();
			Map byWeekCellMap = new HashMap();
			Map byMonthCellMap = new HashMap();
			Map bySeasonCellMap = new HashMap();
			Map byYearCellMap = new HashMap();
			String buidIdStr = (String)iterator.next();
			Integer byDayCount = byDayMap.get(buidIdStr)==null?new Integer(0):(Integer)byDayMap.get(buidIdStr);
			Integer byWeekCount = byWeekMap.get(buidIdStr)==null?new Integer(0):(Integer)byWeekMap.get(buidIdStr);
			Integer byMonthCount = byMonthMap.get(buidIdStr)==null?new Integer(0):(Integer)byMonthMap.get(buidIdStr);
			Integer bySeasonCount = bySeasonMap.get(buidIdStr)==null?new Integer(0):(Integer)bySeasonMap.get(buidIdStr);
			Integer byYearCount = byYearMap.get(buidIdStr)==null?new Integer(0):(Integer)byYearMap.get(buidIdStr);

			BigDecimal byDayRent = byDayRentMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)byDayRentMap.get(buidIdStr);
			BigDecimal byWeekRent = byWeekRentMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)byWeekRentMap.get(buidIdStr);
			BigDecimal byMonthRent = byMonthRentMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)byMonthRentMap.get(buidIdStr);
			BigDecimal bySeasonRent = bySeasonRentMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)bySeasonRentMap.get(buidIdStr);
			BigDecimal byYearRent = byYearRentMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)byYearRentMap.get(buidIdStr);

			BigDecimal byDayArea = byDayAreaMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)byDayAreaMap.get(buidIdStr);
			BigDecimal byWeekArea = byWeekAreaMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)byWeekAreaMap.get(buidIdStr);
			BigDecimal byMonthArea = byMonthAreaMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)byMonthAreaMap.get(buidIdStr);
			BigDecimal bySeasonArea = bySeasonAreaMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)bySeasonAreaMap.get(buidIdStr);
			BigDecimal byYearArea = byYearAreaMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)byYearAreaMap.get(buidIdStr);
			//按日套数为0的时候，租金肯定也是0，所以把套数变为1结果也是一样还是0.只是为了不出byZero的错误
			int dayCount = byDayCount.intValue()==0?1:byDayCount.intValue();
			int weekCount = byWeekCount.intValue()==0?1:byWeekCount.intValue();
			int monthCount = byMonthCount.intValue()==0?1:byMonthCount.intValue();
			int seasonCount = bySeasonCount.intValue()==0?1:bySeasonCount.intValue();
			int yearCount = byYearCount.intValue()==0?1:byYearCount.intValue();

			BigDecimal avgDayPrice = byDayRent.divide(new BigDecimal(dayCount),2,BigDecimal.ROUND_HALF_UP);
			BigDecimal avgWeekPrice = byWeekRent.divide(new BigDecimal(weekCount),2,BigDecimal.ROUND_HALF_UP);
			BigDecimal avgMonthPrice = byMonthRent.divide(new BigDecimal(monthCount),2,BigDecimal.ROUND_HALF_UP);
			BigDecimal avgSeasonPrice = bySeasonRent.divide(new BigDecimal(seasonCount),2,BigDecimal.ROUND_HALF_UP);
			BigDecimal avgYearPrice = byYearRent.divide(new BigDecimal(yearCount),2,BigDecimal.ROUND_HALF_UP);

			//当租金为0的时候处理一下，免得界面上看起来太难看
			avgDayPrice = avgDayPrice.compareTo(new BigDecimal(0))==0?null:avgDayPrice;
			avgWeekPrice = avgWeekPrice.compareTo(new BigDecimal(0))==0?null:avgWeekPrice;
			avgMonthPrice = avgMonthPrice.compareTo(new BigDecimal(0))==0?null:avgMonthPrice;
			avgSeasonPrice = avgSeasonPrice.compareTo(new BigDecimal(0))==0?null:avgSeasonPrice;
			avgYearPrice = avgYearPrice.compareTo(new BigDecimal(0))==0?null:avgYearPrice;


			String dayStr = TenancyHelper.setCellValue(byDayCount, byDayArea,byDayCellMap);
			String weekStr = TenancyHelper.setCellValue(byWeekCount, byWeekArea,byWeekCellMap);
			String monthStr = TenancyHelper.setCellValue(byMonthCount, byMonthArea,byMonthCellMap);
			String seasonStr = TenancyHelper.setCellValue(bySeasonCount, bySeasonArea,bySeasonCellMap);
			String yearStr = TenancyHelper.setCellValue(byYearCount, byYearArea,byYearCellMap);

			IRow thisRow = (IRow)rowMap.get(buidIdStr);
			thisRow.getCell("dayCount").setValue(dayStr);
			thisRow.getCell("dayCount").setUserObject(byDayCellMap);
			thisRow.getCell("weekCount").setValue(weekStr);
			thisRow.getCell("weekCount").setUserObject(byWeekCellMap);
			thisRow.getCell("monthCount").setValue(monthStr);
			thisRow.getCell("monthCount").setUserObject(byMonthCellMap);
			thisRow.getCell("seasonCount").setValue(seasonStr);
			thisRow.getCell("seasonCount").setUserObject(bySeasonCellMap);
			thisRow.getCell("yearCount").setValue(yearStr);
			thisRow.getCell("yearCount").setUserObject(byYearCellMap);

			thisRow.getCell("avgDayPrice").setValue(avgDayPrice);
			thisRow.getCell("avgWeekPrice").setValue(avgWeekPrice);
			thisRow.getCell("avgMonthPrice").setValue(avgMonthPrice);
			thisRow.getCell("avgSeasonPrice").setValue(avgSeasonPrice);
			thisRow.getCell("avgYearPrice").setValue(avgYearPrice);

			thisRow.getCell("sumDayPrice").setValue(byDayStrandardRentMap.get(buidIdStr));
			thisRow.getCell("sumWeekPrice").setValue(byWeekStrandardRentMap.get(buidIdStr));
			thisRow.getCell("sumMonthPrice").setValue(byMonthStrandardRentMap.get(buidIdStr));
			thisRow.getCell("sumMonthPrice").setUserObject(byMonthStrandardRentMap.get(buidIdStr));
			thisRow.getCell("sumSeasonPrice").setValue(bySeasonStrandardRentMap.get(buidIdStr));
			thisRow.getCell("sumYearPrice").setValue(byYearStrandardRentMap.get(buidIdStr));
		}

		//累计收租(从房地产收款单分录)
		builder = new FDCSQLBuilder();
		builder.appendSql(" select FBuildingId,sum(fdcEntry.famount) termSumRent from t_she_fdcreceivebillentry fdcEntry "+
				" left join t_cas_receivingbill cas on cas.fid=fdcentry.FReceivingBillID "+ 
				" left join T_SHE_FDCReceiveBill fdc on cas.FFDCReceivebillid=fdc.fid "+
				" left join t_she_room room on fdc.froomid=room.fid "+
				" left join t_she_building building on  room.fbuildingid=building.fid "+ 
				" left join T_TEN_TenancyBill as tenBill on room.FLastTenancyID= tenBill.fid "+ 
				" left join t_she_moneyDefine money on fdcentry.FMoneyDefineId=money.fid "+ 
		" where money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount' and money.FSysType ='TenancySys' ");
		this.appendFilterSql(builder, "cas.FBizDate");
		builder.appendSql(" group by FBuildingId ");
		IRowSet allReceiveSet = null;
		allReceiveSet = builder.executeQuery();
		while(allReceiveSet.next())
		{
			String buildingId = allReceiveSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null) {
				continue;
			}
			BigDecimal termSumRent = allReceiveSet.getBigDecimal("termSumRent");
			row.getCell("termSumRent").setValue(termSumRent);
		}
		setUnionData();
	}

	/**
	 * 根据房间状态来设置
	 * */
	public void setCountArea(String buildingID,int tenCount,BigDecimal buildingArea,Map map,Map areaMap)
	{
		if(map.get("buildingID")==null)
		{
			map.put(buildingID, new Integer(tenCount));
			areaMap.put(buildingID, buildingArea);
		}else
		{
			int newCount = ((Integer)map.get(buildingID)).intValue();
			map.put(buildingID,new Integer(newCount+tenCount));
			BigDecimal area = (BigDecimal)areaMap.get(buildingID);
			areaMap.put(buildingID,area.add(buildingArea));
		}
	}

	/**
	 * 根据房间状态来设置套数面积租金和标准租金
	 * */
	public void setRentCountArea(String buildingID,int tenCount,BigDecimal buildingArea,BigDecimal rentPrice,BigDecimal strandardRent,
			Map countMap,Map areaMap,Map rentPriceMap,Map strandardRentMap)
	{
		if(countMap.get("buildingID")==null)
		{
			countMap.put(buildingID, new Integer(tenCount));
			areaMap.put(buildingID, buildingArea);
			rentPriceMap.put(buildingID, rentPrice);
			strandardRentMap.put(buildingID, strandardRent);
		}else
		{
			int newCount = ((Integer)countMap.get(buildingID)).intValue();
			countMap.put(buildingID,new Integer(newCount+tenCount));
			BigDecimal area = (BigDecimal)areaMap.get(buildingID);
			areaMap.put(buildingID,area.add(buildingArea));

			BigDecimal rentPrice2 = (BigDecimal)rentPriceMap.get(buildingID);
			BigDecimal strandardRent2 = (BigDecimal)strandardRentMap.get(buildingID);
			rentPriceMap.put(buildingID,rentPrice2.add(rentPrice));
			strandardRentMap.put(buildingID, strandardRent2.add(strandardRent));

		}

	}

	private Date getBeginQueryDate() {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
				.getCustomerParams());
		return this.getFilterUI().getBeginQueryDate(para);
	}

	private Date getEndQueryDate() {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
				.getCustomerParams());
		return this.getFilterUI().getEndQueryDate(para);
	}

	//	设置汇总行
	public void setUnionData() {
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (row.getUserObject() == null) {
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < tblMain.getRowCount(); j++) {
					IRow rowAfter = tblMain.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int j = 1; j < this.tblMain.getColumnCount(); j++) {
					BigDecimal aimCost = FMConstants.ZERO;
					String strValue = "";
					int count = 0;
					BigDecimal area  = FMConstants.ZERO;
					BigDecimal sumMonthArea  = FMConstants.ZERO;
					BigDecimal sumMonthPrice  = FMConstants.ZERO;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(j).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								aimCost = aimCost.add((BigDecimal) value);
								if("avgMonthPrice".equals(this.tblMain.getColumn(j).getKey()))
								{
									BigDecimal monthPrice = rowAdd.getCell("sumMonthPrice").getValue()==null?new BigDecimal(0):(BigDecimal)rowAdd.getCell("sumMonthPrice").getValue();							
									Map map = (HashMap)rowAdd.getCell("monthCount").getUserObject();
									BigDecimal monthArea = new BigDecimal(0);
									if(map!=null)
									{
										monthArea = (BigDecimal)map.get("面积");
									}
									sumMonthArea = sumMonthArea.add(monthArea);
									sumMonthPrice = sumMonthPrice.add(monthPrice);
									BigDecimal avgPrice = sumMonthPrice.divide(sumMonthArea, 2,BigDecimal.ROUND_HALF_UP);
									aimCost = avgPrice;
								}
							} else if (value instanceof Integer) {
								aimCost = aimCost.add(new BigDecimal(
										((Integer) value).toString()));
							} else if(value instanceof String)
							{
								if("dayCount".equals(this.tblMain.getColumn(j).getKey())){
									Map dayCountCellMap = (HashMap)rowAdd.getCell("dayCount").getUserObject();
									if(dayCountCellMap!=null)
									{
										Object[] obj = this.getCellStr(dayCountCellMap, count, area);
										count = ((Integer)obj[0]).intValue();
										area = (BigDecimal)obj[1];
									}
								}else if("weekCount".equals(this.tblMain.getColumn(j).getKey()))
								{
									Map weekCountCellMap = (HashMap)rowAdd.getCell("weekCount").getUserObject();
									if(weekCountCellMap!=null)
									{
										Object[] obj = this.getCellStr(weekCountCellMap, count, area);
										count = ((Integer)obj[0]).intValue();
										area = (BigDecimal)obj[1];
									}
								}else if("monthCount".equals(this.tblMain.getColumn(j).getKey()))
								{
									Map monthCountCellMap = (HashMap)rowAdd.getCell("monthCount").getUserObject();
									if(monthCountCellMap!=null)
									{
										Object[] obj = this.getCellStr(monthCountCellMap, count, area);
										count = ((Integer)obj[0]).intValue();
										area = (BigDecimal)obj[1];
									}
								}else if("seasonCount".equals(this.tblMain.getColumn(j).getKey()))
								{
									Map seasonCountCellMap = (HashMap)rowAdd.getCell("seasonCount").getUserObject();
									if(seasonCountCellMap!=null)
									{
										Object[] obj = this.getCellStr(seasonCountCellMap, count, area);
										count = ((Integer)obj[0]).intValue();
										area = (BigDecimal)obj[1];
									}
								}else if("yearCount".equals(this.tblMain.getColumn(j).getKey()))
								{
									Map yearCountCellMap = (HashMap)rowAdd.getCell("yearCount").getUserObject();
									if(yearCountCellMap!=null)
									{
										Object[] obj = this.getCellStr(yearCountCellMap, count, area);
										count = ((Integer)obj[0]).intValue();
										area = (BigDecimal)obj[1];
									}
								}else if("tenancyCount".equals(this.tblMain.getColumn(j).getKey()))
								{
									Map tenancyCellMap = (HashMap)rowAdd.getCell("tenancyCount").getUserObject();
									if(tenancyCellMap!=null)
									{
										Object[] obj = this.getCellStr(tenancyCellMap, count, area);
										count = ((Integer)obj[0]).intValue();
										area = (BigDecimal)obj[1];
									}
								}else if("termNewCount".equals(this.tblMain.getColumn(j).getKey()))
								{
									Map newTenancyCellMap = (HashMap)rowAdd.getCell("termNewCount").getUserObject();
									if(newTenancyCellMap!=null)
									{
										Object[] obj = this.getCellStr(newTenancyCellMap, count, area);
										count = ((Integer)obj[0]).intValue();
										area = (BigDecimal)obj[1];
									}
								}else if("termContinueCount".equals(this.tblMain.getColumn(j).getKey()))
								{
									Map conTenancyCellMap = (HashMap)rowAdd.getCell("termContinueCount").getUserObject();
									if(conTenancyCellMap!=null)
									{
										Object[] obj = this.getCellStr(conTenancyCellMap, count, area);
										count = ((Integer)obj[0]).intValue();
										area = (BigDecimal)obj[1];
									}
								}else if("termExpandCount".equals(this.tblMain.getColumn(j).getKey()))
								{
									Map enlargTenCellMap = (HashMap)rowAdd.getCell("termExpandCount").getUserObject();
									if(enlargTenCellMap!=null)
									{
										Object[] obj = this.getCellStr(enlargTenCellMap, count, area);
										count = ((Integer)obj[0]).intValue();
										area = (BigDecimal)obj[1];
									}
								}
							}
						}

					}
					StringBuffer strBuff = new StringBuffer();
					if(count==0 || area.compareTo(new BigDecimal(0))==0)
					{
						strValue = null;
					}else
					{
						BigDecimal tenArea = area.divide(new BigDecimal(1), 2,BigDecimal.ROUND_HALF_UP);
						strBuff.append(count).append(TenancyReportParameter.REPORT_CELL_JOIN).append(tenArea).append(TenancyReportParameter.REPORT_CELL_UNIT);
						strValue = strBuff.toString();
					}
					if(aimCost==null || aimCost.equals(FDCHelper.ZERO))
					{
						row.getCell(j).setValue(null);
					}else
					{
//						if("avgTenRate".equals(this.tblMain.getColumn(j).getKey()))
//						{
//							Object ob = row.getCell(0).getUserObject();
//							aimCost = this.getAimCost(ob, aimCost);
//							row.getCell(j).setValue(aimCost);
//						}else
//						{
							row.getCell(j).setValue(aimCost);
//						}

					}
					if("dayCount".equals(this.tblMain.getColumn(j).getKey()) 
							|| "weekCount".equals(this.tblMain.getColumn(j).getKey())
							|| "monthCount".equals(this.tblMain.getColumn(j).getKey())
							|| "seasonCount".equals(this.tblMain.getColumn(j).getKey())
							|| "yearCount".equals(this.tblMain.getColumn(j).getKey())
							|| "tenancyCount".equals(this.tblMain.getColumn(j).getKey())
							|| "termNewCount".equals(this.tblMain.getColumn(j).getKey())
							|| "termContinueCount".equals(this.tblMain.getColumn(j).getKey())
							|| "termExpandCount".equals(this.tblMain.getColumn(j).getKey())){
						row.getCell(j).setValue(strValue);
					}
				}
			}
		}
	}

	private BigDecimal getAimCost(Object ob,BigDecimal aimCost)
	{
		if(ob instanceof SubareaInfo)
		{
			SubareaInfo subInfo = (SubareaInfo)ob;
			//SellProjectInfo project = subInfo.getSellProject();
			String sql = " select room.fbuildingID buildingID,sum(fbuildingArea) buildingArea from t_she_room room "+
			" left join t_she_building build on build.fid = room.fbuildingid "+
			" left join t_she_subarea subarea on build.FSubareaID = subarea.fid "+
			//" left join t_she_sellproject project on build.fsellprojectid = project.fid "+
			" where room.FIsForTen=1 and subarea.fid = '"+subInfo.getId().toString()+ "'"+
			" group by fbuildingID ";
			return this.getRow(sql, aimCost);
		}
		else if(ob instanceof SellProjectInfo)
		{
			SellProjectInfo project = (SellProjectInfo)ob;
			String sql = " select room.fbuildingID buildingID,sum(fbuildingArea) buildingArea from t_she_room room "+
			" left join t_she_building build on build.fid = room.fbuildingid "+
			" left join t_she_sellproject project on build.fsellprojectid = project.fid "+
			" where room.FIsForTen=1 and project.fid = '"+project.getId().toString()+ "'"+
			" group by fbuildingID ";
			return this.getRow(sql, aimCost);
		}else if(ob instanceof OrgStructureInfo)
		{
			OrgStructureInfo info = (OrgStructureInfo)ob;
			FullOrgUnitInfo orgInfo = info.getUnit();
//			String sql = "select room.fbuildingID buildingID,project.fid projectid,sum(fbuildingArea) buildingArea from t_she_room room "+ 
//			" left join t_she_building build on build.fid = room.fbuildingid "+
//			" left join t_she_sellproject project on build.fsellprojectid = project.fid "+
//			" left join t_she_shareorgunit unit on unit.fheadid = project.fid "+
//			" where room.FIsForTen=1  and project.FOrgUnitID='"+orgInfo.getId().toString() +  "' " +
//			" or unit.FOrgUnitID='"+orgInfo.getId().toString()+"' "+
//			" group by fbuildingID,project.fid ";
//			return this.getRow(sql, aimCost);
			//这里主要是因为还有共享的项目也计算到该组织下项目的总面积，但是上面的SQL在一个项目共享给很多不同的组织的
			//时候把面积也计算了很多次，所以我在这把组织下的项目和共享的项目面积分别计算，最后相加
			IRowSet areaSet = null;
			List buildingSet = new ArrayList();
			BigDecimal allArea = new BigDecimal(0);
			BigDecimal oldTenRate  = new BigDecimal(0);
			String sql = "select room.fbuildingID buildingID,project.fid projectid,sum(fbuildingArea) buildingArea from t_she_room room "+ 
			" left join t_she_building build on build.fid = room.fbuildingid "+
			" left join t_she_sellproject project on build.fsellprojectid = project.fid "+
			" where room.FIsForTen=1  and project.FOrgUnitID='"+orgInfo.getId().toString() +  "' " +
			" group by fbuildingID,project.fid ";			
			try {
				areaSet = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
				while(areaSet.next())
				{
					BigDecimal buildingArea = areaSet.getBigDecimal("buildingArea");
					if(buildingArea==null)
					{
						buildingArea = new BigDecimal(0);
					}
					String buildingID = areaSet.getString("buildingID");
					allArea = allArea.add(buildingArea);
					buildingSet.add(buildingID);
				}
				String againSql = "select room.fbuildingID buildingID,project.fid projectid,sum(fbuildingArea) buildingArea from t_she_room room "+ 
				" left join t_she_building build on build.fid = room.fbuildingid "+
				" left join t_she_sellproject project on build.fsellprojectid = project.fid "+
				" left join t_she_shareorgunit unit on unit.fheadid = project.fid "+
				" where room.FIsForTen=1  and unit.FOrgUnitID='"+orgInfo.getId().toString()+"' "+
				" group by fbuildingID,project.fid ";
				try {
					areaSet = SQLExecutorFactory.getRemoteInstance(againSql).executeSQL();
					while(areaSet.next())
					{
						BigDecimal buildingArea = areaSet.getBigDecimal("buildingArea");
						if(buildingArea==null)
						{
							buildingArea = new BigDecimal(0);
						}
						String buildingID = areaSet.getString("buildingID");
						allArea = allArea.add(buildingArea);
						buildingSet.add(buildingID);
					}
				} catch (BOSException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				Date beginDate = DateTimeUtils.truncateDate(this.getBeginQueryDate());
				Date endDate = DateTimeUtils.truncateDate(this.getEndQueryDate());
				beginDate = FDCDateHelper.getDayBegin(beginDate);
				endDate = FDCDateHelper.getDayBegin(endDate);
				String tenRateSql = "select building.fid buildingID,room.fbuildingArea buildingArea,ten.fstartDate startDate,ten.fenddate endDate " +
				" from T_TEN_TenancyRoomEntry ten "+
				" left join t_she_room room on ten.FRoomID = room.fid "+
				" left join t_she_building building on room.FBuildingID = building.fid "+
				" where ten.fStartDate is not null and ten.fendDate is not null ";
				IRowSet tenRateSet = SQLExecutorFactory.getRemoteInstance(tenRateSql).executeSQL();
				while(tenRateSet.next())
				{
					String buildingID = tenRateSet.getString("buildingID");
					if(buildingID==null)continue;
					BigDecimal buildingArea = tenRateSet.getBigDecimal("buildingArea");
					if(buildingArea==null)
					{
						buildingArea = new BigDecimal(0);
					}
					Date tenStratDate = tenRateSet.getDate("startDate");
					Date tenEndDate = tenRateSet.getDate("endDate");		
					tenStratDate = FDCDateHelper.getDayBegin(tenStratDate);
					tenEndDate = FDCDateHelper.getDayBegin(tenEndDate);
					BigDecimal tenRate = new BigDecimal(0);
					if(TenancyClientHelper.isInclude(buildingID, buildingSet))
					{
						Date end = this.minDate(endDate, tenEndDate);
						Date start = this.maxDate(beginDate,tenStratDate);
						if(!end.before(start))
						{
							int tenDays = TenancyHelper.getDistantDays(start, end);
							int allDays = TenancyHelper.getDistantDays(beginDate, endDate);
							tenRate = buildingArea.divide(allArea, 10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).multiply(new BigDecimal(tenDays)).divide(new BigDecimal(allDays), 10,BigDecimal.ROUND_HALF_UP);
							oldTenRate = oldTenRate.add(tenRate);
							aimCost = oldTenRate;
						}
					}
				}
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
			return aimCost;
		}else
		{
			return new BigDecimal(0);
		}
	}

	private BigDecimal getRow(String sql,BigDecimal aimCost)
	{
		IRowSet areaSet = null;
		List buildingSet = new ArrayList();
		BigDecimal allArea = new BigDecimal(0);
		BigDecimal oldTenRate  = new BigDecimal(0);
		try {
			areaSet = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
			while(areaSet.next())
			{
				BigDecimal buildingArea = areaSet.getBigDecimal("buildingArea");
				if(buildingArea==null)
				{
					buildingArea = new BigDecimal(0);
				}
				String buildingID = areaSet.getString("buildingID");
				allArea = allArea.add(buildingArea);
				buildingSet.add(buildingID);
			}
			Date beginDate = DateTimeUtils.truncateDate(this.getBeginQueryDate());
			Date endDate = DateTimeUtils.truncateDate(this.getEndQueryDate());
			beginDate = FDCDateHelper.getDayBegin(beginDate);
			endDate = FDCDateHelper.getDayBegin(endDate);
			String tenRateSql = "select building.fid buildingID,room.fbuildingArea buildingArea,ten.fstartDate startDate,ten.fenddate endDate " +
			" from T_TEN_TenancyRoomEntry ten "+
			" left join t_she_room room on ten.FRoomID = room.fid "+
			" left join t_she_building building on room.FBuildingID = building.fid "+
			" where ten.fstartDate is not null and ten.fendDate is not null";
			IRowSet tenRateSet = SQLExecutorFactory.getRemoteInstance(tenRateSql).executeSQL();
			while(tenRateSet.next())
			{
				String buildingID = tenRateSet.getString("buildingID");
				BigDecimal buildingArea = tenRateSet.getBigDecimal("buildingArea");
				if(buildingArea==null)
				{
					buildingArea = new BigDecimal(0);
				}
				Date tenStratDate = tenRateSet.getDate("startDate");
				Date tenEndDate = tenRateSet.getDate("endDate");		
				tenStratDate = FDCDateHelper.getDayBegin(tenStratDate);
				tenEndDate = FDCDateHelper.getDayBegin(tenEndDate);
				BigDecimal tenRate = new BigDecimal(0);
				if(TenancyClientHelper.isInclude(buildingID, buildingSet))
				{
					Date end = this.minDate(endDate, tenEndDate);
					Date start = this.maxDate(beginDate,tenStratDate);
					if(!end.before(start))
					{
						int tenDays = TenancyHelper.getDistantDays(start, end);
						int allDays = TenancyHelper.getDistantDays(beginDate, endDate);
						tenRate = buildingArea.divide(allArea, 10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).multiply(new BigDecimal(tenDays)).divide(new BigDecimal(allDays), 10,BigDecimal.ROUND_HALF_UP);
						oldTenRate = oldTenRate.add(tenRate);
						aimCost = oldTenRate;
					}
				}	
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aimCost;
	}

	private Object[] getCellStr(Map map,int tenCount,BigDecimal tenArea)
	{
		int count = ((Integer)map.get("套数")).intValue();
		tenCount = tenCount+count;
		BigDecimal area = (BigDecimal)map.get("面积");
		tenArea = tenArea.add(area);
		//StringBuffer s = new StringBuffer();
		//s.append(tenCount).append("/").append(tenArea.divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP)).append("m2");
		return new Object[]{new Integer(tenCount),tenArea};
	}

	private String getCellValue(String value,int count,BigDecimal area)
	{
		String[] str = value.split("/");
		int cellCount = new Integer(str[0]).intValue();
		BigDecimal cellArea = new BigDecimal(str[1].substring(0, str[1].length()-2));
		count+=cellCount;
		area = area.add(cellArea);
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(count).append("/").append(area).append("m2");
		String strValue = strBuf.toString();
		return strValue;
	}

	//把过滤条件传过来拼成sql
	public void appendFilterSql(FDCSQLBuilder builder, String proName) {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
				.getCustomerParams());
		if (para.isNotNull("isShowAll") && !para.getBoolean("isShowAll")) {		
			Date beginDate = this.getBeginQueryDate();
			if (beginDate != null) {
				builder.appendSql(" and " + proName + " >= ? ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
			}
			Date endDate = this.getEndQueryDate();
			if (endDate != null) {
				builder.appendSql(" and " + proName + " < ? ");
				builder.addParam(FDCDateHelper.getNextDay(endDate));
			}
		}

	}

	public void appendFilterSql2(FDCSQLBuilder builder, String start,String end) {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
				.getCustomerParams());
		if (para.isNotNull("isShowAll") && !para.getBoolean("isShowAll")) {		
			Date beginDate = this.getBeginQueryDate();
			if (beginDate != null) {
				builder.appendSql(" where " + start + " >= ? ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
			}
			Date endDate = this.getEndQueryDate();
			if (endDate != null) {
				builder.appendSql(" and " + end + " < ? ");
				builder.addParam(FDCDateHelper.getNextDay(endDate));
			}
		}

	}

	//项目树
	private void fillProjectAndBuilding() throws BOSException, EASBizException,
	SQLException {
		tblMain.removeRows();
		TreeModel buildingTree = null;
		try {
			buildingTree = SHEHelper.getBuildingTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys);
		} catch (Exception e) {
			logger.error(e.getMessage());
			this.handleException(e);
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) buildingTree
		.getRoot();
		tblMain.getTreeColumn().setDepth(root.getDepth() + 1);
		fillNode(tblMain, (DefaultMutableTreeNode) root);
	}

	//以项目树的方式生成名称
	private void fillNode(KDTable table, DefaultMutableTreeNode node)
	throws BOSException, SQLException, EASBizException {
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel());
		String space = "";
		for (int i = 0; i < node.getLevel(); i++) {
			space += " ";
		}
		row.getCell(0).setUserObject(node.getUserObject());
		if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			row.getCell("name").setValue(space + building.getName());
			row.setUserObject(node.getUserObject());
			this.rowMap.put(building.getId().toString(), row);
		} else {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			row.getCell("name").setValue(space + node);
		}
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
		}
	}

	//单击进入出租明细表
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			IRow row = this.tblMain.getRow(rowIndex);
			if (row != null) {
				BuildingInfo building = (BuildingInfo) row.getUserObject();
				if (building != null) {
					TenancyStatListRptUI.showUI(this, building.getId().toString(),
							this.getBeginQueryDate(), this.getEndQueryDate(),
							this.getFilterUI().getCustomerParams());
				}
			}
		}
	}

	private Date minDate(Date endDate,Date tenEndDate)
	{
		if(endDate.compareTo(tenEndDate)==1)
		{
			return tenEndDate;
		}else if(endDate.compareTo(tenEndDate)==-1)
		{
			return endDate;
		}else
		{
			return endDate;
		}
	}

	private Date maxDate(Date startDate,Date tenStartDate)
	{
		if(startDate.compareTo(tenStartDate)==1)
		{
			return startDate;
		}else if(startDate.compareTo(tenStartDate)==1)
		{
			return tenStartDate;
		}else
		{
			return tenStartDate;
		}
	}

	//在list表中没设置id的情况下重载的方法
	protected void setActionState() {

	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

}