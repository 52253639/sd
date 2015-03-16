/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.finance.DepConPayPlanSplitBillFactory;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class DepConPayPlanSplitListUI extends AbstractDepConPayPlanSplitListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(DepConPayPlanSplitListUI.class);

	public DepConPayPlanSplitListUI() throws Exception {
		super();
	}

	protected String getKeyFieldName() {
		// TODO Auto-generated method stub
		return "splitBillId";
	}

	protected void updateButtonStatus() {
	}

	protected void execQuery() {
		// TODO Auto-generated method stub
		super.execQuery();
	}

	Set projectLeafset = null;

	protected void treeProject_valueChanged(TreeSelectionEvent e)
			throws Exception {
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
						new FilterItemInfo("fullOrgUnit.longNumber",
								"%"+orgUnitLongNumber+"%", CompareType.LIKE));
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
				Set tempSet = new HashSet();
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder
						.appendParam(
								"select fid from T_FNC_FDCDepConPayPlanBill where FCurProjectID ",
								projectLeafset.toArray());
				IRowSet rowSet = builder.executeQuery();
				while (rowSet != null && rowSet.next()) {
					tempSet.add(rowSet.getString("fid"));
				}
				if (tempSet != null && tempSet.size() > 0) {
					filter.getFilterItems()
							.add(
									new FilterItemInfo("id", tempSet,
											CompareType.INNER));
					this.mainQuery.setFilter(filter);
				} else {
					filter.getFilterItems().add(new FilterItemInfo("id", ""));
					this.mainQuery.setFilter(filter);
				}
			}
		}
		/*filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));*/// 必须是已审批的单据
		//update by renliang
		if(filter!=null && filter.getFilterItems()!=null){
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));// 必须是已审批的单据
		}
		
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeProject
				.getModel().getRoot();
		if (root == node) {
			tblMain.removeRows();
			tblMain.getSelectManager().removeAll();
			this.refresh(null);
			if (this.tblMain.getRowCount() > 0) {
				this.tblMain.getSelectManager().setActiveRowIndex(0);
			}
			return;
		}
		this.refresh(null);
		// 为了防止在选择树节点的时候表格未选中任何行导致但是后续又有类似
		// int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		// 这样的代码导致空指针，显式指定默认选中第一行
		if (this.tblMain.getRowCount() > 0) {
			this.tblMain.getSelectManager().setActiveRowIndex(0);
		}
	}

	// 得到付款计划单据ID
	private Object getPayPlanId() {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		Object o = tblMain.getCell(actRowIdx, "id").getValue();
		if (o == null) {
			return null;
		} else {
			return o;
		}
	}

	// 得到拆分单据本身ID
	private Object getPayPlanSplitId() {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		Object o = tblMain.getCell(actRowIdx, "splitBillId").getValue();
		if (o == null) {
			return null;
		} else {
			return o;
		}
	}

	protected FilterInfo getTreeFilter() throws Exception {
		return null;
	}

	private void checkCanSplit() throws BOSException, SQLException {

		// 合同未拆分的话不能进行付款计划的拆分
		// 合同已拆分但已作废的也不能进行付款计划的拆分
		Object payPlanId = getPayPlanId();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select FID ,FIsInvalid from T_CON_ContractCostSplit where FContractBillId in "
						+ "(select FContractBillId from T_FNC_FDCDepConPayPlanEntry where FParentID = ?) ");
		builder.addParam(payPlanId.toString());
		IRowSet rowSet = builder.executeQuery();

		if (rowSet != null && rowSet.size() == 0) {
			FDCMsgBox.showWarning("该付款计划中有部门合同未作拆分，请将所有合同都拆分完毕！");
			SysUtil.abort();
		//} else if (rowSet.size() >= 1) {
			//update by renliang
		} else if (rowSet!=null && rowSet.size() >= 1) {	
			rowSet.next();
			String tempID = rowSet.getString("FID");
			boolean isInvalid = rowSet.getBoolean("FIsInvalid");
			if (tempID == null) {
				FDCMsgBox.showWarning("该付款计划中有部门合同未作拆分，请将所有合同都拆分完毕！");
				SysUtil.abort();
			} else if (isInvalid) {
				FDCMsgBox.showWarning("该付款计划中有部门合同拆分已经作废，请重新拆分这些合同！");
				SysUtil.abort();
			}
		}
		// 已审批的单据不能进行重新拆分
		Object splitId = getPayPlanSplitId();
		if (splitId != null) {
			builder.clear();
			builder
					.appendSql("select FState from T_FNC_FDCDepConPayPlanBill where FID = ?  ");
			builder.addParam(splitId.toString());
			IRowSet _rowSet = builder.executeQuery();
			//if (_rowSet != null & _rowSet.size() == 1) {
			//update by renliang
			if (_rowSet != null && _rowSet.size() == 1) {
				if (FDCBillStateEnum.AUDITTED_VALUE.equals(rowSet
						.getString("FState"))) {
					FDCMsgBox.showWarning("已审批的单据不能重新拆分！");
					SysUtil.abort();
				}
			}
		}
	}

	protected static final BOSUuid splitBillNullID = BOSUuid.create("null");

	private boolean isAddNewOrEdit() throws EASBizException, BOSException,
			Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper
					.getRes("multiRowSelected"));
			SysUtil.abort();
		}
		boolean isAddNew = false;
		String keyValue = getSelectedKeyValue();

		if (keyValue == null || keyValue.equals(splitBillNullID.toString())) {
			isAddNew = true;
		} else {
			// 界面上显示有，但数据库内已经删除时的情况
			IObjectPK pk = new ObjectUuidPK(BOSUuid.read(keyValue));
			if (!getBizInterface().exists(pk)) {
				isAddNew = true;
				int[] selectedRows = KDTableUtil
						.getSelectedRows(getMainTable());
				getMainTable().getCell(selectedRows[0],
						"splitPlanBill.splitState").setValue(
						CostSplitStateEnum.NOSPLIT.toString());
			}
		}

		String billId = getMainTable().getCell(selectRows[0], "id").getValue()
				.toString();
		if (isAddNew) {
			// 到数据库内进行检查
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("splitPayPlanBill.id", billId));
			if (getRemoteInterface().exists(filter)) {
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				String payPlanId = getRemoteInterface().getCollection(view)
						.get(0).getId().toString();
				getMainTable().getCell(selectRows[0], getKeyFieldName())
						.setValue(payPlanId);
				isAddNew = false;
			}

		}
		return isAddNew;
	}

	public void actionCostSplti_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper
					.getRes("multiRowSelected"));
			SysUtil.abort();
		}

		// 注意该ID是付款计划的ID，不是拆分本身这张单据的ID
		Object o = getPayPlanId();
		if (o == null) {
			return;
		}

		if(getPayPlanSplitId()!=null){
			// 已审批的单据不能修改
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select FState from  T_FNC_DepConPayPlanSplitBill where FID = ?  ");
			builder.addParam(getPayPlanSplitId().toString());
			IRowSet rowSet = builder.executeQuery();
			if(rowSet!=null&&rowSet.next()){
				String state = rowSet.getString("FState");
				if(state!=null&&FDCBillStateEnum.AUDITTED_VALUE.equals(state)){
					MsgBox.showWarning("单据已审批，不能重新拆分！");
					SysUtil.abort();
				}
			}
		}
		
		checkCanSplit();

		boolean isAddNew = isAddNewOrEdit();
		if (isAddNew) {
			ActionEvent evt = new ActionEvent(btnAddNew, 0, "FDCCostSplit");
			ItemAction actAddNew = getActionFromActionEvent(evt);
			actAddNew.actionPerformed(evt);
		} else {
			Object _o = getPayPlanSplitId();
			if (_o == null) {
				return;
			}
			// 把所选择节点信息存入uiContext 传给DepConPayPlanSplitEditUI
			DefaultKingdeeTreeNode node = getProjSelectedTreeNode();
			IUIWindow uiWin = null;
			UIContext newContext = new UIContext(this);
			newContext.put(UIContext.ID, _o.toString());
			newContext.put("node", node);
			newContext.put("payPlanID", o.toString());

			uiWin = UIFactory.createUIFactory(getEditUIModal()).create(
					getEditUIName(), newContext, null, OprtState.EDIT);

			uiWin.show();

		}
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSplited();
		// 未拆分的单据不能审批,这个校验在保存拆分的时候就可以控制得到了。因为只有完全拆分的单据才予以保存
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FState from  T_FNC_DepConPayPlanSplitBill where FID = ?  ");
		builder.addParam(getPayPlanSplitId().toString());
		IRowSet rowSet = builder.executeQuery();
		if(rowSet!=null&&rowSet.next()){
			String state = rowSet.getString("FState");
			if(state==null||"".equals(state)){
				MsgBox.showWarning("存在不符合审批条件的记录，请重新选择，保证所选的记录都是保存状态的！");
				SysUtil.abort();
			}
		}
		
		builder.clear();
		builder.appendSql("update T_FNC_DepConPayPlanSplitBill  set FState = ? where FID = ?  ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		builder.addParam(getPayPlanSplitId().toString());
		builder.executeUpdate();
		
		MsgBox.showInfo("操作成功！");
		refresh(e);
//		super.actionAudit_actionPerformed(e);
	}

	private void checkSplited() {
		checkSelected();
		if (getPayPlanSplitId() == null || "".equals(getPayPlanSplitId())) {
			MsgBox.showWarning("单据尚未拆分，不能进行此操作！");
			SysUtil.abort();
		}
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSplited();

		super.actionUnAudit_actionPerformed(e);
	}

	protected void getRowSetBeforeFillTable(IRowSet rowSet) {
		String splitState = null;
		try {
			rowSet.beforeFirst();
			while (rowSet.next()) {
				splitState = rowSet.getString("splitPlanBill.splitState");
				if (splitState == null) {
					rowSet.updateString("splitPlanBill.splitState",
							CostSplitStateEnum.NOSPLIT.toString());
				} else if (splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)) {
					rowSet.updateString("splitPlanBill.splitState",
							CostSplitStateEnum.ALLSPLIT.toString());
				} else if (splitState.equals(CostSplitStateEnum.NOSPLIT_VALUE)) {
					rowSet.updateString("splitPlanBill.splitState",
							CostSplitStateEnum.NOSPLIT.toString());
				}
				
				// 编制月份editMonth在Query中不能硬拼接，改在这里拼接  Added By Owen_wen 2010-11-01	
				rowSet.updateString("editMonth", rowSet.getString("year") + "-" + rowSet.getString("month"));
			}
			rowSet.beforeFirst();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();

		actionCostSplit.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_split"));
		menuItemCostSplit.setAccelerator(KeyStroke.getKeyStroke("alt shift S"));
	}

	public void onLoad() throws Exception {
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	public void onShow() throws Exception {
		super.onShow();

		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemEdit.setVisible(false);

		this.actionView.setVisible(true);
		this.actionView.setEnabled(true);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);

		this.btnLocate.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnQuery.setVisible(false);
		this.btnAttachment.setVisible(false);

		this.menuItemLocate.setVisible(false);
		this.menuItemQuery.setVisible(false);
		this.menuItemPrint.setVisible(false);
		this.menuItemPrintPreview.setVisible(false);
		this.MenuItemAttachment.setVisible(false);

		this.btnWorkFlowG.setVisible(false);
		this.btnAuditResult.setVisible(false);

		this.menuWorkFlow.setVisible(false);
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return DepConPayPlanSplitEditUI.class.getName();
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		// TODO Auto-generated method stub
		return DepConPayPlanSplitBillFactory.getRemoteInstance();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		// TODO Auto-generated method stub
		super.prepareUIContext(uiContext, e);
		Object o = getPayPlanId();
		if (o != null) {
			uiContext.put("payPlanID", o.toString());
		}
		// 把所选择节点信息存入uiContext 传给DepConPayPlanSplitEditUI
		DefaultKingdeeTreeNode node = getProjSelectedTreeNode();
		uiContext.put("node", node);
	}

	protected void audit(List ids) throws Exception {
		DepConPayPlanSplitBillFactory.getRemoteInstance().audit(ids);
	}

	protected void unAudit(List ids) throws Exception {
		DepConPayPlanSplitBillFactory.getRemoteInstance().unAudit(ids);
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("year"));
		sic.add(new SelectorItemInfo("month"));
		return sic;
	}
}