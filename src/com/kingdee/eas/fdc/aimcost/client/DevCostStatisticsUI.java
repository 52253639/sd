/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
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

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
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
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.TypeConversionUtils;

/**
 * output class name
 */
public class DevCostStatisticsUI extends AbstractDevCostStatisticsUI {
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
	public DevCostStatisticsUI() throws Exception {
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
		
		acctFilter.getFilterItems().add(
				new FilterItemInfo("level", new Integer(1)));
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isLeaf", new Integer(1)));
		if(!includeNoCostAccount){
			acctFilter.getFilterItems().add(
					new FilterItemInfo("isCostAccount", new Integer(1)));
			acctFilter.setMaskString("#0 and #1 and (#2 or (#3 and #4)) and #5");
		}else{
			acctFilter.setMaskString("#0 and #1 and (#2 or (#3 and #4))");
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

		Calendar curCal = Calendar.getInstance();
		curCal.setTime(pkdate[0]);
		int year = curCal.get(Calendar.YEAR);
		int month = curCal.get(Calendar.MONTH);

		SpinnerNumberModel yearMo = new SpinnerNumberModel(year, 1990, 2099, 1);
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
		int baseCount = 3;
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
			tblMain.getHeadRow(0).getCell(acct.getId().toString() + "1")
					.setValue(acct.getName());
			tblMain.getHeadMergeManager().mergeBlock(0, baseCount + i * 3, 0,
					baseCount + i * 3 + 2);
			tblMain.getHeadRow(1).getCell(acct.getId().toString() + "1")
					.setValue("总金额");
			tblMain.getHeadRow(1).getCell(acct.getId().toString() + "2")
					.setValue("单方金额");
			tblMain.getHeadRow(1).getCell(acct.getId().toString() + "3")
					.setValue("百分比");
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
		column = tblMain.addColumn();
		column.setKey("sum3");
		tblMain.getHeadRow(0).getCell("sum1").setValue("汇总");
		tblMain.getHeadMergeManager().mergeBlock(0,
				baseCount + cuAccts.size() * 3, 0,
				baseCount + cuAccts.size() * 3 + 2);
		tblMain.getHeadRow(1).getCell("sum1").setValue("总金额");
		tblMain.getHeadRow(1).getCell("sum2").setValue("单方金额");
		tblMain.getHeadRow(1).getCell("sum3").setValue("百分比");
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
		String[] cols = new String[cuAccts.size() + 1];
		cols[0] = "sellArea";
		for (int i = 0; i < cuAccts.size(); i++) {
			CostAccountInfo acct = cuAccts.get(i);
			cols[i + 1] = acct.getId().toString() + "1";
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
			BigDecimal sellArea = (BigDecimal) row.getCell("sellArea")
					.getValue();
			BigDecimal sum = FDCHelper.ZERO;
			for (int j = 0; j < cuAccts.size(); j++) {
				CostAccountInfo acct = cuAccts.get(j);
				BigDecimal dynamic = (BigDecimal) row.getCell(
						acct.getId().toString() + "1").getValue();
				if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
					row.getCell(acct.getId().toString() + "2").setValue(
							dynamic.divide(sellArea, 2,
									BigDecimal.ROUND_HALF_UP));
				}
				sum = sum.add(dynamic);
			}
			for (int j = 0; j < cuAccts.size(); j++) {
				CostAccountInfo acct = cuAccts.get(j);
				BigDecimal dynamic = (BigDecimal) row.getCell(
						acct.getId().toString() + "1").getValue();
				BigDecimal prop = FDCHelper.ZERO.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				if (sum.compareTo(FDCHelper.ZERO) != 0) {
					prop = dynamic.multiply(new BigDecimal("100")).divide(sum,
							2, BigDecimal.ROUND_HALF_UP);
				}
				row.getCell(acct.getId().toString() + "3").setValue(
						prop.toString() + "%");
			}
			row.getCell("sum1").setValue(sum);
			if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
				row.getCell("sum2").setValue(
						sum.divide(sellArea, 2, BigDecimal.ROUND_HALF_UP));
			}
			row.getCell("sum3").setValue("100%");
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

			String key = projectInfo.getId().toString();
			BigDecimal sellArea = (BigDecimal) this.appMap.get(key + " "
					+ ApportionTypeInfo.sellAreaType);
			row.getCell("sellArea").setValue(sellArea);
			String myKey = null;
			for (int i = 0; i < cuAccts.size(); i++) {
				CostAccountInfo acct = cuAccts.get(i);
				String longNumber = acct.getLongNumber();
				BigDecimal sum = FDCHelper.ZERO;
				myKey = key + " " + longNumber;
				if(tableDataMap.containsKey(myKey)){
					sum = (BigDecimal) this.tableDataMap.get(myKey);
				}
				
				row.getCell(acct.getId().toString() + "1").setValue(sum);
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
		String myKey = null;
		for (int i = 0; i < cuAccts.size(); i++) {
			CostAccountInfo acct = cuAccts.get(i);
			String longNumber = acct.getLongNumber();
			if(i>0){
				mySql.append(" union  		\r\n");
			}
			mySql.append("select acct.FCurProject,'" + longNumber + "' as twoLevelNumber,sum(FDynCostAmt) sumCostAmount From T_AIM_DynCostSnapShot shot 		\r\n");
			mySql.append("inner join T_FDC_Costaccount acct on shot.FCostAccountId=acct.fid 		\r\n");
			mySql.append("where (acct.FLongNumber = '" + longNumber + "' 		\r\n");
			mySql.append("or acct.FLongNumber like '" + longNumber + "!%')		\r\n");
			mySql.append("And acct.FIsLeaf=1 And FSavedType='" + CostMonthlySaveTypeEnum.AUTOSAVE_VALUE + "' 		\r\n");
			mySql.append("And YEAR(FSnapShotDate)=" + this.spiYear.getValue().toString() + " 		\r\n");
			mySql.append("And MONTH(FSnapShotDate)=" + this.spiMonth.getValue().toString() + " 		\r\n");
			mySql.append("group by acct.FCurProject  		\r\n");
		}
		
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(mySql.toString()).executeSQL();
		
		while (rs.next()) {
			BigDecimal sum = FDCHelper.ZERO;
			if (rs.getBigDecimal("sumCostAmount") != null) {
				sum = rs.getBigDecimal("sumCostAmount");
			}
			myKey = rs.getString("FCurProject") + " " + rs.getString("twoLevelNumber");
			this.tableDataMap.put(myKey,sum);
		}
	}
}