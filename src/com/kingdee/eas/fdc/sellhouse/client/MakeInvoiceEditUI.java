/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevCustEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevCustEntryFactory;
import com.kingdee.eas.fdc.basecrm.client.CRMDataProvider;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.CRMChequeFactory;
import com.kingdee.eas.fdc.sellhouse.CRMChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeRevListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeRevListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class MakeInvoiceEditUI extends AbstractMakeInvoiceEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MakeInvoiceEditUI.class);
    MarketDisplaySetting setting=new MarketDisplaySetting();
  //setting.getMarkInvoice() 营销环节是否启用统一票据管理 16不启用  32启用
    private boolean isFromRev = false;
    
    private SellProjectInfo sellproject = null;
    /**
     * output class constructor
     */
    public MakeInvoiceEditUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
    	storeCustomer();
    	storeRevList();
    	super.storeFields();
    	
    	UserInfo thisUserInfo = (UserInfo)this.prmtUser.getValue();
    	this.editData.setWrittenOffer(thisUserInfo);
    	Date thisDate = (Date)this.pkDate.getValue();
    	if(thisDate!=null)
    		this.editData.setWrittenOffTime(new Timestamp(thisDate.getTime()));
    	this.editData.setAmount(this.txtAmount.getBigDecimalValue());
    	this.editData.setRoom((RoomInfo)this.prmtRoom.getValue());
    	this.editData.setStatus((ChequeStatusEnum)this.comboStatus.getSelectedItem());
    	this.editData.setChequeCustomer(this.txtCustomer.getText());
    	this.editData.setDes(this.txtDescription.getText());
    	this.editData.setCheQueType((ChequeTypeEnum)this.KDchequeType.getSelectedItem());
    	this.editData.setNumber(this.txtNumber.getText());
    }

   
	private void storeRevList() {
		ChequeRevListEntryCollection  revListColl = this.editData.getRevListEntry();
		revListColl.clear();
		for(int i =0 ; i <this.tblReceive.getRowCount(); i++){
			revListColl.add((ChequeRevListEntryInfo)this.tblReceive.getRow(i).getUserObject());
		}
	}

	private void storeCustomer() {
		ChequeCustomerEntryCollection  chequeCustomer =this.editData.getCustomerEntry();
		chequeCustomer.clear();
		Object[] customerArray = (Object[])this.prmtCustomer.getValue();
		if(customerArray.length >0 &&customerArray[0]!=null ){
			for(int i = 0; i <customerArray.length ; i++){
				ChequeCustomerEntryInfo customer= new ChequeCustomerEntryInfo();
				customer.setCustomer((SHECustomerInfo)customerArray[i]);
				chequeCustomer.add(customer);
			}
			
		}
	}

	protected IObjectValue createNewData() {
		//新增进来但是是编辑界面
		this.setOprtState(OprtState.EDIT);
		ChequeDetailEntryInfo info = null ;
		if(this.getUIContext().get("id")!=null){//开票界面
			sellproject =(SellProjectInfo)this.getUIContext().get("sellProject");
			this.contCheque.setEnabled(false);
			this.KDchequeType.setEnabled(false);
			try {
				info = ChequeDetailEntryFactory.getRemoteInstance().getChequeDetailEntryInfo(new ObjectUuidPK(this.getUIContext().get("id").toString()), getSelectors());
				this.txtNumber.setText(info.getNumber());
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		if(this.getUIContext().get("revBillColl")!=null){//收款单传过来的
			isFromRev = true;
			info = new ChequeDetailEntryInfo();
			ChequeRevListEntryCollection  chequeRev = info.getRevListEntry();
			chequeRev.clear();
			SHERevBillEntryCollection  revBillC = (SHERevBillEntryCollection)this.getUIContext().get("revBillColl");
			ChequeRevListEntryInfo chequeRevList = null;
			for(int i = 0 ; i < revBillC.size(); i++){
				chequeRevList = new ChequeRevListEntryInfo();
				chequeRevList.setRevBillEntry(revBillC.get(i));
				chequeRevList.setChequeDetail(info);
				chequeRevList.setInvoiceNum(revBillC.get(i).getInvoiceNumber());
				chequeRev.add(chequeRevList);
			}
		}
		
		if(this.getUIContext().get("customer")!=null){//收款单传过来
			isFromRev = true;
			Object[] object = (Object[])this.getUIContext().get("customer");
			ChequeCustomerEntryCollection chequeCustomerColl =  info.getCustomerEntry();
			chequeCustomerColl.clear();
			ChequeCustomerEntryInfo chequeCustomerInfo = null;
			String custNamesStr = "";
			if(object!=null){
				for(int i = 0 ; i < object.length; i++){
					chequeCustomerInfo = new ChequeCustomerEntryInfo();
					chequeCustomerInfo.setCustomer((SHECustomerInfo)object[i]);
					chequeCustomerInfo.setCheque(info);
					chequeCustomerColl.add(chequeCustomerInfo);
					custNamesStr += ","+chequeCustomerInfo.getCustomer().getName();
				}
			}
			if(!custNamesStr.equals("")) custNamesStr = custNamesStr.substring(1);
			info.setChequeCustomer(custNamesStr);
		}
		
		RoomInfo roomInfo = (RoomInfo)this.getUIContext().get("Room"); 
		if(roomInfo!=null){
			info.setRoom(roomInfo);
		}
		this.prmtRoom.setEnabled(true);
		if(sellproject!=null) {
			EntityViewInfo roomView = new EntityViewInfo();
			FilterInfo roomFilter = new FilterInfo();
			roomView.setFilter(roomFilter);
			roomFilter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellproject.getId()));
			this.prmtRoom.setEntityViewInfo(roomView);
		}
		
		info.setWrittenOffer(SysContext.getSysContext().getCurrentUserInfo());
		info.setWrittenOffTime(new Timestamp(new Date().getTime()));
		return info;
	}
	
	public void loadFields()  {
		super.loadFields();

		EventListener[] listeners = this.prmtCustomer.getListeners(DataChangeListener.class);//获得控件上所有的监听器
		EventListener[] listenerCheq = this.f7Cheque.getListeners(DataChangeListener.class);//获得控件上所有的监听器
		for (int i = 0; i < listeners.length; i++) {
			this.prmtCustomer.removeDataChangeListener((DataChangeListener) listeners[i]);
			
		}
		for (int i = 0; i < listenerCheq.length; i++) {
			this.f7Cheque.removeDataChangeListener((DataChangeListener) listenerCheq[i]);
		}		
		
		if(this.editData.getId()!=null){
			this.f7Cheque.setValue(this.editData); 
		}		
		loadCustomerEntry();
		loadPayListEntry();
		
		 for (int i = 0; i < listeners.length; i++) {//加载完数据之后，加上监听器
				this.prmtCustomer.addDataChangeListener((DataChangeListener) listeners[i]);
			}
		 for (int i = 0; i < listenerCheq.length; i++) {//加载完数据之后，加上监听器
				this.f7Cheque.addDataChangeListener((DataChangeListener) listenerCheq[i]);
			}
		 
	}

	private void loadPayListEntry() {
		this.tblReceive.checkParsed();
		this.tblReceive.removeRows();
		ChequeRevListEntryCollection  revListColl = this.editData.getRevListEntry();
		CRMHelper.sortCollection(revListColl, "revBillEntry.parent.bizDate", false);
		for(int i = 0; i< revListColl.size(); i++){
			ChequeRevListEntryInfo revListInfo = revListColl.get(i);//付款单分录
			IRow row = tblReceive.addRow();
			row.setUserObject(revListInfo);
			row.getCell("makeInvoiceAmount").setValue(revListInfo.getChequeAmount());
			row.getCell("isSelected").setValue(Boolean.valueOf(revListInfo.isIsSelect()));
			
			SHERevBillEntryInfo  sheRevBillInfo = revListInfo.getRevBillEntry();//付款明细
			if(sheRevBillInfo==null) continue;
			row.getCell("receipt").setValue(sheRevBillInfo.getReceiptNumber());
			row.getCell("invoiceNum").setValue(sheRevBillInfo.getInvoiceNumber());			
			row.getCell("moneyName").setValue(sheRevBillInfo.getMoneyDefine());
			row.getCell("amount").setValue(sheRevBillInfo.getRevAmount());//
			row.getCell("hasMakeAmount").setValue(sheRevBillInfo.getHasMakeInvoiceAmount());
			row.getCell("hasTransferredAmount").setValue(sheRevBillInfo.getHasTransferAmount());
			row.getCell("hasRefundmentAmount").setValue(sheRevBillInfo.getHasRefundmentAmount());
			row.getCell("remainAmount").setValue(sheRevBillInfo.getRemainAmount());
			row.getCell("hasMakeAmount").setValue(sheRevBillInfo.getHasMakeInvoiceAmount());
			if(sheRevBillInfo.getParent()!=null){
				row.getCell("revNumber").setValue(sheRevBillInfo.getParent().getNumber());
				row.getCell("billDate").setValue(sheRevBillInfo.getParent().getBizDate());
				row.getCell("des").setValue(sheRevBillInfo.getParent().getDescription());
			}
		}
		
	}

	private void loadCustomerEntry() {
		ChequeCustomerEntryCollection  chequeCustomer =this.editData.getCustomerEntry();
		Object[] customerArray = new Object[chequeCustomer.size()];
		for(int i =0 ; i< chequeCustomer.size();i++){
			ChequeCustomerEntryInfo chequeCustomerInfo = chequeCustomer.get(i);
			SHECustomerInfo  customer = chequeCustomerInfo.getCustomer();
			customerArray[i] = customer;
		}
		this.prmtCustomer.setValue(customerArray);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ChequeDetailEntryFactory.getRemoteInstance();
	}
	public void onLoad() throws Exception {
		this.tblReceive.checkParsed();
		super.onLoad();
		
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		this.txtDescription.setMaxLength(255);
		
		//this.KDchequeType.setEnabled(false);
		initF7Customer();
		initRoom();
		initcontrolbtn();
		initChequeNumber();
		initTblRev();

    	NewFDCRoomPromptDialog roomDialog = new NewFDCRoomPromptDialog(new Boolean(false),null,null,MoneySysTypeEnum.SalehouseSys,null,sellproject);
    	this.prmtRoom.setSelector(roomDialog);
		
		this.storeFields();
		this.initOldData(this.editData);

	}
	
	  private void initRoom() {
		
	}

	private void initcontrolbtn() {
		  this.contBatchNumber.setEnabled(false);
		 if(isFromRev){//非开票界面
			 this.KDchequeType.setSelectedItem(ChequeTypeEnum.invoice);
			 //客户不能编辑
			 //this.contCustomer.setEnabled(false);
			 this.prmtCustomer.setEnabled(false);
		 }else{
			 this.contCheque.setEnabled(false);
		 }
	}

	private void initTblRev() {
		this.tblReceive.getStyleAttributes().setLocked(true);
		this.tblReceive.getColumn("isSelected").getStyleAttributes().setLocked(false);
		if(ChequeTypeEnum.receipt.equals(this.KDchequeType.getSelectedItem())){
			this.tblReceive.getColumn("makeInvoiceAmount").getStyleAttributes().setLocked(true);
		} else {
			this.tblReceive.getColumn("makeInvoiceAmount").getStyleAttributes().setLocked(false);
		}
		this.tblReceive.getColumn("makeInvoiceAmount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		this.tblReceive.getColumn("amount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		this.tblReceive.getColumn("hasTransferredAmount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		this.tblReceive.getColumn("hasRefundmentAmount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		
	}

	private void initChequeNumber() {
		  if(setting.getMarkInvoice() == 32){
				this.f7Cheque.setRequired(true);
				this.txtNumber.setVisible(false);
			}else{
				this.f7Cheque.setVisible(false);
				this.txtNumber.setRequired(true);
			}
		
	}

	private void initF7Customer() throws EASBizException, BOSException {
		UserInfo use = SysContext.getSysContext().getCurrentUserInfo();
		EntityViewInfo view = null;
		if(sellproject!=null){
			view= NewCommerceHelper.getPermitCustomerView(sellproject, use);
		}else {
			 view = NewCommerceHelper.getPermitCustomerView(use);
		}
			this.prmtCustomer.setEntityViewInfo(view);
	    	this.prmtCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECustomerQuery");
		}
	
	/**
	 * 客户带出客户名下的收款明细
	 */
	protected void prmtCustomer_dataChanged(DataChangeEvent e) throws Exception {
		Object customer = this.prmtCustomer.getValue();
		Object[] customers = null;
		if(customer instanceof Object[]){
			customers = (Object[])customer;
			if(!(customers[0] instanceof SHECustomerInfo))return;
		}
		
		SHERevBillEntryCollection sherevColl = new SHERevBillEntryCollection();
		if(customers!=null && customers.length>0) 
			sherevColl = getSHERevBillEntryCollection(customers);
		addReceiveRow(sherevColl);//添加收款明细
	}

	private SHERevBillEntryCollection getSHERevBillEntryCollection(Object[] customer) throws BOSException {
		String custsIdStr = "";
		for(int i = 0 ; i <customer.length ; i ++){
			custsIdStr += ",'"+ ((SHECustomerInfo)customer[i]).getId().toString() +"'";
		}

		SHERevCustEntryCollection cusColl = SHERevCustEntryFactory.getRemoteInstance()
					.getSHERevCustEntryCollection("select head.id where sheCustomer.id in ("+custsIdStr.substring(1)+")");
		
		Set cusIds = new HashSet();
		for(int i = 0 ; i <cusColl.size() ; i ++){
			
			cusIds.add(cusColl.get(i).getHead().getId().toString());
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.revBillType",RevBillTypeEnum.GATHERING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("parent.sellProject.id",sellproject!=null?sellproject.getId().toString():null));
		filter.getFilterItems().add(new FilterItemInfo("parent.id",cusIds,CompareType.INCLUDE));
		view.setFilter(filter);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("invoiceNumber"));
		selector.add(new SelectorItemInfo("moneyDefine.*"));
		selector.add(new SelectorItemInfo("parent.number"));
		selector.add(new SelectorItemInfo("parent.bizDate"));
		selector.add(new SelectorItemInfo("parent.description"));
		
		view.setSelector(selector);
		return SHERevBillEntryFactory.getRemoteInstance().getSHERevBillEntryCollection(view);
		
	}

	/**
	 * 添加收款明细的时候注意处理 发票号 以及 开票金额
	 * @param sherevColl
	 */
	private void addReceiveRow(SHERevBillEntryCollection sherevColl) {
		this.tblReceive.checkParsed();
		this.tblReceive.removeRows();
		
		CRMHelper.sortCollection(sherevColl, "parent.bizDate", false);
		
		for(int i = 0; i < sherevColl.size();i++){
			SHERevBillEntryInfo revInfo = sherevColl.get(i);
			BigDecimal revAmount = revInfo.getRevAmount()!=null?revInfo.getRevAmount():FDCHelper.ZERO;
			BigDecimal hasTransferredAmount = revInfo.getHasTransferAmount()!=null?revInfo.getHasTransferAmount():FDCHelper.ZERO;
			BigDecimal hasRefundmentAmount = revInfo.getHasRefundmentAmount()!=null?revInfo.getHasRefundmentAmount():FDCHelper.ZERO;
			ChequeRevListEntryInfo info = new ChequeRevListEntryInfo();
			info.setRevBillEntry(revInfo);
			IRow row = this.tblReceive.addRow();
			row.setUserObject(info);
			row.getCell("isSelected").setValue(Boolean.FALSE);
			row.getCell("revNumber").setValue(revInfo.getParent().getNumber());
			row.getCell("moneyName").setValue(revInfo.getMoneyDefine());
			row.getCell("amount").setValue(revAmount);
			row.getCell("billDate").setValue(revInfo.getParent().getBizDate());
			row.getCell("des").setValue(revInfo.getParent().getDescription());
			//根据票据类型来处理是那个设置发票号还是收据号
/*			ChequeDetailEntryInfo fuCheQueDetaiInfo = (ChequeDetailEntryInfo)this.f7Cheque.getValue();
			String showNumber = fuCheQueDetaiInfo!=null?fuCheQueDetaiInfo.getNumber():null;*/
			row.getCell("receipt").setValue(revInfo.getReceiptNumber());
			row.getCell("invoiceNum").setValue(revInfo.getInvoiceNumber());
			if(ChequeTypeEnum.receipt.equals(this.KDchequeType.getSelectedItem())){				
				info.setReceipt(revInfo.getReceiptNumber());
			} else {				
				info.setInvoiceNum(revInfo.getInvoiceNumber());
			}
			row.getCell("hasTransferredAmount").setValue(hasTransferredAmount);
			row.getCell("hasRefundmentAmount").setValue(hasRefundmentAmount);
			row.getCell("remainAmount").setValue(revInfo.getRemainAmount());
			row.getCell("hasMakeAmount").setValue(revInfo.getHasMakeInvoiceAmount());
			//如果 RevAmount - asTransferAmount - HasRefundmentAmount = 已开票金额 则这条灰显不能编辑
			
			if(revAmount.subtract(hasTransferredAmount).subtract(hasRefundmentAmount).compareTo(FDCHelper.ZERO)<=0){
				row.getStyleAttributes().setLocked(true);
				row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
			}
		}
		if(ChequeTypeEnum.receipt.equals(this.KDchequeType.getSelectedItem())){//如果是收据不能编辑开票金额
			this.tblReceive.getColumn("makeInvoiceAmount").getStyleAttributes().setLocked(true);
		}
	}
	
	public SelectorItemCollection getSelectors() {
		 SelectorItemCollection sel = super.getSelectors();
		 sel.add("*");
		 sel.add("revListEntry.*");
		 sel.add("revListEntry.revBillEntry.*");
		 sel.add("revListEntry.revBillEntry.moneyDefine.name");
		 sel.add("revListEntry.revBillEntry.parent.bizDate");
		 sel.add("revListEntry.revBillEntry.parent.number");
		 sel.add("revListEntry.revBillEntry.parent.description");
		 sel.add("verifyOrgUnit.*");
		 sel.add("verifier.*");
		 sel.add("customerEntry.*");
		 sel.add("customerEntry.customer.*");
		 sel.add("picker.*");
		 sel.add("cheque.*");
		 sel.add("cheque.currency.isoCode");
		 return sel;
	}
	
	/**
	 * 
	 * 当这个F7改变的时候界面上的值对象也要全部更新下来，填上了的值都要取下来然后在处理
	 */
	protected void f7Cheque_dataChanged(DataChangeEvent e) throws Exception {
		ChequeDetailEntryInfo chequeDetail = (ChequeDetailEntryInfo)this.f7Cheque.getValue();
				
		this.editData = ChequeDetailEntryFactory.getRemoteInstance().getChequeDetailEntryInfo("select *,cheque.*,cheque.currency.isoCode" +
											"	 where id='"+chequeDetail.getId()+"' "); 		
		chequeDetail = editData;
		this.comboStatus.setSelectedItem(this.editData.getStatus());
		this.KDchequeType.setSelectedItem(this.editData.getCheque().getChequeType());
		this.txtBatchNumber.setText(this.editData.getCheque().getChequeBathNumber());
		this.txtNumber.setText(this.editData.getNumber());
	}
	
	protected void KDchequeType_actionPerformed(ActionEvent e) throws Exception {
		if(ChequeTypeEnum.invoice.equals(this.KDchequeType.getSelectedItem())){//发票
//			this.f7Cheque.setEntityViewInfo(NewCommerceHelper.getPermitCrmChequeView(ChequeTypeEnum.invoice));
			this.f7Cheque.getQueryAgent().resetRuntimeEntityView();
			this.f7Cheque.setRefresh(true);
			
			this.tblReceive.getColumn("receipt").getStyleAttributes().setHided(true);
			this.tblReceive.getColumn("invoiceNum").getStyleAttributes().setHided(false);
		}else if(ChequeTypeEnum.receipt.equals(this.KDchequeType.getSelectedItem())){
//			this.f7Cheque.setEntityViewInfo(NewCommerceHelper.getPermitCrmChequeView(ChequeTypeEnum.receipt));
			
			this.tblReceive.getColumn("receipt").getStyleAttributes().setHided(false);
			this.tblReceive.getColumn("invoiceNum").getStyleAttributes().setHided(true);
		}
		
	}
	protected void tblReceive_editStopped(KDTEditEvent e) throws Exception {
		//更新开票金额，以及更新开票金额，是否选择到值对象
		BigDecimal totalAmount = new BigDecimal("0");
		for(int i = 0 ; i <tblReceive.getRowCount();i++){
			IRow row = tblReceive.getRow(i);
			Boolean isSelected = (Boolean)row.getCell("isSelected").getValue();
			ICell makeInvoiceCell = row.getCell("makeInvoiceAmount");
			BigDecimal makeInvoiceAmount = makeInvoiceCell.getValue()!=null?new BigDecimal(makeInvoiceCell.getValue().toString()):FDCHelper.ZERO;
			ChequeRevListEntryInfo info = (ChequeRevListEntryInfo)row.getUserObject();
			info.setIsSelect(isSelected.booleanValue());
			if(!isSelected.booleanValue()){
				info.setChequeAmount(FDCHelper.ZERO);
				makeInvoiceCell.getStyleAttributes().setLocked(true);
				continue;
			} else {
				if(makeInvoiceAmount==null || makeInvoiceAmount.compareTo(new BigDecimal("0"))==0) {
					BigDecimal revAmount = (BigDecimal)row.getCell("amount").getValue();
					makeInvoiceAmount = revAmount;
					makeInvoiceCell.setValue(makeInvoiceAmount);
				}
				info.setChequeAmount(makeInvoiceAmount);
				totalAmount = totalAmount.add(makeInvoiceAmount);
				makeInvoiceCell.getStyleAttributes().setLocked(false);
			}
			
		}
		this.txtAmount.setValue(totalAmount);
		
		
	}
	
	protected void doBeforeSubmit(ActionEvent e) throws Exception {
		
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyinput();
		 verifytblRev();
		 
		 

		 if(this.editData.getCheque()!=null && this.editData.getCheque().getCurrency()!=null) {
			 String capStr = FDCHelper.transCap(this.editData.getCheque().getCurrency(), this.txtAmount.getBigDecimalValue());
			 this.editData.setCapitalization(capStr);
		 }else{
			 CompanyOrgUnitInfo tempCompany = SysContext.getSysContext().getCurrentFIUnit();
			 CompanyOrgUnitInfo company = GlUtils.getCurrentCompany(null,tempCompany.getId().toString(),null,false);
			 company.getBaseCurrency().setIsoCode("RMB");
			 String capStr = FDCHelper.transCap(company.getBaseCurrency(), this.txtAmount.getBigDecimalValue());
			 this.editData.setCapitalization(capStr);
		 }
		//super.actionSubmit_actionPerformed(e);
		 this.storeFields();
		 getBizInterface().submit(this.editData);
		 
		this.setOprtState(OprtState.VIEW);
	}

	private void verifyinput() {
		 if(setting.getMarkInvoice() == 32){
				if(this.f7Cheque.getValue()==null){
					FDCMsgBox.showWarning("票据编码不能为空！");
					this.abort();
				}
			}else{
				if(this.txtNumber.getText()==null){
					FDCMsgBox.showWarning("票据编码不能为空！");
					this.abort();
				}
			}
		 if(this.prmtCustomer.getValue()==null){
			 FDCMsgBox.showWarning("客户不能为空！");
				this.abort();
		 }
		 if(this.txtAmount.getBigDecimalValue()!=null&&this.txtAmount.getBigDecimalValue().compareTo(FDCHelper.ZERO)<=0){
			 FDCMsgBox.showWarning("开票金额不能小于等于零！");
				this.abort();
		 }
		 
		 if(this.editData.getCheque()!= null && this.editData.getCheque().getLimitAmount()!=null){
			 if(this.txtAmount.getBigDecimalValue()!=null && this.txtAmount.getBigDecimalValue().compareTo(this.editData.getCheque().getLimitAmount())>0){
				 FDCMsgBox.showWarning("开票金额不能大于票据限额！");
					this.abort();
			 }
		 }
	}

	private void verifytblRev() {
		if(this.tblReceive.getRowCount()<1){
			 FDCMsgBox.showWarning("收款明细不能为空！");
				this.abort();
		}
		boolean flag = false;
		for(int i = 0 ; i <tblReceive.getRowCount();i++){
			IRow row = tblReceive.getRow(i);
			Boolean isSelected = (Boolean)row.getCell("isSelected").getValue();
			if(isSelected.booleanValue()){
				flag = true ;
				BigDecimal makeInvoiceAmount = row.getCell("makeInvoiceAmount").getValue()!=null?new BigDecimal(row.getCell("makeInvoiceAmount").getValue().toString()):FDCHelper.ZERO;
				BigDecimal remainAmount = row.getCell("remainAmount").getValue()!=null?new BigDecimal(row.getCell("remainAmount").getValue().toString()):FDCHelper.ZERO;
				BigDecimal hasMakeAmount = row.getCell("hasMakeAmount").getValue()!=null?new BigDecimal(row.getCell("hasMakeAmount").getValue().toString()):FDCHelper.ZERO;
				//如果 RevAmount - asTransferAmount - HasRefundmentAmount > 已开票金额 +开票则蹦蹦
				if(makeInvoiceAmount.add(hasMakeAmount).compareTo(remainAmount)>0){
					 FDCMsgBox.showWarning("已开票金额与开票金额的和不能大于剩余金额");
					 this.abort();
				}
			}else{
				continue;
			}
		}
		if(!flag){
			 FDCMsgBox.showWarning("请选择一条收款明细！");
			 this.abort();
		}
	}	
	
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.editData.getId()==null) return;

        BOSQueryDelegate data = new CRMDataProvider(this.editData.getId().toString()
        			,"com.kingdee.eas.fdc.sellhouse.app.ChequeDetailEntryQuery","com.kingdee.eas.fdc.sellhouse.app.ChequeRevListEntryQuery","chequeDetail.id");
        KDNoteHelper appHlp = new KDNoteHelper();
        appHlp.print(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
    }    	

    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.editData.getId()==null) return;
        BOSQueryDelegate data = new CRMDataProvider(this.editData.getId().toString()
    			,"com.kingdee.eas.fdc.sellhouse.app.ChequeDetailEntryQuery","com.kingdee.eas.fdc.sellhouse.app.ChequeRevListEntryQuery","chequeDetail.id");
        KDNoteHelper appHlp = new KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
    }
	
	
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.CRMChequeQuery");
    }
    
	protected String getTDFileName() {
    	return "/bim/fdc/sellhouse/CRMCheque";
	}    
	
}