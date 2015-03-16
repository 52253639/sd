/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectTypeCollection;
import com.kingdee.eas.fdc.basedata.ProjectTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.TypeConversionUtils;

/**
 * output class name
 */
public class DyCostSumUI extends AbstractDyCostSumUI {
	private CostAccountCollection cuAccts = null;
	private Map aimAppMap = null;
	private Map dyAppMap = null;
	private Map projectTypeMap = new HashMap();
	private boolean includeNoCostAccount = false;
	private Map tableDataMap = null;
	//是否使用成本月结,当前财务组织的期间
	private Date[] pkdate ;
	/**
	 * output class constructor
	 */
	public DyCostSumUI() throws Exception {
		super();
		pkdate = FDCClientHelper.getCompanyCurrentDate(false);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		//确定成本报表是否包含非成本类科目，初始化includeNoCostAccount
		includeNoCostAccount = FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_INCLUDENOCOSTACCOUNT);
		
		FilterInfo acctFilter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		acctFilter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit.id", OrgConstants.DEF_CU_ID));
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		acctFilter.getFilterItems().add(
				new FilterItemInfo("level", new Integer(1)));
		if(!includeNoCostAccount){
			acctFilter.getFilterItems().add(
					new FilterItemInfo("isCostAccount", new Integer(1)));
		}
		view.setFilter(acctFilter);
		view.getSorter().add(new SorterItemInfo("longNumber"));
		cuAccts = CostAccountFactory.getRemoteInstance()
				.getCostAccountCollection(view);
		
