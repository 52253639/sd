/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
//import com.kingdee.bos.ctrl.r1.designercore.actions.menu.ActionSave;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.CostAcctOrgAssignCollection;
import com.kingdee.eas.fdc.aimcost.CostAcctOrgAssignFactory;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.FullDynamicCostMap;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class OrgBudgetExecUI extends AbstractOrgBudgetExecUI {
	private static final Logger logger = CoreUIObject.getLogger(OrgBudgetExecUI.class);
	private Map acctMap = new HashMap();
	private Map acctMaps = new HashMap();

	private Map dynamicCostMap = new HashMap();

	private Map aimCostMap = new HashMap();

	private HappenDataGetter happenGetter;

	/**
	 * output class constructor
	 */
	public OrgBudgetExecUI() throws Exception {
		super();
	}

	protected void initTree() throws Exception {
		this.initMainTable();
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true, (TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	/**
	 * 设置表格属性
	 * 
	 * @throws BOSException
	 */
	private void initMainTable() throws BOSException {
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		tblMain.getViewManager().setFreezeView(-1, 2);
		IRow row=tblMain.addHeadRow();
		row.getCell(1).setValue("成本科目代码");
		row.getCell(2).setValue("成本科目名称");
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		fillMainTable(selectObjId);
		int acctNameIndex = tblMain.getColumn("acctName").getColumnIndex() + 1;
		tblMain.getViewManager().freeze(0, acctNameIndex);
	}

	private String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
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
		resetTableHead();
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		FilterInfo acctFilter = new FilterInfo();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", objectId));
		} else {
			acctFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", objectId));
		}
		acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		TreeModel costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory.getRemoteInstance(), acctFilter);
		if (acctMaps.containsKey(objectId)) {
			acctMap = (Map) acctMaps.get(objectId);
		} else {
			acctMap = this.initAcct(acctFilter);
			acctMaps.put(objectId, acctMap);
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		this.tblMain.getTreeColumn().setDepth(maxLevel);
		fetchData(objectId);
		resetTableHead();
		for (int i = 0; i < root.getChildCount(); i++) {
			fillNode((DefaultMutableTreeNode) root.getChildAt(i));
		}
		this.setUnionData();
	}

	private void fillNode(DefaultMutableTreeNode node) throws BOSException, SQLException, EASBizException {
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (!proNode.isLeaf()) {
			return;
		}
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		String acctId = costAcct.getId().toString();
		IRow row = tblMain.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("id").setValue(acctId);
		row.getCell("acctNumber").setValue(costAcct.getLongNumber().replace('!', '.'));
		row.getCell("acctName").setValue(costAcct.getName());
		if (node.isLeaf()) {
			// 区分叶子节点
			row.setUserObject(costAcct);
			String orgId=(String)orgAcctMap.get(costAcct.getId().toString());
			if(orgId!=null){
				BigDecimal aimCost = (BigDecimal) this.aimCostMap.get(acctId);
				BigDecimal adjustAmount = (BigDecimal) this.dynamicCostMap.get(acctId);
				BigDecimal dynCost= FDCNumberHelper.add(aimCost, adjustAmount);
				BigDecimal noSettAmt=happenGetter.getHappenInfo(acctId+0)!=null?happenGetter.getHappenInfo(acctId+0).getAmount():null;
				BigDecimal settAmt = happenGetter.getHappenInfo(acctId)!=null?happenGetter.getHappenInfo(acctId).getAmount():null;
				BigDecimal hasHappenAmount=FDCNumberHelper.add(noSettAmt, settAmt);
				row.getCell(orgId+"aimCost").setValue(aimCost);
				row.getCell(orgId+"dynCost").setValue(dynCost);
				row.getCell(orgId+"happendCost").setValue(hasHappenAmount);
				row.getCell(orgId+"aimDiff").setValue(FDCNumberHelper.subtract(aimCost, hasHappenAmount));
				row.getCell(orgId+"dynDiff").setValue(FDCNumberHelper.subtract(dynCost, hasHappenAmount));
			}
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			this.fillNode((DefaultMutableTreeNode) node.getChildAt(i));
		}
	}

	private Map orgAcctMap = new HashMap();
	private Map orgUnitMap = new TreeMap();

	private void fetchData(String prjId) throws BOSException, EASBizException {
		final FullDynamicCostMap fullDynamicCostMap = FDCCostRptFacadeFactory.getRemoteInstance().getFullDynamicCost(prjId, null);
		this.aimCostMap=fullDynamicCostMap.getAimCostMap();
		this.dynamicCostMap=fullDynamicCostMap.getDynamicCostMapp();
		this.happenGetter=fullDynamicCostMap.getHappenDataGetter();
		// String
		// orgId=SysContext.getSysContext().getCurrentOrgUnit().getId().toString();

		orgAcctMap.clear();
		orgUnitMap.clear();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("objectId", prjId);
		// view.getFilter().appendFilterItem("orgUnit.id", orgId);
		view.getSelector().add("*");
		view.getSelector().add("orgUnit.id");
		view.getSelector().add("orgUnit.name");
		view.getSelector().add("orgUnit.longNumber");
		view.getSelector().add("costAccount.id");

		CostAcctOrgAssignCollection c = CostAcctOrgAssignFactory.getRemoteInstance().getCostAcctOrgAssignCollection(view);
		for (int i = 0; i < c.size(); i++) {
			orgAcctMap.put(c.get(i).getCostAccount().getId().toString(),c.get(i).getOrgUnit().getId().toString());
			orgUnitMap.put(c.get(i).getOrgUnit().getLongNumber(), c.get(i).getOrgUnit());
		}
	}

	private void resetTableHead() {
		for (int i = tblMain.getColumnCount() - 1; i > 2; i--) {
			tblMain.removeColumn(i);
		}
		IRow headRow1 = tblMain.getHeadRow(0);
		IRow headRow2 = tblMain.getHeadRow(1);
		int base = 3;
		int offset = 0;
		for (Iterator iter = orgUnitMap.keySet().iterator(); iter.hasNext();) {
			FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo) orgUnitMap.get(iter.next());
			IColumn c = tblMain.addColumn(base + offset);
			headRow1.getCell(base + offset).setValue(orgUnitInfo.getName());
			headRow2.getCell(base + offset).setValue("目标成本");
			c.setKey(orgUnitInfo.getId().toString()+"aimCost");
			offset++;
			c = tblMain.addColumn(base + offset);
			headRow1.getCell(base + offset).setValue(orgUnitInfo.getName());
			headRow2.getCell(base + offset).setValue("动态成本");
			c.setKey(orgUnitInfo.getId().toString()+"dynCost");
			offset++;
			c = tblMain.addColumn(base + offset);
			headRow1.getCell(base + offset).setValue(orgUnitInfo.getName());
			headRow2.getCell(base + offset).setValue("已发生成本");
			c.setKey(orgUnitInfo.getId().toString()+"happendCost");
			offset++;
			c = tblMain.addColumn(base + offset);
			headRow1.getCell(base + offset).setValue(orgUnitInfo.getName());
			headRow2.getCell(base + offset).setValue("目标执行差异");
			c.setKey(orgUnitInfo.getId().toString()+"aimDiff");
			offset++;
			c = tblMain.addColumn(base + offset);
			headRow1.getCell(base + offset).setValue(orgUnitInfo.getName());
			headRow2.getCell(base + offset).setValue("动态执行差异");
			c.setKey(orgUnitInfo.getId().toString()+"dynDiff");
			offset++;
		}
		tblMain.getHeadMergeManager().mergeBlock(0, 0, 1, 2, KDTMergeManager.FREE_ROW_MERGE);
		tblMain.getHeadMergeManager().mergeBlock(0, 3, 0, tblMain.getColumnCount()-1, KDTMergeManager.FREE_COLUMN_MERGE);
		
		FDCHelper.formatTableNumber(tblMain, 3,tblMain.getColumnCount());
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
		for(int i=3;i<tblMain.getColumnCount();i++){
			amountColumns.add(tblMain.getColumnKey(i));
		}
		 
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
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(((Integer) value).toString()));
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
		if (amountColumns.size() > 0) {
			String[] columns = new String[amountColumns.size()];
			amountColumns.toArray(columns);
			try {
				AimCostClientHelper.setTotalCostRow(table, columns);
			} catch (Exception e) {
				handUIException(e);
			}
		}

	}

	private Map acctNumberMap = new HashMap();

	private Map initAcct(FilterInfo acctFilter) throws BOSException {
		Map acctMap = new HashMap();
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
		CostAccountCollection accts = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
		for (int i = 0; i < accts.size(); i++) {
			CostAccountInfo info = accts.get(i);
			acctMap.put(info.getId().toString(), info);
			acctNumberMap.put(info.getLongNumber(), info);
		}
		return acctMap;
	}

	public void onLoad() throws Exception {
		if(!SysContext.getSysContext().getCurrentOrgUnit().isIsCU()){
			MsgBox.showInfo(this,"当前组织不是管理单元,不能查看此报表,请切换到管理单元");
			SysUtil.abort();
		}
		super.onLoad();
		FDCClientHelper.setUIMainMenuAsTitle(this);
		actionSubmit.setVisible(false);
		actionSubmit.setEnabled(false);
		actionAddNew.setVisible(false);
		actionAddNew.setEnabled(false);
		actionEdit.setVisible(false);
		actionEdit.setEnabled(false);
		actionView.setVisible(false);
		actionView.setEnabled(false);
		actionQuery.setVisible(false);
		actionQuery.setEnabled(false);
		actionRemove.setVisible(false);
		actionRemove.setEnabled(false);
		actionApportion.setVisible(false);
		actionApportion.setEnabled(false);
		actionRevert.setVisible(false);
		actionRevert.setEnabled(false);
		menuEdit.setVisible(false);
		menuEdit.setEnabled(false);
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
//		super.tblMain_tableClicked(e);
	}
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
//		super.tblMain_tableSelectChanged(e);
	}
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		treeMain_valueChanged(null);
	}
}