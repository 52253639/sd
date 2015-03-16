/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingPriceSetFactory;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeCollection;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PriceSetSchemeListUI extends AbstractPriceSetSchemeListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PriceSetSchemeListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
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

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		if (!saleOrg.isIsBizUnit()) {
			return;
		}
		String id = this.getSelectedKeyValue();
		if (id == null) {
			return;
		}
		PriceSetSchemeInfo scheme = PriceSetSchemeFactory.getRemoteInstance()
				.getPriceSetSchemeInfo(new ObjectUuidPK(BOSUuid.read(id)));
		if (scheme.isIsEnabled()) {
			this.actionCancel.setEnabled(true);
			this.actionCancelCancel.setEnabled(false);
		} else {
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(true);
		}
	}

	/**
	 * output class constructor
	 */
	public PriceSetSchemeListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.actionQuery.setVisible(false);
		super.onLoad();
		this.actionCancel.setVisible(true);
		this.actionCancelCancel.setVisible(true);
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
		}
		
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof SellProjectInfo) {
			String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject")
					.keySet());
			if (allSpIdStr.trim().length() == 0) {
				allSpIdStr = "'nullnull'";
			}

			filter.getFilterItems().add(new FilterItemInfo("project.id", allSpIdStr, CompareType.INNER));
			if (saleOrg.isIsBizUnit()) {
				this.actionAddNew.setEnabled(true);
			}
		} else if(node.getUserObject() instanceof OrgStructureInfo){
			
			String orgUnitIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper
					.getAllObjectIdMap(node, "SellProject").keySet());
			if (orgUnitIdStr.trim().length() == 0) {
				orgUnitIdStr = "'nullnull'";
			}
			filter.getFilterItems().add(
					new FilterItemInfo("project.id", orgUnitIdStr,
							CompareType.INNER));
			
		} else {
			filter.getFilterItems().add(new FilterItemInfo("project.id", null));
			this.actionAddNew.setEnabled(false);
		}
		this.mainQuery.setFilter(filter);
		//tHelper.setDefaultSolution(false);
		this.tblMain.removeRows();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	protected String getEditUIName() {
		return PriceSetSchemeEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PriceSetSchemeFactory.getRemoteInstance();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if (id != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("priceScheme.id", id));
			if (BuildingPriceSetFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经参与定价,不能删除!");
				return;
			}
		}
//		增加判断，当前单据为启用状态 时不允许删除 ，by xiaoao_liu
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("id",id));
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.valueOf(true)));//判断是否启用
    	if(this.getBizInterface().exists(filter)){//判断记录是否存在
    		MsgBox.showWarning("本记录已经启用，不能删除");
    		return;
    	}
		checkSelected();
		if (confirmRemove()) {
//			prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if (id != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("priceScheme.id", id));
			if (BuildingPriceSetFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经参与定价,不能修改!");
				return;
			}
		}
//		增加判断，当前单据为启用状态 时不允许修改，by xiaoao_liu
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("id",this.getSelectedKeyValue()));
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.valueOf(true)));//判断是否启用
    	if(this.getBizInterface().exists(filter)){//判断记录是否存在
    		MsgBox.showWarning("本记录已经启用，不能修改");
    		return;
    	}
		super.actionEdit_actionPerformed(e);
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		List idList = this.getSelectedIdValues();
		if (idList != null && idList.size()>0) {
			isSchemeEnabled(idList,false);
			PriceSetSchemeFactory.getRemoteInstance().setEnabled(idList, false);
			MsgBox.showInfo("禁用成功!");
		}else{
			MsgBox.showInfo("请选择记录!");
			return;
		}
			
		String id = "";
		for (int i = 0; i < idList.size(); i++) {
			id = idList.get(i).toString();
			CacheServiceFactory.getInstance().discardType(new ObjectUuidPK(id).getObjectType());
		}
	
		this.refresh(null);
	}

	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		List idList = this.getSelectedIdValues();
		if (idList != null && idList.size()>0) {
			isSchemeEnabled(idList,true);
			PriceSetSchemeFactory.getRemoteInstance().setEnabled(idList, true);
			MsgBox.showInfo("启用成功!");
		}else{
			MsgBox.showInfo("请选择记录!");
			return;
		}
		String id = "";
		for (int i = 0; i < idList.size(); i++) {
			id = idList.get(i).toString();
			CacheServiceFactory.getInstance().discardType(new ObjectUuidPK(id).getObjectType());
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
		PriceSetSchemeCollection priceColl=PriceSetSchemeFactory.getRemoteInstance().getPriceSetSchemeCollection(view);
		boolean isEnabled = true;
		for (int i = 0; i < priceColl.size(); i++) {
			PriceSetSchemeInfo info  = priceColl.get(i);
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

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if(getExistChild()>0){
			FDCMsgBox.showWarning(this,"非末级项目不能新增!");
			this.abort();
		}
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