		initApportionData();
		ProjectTypeCollection types = ProjectTypeFactory.getRemoteInstance()
				.getProjectTypeCollection();
		for (int i = 0; i < types.size(); i++) {
			ProjectTypeInfo type = types.get(i);
			this.projectTypeMap.put(type.getId().toString(), type);
		}
		initTable();
		fillTable();
	}

	private void initApportionData() throws BOSException {
		aimAppMap = ProjectHelper.getIndexValueByProjProd(null, (String) null,
				ProjectStageEnum.AIMCOST);
		dyAppMap = ProjectHelper.getIndexValueByProjProd(null, (String) null,
				ProjectStageEnum.DYNCOST);
	}

	private void initControl() {
		this.btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
//		int year = Calendar.getInstance().get(Calendar.YEAR);
//		int month = Calendar.getInstance().get(Calendar.MONTH);
		Date curDate = pkdate[0];
		int year = curDate.getYear();
		int month = curDate.getMonth();
		
		SpinnerNumberModel yearMo = new SpinnerNumberModel(year+1900, 1990, 2099, 1);
		this.spiYear.setModel(yearMo);
		SpinnerNumberModel monthMo = new SpinnerNumberModel(month + 1, 1, 12, 1);
		spiMonth.setModel(monthMo);
	}

	public void initTable() throws BOSException {
		this.tblMain.checkParsed();
		this.tblMain.getStyleAttributes().setLocked(true);
		IColumn column = this.tblMain.addColumn();
		column.setKey("projectType");
		this.tblMain.getHeadRow(0).getCell("projectType").setValue("项目系列");
		this.tblMain.getHeadMergeManager().mergeBlock(0, 1, 1, 1);
		column = this.tblMain.addColumn();
		column.setKey("目标可售面积");
		column.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getHeadRow(0).getCell("目标可售面积").setValue("目标可售面积");
		this.tblMain.getHeadMergeManager().mergeBlock(0, 2, 1, 2);
		column = this.tblMain.addColumn();
		column.setKey("动态可售面积");
		column.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getHeadRow(0).getCell("动态可售面积").setValue("动态可售面积");
		this.tblMain.getHeadMergeManager().mergeBlock(0, 3, 1, 3);
		int baseCount = 4;
		for (int i = 0; i < cuAccts.size(); i++) {
			CostAccountInfo acct = cuAccts.get(i);
			String acctId = acct.getId().toString();
			column = tblMain.addColumn();
			column.setKey(acctId + "目标成本总金额");
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			column = tblMain.addColumn();
			column.setKey(acctId + "动态成本总金额");
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			column = tblMain.addColumn();
			column.setKey(acctId + "上月动态成本总金额");
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			column = tblMain.addColumn();
			column.setKey(acctId + "目标单方");
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			column = tblMain.addColumn();
			column.setKey(acctId + "动态单方");
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			column = tblMain.addColumn();
			column.setKey(acctId + "上月动态单方");
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			
			tblMain.getHeadRow(0).getCell(acctId + "目标成本总金额")
					.setValue(acct.getName());
			tblMain.getHeadMergeManager().mergeBlock(0, baseCount + i * 6, 0,
					baseCount + i * 6 + 5);
			tblMain.getHeadRow(1).getCell(acctId + "目标成本总金额")
					.setValue("目标成本总金额");
			tblMain.getHeadRow(1).getCell(acctId + "动态成本总金额")
					.setValue("动态成本总金额");
			tblMain.getHeadRow(1).getCell(acctId + "上月动态成本总金额")
					.setValue("上月动态成本总金额");
			tblMain.getHeadRow(1).getCell(acctId + "目标单方")
					.setValue("目标单方");
			tblMain.getHeadRow(1).getCell(acctId + "动态单方")
					.setValue("动态单方");
			tblMain.getHeadRow(1).getCell(acctId + "上月动态单方")
					.setValue("上月动态单方");
		}
	}

	public void fillTable() throws Exception {
		tblMain.removeRows();
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		KDTree tree = new KDTree();
		treeBuilder.build(this, tree, this.actionOnLoad);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) tree.getModel()
				.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
					.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		if (root.isRoot()) {
			maxLevel++;
		}
		tblMain.getTreeColumn().setDepth(maxLevel);
		getAllData();
		fillNode(tblMain, root);
		setUnionData();
		filterProjectType();
	}

	private void setUnionData() {
		String[] cols = new String[3 * this.cuAccts.size() + 2];
		int index = 0;
		cols[index++] = "目标可售面积";
		cols[index++] = "动态可售面积";
		for (int i = 0; i < this.cuAccts.size(); i++) {
			CostAccountInfo acct = this.cuAccts.get(i);
			cols[index++] = acct.getId().toString() + "目标成本总金额";
			cols[index++] = acct.getId().toString() + "动态成本总金额";
			cols[index++] = acct.getId().toString() + "上月动态成本总金额";
		}
		KDTable table = this.tblMain;
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getCell("orgOrProject").getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List aimRowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						aimRowList.add(rowAfter);
					}
				}
				for (int j = 0; j < cols.length; j++) {
					BigDecimal aimCost = FMConstants.ZERO;
					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
						Object value = rowAdd.getCell(cols[j]).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								aimCost = aimCost.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								aimCost = aimCost.add(new BigDecimal(
										((Integer) value).toString()));
							}
						}
					}
					row.getCell(cols[j]).setValue(aimCost);
				}
			}
			BigDecimal aimArea = (BigDecimal) row.getCell("目标可售面积").getValue();
			BigDecimal dyArea = (BigDecimal) row.getCell("动态可售面积").getValue();

			for (int j = 0; j < cuAccts.size(); j++) {
				CostAccountInfo acct = cuAccts.get(j);
				String acctId = acct.getId().toString();
				BigDecimal aim = (BigDecimal) row.getCell(acctId + "目标成本总金额").getValue();
				BigDecimal dynamic = (BigDecimal) row.getCell(acctId + "动态成本总金额").getValue();
				BigDecimal lastDynamic = (BigDecimal) row.getCell(acctId + "上月动态成本总金额").getValue();
				if (aim != null && aimArea != null
						&& aimArea.compareTo(FDCHelper.ZERO) != 0) {
					row.getCell(acctId + "目标单方").setValue(
							aim.divide(aimArea, 2, BigDecimal.ROUND_HALF_UP));
				}
				if (dynamic != null && dyArea != null
						&& dyArea.compareTo(FDCHelper.ZERO) != 0) {
					row.getCell(acctId + "动态单方").setValue(
							dynamic.divide(dyArea, 2, BigDecimal.ROUND_HALF_UP));
				}
				if (lastDynamic != null && dyArea != null
						&& dyArea.compareTo(FDCHelper.ZERO) != 0) {
					row.getCell(acctId + "上月动态单方").setValue(
							lastDynamic.divide(dyArea, 2,BigDecimal.ROUND_HALF_UP));
				}
			}
		}
	}

	public void fillNode(KDTable table, DefaultKingdeeTreeNode node)
			throws BOSException, SQLException {
		IRow row = table.addRow();
		if (((DefaultKingdeeTreeNode) node.getRoot()).getUserObject() == null) {
			row.setTreeLevel(node.getLevel() - 1);
		} else {
			row.setTreeLevel(node.getLevel());
		}
		row.setUserObject(node.getUserObject());
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			row.getCell("orgOrProject").setValue(projectInfo.getName());
			if (projectInfo.getProjectType() != null) {
				String typeId = projectInfo.getProjectType().getId().toString();
				ProjectTypeInfo typeInfo = (ProjectTypeInfo) this.projectTypeMap
						.get(typeId);
				row.getCell("projectType").setValue(typeInfo.getName());
			}
		} else {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {

			}
			FullOrgUnitInfo info = oui.getUnit();
			row.getCell("orgOrProject").setValue(info.getName());
		}
		if (node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			row.getCell("orgOrProject").setUserObject(projectInfo);
			String key = projectInfo.getId().toString() + " "
					+ ApportionTypeInfo.sellAreaType;
			BigDecimal aimArea = (BigDecimal) this.aimAppMap.get(key);
			row.getCell("目标可售面积").setValue(aimArea);
			BigDecimal dyArea = (BigDecimal) this.dyAppMap.get(key);
			row.getCell("动态可售面积").setValue(dyArea);
			BigDecimal dyCost = FDCHelper.ZERO;
			BigDecimal aimCost = FDCHelper.ZERO;
			String myKey = null;
			for (int i = 0; i < cuAccts.size(); i++) {
				CostAccountInfo acct = cuAccts.get(i);
				String acctId = acct.getId().toString();
				String longNumber = acct.getLongNumber();
				
				dyCost = FDCHelper.ZERO;
				aimCost = FDCHelper.ZERO;
				myKey = projectInfo.getId().toString() + " " + longNumber;
				if(tableDataMap.containsKey(myKey + "aimCost")){
					aimCost = (BigDecimal) this.tableDataMap.get(myKey + "aimCost");
				}
				if(tableDataMap.containsKey(myKey + "dyCost")){
					dyCost = (BigDecimal) this.tableDataMap.get(myKey + "dyCost");
				}
				row.getCell(acctId + "目标成本总金额").setValue(aimCost);
				row.getCell(acctId + "动态成本总金额").setValue(dyCost);	
				
				dyCost = FDCHelper.ZERO;
				if(tableDataMap.containsKey(myKey + "lastDyCost")){
					dyCost = (BigDecimal) this.tableDataMap.get(myKey + "lastDyCost");
				}
				row.getCell(acctId + "上月动态成本总金额").setValue(dyCost);
			}

		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultKingdeeTreeNode) node
						.getChildAt(i));
			}
		}
	}

	protected void spiMonth_stateChanged(ChangeEvent e) throws Exception {
		super.spiMonth_stateChanged(e);
		this.fillTable();
	}

	protected void spiYear_stateChanged(ChangeEvent e) throws Exception {
		super.spiYear_stateChanged(e);
		this.fillTable();
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
		initApportionData();
		this.fillTable();
	}

	public void filterProjectType() {
		ProjectTypeInfo type = (ProjectTypeInfo) this.f7ProjectType.getValue();
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (type == null) {
				row.getStyleAttributes().setHided(false);
			} else {
				String projectType = (String) row.getCell("projectType")
						.getValue();
				if (projectType == null) {
					row.getStyleAttributes().setHided(true);
				} else {
					if (!projectType.equals(type.getName())) {
						row.getStyleAttributes().setHided(true);
					} else {
						row.getStyleAttributes().setHided(false);
					}
				}
			}
		}
	}

	protected void f7ProjectType_dataChanged(DataChangeEvent e)
			throws Exception {
		super.f7ProjectType_dataChanged(e);
		filterProjectType();
	}
	
	private void getAllData()throws BOSException, SQLException {
		tableDataMap = new HashMap();
		StringBuffer mySql = new StringBuffer();
		StringBuffer lastSql = new StringBuffer();
		String myKey = null;
		for (int i = 0; i < cuAccts.size(); i++) {
			CostAccountInfo acct = cuAccts.get(i);
			String longNumber = acct.getLongNumber();
			if(i>0){
				mySql.append(" union  		\r\n");
			}
			mySql.append("select '1' as howtime,acct.FCurProject,'" + longNumber + "' as oneLevelNumber,sum(FAimCostAmt) aimCost,sum(FDynCostAmt) dyCost From T_AIM_DynCostSnapShot shot 		\r\n");
			mySql.append("inner join T_FDC_Costaccount acct on shot.FCostAccountId=acct.fid 		\r\n");
			mySql.append("where (acct.FLongNumber = '" + longNumber + "' 		\r\n");
			mySql.append("or acct.FLongNumber like '" + longNumber + "!%')		\r\n");
			mySql.append("And acct.FIsLeaf=1 And FSavedType='" + CostMonthlySaveTypeEnum.AUTOSAVE_VALUE + "' 		\r\n");
			mySql.append("And YEAR(FSnapShotDate)=" + this.spiYear.getValue().toString() + " 		\r\n");
			mySql.append("And MONTH(FSnapShotDate)=" + this.spiMonth.getValue().toString() + " 		\r\n");
			mySql.append("group by acct.FCurProject  		\r\n");
			
			Calendar lastCal = Calendar.getInstance();
			lastCal.set(Calendar.YEAR, ((Integer) this.spiYear.getValue())
					.intValue());
			lastCal.set(Calendar.MONTH, ((Integer) this.spiMonth.getValue())
					.intValue() - 1);
			lastCal.set(Calendar.DATE, 0);
			
			String lastYear = String.valueOf(DateTimeUtils.getYear(lastCal.getTime()));
			String lastMonth = String.valueOf(DateTimeUtils.getMonth(lastCal.getTime()));
			if(i>0){
				lastSql.append(" union  		\r\n");
			}
			lastSql.append("select '0' as howtime,acct.FCurProject,'" + longNumber + "' as oneLevelNumber,sum(FAimCostAmt) aimCost,sum(FDynCostAmt) dyCost From T_AIM_DynCostSnapShot shot 		\r\n");
			lastSql.append("inner join T_FDC_Costaccount acct on shot.FCostAccountId=acct.fid 		\r\n");
			lastSql.append("where (acct.FLongNumber = '" + longNumber + "' 		\r\n");
			lastSql.append("or acct.FLongNumber like '" + longNumber + "!%')		\r\n");
			lastSql.append("And acct.FIsLeaf=1 And FSavedType='" + CostMonthlySaveTypeEnum.AUTOSAVE_VALUE + "' 		\r\n");
			lastSql.append("And YEAR(FSnapShotDate)=" + lastYear + " 		\r\n");
			lastSql.append("And MONTH(FSnapShotDate)=" + lastMonth + " 		\r\n");
			lastSql.append("group by acct.FCurProject  		\r\n");
		}
		
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(mySql.toString()).executeSQL();
		BigDecimal dyCost = FDCHelper.ZERO;
		BigDecimal aimCost = FDCHelper.ZERO;
		while (rs.next()) {
			dyCost = FDCHelper.ZERO;
			aimCost = FDCHelper.ZERO;
			if (rs.getBigDecimal("dyCost") != null) {
				dyCost = dyCost.add(rs.getBigDecimal("dyCost"));
			}
			if (rs.getBigDecimal("aimCost") != null) {
				aimCost = aimCost.add(rs.getBigDecimal("aimCost"));
			}
			myKey = rs.getString("FCurProject") + " " + rs.getString("oneLevelNumber");
			this.tableDataMap.put(myKey + "dyCost",dyCost);
			this.tableDataMap.put(myKey + "aimCost",aimCost);
		}
		
		IRowSet rsLast = SQLExecutorFactory.getRemoteInstance(lastSql.toString()).executeSQL();
		while (rsLast.next()) {
			dyCost = FDCHelper.ZERO;
			if (rsLast.getBigDecimal("dyCost") != null) {
				dyCost = rsLast.getBigDecimal("dyCost");
			}
			myKey = rsLast.getString("FCurProject") + " " + rsLast.getString("oneLevelNumber");
			this.tableDataMap.put(myKey + "lastDyCost",dyCost);
		}
	}
}