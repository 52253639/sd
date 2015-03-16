/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.chart.ChartType;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
//import com.kingdee.bos.sql.schema.TableManager;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.TableManagerFacade;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.CostAcctOrgAssignCollection;
import com.kingdee.eas.fdc.aimcost.CostAcctOrgAssignFactory;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotFactory;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotSettEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotSettEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.FullDynamicCostMap;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.aimcost.HasPaySplitDataGetter;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.client.ChartData;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class OrgDynCostUI extends AbstractFullProjectDynamicCostUI {
	public static final String SETTLE_ADJUST = "settleAdjust";

	public static final String BUILD_PART = "buildPart";

	public static final String SELL_PART = "sellPart";

	public static final String DIFF = "diff";

	public static final String DYNAMIC_COST = "dynamicCost";

	public static final String AIM_COST = "aimCost";

	public static final String INTENDING_HAPPEN = "intendingHappen";

	public static final String HAS_HAPPEN = "hasHappen";

	public static final String NO_TEXT = "noText";
	
	public static final String HAS_PAY = "hasPay";

	public static final String SETTLE = "Settle";

	public static final String NO_SETTLE = "NoSettle";

	public static final String SUB_TOTAL_SETTLE = "subTotalSettle";

	public static final String SUB_TOTAL_NO_SETTLE = "subTotalNoSettle";

	public static final String ASSIGN_AMOUNT_SETTLE = "assignAmountSettle";

	public static final String ASSIGN_AMOUNT_NO_SETTLE = "assignAmountNoSettle";

	private ChangeTypeCollection changeTypes;

	private Map acctMap = new HashMap();
	private Map acctMaps = new HashMap();//用于缓存成本科目map

	private Map dynamicCostMap = new HashMap();

	private Map aimCostMap = new HashMap();

	private HappenDataGetter happenGetter;

	private BigDecimal sellArea = null;

	private BigDecimal buildArea = null;

	/**
	 * output class constructor
	 */
	public OrgDynCostUI() throws Exception {
		super();
	}

	// 数据对象变化时，刷新界面状态
	protected void setActionState() {

	}

	/**
	 * 屏蔽回车键
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		//ctrl+L 用于测试程序
/*		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if(proNode!=null&&proNode.getUserObject() instanceof CurProjectInfo){
			String prjId=((CurProjectInfo)proNode.getUserObject()).getId().toString();
			BigDecimal amount=FDCCostRptFacadeFactory.getRemoteInstance().getAimCost(prjId);
			System.out.println("aimCost:"+amount);
			amount=FDCCostRptFacadeFactory.getRemoteInstance().getDynCost(prjId);
			System.out.println("dynCost:"+amount);
			amount=FDCCostRptFacadeFactory.getRemoteInstance().getHappendCost(prjId);
			System.out.println("HappendCost:"+amount);
		}
		System.out.println("test");*/
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// super.tblMain_tableClicked(e);

		if (e.getClickCount() == 2) {

			int rowIndex = e.getRowIndex();

			// if(getMainTable().getCell(rowIndex, colIndex).getValue() == null)
			// return;

			Object value = getMainTable().getRow(rowIndex).getUserObject();
			if (value == null)
				return;
			CostAccountInfo acctInfo = (CostAccountInfo) value;

			boolean b = acctInfo.isIsLeaf();
			if (!b)
				return;
			this.setCursorOfWair();
			Map map = new HashMap();

			map.put("acctId", acctInfo.getId().toString());

			Object no_set_amt = getMainTable().getRow(rowIndex).getCell(
					"assignAmountNoSettle").getValue();
			Object no_set_design = getMainTable().getRow(rowIndex).getCell(
					FDCConstants.CHANGE_TYPE_DESIGN + NO_SETTLE).getValue();
			Object no_set_sign = getMainTable().getRow(rowIndex).getCell(
					FDCConstants.CHANGE_TYPE_SIGN + NO_SETTLE).getValue();
			Object no_set_other = getMainTable().getRow(rowIndex).getCell(
					FDCConstants.CHANGE_TYPE_OTHER + NO_SETTLE).getValue();
			Object no_set_total = getMainTable().getRow(rowIndex).getCell(
					"subTotalNoSettle").getValue();

			Object set_amt = getMainTable().getRow(rowIndex).getCell(
					"assignAmountSettle").getValue();
			Object set_design = getMainTable().getRow(rowIndex).getCell(
					FDCConstants.CHANGE_TYPE_DESIGN + SETTLE).getValue();
			Object set_sign = getMainTable().getRow(rowIndex).getCell(
					FDCConstants.CHANGE_TYPE_SIGN + SETTLE).getValue();
			Object set_other = getMainTable().getRow(rowIndex).getCell(
					FDCConstants.CHANGE_TYPE_OTHER + SETTLE).getValue();
			Object set_total = getMainTable().getRow(rowIndex).getCell(
					"subTotalSettle").getValue();

			Object no_text = getMainTable().getRow(rowIndex).getCell(NO_TEXT)
					.getValue();

			Object will_happen = getMainTable().getRow(rowIndex).getCell(
					INTENDING_HAPPEN).getValue();

			map.put("no_set_amt", no_set_amt);
			map.put("no_set_design", no_set_design);
			map.put("no_set_sign", no_set_sign);
			map.put("no_set_other", no_set_other);
			map.put("no_set_total", no_set_total);

			map.put("set_amt", set_amt);
			map.put("set_design", set_design);
			map.put("set_sign", set_sign);
			map.put("set_other", set_other);
			map.put("set_total", set_total);

			map.put("no_text", no_text);
			map.put("will_happen", will_happen);
			map.put("FullDyDetailInfoTitle","全项目动态成本详细信息");
			FullDyDetailInfoUI.showDialogWindows(this, map);
			this.setCursorOfDefault();

		}
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	protected String getEditUIName() {
		// TODO 自动生成方法存根
		return null;
	}

	protected void initTree() throws Exception {
		this.initMainTable();
		OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
//		if(!currentOrgUnit.isIsCostOrgUnit()){
			SysContext.getSysContext().setCurrentOrgUnit(SysContext.getSysContext().getCurrentCtrlUnit());
//		}
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
		SysContext.getSysContext().setCurrentOrgUnit(currentOrgUnit);
	}

	/**
	 * 设置表格属性
	 * 
	 * @throws BOSException
	 */
	private void initMainTable() throws BOSException {
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
		tblMain.getViewManager().setFreezeView(-1, 2);
		tblMain.getColumn(AIM_COST).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn(HAS_HAPPEN).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn(HAS_PAY).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn(INTENDING_HAPPEN).getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(DYNAMIC_COST).getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(NO_TEXT).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn(DIFF).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn(SELL_PART).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn(BUILD_PART).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);

		tblMain.getColumn(AIM_COST).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn(HAS_HAPPEN).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn(HAS_PAY).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn(INTENDING_HAPPEN).getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(DYNAMIC_COST).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn(NO_TEXT).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn(DIFF).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn(SELL_PART).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn(BUILD_PART).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));

		tblMain.addHeadRow(1, (IRow) tblMain.getHeadRow(0).clone());
		for (int i = 0; i < tblMain.getColumnCount(); i++) {
			tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
		}
		int colIndex = 2;
		IColumn col = tblMain.addColumn(colIndex++);
		String key = ASSIGN_AMOUNT_NO_SETTLE;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(0).getCell(key).setValue(
				AimCostHandler.getResource("NoSettleContract"));
		tblMain.getHeadRow(1).getCell(key).setValue(
				AimCostHandler.getResource("AssignAmount"));
		for (int i = 0; i < this.changeTypes.size(); i++) {
			ChangeTypeInfo changeTypeInfo = this.changeTypes.get(i);
			key = changeTypeInfo.getId().toString() + NO_SETTLE;
			col = tblMain.addColumn(colIndex++);
			col.setKey(key);
			tblMain.getHeadRow(0).getCell(key).setValue(
					AimCostHandler.getResource("NoSettleContract"));
			tblMain.getHeadRow(1).getCell(key).setValue(
					changeTypeInfo.getName());
			col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			col.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
		}
		col = tblMain.addColumn(colIndex++);
		key = SUB_TOTAL_NO_SETTLE;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(0).getCell(key).setValue(
				AimCostHandler.getResource("NoSettleContract"));
		tblMain.getHeadRow(1).getCell(key).setValue(
				AimCostHandler.getResource("ContractSubTotal"));

		tblMain.getHeadMergeManager().mergeBlock(0, 2, 0, colIndex - 1);
		col = tblMain.addColumn(colIndex++);
		key = ASSIGN_AMOUNT_SETTLE;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(0).getCell(key).setValue(
				AimCostHandler.getResource("SettleContract"));
		tblMain.getHeadRow(1).getCell(key).setValue(
				AimCostHandler.getResource("AssignAmount"));
		for (int i = 0; i < this.changeTypes.size(); i++) {
			ChangeTypeInfo changeTypeInfo = this.changeTypes.get(i);
			key = changeTypeInfo.getId().toString() + SETTLE;
			col = tblMain.addColumn(colIndex++);
			col.setKey(key);
			tblMain.getHeadRow(0).getCell(key).setValue(
					AimCostHandler.getResource("SettleContract"));
			tblMain.getHeadRow(1).getCell(key).setValue(
					changeTypeInfo.getName());
			col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			col.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
		}

		col = tblMain.addColumn(colIndex++);
		key = SETTLE_ADJUST;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(0).getCell(key).setValue(
				AimCostHandler.getResource("SettleContract"));
		tblMain.getHeadRow(1).getCell(key).setValue(
				AimCostHandler.getResource("SettleAdjust"));

		col = tblMain.addColumn(colIndex++);
		key = SUB_TOTAL_SETTLE;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(0).getCell(key).setValue(
				AimCostHandler.getResource("SettleContract"));
		tblMain.getHeadRow(1).getCell(key).setValue(
				AimCostHandler.getResource("ContractSubTotal"));
		tblMain.getHeadMergeManager().mergeBlock(0,
				this.changeTypes.size() + 4, 0, colIndex - 1);

	}

	/**
	 * 获取query中的主键列名称，返回供编辑/删除时获取主键用，默认值为"id"，继承类可以重载
	 * 
	 */
	protected String getKeyFieldName() {
		return "acctNumber";
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		this.fillMainTable(getSelectObjId());
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		fillMainTable(selectObjId);
		int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
		tblMain.getViewManager().freeze(0, acctNameIndex);
	}

	private String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.getId().toString();
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {

			}
			FullOrgUnitInfo info = oui.getUnit();
			return info.getId().toString();
		}
		return null;
	}

	public void fillMainTable(String objectId) throws Exception {
		tblMain.removeRows();
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		FilterInfo acctFilter = new FilterInfo();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("curProject.id", objectId));
		} else {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id", objectId));
		}
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		TreeModel costAcctTree = FDCClientHelper.createDataTree(
				CostAccountFactory.getRemoteInstance(), acctFilter);
		if(acctMaps.containsKey(objectId)){
			acctMap=(Map)acctMaps.get(objectId);
		}else{
			acctMap=this.initAcct(acctFilter);
			acctMaps.put(objectId, acctMap);
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree
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
		this.tblMain.getTreeColumn().setDepth(maxLevel);
		fetchData(objectId);
/*		setApportionData();
		initAimCostData(objectId);
		initDynamicCostData(objectId);
		this.happenGetter = new HappenDataGetter(objectId, true, true);
*/
		// addTotalRows();
		for (int i = 0; i < root.getChildCount(); i++) {
			fillNode((DefaultMutableTreeNode) root.getChildAt(i));
		}
		this.setUnionData();
	}

	private void addTotalRows() {
		IRow row = tblMain.addRow();
		row.setTreeLevel(0);
		Color totalColor = new Color(0xF0AAA9);
		row.getStyleAttributes().setBackground(totalColor);
		row.getCell("acctName").setValue(
				AimCostHandler.getResource("ProjectTotal"));
		row = tblMain.addRow();
		row.setTreeLevel(0);
		row.getStyleAttributes().setBackground(totalColor);
		row.getCell("acctName").setValue(
				AimCostHandler.getResource("NoSplitTotal"));
		row = tblMain.addRow();
		row.setTreeLevel(0);
		row.getStyleAttributes().setBackground(totalColor);
		row.getCell("acctName").setValue(
				AimCostHandler.getResource("SplitTotal"));
	}

	private void setApportionData() throws BOSException, SQLException,
			EASBizException {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return;
		}
		String selectObjId = this.getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		// String[] projectIds = null;
		// if (node.getUserObject() instanceof CurProjectInfo) {
		// CurProjectInfo project = (CurProjectInfo) node.getUserObject();
		// // projectIds = FDCHelper.getProjectLeafIds(project);
		// projectIds = new String[] { project.getId().toString() };
		// } else {
		// OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
		// if (oui.getUnit() == null) {
		// return;
		// }
		// FullOrgUnitInfo info = oui.getUnit();
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// view.setFilter(filter);
		// String[] leafIds = FDCHelper.getFullOrgUnitLeafIds(info);
		// filter.getFilterItems().add(
		// new FilterItemInfo("fullOrgUnit", FDCHelper
		// .getSetByArray(leafIds), CompareType.INCLUDE));
		// filter.getFilterItems().add(
		// new FilterItemInfo("isLeaf", Boolean.TRUE));
		// CurProjectCollection projects = CurProjectFactory
		// .getRemoteInstance().getCurProjectCollection(view);
		// projectIds = new String[projects.size()];
		// for (int i = 0; i < projectIds.length; i++) {
		// projectIds[i] = projects.get(i).getId().toString();
		// }
		// }
		buildArea = FDCHelper.getApportionValue(selectObjId,
				ApportionTypeInfo.buildAreaType, ProjectStageEnum.DYNCOST);
		sellArea = FDCHelper.getApportionValue(selectObjId,
				ApportionTypeInfo.sellAreaType, ProjectStageEnum.DYNCOST);
	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData() {
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		amountColumns.add(ASSIGN_AMOUNT_SETTLE);
		amountColumns.add(ASSIGN_AMOUNT_NO_SETTLE);
		for (int j = 0; j < this.changeTypes.size(); j++) {
			amountColumns
					.add(changeTypes.get(j).getId().toString() + NO_SETTLE);
			amountColumns.add(changeTypes.get(j).getId().toString() + SETTLE);
		}
		amountColumns.add(SETTLE_ADJUST);
		amountColumns.add(SUB_TOTAL_NO_SETTLE);
		amountColumns.add(SUB_TOTAL_SETTLE);
		amountColumns.add(NO_TEXT);
		amountColumns.add(HAS_PAY);
		amountColumns.add(AIM_COST);
		amountColumns.add(HAS_HAPPEN);
		amountColumns.add(INTENDING_HAPPEN);
		amountColumns.add(DYNAMIC_COST);
		amountColumns.add(DIFF);
		amountColumns.add(SELL_PART);
		amountColumns.add(BUILD_PART);
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					BigDecimal amount = FMConstants.ZERO;
					boolean hasData = false;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						if (rowAdd.getCell(colName).getStyleAttributes()
								.getFontColor().equals(Color.RED)) {
						}
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(
										((Integer) value).toString()));
							}
							hasData = true;
						}
					}
					if (hasData) {
						row.getCell(colName).setValue(amount);
					}
				}
			}
		}
		if(amountColumns.size()>0){
			String[] columns=new String[amountColumns.size()];
			amountColumns.toArray(columns);
			try{
				AimCostClientHelper.setTotalCostRow(table, columns);
			}catch(Exception e){
				handUIException(e);
			}
		}
		// IRow projectRow = table.getRow(0);
		// IRow noSplitRow = table.getRow(1);
		// IRow splitRow = table.getRow(2);
		// for (int i = 0; i < amountColumns.size(); i++) {
		// String colName = (String) amountColumns.get(i);
		// BigDecimal amount = FMConstants.ZERO;
		// boolean hasData = false;
		// for (int rowIndex = 0; rowIndex < zeroLeverRowList.size();
		// rowIndex++) {
		// IRow rowAdd = (IRow) zeroLeverRowList.get(rowIndex);
		// Object value = rowAdd.getCell(colName).getValue();
		// if (value != null) {
		// if (value instanceof BigDecimal) {
		// amount = amount.add((BigDecimal) value);
		// } else if (value instanceof Integer) {
		// amount = amount.add(new BigDecimal(((Integer) value)
		// .toString()));
		// }
		// hasData = true;
		// }
		// }
		// if (hasData) {
		// splitRow.getCell(colName).setValue(amount);
		// }
		// }
		// for (int i = 0; i < amountColumns.size(); i++) {
		// String colName = (String) amountColumns.get(i);
		// BigDecimal projectValue = (BigDecimal)
		// projectRow.getCell(colName).getValue();
		// BigDecimal splitValue = (BigDecimal)
		// splitRow.getCell(colName).getValue();
		// if (projectValue == null&&splitValue == null) {
		// continue;
		// }
		// if (projectValue == null) {
		// projectValue=FDCHelper.ZERO;
		// }
		// if (splitValue == null) {
		// splitValue=FDCHelper.ZERO;
		// }
		// BigDecimal noSplitValue = projectValue.subtract(splitValue);
		// noSplitRow.getCell(colName).setValue(noSplitValue);
		// }

	}

	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {

	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnChart.setIcon(EASResource.getIcon("imgTbtn_planchart"));
		this.menuChat.setIcon(EASResource.getIcon("imgTbtn_planchart"));
		menuItemSave.setIcon(FDCClientHelper.ICON_SAVE);
		btnSave.setIcon(FDCClientHelper.ICON_SAVE);
		actionMonthSave.setEnabled(false);
		actionMonthSave.setVisible(false);
		btnMonthSave.setEnabled(false);
		btnMonthSave.setVisible(false);
		menuBiz.setVisible(false);
//		btnMonthSave.setIcon(EASResource.getIcon("imgTbtn_closeinitialize"));
	}

	public void showChart(int type) {
		ChartData data = new ChartData();
		if (type == 1) {
			List colKeys = new ArrayList();
			colKeys.add(AIM_COST);
			colKeys.add(DYNAMIC_COST);
			String[] serials = new String[colKeys.size()];
			for (int i = 0; i < colKeys.size(); i++) {
				serials[i] = (String) tblMain.getHeadRow(0).getCell(
						(String) colKeys.get(i)).getValue();
			}
			data.setSeriesKeys(serials);
			List rows = new ArrayList();
			for (int i = 0; i < tblMain.getSelectManager().size(); i++) {
				KDTSelectBlock selectBlock = tblMain.getSelectManager().get(i);
				for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
					IRow row = this.tblMain.getRow(j);
					rows.add(row);
				}
			}

			for (int i = 0; i < rows.size(); i++) {
				fillChartDataByRow(data, (IRow) rows.get(i), colKeys);
			}
			data.setChartType(ChartType.CT_COLUMNCLUSTERED3D);
		} else if (type == 2) {
			String noText = (String) tblMain.getHeadRow(0).getCell(NO_TEXT)
					.getValue();
			String intendingHappen = (String) tblMain.getHeadRow(0).getCell(
					INTENDING_HAPPEN).getValue();
			String[] serials = new String[] { "合同性成本", noText, intendingHappen };
			data.setSeriesKeys(serials);

			List rows = new ArrayList();
			for (int i = 0; i < tblMain.getSelectManager().size(); i++) {
				KDTSelectBlock selectBlock = tblMain.getSelectManager().get(i);
				for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
					IRow row = this.tblMain.getRow(j);
					rows.add(row);
				}
			}
			for (int i = 0; i < rows.size(); i++) {
				IRow row = (IRow) rows.get(i);
				BigDecimal[] values = new BigDecimal[serials.length];
				BigDecimal subNoSettle = (BigDecimal) row.getCell(
						SUB_TOTAL_NO_SETTLE).getValue();
				BigDecimal subSettle = (BigDecimal) row.getCell(
						SUB_TOTAL_SETTLE).getValue();
				if (subNoSettle == null) {
					subNoSettle = FDCHelper.ZERO;
				}
				if (subSettle == null) {
					subSettle = FDCHelper.ZERO;
				}
				values[0] = subNoSettle.add(subSettle);
				values[1] = (BigDecimal) row.getCell(NO_TEXT).getValue();
				values[2] = (BigDecimal) row.getCell(INTENDING_HAPPEN)
						.getValue();
				data.addGroupData(
						row.getCell("acctName").getValue().toString(), values);
			}
			data.setChartType(ChartType.CT_MULTIPIE);
		}
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		// colKeys.add("intendingHappen");

		data.setTitle(proNode.getText());

		try {
			ChartUI.showChart(this, data);
		} catch (UIException e) {
			e.printStackTrace();
		}
	}

	private void fillChartDataByRow(ChartData data, IRow row, List colKeys) {
		BigDecimal[] values = new BigDecimal[colKeys.size()];
		for (int k = 0; k < colKeys.size(); k++) {
			values[k] = (BigDecimal) row.getCell((String) colKeys.get(k))
					.getValue();
		}
		data
				.addGroupData(row.getCell("acctName").getValue().toString(),
						values);
	}

	public void onLoad() throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.getSorter().add(new SorterItemInfo("number"));
		view.getSelector().add("id");
		view.getSelector().add("name");
		this.changeTypes = ChangeTypeFactory.getRemoteInstance()
				.getChangeTypeCollection(view);
		super.onLoad();
		initControl();
		actionSave.setEnabled(true);
