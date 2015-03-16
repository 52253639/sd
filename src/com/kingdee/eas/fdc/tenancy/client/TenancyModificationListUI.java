/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.RentRemissionFactory;
import com.kingdee.eas.fdc.tenancy.RentRemissionInfo;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyModificationFactory;
import com.kingdee.eas.fdc.tenancy.TenancyModificationInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TenancyModificationListUI extends AbstractTenancyModificationListUI {

	private static final Logger logger = CoreUIObject.getLogger(TenancyModificationListUI.class);

	/**
	 * output class constructor
	 */
	public TenancyModificationListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);

	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}

		if (node.getUserObject() instanceof SellProjectInfo) {
			SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
			if (saleOrg.isIsBizUnit()) {
				this.actionAddNew.setEnabled(true);
			}
		} else {
			this.actionAddNew.setEnabled(false);
		}
		this.execQuery();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();

			FilterInfo filter = new FilterInfo();
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", pro.getId().toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}

			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			handleException(e);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	public void onLoad() throws Exception {

		super.onLoad();
		initTree();
		this.treeMain.setSelectionRow(0);

		this.actionAttachment.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);

		this.btnAudit.setEnabled(true);
		this.btnAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
		this.actionAudit.setVisible(true);
		this.actionAuditResult.setVisible(false);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		String id = getSelectedId();
		TenancyModificationInfo tenModifiInfo = TenancyModificationFactory.getRemoteInstance().getTenancyModificationInfo(new ObjectUuidPK(id));
		FDCBillStateEnum tenModifiState = tenModifiInfo.getState();
		if (!FDCBillStateEnum.SUBMITTED.equals(tenModifiState)) {
			MsgBox.showInfo(this, "只有已提交的合同变更单才能审批！");
			this.abort();
		}
		TenancyModificationFactory.getRemoteInstance().audit(BOSUuid.read(id));
		MsgBox.showInfo(this, "审批成功！");
		this.refresh(null);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {

		String id = getSelectedId();
		TenancyModificationInfo tenModifiInfo = TenancyModificationFactory.getRemoteInstance().getTenancyModificationInfo(new ObjectUuidPK(id));
		FDCBillStateEnum tenModifiState = tenModifiInfo.getState();
		if ((!FDCBillStateEnum.SAVED.equals(tenModifiState)) && (!FDCBillStateEnum.SUBMITTED.equals(tenModifiState))) {
			MsgBox.showInfo(this, "只有保存或提交状态的的合同变更单才能删除！");
			this.abort();
		}
		super.actionRemove_actionPerformed(e);

	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String id = getSelectedId();
		TenancyModificationInfo tenModifiInfo = TenancyModificationFactory.getRemoteInstance().getTenancyModificationInfo(new ObjectUuidPK(id));
		FDCBillStateEnum tenModifiState = tenModifiInfo.getState();
		if (!FDCBillStateEnum.SAVED.equals(tenModifiState) && !FDCBillStateEnum.SUBMITTED.equals(tenModifiState)) {
			MsgBox.showInfo("保存或提交状态的的合同变更单才能修改！");
			abort();
		}
		super.actionEdit_actionPerformed(e);
	}

	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return TenancyModificationFactory.getRemoteInstance();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected String getEditUIName() {
		return TenancyModificationEditUI.class.getName();
	}

	protected String getKeyFieldName() {
		return super.getKeyFieldName();
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected CommonQueryDialog initCommonQueryDialog() {
		CommonQueryDialog cqd =  super.initCommonQueryDialog();
		cqd.setShowSorter(true);
		return cqd;
	}
}