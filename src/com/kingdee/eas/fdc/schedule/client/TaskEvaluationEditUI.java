/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.schedule.TaskEvaluationCollection;
import com.kingdee.eas.fdc.schedule.TaskEvaluationFactory;
import com.kingdee.eas.fdc.schedule.TaskEvaluationInfo;
import com.kingdee.eas.fdc.schedule.TaskEvaluationTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * @(#)						
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		任务评价设置编辑界面
 *		
 * @author		周航健
 * @version		EAS7.0		
 * @createDate	2011-08-08 
 * @see
 */
public class TaskEvaluationEditUI extends AbstractTaskEvaluationEditUI
{
    /**
	 * serialnumber
	 */
	private static final long serialVersionUID = -2570691983226007630L;
	private static final Logger logger = CoreUIObject.getLogger(TaskEvaluationEditUI.class);
	/*
	 * 编辑时编码初始值
	 */
	private String tempNumber= "";
    
    protected FDCDataBaseInfo getEditData() {
		return new TaskEvaluationInfo();
	}

	protected KDBizMultiLangBox getNameCtrl() {
		
		return this.txtEvaluationResult;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected IObjectValue createNewData() {
		return new TaskEvaluationInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return TaskEvaluationFactory.getRemoteInstance();
	}
	
	/**
	 * 
	 * 		 	
	 * 描述：		初始化ListUI界面按钮
	 *		
	 * @author		周航健
	 * @version		EAS7.0		
	 * @createDate	2011-8-8 
	 *
	 */
	protected void initWorkButton() {

		this.btnReset.setVisible(false);
		this.btnSave.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnPageSetup.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		
		super.initWorkButton();
	}
	
	/**
	 * @description		数据保存后,无需验证数据是否被修改确认保存
	 * @author			周航健	
	 * @createDate		2011-08-09
	 * @param			
	 * @return			boolean
	 * 	
	 * @version			EAS7.0
	 */
	public boolean isModify() {
		return false;
	}
	
	/**
	 * @description		数据验证
	 * @author			周航健	
	 * @createDate		2011-08-09
	 * @param			
	 * @return			boolean
	 * 	
	 * @version			EAS7.0
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		
		if(null == editData.getEvaluationResult() || editData.getEvaluationResult().trim().length()<=0)
    	{
    		MsgBox.showWarning("评价结果不允许为空!");
    		SysUtil.abort();
    		
    	}
		if(null == editData.getNumber() || editData.getNumber().trim().length()<=0)
    	{
    		MsgBox.showWarning("编码不允许为空!");
    		SysUtil.abort();
    		
    	}
		Pattern tDouble = Pattern.compile("([0-9]{1,4})");
		if (!tDouble.matcher(editData.getNumber().trim()).matches()) {
			MsgBox.showWarning("编码只能为0-9的数字!");
    		SysUtil.abort();
		}
		if(!getOprtState().equals(OprtState.EDIT)){
			if(checkNumber()){
				MsgBox.showWarning("编码已经存在!");
	    		SysUtil.abort();
			}
		}
		else
		{
			if(!(tempNumber.trim().equals(this.txtNumber.getText().trim())))
			{
				if(checkNumber()){
					MsgBox.showWarning("编码已经存在!");
		    		SysUtil.abort();
				}
			}
		}
		
//		if(isExist("number",editData.getNumber().trim())==true){
//    		MsgBox.showWarning("编码重复,请重新输入!");
//    		SysUtil.abort();
//    	}
		
		
	}
	
	private boolean checkNumber() throws EASBizException {
		String type = "";
		String number = this.editData.getNumber().trim();
		boolean flag = false ;
		if( null != getUIContext().get("pane")){
			
			UIContext ctxt = new UIContext();
			/*
			 * 取枚举对象
			 */
			TaskEvaluationTypeEnum taskEvaluationTypeEnum=(TaskEvaluationTypeEnum)getUIContext().get("pane");
			/*
			 * 如若不是进度评价
			 * 
			 */
			if(taskEvaluationTypeEnum != TaskEvaluationTypeEnum.SCHEDULE)
			{
				type = TaskEvaluationTypeEnum.QUALITY_VALUE;
			}
			else
			{
				type = TaskEvaluationTypeEnum.SCHEDULE_VALUE;
			}
		}
		try {
//			flag = TaskEvaluationFactory.getRemoteInstance().checkNumberDup(type, number);
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("evaluationType", type);
			filter.appendFilterItem("number", number);
			view.setFilter(filter);
			TaskEvaluationCollection taskEvaluationCollection = TaskEvaluationFactory.getRemoteInstance().getTaskEvaluationCollection(view);
			if(null != taskEvaluationCollection && taskEvaluationCollection.size() > 0){
				flag = true;
			}
		} catch (BOSException e) {
			logger.info(e.getMessage());
		}
		return flag;
	}
	
	/**
	 * @description		过滤条件
	 * @author			周航健	
	 * @createDate		2011-08-09
	 * @param			FiledName,filedValue
	 * @return			boolean
	 * 	
	 * @version			EAS7.0
	 */
    private boolean isExist(String FiledName,String filedValue)throws Exception{
    	FilterInfo filterInfo=new FilterInfo();
	    FilterItemInfo itemInfo=new FilterItemInfo(FiledName,filedValue,com.kingdee.bos.metadata.query.util.CompareType.EQUALS);
	    
	    filterInfo.getFilterItems().add(itemInfo);
	    filterInfo.setMaskString(" #0");
	    if(STATUS_EDIT.equals(this.getOprtState())){
	    	FilterItemInfo itemInfo_2=new FilterItemInfo("id",editData.getId().toString(),com.kingdee.bos.metadata.query.util.CompareType.NOTEQUALS);
	    	filterInfo.getFilterItems().add(itemInfo_2);
	    	filterInfo.setMaskString("#0 and #1");
	    }
	    boolean flag=TaskEvaluationFactory.getRemoteInstance().exists(filterInfo);
	    return flag;
    }
    
    /**
	 * @description		初始化数据对象
	 * @author			周航健	
	 * @createDate		2011-08-09
	 * @param			FiledName,filedValue
	 * @return			boolean
	 * 	
	 * @version			EAS7.0
	 */
    private void initData() {
		if( null != getUIContext().get("pane")){
			/*
			 * 取枚举对象
			 */
			TaskEvaluationTypeEnum taskEvaluationTypeEnum=(TaskEvaluationTypeEnum)getUIContext().get("pane");
			
			this.editData.setEvaluationType(taskEvaluationTypeEnum);
			/*
			 * 如若不是进度评价隐藏控件
			 * 否则显示进度完成评价控件
			 */
			if(taskEvaluationTypeEnum != TaskEvaluationTypeEnum.SCHEDULE)
			{
				this.kDLabelIsNotPass.setVisible(false);
				this.kDEvaluationPass.setVisible(false);
				this.kDEvaluationNotPass.setVisible(false);
			}
			else
			{
				this.kDLabelIsNotPass.setVisible(true);
				this.kDEvaluationPass.setVisible(true);
				this.kDEvaluationNotPass.setVisible(true);
				if(this.kDEvaluationPass.isSelected()){
					this.editData.setEvaluationPass(true);
				}else{
					this.editData.setEvaluationPass(false);
				}
			}
			
		}
		
		this.editData.setName(BOSUuid.create((new TaskEvaluationInfo()).getBOSType()).toString());
		
		if (getOprtState().equals(OprtState.ADDNEW))
		{
			this.kDEvaluationPass.setSelected(true); 
		}else{
			this.kDEvaluationPass.setSelected(false);
		}
		
		/*
		 *根据当前UI状态设置按钮状态是否可用 
		 */
		if (getOprtState().equals(OprtState.VIEW))
		{
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.kDEvaluationNotPass.setEnabled(false);
			this.kDEvaluationPass.setEnabled(false);
			
		}
		else{
			this.btnAddNew.setEnabled(true);
//			this.btnEdit.setEnabled(true);
		}
	}
    
    /**
     * 
     */
    public boolean destroyWindow() {
    	boolean destroyWindow = super.destroyWindow();
    	TaskEvaluationListUI listUI = (TaskEvaluationListUI) getUIContext().get("Owner");
    	listUI.refresh();
		return destroyWindow;
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	initData();
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    	this.btnCancelCancel.setVisible(false);
    }
    /**
     * output class constructor
     */
    public TaskEvaluationEditUI() throws Exception
    {
        super();
    }

    /**
	 * @description		设置数据值
	 * @author			周航健	
	 * @createDate		2011-08-09
	 * @param			FiledName,filedValue
	 * @return			
	 * 	
	 * @version			EAS7.0
	 */
    public void storeFields()
    {
    	initData();
        super.storeFields();
    }
    
    /**
	 * @description		取修改数据值进行重复验证
	 * @author			周航健	
	 * @createDate		2011-08-09
	 * @param			
	 * @return			
	 * 	
	 * @version			EAS7.0
	 */
    public void loadFields() {
    	super.loadFields();
    	tempNumber = this.txtNumber.getText();
    	try {
			TaskEvaluationInfo taskEvaluationInfo = TaskEvaluationFactory.getRemoteInstance().getTaskEvaluationInfo(new ObjectUuidPK(editData.getId()));
			boolean falg = taskEvaluationInfo.isEvaluationPass();
			if(falg){
				this.kDEvaluationPass.setSelected(true);
			}
			else
			{
				this.kDEvaluationNotPass.setSelected(true);
			}
		} catch (EASBizException e) {
			logger.error(e.getMessage());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
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
	 * @description		手动刷新
	 * @author			周航健	
	 * @createDate		2011-09-01
	 * @param			
	 * @return			
	 * 	
	 * @version			EAS7.0
	 */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	
        super.actionSave_actionPerformed(e);
    	TaskEvaluationListUI listUI = (TaskEvaluationListUI) getUIContext().get("Owner");
    	listUI.refresh();
    }

    /**
	 * @description		手动刷新
	 * @author			周航健	
	 * @createDate		2011-09-01
	 * @param			
	 * @return			
	 * 	
	 * @version			EAS7.0
	 */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    	TaskEvaluationListUI listUI = (TaskEvaluationListUI) getUIContext().get("Owner");
    	listUI.refresh();
    }
     //保存成功提示
    protected void showSubmitSuccess() {
    	// TODO Auto-generated method stub
    	//super.showSubmitSuccess();
    	setMessageText("任务评价设置保存成功！");
    	setShowMessagePolicy(0);
    	setIsShowTextOnly(false);
    	showMessage();
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
    	logger.getName();
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
    	initData();
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
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


}