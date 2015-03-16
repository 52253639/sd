/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.metas.ProcDefCollection;
import com.kingdee.bos.workflow.metas.ProcDefFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.SHERevCustEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevCustEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.CRMChequeFactory;
import com.kingdee.eas.fdc.sellhouse.CRMChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ITranCustomerEntry;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ProjectSet;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.client.MakeInvoiceEditUI;
import com.kingdee.eas.fdc.sellhouse.client.NewFDCRoomPromptDialog;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class SHERevBillEditUI extends AbstractSHERevBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SHERevBillEditUI.class);
    
    MarketDisplaySetting setting = new MarketDisplaySetting();
//    RoomDisplaySetting setSellHouse = new RoomDisplaySetting(null,SHEParamConstant.PROJECT_SET_MAP);
    
    
    public SHERevBillEditUI() throws Exception
    {
        super();
    }

    protected void applyDefaultValue(IObjectValue vo) {
    }
    
    public void loadFields()
    {
        super.loadFields();                		
        
        if(!this.getOprtState().equals(OprtState.ADDNEW)) {
	        dealRelateBillLoadFields();        
	        dealCustomerEntryLoadFields();     
        }
        
        if(this.editData.getRevBillType()!=null && this.editData.getRevBillType().equals(RevBillTypeEnum.transfer)) { //转款
	        for(int i=0;i<this.kdtEntrys.getRowCount();i++){
	        	IRow crrRow = this.kdtEntrys.getRow(i);
	        	
				SHERevBillEntryInfo revEntryInfo = (SHERevBillEntryInfo)crrRow.getUserObject();
	    		if(revEntryInfo.getTransferFromEntryId()!=null){
	    			crrRow.getStyleAttributes().setBackground(new Color(0xFCFBDF));
	    			crrRow.getCell("moneyDefine").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    			crrRow.getCell("revAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    			crrRow.getCell("hasRefundmentAmount").setValue(null);
	    			crrRow.getCell("hasTransferAmount").setValue(null); 
	    		}
	        }
        }        
        
        addAllEntryRevAmountToTop();
    }
    
    /**加载客户分录信息*/
    private void dealCustomerEntryLoadFields(){
    	SHERevCustEntryCollection custEntryColl = this.editData.getCustomerEntry();
    	Object[] sheCustObjs = new Object[custEntryColl.size()];
    	for (int i = 0; i < custEntryColl.size(); i++) {
    		sheCustObjs[i] = custEntryColl.get(i).getSheCustomer(); 
		}
    	this.prmtCustomerEntry.setValue(sheCustObjs);
    }
    
    /**加载交易单据的信息**/
    private void dealRelateBillLoadFields(){
        if(this.editData.getRelateBizBillId()!=null){
        	FDCBillInfo reltInfo = new FDCBillInfo();
        	reltInfo.put("RelateBizType", this.editData.getRelateBizType());
        	if(this.editData.getRelateTransId()!=null)
        		reltInfo.put("TransactionId", BOSUuid.read(this.editData.getRelateTransId()));
        	reltInfo.setId(BOSUuid.read(this.editData.getRelateBizBillId()));
        	reltInfo.setNumber(this.editData.getRelateBizBillNumber());
        	this.prmtRelateBizBill.setValue(reltInfo);
        }
        if(this.editData.getRelateFromBizId()!=null){
        	FDCBillInfo reltInfo = new FDCBillInfo();
        	reltInfo.put("RelateBizType", this.editData.getRelateFromBizType());
        	if(this.editData.getRelateFromTransId()!=null)
        		reltInfo.put("TransactionId", BOSUuid.read(this.editData.getRelateFromTransId()));
        	reltInfo.setId(BOSUuid.read(this.editData.getRelateFromBizId()));
        	reltInfo.setNumber(this.editData.getRelateFromBizNumber());
        	this.prmtRelateFromBizBill.setValue(reltInfo);        	
        }
    }
    
    
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection selector = super.getSelectors();
    	selector.add(new SelectorItemInfo("state"));
    	selector.add(new SelectorItemInfo("relateBizType"));
    	selector.add(new SelectorItemInfo("relateBizBillId"));
    	selector.add(new SelectorItemInfo("relateBizBillNumber"));
    	selector.add(new SelectorItemInfo("relateFromBizType"));
    	selector.add(new SelectorItemInfo("relateFromBizId"));
    	selector.add(new SelectorItemInfo("relateFromBizNumber"));
    	selector.add(new SelectorItemInfo("relateTransId"));
    	selector.add(new SelectorItemInfo("relateFromTransId"));
    	selector.add(new SelectorItemInfo("customerEntry.head.id"));
    	selector.add(new SelectorItemInfo("customerEntry.sheCustomer.name"));
    	selector.add(new SelectorItemInfo("isTansCreate"));
    	    	
    	return selector;
    }
    

    public void storeFields()
    {
        super.storeFields();        
        
        dealRelateBillStoreFields();
        
        dealCustomerEntryStoreFields();
        
        this.editData.setOriginalAmount(this.editData.getRevAmount());
        
        if(this.editData.getInvoice()!=null)
        	this.editData.setInvoiceNumber(this.editData.getInvoice().getNumber());
        if(this.editData.getReceipt()!=null)
        	this.editData.setReceiptNumber(this.editData.getReceipt().getNumber());
        
/*        if (setting.getMarkInvoice() == 32) {	//统一发票管理        
        	SHERevBillEntryCollection revEntryColl = this.editData.getEntrys();
        	for(int i=0;i<revEntryColl.size();i++) {
        		SHERevBillEntryInfo revEntryInfo = revEntryColl.get(i);
        		//revEntryInfo.getre
        	}        	
        }*/
    } 
    
    /**处理多客户的存储**/
    private void dealCustomerEntryStoreFields(){
    	Object[] custObjs = (Object[])this.prmtCustomerEntry.getValue();
    	
    	String custIdsStr = "";
    	String custNamesStr = "";
    	this.editData.getCustomerEntry().clear();
    	CRMHelper.sortCollection(custObjs, "name", true);
    	for (int i = 0; i < custObjs.length; i++) {
			SHECustomerInfo custInfo = (SHECustomerInfo)custObjs[i];
			if(custInfo!=null) {
				SHERevCustEntryInfo custEntryInfo = new SHERevCustEntryInfo();
				custEntryInfo.setHead(this.editData);
				custEntryInfo.setSheCustomer(custInfo);
				custIdsStr += ","+custInfo.getId().toString();
				custNamesStr += ","+custInfo.getName();
				this.editData.getCustomerEntry().add(custEntryInfo);
			}
		}
    	if(!custIdsStr.equals("")) {
    		this.editData.setCustomerIds(custIdsStr.substring(1));
    		this.editData.setCustomerNames(custNamesStr.substring(1));
    	}
    }
    
    
    /**处理关联交易单据的存储信息*/
    private void dealRelateBillStoreFields(){
        //关联单据的实体都继承与FDCBILL
        this.editData.setRelateBizType(null);
        this.editData.setRelateBizBillId(null);
        this.editData.setRelateBizBillNumber(null);
        this.editData.setRelateFromBizType(null);
        this.editData.setRelateFromBizId(null);
        this.editData.setRelateFromBizNumber(null); 
        this.editData.setRelateTransId(null);
        this.editData.setRelateFromTransId(null);
        FDCBillInfo relateBillInfo = (FDCBillInfo)this.prmtRelateBizBill.getValue();
        FDCBillInfo relateFrommBillInfo = (FDCBillInfo)this.prmtRelateFromBizBill.getValue();
        if(relateBillInfo!=null) {
	        this.editData.setRelateBizType((RelatBizType)relateBillInfo.get("RelateBizType"));
	        BOSUuid bosId = (BOSUuid)relateBillInfo.get("TransactionId");
	        if(bosId!=null) this.editData.setRelateTransId(bosId.toString());
	        this.editData.setRelateBizBillId(relateBillInfo.getId().toString());
	        this.editData.setRelateBizBillNumber(relateBillInfo.getNumber());
        }
        if(relateFrommBillInfo!=null) {
	        this.editData.setRelateFromBizType((RelatBizType)relateFrommBillInfo.get("RelateBizType"));
	        BOSUuid bosId = (BOSUuid)relateFrommBillInfo.get("TransactionId");
	        if(bosId!=null)  this.editData.setRelateFromTransId(bosId.toString());
	        this.editData.setRelateFromBizId(relateFrommBillInfo.getId().toString());
	        this.editData.setRelateFromBizNumber(relateFrommBillInfo.getNumber());
        }
        ChequeDetailEntryInfo cheInfo = (ChequeDetailEntryInfo)this.prmtReceipt.getValue();
        if(cheInfo!=null)	this.editData.setReceiptNumber(cheInfo.getNumber());
        ChequeDetailEntryInfo invoiceInfo = (ChequeDetailEntryInfo)this.prmtInvoice.getValue();
        if(invoiceInfo!=null) this.editData.setInvoiceNumber(invoiceInfo.getNumber());
    }

    public void onLoad() throws Exception {   	
    	super.onLoad();    	
    	this.txtrevAmount.setPrecision(2);
		this.txtrevAmount.setRoundingMode(4);
		this.txtDescription.setMaxLength(255);
		
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
       	this.actionCreateTo.setVisible(false);
       	this.actionTraceDown.setVisible(false);
       	this.actionTraceUp.setVisible(false);
       	//this.actionWorkFlowG.setVisible(false);
       	this.actionWorkflowList.setVisible(false);
       	//this.actionAuditResult.setVisible(false);
       	this.actionCreateFrom.setVisible(false);
       	this.actionMultiapprove.setVisible(false);
       	this.actionNextPerson.setVisible(false);
       	this.actionCopy.setVisible(false);
       	this.actionCopyFrom.setVisible(false);
       	this.actionSave.setVisible(false); //隐藏保存按钮
       	
       	this.actionMakeInvoice.setVisible(false);
       	this.actionRecycle.setVisible(false);	//这里应该不需要，直接通过选择收据或发票的f7处理即可
       	
    	this.actionAudit.setEnabled(true);
    	this.actionUnAudit.setEnabled(true);
    	this.actionRecycle.setEnabled(true);
    	this.actionMakeInvoice.setEnabled(true);
    	this.prmtsellProject.setEnabled(false);

    	if(this.editData.getState()!=null && !this.editData.getState().equals(FDCBillStateEnum.SAVED)) {
    		this.actionSave.setVisible(false);
    	}

    	this.revBillType.removeItem(RevBillTypeEnum.adjust);	//已没有调整类型 
    	this.kdtEntrys.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	this.kdtEntrys.getColumn("hasRefundmentAmount").getStyleAttributes().setLocked(true);
    	this.kdtEntrys.getColumn("hasTransferAmount").getStyleAttributes().setLocked(true);
    	this.kdtEntrys.getColumn("leftAmount").getStyleAttributes().setLocked(true);
    	this.kdtEntrys.getColumn("revAmount").getStyleAttributes().setBackground(new Color(0xFCFBDF));
    	this.kdtEntrys.getColumn("moneyDefine").getStyleAttributes().setBackground(new Color(0xFCFBDF));
    	this.kdtEntrys.getColumn("receiptNumber").getStyleAttributes().setLocked(true);
    	this.kdtEntrys.getColumn("invoiceNumber").getStyleAttributes().setLocked(true);
    	this.kdtEntrys.getColumn("transferFromEntryId").getStyleAttributes().setHided(true);
    	this.kdtEntrys.getColumn("hasMapedAmount").getStyleAttributes().setHided(true);
    	this.kdtEntrys.getColumn("hasMakeInvoiceAmount").getStyleAttributes().setLocked(true);
    	
		//只有新增时才需要设置结算方式
    	int colMonDefinIndex = this.kdtEntrys.getColumnIndex("settlementType");	//增加结算笔数列
    	IColumn settleMentColumn = this.kdtEntrys.addColumn(colMonDefinIndex);
    	settleMentColumn.setKey("settlementCount");
    	settleMentColumn.getStyleAttributes().setHided(false);
    	settleMentColumn.setWidth(60);
    	this.kdtEntrys.getHeadRow(0).getCell("settlementCount").setValue("结算笔数");
		KDFormattedTextField setlCountField = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		setlCountField.setNegatived(false);
		ICellEditor setlCountEditor = new KDTDefaultCellEditor(setlCountField);
		settleMentColumn.setEditor(setlCountEditor);
		settleMentColumn.getStyleAttributes().setHided(true);
    	
    	dealReceptAndInvoiceOnload();
    	
       	FDCCommonPromptBox comBox = new FDCCommonPromptBox(RelateBillQueryUI.class.getName(),this);
    	this.prmtRelateBizBill.setSelector(comBox);  
    	this.prmtRelateFromBizBill.setSelector(comBox);    

        KDTDefaultCellEditor kdtEntrys_revAmount_CellEditor = (KDTDefaultCellEditor)this.kdtEntrys.getColumn("revAmount").getEditor(); 
        KDFormattedTextField kdtEntrys_revAmount_TextField = (KDFormattedTextField)kdtEntrys_revAmount_CellEditor.getComponent();
        kdtEntrys_revAmount_TextField.setPrecision(2);      
    	
    	if(this.editData.getRevBillType()!=null && RevBillTypeEnum.transfer.equals(this.editData.getRevBillType())) {
	    	for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
				IRow crrRow = this.kdtEntrys.getRow(i);
				SHERevBillEntryInfo revEntryInfo = (SHERevBillEntryInfo)crrRow.getUserObject();;
	    		if(revEntryInfo.getTransferFromEntryId()!=null){
	    			crrRow.getStyleAttributes().setBackground(new Color(0xFCFBDF));
	    			crrRow.getCell("moneyDefine").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    			crrRow.getCell("revAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    		}
			}
    	}    	 
   	
    	
    	if(this.getOprtState().equals(OprtState.ADDNEW)) {
    		if(this.editData.getId()!=null)
    			this.revBillType.setEnabled(false);
    		
    		//未开启工作流时，可以直接<提交&审批>;否则不允许.  
    		EntityViewInfo procDefView = new EntityViewInfo();
    		FilterInfo proDefFilterInfo = new FilterInfo();
    		proDefFilterInfo.getFilterItems().add(new FilterItemInfo("proccode","SheRevBillWorkFlow"));
    		proDefFilterInfo.getFilterItems().add(new FilterItemInfo("enable",new Boolean(true)));
    		procDefView.setFilter(proDefFilterInfo);
    		ProcDefCollection preDefColl = ProcDefFactory.getRemoteInstance().getCollection(procDefView);
    		if(preDefColl.size()>0)
    			this.actionSubmitAudit.setVisible(false);
    		else
    			this.actionSubmitAudit.setEnabled(true);
    		
    		if(!this.editData.getRevBillType().equals(RevBillTypeEnum.transfer))
    		  this.kdtEntrys.getColumn("settlementCount").getStyleAttributes().setHided(false);
    	}else{
    		this.revBillType.setEnabled(false);	//非新增状态不能修改单据类型
    		changeComStateByRevBillType(this.editData.getRevBillType());
    		this.actionSubmitAudit.setVisible(false);
    		
    		String invoiceNum = this.txtinvoiceNumber.getText();
    		if(invoiceNum!=null && !invoiceNum.trim().equals("")){
    			this.txtinvoiceNumber.setEnabled(false);
    			this.prmtInvoice.setEnabled(false);
    		}
    		String reciptNum = this.txtreceiptNumber.getText();
    		if(reciptNum!=null && !reciptNum.trim().equals("")) {
    			this.txtreceiptNumber.setEnabled(false);
    			this.prmtReceipt.setEnabled(false);
    		}
    		
    	}
    	
    	NewFDCRoomPromptDialog roomDialog = new NewFDCRoomPromptDialog(new Boolean(false),null,null,MoneySysTypeEnum.SalehouseSys,null,(SellProjectInfo)this.prmtsellProject.getValue());
    	this.prmtroom.setSelector(roomDialog);
    	
    	
    	this.prmtCustomerEntry.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				setPromtCustomerFilterView(SHERevBillEditUI.this.prmtCustomerEntry,SHERevBillEditUI.this.prmtRelateBizBill);
			}
		});

    	this.prmtRelateFromCust.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				setPromtCustomerFilterView(SHERevBillEditUI.this.prmtRelateFromCust,SHERevBillEditUI.this.prmtRelateFromBizBill);
			}
		});
    	
    	
        EntityViewInfo kdtEntrys_moneyDefine_f7View = new EntityViewInfo();
        FilterInfo kdtEntrys_moneyDefine_f7Filter = new FilterInfo();
        kdtEntrys_moneyDefine_f7View.setFilter(kdtEntrys_moneyDefine_f7Filter);
        kdtEntrys_moneyDefine_f7Filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE));
    	SellProjectInfo currSellProInfo = (SellProjectInfo)this.prmtsellProject.getValue();
    	if(currSellProInfo!=null) {
        	Map detailSet = RoomDisplaySetting.getNewProjectSet(null,currSellProInfo.getId().toString());
        	boolean isMustBank = (detailSet==null)?false:((Boolean)detailSet.get(SHEParamConstant.T2_IS_MUST_BY_BANK)).booleanValue();
        	if(isMustBank){
        		kdtEntrys_moneyDefine_f7Filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.LOANAMOUNT_VALUE,CompareType.NOTEQUALS));
        		kdtEntrys_moneyDefine_f7Filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.ACCFUNDAMOUNT_VALUE,CompareType.NOTEQUALS));
        	}
    	}
    	ICellEditor kdtEntrys_moneyDefine_f7CellEditor = CRMClientHelper.getF7CellEditorForMoneyDefine(MoneySysTypeEnum.SalehouseSys);
        this.kdtEntrys.getColumn("moneyDefine").setEditor(kdtEntrys_moneyDefine_f7CellEditor);    	
        

        
    	this.storeFields();
    	this.initOldData(this.editData);
    	
    }
     
  

    
    /** 处理收据和发票的逻辑 */
    private void dealReceptAndInvoiceOnload(){
/*        final KDBizPromptBox kdtEntrys_receipt_PromptBox = new KDBizPromptBox();
        kdtEntrys_receipt_PromptBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ChequeQuery");
        kdtEntrys_receipt_PromptBox.setVisible(true);
        kdtEntrys_receipt_PromptBox.setEditable(true);
        kdtEntrys_receipt_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_receipt_PromptBox.setEditFormat("$number$");
        kdtEntrys_receipt_PromptBox.setCommitFormat("$number$");
        kdtEntrys_receipt_PromptBox.setEnabledMultiSelection(true);
        KDTDefaultCellEditor kdtEntrys_receipt_CellEditor = new KDTDefaultCellEditor(kdtEntrys_receipt_PromptBox);
        this.kdtEntrys.getColumn("receipt").setEditor(kdtEntrys_receipt_CellEditor);
        ObjectValueRender kdtEntrys_receipt_OVR = new ObjectValueRender();
        kdtEntrys_receipt_OVR.setFormat(new BizDataFormat("$number$"));
        this.kdtEntrys.getColumn("receipt").setRenderer(kdtEntrys_receipt_OVR);    	
        
        final KDBizPromptBox kdtEntrys_invoice_PromptBox = new KDBizPromptBox();
        kdtEntrys_invoice_PromptBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.InvoiceQuery");
        kdtEntrys_invoice_PromptBox.setVisible(true);
        kdtEntrys_invoice_PromptBox.setEditable(true);
        kdtEntrys_invoice_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_invoice_PromptBox.setEditFormat("$number$");
        kdtEntrys_invoice_PromptBox.setCommitFormat("$number$");
        kdtEntrys_invoice_PromptBox.setEnabledMultiSelection(true);
        KDTDefaultCellEditor kdtEntrys_invoice_CellEditor = new KDTDefaultCellEditor(kdtEntrys_invoice_PromptBox);
        this.kdtEntrys.getColumn("invoice").setEditor(kdtEntrys_invoice_CellEditor);
        ObjectValueRender kdtEntrys_invoice_OVR = new ObjectValueRender();
        kdtEntrys_receipt_OVR.setFormat(new BizDataFormat("$number$"));
        this.kdtEntrys.getColumn("invoice").setRenderer(kdtEntrys_invoice_OVR);    	*/
    	
        if (setting.getMarkInvoice() == 32) {	//统一发票管理
        	this.contreceiptNumber.setVisible(false);
        	this.continvoiceNumber.setVisible(false);
//        	this.prmtInvoice.setEntityViewInfo(NewCommerceHelper.getPermitCrmChequeView(ChequeTypeEnum.invoice));
//        	this.prmtReceipt.setEntityViewInfo(NewCommerceHelper.getPermitCrmChequeView(ChequeTypeEnum.receipt));
        }else{
        	this.contReceipt.setVisible(false);
        	this.contInvoice.setVisible(false);
        }
        
        dealReceiptAndInvoicePermit();
    }
    
    
    private void dealReceiptAndInvoicePermit(){
    	    if(this.editData.getId()!=null) {  //若明细中存在发票号或收据号，则不允许再录入发票或收据信息
    	            	boolean existCheque = false;
    	            	for (int i = 0; i < this.editData.getEntrys().size(); i++) {
    	            		SHERevBillEntryInfo entryInfo = (SHERevBillEntryInfo)this.editData.getEntrys().get(i);
    	            		if(entryInfo.getInvoiceNumber()!=null || entryInfo.getReceiptNumber()!=null) {
    	            			existCheque = true;
    	            			break;
    	            		}
    	    			}
    	            	if(existCheque) {
    	                	this.txtinvoiceNumber.setEnabled(false);
    	                	this.txtreceiptNumber.setEnabled(false);
    	                	this.prmtInvoice.setEnabled(false);
    	                	this.prmtReceipt.setEnabled(false);
    	            	}
    	            }    	
    }
    

    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.basecrm.SHERevBillFactory.getRemoteInstance();
    }

    protected IObjectValue createNewDetailData(KDTable table)
    {	
        return new SHERevBillEntryInfo();
    }

    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        SHERevBillInfo objectValue = new SHERevBillInfo();
		//if (SysContext.getSysContext().getCurrentOrgUnit(OrgType.getEnum("Sale")) != null 
		//		&& SysContext.getSysContext().getCurrentOrgUnit(OrgType.getEnum("Sale")).getBoolean("isBizUnit"))
    	Timestamp curTime = null;
		try {
			curTime = FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e1) {
			e1.printStackTrace();
			curTime = new Timestamp(System.currentTimeMillis());
		}
        
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
        objectValue.setBizDate(new Date(curTime.getTime()));
        objectValue.setIsTansCreate(false);
        objectValue.setRevBillType(RevBillTypeEnum.gathering);
        objectValue.setState(FDCBillStateEnum.SAVED);
        
		this.kdSelectRefundDetail.setVisible(false);
		this.kDSelectTransDetail.setVisible(false);
        
		
		RevBillTypeEnum billType = (RevBillTypeEnum)this.getUIContext().get("RevBillTypeEnum");
		if(billType!=null)	{
			objectValue.setRevBillType(billType);
			this.revBillType.setEnabled(false);
		}
		
		FDCBillInfo fdcBillInfo = (FDCBillInfo)this.getUIContext().get("FDCBillInfo");
		Object[] custEntrys = (Object[])this.getUIContext().get("CustomerEntrys");
		if(billType!=null && billType.equals(RevBillTypeEnum.transfer)) {   //转款情况
			if(custEntrys!=null && custEntrys.length>0)	
				objectValue.setRelateFromCust((SHECustomerInfo)custEntrys[0]);
			if(fdcBillInfo!=null)	{
				this.prmtRelateFromBizBill.setValue(fdcBillInfo);
				this.prmtRelateFromBizBill.setEnabled(false);
			}
		}else{
			if(custEntrys!=null)	this.prmtCustomerEntry.setValue(custEntrys);
    		if(fdcBillInfo!=null) {	
    			this.prmtRelateBizBill.setValue(fdcBillInfo);
    			this.prmtRelateBizBill.setEnabled(false);
    			if(fdcBillInfo.get("room")!=null)
    				objectValue.setRoom((RoomInfo)fdcBillInfo.get("room"));
    		}
		}
		
		if(custEntrys!=null && custEntrys.length>0){
			String custNames = "";
			for (int i = 0; i < custEntrys.length; i++) {
				SHECustomerInfo sheCustInfo = (SHECustomerInfo)custEntrys[i];
				custNames += "," + sheCustInfo.getName();
			}
			if(!custNames.equals("")) custNames = custNames.substring(1);
			objectValue.setPayCustomerName(custNames);
		}
		
		SellProjectInfo spInfo = (SellProjectInfo)this.getUIContext().get("SellProjectInfo");
		if(spInfo!=null)	objectValue.setSellProject(spInfo);
		RoomInfo roomInfo = (RoomInfo)this.getUIContext().get("RoomInfo");
		if(roomInfo!=null) objectValue.setRoom(roomInfo);
		
		SHERevBillEntryCollection revEntryColl = (SHERevBillEntryCollection)this.getUIContext().get("RevEntryColl");
		if(revEntryColl!=null) {
			for (int i = 0; i < revEntryColl.size(); i++) {
				SHERevBillEntryInfo retObjInfo = (SHERevBillEntryInfo)revEntryColl.get(i);
				//retObjInfo.setParent(this.editData);
				objectValue.getEntrys().add(retObjInfo);  
/*				IRow addRow = this.kdtEntrys.addRow();
				addRow.setUserObject(retObjInfo);
				addRow.getCell("moneyDefine").setValue(retObjInfo.getMoneyDefine());
				addRow.getCell("revAmount").setValue(retObjInfo.getRevAmount());
				addRow.getCell("transferFromEntryId").setValue(retObjInfo.getTransferFromEntryId());*/
			}
			//
		}
		
        return objectValue; 
    }

    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(this.editData.getId()!=null) {
    		if(!this.editData.getState().equals(FDCBillStateEnum.SUBMITTED) && !this.editData.getState().equals(FDCBillStateEnum.AUDITTING)){
    			FDCMsgBox.showInfo("该单据非提交或审批中状态，禁止审批！");
    			return;
    		}
    		SHERevBillFactory.getRemoteInstance().audit(this.editData.getId());
    		FDCMsgBox.showInfo("审批成功！");
    	}
    }

    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(this.editData.getId()!=null) {
    		if(!this.editData.getState().equals(FDCBillStateEnum.AUDITTED)){
    			FDCMsgBox.showInfo("该单据非已审批状态，禁止反审批！");
    			return;
    		}
    		SHERevBillFactory.getRemoteInstance().unAudit(this.editData.getId());
    		FDCMsgBox.showInfo("反审批成功！");
    	}
    }
    
    
    protected void prmtcurrency_dataChanged(DataChangeEvent e) throws Exception {
/*		Object newValue = e.getNewValue();
		if (newValue!=null && newValue instanceof CurrencyInfo) {
	    	CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit();
	    	Date bookedDate = new Date(FDCCommonServerHelper.getServerTimeStamp().getTime());
	    	
			BOSUuid srcid = ((CurrencyInfo)newValue).getId();			
	    	ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this,srcid,company,bookedDate);
	    	
	    	if(exchangeRate!=null){
	    		BigDecimal exRate = exchangeRate.getConvertRate();
	    		int exPrecision = exchangeRate.getPrecision();
		    	this.txtexchangeRate.setValue(exRate);
		    	this.txtexchangeRate.setPrecision(exPrecision);
	    	}else{
	    		this.txtexchangeRate.setValue(FDCHelper.ONE);
		    	this.txtexchangeRate.setPrecision(2);
	    	}
	    	
			// 本位币处理   如果选择的是本位币，则汇率不允许修改
	    	this.txtexchangeRate.setEditable(true);
			CurrencyInfo baseCurrency = company.getBaseCurrency();
			if (baseCurrency != null) {
				if (srcid.equals(baseCurrency.getId())) {
					this.txtexchangeRate.setEditable(false);
				}
			}
		}*/
	
    }

    protected void revBillType_itemStateChanged(ItemEvent e) throws Exception {
    	super.revBillType_itemStateChanged(e);
    	
    	RevBillTypeEnum revType = (RevBillTypeEnum)e.getItem();
    	changeComStateByRevBillType(revType);
    	
    	this.kdtEntrys.removeRows();    	
    	 
    }
    
    
    /**根据单据类型调整界面显示*/
    private void changeComStateByRevBillType(RevBillTypeEnum revType) {
    	if(RevBillTypeEnum.transfer.equals(revType)){
    		this.prmtRelateFromBizBill.setEnabled(true);
    		this.prmtRelateFromCust.setEnabled(true);
    		this.kdAddnewLine.setVisible(false);
    		this.kdInserLine.setVisible(false);
    		this.kdRemoveLine.setVisible(false);
    		this.kdSelectRefundDetail.setVisible(false);
    		this.kDSelectTransDetail.setVisible(true);
    		if(this.getOprtState().equals(OprtState.ADDNEW))
    			this.kDSelectTransDetail.setEnabled(true);
    		else
    			this.kDSelectTransDetail.setEnabled(false);
        	this.kdtEntrys.getColumn("moneyDefine").getStyleAttributes().setLocked(true);
        	this.contrevAmount.setBoundLabelText("转款金额");
        	this.kdtEntrys.getHead().getRow(0).getCell(this.kdtEntrys.getColumnIndex("moneyDefine")).setValue("转款款项");
        	this.kdtEntrys.getHead().getRow(0).getCell(this.kdtEntrys.getColumnIndex("revAmount")).setValue("转款金额");
        	if(this.kdtEntrys.getColumn("settlementCount")!=null)
        		this.kdtEntrys.getColumn("settlementCount").getStyleAttributes().setHided(true);
        	this.kdtEntrys.getColumn("hasRefundmentAmount").getStyleAttributes().setHided(true);
        	this.kdtEntrys.getColumn("hasTransferAmount").getStyleAttributes().setHided(true);
        	this.kdtEntrys.getColumn("leftAmount").getStyleAttributes().setHided(true);     
        	this.kdtEntrys.getColumn("revAmount").getStyleAttributes().setLocked(true);
        	this.kdtEntrys.getColumn("receiptNumber").getStyleAttributes().setHided(true);
        	this.kdtEntrys.getColumn("invoiceNumber").getStyleAttributes().setHided(true);
        	
        	this.txtinvoiceNumber.setEnabled(false);
        	this.txtreceiptNumber.setEnabled(false);
        	this.prmtInvoice.setEnabled(false);
        	this.prmtReceipt.setEnabled(false);
    	}else if(RevBillTypeEnum.refundment.equals(revType)){
    		this.prmtRelateFromBizBill.setEnabled(false);
    		this.prmtRelateFromCust.setEnabled(false);
    		this.kdAddnewLine.setVisible(false);
    		this.kdInserLine.setVisible(false);
    		this.kdRemoveLine.setVisible(false);
    		this.kdSelectRefundDetail.setVisible(true);
    		this.kDSelectTransDetail.setVisible(false);
        	this.kdtEntrys.getColumn("moneyDefine").getStyleAttributes().setLocked(true);
        	this.contrevAmount.setBoundLabelText("退款金额");
        	this.kdtEntrys.getHead().getRow(0).getCell(this.kdtEntrys.getColumnIndex("moneyDefine")).setValue("退款款项");
        	this.kdtEntrys.getHead().getRow(0).getCell(this.kdtEntrys.getColumnIndex("revAmount")).setValue("退款金额");
        	if(this.kdtEntrys.getColumn("settlementCount")!=null)
        		this.kdtEntrys.getColumn("settlementCount").getStyleAttributes().setHided(false);
        	this.kdtEntrys.getColumn("hasRefundmentAmount").getStyleAttributes().setHided(true);
        	this.kdtEntrys.getColumn("hasTransferAmount").getStyleAttributes().setHided(true);
        	this.kdtEntrys.getColumn("leftAmount").getStyleAttributes().setHided(true);
        	this.kdtEntrys.getColumn("revAmount").getStyleAttributes().setLocked(false);
        	this.kdtEntrys.getColumn("receiptNumber").getStyleAttributes().setHided(true);
        	this.kdtEntrys.getColumn("invoiceNumber").getStyleAttributes().setHided(true);
        	
        	this.txtinvoiceNumber.setEnabled(false);
        	this.txtreceiptNumber.setEnabled(false);
        	this.prmtInvoice.setEnabled(false);
        	this.prmtReceipt.setEnabled(false);        	
    	}else if(RevBillTypeEnum.gathering.equals(revType)){
    		this.prmtRelateFromBizBill.setEnabled(false);
    		this.prmtRelateFromCust.setEnabled(false);
    		this.kdAddnewLine.setVisible(true);
    		this.kdInserLine.setVisible(true);
    		this.kdRemoveLine.setVisible(true);
    		this.kdSelectRefundDetail.setVisible(false);
    		this.kDSelectTransDetail.setVisible(false);
        	this.kdtEntrys.getColumn("moneyDefine").getStyleAttributes().setLocked(false);
        	this.contrevAmount.setBoundLabelText("收款金额");
        	this.kdtEntrys.getHead().getRow(0).getCell(this.kdtEntrys.getColumnIndex("moneyDefine")).setValue("收款款项");
        	this.kdtEntrys.getHead().getRow(0).getCell(this.kdtEntrys.getColumnIndex("revAmount")).setValue("收款金额");
        	if(this.getOprtState().equals(OprtState.ADDNEW) && this.kdtEntrys.getColumn("settlementCount")!=null)
        		this.kdtEntrys.getColumn("settlementCount").getStyleAttributes().setHided(false);
        	this.kdtEntrys.getColumn("hasRefundmentAmount").getStyleAttributes().setHided(false);
        	this.kdtEntrys.getColumn("hasTransferAmount").getStyleAttributes().setHided(false);
        	this.kdtEntrys.getColumn("leftAmount").getStyleAttributes().setHided(false);    
        	this.kdtEntrys.getColumn("revAmount").getStyleAttributes().setLocked(false);
        	this.kdtEntrys.getColumn("receiptNumber").getStyleAttributes().setHided(false);
        	this.kdtEntrys.getColumn("invoiceNumber").getStyleAttributes().setHided(false);    
        	
        	if(this.getOprtState().equals(OprtState.ADDNEW)) {
	        	this.txtinvoiceNumber.setEnabled(true);
	        	this.txtreceiptNumber.setEnabled(true);
	        	this.prmtInvoice.setEnabled(true);
	        	this.prmtReceipt.setEnabled(true);
        	}
    	}
    	if(this.getOprtState().equals(OprtState.VIEW)){
    		this.kdAddnewLine.setEnabled(false);
    		this.kdInserLine.setEnabled(false);
    		this.kdRemoveLine.setEnabled(false);
    		this.kdSelectRefundDetail.setEnabled(false);
    		this.kDSelectTransDetail.setEnabled(false);
    	}else{
    		this.kdAddnewLine.setEnabled(true);
    		this.kdInserLine.setEnabled(true);
    		this.kdRemoveLine.setEnabled(true);
    		this.kdSelectRefundDetail.setEnabled(true);
    		this.kDSelectTransDetail.setEnabled(true);
    	}
    }
    
    
    protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
    	if(e.getType()!=1)  return;
    	if(e.getColIndex()==this.kdtEntrys.getColumnIndex("revAmount")){
    		addAllEntryRevAmountToTop();
    	}else if(e.getColIndex()==this.kdtEntrys.getColumnIndex("settlementCount")){
    		Integer oldValue = (Integer)e.getOldValue();
    		if(oldValue==null || oldValue.intValue()==0) 
    			oldValue = new Integer(1);
    		Integer newValue = (Integer)e.getValue();
    		if(newValue==null || newValue.intValue()==0) 
    			newValue = new Integer(1);
    		
    		int changCount = newValue.intValue() - oldValue.intValue();
    		if(changCount==0) return;
    		
    		if(changCount>0) {	//增加行
    			IRow currRow = this.kdtEntrys.getRow(e.getRowIndex());
    			Object usObj = currRow.getCell("settlementCount").getUserObject();
    			if(usObj==null) 	{
    				String settleFlagStr = String.valueOf(System.currentTimeMillis()); //作为分组标识
    				usObj = settleFlagStr;
    				currRow.getCell("settlementCount").setUserObject(settleFlagStr);
    			}

    			for (int i = 0; i < changCount; i++) {
        			IRow addRow = this.kdtEntrys.addRow(e.getRowIndex());
        			addRow.setUserObject(new SHERevBillEntryInfo());
        			addRow.getCell("settlementCount").setUserObject(usObj);
        			addRow.getCell("moneyDefine").setValue(currRow.getCell("moneyDefine").getValue());
        			addRow.getCell("transferFromEntryId").setValue(currRow.getCell("transferFromEntryId").getValue());
				}
    			
    			this.kdtEntrys.getMergeManager().mergeBlock(e.getRowIndex(), e.getColIndex(), e.getRowIndex()+newValue.intValue()-1, e.getColIndex());	
    			this.kdtEntrys.getMergeManager().mergeBlock(e.getRowIndex(), e.getColIndex()-1, e.getRowIndex()+newValue.intValue()-1, e.getColIndex()-1);
    		}else{			//删除行
    			for(int i=0;i<(-changCount);i++){
    				this.kdtEntrys.removeRow(e.getRowIndex()+oldValue.intValue()-1-i);
    			}
    		}    		
    	}
    }
    
    
    private void addAllEntryRevAmountToTop(){
		BigDecimal allRevAmount = new BigDecimal("0");
		for(int i=0;i<this.kdtEntrys.getRowCount();i++){
			IRow thisRow = this.kdtEntrys.getRow(i);
			SHERevBillEntryInfo revEntryInfo = (SHERevBillEntryInfo)thisRow.getUserObject();
			BigDecimal revAmount = (BigDecimal)thisRow.getCell("revAmount").getValue();
			if(revAmount==null) revAmount = new BigDecimal("0");
			allRevAmount = allRevAmount.add(revAmount);
			
			if(revEntryInfo.getTransferFromEntryId()==null && this.editData.getId()!=null)
				thisRow.getCell("leftAmount").setValue(revEntryInfo.getRemainAmount());
		}
		this.txtrevAmount.setValue(allRevAmount);	//收款金额汇总
    }
    
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		Object[] custObjs = (Object[])this.prmtCustomerEntry.getValue();
		if(custObjs==null || custObjs.length==0 || custObjs[0]==null){			
			FDCMsgBox.showWarning("客户必须先选择！");
			return;			 
		}    	
    	
		super.actionAddLine_actionPerformed(e);
		
		if(this.getOprtState().equals(OprtState.ADDNEW)) {
			IRow addRow = this.kdtEntrys.getRow(this.kdtEntrys.getRowCount()-1);
			addRow.getCell("settlementCount").setValue(new Integer(1));
			String invoiceNum = this.txtinvoiceNumber.getText();
			String receiptNum = this.txtreceiptNumber.getText();
	        if (setting.getMarkInvoice() == 32) {	//统一发票管理
	        	ChequeDetailEntryInfo invoiceInfo = (ChequeDetailEntryInfo)this.prmtInvoice.getValue();
	        	if(invoiceInfo!=null) invoiceNum = invoiceInfo.getNumber();
	        	ChequeDetailEntryInfo receiptInfo = (ChequeDetailEntryInfo)this.prmtReceipt.getValue();
	        	if(receiptInfo!=null) receiptNum = receiptInfo.getNumber();
	        	if(invoiceNum!=null && !invoiceNum.trim().equals(""))
	        		addRow.getCell("invoiceNumber").setValue(invoiceNum);
	        	if(receiptNum!=null && !receiptNum.trim().equals(""))
	        		addRow.getCell("receiptNumber").setValue(receiptNum);	        	
	        }
		}
    }
    
    public void actionInsertLine_actionPerformed(ActionEvent e)
    		throws Exception {
    	//当前行及上一行都是多行合并的(分组标识一样)，则计算笔数要加一
    	boolean isAddCount = false;
    	Object settleFlagStr = null;
		if(this.getOprtState().equals(OprtState.ADDNEW)) {
			IRow selectRow = KDTableUtil.getSelectedRow(this.kdtEntrys);
			if(selectRow!=null && selectRow.getRowIndex()>0) { 
				IRow upRow = this.kdtEntrys.getRow(selectRow.getRowIndex()-1);
				Object upFlag = upRow.getCell("settlementCount").getUserObject();
				Object selFlag = selectRow.getCell("settlementCount").getUserObject();
				if(upFlag!=null && selFlag!=null && upFlag.equals(selFlag)) {
					settleFlagStr = upFlag;
					//只有对分组标识中的第一行的值更新是会有效果
					IRow firstRow = upRow;
					while(firstRow.getRowIndex()>0){
						IRow tempRow = this.kdtEntrys.getRow(firstRow.getRowIndex()-1);
						Object tempObj = tempRow.getCell("settlementCount").getUserObject();
						if(tempObj==null || !tempObj.equals(settleFlagStr)) break;
						firstRow = tempRow;
					}
					Integer setlCount = (Integer)firstRow.getCell("settlementCount").getValue();
					firstRow.getCell("settlementCount").setValue(new Integer(setlCount.intValue()+1));
					isAddCount = true;
				}	
			}
		}
    	
    	super.actionInsertLine_actionPerformed(e);
    	IRow selectRow = KDTableUtil.getSelectedRow(this.kdtEntrys);    
    	if(selectRow!=null) {
	    	if(isAddCount){
	    		selectRow.getCell("settlementCount").setUserObject(settleFlagStr);
	    	}else{
	    		selectRow.getCell("settlementCount").setValue(new Integer(1));
	    	}
    	}

    }
    
    public void actionRemoveLine_actionPerformed(ActionEvent e)
    		throws Exception {
    	IRow selectRow = KDTableUtil.getSelectedRow(this.kdtEntrys);
    	if(selectRow==null) return;
    	boolean isDeleteCount = false;
    	Object settleFlagStr = selectRow.getCell("settlementCount").getUserObject();
    	
		IRow firstRow = selectRow;
		while(firstRow.getRowIndex()>0){
			IRow tempRow = this.kdtEntrys.getRow(firstRow.getRowIndex()-1);
			Object tempObj = tempRow.getCell("settlementCount").getUserObject();
			if(tempObj==null || !tempObj.equals(settleFlagStr)) break;
			firstRow = tempRow;
		}
    	
    	Integer setlCount = (Integer)firstRow.getCell("settlementCount").getValue(); 
    	if(setlCount==null || setlCount.intValue()==0) setlCount = new Integer(1);
    	if(setlCount.intValue()>1){
    		isDeleteCount = true;			
    	}
    	
    	super.actionRemoveLine_actionPerformed(e);
    	
    	if(isDeleteCount){
    		for(int i=0;i<this.kdtEntrys.getRowCount();i++) {
    			IRow tempRow = this.kdtEntrys.getRow(i);
    			Object tempObj = tempRow.getCell("settlementCount").getUserObject();
    			if(tempObj!=null && tempObj.equals(settleFlagStr)){
    				tempRow.getCell("settlementCount").setValue(new Integer(setlCount.intValue()-1));
    				break;
    			}
    		}
    	}
    	addAllEntryRevAmountToTop();
    }
    
    protected void kdSelectRefundDetail_actionPerformed(ActionEvent e)
    		throws Exception {
		Object[] custObjs = (Object[])this.prmtCustomerEntry.getValue();
		if(custObjs==null || custObjs.length==0 || custObjs[0]==null){			
			FDCMsgBox.showWarning("客户必须先选择！");
			return;			 
		}    	
    

		UIContext uiContext = new UIContext(this);	
		CRMHelper.sortCollection(custObjs, "name", true);
		uiContext.put("CustomerObjs", custObjs);
		FDCBillInfo relateBillInfo = (FDCBillInfo)this.prmtRelateBizBill.getValue();
		uiContext.put("RelateBillInfo", relateBillInfo);
		uiContext.put("CurrRevBillInfo", this.editData);	
		
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)//打开转款明细选择窗口
								.create(SelectRefundDetailUI.class.getName(), uiContext);
			uiWindow.show();
			//生成转款单的实收明细
			Map resContext = uiWindow.getUIObject().getUIContext();
			SHERevBillEntryCollection retObjColl = (SHERevBillEntryCollection)resContext.get("RetObjColl");
			if(retObjColl!=null) {
				this.kdtEntrys.removeRows();
				for(int i=0;i<retObjColl.size();i++){
					SHERevBillEntryInfo retObjInfo = (SHERevBillEntryInfo)retObjColl.get(i);
					retObjInfo.setParent(this.editData);
					IRow addRow = this.kdtEntrys.addRow();
					addRow.setUserObject(retObjInfo);
					addRow.getCell("moneyDefine").setValue(retObjInfo.getMoneyDefine());
					addRow.getCell("revAmount").setValue(retObjInfo.getRevAmount());
					addRow.getCell("transferFromEntryId").setValue(retObjInfo.getTransferFromEntryId());
					
					addRow.getCell("hasRefundmentAmount").setValue(retObjInfo.getHasRefundmentAmount());
					addRow.getCell("hasTransferAmount").setValue(retObjInfo.getHasTransferAmount());
					addRow.getCell("leftAmount").setValue(retObjInfo.getRemainAmount());
				}
				addAllEntryRevAmountToTop();
			}
		} catch (UIException e1) {
			e1.printStackTrace();
		}
    
    }
    
    protected void kDSelectTransDetail_actionPerformed(ActionEvent e)
    		throws Exception {
		Object[] custObjs = (Object[])this.prmtCustomerEntry.getValue();
		if(custObjs==null || custObjs.length==0 || custObjs[0]==null){			
			FDCMsgBox.showWarning("客户必须先选择！");
			return;			 
		}    	

		FDCBillInfo reltInfo = (FDCBillInfo)this.prmtRelateBizBill.getValue();		
		/*		if(reltInfo==null){ //转入单据一定要选择
			FDCMsgBox.showWarning("关联单据必须选择！");
			return;
		}		*/
		
		UIContext uiContext = new UIContext(this);
		FDCBillInfo relateFromInfo = (FDCBillInfo)this.prmtRelateFromBizBill.getValue();
		SHECustomerInfo reltCustInfo = (SHECustomerInfo)this.prmtRelateFromCust.getValue();
		if(relateFromInfo==null && reltCustInfo==null){ 
			FDCMsgBox.showWarning("转出关联单据和转出款项客户必须选择一个！");
			return;
		}				
		
		uiContext.put("RelateBizBillInfo", reltInfo);
		uiContext.put("RelateFromBizBillInfo", relateFromInfo);
		uiContext.put("RelateFromCustInfo", reltCustInfo);
		CRMHelper.sortCollection(custObjs, "name", true);
		uiContext.put("CustomerObjs", custObjs);
		
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)//打开转款明细选择窗口
								.create(SelectTransfDetailUI.class.getName(), uiContext);
			uiWindow.show();
			
			//生成转款单的实收明细
			Map resContext = uiWindow.getUIObject().getUIContext();
			SHERevBillEntryCollection retObjColl = (SHERevBillEntryCollection)resContext.get("RetObjColl");
			
			if(retObjColl!=null) {
				this.kdtEntrys.removeRows();
				for(int i=0;i<retObjColl.size();i++){
					SHERevBillEntryInfo retObjInfo = (SHERevBillEntryInfo)retObjColl.get(i);
					retObjInfo.setParent(this.editData);
					IRow addRow = this.kdtEntrys.addRow();
					addRow.setUserObject(retObjInfo);
					addRow.getCell("moneyDefine").setValue(retObjInfo.getMoneyDefine());
					addRow.getCell("revAmount").setValue(retObjInfo.getRevAmount());
					addRow.getCell("transferFromEntryId").setValue(retObjInfo.getTransferFromEntryId());
					if(retObjInfo.getTransferFromEntryId()!=null){
						addRow.getStyleAttributes().setBackground(new Color(0xFCFBDF));
						addRow.getCell("moneyDefine").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
						addRow.getCell("revAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
					}else{
						addRow.getCell("hasRefundmentAmount").setValue(retObjInfo.getHasRefundmentAmount());
						addRow.getCell("hasTransferAmount").setValue(retObjInfo.getHasTransferAmount());
						addRow.getCell("leftAmount").setValue(retObjInfo.getRemainAmount());
					}
				}
				addAllEntryRevAmountToTop();
			}
		} catch (UIException e1) {
			e1.printStackTrace();
		}
    
    }
    
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(this.txtNumber.isEditable()  &&  this.txtNumber.isEnabled()  &&  StringUtils.isEmpty(this.txtNumber.getText())){
			FDCMsgBox.showInfo(this, "编码不能为空！");
			return;
		}    	
    	
		Object[] custObjs = (Object[])this.prmtCustomerEntry.getValue();
		if(custObjs==null || custObjs.length==0) {
			FDCMsgBox.showInfo(this, "客户不能为空！");
			return;
		}
		
		if(this.kdtEntrys.getRowCount()==0){
			FDCMsgBox.showInfo(this, "明细列表不能为空！");
			return;
		}
		
		ChequeDetailEntryInfo invoiceInfo = (ChequeDetailEntryInfo)this.prmtInvoice.getValue();
		if(invoiceInfo!=null) {
			 BigDecimal revAllAmount = (BigDecimal)this.txtrevAmount.getBigDecimalValue();
			 if(revAllAmount==null) revAllAmount = new BigDecimal("0");
			 if(invoiceInfo.getCheque()!=null){
				 CRMChequeInfo crmCheqInfo = CRMChequeFactory.getRemoteInstance().getCRMChequeInfo("select limitAmount where id = '"+invoiceInfo.getCheque().getId()+"'");
				 if(crmCheqInfo.getLimitAmount()!=null && revAllAmount.compareTo(crmCheqInfo.getLimitAmount())>0){
					 FDCMsgBox.showWarning("开票金额不能大于票据限额！");
						this.abort();
			 	}
			 }
		}		
		
		SellProjectInfo currSellProInfo = (SellProjectInfo)this.prmtsellProject.getValue();
		if(currSellProInfo==null){
			FDCMsgBox.showInfo(this, "项目不能为空！");
			return;
		}
		
		boolean isMustBank = false;		//若必须必须银行放款则不允许有按揭款和公积金收款
		RevBillTypeEnum currRevBillType = (RevBillTypeEnum)this.revBillType.getSelectedItem();
		if(currRevBillType.equals(RevBillTypeEnum.transfer)){ //转款时转出和转入单据不能一样，客户也是不能一样
	        FDCBillInfo relateBillInfo = (FDCBillInfo)this.prmtRelateBizBill.getValue();
	        FDCBillInfo relateFrommBillInfo = (FDCBillInfo)this.prmtRelateFromBizBill.getValue();
			if(relateBillInfo!=null && relateFrommBillInfo!=null){
				if(relateBillInfo.getId().equals(relateFrommBillInfo.getId())) {
					FDCMsgBox.showInfo(this, "转款单中关联单据和转出关联单据不能相同！");
					return;					
				}
			}
	        if(relateBillInfo==null && relateFrommBillInfo==null){
	        	SHECustomerInfo reltCustInfo = (SHECustomerInfo)this.prmtRelateFromCust.getValue();
	        	if(reltCustInfo!=null && custObjs.length==1){
	        		SHECustomerInfo toCustInfo = (SHECustomerInfo)custObjs[0];
	        		if(reltCustInfo.getId().equals(toCustInfo.getId())){
						FDCMsgBox.showInfo(this, "转款单中客户和转出款项客户不能相同！");
						return;		
	        		}
	        	}
	        }
		}else if(currRevBillType.equals(RevBillTypeEnum.gathering)){	
        	Map detailSet = RoomDisplaySetting.getNewProjectSet(null,currSellProInfo.getId().toString());
        	isMustBank = (detailSet==null)?false:((Boolean)detailSet.get(SHEParamConstant.T2_IS_MUST_BY_BANK)).booleanValue();
		}
		
		
		RevBillTypeEnum revBillType = (RevBillTypeEnum)this.revBillType.getSelectedItem();
		for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			IRow currRow = this.kdtEntrys.getRow(i);
			MoneyDefineInfo defineInfo = (MoneyDefineInfo)currRow.getCell("moneyDefine").getValue();
			if(defineInfo==null){
				Object settleFlagStr = currRow.getCell("settlementCount").getUserObject();
				IRow firstRow = currRow;
				while(firstRow.getRowIndex()>0){
					IRow tempRow = this.kdtEntrys.getRow(firstRow.getRowIndex()-1);
					Object tempObj = tempRow.getCell("settlementCount").getUserObject();
					if(tempObj==null || !tempObj.equals(settleFlagStr)) break;
					firstRow = tempRow;
				}				
				if(firstRow!=null && firstRow.getRowIndex()!=currRow.getRowIndex()) {
					if(firstRow.getCell("moneyDefine").getValue()==null){
						MsgBox.showInfo(this, "收费款项不能为空！");
						this.abort();
					}else{
						currRow.getCell("moneyDefine").setValue(firstRow.getCell("moneyDefine").getValue());
					}
				}
			}
			
			if(isMustBank){
				if(defineInfo.getMoneyType().equals(MoneyTypeEnum.LoanAmount) || defineInfo.getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
					MsgBox.showInfo(this, "‘贷款类型必须银行放款’，因此款项不能有按揭款或公积金！");
					this.abort();		
				}
			}
			
			BigDecimal revAmount = (BigDecimal)currRow.getCell("revAmount").getValue();
			if(RevBillTypeEnum.gathering.equals(revBillType)){
				if(revAmount==null || revAmount.compareTo(new BigDecimal("0"))<=0) {
					MsgBox.showInfo(this, "收款金额必须大于0！");
					this.abort();				
				}					
			}else if(RevBillTypeEnum.refundment.equals(revBillType)){
				if(revAmount==null || revAmount.compareTo(new BigDecimal("0"))>=0) {
					MsgBox.showInfo(this, "退款金额必须小于0！");
					this.abort();				
				}					
			}if(RevBillTypeEnum.transfer.equals(revBillType)){
				String transfEntryId = (String)currRow.getCell("transferFromEntryId").getValue();
				if(transfEntryId==null) {
					if(revAmount==null || revAmount.compareTo(new BigDecimal("0"))<=0) {
						MsgBox.showInfo(this, "转款单的转入明细的转款金额必须大于0！");
						this.abort();				
					}						
				}else{
					if(revAmount==null || revAmount.compareTo(new BigDecimal("0"))>=0) {
						MsgBox.showInfo(this, "转款单的转出明细的转款金额必须小于0！");
						this.abort();				
					}		
				}				
			}
		
		}
    	
    	this.chkMenuItemSubmitAndPrint.setSelected(false);
    	super.actionSubmit_actionPerformed(e);
    	
    	this.setOprtState(OprtState.VIEW);
    	if(this.editData.getRevBillType()!=null && this.editData.getRevBillType().equals(RevBillTypeEnum.transfer))
    		this.kDSelectTransDetail.setEnabled(false);
    	
    	this.btnSubmitAudit.setEnabled(false);
    	this.kdtEntrys.getColumn("settlementCount").getStyleAttributes().setHided(true);
