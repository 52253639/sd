/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.ILoanData;
import com.kingdee.eas.fdc.sellhouse.LoanDataFactory;
import com.kingdee.eas.fdc.sellhouse.RoomJoinFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.config.TableListPreferencesHelper;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class LoanDataListUI extends AbstractLoanDataListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(LoanDataListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public LoanDataListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIName() {
		return LoanDataEditUI.class.getName();
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		// 不设为false,则会执行tHelper = new
		// TableListPreferencesHelper(this);导致表格设置不能保存样式
		actionQuery.setEnabled(false);
		super.onLoad();
		actionQuery.setEnabled(true);
		if (!saleOrg.isIsBizUnit()) {
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] {
					actionAddNew, actionEdit, actionRemove, actionCancel,
					actionCancelCancel }, false);
		}
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return LoanDataFactory.getRemoteInstance();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected void refresh(ActionEvent e) throws Exception {
		treeMain_valueChanged(null);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			if (saleOrg.isIsBizUnit()) {
				FDCClientHelper
						.setActionEnableAndNotSetVisible(new ItemAction[] {
								actionAddNew, actionEdit, actionRemove,
								actionCancel, actionCancelCancel }, true);
			}
		} else {
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] {
					actionAddNew, actionEdit, actionRemove, actionCancel,
					actionCancelCancel }, false);
		}
		execQuery();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof String) {
			MsgBox.showInfo("请选择具体销售项目！");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
					.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
		super.prepareUIContext(uiContext, e);
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		LoanDataFactory.getRemoteInstance().disEnable(new ObjectUuidPK(id));
		MsgBox.showInfo(EASResource.getString(
				FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
		actionRefresh_actionPerformed(e);
	}

	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager()
				.getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		LoanDataFactory.getRemoteInstance().enable(new ObjectUuidPK(id));
		MsgBox.showInfo(EASResource.getString(
				FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
		actionRefresh_actionPerformed(e);
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
		if (saleOrg.isIsBizUnit() && activeRowIndex != -1) {
			if (this.tblMain.getRow(activeRowIndex).getCell("enable") != null) {
				boolean status = ((Boolean) this.tblMain.getCell(
						activeRowIndex, "enable").getValue()).booleanValue();
				actionCancelCancel.setEnabled(!status);
				actionCancel.setEnabled(status);
			}
		} else {
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] {
					actionCancel, actionCancelCancel }, false);
		}
	}

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}

	protected FilterInfo getTreeFilter() throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return null;
		}
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
			filter.getFilterItems()
					.add(
							new FilterItemInfo("sellProject.id", pro.getId()
									.toString()));			
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));			
		}
		return filter;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		viewInfo = (EntityViewInfo) mainQuery.clone();
		// 合并查询条件
		try {
			FilterInfo filter = getTreeFilter();
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if(confirmRemove()) {
			String selectID = this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",selectID,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("isEnable",Boolean.valueOf(true),CompareType.EQUALS));
			if(LoanDataFactory.getRemoteInstance().exists(filter)){
				MsgBox.showInfo("该条记录已启用，不允许删除！");
				this.abort();
			}
//			prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}

	}
	
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		if(LoanDataFactory.getRemoteInstance().exists("where isEnable =1 and id='"+id+"'")) {
			MsgBox.showInfo("该条记录已启用，不允许修改！");
			return;
		}

		
		super.actionEdit_actionPerformed(e);
	}
	
}