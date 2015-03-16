/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AdjustCostDetailUI extends AbstractAdjustCostDetailUI {

	private Map aimCostMap = new HashMap();

	private Map dynamicCostMap = new HashMap();

	public AdjustCostDetailUI() throws Exception {
		super();
	}

	// 数据对象变化时，刷新界面状态
	protected void setActionState() {

	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	protected void initTree() throws Exception {
		this.initTable();
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		this.refresh();
	}

	public void fillTable(String objectId) throws Exception {

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
		AcctAccreditHelper.handAcctAccreditFilter(null, objectId, acctFilter);
		TreeModel costAcctTree = FDCClientHelper.createDataTree(
				CostAccountFactory.getRemoteInstance(), acctFilter);
		KDTable table = this.tblMain;
		table.removeRows();
		table.setUserObject(null);
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
		table.getTreeColumn().setDepth(maxLevel);
		for (int i = 0; i < root.getChildCount(); i++) {
			fillNode(table, (DefaultMutableTreeNode) root.getChildAt(i));
		}
	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node)
			throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			MsgBox.showError("too many costAccount level!");
			return;
		}
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getStyleAttributes().setLocked(true);
		row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		row.getCell("acctNumber").setUserObject(costAcct);
		String longNumber = costAcct.getLongNumber();
		longNumber = longNumber.replace('!', '.');
		row.getCell("acctNumber").setValue(longNumber);
		row.getCell("acctName").setValue(costAcct.getName());
		if (node.isLeaf()) {
			row.setUserObject(costAcct);
			BigDecimal aimCost = (BigDecimal) this.aimCostMap.get(costAcct
					.getId().toString());
			row.getCell("aimCost").setValue(aimCost);
			DynamicCostInfo dynamic = (DynamicCostInfo) this.dynamicCostMap
					.get(costAcct.getId().toString());
			if (dynamic != null) {
				AdjustRecordEntryCollection adjustEntrys = dynamic
						.getAdjustEntrys();
				for (int i = 0; i < adjustEntrys.size(); i++) {
					AdjustRecordEntryInfo adjust = adjustEntrys.get(i);
					IRow entryRow = table.addRow();
					entryRow.setTreeLevel(node.getLevel());
					entryRow.getCell("adjustName").setValue(
							adjust.getAdjustAcctName());
					entryRow.getCell("adjustType").setValue(
							adjust.getAdjustType());
					entryRow.getCell("adjustReason").setValue(
							adjust.getAdjustReason());
					entryRow.getCell("adjustAmount").setValue(
							adjust.getCostAmount());
					entryRow.getCell("adjustDate").setValue(
							adjust.getAdjustDate());
					entryRow.getCell("description").setValue(
							adjust.getDescription());
					entryRow.setUserObject(adjust);
				}
				BigDecimal dyCost = FDCHelper.ZERO;
				if (aimCost == null) {
					aimCost = FDCHelper.ZERO;
				}
				dyCost=dyCost.add(aimCost);
				if (dynamic.getAdjustSumAmount() != null) {
					dyCost = dyCost.add(dynamic.getAdjustSumAmount());
				}
				row.getCell("dyCost").setValue(dyCost);
			}else{
				if(row.getCell("aimCost").getValue()!=null){
					row.getCell("dyCost").setValue(row.getCell("aimCost").getValue());
				}
			}
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
		}
	}

	/**
	 * 设置表格属性
	 */
	private void initTable() {
		KDTable table = this.tblMain;
		table.checkParsed();
		table.getStyleAttributes().setLocked(true);
		table.setRefresh(false);
		table.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		table.getViewManager().setFreezeView(-1, 2);
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);

		table.getColumn("aimCost").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("aimCost").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("adjustAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("adjustAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("dyCost").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		table.getColumn("dyCost").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.setColumnMoveable(true);
//		FDCTableHelper.setColumnMoveable(table, true);
	}

	/**
	 * 屏蔽回车键
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {

	}

	private String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null
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

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
				tblMain.getViewManager().freeze(0, acctNameIndex);
		}});

	}

	private void initControl() {
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuBiz.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionChart.setVisible(false);
		this.actionView.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionAttachment.setVisible(false);
	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData() {
		KDTable table = this.tblMain;
		String[] cols = new String[] { "aimCost", "adjustAmount", "dyCost" };
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
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
				boolean hasData = false;
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
						hasData = true;
					}
				}
				if (hasData) {
					row.getCell(cols[j]).setValue(aimCost);
				}
			}
		}
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {

	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnChart.setIcon(EASResource.getIcon("imgTbtn_planchart"));
		this.menuItemChart.setIcon(EASResource.getIcon("imgTbtn_planchart"));
	}

	/**
	 * 获取query中的主键列名称，返回供编辑/删除时获取主键用，默认值为"id"，继承类可以重载
	 * 
	 */
	protected String getKeyFieldName() {
		return "acctNumber";
	}

	private void refresh() throws Exception {
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		this.initDynamicCostData(selectObjId);
		this.initAimCostData(selectObjId);
		fillTable(selectObjId);
		this.setUnionData();
		initUserConfig();
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
		try {
			if(FDCUtils.getDefaultFDCParamByKey(
					null, SysContext.getSysContext().getCurrentFIUnit().getId().toString(), FDCConstants.FDC_PARAM_AIMCOSTAUDIT)){
				filter.getFilterItems().add(
						new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		}
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
		view.getSelector().add(new SelectorItemInfo("adjustEntrys.*"));
		view.getSelector().add(new SelectorItemInfo("intendingCostEntrys.*"));
		view.getSelector().add(new SelectorItemInfo("adjustEntrys.product.*"));
		view.getSelector().add(
				new SelectorItemInfo("adjustEntrys.adjustType.*"));
		view.getSelector().add(
				new SelectorItemInfo("adjustEntrys.adjustReason.*"));
		view.getSelector().add(
				new SelectorItemInfo("intendingCostEntrys.product.*"));
		DynamicCostCollection DynamicCostCollection = DynamicCostFactory
				.getRemoteInstance().getDynamicCostCollection(view);
		for (int i = 0; i < DynamicCostCollection.size(); i++) {
			DynamicCostInfo info = DynamicCostCollection.get(i);
			CostAccountInfo acct = info.getAccount();
			dynamicCostMap.put(acct.getId().toString(), info);
		}

	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		refresh();
	}

	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		//
		// String boID = this.handler.aimCost.getId().toString();
		// AttachmentClientManager acm = AttachmentManagerFactory
		// .getClientManager();
		// if (boID == null) {
		// return;
		// }
		// acm.showAttachmentListUIByBoID(boID, this);

	}

	public void actionChart_actionPerformed(ActionEvent e) throws Exception {
	}
}