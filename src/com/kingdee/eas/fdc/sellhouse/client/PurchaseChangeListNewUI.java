/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

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
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.Uuid;

/**
 * output class name
 */
public class PurchaseChangeListNewUI extends AbstractPurchaseChangeListNewUI
{
    private static final Logger logger = CoreUIObject.getLogger(PurchaseChangeListNewUI.class);
    
    /**
     * output class constructor
     */
    public PurchaseChangeListNewUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	this.kDContainer1.setEnableActive(false);
    	initTree();
    	initButtonStyle();
    }

	protected boolean isIgnoreCUFilter() {
		return true;
	}
    
    private void initButtonStyle(){
    	if(!SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()){
    		this.actionAddNew.setEnabled(false);
    		this.actionEdit.setEnabled(false);
    		this.actionRemove.setEnabled(false);
    	}
    	
    	this.actionCreateTo.setVisible(false);
    	this.actionCopyTo.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.menuBiz.setVisible(false);
    	this.actionAudit.setEnabled(true);
    	
    }

    
    protected void initTree() throws Exception
	{
		this.mainTree.setModel(SHEHelper.getUnitTree(this.actionOnLoad,
				MoneySysTypeEnum.SalehouseSys,true));
		this.mainTree.expandAllNodes(true, (TreeNode) this.mainTree.getModel()
				.getRoot());
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
//		this.tblMain.getColumn("prePurchaseAmount").getStyleAttributes()
//				.setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("prePurchaseAmount").getStyleAttributes()
//				.setNumberFormat(FDCHelper.getNumberFtm(2));
//		this.tblMain.getColumn("dealAmount").getStyleAttributes()
//				.setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("dealAmount").getStyleAttributes()
//				.setNumberFormat(FDCHelper.getNumberFtm(2));
//		this.tblMain.getColumn("dealPrice").getStyleAttributes()
//				.setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("dealPrice").getStyleAttributes()
//				.setNumberFormat(FDCHelper.getNumberFtm(2));
//
//		this.tblMain.getColumn("createTime").getStyleAttributes()
//				.setNumberFormat("yyyy-MM-dd hh:mm:ss");
	}
	
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	// TODO Auto-generated method stub
    	super.prepareUIContext(uiContext, e);
    	setUIContextAboutTreeNode(uiContext);
    }
    
    protected void mainTree_valueChanged(TreeSelectionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.mainTree_valueChanged(e);
    	if(getSelectNode() != null && getSelectNode().getUserObject()!= null){
    		if(getSelectNode().getUserObject() instanceof OrgStructureInfo){
    			this.actionAddNew.setEnabled(false);
    			this.actionEdit.setEnabled(false);
    			this.actionRemove.setEnabled(false);
    		}else{
    			this.actionAddNew.setEnabled(true);
    			this.actionEdit.setEnabled(true);
    			this.actionRemove.setEnabled(true);
    		}
    	}
    	
    	tblMain.removeRows();
    }
    
    
    public DefaultKingdeeTreeNode getSelectNode(){
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) mainTree.getLastSelectedPathComponent();
    	if(node != null){
    		return node;
    	}else{
    		return null;
    	}
    }
    
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
    		EntityViewInfo viewInfo) {
    	
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) mainTree.getLastSelectedPathComponent();
		if(node==null) node = (DefaultKingdeeTreeNode)mainTree.getModel().getRoot();
		
		String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet());
		if(allSpIdStr.trim().length()==0)
			allSpIdStr = "'nullnull'"; 
		
		try	{
			FilterInfo filter = new FilterInfo();
			if(node!=null){
				if (node.getUserObject() instanceof SellProjectInfo)		{
					SellProjectInfo spInfo = (SellProjectInfo)node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", spInfo.getId().toString()));
				}else if (node.getUserObject() instanceof SubareaInfo)		{
					SubareaInfo subInfo = (SubareaInfo) node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("subarea.id", subInfo.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingInfo)		{
					BuildingInfo bdInfo = (BuildingInfo)node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("building.id", bdInfo.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingUnitInfo)		{
					//BuildingInfo bdInfo = (BuildingInfo)((DefaultKingdeeTreeNode)node.getParent()).getUserObject();
					BuildingUnitInfo buInfo = (BuildingUnitInfo)node.getUserObject();
					/*filter.getFilterItems().add(new FilterItemInfo("building.id", bdInfo.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("room.unit", new Integer(buInfo.getSeq())));
					现在已近改为buildUnit这个字段 ，这里的过滤条件也改掉 xiaoao_liu*/
					filter.getFilterItems().add(new FilterItemInfo("room.buildUnit.id", buInfo.getId().toString()));
				}
			}
			if(filter.getFilterItems().size()==0)
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", allSpIdStr ,CompareType.INNER));
			
			
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (Exception e)
		{
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
    	
    }
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
//    	super.actionAudit_actionPerformed(e);
    	checkSelected();
		String id = KDTableUtil.getSelectedRow(tblMain).getCell("id").getValue().toString();

		PurchaseChangeInfo changeInfo = PurchaseChangeFactory.getRemoteInstance().getPurchaseChangeInfo("where id = '" + id + "'");
		if (FDCBillStateEnum.SUBMITTED.equals(changeInfo.getState())) {
			PurchaseChangeFactory.getRemoteInstance().audit(BOSUuid.read(id));
			showOprtOKMsgAndRefresh();
		} else {
			MsgBox.showWarning("当前单据不允许审批！");
			SysUtil.abort();
		}

		
    }
    
    
    private void setUIContextAboutTreeNode(com.kingdee.eas.common.client.UIContext uiContext)
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) mainTree.getLastSelectedPathComponent();
//		if(sellProjectInfo==null && building == null && buildUnit == null){
//			FDCMsgBox.showError("请选择相应的销售项目或者楼幢！");
//			abort();
//		}
		
		if(node == null){
			FDCMsgBox.showError("请选择相应的销售项目或者楼栋！");
			abort();
		}else if (node.getUserObject() instanceof OrgStructureInfo){
			FDCMsgBox.showError("请选择销售项目或者楼栋！");
			abort();
		}
		
		BuildingInfo building = null;
		BuildingUnitInfo buildUnit = null;
		SellProjectInfo sellProjectInfo = null;
		if (node.getUserObject() instanceof Integer)
		{ // 已作废
			Integer unit = (Integer) node.getUserObject();
			uiContext.put("unit", unit);
			building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			uiContext.put("building", building);
			uiContext.put("sellProject", building.getSellProject());
		}
		if (node.getUserObject() instanceof BuildingUnitInfo)
		{
			buildUnit = (BuildingUnitInfo) node
					.getUserObject();
			uiContext.put("buildUnit", buildUnit);
			building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			uiContext.put("building", building);
			uiContext.put("sellProject", building.getSellProject());
		} else if (node.getUserObject() instanceof BuildingInfo)	{
			building = (BuildingInfo) node.getUserObject();
			if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum))
			{
				uiContext.put("building", building);
				uiContext.put("sellProject", building.getSellProject());
				uiContext.put("unit", new Integer(0));
			}
		}
		else if(node.getUserObject() instanceof SellProjectInfo){
			sellProjectInfo = (SellProjectInfo) node.getUserObject();
			if(sellProjectInfo!=null){
				uiContext.put("sellProject", sellProjectInfo);
			}
		}
		
	}

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionView_actionPerformed(e);
    	checkSelected();
        String id = KDTableUtil.getSelectedRow(this.tblMain).getCell("id").getValue().toString();
        

        UIContext context = new UIContext(ui);
        context.put(UIContext.ID, id);
        context.put(UIContext.OWNER, this);
        try {
	       IUIWindow window = UIFactory.createUIFactory(UIFactoryName.NEWWIN).create(PurchaseChangeBillEditUI.class.getName(), context,
					null, OprtState.VIEW);
	     window.show();
		} catch (UIException e1) {
			this.handleException(e1);
		}
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionEdit_actionPerformed(e);
    	checkSelected();
    	String id = KDTableUtil.getSelectedRow(this.tblMain).getCell("id").getValue().toString();
    	PurchaseChangeInfo changeInfo = PurchaseChangeFactory.getRemoteInstance().getPurchaseChangeInfo(new ObjectUuidPK(BOSUuid.read(id)));
    	if(!canOprt(changeInfo.getState(), getCanChangeState())){
    		FDCMsgBox.showError("当前单据的状态不适合修改!");
    		abort();
    	}
        
        

        UIContext context = new UIContext(ui);
        context.put("src","LISTUI");
        context.put(UIContext.ID, id);
        context.put(UIContext.OWNER, this);
        try {
	       IUIWindow window = UIFactory.createUIFactory(UIFactoryName.NEWWIN).create(PurchaseChangeBillEditUI.class.getName(), context,
					null, OprtState.EDIT);
	     window.show();
		} catch (UIException e1) {
			this.handleException(e1);
		}
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	String id = KDTableUtil.getSelectedRow(this.tblMain).getCell("id").getValue().toString();
    	PurchaseChangeInfo changeInfo = PurchaseChangeFactory.getRemoteInstance().getPurchaseChangeInfo(new ObjectUuidPK(BOSUuid.read(id)));
    	if(!canOprt(changeInfo.getState(), getCanChangeState())){
    		FDCMsgBox.showError("当前单据的状态不能被删除!");
    		abort();
    	}
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("purchase.purchaseState");
    	PurchaseChangeInfo purchaseChangeInfo = PurchaseChangeFactory
		.getRemoteInstance().getPurchaseChangeInfo(new ObjectUuidPK(id), sels);
    	super.actionRemove_actionPerformed(e);
    	//----删除变更单时,如果对应的认购单处于变更中状态,则将认购单置为已审批状态  xiaoao_liu
    	
		PurchaseInfo purchase = purchaseChangeInfo.getPurchase();
		if(purchase != null){
			PurchaseStateEnum purState = purchase.getPurchaseState();
			if(PurchaseStateEnum.PurchaseChange.equals(purState)){
				SelectorItemCollection tSels = new SelectorItemCollection();
				tSels.add("purchaseState");
				purchase.setPurchaseState(PurchaseStateEnum.PurchaseAudit);
				PurchaseFactory.getRemoteInstance().updatePartial(purchase, tSels);
			}
		}
		//---------------------
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionCopyTo_actionPerformed
     */
    public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyTo_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionSendSmsMessage_actionPerformed
     */
    public void actionSendSmsMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendSmsMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actoinViewSignature_actionPerformed
     */
    public void actoinViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actoinViewSignature_actionPerformed(e);
    }
    
    protected ICoreBase getBizInterface() throws Exception {
    	// TODO Auto-generated method stub
    	return PurchaseChangeFactory.getRemoteInstance();
    }
    
    protected String getEditUIName() {
    	// TODO Auto-generated method stub
    	return PurchaseChangeBillEditUI.class.getName();
    }
    
    
    public boolean canOprt(FDCBillStateEnum billStateEnum,FDCBillStateEnum[] state){
		boolean canOprt = false;
		for(int i=0;i<state.length;i++){
			if(billStateEnum.equals(state[i])){
				return true;
			}
		}
		return canOprt;
	}
	
	public FDCBillStateEnum[] getCanChangeState(){
		return new FDCBillStateEnum[]{FDCBillStateEnum.SAVED,FDCBillStateEnum.SUBMITTED};
	}

}