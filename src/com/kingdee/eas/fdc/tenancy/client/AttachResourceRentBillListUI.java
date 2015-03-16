/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.AttachResourceInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentBillFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentBillInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysCollection;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AttachResourceRentBillListUI extends AbstractAttachResourceRentBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AttachResourceRentBillListUI.class);
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    
    public AttachResourceRentBillListUI() throws Exception
    {
        super();
    }
    public void storeFields()
    {
        super.storeFields();
    }
    
    protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
    
    public void onLoad() throws Exception {
    	FDCClientHelper.addSqlMenu(this, this.menuEdit);
    	super.onLoad();
    	initTree();
    	this.treeMain.setSelectionRow(0);
		this.menuItemCreateTo.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.menuItemCopyTo.setVisible(false);
		this.menuItemTraceUp.setVisible(false);
		this.menuItemTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.btnAudit.setEnabled(true);
		this.btnUnAudit.setVisible(false);
		this.btnExecute.setEnabled(true);
		this.btnBlankOut.setEnabled(true);
		this.actionAuditResult.setVisible(false);
    }
    
    /**
	 * 存储项目，相当于request的作用
	 * */
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
	
    protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
	protected String getEditUIName() {
		return AttachResourceRentBillEditUI.class.getName();
	}
	
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
	throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		
		if (node.getUserObject() instanceof SellProjectInfo) {
			if (this.saleOrg.isIsBizUnit()) {
				this.actionAddNew.setEnabled(true);
			}
		} else {
			this.actionAddNew.setEnabled(false);
		}
		this.execQuery();
	}
	
	/**
	 *查询过滤条件
	 * */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
			.getLastSelectedPathComponent();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			
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
	
	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}
	
	/**
	 * 配套资源定租单审核事件
	 * */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("state")
		.getValue();
		if (FDCBillStateEnum.SAVED_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("定租单没有提交!");
			return;
		}
		if (FDCBillStateEnum.AUDITTED_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("定租单已经审核!");
			return;
		}
		if (FDCBillStateEnum.INVALID_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("定租单已经作废!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		AttachResourceRentBillFactory.getRemoteInstance().audit(BOSUuid.read(id));
		this.refresh(null);
	}
	
	/**
	 * 定租单作废事件
	 * */
	public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		if(((Boolean)row.getCell("isExecuted").getValue()).booleanValue())
		{
			MsgBox.showInfo("定租单已执行，不能作废!");
			return;
		}
		BizEnumValueInfo tenancyState = (BizEnumValueInfo) row.getCell(
		"state").getValue();
		if(FDCBillStateEnum.INVALID_VALUE.equals(tenancyState.getValue()))
		{
			MsgBox.showInfo("定租单已经作废!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		if (MsgBox.showConfirm2New(this, "作废后不可恢复，请确认是否作废?") == MsgBox.YES) {
			//作废时需要把状态写入单据中，并且停止当前正在运行的工作流
			AttachResourceRentBillInfo attachInfo = AttachResourceRentBillFactory.getRemoteInstance()
			.getAttachResourceRentBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
			attachInfo.setState(FDCBillStateEnum.INVALID);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("state");
			AttachResourceRentBillFactory.getRemoteInstance().updatePartial(attachInfo, sels);
			
			ProcessInstInfo instInfo = null;
			ProcessInstInfo[] procInsts = null;
			IEnactmentService service = EnactmentServiceFactory
			.createRemoteEnactService();
			procInsts = service.getProcessInstanceByHoldedObjectId(attachInfo.getId()
					.toString());
			for (int j = 0; j < procInsts.length; j++) {
				if ("open.running".equals(procInsts[j].getState())) {
					instInfo = procInsts[j];
					service.abortProcessInst(instInfo.getProcInstId());
				}
				
			}
			this.refresh(null);
		}
	}
	
	/**
	 * 定租单执行事件
	 * */
	public void actionExecute_actionPerformed(ActionEvent e) throws Exception {
		super.actionExecute_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		Boolean isExecuted = (Boolean) row.getCell("isExecuted").getValue();
		if (isExecuted.booleanValue()) {
			MsgBox.showInfo("定租单已经执行!");
			return;
		}
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName == null) {
			MsgBox.showInfo("定租单没有审核!");
			return;
		}
		if (MsgBox.showConfirm2New(this, "是否执行?") == MsgBox.YES) {
			String id = (String) row.getCell(this.getKeyFieldName()).getValue();
//			EntityViewInfo view = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("head.id", id));
//			view.setFilter(filter);
//			view.getSelector().add("*");
//			view.getSelector().add("sellProject.*");
//			view.getSelector().add("entrys.*");
//			view.getSelector().add("entrys.subarea.*");
//			view.getSelector().add("entrys.building.*");
//			AttachResourceRentEntrysCollection attEntrysColl = AttachResourceRentEntrysFactory.getRemoteInstance().getAttachResourceRentEntrysCollection(view);
//			for(int i=0;i<attEntrysColl.size();i++)
//			{
//				AttachResourceRentEntrysInfo attEntrysInfo = attEntrysColl.get(i);
//				AttachResourceInfo info = attEntrysInfo.getAttach();
//				info.getAttachState();
//			}
			if(AttachResourceRentBillFactory.getRemoteInstance().execute(id)) {
			MsgBox.showInfo("执行成功!");
			this.refresh(null);
		} else {
			MsgBox.showInfo("定租单没有审核!");
		}
		}
	}
	
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName != null) {
			MsgBox.showInfo("配套资源定租单已经审批,不能修改!");
			return;
		}
		super.actionEdit_actionPerformed(arg0);
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return AttachResourceRentBillFactory.getRemoteInstance();
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}

}