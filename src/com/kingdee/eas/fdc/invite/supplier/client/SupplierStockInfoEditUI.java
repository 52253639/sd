/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import org.apache.jackrabbit.uuid.UUID;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.supplier.AchievementCollection;
import com.kingdee.eas.fdc.invite.supplier.AchievementFactory;
import com.kingdee.eas.fdc.invite.supplier.AchievementInfo;
import com.kingdee.eas.fdc.invite.supplier.AptitudeFileCollection;
import com.kingdee.eas.fdc.invite.supplier.AptitudeFileFactory;
import com.kingdee.eas.fdc.invite.supplier.AptitudeFileInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplChangeHistroyFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplChangeHistroyInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.invite.supplier.YearSaleCollection;
import com.kingdee.eas.fdc.invite.supplier.YearSaleFactory;
import com.kingdee.eas.fdc.invite.supplier.YearSaleInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;



/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		信息变更界面
 *		
 * @author		陈伟
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see						
 */
public class SupplierStockInfoEditUI extends AbstractSupplierStockInfoEditUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -10976015386657076L;
	private static final Logger logger = CoreUIObject.getLogger(SupplierStockInfoEditUI.class);
	/** 供应商界面，用于嵌套 */
	private SupplierStockEditUI plUI = null;
	/**供应商版本*/
	private long verseion = -1;
    /**
     * output class constructor
     */
    public SupplierStockInfoEditUI() throws Exception
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
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
        setAuditBtn();
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    	plUI.actionAttachment_actionPerformed(e);
        //super.actionAttachment_actionPerformed(e);
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
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(editData.getId()==null){
    		abort();
    	}
    	if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
     	   editData = FDCSplChangeHistroyFactory.getRemoteInstance().getFDCSplChangeHistroyInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
     	}
     	if(!editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
 		    FDCMsgBox.showWarning(this,getSupplierResources("notState"));
 	    	abort();
     	}
    	super.actionAudit_actionPerformed(e);
    	this.setOprtState(OprtState.VIEW);

    	setAuditBtn();
    	this.actionAudit.setEnabled(false);
    	this.actionUnAudit.setEnabled(true);
    }



    /**
     * output actionAttamentCtrl_actionPerformed
     */
    public void actionAttamentCtrl_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttamentCtrl_actionPerformed(e);
    }

	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCSplChangeHistroyFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}
	protected IObjectValue createNewData() {
		 
		return new FDCSplChangeHistroyInfo();
	}
    /**
     * @description		资源文件读取方法
     * @author			陈伟		
     * @createDate		2010-12-10
     * @param msg 资源文件键值
     * @return String 
     * @version			EAS1.0
     * @see						
     */
    private String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}

	//获取资源文件
	private String getSupplierResources(String key) {
	    return	EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource",key);

	}
 
	/**
	 * @description		返回字段是否修改
	 * @author			陈伟		
	 * @createDate		2010-12-10
	 * @param nowStr
	 * @param hisStr
	 * @return String
	 * @version			EAS1.0
	 * @see						
	 */
	private String  getChangeStr(String nowStr ,String hisStr) {
		String changeStr = "";
		nowStr = (nowStr==null||nowStr.equals(""))?"":nowStr;
		hisStr = (hisStr==null||hisStr.equals(""))?"":hisStr;
        if(nowStr.equals("") && hisStr.equals("") ){
        	changeStr ="";
		}else
        if(!nowStr.equals("") && hisStr.equals("")){
        	changeStr = getSupplierResources("add")+nowStr +";";
		}else if( nowStr.equals("") && !hisStr.equals("")){
        	changeStr = getSupplierResources("delete")+hisStr+";";
		}else			
		if( !(nowStr .equals(hisStr))){
			changeStr = getSupplierResources("from")+hisStr+getSupplierResources("update")+nowStr+";";
		}
        return changeStr;

	}
	
	/**
	 * @description		不清空界面屏蔽父类方法
	 * @author			陈伟		
	 * @createDate		2010-11-28
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	protected void doAfterSubmit(IObjectPK pk) throws Exception {
 
	}
	/**
	 * @description		得到变更信息
	 * @author			陈伟		
	 * @createDate		2010-11-15
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private String getChangeHistory(SupplierStockInfo info) {
		/*变更信息用于存储*/
		String changeStr = "";
		/*字段变更判断用于每次获取变更情况*/
		String chStr = "";
		SupplierStockInfo hisInfo = editData.getSupplier();
		chStr = getChangeStr(info.getName(),hisInfo.getName()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("supplierName")+getSupplierResources("from")+chStr;
		}
		chStr = getChangeStr(info.getNumber(),hisInfo.getNumber()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("supplierNumber")+getSupplierResources("from")+chStr;
		}		
		chStr = getChangeStr(info.getEnterpriseKind()==null?null:info.getEnterpriseKind().toString(),hisInfo.getEnterpriseKind()==null?null:hisInfo.getEnterpriseKind().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("kind")+chStr;
		}	
		chStr = getChangeStr(info.getEnterpriseMaster()==null?null:info.getEnterpriseMaster().toString(),hisInfo.getEnterpriseMaster()==null?null:hisInfo.getEnterpriseMaster().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("deputy")+chStr;
		}
		chStr = getChangeStr(info.getBuildDate()==null?null:info.getBuildDate().toString(),hisInfo.getBuildDate()==null?null:hisInfo.getBuildDate().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("bornDate")+chStr;
		}
		chStr = getChangeStr(info.getRegisterMoney()==null?null:info.getRegisterMoney().toString(),hisInfo.getRegisterMoney()==null?null:hisInfo.getRegisterMoney().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("money")+chStr;
		}
		chStr = getChangeStr(info.getCurrency()==null?null:info.getCurrency().toString(),hisInfo.getCurrency()==null?null:hisInfo.getCurrency().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("currency")+chStr;
		}
		chStr = getChangeStr(info.getWebSite()==null?null:info.getWebSite().toString(),hisInfo.getWebSite()==null?null:hisInfo.getWebSite().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("homepage")+chStr;
		}
		chStr = getChangeStr(info.getBusinessNum()==null?null:info.getBusinessNum().toString(),hisInfo.getBusinessNum()==null?null:hisInfo.getBusinessNum().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("license")+chStr;
		}
		
		chStr = getChangeStr(info.getPeopleCount()==null?null:info.getPeopleCount().toBigInteger().toString(),hisInfo.getPeopleCount()==null?null:hisInfo.getPeopleCount().toBigInteger().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("handTotal")+chStr;
		}
		chStr = getChangeStr(info.getMainPeopleCount()==null?null:info.getMainPeopleCount().toBigInteger().toString(),hisInfo.getMainPeopleCount()==null?null:hisInfo.getMainPeopleCount().toBigInteger().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("workersAmount")+chStr;
		}
		
		chStr = getChangeStr(info.getDevelopPeopleCount()==null?null:info.getDevelopPeopleCount().toBigInteger().toString(),hisInfo.getDevelopPeopleCount()==null?null:hisInfo.getDevelopPeopleCount().toBigInteger().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("researchAmount")+chStr;
		}
		
		chStr = getChangeStr(info.getManagePeopleCount()==null?null:info.getManagePeopleCount().toBigInteger().toString(),hisInfo.getManagePeopleCount()==null?null:hisInfo.getManagePeopleCount().toBigInteger().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("managerAmount")+chStr;
		}
		chStr = getChangeStr(info.getProvince()==null?null:info.getProvince().getName(),hisInfo.getProvince()==null?null:hisInfo.getProvince().getName()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("province")+chStr;
		}		
		chStr = getChangeStr(info.getCity()==null?null:info.getCity().getName(),hisInfo.getCity()==null?null:hisInfo.getCity().getName()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("city")+chStr;
		}	
		chStr = getChangeStr(info.getPostNumber()==null?null:info.getPostNumber().toString(),hisInfo.getPostNumber()==null?null:hisInfo.getPostNumber().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("postalcode")+chStr;
		}
		chStr = getChangeStr(info.getLinkPhone()==null?null:info.getLinkPhone().toString(),hisInfo.getLinkPhone()==null?null:hisInfo.getLinkPhone().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("companyPhone")+chStr;
		}
		chStr = getChangeStr(info.getLinkMail()==null?null:info.getLinkMail().toString(),hisInfo.getLinkMail()==null?null:hisInfo.getLinkMail().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("companyMailbox")+chStr;
		}
	 
		chStr = getChangeStr(info.getLinkFax()==null?null:info.getLinkFax().toString(),hisInfo.getLinkFax()==null?null:hisInfo.getLinkFax().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("linkFax")+chStr;
		}
		chStr = getChangeStr(info.getAddress()==null?null:info.getAddress().toString(),hisInfo.getAddress()==null?null:hisInfo.getAddress().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("companyAddress")+chStr;
		}
		chStr = getChangeStr(info.getNetMoney()==null?null:info.getNetMoney().toBigInteger().toString(),hisInfo.getNetMoney()==null?null:hisInfo.getNetMoney().toBigInteger().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("asset")+chStr;
		}
		chStr = getChangeStr(info.getMoneyPercent()==null?null:info.getMoneyPercent().toString(),hisInfo.getMoneyPercent()==null?null:hisInfo.getMoneyPercent().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("indebted")+chStr;
		}
		chStr = getChangeStr(info.getBankName()==null?null:info.getBankName().toString(),hisInfo.getBankName()==null?null:hisInfo.getBankName().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("bank")+chStr;
		}
		chStr = getChangeStr(info.getBankCount()==null?null:info.getBankCount().toString(),hisInfo.getBankCount()==null?null:hisInfo.getBankCount().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("account")+chStr;
		}
		chStr = getChangeStr(info.getCreditLevel()==null?null:info.getCreditLevel().toString(),hisInfo.getCreditLevel()==null?null:hisInfo.getCreditLevel().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("bankGrade")+chStr;
		}
		chStr = getChangeStr(info.getBankLinkPhone()==null?null:info.getBankLinkPhone().toString(),hisInfo.getBankLinkPhone()==null?null:hisInfo.getBankLinkPhone().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("bankLinkman")+chStr;
		}
		chStr = getChangeStr(info.getMainWork()==null?null:info.getMainWork().toString(),hisInfo.getMainWork()==null?null:hisInfo.getMainWork().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("area")+chStr;
		}
		chStr = getChangeStr(info.getWorkClass()==null?null:info.getWorkClass().toString(),hisInfo.getWorkClass()==null?null:hisInfo.getWorkClass().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("workClass")+chStr;
		}
		chStr = getChangeStr(info.getRemark()==null?null:info.getRemark().toString(),hisInfo.getRemark()==null?null:hisInfo.getRemark().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("remark")+chStr;
		}
		String serviceArea = info.getServiceArea();
		chStr = getChangeStr(serviceArea==null?null:serviceArea,hisInfo.getServiceArea()==null?null:hisInfo.getServiceArea().toString()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("serviceArea")+chStr;
		}
		FDCSupplierServiceTypeCollection ifsc = info.getSupplierServiceType();
		/*在保存后供应商中的分录会被清空所以重取，下面是取服务类型*/
		if(ifsc.size() ==0 ||ifsc.get(0).getServiceType()==null){
		    EntityViewInfo view = new EntityViewInfo();
		    view.getSelector().add(new SelectorItemInfo("*"));
		    view.getSelector().add(new SelectorItemInfo("serviceType.*"));
		    
	    	FilterInfo filter = new FilterInfo();
	    	
	    	filter.getFilterItems().add(new FilterItemInfo("supplier", info.getId().toString()));
		    view.setFilter(filter);
			
			try {
				ifsc = FDCSupplierServiceTypeFactory.getRemoteInstance().getFDCSupplierServiceTypeCollection(view);
			} catch (BOSException e) {
				handleException(e);
			}
		}
		/*由于层次太深经常取不到分录所以重新取值，下面是取服务类型*/
		FDCSupplierServiceTypeCollection hisIfsc = hisInfo.getSupplierServiceType();
		if(hisIfsc.size() ==0 ||hisIfsc.get(0).getServiceType()==null){
		    EntityViewInfo view = new EntityViewInfo();
		    view.getSelector().add(new SelectorItemInfo("*"));
		    view.getSelector().add(new SelectorItemInfo("serviceType.*"));
		    
	    	FilterInfo filter = new FilterInfo();
	    	
	    	filter.getFilterItems().add(new FilterItemInfo("supplier", hisInfo.getId().toString()));
		    view.setFilter(filter);
			
			try {
				hisIfsc = FDCSupplierServiceTypeFactory.getRemoteInstance().getFDCSupplierServiceTypeCollection(view);
			} catch (BOSException e) {
				handleException(e);
			}
		}
		Iterator it = ifsc.iterator();
		boolean b = false;
		while(it.hasNext()){
			FDCSupplierServiceTypeInfo ftypeInfo = (FDCSupplierServiceTypeInfo) it.next();
			Iterator hisIt = hisIfsc.iterator();
			b = false;
			while(hisIt.hasNext()){
			  FDCSupplierServiceTypeInfo hisftypeInfo = (FDCSupplierServiceTypeInfo) hisIt.next();
			  if(hisftypeInfo.getServiceType()==null){
				  continue;
			  }
			  if(ftypeInfo.getServiceType().getName().equals(hisftypeInfo.getServiceType().getName())){
				  b=true;
				  break;
			  }
			}
			if(!b){
				changeStr += getSupplierResources("serviceType")+getSupplierResources("add")+ftypeInfo.getServiceType().getName()+";";
			}
		}

		  it = hisIfsc.iterator();
		  b = false;
		while(it.hasNext()){
			
			FDCSupplierServiceTypeInfo ftypeInfo = (FDCSupplierServiceTypeInfo) it.next();
			  if(ftypeInfo.getServiceType()==null){
				  continue;
			  }
			Iterator hisIt = ifsc.iterator();
			b = false;
			while(hisIt.hasNext()){
			  FDCSupplierServiceTypeInfo hisftypeInfo = (FDCSupplierServiceTypeInfo) hisIt.next();

			  if(ftypeInfo.getServiceType().getName().equals(hisftypeInfo.getServiceType().getName())){
				  b=true;
				  break;
			  }
			}
			if(!b){
				changeStr += getSupplierResources("serviceType")+getSupplierResources("delete")+ftypeInfo.getServiceType().getName()+";";
			}
		}
		chStr = getChangeStr(info.getSupplierType()==null?null:info.getSupplierType().getName(),hisInfo.getSupplierType()==null?null:hisInfo.getSupplierType().getName()) ;
		if(chStr.length()>0){
			changeStr += getSupplierResources("supType")+chStr;
		}	
		SupplierLinkPersonCollection iflp = info.getLinkPerson();
		if(iflp.size() == 0 || iflp.get(0).getPersonName()==null){
		    EntityViewInfo view = new EntityViewInfo();
		    view.getSelector().add(new SelectorItemInfo("*"));
	    	FilterInfo filter = new FilterInfo();
	    	
	    	filter.getFilterItems().add(new FilterItemInfo("parent", info.getId().toString()));
		    view.setFilter(filter);
			
			try {
				iflp = SupplierLinkPersonFactory.getRemoteInstance().getSupplierLinkPersonCollection(view);
			} catch (BOSException e) {
				handleException(e);;
			}
		}
		SupplierLinkPersonCollection hislp = hisInfo.getLinkPerson();
		/*由于层次太深经常取不到分录所以重新取值，下面是取联系人*/
		if(hislp.size() == 0 || hislp.get(0).getPersonName()==null){
		    EntityViewInfo view = new EntityViewInfo();
		    view.getSelector().add(new SelectorItemInfo("*"));
	    	FilterInfo filter = new FilterInfo();
	    	
	    	filter.getFilterItems().add(new FilterItemInfo("parent", hisInfo.getId().toString()));
		    view.setFilter(filter);
			
			try {
				hislp = SupplierLinkPersonFactory.getRemoteInstance().getSupplierLinkPersonCollection(view);
			} catch (BOSException e) {
				handleException(e);
			}
		}
		Iterator iter = iflp.iterator();
		boolean m = false;
		while(iter.hasNext()){
			SupplierLinkPersonInfo lpInfo = (SupplierLinkPersonInfo)iter.next();
			Iterator hisIter = hislp.iterator();
			m = false;
			while(hisIter.hasNext()){
				SupplierLinkPersonInfo hislpInfo = (SupplierLinkPersonInfo) hisIter.next();
				if(lpInfo.getPersonName().equals(hislpInfo.getPersonName())){
					m = true;
					
					chStr = getChangeStr(lpInfo.getPosition() ==null?null:lpInfo.getPosition().toString(),hislpInfo.getPosition() == null?null:hislpInfo.getPosition().toString());
					if(chStr.length()>0){
						changeStr +=lpInfo.getPersonName()+getSupplierResources("duty")+chStr;
					}
					chStr = getChangeStr(lpInfo.getPhone() ==null?null:lpInfo.getPhone().toString(), hislpInfo.getPhone() ==null?null:hislpInfo.getPhone().toString());
					if(chStr.length()>0){
						changeStr += lpInfo.getPersonName()+getSupplierResources("telephone")+chStr;
					}
					chStr = getChangeStr(lpInfo.getWorkPhone() ==null?null:lpInfo.getWorkPhone().toString(), hislpInfo.getWorkPhone() ==null?null:hislpInfo.getWorkPhone().toString());
					if(chStr.length()>0){
						changeStr +=lpInfo.getPersonName()+getSupplierResources("workPhone")+ chStr;
					}
					chStr = getChangeStr(lpInfo.getPersonFax() ==null?null:lpInfo.getPersonFax().toString(), hislpInfo.getPersonFax() ==null?null:hislpInfo.getPersonFax());
					if(chStr.length()>0){
						changeStr += lpInfo.getPersonName()+getSupplierResources("fax")+ chStr;
					}
					if(!lpInfo.isIsDefault() && hislpInfo.isIsDefault()){
						changeStr +=lpInfo.getPersonName()+getSupplierResources("isChange")+lpInfo.isIsDefault()+";";
					}
					chStr = getChangeStr(lpInfo.getEmail() ==null?null:lpInfo.getEmail().toString(), hislpInfo.getEmail() ==null?null:hislpInfo.getEmail().toString());
					if(chStr.length()>0){
						changeStr +=lpInfo.getPersonName()+getSupplierResources("mailBox") +chStr;
					}
					break;
				}
			}
			if(!m){
				changeStr +=getSupplierResources("linkPerson")+getSupplierResources("add")+ lpInfo.getPersonName()+";";
			}
			}
		Iterator iterP =hislp.iterator();
		m =false;
		while(iterP.hasNext()){
			SupplierLinkPersonInfo lpInfo = (SupplierLinkPersonInfo)iterP.next();
			Iterator hisIter = iflp.iterator();
			m = false;
			while(hisIter.hasNext()){
				SupplierLinkPersonInfo hislpInfo = (SupplierLinkPersonInfo) hisIter.next();
				if(lpInfo.getPersonName().equals(hislpInfo.getPersonName())){
					m = true;
	                break;
				}
			}
			if(!m){
				changeStr +=getSupplierResources("linkPerson")+getSupplierResources("delete") + lpInfo.getPersonName()+";";
			}
		}
		/*由于层次太深经常取不到分录所以重新取值，下面是取近三年销售额*/
		YearSaleCollection yCollection = info.getYearSale();
		if(yCollection.size() == 0 || yCollection.get(0).getYear()==null){
		    EntityViewInfo view = new EntityViewInfo();
		    view.getSelector().add(new SelectorItemInfo("*"));
	    	FilterInfo filter = new FilterInfo();
	    	
	    	filter.getFilterItems().add(new FilterItemInfo("parent", info.getId().toString()));
		    view.setFilter(filter);
			
			try {
				yCollection = YearSaleFactory.getRemoteInstance().getYearSaleCollection(view);
			} catch (BOSException e) {
				handleException(e);;
			}
		}
		YearSaleCollection hisSaleCollection = hisInfo.getYearSale();
		if(hisSaleCollection.size() == 0 || hisSaleCollection.get(0).getYear()==null){
		    EntityViewInfo view = new EntityViewInfo();
		    view.getSelector().add(new SelectorItemInfo("*"));
	    	FilterInfo filter = new FilterInfo();
	    	
	    	filter.getFilterItems().add(new FilterItemInfo("parent", hisInfo.getId().toString()));
		    view.setFilter(filter);
			
			try {
				hisSaleCollection = YearSaleFactory.getRemoteInstance().getYearSaleCollection(view);
			} catch (BOSException e) {
				handleException(e);
			}
		}
		Iterator saleIterator = yCollection.iterator();
		boolean n = false;
		while(saleIterator.hasNext()){
			YearSaleInfo lpInfo = (YearSaleInfo)saleIterator.next();
            if(lpInfo.getYear() == null || lpInfo.getYear().equals("")){
            	continue;
            }
			Iterator hisSaleIterator = hisSaleCollection.iterator();
			n = false;
			while(hisSaleIterator.hasNext()){
				YearSaleInfo hislpInfo = (YearSaleInfo) hisSaleIterator.next();

				if(lpInfo.getYear().equals(hislpInfo.getYear())){
					n = true;
					
					chStr = getChangeStr(lpInfo.getSaleCount() ==null?null:lpInfo.getSaleCount().toString(),hislpInfo.getSaleCount() == null?null:hislpInfo.getSaleCount().toString());
					if(chStr.length()>0){
						changeStr +=getSupplierResources("year")+lpInfo.getYear()+getSupplierResources("sale")+chStr;
					}
					break;
				}
			}
			if(!n){
				changeStr +=getSupplierResources("threeYear")+getSupplierResources("sale")+getSupplierResources("add") + lpInfo.getYear()+";";
			}
			}
		saleIterator =hisSaleCollection.iterator();
		m =false;
		while(saleIterator.hasNext()){
			YearSaleInfo lpInfo = (YearSaleInfo)saleIterator.next();
            if(lpInfo.getYear() == null || lpInfo.getYear().equals("")){
            	continue;
            }
			Iterator hisSaleIterator = yCollection.iterator();
			m = false;
			while(hisSaleIterator.hasNext()){
				YearSaleInfo hislpInfo = (YearSaleInfo) hisSaleIterator.next();
 
				if(lpInfo.getYear().equals(hislpInfo.getYear())){
					m = true;
					break;
				}

				}
				if(!m){
					changeStr +=getSupplierResources("threeYear")+getSupplierResources("sale")+getSupplierResources("delete")+ lpInfo.getYear()+";";
				}

				
			}
		AptitudeFileCollection aptitudeFileCollection = info.getAptitudeFile();
		if(aptitudeFileCollection.size() ==0 || aptitudeFileCollection.get(0).getAptNumber()==null){
		    EntityViewInfo view = new EntityViewInfo();
		    view.getSelector().add(new SelectorItemInfo("*"));
	    	FilterInfo filter = new FilterInfo();
	    	
	    	filter.getFilterItems().add(new FilterItemInfo("parent", info.getId().toString()));
		    view.setFilter(filter);
			
			try {
				aptitudeFileCollection = AptitudeFileFactory.getRemoteInstance().getAptitudeFileCollection(view);
			} catch (BOSException e) {
				handleException(e);
			}
		}
		AptitudeFileCollection hisAptitudeFileCollection = hisInfo.getAptitudeFile();
		if(hisAptitudeFileCollection.size() ==0 || hisAptitudeFileCollection.get(0).getAptNumber()==null){
		    EntityViewInfo view = new EntityViewInfo();
		    view.getSelector().add(new SelectorItemInfo("*"));
	    	FilterInfo filter = new FilterInfo();
	    	
	    	filter.getFilterItems().add(new FilterItemInfo("parent", hisInfo.getId().toString()));
		    view.setFilter(filter);
			
			try {
				hisAptitudeFileCollection = AptitudeFileFactory.getRemoteInstance().getAptitudeFileCollection(view);
			} catch (BOSException e) {
				handleException(e);
			}
		}
		Iterator apIt = aptitudeFileCollection.iterator();
		boolean y = false;
		while(apIt.hasNext()){
			AptitudeFileInfo lpInfo = (AptitudeFileInfo)apIt.next();
			Iterator hisIter = hisAptitudeFileCollection.iterator();
			y = false;
			while(hisIter.hasNext()){
				AptitudeFileInfo hislpInfo = (AptitudeFileInfo) hisIter.next();
				if(lpInfo.getAptNumber().equals(hislpInfo.getAptNumber())){
					y = true;
					chStr = getChangeStr(lpInfo.getAptName() ==null?null:lpInfo.getAptName().toString(),hislpInfo.getAptName() == null?null:hislpInfo.getAptName().toString());
					if(chStr.length()>0){
						changeStr +=getSupplierResources("fileName")+chStr;
					}
					chStr = getChangeStr(lpInfo.getAptLevel() ==null?null:lpInfo.getAptLevel().toString(), hislpInfo.getAptLevel() ==null?null:hislpInfo.getAptLevel().toString());
					if(chStr.length()>0){
						changeStr += getSupplierResources("aptitude")+chStr;
					}
					chStr = getChangeStr(lpInfo.getEndDate()==null?null:lpInfo.getEndDate().toString(), hislpInfo.getEndDate() ==null?null:hislpInfo.getEndDate().toString());
					if(chStr.length()>0){
						changeStr +=getSupplierResources("endTime")+ chStr;
					}
					chStr = getChangeStr(lpInfo.getAwardUnit() ==null?null:lpInfo.getAwardUnit().toString(), hislpInfo.getAwardUnit() ==null?null:hislpInfo.getAwardUnit());
					if(chStr.length()>0){
						changeStr += getSupplierResources("award")+ chStr;
					}
					chStr = getChangeStr(lpInfo.getRemark() ==null?null:lpInfo.getRemark().toString(), hislpInfo.getRemark() ==null?null:hislpInfo.getRemark().toString());
					if(chStr.length()>0){
						changeStr +=getSupplierResources("remark") +chStr;
					}
					if(!lpInfo.isIsHaveAttach() == hislpInfo.isIsHaveAttach()){
						changeStr +=getSupplierResources("isChange")+lpInfo.isIsHaveAttach()+";";
					}
					break;
				}
			}
			if(!y){
				changeStr +=getSupplierResources("aptitudeFile")+getSupplierResources("add") + lpInfo.getAptNumber()+";";
			}
			}
 
		Iterator apItIter =hisAptitudeFileCollection.iterator();
		y =false;
		while(apItIter.hasNext()){
			AptitudeFileInfo lpInfo = (AptitudeFileInfo)apItIter.next();
			Iterator hisIter = aptitudeFileCollection.iterator();
			y = false;
			while(hisIter.hasNext()){
				AptitudeFileInfo hislpInfo = (AptitudeFileInfo) hisIter.next();
				if(lpInfo.getAptNumber().equals(hislpInfo.getAptNumber())){
					y = true;
 
					break;
				  }
				}
				if(!y){
					changeStr +=getSupplierResources("aptitudeFile")+getSupplierResources("delete")+ lpInfo.getAptNumber()+";";
				}
				
			}
		AchievementCollection aCollection = info.getAchievement();
		if(aCollection.size()==0 || aCollection.get(0).getParent()==null){
		    EntityViewInfo view = new EntityViewInfo();
		    view.getSelector().add(new SelectorItemInfo("*"));
	    	FilterInfo filter = new FilterInfo();
	    	
	    	filter.getFilterItems().add(new FilterItemInfo("parent", info.getId().toString()));
		    view.setFilter(filter);
			
			try {
				aCollection = AchievementFactory.getRemoteInstance().getAchievementCollection(view);
			} catch (BOSException e) {
				handleException(e);
			}
		}
		AchievementCollection hisCollection = hisInfo.getAchievement();
		if(hisCollection.size() ==0 || hisCollection.get(0).getProjectName()==null){
		    EntityViewInfo view = new EntityViewInfo();
		    view.getSelector().add(new SelectorItemInfo("*"));
	    	FilterInfo filter = new FilterInfo();
	    	
	    	filter.getFilterItems().add(new FilterItemInfo("parent", hisInfo.getId().toString()));
		    view.setFilter(filter);
			
			try {
				hisCollection = AchievementFactory.getRemoteInstance().getAchievementCollection(view);
			} catch (BOSException e) {
				handleException(e);
			}
		}
		Iterator acIt = aCollection.iterator();
		boolean j = false;
		while(acIt.hasNext()){
			AchievementInfo lpInfo = (AchievementInfo)acIt.next();
			Iterator hisIter = hisCollection.iterator();
			j = false;
			while(hisIter.hasNext()){
				AchievementInfo hislpInfo = (AchievementInfo) hisIter.next();
				if(lpInfo.getProjectName().equals(hislpInfo.getProjectName())){
					j = true;
					chStr = getChangeStr(lpInfo.getClientName() ==null?null:lpInfo.getClientName().toString(),hislpInfo.getClientName() == null?null:hislpInfo.getClientName().toString());
					if(chStr.length()>0){
						changeStr +=getSupplierResources("clientName")+chStr;
					}
					chStr = getChangeStr(lpInfo.getClientAddress() ==null?null:lpInfo.getClientAddress().toString(), hislpInfo.getClientAddress() ==null?null:hislpInfo.getClientAddress().toString());
					if(chStr.length()>0){
						changeStr += getSupplierResources("clientAddress")+chStr;
					}
					chStr = getChangeStr(lpInfo.getClientLinkPerson()==null?null:lpInfo.getClientLinkPerson().toString(), hislpInfo.getClientLinkPerson() ==null?null:hislpInfo.getClientLinkPerson().toString());
					if(chStr.length()>0){
						changeStr +=getSupplierResources("clientLink")+ chStr;
					}
					chStr = getChangeStr(lpInfo.getPhone() ==null?null:lpInfo.getPhone().toString(), hislpInfo.getPhone() ==null?null:hislpInfo.getPhone().toString());
					if(chStr.length()>0){
						changeStr += getSupplierResources("phone")+ chStr;
					}
					chStr = getChangeStr(lpInfo.getContractPay() ==null?null:lpInfo.getContractPay().toString(), hislpInfo.getContractPay() ==null?null:hislpInfo.getContractPay().toString());
					if(chStr.length()>0){
						changeStr += getSupplierResources("contractTotal")+ chStr;
					}
					chStr = getChangeStr(lpInfo.getStartDate() ==null?null:lpInfo.getStartDate().toString(), hislpInfo.getStartDate() ==null?null:hislpInfo.getStartDate().toString());
					if(chStr.length()>0){
						changeStr += getSupplierResources("startDate")+ chStr;
					}
					chStr = getChangeStr(lpInfo.getEndDate() ==null?null:lpInfo.getEndDate().toString(), hislpInfo.getEndDate() ==null?null:hislpInfo.getEndDate().toString());
					if(chStr.length()>0){
						changeStr +=getSupplierResources("endDate")+ chStr;
					}
					chStr = getChangeStr(lpInfo.getPeopleCount() ==null?null:lpInfo.getPeopleCount().toString(), hislpInfo.getPeopleCount() ==null?null:hislpInfo.getPeopleCount().toString());
					if(chStr.length()>0){
						changeStr += getSupplierResources("personCount")+ chStr;
					}
					chStr = getChangeStr(lpInfo.getRemark() ==null?null:lpInfo.getRemark().toString(), hislpInfo.getRemark() ==null?null:hislpInfo.getRemark().toString());
					if(chStr.length()>0){
						changeStr +=getSupplierResources("remark") +chStr;
					}
					if(!lpInfo.isIsHaveAttach() == hislpInfo.isIsHaveAttach()){
						changeStr +=getSupplierResources("isChange")+lpInfo.isIsHaveAttach()+";";
					}
					break;
				}
			}
			if(!j){
				changeStr +=getSupplierResources("achievement")+getSupplierResources("add") + lpInfo.getProjectName()+";";
			}
			}
		Iterator apIts =hisCollection.iterator();
		y =false;
		while(apIts.hasNext()){
			AchievementInfo lpInfo = (AchievementInfo)apIts.next();
			Iterator hisIter = aCollection.iterator();
			j = false;
			while(hisIter.hasNext()){
				AchievementInfo hislpInfo = (AchievementInfo) hisIter.next();
				if(lpInfo.getProjectName().equals(hislpInfo.getProjectName())){
					j = true; 
				}				
			}
			
			if(!j){
				changeStr +=getSupplierResources("achievement")+getSupplierResources("delete") + lpInfo.getProjectName()+";";
			}
		}
		return changeStr;
		
	}
    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	 
 
    	if(null == editData.getName()){
     	   editData.setName(UUID.randomUUID().toString());
     	}
     	if(null == editData.getNumber()){
     	   editData.setNumber(UUID.randomUUID().toString());
     	}
     	loadSupplierUI(e); 
        super.actionSave_actionPerformed(e);
    }
    /**
	 * @description		装载供应商界面数据
	 * @author			陈伟		
	 * @createDate		2010-11-29
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
     * @throws Exception 
	 * @see						
	 */
	private void loadSupplierUI(ActionEvent e) throws Exception {
		SupplierStockInfo info = editData.getSupplier();
 		EntityViewInfo view = new EntityViewInfo();
 		view.getSelector().add(new SelectorItemInfo("*"));
 		view.getSelector().add(new SelectorItemInfo("serviceType.*"));
 		
 		FilterInfo filter = new FilterInfo();
 		filter.getFilterItems().add(new FilterItemInfo("supplier", info.getId().toString()));
		view.setFilter(filter);
		/*将历史数据中的服务类型分录取出*/
		FDCSupplierServiceTypeCollection stc =FDCSupplierServiceTypeFactory.getRemoteInstance().getFDCSupplierServiceTypeCollection(view);
		plUI.storeFields(); 
		
	 	SupplierStockInfo infoChange = (SupplierStockInfo)plUI.getDataObject();

    	if(null != infoChange){
    	  infoChange.setState(SupplierStateEnum.SUBMIT);
    	   
    	if( verseion <0 || null == infoChange.getId() ){
    		verseion = info.getVersion().longValue()+1;
        	infoChange.setVersion(BigDecimal.valueOf(verseion));
        	infoChange.setIsLatestVer(false);  
        	infoChange.setSrcSupplier(info)  ;
 
        	infoChange.setId(null);        	 
    	  }else{ 
 
 
        	infoChange.setVersion(BigDecimal.valueOf(verseion));
        	infoChange.setIsLatestVer(false);  
        	infoChange.setSrcSupplier(info)  ;

         	 
    		
    	  } 

    	/*
    	 * 值空分录的ID并对部分分录值赋值
    	 */
    	for(int i= 0 ;i<infoChange.getYearSale().size();i++){
    		infoChange.getYearSale().get(i).setId(null);
    		String year =(String)plUI.kdtYearSale.getRow(i).getCell("year").getValue();
    		BigDecimal saleCount =(BigDecimal)plUI.kdtYearSale.getRow(i).getCell("saleCount").getValue();
    		infoChange.getYearSale().get(i).setParent(infoChange);
    		infoChange.getYearSale().get(i).setYear(year);        		 
			infoChange.getYearSale().get(i).setSaleCount(saleCount);
    		
    	}

    	/*
    	 * 值空分录的ID并对状态赋值
    	 */
    	for(int i= 0 ;i<infoChange.getSupplierServiceType().size();i++){
    		infoChange.getSupplierServiceType().get(i).setId(null);
    		FDCSplServiceTypeInfo fdcinfoChange =  infoChange.getSupplierServiceType().get(i).getServiceType();
    		infoChange.getSupplierServiceType().get(i).setSupplier(infoChange);
    		for(int j= 0 ; j < stc.size();j++){
    			FDCSupplierServiceTypeInfo sinfo = stc.get(j);
    			if(sinfo != null){
    			   FDCSplServiceTypeInfo fdcinfo  =sinfo.getServiceType();
    			  if( fdcinfoChange.getName().equals(fdcinfo.getName())){
    				  infoChange.getSupplierServiceType().get(i).setState(sinfo.getState());
    				  
    			   }
    			}
    			
    			
    		}
    	}

    	/*
    	 * 值空分录的ID并对部分分录值赋值
    	 */
    	for(int i= 0 ;i<infoChange.getLinkPerson().size();i++){
    		infoChange.getLinkPerson().get(i).setId(null);
    		Boolean isDefault =(Boolean)plUI.kdtLinkPerson.getRow(i).getCell("isDefault").getValue();
    		 
    		infoChange.getLinkPerson().get(i).setIsDefault(isDefault==null?false:isDefault.booleanValue());
    		String  email =(String)plUI.kdtLinkPerson.getRow(i).getCell("email").getValue();
    		 
    		infoChange.getLinkPerson().get(i).setEmail(email) ;
    		infoChange.getLinkPerson().get(i).setParent(infoChange);
    	}
    	

    	/*
    	 * 值空分录的ID并对部分分录值赋值
    	 */
    	for(int i= 0 ;i<infoChange.getAchievement().size();i++){
    		infoChange.getAchievement().get(i).setId(null);
    		infoChange.getAchievement().get(i).setParent(infoChange);
    	}

    	/*
    	 * 值空分录的ID并对部分分录值赋值
    	 */
    	
    	for(int i= 0 ;i<infoChange.getAptitudeFile().size();i++){
    		infoChange.getAptitudeFile().get(i).setId(null);
    		infoChange.getAptitudeFile().get(i).setParent(infoChange);
    	}
    	
    	if(infoChange.getId()!=null){
    	   if(! (SupplierStockFactory.getRemoteInstance().exists(new ObjectUuidPK(infoChange.getId().toString())))){
    		 FDCMsgBox.showWarning(this,getResource("supplierInfoDeleted"));
      	     super.disposeUIWindow();
    	     abort();
    		
        	}
    	}
    		plUI.setDataObject(infoChange);
    		plUI.loadFields();
        	plUI.storeFields(); 
        	plUI.actionSubmitTwo_actionPerformed(e);
    	}
    	
        infoChange = (SupplierStockInfo)plUI.getDataObject();
    	if(null != infoChange){
   		  
    		infoChange =	SupplierStockFactory.getRemoteInstance().getSupplierStockInfo(new ObjectUuidPK(infoChange.getId().toString()), plUI.getSelectors());
    		plUI.setDataObject(infoChange);
    		plUI.loadFields();
    		info = SupplierStockFactory.getRemoteInstance().getSupplierStockInfo(new ObjectUuidPK(info.getId().toString()), plUI.getSelectors());
    		editData.setSupplier(info) ;
    		String changeStr = getChangeHistory(infoChange);
    		editData.setChangeText(changeStr);
    	}

	}
    
	/**
	 * @description		
	 * @author			陈伟		
	 * @createDate		2010-12-1
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	protected void disposeUIWindow() { 
		super.disposeUIWindow();
	
	}
   
    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	
    	if(editData.getState()!=null &&! this.editData.getState().equals(FDCBillStateEnum.SAVED) && !this.editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
			MsgBox.showWarning( getResource("notSubmit")) ;
			SysUtil.abort();
    		abort();
    	}
    	if(null == editData.getName()){
      	   editData.setName(UUID.randomUUID().toString());
      	}
      	if(null == editData.getNumber()){
      	   editData.setNumber(UUID.randomUUID().toString());
      	}
		IObjectPK pk = null ;
      	if(null == editData.getId()){
      		editData.setState(FDCBillStateEnum.SUBMITTED);
      		  pk = FDCSplChangeHistroyFactory.getRemoteInstance().addnew(editData);
      		editData.setId((BOSUuid) pk.getKeyValue("id"));
      		
      	}
      	loadSupplierUI(e);
    	
     	  
        editData.setState(FDCBillStateEnum.SUBMITTED);

        super.actionSubmit_actionPerformed(e);
        /*设置界面状态为查看*/
        setOprtState(OprtState.VIEW);
        loadFields();
        getUIContext().put(UIContext.ID, editData.getId().toString());
        setAuditBtn();
        this.actionUnAudit.setEnabled(false);
        plUI.storeFields();
        /*删除供应商页签*/
        this.supplierInfo.removeAll();
        /*重新加载供应商页签*/
        initPanel((SupplierStockInfo) plUI.getEditData() );
    	
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
        this.btnUnAudit.setVisible(true);
        this.btnUnAudit.setEnabled(true);
        this.btnSubmit.setEnabled(true);
        this.menuItemSubmit.setEnabled(true);
        
        this.actionUnAudit.setEnabled(true);
        if(getOprtState().equals(OprtState.VIEW) ){
        	this.btnAudit.setEnabled(true);
        	this.btnUnAudit.setEnabled(true);
        	this.actionAudit.setEnabled(true);
        	this.actionUnAudit.setEnabled(true);
        	this.actionSubmit.setEnabled(false);
        }else if(getOprtState().equals("FINDVIEW")){
        	this.btnAudit.setEnabled(false);
        	this.btnUnAudit.setEnabled(false);
        	this.actionAudit.setEnabled(false);
        	this.actionUnAudit.setEnabled(false);
        	this.actionSubmit.setEnabled(false);
        	this.btnSubmit.setEnabled(false);
        	this.menuItemSubmit.setEnabled(false);
        }else{
        	this.btnAudit.setEnabled(false);
        	this.btnUnAudit.setEnabled(false);
        	this.actionAudit.setEnabled(false);
        	this.actionUnAudit.setEnabled(false);
        	this.actionSubmit.setEnabled(true);
        	
        }
       


	}
	
	/**
	 * @description		引用基础供应商库编辑界面
	 * @author			陈伟		
	 * @createDate		2010-12-9
	 * @param Id
	 * @return SupplierStockInfo
	 * @throws BOSException 
	 * @version			EAS1.0
	 * @see						
	 */
	private SupplierStockInfo getSrcSupplierStockInfo(String Id) throws BOSException { 
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));	
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcSupplier", Id));
		filter.getFilterItems().add(new FilterItemInfo("version",FDCHelper.ONE ,CompareType.GREATER));

		view.setFilter(filter);
		SupplierStockCollection ssc  = SupplierStockFactory.getRemoteInstance().getSupplierStockCollection(view);
		if(ssc.size()>0){
			return ssc.get(0);
		}
		return null;
	}
	protected KDTextField getNumberCtrl() { 
		KDTextField number = new KDTextField();
		number.setText(UUID.randomUUID().toString());
		return number;
	}
	/**
	 * @description		判断是否正在修改
	 * @author			陈伟		
	 * @createDate		2010-11-26
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	public void changIsState(){
		if(getOprtState().equals(OprtState.ADDNEW)){
		    SupplierStockInfo supplier = (SupplierStockInfo) getUIContext().get("SupplierInfo");
            
		    EntityViewInfo view = new EntityViewInfo();
	    	FilterInfo checkFilter = new FilterInfo();
		    checkFilter.getFilterItems().add(new FilterItemInfo("supplier.id", supplier.getId().toString()));
		   // checkFilter.getFilterItems().add(new FilterItemInfo("version",FDCHelper.ONE ,CompareType.GREATER));
		    checkFilter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.NOTEQUALS));
		    view.setFilter(checkFilter);
		     
		    FDCSplChangeHistroyInfo fschc = null;
			try {
				  fschc =	FDCSplChangeHistroyFactory.getRemoteInstance().getFDCSplChangeHistroyCollection(view).get(0);
				  
			} catch (BOSException e1) {
 
			}
		//当被引用的时候用
		try {
			if(fschc != null)
			{
                /*判断单据是否在工作流中*/
				ProcessInstInfo instInfo = null;
				ProcessInstInfo[] procInsts = null;
				try {
					IEnactmentService service2 = EnactmentServiceFactory
							.createRemoteEnactService();
					procInsts = service2.getProcessInstanceByHoldedObjectId(fschc.getId().toString());
				} catch (BOSException e) {
					ExceptionHandler.handle(e);
				}

				for (int i = 0, n = procInsts.length; i < n; i++) {
					if ("open.running".equals(procInsts[i].getState())
							|| "open.not_running.suspended".equals(procInsts[i]
									.getState())) {
						instInfo = procInsts[i];
					}
				}
				if (instInfo != null) {
					MsgBox.showWarning( getResource("supplierInfoWorkFlow")) ;
					SysUtil.abort();
				}
        		int yes=FDCMsgBox.showConfirm2New(this, getResource("deleteInfo"));
    			if(yes > 0){
    				SysUtil.abort();
    			}
    			/*设置原版本供应商为最新状态*/
    			supplier.setIsLatestVer(true);
    			/*调用远程接口修改原版本供应商*/
    			SupplierStockFactory.getRemoteInstance().update(new ObjectUuidPK(supplier.getId().toString()), supplier) ;
    			/*将当前变更版本供应商删除*/
    			SupplierStockInfo srcinfo =getSrcSupplierStockInfo(supplier.getId().toString());
    			if(srcinfo != null){
    	        	IObjectPK pk = new ObjectUuidPK(srcinfo.getId());
    	        	SupplierStockFactory.getRemoteInstance().delete(pk);
    			}
			}
		} catch (EASBizException e) {
			handleException(e);
			logger.error(e);
		} catch (BOSException e) {
			handleException(e);
		}
		}
	}
	/**
	 * 界面调用的list页面，这个参数会传入到供应商编辑界面
	 */
	private Object owner = null;
	public void onLoad() throws Exception { 
		changIsState();
		super.onLoad();	
		
		owner =(Object)getUIContext().get("Owner");
		SupplierStockInfo supplier = (SupplierStockInfo)editData.getSupplier(); 
		if(supplier == null){
			/*
			 * 新增时需要从供应商库页面传入供应商。
			 */
			supplier = (SupplierStockInfo) getUIContext().get("SupplierInfo");
			editData.setSupplier(supplier);
		}
		if(null == editData.getCreator()){
			/*
			 * 对创建时间和创建人赋值
			 */
		    editData.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		    Date now = new Date();
			Timestamp item = new Timestamp(now.getTime());
			editData.setCreateTime(item);
		}
        if(getOprtState().equals(OprtState.ADDNEW)){
		  initPanel(supplier);
        }else{
        	/*
        	 * 非新增时需要将供应商变更版本查询出来
        	 */
        	SupplierStockInfo nowSupplier = getSrcSupplierStockInfo(supplier.getId().toString());
        	verseion = nowSupplier.getVersion().longValue();
        	initPanel(nowSupplier);
        	 
        }
 

		
	}
  /**
 * @description		
 * @author			陈伟		
 * @createDate		2010-12-3
 * @param	
 * @return					
 *	
 * @version			EAS1.0
 * @see						
 */
