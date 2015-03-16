/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditIndexValueFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditIndexValueFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;


/**
 * @(#)			SupplierAppraiseTemplateListUI.java				
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		评审模板序事薄
 *		
 * @author		
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see						
 */
public class SupplierAppraiseTemplateListUI extends AbstractSupplierAppraiseTemplateListUI
{
	/*
	 * 序列号
	 */
	private static final long serialVersionUID = -6098956959479962991L;

	private static final Logger logger = CoreUIObject.getLogger(SupplierAppraiseTemplateListUI.class);
	/*
	 * 组织
	 */
    private OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentOrgUnit();
    /*
	 * 状态
	 */
    private final String COL_STATE = "state";
    /*
	 * 序列号
	 */
    private final String COL_ISSTATE = "isStart";
    protected KDMenuItem menuItemGroupView;
    
	//是否可以显示上一,下一
	private final static String GroupOperate="GroupOperate";
    
    /**
     * output class constructor
     */
    public SupplierAppraiseTemplateListUI() throws Exception
    {
        super();
    }
    /**
     * output storeFields method
     */
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
    		EntityViewInfo viewInfo) {
    	return super.getQueryExecutor(queryPK, viewInfo);
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    /**
     * @description		
     * @author			陈伟		
     * @createDate		2010-12-3
     * @param	
     * @return					
     *	
     * @version			EAS7.0
     * @see						
     */
    protected boolean isIgnoreCUFilter() { 
    	return false;
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
        setAuditStyle();
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
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }
    
    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionView_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getSupplierResources("notAddBase"));
        	SysUtil.abort();
		}
		if(getTypeSelectedTreeNode().getUserObject() instanceof SupplierAppraiseTypeInfo&&((SupplierAppraiseTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf())
		{
			super.actionAddNew_actionPerformed(e);
		}else{
			FDCMsgBox.showWarning(this,getSupplierResources("choiceOne"));
		}
    }
    
     /**
         * @description		树上删除按钮事件
         * @author			
         * @createDate		2010-12-1
         * @param	
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    public void actionGroupRemove_actionPerformed(ActionEvent e)
    		throws Exception {
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getSupplierResources("canntremove"));
        	SysUtil.abort();
		}
		if(getTypeSelectedTreeNode().getUserObject() instanceof SupplierAppraiseTypeInfo){
    		if(((SupplierAppraiseTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
    			FilterInfo filter = new FilterInfo();
        		filter.getFilterItems().add(new FilterItemInfo("appraiseType.id",((SupplierAppraiseTypeInfo)getTypeSelectedTreeNode().getUserObject()).getId().toString()));
        		if(getBizInterface().exists(filter)){
        			FDCMsgBox.showWarning(this,getSupplierResources("haved")+getSupplierResources("doNot"));
        			abort();
        		}
    		}
    		else{
    			FDCMsgBox.showWarning(this,getSupplierResources("notOne")+getSupplierResources("doNot"));
    			abort();
    		}
    	}
    	super.actionGroupRemove_actionPerformed(e);
    }
     /**
         * @description		树上修改按钮事件
         * @author			胥凯	
         * @createDate		2010-12-1
         * @param	
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    public void actionGroupEdit_actionPerformed(ActionEvent e) throws Exception {
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getSupplierResources("canntedit"));
        	SysUtil.abort();
		}
    	if(getTypeSelectedTreeNode().getUserObject() instanceof SupplierAppraiseTypeInfo){
    		if(!((SupplierAppraiseTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
    			FDCMsgBox.showWarning(this,getSupplierResources("notOne")+getSupplierResources("doNot"));
    			abort();
    		}
    	}
    	super.actionGroupEdit_actionPerformed(e);
    }
    /**
     * @description		
     * @author			杜余		
     * @createDate		2010-12-10
     * @param e
     * @throws Exception									
     * @version			EAS7.0
     * @see	@see com.kingdee.eas.framework.client.TreeDetailListUI#actionGroupView_actionPerformed(java.awt.event.ActionEvent)					
     */
    public void actionGroupView_actionPerformed(ActionEvent e) throws Exception {
 
    	super.actionGroupView_actionPerformed(e);
    }
     /**
         * @description		树上新增按钮事件
         * @author				
         * @createDate		2010-12-1
         * @param	
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    public void actionGroupAddNew_actionPerformed(ActionEvent e)
    		throws Exception {
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getSupplierResources("notAddBase"));
        	SysUtil.abort();
		}
    	if(getTypeSelectedTreeNode().getUserObject() instanceof SupplierAppraiseTypeInfo){
    		if(((SupplierAppraiseTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
    			FilterInfo filter = new FilterInfo();
        		filter.getFilterItems().add(new FilterItemInfo("appraiseType.id",((SupplierAppraiseTypeInfo)getTypeSelectedTreeNode().getUserObject()).getId().toString()));
        		if(getBizInterface().exists(filter)){
        			FDCMsgBox.showWarning(this,getSupplierResources("haved")+getSupplierResources("doNot"));
        			abort();
        		}
    		}
    	}
    	checkTreeNodeSelected(e);
    	UIContext uiContext = new UIContext(this);
    	uiContext.put(UIContext.ID, null);
    	uiContext.put(GroupOperate,"false");
    	if(getTypeSelectedTreeNode()!=null){
    		uiContext.put("APPRAISETYPE", getTypeSelectedTreeNode().getUserObject());
    	}
		/*
		 *  供子类定义要传递到EditUI中扩展的属性
		 */
		prepareGroupUIContext(uiContext, e);
		IUIWindow uiWindow = UIFactory.createUIFactory(getGroupEditUIModal()).create(getGroupEditUIName(), uiContext,
				null, OprtState.ADDNEW);
		uiWindow.show();
		/*
		 *  如果编辑界面以模式窗口方式打开，则刷新界面
		 */
		setActionEvent(e);
		if (isDoRefresh(uiWindow,getGroupEditUIModal()))
		{
			refresh(e);
		}
    }
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	super.prepareUIContext(uiContext, e);
		if(getTypeSelectedTreeNode()!=null){
			uiContext.put("APPRAISETYPE", getTypeSelectedTreeNode().getUserObject());
		}
    }

     /**
         * @description		修改事件
         * @author				
         * @createDate		2010-12-1
         * @param	
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getSupplierResources("canntedit"));
        	SysUtil.abort();
		}
    	String id = getSelectedKeyValue();
        SupplierAppraiseTemplateInfo info = SupplierAppraiseTemplateFactory.getRemoteInstance().getSupplierAppraiseTemplateInfo(new ObjectUuidPK(id));
    	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
    		FDCMsgBox.showWarning(this,getSupplierResources("notEdit"));
    		SysUtil.abort();    		
    	}
    	super.actionEdit_actionPerformed(e);
    }

    
	 /**
	     * @description		获取资源文件
	     * @author			胥凯	
	     * @createDate		2010-12-1
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private String getSupplierResources(String key) {
	    return	EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource",key);
	}
     /**
         * @description		删除事件
         * @author				
         * @createDate		2010-12-1
         * @param	
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {   
    	checkSelected();
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getSupplierResources("canntremove"));
        	SysUtil.abort();
		}
    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
    	String state = this.tblMain.getRow(rowIndex).getCell("state").getValue().toString();
    	if(state.equals(FDCBillStateEnum.AUDITTED.toString())){
			FDCMsgBox.showWarning(this,getSupplierResources("auditState"));
			abort();
    	}
		int yes=FDCMsgBox.showConfirm2New(this, getSupplierResources("deleteS"));
		if(yes > 0){
			abort();
		}
    	Object id = this.tblMain.getRow(rowIndex).getCell("id").getValue();    	
    	getBizInterface().delete(new ObjectUuidPK(id.toString())); 
    	refresh(e);
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
         * @description		禁用
         * @author				
         * @createDate		2010-12-1
         * @param	
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getSupplierResources("canntedit"));
        	SysUtil.abort();
		}
    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		int yes=MsgBox.showConfirm2New(this, getSupplierResources("isForbid"));
		if(yes > 0){
			SysUtil.abort();
		}
        String id = getSelectedKeyValue();
        
        SupplierAppraiseTemplateInfo info =SupplierAppraiseTemplateFactory.getRemoteInstance().getSupplierAppraiseTemplateInfo(new ObjectUuidPK(id));
        info.setIsStart(false);
        SupplierAppraiseTemplateFactory.getRemoteInstance().update(new ObjectUuidPK(id),  info);
        tblMain.getSelectManager().select(rowIndex, 0);
        this.actionCancel.setEnabled(false);
        this.actionCancelCancel.setEnabled(true);
        this.actionUnAudit.setEnabled(true);
        refreshList();
        this.actionRefresh_actionPerformed(e);

    }

     /**
         * @description		启用
         * @author				
         * @createDate		2010-12-1
         * @param	
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getSupplierResources("canntedit"));
        	SysUtil.abort();
		}
    	int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
        super.actionCancelCancel_actionPerformed(e);
        String id = getSelectedKeyValue();
        SupplierAppraiseTemplateInfo info =SupplierAppraiseTemplateFactory.getRemoteInstance().getSupplierAppraiseTemplateInfo(new ObjectUuidPK(id));
        info.setIsStart(true);
        SupplierAppraiseTemplateFactory.getRemoteInstance().update(new ObjectUuidPK(id),  info);
        tblMain.getSelectManager().select(rowIndex, 0);
        this.actionCancel.setEnabled(true);
        this.actionCancelCancel.setEnabled(false);
        this.actionUnAudit.setEnabled(false);
        refreshList();
        this.actionRefresh_actionPerformed(e);
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
	public void onLoad() throws Exception {
		super.onLoad();
		tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.contContener.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
		this.btnGroupMoveTree.setVisible(false);
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		KDTableHelper.downArrowAutoAddRow(tblMain, false, null);//锁向下方向键
	}
	
	 /**
	     * @description		设置树根节点头信息
	     * @author			
	     * @createDate		2010-12-1
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	protected String getRootName() {
		String root = getSupplierResources("type");
		return root;
	}
	 /**
	     * @description		
	     * @author				
	     * @createDate		2010-12-9
	     * @param	
	     * @return			IObjectPK		
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	protected IObjectPK getSelectedTreeKeyValue() {
		SupplierAppraiseTemplateInfo suppAppInfo = null;
		IObjectPK detail = new ObjectUuidPK(getSelectedKeyValue());
		if (detail == null) {
			return null;
		}
		try {
			suppAppInfo = (SupplierAppraiseTemplateInfo) getBizInterface().getValue(detail);
			if (suppAppInfo == null || suppAppInfo.getAppraiseType() == null
					|| suppAppInfo.getAppraiseType().getId() == null) {
				this.treeMain.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
				abort();
			}
		} catch (Exception e) {
			handUIException(e);
		}
		return new ObjectUuidPK(suppAppInfo.getAppraiseType().getId());
	}
	 /**
	     * @description		审批事件
	     * @author				
	     * @createDate		2010-12-1
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getSupplierResources("canntedit"));
        	SysUtil.abort();
		}
		if(!checkTemplateState(FDCBillStateEnum.SUBMITTED_VALUE))
		{
			FDCMsgBox.showWarning(this,getSupplierResources("notState"));
			abort();
		}
		List idList =ContractClientUtils.getSelectedIdValues(this.getMainTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
			if(idList.size() > 0 && idList.get(0) != null)
			{
				BOSUuid billId = BOSUuid.read(idList.get(0).toString());
				SupplierAppraiseTemplateFactory.getRemoteInstance().audit(billId);
			}
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
		showOprtOKMsgAndRefresh();
		this.actionRefresh_actionPerformed(e);
	}
	
	 /**
	     * @description		检查所属组织
	     * @author			
	     * @createDate		2010-12-1
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	public void checkSelectedOrg() {
		if (tblMain.getRowCount() == 0
				|| tblMain.getSelectManager().size() == 0) {
			MsgBox.showWarning(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_MustSelected"));
			SysUtil.abort();
		}
		String keyFiledName = "orgUnit.id";
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		String orgUnitId = ListUiHelper.getSelectedKeyValue(selectRows,
				this.tblMain, keyFiledName);
		if (currentOrg.getId().toString().equals(orgUnitId))
			return;
		else {
			FDCMsgBox.showWarning(this, getSupplierResources("notEnter")+getSupplierResources("doNot"));
			SysUtil.abort();
		}
	}
	
	 /**
	     * @description		检查模板状态
	     * @author				
	     * @createDate		2010-12-1
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private boolean checkTemplateState(String state)
	{	
		checkSelected();
		boolean flag = false ;
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex() ;
		IRow row = tblMain.getRow(rowIndex);

		if(row.getCell(COL_STATE).getValue() != null)
		{
			if(state.equalsIgnoreCase(((BizEnumValueInfo)row.getCell(COL_STATE).getValue()).getValue().toString()))
			{
				flag = true ;
			}
			else
			{
				flag = false ;
			}
		}
		return flag ;
	}
	
	/**
	 * @description		判断是否是启用状态
	 * @author			陈伟		
	 * @createDate		2010-11-27
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private boolean checkTemplateIsState()
	{	
		checkSelected();
		boolean flag = false ;
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex() ;
		IRow row = tblMain.getRow(rowIndex);

		if(row.getCell(COL_ISSTATE).getValue() != null)
		{
			if(((Boolean)row.getCell(COL_ISSTATE).getValue() ).booleanValue())
			{
				flag = true ;
			}
			else
			{
				flag = false ;
			}
		}
		return flag ;
	}
	/**
	 * 
	 * @throws Exception
	 */
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}
	 /**
	     * @description		反审批
	     * @author			胥凯	
	     * @createDate		2010-12-1
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception 
	{	
		if (!SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			MsgBox.showWarning(this, getSupplierResources("canntedit"));
        	SysUtil.abort();
		}
		if(!checkTemplateState(FDCBillStateEnum.AUDITTED_VALUE))
		{
			FDCMsgBox.showWarning(this,getSupplierResources("unAudit"));
			abort();
		}
		if(checkTemplateIsState())
		{
			FDCMsgBox.showWarning(this,getSupplierResources("reveal"));
			abort();
		}
		/*
		 * 引用检测实现
		 */
		List idList =ContractClientUtils.getSelectedIdValues(this.getMainTable(), getKeyFieldName());
		Set idSet = new HashSet();
		for(int i = 0; i < idList.size(); ++i)
		{
			idSet.add(idList.get(i).toString());
		}
		FilterInfo checkFilter = new FilterInfo();
		checkFilter.getFilterItems().add(new FilterItemInfo("templateEntry.parent.id", idSet, CompareType.INCLUDE));
		/*
		 * 当被引用的时候用
		 */
		if(FDCSplPeriodAuditIndexValueFactory.getRemoteInstance().exists(checkFilter))
		{
			FDCMsgBox.showWarning(this,getSupplierResources("unAuditTemplate"));
			abort();
		}
		if(FDCSplQualificationAuditTemplateFactory.getRemoteInstance().exists(checkFilter))
		{
			FDCMsgBox.showWarning(this,getSupplierResources("unAuditTemplate"));
			abort();
		}
		checkFilter = new FilterInfo();
		checkFilter.getFilterItems().add(new FilterItemInfo("template.parent.id", idSet, CompareType.INCLUDE));
		if(FDCSplKeepAuditIndexValueFactory.getRemoteInstance().exists(checkFilter))
		{
			FDCMsgBox.showWarning(this,getSupplierResources("unAuditTemplate"));
			abort();
		}
		boolean hasMutex = false;
		try{
			FDCClientUtils.requestDataObjectLock(this, idList, "UnAudit");
			if(idList.size() > 0 && idList.get(0) != null)
			{
				BOSUuid billId = BOSUuid.read(idList.get(0).toString());
				SupplierAppraiseTemplateFactory.getRemoteInstance().unAudit(billId);
			}
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
		showOprtOKMsgAndRefresh();
		this.actionRefresh_actionPerformed(e);
		logger.info(e.getSource());
	}
	
	 /**
	     * @description		树的变化事件
	     * @author				
	     * @createDate		2010-12-1
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		if(null != getTypeSelectedTreeNode()){
			String name = getTypeSelectedTreeNode().getText();
			if(null != name || "".equals(name)){
				this.contContener.setTitle(name);
			}
		}
		super.treeMain_valueChanged(e);
	}
	 /**
	     * @description		设置审批按钮在表格切换时候的显示状态
	     * @author				
	     * @createDate		2010-12-1
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private void setAuditStyle(){
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex < 0)
		{
			return ;
		}
		IRow row = tblMain.getRow(rowIndex);
		/*
		 * 判断当前状态，更新按钮状态
		 */
		if(FDCBillStateEnum.SAVED_VALUE.equalsIgnoreCase(((BizEnumValueInfo)row.getCell(COL_STATE).getValue()).getValue().toString()))
		{
			this.actionEdit.setEnabled(true);
			this.actionView.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			this.actionCancel.setEnabled(false);
		}
		else if(FDCBillStateEnum.SUBMITTED_VALUE.equalsIgnoreCase(((BizEnumValueInfo)row.getCell(COL_STATE).getValue()).getValue().toString()))
		{
			this.actionEdit.setEnabled(true);
			this.actionView.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionAudit.setEnabled(true);
			this.actionUnAudit.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			this.actionCancel.setEnabled(false);
		}
		else if(FDCBillStateEnum.AUDITTED_VALUE.equalsIgnoreCase(((BizEnumValueInfo)row.getCell(COL_STATE).getValue()).getValue().toString()))
		{
			this.actionEdit.setEnabled(false);
			this.actionView.setEnabled(true);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			if(((Boolean)row.getCell(COL_ISSTATE).getValue()).booleanValue()){
				this.actionCancelCancel.setEnabled(false);
				this.actionCancel.setEnabled(true);
				this.actionUnAudit.setEnabled(false);
			}else{
				this.actionUnAudit.setEnabled(true);
				this.actionCancelCancel.setEnabled(true);
				this.actionCancel.setEnabled(false);
			}
		}
	}
	public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.treeMain
				.getLastSelectedPathComponent();
	}
	protected ITreeBase getTreeInterface() throws Exception {
		return SupplierAppraiseTypeFactory.getRemoteInstance();
	}
	//父类方法，必须实现
	protected String getEditUIName() {
		return SupplierAppraiseTemplateEditUI.class.getName();
	}
    //父类方法，必须实现
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	//父类方法，必须实现
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierAppraiseTemplateFactory.getRemoteInstance();
	}
	protected String getGroupEditUIName() {
		return AppraiseTypeEditUI.class.getName();
	}
	protected String getQueryFieldName() {
		return  "appraiseType.id";
	}
	public EntityViewInfo getMainQuery() {
		return mainQuery;
	}
	protected String getKeyFieldName() {
		return "id";
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("guideEntry.*"));
		return sic ;
	}
	
}