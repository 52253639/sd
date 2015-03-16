/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.supplier.AppraisePhaseEnum;
import com.kingdee.eas.fdc.invite.supplier.AppraiseTypeEnum;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexGroupInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizNewFlowEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;


/**
 * @(#)			SupplierAppraiseTemplateEditUI.java				
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		评审模板编辑界面
 *		
 * @author		
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see						
 */
public class SupplierAppraiseTemplateEditUI extends AbstractSupplierAppraiseTemplateEditUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8937159002901505691L;

	private static final Logger logger = CoreUIObject.getLogger(SupplierAppraiseTemplateEditUI.class);
    /*
     * 评审维度
     */
    private final String TYPE_COL = "guideType";
    /*
     * 评审指标
     */
    private final String NAME_COL = "guideName";
    /*
     * 权重
     */
    private final String WEIGHT_COL = "weight";
    /*
     * 满分标准
     */
    private final String DESC_COL = "fullNum";
    /*
     * 状态序列号
     */
    private int state = 0;
    
	private KDBizPromptBox F7NameCol = new KDBizPromptBox();
    
    /**
     * output class constructor
     */
    public SupplierAppraiseTemplateEditUI() throws Exception
    {
        super();
    }
	 /**
	     * @description		获取资源文件
	     * @author			
	     * @createDate		2010-12-9
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
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {	
    	
    	if(editData.getState().equals(FDCBillStateEnum.AUDITTED)){
    		FDCMsgBox.showWarning(this,getSupplierResources("auditedNotEdit"));
    		SysUtil.abort();
    	}
    	deleteTrim();
        super.actionSave_actionPerformed(e);
        setColumMerger();
    }
     /**
         * @description		去空格
         * @author			胥凯	
         * @createDate		2010-12-6
         * @param	
         * @return					
         *	
         * @version			EAS1.0
         * @see						
         */
    private void deleteTrim(){
    	if(!("".equals(txtNumber.getText().trim())) || null !=txtNumber.getText()){
    		txtNumber.setText(txtNumber.getText().trim());
    	}
    	if(!("".equals(txtName.getText().trim())) || null !=txtName.getText()){
    		txtName.setText(txtName.getText().trim());
    	}
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	BigDecimal big = getNumWeight();
		if(big.compareTo(new BigDecimal("100")) >0){
    		FDCMsgBox.showWarning(this, getSupplierResources("weight")+getSupplierResources("onTotal")+getSupplierResources("moreHundred"));
    		SysUtil.abort();
    	}
        super.actionSubmit_actionPerformed(e);
        if(state == 1){
        	editData = (SupplierAppraiseTemplateInfo) createNewData();
			loadFields();
			this.txtName.setText(null);
			this.txtNumber.setText(null);
			this.txtRemark.setText(null);
//			this.comboPhase.setSelectedIndex(-1);
			this.kdtGuideEnty.removeRows();
			this.btnSave.setEnabled(true);
			this.setOprtState(OprtState.ADDNEW);
        }
        setColumMerger();
        logger.info(e.getSource());
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
        initEntyStyle();
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
        super.actionAttachment_actionPerformed(e);
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
        setFootRowSum();
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
        setFootRowSum();
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
    	checkSelected();
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
        super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output actionAttamentCtrl_actionPerformed
     */
    public void actionAttamentCtrl_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttamentCtrl_actionPerformed(e);
    }
    
    public void onLoad() throws Exception {

    	kdtGuideEnty.checkParsed();
    	if(getOprtState().equals(STATUS_ADDNEW)){
    		state = 1;
		}
    	/*
    	 * 初始化分录样式
    	 */
    	initEntyStyle();
    	initEditStyle();
    	super.onLoad();
    	initGuideNameEntry();
    	if(!getOprtState().equals(OprtState.ADDNEW)){
    		setFootRowSum();
    	}
    	setColumMerger();
    	initBtn();

    }
    private void initBtn(){
    	contEntryGuide.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
    	FDCTableHelper.disableDelete(this.kdtGuideEnty);
    	FDCTableHelper.setColumnLocked(kdtGuideEnty, 0, true);
    	this.btnUnAudit.setVisible(false);
    	this.setUITitle(getSupplierResources("supplier")+getSupplierResources("evaluateModel"));
    	this.btnAuditResult.setVisible(false);
    	this.btnAttachment.setVisible(false);
    	if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
    		this.btnSave.setEnabled(false);
    	}
    }
    
    public void loadFields() {
    	kdtGuideEnty.checkParsed();
    	this.setDataObject(editData);
    	super.loadFields();
    	//设置评审指标table
    }
     /**
         * @description		初始化分录样式
         * @author				
         * @createDate		2010-11-26
         * @param	
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    private void initEntyStyle(){
    	this.contEntryGuide.removeAllButton();
    	kdtGuideEnty.getColumn(DESC_COL).getStyleAttributes().setLocked(true);
    	kdtGuideEnty.getColumn("guideDescription").getStyleAttributes().setLocked(true);
		KDWorkButton btnAddLineGuide = new KDWorkButton();
		KDWorkButton btnInsertLineGuide = new KDWorkButton();
		KDWorkButton btnDelLineGuide = new KDWorkButton();
		btnAddLineGuide.setName("btnAddLineGuide");
		btnAddLineGuide.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnAddLineGuide.setText(getSupplierResources("addNew")+getSupplierResources("distract"));
		btnAddLineGuide.setSize(22, 19);
		btnAddLineGuide.setEnabled(true);
		btnAddLineGuide.setVisible(true);
		/*
		 * 按钮事件绑定
		 */
		btnAddLineGuide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addLine(kdtGuideEnty);
			}
		});
		btnInsertLineGuide.setName("btnInsertLineGuide");
		btnInsertLineGuide.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnInsertLineGuide.setText(getSupplierResources("insert")+getSupplierResources("distract"));
		btnInsertLineGuide.setSize(22, 19);
		btnInsertLineGuide.setEnabled(true);
		btnInsertLineGuide.setVisible(true);
		/*
		 * 按钮事件绑定
		 */
		btnInsertLineGuide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertLine(kdtGuideEnty);
			}
		});
		btnDelLineGuide.setName("btnDelLineGuide");
		btnDelLineGuide.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnDelLineGuide.setText(getSupplierResources("delete")+getSupplierResources("distract"));
		btnDelLineGuide.setSize(22, 19);
		btnDelLineGuide.setEnabled(true);
		btnDelLineGuide.setVisible(true);
		/*
		 * 按钮事件绑定
		 */
		btnDelLineGuide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeLine(kdtGuideEnty);
				setFootRowSum();
			}
		});
		if(getOprtState().equals(OprtState.VIEW)){
			btnAddLineGuide.setEnabled(false);
			btnInsertLineGuide.setEnabled(false);
			btnDelLineGuide.setEnabled(false);
		}
    	this.contEntryGuide.addButton(btnAddLineGuide);
		this.contEntryGuide.addButton(btnInsertLineGuide);
		this.contEntryGuide.addButton(btnDelLineGuide);
		this.kdtGuideEnty.getColumn(WEIGHT_COL).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtGuideEnty.getColumn(TYPE_COL).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		
		KDComboBox combo = new KDComboBox();
		Iterator iterator = AppraiseTypeEnum.iterator();
		while (iterator.hasNext()) {
			combo.addItem(iterator.next());
		}
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
		this.kdtGuideEnty.getColumn("appraiseType").setEditor(comboEditor);
		
    }
     /**
         * @description		初始化控件样式
         * @author				
         * @createDate		2010-11-26
         * @param	
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    private void initEditStyle(){
		this.contCreator.setEnabled(false);
		this.contCreateTime.setEnabled(false);
		this.contAuditor.setEnabled(false);
		this.contAuditTime.setEnabled(false);
		this.contAppraiseType.setEnabled(false);
		this.btnAudit.setVisible(false);
		this.prmtCreator.setDisplayFormat("$name$");
		this.prmtAuditor.setDisplayFormat("$name$");
    }
    /**
     * 获取与表格绑定的对象信息
     */
    protected IObjectValue createNewDetailData(KDTable table) {
    	SupplierGuideEntryInfo sgEntry =new SupplierGuideEntryInfo();
    	sgEntry.setAppraiseType(AppraiseTypeEnum.WEIGHT);
    	sgEntry.setWeight(FDCHelper.ZERO);
    	return sgEntry;
    }
    
	protected void attachListeners() {
	}

	protected void detachListeners() {
		
	}
	/**父类方法，必须实现*/
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierAppraiseTemplateFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() { 
		return this.kdtGuideEnty;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	/**
	 * 初始化数据状态
	 */
	protected IObjectValue createNewData() {
		SupplierAppraiseTemplateInfo tempInfo = new SupplierAppraiseTemplateInfo();
		SupplierAppraiseTypeInfo type = (SupplierAppraiseTypeInfo)(getUIContext().get("APPRAISETYPE"));
		tempInfo.setAppraiseType(type);
		tempInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		Date createDate = new Date();
		tempInfo.setCreateTime(new Timestamp(createDate.getTime()));
		tempInfo.setState(FDCBillStateEnum.SAVED);
		return tempInfo;
	}
	 /**
	     * @description		初始化分录并给评审指标添加F7
	     * @author			
	     * @createDate		2010-11-26
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private void initGuideNameEntry(){
		F7NameCol.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.FDCSplAuditIndexQuery");
		F7NameCol.setDisplayFormat("$name$");
		F7NameCol.setEditFormat("$number$");
		F7NameCol.setCommitFormat("$number$");
		F7NameCol.setEditable(false);
		SelectorItemCollection selectors = new SelectorItemCollection();		//手动获取关联属性值
		selectors.add("*");
		selectors.add("indexGroup.name");
		EntityViewInfo evInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		evInfo.setFilter(filter);
		F7NameCol.setSelectorCollection(selectors);
		F7NameCol.setEntityViewInfo(evInfo);;
		/*
		 * F7数据改变事件
		 */
		F7NameCol.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				setTypeColValue(eventObj);
			}
		});
		KDTDefaultCellEditor F7GuideName = new KDTDefaultCellEditor(F7NameCol);
		IColumn guideTypeCol = this.kdtGuideEnty.getColumn(TYPE_COL);
		IColumn guideNameCol = this.kdtGuideEnty.getColumn(NAME_COL);
		guideNameCol.setEditor(F7GuideName);
		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtWeight.setPrecision(2);
		txtWeight.setMaximumValue(FDCHelper.ONE_HUNDRED);
		txtWeight.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		kdtGuideEnty.getColumn(WEIGHT_COL).setEditor(weight);
		guideTypeCol.getStyleAttributes().setLocked(true);
		kdtGuideEnty.getColumn(WEIGHT_COL).getStyleAttributes().setNumberFormat("#0.00");
		/*
		 * 设置合计行
		 */
		KDTFootManager footRowManager= kdtGuideEnty.getFootManager();
		IRow footRow = null;
		if(footRowManager==null){
			footRowManager = new KDTFootManager(kdtGuideEnty);
			footRowManager.addFootView();
			kdtGuideEnty.setFootManager(footRowManager);
			footRow= footRowManager.addFootRow(0);
			footRow.setUserObject("FDC_PARAM_TOTALCOST");
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			kdtGuideEnty.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			kdtGuideEnty.getIndexColumn().setWidth(60);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			/*
			 * 设置到第一个可视行
			 */
			footRowManager.addIndexText(0, getSupplierResources("sum"));
		}else{
			footRow=kdtGuideEnty.getFootRow(0);
			if(footRow.getUserObject()==null||!footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")){
				footRow=kdtGuideEnty.addFootRow(1);
			}
		}
	}
	
	
	 /**
	     * @description		根据Id查出FDCSplAuditIndexInfo
	     * @author			
	     * @createDate		2010-11-26
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	protected FDCSplAuditIndexInfo getSelectedInfo(String id) throws EASBizException, BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));	  
        view.getSelector().add(new SelectorItemInfo("indexGroup.*"));   
		FilterInfo filter = new FilterInfo();
		 filter.getFilterItems().add(new FilterItemInfo("id", id));
	    view.setFilter(filter);
	    FDCSplAuditIndexCollection facoll = FDCSplAuditIndexFactory.getRemoteInstance().getFDCSplAuditIndexCollection(view);
	    if(facoll.size()>0){
	    	return facoll.get(0);
	    }
        return null;
 
	}
	 /**
	     * @description		F7数据改变时候想其他单元格自动填充数据
	     * @author			
	     * @createDate		2010-11-26
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	protected void setTypeColValue(DataChangeEvent e)
    {
    	int index = this.kdtGuideEnty.getSelectManager().getActiveRowIndex();
    	IRow row = this.kdtGuideEnty.getRow(index);
    	if(e.getNewValue() != null)
    	{
    		FDCSplAuditIndexInfo auditIndexInfo = (FDCSplAuditIndexInfo)(e.getNewValue());
    		if(null == auditIndexInfo.getIndexGroup().getName()){
					try {
						auditIndexInfo=getSelectedInfo(auditIndexInfo.getId().toString());
					} catch (Exception e1) {
						handUIException(e1);
					}
    		}
    		for(int i = 0; i < kdtGuideEnty.getRowCount(); ++i)
    		{
    			if(i != index)
    			{
    				IRow tmpRow = kdtGuideEnty.getRow(i);
    				if(tmpRow.getCell(NAME_COL).getValue() != null)
    				{
    					if(tmpRow.getCell(NAME_COL).getValue() instanceof FDCSplAuditIndexInfo)
    					{
    						FDCSplAuditIndexInfo tmpLineInfo = (FDCSplAuditIndexInfo)(tmpRow.getCell(NAME_COL).getValue());
    						if(auditIndexInfo.getId().equals(tmpLineInfo.getId()))
    						{
    							Integer indexInt = new Integer(index+1);
    							Integer iInt = new Integer(i+1);
    							StringBuffer buffer = new StringBuffer(getSupplierResources("next") + indexInt.toString());
    							buffer.append(getSupplierResources("rowAdd"));
    							buffer.append(getSupplierResources("next"));
    							buffer.append(iInt.toString() +getSupplierResources("notSameIndex"));
    							FDCMsgBox.showWarning(this,buffer.toString());
    							kdtGuideEnty.removeRow(index);
    							abort();
    						}
    					}
    				}
    			}
    		}
    		FDCSplAuditIndexGroupInfo groupInfo = auditIndexInfo.getIndexGroup();
    		row.getCell(NAME_COL).setUserObject(auditIndexInfo);
    		row.getCell(TYPE_COL).setValue(groupInfo.getName());
    		row.getCell(DESC_COL).setValue(auditIndexInfo.getFullMarkStandard());
    		row.getCell("guideDescription").setValue(auditIndexInfo.getDescription());
    	}
    	else
    	{
    		row.getCell(TYPE_COL).setValue(null);
    	}
    	
    	
    
    }
	
	/**
	 * @description		插入指标
	 * @author			陈伟		
	 * @createDate		2010-11-20
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void insertGuideEnty(int index,FDCSplAuditIndexInfo  info ) {
		 kdtGuideEnty.checkParsed();
		 IRow row = kdtGuideEnty.getRow(index); 
		 String type = (String)row.getCell(TYPE_COL).getValue();
		 SupplierGuideEntryInfo	guideInfo= new SupplierGuideEntryInfo();
//		 guideInfo.setGuideType((String)kdtGuideEnty.getRow(index).getCell(TYPE_COL).getValue());
		 guideInfo.setSplAuditIndex(info); 
		 guideInfo.setAppraiseType((AppraiseTypeEnum) kdtGuideEnty.getRow(index).getCell("appraiseType").getValue());
//		 guideInfo.setGuideName(info.getName());
//		 guideInfo.setGuideDescription(info.getDescription());
		 guideInfo.setRemark((String)kdtGuideEnty.getRow(index).getCell("remark").getValue());
		 guideInfo.setWeight((BigDecimal)kdtGuideEnty.getRow(index).getCell(WEIGHT_COL).getValue());
//		 guideInfo.setFullNum((String)kdtGuideEnty.getRow(index).getCell(DESC_COL).getValue());
		 
		 kdtGuideEnty.removeRow(index);
		 int longth = kdtGuideEnty.getRowCount();	
		 if(null == type){
			 abort();
		 }
		 int startEnd[] =getStartEnd( -1 , -1 , type, longth); 
         if(startEnd[0]== -1){
        	 row =kdtGuideEnty.addRow(longth); 
        	 row.setUserObject(guideInfo);
//        	 kdtGuideEnty.getRow(longth).getCell(TYPE_COL).setValue(guideInfo.getGuideType());
        	 kdtGuideEnty.getRow(longth).getCell(NAME_COL).setValue(info);
        	 kdtGuideEnty.getRow(longth).getCell(WEIGHT_COL).setValue(guideInfo.getWeight());
//        	 kdtGuideEnty.getRow(longth).getCell(DESC_COL).setValue(guideInfo.getFullNum());  

//        	 kdtGuideEnty.getRow(longth).getCell("guideDescription").setValue(guideInfo.getGuideDescription());
        	 kdtGuideEnty.getRow(longth).getCell("appraiseType").setValue(guideInfo.getAppraiseType());
        	 kdtGuideEnty.getRow(longth).getCell("remark").setValue(guideInfo.getRemark());
    
         }else if(startEnd[1] ==-1){
        	 int rowindex =0;
        	 if(startEnd[0]==index){
        		 rowindex =index;
        	 }else{
        		 rowindex =startEnd[0]+1;
        	 }
        	 row = kdtGuideEnty.addRow(rowindex);
        	 row.setUserObject(guideInfo);
//        	 kdtGuideEnty.getRow(rowindex).getCell(TYPE_COL).setValue(guideInfo.getGuideType());
        	 kdtGuideEnty.getRow(rowindex).getCell(NAME_COL).setValue(info);
        	 kdtGuideEnty.getRow(rowindex).getCell(WEIGHT_COL).setValue(guideInfo.getWeight());
//        	 kdtGuideEnty.getRow(rowindex).getCell(DESC_COL).setValue(guideInfo.getFullNum());  
        	 
//        	 kdtGuideEnty.getRow(rowindex).getCell("guideDescription").setValue(guideInfo.getGuideDescription());
        	 kdtGuideEnty.getRow(rowindex).getCell("appraiseType").setValue(guideInfo.getAppraiseType());
        	 kdtGuideEnty.getRow(rowindex).getCell("remark").setValue(guideInfo.getRemark());
    
         }else if(startEnd[0]<startEnd[1]){
        	 int rowindex =0;
        	 if(startEnd[0]<=index &&  startEnd[1] >= index ){
        		 rowindex =index;
        	 }else{
        		 rowindex =startEnd[1]+1;
        	 }
        	 row = kdtGuideEnty.addRow(rowindex);
        	 row.setUserObject(guideInfo);
//        	 kdtGuideEnty.getRow(rowindex).getCell(TYPE_COL).setValue(guideInfo.getGuideType());
        	 kdtGuideEnty.getRow(rowindex).getCell(NAME_COL).setValue(info);
        	 kdtGuideEnty.getRow(rowindex).getCell(WEIGHT_COL).setValue(guideInfo.getWeight());
//        	 kdtGuideEnty.getRow(rowindex).getCell(DESC_COL).setValue(guideInfo.getFullNum());  
        	 
//        	 kdtGuideEnty.getRow(rowindex).getCell("guideDescription").setValue(guideInfo.getGuideDescription());
        	 kdtGuideEnty.getRow(rowindex).getCell("appraiseType").setValue(guideInfo.getAppraiseType());
        	 kdtGuideEnty.getRow(rowindex).getCell("remark").setValue(guideInfo.getRemark());
        	 
         }       
	}
	 /**
	     * @description		F7数据改变时候当评审维度相同合并单元格
	     * @author			
	     * @createDate		2010-11-26
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	protected void setColumMerger(){
		kdtGuideEnty.checkParsed();
		KDTMergeManager mm =kdtGuideEnty.getMergeManager();
		int longth = kdtGuideEnty.getRowCount();
		Map map = new HashMap();
		IRow row = null;
		for(int i = 0 ; i < longth ;i ++){
			/*
			 * 取得所有的纬度
			 */
			  row = kdtGuideEnty.getRow(i);
			  String type = (String)row.getCell(TYPE_COL).getValue();
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
	}
	
	 /**
	     * @description		
	     * @author			
	     * @createDate		2010-11-26
	     * @param			int int String int 
	     * @return			int []		
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private int[] getStartEnd(int ben ,int end ,String type,int longth) {
		IRow row = null;
		for(int i = 0 ; i < longth ;i++){
			//取得所有的纬度
			  row = kdtGuideEnty.getRow(i);
			  String str = (String)row.getCell(TYPE_COL).getValue();
			  if(type == null || str == null){
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
	     * @description		设置权重之和
	     * @author				
	     * @createDate		2010-11-26
	     * @param	
	     * @return			int		
	     * @修改			胥凯
	     * @version			EAS7.0
	     * @see						
	     */
	private int setFootRowSum()
	{
		kdtGuideEnty.checkParsed();
		int i = 0;
		IRow footRow =  kdtGuideEnty.getFootRow(0);
    	BigDecimal sum =getNumWeight();
    	if(sum.compareTo(new BigDecimal("100")) >0){
    		FDCMsgBox.showWarning(this, getSupplierResources("weight")+getSupplierResources("onTotal")+getSupplierResources("moreHundred"));
    		i=1;
    	}
    	footRow.getCell(WEIGHT_COL).setValue(sum);
    	return i;
	}
	 /**
	     * @description		获得所有权重的和
	     * @author			胥凯	
	     * @createDate		2010-11-26
	     * @param	
	     * @return			BigDecimal	
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private BigDecimal getNumWeight(){
		BigDecimal sum = new BigDecimal(0);
		for(int i= 0; i < kdtGuideEnty.getRowCount(); ++i )
    	{
    		IRow tmpRow = kdtGuideEnty.getRow(i);
    		if(AppraiseTypeEnum.PASS.equals(tmpRow.getCell("appraiseType").getValue())){
    			continue;
    		}
    		if(tmpRow.getCell(WEIGHT_COL).getValue() != null)
    		{
    			BigDecimal tmp = new BigDecimal(tmpRow.getCell(WEIGHT_COL).getValue().toString());
    			sum = sum.add(tmp);
    		}else{
    			tmpRow.getCell(WEIGHT_COL).setValue(new BigDecimal("1.00"));
    			BigDecimal tmp = new BigDecimal(tmpRow.getCell(WEIGHT_COL).getValue().toString());
    			sum = sum.add(tmp);
    		}
    	}
		return sum;
	}
	 /**
	     * @description		判断权重是否合格	
	     * @author			胥凯	
	     * @createDate		2010-11-26
	     * @param			String int
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private void isWeightPoint(String colName,int rowIndex){
		if(colName.equals(WEIGHT_COL))
    	{	
    		if(kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getValue() != null)
    		{
    			BigDecimal tmp = new BigDecimal(kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getValue().toString());	
    			if(tmp.compareTo(FDCHelper.ZERO) < 0)
    			{
    				FDCMsgBox.showWarning(this,getSupplierResources("weight")+getSupplierResources("lessZero"));
    				kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(new BigDecimal(1));
    				abort();
    			}
    			if(tmp.compareTo(new BigDecimal("100") ) > 0)
    			{
    				FDCMsgBox.showWarning(this,getSupplierResources("weight")+getSupplierResources("moreHundred"));
    				kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(new BigDecimal(1));
    				abort();
    			}
    			int i = setFootRowSum();
    			if(i == 1){
    				kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(new BigDecimal(0));
    				setFootRowSum();
    			}
    		}
    	}
	}
 
	 /**
	     * @description		评审指标名称F7改变事件、相同的评审维度合并单元格
	     * @author			刘俊
	     * @createDate		2010-11-26
	     * @param	
	     * @return					
	     * @修改			胥凯
	     * @version			EAS7.0
	     * @see						
	     */
	public void kdtGuideEnty_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e){
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		if(e.getValue() instanceof FDCSplAuditIndexInfo ){
			FDCSplAuditIndexInfo  aInfo =(FDCSplAuditIndexInfo )(e.getValue());
//			insertGuideEnty(rowIndex,aInfo);
		}else if(AppraiseTypeEnum.PASS.equals(e.getValue())){
			this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(true);
			this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue(null);
		}else{
			this.kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getStyleAttributes().setLocked(false);
	    	if(kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).getValue() == null){
	    		kdtGuideEnty.getRow(rowIndex).getCell(WEIGHT_COL).setValue("0.00");
	    	}
		}
		setColumMerger();
    	String colName  = kdtGuideEnty.getColumnKey(colIndex);
    	isWeightPoint(colName,rowIndex);
	}
	 /**
     * @description		过滤条件，判断是否重复
     * @author			胥凯	
     * @createDate		2010-11-18
     * @param			filedName  对应字段
     * @param			filedValue  界面输入的数据
     * @return			存在就返回true 否则false	
     *	
     * @version			EAS7.0
     * @see						
     */
	private boolean isExist(String filedName,String filedValue)throws Exception{
		
		FilterInfo filterInfo=new FilterInfo();
		FilterItemInfo itemInfo=new FilterItemInfo(filedName,filedValue,com.kingdee.bos.metadata.query.util.CompareType.EQUALS);
		filterInfo.getFilterItems().add(itemInfo);
		filterInfo.setMaskString(" #0");
		if(STATUS_EDIT.equals(this.getOprtState())){
			if(editData.getId() == null){
				return false;
			}
			FilterItemInfo itemInfo_2=new FilterItemInfo("id",editData.getId().toString(),com.kingdee.bos.metadata.query.util.CompareType.NOTEQUALS);
			filterInfo.getFilterItems().add(itemInfo_2);
			filterInfo.setMaskString("#0 and #1");
		}
		boolean flag=SupplierAppraiseTemplateFactory.getRemoteInstance().exists(filterInfo);
		return flag;
	}
	
	
	 /**
	     * @description		保存验证
	     * @author			胥凯	
	     * @createDate		2010-12-1
	     * @param	
	     * @return					
	     *	
	     * @version			EAS1.0
	     * @see						
	     */
	protected void verifyInputForSave() throws Exception {
		validateInPut();
	}
	 /**
	     * @description		提交验证
	     * @author			胥凯	
	     * @createDate		2010-12-1
	     * @param	
	     * @return					
	     *	
	     * @version			EAS1.0
	     * @see						
	     */
	protected void verifyInputForSubmint() throws Exception {
		
		validateInPut();
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
	     * @description		输入验证
	     * @author			胥凯	
	     * @createDate		2010-12-2
	     * @param	
	     * @return					
	     *	
	     * @version			EAS1.0
	     * @see						
	     */
	private void validateInPut() throws Exception{
//		editData.setPhase((AppraisePhaseEnum)comboPhase.getSelectedItem());
//		editData.setName(txtName.getText());
//		editData.setNumber(txtNumber.getText());
//		if(editData.getNumber()==null||editData.getNumber().trim().length()==0){
//			FDCMsgBox.showWarning(this,getSupplierResources("modelNumber")+getSupplierResources("notNull"));
//			SysUtil.abort();
//		}
//
//		if(editData.getPhase()==null){
//			FDCMsgBox.showWarning(this,getSupplierResources("syndicPhase")+getSupplierResources("notNull"));
//			SysUtil.abort();
//		}
//		if(editData.getName()==null||editData.getName().trim().length()==0){
//			FDCMsgBox.showWarning(this,getSupplierResources("model")+getSupplierResources("name")+getSupplierResources("notNull"));
//			SysUtil.abort();
//		}
//		if(isExist("name",editData.getName())==true){
//			MsgBox.showInfo(this,getSupplierResources("model")+getSupplierResources("name")+editData.getName()+getSupplierResources("againInput"));
//    		SysUtil.abort();
//    		this.txtName.setFocusable(true);
//		}
//		if(isExist("number",editData.getNumber())==true){
//			MsgBox.showInfo(this,getSupplierResources("modelNumber")+editData.getNumber()+getSupplierResources("againInput"));
//    		SysUtil.abort();
//    		this.txtNumber.setFocusable(true);
//		}
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtAppraiseType);
		
		kdtGuideEnty.checkParsed();
		int count = kdtGuideEnty.getRowCount();
		if(count <0){
			return;
		}else{
			for(int i =0;i<count;i++){
				if("".equals(kdtGuideEnty.getRow(i).getCell(NAME_COL).getValue()) || null == kdtGuideEnty.getRow(i).getCell(NAME_COL).getValue()){
					FDCMsgBox.showWarning(this, getSupplierResources("index")+getSupplierResources("notNull"));
					SysUtil.abort();
				}
			}
		}
		BigDecimal big = getNumWeight();
		if(big.compareTo(new BigDecimal("100")) >0){
    		FDCMsgBox.showWarning(this, getSupplierResources("weight")+getSupplierResources("onTotal")+getSupplierResources("moreHundred"));
    		SysUtil.abort();
    		
    	}
	}
	
	/**
	 * @description		
	 * @author			陈伟		
	 * @createDate		2010-11-21
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("guideEntry.*"));
		sic.add(new SelectorItemInfo("guideEntry.splAuditIndex.*"));
		return sic;
	}
}