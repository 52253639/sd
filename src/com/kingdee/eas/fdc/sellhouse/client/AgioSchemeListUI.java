/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
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
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientCtrler;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.AgioSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.AgioSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.client.ListUIController;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AgioSchemeListUI extends AbstractAgioSchemeListUI {
	private static final Logger logger = CoreUIObject.getLogger(AgioSchemeListUI.class);
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public AgioSchemeListUI() throws Exception {
		super();
	}

	protected void fetchInitData() throws Exception {
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	public void onLoad() throws Exception {
		this.initTree();
		super.onLoad();
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		viewInfo = (EntityViewInfo) this.mainQuery.clone();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		FilterInfo filter = new FilterInfo();
/*		String proIdStrs = "'nullnull'";
		if (node != null) {
			Set keySet = FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet();
			if (keySet.size() > 0)
				proIdStrs = FDCTreeHelper.getStringFromSet(keySet);
		}
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", proIdStrs, CompareType.INNER));*/

		if (node != null && node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo spInfo = (SellProjectInfo)node.getUserObject();
			if (saleOrg.isIsBizUnit()) {
				FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] { actionAddNew, actionEdit,
						actionCancel, actionCancelCancel }, true);
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", spInfo.getId()));
			}
		} else {
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] { actionAddNew, actionCancel,
					actionCancelCancel }, false);
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", "nullnull"));
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

	protected void initTree() throws Exception {
//		this.treeMain.setModel(SHEHelper.getSellProjectTree(actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new AgioSchemeInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AgioSchemeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return AgioSchemeEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return super.getEditUIModal();
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

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		String selectID = this.tblMain.getCell(activeRowIndex, "id").getValue().toString();
		if (outPutWarningSentanceAndVerifyCancelorCancelCancelByID("修改", selectID)) {// 判断是否启用禁用
			return;
		}
		IUIWindow uiWindow = showEditUI1(e);
		uiWindow.show();

		// 如果编辑界面以模式窗口方式打开，则刷新界面(继承EditUI，但isSave()为false的例外)
		// actionEvent = e;
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

	private IUIWindow showEditUI1(ActionEvent e) throws EASBizException, BOSException, Exception {
		checkSelected();
		checkObjectExists();
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		// 供子类定义要传递到EditUI中扩展的属性
		prepareUIContext(uiContext, e);
		IUIWindow uiWindow = null;
		if (SwingUtilities.getWindowAncestor(this) != null && SwingUtilities.getWindowAncestor(this) instanceof JDialog) {
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(getEditUIName(), uiContext, null,
					OprtState.EDIT);

		} else {
			// 创建UI对象并显示
			uiWindow = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,
					OprtState.EDIT);
		}
		return uiWindow;
	}

	private void checkObjectExists() throws BOSException, EASBizException, Exception {
		if (getSelectedKeyValue() == null) {
			return;
		}
		if (!getBizInterface().exists(new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())))) {
			refreshList();
			throw new EASBizException(EASBizException.CHECKEXIST);
		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		String selectID = this.tblMain.getCell(activeRowIndex, "id").getValue().toString();
		if (outPutWarningSentanceAndVerifyCancelorCancelCancelByID("删除", selectID)) {
			return;
		}
		checkSelected();
		if (confirmRemove()) {
			Remove();
			this.refresh(e);
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		prepareUIContext(uiContext, e);
		this.getUIContext().putAll(uiContext);

		IUIWindow uiWindow = null;

		((ListUIController) super.getUIController()).actionAddNew();

		uiWindow = ((ListUIController) super.getUIController()).getNavigator().getUIWindow();

		Window win = SwingUtilities.getWindowAncestor((Component) uiWindow.getUIObject());
		if (!win.isActive()) {
			if (win instanceof JFrame && ((JFrame) win).getExtendedState() == Frame.ICONIFIED) {
				((JFrame) win).setExtendedState(Frame.NORMAL);
			}
		}

		setActionEvent(e);
		if (uiWindow != null && isDoRefresh(uiWindow)) {
			if (UtilRequest.isPrepare("ActionRefresh", this)) {
				prepareRefresh(null).callHandler();
			}
			refresh(e);
			setPreSelecteRow();
		}

	}
	
	protected void setIsEnabled(boolean flag) throws Exception {
//		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		// 不允许操作系统预设数据
//		if (!flag)
//			if (isSystemDefaultData(activeRowIndex)) {
//				MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
//				return;
//			}
		String id = tblMain.getRow(activeRowIndex).getCell("id").getValue().toString().trim();
		FDCDataBaseInfo info = getBaseDataInfo();
		info.setId(BOSUuid.read(id));
		info.setIsEnabled(flag);
		//便于启用、禁用时得到编码及名称
		String number = tblMain.getRow(activeRowIndex).getCell("number").getValue().toString().trim();
		String name = tblMain.getRow(activeRowIndex).getCell("name").getValue().toString().trim();
		info.setNumber(number);
		info.setName(name);
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("isEnabled"));
		String message = null;
		if (flag) {
			getBizInterface().updatePartial(info, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK");
		}
		// ContractCostPropertyFactory.getRemoteInstance().enabled(new
		// ObjectUuidPK(id), info);
		else {
			getBizInterface().updatePartial(info, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK");
		}
		// ContractCostPropertyFactory.getRemoteInstance().disEnabled(new
		// ObjectUuidPK(id), info);

		setMessageText(message);
		showMessage();
		tblMain.refresh();
		loadFields();
		actionCancel.setEnabled(false);
		actionCancelCancel.setEnabled(false);
	}
}