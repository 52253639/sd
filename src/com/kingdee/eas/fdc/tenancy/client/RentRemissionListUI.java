/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.RentRemissionFactory;
import com.kingdee.eas.fdc.tenancy.RentRemissionInfo;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RentRemissionListUI extends AbstractRentRemissionListUI {	
    
	private static final Logger logger = CoreUIObject.getLogger(RentRemissionListUI.class);

	/**
	 * output class constructor
	 */
	public RentRemissionListUI() throws Exception {
		super();
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
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();

			FilterInfo filter = new FilterInfo();
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", pro.getId().toString()));
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
		this.actionAttachment.setVisible(true);
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
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {			
		String id = getSelectedId();
		RentRemissionInfo remissionInfo = RentRemissionFactory.getRemoteInstance().getRentRemissionInfo(new ObjectUuidPK(id));	
    	FDCBillStateEnum rentRemisState=remissionInfo.getState();
		if(!FDCBillStateEnum.SUBMITTED.equals(rentRemisState)){
    		MsgBox.showInfo(this, "只有已提交的租金减免单才能审批！");
    		this.abort();
    	}
		RentRemissionFactory.getRemoteInstance().audit(BOSUuid.read(id));
    	this.refresh(null);
    	super.actionAudit_actionPerformed(e);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = getSelectedId();
		RentRemissionInfo remissionInfo = RentRemissionFactory.getRemoteInstance().getRentRemissionInfo(new ObjectUuidPK(id));	
    	FDCBillStateEnum rentRemisState=remissionInfo.getState();
		if(!FDCBillStateEnum.SAVED.equals(rentRemisState) && !FDCBillStateEnum.SUBMITTED.equals(rentRemisState)){
    		MsgBox.showInfo(this, "只有保存或者提交状态的的租金减免单才能删除！");
    		this.abort();
    	}
		super.actionRemove_actionPerformed(e);	
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	String id = getSelectedId();
		RentRemissionInfo remissionInfo = RentRemissionFactory.getRemoteInstance().getRentRemissionInfo(new ObjectUuidPK(id));	
    	FDCBillStateEnum rentRemisState=remissionInfo.getState();
    	if(!FDCBillStateEnum.SAVED.equals(rentRemisState) && !FDCBillStateEnum.SUBMITTED.equals(rentRemisState)){
    		MsgBox.showInfo("保存或提交状态的的租金减免单才能修改！");
			abort();
    	}
    	super.actionEdit_actionPerformed(e);
    }
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
	}

	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return RentRemissionFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return RentRemissionEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}
	public void actionUnaudit_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedId();
		IRow row = this.getSelectedRow();
		if(row!=null){
			String state = ((BizEnumValueDTO)(row.getCell("state").getValue())).getString();
			if(FDCBillStateEnum.AUDITTED_VALUE.equals(state)){
				if (id != null) {
					RentRemissionFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
				}
				FDCClientUtils.showOprtOK(this);
				refresh(null);
			}else{
				MsgBox.showInfo("只有审批的单据才能反审批！");
				this.abort();
			}
		}else{
			MsgBox.showInfo("请先选择一行！");
			this.abort();
		}
		
	}
}