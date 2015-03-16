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
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
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
import com.kingdee.bos.util.BOSUuid;
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
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.supplier.AppraisePhaseEnum;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpCollection;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		资格考察
 *		
 * @author		梁良
 * @version		EAS7.0		
 * @createDate	2010-11-5	 
 * @see
 */
public class SupplierQuaReviewEditUI extends AbstractSupplierQuaReviewEditUI
{
    /*
     * 序列号
     */
	private static final long serialVersionUID = 3923729208143800392L;
	private static final Logger logger = CoreUIObject.getLogger(SupplierQuaReviewEditUI.class);
    /**
     * output class constructor
     */
    public SupplierQuaReviewEditUI() throws Exception
    {
        super();
    }

    /**
     * @description		
     * @author				
     * @createDate		2010-11-6
     * @param	
     * @return					
     *	
     * @version			EAS7.0
     * @see						
     */
    public void onLoad() throws Exception {
    	super.onLoad();
    	setQuery();
    	initButtonStyle();
    	setAuditBtn();
    	fillAttachmentList();
    	initTable();
    	if(getOprtState().equals(OprtState.ADDNEW)){
    		initSupplierName();
    		getTemplate();
    	}else{
    	    initSupplierNameF7();
    	}
    }
    /**
	 * @description		获取资源文件
	 * @author			梁良		
	 * @createDate		2010-11-23
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}
    
    /**
	 * @description		初始化供应商名称
	 * @author			梁良		
	 * @createDate		2010-11-10
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void initSupplierName() throws Exception{
		if( null != getUIContext().get("SupplierInfo")){
			SupplierStockInfo info = (SupplierStockInfo) getUIContext().get("SupplierInfo");
			editData.setSupplier(info);
    		this.txtSupplierName.setText(info.getName());
    		FDCSupplierServiceTypeCollection sic = info.getSupplierServiceType();
    		Iterator it = sic.iterator();
    		Object obj[] = new Object[sic.size()];
    		int i = 0;
    		while(it.hasNext()){
    			FDCSupplierServiceTypeInfo finfo = (FDCSupplierServiceTypeInfo)it.next();
    			obj[i++]=finfo.getServiceType();
    		}
			this.prmtSupplierType.setData(obj);
    	}
	}
	/**
     * @description		初始化分录的类型
     * @author			梁良
     * @createDate		2010-11-9
     * @param	
     * @return					
     *	
     * @version			EAS7.0
     * @see						
     */
	private void initSupplierNameF7(){
		if(null != editData){
			FDCSplQualificationAuditEntryCollection seic  = editData.getAuditResult();
			Object obj[] = new Object[seic.size()];
			for(int i = 0 ; i < seic.size() ; i ++){
				FDCSplQualificationAuditEntryInfo info  = seic.get(i);
				if(null == info.getSupplierType()){
					continue;
				}	
				obj[i]=(FDCSplServiceTypeInfo)info.getSupplierType();
			}
			this.prmtSupplierType.setDataNoNotify(obj);
		}
	}
	
