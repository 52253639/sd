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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;

/**
 * output class name
 */
public class TenancyStatRptUI extends AbstractTenancyStatRptUI
{
	private static final Logger logger = CoreUIObject.getLogger(TenancyStatRptUI.class);
	
	Map rowMap = new HashMap();
	
	private TenancyStatRptFilterUI filterUI = null;
	
	private CommonQueryDialog commonQueryDialog = null;
	
	public TenancyStatRptUI() throws Exception
	{
		super();
	}
	
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
				this.tblMain.getHeadRow(0).getCell(17).setValue("查询区间数据(全部显示)");
			}
			else if(beginDate == null && endDate != null)
			{
				Calendar cal = new GregorianCalendar();
				cal.setTime(endDate);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE));
				DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
				String des = "查询区间数据(" 	+ FORMAT_DAY.format(cal.getTime()) + "之前)";
				this.tblMain.getHeadRow(0).getCell(17).setValue(des);
			}
			else if(beginDate != null && endDate == null)
			{
				Calendar cal = new GregorianCalendar();
				cal.set(Calendar.DATE, cal.get(Calendar.DATE));
				DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
				String des = "查询区间数据(" + FORMAT_DAY.format(beginDate) + "之后)";
				this.tblMain.getHeadRow(0).getCell(17).setValue(des);
			}
			else
			{
				Calendar cal = new GregorianCalendar();
				cal.setTime(endDate);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE));
				DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
				String des = "查询区间数据(" + FORMAT_DAY.format(beginDate) + "到"
				+ FORMAT_DAY.format(cal.getTime()) + ")";
				this.tblMain.getHeadRow(0).getCell(17).setValue(des);
			}
		} else
		{
			this.tblMain.getHeadRow(0).getCell(17).setValue("查询区间数据(全部显示)");
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

		
		//从房间状态里找已租，放租，总套数和面积和区间的不同
		String tenCountSql = "select fbuildingid,FTenancyState,count(*) totalCount,sum(FBuildingArea) buildingArea  from t_she_room "+
		                     " group by fbuildingid,ftenancystate ";
		IRowSet countSet = null;
		try {
			countSet = SQLExecutorFactory.getRemoteInstance(tenCountSql).executeSQL();
			//未放租套数,面积
			Map UNTenancyMap = new HashMap();
			Map unAreaMap = new HashMap();
			//待租套数,面积
			Map WaitTenancyMap = new HashMap();
			Map waitAreaMap = new HashMap();
			//保留套数,面积
			Map KeepTenancyMap = new HashMap();
			Map keepAreaMap = new HashMap();
			//新租套数,面积
			Map newTenancyMap = new HashMap();
			Map newAreaMap = new HashMap();
			//续租套数,面积
			Map conTenaycyMap = new HashMap();
			Map conAreaMap = new HashMap();
			//扩租套数,面积
			Map enlargTenMap = new HashMap();
			Map enlargAreaMap = new HashMap();
			while(countSet.next())
			{
				String buildingID = countSet.getString("fbuildingid");
				String tenState = countSet.getString("FTenancyState");
				int totalCount = countSet.getInt("totalCount");
				BigDecimal buildingArea = countSet.getBigDecimal("buildingArea");
				if(buildingArea==null)
				{
					buildingArea = FDCHelper.ZERO;
				}
				if(newTenancyMap.get(buildingID)==null || conTenaycyMap.get(buildingID)==null || enlargTenMap.get(buildingID)==null
						|| UNTenancyMap.get(buildingID)==null || WaitTenancyMap.get(buildingID)==null || KeepTenancyMap.get(buildingID)==null) {
					if(tenState !=null && (TenancyStateEnum.NEWTENANCY_VALUE).equals(tenState))
					{
						newTenancyMap.put(buildingID,new Integer(totalCount));
						newAreaMap.put(buildingID,buildingArea);
					}
					else if(tenState !=null && (TenancyStateEnum.CONTINUETENANCY_VALUE).equals(tenState))
					{
						conTenaycyMap.put(buildingID,new Integer(totalCount));
						conAreaMap.put(buildingID,buildingArea);
					}else if(tenState !=null && (TenancyStateEnum.ENLARGETENANCY_VALUE).equals(tenState))
					{
						enlargTenMap.put(buildingID,new Integer(totalCount));
						enlargAreaMap.put(buildingID,buildingArea);
					}else if(tenState !=null && (TenancyStateEnum.UNTENANCY_VALUE).equals(tenState))
					{
						UNTenancyMap.put(buildingID,new Integer(totalCount));
						unAreaMap.put(buildingID,buildingArea);
					}else if(tenState !=null && (TenancyStateEnum.WAITTENANCY_VALUE).equals(tenState))
					{
						WaitTenancyMap.put(buildingID,new Integer(totalCount));
						waitAreaMap.put(buildingID,buildingArea);
					}else if(tenState !=null && (TenancyStateEnum.KEEPTENANCY_VALUE).equals(tenState))
					{
						KeepTenancyMap.put(buildingID,new Integer(totalCount));
						keepAreaMap.put(buildingID,buildingArea);
					}
				}
				else
				{
					if(tenState!=null && (TenancyStateEnum.NEWTENANCY_VALUE).equals(tenState))
					{
						int newCount = ((Integer)newTenancyMap.get(buildingID)).intValue();
						newTenancyMap.put(buildingID,new Integer(newCount+totalCount));
						BigDecimal area = (BigDecimal)newAreaMap.get(buildingID);
						newAreaMap.put(buildingID,area.add(buildingArea));
					}else if(tenState !=null && (TenancyStateEnum.CONTINUETENANCY_VALUE).equals(tenState))
					{
						int continueCount = ((Integer)conTenaycyMap.get(buildingID)).intValue();
						conTenaycyMap.put(buildingID,new Integer(continueCount+totalCount));
						BigDecimal area = (BigDecimal)conAreaMap.get(buildingID);
						conAreaMap.put(buildingID,area.add(buildingArea));
					}else if(tenState !=null && (TenancyStateEnum.ENLARGETENANCY_VALUE).equals(tenState))
					{
						int enlargCount = ((Integer)enlargTenMap.get(buildingID)).intValue();
						enlargTenMap.put(buildingID,new Integer(enlargCount+totalCount));
						BigDecimal area = (BigDecimal)enlargAreaMap.get(buildingID);
						enlargAreaMap.put(buildingID,area.add(buildingArea));
					}else if(tenState !=null && (TenancyStateEnum.UNTENANCY_VALUE).equals(tenState))
					{
						int unTenCount = ((Integer)UNTenancyMap.get(buildingID)).intValue();
						UNTenancyMap.put(buildingID,new Integer(unTenCount+totalCount));
						BigDecimal area = (BigDecimal)unAreaMap.get(buildingID);
						unAreaMap.put(buildingID,area.add(buildingArea));
					}else if(tenState !=null && (TenancyStateEnum.WAITTENANCY_VALUE).equals(tenState))
					{
						int waitTenCount = ((Integer)WaitTenancyMap.get(buildingID)).intValue();
						WaitTenancyMap.put(buildingID,new Integer(waitTenCount+totalCount));
						BigDecimal area = (BigDecimal)waitAreaMap.get(buildingID);
						waitAreaMap.put(buildingID,area.add(buildingArea));
					}else if(tenState !=null && (TenancyStateEnum.KEEPTENANCY_VALUE).equals(tenState))
					{
						int keepTenCount = ((Integer)KeepTenancyMap.get(buildingID)).intValue();
						KeepTenancyMap.put(buildingID,new Integer(keepTenCount+totalCount));
						BigDecimal area = (BigDecimal)keepAreaMap.get(buildingID);
						keepAreaMap.put(buildingID,area.add(buildingArea));
					}
				}
				
			}
			Set keySet = rowMap.keySet();
			Iterator iterator = keySet.iterator();
			while(iterator.hasNext()) {
				String buidIdStr = (String)iterator.next();
				IRow thisRow = (IRow)rowMap.get(buidIdStr);
				//新租套数,面积
				Integer newTenCount = (Integer)newTenancyMap.get(buidIdStr);
				BigDecimal newArea = (BigDecimal)newAreaMap.get(buidIdStr);
				//扩租套数,面积
				Integer expandTenCount = (Integer)enlargTenMap.get(buidIdStr);
				BigDecimal enlargArea = (BigDecimal)enlargAreaMap.get(buidIdStr);
				//续租套数,面积
				Integer continueCount = (Integer)conTenaycyMap.get(buidIdStr);
				BigDecimal conArea = (BigDecimal)conAreaMap.get(buidIdStr);
				//未放租套数,面积
				Integer unTenCount = (Integer)UNTenancyMap.get(buidIdStr);
				BigDecimal unArea = (BigDecimal)unAreaMap.get(buidIdStr);
				//待租套数,面积
				Integer waitTenCount = (Integer)WaitTenancyMap.get(buidIdStr);
				BigDecimal waitArea = (BigDecimal)waitAreaMap.get(buidIdStr);
				//保留套数,面积
				Integer keepTenCount = (Integer)KeepTenancyMap.get(buidIdStr);
				BigDecimal keepArea = (BigDecimal)keepAreaMap.get(buidIdStr);
				if(newTenCount ==null)
				{
					newTenCount = new Integer(0);
				}if(expandTenCount ==null)
				{
					expandTenCount = new Integer(0);
				}
				if(continueCount ==null)
				{
					continueCount = new Integer(0);
				}if(unTenCount ==null)
				{
					unTenCount = new Integer(0);
				}
				if(waitTenCount ==null)
				{
					waitTenCount = new Integer(0);
				}if(keepTenCount ==null)
				{
					keepTenCount = new Integer(0);
				}if(newArea == null)
				{
					newArea = new BigDecimal(0);
				}if(enlargArea == null)
				{
					enlargArea = new BigDecimal(0);
				}
				if(conArea == null)
				{
					conArea = new BigDecimal(0);
				}
				if(unArea == null)
				{
					unArea = new BigDecimal(0);
				}
				if(waitArea == null)
				{
					waitArea = new BigDecimal(0);
				}if(keepArea == null)
				{
					keepArea = new BigDecimal(0);
				}
				//总套数
				int allCount = newTenCount.intValue()+expandTenCount.intValue()+continueCount.intValue()
				               +unTenCount.intValue()+waitTenCount.intValue()+keepTenCount.intValue();
				Integer tenSumCount = new Integer(allCount);
				//总面积
				BigDecimal allArea = newArea.add(enlargArea).add(conArea).add(unArea).add(waitArea).add(keepArea);
				//放租套数
				int tenCount = newTenCount.intValue()+expandTenCount.intValue()+continueCount.intValue()
				               +waitTenCount.intValue()+keepTenCount.intValue();
				Integer tenCount2 = new Integer(tenCount);
				//放租面积
				BigDecimal tenArea = newArea.add(enlargArea).add(conArea).add(waitArea).add(keepArea);
				//已租套数
				int alreadyCount = newTenCount.intValue()+expandTenCount.intValue()+continueCount.intValue();
				//已租面积
				BigDecimal alreadyArea = newArea.add(enlargArea).add(conArea);
				//空置套数
				int nullCount = tenCount-alreadyCount;
				//空置面积
				BigDecimal nullArea = tenArea.subtract(alreadyArea);
				//空置率
				if(tenCount==0)
				{
					tenCount2 = null;
					thisRow.getCell("nullRate").setValue(null);
				}else
				{
					BigDecimal nullRate = new BigDecimal(nullCount).divide(new BigDecimal(tenCount),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
					thisRow.getCell("nullRate").setValue(nullRate.toString());
				}		
				if(allArea.equals(FDCHelper.ZERO))
				{
					allArea = null;
				}if(tenArea.equals(FDCHelper.ZERO))
				{
					tenArea = null;
				}if(alreadyArea.equals(FDCHelper.ZERO))
				{
					alreadyArea = null;
				}if(nullArea.equals(FDCHelper.ZERO))
				{
					nullArea = null;
				}if(newTenCount.equals(new Integer(0)))
				{
					newTenCount = null;
				}if(expandTenCount.equals(new Integer(0)))
				{
					expandTenCount = null;
				}
				if(continueCount.equals(new Integer(0)))
				{
					continueCount = null;
				}if(unTenCount.equals(new Integer(0)))
				{
					unTenCount = null;
				}
				if(waitTenCount.equals(new Integer(0)))
				{
					waitTenCount = null;
				}if(keepTenCount.equals(new Integer(0)))
				{
					keepTenCount = null;
				}if(tenSumCount.equals(new Integer(0)))
				{
					tenSumCount = null;
				}
				thisRow.getCell("tenSumCount").setValue(tenSumCount);
				thisRow.getCell("tenCount").setValue(tenCount2);
				thisRow.getCell("alreadyTenSumCount").setValue(new Integer(alreadyCount));
				thisRow.getCell("nullCount").setValue(new Integer(nullCount));
				
				thisRow.getCell("newTenCount").setValue(newTenCount);
				thisRow.getCell("expandTenCount").setValue(expandTenCount);
				thisRow.getCell("continueCount").setValue(continueCount);
				
				thisRow.getCell("sumArea").setValue(allArea);
				thisRow.getCell("tenSumArea").setValue(tenArea);
				thisRow.getCell("alreadyArea").setValue(alreadyArea);
				thisRow.getCell("nullArea").setValue(nullArea);
			}
		}catch(BOSException e)
		{
			logger.error(e.getMessage());
			handleException(e);
		}catch(SQLException e)
		{
			logger.error(e.getMessage());
			handleException(e);
		}
		
		//累计收租(从收款单)
		String allReceiveSql = "select FBuildingId,sum(cas.famount) sumRevAmount from t_cas_receivingbill cas "+ 
		" left join t_she_fdcreceivebill fdc on cas.FFDCReceivebillid=fdc.fid "+ 
		" left join t_she_room room on fdc.froomid=room.fid "+
		" left join t_she_building building on  room.fbuildingid=building.fid "+ 
		" left join T_TEN_TenancyBill as tenBill on room.FLastTenancyID= tenBill.fid "+ 
		" left join t_she_moneyDefine money on  fdc.FMoneyDefineId=money.fid "+ 
		" where money.FMoneyType ='RentAmount' "+
		" group by FBuildingId"; 
		IRowSet allReceiveSet = null;
		allReceiveSet = SQLExecutorFactory.getRemoteInstance(allReceiveSql).executeSQL();
		while(allReceiveSet.next())
		{
			String buildingId = allReceiveSet.getString("FBuildingId");
			IRow row = (IRow) this.rowMap.get(buildingId);
			if (row == null) {
				continue;
			}
			BigDecimal sumRevAmount = allReceiveSet.getBigDecimal("sumRevAmount");
			row.getCell("tenSumAmount").setValue(sumRevAmount);
		}
		
		//区间新租，续租，扩组套数(从合同房间分录里找，并且实际交房日期不能为空)
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
		.appendSql("select building.fid buildingID,FTenRoomState FTenancyState,count(*) totalCount from T_TEN_TenancyRoomEntry ten "+
				   " left join t_she_room room on ten.FRoomID = room.fid "+
		           " left join t_she_building building on room.FBuildingID = building.fid ");
		builder.appendSql(" where FActDeliveryRoomDate is not null ");
		this.appendFilterSql(builder, "FActDeliveryRoomDate");
		builder.appendSql("group by building.fid,FTenRoomState ");
		IRowSet termTenSet = null;
		try {
			termTenSet = builder.executeQuery();
			termTenSet.size();
			Map newTenancyMap = new HashMap();
			Map conTenaycyMap = new HashMap();
			Map enlargTenMap = new HashMap();
			while (termTenSet.next()) {
				String buildingID = termTenSet.getString("buildingID");
				String tenState = termTenSet.getString("FTenancyState");
				int totalCount = termTenSet.getInt("totalCount");
				if(newTenancyMap.get(buildingID)==null || conTenaycyMap.get(buildingID)==null ||enlargTenMap.get(buildingID)==null) {
					if(tenState !=null && (TenancyStateEnum.NEWTENANCY_VALUE).equals(tenState))
					{
						newTenancyMap.put(buildingID,new Integer(totalCount));
					}
					else if(tenState !=null && (TenancyStateEnum.CONTINUETENANCY_VALUE).equals(tenState))
					{
						conTenaycyMap.put(buildingID,new Integer(totalCount));
					}else if(tenState !=null && (TenancyStateEnum.ENLARGETENANCY_VALUE).equals(tenState))
					{
						enlargTenMap.put(buildingID,new Integer(totalCount));
					}
				}
				else
				{
					if(tenState!=null && (TenancyStateEnum.NEWTENANCY_VALUE).equals(tenState))
					{
						int newCount = ((Integer)newTenancyMap.get(buildingID)).intValue();
						newTenancyMap.put(buildingID,new Integer(newCount+totalCount));
					}else if(tenState !=null && (TenancyStateEnum.CONTINUETENANCY_VALUE).equals(tenState))
					{
						int continueCount = ((Integer)conTenaycyMap.get(buildingID)).intValue();
						conTenaycyMap.put(buildingID,new Integer(continueCount+totalCount));
					}else if(tenState !=null && (TenancyStateEnum.ENLARGETENANCY_VALUE).equals(tenState))
					{
						int enlargCount = ((Integer)enlargTenMap.get(buildingID)).intValue();
						enlargTenMap.put(buildingID,new Integer(enlargCount+totalCount));
					}
				}
			}
			Set keySet = rowMap.keySet();
			Iterator iterator = keySet.iterator();
			while(iterator.hasNext()) {
				String buidIdStr = (String)iterator.next();
				IRow thisRow = (IRow)rowMap.get(buidIdStr);
				thisRow.getCell("termNewCount").setValue(newTenancyMap.get(buidIdStr));
				thisRow.getCell("termExpandCount").setValue(enlargTenMap.get(buidIdStr));
				thisRow.getCell("termContinueCount").setValue(conTenaycyMap.get(buidIdStr));
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
		setUnionData();
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
					int allTenCount = 0;
					int allNullCount = 0;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(j).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								aimCost = aimCost.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								aimCost = aimCost.add(new BigDecimal(
										((Integer) value).toString()));
							//把空置率存为字符型，汇总的时候不像其他的汇总相加，而是求出汇总的放租套数和汇总的空置套数求总的空置率
							} else if(value instanceof String)
							{
								int tenCount = ((Integer)rowAdd.getCell("tenCount").getValue()).intValue();
								int nullCount = ((Integer)rowAdd.getCell("nullCount").getValue()).intValue();
								allTenCount+=tenCount;
								allNullCount+=nullCount;
								BigDecimal nullRate = new BigDecimal(allNullCount).divide(new BigDecimal(allTenCount),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
								aimCost = nullRate;
							}
						}
					}
					if(aimCost==null ||aimCost.equals(FDCHelper.ZERO))
					{
						row.getCell(j).setValue(null);
					}else
					{
						row.getCell(j).setValue(aimCost);
					}
					
				}
			}
		}
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
	
	private void initTable() {
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		for (int i = 1; i < this.tblMain.getColumnCount(); i++) {
			tblMain.getColumn(i).getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
		}
		tblMain.getColumn("tenSumCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("tenCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("newTenCount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(0));
		tblMain.getColumn("expandTenCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("continueCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("alreadyTenSumCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("nullCount").getStyleAttributes().setNumberFormat(
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
	
	//在list表中没设置id的情况下重载的方法
	protected void setActionState() {
		
	}
	
	public void storeFields()
	{
		super.storeFields();
	}
	
	protected String getEditUIName() {
		return null;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}
	
}