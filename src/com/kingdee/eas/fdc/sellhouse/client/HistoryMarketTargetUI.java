/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class HistoryMarketTargetUI extends AbstractHistoryMarketTargetUI
{
    private static final Logger logger = CoreUIObject.getLogger(HistoryMarketTargetUI.class);
    Map sellProCountMap = null;//销售项目id  对应   Row
    Map receptMap = null; //接待方式集合
    Map receptTypeColumnNameMap2 = new HashMap();   //区间接待方式 对应 列名
	Map receptTypeColumnNameMap3 = new HashMap();   //区间接待方式成交比率 对应 列名

	Map receptProjectMap = null;//接待方式和销售项目的对应
	
	Date beginDate = null;
	Date endDate = null;
	
    private HistoryMarketRptFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	protected boolean initDefaultFilter() {
		return true;
	}
	
	private HistoryMarketRptFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new HistoryMarketRptFilterUI(this, this.actionOnLoad);
				this.filterUI.onLoad();
			} catch (Exception e) {
				this.handleException(e);
				abort(e);
			}
		}
		return this.filterUI;
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
		//commonQueryDialog.setUiObject(null);
		return commonQueryDialog;
	}
	
    public HistoryMarketTargetUI() throws Exception
    {
        super();
    }
    
    protected void execQuery() {
    	this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	if(receptMap==null) {
			this.tblMain.removeHeadRows();
			try {
				initTableHead();
			} catch (BOSException e) {
				this.handleException(e);
			}
		}	
    	getAllReceptMap();
    	getReceptTypeMap();
		IRow headRow0 = this.tblMain.getHeadRow(0);
		beginDate = this.getFilterUI().getDateFrom();
		endDate = this.getFilterUI().getDateTo();
		DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		if(receptMap.keySet().size()!=0)
		{
			int a = (receptMap.keySet().size());	
			headRow0.getCell(2).setValue((FORMAT_DAY.format(beginDate)+"--"+FORMAT_DAY.format(endDate)+"接待方式"));
			headRow0.getCell(a+4).setValue((FORMAT_DAY.format(beginDate)+"--"+FORMAT_DAY.format(endDate)+"合计"));
			headRow0.getCell(a+7).setValue(FORMAT_DAY.format(beginDate)+"--"+FORMAT_DAY.format(endDate)+"接待方式成交比例");
		}else
		{
			headRow0.getCell(4).setValue((FORMAT_DAY.format(beginDate)+"--"+FORMAT_DAY.format(endDate)+"合计"));
		}
			
		if(tblMain.getRowCount() == 0){
			try {
				fillProjectAndBuilding();
			} catch (EASBizException e) {
				handleException(e);
			} catch (BOSException e) {
				handleException(e);
			} catch (SQLException e) {
				handleException(e);
			}
		}

		if(tblMain.getRowCount() == 0){
			return;
		}

		if(sellProCountMap==null || sellProCountMap.size() == 0)  return;
		
		Set idSet = sellProCountMap.keySet();
		Iterator iter = idSet.iterator();
		while(iter.hasNext()) {
			String projectid = (String)iter.next();
			IRow row = (IRow)sellProCountMap.get(projectid);
			Map receMap = (Map)receptProjectMap.get(projectid);
			Set recepIDSet = receptMap.keySet();
			Iterator receIter = recepIDSet.iterator();
			while(receIter.hasNext())
			{
				String recepID = (String)receIter.next();
				if(receMap!=null)
				{
					Integer recepCount = (Integer)receMap.get(recepID);
					row.getCell((String)receptTypeColumnNameMap2.get(recepID)).setValue(recepCount);
				}				
			}
		}
		
		//累计预定合同数和合同金额
		PeriodInfo periodInfo = this.getFilterUI().getPeriod();
		Set idSet2 = sellProCountMap.keySet();
		Iterator iter2 = idSet2.iterator();
		while(iter2.hasNext())
		{
			//T_DAY_AccumulateBld	FSaleBalanceId FperiodId FBuildingId  FpreCount  FpreAmount
			String projectid = (String)iter2.next();
			IRow row = (IRow)sellProCountMap.get(projectid);
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(" select sale.fsellprojectid FsellProID,sum(accuBld.FpreCount) FpreCount, ");
			builder.appendSql(" sum(accuBld.FpreAmount) FpreAmount  from t_she_salebalance  sale");
			builder.appendSql(" left join T_DAY_AccumulateBld accuBld on sale.fid=accuBld.FSaleBalanceId ");
			builder.appendSql(" where sale.fsellprojectid='"+projectid+"' ");
			builder.appendSql(" and sale.FPeriodID='"+periodInfo.getId().toString()+"' ");
			builder.appendSql(" group by sale.fsellprojectid");
			IRowSet countSet = null;			
			try {
				countSet =  builder.executeQuery();
				while(countSet.next())
				{
					Integer contractCount = new Integer(countSet.getInt("FpreCount"));
					BigDecimal contractAmount = countSet.getBigDecimal("FpreAmount");
					row.getCell("sumContractCount").setValue(contractCount);
					row.getCell("sumContractAmount").setValue(contractAmount);		
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
		
		//区间已售合同数和合同金额和面积
		Set idSet3 = sellProCountMap.keySet();
		Iterator iter3 = idSet3.iterator();
		while(iter3.hasNext())
		{
			String projectid = (String)iter3.next();
			IRow row = (IRow)sellProCountMap.get(projectid);
			String[] buildingSet = this.getBuildingIDSet(projectid);
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(" select main.fsellprojectid projectid,sum(pur.fcount) count,sum(pur.famount) amount,");
			builder.appendSql(" sum(pur.farea) area from  t_she_salebalance sale ");
			builder.appendSql(" left join t_day_maintable main on sale.fid = main.fsalebalanceid");
			builder.appendSql(" left join t_day_purchasebld pur on main.fid = pur.fdaymainid");
			builder.appendSql(" where ");
			if(buildingSet.length!=0)
			{
				builder.appendParam("pur.FBuildingId", buildingSet);
			}else
			{
				builder.appendSql(" pur.FBuildingId is null ");
			}
			this.appendFilterSql2(builder,"main.FDayNum");
			builder.appendSql("group by main.fsellprojectid");
			IRowSet countSet = null;			
			try {
				countSet =  builder.executeQuery();
				while(countSet.next())
				{
					//String projectID = countSet.getString("projectID");
					Integer count = new Integer(countSet.getInt("count"));
					count = count.equals(new Integer(0))?null:count;
					BigDecimal amount = countSet.getBigDecimal("amount");
					BigDecimal area = countSet.getBigDecimal("area");
					row.getCell("contractCount").setValue(count);
					row.getCell("contractAmount").setValue(amount);	
					row.getCell("contractArea").setValue(area);
					Map receMap = (Map)receptProjectMap.get(projectid);
					Set recepIDSet = receptMap.keySet();
					Iterator receIter = recepIDSet.iterator();
					while(receIter.hasNext())
					{
						String recepID = (String)receIter.next();
						if(receMap!=null)
						{
							Integer recepCount = (Integer)receMap.get(recepID);
							int recepCount2 = 0;
							if(recepCount==null)
							{
								recepCount2 =0;
							}else
							{
								recepCount2 = recepCount.intValue();
							}
							float num =0;
							if(count!=null && count.intValue()>0 && recepCount2!=0)
							{
								num =count.floatValue()/(float)recepCount2*100;
							}
							Float num2 = num==0?null:new Float(num);
							row.getCell((String)receptTypeColumnNameMap3.get(recepID)).setValue(num2);
						}
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		setUnionData();
    }
    
    private void initTableHead() throws BOSException {
    	getAllReceptMap();
    	getReceptTypeMap();
		IRow headRow0 = this.tblMain.addHeadRow();
		IRow headRow1 = this.tblMain.addHeadRow();	
		IColumn columnSellProjectId = this.tblMain.addColumn();
		columnSellProjectId.setKey("id");
		headRow0.getCell("id").setValue("ID");
		headRow1.getCell("id").setValue("ID");
		this.tblMain.getHeadMergeManager().mergeBlock(0,columnSellProjectId.getColumnIndex(), 1, columnSellProjectId.getColumnIndex());
		columnSellProjectId.getStyleAttributes().setHided(true);
		IColumn columnSellProject = this.tblMain.addColumn();
		columnSellProject.setKey("sellProjectName");
		headRow0.getCell("sellProjectName").setValue("名称");
		headRow1.getCell("sellProjectName").setValue("名称");
		this.tblMain.getHeadMergeManager().mergeBlock(0,columnSellProject.getColumnIndex(), 1, columnSellProject.getColumnIndex());
		if(receptMap!=null)  {      //构建区间接待方式表头	
			Set keySet = receptMap.keySet();
			Iterator iter = keySet.iterator();
			int i = 1;
			while(iter.hasNext()) {		
				String key = (String)iter.next();
				receptTypeColumnNameMap2.put(key,"tremtrackCountSum"+i);

				IColumn columnTrack = this.tblMain.addColumn();
				columnTrack.setKey("tremtrackCountSum"+i);
				headRow1.getCell("tremtrackCountSum"+i).setValue(receptMap.get(key));
				this.tblMain.getColumn("tremtrackCountSum"+i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);			
				i++;
			}
			this.tblMain.getHeadMergeManager().mergeBlock(0,this.tblMain.getColumnIndex("tremtrackCountSum1"),0,this.tblMain.getColumnIndex("tremtrackCountSum"+(i-1)));
		}
		IColumn columnSumContractCount = this.tblMain.addColumn();
		columnSumContractCount.setKey("sumContractCount");
		headRow0.getCell("sumContractCount").setValue("累计预定合同数");
		headRow1.getCell("sumContractCount").setValue("累计预定合同数");
		this.tblMain.getColumn("sumContractCount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("sumContractCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		this.tblMain.getHeadMergeManager().mergeBlock(0,columnSumContractCount.getColumnIndex(), 1, columnSumContractCount.getColumnIndex());

		IColumn columnSumContractAmount = this.tblMain.addColumn();
		columnSumContractAmount.setKey("sumContractAmount");
		headRow0.getCell("sumContractAmount").setValue("累计预定合同总价");
		headRow1.getCell("sumContractAmount").setValue("累计预定合同总价");
		this.tblMain.getColumn("sumContractAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("sumContractAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		this.tblMain.getHeadMergeManager().mergeBlock(0,columnSumContractAmount.getColumnIndex(), 1, columnSumContractAmount.getColumnIndex());

		IColumn columnContractCount = this.tblMain.addColumn();
		columnContractCount.setKey("contractCount");
		headRow1.getCell("contractCount").setValue("合同数");
		this.tblMain.getColumn("contractCount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("contractCount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));

		IColumn columnContractAmount = this.tblMain.addColumn();
		columnContractAmount.setKey("contractAmount");
		headRow1.getCell("contractAmount").setValue("合同总价");
		this.tblMain.getColumn("contractAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("contractAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));

		IColumn columnContractArea = this.tblMain.addColumn();
		columnContractArea.setKey("contractArea");
		headRow1.getCell("contractArea").setValue("合同面积");
		tblMain.getColumn("contractArea").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("contractArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getHeadMergeManager().mergeBlock(0,columnContractCount.getColumnIndex(), 0, columnContractArea.getColumnIndex());
		if(receptMap!=null)  {      //构建区间接待方式表头	
			Set keySet = receptMap.keySet();
			Iterator iter = keySet.iterator();
			int i = 1;
			while(iter.hasNext()) {		
				String key = (String)iter.next();
				receptTypeColumnNameMap3.put(key,"tremTrackScale"+i);

				IColumn columnTrack = this.tblMain.addColumn();
				columnTrack.setKey("tremTrackScale"+i);
				headRow1.getCell("tremTrackScale"+i).setValue(receptMap.get(key)+"%");
				this.tblMain.getColumn("tremTrackScale"+i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				this.tblMain.getColumn("tremTrackScale"+i).getStyleAttributes().setNumberFormat(
						FDCHelper.getNumberFtm(2));
				i++;
			}
			this.tblMain.getHeadMergeManager().mergeBlock(0,this.tblMain.getColumnIndex("tremTrackScale1"),0,this.tblMain.getColumnIndex("tremTrackScale"+(i-1)));
		}	
    }
    
    /**
	 * 统计所有父节点
	 */
	public void setUnionData() {
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);            //当前行
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();     //所有叶节点项目集合
				for (int j = i + 1; j < tblMain.getRowCount(); j++) {
					IRow rowAfter = tblMain.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				if(row!=null)  {
					//累计预定合同数
					int sumContractCount = 0;
					//累计预定合同总价
					BigDecimal sumContractAmount= FDCHelper.ZERO;
					//区间合同数
					int contractCount = 0;
					//区间合同总价
					BigDecimal contractAmount= FDCHelper.ZERO;
					//区间合同面积
					BigDecimal contractArea= FDCHelper.ZERO;
					for(int j=0;j<rowList.size();j++)
					{
						IRow addRow = (IRow)rowList.get(j);
						Integer tempSumContractCount = (Integer)addRow.getCell("sumContractCount").getValue();
						if(tempSumContractCount!=null)
						{
							sumContractCount+=tempSumContractCount.intValue();//累计合同数汇总
						}
						BigDecimal tempSumContractAmount = (BigDecimal)addRow.getCell("sumContractAmount").getValue();
						if(tempSumContractAmount!=null)
						{
							sumContractAmount = sumContractAmount.add(tempSumContractAmount);//累计合同总价汇总
						}
						Integer tempContractCount = (Integer)addRow.getCell("contractCount").getValue();
						if(tempContractCount!=null)
						{
							contractCount+=tempContractCount.intValue();//区间合同数汇总
						}
						BigDecimal tempContractAmount = (BigDecimal)addRow.getCell("contractAmount").getValue();
						if(tempContractAmount!=null)
						{
							contractAmount = contractAmount.add(tempContractAmount);//区间合同总价汇总
						}
						BigDecimal tempContractArea = (BigDecimal)addRow.getCell("contractArea").getValue();
						if(tempContractArea!=null)
						{
							contractArea = contractArea.add(tempContractArea);//区间合同面积汇总
						}

					}
					if(sumContractCount!=0)
					{
						row.getCell("sumContractCount").setValue(new Integer(sumContractCount));

					}if(sumContractAmount!=null)
					{
						row.getCell("sumContractAmount").setValue(sumContractAmount);
						row.getCell("sumContractAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
					}
					if(contractCount!=0)
					{
						row.getCell("contractCount").setValue(new Integer(contractCount));

					}if(contractAmount!=null)
					{
						row.getCell("contractAmount").setValue(contractAmount);
						row.getCell("contractAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
					}if(contractArea!=null)
					{
						row.getCell("contractArea").setValue(contractArea);
						row.getCell("contractArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
					}
					//					累计接待方式动态列合计
					//this.setSumTrackNum(receptTypeColumnNameMap,row,rowList);
					//					区间接待方式动态列合计
					this.setSumTrackNum(receptTypeColumnNameMap2,row,rowList);
					//					区间接待方式成交比例
					this.setSumTrackScale(receptTypeColumnNameMap3,receptTypeColumnNameMap2,row,rowList,contractCount);

				}		
			}
		}
	}
	
	private void setSumTrackNum(Map map,IRow row,List rowList)
	{
		Set tempSet2 = map.keySet();
		Iterator tempIter2 = tempSet2.iterator();
		while(tempIter2.hasNext()) {
			String trackColName = (String)map.get(tempIter2.next());
			int colunIndex = row.getCell(trackColName).getColumnIndex();
			int sumTrackNum = 0;
			for(int ii=0;ii<rowList.size();ii++) {
				IRow addRow = (IRow)rowList.get(ii);
				Integer cellTrackValue = (Integer)addRow.getCell(colunIndex).getValue();
				if(cellTrackValue!=null) 
					//sumTrackNum += Integer.parseInt(cellTrackValue);
					sumTrackNum += cellTrackValue.intValue();
			}
			if(sumTrackNum!=0)  
				row.getCell(colunIndex).setValue(new Integer(sumTrackNum));
		}
	}
	
	private void setSumTrackScale(Map map,Map map2,IRow row ,List rowList,int contractCount)
	{
		Set tempSet3 = map.keySet();
		Iterator tempIter3 = tempSet3.iterator();
		while(tempIter3.hasNext()) {
			String receptionTypeId = (String) tempIter3.next();
			String trackColName = (String)map.get(receptionTypeId);																	
			int colunIndex = row.getCell(trackColName).getColumnIndex();//获得接待方式成交比例列位置

			//receptTypeColumnNameMap2和receptTypeColumnNameMap3存储时key值相同，找出不同的value
			String tera = (String) map2.get(receptionTypeId);

			int sumTrackNum = 0;
			for(int ii=0;ii<rowList.size();ii++) {
				IRow addRow = (IRow)rowList.get(ii);
				//找出区间接待方式累计
				Integer value = (Integer)addRow.getCell(tera).getValue();
				if(value!=null) 
					//sumTrackNum += Integer.parseInt(value);
					sumTrackNum += value.intValue();
			}
			float num =0;
			if(sumTrackNum!=0)
				num = contractCount/(float)sumTrackNum *100;
			row.getCell(colunIndex).setValue(new Float(num));
			row.getCell(colunIndex).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		}
	}
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.initControl();
    }
    
    public void initControl()
    {
    	this.actionAddNew.setVisible(false);
    	this.actionEdit.setVisible(false);
    	this.actionRemove.setVisible(false);
    	this.actionView.setVisible(false);
    }
    
    private void fillProjectAndBuilding() throws EASBizException, BOSException, SQLException{
		tblMain.removeRows(false);
		TreeModel sellProTree = null;
		try {
			sellProTree = SHEHelper.getSellProjectTree(this.actionOnLoad,null);
		} catch (Exception e) {
			this.handleException(e);
		}
		DefaultMutableTreeNode sellProRoot = (DefaultMutableTreeNode) sellProTree.getRoot();
		sellProCountMap = new HashMap();
		this.tblMain.getTreeColumn().setDepth(sellProRoot.getDepth() + 1);
		fillNode(this.tblMain, sellProRoot);
	}
    
    private String[] getProjectID() throws BOSException
    {
    	String[] projectID = null;
    	Set proIds = CommerceHelper.getPermitProjectIds();
    	Iterator iter = proIds.iterator();
    	int i=0;
    	projectID = new String[proIds.size()];
    	while(iter.hasNext())
    	{   		   		
    		projectID[i] = (String)iter.next();
    		i++;
    	}
    	return projectID;
    }
    
    private String[] getBuildingIDSet(String projectID)
    {
    	String[] buildingSet = null;
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("sellProject.id", projectID));
		view.setFilter(filterInfo);
		try {
			
			BuildingCollection buildColl = BuildingFactory.getRemoteInstance().getBuildingCollection(view);
			if(buildColl.size()==0)
			{
				buildingSet = new String[0];
			}else
			{
				buildingSet = new String[buildColl.size()];
				for(int i=0;i<buildColl.size();i++)
				{
					BuildingInfo buildingInfo = buildColl.get(i);
					buildingSet[i] = buildingInfo.getId().toString();
				}
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return buildingSet;
    }
    
    private void fillNode(KDTable table, DefaultMutableTreeNode node)	throws BOSException, SQLException, EASBizException {
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel());
		String space = "";
		for (int i = 0; i < node.getLevel(); i++) {
			space += " ";
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellPro = (SellProjectInfo) node.getUserObject();
			row.getCell("sellProjectName").setValue(space + sellPro.getName());
			row.setUserObject(node.getUserObject());
			this.sellProCountMap.put(sellPro.getId().toString(), row);			
		} else {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			row.getCell("sellProjectName").setValue(space + node);
		}
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node.getChildAt(i));
			}
		}
	}
    
    private void getAllReceptMap()
    {
    	String[] projectID = null;
		try {
			projectID = this.getProjectID();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	receptMap = new HashMap();
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql(" select recepType.fid recptTypeID,recepType.fname_l2 recptTypeName");
		builder.appendSql(" from t_day_MainTable main ");
		builder.appendSql(" left join t_day_RecepType dayRecepType on main.fid = dayRecepType.FDayMainId ");
		builder.appendSql(" left join t_she_ReceptionType recepType on dayRecepType.FRecptTypeId = recepType.fid ");
		builder.appendSql(" where ");
		builder.appendParam("main.FSellProjectID", projectID);
		IRowSet countSet = null;			
		try {
			countSet =  builder.executeQuery();
			while(countSet.next())
			{
				String recptTypeID = countSet.getString("recptTypeID");
				String recptTypeName = countSet.getString("recptTypeName");
				if(recptTypeID!=null)
				{
					receptMap.put(recptTypeID,recptTypeName);
				}			
			}
		}catch (Exception e1) {
				e1.printStackTrace();
			}
    }
    
  //获取接待方式的id和name和项目的映射关系
	private void getReceptTypeMap(){
		receptProjectMap = new HashMap();
		//receptMap = new HashMap();		
		//保存项目对应的接待方式
		Map receptTypeMap2= new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select main.FsellProjectId projectID,recepType.fid recptTypeID,recepType.fname_l2 recptTypeName,");
		builder.appendSql(" sum(dayRecepType.FrecptCount) recepCount from t_day_RecepType dayRecepType");
		builder.appendSql(" left join t_day_MainTable main on dayRecepType.fdaymainid = main.fid");
		builder.appendSql(" left join t_she_ReceptionType recepType on dayRecepType.FRecptTypeId = recepType.fid");
		this.appendFilterSql(builder,"FDayNum");
		builder.appendSql(" group by main.FsellProjectId,recepType.fid,recepType.fname_l2");
		IRowSet countSet = null;			
		try {
			countSet =  builder.executeQuery();
			while(countSet.next())
			{
				String projectID = countSet.getString("projectID");
				String recptTypeID = countSet.getString("recptTypeID");
				//String recptTypeName = countSet.getString("recptTypeName");
				int recepCount = countSet.getInt("recepCount");
				//receptMap.put(recptTypeID,recptTypeName);
				if(receptProjectMap.get(projectID)==null)
				{
					receptTypeMap2= new HashMap();
					receptTypeMap2.put(recptTypeID, new Integer(recepCount));
					receptProjectMap.put(projectID, receptTypeMap2);
				}else
				{
					receptTypeMap2.put(recptTypeID, new Integer(recepCount));
					receptProjectMap.put(projectID, receptTypeMap2);
				}							
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void appendFilterSql(FDCSQLBuilder builder, String proName) {
		Date beginDate = this.getFilterUI().getDateFrom();
		if (beginDate != null) {
			builder.appendSql(" where " + proName + " >= ? ");
			String dateStr = FDCDateHelper.formatDate(beginDate);
			//int dateNum = (new Integer(dateStr)).intValue();		
			builder.addParam(new Integer(dateStr));
			//builder.addParam(FDCDateHelper.getSqlDate(beginDate));
		}
		Date endDate = this.getFilterUI().getDateTo();
		if (endDate != null) {
			builder.appendSql(" and " + proName + " < ? ");
			String endDateStr = FDCDateHelper.formatDate(FDCDateHelper.getNextDay(endDate));
			builder.addParam(new Integer(endDateStr));
			//builder.addParam(FDCDateHelper.getNextDay(endDate));
		}
	}
	
	public void appendFilterSql2(FDCSQLBuilder builder, String proName) {
		Date beginDate = this.getFilterUI().getDateFrom();
		if (beginDate != null) {
			builder.appendSql(" and " + proName + " >= ? ");
			String dateStr = FDCDateHelper.formatDate(beginDate);
			//int dateNum = (new Integer(dateStr)).intValue();		
			builder.addParam(new Integer(dateStr));
			//builder.addParam(FDCDateHelper.getSqlDate(beginDate));
		}
		Date endDate = this.getFilterUI().getDateTo();
		if (endDate != null) {
			builder.appendSql(" and " + proName + " < ? ");
			String endDateStr = FDCDateHelper.formatDate(FDCDateHelper.getNextDay(endDate));
			builder.addParam(new Integer(endDateStr));
			//builder.addParam(FDCDateHelper.getNextDay(endDate));
		}
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			return;
		}
		super.tblMain_tableClicked(e);
	}

    public void storeFields()
    {
        super.storeFields();
    }

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
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