public void onShow() throws Exception { 
	super.onShow();
	inibtn();
	setAuditBtn();
}
      /**
	 * @description		初始化按钮
	 * @author			陈伟		
	 * @createDate		2010-11-28
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private void inibtn() {
		this.btnAudit.setVisible(true);
		this.btnUnAudit.setVisible(true);
		this.btnAudit.setEnabled(true);
		this.btnUnAudit.setEnabled(true);
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);
		this.menuItemAudit.setVisible(true);
		this.menuItemUnAudit.setVisible(true);
		this.btnAuditResult.setVisible(false);
		this.menuItemAuditResult.setVisible(false);

	}
	
 
	/**
	 * @description		信息变更查看事件，只查看本次变更信息。
	 * @author			陈伟		
	 * @createDate		2010-12-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	public void actionEditInfo_actionPerformed(ActionEvent e) throws Exception {

        UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("FDCSplChangeHistroyInfo", editData);		
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL); 
 		IUIWindow uiWindow = uiFactory.create(SupplierStockInfoFullUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
    /**
     * 反审批回写
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(editData.getId()==null){
    		abort();
    	}
    	super.actionUnAudit_actionPerformed(e);   
    	
    	setOprtState(OprtState.EDIT); 
        plUI.storeFields();
        this.supplierInfo.removeAll();
        initPanel((SupplierStockInfo) plUI.getEditData() );
    	setAuditBtn();
    	this.actionEdit.setEnabled(true);   	
       
    }
 
	/**
	 * @description		初始化时候装载供应商界面
	 * @author			陈伟		
	 * @createDate		2010-12-9
	 * @param supplier
	 * @throws UIException void
	 * @version			EAS1.0
	 * @see						
	 */
	private void initPanel(SupplierStockInfo supplier) throws UIException {
	    UIContext uiContext = new UIContext(ui);
		uiContext.put(UIContext.ID, supplier.getId()); 
		uiContext.put("editData", supplier); 
		uiContext.put("changeNo", "1"); 
		uiContext.put("Owner", owner);
		String oprtState = OprtState.EDIT;
		if(getOprtState().equals("FINDVIEW") || getOprtState().equals("VIEW")){
			oprtState =OprtState.VIEW;
		}
		plUI = (SupplierStockEditUI) UIFactoryHelper.initUIObject(SupplierStockEditUI.class.getName(), uiContext,
				null, oprtState);	
		
		supplierInfo.add(plUI,BorderLayout.CENTER);  
		plUI.setUserObject(supplier);
		
		

	}
	public void loadFields() { 
		
		super.loadFields();

	}
	
    public boolean destroyWindow() {
      	 boolean b = super.destroyWindow();
      	 Object ui = null ;
      	 ui = getUIContext().get("Owner");
      	 if( b && null !=ui && ui instanceof SupplierStockListUI){
      		 SupplierStockListUI newUI = (SupplierStockListUI)ui;
          	 try {
   				newUI.getTblMain();
   			} catch (Exception e) {
   				handleException(e);
   			}
           }
           return b;
      }

	/**
	 * @description		绑定数据
	 * @author			陈伟		
	 * @createDate		2010-12-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */

	public SelectorItemCollection getSelectors() {
		 
	       SelectorItemCollection sic = new SelectorItemCollection();
	       sic.add(new SelectorItemInfo("*"));
	       sic.add(new SelectorItemInfo("creator.*"));
	       sic.add(new SelectorItemInfo("supplier.*"));
	       sic.add(new SelectorItemInfo("supplier.supplierType.*"));
	       sic.add(new SelectorItemInfo("supplier.city.*"));
	       sic.add(new SelectorItemInfo("supplier.linkPerson.*"));
	       sic.add(new SelectorItemInfo("supplier.yearSale.*"));
	       sic.add(new SelectorItemInfo("supplier.aptitudeFile.*"));
	       sic.add(new SelectorItemInfo("supplier.province.*"));
		return sic;
	}

}