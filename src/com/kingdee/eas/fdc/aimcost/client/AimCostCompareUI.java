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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
import com.kingdee.bos.ctrl.swing.chart.ChartType;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.ChartData;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.XTable;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AimCostCompareUI extends AbstractAimCostCompareUI {

	private XTable aimCostTable;

	public AimCostCompareUI() throws Exception {
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
			String[] rowKeys = this.aimCostTable.getRowKeys();
//			Map keys = new TreeMap();
			for (int i = 0; i < rowKeys.length; i++) {
				Map map = this.aimCostTable.getRow(i);
				String versionNumber = (String) map.get("versionNumber");
				Map amountMap = (Map) map.get("amountMap");
				BigDecimal amount = (BigDecimal) amountMap.get(costAcct.getId()
						.toString());
				row.getCell(versionNumber).setValue(amount);
			}
			row.setUserObject(costAcct);
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

	}

	private void initControl() {
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		// this.actionRefresh.setVisible(false);
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
		String[] rowKeys = this.aimCostTable.getRowKeys();
		String[] cols = new String[rowKeys.length];
		for (int i = 0; i < rowKeys.length; i++) {
			Map map = this.aimCostTable.getRow(i);
			String versionNumber = (String) map.get("versionNumber");
			cols[i] = versionNumber;
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() == null) {
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
		this.initAimCostData(selectObjId);
		int columnCount = tblMain.getColumnCount();
		for (int i = 0; i < columnCount - 2; i++) {
			tblMain.removeColumn(2);
		}
		String[] rowKeys = this.aimCostTable.getRowKeys();
		Map keys = new TreeMap();
		for (int i = 0; i < rowKeys.length; i++) {
			Integer num = new Integer(rowKeys[i]);
			keys.put(num,rowKeys[i]);
		}
		Set set = keys.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			Integer intKey = (Integer) iter.next();
			String key = (String) keys.get(intKey);
			Map map = this.aimCostTable.getRow(key);
			String versionNumber = (String) map.get("versionNumber");
			String versionName = (String) map.get("versionName");
			IColumn col = tblMain.addColumn();
			col.setKey(versionNumber);
			col.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			tblMain.getHeadRow(0).getCell(versionNumber).setValue(versionName);
		}
		fillTable(selectObjId);
		this.setUnionData();
	}

	private void initAimCostData(String objectId) throws BOSException,
			SQLException {
		aimCostTable = new XTable();
		aimCostTable.addColumn("versionNumber");
		aimCostTable.addColumn("versionName");
		aimCostTable.addColumn("amountMap");

		EntityViewInfo aimView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		aimView.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("orgOrProId", objectId));
		aimView.getSelector().add(new SelectorItemInfo("*"));
		aimView.getSelector().add(new SelectorItemInfo("costEntry.*"));
		AimCostCollection aimCostCollection = AimCostFactory
				.getRemoteInstance().getAimCostCollection(aimView);
		for (int i = 0; i < aimCostCollection.size(); i++) {
			AimCostInfo aimInfo = aimCostCollection.get(i);
			String versionNumber = aimInfo.getVersionNumber();
			if (versionNumber.equals("0.0")) {
				continue;
			}
			int k = versionNumber.indexOf(".");
			String number = versionNumber.substring(0, k);
			Map acctMap = new HashMap();
			Map row = aimCostTable.addRow(number);
			row.put("versionNumber", aimInfo.getVersionNumber());
			row.put("versionName", aimInfo.getVersionName());
			row.put("amountMap", acctMap);
			CostEntryCollection costEntrys = aimInfo.getCostEntry();
			for (int j = 0; j < costEntrys.size(); j++) {
				CostEntryInfo entry = costEntrys.get(j);
				String acctId = entry.getCostAccount().getId().toString();
				BigDecimal value = entry.getCostAmount();
				if (value == null) {
					value = FDCHelper.ZERO;
				}
				if (acctMap.containsKey(acctId)) {
					BigDecimal sum = (BigDecimal) acctMap.get(acctId);
					acctMap.put(acctId, sum.add(value));

				} else {
					acctMap.put(acctId, value);
				}
			}
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
		super.actionChart_actionPerformed(e);
		ChartData data = new ChartData();
		List colKeys = new ArrayList();
		String[] rowKeys = this.aimCostTable.getRowKeys();
		for (int i = 0; i < rowKeys.length; i++) {
			Map map = this.aimCostTable.getRow(i);
			String versionNumber = (String) map.get("versionNumber");
			colKeys.add(versionNumber);
		}
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
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		data.setTitle(proNode.getText());
		ChartUI.showChart(this, data);
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

}