/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanFactory;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class FDCProDepConPayPlanListUI extends
		AbstractFDCProDepConPayPlanListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FDCProDepConPayPlanListUI.class);
	Set projectLeafset = null;
	String selectOrgId = null;
	String flag_state = "";

	/**
	 * output class constructor
	 */
	public FDCProDepConPayPlanListUI() throws Exception {
		super();
	}

	protected void updateButtonStatus() {
		// super.updateButtonStatus();
	}

	public void onLoad() throws Exception {
		if (SysContext.getSysContext().getCurrentCostUnit() == null) {
			MsgBox.showWarning("请切换到成本中心组织进行业务处理!");
			abort();
		}
		// 如此设置便能保证即使我们切换到了财务组织下边，部门合同付款计划序时簿左边的树上也可以显示出来工程项目
		OrgUnitInfo currentOrgUnit = SysContext.getSysContext()
				.getCurrentOrgUnit();
		try {
			SysContext.getSysContext().setCurrentOrgUnit(
					SysContext.getSysContext().getCurrentFIUnit());
			super.onLoad();
		} finally {
			SysContext.getSysContext().setCurrentOrgUnit(currentOrgUnit);
		}

		publish.setIcon(EASResource.getIcon("imgTbtn_timingrefer"));
		revise.setIcon(EASResource.getIcon("imgTbtn_emend"));
		back.setIcon(EASResource.getIcon("imgTbtn_untreadreport"));
		menuItemPublish.setIcon(EASResource.getIcon("imgTbtn_timingrefer"));
		menuItemRevise.setIcon(EASResource.getIcon("imgTbtn_emend"));
		this.actionBack.setVisible(false);
		setUITitle("项目月度滚动资金需求计划-序时薄");
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void actionBack_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		// List selectedIdValues = ContractClientUtils.getSelectedIdValues(
		// getMainTable(), getKeyFieldName());
		//FDCProDepConPayPlanFactory.getRemoteInstance().back(selectedIdValues);
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		showOprtOKMsgAndRefresh();
		super.actionBack_actionPerformed(e);
		actionBack.setEnabled(false);
		FDCSQLBuilder builder = new FDCSQLBuilder();
		String editMonth = tblMain.getCell(actRowIdx, "editMonth").getValue()
				.toString();

		builder
				.appendSql("update T_FNC_FDCDepConPayPlanBill set FState = '11BACK' where  feditmonth = ? and Fstate = '10PUBLISH'");
		if (editMonth.length() == 6) {
			String a = editMonth.replaceAll("-", "0");
			builder.addParam(Integer.valueOf(a));
		} else if (editMonth.length() == 7) {
			String a = editMonth.replaceAll("-", "");
			builder.addParam(Integer.valueOf(a));
		}
		builder.execute();
	}

	public void actionPublish_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (MsgBox.showConfirm2("是否确定上报？") == MsgBox.CANCEL) {
			return;
		}
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		FDCProDepConPayPlanFactory.getRemoteInstance()
				.publish(selectedIdValues);
		showOprtOKMsgAndRefresh();
		super.actionPublish_actionPerformed(e);
	}

	public void actionRevise_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		flag_state = "revist";
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("flag_state", flag_state);
		IUIWindow window = this.getUIWindow();
		IUIWindow uiWin;
		if (window != null && window instanceof UIModelDialog) {
			uiWin = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
					getEditUIName(), uiContext, null, OprtState.EDIT);
		} else {
			uiWin = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
					getEditUIName(), uiContext, null, OprtState.EDIT);
		}
		uiWin.show();
		if (isDoRefresh(uiWin)) {
			if (UtilRequest.isPrepare("ActionRefresh", this)) {
				prepareRefresh(null).callHandler();
			}
			setLocatePre(false);
			refresh(e);
			setPreSelecteRow();
			setLocatePre(true);
		}
	}

	protected void checkBeforeEdit(ActionEvent e) throws Exception {
		if (!flag_state.equals("revist")) {
			super.checkBeforeEdit(e);
		}
	}

	protected void execQuery() {
		try {
			treeSelectChange();
		} catch (Exception e) {
			handUIException(e);
		}
		super.execQuery();
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

	protected void treeSelectChange() throws Exception {
		projectLeafset = new HashSet();
		FilterInfo filter = null;
		SelectorItemCollection selector = null;
		EntityViewInfo view = null;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeProject
				.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() instanceof CoreBaseInfo) {
			view = new EntityViewInfo();
			filter = new FilterInfo();
			selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("number");
			selector.add("longNumber");
			view.setSelector(selector);
			view.setFilter(filter);
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) node.getUserObject();
			// 选中组织节点
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				BOSUuid id = ((OrgStructureInfo) projTreeNodeInfo).getUnit()
						.getId();
				String orgUnitLongNumber = null;
				if (orgUnit != null
						&& id.toString().equals(orgUnit.getId().toString())) {
					orgUnitLongNumber = orgUnit.getLongNumber();
				} else {
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory
							.getRemoteInstance().getFullOrgUnitInfo(
									new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}
				filter.getFilterItems().add(
						new FilterItemInfo("fullOrgUnit.longNumber", "%"
								+ orgUnitLongNumber + "%", CompareType.LIKE));
				// 根据组织找到工程项目Ids
				CurProjectCollection projectColl = CurProjectFactory
						.getRemoteInstance().getCurProjectCollection(view);
				for (Iterator iter = projectColl.iterator(); iter.hasNext();) {
					CurProjectInfo project = (CurProjectInfo) iter.next();
					String prjId = project.getId().toString();
					projectLeafset.add(prjId);
				}// 选中项目节点
			} else if (projTreeNodeInfo instanceof CurProjectInfo) {
				BOSUuid id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				if (idSet != null && idSet.size() > 0) {
					for (Iterator iter = idSet.iterator(); iter.hasNext();) {
						String temp = (String) iter.next();
						projectLeafset.add(temp);
					}
				}
			}
			// 根据工程项目ID找到所有部门合同付款计划
			if (projectLeafset != null && projectLeafset.size() > 0) {
				filter = new FilterInfo();
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder
						.appendParam(
								"select fid from T_FNC_FDCProDepConPlan where FCurProjectID ",
								projectLeafset.toArray());
				filter.getFilterItems().add(
						new FilterItemInfo("id", builder.getTestSql(),
								CompareType.INNER));
				this.mainQuery.setFilter(filter);
			}
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeProject
				.getModel().getRoot();
		if (root == node) {
			tblMain.removeRows();
			tblMain.getSelectManager().removeAll();
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select FID ,FIsEnabled from  T_FDC_PayPlanCycle where FIsEnabled = 1");
		IRowSet rowSet = builder.executeQuery();
		if (rowSet == null || rowSet.size() == 0) {
			MsgBox.showWarning("没有付款计划周期或者没有启用付款计划周期，不能新增！");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}

	protected FilterInfo getTreeFilter() throws Exception {
		return null;
	}

	protected void treeProject_valueChanged(TreeSelectionEvent e)
			throws Exception {
		treeSelectChange();
		this.refresh(null);
	}

	protected void refresh(ActionEvent e) throws Exception {
		super.refresh(e);
	}

	protected String getEditUIName() {
		return FDCProDepConPayPlanEditUI.class.getName();
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

		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		Object o = getProjSelectedTreeNode().getUserObject();
		if (o != null && o instanceof CurProjectInfo) {
			CurProjectInfo curProj = (CurProjectInfo) o;
			getUIContext().put("selectedProj", curProj);
		}
		getUIContext().put("currentAdmit",
				SysContext.getSysContext().getCurrentAdminUnit());
	}

	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		checkSelected();
		actionEdit.setEnabled(false);
		super.tblMain_tableSelectChanged(e);
		int row = e.getSelectBlock().getEndRow();
		actionAudit.setEnabled(false);
		actionUnAudit.setEnabled(false);
		actionPublish.setEnabled(false);
		actionBack.setEnabled(false);
		actionRevise.setEnabled(false);
		BizEnumValueDTO fde = (BizEnumValueDTO) tblMain.getRow(row).getCell(
				"state").getValue();
		if (fde.getValue().equals(FDCBillStateEnum.SAVED_VALUE)
				|| fde.getValue().equals(FDCBillStateEnum.SUBMITTED_VALUE)) {
			actionEdit.setEnabled(true);
			actionBack.setEnabled(true);
		}
		if (fde.getValue().equals(FDCBillStateEnum.SUBMITTED_VALUE)) {
			actionAudit.setEnabled(true);
			actionBack.setEnabled(true);
		}
		if (fde.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
			actionUnAudit.setEnabled(true);
			actionPublish.setEnabled(true);
			actionBack.setEnabled(false);
		}
		if (fde.getValue().equals(FDCBillStateEnum.REVISE_VALUE)) {
			actionBack.setEnabled(false);
		}
		if (fde.getValue().equals(FDCBillStateEnum.REVISING_VALUE)) {
			actionBack.setEnabled(false);
		}
		if (fde.getValue().equals(FDCBillStateEnum.PUBLISH_VALUE)) {
			actionBack.setEnabled(false);
		}
		if (fde.getValue().equals(FDCBillStateEnum.BACK_VALUE)) {
			actionRevise.setEnabled(true);
			actionBack.setEnabled(true);
		}
		if (KDTableUtil.getSelectedRowCount(tblMain) > 1) {
			actionRevise.setEnabled(false);
			actionEdit.setEnabled(false);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select fid as id from T_FNC_FDCProDepConPlan where Fnumber = ? and Fversion =?  ");
		String number = tblMain.getCell(actRowIdx, "number").getValue()
				.toString();
		double versions = Double.parseDouble(tblMain.getCell(actRowIdx,
				"version").getValue().toString()) - 0.1;
		DecimalFormat myformat = new DecimalFormat("#0.0");
		String version = myformat.format(versions);
		if (versions >= 1.0
				&& tblMain.getCell(actRowIdx, "state").getValue().toString()
						.equals("已提交")) {
			builder.addParam(number);
			builder.addParam(version);
			IRowSet rowSet = builder.executeQuery();
			builder.clear();
			if (rowSet != null && rowSet.size() >= 1) {
				rowSet.next();
				String id = rowSet.getString("id");

				builder
						.appendSql("update T_FNC_FDCProDepConPlan set  FState='11BACK' where fid =?  ");
				builder.addParam(id);
				builder.execute();
			}
		}
		super.actionRemove_actionPerformed(e);
	}

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
				.appendSql("select fid as id from T_FNC_FDCProDepConPlan where Fnumber = ? and Fversion =?  ");
		String number = tblMain.getCell(actRowIdx, "number").getValue()
				.toString();
		double versions = Double.parseDouble(tblMain.getCell(actRowIdx,
				"version").getValue().toString()) - 0.1;
		DecimalFormat myformat = new DecimalFormat("#0.0");
		String version = myformat.format(versions);
		if (versions >= 1.0
				&& tblMain.getCell(actRowIdx, "state").getValue().toString()
						.equals("已审批")) {
			builder.addParam(number);
			builder.addParam(version);
			IRowSet rowSet = builder.executeQuery();
			builder.clear();
			if (rowSet != null && rowSet.size() >= 1) {
				rowSet.next();
				String id = rowSet.getString("id");

				builder.appendSql("update T_FNC_FDCProDepConPlan set  FState='"
						+ FDCBillStateEnum.REVISING_VALUE + "' where fid =?  ");
				builder.addParam(id);
				builder.execute();
			}
		}
		super.actionUnAudit_actionPerformed(e);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select fid as id from T_FNC_FDCProDepConPlan where Fnumber = ? and Fversion =?  ");
		String number = tblMain.getCell(actRowIdx, "number").getValue()
				.toString();
		double versions = Double.parseDouble(tblMain.getCell(actRowIdx,
				"version").getValue().toString()) - 0.1;
		DecimalFormat myformat = new DecimalFormat("#0.0");
		String version = myformat.format(versions);
		if (versions >= 1.0
				&& tblMain.getCell(actRowIdx, "state").getValue().toString()
						.equals("已提交")) {
			builder.addParam(number);
			builder.addParam(version);
			IRowSet rowSet = builder.executeQuery();
			builder.clear();
			if (rowSet != null && rowSet.size() >= 1) {
				rowSet.next();
				String id = rowSet.getString("id");

				builder
						.appendSql("update T_FNC_FDCProDepConPlan set  FState='12REVISE' where fid =?  ");
				builder.addParam(id);
				builder.execute();
			}
		}
		super.actionAudit_actionPerformed(e);
	}

	protected void audit(List ids) throws Exception {
		FDCProDepConPayPlanFactory.getRemoteInstance().audit(ids);
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return FDCProDepConPayPlanFactory.getRemoteInstance();
	}

	protected void unAudit(List ids) throws Exception {
		FDCProDepConPayPlanFactory.getRemoteInstance().unAudit(ids);

	}

}