/*    	this.storeFields();
    	this.initOldData(this.editData);*/
    }
 
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	if(this.editData.getId()!=null){
    		if(this.editData.getState()==null || this.editData.getState().equals(FDCBillStateEnum.SAVED)
    				|| this.editData.getState().equals(FDCBillStateEnum.SUBMITTED) ){
    			if(this.editData.getRelateBizType()!=null && this.editData.getRelateBizType().equals(RelatBizType.Change)){
    				FDCMsgBox.showWarning("此单据由变更单生成，禁止修改！");
    			}else{
    		    	if(this.editData.getRevBillType()!=null && this.editData.getRevBillType().equals(RevBillTypeEnum.gathering)) {
    			    	boolean isQuitOrTrans = SHERevBillEntryFactory.getRemoteInstance().exists(" where hasRefundmentAmount > 0 and hasTransferAmount >0 and parent.id = '"+this.editData.getId()+"' ");
    			    	if(isQuitOrTrans){
    			    		FDCMsgBox.showWarning("此收款单据已发生退款和转款，禁止修改！");
    			    		return;    		
    			    	}
    		    	}
    				
    				super.actionEdit_actionPerformed(e);
    				
    				if(RevBillTypeEnum.transfer.equals(this.editData.getRevBillType())){
    					this.kDSelectTransDetail.setEnabled(true);
    				}else if(RevBillTypeEnum.refundment.equals(this.editData.getRevBillType())){
    					this.kdSelectRefundDetail.setEnabled(true);
    				}else if(RevBillTypeEnum.gathering.equals(this.editData.getRevBillType())){
    					this.kdAddnewLine.setEnabled(true);
    					this.kdInserLine.setEnabled(true);
    					this.kdRemoveLine.setEnabled(true);
    				}
    				
    				dealReceiptAndInvoicePermit();
    			}
    		}else{
    			FDCMsgBox.showWarning("当前单据非保存或提交状态，禁止修改！");
    		}
    	}
    	
    }
    
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	if(this.editData.getId()!=null){
			if(this.editData.getRelateBizType()!=null && this.editData.getRelateBizType().equals(RelatBizType.Change)){
				FDCMsgBox.showWarning("此单据由变更单生成，禁止删除！");
				return;
			}
			if(this.editData.isIsTansCreate()){
				FDCMsgBox.showWarning("此单据由转款单自动生成，禁止删除！");
				return;
			}			
    		if(this.editData.getState()!=null && !this.editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
	    		FDCMsgBox.showWarning("此单据非提交状态，禁止删除！");
	    		return;    	
    		} 
    	}
    	super.actionRemove_actionPerformed(e);
    }
    
    
    
    
    
	protected Map listenersMap = new HashMap();
    
    protected void addDataChangeListener(JComponent com) {
    	
    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
    	
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDPromptBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDFormattedTextField){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDDatePicker){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	} else if(com instanceof KDComboBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
	    		}
	    	}
    	}
		
    }
        
    protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;	
  	
		if(com instanceof KDPromptBox){
			listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDFormattedTextField){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDDatePicker){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	} 
    	else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	} 
		
		if(listeners!=null && listeners.length>0){
			listenersMap.put(com,listeners );
		}
    }    
 
    
    
    public void actionMakeInvoice_actionPerformed(ActionEvent e)
    		throws Exception {
    	if(this.editData.getId()==null) return;
    	
    	SHERevBillEntryCollection revEntryColl = SHERevBillEntryFactory.getRemoteInstance()
			.getSHERevBillEntryCollection("select *,moneyDefine.name,parent.* where parent.id = '"+this.editData.getId()+"' ");

    	UIContext uiContext = new UIContext(this); 		
    	uiContext.put("revBillColl", revEntryColl);
    	uiContext.put("customer", this.prmtCustomerEntry.getValue());    	
    	
    	RoomInfo roomInfo = (RoomInfo)this.prmtroom.getValue();
    	if(roomInfo!=null) uiContext.put("Room", roomInfo);
    	
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
							.create(MakeInvoiceEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
    }
    
    public void actionRecycle_actionPerformed(ActionEvent e) throws Exception {
    	if(this.editData.getId()!=null && (this.editData.getInvoiceNumber()!=null || this.editData.getReceiptNumber()!=null))	{
    		ChequeDetailEntryFactory.getRemoteInstance().clearInvoice(this.editData.getId().toString(), true);
    		
    		FDCMsgBox.showInfo("清除票据成功 ！");
    		this.editData.setInvoiceNumber(null);
    		this.editData.setReceiptNumber(null);
    	}
    }
    
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.editData.getId()==null) return;

        BOSQueryDelegate data = new CRMDataProvider(this.editData.getId().toString()
        			,"com.kingdee.eas.fdc.basecrm.app.SHERevBillQuery","com.kingdee.eas.fdc.basecrm.app.SHERevBillEntryQuery","parent.id");
        KDNoteHelper appHlp = new KDNoteHelper();
        appHlp.print(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
    }    	

    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.editData.getId()==null) return;
        BOSQueryDelegate data = new CRMDataProvider(this.editData.getId().toString()
    			,"com.kingdee.eas.fdc.basecrm.app.SHERevBillQuery","com.kingdee.eas.fdc.basecrm.app.SHERevBillEntryQuery","parent.id");
        KDNoteHelper appHlp = new KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
    }
 
    
   
    public void actionSubmitAudit_actionPerformed(ActionEvent e)
    		throws Exception {
    	this.editData.put("SubmitAndAudit", "TRUE");
    	
    	this.actionSubmit_actionPerformed(e);
    }
    
    
    protected void prmtRelateBizBill_dataChanged(DataChangeEvent e)
    		throws Exception {
    	Object billInfo = this.prmtRelateBizBill.getValue();
    	if(billInfo!=null) {
    		if(billInfo instanceof BaseTransactionInfo){
    			BaseTransactionInfo transInfo = (BaseTransactionInfo)billInfo;
    			if(this.prmtroom.getValue()==null) {
    				this.prmtroom.setValue(transInfo.getRoom());
    			}

				SHECustomerCollection sheCustColl = (SHECustomerCollection)transInfo.get("SHECustomerColl");
				if(sheCustColl!=null){	//客户一定是交易里的全部客户，不能修改
					this.prmtCustomerEntry.setValue(sheCustColl.toArray());
					String custNamsStr = "";
					for (int i = 0; i < sheCustColl.size(); i++) {
						if(sheCustColl.get(i)!=null)
							custNamsStr += ","+sheCustColl.get(i).getName();
					}
					if(!custNamsStr.equals("")) custNamsStr = custNamsStr.substring(1);
					this.txtpayCustomerName.setText(custNamsStr);
				}    			
    		}
    		this.prmtCustomerEntry.setEnabled(false);
    	}else{
    		this.prmtCustomerEntry.setValue(null);
    		this.prmtCustomerEntry.setEnabled(true);
    	}
    }
    
    
    protected void prmtRelateFromBizBill_dataChanged(DataChangeEvent e)
    		throws Exception {
    	Object billFromInfo = this.prmtRelateFromBizBill.getValue();
    	this.prmtRelateFromCust.setValue(null);
    	if(billFromInfo!=null){
    		this.prmtRelateFromCust.setEnabled(false);
    	}else{
    		this.prmtRelateFromCust.setEnabled(true);
    	}
    }
    
    /**根据所选择业务单据f7的值，限制客户f7的过滤条件*/
    private void setPromtCustomerFilterView(KDBizPromptBox prmtCustBox,KDBizPromptBox prmtBizBox){
		FDCBillInfo relateBillInfo = (FDCBillInfo)prmtBizBox.getValue();
		try {				
			if(relateBillInfo==null){
				UserInfo currInfo = SysContext.getSysContext().getCurrentUserInfo();	
				SellProjectInfo rootSpInfo = this.editData.getSellProject();	//所选项目对应的根项目
				if(rootSpInfo!=null) {
					while(rootSpInfo.getLevel()!=1)
						rootSpInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo("select * where id = '"+rootSpInfo.getParent().getId()+"' ");
				}	
				prmtCustBox.setEntityViewInfo(NewCommerceHelper.getPermitCustomerView(rootSpInfo,currInfo));	
			}else{	//只能选关联业务单据里的客户
				String idStr = "'nullnull'";
				ITranCustomerEntry custFacFactory = CRMHelper.getCustmerEntryInterFace(null,relateBillInfo.getId());
				TranCustomerEntryCollection custEntryColl = custFacFactory.getTranCustomerEntryCollection("select customer.id where head.id='"+relateBillInfo.getId()+"' ");
				for (int i = 0; i < custEntryColl.size(); i++) {
					if(custEntryColl.get(i).getCustomer()!=null)
						idStr += ",'" + custEntryColl.get(i).getCustomer().getId()+"'";
				}
				EntityViewInfo custFilterView = new EntityViewInfo();
				FilterInfo custFilter = new FilterInfo();
				custFilterView.setFilter(custFilter);
				custFilter.getFilterItems().add(new FilterItemInfo("id",idStr,CompareType.INNER));
				prmtCustBox.setEntityViewInfo(custFilterView);
			}
			prmtCustBox.getQueryAgent().resetRuntimeEntityView();
			prmtCustBox.setRefresh(true);
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
    }
    
    
    
    
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
		            
		            if(!iCodingRuleManager.isModifiable(editData, companyID))
		            	txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }
    
    
}