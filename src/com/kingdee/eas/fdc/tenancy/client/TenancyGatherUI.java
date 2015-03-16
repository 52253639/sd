/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyReportParameter;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class TenancyGatherUI extends AbstractTenancyGatherUI
{
	private static final Logger logger = CoreUIObject.getLogger(TenancyGatherUI.class);

	Map rowMap = new HashMap();

	public TenancyGatherUI() throws Exception
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
	
	protected CommonQueryDialog initCommonQueryDialog() 
	{
		CommonQueryDialog dialog = super.initCommonQueryDialog();
		try 
		{
			dialog.setShowFilter(false);
			dialog.setShowSorter(false);
		}
		catch (Exception e) 
		{
			handUIException(e);
		}
		return dialog;
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
		tblMain.getColumn("alreadySumCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("newTenCount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(0));
		tblMain.getColumn("expandTenCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("continueCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("nullCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));

		tblMain.getColumn("tenUnAuditCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("tenUnReceiveCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("tenUnRoomCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));

		tblMain.getColumn("sincerObliCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("holdCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("allNullCount").getStyleAttributes().setNumberFormat(
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


		Map allNullMap = this.setTenStateCountArea();
		this.setSumRevAmount();
		this.setNotAllCount(allNullMap);	
		setUnionData();
	}

	/**
	 * 查找房间各种出租状态的套数和面积,因为后面需要用到算出来的空置套数所以返回一个map
	 * */
	public Map setTenStateCountArea()
	{
		//从房间状态里找已租，放租，总套数和面积和区间的不同
		String tenCountSql = " select fbuildingid,FTenancyState,count(*) totalCount,sum(FBuildingArea) buildingArea  from t_she_room "+
		" group by fbuildingid,ftenancystate ";
		IRowSet countSet = null;
		Map allNullMap = new HashMap();
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
			//诚意预留套数
			Map sincerObliMap = new HashMap();
			Map sincerObliAreaMap = new HashMap();
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
				if(tenState !=null && (TenancyStateEnum.NEWTENANCY_VALUE).equals(tenState))
				{
					this.setCountArea(buildingID,totalCount, buildingArea, newTenancyMap, newAreaMap);
				}
				else if(tenState !=null && (TenancyStateEnum.CONTINUETENANCY_VALUE).equals(tenState))
				{
					this.setCountArea(buildingID,totalCount, buildingArea, conTenaycyMap, conAreaMap);
				}else if(tenState !=null && (TenancyStateEnum.ENLARGETENANCY_VALUE).equals(tenState))
				{
					this.setCountArea(buildingID,totalCount, buildingArea, enlargTenMap, enlargAreaMap);
				}else if(tenState !=null && (TenancyStateEnum.UNTENANCY_VALUE).equals(tenState))
				{
					this.setCountArea(buildingID,totalCount, buildingArea, UNTenancyMap, unAreaMap);
				}else if(tenState !=null && (TenancyStateEnum.WAITTENANCY_VALUE).equals(tenState))
				{
					this.setCountArea(buildingID,totalCount, buildingArea, WaitTenancyMap, waitAreaMap);
				}else if(tenState !=null && (TenancyStateEnum.KEEPTENANCY_VALUE).equals(tenState))
				{
					this.setCountArea(buildingID,totalCount, buildingArea, KeepTenancyMap, keepAreaMap);
				}else if(tenState !=null && TenancyStateEnum.SINCEROBLIGATE_VALUE.equals(tenState))
				{
					this.setCountArea(buildingID,totalCount, buildingArea, sincerObliMap, sincerObliAreaMap);
				}
			}
			Set keySet = rowMap.keySet();
			Iterator iterator = keySet.iterator();
			while(iterator.hasNext()) {
				Map allCountCellMap = new HashMap();
				Map tenancyCountCellMap = new HashMap();
				Map alreadyCountCellMap = new HashMap();
				Map nullCountCellMap = new HashMap();
				Map newTenCellMap = new HashMap();
				Map expandCellMap = new HashMap();
				Map continueCellMap = new HashMap();				
				//Map 
				String buidIdStr = (String)iterator.next();
				IRow thisRow = (IRow)rowMap.get(buidIdStr);
				//新租套数,面积
				Integer newTenCount = newTenancyMap.get(buidIdStr)==null?new Integer(0):(Integer)newTenancyMap.get(buidIdStr);
				BigDecimal newArea = newAreaMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)newAreaMap.get(buidIdStr);
				//扩租套数,面积
				Integer expandTenCount = enlargTenMap.get(buidIdStr)==null?new Integer(0):(Integer)enlargTenMap.get(buidIdStr);
				BigDecimal enlargArea = enlargAreaMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)enlargAreaMap.get(buidIdStr);
				//续租套数,面积
				Integer continueCount = conTenaycyMap.get(buidIdStr)==null?new Integer(0):(Integer)conTenaycyMap.get(buidIdStr);
				BigDecimal conArea = conAreaMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)conAreaMap.get(buidIdStr);
				//未放租套数,面积
				Integer unTenCount = UNTenancyMap.get(buidIdStr)==null?new Integer(0):(Integer)UNTenancyMap.get(buidIdStr);
				BigDecimal unArea = unAreaMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)unAreaMap.get(buidIdStr);
				//待租套数,面积
				Integer waitTenCount = WaitTenancyMap.get(buidIdStr)==null?new Integer(0):(Integer)WaitTenancyMap.get(buidIdStr);
				BigDecimal waitArea = waitAreaMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)waitAreaMap.get(buidIdStr);
				//保留套数,面积
				Integer keepTenCount = KeepTenancyMap.get(buidIdStr)==null?new Integer(0):(Integer)KeepTenancyMap.get(buidIdStr);
				BigDecimal keepArea = keepAreaMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)keepAreaMap.get(buidIdStr);
				//预留套数
				Integer sincerObliCount = sincerObliMap.get(buidIdStr)==null?new Integer(0):(Integer)sincerObliMap.get(buidIdStr);
				BigDecimal sincerArea = sincerObliAreaMap.get(buidIdStr)==null?new BigDecimal(0):(BigDecimal)sincerObliAreaMap.get(buidIdStr);

				//总套数
				int allCount = newTenCount.intValue()+expandTenCount.intValue()+continueCount.intValue()
				+unTenCount.intValue()+waitTenCount.intValue()+keepTenCount.intValue()+sincerObliCount.intValue();
				//总面积
				BigDecimal allArea = newArea.add(enlargArea).add(conArea).add(unArea).add(waitArea).add(keepArea).add(sincerArea);
				//放租套数
				int tenCount = newTenCount.intValue()+expandTenCount.intValue()+continueCount.intValue()
				+waitTenCount.intValue()+keepTenCount.intValue()+sincerObliCount.intValue();
				//放租面积
				BigDecimal tenArea = newArea.add(enlargArea).add(conArea).add(waitArea).add(keepArea);
				//已租套数
				int alreadyCount = newTenCount.intValue()+expandTenCount.intValue()+continueCount.intValue();
				//已租面积
				BigDecimal alreadyArea = newArea.add(enlargArea).add(conArea);
				//空置套数
				int nullCount = tenCount-alreadyCount;
				//纯空置套数
				int allNullCount = nullCount-keepTenCount.intValue()-sincerObliCount.intValue();
				allNullMap.put(buidIdStr, new Integer(allNullCount));
				//空置面积
				BigDecimal nullArea = tenArea.subtract(alreadyArea);
				//空置率
				if(tenCount==0)
				{
					thisRow.getCell("nullRate").setValue(null);
				}else
				{
					BigDecimal nullRate = new BigDecimal(nullCount).divide(new BigDecimal(tenCount),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
					thisRow.getCell("nullRate").setValue(nullRate.toString());
				}
				allArea = allArea.equals(FDCHelper.ZERO)?null:allArea;
				tenArea = tenArea.equals(FDCHelper.ZERO)?null:tenArea;
				alreadyArea = alreadyArea.equals(FDCHelper.ZERO)?null:alreadyArea;
				nullArea = nullArea.equals(FDCHelper.ZERO)?null:nullArea;
				newArea = newArea.equals(FDCHelper.ZERO)?null:newArea;
				enlargArea = enlargArea.equals(FDCHelper.ZERO)?null:enlargArea;
				conArea = conArea.equals(FDCHelper.ZERO)?null:conArea;

				String newTenStr = TenancyHelper.setCellValue(newTenCount, newArea,newTenCellMap);
				String expandTenStr = TenancyHelper.setCellValue(expandTenCount, enlargArea,expandCellMap);
				String continueTenStr = TenancyHelper.setCellValue(continueCount, conArea,continueCellMap);
				String allCountStr = TenancyHelper.setCellValue(new Integer(allCount), allArea,allCountCellMap);				
				String tenCountStr = TenancyHelper.setCellValue(new Integer(tenCount), tenArea,tenancyCountCellMap);
				String alreadyStr = TenancyHelper.setCellValue(new Integer(alreadyCount), alreadyArea,alreadyCountCellMap);
				String nullCountStr = TenancyHelper.setCellValue(new Integer(nullCount), nullArea,nullCountCellMap);

				thisRow.getCell("tenSumCount").setValue(allCountStr);
				thisRow.getCell("tenCount").setValue(tenCountStr);
				thisRow.getCell("alreadySumCount").setValue(alreadyStr);
				thisRow.getCell("nullCount").setValue(nullCountStr);
				thisRow.getCell("tenSumCount").setUserObject(allCountCellMap);
				thisRow.getCell("tenCount").setUserObject(tenancyCountCellMap);
				thisRow.getCell("alreadySumCount").setUserObject(alreadyCountCellMap);
				thisRow.getCell("nullCount").setUserObject(nullCountCellMap);

				thisRow.getCell("newTenCount").setValue(newTenStr);
				thisRow.getCell("newTenCount").setUserObject(newTenCellMap);
				thisRow.getCell("expandTenCount").setValue(expandTenStr);
				thisRow.getCell("expandTenCount").setUserObject(expandCellMap);
				thisRow.getCell("continueCount").setValue(continueTenStr);
				thisRow.getCell("continueCount").setUserObject(continueCellMap);
				thisRow.getCell("sincerObliCount").setValue(sincerObliMap.get(buidIdStr)==null?null:sincerObliMap.get(buidIdStr));
				thisRow.getCell("holdCount").setValue(KeepTenancyMap.get(buidIdStr)==null?null:KeepTenancyMap.get(buidIdStr));
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
		return allNullMap;
	}

	/**
	 * 设置累计收租额
	 * @throws BOSException,SQLException
	 * */
	public void setSumRevAmount() throws BOSException, SQLException
	{
		//累计收租(从收款单)
		String allReceiveSql = " select FBuildingId,sum(fdcEntry.famount) sumRevAmount from t_she_fdcreceivebillentry fdcEntry "+
		" left join t_cas_receivingbill cas on cas.fid=fdcentry.FReceivingBillID "+ 
		" left join T_SHE_FDCReceiveBill fdc on cas.FFDCReceivebillid=fdc.fid "+
		" left join t_she_room room on fdc.froomid=room.fid "+
		" left join t_she_building building on  room.fbuildingid=building.fid "+ 
		" left join T_TEN_TenancyBill as tenBill on room.FLastTenancyID= tenBill.fid "+ 
		" left join t_she_moneyDefine money on fdcentry.FMoneyDefineId=money.fid "+ 
		" where money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount' and money.FSysType ='TenancySys' "+
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
	}

	/**
	 * 设置认租未审批，认租未交房，认租未收款
	 * @throws BOSException,SQLException
	 * */
	public void setNotAllCount(Map allNullMap)throws BOSException, SQLException
	{
		//认租未审批，审批未收款和认租未交房
		String tenancySql = "select build.fid buildingID,tenBill.FTenancyState tenancyState, count(*) totalCount from T_TEN_TenancyRoomEntry ten "+
		" left join t_she_room room on room.fid = ten.froomid "+
		" left join t_she_building build on build.fid = room.fbuildingid "+
		" left join t_ten_tenancyBill tenBill on tenBill.fid = ten.FTenancyID "+
		" group by build.fid,tenBill.FTenancyState ";
		IRowSet tenancySet = null;
		Map submitMap = new HashMap();
		Map auditMap = new HashMap();
		Map noJoinMap = new HashMap();
		tenancySet = SQLExecutorFactory.getRemoteInstance(tenancySql).executeSQL();
		while(tenancySet.next())
		{
			String buildingID = tenancySet.getString("buildingID");
			String tenancyState = tenancySet.getString("tenancyState");
			int totalCount  = tenancySet.getInt("totalCount");
			if(tenancyState!=null && TenancyBillStateEnum.SUBMITTED_VALUE.equals(tenancyState))
			{
				submitMap.put(buildingID, new Integer(totalCount));
			}else if(tenancyState!=null && TenancyBillStateEnum.AUDITED_VALUE.equals(tenancyState))
			{
				auditMap.put(buildingID, new Integer(totalCount));
			}else if(tenancyState!=null && (/*TenancyBillStateEnum.DEPOSITREVED_VALUE.equals(tenancyState)||*/TenancyBillStateEnum.PARTEXECUTED_VALUE.equals(tenancyState)))
			{
				noJoinMap.put(buildingID, new Integer(totalCount));
			}
		}
		Set keySet = rowMap.keySet();
		Iterator iterator = keySet.iterator();
		while(iterator.hasNext()) {
			String buidIdStr = (String)iterator.next();
			IRow thisRow = (IRow)rowMap.get(buidIdStr);
			Integer submitCount = submitMap.get(buidIdStr)==null?new Integer(0):((Integer)submitMap.get(buidIdStr));
			Integer auditCount = auditMap.get(buidIdStr)==null?new Integer(0):((Integer)auditMap.get(buidIdStr));
			Integer nojoinCount = noJoinMap.get(buidIdStr)==null?new Integer(0):((Integer)noJoinMap.get(buidIdStr));
			Integer submitCount2 = submitCount.equals(new Integer(0))?null:submitCount;
			Integer auditCount2 = auditCount.equals(new Integer(0))?null:auditCount;
			Integer nojoinCount2 = nojoinCount.equals(new Integer(0))?null:nojoinCount;
			thisRow.getCell("tenUnAuditCount").setValue(submitCount2);
			thisRow.getCell("tenUnReceiveCount").setValue(auditCount2);
			thisRow.getCell("tenUnRoomCount").setValue(nojoinCount2);

			Integer allNullCount2 = allNullMap.get(buidIdStr)==null?new Integer(0):((Integer)allNullMap.get(buidIdStr));
			int allNullCount3 = allNullCount2.intValue()-submitCount.intValue()-auditCount.intValue()-nojoinCount.intValue();
			Integer allNullCount4 = new Integer(allNullCount3).equals(new Integer(0))?null:new Integer(allNullCount3);
			thisRow.getCell("allNullCount").setValue(allNullCount4);
		}
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
					BigDecimal tenArea  = FMConstants.ZERO;
					int allTenCount =  0;
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
								if("newTenCount".equals(this.tblMain.getColumn(j).getKey())){
									Map newTenCellMap = (HashMap)rowAdd.getCell("newTenCount").getUserObject();
									if(newTenCellMap!=null)
									{
										Object[] objs = TenancyHelper.getCellValue(newTenCellMap, count, tenArea);
										count = ((Integer)objs[0]).intValue();
										tenArea = (BigDecimal) objs[1];
									}
								}else if("expandTenCount".equals(this.tblMain.getColumn(j).getKey()))
								{							
									Map expandCellMap = (HashMap)rowAdd.getCell("expandTenCount").getUserObject();
									if(expandCellMap!=null)
									{
										Object[] objs = TenancyHelper.getCellValue(expandCellMap, count, tenArea);
										count = ((Integer)objs[0]).intValue();
										tenArea = (BigDecimal) objs[1];
									}
								}else if("continueCount".equals(this.tblMain.getColumn(j).getKey()))
								{
									Map continueCellMap = (HashMap)rowAdd.getCell("continueCount").getUserObject();
									if(continueCellMap!=null)
									{
										Object[] objs = TenancyHelper.getCellValue(continueCellMap, count, tenArea);
										count = ((Integer)objs[0]).intValue();
										tenArea = (BigDecimal) objs[1];
									}									
								}else if("tenSumCount".equals(this.tblMain.getColumn(j).getKey()))
								{
									Map allCountCellMap = (HashMap)rowAdd.getCell("tenSumCount").getUserObject();
									if(allCountCellMap!=null)
									{
										Object[] objs = TenancyHelper.getCellValue(allCountCellMap, count, tenArea);
										count = ((Integer)objs[0]).intValue();
										tenArea = (BigDecimal) objs[1];
									}									
								}
								else if("tenCount".equals(this.tblMain.getColumn(j).getKey()))
								{
									Map tenancyCountCellMap = (HashMap)rowAdd.getCell("tenCount").getUserObject();
									if(tenancyCountCellMap!=null)
									{
										Object[] objs = TenancyHelper.getCellValue(tenancyCountCellMap, count, tenArea);
										count = ((Integer)objs[0]).intValue();
										tenArea = (BigDecimal) objs[1];
									}									
								}
								else if("alreadySumCount".equals(this.tblMain.getColumn(j).getKey()))
								{
									Map alreadyCountCellMap = (HashMap)rowAdd.getCell("alreadySumCount").getUserObject();
									if(alreadyCountCellMap!=null)
									{
										Object[] objs = TenancyHelper.getCellValue(alreadyCountCellMap, count, tenArea);
										count = ((Integer)objs[0]).intValue();
										tenArea = (BigDecimal) objs[1];
									}									
								}
								else if("nullCount".equals(this.tblMain.getColumn(j).getKey()))
								{
									Map nullCountCellMap = (HashMap)rowAdd.getCell("nullCount").getUserObject();
									if(nullCountCellMap!=null)
									{
										Object[] objs = TenancyHelper.getCellValue(nullCountCellMap, count, tenArea);
										count = ((Integer)objs[0]).intValue();
										tenArea = (BigDecimal) objs[1];
									}									
								}
								else if("nullRate".equals(this.tblMain.getColumn(j).getKey()))
								{
									int tenCount =0;
									int nullCount = 0;
									Map tenancyCountCellMap = (HashMap)rowAdd.getCell("tenCount").getUserObject();
									if(tenancyCountCellMap!=null)
									{
										Object[] objs = TenancyHelper.getCellValue(tenancyCountCellMap, count, tenArea);
										tenCount = ((Integer)objs[0]).intValue();
									}
									Map nullCountCellMap = (HashMap)rowAdd.getCell("nullCount").getUserObject();
									if(nullCountCellMap!=null)
									{
										Object[] objs = TenancyHelper.getCellValue(nullCountCellMap, count, tenArea);
										nullCount = ((Integer)objs[0]).intValue();
									}
									allTenCount+=tenCount;
									allNullCount+=nullCount;
									allTenCount = allTenCount==0?1:allTenCount;
									BigDecimal nullRate = new BigDecimal(allNullCount).divide(new BigDecimal(allTenCount),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
									aimCost = nullRate;
								}							
							}
						}
					}
					StringBuffer strBuff = new StringBuffer();
					if(count==0 || tenArea.compareTo(new BigDecimal(0))==0)
					{
						strValue = null;
					}else
					{
						BigDecimal area = tenArea.divide(new BigDecimal(1), 2,BigDecimal.ROUND_HALF_UP);
						strBuff.append(count).append(TenancyReportParameter.REPORT_CELL_JOIN).append(area).append(TenancyReportParameter.REPORT_CELL_UNIT);
						strValue = strBuff.toString();
					}

					if(aimCost==null ||aimCost.equals(FDCHelper.ZERO))
					{
						row.getCell(j).setValue(null);
					}else
					{
						row.getCell(j).setValue(aimCost);
					}
					if("newTenCount".equals(this.tblMain.getColumn(j).getKey()) 
							|| "expandTenCount".equals(this.tblMain.getColumn(j).getKey())
							|| "continueCount".equals(this.tblMain.getColumn(j).getKey())
							|| "tenSumCount".equals(this.tblMain.getColumn(j).getKey())
							|| "tenCount".equals(this.tblMain.getColumn(j).getKey())
							|| "alreadySumCount".equals(this.tblMain.getColumn(j).getKey())
							|| "nullCount".equals(this.tblMain.getColumn(j).getKey())
					){
						row.getCell(j).setValue(strValue);
					}
				}
			}
		}
	}

	//在list表中没设置id的情况下重载的方法
	protected void setActionState() {

	}

	protected String getKeyFieldName() {
		return "name";
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
	}

	protected String getEditUIName() {
		return null;
	}

}