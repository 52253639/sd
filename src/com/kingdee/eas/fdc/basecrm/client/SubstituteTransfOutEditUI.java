/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SubstituteTransfOutEntryCollection;
import com.kingdee.eas.fdc.basecrm.SubstituteTransfOutEntryInfo;
import com.kingdee.eas.fdc.basecrm.SubstituteTransfOutFactory;
import com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BankPaymentInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.GatherTypeEnum;
import com.kingdee.eas.fdc.sellhouse.IBaseTransaction;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.client.PrePurchaseManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SignManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SincerityPurchaseChangeNameUI;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableUI;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;

/**
 * output class name
 */
public class SubstituteTransfOutEditUI extends AbstractSubstituteTransfOutEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SubstituteTransfOutEditUI.class);
    

    public SubstituteTransfOutEditUI() throws Exception
    {
        super();
    }

    public void loadFields()
    {
        super.loadFields();
    }


    public void storeFields()
    {
        super.storeFields();
    }

    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.basecrm.SubstituteTransfOutFactory.getRemoteInstance();
    }

    protected IObjectValue createNewDetailData(KDTable table)
    {
		SubstituteTransfOutEntryInfo subEntryInfo = new SubstituteTransfOutEntryInfo();
		
        return subEntryInfo;
    }
    


    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        SubstituteTransfOutInfo objectValue = new SubstituteTransfOutInfo();
//		if (SysContext.getSysContext().getCurrentOrgUnit(OrgType.getEnum("Sale")) != null 
//				&& SysContext.getSysContext().getCurrentOrgUnit(OrgType.getEnum("Sale")).getBoolean("isBizUnit"))
		objectValue.put("saleOrgUnit",SysContext.getSysContext().getCurrentOrgUnit(OrgType.getEnum("Sale")));

        objectValue.setCreator((UserInfo)(SysContext.getSysContext().getCurrentUserInfo()));
		
        try {
        	CompanyOrgUnitInfo tempCompany = SysContext.getSysContext().getCurrentFIUnit();
			CompanyOrgUnitInfo company = GlUtils.getCurrentCompany(null,tempCompany.getId().toString(),null,false);
			objectValue.setCurrency(company.getBaseCurrency());
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}	
        
        objectValue.setExchangeRate(FDCHelper.ONE);
        objectValue.setState(FDCBillStateEnum.SUBMITTED);
        objectValue.setBizDate(new Date());
        objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
        return objectValue;
    }

    
    public void onLoad() throws Exception {
    	super.onLoad();
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);    	
    	
    	this.kdtEntrys.getStyleAttributes().setLocked(true);
    	this.kdtEntrys.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	this.kdtEntrys.getColumn("building").getStyleAttributes().setHided(true);
    	this.actionCopy.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.actionCreateFrom.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	this.actionMultiapprove.setVisible(false);
    	this.actionPrint.setVisible(false);
    	this.actionPrintPreview.setVisible(false);
    	this.actionSave.setVisible(false);
    	this.actionCopyFrom.setVisible(false);
    	
    	
    	if(this.editData.getState()!=null ) {
	    	if(this.editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
	    		this.actionAudit.setEnabled(true);
	    		this.actionUnAudit.setEnabled(false);
	    		this.actionSubmit.setEnabled(true);
	    	}else if(this.editData.getState().equals(FDCBillStateEnum.AUDITTED)){
	    		this.actionAudit.setEnabled(false);
	    		this.actionUnAudit.setEnabled(true);
	    		this.actionSubmit.setEnabled(false);
	    	}else{
	    		this.actionAudit.setEnabled(false);
	    		this.actionUnAudit.setEnabled(false);    		
	    	}
    	} 
    	
    	if(this.getOprtState().equals(OprtState.VIEW)){
    		this.btnSearchShowEntry.setEnabled(false);
    		this.btnDeleteEntry.setEnabled(false);
    	}
    	
    	SellProjectInfo spInfo = (SellProjectInfo)this.getUIContext().get("SellProjectInfo");
    	BuildingInfo buildInfo = (BuildingInfo)this.getUIContext().get("BuildingInfo");
    	if(spInfo!=null)	this.prmtsellProject.setValue(spInfo);
    	if(buildInfo!=null) this.prmtbuilding.setValue(buildInfo);
    	
    	this.prmtbuilding.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				SellProjectInfo spInfo = (SellProjectInfo)SubstituteTransfOutEditUI.this.prmtsellProject.getValue();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id",spInfo==null?null:spInfo.getId()));
				view.setFilter(filter);
				SubstituteTransfOutEditUI.this.prmtbuilding.setEntityViewInfo(view);
				SubstituteTransfOutEditUI.this.prmtbuilding.getQueryAgent().resetRuntimeEntityView();
				SubstituteTransfOutEditUI.this.prmtbuilding.setRefresh(true); 
			}
		});
    	
