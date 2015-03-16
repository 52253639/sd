/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.sql.Timestamp;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.AgioBillFactory;
import com.kingdee.eas.fdc.sellhouse.AgioBillInfo;
import com.kingdee.eas.fdc.sellhouse.LoanDataFactory;
//import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AgioListUI extends AbstractAgioListUI {

	private static final Logger logger = CoreUIObject.getLogger(AgioListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	protected void initTree() throws Exception {
//		this.treeMain.setModel(SHEHelper.getSellProjectTree(actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	/**
	 * output class constructor
	 */
	public AgioListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
//		Boolean isEnabled = (Boolean)KDTableUtil.getSelectedRow(tblMain).getCell("isEnabled").getValue();
//		if(isEnabled.booleanValue()){
//			this.actionCancel.setEnabled(true);
//			this.actionCancelCancel.setEnabled(false);
//		}else{
//			this.actionCancel.setEnabled(false);
//			this.actionCancelCancel.setEnabled(true);
//		}
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AgioBillFactory.getRemoteInstance();
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		viewInfo = (EntityViewInfo) this.mainQuery.clone();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		FilterInfo filter = new FilterInfo();
/*		String proIdStrs = "'nullnull'";
		if(node!=null){
			Set keySet = FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet();			
			if(keySet.size()>0) proIdStrs = FDCTreeHelper.getStringFromSet(keySet);
		}
		filter.getFilterItems().add(new FilterItemInfo("project.id", proIdStrs ,CompareType.INNER));*/

		if (node!=null && node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo spInfo = (SellProjectInfo)node.getUserObject();
			if (saleOrg.isIsBizUnit()) {
				FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] { actionAddNew, actionEdit, actionCancel, actionCancelCancel }, true);
			}
			filter.getFilterItems().add(new FilterItemInfo("project.id", spInfo.getId()));
		} else {			
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] { actionAddNew, actionCancel, actionCancelCancel }, false);
			filter.getFilterItems().add(new FilterItemInfo("project.id", "nullnull"));
		}
		
		if (viewInfo.getFilter() == null)
			viewInfo.setFilter(filter);
		else {
			try {
				viewInfo.getFilter().mergeFilter(filter, "AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof String) {
			MsgBox.showInfo("请选择具体销售项目！");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
		super.prepareUIContext(uiContext, e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return AgioEditUI.class.getName();
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

	public void onLoad() throws Exception {
		this.initTree();
		super.onLoad();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			actionCancel.setEnabled(false);
			actionCancelCancel.setEnabled(false);
		}
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionCancel.setVisible(true);
		this.actionCancelCancel.setVisible(true);
		this.actionAttachment.setVisible(false);
		this.actionQuery.setVisible(true);
		this.actionAudit.setEnabled(false);
		this.actionUnAudit.setEnabled(false);
		
		if(this.tblMain.getColumn("pro")!=null){
			this.tblMain.getColumn("pro").getStyleAttributes().setNumberFormat("##.00");
		}
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		AgioBillInfo agioInfo = AgioBillFactory.getRemoteInstance().getAgioBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
		if (agioInfo != null && agioInfo.getState().equals(FDCBillStateEnum.SUBMITTED)) {
			agioInfo.setAuditor(SysContext.getSysContext().getCurrentUserInfo());
			agioInfo.setAuditTime(FDCDateHelper.getServerTimeStamp());
			agioInfo.setState(FDCBillStateEnum.AUDITTED);
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("auditor");
			sel.add("auditTime");
			sel.add("state");
			AgioBillFactory.getRemoteInstance().updatePartial(agioInfo, sel);
			MsgBox.showInfo("审批成功！");
			actionRefresh_actionPerformed(e);
		} else {
			MsgBox.showInfo("只有已提交的折扣才可以审批");
			return;
		}

	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		AgioBillInfo agioInfo = AgioBillFactory.getRemoteInstance().getAgioBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
		//by time_gao 2010-11-2
		if(agioInfo.isIsEnabled()){
			MsgBox.showInfo("数据已启用不能反审核！");
			return;
		}
		if (agioInfo != null && agioInfo.getState().equals(FDCBillStateEnum.AUDITTED)) {
			agioInfo.setAuditor(null);
			agioInfo.setAuditTime(FDCDateHelper.getServerTimeStamp());
			agioInfo.setState(FDCBillStateEnum.SUBMITTED);
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("auditor");
			sel.add("auditTime");
			sel.add("state");
			AgioBillFactory.getRemoteInstance().updatePartial(agioInfo, sel);
			MsgBox.showInfo("反审批成功！");
			actionRefresh_actionPerformed(e);
		} else {
			MsgBox.showInfo("只有已审批的折扣才可以反审批");
			return;
		}
		super.actionUnAudit_actionPerformed(e);
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		AgioBillInfo agioInfo = AgioBillFactory.getRemoteInstance().getAgioBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
		if (agioInfo != null && agioInfo.isIsEnabled()) {
//			agioInfo.setEnabledMan(SysContext.getSysContext().getCurrentUserInfo());这句话放到这里很疑惑
			agioInfo.setUnenableMan(SysContext.getSysContext().getCurrentUserInfo());
			agioInfo.setUnenabledDate(FDCDateHelper.getServerTimeStamp());
			agioInfo.setIsEnabled(false);
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("unenableMan");
			sel.add("unenabledDate");
			sel.add("isEnabled");
			AgioBillFactory.getRemoteInstance().updatePartial(agioInfo, sel);
			MsgBox.showInfo(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
			actionRefresh_actionPerformed(e);
		} else {
			MsgBox.showInfo("只有启用折扣方案才能禁用！");
			return;
		}
	}

	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		AgioBillInfo agioInfo = AgioBillFactory.getRemoteInstance().getAgioBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
		if (agioInfo != null && agioInfo.getState().equals(FDCBillStateEnum.AUDITTED)) {
//			添加了设置其用人 xiaoao_liu
			agioInfo.setEnabledMan(SysContext.getSysContext().getCurrentUserInfo());
			agioInfo.setEnabledDate(FDCDateHelper.getServerTimeStamp());
			agioInfo.setIsEnabled(true);
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("enabledMan");
			sel.add("enabledDate");
			sel.add("isEnabled");
			AgioBillFactory.getRemoteInstance().updatePartial(agioInfo, sel);
			MsgBox.showInfo(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			actionRefresh_actionPerformed(e);
		} else {
			MsgBox.showInfo("只有已审批的折扣才可以启用");
			return;
		}

	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		if (!saleOrg.isIsBizUnit()) {
			return;
		}
		this.checkSelected();
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(activeRowIndex);
		String id = row.getCell("id").getValue().toString().trim();
		AgioBillInfo agioInfo = AgioBillFactory.getRemoteInstance().getAgioBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
		if (agioInfo != null && agioInfo.isIsEnabled()) {
			actionCancelCancel.setEnabled(false);
			actionCancel.setEnabled(true);
		} else {
			actionCancelCancel.setEnabled(true);
			actionCancel.setEnabled(false);
			actionEdit.setEnabled(true);
			actionRemove.setEnabled(true);
		}
		if (agioInfo != null && FDCBillStateEnum.AUDITTED.equals(agioInfo.getState())) {
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
			actionCancelCancel.setEnabled(!agioInfo.isIsEnabled());
			actionCancel.setEnabled(agioInfo.isIsEnabled());
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(true);
		} else if (agioInfo != null && FDCBillStateEnum.SAVED.equals(agioInfo.getState())) {
			actionEdit.setEnabled(true);
			actionRemove.setEnabled(true);
			actionCancelCancel.setEnabled(false);
			actionCancel.setEnabled(false);
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		} else if (agioInfo != null && FDCBillStateEnum.SUBMITTED.equals(agioInfo.getState())) {
			actionEdit.setEnabled(true);
			actionRemove.setEnabled(true);
			actionCancelCancel.setEnabled(false);
			actionCancel.setEnabled(false);
			actionAudit.setEnabled(true);
			actionUnAudit.setEnabled(false);
		} else {
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
			actionCancelCancel.setEnabled(false);
			actionCancel.setEnabled(false);
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		}

	}

	protected String getLongNumberFieldName() {
		return "number";
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (confirmRemove()) {
			// prepareRemove(null).callHandler();
			// 判断如果该折扣已被引用，则不能被删除
//			int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
//			IRow row = this.tblMain.getRow(activeRowIndex);
//			String id = row.getCell("id").getValue().toString().trim();
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("agio.id", id));
//			if (PurchaseAgioEntryFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showInfo("该折扣已被引用，不能删除");
//				SysUtil.abort();
//			}
			Remove();
			refresh(e);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
//		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
//		IRow row = this.tblMain.getRow(activeRowIndex);
//		String id = row.getCell("id").getValue().toString().trim();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("agio.id", id));
//		if (PurchaseAgioEntryFactory.getRemoteInstance().exists(filter)) {
//			MsgBox.showInfo("该折扣已被引用，不能修改");
//			SysUtil.abort();
//		}
		super.actionEdit_actionPerformed(e);
	}
}