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
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.QuitTenancyInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class QuitTenancyListUI extends AbstractQuitTenancyListUI
{
    private static final Logger logger = CoreUIObject.getLogger(QuitTenancyListUI.class);
    
    /**
     * output class constructor
     */
    public QuitTenancyListUI() throws Exception
    {
        super();
    }
    
	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
			.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
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
				this.actionEdit.setEnabled(true);
				this.actionRemove.setEnabled(true);
				this.actionAudit.setEnabled(true);
				this.actionHandleTenancy.setEnabled(true);
			}
		} else {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionHandleTenancy.setEnabled(false);
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
				filter.getFilterItems().add(new FilterItemInfo("tenancyBill.sellProject.id", pro.getId().toString()));
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
    	this.actionAuditResult.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    }
	
    /**
     * 审批
     * */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionAudit_actionPerformed(e);
    	String id = getSelectedId();
    	QuitTenancyInfo tenBill = QuitTenancyFactory.getRemoteInstance().getQuitTenancyInfo(new ObjectUuidPK(id));
    	FDCBillStateEnum tenState = tenBill.getState();
    	if(!FDCBillStateEnum.SUBMITTED.equals(tenState)){
    		MsgBox.showInfo(this, "只有已提交的单据才能审批！");
    		this.abort();
    	}
    	QuitTenancyFactory.getRemoteInstance().audit(BOSUuid.read(id));
    	this.refresh(null);
    }
    
    /**
     * 房间交接
     * */
    public void actionHandleTenancy_actionPerformed(ActionEvent e) throws Exception {
    	IRow row = this.getSelectedRow();
    	if(row!=null){
    		String  state = ((BizEnumValueDTO)(row.getCell("state").getValue())).getString();
    		if(!FDCBillStateEnum.AUDITTED_VALUE.equals(state)){
    			MsgBox.showInfo(this, "只有已审批的单据才能做房间交接！");
        		this.abort();
    		}
    		
    	}else{
    		MsgBox.showInfo("请选择一行数据！");
    		this.abort();
    	}
    	String id = getSelectedId();
    	QuitTenancyInfo quitTen = QuitTenancyFactory.getRemoteInstance().getQuitTenancyInfo(new ObjectUuidPK(id));
    	String tenId = quitTen.getTenancyBill().getId().toString();
    	
    	UIContext uiContext = new UIContext(this);
		uiContext.put("tenancyBillId", tenId);
		
		//创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(HandleRoomTenancyEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
    }
    
    /**
	 * 退租单删除事件
	 * */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);		
		BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("state")
		.getValue();
		if (FDCBillStateEnum.AUDITTED_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("退租单已经审核不能删除!");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}
	
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("state")
				.getValue();
		if (state.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
			MsgBox.showInfo("退租单已经审核不能修改!");
			return;
		}
		if(state.getValue().equals(FDCBillStateEnum.AUDITTING_VALUE)) {
			MsgBox.showInfo("退租单审批中不能修改!");
			return;
		}
		super.actionEdit_actionPerformed(e);
	}
    
    protected ICoreBase getBizInterface() throws Exception
    {
        return QuitTenancyFactory.getRemoteInstance();
    }
    
    protected String getEditUIName() {
    	return QuitTenancyEditUI.class.getName();
    }
    
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
    
	protected boolean isIgnoreCUFilter() {
		return true;
	}
    
}