/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;

import org.apache.jackrabbit.uuid.UUID;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
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
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.client.CSClientUtils;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.invite.supplier.YearSaleCollection;
import com.kingdee.eas.fdc.invite.supplier.YearSaleInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		供应商编辑
 *		
 * @author		杜余
 * @version		EAS7.0		
 * @createDate	2010-11-5	 
 * @see
 */
public class SupplierStockEditUI extends AbstractSupplierStockEditUI
	{
		/**   */
	private static final long serialVersionUID = -1098109195050158782L;

		private static final Logger logger = CoreUIObject.getLogger(SupplierStockEditUI.class);
	
	private static FDCSplAreaCollection sc = null;
	/*
	 * 是否清空标志,默认提交清空
	 */
	private boolean isClear = true;
	
	/*
	 * 信息变更参数0为非信息变更打开页面。
	 */
	private int changeNo = 0;
	
	/**
	 * output class constructor
	 */
	public SupplierStockEditUI() throws Exception
	{
	    super();
	}
	 private String getResource(String msg) {
			return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
		}
	
	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
	    super.storeFields();
	    /*
	     * 重新设置多语言控件值,这个界面多语言控件存在缺陷,控件BUG
	     */
	    this.editData.setName(this.txtName.getEditor().getItem().toString());
	    this.txtName.setDefaultLangItemData(this.txtName.getEditor().getItem().toString());
	    this.txtName.setSelectedItem(this.txtName.getEditor().getItem().toString(), true);
	    CtrlUnitInfo info = new CtrlUnitInfo();
	    info = SysContext.getSysContext().getCurrentCtrlUnit();
	    this.editData.setAdminCU(info);
	}
	/**
	 * 
	 * @description		以下都是界面上的新增删除插入分录action
	 * @author			杜余		
	 * @createDate		2010-12-1
	 * @param e
	 * @throws Exception									
	 * @version			EAS7.0
	 * @see com.kingdee.eas.fdc.invite.supplier.client.AbstractSupplierStockEditUI#actionPersonAddLine_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPersonAddLine_actionPerformed(ActionEvent e)
			throws Exception {
		kdTableAddRow(kdtLinkPerson);
	}
	public void actionPersonInsertLine_actionPerformed(ActionEvent e)
		throws Exception {
		kdTableInsertLine(kdtLinkPerson);
	}
	public void actionPersonDeleteLine_actionPerformed(ActionEvent e)
			throws Exception {
		kdTableDeleteRow(kdtLinkPerson);
	}
	public void actionFileAddLine_actionPerformed(ActionEvent e)
			throws Exception {
		kdTableAddRow(kdtAptitudeFile);
	}
	public void actionFileDelLine_actionPerformed(ActionEvent e)
			throws Exception {
		kdTableDeleteRow(kdtAptitudeFile);
	}
	public void actionApproveAddLine_actionPerformed(ActionEvent e)
			throws Exception {
		kdTableAddRow(kdtAchievement);
	}
	public void actionApproveDelLine_actionPerformed(ActionEvent e)
			throws Exception {
		kdTableDeleteRow(kdtAchievement);
	}
	public void actionInsertLine_actionPerformed(ActionEvent e)
		throws Exception {
		kdTableInsertLine(kdtAptitudeFile);
	}
	public void actionInsertLineAppr_actionPerformed(ActionEvent e)
		throws Exception {
		kdTableInsertLine(kdtAchievement);
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
		/*
		 * 设置随机id
		 */
		editData.setNumber(UUID.randomUUID().toString());
		verifyInput();
		storeFields();
	    super.actionSave_actionPerformed(e);
	}
	
	/**
	 * 
	 * @description		此方法为保存,并非提交
	 * @author			杜余		
	 * @createDate		2010-12-1
	 * @param e
	 * @throws Exception									
	 * @version			EAS7.0
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI#actionSubmit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
	{	SupplierStockInfo info = null;
		/*
		 * 如果id不空,取出info,判断状态,处理
		 */
		if(null != editData.getId()){
			  try {
				info = SupplierStockFactory.getRemoteInstance().getSupplierStockInfo(new ObjectUuidPK(editData.getId().toString()));
			 } catch (Exception ex) {
				handUIException(ex);
			 }
			 /*
			  * 这段暂时还不理解当时写的是什么意思,暂时不处理
			  */
			 if((info != null && null != info.getState() && 
					 info.getState().equals(SupplierStateEnum.SAVE))
					 || info.getState().equals(SupplierStateEnum.SUBMIT)){
//				 info.setState(SupplierStateEnum.SUBMIT); 
			 }else{
				 MsgBox.showWarning(this, getResource("notSave"));
				 abort();
			 }
			 /*
			  * 新加   处理提交状态修改后不提交直接点退出保存
			  */
			 if(info != null && null != info.getState() && 
					 info.getState().equals(SupplierStateEnum.SUBMIT)){
				 this.actionSubmitTwo_actionPerformed(e);
				 return;
			 }
	    }
		if( editData.getId()== null){
			editData.setNumber(UUID.randomUUID().toString());
		}else{
			editData.setState(SupplierStateEnum.SAVE);
		}
		editData.setServiceArea(getServiceAreaValue());
		editData.setSrcSupplier(this.editData);
		/*
		 * 保存验证,只做了名称编码验证
		 */
		verifyInputForSave();
		/*
		 * 去界面控件空格
		 */
		trimTextfield();
		storeFields();
		/*
		 * 判断名称是否重复
		 */
		if (isExist("name", txtName.getSelectedItem().toString())) {
    		MsgBox.showInfo(this,getResource("firmName")+txtName.getSelectedItem().toString()+getResource("againInput"));
    		abort();
    	}
		/*
		 * 其实还是调用的暂存来做的保存
		 */
	    super.actionSave_actionPerformed(e);
	}
	/**
	 * 
	 * @description		专职保存验证
	 * @author			杜余		
	 * @createDate		2010-12-1 void
	 */
	private void verifyInputForSave(){
		if(null == txtName.getSelectedItem().toString() || "".equals(txtName.getSelectedItem().toString())){
			MsgBox.showWarning(this ,getResource("companyName"));
			SysUtil.abort();
		}else{
			txtName.setSelectedItem(txtName.getSelectedItem().toString());
		}
		
		if (StringUtils.isEmpty(txtNumber.getText().trim())) {
			MsgBox.showWarning(this ,getResource("number"));
			SysUtil.abort();
		}else{
			txtNumber.setText(txtNumber.getText().trim());
		}
		
		if (!StringUtils.isEmpty(txtBusinessNum.getText().trim())) {
			if (isExist("businessNum", txtBusinessNum.getText().toString().trim())) {
	    		MsgBox.showInfo(this,getResource("license")+txtBusinessNum.getText()+getResource("again"));
	    		abort();
	    	}
		}
		
		if(txtEnterpriseMaster!=null){
			if(txtEnterpriseMaster.getText().toString().equals(txtName.getSelectedItem().toString())){
				String temp = getResource("being")+txtName.getSelectedItem().toString()+getResource("supplierAnd")+txtEnterpriseMaster.getText().toString()+getResource("deputySame");
				int yes = MsgBox.showConfirm2New(this, temp);
				if(yes>0){
					SysUtil.abort();
				}
			}
		}
		kdtAchievement.checkParsed();
		int countsAch = kdtAchievement.getRowCount();
		if(countsAch < 1){
			
		} else {
			for (int i = 0; i < countsAch; i++) {
				if (null != kdtAchievement.getRow(i).getCell("startDate").getValue()&& null != kdtAchievement.getRow(i).getCell("endDate").getValue()) {
					Date from = (Date) kdtAchievement.getRow(i).getCell("startDate").getValue();
					Date to = (Date) kdtAchievement.getRow(i).getCell("endDate").getValue();
					if (from.after(to)) {
						MsgBox.showWarning(this, getResource("compare"));
						SysUtil.abort();
						break;
					}
				}
			}
		}
	}
		
	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
	{
	    
		kdtAptitudeFile.checkParsed();
	    kdtAchievement.checkParsed();
	    /*
	     * 如果为资质文件的附件管理则关联本条基础资料的资质文件的业务附件
	     */
	    if(kDTabbedMain.getSelectedIndex()==1){
	    	initAttachment(kdtAptitudeFile);
	    	initShow(kdtAptitudeFile);
	    }
	    /*
	     * 如果为业绩的附件管理则关联本条基础资料的业绩的业务附件
	     */
	    if(kDTabbedMain.getSelectedIndex()==2){
	    	initAttachment(kdtAchievement);
	    	initShow(kdtAchievement);
	    }
	    /*
	     * 否则关联本条基础资料的业务附件,直接调用super
	     */
	    if(kDTabbedMain.getSelectedIndex()==0){
	    	if(null == editData.getId()){
	    		MsgBox.showWarning(getResource("accessory"));
	    		abort();
	    	}else{
	    		super.actionAttachment_actionPerformed(e);
	    	}
	    }
		
	}
	/**
	 * 
	 * @description		针对两个分录的附件关联
	 * @author			杜余		
	 * @createDate		2010-12-1
	 * @param tableList void
	 */
	private void initAttachment(KDTable tableList) {
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		int actionRow = tableList.getSelectManager().getActiveRowIndex();
		if (actionRow < 0){
			FDCMsgBox.showInfo(this , getResource("pointRow"));
			return;
		}
	    Object IDObject  = tableList.getRow(actionRow).getCell("id").getValue();
	    /*
		 * 是保存状态的分录才能上传附件
		 */
	    if(IDObject == null)
	    {
	    	FDCMsgBox.showInfo(this , getResource("afterAccessory"));
	        return;
	    } 
	    String boID = IDObject.toString();
	    boolean isEdit = false;
	    /*
	     * 传入是否能编辑附件
	     */
	    if(STATUS_ADDNEW.equals(getOprtState())||STATUS_EDIT.equals(getOprtState())){
	    	isEdit = true;
	    }
	    acm.showAttachmentListUIByBoID(boID,this,isEdit);
	}
	/**
	 * 
	 * @description		有附件则分录中附件一列打勾
	 * @author			杜余		
	 * @createDate		2010-12-1
	 * @param tableList
	 * @throws BOSException void
	 */
	private void initShow(KDTable tableList) throws BOSException {
		tableList.checkParsed();
		String boID = "";
		for (int i = 0, size  = tableList.getRowCount(); i < size; i++){
			if (tableList.getRow(i).getCell("id").getValue() == null){
				continue;
			}
			boID  = tableList.getRow(i).getCell("id").getValue().toString();
			SelectorItemCollection itemColl = new SelectorItemCollection();
			itemColl.add(new SelectorItemInfo("id"));
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().addObjectCollection(itemColl);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", boID));
			view.setFilter(filter);
			BoAttchAssoCollection bc = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
			/*
			 * 有关联附件就打上勾勾
			 */
			if(bc.size()>0){
				tableList.getRow(i).getCell("isHaveAttach").setValue(Boolean.TRUE);
			}else{
				tableList.getRow(i).getCell("isHaveAttach").setValue(Boolean.FALSE);
			}
		}
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
	protected void showSaveSuccess() {
		/*
		 * 同下
		 */
	setMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Save_OK"));
    setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
    setShowMessagePolicy(0);
    setIsShowTextOnly(false);
    showMessage();
	
	}
	protected void showSubmitSuccess() {
		/*
		 * 修改下面的提示信息,原先保存下面提示是暂存成功,提交显示是保存成功
		 */
	setMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Submit_OK"));
    if(chkMenuItemSubmitAndAddNew.isSelected())
        setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_AddNew"));
    else
    if(!chkMenuItemSubmitAndPrint.isSelected() && chkMenuItemSubmitAndAddNew.isSelected())
        setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_AddNew"));
    else
        setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
    setIsShowTextOnly(false);
    setShowMessagePolicy(0);
    showMessage();
	}
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-11-5
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected FDCDataBaseInfo getEditData() {
		return editData;
	}
	
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-11-5
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}
	
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-11-5
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-11-5
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected IObjectValue createNewData() {
		/*
		 * 带入录入人与录入时间
		 */
		SupplierStockInfo info = new SupplierStockInfo();
		try {
			info.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		/*
		 * 新增自动带入树上的基本分类
		 */
		if(null!=getUIContext().get("TypeInfo")){
			info.setSupplierType(((CSSPGroupInfo)getUIContext().get("TypeInfo")));
		}
		info.setHasCreatedSupplierBill(false);
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		return info;
	}
	
	
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-11-5
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierStockFactory.getRemoteInstance();
	}
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-11-5
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
	//	super.verifyInput(e);
	}
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-11-8
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		if(getOprtState().equals(OprtState.EDIT)){
			isClear = false;
		}
		initButton();
		initTable();
		initYearSale();
	}
	
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-11-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	
	public void loadFields() {
	    kdtLinkPerson.checkParsed();
	    kdtAptitudeFile.checkParsed();
	    kdtAchievement.checkParsed();
	    kdtYearSale.checkParsed();
	    super.loadFields();
	    initF7ServiceType();
	}
	/**
	 * @description		提交
	 * @author			杜余		
	 * @createDate		2010-11-8
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void actionSubmitTwo_actionPerformed(ActionEvent e) throws Exception {
		/*必须加,多语言控件存在bug,不能正确赋值*/
		storeFields();
		/*检查是否是信息变更提交*/
		if(changeNo > 0){
			IObjectPK pk = null ;
	      	if(null == editData.getId()){
	      		  pk = SupplierStockFactory.getRemoteInstance().addnew(editData);
	      		editData.setId((BOSUuid) pk.getKeyValue("id"));
	      		
	      	}else{
	      		editData.setState(SupplierStateEnum.SUBMIT);
	      		SupplierStockFactory.getRemoteInstance().update(new ObjectUuidPK(editData.getId().toString()), editData);
	      	}
		}
		/*看它能不能来提交*/
		SupplierStockInfo info = null;
		if(null != editData.getId()){
			  try {
				info = SupplierStockFactory.getRemoteInstance().getSupplierStockInfo(new ObjectUuidPK(editData.getId().toString()));
			 } catch (Exception ex) {
				handUIException(ex);
			 } 
			 if((info != null && null != info.getState() && 
					 info.getState().equals(SupplierStateEnum.SAVE))
					 || info.getState().equals(SupplierStateEnum.SUBMIT)){
//				 info.setState(SupplierStateEnum.SUBMIT); 
			 }else{
				 MsgBox.showWarning(this, getResource("notSubmit"));
				 abort();
			 }
	    }else{
	    	
	    }
		/*上下级供应商引用赋值*/
		if(null==editData.getSrcSupplier()){
			editData.setSrcSupplier(this.editData);
		} 
		/*验证*/
		verifyInput();
		/*去空格*/
		trimTextfield();
		/*名称不重复*/
		if(isExist("name",txtName.getSelectedItem().toString()) == true){
    		MsgBox.showInfo(this,getResource("firmName")+txtName.getSelectedItem().toString()+getResource("again"));
    		abort();
    	}
		/*设置服务区域值*/
    	editData.setServiceArea(getServiceAreaValue());
		super.actionSubmit_actionPerformed(e);
		/*
		 * 看是新增直接提交还是修改在提交的,修改提交不清空,新增提交所有清空
		 */
		if(!isClear){
			SupplierStockInfo editDataInfo = 
				SupplierStockFactory.getRemoteInstance().getSupplierStockInfo(new ObjectUuidPK(editData.getId().toString()), getSelectors());
			editData.clear();
		    setDataObject(editDataInfo);
		    loadFields();
		}else{
			/*
			 * 刷新服务区域,为新增时那个
			 */
			reflushArea();
			/*
			 * 提交后清空服务类型F7与近三年销售额分录
			 */
			refreshCompons();
		}
		/*
		 * 判断这个页面是从哪个序时薄点进来的,提交后有可能前一个序时薄又重新布局了页面,又重新合并单元格
		 */
		Object ui = null ;
		 ui = getUIContext().get("Owner");
	     if(null !=ui && ui instanceof SupplierInputListUI){
	    	 SupplierInputListUI newUI = (SupplierInputListUI)ui;
//	    	 newUI.unitTable();
	     }
	     if(!isClear){
	    	 this.btnSubmit.setEnabled(false);
		     this.menuItemSubmit.setEnabled(false);
	     }
	}
	/**
	 * 
	 * @description		清空清空啊
	 * @author			杜余		
	 * @createDate		2010-12-1
	 * @param pk
	 * @throws Exception									
	 * @version			EAS7.0
	 * @see com.kingdee.eas.framework.client.EditUI#doAfterSubmit(com.kingdee.bos.dao.IObjectPK)
	 */
	protected void doAfterSubmit(IObjectPK pk) throws Exception {
		if(isClear){
			super.doAfterSubmit(pk);
		}
	}
	/*
	 * 提交后清空服务类型F7与近三年销售额分录
	 */
	private void refreshCompons() {
		this.prmtServiceType.setData(null);
		kdtYearSale.checkParsed();
			IRow row = null;
			for(int i = 0 ; i<3 ; i++){
				row = kdtYearSale.getRow(i);
				row.getCell("year").setValue(null);
				row.getCell("saleCount").setValue(null);
			}
	}
	/**
	 * @description		初始按钮
	 * @author			杜余		
	 * @createDate		2010-11-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void initButton() {
		SupplierStockInfo info = null;
		if(null != editData.getId()){
			  try {
				info = SupplierStockFactory.getRemoteInstance().getSupplierStockInfo(new ObjectUuidPK(editData.getId().toString()));
			 } catch (Exception e) {
				handUIException(e);
			 } 
	    }
		if(null != info && null != info.getState() && info.getState().equals(SupplierStateEnum.SUBMIT)){
			/*
			 * 这个是保存按钮
			 */
			btnSubmit.setEnabled(false);
			menuItemSubmit.setEnabled(false);
		}
		btnAttachment.setVisible(true);//附件
		MenuItemAttachment.setVisible(true);
		MenuItemAttachment.setEnabled(true);
		btnAttachment.setEnabled(true);
		
		submitTwo.setEnabled(true);
		kdMenuSubmitTwo.setEnabled(true);
		kdMenuSubmitTwo.setIcon(EASResource.getIcon("imgTbtn_submit"));
		kdMenuSubmitTwo.setAccelerator(KeyStroke.getKeyStroke("ctrl T"));
		menuBiz.setVisible(false);
		prmtSupplierType.setEditable(false);
		prmtProvince.setEditable(false);
		prmtCity.setEditable(false);
		prmtServiceType.setEditable(false);
		if(getOprtState().equals(OprtState.VIEW)){
			submitTwo.setEnabled(false);
			kdMenuSubmitTwo.setEnabled(false);
		}
		try {
			/*
			 * 得到启用的服务区域,新增时
			 */
			getAreaCollection(true);
			/*
			 * 动态生成服务区域复选框控件,生成到界面上,查看时让其灰显
			 */
			initSericeArea(getOprtState().equals(OprtState.VIEW));
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(null != info){
			/*
			 * 如果存在当前的供应商信息,设置值与让服务区域该勾起的勾起
			 */
			setServiceAreaValue(info);
		}
		txtRegisterMoney.setPrecision(2);
		txtRegisterMoney.setMaximumValue(FDCHelper.MAX_DECIMAL);
		txtRegisterMoney.setHorizontalAlignment(JTextField.RIGHT);
		if(null != getUIContext().get("changeNo")){
			changeNo = 1;
		}
		/*
		 * 禁用del键,等删除键
		 */
		FDCTableHelper.disableDelete(kdtLinkPerson);
		/*
		 * 新增删除插入分录按钮加到分录上
		 */
		kDConAptitudeFile.addButton(this.btnAddRow);
		kDConAptitudeFile.addButton(this.btnInsertLine);
		kDConAptitudeFile.addButton(this.btnDeleteRow);
		kDConAchievement.addButton(btnAddRowApro);
		kDConAchievement.addButton(btnInsertLineAppr);
		kDConAchievement.addButton(btnDelRowApro);
		/*
		 * 数字框格式 
		 */
		txtPeopleCount.setHorizontalAlignment(JTextField.RIGHT);
		txtPeopleCount.setMinimumValue(FDCHelper.ZERO);
		txtMainPeopleCount.setHorizontalAlignment(JTextField.RIGHT);
		txtMainPeopleCount.setMinimumValue(FDCHelper.ZERO);
		txtDevelopPeopleCount.setHorizontalAlignment(JTextField.RIGHT);
		txtDevelopPeopleCount.setMinimumValue(FDCHelper.ZERO);
		txtManagePeopleCount.setHorizontalAlignment(JTextField.RIGHT);
		txtManagePeopleCount.setMinimumValue(FDCHelper.ZERO);
		txtNetMoney.setHorizontalAlignment(JTextField.RIGHT);
	}
	/**
	 * 
	 * @description		初始服务区域,动态生成到界面上
	 * @author			杜余		
	 * @createDate		2010-11-27
	 * @param	
	 * @return
	 */
	private void initSericeArea(boolean Flag) throws BOSException{
		int size = sc.size();
		if(size != 0 ){
			for(int i = 0 ; i<size && i<7 ; i++){
				KDCheckBox sb = new KDCheckBox();
				sb.setSize(new Dimension(100, 19));
				sb.setEnabled(!Flag);
				sb.setText(sc.get(i).getName().toString());
				sb.setBounds(100*i, 5, 100, 19);
				serviceAreaPane.add(sb);
			}
		}
	}
	/**
	 * 
	 * @description		得到启用的服务区域
	 * @author			杜余		
	 * @createDate		2010-11-27
	 * @param	
	 * @return
	 */
	private void getAreaCollection(boolean Flag){
		if(Flag){
			try {
				sc = new FDCSplAreaCollection();
				EntityViewInfo view = new EntityViewInfo();
			    FilterInfo info = new FilterInfo();
			    info.getFilterItems().add(new FilterItemInfo("isEnabled","1"));
			    view.setFilter(info);
			    sc = FDCSplAreaFactory.getRemoteInstance().getFDCSplAreaCollection(view);
			} catch (Exception e) {
				handUIException(e);
			}
		}
	}
	/**
	 * 
	 * @description		重画服务区域
	 * @author			杜余		
	 * @createDate		2010-12-1 void
	 */
	private void reflushArea(){
		serviceAreaPane.removeAll();
		try {
			getAreaCollection(false);
			initSericeArea(false);
		} catch (BOSException e) {
			handUIException(e);
		}
		serviceAreaPane.repaint();
	}
	/**
	 * 
	 * @description		取值服务区域,用|分隔存起来
	 * @author			杜余		
	 * @createDate		2010-12-1
	 * @return String
	 */
	private String getServiceAreaValue() {
		String serviceArea = "";
		boolean Flag = false;
		Component[] cm = (Component[]) serviceAreaPane.getComponents();
		int ss = serviceAreaPane.getComponentCount();
		for(int i = 0 ; i<ss ; i++){
			KDCheckBox db = null;
			if(cm[i] instanceof KDCheckBox){
				db = (KDCheckBox) cm[i];
				if(db.isSelected()){
					if(Flag){
						serviceArea = serviceArea +"|"+db.getText().toString();
					}else{
						serviceArea = serviceArea +db.getText().toString();
						Flag = true;
					}
				}
			}
		}
		return serviceArea;
	}
	/**
	 * 
	 * @description		界面显示时拆分服务区域
	 * @author			杜余		
	 * @createDate		2010-12-2
	 * @param	
	 * @return
	 */
	private void setServiceAreaValue(SupplierStockInfo info) {
		String serviceArea = info.getServiceArea();
		Component[] cm = (Component[]) serviceAreaPane.getComponents();
		int count = serviceAreaPane.getComponentCount();
		if(null != serviceArea && !serviceArea.equals("")){
			String[] dss = serviceArea.split("\\|");
			/*
			 * 服务区域被禁用后,界面上显示出来的,对应这个单据选择了这个服务区域的集合(如果引用了一个服务区域
			 * 而服务区域设置那里又被禁用了,则初始界面时不会将这个服务区域显示出来)
			 */
			java.util.List list = new ArrayList();
			/*
			 * 数据库中保存有几个多少个服务区域的集合
			 */
			java.util.List listtemp = new ArrayList();
			/*
			 * 拆分动态生成到pane中
			 */
			for(int i = 0 ; i<dss.length ;i++){
				listtemp.add(dss[i]);
				for(int j=0 ; j<count ; j++){
					KDCheckBox cb = new KDCheckBox();
					if(cm[j] instanceof KDCheckBox){
						cb = (KDCheckBox) cm[j];
						if(dss[i].equals(cb.getText())){
							cb.setSelected(true);
							list.add(dss[i]);
						}
					}
				}
			}
			if(null!=list){
				listtemp.removeAll(list);
				Iterator it = listtemp.iterator();
				/*
				 * 界面上有多少个服务区域
				 */
				int size = sc.size();
				/*
				 * 当界面上已经布满的时候直接返回(注释部分是想从显示的box中拿出没勾选的,将已经有的没显示的显示出来,测试有点缺陷,暂时不管)
				 */
				if(size>7){
					size = 7 ;
					/*while(it.hasNext()){
						for(int k= 0 ; k<size ; k++){
							KDCheckBox cb = new KDCheckBox();
							if(cm[k] instanceof KDCheckBox){
								cb = (KDCheckBox) cm[k];
								if(!cb.isSelected()){
									cb.setText(it.next().toString());
									cb.setSelected(true);
									cb.setEnabled(false);
									break;
								}
							}
						}
					}*/
					return;
				}else{
					/*
					 * 界面上不够7个时,在把选了没显示出来的显示出来
					 */
					int i=listtemp.size();
					int j = 0 ;
					while(it.hasNext() && size<7 && i>0){
						KDCheckBox sb = new KDCheckBox();
						sb.setSize(new Dimension(100, 19));
						sb.setEnabled(false);
						sb.setText(it.next().toString());
						sb.setBounds(100*(size + j), 5, 100, 19);
						sb.setSelected(true);
						serviceAreaPane.add(sb);
						size++;
						i--;
					}
				}
			}
		}
	}
	private KDWorkButton btnAddRowinfo;
	private KDWorkButton btnInsertRowinfo;
	private KDWorkButton btnDeleteRowinfo;
	/**
	 * @description		初始分录表格
	 * @author			杜余		
	 * @createDate		2010-11-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void initTable() {
		/*
		 * 基本信息分录加新增删除行
		 */
		if(btnAddRowinfo == null)
	    {
			btnAddRowinfo = new KDWorkButton();
			actionPersonAddLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
			btnAddRowinfo = (KDWorkButton)kDContainer1.add(actionPersonAddLine);
			btnAddRowinfo.setText(getResource("addRow"));
			btnAddRowinfo.setSize(new Dimension(140, 19));
			if(getOprtState().equals(OprtState.EDIT) || getOprtState().equals(OprtState.ADDNEW)){
				btnAddRowinfo.setEnabled(true);
			}else{
				btnAddRowinfo.setEnabled(false);
			}
	    }
		if(btnInsertRowinfo == null){
			btnInsertRowinfo = new KDWorkButton();
			actionPersonInsertLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
			btnInsertRowinfo = (KDWorkButton)kDContainer1.add(actionPersonInsertLine);
			btnInsertRowinfo.setText(getResource("insertRow"));
			btnInsertRowinfo.setSize(new Dimension(140, 19));
			if(getOprtState().equals(OprtState.EDIT) || getOprtState().equals(OprtState.ADDNEW)){
				btnInsertRowinfo.setEnabled(true);
			}else{
				btnInsertRowinfo.setEnabled(false);
			}
			
		}
		if(btnDeleteRowinfo == null)
	    {
			btnDeleteRowinfo = new KDWorkButton();
			btnDeleteRowinfo.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
			actionPersonDeleteLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
			btnDeleteRowinfo = (KDWorkButton)kDContainer1.add(actionPersonDeleteLine);
			btnDeleteRowinfo.setText(getResource("deleteRow"));
			btnDeleteRowinfo.setSize(new Dimension(140, 19));
			if(getOprtState().equals(OprtState.EDIT) || getOprtState().equals(OprtState.ADDNEW)){
				btnDeleteRowinfo.setEnabled(true);
			}else{
				btnDeleteRowinfo.setEnabled(false);
			}
	    }
	
	    
		/*
		 * 近三年销售额,初始三行
		 */
		kdtYearSale.checkParsed();
		int s = kdtYearSale.getRowCount();
		if(s<3){
			IRow row = null;
			for(int i = 0 ; i<3 ; i++){
				row = kdtYearSale.addRow();
				row.getCell("year").setValue(null);
				row.getCell("saleCount").setValue(null);
			}
		}
		FDCTableHelper.disableDelete(kdtYearSale);
		KDTextField testField1 = new KDTextField();
		testField1.setMaxLength(80);
		KDTDefaultCellEditor editorSize = new KDTDefaultCellEditor(testField1);
		
		
		KDFormattedTextField ff = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		KDTDefaultCellEditor ffe = new KDTDefaultCellEditor(ff);
		ff.setPrecision(2);
		kdtYearSale.getColumn("saleCount").setEditor(ffe);
		kdtYearSale.getColumn("saleCount").getStyleAttributes().setNumberFormat("#0.00");
		kdtYearSale.getColumn("saleCount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		kdtYearSale.getColumn("year").setEditor(editorSize);
		
		/*
		 * 联系人是否默认初始
		 */
	    kdtLinkPerson.checkParsed();
	    kdtLinkPerson.getColumn("personName").setEditor(editorSize);
		kdtLinkPerson.getColumn("position").setEditor(editorSize);
		kdtLinkPerson.getColumn("phone").setEditor(editorSize);
		kdtLinkPerson.getColumn("workPhone").setEditor(editorSize);
		kdtLinkPerson.getColumn("personFax").setEditor(editorSize);
		kdtLinkPerson.getColumn("email").setEditor(editorSize);
		KDCheckBox isAutidor = new KDCheckBox();
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(isAutidor);
		kdtLinkPerson.getColumn("isDefault").setEditor(editor);
		/*
		 * 初始资质文件是否有附件
		 */
		kdtAptitudeFile.checkParsed();
		KDCheckBox isAutidor1 = new KDCheckBox();
		isAutidor1.setEditable(false);
		KDTDefaultCellEditor editor1 = new KDTDefaultCellEditor(isAutidor1);
		kdtAptitudeFile.getColumn("isHaveAttach").setEditor(editor1);
		kdtAptitudeFile.getColumn("isHaveAttach").getStyleAttributes().setLocked(true);
		/*
		 * 初始资质文件时间
		 */
		KDDatePicker dpFile = new KDDatePicker();
		dpFile.setEditable(false);
		KDTDefaultCellEditor editorFile = new KDTDefaultCellEditor(dpFile);
		kdtAptitudeFile.getColumn("endDate").setEditor(editorFile);
		kdtAptitudeFile.getColumn("endDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		kdtAptitudeFile.getColumn("aptNumber").setEditor(editorSize);
		kdtAptitudeFile.getColumn("aptName").setEditor(editorSize);
		kdtAptitudeFile.getColumn("aptLevel").setEditor(editorSize);
		kdtAptitudeFile.getColumn("awardUnit").setEditor(editorSize);
		KDTextField testField2 = new KDTextField();
		testField2.setMaxLength(200);
		KDTDefaultCellEditor editorSize2 = new KDTDefaultCellEditor(testField2);
		kdtAptitudeFile.getColumn("remark").setEditor(editorSize2);
		
		
		/*
		 * 初始业绩是否有附件
		 */
	    kdtAchievement.checkParsed();
	    KDCheckBox isAutidor2 = new KDCheckBox();
	    isAutidor2.setEditable(false);
		KDTDefaultCellEditor editor2 = new KDTDefaultCellEditor(isAutidor2);
		kdtAchievement.getColumn("isHaveAttach").setEditor(editor2);
		/*
		 * 初始合同总额
		 */
		KDFormattedTextField ftf = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		KDTDefaultCellEditor tce = new KDTDefaultCellEditor(ftf);
		ftf.setPrecision(2);
		kdtAchievement.getColumn("contractPay").setEditor(tce);
		kdtAchievement.getColumn("contractPay").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kdtAchievement.getColumn("contractPay").getStyleAttributes().setNumberFormat("#0.00");
		/*
		 * 初始日期
		 */
		KDDatePicker dp = new KDDatePicker();
		dp.setEditable(false);
		KDTDefaultCellEditor editor4 = new KDTDefaultCellEditor(dp);
		
		KDFormattedTextField peopleC = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		peopleC.setPrecision(0);
		peopleC.setDataVerifierType(12);
		KDTDefaultCellEditor peopleCount = new KDTDefaultCellEditor(peopleC);
		kdtAchievement.getColumn("peopleCount").setEditor(peopleCount);
		
		kdtAchievement.getColumn("startDate").setEditor(editor4);
		kdtAchievement.getColumn("startDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		kdtAchievement.getColumn("endDate").setEditor(editor4);
		kdtAchievement.getColumn("endDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		kdtAchievement.getColumn("isHaveAttach").getStyleAttributes().setLocked(true);
		
		kdtAchievement.getColumn("projectName").setEditor(editorSize);
		kdtAchievement.getColumn("clientName").setEditor(editorSize);
		kdtAchievement.getColumn("clientAddress").setEditor(editorSize);
		kdtAchievement.getColumn("clientLinkPerson").setEditor(editorSize);
		kdtAchievement.getColumn("phone").setEditor(editorSize);
		kdtAchievement.getColumn("remark").setEditor(editorSize2);
		kdtAchievement.getColumn("peopleCount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		
	}
	/**
	 * 
	 * @description		新增行
	 * @author			杜余		
	 * @createDate		2010-12-8
	 * @param	
	 * @return
	 */
	private void kdTableAddRow(KDTable table) {
		if(!getOprtState().equals(OprtState.VIEW))
			table.addRow();	
	}
	/**
	 * 
	 * @description		插入行
	 * @author			杜余		
	 * @createDate		2010-12-8
	 * @param	
	 * @return
	 */
	private void kdTableInsertLine(KDTable table){
		if(!getOprtState().equals(OprtState.VIEW)){
			if(table == null)
	            return;
	        IRow row = null;
	        if(table.getSelectManager().size() > 0)
	        {
	            int top = table.getSelectManager().get().getTop();
	            if(isTableColumnSelected(table))
	                row = table.addRow();
	            else
	                row = table.addRow(top);
	        } else
	        {
	            row = table.addRow();
	        }
		}
	}
	/**
	 * 
	 * @description		插入行用的,从基类中拷来的
	 * @author			杜余		
	 * @createDate		2010-12-2
	 * @param	
	 * @return
	 */
	protected final boolean isTableColumnSelected(KDTable table)
    {
        if(table.getSelectManager().size() > 0)
        {
            KDTSelectBlock block = table.getSelectManager().get();
            if(block.getMode() == 4 || block.getMode() == 8)
                return true;
        }
        return false;
    }
	/**
	 * 
	 * @description		删除行
	 * @author			杜余		
	 * @createDate		2010-12-8
	 * @param	
	 * @return
	 */
	private void kdTableDeleteRow(KDTable table) {
		if(!getOprtState().equals(OprtState.VIEW))
	        CSClientUtils.removeLine(this, table);
	}
	public SelectorItemCollection getSelectors() {
		 SelectorItemCollection sic = super.getSelectors();
		 sic.add(new SelectorItemInfo("*"));
		return sic;
	}
	/**
	 * 
	 * @description		去空格.控件
	 * @author			杜余		
	 * @createDate		2010-12-2
	 * @param	
	 * @return
	 */
	private void trimTextfield(){
		if(null!=txtEnterpriseMaster.getText() && !"".equals(txtEnterpriseMaster.getText().trim())){
			txtEnterpriseMaster.setText(txtEnterpriseMaster.getText().trim());
		}
		if(null!=txtBusinessNum.getText() && !"".equals(txtBusinessNum.getText().trim())){
			txtBusinessNum.setText(txtBusinessNum.getText().trim());
		}
		if(null!=txtWebSite.getText() && !"".equals(txtWebSite.getText().trim())){
			txtWebSite.setText(txtWebSite.getText().trim());
		}
		if(null!=txtLinkPhone.getText() && !"".equals(txtLinkPhone.getText().trim())){
			txtLinkPhone.setText(txtLinkPhone.getText().trim());
		}
		if(null!=txtLinkMail.getText() && !"".equals(txtLinkMail.getText().trim())){
			txtLinkMail.setText(txtLinkMail.getText().trim());
		}
		if(null!=txtPostNumber.getText() && !"".equals(txtPostNumber.getText().trim())){
			txtPostNumber.setText(txtPostNumber.getText().trim());
		}
		if(null!=txtLinkFax.getText() && !"".equals(txtLinkFax.getText().trim())){
			txtLinkFax.setText(txtLinkFax.getText().trim());
		}
		if(null!=txtAddress.getText() && !"".equals(txtAddress.getText().trim())){
			txtAddress.setText(txtAddress.getText().trim());
		}
		if(null!=txtBankLinkPhone.getText() && !"".equals(txtBankLinkPhone.getText().trim())){
			txtBankLinkPhone.setText(txtBankLinkPhone.getText().trim());
		}
		if(null!=txtBankCount.getText() && !"".equals(txtBankCount.getText().trim())){
			txtBankCount.setText(txtBankCount.getText().trim());
		}
		if(null!=txtMoneyPercent.getText() && !"".equals(txtMoneyPercent.getText().trim())){
			txtMoneyPercent.setText(txtMoneyPercent.getText().trim());
		}
		if(null!=txtBankName.getText() && !"".equals(txtBankName.getText().trim())){
			txtBankName.setText(txtBankName.getText().trim());
		}
		if(null!=txtCreditLevel.getText() && !"".equals(txtCreditLevel.getText().trim())){
			txtCreditLevel.setText(txtCreditLevel.getText().trim());
		}
	}
	/*
	 * 几个全局变量,判断分录名称重复时用的
	 */
	private boolean isBreak = false;
	private boolean isBreak1 = false;
	private boolean isBreak2 = false;
	private boolean isBreak3 = false;
	private void verifyInput() {
		if(null == prmtSupplierType.getText() || "".equals(prmtSupplierType.getText().trim())){
			MsgBox.showWarning(this ,getResource("basicClass")+getResource("notNull"));
			SysUtil.abort();
		}
		if(null == prmtServiceType.getText() || "".equals(prmtServiceType.getText().trim())){
			MsgBox.showWarning(this ,getResource("supplierType")+getResource("notNull"));
			SysUtil.abort();
		}
		if(null == txtName.getSelectedItem().toString() || "".equals(txtName.getSelectedItem().toString())){
			MsgBox.showWarning(this ,getResource("companyName"));
			SysUtil.abort();
		}else{
			txtName.setSelectedItem(txtName.getSelectedItem().toString());
		}
		
		if(null == txtNumber.getText() || "".equals(txtNumber.getText().trim())){
			MsgBox.showWarning(this ,getResource("number"));
			SysUtil.abort();
		}else{
			txtNumber.setText(txtNumber.getText().trim());
		}
		if(null == pkBuildDate.getText() || "".equals(pkBuildDate.getText().trim())){
			MsgBox.showWarning(this ,getResource("bornDate")+getResource("notNull"));
			SysUtil.abort();
		}
		if(this.changeNo == 0){
		if(null == txtBusinessNum.getText() || "".equals(txtBusinessNum.getText().trim())){
			MsgBox.showWarning(this ,getResource("licenseNotNull"));
			SysUtil.abort();
		}else{
			txtBusinessNum.setText(txtBusinessNum.getText().trim());
		}
		}
		if(null == prmtProvince.getText() || "".equals(prmtProvince.getText().trim())){
			MsgBox.showWarning(this ,getResource("provinceNotNull"));
			SysUtil.abort();
		}
		if(null == txtLinkPhone.getText() || "".equals(txtLinkPhone.getText().trim())){
			MsgBox.showWarning(this ,getResource("phoneNotNull"));
			SysUtil.abort();
		}else{
			txtLinkPhone.setText(txtLinkPhone.getText().trim());
		}
		if(null == txtLinkMail.getText() || "".equals(txtLinkMail.getText().trim())){
			MsgBox.showWarning(this ,getResource("mailNotNull"));
			SysUtil.abort();
		}else{
			txtLinkMail.setText(txtLinkMail.getText().trim());
		}
		if(null == txtLinkFax.getText() || "".equals(txtLinkFax.getText().trim())){
			MsgBox.showWarning(this ,getResource("faxNotNull"));
			SysUtil.abort();
		}else{
			txtLinkFax.setText(txtLinkFax.getText().trim());
		}
		
		if(isExist("businessNum",txtBusinessNum.getText().toString().trim()) == true){
    		MsgBox.showInfo(this,getResource("license")+txtBusinessNum.getText()+getResource("again"));
    		abort();
    	}
		kdtYearSale.checkParsed();
		int countYear = kdtYearSale.getRowCount();
		if(countYear > 1){
			for(int i = 0 ; i < countYear ; i++){
    			if(isBreak3){
    				break;
    			}
    			    for(int j = i + 1 ; j < countYear ; j++){
    			    	String attachName = (String) kdtYearSale.getRow(i).getCell("year").getValue();
    			    	String attachNameTemp = (String) kdtYearSale.getRow(j).getCell("year").getValue();
    			    	if(null == attachName || null == attachNameTemp || "".equals(attachName) || "".equals(attachNameTemp)){
    			    		continue;
    			    	}
    			    	if(attachName.equals(attachNameTemp)){
    			    		MsgBox.showWarning(this ,getResource("year")+getResource("notSame"));
    	    				SysUtil.abort();
    	    				isBreak3 = true;
    	    				break;
    			    	}
    			    }
    	    	}
		}
		kdtLinkPerson.checkParsed();
		int countP = kdtLinkPerson.getRowCount();
		if(countP > 0){
			for(int i = 0 ; i < countP ; i++){
	    		if( "".equals(kdtLinkPerson.getRow(i).getCell("personName").getValue()) || null == kdtLinkPerson.getRow(i).getCell("personName").getValue()){
	    			MsgBox.showWarning(this , getResource("nameB")+getResource("notNull"));
	    			SysUtil.abort();
	    			break;
	    		}
	    	}
			for(int i = 0 ; i < countP ; i++){
    			if(isBreak2){
    				break;
    			}
    			    for(int j = i + 1 ; j < countP ; j++){
    			    	String attachName = (String) kdtLinkPerson.getRow(i).getCell("personName").getValue();
    			    	String attachNameTemp = (String) kdtLinkPerson.getRow(j).getCell("personName").getValue();
    			    	if(attachName.equals(attachNameTemp)){
    			    		MsgBox.showWarning(this ,getResource("nameB")+getResource("notSame"));
    	    				SysUtil.abort();
    	    				isBreak2 = true;
    	    				break;
    			    	}
    			    }
    	    	}
		}
		/*
		 * 资质文件分录验证
		 */
		kdtAptitudeFile.checkParsed();
		int counts = kdtAptitudeFile.getRowCount();
		if(counts < 1){
			
		}else{
			/*
			 * 不为空验证
			 */
			for(int i = 0 ; i < counts ; i++){
				if( "".equals(kdtAptitudeFile.getRow(i).getCell("aptNumber").getValue()) || null == kdtAptitudeFile.getRow(i).getCell("aptNumber").getValue()){
					MsgBox.showWarning(this , getResource("fileNumber")+getResource("notNull"));
					SysUtil.abort();
					break;
				}
				if( "".equals(kdtAptitudeFile.getRow(i).getCell("aptName").getValue()) || null == kdtAptitudeFile.getRow(i).getCell("aptName").getValue()){
					MsgBox.showWarning(this , getResource("fileName")+getResource("notNull"));
					SysUtil.abort();
					break;
				}
				if( "".equals(kdtAptitudeFile.getRow(i).getCell("awardUnit").getValue()) || null == kdtAptitudeFile.getRow(i).getCell("awardUnit").getValue()){
					MsgBox.showWarning(this , getResource("unit")+getResource("notNull"));
					SysUtil.abort();
					break;
				}
			}
			/*
			 * 不重复验证
			 */
			for(int i = 0 ; i < counts ; i++){
    			if(isBreak){
    				break;
    			}
    			    for(int j = i + 1 ; j < counts ; j++){
    			    	String attachName = (String) kdtAptitudeFile.getRow(i).getCell("aptNumber").getValue();
    			    	String attachNameTemp = (String) kdtAptitudeFile.getRow(j).getCell("aptNumber").getValue();
    			    	if(attachName.equals(attachNameTemp)){
    			    		MsgBox.showWarning(this ,getResource("fileNumber")+getResource("notSame"));
    	    				SysUtil.abort();
    	    				isBreak = true;
    	    				break;
    			    	}
    			    }
    	    	}
		}
		/*
		 * 业绩分录验证
		 */
		kdtAchievement.checkParsed();
		int countsAch = kdtAchievement.getRowCount();
		if(countsAch < 1){
			
		}else{
			/*
			 * 不为空验证
			 */
			for(int i = 0 ; i < countsAch ; i++){
				if( "".equals(kdtAchievement.getRow(i).getCell("projectName").getValue()) || null == kdtAchievement.getRow(i).getCell("projectName").getValue()){
					MsgBox.showWarning(this , getResource("projectName")+getResource("notNull"));
					SysUtil.abort();
					break;
				}
				if( "".equals(kdtAchievement.getRow(i).getCell("clientName").getValue()) || null == kdtAchievement.getRow(i).getCell("clientName").getValue()){
					MsgBox.showWarning(this , getResource("clientName")+getResource("notNull"));
					SysUtil.abort();
					break;
				}
				if( "".equals(kdtAchievement.getRow(i).getCell("contractPay").getValue()) || null == kdtAchievement.getRow(i).getCell("contractPay").getValue()){
					MsgBox.showWarning(this , getResource("contractTotal")+getResource("notNull"));
					SysUtil.abort();
					break;
				}
				if(null != kdtAchievement.getRow(i).getCell("startDate").getValue() && null != kdtAchievement.getRow(i).getCell("endDate").getValue()){
					Date from = (Date) kdtAchievement.getRow(i).getCell("startDate").getValue();
					Date to = (Date) kdtAchievement.getRow(i).getCell("endDate").getValue();
					if (from.after(to)) {
						MsgBox.showWarning(this, getResource("compare"));
						SysUtil.abort();
						break;
					}
				}
			}
			/*
			 * 不重复验证
			 */
			for(int i = 0 ; i < countsAch ; i++){
    			if(isBreak1){
    				break;
    			}
    			    for(int j = i + 1 ; j < countsAch ; j++){
    			    	String attachName = (String) kdtAchievement.getRow(i).getCell("projectName").getValue();
    			    	String attachNameTemp = (String) kdtAchievement.getRow(j).getCell("projectName").getValue();
    			    	if(attachName.equals(attachNameTemp)){
    			    		MsgBox.showWarning(this ,getResource("projectName")+getResource("notSame"));
    	    				SysUtil.abort();
    	    				isBreak1 = true;
    	    				break;
    			    	}
    			    }
    	    	}
		}
		/*
		 * 法人代表跟名称相同提示
		 */
		if(txtEnterpriseMaster!=null){
			if(txtEnterpriseMaster.getText().toString().equals(txtName.getSelectedItem().toString())){
				String temp = getResource("being")+txtName.getSelectedItem().toString()+getResource("supplierAnd")+txtEnterpriseMaster.getText().toString()+getResource("deputySame");
				int yes = MsgBox.showConfirm2New(this, temp);
				if(yes>0){
					SysUtil.abort();
				}
			}
		}
	}
	/**
	 * 
	 * @description		判断字段唯一
	 * @author			杜余		
	 * @createDate		2010-12-2
	 * @param	
	 * @return
	 */
	private boolean isExist(String FiledName,String filedValue){
		if(changeNo > 0){
			return false;
		}
	    FilterInfo filterInfo=new FilterInfo();
	    FilterItemInfo itemInfo = new FilterItemInfo(FiledName, filedValue, CompareType.EQUALS);
	    filterInfo.getFilterItems().add(itemInfo);
	    
	    // 同级CU及下级CU不允许出现重名，如公司名称、营业执照等 By Owen_wen 2011-02-23
	    filterInfo.getFilterItems().add((new FilterItemInfo("CU.id", SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())));
		filterInfo.getFilterItems().add(
				(new FilterItemInfo("CU.longNumber", SysContext.getSysContext().getCurrentCtrlUnit().getLongNumber() + "!%", CompareType.LIKE)));
	    if(STATUS_EDIT.equals(this.getOprtState()) && changeNo == 0){
	    	FilterItemInfo itemInfo_2 = new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS);
	    	filterInfo.getFilterItems().add(itemInfo_2);
	    	filterInfo.setMaskString("#0 and (#1 or #2) and #3");
		} else {
			filterInfo.setMaskString("#0 and (#1 or #2)");
	    }
	    boolean flag = false;
		try {
			flag = SupplierStockFactory.getRemoteInstance().exists(filterInfo);
		} catch (Exception e) {
			handUIException(e);
		}

	    return flag;
    }
	/**
	 * 
	 * @description		页签变化事件
	 * @author			杜余		
	 * @createDate		2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	protected void kDTabbedMain_stateChanged(ChangeEvent e) throws Exception {
		setButtonStyle();
	}
	
	
	/**
	 * @description		初始新增分录删除分录按钮
	 * @author			杜余		
	 * @createDate		2010-11-11
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected void setButtonStyle() {
		btnAddRow.setIcon(EASResource.getIcon("imgTbtn_addline"));
		
		btnDeleteRow.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		
		btnAddRowApro.setIcon(EASResource.getIcon("imgTbtn_addline"));
		
		btnDelRowApro.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		
		btnInsertLine.setIcon(EASResource.getIcon("imgTbtn_insert"));
		btnInsertLineAppr.setIcon(EASResource.getIcon("imgTbtn_insert"));
		if(kDTabbedMain.getSelectedIndex()==0){
			btnAddRow.setVisible(false);//新增分录
			
			btnDeleteRow.setVisible(false);//删除分录
			
			btnAddRowApro.setVisible(false);
			
			btnDelRowApro.setVisible(false);
			
			btnInsertLine.setVisible(false);
			btnInsertLineAppr.setVisible(false);
			setUITitle(getResource("baseInfo"));
		}else if(kDTabbedMain.getSelectedIndex()==1){
			btnAddRow.setVisible(true);//新增分录
			btnInsertLine.setVisible(true);//插入行
			btnDeleteRow.setVisible(true);//删除分录
			
			if(getOprtState().equals(OprtState.VIEW)){
				btnAddRow.setEnabled(false);
				btnInsertLine.setEnabled(false);
				
				btnDeleteRow.setEnabled(false);
			}else{
				btnAddRow.setEnabled(true);
				btnDeleteRow.setEnabled(true);
				
				btnInsertLine.setEnabled(true);
			}
			btnAddRowApro.setVisible(false);
			btnDelRowApro.setVisible(false);
			btnInsertLineAppr.setVisible(false);
		    this.setUITitle(getResource("aptitudeFile"));
		}else if(kDTabbedMain.getSelectedIndex()==2){
			btnAddRow.setVisible(false);//新增分录
			btnDeleteRow.setVisible(false);//删除分录
			
			btnInsertLine.setVisible(false);//插入行
			
			btnAddRowApro.setVisible(true);
			btnDelRowApro.setVisible(true);
			btnInsertLineAppr.setVisible(true);
			if(getOprtState().equals(OprtState.VIEW)){
				btnAddRowApro.setEnabled(false);
				btnDelRowApro.setEnabled(false);
				btnInsertLineAppr.setEnabled(false);
			}else{
				btnAddRowApro.setEnabled(true);
				btnDelRowApro.setEnabled(true);
				btnInsertLineAppr.setEnabled(true);
			}
			setUITitle(getResource("achievement"));
		}
	}
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-11-12
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void onShow() throws Exception {
		super.onShow();
		btnCancelCancel.setVisible(false);
		btnCancel.setVisible(false);
		if(changeNo > 0){
	    	this.txtBusinessNum.setEditable(false);
	    	this.txtNumber.setEditable(false);
		}
	}
	/**
	 * 
	 * @description		联系人分录结束编辑事件
	 * @author			杜余		
	 * @createDate		2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	protected void kdtLinkPerson_editStopped(KDTEditEvent e) throws Exception {
		int index = e.getRowIndex();
		kdtLinkPerson.checkParsed();
		int size = kdtLinkPerson.getRowCount();
	    IRow row = kdtLinkPerson.getRow(index);
	    Boolean b=(Boolean) row.getCell("isDefault").getValue();
	    /*
	     * 这里是合并手机与办公电话到一列中去,序时薄上要把手机和办公电话显示到一起.暂用这种方式做
	     */
	    for(int j = 0 ; j<size ; j++){
	    	if(null != kdtLinkPerson.getRow(j).getCell("contact").getValue()){
	    		kdtLinkPerson.getRow(j).getCell("contact").setValue(null);
	    	}
	    	if(null != kdtLinkPerson.getRow(j).getCell("phone").getValue()){
	    		if(null != kdtLinkPerson.getRow(j).getCell("workPhone").getValue()){
	    			kdtLinkPerson.getRow(j).getCell("contact").setValue(kdtLinkPerson.getRow(j).getCell("phone").getValue()+";"+kdtLinkPerson.getRow(j).getCell("workPhone").getValue());
	    		}else{
	    			kdtLinkPerson.getRow(j).getCell("contact").setValue(kdtLinkPerson.getRow(j).getCell("phone").getValue().toString());
	    		}
	    	}else if(null != kdtLinkPerson.getRow(j).getCell("workPhone").getValue()){
	    		kdtLinkPerson.getRow(j).getCell("contact").setValue(kdtLinkPerson.getRow(j).getCell("workPhone").getValue().toString());
	    	}else{
	    		continue;
	    	}
	    }
	    /*
	     * 处理只能勾选一个人为默认联系.新增第一行时为空 则返回
	     */
	    if(null == b ){
	    	return ;
	    }
	    if(b.booleanValue()){
			for( int i = 0 ; i< size ;i++){
				if(i ==index ){
					continue;
				}
				kdtLinkPerson.getRow(i).getCell("isDefault").setValue(Boolean.FALSE);
			}
	    }else{
			
	    }
	
	}
	/**
	 * 
	 * @description		当前页面关闭后对前一个list相同的公司合并
	 * @author			杜余		
	 * @createDate		2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	public boolean destroyWindow() {
	 storeFields();
	 boolean b = super.destroyWindow();
	 Object ui = null ;
	 ui = getUIContext().get("Owner");
	 /*
	  * 判断前一个父窗口是哪个,对前一个列表合并单元格
	  */
     if( b && null !=ui && ui instanceof SupplierInputListUI){
    	 SupplierInputListUI newUI = (SupplierInputListUI)ui;
//    	 newUI.unitTable();
     }
     if( b && null !=ui && ui instanceof SupplierStockListUI){
    	 SupplierStockListUI stockListUI = (SupplierStockListUI)ui;
    	 stockListUI.unitTable();
     }
     return b;
	}
	/*
	 * 服务类型F7值变化事件即绑定服务类型数据
	 */
	protected void prmtServiceType_dataChanged(DataChangeEvent e)
		throws Exception {
		if(e.getNewValue()!=null&&e.getNewValue()instanceof Object[]){
			editData.getSupplierServiceType().clear();
			FDCSupplierServiceTypeCollection stc = editData.getSupplierServiceType();
			Object[] objs = (Object[]) e.getNewValue();
			for(int i = 0 ; i<objs.length ; i++){
				FDCSplServiceTypeInfo info = (FDCSplServiceTypeInfo) objs[i];
				FDCSupplierServiceTypeInfo sstInfo = new FDCSupplierServiceTypeInfo();
				sstInfo.setSupplier(this.editData);
				sstInfo.setServiceType(info);
				stc.add(sstInfo);
			}
		}else{
			prmtServiceType.setData(null);
		}
	}
	/**
	 * 
	 * @description		初始服务类型F7按钮与绑定查询数据
	 * @author			杜余		
	 * @createDate		2010-12-2
	 * @param	
	 * @return
	 */
	private void initF7ServiceType() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.valueOf(true)));
		view.setFilter(filter);
		prmtServiceType.setEntityViewInfo(view);
 		prmtServiceType.setDisplayFormat("$name$");
		prmtServiceType.setEditFormat("$name$");
		prmtServiceType.setCommitFormat("$name$");
		prmtServiceType.setEnabledMultiSelection(true);
		SupplierStockInfo info = null;
		if(null != editData.getId()){
			  try {
				info = SupplierStockFactory.getRemoteInstance().getSupplierStockInfo(new ObjectUuidPK(editData.getId().toString()));
			 } catch (Exception e) {
				handUIException(e);
			 } 
			 if(null != info.getSupplierServiceType()){
				 /**
				  * 服务类型对应是一个分录,每条分录对应一个服务类型基本实体
				  */
				 FDCSupplierServiceTypeCollection fstc = info.getSupplierServiceType();
				 FDCSplServiceTypeInfo[] sti = new FDCSplServiceTypeInfo[fstc.size()]; 
				 for(int i = 0 ; i < fstc.size() ; i++){
					 try {
						 if(null != fstc.get(i).getServiceType() && null != fstc.get(i).getServiceType().getId()){
							 FDCSplServiceTypeInfo sstInfo = FDCSplServiceTypeFactory.getRemoteInstance().getFDCSplServiceTypeInfo(new ObjectUuidPK(fstc.get(i).getServiceType().getId().toString()));
							 sti[i] = sstInfo;
						 }else{
							 
						 }
					} catch (Exception e) {
						handUIException(e);
					}
				 }
				 prmtServiceType.setData(sti);
			 }
	    }
		
	}
	/**
	 * 
	 * @description		近三年销售额,结束编辑时赋值
	 * @author			杜余		
	 * @createDate		2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	protected void kdtYearSale_editStopped(KDTEditEvent e) throws Exception {
		kdtYearSale.checkParsed();
		int size = kdtYearSale.getRowCount();
		IRow row = null ;
		YearSaleCollection ysc = editData.getYearSale();
		ysc.clear();
		for(int i = 0 ; i<size ; i++){
			YearSaleInfo info = new YearSaleInfo();
			row = kdtYearSale.getRow(i);
			if( null != row.getCell("year").getValue()){
				String year = row.getCell("year").getValue().toString();
				info.setYear(year);
			}
			if( null != row.getCell("saleCount").getValue()){
				BigDecimal sale =  (BigDecimal) row.getCell("saleCount").getValue();
				info.setSaleCount(sale);
			}
			ysc.add(info);
		}
	}
	/**
	 * 
	 * @description		初始近三年销售额分录
	 * @author			杜余		
	 * @createDate		2010-12-2
	 * @param	
	 * @return
	 */
	private void initYearSale() {
		kdtYearSale.checkParsed();
		int yearSize = kdtYearSale.getRowCount();
		SupplierStockInfo info = null;
		if(null != editData.getId()){
			  try {
				info = SupplierStockFactory.getRemoteInstance().getSupplierStockInfo(new ObjectUuidPK(editData.getId().toString()));
			 } catch (Exception e) {
				handUIException(e);
			 } 
			 if(null != info.getYearSale()){
				YearSaleCollection ysc = info.getYearSale();
				int size = ysc.size();
				for(int i = 0 ; i<size && i<3 && size <= yearSize; i++){
					kdtYearSale.getRow(i).getCell("year").setValue(ysc.get(i).get("year"));
					kdtYearSale.getRow(i).getCell("saleCount").setValue(ysc.get(i).get("saleCount"));
				}
			 }
	    }
	}
	protected void txtName_propertyChange(PropertyChangeEvent e)
		throws Exception {
	}

	protected void txtName_itemStateChanged(ItemEvent e) throws Exception {
	}
	/**
	 * 
	 * @description		得到省份过滤,需求没提选择省份,对应城市选择,暂时没用
	 * @author			杜余		
	 * @createDate		2010-12-2
	 * @param	
	 * @return
	 */
	private FilterInfo getProvinceFilter(){
		FilterInfo filter = new FilterInfo();
		if(null!=prmtProvince.getData()){
			if(prmtProvince.getData() instanceof ProvinceInfo){
				ProvinceInfo pinfo = (ProvinceInfo) prmtProvince.getData();
				filter.getFilterItems().add(new FilterItemInfo("province.id" , pinfo.getId().toString()));
			}
		}
		return filter;
	}
	
	 protected void prmtProvince_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
	    {
		EntityViewInfo view = null;
		if(prmtCity.getEntityViewInfo() == null){
			view = new EntityViewInfo();
		}else{
			view = prmtCity.getEntityViewInfo();
			view.clear();
		}
		FilterInfo filter = new FilterInfo();
		if(null != getProvinceFilter()){
			filter = getProvinceFilter();
		}
		view.setFilter(filter);
		prmtCity.setEntityViewInfo(view);
	}
}