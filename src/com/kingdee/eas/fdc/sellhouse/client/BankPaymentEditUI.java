/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.BankInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BankPaymentFactory;
import com.kingdee.eas.fdc.sellhouse.BankPaymentInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fdc.tenancy.client.TenancyClientHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BankPaymentEditUI extends AbstractBankPaymentEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(BankPaymentEditUI.class);
    
    public static final String KEY_SELL_PROJECT = "sellProject";
    CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit(); 
//    BigDecimal paymentAmount = (BigDecimal)this.txtPaymentAmout.getBigDecimalValue();
    
    public BankPaymentEditUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();   
        storeBankPayEntry();
    }
    
    protected void loadData() throws Exception {
    	super.loadData();
    	BankPaymentInfo bankPayInfo = (BankPaymentInfo)this.editData;
    	loadBankPayEntry(bankPayInfo);
    	
    	if(this.editData.getPaymentBank()!=null){
    		this.prmtPaymentBank.setEnabled(false);
    	}
    	if(this.editData.getMoneyDefine()!=null){
    		this.prmtMoneyDefine.setEnabled(false);
    	}
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	if(this.oprtState.equals(OprtState.VIEW))
    	{
    		this.btnselectPaymentEntry.setEnabled(false);
        	this.btnDelPaymentEntry.setEnabled(false);
    	}
    	initCotrol();
    	initBankPaymentEntryTable();
    	initpaymentAccountBankFilter();
    	initrevAccountBankFilter();   	   
    }
    
    protected void initCotrol()
    {
    	//开票和回收票据的功能先隐藏
    	this.btnCreateCheque.setVisible(false);
    	this.btnClearCheque.setVisible(false);
    	this.chkMenuItemSubmitAndAddNew.setSelected(false);
    	this.actionSave.setVisible(false);
    	this.actionRemove.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.actionInsertLine.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.menuBiz.setVisible(false);
		this.prmtCreator.setEnabled(false);
		this.prmtAuditor.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		this.pkAuditTime.setEnabled(false);
		this.txtPaymentAmout.setEnabled(false);
		this.prmtProject.setEnabled(false);
		this.prmtPaymentBank.setRequired(true);
		this.prmtMoneyDefine.setRequired(true);
		this.prmtMoneyDefine.setEditable(false);
		initMoneyDefineFilter();
		
		// 收款科目
		KDBizPromptBox gatheringSubject = new KDBizPromptBox();
		CompanyOrgUnitInfo curCompany = SysContext.getSysContext().getCurrentFIUnit();
		
		EntityViewInfo view = this.getAccountEvi(curCompany);
		AccountPromptBox opseelect = new AccountPromptBox(this, curCompany,view.getFilter(), false, true);
		gatheringSubject.setEntityViewInfo(view);
		gatheringSubject.setSelector(opseelect);
		gatheringSubject.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
		gatheringSubject.setEditFormat("$number$");
		gatheringSubject.setCommitFormat("$number$");
		
		this.prmtRevAccount.setDialog(opseelect);
		this.prmtOppAccount.setDialog(opseelect);
		this.txtSettlementNumber.setMaxLength(44);
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
    
    protected void initBankPaymentEntryTable()
    {
    	this.kdtBankPaymentEntry.checkParsed();
    	this.kdtBankPaymentEntry.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		
		this.kdtBankPaymentEntry.getColumn("appAmount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		this.kdtBankPaymentEntry.getColumn("paymentAmount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());

		this.kdtBankPaymentEntry.getColumn("appAmount").getStyleAttributes().setNumberFormat("###,###.00");
		this.kdtBankPaymentEntry.getColumn("appAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtBankPaymentEntry.getColumn("paymentAmount").getStyleAttributes().setNumberFormat("###,###.00");
		this.kdtBankPaymentEntry.getColumn("paymentAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
//		this.kdtBankPaymentEntry.getColumn("moneyDefine").setEditor(txtEditor);
		this.kdtBankPaymentEntry.getColumn("room").setEditor(txtEditor);
		this.kdtBankPaymentEntry.getColumn("cusomter").setEditor(txtEditor);
		this.kdtBankPaymentEntry.getColumn("signManagerName").setEditor(txtEditor);
		
		//发票和收据是什么格式还需要根据是否启用统一票据管理来确定
		this.kdtBankPaymentEntry.getColumn("receiptDisName").setEditor(txtEditor);
		this.kdtBankPaymentEntry.getColumn("invoiceDisName").setEditor(txtEditor);
    }
    
    private void initMoneyDefineFilter()
    {
    	EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.LOANAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.ACCFUNDAMOUNT_VALUE));
		filter.setMaskString("#0 or #1");
		viewInfo.setFilter(filter);
		prmtMoneyDefine.setEntityViewInfo(viewInfo);
    }
    /*
     * 银行账户根据银行来进行过滤
     */
    private void initpaymentAccountBankFilter() throws BOSException, EASBizException {
    	EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(this.prmtPaymentBank.getValue()!=null)
		{
			BankInfo paymentBank = (BankInfo)this.prmtPaymentBank.getValue();
			filter.getFilterItems().add(new FilterItemInfo("bank.id", paymentBank.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("company.id", company.getId().toString()));
			viewInfo.setFilter(filter);
			prmtPaymentAccountBank.setEntityViewInfo(viewInfo);
		}else
		{
			filter.getFilterItems().add(new FilterItemInfo("company.id", company.getId().toString()));
			viewInfo.setFilter(filter);
			prmtPaymentAccountBank.setEntityViewInfo(viewInfo);
		}			
	}
    
    private void initrevAccountBankFilter() throws BOSException, EASBizException {
    	EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
    	if(this.prmtRevBank.getValue()!=null)
		{
			BankInfo revBank = (BankInfo)this.prmtRevBank.getValue();
			filter.getFilterItems().add(new FilterItemInfo("bank.id", revBank.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("company.id", company.getId().toString()));
			viewInfo.setFilter(filter);
			prmtRevAccountBank.setEntityViewInfo(viewInfo);
		}else
		{
			filter.getFilterItems().add(new FilterItemInfo("company.id", company.getId().toString()));
			viewInfo.setFilter(filter);
			prmtRevAccountBank.setEntityViewInfo(viewInfo);
		}
    }
    
    protected void prmtPaymentBank_dataChanged(DataChangeEvent e) throws Exception {
    	super.prmtPaymentBank_dataChanged(e);
    	//如果值改变了则重新过滤
    	if(CommerceHelper.isF7DataChanged(e))
    	{
    		initpaymentAccountBankFilter();
//    		this.prmtPaymentAccountBank.setValue(null);
    	}
    }
    
    protected void prmtPaymentAccountBank_dataChanged(DataChangeEvent e) throws Exception {
    	if(this.prmtPaymentAccountBank.getValue()==null) return;
    	//如果选择了银行账户的值则直接带出银行的值
    	AccountBankInfo payAccBankInfo = (AccountBankInfo)this.prmtPaymentAccountBank.getValue();
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("bank.id");
		sels.add("bank.name");
		sels.add("bank.number");
		sels.add("account.*");
    	payAccBankInfo = (AccountBankInfo) AccountBankFactory.getRemoteInstance().getValue(new ObjectUuidPK(payAccBankInfo.getId()), sels);
    	this.prmtPaymentBank.setValue(payAccBankInfo.getBank());
    	this.prmtOppAccount.setValue(payAccBankInfo.getAccount());
    }
    
    protected void prmtRevBank_dataChanged(DataChangeEvent e) throws Exception {
    	super.prmtRevBank_dataChanged(e);
    	//如果值改变了则重新过滤
    	if(CommerceHelper.isF7DataChanged(e))
    	{
    		initrevAccountBankFilter();
//    		this.prmtRevAccountBank.setValue(null);
    	}
    }
    
    protected void prmtRevAccountBank_dataChanged(DataChangeEvent e) throws Exception {
    	if(this.prmtRevAccountBank.getValue()==null) return;
    	//如果选择了银行账户的值则直接带出银行的值
    	AccountBankInfo revAccBankInfo = (AccountBankInfo)this.prmtRevAccountBank.getValue();
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("bank.id");
		sels.add("bank.name");
		sels.add("bank.number");
		sels.add("account.*");
		revAccBankInfo = (AccountBankInfo) AccountBankFactory.getRemoteInstance().getValue(new ObjectUuidPK(revAccBankInfo.getId()), sels);
    	this.prmtRevBank.setValue(revAccBankInfo.getBank());
    	this.prmtRevAccount.setValue(revAccBankInfo.getAccount());
    }
    
    private void storeBankPayEntry()
    {
    	BankPaymentInfo bankPayInfo = (BankPaymentInfo)this.editData;
    	bankPayInfo.getBankPaymentEntry().clear();
    	for(int i=0;i<kdtBankPaymentEntry.getRowCount();i++)
    	{
    		IRow row = this.kdtBankPaymentEntry.getRow(i);
    		BankPaymentEntryInfo bankEntryInfo = (BankPaymentEntryInfo)row.getUserObject();
//    		MoneyDefineInfo moneyInfo = (MoneyDefineInfo)row.getCell("moneyDefine").getUserObject();
//    		bankEntryInfo.setMoneyDefine(moneyInfo);
    		bankEntryInfo.setRoom((RoomInfo)row.getCell("room").getUserObject());
    		bankEntryInfo.setSignManager((SignManageInfo)row.getCell("signManagerName").getUserObject());
    		bankEntryInfo.setCustomerDisName((String)row.getCell("cusomter").getValue());
    		bankEntryInfo.setAppAmount((BigDecimal)row.getCell("appAmount").getValue());
    		bankEntryInfo.setPaymentAmount((BigDecimal)row.getCell("paymentAmount").getValue());
    		bankEntryInfo.setSignPayList((SignPayListEntryInfo)row.getCell("id").getUserObject());
    		bankPayInfo.getBankPaymentEntry().add(bankEntryInfo);
    	}
    }
    
    private void loadBankPayEntry(BankPaymentInfo bankPayInfo)
    {
    	BankPaymentEntryCollection bankEntryColl = bankPayInfo.getBankPaymentEntry();
    	this.kdtBankPaymentEntry.checkParsed();
		this.kdtBankPaymentEntry.removeRows();
		for(int i=0;i<bankEntryColl.size();i++) {
			BankPaymentEntryInfo bankEntryInfo = bankEntryColl.get(i);
			addPayListEntryRow(bankEntryInfo);
		}
    }
    
    private void addPayListEntryRow(BankPaymentEntryInfo bankEntryInfo)
    {
    	IRow row = this.kdtBankPaymentEntry.addRow();
		row.setUserObject(bankEntryInfo);
		row.getCell("id").setValue(bankEntryInfo.getSignPayList().getId().toString());
		row.getCell("id").setUserObject(bankEntryInfo.getSignPayList());
    	row.getCell("room").setValue(bankEntryInfo.getRoom().getName());
    	row.getCell("room").setUserObject(bankEntryInfo.getRoom());
//    	row.getCell("moneyDefine").setValue(bankEntryInfo.getMoneyDefine().getName());
//    	row.getCell("moneyDefine").setUserObject(bankEntryInfo.getMoneyDefine());
    	if(bankEntryInfo.getCustomerDisName()!=null){
    		row.getCell("cusomter").setValue(bankEntryInfo.getCustomerDisName());
    	}
    	row.getCell("signManagerName").setValue(bankEntryInfo.getSignManager().getNumber());
    	row.getCell("signManagerName").setUserObject(bankEntryInfo.getSignManager());
    	row.getCell("appAmount").setValue(bankEntryInfo.getAppAmount());
    	row.getCell("paymentAmount").setValue(bankEntryInfo.getPaymentAmount());
    }

    /*
     * 选择从已签约的销售单据中找出贷款明细
     */
    protected void btnselectPaymentEntry_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	if(this.prmtMoneyDefine.getValue()==null)
    	{
    		MsgBox.showInfo("请先选择放款款项!");
    		this.abort();
    	}
        super.btnselectPaymentEntry_actionPerformed(e);   
        BigDecimal paymentAmount = this.txtPaymentAmout.getBigDecimalValue();
        if(paymentAmount==null)paymentAmount = FDCHelper.ZERO;
        UIContext uiContext = new UIContext(ui);
        SellProjectInfo sellProject = this.editData.getProject();
		uiContext.put(KEY_SELL_PROJECT, sellProject);
		KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.BankPaymentSignPayListQuery")));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();		
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id",((MoneyDefineInfo)this.prmtMoneyDefine.getValue()).getId().toString()));
//		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType", MoneyTypeEnum.ACCFUNDAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("head.bizState", TransactionStateEnum.SIGNAPPLE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("head.bizState", TransactionStateEnum.SIGNAUDIT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("remnantAmount", FDCHelper.ZERO,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("id", "select fsignPayListid from t_she_bankPaymentEntry",CompareType.NOTINNER));	//已存在银行放款单的不显示
		filter.setMaskString("#0 and #1 and (#2 or #3) and #4 and #5");
		view.setFilter(filter);
		dlg.setEntityViewInfo(view);
		//设置多选
		dlg.setEnabledMultiSelection(true);
        dlg.show();
        
        Object[] object = (Object[])dlg.getData();
        Set idSet = new HashSet();
        List list = new ArrayList();
        if(object!=null)
        {
        	for(int j=0;j<kdtBankPaymentEntry.getRowCount();j++)
        	{
        		IRow row2 = kdtBankPaymentEntry.getRow(j);
        		String id = (String)row2.getCell("id").getValue();
        		list.add(id);
        	}
        	
        	for(int i=0;i<object.length;i++)
            {
            	SignPayListEntryInfo signPayInfo = (SignPayListEntryInfo)object[i];
/*            	if(vifySign(signPayInfo))	//已放在上面过滤条件中
            	{
            		MsgBox.showInfo("该明细已经存在银行放款单！");
            		continue;
            	}*/
            	if(TenancyClientHelper.isInclude(signPayInfo.getId().toString(),list))
             	{
            		MsgBox.showInfo("该明细已经存在不要重复添加！");
            		continue;
             	}else
        		{
        			idSet.add(signPayInfo.getId().toString()); 
        		}       	     		
            }           

         	SignPayListEntryCollection signPayColl =  getPayListCollection(idSet);
         	if(signPayColl != null && signPayColl.size()>0)
         	{
         		for(int i=0;i<signPayColl.size();i++)
             	{
             		SignPayListEntryInfo signPayListInfo = signPayColl.get(i);
             		BankPaymentEntryInfo bankEntryInfo = new BankPaymentEntryInfo();
             		IRow row = kdtBankPaymentEntry.addRow(i);
             		row.setUserObject(bankEntryInfo);
             		paymentAmount = setTableBankEntry(signPayListInfo,row,paymentAmount); 
             	}
         	}       	
        }     
        this.txtPaymentAmout.setValue(paymentAmount);
    }
    
    //校验签约单明细是否已经存在对应的银行放款单
    private boolean vifySign(SignPayListEntryInfo signPayInfo) throws BOSException
    {
    	EntityViewInfo viewInfo = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
  		filter.getFilterItems().add(new FilterItemInfo("signPayList.id",signPayInfo.getId().toString(),CompareType.EQUALS));
  		viewInfo.setFilter(filter);
  		BankPaymentEntryCollection bankEntrycoll = BankPaymentEntryFactory.getRemoteInstance().getBankPaymentEntryCollection(viewInfo);
  		if(bankEntrycoll.size()>0)
  		{
  			return true;
  		}else
  		{
  			return false;
  		}    	
    }
    
    protected BigDecimal setTableBankEntry(SignPayListEntryInfo signPayListInfo,IRow row,BigDecimal paymentAmount)
    {
    	row.getCell("id").setValue(signPayListInfo.getId().toString());
    	row.getCell("id").setUserObject(signPayListInfo);
    	row.getCell("room").setValue(signPayListInfo.getHead().getRoom().getName());
    	row.getCell("room").setUserObject(signPayListInfo.getHead().getRoom());
//    	row.getCell("moneyDefine").setValue(signPayListInfo.getMoneyDefine().getName());
//    	row.getCell("moneyDefine").setUserObject(signPayListInfo.getMoneyDefine());
    	row.getCell("cusomter").setValue(signPayListInfo.getHead().getCustomerNames());
    	row.getCell("signManagerName").setValue(signPayListInfo.getHead().getNumber());
    	row.getCell("signManagerName").setUserObject(signPayListInfo.getHead());
    	BigDecimal appAmount = signPayListInfo.getAppAmount();
    	if(appAmount==null)appAmount = FDCHelper.ZERO;
    	row.getCell("appAmount").setValue(signPayListInfo.getAppAmount());
    	BigDecimal revAmount = signPayListInfo.getActRevAmount();
    	if(revAmount==null)revAmount = FDCHelper.ZERO;
    	row.getCell("paymentAmount").setValue(appAmount.subtract(revAmount));
    	paymentAmount=paymentAmount.add(appAmount.subtract(revAmount));
    	return paymentAmount;
    }
    
    protected SignPayListEntryCollection getPayListCollection(Set idSet) throws BOSException
    {
    	SignPayListEntryCollection signPayColl = null;
    	 if(idSet.size()>0)
         {
         	EntityViewInfo viewInfo = new EntityViewInfo();
      		FilterInfo filter = new FilterInfo();
      		SelectorItemCollection sellColl = new SelectorItemCollection();
      		 sellColl.add("id");
      		 sellColl.add("appAmount");
      		 sellColl.add("actRevAmount");
             sellColl.add("moneyDefine.name");                   
             sellColl.add("moneyDefine.number");
             sellColl.add("moneyDefine.moneyType"); 
             sellColl.add("head.room.name");
             sellColl.add("head.room.number");
             sellColl.add("head.number");
             sellColl.add("head.number");
             sellColl.add("head.customerNames");
             sellColl.add("head.signCustomerEntry.customer.*");
             sellColl.add("head.loanBank.*");
             sellColl.add("head.acfBank.*");
             
      		viewInfo.setSelector(sellColl);
      		filter.getFilterItems().add(new FilterItemInfo("id",idSet,CompareType.INCLUDE));
      		viewInfo.setFilter(filter);
      		signPayColl =  SignPayListEntryFactory.getRemoteInstance().getSignPayListEntryCollection(viewInfo);
         }
    	 return signPayColl;
    }

    protected void btnDelPaymentEntry_actionPerformed(ActionEvent e) throws Exception {
    	super.btnDelPaymentEntry_actionPerformed(e);
    	BigDecimal paymentAmount = this.txtPaymentAmout.getBigDecimalValue();
    	if(paymentAmount==null)paymentAmount = FDCHelper.ZERO;
    	int activeRowIndex = this.kdtBankPaymentEntry.getSelectManager()
		.getActiveRowIndex();
		if (activeRowIndex == -1) {
//			activeRowIndex = this.kdtBankPaymentEntry.getRowCount();
			MsgBox.showInfo("请选择要删除的行!");
			this.abort();
		}
		IRow row = kdtBankPaymentEntry.getRow(activeRowIndex);
		BigDecimal payAmount = (BigDecimal)row.getCell("paymentAmount").getValue();
		if(payAmount==null)payAmount = FDCHelper.ZERO;
		this.kdtBankPaymentEntry.removeRow(activeRowIndex);
		paymentAmount = paymentAmount.subtract(payAmount);
		this.txtPaymentAmout.setValue(paymentAmount);
    }
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	kdtBankPaymentEntry.removeRows();
    	super.actionAddNew_actionPerformed(e);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	BankPaymentInfo bankInfo = this.editData;
    	if(FDCBillStateEnum.AUDITTED.equals(bankInfo.getState()))
    	{
    		MsgBox.showInfo("已审批的单据不能修改!");
			return;
    	}
    	super.actionEdit_actionPerformed(e);
    	this.prmtProject.setEnabled(false);
    	this.prmtCreator.setEnabled(false);
    	this.txtPaymentAmout.setEnabled(false);
    	this.prmtAuditor.setEnabled(false);
    	this.pkAuditTime.setEnabled(false);
    	this.pkCreateTime.setEnabled(false);
    	this.actionAttachment.setVisible(false);
    	this.btnselectPaymentEntry.setEnabled(true);
    	this.btnDelPaymentEntry.setEnabled(true);
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSubmit_actionPerformed(e);
    	this.actionAttachment.setVisible(false);
    	this.actionAudit.setVisible(true);
    	this.actionAudit.setEnabled(true);
    	this.oprtState = OprtState.VIEW;
    	this.txtPaymentAmout.setValue(this.editData.getPaymentAmout());
    }
    
   protected void verifyInput(ActionEvent e) throws Exception {
	verifyEntry();
	super.verifyInput(e);
   }
   
   protected void verifyEntry()
   {
	   if(this.kdtBankPaymentEntry.getRowCount()==0)
	   {
		   MsgBox.showInfo("贷款明细不能为空!");
		   this.abort();
	   }
   }
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionAudit_actionPerformed(e);
    	this.actionAudit.setVisible(false);
    	this.actionUnAudit.setVisible(true);
    	this.actionUnAudit.setEnabled(true);
    }
    
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionUnAudit_actionPerformed(e);
    	this.actionAudit.setVisible(true);
    	this.actionAudit.setEnabled(true);
    	this.actionUnAudit.setVisible(false);
    }
    
    public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectorItemCollection = super.getSelectors();
		selectorItemCollection.add("id");
		selectorItemCollection.add("customerDisName");
		selectorItemCollection.add("appAmount");
		selectorItemCollection.add("paymentAmount");		
		selectorItemCollection.add("state");
		
		selectorItemCollection.add("project.name");
		selectorItemCollection.add("project.number");
		selectorItemCollection.add("paymentBank.name");
		selectorItemCollection.add("paymentBank.number");
		selectorItemCollection.add("paymentAccountBank.name");
		selectorItemCollection.add("paymentAccountBank.number");
		selectorItemCollection.add("settlementType.name");
		selectorItemCollection.add("settlementType.number");
		selectorItemCollection.add("revBank.name");
		selectorItemCollection.add("revBank.number");
		selectorItemCollection.add("revAccountBank.name");
		selectorItemCollection.add("revAccountBank.number");
		selectorItemCollection.add("revAccount.name");
		selectorItemCollection.add("revAccount.number");
		selectorItemCollection.add("oppAccount.name");
		selectorItemCollection.add("oppAccount.number");
		selectorItemCollection.add("bankPaymentEntry.id");
		selectorItemCollection.add("bankPaymentEntry.customerDisName");