/*		KDComboBox entry_revBillType_Field = new KDComboBox();
		entry_revBillType_Field.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basecrm.RelatBizType").toArray());
		ICellEditor entry_revBillType_Editor = new KDTDefaultCellEditor(entry_revBillType_Field);
		this.kdtEntrys.getColumn("revBillType").setEditor(entry_revBillType_Editor);
		
		KDBizPromptBox entry_room_Field = new KDBizPromptBox();
		entry_room_Field.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomQuery");
		entry_room_Field.setEditable(true);
		entry_room_Field.setDisplayFormat("$name$");
		entry_room_Field.setEditFormat("$name$");
		entry_room_Field.setCommitFormat("$number$");
		ICellEditor entry_room_editor = new KDTDefaultCellEditor(entry_room_Field);
		this.kdtEntrys.getColumn("room").setEditor(entry_room_editor);
    	
		KDBizPromptBox entry_customer_Field = new KDBizPromptBox();
		entry_customer_Field.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECustomerQuery");
		entry_customer_Field.setEditable(true);
		entry_customer_Field.setDisplayFormat("$name$");
		entry_customer_Field.setEditFormat("$name$");
		entry_customer_Field.setCommitFormat("$number$");
		ICellEditor entry_customer_editor = new KDTDefaultCellEditor(entry_customer_Field);
		this.kdtEntrys.getColumn("customer").setEditor(entry_customer_editor);
*/    	
	
    	EntityViewInfo monDefView = new EntityViewInfo();	//代收费用类的
    	FilterInfo monDefFilter = new FilterInfo();
    	monDefFilter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.REPLACEFEE_VALUE));
    	monDefView.setFilter(monDefFilter);
    	this.prmtmoneyDefine.setEntityViewInfo(monDefView);
    	
		CompanyOrgUnitInfo curCompany = SysContext.getSysContext().getCurrentFIUnit();		
		EntityViewInfo revAccountView = this.getAccountEvi(curCompany);
		AccountPromptBox opseelect = new AccountPromptBox(this, curCompany,revAccountView.getFilter(), false, true);
		this.prmtrevAccount.setEntityViewInfo(revAccountView);
		this.prmtrevAccount.setSelector(opseelect);
		this.prmtoppAccount.setEntityViewInfo(revAccountView);
		this.prmtoppAccount.setSelector(opseelect);
    	
		FDCClientUtils.setPersonF7(this.prmtpayUser, this, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString(),true);
		
		//非售楼组织不能操作
		if(!FDCSysContext.getInstance().checkIsSHEOrg()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			//this.actionCancel.setEnabled(false);
			//this.actionCancelCancel.setEnabled(false);
		}
    }
    
    
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection selectors = super.getSelectors();
    	selectors.add(new SelectorItemInfo("state"));
    	selectors.add("CU.*");
    	return selectors;
    }
    
    protected void btnSearchShowEntry_actionPerformed(ActionEvent e)
    		throws Exception {
    	MoneyDefineInfo moneyDefInfo = (MoneyDefineInfo)this.prmtmoneyDefine.getValue();
    	if(moneyDefInfo==null) {
    		FDCMsgBox.showWarning("款项名称必须录入！");
    		return;
    	}
    	
    	this.kdtEntrys.removeRows();
    	
    	BuildingInfo buidInfo = (BuildingInfo)this.prmtbuilding.getValue();
    	String filterSql = "";
    	if(buidInfo!=null) 
    		filterSql += " and head.room.building.id = '"+buidInfo.getId()+"' ";
    	else{
    		SellProjectInfo spInfo = (SellProjectInfo)this.prmtsellProject.getValue();
    		if(spInfo!=null)
    			filterSql += " and head.room.building.sellProject.id = '"+spInfo.getId()+"' ";
    	}
    	TranBusinessOverViewCollection tranViewColl = TranBusinessOverViewFactory.getRemoteInstance()
    				.getTranBusinessOverViewCollection("select moneyDefine,appAmount,actRevAmount,head.billId,head.billNumber,head.currentLink,head.room.name," +
    						"head.customerNames , head.room.building.name where (isSubstitute = 0 or isSubstitute is null) " +
    						" and moneyDefine.id = '"+moneyDefInfo.getId().toString()+"' and (head.isValid =0 or head.isValid is null ) "+filterSql );
    	Map bizIDTypeMap = new HashMap();			//为了获取业务单据编码
    	SubstituteTransfOutEntryCollection allAddToTableColl = new SubstituteTransfOutEntryCollection();
    	for (int i = 0; i < tranViewColl.size(); i++) {
    		TranBusinessOverViewInfo tranViewInfo = tranViewColl.get(i);
    		if(tranViewInfo.getHead()!=null && tranViewInfo.getHead().getBillId()!=null) {
    	    	SubstituteTransfOutEntryInfo newEntryInfo = new SubstituteTransfOutEntryInfo();
    	    	newEntryInfo.setParent(this.editData);
    			newEntryInfo.setRoom(tranViewInfo.getHead().getRoom());
    			newEntryInfo.setBuilding(tranViewInfo.getHead().getRoom().getBuilding());
    			newEntryInfo.setCustomer(tranViewInfo.getHead().getCustomerNames());
    			newEntryInfo.setPayAmount(tranViewInfo.getAppAmount());
    			newEntryInfo.setRelateBizId(tranViewInfo.getHead().getBillId().toString());
    			newEntryInfo.setRelateBillEntryId(tranViewInfo.getId().toString());
    			RelatBizType reBizType = CRMHelper.retRelateBizTypeById(tranViewInfo.getHead().getBillId());
    			newEntryInfo.setRevBillType(reBizType);
    			newEntryInfo.setActRevAmount(tranViewInfo.getActRevAmount());
    			newEntryInfo.setAppAmount(tranViewInfo.getAppAmount());
    			
    			String bizIdStr = tranViewInfo.getHead().getBillId().toString();
    			if(bizIDTypeMap.get(reBizType)==null)
    				bizIDTypeMap.put(reBizType, "'"+bizIdStr+"'");
    			else{
    				String tmpIds = (String)bizIDTypeMap.get(reBizType);
    				if(tmpIds.indexOf(bizIdStr)<0) {
    						tmpIds += ",'"+bizIdStr+"'";
    						bizIDTypeMap.put(reBizType, tmpIds);
    				}
    			}
    			allAddToTableColl.add(newEntryInfo);
    		}
		}
    	
    	Map bizIdNumberMap = new HashMap();
    	Iterator iterator = bizIDTypeMap.keySet().iterator();
    	while(iterator.hasNext()){
    		RelatBizType reBizType = (RelatBizType)iterator.next();
    		String bizIdStr = (String)bizIDTypeMap.get(reBizType);
    		IBaseTransaction tansFactory = null;
			if(RelatBizType.PreOrder.equals(reBizType)) { //预约单
				tansFactory = SincerityPurchaseFactory.getRemoteInstance();
			}else if(RelatBizType.PrePur.equals(reBizType)) { //预订单
				tansFactory = PrePurchaseManageFactory.getRemoteInstance();
			}else if(RelatBizType.Purchase.equals(reBizType)) { //认购单
				tansFactory = PurchaseManageFactory.getRemoteInstance();
			}else if(RelatBizType.Sign.equals(reBizType)) { //签约单
				tansFactory = SignManageFactory.getRemoteInstance();
			}
			if(tansFactory!=null) {
				FDCBillCollection fdcColl = tansFactory.getFDCBillCollection("select id,number where id in ("+bizIdStr+")");
				for (int i = 0; i < fdcColl.size(); i++) {
					FDCBillInfo fdcInfo = fdcColl.get(i);
					bizIdNumberMap.put(fdcInfo.getId().toString(), fdcInfo.getNumber());
				}
				
			}
    	}
    	
    	
    	BigDecimal allRevAmount = new BigDecimal("0");
    	for (int i = 0; i < allAddToTableColl.size(); i++) {
    		SubstituteTransfOutEntryInfo newEntryInfo = allAddToTableColl.get(i);
    		if(bizIdNumberMap.get(newEntryInfo.getRelateBizId())!=null) {
    			String bizNumber = (String)bizIdNumberMap.get(newEntryInfo.getRelateBizId());
    			newEntryInfo.setRelateBillNumber(bizNumber);
    		}
    		
			IRow addRow = this.kdtEntrys.addRow();
			addRow.setUserObject(newEntryInfo);
			addRow.getCell("revBillType").setValue(newEntryInfo.getRevBillType());
			addRow.getCell("room").setValue(newEntryInfo.getRoom());
			addRow.getCell("customer").setValue(newEntryInfo.getCustomer());
			addRow.getCell("payAmount").setValue(newEntryInfo.getPayAmount());
			addRow.getCell("relateBillNumber").setValue(newEntryInfo.getRelateBillNumber());
			addRow.getCell("relateBillEntryId").setValue(newEntryInfo.getRelateBillEntryId());
			addRow.getCell("building").setValue(newEntryInfo.getBuilding());
			addRow.getCell("relateBizId").setValue(newEntryInfo.getRelateBizId());
			addRow.getCell("actRevAmount").setValue(newEntryInfo.getActRevAmount());
			addRow.getCell("appAmount").setValue(newEntryInfo.getAppAmount());
			allRevAmount = allRevAmount.add(newEntryInfo.getPayAmount());
		}    	
    	this.txtpayAmount.setValue(allRevAmount);
    }
        
    protected void btnDeleteEntry_actionPerformed(ActionEvent e)
    		throws Exception {
    	int selectIndex[] = KDTableUtil.getSelectedRows(this.kdtEntrys);
    	BigDecimal allRevAmount = new BigDecimal("0");
    	for (int i = 0; i < selectIndex.length; i++) {
			IRow selectRow = this.kdtEntrys.getRow(selectIndex[i]);
			BigDecimal payAmount = (BigDecimal)selectRow.getCell("payAmount").getValue();
			allRevAmount = allRevAmount.add(payAmount);
		}
    	
    	this.actionRemoveLine_actionPerformed(null);
    	BigDecimal currAmount = this.txtpayAmount.getBigDecimalValue();
    	this.txtpayAmount.setValue(currAmount.subtract(allRevAmount));
    }
    
    protected void kdtEntrys_tableClicked(KDTMouseEvent e) throws Exception {
    	if(e.getType()!=1) return;
    	if(e.getClickCount()!=2) return;
    	IRow curRow = KDTableUtil.getSelectedRow(this.kdtEntrys);
    	String idStr = (String)curRow.getCell("relateBizId").getValue();
    	if(idStr==null) return;    	
    	RelatBizType relatBizType = (RelatBizType)curRow.getCell("revBillType").getValue();
    	if(relatBizType==null) return;
    	
    	UIContext uiContext = new UIContext(this); 		
		uiContext.put(UIContext.ID, idStr);
		String uiName = "";
		if(relatBizType.equals(RelatBizType.PreOrder))
			uiName = SincerityPurchaseChangeNameUI.class.getName();
		else if(relatBizType.equals(RelatBizType.PrePur))
			uiName = PrePurchaseManageEditUI.class.getName();
		else if(relatBizType.equals(RelatBizType.Purchase))
			uiName = PurchaseManageEditUI.class.getName();
		else if(relatBizType.equals(RelatBizType.Sign))
			uiName = SignManageEditUI.class.getName();
		if(uiName.equals("")) return;
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiName, uiContext, null, OprtState.VIEW);
		uiWindow.show();
    }
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
  		if(this.editData.getState()!=null && !this.editData.getState().equals(FDCBillStateEnum.SUBMITTED) 
  				&& !this.editData.getState().equals(FDCBillStateEnum.AUDITTING)){
			FDCMsgBox.showInfo("该单据非提交或审批中状态，禁止审批！");
			return;
		}
    	SubstituteTransfOutFactory.getRemoteInstance().audit(this.editData.getId());
    	FDCMsgBox.showInfo("审批成功！");
    	this.editData.setState(FDCBillStateEnum.AUDITTED);
    	this.actionAudit.setEnabled(false);
    	this.actionUnAudit.setEnabled(true);
    	this.actionSubmit.setEnabled(false);
    }
    
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
  		if(this.editData.getState()!=null && !this.editData.getState().equals(FDCBillStateEnum.AUDITTED) ){
			FDCMsgBox.showInfo("该单据非审批状态，禁止反审批！");
			return;
		}    	
    	SubstituteTransfOutFactory.getRemoteInstance().unAudit(this.editData.getId());
    	FDCMsgBox.showInfo("反审批成功！");
    	this.editData.setState(FDCBillStateEnum.SUBMITTED);
    	this.actionAudit.setEnabled(true);
    	this.actionUnAudit.setEnabled(false);
    	this.actionSubmit.setEnabled(true);
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(this.txtNumber.isEditable()  &&  this.txtNumber.isEnabled()  &&  StringUtils.isEmpty(this.txtNumber.getText())){
			FDCMsgBox.showInfo(this, "编码不能为空！");
			return;
		}    	
    	
		if(this.pkBizDate.getValue()==null){
			FDCMsgBox.showInfo(this, "业务日期不能为空！");
			return;
		}
		if(this.prmtmoneyDefine.getValue()==null){
			FDCMsgBox.showInfo(this, "款项名称不能为空！");
			return;
		}
		if(this.txtrevUserName.getText()==null){
			FDCMsgBox.showInfo(this, "收款人不能为空！");
			return;
		}
		if(this.prmtrevBank.getValue()==null){
			FDCMsgBox.showInfo(this, "收款银行不能为空！");
			return;
		}
		if(this.prmtpayUser.getValue()==null){
			FDCMsgBox.showInfo(this, "付款人不能为空！");
			return;
		}
		if(this.prmtpayBank.getValue()==null){
			FDCMsgBox.showInfo(this, "付款银行不能为空！");
			return;
		}
		
		if(this.kdtEntrys.getRowCount()==0){
			FDCMsgBox.showInfo(this, "转出明细列表不能为空！");
			return;
		}		
		
		this.chkMenuItemSubmitAndPrint.setSelected(false);
    	super.actionSubmit_actionPerformed(e);
    	this.actionAudit.setEnabled(true);
    	this.actionUnAudit.setEnabled(false);
    }
    
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	if(this.editData.getId()!=null) {
    		if(this.editData.getState()!=null && !this.editData.getState().equals(FDCBillStateEnum.SAVED)
    				&& !this.editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
    			FDCMsgBox.showWarning("单据非保存或提交状态，禁止修改！");
    			return;
    		}	
    	}
    	super.actionEdit_actionPerformed(e);
    	this.btnSearchShowEntry.setEnabled(true);
    	this.btnDeleteEntry.setEnabled(true);
    }
    
    
    
	private EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo)
	{
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// -----------------转6.0后修改,科目不按CU隔离,根据财务组织进行隔离
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString()));
		if (companyInfo.getAccountTable() != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", companyInfo.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		evi.setFilter(filter);
		return evi;
	}
 
	
	
	
}