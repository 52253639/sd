/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.jackrabbit.uuid.UUID;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.supplier.AppraisePhaseEnum;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpCollection;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierException;
import com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		供应商定期评审
 *		
 * @author		陈伟
 * @version		EAS1.0		
 * @createDate	2010-11-8	 
 * @see						
 */
 
public class SupplierEvaluateEditUI extends AbstractSupplierEvaluateEditUI
{
    /**
	 * 序列号1
	 */
	private static final long serialVersionUID = 6328044607089119205L;
	private static final Logger logger = CoreUIObject.getLogger(SupplierEvaluateEditUI.class);
    
    /**
     * output class constructor
     */
    public SupplierEvaluateEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
    }

    /**
     * output menuItemEnterToNextRow_itemStateChanged method
     */
    protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.menuItemEnterToNextRow_itemStateChanged(e);
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
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }



    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	/*
    	 * 直接点击提交时候给name和number赋值
    	 */
    	if(null == editData.getName()){
    	   editData.setName(UUID.randomUUID().toString());
    	}
    	if(null == editData.getNumber()){
    	   editData.setNumber(UUID.randomUUID().toString());
    	}
        super.actionSubmit_actionPerformed(e);
        kdtDemoUnite();//重新和并下单元格
        
        this.actionEdit.setEnabled(true);
        this.actionSubmit.setEnabled(false);
        this.setOprtState(OprtState.VIEW);
        setAuditBtn();
        btnSave.setEnabled(false);
        menuItemSave.setEnabled(false);
        btnAudit.setEnabled(true);
        menuItemAudit.setEnabled(true);
        this.actionUnAudit.setEnabled(false);
    }

    /**
	 * @description		设置按钮
	 * @author			陈伟		
	 * @createDate		2010-11-28
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private void setAuditBtn() {
		this.btnAudit.setVisible(true);
        this.btnAudit.setEnabled(true);
        this.menuItemAudit.setEnabled(true);
        this.menuItemUnAudit.setEnabled(true);
        this.menuItemAudit.setVisible(true);
        this.menuItemUnAudit.setVisible(true);
        this.btnUnAudit.setVisible(true);
        this.btnUnAudit.setEnabled(true);
        
        this.actionUnAudit.setEnabled(true);
        if(getOprtState().equals(OprtState.VIEW)){
        	this.btnAudit.setEnabled(true);
        	this.btnUnAudit.setEnabled(true);
        	this.actionAudit.setEnabled(true);
        	this.actionUnAudit.setEnabled(true);
        } else{
        	this.btnAudit.setEnabled(false);
        	this.btnUnAudit.setEnabled(false);
        	this.actionAudit.setEnabled(false);
        	this.actionUnAudit.setEnabled(false);
        }
       


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
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
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
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }



    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
       
		//检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());
    	//如果进入工作流后页面状态不会刷新 手动刷新下
    	if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
    	   editData = FDCSplPeriodAuditBillFactory.getRemoteInstance().getFDCSplPeriodAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
    	}
		boolean b = editData != null
		&& editData.getState() != null
		&& !editData.getState().equals(FDCBillStateEnum.AUDITTING)&& !editData.getState().equals(FDCBillStateEnum.AUDITTED);
        if (!b) {
	      FDCMsgBox.showWarning(this, getSupplierResources("notRemove"));
	      SysUtil.abort();
        }
		int yes=FDCMsgBox.showConfirm2New(this, getSupplierResources("isRemove"));
		  if(yes > 0){
			SysUtil.abort();
		  }
    	SupplierStockInfo supplier = new SupplierStockInfo();
    	supplier.setId(editData.getSupplier().getId());
    	supplier.setName(editData.getSupplier().getName());  
    	getBizInterface().delete(new ObjectUuidPK(editData.getId().toString()));
    	
    	this.kdtDemo.removeRows();
    	this.kdtIsGrade.removeRows();
    	this.prmtSupplierType.setData(null);
    	loadFields();
    	editData.clear();
    	editData = (FDCSplPeriodAuditBillInfo)createNewData();
    	editData.setSupplier(supplier);
    	this.setOprtState(OprtState.ADDNEW);
    	getUIContext().put(UIContext.ID, null);
    	onLoad();    	
    	this.actionRemove.setEnabled(false);
    }



    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
    }

    /**
     * output actionCopyLine_actionPerformed
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {

        super.actionRemoveLine_actionPerformed(e);
    }

    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
        
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
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
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
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
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
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
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
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionSendMail_actionPerformed
     */
    public void actionSendMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMail_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }



    /**
     * output actionAttamentCtrl_actionPerformed
     */
    public void actionAttamentCtrl_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttamentCtrl_actionPerformed(e);
    }

	protected void attachListeners() {
		
		
	}

	protected void detachListeners() {
		
		
	}
	
    public void onLoad() throws Exception {    	
    	
    	super.onLoad();
    	initConAttchment();
    	initEntry();
    	
    	if(getOprtState().equals(OprtState.ADDNEW)){
    		editData.setId(null);
    		initSupplier();
    		getTemplate();
    	}else{
    	    initSupplierF7();
    	}
    	

    }
    private void getTemplate() throws Exception{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));	 
		view.getSelector().add(new SelectorItemInfo("state"));	 
		view.getSelector().add(new SelectorItemInfo("phase"));	 
        view.getSelector().add(new SelectorItemInfo("guideEntry.*"));     
        view.getSelector().add(new SelectorItemInfo("creator.*"));   
        view.getSelector().add(new SelectorItemInfo("guideEntry.splAuditIndex.*"));  
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("phase",AppraisePhaseEnum.DEADLINE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		 filter.getFilterItems().add(new FilterItemInfo("isStart", Boolean.TRUE));
		view.setFilter(filter);
		SupplierAppraiseTemplateCollection sic = SupplierAppraiseTemplateFactory.getRemoteInstance().getSupplierAppraiseTemplateCollection(view);
		 Iterator it = (Iterator)sic.iterator();
		 while(it.hasNext()){
			 SupplierAppraiseTemplateInfo info = (SupplierAppraiseTemplateInfo)it.next();
			 if(info.getGuideEntry().size() == 0 ){//没有分录的不要加入
				 continue;
			 }
			 fillInTemplate(info.getGuideEntry());
			 break;
		 }
	 
	}
    
    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
    	if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
      	   editData = FDCSplPeriodAuditBillFactory.getRemoteInstance().getFDCSplPeriodAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
      	}
     	if(editData.getState().equals(FDCBillStateEnum.AUDITTED)){
     		MsgBox.showWarning(this, getSupplierResources("notEdit"));
     		SysUtil.abort();
     	}
        super.actionEdit_actionPerformed(e);
        setAuditBtn();
    }


	 
    
    /**
     * @description		初始化供应商
     * @author			陈伟		
     * @createDate		2010-11-9
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    public void initSupplier(){
    	if( null != getUIContext().get("SupplierInfo")){
    		SupplierStockInfo info =(SupplierStockInfo) getUIContext().get("SupplierInfo");
    		editData.setSupplier(info);
    		this.textSupplier.setText(info.getName());
    		try {
    			FDCSupplierServiceTypeCollection fssc = info.getSupplierServiceType();
    			Iterator it = fssc.iterator();
    			Object obj[] = new Object[fssc.size()];
    			int i = 0;
    			while(it.hasNext()){
    				
    				FDCSupplierServiceTypeInfo finfo = (FDCSupplierServiceTypeInfo)it.next();
    				obj[i++]=finfo.getServiceType();
    				 
    			}
				this.prmtSupplierType.setData(obj);
			} catch (Exception e) {
 
			}
    	}
    	
    }
    /**
     * @description		初始化类型F7
     * @author			陈伟		
     * @createDate		2010-11-9
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    private void initSupplierF7(){
    	if(null != editData){
    		FDCSplPeriodAuditBillEntryCollection  seic  = editData.getAuditBill();
    		Object obj[] = new Object[seic.size()];
    		for(int i = 0 ; i < seic.size() ; i ++){
    			FDCSplPeriodAuditBillEntryInfo info  = seic.get(i);
    			if(null == info.getSupplierType()){
    				continue;
    			}	
    			obj[i]=(FDCSplServiceTypeInfo)info.getSupplierType();
    		}
    		this.prmtSupplierType.setDataNoNotify(obj);
    	}
    }
 
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {

    	if(null == editData.getName())
    	  editData.setName(UUID.randomUUID().toString());
    	if(null == editData.getNumber())
    	  editData.setNumber(UUID.randomUUID().toString());
        super.actionSave_actionPerformed(e);
        kdtDemoUnite();
    }
    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	//如果进入工作流后页面状态不会刷新 手动刷新下
    	if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
    	   editData = FDCSplPeriodAuditBillFactory.getRemoteInstance().getFDCSplPeriodAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
    	}
    	if(!editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
		    FDCMsgBox.showWarning(this,getSupplierResources("notState"));
	    	abort();
    	}
    	btnUnAudit.setVisible(true);
    	menuItemUnAudit.setVisible(true);
        super.actionAudit_actionPerformed(e);
        setAuditBtn();
        btnUnAudit.setEnabled(true);
        menuItemAudit.setEnabled(true);
        btnAudit.setEnabled(false);
        menuItemAudit.setEnabled(false);
        btnMould.setEnabled(false);
        menuItemModel.setEnabled(false);
        Object[] obj=getSupplierTypeObj();
    	prmtSupplierType.setDataNoNotify(obj);
    	
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	//如果进入工作流后页面状态不会刷新 手动刷新下
    	if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
    	   editData = FDCSplPeriodAuditBillFactory.getRemoteInstance().getFDCSplPeriodAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
    	}
        super.actionUnAudit_actionPerformed(e);
        setAuditBtn();
        btnAudit.setEnabled(true);
        menuItemAudit.setEnabled(true);
        btnMould.setEnabled(true);
        menuItemModel.setEnabled(true);
        this.actionEdit.setEnabled(true);
        this.btnUnAudit.setEnabled(false);
        this.menuItemUnAudit.setEnabled(false);
    }
    /**
     * @description		初始化按钮.绑定F7等
     * @author			陈伟		
     * @createDate		2010-11-4
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    public void initButton(){
    	this.btnAudit.setVisible(false);
    	menuItemAudit.setVisible(false);
    	this.btnUnAudit.setEnabled(false);
    	this.btnAuditResult.setVisible(false);
    	this.menuItemAuditResult.setVisible(false);
    	
    	if(this.pkBusinessDate.getValue() == null){
    		Date nows = new Date();
    		 
    		this.pkBusinessDate.setValue(nows);
    		
    		editData.setBusinessDate(nows);
    	}
    	
    	this.btnUnAudit.setVisible(true);
    	this.btnAudit.setVisible(true);
    	/*
    	 * 绑定供应商类型F7
    	 */
		this.prmtSupplierType.setDisplayFormat("$name$");
		this.prmtSupplierType.setEditFormat("$name$");
		this.prmtSupplierType.setCommitFormat("$name$");
		this.prmtSupplierType
				.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7SupplierServiceTypeQuery");		
		this.prmtSupplierType.setEnabledMultiSelection(true); 
		this.prmtSupplierType.setEditable(false);
		this.prmtProject.setDisplayFormat("$name$");
		this.prmtProject.setEditFormat("$name$");
		this.prmtProject.setCommitFormat("$name$");
		this.prmtProject
		.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");	
		/*
		 * 给制单人，制单时间赋值
		 */
		
		this.prmtCreator.setDisplayFormat("$name$");
		this.prmtCreator.setEditFormat("$name$");
		this.prmtCreator.setCommitFormat("$name$");
		
		
		if(null == prmtCreator.getData()){
			prmtCreator.setData(SysContext.getSysContext().getCurrentUserInfo());
			pkCreateTime.setValue(new Date());
			
		}
		this.prmtAuditor.setDisplayFormat("$name$");
		this.prmtAuditor.setEditFormat("$name$");
		this.prmtAuditor.setCommitFormat("$name$");
		/*
		 * 判断单据状态如果是已审批不允许修改
		 */
		if(null != editData.getState() && editData.getState().equals(FDCBillStateEnum.AUDITTED)){
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionUnAudit.setEnabled(true);
			btnUnAudit.setEnabled(true);
			this.actionAudit.setEnabled(false);
		}
		this.txtScore.setMaximumValue(FDCHelper.ONE_HUNDRED);
		this.txtScore.setMinimumValue(FDCHelper.ZERO);

		this.actionSupplierInfo.setEnabled(true);
		this.actionModelInfo.setEnabled(true);
		
	 
		
    }
    
    /**
     *查看供应商信息
     */
    public void actionSupplierInfo_actionPerformed(ActionEvent e)
    		throws Exception {
    	SupplierStockInfo info = editData.getSupplier(); 
        UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, info.getId().toString());
		uiContext.put("SupplierInfo", info);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB); 
 		IUIWindow uiWindow = uiFactory.create(SupplierStockEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
    }
    
    
    
    /**
	 * @description		合并单元格
	 * @author			陈伟		
	 * @createDate		2010-11-17
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private void kdtDemoUnite() {
		KDTMergeManager mm =kdtDemo.getMergeManager();
		int longth = kdtDemo.getRowCount();
		Map map = new HashMap();
		IRow row = null;
		for(int i = 0 ; i < longth ;i ++){
			//取得所有的纬度
			  row = kdtDemo.getRow(i);
			  String type = (String)row.getCell("lat").getValue();
			  /*
			   * 非空判断
			   */
			  if(null == type){
				  continue;
			  }
			  
			  if(map.get(type) == null){
				  map.put(type, Boolean.TRUE);
			  }
		} 
		Set key = map.keySet();
		Iterator it = key.iterator();
		while(it.hasNext()){
			String type = (String)it.next();			
			int startEnd[] =getStartEnd( -1 , -1 , type, longth); 
            if(startEnd[0]<startEnd[1]){
			  mm.mergeBlock(startEnd[0],0,startEnd[1],0,KDTMergeManager.SPECIFY_MERGE);
            }
		}
 
		setFootRowSum();

	}
	
 
	/**
	 * @description		分数求和
	 * @author			陈伟		
	 * @createDate		2010-11-19
	 * @param	       
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private void setFootRowSum()
	{
		IRow footRow = kdtDemo.getFootRow(0);

    	BigDecimal sum = new BigDecimal("0");
    	
    	for(int i= 0; i < kdtDemo.getRowCount(); ++i )
    	{
    		IRow tmpRow = kdtDemo.getRow(i);
    		if(tmpRow.getCell("score").getValue() != null)
    		{
    			
    			BigDecimal tmpwh = new BigDecimal(tmpRow.getCell("percent").getValue().toString());
    			BigDecimal tmp = new BigDecimal(tmpRow.getCell("score").getValue().toString());   
    			tmpwh=tmpwh.divide(FDCHelper.ONE_HUNDRED);
    			tmp=tmp.multiply(tmpwh);
    			
    			sum = sum.add(tmp);
    		}else{
    			tmpRow.getCell("score").setValue(new BigDecimal("0.00"));
    			BigDecimal tmpwh = new BigDecimal(tmpRow.getCell("percent").getValue().toString());
    			BigDecimal tmp = new BigDecimal(tmpRow.getCell("score").getValue().toString());
    			tmpwh=tmpwh.divide(FDCHelper.ONE_HUNDRED);
    			tmp=tmp.multiply(tmpwh);
    			sum = sum.add(tmp);
    		}
    	}

    	footRow.getCell("score").setValue(sum);
	}
	
	/**
	 * @description		
	 * @author			陈伟		
	 * @createDate		2010-11-17
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private int[] getStartEnd(int ben ,int end ,String type,int longth) {
		IRow row = null;
		for(int i = 0 ; i < longth ;i ++){
			//取得所有的纬度
			  row = kdtDemo.getRow(i);
			  String str = (String)row.getCell("lat").getValue();
			  /*
			   * 非空判断
			   */
			  if(str == null){
				  continue;
			  }
			  if(str.equals(type)){
				  if(ben < 0){
					  ben = i ;   
				  }else{
					  end = i ; 
				  }
			  }
 
		} 
       int obj [] = new int[2];
       obj[0]=ben;
       obj[1]=end;
       return obj;
	}
 
	
	/**
	 * @description		填充评审模版
	 * @author			陈伟		
	 * @createDate		2010-12-9
	 * @param sgec void
	 * @version			EAS1.0
	 * @see						
	 */
	public void fillInTemplate(SupplierGuideEntryCollection sgec ) {
		this.kdtDemo.checkParsed();
		kdtDemo.removeRows();
		Date now = new Date();
		IRow row = null;  	
		Iterator it = (Iterator)sgec.iterator();
		while(it.hasNext()){
			row = kdtDemo.addRow();
			SupplierGuideEntryInfo info = (SupplierGuideEntryInfo)it.next();			 
//			row.getCell("lat").setValue(info.getGuideType());//纬度
			row.getCell("index").setValue(info.getSplAuditIndex().getName());//指标
			row.getCell("percent").setValue(info.getWeight());//权重
//			row.getCell("criterion").setValue(info.getFullNum());//满分标准
			row.getCell("lat").getStyleAttributes().setLocked(true);
			row.getCell("index").getStyleAttributes().setLocked(true);
			row.getCell("percent").getStyleAttributes().setLocked(true);
			row.getCell("criterion").getStyleAttributes().setLocked(true);
			
			row.getCell("score").setValue("0.00");//
			
			row.getCell("syndic").setValue(SysContext.getSysContext().getCurrentUserInfo().getName());//评审人
			row.getCell("dept").setValue(orgUnitInfo.getName());//评审部门			
			row.getCell("date").setValue(now);//时间
			row.getCell("info").setValue(info);
			 
			
		}
		kdtDemoUnite();
	}
	
 
	
	
	
    /**
     * @description		启用模版
     * @author			陈伟		
     * @createDate		2010-12-10
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    public void actionModelInfo_actionPerformed(ActionEvent e)
    		throws Exception {
    	
    	if(editData.getId()!=null &&null != editData.getState()){
    		if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
    			editData = FDCSplPeriodAuditBillFactory.getRemoteInstance().getFDCSplPeriodAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
    		}
    		if(editData.getState().equals(FDCBillStateEnum.AUDITTED)){
    			MsgBox.showWarning(this, getSupplierResources("changeTemplet"));
    			SysUtil.abort();
    		}
    		
    	}
    	if(kdtDemo.getRowCount()>1){
    		int yes=MsgBox.showConfirm2New(this, getSupplierResources("template"));
			if(yes > 0){
				SysUtil.abort();
			}
    	}
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		
		SupplierEvaluateEditUI evaluate =(SupplierEvaluateEditUI)this;  
		uiContext.put("Evaluate", evaluate);	

		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL); 
 		IUIWindow uiWindow = uiFactory.create(F7TemplatelistUI.class.getName(), uiContext, null, OprtState.ADDNEW);
 		
 		
		uiWindow.show();
    }
    
   
    public void loadFields() {
	 this.kdtDemo.checkParsed();
	 this.kdtIsGrade.checkParsed();
 	if(getOprtState().equals(OprtState.ADDNEW)){
		editData.setId(null);
		editData.getAuditBill().clear();
		editData.getAuditValue().clear();
		editData.setProject(null);
		editData.setScore(null);
		
 	}
	super.loadFields();
	initButton();
	setAuditBtn();


	
}
 
  /**
   * output actionAttachment_actionPerformed
   */
  public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
  {
      AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
      String boID = getSelectBOID();
      if(boID == null)
      {
          return;
      } 
        editData = FDCSplPeriodAuditBillFactory.getRemoteInstance().getFDCSplPeriodAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
         
        /*
         * 进入工作流中不能新增附件
         */
		ProcessInstInfo instInfo = null;
		ProcessInstInfo[] procInsts = null;
		try {
			IEnactmentService service2 = EnactmentServiceFactory
					.createRemoteEnactService();
			procInsts = service2.getProcessInstanceByHoldedObjectId(boID);
		} catch (BOSException ex) {
			ExceptionHandler.handle(ex);
		}

		for (int i = 0, n = procInsts.length; i < n; i++) {
			
			if ("open.running".equals(procInsts[i].getState())
					|| "open.not_running.suspended".equals(procInsts[i]
							.getState())) {
				instInfo = procInsts[i];
			}
		}
        boolean isEdit = true; 
        
		boolean b = editData != null
				&& editData.getState() != null
				&& !editData.getState().equals(FDCBillStateEnum.AUDITTING)&& !editData.getState().equals(FDCBillStateEnum.AUDITTED);
		if (!b) {
			 isEdit = false; 
		}
		if(instInfo!=null){
			isEdit = false; 
		}

      // boID 是 与附件关联的 业务对象的 ID
      acm.showAttachmentListUIByBoID(boID,this,isEdit);
 
     
      initConAttchment();
  }
	/**
	 * @description		设置附件值
	 * @author			陈伟		
	 * @createDate		2010-11-5
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private void initConAttchment() throws Exception {
		
		String boID = getSelectBOID();
		
		/*
		 * 附件ID为空直接返回
		 */
		if (boID == null) {
			return ;
		}

		SelectorItemCollection itemColl = new SelectorItemCollection();
		itemColl.add(new SelectorItemInfo("id"));
		itemColl.add(new SelectorItemInfo("attachment.name"));
		itemColl.add(new SelectorItemInfo("attachment.id"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(itemColl);

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo("boID", getSelectBOID()));
		view.setFilter(filter);

		BoAttchAssoCollection boAttchColl = BoAttchAssoFactory
				.getRemoteInstance().getBoAttchAssoCollection(view);
		String attchStrt = getSupplierResources("attachment")+"    ";
		if (boAttchColl != null && boAttchColl.size() > 0) {
				for (int i = 0; i < boAttchColl.size(); i++) {
				AttachmentInfo attachment = (AttachmentInfo) boAttchColl.get(i)
						.getAttachment();
				
				attchStrt += attachment.getName() +"; ";
				 
			}
		}
		conAttchment.setBoundLabelText(attchStrt);
		
	} 
    
    /**
     * @description		
     * @author			陈伟		
     * @createDate		2010-11-21
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    public void onShow() throws Exception {
    	kdtDemoUnite();
		this.btnAuditResult.setVisible(false);
		this.menuItemAuditResult.setVisible(false);

    }

    /**
     * @description		初始化Entry
     * @author			陈伟		
     * @createDate		2010-11-5
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    public void initEntry(){
    	txtScore.setMaximumValue(FDCHelper.ONE_HUNDRED);
    	txtScore.setMinimumValue(FDCHelper.ZERO);
    	getDetailTable().checkParsed();
    	
    	KDCheckBox isAutidor = new KDCheckBox();    	
    	isAutidor.setRequired(true);
    	KDTDefaultCellEditor editor = new KDTDefaultCellEditor(isAutidor);
		getDetailTable().getColumn("isAutidor").setEditor(editor);
		getDetailTable().getColumn("isAutidor").setRequired(true);
		
     
		KDBizPromptBox grade = new KDBizPromptBox();  
		grade.setDisplayFormat("$name$");
		grade.setEditFormat("$name$");
		grade.setCommitFormat("$name$");
		grade.setEditable(false);
		grade.setRequired(true);
		grade.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7GradeSetUpQuery");
        editor = new KDTDefaultCellEditor(grade);
		getDetailTable().getColumn("grade").setEditor(editor);
		 
		
		KDBizPromptBox type = new KDBizPromptBox();		 
		type.setDisplayFormat("$name$");
		type.setEditFormat("$name$");
		type.setCommitFormat("$name$");
		type.setEnabled(false);
        editor = new KDTDefaultCellEditor(type);
		getDetailTable().getColumn("supplierType").setEditor(editor);
		
		KDFormattedTextField textscore = new KDFormattedTextField();
		textscore.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		textscore.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		textscore.setPrecision(2);
		textscore.setMaximumValue(FDCHelper.ONE_HUNDRED);
		textscore.setMinimumValue(FDCHelper.ZERO);
        editor = new KDTDefaultCellEditor(textscore);
		getDetailTable().getColumn("score").setEditor(editor);
		
		getDetailTable().getColumn("score").getStyleAttributes().setNumberFormat("#0.00");
		getDetailTable().getColumn("score").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		
		kdtDemo.checkParsed();
		
		KDFormattedTextField descore = new KDFormattedTextField();
		descore.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		descore.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		descore.setPrecision(2);
		descore.setMaximumValue(FDCHelper.ONE_HUNDRED);
		descore.setMinimumValue(FDCHelper.ZERO);
        editor = new KDTDefaultCellEditor(textscore);
        kdtDemo.getColumn("score").setEditor(editor);
        
		KDFormattedTextField percent = new KDFormattedTextField();
		percent.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		percent.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		percent.setPrecision(2);
		percent.setMaximumValue(FDCHelper.ONE_HUNDRED);
		percent.setMinimumValue(FDCHelper.ZERO);
        editor = new KDTDefaultCellEditor(percent);
        kdtDemo.getColumn("percent").setEditor(editor);
        kdtDemo.getColumn("percent").getStyleAttributes().setNumberFormat("#0.00");
        kdtDemo.getColumn("percent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
        kdtDemo.getColumn("score").getStyleAttributes().setNumberFormat("#0.00");
        kdtDemo.getColumn("score").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
         KDBizPromptBox person  = new KDBizPromptBox();
         person.setDisplayFormat("$name$");
         person.setEditFormat("$name$");
         person.setCommitFormat("$name$");
         person.setEditable(false);
         person.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");  
		 
	     editor = new KDTDefaultCellEditor(person);
	     kdtDemo.getColumn("syndic").setEditor(editor);
         kdtDemo.getColumn("date").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
         kdtDemo.getColumn("dept").getStyleAttributes().setLocked(true);
          
		//设置合计行
		KDTFootManager footRowManager= kdtDemo.getFootManager();
		IRow footRow = null;
		if(footRowManager==null){

			footRowManager = new KDTFootManager(kdtDemo);
			footRowManager.addFootView();
			kdtDemo.setFootManager(footRowManager);
			
			footRow= footRowManager.addFootRow(0);
			footRow.setUserObject("FDC_PARAM_TOTALCOST");
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			
			kdtDemo.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			kdtDemo.getIndexColumn().setWidth(60);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			
			//设置到第一个可视行
			footRowManager.addIndexText(0,getSupplierResources("sum"));
		}else{
			footRow=kdtDemo.getFootRow(0);
			if(footRow.getUserObject()==null||!footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")){
				footRow=kdtDemo.addFootRow(1);
			};
		}
		
    }
/**
 * @description		
 * @author			陈伟		
 * @createDate		2010-11-17
 * @param	
 * @return					
 *	
 * @version			EAS1.0
 * @see						
 */
  protected void kdtDemo_editStopped(KDTEditEvent e) throws Exception {
	  
	  int index = e.getRowIndex();
	  IRow row  = null;
	  if( e.getValue() instanceof UserInfo){		  
		  row = kdtDemo.getRow(index);
		  
		  UserInfo user = (UserInfo)e.getValue();
		  SelectorItemCollection sic = new SelectorItemCollection();
		  sic.add(new SelectorItemInfo("*"));
		  sic.add(new SelectorItemInfo("defOrgUnit.*"));
		  user = UserFactory.getRemoteInstance().getUserInfo(new ObjectUuidPK(user.getId().toString()),sic);
		   row.getCell("syndic").setValue(user.getName());
		   row.getCell("dept").setValue(user.getDefOrgUnit().getName());

	  }
	  
	  setFootRowSum();
  }
    
    /**
     * @description		供应商类型事件。
     * @author			陈伟		
     * @createDate		2010-12-9
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    protected void prmtSupplierType_dataChanged(DataChangeEvent e)
    		throws Exception {   
    	setSupplierType();
    }
    
    /**
     *查询过滤设置
     */
    protected void prmtSupplierType_willShow(SelectorEvent e) throws Exception {

 
    }
    
    /**
     * @description		获取等级
     * @author			陈伟		
     * @createDate		2010-11-8
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    public Object[] getGrades (){
    	Object obj[] = null;
		try {
			
			EntityViewInfo view = new EntityViewInfo();

			view.getSelector().add(new SelectorItemInfo("*")); 

			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			GradeSetUpCollection gsc = GradeSetUpFactory.getRemoteInstance().getGradeSetUpCollection(view);
			
			if(gsc.size()>0){
			  obj = new Object[gsc.size()];
			  Iterator iter = gsc.iterator();
			  int i = 0 ;
			   while(iter.hasNext())
			  {
				   
				   GradeSetUpInfo info = (GradeSetUpInfo)iter.next();
				   obj[i++]=info.getName();
				   
			  }
			}
		} catch (BOSException e) {
			handUIException(e);
		}
		return obj;
    	 
    }
    /*
     * 分录事件
     */
    protected void kdtIsGrade_editStopped(KDTEditEvent e) throws Exception {
	    int index = e.getRowIndex();
	    IRow row = getDetailTable().getRow(index);
	    Boolean b=(Boolean) row.getCell("isAutidor").getValue();
	    ICell cellGrade = row.getCell("grade");
	    ICell cellScore = row.getCell("score");
	    if(b.booleanValue()){
	    	cellScore.getStyleAttributes().setLocked(false);
			
			cellGrade.getStyleAttributes().setLocked(false);
			 
			cellGrade.getStyleAttributes().setBackground(new Color(252, 251, 223));

	    }else{
	    	cellScore.setValue(null);
			cellGrade.setValue(null);
			cellScore.getStyleAttributes().setLocked(true);
			cellGrade.getStyleAttributes().setLocked(true);
			cellGrade.getStyleAttributes().setBackground(Color.WHITE);
	    }
    }
 
    protected IObjectValue createNewData() {
    	
    	return new FDCSplPeriodAuditBillInfo();
    }
  
    
    /**
	 * @description		
	 * @author			陈伟		
	 * @createDate		2010-11-19
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private String getSupplierResources(String key) {
	    return	EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource",key);

	}
    /**
     *保存校验
     */
    
    protected void verifyInputForSave() throws Exception {
 
    	if(null == editData.getBusinessDate()){
    		throw  new  SupplierException(SupplierException.BIZDATEEXCEPTION);
    	}
		if(0 == editData.getAuditBill().size()){
			FDCMsgBox.showWarning(this,getSupplierResources("supplierSeverType"));
			abort();
			
		}
		if(prmtSupplierType.getText().trim() == null||prmtSupplierType.getText().trim().equals("")){
    		MsgBox.showWarning(this,getSupplierResources("supplierType")+getSupplierResources("notNull"));
        	SysUtil.abort();
    	}
		String isAuditstr = "";
    	for (int i = 0; i<editData.getAuditBill().size();i++){
    		FDCSplPeriodAuditBillEntryInfo info = editData.getAuditBill().get(i);
    		if(info.isIsAudit()){
//    			if(null == info.getScore()){
//    				FDCMsgBox.showWarning(this,getSupplierResources("auditScore"));
//    				abort();
//    				
//    			}
    			if(null == info.getGrade()){
    				FDCMsgBox.showWarning(this,getSupplierResources("supplierGrade"));
    				abort();
    			}
    		}else{
    			isAuditstr += info.getSupplierType().getName()+",";

    		}
    	}
    	if(isAuditstr.length() > 0){
		  int yes=MsgBox.showConfirm2New(this, isAuditstr+getSupplierResources("isAudit"));
		  if(yes > 0){
			SysUtil.abort();
		  }
    	}
    	//super.verifyInputForSave();
    }
    /**
     *提交校验
     */
    protected void verifyInputForSubmint() throws Exception {
 
    	if(null == editData.getBusinessDate()){
    		throw new  SupplierException(SupplierException.BIZDATEEXCEPTION);
     	}
		if(0 == editData.getAuditBill().size()){
			FDCMsgBox.showWarning(this,getSupplierResources("supplierSeverType"));
			abort();
			
		}
		if(prmtSupplierType.getText().trim() == null||prmtSupplierType.getText().trim().equals("")){
    		MsgBox.showWarning(this,getSupplierResources("supplierType")+getSupplierResources("notNull"));
        	SysUtil.abort();
    	}
		String isAuditstr = "";
    	for (int i = 0; i<editData.getAuditBill().size();i++){
    		FDCSplPeriodAuditBillEntryInfo info = editData.getAuditBill().get(i);
    		if(info.isIsAudit()){

    			if(null == info.getGrade()){
    				FDCMsgBox.showWarning(this,getSupplierResources("supplierGrade"));
    				abort();
    			}
    		}else{
    			isAuditstr += info.getSupplierType().getName()+",";

    		}
    	}
    	if(isAuditstr.length() > 0){
		  int yes=MsgBox.showConfirm2New(this, isAuditstr+getSupplierResources("isAudit"));
		  if(yes > 0){
			SysUtil.abort();
		  }
    	}
    }
    
    
    /**
	 * @description		判断选择的供应商类型是否已经添加
	 * @author			陈伟		
	 * @createDate		2010-11-20
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private boolean isSupplierType(String typeName) {
		IRow row = null;
		int length = getDetailTable().getRowCount();
		for(int i = 0 ; i<length;i++){
			row = getDetailTable().getRow(i);
			FDCSplServiceTypeInfo info = (FDCSplServiceTypeInfo)row.getCell("supplierType").getValue();
			if(typeName.equals(info.getName())){
				return true;
			}
			
		}
		return false;
	}
	
	/**
	 * @description		获取供应商评审分录中的供应商类型用于给供应商类型F7赋值
	 * @author			陈伟		
	 * @createDate		2010-11-20
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private Object[] getSupplierTypeObj() {
		IRow row = null;
		int length = getDetailTable().getRowCount();
		Object obj[] = new Object[length];
		for(int i = 0 ; i<length;i++){
			row = getDetailTable().getRow(i);
			FDCSplServiceTypeInfo info = (FDCSplServiceTypeInfo)row.getCell("supplierType").getValue();
			obj[i]=info;
			
		}
       return obj;
	}
    /**
     * @description		设置供应商类型和增加分录行
     * @author			陈伟		
     * @createDate		2010-11-4
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    public void setSupplierType(){

		
		IRow row = null;
		/*
		 * 判断数据类型
		 */
		if(	prmtSupplierType.getData() instanceof FDCSplServiceTypeInfo){		 

			FDCSplServiceTypeInfo info = (FDCSplServiceTypeInfo)prmtSupplierType.getData() ;
			if(null == info){
				prmtSupplierType.setData(editData.getSupplier().getSupplierType());
			}
			info = (FDCSplServiceTypeInfo)prmtSupplierType.getData() ;
			if(!isSupplierType(info.getName())){	 
			row = getDetailTable().addRow();
		     for(int j=0;j< editData.getSupplier().getSupplierServiceType().size();j++){
			     if( null != editData.getSupplier().getSupplierServiceType().get(j)){	    			 
				    	  String grade = editData.getSupplier().getSupplierServiceType().get(j).getState(); 
				    	  if(info.getName().equals(editData.getSupplier().getSupplierServiceType().get(j).getServiceType().getName())){
				    	    row.getCell("historyState").setValue(grade);	
				    	    break;
				    	  }
				  }	 
			     }    		 
			row.getCell("supplierType").setValue(info);
			row.getCell("supplierType").setUserObject(info);
//			row.getCell("historyState").setValue(info.getName());
			row.getCell("isAutidor").setValue(Boolean.FALSE);
			row.getCell("historyState").getStyleAttributes().setLocked(true);
			row.getCell("score").getStyleAttributes().setLocked(true);
			row.getCell("grade").getStyleAttributes().setLocked(true);
			}
		}else{
			Object obj[] = (Object[])prmtSupplierType.getData();
			
			/*
			 * 对供应商类型赋值
			 */
			int lengths = obj != null ? obj.length : 0;
			if(lengths <= 0){
				return;
			}
			
			if(obj[0] instanceof FDCSplServiceTypeInfo ){
				
			   for(int i = 0 ; i< lengths;i++){
				 
				  FDCSplServiceTypeInfo info = (FDCSplServiceTypeInfo)obj[i];
				  if(!isSupplierType(info.getName())){
				     row = getDetailTable().addRow();
				     for(int j=0;j< editData.getSupplier().getSupplierServiceType().size();j++){
				     if( null != editData.getSupplier().getSupplierServiceType().get(j)){	    			 
					    	  String grade = editData.getSupplier().getSupplierServiceType().get(j).getState(); 
					    	  if(info.getName().equals(editData.getSupplier().getSupplierServiceType().get(j).getServiceType().getName())){
					    	    row.getCell("historyState").setValue(grade);	
					    	    break;
					    	  }
					  }	 
				     }
				     row.getCell("supplierType").setValue(info);
				     row.getCell("isAutidor").setValue(Boolean.FALSE);
				     row.getCell("historyState").getStyleAttributes().setLocked(true);
				     row.getCell("score").getStyleAttributes().setLocked(true);
				     row.getCell("grade").getStyleAttributes().setLocked(true);
				  }
				 
				}
			    obj=getSupplierTypeObj();
		    	prmtSupplierType.setDataNoNotify(obj);
			}else{
				if(null != obj[0]){
			      prmtSupplierType.setData(obj[0]);
				}
			}
		}
			
		
    }
    
	protected ICoreBase getBizInterface() throws Exception {
		 
		return FDCSplPeriodAuditBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtIsGrade;
	}

	protected KDTextField getNumberCtrl() { 
		KDTextField t = new KDTextField();
		/*
		 * 基类校验必须返回一个非空的KDTextField。
		 */
		if (null == editData.getNumber()) {
			editData.setNumber(UUID.randomUUID().toString());

		}
		t.setText(editData.getNumber());
		return t;
	}
   public SelectorItemCollection getSelectors() {
       SelectorItemCollection sic = super.getSelectors();
       sic.add(new SelectorItemInfo("*"));
       sic.add(new SelectorItemInfo("businessDate"));
       sic.add(new SelectorItemInfo("creator.*"));
       sic.add(new SelectorItemInfo("createTime"));
       sic.add(new SelectorItemInfo("bizDate"));
       sic.add(new SelectorItemInfo("description"));
       sic.add(new SelectorItemInfo("auditor.*"));
       sic.add(new SelectorItemInfo("auditTime"));
       sic.add(new SelectorItemInfo("score"));
       sic.add(new SelectorItemInfo("state"));
       sic.add(new SelectorItemInfo("supplier.*"));
       sic.add(new SelectorItemInfo("supplier.supplierType.*"));
       sic.add(new SelectorItemInfo("supplier.supplierServiceType.*"));
       sic.add(new SelectorItemInfo("supplier.supplierServiceType.serviceType.*"));
       sic.add(new SelectorItemInfo("number"));
       sic.add(new SelectorItemInfo("project.*"));
       sic.add(new SelectorItemInfo("auditor.*"));
       
	return sic;
}
}