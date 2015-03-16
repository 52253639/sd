/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.CompensateSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.ICompensateScheme;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.SellHouseException;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CompensateSchemeListUI extends AbstractCompensateSchemeListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CompensateSchemeListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public CompensateSchemeListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionRow(0);
	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		// 不设为false,则会执行tHelper = new
		// TableListPreferencesHelper(this);导致表格设置不能保存样式
		actionQuery.setEnabled(false);
		super.onLoad();
		actionQuery.setEnabled(true);
		if (!FDCSysContext.getInstance().checkIsSHEOrg()) {
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] {
					actionAddNew, actionEdit, actionRemove, actionCancel, actionCancelCancel }, false);
		}
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CompensateSchemeFactory.getRemoteInstance();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected void refresh(ActionEvent e) throws Exception {
		treeMain_valueChanged(null);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		execQuery();
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			Boolean bol = (Boolean) row.getCell("isEnabled").getValue();
			if (bol.equals(Boolean.TRUE)) {
				btnCancelCancel.setEnabled(false);
				btnCancel.setEnabled(true);
			} else {
				btnCancelCancel.setEnabled(true);
				btnCancel.setEnabled(false);
			}
		}
		super.tblMain_tableClicked(e);
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
//		super.tblMain_tableSelectChanged(e);
	}
	protected boolean isIgnoreCUFilter() {
		return true;
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
			if (FDCSysContext.getInstance().checkIsSHEOrg()) {
				FDCClientHelper.setActionEnableAndNotSetVisible(
						new ItemAction[] { actionAddNew, actionEdit,
								actionRemove, actionCancel, actionCancelCancel }, true);
			}
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] {
					actionAddNew, actionEdit, actionRemove, actionCancel, actionCancelCancel }, false);
		}
		return filter;
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

	protected String getEditUIName() {
		return CompensateSchemeEditUI.class.getName();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkIsEnable();
		checkBeforeEdit();
		super.actionEdit_actionPerformed(e);
	}

	private void checkBeforeEdit() throws SellHouseException, EASBizException, BOSException {
		checkSelected();
		if(getSelectedKeyValue()!=null){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("scheme.id", getSelectedKeyValue()));
			if (RoomAreaCompensateFactory.getRemoteInstance().exists(filter)) {
				throw new SellHouseException(SellHouseException.USEDBYCOMPENSATE);
			}
		}
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}
	
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		ArrayList idList = this.getSelectedIdValues();

		ICompensateScheme cs = (ICompensateScheme) getBizInterface();
		for (int i = 0; i < idList.size(); i++) {
			CompensateSchemeInfo csInfo = cs.getCompensateSchemeInfo(new ObjectUuidPK(
					idList.get(i).toString()));
			if (csInfo != null && !csInfo.isIsEnabled()) {
				csInfo.setIsEnabled(true);
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("isEnabled");
				cs.updatePartial(csInfo, sel);
			}
		}
		MsgBox.showWarning(this, "启用成功！");
		refresh(null);
	}
	
	/**
	 * 禁用
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();
		ICompensateScheme cs = (ICompensateScheme) getBizInterface();
		CompensateSchemeInfo csInfo = cs.getCompensateSchemeInfo(new ObjectUuidPK(id));
		if (csInfo != null) {
			if (!csInfo.isIsEnabled()) {
				MsgBox.showWarning(this, "所选方案已经禁用，无法再禁用！");
			} else {
				csInfo.setIsEnabled(false);
				cs.update(new ObjectUuidPK(id), csInfo);
				MsgBox.showWarning(this, "禁用成功！");
				refresh(null);
			}
		}
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkIsEnable();
		checkBeforeEdit();
		if (confirmRemove()) {
//			prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
	}
	
	protected void checkIsEnable() {
		if(tblMain.getSelectManager().getActiveRowIndex() >=0) {
			IRow tmpRow = tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex());
			Boolean isEnabled = (Boolean)(tmpRow.getCell("isEnabled").getValue());
			
			if(isEnabled.booleanValue())	{
				MsgBox.showWarning("所选项为启用状态，不能执行此操作！");
				abort();
			}
		}
	}
}