//		selectorItemCollection.add("bankPaymentEntry.moneyDefine.id");
//		selectorItemCollection.add("bankPaymentEntry.moneyDefine.name");
//		selectorItemCollection.add("bankPaymentEntry.moneyDefine.number");
		selectorItemCollection.add("bankPaymentEntry.room.id");
		selectorItemCollection.add("bankPaymentEntry.room.name");
		selectorItemCollection.add("bankPaymentEntry.room.number");
		selectorItemCollection.add("bankPaymentEntry.signManager.id");
		selectorItemCollection.add("bankPaymentEntry.signManager.name");
		selectorItemCollection.add("bankPaymentEntry.signManager.number");
		selectorItemCollection.add("bankPaymentEntry.signManager.id");
		selectorItemCollection.add("bankPaymentEntry.signManager.name");
		selectorItemCollection.add("bankPaymentEntry.signManager.number");
		
		selectorItemCollection.add("bankPaymentEntry.customerDisName");
		selectorItemCollection.add("bankPaymentEntry.appAmount");
		selectorItemCollection.add("bankPaymentEntry.paymentAmount");
		selectorItemCollection.add("bankPaymentEntry.receiptDisName");
		selectorItemCollection.add("bankPaymentEntry.invoiceDisName");
		selectorItemCollection.add("bankPaymentEntry.signPayList.id");
		selectorItemCollection.add("CU.*");
		return selectorItemCollection;
    }

	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return BankPaymentFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		BankPaymentInfo bankPayment = new BankPaymentInfo();
		Timestamp curTime = null;
		try {
			curTime = FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e1) {
			e1.printStackTrace();
			curTime = new Timestamp(System.currentTimeMillis());
		}
		
		bankPayment.setCreateTime(curTime);
		bankPayment.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		bankPayment.setOrgUnit(orgUnitInfo);
		
		SellProjectInfo sellProject = (SellProjectInfo)this.getUIContext().get(KEY_SELL_PROJECT);
		bankPayment.setProject(sellProject);
		
		//默认收款日期为当前服务器日期
		bankPayment.setPaymentDate(curTime);
		
		if(this.getUIContext().get("signEntrySet")!=null)
    	{
			BigDecimal paymentAmount = FDCHelper.ZERO;
			Set entrySet = (Set)this.getUIContext().get("signEntrySet");
			SignPayListEntryCollection signPayColl;
			try {
				signPayColl = getPayListCollection(entrySet);
				for(int i=0;i<signPayColl.size();i++)
				{
					SignPayListEntryInfo signPayEntryInfo = signPayColl.get(i);
					
					bankPayment.setMoneyDefine(signPayEntryInfo.getMoneyDefine());
					if(signPayEntryInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){
						bankPayment.setPaymentBank(signPayEntryInfo.getHead().getLoanBank());
					}else{
						bankPayment.setPaymentBank(signPayEntryInfo.getHead().getAcfBank());
					}
					
					BankPaymentEntryInfo bankEntryInfo = new BankPaymentEntryInfo();
					bankEntryInfo.setSignPayList(signPayEntryInfo);
					bankEntryInfo.setRoom(signPayEntryInfo.getHead().getRoom());
					
					/**
					 * 添加客户的名称
					 * add by renliang 
					 */
					if(this.getUIContext().get("signEntrySet")!=null){
						Map customer= (Map)this.getUIContext().get("customerMap");
							if(customer!= null && customer.get(signPayEntryInfo.getId().toString())!=null){
								bankEntryInfo.setCustomerDisName(customer.get(signPayEntryInfo.getId().toString()).toString());
							}
					}
					
					bankEntryInfo.setSignManager(signPayEntryInfo.getHead());
					BigDecimal appAmount = signPayEntryInfo.getAppAmount();
			    	if(appAmount==null)appAmount = FDCHelper.ZERO;
			    	/**
			    	 * 添加appamount数值
			    	 */
			    	bankEntryInfo.setAppAmount(appAmount);
			    	BigDecimal revAmount = signPayEntryInfo.getActRevAmount();
			    	if(revAmount==null)revAmount = FDCHelper.ZERO;
					bankEntryInfo.setPaymentAmount(appAmount.subtract(revAmount));				
					paymentAmount=paymentAmount.add(appAmount.subtract(revAmount));
					bankPayment.getBankPaymentEntry().add(bankEntryInfo);
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
			bankPayment.setPaymentAmout(paymentAmount);  	
    	}
		bankPayment.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return bankPayment;
	}
}