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
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
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
public class ContractChangeSumUI extends AbstractContractChangeSumUI {
	private CostAccountCollection cuAccts = null;
	private Map appMap = null;
	private Map projectTypeMap = new HashMap();
	private boolean includeNoCostAccount = false;
	private Map tableDataMap = null;	
	//是否使用成本月结,当前财务组织的期间
	private Date[] pkdate ;
	/**
	 * output class constructor
	 */
	public ContractChangeSumUI() throws Exception {
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
				new FilterItemInfo("level", new Integer(2)));
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
		appMap = ProjectHelper.getIndexValueByProjProd(null, (String) null,
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
		column.setKey("sellArea");
		column.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getHeadRow(0).getCell("sellArea").setValue("可售面积");
		this.tblMain.getHeadMergeManager().mergeBlock(0, 2, 1, 2);
		for (int i = 0; i < cuAccts.size(); i++) {
			CostAccountInfo acct = cuAccts.get(i);
			column = tblMain.addColumn();
			column.setKey(acct.getId().toString() + "1");
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			column = tblMain.addColumn();
			column.setKey(acct.getId().toString() + "2");
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			column = tblMain.addColumn();
			column.setKey(acct.getId().toString() + "3");
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			column = tblMain.addColumn();
			column.setKey(acct.getId().toString() + "4");
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			column = tblMain.addColumn();
			column.setKey(acct.getId().toString() + "5");
			tblMain.getHeadRow(0).getCell(acct.getId().toString() + "1")
					.setValue(acct.getName());
			tblMain.getHeadRow(1).getCell(acct.getId().toString() + "1")
					.setValue("变更指令单方");
			tblMain.getHeadRow(1).getCell(acct.getId().toString() + "2")
					.setValue("变更指令总额");
			tblMain.getHeadRow(1).getCell(acct.getId().toString() + "3")
					.setValue("动态成本单方");
			tblMain.getHeadRow(1).getCell(acct.getId().toString() + "4")
					.setValue("动态成本总额");
			tblMain.getHeadRow(1).getCell(acct.getId().toString() + "5")
					.setValue("变更率");
			tblMain.getHeadMergeManager().mergeBlock(0, 3 + i * 5, 0,
					3 + i * 5 + 4);
		}
		column = tblMain.addColumn();
		column.setKey("sum1");
		column.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		column = tblMain.addColumn();
		column.setKey("sum2");
		column.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getHeadRow(1).getCell("sum1").setValue("变更指令单方");
		tblMain.getHeadRow(1).getCell("sum2").setValue("变更率");
		tblMain.getHeadRow(0).getCell("sum1").setValue("汇总");
		tblMain.getHeadMergeManager().mergeBlock(0, 3 + cuAccts.size() * 5, 0,
				3 + cuAccts.size() * 5 + 1);
		column = tblMain.addColumn();
		column.setKey("rate");
		column.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getHeadRow(0).getCell("rate").setValue("整体变更签证率");
		tblMain.getHeadMergeManager().mergeBlock(0,
				tblMain.getColumnCount() - 1, 1, tblMain.getColumnCount() - 1);
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
		String[] cols = new String[cuAccts.size() * 2 + 2];
		cols[0] = "sellArea";
		for (int i = 0; i < cuAccts.size(); i++) {
			CostAccountInfo acct = cuAccts.get(i);
			cols[i * 2 + 1] = acct.getId().toString() + "2";
			cols[i * 2 + 1 + 1] = acct.getId().toString() + "4";
		}
		cols[cuAccts.size() * 2 + 1] = "sum1";
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

			BigDecimal sellArea = (BigDecimal) row.getCell("sellArea")
					.getValue();
			BigDecimal changeSum = FDCHelper.ZERO;
			BigDecimal sixChangeSum = FDCHelper.ZERO;
			BigDecimal sum = FDCHelper.ZERO;
			BigDecimal sixSum = FDCHelper.ZERO;
			for (int j = 0; j < cuAccts.size(); j++) {
				CostAccountInfo acct = cuAccts.get(j);
				BigDecimal dynamic = (BigDecimal) row.getCell(
						acct.getId().toString() + "4").getValue();
				if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
					row.getCell(acct.getId().toString() + "3").setValue(
							dynamic.divide(sellArea, 2,
									BigDecimal.ROUND_HALF_UP));
				}
				BigDecimal change = (BigDecimal) row.getCell(
						acct.getId().toString() + "2").getValue();
				if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
					row.getCell(acct.getId().toString() + "1").setValue(
							change
									.divide(sellArea, 2,
											BigDecimal.ROUND_HALF_UP));
				}
				BigDecimal prop = FDCHelper.ZERO.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				if (dynamic.compareTo(FDCHelper.ZERO) != 0) {
					prop = change.multiply(new BigDecimal("100")).divide(
							dynamic, 2, BigDecimal.ROUND_HALF_UP);
					row.getCell(acct.getId().toString() + "5").setValue(
							prop.toString() + "%");
				}
				sum = sum.add(dynamic);
				changeSum = changeSum.add(change);
				if (isSixAcct(acct)) {
					sixSum = sixSum.add(dynamic);
					sixChangeSum = sixChangeSum.add(change);
				}
			}
			if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
				row.getCell("sum1")
						.setValue(
								changeSum.divide(sellArea, 2,
										BigDecimal.ROUND_HALF_UP));
			}
			BigDecimal prop = FDCHelper.ZERO.setScale(2,
					BigDecimal.ROUND_HALF_UP);
			if (sum.compareTo(FDCHelper.ZERO) != 0) {
				prop = changeSum.multiply(new BigDecimal("100")).divide(sum, 2,
						BigDecimal.ROUND_HALF_UP);
				row.getCell("sum2").setValue(prop.toString() + "%");
			}
			if (sixSum.compareTo(FDCHelper.ZERO) != 0) {
				prop = sixChangeSum.multiply(new BigDecimal("100")).divide(
						sixSum, 2, BigDecimal.ROUND_HALF_UP);
				row.getCell("rate").setValue(prop.toString() + "%");
			}

		}
	}

	private boolean isSixAcct(CostAccountInfo acct) {
		if (acct.getSimpleName() != null) {
			return true;
		}
		return false;
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
			String key = projectInfo.getId().toString();
			BigDecimal sellArea = (BigDecimal) this.appMap.get(key + " "
					+ ApportionTypeInfo.sellAreaType);
			row.getCell("sellArea").setValue(sellArea);
			BigDecimal sum = FDCHelper.ZERO;
			BigDecimal change = FDCHelper.ZERO;
			String myKey = null;
			for (int i = 0; i < cuAccts.size(); i++) {
				CostAccountInfo acct = cuAccts.get(i);
				String longNumber = acct.getLongNumber();
				myKey = key + " " + longNumber;
				
				sum = FDCHelper.ZERO;
				if(tableDataMap.containsKey(myKey)){
					sum = (BigDecimal) this.tableDataMap.get(myKey);
				}
				row.getCell(acct.getId().toString() + "4").setValue(sum);

				change = FDCHelper.ZERO;
				if(tableDataMap.containsKey(myKey + "change")){
					change = (BigDecimal) this.tableDataMap.get(myKey + "change");
				}
				row.getCell(acct.getId().toString() + "2").setValue(change);
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

	protected void f7ChangeType_dataChanged(DataChangeEvent e) throws Exception {
		super.f7ChangeType_dataChanged(e);
		this.fillTable();
	}
	private void getAllData()throws BOSException, SQLException {
		tableDataMap = new HashMap();
		StringBuffer mySql = new StringBuffer();
		StringBuffer changeSql = new StringBuffer();
		String myKey = null;
		for (int i = 0; i < cuAccts.size(); i++) {
			CostAccountInfo acct = cuAccts.get(i);
			String longNumber = acct.getLongNumber();
			if(i>0){
				mySql.append(" union  		\r\n");
			}
			mySql.append("select acct.FCurProject,'" + longNumber + "' as twoLevelNumber,sum(FDynCostAmt) dyCost From T_AIM_DynCostSnapShot shot 		\r\n");
			mySql.append("inner join T_FDC_Costaccount acct on shot.FCostAccountId=acct.fid 		\r\n");
			mySql.append("where (acct.FLongNumber = '" + longNumber + "' 		\r\n");
			mySql.append("or acct.FLongNumber like '" + longNumber + "!%')		\r\n");
			mySql.append("And acct.FIsLeaf=1 And FSavedType='" + CostMonthlySaveTypeEnum.AUTOSAVE_VALUE + "' 		\r\n");
			mySql.append("And YEAR(FSnapShotDate)=" + this.spiYear.getValue().toString() + " 		\r\n");
			mySql.append("And MONTH(FSnapShotDate)=" + this.spiMonth.getValue().toString() + " 		\r\n");
			mySql.append("group by acct.FCurProject  		\r\n");
			
			if(i>0){
				changeSql.append(" union  		\r\n");
			}
			changeSql.append("select acct.FCurProject,'" + longNumber + "' as twoLevelNumber,sum(entry.FAmount) change from T_AIM_CostSplitEntry entry	\r\n");
			changeSql.append("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  		\r\n");
			changeSql.append("inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FID  		\r\n");
			changeSql.append("inner join T_CON_ContractChangeBill change on head.FCostBillID=change.FID  		\r\n");
			changeSql.append("where head.FIsInvalid=0 And FCostBillType='CNTRCHANGESPLIT'  		\r\n");
			changeSql.append("And (acct.FLongNumber = '" + longNumber + "' 		\r\n");
			changeSql.append("or acct.FLongNumber like '" + longNumber + "!%') 		\r\n");
			changeSql.append("And acct.FIsLeaf=1  		\r\n");
			changeSql.append("And YEAR(FConductTime)=" + this.spiYear.getValue().toString() + " 		\r\n");
			changeSql.append("And MONTH(FConductTime)=" + this.spiMonth.getValue().toString() + " 		\r\n");		
			
			ChangeTypeInfo changeType = (ChangeTypeInfo) this.f7ChangeType
			.getValue();
			if (changeType != null) {
				changeSql.append("And change.FChangeTypeID='" + changeType.getId().toString() + "' 		\r\n");
			}
			changeSql.append("group by acct.FCurProject  		\r\n");
		}
		
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(mySql.toString()).executeSQL();
		BigDecimal sum = FDCHelper.ZERO;
		while (rs.next()) {
			sum = FDCHelper.ZERO;
			if (rs.getBigDecimal("dyCost") != null) {
				sum = rs.getBigDecimal("dyCost");
			}
			myKey = rs.getString("FCurProject") + " " + rs.getString("twoLevelNumber");
			this.tableDataMap.put(myKey,sum);
		}
		
		IRowSet rsChange = SQLExecutorFactory.getRemoteInstance(changeSql.toString()).executeSQL();
		BigDecimal change = FDCHelper.ZERO;
		while (rsChange.next()) {
			change = FDCHelper.ZERO;
			if (rsChange.getBigDecimal("change") != null) {
				change = rsChange.getBigDecimal("change");
			}
			myKey = rsChange.getString("FCurProject") + " " + rsChange.getString("twoLevelNumber");
			this.tableDataMap.put(myKey + "change",change);
		}
	}
}