    public void loadFields() {
    	this.kdtReviewResult.checkParsed();
    	this.kdtTemplateBill.checkParsed();
    	super.loadFields();
    }
    /**
	 * @description		设置按钮
	 * @author			梁良	
	 * @createDate		2010-11-28
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void setAuditBtn() {
		actionAudit.setVisible(true);
		actionUnAudit.setVisible(true);
		if (STATUS_ADDNEW.equals(this.getOprtState())) {
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		} else {
			if (FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
				actionAudit.setEnabled(true);
				actionUnAudit.setEnabled(false);
			} else if (FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
				actionAudit.setEnabled(false);
				actionUnAudit.setEnabled(true);
			} else {
				actionAudit.setEnabled(false);
				actionUnAudit.setEnabled(false);
			}
		}
	}
    /**
     * @description		保存时验证
     * @author				
     * @createDate		2010-11-14
     * @param	
     * @return					
     *	
     * @version			EAS7.0
     * @see						
     */
    protected void verifyInputForSave() throws Exception {
    	txtAddress.setText(txtAddress.getText().trim());
    	txtPerson.setText(txtPerson.getText().trim());
    	if(prmtSupplierType.getText().trim() == null||prmtSupplierType.getText().trim().equals("")){
    		MsgBox.showWarning(this,getResource("supplierType")+getResource("notNull"));
        	SysUtil.abort();
    	}
    	if(pkBizDate.getText().trim()==null||pkBizDate.getText().trim().equals("")){
        	MsgBox.showWarning(this,getResource("bizDate"));
        	SysUtil.abort();
        	}
    	if(txtAddress.getText().trim()==null||txtAddress.getText().trim().equals("")){
        	MsgBox.showWarning(this,getResource("address"));
        	SysUtil.abort();
        	}
    	if(txtPerson.getText().trim()==null||txtPerson.getText().trim().equals("")){
        	MsgBox.showWarning(this,getResource("person"));
        	SysUtil.abort();
        	}
    	String isAuditstr = "";
    	for (int i = 0; i<editData.getAuditResult().size();i++){
    		FDCSplQualificationAuditEntryInfo info = editData.getAuditResult().get(i);
    		if(info.isIsAudit()){
    			if(null == info.getGrade()){
    				FDCMsgBox.showWarning(this,getResource("supplierGrade"));
    				abort();
    			}
    		}else{
    			isAuditstr += info.getSupplierType().getName()+",";
    		}
    	}
    	if(isAuditstr.length() > 0){
		  int yes=MsgBox.showConfirm2New(this, isAuditstr+getResource("isReview"));
		  if(yes > 0){
			SysUtil.abort();
		  }
    	}
    }
    /**
     * @description		提交时验证
     * @author				
     * @createDate		2010-11-22
     * @param	
     * @return					
     *	
     * @version			EAS7.0
     * @see						
     */
    protected void verifyInputForSubmint() throws Exception {
    	txtAddress.setText(txtAddress.getText().trim());
    	txtPerson.setText(txtPerson.getText().trim());
    	if(prmtSupplierType.getText().trim() == null||prmtSupplierType.getText().trim().equals("")){
    		MsgBox.showWarning(this,getResource("supplierType")+getResource("notNull"));
        	SysUtil.abort();
    	}
    	if(pkBizDate.getText().trim()==null||pkBizDate.getText().trim().equals("")){
        	MsgBox.showWarning(this,getResource("bizDate"));
        	SysUtil.abort();
        	}
    	if(txtAddress.getText().trim()==null||txtAddress.getText().trim().equals("")){
        	MsgBox.showWarning(this,getResource("address"));
        	SysUtil.abort();
        	}
    	if(txtPerson.getText().trim()==null||txtPerson.getText().trim().equals("")){
        	MsgBox.showWarning(this,getResource("person"));
        	SysUtil.abort();
        	}
    	String isAuditstr = "";
    	for (int i = 0; i<editData.getAuditResult().size();i++){
    		FDCSplQualificationAuditEntryInfo info = editData.getAuditResult().get(i);
    		if(info.isIsAudit()){
    			if(null == info.getGrade()){
    				FDCMsgBox.showWarning(this,getResource("supplierGrade"));
    				abort();
    			}
    		}else{
    			isAuditstr += info.getSupplierType().getName()+",";
    		}
    	}
    	if(isAuditstr.length() > 0){
		  int yes=MsgBox.showConfirm2New(this, isAuditstr+getResource("isReview"));
		  if(yes > 0){
			SysUtil.abort();
		  }
    	}
    }
    /**
	 * @description		初始界面设置
	 * @author			梁良		
	 * @createDate		2010-11-7
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void initButtonStyle() throws Exception{
     	/*
     	 * 给按钮设置图标
     	 */
		btnSupplier.setIcon(EASResource.getIcon("imgTbtn_check"));
		btnTemplet.setIcon(EASResource.getIcon("imgTbtn_copy"));
		menuItemSupplier.setIcon(EASResource.getIcon("imgTbtn_check"));
		menuItemTemplet.setIcon(EASResource.getIcon("imgTbtn_copy"));
		menuItemNextPerson.setIcon(EASResource.getIcon("imgTbtn_nextactor"));
		actionSupplierInfo.setVisible(true);
		actionSupplierInfo.setEnabled(true);
		actionTemplet.setVisible(true);
		actionTemplet.setEnabled(true);
		actionAuditResult.setVisible(false);
		actionAuditResult.setEnabled(false);
		/*
		 *  显示真实姓名
		 */
		prmtCreator.setDisplayFormat("$name$");
		prmtAuditor.setDisplayFormat("$name$");
		/*
		 * 供应商名称不能操作
		 */
		txtSupplierName.setEnabled(false);
		/*
		 * 服务类型不能编辑
		 */
		prmtSupplierType.setEditable(false);
		if(null == prmtCreator.getData()){
			prmtCreator.setData(SysContext.getSysContext().getCurrentUserInfo());
			pkCreateTime.setValue(new Date());
		}
		if(null == pkBizDate.getValue()){
			pkBizDate.setValue(new Date());
			editData.setBusinessDate(new Date());
		}
		/*
		 * 关于分录表的设置
		 */
		this.txtScore.setMaximumValue(FDCHelper.ONE_HUNDRED);
		this.txtScore.setMinimumValue(FDCHelper.ZERO);
		KDTableHelper.updateEnterWithTab(kdtReviewResult, false);
		kdtReviewResult.getActionMap().put(KDTAction.SELECT_DOWN_CELL,null);
		kdtReviewResult.getActionMap().put(KDTAction.SELECT_UP_CELL,null);
		/*
		 * 数字靠右
		 */
		this.kdtReviewResult.getColumn("raction").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//		if(FDCBillStateEnum.AUDITTED.equals(editData.getState())){
//			this.actionRemove.setEnabled(false);
//			this.actionUnAudit.setEnabled(true);
//			this.btnUnAudit.setEnabled(true);
//			this.actionAudit.setEnabled(false);
//			this.btnEdit.setEnabled(false);
//			this.menuItemEdit.setEnabled(false);
//		} 
//		if(FDCBillStateEnum.AUDITTING.equals(editData.getState())){
//			this.actionEdit.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//			this.actionUnAudit.setEnabled(true);
//		}
		/*
		 * 锁表
		 */
		getDetailTable().getColumn("primaryState").getStyleAttributes().setLocked(true);
		getDetailTable().getColumn("raction").getStyleAttributes().setLocked(true);
		getDetailTable().getColumn("supplierGrade").getStyleAttributes().setLocked(true);
		this.kdtTemplateBill.getColumn("dimension").getStyleAttributes().setLocked(true);
		this.kdtTemplateBill.getColumn("target").getStyleAttributes().setLocked(true);
		this.kdtTemplateBill.getColumn("proportion").getStyleAttributes().setLocked(true);
		this.kdtTemplateBill.getColumn("standard").getStyleAttributes().setLocked(true);
		this.kdtTemplateBill.getColumn("syndicDept").getStyleAttributes().setLocked(true);
		this.kdtTemplateBill.getColumn("syndicTime").getStyleAttributes().setLocked(true);
	}
	/**
	 * 
	 * @description		初始模板
	 * @author			梁良		
	 * @createDate		2010-11-24
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	private void getTemplate() throws Exception{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));	 
		view.getSelector().add(new SelectorItemInfo("state"));	 
		view.getSelector().add(new SelectorItemInfo("phase"));	 
        view.getSelector().add(new SelectorItemInfo("guideEntry.*"));     
        view.getSelector().add(new SelectorItemInfo("creator.*"));   
        view.getSelector().add(new SelectorItemInfo("guideEntry.splAuditIndex.*"));  
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("phase",AppraisePhaseEnum.QUAL_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isStart",Boolean.TRUE));
		view.setFilter(filter);
		SupplierAppraiseTemplateCollection sic = SupplierAppraiseTemplateFactory.getRemoteInstance().getSupplierAppraiseTemplateCollection(view);
		Iterator it = (Iterator)sic.iterator();
		while(it.hasNext()){
			SupplierAppraiseTemplateInfo info = (SupplierAppraiseTemplateInfo)it.next();
			if(info.getGuideEntry().size() == 0  ){
				continue;
			}
			fillInTemplate(info.getGuideEntry());
			break;
		 } 
	}
	/**
	 * @description		
	 * @author				
	 * @createDate		2010-11-22
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void onShow() throws Exception {
		super.onShow();
	}
	/**
	 * @description		将附件列表的附件显示在文本框里
	 * @author				
	 * @createDate		2010-11-5
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void fillAttachmentList() throws Exception{
		String boID = getSelectBOID();
		/*
		 * 附件ID为空直接返回
		 */
		if(boID == null) {
			return ;
		}
		SelectorItemCollection itemColl = new SelectorItemCollection();
		itemColl.add(new SelectorItemInfo("id"));
		itemColl.add(new SelectorItemInfo("attachment.name"));
		itemColl.add(new SelectorItemInfo("attachment.id"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(itemColl);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", getSelectBOID()));
		view.setFilter(filter);
		BoAttchAssoCollection boAttchColl = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		String attchStrt = getResource("attachment")+"    ";
		if(boAttchColl != null && boAttchColl.size() > 0) {
			for(int i = 0; i < boAttchColl.size(); i++) {
				AttachmentInfo attachment = (AttachmentInfo) boAttchColl.get(i).getAttachment();
				attchStrt += attachment.getName() +"; ";
			}
		}
		contAttachment.setBoundLabelText(attchStrt);
	}
	protected IObjectValue createNewData() {
		FDCSplQualificationAuditBillInfo info = new FDCSplQualificationAuditBillInfo();
		try {
			info.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		return info;
	}
	
	/**
	 * @description		给供应商类型绑定F7
	 * @author			梁良		
	 * @createDate		2010-11-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void setQuery() {
		this.prmtSupplierType.setEnabledMultiSelection(true);
		prmtSupplierType.setDisplayFormat("$name$");
		prmtSupplierType.setCommitFormat("$name$");
		prmtSupplierType.setEditFormat("$name$");
		this.prmtSupplierType.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7SupplierServiceTypeQuery");
	}
    /**
     * @description		设置供应商类型和增加分录行
     * @author			梁良		
     * @createDate		2010-11-4
     * @param	
     * @return					
     *	
     * @version			EAS7.0
     * @see						
     */
    public void setSupType(){
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
					    row.getCell("primaryState").setValue(grade);	
					    break;
					    }
					}	 
				}    		 
		        row.getCell("type").setValue(info);
				row.getCell("type").setUserObject(info);
				row.getCell("whetherReview").setValue(Boolean.FALSE);
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
						    		row.getCell("primaryState").setValue(grade);	
						    		break;
					    		}
					    	}	 
					    }
					    row.getCell("type").setValue(info);
					    row.getCell("type").setUserObject(info);
						row.getCell("whetherReview").setValue(Boolean.FALSE);
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
			
    /**
	 * 
	 * @description		判断选择的供应商类型是否已经添加
	 * @author			梁良		
	 * @createDate		2010-11-23
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */

	private boolean isSupplierType(String typeName) {
		IRow row = null;
		int length = getDetailTable().getRowCount();
		for(int i = 0 ; i<length;i++){
			row = getDetailTable().getRow(i);
			FDCSplServiceTypeInfo info = (FDCSplServiceTypeInfo)row.getCell("type").getValue();
			if(typeName.equals(info.getName())){
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @description		获取供应商考察结论分录中的供应商类型用于给供应商类型F7赋值
	 * @author			梁良		
	 * @createDate		2010-11-23
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	private Object[] getSupplierTypeObj() {
		IRow row = null;
		int length = getDetailTable().getRowCount();
		Object obj[] = new Object[length];
		for(int i = 0 ; i<length;i++){
			row = getDetailTable().getRow(i);
			FDCSplServiceTypeInfo info = (FDCSplServiceTypeInfo)row.getCell("type").getValue();
			obj[i]=info;
		}
        return obj;
	}
	/**
	 * 
	 * @description		初始分录并绑定F7Query
	 * @author			梁良		
	 * @createDate		2010-11-23
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */
    private void initTable(){
    	kdtReviewResult.checkParsed();
	    KDCheckBox isAutidor = new KDCheckBox();    	 
    	KDTDefaultCellEditor editor = new KDTDefaultCellEditor(isAutidor);
		getDetailTable().getColumn("whetherReview").setEditor(editor);
		/*
		 * 为供应商等级绑定F7
		 */
		KDBizPromptBox grade = new KDBizPromptBox();
		grade.setDisplayFormat("$name$");
		grade.setEditFormat("$name$");
		grade.setCommitFormat("$name$");
		grade.setEditable(false);
		grade.setRequired(true);
		grade.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7GradeSetUpQuery");
		editor = new KDTDefaultCellEditor(grade);
		getDetailTable().getColumn("supplierGrade").setEditor(editor); 
		/*
		 * 为服务类型绑定F7
		 */
		KDBizPromptBox type = new KDBizPromptBox();		 
		type.setDisplayFormat("$name$");
		type.setEditFormat("$name$");
		type.setCommitFormat("$name$");
		type.setEnabled(false);
        editor = new KDTDefaultCellEditor(type);
		getDetailTable().getColumn("type").setEditor(editor);
		/*
		 * 考察分数
		 */
		KDFormattedTextField textscore = new KDFormattedTextField();
		textscore.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		textscore.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		textscore.setPrecision(2);
		textscore.setMaximumValue(FDCHelper.ONE_HUNDRED);
		textscore.setMinimumValue(FDCHelper.ZERO);
        editor = new KDTDefaultCellEditor(textscore);
		getDetailTable().getColumn("raction").setEditor(editor);
		getDetailTable().getColumn("raction").getStyleAttributes().setNumberFormat("#0.00");
		getDetailTable().getColumn("raction").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kdtTemplateBill.checkParsed();
		/*
		 * 权重
		 */
		KDFormattedTextField proportion = new KDFormattedTextField();
		proportion.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		proportion.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		proportion.setPrecision(2);
		proportion.setMaximumValue(FDCHelper.ONE_HUNDRED);
		proportion.setMinimumValue(FDCHelper.ZERO);
        editor = new KDTDefaultCellEditor(proportion);
        kdtTemplateBill.getColumn("proportion").setEditor(editor);
        kdtTemplateBill.getColumn("proportion").getStyleAttributes().setNumberFormat("#0.00");
        kdtTemplateBill.getColumn("proportion").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		/*
		 * 评审模板得分
		 */
        KDFormattedTextField descore = new KDFormattedTextField();
		descore.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		descore.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		descore.setPrecision(2);
		descore.setMaximumValue(FDCHelper.ONE_HUNDRED);
		descore.setMinimumValue(FDCHelper.ZERO);
        editor = new KDTDefaultCellEditor(textscore);
        kdtTemplateBill.getColumn("score").setEditor(editor);
        kdtTemplateBill.getColumn("score").getStyleAttributes().setNumberFormat("#0.00");
        kdtTemplateBill.getColumn("score").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
        kdtTemplateBill.getColumn("syndicTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		/*
		 * 设置合计行
		 */
		KDTFootManager footRowManager = kdtTemplateBill.getFootManager();
		IRow footRow = null;
		/*
		 * 评审人
		 */
		KDBizPromptBox person  = new KDBizPromptBox();
		person.setDisplayFormat("$name$");
		person.setEditFormat("$name$");
		person.setCommitFormat("$name$");
		person.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
	    editor = new KDTDefaultCellEditor(person);
	    kdtTemplateBill.getColumn("syndic").setEditor(editor);
		if(footRowManager == null){
			footRowManager = new KDTFootManager(kdtTemplateBill);
			footRowManager.addFootView();
			kdtTemplateBill.setFootManager(footRowManager);
			footRow= footRowManager.addFootRow(0);
			footRow.setUserObject("FDC_PARAM_TOTALCOST");
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			kdtTemplateBill.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			kdtTemplateBill.getIndexColumn().setWidth(60);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			/*
			 * 设置到第一个可视行
			 */
			footRowManager.addIndexText(0, getResource("sum"));
		}else{
			footRow=kdtTemplateBill.getFootRow(0);
			if(footRow.getUserObject()==null||!footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")){
				footRow=kdtTemplateBill.addFootRow(1);
			}
		}
   }
   /**
    * @description		得到供应商等级
    * @author			梁良	
    * @createDate		2010-11-9
    * @param	
    * @return					
    *	
    * @version			EAS7.0
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
				while(iter.hasNext()){
					GradeSetUpInfo info = (GradeSetUpInfo)iter.next();
					obj[i++]=info.getName();
				}
			}
		} catch (BOSException e) {
			handUIException(e);
		}
		return obj;
	}
	/**
	 * @description		考察结论事件
	 * @author				
	 * @createDate		2010-11-12
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected void kdtReviewResult_editStopped(KDTEditEvent e)
			throws Exception {
		int index = e.getRowIndex();
	    IRow row = getDetailTable().getRow2(index);
	    Boolean b=(Boolean) row.getCell("whetherReview").getValue();
 
	    ICell cellGrade = row.getCell("supplierGrade");
	    ICell cellScore = row.getCell("raction");
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
	/**
	 * @description		
	 * @author			梁良		
	 * @createDate		2010-11-17
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private int[] getStartEnd(int ben ,int end ,String type,int longth) {
		IRow row = null;
		for(int i = 0 ; i < longth ;i ++){
			/*
			 * 取得所有的维度
			 */
			  row = kdtTemplateBill.getRow(i);
			  String Str = (String)row.getCell("dimension").getValue();
			  if(Str.equals(type)){
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
	 * @author			梁良		
	 * @createDate		2010-11-16
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void fillInTemplate(SupplierGuideEntryCollection sgec ) {
		this.kdtTemplateBill.checkParsed();
		kdtTemplateBill.removeRows();
		IRow row = null;  	
		Iterator it = (Iterator)sgec.iterator();
		while(it.hasNext()){
			row = kdtTemplateBill.addRow();
			SupplierGuideEntryInfo info = (SupplierGuideEntryInfo)it.next();			 
//			row.getCell("dimension").setValue(info.getGuideType());
			row.getCell("target").setValue(info.getSplAuditIndex().getName());
			row.getCell("proportion").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			row.getCell("proportion").setValue(info.getWeight().longValue()+"");
//			row.getCell("standard").setValue(info.getFullNum());
			row.getCell("score").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			row.getCell("score").setValue("0.00");
			row.getCell("syndic").setValue(SysContext.getSysContext().getCurrentUserInfo().getName());
			row.getCell("syndicDept").setValue(orgUnitInfo.getName());		
			row.getCell("syndicTime").setValue(new Date());
			row.getCell("info").setValue(info);
		}
		kdtTemplateBillUnite();
	}
	/**
	 * @description		合并单元格
	 * @author			梁良		
	 * @createDate		2010-11-17
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void kdtTemplateBillUnite() {
		kdtTemplateBill.checkParsed();
		KDTMergeManager mm =kdtTemplateBill.getMergeManager();
		int longth = kdtTemplateBill.getRowCount();
		Map map = new HashMap();
		IRow row = null;
		for(int i = 0 ; i < longth ;i ++){
			/*
			 * 取得所有的维度
			 */
			row = kdtTemplateBill.getRow(i);
			String type = (String)row.getCell("dimension").getValue();
			if(map.get(type) == null){
				map.put(type, Boolean.TRUE);
			}
		} 
		Set key = map.keySet();
		Iterator it = key.iterator();
		while(it.hasNext()){
			String type = (String)it.next();
			int ben =getStartEnd( -1 , -1 , type, longth)[0];
			int end =getStartEnd( -1 , -1 , type, longth)[1];
	        if(ben<end){
	        	mm.mergeBlock(ben,0,end,0,KDTMergeManager.SPECIFY_MERGE);
	        }
		}
		setFootRowSum();
	}
	/**
	 * @description		设置权重之和
	 * @author			梁良		
	 * @createDate		2010-11-18
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void setFootRowSum(){
		IRow footRow = kdtTemplateBill.getFootRow(0);
    	BigDecimal sum = new BigDecimal("0");
    	for(int i= 0; i < kdtTemplateBill.getRowCount(); ++i ){
    		IRow tmpRow = kdtTemplateBill.getRow(i);
    		if(tmpRow.getCell("score").getValue() != null){
    			BigDecimal tmpwh = new BigDecimal(tmpRow.getCell("proportion").getValue().toString());
    			BigDecimal tmp = new BigDecimal(tmpRow.getCell("score").getValue().toString());
    			tmpwh=tmpwh.divide(FDCHelper.ONE_HUNDRED);
    			tmp=tmp.multiply(tmpwh);
    			sum = sum.add(tmp);
    		}else{
    			tmpRow.getCell("score").setValue(new BigDecimal("0.00"));
    			BigDecimal tmpwh = new BigDecimal(tmpRow.getCell("proportion").getValue().toString());
    			BigDecimal tmp = new BigDecimal(tmpRow.getCell("score").getValue().toString());
    			tmpwh=tmpwh.divide(FDCHelper.ONE_HUNDRED);
    			tmp=tmp.multiply(tmpwh);
    			sum = sum.add(tmp);
    		}
    	}
    	footRow.getCell("score").setValue(sum);
	}
	
	/**
	 * @description		模板分录事件
	 * @author			梁良		
	 * @createDate		2010-11-18
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	 protected void kdtTemplateBill_editStopped(KDTEditEvent e) throws Exception{
		 int index = e.getRowIndex();
		  IRow row  = null;
		  if( e.getValue() instanceof UserInfo){		  
			  row = kdtTemplateBill.getRow(index);
			  UserInfo user = (UserInfo)e.getValue();
			  SelectorItemCollection sic = new SelectorItemCollection();
			  sic.add(new SelectorItemInfo("*"));
			  sic.add(new SelectorItemInfo("defOrgUnit.*"));
			  user = UserFactory.getRemoteInstance().getUserInfo(new ObjectUuidPK(user.getId().toString()),sic);
			  row.getCell("syndic").setValue(user.getName());
			  row.getCell("syndicDept").setValue(user.getDefOrgUnit().getName());
		  }
		  setFootRowSum();
	}
   
    /**
     * @description		供应商类型事件
     * @author			梁良		
     * @createDate		2010-12-9
     * @param	
     * @return					
     *	
     * @version			EAS7.0
     * @see						
     */
    protected void prmtSupplierType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	setSupType();
    }
	/**
	 * @description		对供应商类型的F7Query设置过滤条件
	 * @author			梁良		
	 * @createDate		2010-12-8
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
    protected void prmtSupplierType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    	EntityViewInfo view = null;
    	/*
    	 * 过虑条件
    	 */
		FilterInfo filter = null;
		/*
		 * 重置查询语句
		 */
		prmtSupplierType.getQueryAgent().resetRuntimeEntityView();
		
		if(null == prmtSupplierType.getEntityViewInfo()){
			/*
			 * 新建
			 */
			view = new EntityViewInfo();
		}else{
			/*
			 * 获取
			 */
			view = prmtSupplierType.getEntityViewInfo();
		}
		prmtSupplierType.setEntityViewInfo(view);
	
    }

    /**
     * @description		跳转到供应商信息
     * @author			梁良	
     * @createDate		2010-11-8
     * @param	
     * @return					
     *	
     * @version			EAS7.0
     * @see						
     */
    public void actionSupplierInfo_actionPerformed(ActionEvent e)
    		throws Exception {
    	super.actionSupplierInfo_actionPerformed(e);
    	UIContext uiContext = new UIContext(this);
		SupplierStockInfo info = editData.getSupplier();
		uiContext.put(UIContext.ID, info.getId().toString());
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB); 
 		IUIWindow uiWindow = uiFactory.create(SupplierStockEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
    }
    /**
     * @description		应用模板
     * @author				
     * @createDate		2010-11-11
     * @param	
     * @return					
     *	
     * @version			EAS7.0
     * @see						
     */
    public void actionTemplet_actionPerformed(ActionEvent e) throws Exception {
    	if(null != editData.getState()){
    		if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
    			editData = FDCSplQualificationAuditBillFactory.getRemoteInstance().getFDCSplQualificationAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
    		}
    		if(editData.getState().equals(FDCBillStateEnum.AUDITTED)){
    			MsgBox.showWarning(this, getResource("changeTemplet"));
    			SysUtil.abort();
    		}
    	}
    	if(null != editData.getState() && editData.getState().equals(FDCBillStateEnum.AUDITTED)){
    		MsgBox.showWarning(this,getResource("changeTemplet"));
    		SysUtil.abort();
    	}
    	if(kdtTemplateBill.getRowCount()>1){
    		int yes=MsgBox.showConfirm2New(this, getResource("template"));
			if(yes>0){
				SysUtil.abort();
			}
    	}
    	if(kdtTemplateBill.getRowCount()<=0){
			MsgBox.showWarning(this,getResource("templateNull"));
			SysUtil.abort();
		}
    	super.actionTemplet_actionPerformed(e);
    	UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		SupplierQuaReviewEditUI evaluate =(SupplierQuaReviewEditUI)this;  
		uiContext.put("Evaluate", evaluate);	
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL); 
 		IUIWindow uiWindow = uiFactory.create(F7TemplatelistUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
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
        super.actionSave_actionPerformed(e);
        kdtTemplateBillUnite();
        setAuditBtn();
        
    }
    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(null == editData.getName()){
  			editData.setName(UUID.randomUUID().toString());
  		}
  		if(null == editData.getNumber()){
        	editData.setNumber(UUID.randomUUID().toString());
  		}
        IObjectPK pk = null ;
        if(null == editData.getId()){
            pk = FDCSplQualificationAuditBillFactory.getRemoteInstance().addnew(editData);
            editData.setId((BOSUuid) pk.getKeyValue("id"));
        	}
        super.actionSubmit_actionPerformed(e);
        editData.setState(FDCBillStateEnum.SUBMITTED);
        this.setOprtState(OprtState.VIEW);
        kdtTemplateBillUnite();
        setAuditBtn();
        btnSave.setEnabled(false);
        menuItemSave.setEnabled(false);
        btnRemove.setEnabled(true);
        menuItemRemove.setEnabled(true);
    }
    
    protected void doAfterSubmit(IObjectPK arg0) throws Exception {
    	showSubmitSuccess();
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
    	if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
    		editData = FDCSplQualificationAuditBillFactory.getRemoteInstance().getFDCSplQualificationAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
     	}
    	if(editData.getState().equals(FDCBillStateEnum.AUDITTED)){
    		MsgBox.showWarning(this,getResource("notEdit"));
    		SysUtil.abort();
    	}
        super.actionEdit_actionPerformed(e);
        setAuditBtn();
        btnTemplet.setEnabled(true);
        menuItemTemplet.setEnabled(true);
        btnRemove.setEnabled(true);
        menuItemRemove.setEnabled(true);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	/*
    	 * 判断是否进入工作流
    	 */
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());
    	/*
    	 * 如果进入工作流后页面状态不会刷新 手动刷新下
    	 */
    	if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
    		editData = FDCSplQualificationAuditBillFactory.getRemoteInstance().getFDCSplQualificationAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
    	}
		boolean b = editData != null
		&& editData.getState() != null
		&& !editData.getState().equals(FDCBillStateEnum.AUDITTING)&& !editData.getState().equals(FDCBillStateEnum.AUDITTED);
        if (!b) {
		    MsgBox.showWarning(this, getResource("notRemove"));
		    SysUtil.abort();
        }
		int yes=MsgBox.showConfirm2New(this, getResource("isRemove"));
		if(yes > 0){
			SysUtil.abort();
		}
    	SupplierStockInfo supplier = new SupplierStockInfo();
    	supplier.setId(editData.getSupplier().getId());
    	supplier.setName(editData.getSupplier().getName());  
    	getBizInterface().delete(new ObjectUuidPK(editData.getId().toString()));
    	editData.clear();
    	editData = (FDCSplQualificationAuditBillInfo)createNewData();
    	editData.setSupplier(supplier);
    	this.setOprtState(OprtState.ADDNEW);
    	onLoad();
    	this.actionRemove.setEnabled(false);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    	AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = getSelectBOID();
        if(boID == null){
        	SysUtil.abort();
        }
        String id  =  getSelectBOID();
        /*
         * 进入工作流中不能新增附件
         */
  		ProcessInstInfo instInfo = null;
  		ProcessInstInfo[] procInsts = null;
  		try {
  			IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
  			procInsts = service2.getProcessInstanceByHoldedObjectId(id);
  		} catch (BOSException ex) {
  			ExceptionHandler.handle(ex);
  		}
  		for (int i = 0, n = procInsts.length; i < n; i++) {
  			/*
  			 * 流程挂起时也显示流程图
  			 */
  			if ("open.running".equals(procInsts[i].getState())
  					|| "open.not_running.suspended".equals(procInsts[i].getState())) {
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
        /*
         * boID 是 与附件关联的 业务对象的 ID
         */
        acm.showAttachmentListUIByBoID(boID,this,isEdit);
        fillAttachmentList();
    }
    
    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	/*
    	 * 如果进入工作流后页面状态不会刷新 手动刷新下
    	 */
    	if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
    		editData = FDCSplQualificationAuditBillFactory.getRemoteInstance().getFDCSplQualificationAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
    	}
    	if(!editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
		    FDCMsgBox.showWarning(this,getResource("notState"));
	    	abort();
    	}
    	getUIContext().put("ID",editData.getId().toString());
        super.actionAudit_actionPerformed(e);
        setAuditBtn();
        actionTemplet.setEnabled(false);
        actionEdit.setEnabled(false);
        actionRemove.setEnabled(false);
        Object[] obj=getSupplierTypeObj();
    	prmtSupplierType.setDataNoNotify(obj);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    	/*
    	 * 如果进入工作流后页面状态不会刷新 手动刷新下
    	 */
    	if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
    		editData = FDCSplQualificationAuditBillFactory.getRemoteInstance().getFDCSplQualificationAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
    	}
        super.actionUnAudit_actionPerformed(e);
        kdtTemplateBillUnite();
        setAuditBtn();
        actionRemove.setEnabled(true);
        actionEdit.setEnabled(true);
    }
    
	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	/**
	 * @description		获取远程接口
	 * @author			梁良
	 * @createDate		2010-11-5
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return FDCSplQualificationAuditBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtReviewResult;
	}

	protected KDTextField getNumberCtrl() {
		KDTextField  kdtTextField1 = new KDTextField();
		if (null == editData.getNumber()) {
			editData.setNumber(UUID.randomUUID().toString());
		}
		kdtTextField1.setText(editData.getNumber());
		return kdtTextField1;
	}
	
	protected IObjectValue createNewDetailData(KDTable table) {
		return new FDCSplQualificationAuditBillInfo();
	}
	/**
     * @description		填充值
     * @author			梁良	
     * @createDate		2010-11-25
     * @param	
     * @return					
     *	
     * @version			EAS7.0
     * @see						
     */
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
	    return sic;
	}
}