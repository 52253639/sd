/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 合同月度滚动付款计划
 */
public class FDCDepConPayPlanListUI extends AbstractFDCDepConPayPlanListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FDCDepConPayPlanListUI.class);
	String flag_state = "";

	/**
	 * output class constructor
	 */
	public FDCDepConPayPlanListUI() throws Exception {
		super();
	}

	protected void execQuery() {
		try {
			treeSelectChange();
		} catch (Exception e) {
			handUIException(e);
		}
		super.execQuery();
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			BizEnumValueDTO fde = (BizEnumValueDTO) tblMain.getRow(i).getCell(
					"state").getValue();
			if (fde != null
					&& FDCBillStateEnum.BACK_VALUE.equals(fde.getValue())) {
				tblMain.getRow(i).getStyleAttributes().setFontColor(Color.RED);
			}
		}
	}

	protected SorterItemCollection getSorter() {
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo("sort1"));
		sorter.add(new SorterItemInfo("sort2"));
		SorterItemInfo num = new SorterItemInfo("number");
		num.setSortType(SortType.DESCEND);
		sorter.add(num);
		SorterItemInfo ver = new SorterItemInfo("version");
		ver.setSortType(SortType.DESCEND);
		sorter.add(ver);
		return sorter;
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		checkSelected();
		actionRemove.setEnabled(false);
		actionEdit.setEnabled(false);
		int row = e.getSelectBlock().getEndRow();
		actionAudit.setEnabled(false);
		actionUnAudit.setEnabled(false);
		actionPublish.setEnabled(false);
		actionRevise.setEnabled(false);
		BizEnumValueDTO fde = (BizEnumValueDTO) tblMain.getRow(row).getCell(
				"state").getValue();
		if (fde.getValue().equals(FDCBillStateEnum.SAVED_VALUE)
				|| fde.getValue().equals(FDCBillStateEnum.SUBMITTED_VALUE)) {
			actionEdit.setEnabled(true);
			actionRemove.setEnabled(true);
		}
		if (fde.getValue().equals(FDCBillStateEnum.SUBMITTED_VALUE)) {
			actionAudit.setEnabled(true);
			actionRemove.setEnabled(true);
		}
		if (fde.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
			actionUnAudit.setEnabled(true);
			actionPublish.setEnabled(true);
		}
		if (fde.getValue().equals(FDCBillStateEnum.BACK_VALUE)) {
			actionRevise.setEnabled(true);
		}
		if (KDTableUtil.getSelectedRowCount(tblMain) > 1) {
			actionRevise.setEnabled(false);
			actionEdit.setEnabled(false);
		}

	}

	protected boolean isOrderByIDForBill() {
		return false;
	}

	protected void treeSelectChange() throws Exception {
		FilterInfo filter = null;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		if (node != null && node.isLeaf()) {
			actionAddNew.setEnabled(true);
		} else {
			actionAddNew.setEnabled(false);
		}
		if (node != null && node.getUserObject() instanceof FullOrgUnitInfo) {
			FullOrgUnitInfo fullinfo = (FullOrgUnitInfo) node.getUserObject();
			String longNumber = fullinfo.getLongNumber();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("deptment.longNumber", "%" + longNumber + "%", CompareType.LIKE));
			this.mainQuery.setFilter(filter);
		}

		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeProject.getModel().getRoot();
		if (root == node) {
			tblMain.removeRows();
			tblMain.getSelectManager().removeAll();
		}
	}

	public void buildProjectTree() {
		AdminOrgUnitInfo adminOUInfo = SysContext.getSysContext().getCurrentAdminUnit();
		if (adminOUInfo != null) {
			try {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("longNumber", adminOUInfo.getLongNumber() + "%", CompareType.LIKE));
				filter.getFilterItems().add(new FilterItemInfo("isFreeze", new Integer(0)));

				CtrlUnitInfo obj = SysContext.getSysContext().getCurrentCtrlUnit();
				String cuId = obj.getId().toString();
				if (cuId != null) {
					filter.getFilterItems().add(new FilterItemInfo("CU.id", cuId));
				}

				// 用户组织范围内的组织才能选择
				try {
					Set authorizedOrgs = new HashSet();
					Map orgs = (Map) ActionCache.get("FDCBillEditUIHandler.authorizedOrgs");
					if (orgs == null) {
						orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
								new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.Admin, null, null, null);
					}
					if (orgs != null) {
						Set orgSet = orgs.keySet();
						Iterator it = orgSet.iterator();
						while (it.hasNext()) {
							authorizedOrgs.add(it.next());
						}
					}
					FilterInfo filterID = new FilterInfo();
					filterID.getFilterItems().add(new FilterItemInfo("id", authorizedOrgs, CompareType.INCLUDE));

					filter.mergeFilter(filterID, "and");

				} catch (Exception e) {
					e.printStackTrace();
				}

				ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(new DefaultLNTreeNodeCtrl(FullOrgUnitFactory.getRemoteInstance()),
						10, 10, filter);
				treeBuilder.buildTree(treeProject);
				treeProject.setSelectionNode((DefaultKingdeeTreeNode) treeProject.getModel().getRoot());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void treeProject_valueChanged(TreeSelectionEvent e)
			throws Exception {
		treeSelectChange();
		this.refresh(null);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		// FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());

		// 如果工程项目对应的财务组织没有做付款计划周期，不允许新增
		// CurProjectInfo curProj = (CurProjectInfo)
		// getProjSelectedTreeNode().getUserObject();
		// String fullOrgId = curProj.getFullOrgUnit().getId().toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FID ,FIsEnabled from  T_FDC_PayPlanCycle where FIsEnabled = 1");
		IRowSet rowSet = builder.executeQuery();
		if (rowSet == null || rowSet.size() == 0) {
			// MsgBox.showWarning("该组织下没做付款计划周期，不能新增！");
			MsgBox.showWarning("没有付款计划周期或没有启用付款计划周期，不能新增！");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}

	protected void checkBeforeAddNew(ActionEvent e) {
		// super.checkBeforeAddNew(e);
	}


	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		// FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		// FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
		checkSelected();
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid as id from T_FNC_FDCDepConPayPlanBill where Fnumber = ? and Fversion =?  ");
		String number = tblMain.getCell(actRowIdx, "number").getValue().toString();
		double versions = Double.parseDouble(tblMain.getCell(actRowIdx, "version").getValue().toString()) - 0.1;
		DecimalFormat myformat = new DecimalFormat("#0.0");
		String version = myformat.format(versions);
		if (versions >= 1.0
				&& FDCBillStateEnum.SUBMITTED.getAlias().equals(tblMain.getCell(actRowIdx, "state").getValue().toString())) {
			builder.addParam(number);
			builder.addParam(version);
			IRowSet rowSet = builder.executeQuery();
			builder.clear();
			if (rowSet != null && rowSet.size() >= 1) {
				rowSet.next();
				String id = rowSet.getString("id");

				builder.appendSql("update T_FNC_FDCDepConPayPlanBill set  FState='11BACK' where fid =?  ");
				builder.addParam(id);
				builder.execute();
			}
		}
		super.actionRemove_actionPerformed(e);
	}

	protected void audit(List ids) throws Exception {
		FDCDepConPayPlanBillFactory.getRemoteInstance().audit(ids);
	}

	protected void unAudit(List ids) throws Exception {
		FDCDepConPayPlanBillFactory.getRemoteInstance().unAudit(ids);
	}

	protected String getEditUIName() {
		return FDCDepConPayPlanEditUI.class.getName();
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return FDCDepConPayPlanBillFactory.getRemoteInstance();
	}

	protected void updateButtonStatus() {
		// 如果是虚体财务组织，则不能增、删、改
		// if (!SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()) {
		// actionAddNew.setEnabled(false);
		// actionEdit.setEnabled(false);
		// actionRemove.setEnabled(false);
		// actionAddNew.setVisible(false);
		// actionEdit.setVisible(false);
		// actionRemove.setVisible(false);
		// menuEdit.setVisible(false);
		// }
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		// super.prepareUIContext(uiContext, e);

		Object o = getProjSelectedTreeNode().getUserObject();
		if (o != null && o instanceof CurProjectInfo) {
			CurProjectInfo curProj = (CurProjectInfo) o;
			getUIContext().put("selectedProj", curProj);
		}
		getUIContext().put("currentAdmit",
				SysContext.getSysContext().getCurrentAdminUnit());
		if (o != null && o instanceof FullOrgUnitInfo) {
			FullOrgUnitInfo info = (FullOrgUnitInfo) o;
			getUIContext().put("department", info);
		}
	}

	Set projectLeafset = null;
	String selectOrgId = null;

	protected FilterInfo getTreeFilter() throws Exception {
		return null;
		// return super.getTreeFilter();
	}

	protected void checkBeforeEdit(ActionEvent e) throws Exception {
		if (!flag_state.equals("revist")) {
			super.checkBeforeEdit(e);
		}
	}

	public void onShow() throws Exception {
		super.onShow();
		this.btnLocate.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);

		this.menuItemLocate.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.menuItemPrint.setVisible(false);
		this.menuItemPrintPreview.setVisible(false);
	}

	public void onLoad() throws Exception {
		if (SysContext.getSysContext().getCurrentAdminUnit() == null) {
			MsgBox.showWarning("只有末级行政组织才能编制合同月度滚动付款计划，请切换组织!");
			abort();
		}

		// 如此设置便能保证即使我们切换到了财务组织下边，部门合同付款计划序时簿左边的树上也可以显示出来工程项目
		OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		try {
			SysContext.getSysContext().setCurrentOrgUnit(SysContext.getSysContext().getCurrentFIUnit());
			super.onLoad();
		} finally {
			SysContext.getSysContext().setCurrentOrgUnit(currentOrgUnit);
		}

		FDCBaseDataClientUtils.setupUITitle(this, "合同月度滚动付款计划");
		btnPublish.setIcon(EASResource.getIcon("imgTbtn_timingrefer"));
		menuItemPublish.setIcon(EASResource.getIcon("imgTbtn_timingrefer"));
		tbnRevise.setIcon(EASResource.getIcon("imgTbtn_emend"));
		menuItemRevise.setIcon(EASResource.getIcon("imgTbtn_emend"));
		btnBack.setIcon(EASResource.getIcon("imgTbtn_untreadreport"));
		KDTreeView treeView = new KDTreeView();
		treeView.setTree(treeProject);
		treeView.setShowButton(false);
		// 标题为“行政组织”
		treeView.setTitle(EASResource.getString("com.kingdee.eas.basedata.org.client.OrgResource", "ADMINORG_F7TITLE"));
		
		treeView.setShowControlPanel(true);
		kDSplitPane1.add(treeView, "left");

		this.btnBack.setVisible(false);
	}

	public void actionBack_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		FDCDepConPayPlanBillFactory.getRemoteInstance().back(selectedIdValues);
		showOprtOKMsgAndRefresh();
		super.actionBack_actionPerformed(e);
	}

	public void actionPublish_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (MsgBox.showConfirm2("是否确定上报？") == MsgBox.CANCEL) {
			return;
		}
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		FDCDepConPayPlanBillFactory.getRemoteInstance().publish(
				selectedIdValues);
		showOprtOKMsgAndRefresh();
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select fid as id from T_FNC_FDCDepConPayPlanBill where Fnumber = ? and Fversion =?  ");
		String number = tblMain.getCell(actRowIdx, "number").getValue()
				.toString();
		double versions = Double.parseDouble(tblMain.getCell(actRowIdx,
				"version").getValue().toString()) - 0.1;
		DecimalFormat myformat = new DecimalFormat("#0.0");
		String version = myformat.format(versions);
		if (versions >= 1.0 && FDCBillStateEnum.SUBMITTED.getAlias().equals(tblMain.getCell(actRowIdx, "state").getValue().toString())) {
			builder.addParam(number);
			builder.addParam(version);
			IRowSet rowSet = builder.executeQuery();
			builder.clear();
			if (rowSet != null && rowSet.size() >= 1) {
				rowSet.next();
				String id = rowSet.getString("id");

				builder
						.appendSql("update T_FNC_FDCDepConPayPlanBill set  FState='12REVISE' where fid =?  ");
				builder.addParam(id);
				builder.execute();
			}
		}

		super.actionAudit_actionPerformed(e);
	}

	public void actionRevise_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		flag_state = "revist";
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("flag_state", flag_state);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(getEditUIName(), uiContext, null, OprtState.EDIT);
		uiWindow.show();
		if (isDoRefresh(uiWindow)) {
			if (UtilRequest.isPrepare("ActionRefresh", this)) {
				prepareRefresh(null).callHandler();
			}
			setLocatePre(false);
			refresh(e);
			setPreSelecteRow();
			setLocatePre(true);
		}
	}

	// 拆分后不能反审批
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		// List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
		// getKeyFieldName());
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(
		// new FilterItemInfo("splitPayPlanBill.id", FDCHelper
		// .list2Set(idList), CompareType.INCLUDE));
		// boolean isDepSplit =
		// DepConPayPlanSplitBillFactory.getRemoteInstance()
		// .exists(filter);
		// if (isDepSplit) {
		// FDCMsgBox.showWarning(this, "您所选择的单据存在已被拆分的对象!");
		// this.abort();
		// }
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select fid as id from T_FNC_FDCDepConPayPlanBill where Fnumber = ? and Fversion =?  ");
		String number = tblMain.getCell(actRowIdx, "number").getValue()
				.toString();
		double versions = Double.parseDouble(tblMain.getCell(actRowIdx,
				"version").getValue().toString()) - 0.1;
		DecimalFormat myformat = new DecimalFormat("#0.0");
		String version = myformat.format(versions);
		if (versions >= 1.0
				&& FDCBillStateEnum.AUDITTED.getAlias().equals(tblMain.getCell(actRowIdx, "state").getValue().toString())) {
			builder.addParam(number);
			builder.addParam(version);
			IRowSet rowSet = builder.executeQuery();
			builder.clear();
			if (rowSet != null && rowSet.size() >= 1) {
				rowSet.next();
				String id = rowSet.getString("id");

				builder.appendSql("update T_FNC_FDCDepConPayPlanBill set  FState=? where fid =?  ");
				builder.addParam(FDCBillStateEnum.REVISING_VALUE);
				builder.addParam(id);
				builder.execute();
			}
		}
		super.actionUnAudit_actionPerformed(e);
	}
}