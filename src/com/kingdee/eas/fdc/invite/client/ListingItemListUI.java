/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.fdc.invite.ListingItemFactory;
import com.kingdee.eas.fdc.invite.NewListTempletEntryFactory;
import com.kingdee.eas.fdc.invite.NewListingEntryFactory;
import com.kingdee.eas.fdc.invite.RefPriceFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 *
 * @author 肖飙彪_金蝶深圳分公司
 *
 */
public class ListingItemListUI extends AbstractListingItemListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ListingItemListUI.class);

	/**
	 * output class constructor
	 */
	public ListingItemListUI() throws Exception {
		super();
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
		KDTable table = this.tblMain;
		int[] rows = KDTableUtil.getSelectedRows(this.tblMain);
		boolean deleteEnabled = true;

		for (int i = 0; i < rows.length; i++) {
			IRow row = table.getRow(rows[i]);
			if (row.getCell("orgUnit.id").getValue()==null||!currentOrg.getId().toString().equals(
					row.getCell("orgUnit.id").getValue().toString())) {
				deleteEnabled = false;
				break;
			}
		}

		if (deleteEnabled) {
			this.actionRemove.setEnabled(true);
		} else {

			this.actionRemove.setEnabled(false);
		}

		if (rows.length > 0) {
			IRow row = table.getRow(rows[0]);
			if (row.getCell("orgUnit.id").getValue()!=null&&currentOrg.getId().toString().equals(
					row.getCell("orgUnit.id").getValue().toString())) {
				this.actionEdit.setEnabled(true);
			} else {
				this.actionEdit.setEnabled(false);
			}
		}
	}

	protected String getEditUIModal() {
		// TODO 自动生成方法存根
		return UIFactoryName.MODEL;
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception{
		if (tblMain.getRowCount() == 0
				|| tblMain.getSelectManager().size() == 0) {
			MsgBox.showWarning("请先选择清单子目！");
			return;
		}
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
//		String id = getSelectedKeyValue();
		if (tblMain.getRowCount() == 0
				|| tblMain.getSelectManager().size() == 0) {
			MsgBox.showWarning(this,EASResource.getString(resourcePath, "pleaseSelectOneItem"));
			return;
		}
//		ListingItemInfo listingItemInfo = ListingItemFactory
//				.getRemoteInstance().getListingItemInfo(new ObjectUuidPK(id));
//		String orgUnitId = listingItemInfo.getOrgUnit().getId().toString();
		// if (SysContext.getSysContext().getCurrentOrgUnit().getId().toString()
		// .equals(orgUnitId)) {
		// }
		super.actionView_actionPerformed(e);

	}
    //BT362720修复。兰远军 2009-09-28
     protected String[] getLocateNames() {
		String[] locateByName = new String[]{IFWEntityStruct.dataBase_Name};
		return locateByName;
	}
	
	
	/**
	 * output actionImportData_actionPerformed
	 */
	public void actionImportData_actionPerformed(ActionEvent e)
			throws Exception {
		//super.actionImportData_actionPerformed(e);
		this.actionImportPrice_actionPerformed(e);

//		AbstractButton refreshBtn = new KDWorkButton();
//		refreshBtn.setAction(actionRefresh);
//		e.setSource(refreshBtn);
//		actionRefresh_actionPerformed(e);
	}

	protected String getEditUIName() {
		return ListingItemEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ListingItemFactory.getRemoteInstance();
	}

	// 右边列表是左边两棵树的交集
	protected FilterInfo getMainFilter() {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeOrg
				.getLastSelectedPathComponent();
		if (orgNode != null
				&& orgNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo org = (OrgStructureInfo) orgNode.getUserObject();
			String orgId = org.getUnit().getId().toString();
			filter.getFilterItems()
					.add(new FilterItemInfo("orgUnit.id", orgId));
		}
		DefaultKingdeeTreeNode headNode = (DefaultKingdeeTreeNode) treeHeadType
				.getLastSelectedPathComponent();
		if (headNode != null
				&& headNode.getUserObject() instanceof HeadTypeInfo) {
			HeadTypeInfo headType = (HeadTypeInfo) headNode.getUserObject();
//			filter.getFilterItems().add(
//					new FilterItemInfo("headType.longNumber", headType
//							.getLongNumber()
//							+ "%", CompareType.LIKE));
			FilterInfo myfilter=new FilterInfo();
			myfilter.getFilterItems().add(new FilterItemInfo("headType.longNumber", headType.getLongNumber() + "!%", CompareType.LIKE));
			myfilter.getFilterItems().add(new FilterItemInfo("headType.longNumber", headType.getLongNumber()));
			myfilter.setMaskString("#0 or #1");
			try {
				filter.mergeFilter(myfilter, "and");
			} catch (BOSException e) {
				handUIException(e);
			}

		}

		return filter;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		// 在ui设计页面隐藏不掉的按钮，菜单
		this.MenuItemAttachment.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnAuditResult.setVisible(false);
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
		this.tblMain.getColumn("isImportant").getStyleAttributes().setHided(
				true);
		this.tblMain.getColumn("orgUnit.name").setWidth(200);
		// 暂时屏蔽
		this.menuItemImportData.setVisible(true);
		this.menuItemImportData.setText(this.btnImportPrice.getText());
		this.btnimport.setVisible(false);
		//this.actionImportPrice.setVisible(false);
		
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeOrg
		.getLastSelectedPathComponent();
		if (orgNode == null) {
			return;
		}
		OrgStructureInfo org = (OrgStructureInfo) orgNode.getUserObject();
		String orgId = org.getUnit().getId().toString();
		if (currentOrg.getId().toString().equals(orgId)) {
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
			this.actionImportPrice.setEnabled(true);
		}
		else
		{
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);	
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
			this.actionImportPrice.setEnabled(false);
		}
	}

	protected void initWorkButton() {
		// TODO 自动生成方法存根
		super.initWorkButton();
		this.btnimport.setIcon(EASResource.getIcon("imgTbtn_input"));
		this.btnImportPrice.setIcon(EASResource.getIcon("imgTbtn_input"));
		this.actionImportPrice.setEnabled(true);
	}

	/*
	 * 导入参数设置 jackwang 2006/11/16
	 */
	protected ArrayList getImportParam() {
		DatataskParameter param = new DatataskParameter();
		String solutionName = "eas.fdc.invite.listingItem";
		param.solutionName = solutionName;
		param.alias = EASResource.getString(resourcePath, "ListItem");
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		return paramList;
	}
	
	
	
	

	
	public void actionImportPrice_actionPerformed(ActionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeOrg
				.getLastSelectedPathComponent();
		if(orgNode==null){
			MsgBox.showInfo(this,EASResource.getString(resourcePath,"selectedOrgNotCurrentOrg"));
			return;
		}
		String selCompName = orgNode.getText();
		if(!currentOrg.getName().equals(selCompName)){
			MsgBox.showInfo(this,EASResource.getString(resourcePath,"selectedOrgNotCurrentOrg"));
			return;
		}

		HeadTypeInfo headType = null;
		DefaultKingdeeTreeNode headNode = (DefaultKingdeeTreeNode) treeHeadType
				.getLastSelectedPathComponent();
		if (headNode != null
				&& headNode.getUserObject() instanceof HeadTypeInfo) {
			headType = (HeadTypeInfo) headNode.getUserObject();
			if (!headType.isIsLeaf())
				headType = null;
		}

		UIContext uiContext = new UIContext(this);
		uiContext.put("headType", headType);
		uiContext.put("selCompName", selCompName);
		// 创建UI对象并显示
		IUIWindow impUI = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(ImportExcelPriceUI.class.getName(), uiContext, null,
						"View");
		impUI.show();
		treeHeadType_valueChanged(null);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		ArrayList ids = getSelectedIdValues();
		for (int i = 0; i < ids.size(); i++) {
			String id = (String) ids.get(i);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("item.id", id));
			if (NewListTempletEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showError("子目被模板引用,不能删除");
				return;
			}
			if (NewListingEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showError("子目被清单引用,不能删除");
				return;
			}
			if (RefPriceFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showError("含有价格的子目,不能删除");
				return;
			}
		}
		super.actionRemove_actionPerformed(e);
	}

	public static final String resourcePath = "com.kingdee.eas.fdc.invite.FDCInviteResource";
	
	protected void treeOrg_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeOrg
				.getLastSelectedPathComponent();
		if (orgNode != null) {
			OrgStructureInfo org = (OrgStructureInfo) orgNode.getUserObject();
			String orgId = org.getUnit().getId().toString();
			if (currentOrg.getId().toString().equals(orgId)) {
				setEditActionState(true);
			} else {
				setEditActionState(false);
			}
		} else {
			setEditActionState(false);
		}
		fillTable();
	}

	protected void setEditActionState(boolean fState) {
		this.actionAddNew.setEnabled(fState);
		this.actionEdit.setEnabled(fState);
		this.actionRemove.setEnabled(fState);
		this.actionAttachment.setEnabled(fState);
		this.actionImportData.setEnabled(fState);
		this.actionImportPrice.setEnabled(fState);
	}
}