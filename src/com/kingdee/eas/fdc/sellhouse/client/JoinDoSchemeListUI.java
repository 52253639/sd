/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.IJoinDoScheme;
import com.kingdee.eas.fdc.sellhouse.JoinDoSchemeCollection;
import com.kingdee.eas.fdc.sellhouse.JoinDoSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.util.FilterUtility;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class JoinDoSchemeListUI extends AbstractJoinDoSchemeListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(JoinDoSchemeListUI.class);
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public JoinDoSchemeListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionRow(0);

		this.btnCancel.setVisible(true);
		this.btnCancelCancel.setVisible(true);
		tblMain.removeRows(false);

		this.actionAddNew.setEnabled(false);
		
		//非售楼组织不能操作
		if (!FDCSysContext.getInstance().checkIsSHEOrg()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
		}
		
		
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			Boolean bol = (Boolean) row.getCell("isEnable").getValue();
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

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();
		IJoinDoScheme joinDoScheme = (IJoinDoScheme) getBizInterface();
		JoinDoSchemeInfo jdsInfo = joinDoScheme
				.getJoinDoSchemeInfo(new ObjectUuidPK(id));
		if (jdsInfo != null) {
			if (jdsInfo.isIsEnabled()) {
				MsgBox.showWarning(this, "所选方案已经启用，无法修改！");
			} else {
				super.actionEdit_actionPerformed(e);
			}
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();
		IJoinDoScheme joinDoScheme = (IJoinDoScheme) getBizInterface();
		JoinDoSchemeInfo jdsInfo = joinDoScheme
				.getJoinDoSchemeInfo(new ObjectUuidPK(id));
		if (jdsInfo != null) {
			if (jdsInfo.isIsEnabled()) {
				MsgBox.showWarning(this, "所选方案已经启用，无法删除！");
			} else {
				super.actionRemove_actionPerformed(e);
			}
		}
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList = this.getSelectedIdValues();
		if (idList != null && idList.size()>0) {
			isSchemeEnabled(idList,false);
			JoinDoSchemeFactory.getRemoteInstance().setEnabled(idList, false);
			MsgBox.showInfo("禁用成功!");
			this.refresh(null);
		
		}else{
			MsgBox.showInfo("请选择记录!");
			return;
		}
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		List idList = this.getSelectedIdValues();
		if (idList != null && idList.size()>0) {
			isSchemeEnabled(idList,true);
			JoinDoSchemeFactory.getRemoteInstance().setEnabled(idList, true);
			MsgBox.showInfo("启用成功!");
		}else{
			MsgBox.showInfo("请选择记录!");
			return;
		}
		this.refresh(null);
	}
	
	private void isSchemeEnabled(List idList,boolean isCancle) throws BOSException {
		StringBuffer idStr = new StringBuffer();
		for (int i = 0; i < idList.size(); i++) {
			idStr.append("'");
			idStr.append(idList.get(0).toString());
			idStr.append("'");
			if(i!=idList.size()-1){
				idStr.append(",");
			}
		}
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("isEnabled"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",idStr.toString(),CompareType.INNER));
		view.setFilter(filter);
		view.setSelector(coll);
		JoinDoSchemeCollection priceColl=JoinDoSchemeFactory.getRemoteInstance().getJoinDoSchemeCollection(view);
		boolean isEnabled = true;
		for (int i = 0; i < priceColl.size(); i++) {
			JoinDoSchemeInfo info  = priceColl.get(i);
			if(info!=null){
				if(isCancle){
					if(info.isIsEnabled()){
						isEnabled = false;
					}
				}else{
					if(!info.isIsEnabled()){
						isEnabled = false;
					}
				}
				
			}
		}
		
		if(isCancle){
			if(!isEnabled){
				FDCMsgBox.showWarning(this,"所选记录中已有启用的方案,请检查!");
				this.abort();
			}
		}else{
			if(!isEnabled){
				FDCMsgBox.showWarning(this,"所选记录中已有禁用的方案,请检查!");
				this.abort();
			}
		}
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return JoinDoSchemeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return JoinDoSchemeEditUI.class.getName();
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew) || act.equals(actionEdit)
				|| (act.equals(actionView))) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
			if (node != null) {
				if (node.getUserObject() instanceof SellProjectInfo) {
					uiContext.put("sellProject", node.getUserObject());
				}
			}
		}
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			if (FDCSysContext.getInstance().checkIsSHEOrg()) {
				this.actionAddNew.setEnabled(true);
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			this.actionAddNew.setEnabled(false);
		} else {
			this.actionAddNew.setEnabled(false);
		}
		this.refreshList();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return super.getQueryExecutor(queryPK, viewInfo);
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			if (FDCSysContext.getInstance().checkIsSHEOrg()) {
				filter.getFilterItems().add(
						new FilterItemInfo("project", ((SellProjectInfo) node
								.getUserObject()).getId()));
				/*String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject")
						.keySet());
				if (allSpIdStr.trim().length() == 0) {
					allSpIdStr = "'nullnull'";
				}

				filter.getFilterItems().add(new FilterItemInfo("project.id", allSpIdStr, CompareType.INNER));*/
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			Map sellProMap = FDCTreeHelper.getAllObjectIdMap(node,
					"SellProject");
			Iterator iter = sellProMap.keySet().iterator();
			Set sellProIdSet = new HashSet();
			while (iter.hasNext())
				sellProIdSet.add(iter.next());
			if (sellProIdSet.size() > 0) {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", sellProIdSet,
								CompareType.INCLUDE));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("project.id", null));
			}
		}

		if (FilterUtility.hasFilterItem(filter)) {
			try {
				filter.mergeFilter(viewInfo.getFilter(), "and");
				EntityViewInfo evi = (EntityViewInfo) viewInfo.clone();
				evi.setFilter(filter);
				return super.getQueryExecutor(queryPK, evi);
			} catch (BOSException e) {
				e.printStackTrace();
				return super.getQueryExecutor(queryPK, viewInfo);
			}
		} else {
			return super.getQueryExecutor(queryPK, viewInfo);
		}
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		/*if(getExistChild()>0){
			FDCMsgBox.showWarning(this,"非末级项目不能新增!");
			this.abort();
		}*/
		super.actionAddNew_actionPerformed(e);
	}
	

	private int getExistChild() {
		DefaultKingdeeTreeNode node = null;
		int number = 0;

		node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null) {
			number = node.getChildCount();
		}
		return number;
	}
}