//		actionSave.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
		menuItemSave.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
//		getActionMap().put("actionSave", actionSave);
//		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl S"),"actionSave");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl shift S"),"actionSave");
		actionMonthSave.setEnabled(true);
//		actionSave.putValue(Action.ACCELERATOR_KEY, ks);
		FDCClientHelper.setUIMainMenuAsTitle(this);
	}

	private void initControl() {
		this.menuEdit.setVisible(false);
		this.separatorEdit1.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.actionLocate.setEnabled(false);
		this.menuItemSubmit.setVisible(false);
		KDMenuItem menuItem1 = new KDMenuItem();
		menuItem1.setAction(new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				showChart(1);
			}
		});
		menuItem1.setText("全项目成本差额图");
		this.btnChart.addAssistMenuItem(menuItem1);
		KDMenuItem menuItem2 = new KDMenuItem();
		menuItem2.setAction(new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				showChart(2);
			}
		});
		menuItem2.setText("动态成本构成图");
		this.btnChart.addAssistMenuItem(menuItem2);
		this.menuItemChart1.setAction(new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				showChart(1);
			}
		});
		this.menuItemChart1.setText("全项目成本差额图");
		this.menuItemChart2.setAction(new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				showChart(2);
			}
		});
		this.menuItemChart2.setText("动态成本构成图");
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
	}

	private void fillNode(DefaultMutableTreeNode node) throws BOSException,
			SQLException, EASBizException {
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (!proNode.isLeaf()) {
			return;
		}
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		String acctId = costAcct.getId().toString();
		String longNumber=costAcct.getLongNumber();
		if(!assignedMap.containsKey(longNumber)){
			return;
		}
		IRow row = tblMain.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("acctNumber").setValue(
				costAcct.getLongNumber().replace('!', '.'));
		row.getCell("acctName").setValue(costAcct.getName());
		if (node.isLeaf()) {
			BigDecimal aimAmount = (BigDecimal) this.aimCostMap.get(acctId);
			row.getCell(AIM_COST).setValue(aimAmount);
			HappenDataInfo happenDataInfo = (HappenDataInfo) this.happenGetter.conSplitMap
					.get(acctId + 0);
			BigDecimal noSettConAmount = null;
			if (happenDataInfo != null) {
				noSettConAmount = happenDataInfo.getAmount();
			}
			row.getCell(ASSIGN_AMOUNT_NO_SETTLE).setValue(noSettConAmount);
			BigDecimal noSettleChangeSumAmount = null;
			for (int i = 0; i < changeTypes.size(); i++) {
				ChangeTypeInfo change = changeTypes.get(i);
				happenDataInfo = (HappenDataInfo) this.happenGetter.changeSplitMap
						.get(acctId + change.getId().toString() + 0);
				BigDecimal changeAmount = null;
				if (happenDataInfo != null) {
					changeAmount = happenDataInfo.getAmount();
				}
				if (changeAmount != null) {
					row.getCell(change.getId().toString() + NO_SETTLE)
							.setValue(changeAmount);
					if (noSettleChangeSumAmount == null) {
						noSettleChangeSumAmount = FDCHelper.ZERO;
					}
					noSettleChangeSumAmount = noSettleChangeSumAmount
							.add(changeAmount);
				}
			}
			BigDecimal noSettleTotal = null;
			if (noSettConAmount != null) {
				noSettleTotal = noSettConAmount;
			}
			if (noSettleChangeSumAmount != null) {
				if (noSettleTotal == null) {
					noSettleTotal = noSettleChangeSumAmount;
				} else {
					noSettleTotal = noSettleTotal.add(noSettleChangeSumAmount);
				}
			}
			row.getCell(SUB_TOTAL_NO_SETTLE).setValue(noSettleTotal);
			happenDataInfo = (HappenDataInfo) this.happenGetter.conSplitMap
					.get(acctId + 1);
			BigDecimal settConAmount = null;
			if (happenDataInfo != null) {
				settConAmount = happenDataInfo.getAmount();
			}
			row.getCell(ASSIGN_AMOUNT_SETTLE).setValue(settConAmount);
			BigDecimal settleChangeSumAmount = null;
			for (int i = 0; i < changeTypes.size(); i++) {
				ChangeTypeInfo change = changeTypes.get(i);
				happenDataInfo = (HappenDataInfo) this.happenGetter.changeSplitMap
						.get(acctId + change.getId().toString() + 1);
				BigDecimal changeAmount = null;
				if (happenDataInfo != null) {
					changeAmount = happenDataInfo.getAmount();
				}
				if (changeAmount != null) {
					row.getCell(change.getId().toString() + SETTLE).setValue(
							changeAmount);
					if (settleChangeSumAmount == null) {
						settleChangeSumAmount = FDCHelper.ZERO;
					}
					settleChangeSumAmount = settleChangeSumAmount
							.add(changeAmount);
				}
			}
			happenDataInfo = (HappenDataInfo) this.happenGetter.settleSplitMap
					.get(acctId);
			BigDecimal settleTotal = null;
			if (happenDataInfo != null) {
				settleTotal = happenDataInfo.getAmount();
			}
			row.getCell(SUB_TOTAL_SETTLE).setValue(settleTotal);

			BigDecimal settleAdjust = null;
			if (settleTotal != null) {
				settleAdjust = settleTotal;
			}
			if (settConAmount != null) {
				if (settleAdjust == null) {
					settleAdjust = FDCHelper.ZERO;
				}
				settleAdjust = settleAdjust.subtract(settConAmount);
			}
			if (settleChangeSumAmount != null) {
				if (settleAdjust == null) {
					settleAdjust = FDCHelper.ZERO;
				}
				settleAdjust = settleAdjust.subtract(settleChangeSumAmount);
			}
			row.getCell(SETTLE_ADJUST).setValue(settleAdjust);

			happenDataInfo = (HappenDataInfo) this.happenGetter.noTextSplitMap
					.get(acctId);
			BigDecimal noTextAmount = null;
			if (happenDataInfo != null) {
				noTextAmount = happenDataInfo.getAmount();
			}
			row.getCell(NO_TEXT).setValue(noTextAmount);

			happenDataInfo = (HappenDataInfo) this.happenGetter.paySplitMap
					.get(acctId);
			BigDecimal hasPayAmount = null;
			if (happenDataInfo != null) {
				hasPayAmount = happenDataInfo.getAmount();
			}
			row.getCell(HAS_PAY).setValue(hasPayAmount);

			BigDecimal hasHappenAmount = null;
			if (noSettleTotal != null) {
				hasHappenAmount = noSettleTotal;
			}
			if (settleTotal != null) {
				if (hasHappenAmount == null) {
					hasHappenAmount = FDCHelper.ZERO;
				}
				hasHappenAmount = hasHappenAmount.add(settleTotal);
			}
			if (noTextAmount != null) {
				if (hasHappenAmount == null) {
					hasHappenAmount = FDCHelper.ZERO;
				}
				hasHappenAmount = hasHappenAmount.add(noTextAmount);
			}
			row.getCell(HAS_HAPPEN).setValue(hasHappenAmount);

			BigDecimal adjustAmount = null;
			BigDecimal dynamicAmount = null;
			// 动态成本跟节点要求汇总
			if (proNode.isLeaf()) {
				adjustAmount = (BigDecimal) this.dynamicCostMap.get(acctId);
			} else {
				adjustAmount = getSumAdjustCol(acctId);
			}
			if (aimAmount != null) {
				dynamicAmount = aimAmount;
			}
			if (adjustAmount != null) {
				if (dynamicAmount == null) {
					dynamicAmount = adjustAmount;
				} else {
					dynamicAmount = dynamicAmount.add(adjustAmount);
				}
			}
			row.getCell(DYNAMIC_COST).setValue(dynamicAmount);

			BigDecimal intendingHappen = null;
			if (dynamicAmount != null) {
				intendingHappen = dynamicAmount;
			}
			if (hasHappenAmount != null) {
				if (intendingHappen == null) {
					intendingHappen = FDCHelper.ZERO;
				}
				intendingHappen = intendingHappen.subtract(hasHappenAmount);
			}
			row.getCell(INTENDING_HAPPEN).setValue(intendingHappen);
			BigDecimal diff = null;
			if (dynamicAmount != null) {
				diff = dynamicAmount;
			}
			if (aimAmount != null) {
				if (diff == null) {
					diff = FDCHelper.ZERO;
				}
				diff = diff.subtract(aimAmount);
			}
			row.getCell(DIFF).setValue(diff);

			BigDecimal sellPart = null;
			if (dynamicAmount != null && sellArea != null
					&& sellArea.compareTo(FDCHelper.ZERO) != 0) {
				sellPart = dynamicAmount.divide(sellArea,2,
						BigDecimal.ROUND_HALF_UP);
				row.getCell(SELL_PART).setValue(sellPart);
			}

			BigDecimal buildPart = null;
			if (dynamicAmount != null && buildArea != null
					&& buildArea.compareTo(FDCHelper.ZERO) != 0) {
				buildPart = dynamicAmount.divide(buildArea,2,
						BigDecimal.ROUND_HALF_UP);
				row.getCell(BUILD_PART).setValue(buildPart);
			}
			// 区分叶子节点
			row.setUserObject(costAcct);
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			this.fillNode((DefaultMutableTreeNode) node.getChildAt(i));
		}
	}

	private BigDecimal getSumAdjustCol(String acctId) throws BOSException,
			SQLException {
		StringBuffer innerSql = new StringBuffer();
		innerSql.append("select fid from " + FDCHelper.getFullAcctSql()
				+ " where ");
		String fullNumber = "";
		CostAccountInfo acct = (CostAccountInfo) this.acctMap.get(acctId);
		if (acct.getCurProject() != null) {
			fullNumber = acct.getCurProject().getFullOrgUnit().getLongNumber()
					+ "!" + acct.getCurProject().getLongNumber();
		} else {
			fullNumber = acct.getFullOrgUnit().getLongNumber();
		}
		String longNumber = acct.getLongNumber();
		innerSql.append(" (FLongNumber ='").append(longNumber).append("'")
				.append(" or FLongNumber like '").append(longNumber).append(
						"!%' ").append(" or FLongNumber like '%!").append(
						longNumber).append("!%') ");
		innerSql.append("and (FullNumber =	'").append(fullNumber).append("'")
				.append(" or FullNumber like '").append(fullNumber).append(
						"!%' ").append(" or FullNumber like '%!").append(
						fullNumber).append("!%') ");

		String sql = "select sum(FAdjustSumAmount) sumAmount from T_AIM_DynamicCost where FAccountId in ("
				+ innerSql.toString() + ")";
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		BigDecimal adjustAmount = null;
		while (rs.next()) {
			adjustAmount = rs.getBigDecimal("sumAmount");
		}
		if (adjustAmount != null && adjustAmount.compareTo(FDCHelper.ZERO) == 0) {
			return null;
		}
		return adjustAmount;
	}

	private void initDynamicCostData(String objectId) throws BOSException {
		dynamicCostMap.clear();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			filter.getFilterItems().add(
					new FilterItemInfo("account.curProject.id", objectId));
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("account.fullOrgUnit.id", objectId));
		}
		view.getSelector().add(new SelectorItemInfo("*"));
		DynamicCostCollection DynamicCostCollection = DynamicCostFactory
				.getRemoteInstance().getDynamicCostCollection(view);
		for (int i = 0; i < DynamicCostCollection.size(); i++) {
			DynamicCostInfo info = DynamicCostCollection.get(i);
			CostAccountInfo acct = info.getAccount();
			dynamicCostMap.put(acct.getId().toString(), info
					.getAdjustSumAmount());
		}
	}

	private void initAimCostData(String objectId) throws BOSException,
			SQLException {
		aimCostMap.clear();
		EntityViewInfo aimView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		aimView.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("orgOrProId", objectId));
		filter.getFilterItems().add(
				new FilterItemInfo("isLastVersion", new Integer(1)));
		aimView.getSelector().add(new SelectorItemInfo("*"));
		aimView.getSelector().add(new SelectorItemInfo("costEntry.*"));
		AimCostCollection aimCostCollection = AimCostFactory
				.getRemoteInstance().getAimCostCollection(aimView);
		if (aimCostCollection.size() >= 1) {
			CostEntryCollection costEntrys = aimCostCollection.get(0)
					.getCostEntry();
			for (int i = 0; i < costEntrys.size(); i++) {
				CostEntryInfo entry = costEntrys.get(i);
				CostAccountInfo costAccount = entry.getCostAccount();
				BigDecimal value = entry.getCostAmount();
				if (value == null) {
					value = FDCHelper.ZERO;
				}
				if (aimCostMap.containsKey(costAccount.getId().toString())) {
					BigDecimal sum = (BigDecimal) aimCostMap.get(costAccount
							.getId().toString());
					aimCostMap.put(costAccount.getId().toString(), sum
							.add(value));

				} else {
					aimCostMap.put(costAccount.getId().toString(), value);
				}
			}
		}
	}
	private Map acctNumberMap=new HashMap();
	private Map initAcct(FilterInfo acctFilter) throws BOSException {
		Map acctMap=new HashMap();
		acctNumberMap.clear();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("longNumber"));
		sel.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
		sel.add(new SelectorItemInfo("curProject.longNumber"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit.longNumber"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(acctFilter);
		CostAccountCollection accts = CostAccountFactory.getRemoteInstance()
				.getCostAccountCollection(view);
		for (int i = 0; i < accts.size(); i++) {
			CostAccountInfo info = accts.get(i);
			acctMap.put(info.getId().toString(), info);
			acctNumberMap.put(info.getLongNumber(), info);
		}
		return acctMap;
	}

	public void actionChart_actionPerformed(ActionEvent e) throws Exception {
		this.showChart(1);
	}

	/**
	 * 保存全项目动态成本表和各产品类型动态成本表的数据到成本数据库(动态成本快照)
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {

		saveSnapShot(false);

	}

	public void actionMonthSave_actionPerformed(ActionEvent e) throws Exception {
		saveSnapShot(true);
	}
	
	private void saveSnapShot(boolean isMonthSave) throws BOSException, EASBizException {
		FDCClientUtils.checkSelectProj(this, (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent());

		int rowCount2 = getMainTable().getRowCount();
		String selectProjId = getSelectObjId();
		if (selectProjId == null || rowCount2 == 0)
			return;
		CostMonthlySaveTypeEnum savedType=isMonthSave?CostMonthlySaveTypeEnum.AUTOSAVE:CostMonthlySaveTypeEnum.COMMON;
//		savedType=CostMonthlySaveTypeEnum.COSTMONTHLY;
		FDCCostRptFacadeFactory.getRemoteInstance().saveSnapShot(selectProjId, savedType);
		FDCClientUtils.showOprtOK(this);
		if(true) return;
		//已废弃,全部移动了服务端
		PeriodInfo glCurPeriod = null;
		if(isMonthSave) {
			glCurPeriod = FDCClientUtils.getGLCurCompanyCurPeriod();
			if(glCurPeriod != null && glCurPeriod.getId() != null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("projectId", selectProjId));
				filter.appendFilterItem("period.id", glCurPeriod.getId().toString());
				filter.appendFilterItem("isUsed", Boolean.TRUE);
				
				boolean exists = DynCostSnapShotFactory.getRemoteInstance().exists(filter);
				
				if(exists) {
					MsgBox.showWarning(this, "该工程项目当前期间已经月结，且竣工结算账务处理已使用此数据，不能再月结");
					SysUtil.abort();
				}
			}
			else {
				MsgBox.showWarning(this, "无法获取当前财务组织的总账当前期间，不能月结");
				SysUtil.abort();
			}
		}
		
		
		// 如果今天/当前期的已经存在，删除
		java.util.Date currentDate = DateTimeUtils.truncateDate(FDCSQLFacadeFactory.getRemoteInstance().getServerTime());
		FilterInfo filter = new FilterInfo();
		if(isMonthSave) {
			filter.getFilterItems().add(
					new FilterItemInfo("projectId", selectProjId));
			filter.appendFilterItem("period.id", glCurPeriod.getId().toString());
			filter.appendFilterItem("isMonthSave", Boolean.TRUE);
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("projectId", selectProjId));
			filter.getFilterItems().add(
					new FilterItemInfo("snapShotDate", currentDate));
			filter.getFilterItems().add(new FilterItemInfo("isMonthSave", Boolean.FALSE));
	
		}
		boolean exist = DynCostSnapShotFactory.getRemoteInstance().exists(
				filter);
		if (exist) {
			DynCostSnapShotFactory.getRemoteInstance().delete(filter);
		}


		BOSUuid projId = BOSUuid.read(selectProjId);
		DynCostSnapShotInfo info = null;
		DynCostSnapShotSettEntryInfo setEntryInfo = null;

		DynCostSnapShotSettEntryCollection setEntryColl = null;

		int noSettStartIdx = getMainTable().getColumn(ASSIGN_AMOUNT_NO_SETTLE)
				.getColumnIndex() + 1;
		int noSettEndIdx = getMainTable().getColumn(SUB_TOTAL_NO_SETTLE)
				.getColumnIndex();
		int settStartIdx = getMainTable().getColumn(ASSIGN_AMOUNT_SETTLE)
				.getColumnIndex() + 1;
		int settEndIdx = getMainTable().getColumn(SETTLE_ADJUST)
				.getColumnIndex();

		// 获得各产品类型动态成本表的Table
		KDTable productCostTable = DynamicProductTableFill
				.getTable(selectProjId);
		AimCostSplitDataGetter aimGetter = new AimCostSplitDataGetter(selectProjId);
		HasPaySplitDataGetter hasPayGetter=new HasPaySplitDataGetter(selectProjId,aimGetter,this.happenGetter);
		int productStartIdx = productCostTable
				.getColumnIndex(DynamicProductTableFill.APPORTION_TYPE) + 1;

		int columnCount = productCostTable.getColumnCount();
		
//		Map costPayedMap = getCostPayedSplitData(selectProjId);
		
		for (int i = 0; i < rowCount2; i++) {
			IRow row = getMainTable().getRow(i);
			CostAccountInfo costAcct = (CostAccountInfo) row.getUserObject();
			if (costAcct == null)
				continue;
			info = new DynCostSnapShotInfo();
			info.setProjectId(projId);
			info.setCostAccountId(costAcct.getId());
			String ctLongNumber = costAcct.getLongNumber();
			if (!FDCHelper.isEmpty(ctLongNumber)) {
				ctLongNumber = ctLongNumber.replaceAll("!", ".");
			}
			info.setCostAcctLgNumber(ctLongNumber);

			info.setUnSettSignAmt((BigDecimal) row.getCell(
					ASSIGN_AMOUNT_NO_SETTLE).getValue());

			setEntryColl = new DynCostSnapShotSettEntryCollection();

			for (int j = noSettStartIdx; j < noSettEndIdx; j++) {
				BigDecimal value = (BigDecimal) row.getCell(j).getValue();
				setEntryInfo = new DynCostSnapShotSettEntryInfo();
				String colKey = getMainTable().getColumnKey(j);
				String changeTypeId = colKey.replaceAll(NO_SETTLE, "");
				setEntryInfo.setChangeTypeId(BOSUuid.read(changeTypeId));
				value = value == null ? null : value.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				setEntryInfo.setUnSettleAmt(value);
				setEntryColl.add(setEntryInfo);
			}

			info.setSettSignAmt((BigDecimal) row.getCell(ASSIGN_AMOUNT_SETTLE)
					.getValue());

			for (int j = settStartIdx, k = 0; j < settEndIdx; j++, k++) {
				BigDecimal value = (BigDecimal) row.getCell(j).getValue();
				setEntryInfo = setEntryColl.get(k);
				String colKey = getMainTable().getColumnKey(j);
				String changeTypeId = colKey.replaceAll(SETTLE, "");
				setEntryInfo.setChangeTypeId(BOSUuid.read(changeTypeId));
				value = setScale(value);
				setEntryInfo.setSettleAmt(value);
			}

			info.getSettEntries().addCollection(setEntryColl);

			BigDecimal value = (BigDecimal) row.getCell(SETTLE_ADJUST)
					.getValue();
			value = setScale(value);
			info.setSettAdjustAmt(value);

			BigDecimal value2 = (BigDecimal) row.getCell(NO_TEXT).getValue();
			value2 = setScale(value2);
			info.setUnContractCostAmt(value2);
			BigDecimal value3 = (BigDecimal) row.getCell(HAS_HAPPEN).getValue();
			value3 = setScale(value3);
			info.setSoFarHasAmt(value3);
			BigDecimal value4 = (BigDecimal) row.getCell(INTENDING_HAPPEN)
					.getValue();
			value4 = setScale(value4);
			info.setSoFarToAmt(value4);
			BigDecimal value5 = (BigDecimal) row.getCell(DYNAMIC_COST)
					.getValue();
			value5 = setScale(value5);
			info.setDynCostAmt(value5);
			BigDecimal value6 = (BigDecimal) row.getCell(AIM_COST).getValue();
			value6 = setScale(value6);
			info.setAimCostAmt(value6);
			BigDecimal value7 = (BigDecimal) row.getCell(DIFF).getValue();
			value7 = setScale(value7);
			info.setDiffAmt(value7);
			BigDecimal value8 = (BigDecimal) row.getCell(SELL_PART).getValue();
			value8 = setScale(value8);
			info.setSalableAmt(value8);
			BigDecimal value9 = (BigDecimal) row.getCell(BUILD_PART).getValue();
			value9 = setScale(value9);
			info.setConstrAmt(value9);

			/*
			 * 各产品类型动态成本表
			 */
			IRow pcRow = productCostTable.getRow(i);

			String appId = (String) pcRow.getCell(
					DynamicProductTableFill.APPORTION_TYPE).getValue();
			BOSUuid appUuId = appId == null ? null : BOSUuid.read(appId);
			info.setApprotionTypeId(appUuId);

			DynCostSnapShotProTypEntryCollection proTypeEntryColl = new DynCostSnapShotProTypEntryCollection();
			DynCostSnapShotProTypEntryInfo proTypeEntryInfo = null;
			for (int j = productStartIdx; j < columnCount;) {
				String key = productCostTable.getColumn(j).getKey();
				key = key.replaceAll(DynamicProductTableFill.AIM_SELL, "");
				proTypeEntryInfo = new DynCostSnapShotProTypEntryInfo();
				proTypeEntryInfo.setProductTypeId(BOSUuid.read(key));

				BigDecimal aimSale = (BigDecimal) pcRow.getCell(j++).getValue();
				BigDecimal aim = (BigDecimal) pcRow.getCell(j++).getValue();
				BigDecimal hasHap = (BigDecimal) pcRow.getCell(j++).getValue();
				BigDecimal notHap = (BigDecimal) pcRow.getCell(j++).getValue();
				BigDecimal dynSale = (BigDecimal) pcRow.getCell(j++).getValue();
				BigDecimal dyn = (BigDecimal) pcRow.getCell(j++).getValue();
				
				Map hasPayProductMap = hasPayGetter.getHasPayProductMap(costAcct.getId().toString());
				BigDecimal costPayed = (BigDecimal) hasPayProductMap.get(key);
				aimSale = aimSale == null ? null : aimSale.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				aim = aim == null ? null : aim.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				hasHap = hasHap == null ? null : hasHap.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				notHap = notHap == null ? null : notHap.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				dynSale = dynSale == null ? null : dynSale.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				dyn = dyn == null ? null : dyn.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				costPayed = costPayed == null ? null : costPayed.setScale(2,
						BigDecimal.ROUND_HALF_UP);
				
				proTypeEntryInfo.setAimSaleUnitAmt(aimSale);
				proTypeEntryInfo.setAimCostAmt(aim);
				proTypeEntryInfo.setHasHappenAmt(hasHap);
				proTypeEntryInfo.setNotHappenAmt(notHap);
				proTypeEntryInfo.setDynSaleUnitAmt(dynSale);
				proTypeEntryInfo.setDynCostAmt(dyn);
				proTypeEntryInfo.setCostPayedAmt(costPayed);
				
				proTypeEntryColl.add(proTypeEntryInfo);
			}

			info.getProTypEntries().addCollection(proTypeEntryColl);

			info.setSnapShotDate(currentDate);
			
			info.setIsMonthSave(isMonthSave);
			if(isMonthSave) {

				info.setPeriod(glCurPeriod);
				info.setIsUsed(false);
				Date endDate = DateTimeUtils.truncateDate(glCurPeriod.getEndDate());
				info.setSnapShotDate(endDate);
			}

			DynCostSnapShotFactory.getRemoteInstance().addnew(info);
		}

		FDCClientUtils.showOprtOK(this);
	}

	private BigDecimal setScale(BigDecimal value) {
		value = value == null ? null : value.setScale(2,
				BigDecimal.ROUND_HALF_UP);
		return value;
	}
	
	private Map getCostPayedSplitData(String projectId) {

		if (FDCHelper.isEmpty(projectId))
			return null;

		//--1、创建临时表
		FDCSQLBuilder builderTable = new FDCSQLBuilder();
		builderTable.appendSql("CREATE TABLE TEMPSplit ( " +
				"	FCostAccountId 	VARCHAR(44), " +
		"		FProductId 	VARCHAR(44),  " +
		"		FStandard 	VARCHAR(44), " +
		"		FSum 		DECIMAL, " +
		"		FOtherValue 	DECIMAL, " +
		"		FRateCur 	DECIMAL, " +
		"		FRateAmount 	DECIMAL, " +
		"		FSumAmount 	DECIMAL, " +
		"		FSumSplit 	DECIMAL  " +
		"			)");

		FDCSQLBuilder builderData = new FDCSQLBuilder();
		
		//--2、获取待拆分数据
		builderData.appendSql("INSERT TEMPSplit(FCostAccountId,FProductId,FSum,FOtherValue)  " +
		" SELECT ca.FID,productSplited.FProductId,accountSplited.AMOUNT,productSplited.PROAMOUNT FROM T_FDC_CostAccount ca  " +
		" LEFT OUTER JOIN (SELECT FCostAccountId, sum(entry.FAmount) AMOUNT FROM T_AIM_CostSplitEntry entry   " +
		" INNER JOIN T_AIM_CostSplit head ON entry.FParentId = head.FId  " +
		" INNER JOIN T_FDC_CostAccount acct ON entry.FCostAccountId = acct.FID  " +
		" WHERE head.FIsInvalid=0 And FIsProduct = 0 AND FCostBillType = 'PAYMENTSPLIT' AND acct.FCurProject=");
		builderData.appendParam(projectId);
		builderData.appendSql("GROUP BY FCostAccountId) accountSplited ON accountSplited.FCostAccountId=ca.FID  " +
		" LEFT OUTER JOIN (SELECT FCostAccountId, FProductId, sum(entry.FProdAmount) PROAMOUNT FROM T_AIM_CostSplitEntry entry   " +
		" INNER JOIN T_AIM_CostSplit head ON entry.FParentId = head.FId   " +
		" INNER JOIN T_FDC_CostAccount acct ON entry.FCostAccountId = acct.FID   " +
		" WHERE head.FIsInvalid=0 And FIsProduct = 1 AND FCostBillType = 'PAYMENTSPLIT' AND acct.FCurProject=");
		builderData.appendParam(projectId);
		builderData.appendSql("GROUP BY FCostAccountId, FProductId) productSplited ON productSplited.FCostAccountId=ca.FID  " +
		" WHERE ca.FIsLeaf = 1 AND accountSplited.AMOUNT<>0 AND ca.FCurProject=");
		builderData.appendParam(projectId);
		
		//--3、获取拆分方案
		builderData.appendSql("\n");
		builderData.appendSql(" UPDATE t1 SET 	t1.FStandard=t2.FAPPORTIONTYPEID  " +
				" FROM TEMPSplit t1  " +
				" INNER JOIN (SELECT dynCost.FAccountID, prodEntry.fproductid, prodEntry.FAPPORTIONTYPEID  " +
				" FROM T_AIM_DynCostProduSplitEntry prodEntry   " +
				" INNER JOIN T_AIM_DynamicCost dynCost on prodEntry.FDynamicCostId = dynCost.fid   " +
				" INNER JOIN T_FDC_CostAccount acct on dynCost.FAccountID = acct.fid   " +
				" WHERE acct.FIsLeaf = 1 and acct.FCurProject=");
		builderData.appendParam(projectId);
		builderData.appendSql(") t2 ON t1.FCostAccountId=t2.FAccountID AND t1.FProductId=t2.FProductId");

		//--4、设置拆分比例
		builderData.appendSql("\n");
		builderData.appendSql("UPDATE t1 SET t1.FRateCur=t2.FINDEXVALUE " +
				" FROM TEMPSplit t1 " +
				" INNER JOIN (SELECT pid.FPRODUCTTYPEID,pide.FAPPORTIONTYPEID,pide.FINDEXVALUE  " +
				" FROM T_FDC_PROJECTINDEXDATA pid " +
				" LEFT OUTER JOIN T_FDC_PROJECTINDEXDATAENTRY pide ON pide.FPARENTID=pid.FID " +
				" WHERE pid.FPROJORORGID=");
		builderData.appendParam(projectId);
		builderData.appendSql(" AND pid.FISLATESTVER=1) t2 " +
				" ON t1.FProductId=t2.FPRODUCTTYPEID AND t1.FStandard=t2.FAPPORTIONTYPEID");
		
		//--5、根据自动拆分计算：拆分比例和、拆分费用和-直接费用和
		builderData.appendSql("\n");
		builderData.appendSql("UPDATE t1 SET 	t1.FRateAmount=ISNULL(t2.FRateAmount,0),  " +
				" t1.FSumAmount=ISNULL(t1.FSum,0)-ISNULL(t2.FOtherAmount,0) " +
				" FROM TEMPSplit t1, ( " +
				" SELECT FCostAccountId,  " +
				" ISNULL(SUM(IsNULL(FRateCur,0)),0) FRateAmount,  " +
				" ISNULL(SUM(ISNULL(FOtherValue,0)),0) FOtherAmount " +
				" FROM TEMPSplit GROUP BY FCostAccountId) t2 " +
				" WHERE t1.FCostAccountId=t2.FCostAccountId");
		
		//--6、关键计算：刷新拆分明细金额，考虑总金额不等于拆分和）
		builderData.appendSql("\n");
		builderData.appendSql(" UPDATE TEMPSplit  " +
				" SET 	FSumSplit=ROUND((ISNULL(ISNULL(FRateCur,0.0)/FRateAmount,0)*ISNULL(FSumAmount,0)) + ISNULL(FOtherValue,0),2)  " +
				" WHERE ABS(FRateAmount)>0");
		//--7、处理尾差
		builderData.appendSql("\n");
		builderData.appendSql(" UPDATE t0 SET t0.FSumSplit=ROUND(t1.FSumSplit+t2.FDiff,2) " +
				" FROM TEMPSplit t0 " +
				" INNER JOIN (SELECT FCostAccountId,FSumSplit,FProductId=MIN(FProductId) FROM " +
				" (SELECT BB.FCostAccountId,BB.FSumSplit,BB.FProductId FROM TEMPSplit BB " +
				" INNER JOIN (SELECT FCostAccountId,FSumSplit=MAX(FSumSplit) FROM TEMPSplit  " +
				" WHERE ABS(FRateAmount)>0 GROUP BY FCostAccountId) AA  " +
				" ON AA.FCostAccountId=BB.FCostAccountId AND AA.FSumSplit=BB.FSumSplit) CC GROUP BY FCostAccountId,FSumSplit) t1 " + 
				" ON t0.FCostAccountId=t1.FCostAccountId AND t0.FProductId=t1.FProductId AND t0.FSumSplit=t1.FSumSplit " +
				" INNER JOIN (SELECT FCostAccountId,FDiff=AVG(ISNULL(FSumAmount,0))-SUM(ISNULL(FSumSplit,0)) " +
				" FROM TEMPSplit GROUP BY FCostAccountId) t2 ON t1.FCostAccountId=t2.FCostAccountId " +
				" WHERE ABS(t0.FRateAmount)>0");
		
		//--8、输出
		builderData.appendSql("\n");
		builderData.appendSql("SELECT * FROM TEMPSplit");
		ResultSet rs = null;
		Map costPayedMap = new HashMap();
		TableManagerFacade tmf = new TableManagerFacade();
		try {
			tmf.createTempTable(builderTable.getSql());
			rs = builderData.executeQuery();
			tmf.releaseTemporaryTableName("TEMPSplit");
			tmf.dropReleasedTable();
			
			while (rs.next()) {
				String costAccountId = rs.getString("FCostAccountId");
				String productId = rs.getString("FProductId");
				BigDecimal costPayedAmt = rs.getBigDecimal("FSumSplit");

				costPayedMap.put(costAccountId + "|" + productId,costPayedAmt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
		return costPayedMap;
	}
	
	public boolean destroyWindow() {
		final boolean isDestroyWindow = super.destroyWindow();
		if(isDestroyWindow){
			clear();
		}
		return isDestroyWindow;
	}
	
	private void fetchData(String prjId) throws BOSException,EASBizException{
		final FullDynamicCostMap fullDynamicCostMap = FDCCostRptFacadeFactory.getRemoteInstance().getFullDynamicCost(prjId, null);
		this.buildArea=fullDynamicCostMap.getDynBuildApp();
		this.sellArea=fullDynamicCostMap.getDynSellApp();
		this.aimCostMap=fullDynamicCostMap.getAimCostMap();
		this.dynamicCostMap=fullDynamicCostMap.getDynamicCostMapp();
		this.happenGetter=fullDynamicCostMap.getHappenDataGetter();
		assignedMap.clear();
		String orgId=SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		EntityViewInfo view=new  EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("objectId", prjId);
		view.getFilter().appendFilterItem("orgUnit.id", orgId);
		view.getSelector().add("*");
		view.getSelector().add("orgUnit.id");
		view.getSelector().add("costAccount.id");
		CostAcctOrgAssignCollection c = CostAcctOrgAssignFactory.getRemoteInstance().getCostAcctOrgAssignCollection(view);
		for(int i=0;i<c.size();i++){
			addCostAcctToAssignedMap(c.get(i).getCostAccount().getId().toString());
		}
	}
	
	/**
	 * release resource
	 */
	private void clear(){
		this.aimCostMap.clear();
		this.dynamicCostMap.clear();
		this.happenGetter.clear();
		this.acctMap.clear();
		this.acctMaps.clear();
	}
	private Map assignedMap=new HashMap();
    private void addCostAcctToAssignedMap(String acctId){
		CostAccountInfo acct=(CostAccountInfo)acctMap.get(acctId);
		if(acct==null){
			return;
		}
//		assignedMap.put(acct.getLongNumber(), acct);
		//添加科目上级
		String []splits=acct.getLongNumber().split("!");
		String last="";
		for(int i=0;i<splits.length;i++){
			if(i==0){
				last=splits[i];
			}else{
				last=last+"!"+splits[i];
			}
			assignedMap.put(last, acctNumberMap.get(last));
		}
    }
}