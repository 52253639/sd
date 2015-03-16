
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

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
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
import com.kingdee.bos.ui.face.UIException;
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
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.util.KDDetailedArea;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaUtil;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.invite.supplier.AppraisePhaseEnum;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditEntyCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditEntyInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;


/**
 * @(#)			SupplierPerformEvalulationEditUI.java				
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		履约评估编辑界面
 *		
 * @author		胥凯
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see						
 */
public class SupplierPerformEvalulationEditUI extends
			AbstractSupplierPerformEvalulationEditUI {

	/*
	 * 序列号
	 */
	private static final long serialVersionUID = 665128500374330252L;
	/*
	 * 资源文件路径
	 */
	private static final String RESOURCEPATH="com.kingdee.eas.fdc.invite.supplier.SupplierResource";
	/*
	 * 状态标识
	 */
	private String state = null;
	
	private static final Logger logger = CoreUIObject
	.getLogger(SupplierPerformEvalulationEditUI.class);
	
	public SupplierPerformEvalulationEditUI() throws Exception {
		super();
	}



	/**
	 * @description		初始化分录的类型
	 * @author			胥凯	
	 * @createDate		2010-11-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void initSupplierNameF7(){
		if(null != editData){
			FDCSplKeepAuditEntyCollection seic  = editData.getAuditBill();
			Object obj[] = new Object[seic.size()];
			for(int i = 0 ; i < seic.size() ; i ++){
				FDCSplKeepAuditEntyInfo info  = seic.get(i);
				if(null == info.getSupplierType()){
					continue;
				}	
				obj[i]=(FDCSplServiceTypeInfo)info.getSupplierType();
			}
			this.prmtSupplierType.setDataNoNotify(obj);
		}
	}

	public void loadFields() {
		this.kdtTemplateBill.checkParsed();
		this.kdtSupplierType.checkParsed();
		super.loadFields();
	}
	protected IObjectValue createNewData() {
		return new FDCSplKeepContractAuditBillInfo();
	}

	/**
	 * @description		初始化按钮，为F7设置Query	
	 * @author			胥凯	
	 * @createDate		2010-11-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void initButton(){
		this.menuItemTemplet.setEnabled(true);
		this.menuItemAudit.setVisible(true);
		this.menuItemUnAudit.setVisible(true);
		this.pkPerformDate.setEditable(false);
		/*
		 * 设置KDContainer的收缩属性
		 */
		this.contConter.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
		this.contPinggu.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
		this.menuItemAddNew.setVisible(false);
		this.btnAuditResult.setVisible(false);
		this.menuItemAuditResult.setVisible(false);
		this.prmtSupplierType.setEditable(false);
		this.prmtProject.setEditable(false);
		this.prmtContact.setEditable(false);
		this.btnAudit.setVisible(false);
		this.btnUnAudit.setEnabled(false);
		this.btnTemplet.setEnabled(true);
		/*
		 * 如果时间为空则放入当前时间
		 */
		if(this.pkPerformDate.getValue() == null){
			Date nows = new Date();
			this.pkPerformDate.setValue(nows);
		}
		this.btnUnAudit.setVisible(true);
		this.btnAudit.setVisible(true);
		this.prmtCreator.setDisplayFormat("$name$");
		this.prmtCreator.setEditFormat("$name$");
		this.prmtCreator.setCommitFormat("$name$");
		this.prmtAuditor.setDisplayFormat("$name$");
		this.prmtAuditor.setEditFormat("$name$");
		this.prmtAuditor.setCommitFormat("$name$");
		/*
		 * 绑定供应商类型F7
		 */
		this.prmtSupplierType.setDisplayFormat("$name$");
		this.prmtSupplierType.setEditFormat("$name$");
		this.prmtSupplierType.setCommitFormat("$name$");
		this.prmtSupplierType
			.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7SupplierServiceTypeQuery");		
		/*
		 * 设置供应商类型为多选
		 */
		this.prmtSupplierType.setEnabledMultiSelection(true); 
		this.prmtProject.setDisplayFormat("$name$");
		this.prmtProject.setEditFormat("$name$");
		this.prmtProject.setCommitFormat("$name$");
		this.prmtProject
			.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");	
		this.prmtContact.setDisplayFormat("$name$");
		this.prmtContact.setCommitFormat("$name$");
		this.prmtContact.setEditFormat("$name$");
		this.prmtContact.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7ContractQuery");
		/*
		 * 给制单人，制单时间赋值
		 */
		if(null == prmtCreator.getData()){
			prmtCreator.setData(SysContext.getSysContext().getCurrentUser());
			pkEditDate.setValue(new Date());
		}
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
		this.txtGrade.setMaximumValue(FDCHelper.ONE_HUNDRED);
		this.txtGrade.setMinimumValue(FDCHelper.ZERO);
		
		if(getOprtState().equals("FINDVIEW")){
			this.actionUnAudit.setEnabled(false);
		}
	}
	protected KDTextField getNumberCtrl() {
		KDTextField  k = new KDTextField();
		if (null == editData.getNumber()) {
			editData.setNumber(UUID.randomUUID().toString());
		}
		k.setText(editData.getNumber());
		return k;
	}
	/**
	 * @description		将附件列表的附件显示在文本框里
	 * @author			胥凯	
	 * @createDate		2010-11-9
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
		String attchStrt = getResource("attachment")+"    ";
		if (boAttchColl != null && boAttchColl.size() > 0) {
				for (int i = 0; i < boAttchColl.size(); i++) {
				AttachmentInfo attachment = (AttachmentInfo) boAttchColl.get(i)
						.getAttachment();
				attchStrt += attachment.getName() +"; ";
			}
		}
		contAttachment.setBoundLabelText(attchStrt);
	}
	/**
	 * @description		设置按钮
	 * @author			胥凯	
	 * @createDate		2010-11-28
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
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
	 * @description		获取资源文件
	 * @author			胥凯	
	 * @createDate		2010-12-8
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	private String getResource(String key) {
		return EASResource.getString(RESOURCEPATH, key);
	}

	/**
	 * @description		应用合同点击事件
	 * @author			胥凯	
	 * @createDate		2010-11-14
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void actionTemplet_actionPerformed(ActionEvent e) throws Exception {
		/*
		 * 如果是审批状态则不能更改模板
		 */
		if(null != editData.getState()){
    		if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
    			editData = FDCSplKeepContractAuditBillFactory.getRemoteInstance().getFDCSplKeepContractAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
    		}
    		if(editData.getState().equals(FDCBillStateEnum.AUDITTED)){
    			MsgBox.showWarning(this, getResource("changeTemplet"));
    			SysUtil.abort();
    		}
    	}
		int count = this.kdtTemplateBill.getRowCount();
		/*
		 * 如果模板为空则提示 
		 */
		if(count > 0){
			int yes=MsgBox.showConfirm2New(this, getResource("template"));
			if(yes == 0){
				toTemplate();
			}else{
				SysUtil.abort();
			}
		}else{
				MsgBox.showWarning(this,getResource("templateNull"));
				SysUtil.abort();
		}
	}
	 /**
	     * @description		打开模板选择界面
	     * @author			胥凯	
	     * @createDate		2010-11-18
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private void toTemplate() throws UIException{
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		SupplierPerformEvalulationEditUI evaluate =(SupplierPerformEvalulationEditUI)this;  
		uiContext.put("Evaluate", evaluate);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL); 
		IUIWindow uiWindow = uiFactory.create(F7TemplatelistUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	/**
	 * @description		跳转到合同信息界面的事件
	 * @author			胥凯	
	 * @createDate		2010-11-10
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void actionContract_actionPerformed(ActionEvent e) throws Exception {
		if(null == this.prmtContact.getData()){
			MsgBox.showWarning(this, getResource("contract"));
			SysUtil.abort();
		}else{
			UIContext uiContext = new UIContext(this);
			editData.setContract((ContractBillInfo)this.prmtContact.getData());
			ContractBillInfo contract = editData.getContract();
			uiContext.put(UIContext.ID, contract.getId().toString());
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			IUIWindow uiWindow = uiFactory.create(ContractBillEditUI.class.getName(), uiContext, null, "FINDVIEW");
			uiWindow.show();
		}
	}
	/**
	 * @description		根据选择的供应商类型设置分录的供应商类型
	 * @author			胥凯	
	 * @createDate		2010-11-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void setSupplierType() {
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
				    		  row.getCell("beforeState").setValue(grade);	
				    		  break;
				    	  }
				  }	 
			}    		 
			row.getCell("type").setValue(info);
			row.getCell("type").setUserObject(info);
			row.getCell("isEvalulation").setValue(Boolean.FALSE);
			row.getCell("beforeState").getStyleAttributes().setLocked(true);
			row.getCell("point").getStyleAttributes().setLocked(true);
			row.getCell("isGrade").getStyleAttributes().setLocked(true);
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
								   row.getCell("beforeState").setValue(grade);	
								   break;
							   }
						   }	 
					   }
					   row.getCell("type").setValue(info);
					   row.getCell("isEvalulation").setValue(Boolean.FALSE);
					   row.getCell("beforeState").getStyleAttributes().setLocked(true);
					   row.getCell("point").getStyleAttributes().setLocked(true);
					   row.getCell("isGrade").getStyleAttributes().setLocked(true);
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
	     * @description		
	     * @author			胥凯	
	     * @createDate		2010-11-23
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private boolean isSupplierType(String typeName) {
		IRow row = null;
		boolean flag = false;
		int length = getDetailTable().getRowCount();
		for(int i = 0 ; i<length;i++){
			row = getDetailTable().getRow(i);
			FDCSplServiceTypeInfo info = (FDCSplServiceTypeInfo)row.getCell("type").getValue();
			if(typeName.equals(info.getName())){
				 flag = true;
			}
		}
		return flag;
	}
	 /**
	     * @description		取得分录的所有服务类型
	     * @author			胥凯	
	     * @createDate		2010-11-22
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
	 * @description		供应商类型值变化事件
	 * @author			胥凯	
	 * @createDate		2010-11-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected void prmtSupplierType_dataChanged(DataChangeEvent e)
		throws Exception {
		setSupplierType();
	}
	/**	
	 * @description		跳转到供应商信息界面的事件
	 * @author			胥凯	
	 * @createDate		2010-11-10
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void actionSupplier_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		SupplierStockInfo info = editData.getSupplier();
		uiContext.put(UIContext.ID, info.getId().toString());
		uiContext.put("SupplierInfo", info);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB); 
		IUIWindow uiWindow = uiFactory.create(SupplierStockEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
}
	/**
	 * @description		选择合同后  如果有与合同项对应的工程项目  则将该工程项目名称放入项目名称文本框
	 * @author			胥凯	
	 * @createDate		2010-11-9
	 * @param	
	 * @return					
	 *	
 	* @version			EAS7.0
 	* @see						
 	*/
	protected void prmtContact_dataChanged(DataChangeEvent e) throws Exception {
		ContractBillInfo info = (ContractBillInfo) this.prmtContact.getData();
		if(info == null)
			return;
		CurProjectInfo projectInfo = (CurProjectInfo)CurProjectFactory.getRemoteInstance()
			.getCurProjectInfo(new ObjectUuidPK(info.getCurProject().getId()));
		this.prmtProject.setData(projectInfo);
	}
	/**
	 * @description		保存时的验证
	 * @author			胥凯	
	 * @createDate		2010-11-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected void verifyInputForSave() throws Exception {
		validateInPut();
	}
	 /**
	     * @description		提交的验证
	     * @author			胥凯	
	     * @createDate		2010-11-18
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	protected void verifyInputForSubmint() throws Exception {
		validateInPut();
	}
	
	 /**
	     * @description		验证界面上输入信息
	     * @author			胥凯	
	     * @createDate		2010-12-9
	     * @param	
	     * @return					
	     *	
	     * @version			EAS1.0
	     * @see						
	     */
	private void validateInPut(){
		if(this.prmtContact.getText() == null || "".equals(this.prmtContact.getText())){
			MsgBox.showWarning(this, getResource("contract"));
			SysUtil.abort();
		}
		if(this.prmtSupplierType.getText() == null || "".equals(this.prmtSupplierType.getText())){
			MsgBox.showWarning(this, getResource("supplierType")+getResource("notNull"));
			SysUtil.abort();
		}
		String isEvaluate = "";
		boolean flag = false;
		for (int i = 0; i<editData.getAuditBill().size();i++){
    		FDCSplKeepAuditEntyInfo info = editData.getAuditBill().get(i);
    		if(info.isIsAudit()){
    			if(null == info.getGrade()){
    				FDCMsgBox.showWarning(this,getResource("supplierGrade"));
    				abort();
    			}
    		}else{
    			flag = true;
    			isEvaluate +=info.getSupplierType().getName()+",";
    		}
    	}
		if(flag){
			int yes=MsgBox.showConfirm2New(this, isEvaluate+getResource("isEvaluate"));
			if(yes>0){
				SysUtil.abort();
			}
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.state = getOprtState();
		initButton();
		fillAttachmentList();
		initEntry();
		if(getOprtState().equals(OprtState.ADDNEW)){
			initSupplierName();
			getTemplate();
		}else{
			initSupplierNameF7();
		}
	}
	 /**
	     * @description		加载页面时默认模板
	     * @author			胥凯	
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
		filter.getFilterItems().add(new FilterItemInfo("phase",AppraisePhaseEnum.EVAL_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("isStart",Boolean.TRUE));
		view.setFilter(filter);
		SupplierAppraiseTemplateCollection sic = SupplierAppraiseTemplateFactory.getRemoteInstance().getSupplierAppraiseTemplateCollection(view);
		Iterator it = (Iterator)sic.iterator();
		while(it.hasNext()){
			 SupplierAppraiseTemplateInfo info = (SupplierAppraiseTemplateInfo)it.next();
			 if(info.getGuideEntry().size() == 0 || !info.getState().equals(FDCBillStateEnum.AUDITTED)){//没有分录的和未审批的不要加入
				 continue;
			 }
			 fillInTemplate(info.getGuideEntry());
			 break;
		}
	}
	/**
	 * @description		初始化供应商名称
	 * @author			胥凯	
	 * @createDate		2010-11-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void initSupplierName() throws Exception{
		if( null != getUIContext().get("SupplierInfo")){
			SupplierStockInfo info =(SupplierStockInfo) getUIContext().get("SupplierInfo");
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
	 * @description		初始化分录
	 * @author			胥凯	
	 * @createDate		2010-11-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	private void initEntry(){
		getDetailTable().checkParsed(); 
		/*
		 * 锁定分录的上下键
		 */
		this.kdtSupplierType.getActionMap().put(KDTAction.SELECT_DOWN_CELL,null);
		this.kdtSupplierType.getActionMap().put(KDTAction.SELECT_UP_CELL,null);
		this.kdtTemplateBill.getActionMap().put(KDTAction.SELECT_DOWN_CELL,null);
		this.kdtTemplateBill.getActionMap().put(KDTAction.SELECT_UP_CELL,null);
		KDCheckBox isAutidor = new KDCheckBox();
		isAutidor.setRequired(true);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(isAutidor);
		getDetailTable().getColumn("isEvalulation").setEditor(editor);
		getDetailTable().getColumn("isEvalulation").setRequired(true);
		KDBizPromptBox grade = new KDBizPromptBox();
		grade.setDisplayFormat("$name$");
		grade.setEditFormat("$name$");
		grade.setCommitFormat("$name$");
		grade.setEditable(false);
		grade.setRequired(true);
		grade.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7GradeSetUpQuery");
		editor = new KDTDefaultCellEditor(grade);
		getDetailTable().getColumn("isGrade").setEditor(editor);
		KDBizPromptBox type = new KDBizPromptBox();		 
		type.setDisplayFormat("$name$");
		type.setEditFormat("$name$");
		type.setCommitFormat("$name$");
		type.setEnabled(false);
		editor = new KDTDefaultCellEditor(type);
		getDetailTable().getColumn("type").setEditor(editor);
		KDFormattedTextField textscore = new KDFormattedTextField();
		textscore.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		textscore.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		textscore.setPrecision(2);
		textscore.setMaximumValue(FDCHelper.ONE_HUNDRED);
		textscore.setMinimumValue(FDCHelper.ZERO);
		editor = new KDTDefaultCellEditor(textscore);
		getDetailTable().getColumn("point").setEditor(editor);
		getDetailTable().getColumn("point").getStyleAttributes().setNumberFormat("#0.00");
		getDetailTable().getColumn("point").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtTemplateBill.getColumn("auditTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		
		KDDetailedArea area = new KDDetailedArea(280, 250);
        area.setRequired(false);
        area.setEnabled(true);
        area.setMaxLength(500);
        kdtTemplateBill.getColumn("description").setEditor(new KDTDefaultCellEditor(area));
        getDetailTable().getColumn("description").setEditor(new KDTDefaultCellEditor(area));
		/*
		 * 设置合计行
		 * */
		setResult();
	}
	
	 /**
	     * @description		设置合计行
	     * @author			胥凯	
	     * @createDate		2010-12-9
	     * @param	
	     * @return					
	     *	
	     * @version			EAS1.0
	     * @see						
	     */
	private void setResult(){
		KDTFootManager footRowManager= kdtTemplateBill.getFootManager();
		IRow footRow = null;
		setTemplateInput();
		if(footRowManager==null){
			footRowManager = new KDTFootManager(kdtTemplateBill);
			footRowManager.addFootView();
			this.kdtTemplateBill.setFootManager(footRowManager);
			footRow= footRowManager.addFootRow(0);
			footRow.setUserObject("FDC_PARAM_TOTALCOST");
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			this.kdtTemplateBill.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			this.kdtTemplateBill.getIndexColumn().setWidth(60);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			/*
			 * 设置到第一个可视行
			 * */
			footRowManager.addIndexText(0, "合计");
		}else{
			footRow=kdtTemplateBill.getFootRow(0);
			if(footRow.getUserObject()==null||!footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")){
				footRow=kdtTemplateBill.addFootRow(1);
			}
		}
	}
	 /**
     * @description		
     * @author			胥凯	
     * @createDate		2010-12-2
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
	private void setBtnForSubmit(){
		this.setOprtState(OprtState.VIEW);
		this.btnSave.setEnabled(false);
		this.menuItemSave.setEnabled(false);
		this.btnAudit.setEnabled(true);
		this.menuItemAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(false);
		this.btnEdit.setEnabled(true);
		this.menuItemEdit.setEnabled(true);
		this.btnUnAudit.setVisible(true);
		this.menuItemUnAudit.setVisible(true);
		this.btnUnAudit.setEnabled(false);
		this.menuItemUnAudit.setEnabled(false);
	}
	 /**
	     * @description		初始化评审模板分录
	     * @author			胥凯	
	     * @createDate		2010-11-19
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private void setTemplateInput(){
		kdtTemplateBill.checkParsed();
		KDFormattedTextField txtScore = new KDFormattedTextField();
		txtScore.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtScore.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtScore.setPrecision(2);
		txtScore.setMaximumValue(FDCHelper.ONE_HUNDRED);
		txtScore.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(txtScore);
		kdtTemplateBill.getColumn("score").setEditor(editor);
		kdtTemplateBill.checkParsed();
		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtWeight.setPrecision(2);
		txtWeight.setMaximumValue(FDCHelper.ONE_HUNDRED);
		txtWeight.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		kdtTemplateBill.getColumn("purview").setEditor(weight);
		kdtTemplateBill.getColumn("purview").getStyleAttributes().setNumberFormat("#0.00");
		kdtTemplateBill.getColumn("score").getStyleAttributes().setNumberFormat("#0.00");
		kdtTemplateBill.getColumn("score").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kdtTemplateBill.getColumn("purview").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		KDBizPromptBox dept = new KDBizPromptBox();		 
		dept.setDisplayFormat("$name$");
		dept.setEditFormat("$name$");
		dept.setCommitFormat("$name$");
		dept.setEnabled(false);
		editor = new KDTDefaultCellEditor(dept);
		kdtTemplateBill.getColumn("auditCur").setEditor(editor);
		KDBizPromptBox person  = new KDBizPromptBox();
		person.setDisplayFormat("$name$");
		person.setEditFormat("$name$");
		person.setCommitFormat("$name$");
		person.setEditable(false);
		person.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
	    editor = new KDTDefaultCellEditor(person);
	    kdtTemplateBill.getColumn("auditPerson").setEditor(editor);
	}
	 /**
	     * @description		模板分录事件
	     * @author			胥凯	
	     * @createDate		2010-11-23
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	protected void kdtTemplateBill_editStopped(KDTEditEvent e) throws Exception {
		 int index = e.getRowIndex();
		 IRow row  = null;
		 if( e.getValue() instanceof UserInfo){		  
			  row = kdtTemplateBill.getRow(index);
			  UserInfo user = (UserInfo)e.getValue();
			  SelectorItemCollection sic = new SelectorItemCollection();
			  sic.add(new SelectorItemInfo("*"));
			  sic.add(new SelectorItemInfo("defOrgUnit.*"));
			  user = UserFactory.getRemoteInstance().getUserInfo(new ObjectUuidPK(user.getId().toString()),sic);
			  row.getCell("auditPerson").setValue(user.getName());
			  row.getCell("auditCur").setValue(user.getDefOrgUnit().getName());
		 }
		 setFootRowSum();
		 
		 KDDetailedAreaUtil.setWrapFalse(kdtTemplateBill.getCell(index, kdtTemplateBill.getColumnIndex("description")));
	}
	
	protected void kdtTemplateBill_tableClicked(KDTMouseEvent e) throws Exception {
		super.kdtTemplateBill_tableClicked(e);
		if (e.getType() == 1 && e.getButton() == 1 && e.getColIndex() == kdtTemplateBill.getColumnIndex("description")) {
			if (this.oprtState.equals(STATUS_VIEW)) {				
				KDDetailedAreaUtil.showDialog(this, kdtTemplateBill,250,200,500);
			}
		}
	}
	
	protected void kdtSupplierType_tableClicked(KDTMouseEvent e) throws Exception {
		super.kdtSupplierType_tableClicked(e);
		if (e.getType() == 1 && e.getButton() == 1 && e.getColIndex() == kdtSupplierType.getColumnIndex("description")) {
			if (this.oprtState.equals(STATUS_VIEW)) {				
				KDDetailedAreaUtil.showDialog(this, kdtSupplierType,250,200,500);
			}
		}
	}
	/**
	 * @description		分录的事件
	 * @author			胥凯	
	 * @createDate		2010-11-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected void kdtSupplierType_editStopped(KDTEditEvent e) throws Exception {
		int index = e.getRowIndex();
		IRow row = getDetailTable().getRow(index);
		Boolean b=(Boolean) row.getCell("isEvalulation").getValue(); 
	    ICell cellGrade = row.getCell("isGrade");
	    ICell cellScore = row.getCell("point");
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
	    
	    KDDetailedAreaUtil.setWrapFalse(kdtSupplierType.getCell(index, kdtSupplierType.getColumnIndex("description")));
	}
	 /**
	     * @description		将模板放入分录
	     * @author			胥凯	
	     * @createDate		2010-11-18
	     * @param			sgec SupplierGuideEntryCollection是SupplierGuideEntryInfo分录的一个集合
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
//			row.getCell("audit").setValue(info.getGuideType());//纬度
			row.getCell("auditIndex").setValue(info.getSplAuditIndex());//指标
			row.getCell("purview").setValue(info.getWeight());//权重
//			row.getCell("fullStarded").setValue(info.getFullNum());//满分标准
			row.getCell("auditPerson").setValue(SysContext.getSysContext().getCurrentUserInfo().getName());//评审人
			row.getCell("auditCur").setValue(orgUnitInfo.getName());//评审部门			
			row.getCell("auditTime").setValue(new Date());//时间
			row.getCell("info").setValue(info);//时间
		}
		kdtTemplateBillUnite();
		kdtTemplateBill.getColumn("audit").getStyleAttributes().setLocked(true);
		kdtTemplateBill.getColumn("auditIndex").getStyleAttributes().setLocked(true);
		kdtTemplateBill.getColumn("purview").getStyleAttributes().setLocked(true);
		kdtTemplateBill.getColumn("fullStarded").getStyleAttributes().setLocked(true);
		kdtTemplateBill.getColumn("auditTime").getStyleAttributes().setLocked(true);
	}
	
	 /**
	     * @description		合并单元格
	     * @author			胥凯	
	     * @createDate		2010-11-18
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private void kdtTemplateBillUnite() {
		KDTMergeManager mm =kdtTemplateBill.getMergeManager();
		int longth = kdtTemplateBill.getRowCount();
		Map map = new HashMap();
		IRow row = null;
		for(int i = 0 ; i < longth ;i ++){
			/*
			 * 取得所有的纬度
			 */
			  row = kdtTemplateBill.getRow(i);
			  String type = (String)row.getCell("audit").getValue();
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
	     * @author			胥凯	
	     * @createDate		2010-11-18
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private void setFootRowSum()
	{
		IRow footRow = kdtTemplateBill.getFootRow(0);
    	BigDecimal sum = new BigDecimal("0");
    	for(int i= 0; i < kdtTemplateBill.getRowCount(); ++i )
    	{
    		IRow tmpRow = kdtTemplateBill.getRow(i);
    		if(tmpRow.getCell("score").getValue() != null)
    		{
    			BigDecimal tmpwh = new BigDecimal(tmpRow.getCell("purview").getValue().toString());
    			BigDecimal tmp = new BigDecimal(tmpRow.getCell("score").getValue().toString());   
    			tmpwh=tmpwh.divide(FDCHelper.ONE_HUNDRED);
    			tmp=tmp.multiply(tmpwh);
    			sum = sum.add(tmp);
    		}else{
    			tmpRow.getCell("score").setValue(new BigDecimal("0.00"));
    			BigDecimal tmpwh = new BigDecimal(tmpRow.getCell("purview").getValue().toString());
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
	     * @author			胥凯	
	     * @createDate		2010-11-18
	     * @param	
	     * @return					
	     *	
	     * @version			EAS7.0
	     * @see						
	     */
	private int[] getStartEnd(int ben ,int end ,String type,int longth) {
		IRow row = null;
		for(int i = 0 ; i < longth ;i ++){
			//取得所有的纬度
			  row = kdtTemplateBill.getRow(i);
			  String Str = (String)row.getCell("audit").getValue();
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
	     * @description		
	     * @author			胥凯	
	     * @createDate		2010-11-24
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
	/**
     * @description		删除
     * @author			胥凯	
     * @createDate		2010-12-1
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		//检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());
    	//如果进入工作流后页面状态不会刷新 手动刷新下
    	if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
    		editData = FDCSplKeepContractAuditBillFactory.getRemoteInstance().getFDCSplKeepContractAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
    	}
		boolean b = editData != null
		&& editData.getState() != null
		&& !editData.getState().equals(FDCBillStateEnum.AUDITTING)&& !editData.getState().equals(FDCBillStateEnum.AUDITTED);
        if (!b) {
        	MsgBox.showWarning(this, EASResource.getString(RESOURCEPATH,"notRemove"));
        	SysUtil.abort();
        }
		int yes=MsgBox.showConfirm2New(this, EASResource.getString(RESOURCEPATH,"isRemove"));
		if(yes > 0){
			  SysUtil.abort();
		}
    	SupplierStockInfo supplier = new SupplierStockInfo();
    	supplier.setId(editData.getSupplier().getId());
    	supplier.setName(editData.getSupplier().getName());  
    	getBizInterface().delete(new ObjectUuidPK(editData.getId().toString()));
    	editData.clear();
    	this.kdtSupplierType.removeRows();
    	this.kdtTemplateBill.removeRows();
    	this.prmtSupplierType.setData(null);
    	editData = (FDCSplKeepContractAuditBillInfo)createNewData();
    	editData.setSupplier(supplier);
    	this.setOprtState(OprtState.ADDNEW);
    	onLoad();
    	this.actionRemove.setEnabled(false);
	}
	 /**
	     * @description		附件管理
	     * @author			胥凯	
	     * @createDate		2010-12-6
	     * @param	
	     * @return					
	     *	
	     * @version			EAS1.0
	     * @see						
	     */
	public void actionAttachment_actionPerformed(ActionEvent e)
		throws Exception {
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		String boID = getSelectBOID();
		if(boID == null)
		{
			SysUtil.abort();
		} 
        String id  =  getSelectBOID();
        /*
         * 进入工作流中不能新增附件
         */
		ProcessInstInfo instInfo = null;
		ProcessInstInfo[] procInsts = null;
		IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
		procInsts = service2.getProcessInstanceByHoldedObjectId(id);
		for (int i = 0, n = procInsts.length; i < n; i++) {
			/*
	         * modify by gongyin,流程挂起时也显示流程图
	         */
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
      fillAttachmentList();
}

//----------------------------------------------------------------------------------------------------

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		
		super.storeFields();
	}
	/**
	 * output actionPopupCopy_actionPerformed
	 */
	public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionPopupCopy_actionPerformed(e);
	}

	/**
	 * output actionHTMLForMail_actionPerformed
	 */
	public void actionHTMLForMail_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionHTMLForMail_actionPerformed(e);
	}

	/**
	 * output actionExcelForMail_actionPerformed
	 */
	public void actionExcelForMail_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionExcelForMail_actionPerformed(e);
	}

	/**
	 * output actionHTMLForRpt_actionPerformed
	 */
	public void actionHTMLForRpt_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionHTMLForRpt_actionPerformed(e);
	}

	/**
	 * output actionExcelForRpt_actionPerformed
	 */
	public void actionExcelForRpt_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionExcelForRpt_actionPerformed(e);
	}

	/**
	 * output actionLinkForRpt_actionPerformed
	 */
	public void actionLinkForRpt_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionLinkForRpt_actionPerformed(e);
	}

	/**
	 * output actionPopupPaste_actionPerformed
	 */
	public void actionPopupPaste_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionPopupPaste_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if(null == editData.getName()){
			editData.setName(UUID.randomUUID().toString());
		}else{}
		if(null == editData.getNumber()){
			editData.setNumber(UUID.randomUUID().toString());
		}else{}
		super.actionSave_actionPerformed(e);
		this.btnEdit.setEnabled(false);
		this.menuItemEdit.setEnabled(false);
		kdtTemplateBillUnite();
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		/*
    	 * 直接点击提交时候给name和number赋值
    	 */
    	if(null == editData.getName()){
    	   editData.setName(UUID.randomUUID().toString());
    	}
    	if(null == editData.getNumber()){
    	   editData.setNumber(UUID.randomUUID().toString());
    	}
    	IObjectPK pk = null ;
     	 if(null == editData.getId()){
     		  pk = FDCSplKeepContractAuditBillFactory.getRemoteInstance().addnew(editData);
     		  editData.setId((BOSUuid) pk.getKeyValue("id"));
     		  getUIContext().put(UIContext.ID, editData.getId().toString());
   	}
    	editData.setState(FDCBillStateEnum.SUBMITTED);
        super.actionSubmit_actionPerformed(e);
        kdtTemplateBillUnite();//重新和并下单元格
        setAuditBtn();
        setBtnForSubmit();
	}
	
	
	protected void doAfterSubmit(IObjectPK pk) throws Exception {
		this.showSubmitSuccess();
	}
	protected void showSubmitSuccess() {
		super.showSubmitSuccess();
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}
		
	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	/**
	 * output actionFirst_actionPerformed
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		super.actionFirst_actionPerformed(e);
	}

	/**
	 * output actionPre_actionPerformed	
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		super.actionPre_actionPerformed(e);
	}

	/**
	 * output actionNext_actionPerformed
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		super.actionNext_actionPerformed(e);
	}

	/**
	 * output actionLast_actionPerformed
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		super.actionLast_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
			editData = FDCSplKeepContractAuditBillFactory.getRemoteInstance().getFDCSplKeepContractAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
	    }
	    if(editData.getState().equals(FDCBillStateEnum.AUDITTED) || editData.getState().equals(FDCBillStateEnum.AUDITTING)){
	    	MsgBox.showWarning(this, "该状态不能被修改！");
	    	SysUtil.abort();
	    }
		super.actionEdit_actionPerformed(e);
		setAuditBtn();
	}
	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmitOption_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionSubmitOption_actionPerformed(e);
	}

	/**
	 * output actionReset_actionPerformed
	 */
	public void actionReset_actionPerformed(ActionEvent e) throws Exception {
		super.actionReset_actionPerformed(e);
	}

	/**
	 * output actionMsgFormat_actionPerformed
	 */
	public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception {
		super.actionMsgFormat_actionPerformed(e);
	}

	/**
	 * output actionAddLine_actionPerformed
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
	}

	/**
	 * output actionCopyLine_actionPerformed
	 */
	public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyLine_actionPerformed(e);
	}

	/**
	 * output actionInsertLine_actionPerformed
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionInsertLine_actionPerformed(e);
	}

	/**
	 * output actionRemoveLine_actionPerformed
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionRemoveLine_actionPerformed(e);
	}

	/**
	 * output actionCreateFrom_actionPerformed
	 */
	public void actionCreateFrom_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionCreateFrom_actionPerformed(e);
	}

	/**
	 * output actionCopyFrom_actionPerformed
	 */
	public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyFrom_actionPerformed(e);
	}

	/**
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionAuditResult_actionPerformed(e);
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceUp_actionPerformed(e);
	}

	/**
	 * output actionTraceDown_actionPerformed
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceDown_actionPerformed(e);
	}

	/**
	 * output actionViewSubmitProccess_actionPerformed
	 */
	public void actionViewSubmitProccess_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionViewSubmitProccess_actionPerformed(e);
	}

	/**
	 * output actionViewDoProccess_actionPerformed
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionViewDoProccess_actionPerformed(e);
	}

	/**
	 * output actionMultiapprove_actionPerformed
	 */
	public void actionMultiapprove_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionMultiapprove_actionPerformed(e);
	}

	/**
	 * output actionNextPerson_actionPerformed
	 */
	public void actionNextPerson_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionNextPerson_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionVoucher_actionPerformed
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionVoucher_actionPerformed(e);
	}

	/**
	 * output actionDelVoucher_actionPerformed
	 */
	public void actionDelVoucher_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionDelVoucher_actionPerformed(e);
	}

	/**
	 * output actionWorkFlowG_actionPerformed
	 */
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkFlowG_actionPerformed(e);
	}

	/**
	 * output actionCreateTo_actionPerformed
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCreateTo_actionPerformed(e);
	}

	/**
	 * output actionSendingMessage_actionPerformed
	 */
	public void actionSendingMessage_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionSendingMessage_actionPerformed(e);
	}

	/**
	 * output actionSignature_actionPerformed
	 */
	public void actionSignature_actionPerformed(ActionEvent e) throws Exception {
		super.actionSignature_actionPerformed(e);
	}

	/**
	 * output actionWorkflowList_actionPerformed
	 */
	public void actionWorkflowList_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionWorkflowList_actionPerformed(e);
	}

	/**
	 * output actionViewSignature_actionPerformed
	 */
	public void actionViewSignature_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionViewSignature_actionPerformed(e);
	}

	/**
	 * output actionSendMail_actionPerformed
	 */
	public void actionSendMail_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMail_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionAudit_actionPerformed
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
	    	editData = FDCSplKeepContractAuditBillFactory.getRemoteInstance().getFDCSplKeepContractAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
	    }
	    if(!editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
			FDCMsgBox.showWarning(this,getResource("notState"));
		    abort();
	    }
	    btnUnAudit.setVisible(true);
	    menuItemUnAudit.setVisible(true);
	    getUIContext().put(UIContext.ID, editData.getId().toString());
	    super.actionAudit_actionPerformed(e);
	    kdtTemplateBillUnite();
	    setAuditBtn();
	    btnUnAudit.setEnabled(true);
	    menuItemUnAudit.setEnabled(true);
	    btnTemplet.setEnabled(false);
	    menuItemTemplet.setEnabled(false);
		Object[] obj=getSupplierTypeObj();
	    prmtSupplierType.setDataNoNotify(obj);
	    this.btnAudit.setEnabled(false);
	    this.menuItemAudit.setEnabled(false);
	}
	
	/**
	 * output actionUnAudit_actionPerformed
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		//如果进入工作流后页面状态不会刷新 手动刷新下
    	if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
    	   editData = FDCSplKeepContractAuditBillFactory.getRemoteInstance().getFDCSplKeepContractAuditBillInfo(new ObjectUuidPK( editData.getId().toString()),getSelectors());
    	}
		super.actionUnAudit_actionPerformed(e);
		kdtTemplateBillUnite();
		setAuditBtn();
		this.btnRemove.setEnabled(false);
		this.menuItemRemove.setEnabled(false);
		btnAudit.setEnabled(true);
        menuItemAudit.setEnabled(true);
        btnAudit.setVisible(true);
        btnTemplet.setEnabled(true);
        menuItemTemplet.setEnabled(true);
        btnEdit.setEnabled(true);
        menuItemEdit.setEnabled(true);
        this.actionRemove.setEnabled(false);
        this.btnUnAudit.setEnabled(false);
        this.menuItemUnAudit.setEnabled(false);
	}

	/**
	 * output actionAttamentCtrl_actionPerformed
	 */
	public void actionAttamentCtrl_actionPerformed(ActionEvent e)
		throws Exception {
		super.actionAttamentCtrl_actionPerformed(e);
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCSplKeepContractAuditBillFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtSupplierType;
	}

}


