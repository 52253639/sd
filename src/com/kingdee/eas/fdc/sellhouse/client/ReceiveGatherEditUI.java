/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
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
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMDataProvider;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BankPaymentInfo;
import com.kingdee.eas.fdc.sellhouse.GatherTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ReceiveGatherEditUI extends AbstractReceiveGatherEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ReceiveGatherEditUI.class);

    public static final String KEY_SELL_PROJECT = "sellProject";
    
    public ReceiveGatherEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	initCotrol(); 	
    	this.kdtEntry.getColumn("oppAccount").setRequired(true);
    }
    
    public void loadFields() {
    	this.kdtEntry.checkParsed();
    	super.loadFields();
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void initCotrol()
    {
    	this.actionSave.setVisible(false);
    	this.chkMenuItemSubmitAndAddNew.setSelected(false);
    	this.actionRemove.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	this.actionInsertLine.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		
		this.menuBiz.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionPre.setVisible(false);
		this.prmtCreator.setEnabled(false);
		this.prmtAuditor.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		this.pkAuditTime.setEnabled(false);
		this.prmtProject.setEnabled(false);
		this.comboGatherType.setEnabled(false);
		this.comboRevBillType.setEnabled(false);
		this.prmtOppAccount.setEditable(false);
		this.prmtRevAccount.setEditable(false);
		this.contSumAmount.setEnabled(false);
		this.txtSettlementNumber.setMaxLength(44);
		
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
		
		EntityViewInfo entityViewInfo = new EntityViewInfo();
        FilterInfo filterInfo = new FilterInfo();
        entityViewInfo.setFilter(filterInfo);
        filterInfo.getFilterItems().add(new FilterItemInfo("company.id",SysContext.getSysContext().getCurrentFIUnit().getId()));
        this.prmtAccountBank.setEntityViewInfo(entityViewInfo);
	        
		this.kdtEntry.checkParsed();
		ICellEditor f7Editor = new KDTDefaultCellEditor(gatheringSubject);
		this.kdtEntry.getColumn("oppAccount").setEditor(f7Editor);
		
		this.kdtEntry.getColumn("revAmount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		this.kdtEntry.getColumn("settlementType").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery", null));
		this.kdtEntry.getColumn("settlementType").getStyleAttributes().setLocked(false);
		
		this.kdtEntry.getColumn("settlementNumber").getStyleAttributes().setLocked(false);
		this.kdtEntry.getColumn("cusAccountBankNumber").getStyleAttributes().setLocked(false);
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
    
    protected IObjectValue createNewData() {
		ReceiveGatherInfo recGather = new ReceiveGatherInfo();
		kdtEntry.checkParsed();
		Timestamp curTime = null;
		try {
			curTime = FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e1) {
			e1.printStackTrace();
			curTime = new Timestamp(System.currentTimeMillis());
		}
		
		recGather.setCreateTime(curTime);
		recGather.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		recGather.setOrgUnit(orgUnitInfo);
		recGather.setBizDate(curTime);
		
		SellProjectInfo sellProject = (SellProjectInfo)this.getUIContext().get(KEY_SELL_PROJECT);
		recGather.setProject(sellProject);
		
		GatherTypeEnum gatherType = (GatherTypeEnum)this.getUIContext().get("gatherType");
		recGather.setGatherType(gatherType);
		RevBillTypeEnum revBillType = (RevBillTypeEnum)this.getUIContext().get("revBillType");
		recGather.setRevBillType(revBillType);
		
		Set revEntryIDSet = (Set)this.getUIContext().get("revEntryIDSet");
		
		BigDecimal sumAmount = FDCHelper.ZERO;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", revEntryIDSet,CompareType.INCLUDE));
		view.setFilter(filter);
		
		view.getSelector().add("parent.accountBank.*");
		view.getSelector().add("parent.revAccount.*");
		view.getSelector().add("parent.bank.*");
		view.getSelector().add("parent.settlementType.*");
		view.getSelector().add("parent.settlementNumber");
		
		view.getSelector().add("parent.room.id");
		view.getSelector().add("parent.room.name");
		view.getSelector().add("parent.room.number");
		view.getSelector().add("parent.customerNames");
		view.getSelector().add("moneyDefine.name");
		view.getSelector().add("moneyDefine.number");
		view.getSelector().add("moneyDefine.id");
		view.getSelector().add("revAmount");
		view.getSelector().add("customerBankNumber");
		view.getSelector().add("invoiceNumber");
		view.getSelector().add("receiptNumber");
		
		view.getSelector().add("revEntry.*");
		
		Set accountBank=new HashSet();
		Set revAccount=new HashSet();
		Set bank=new HashSet();
		Set settlementType=new HashSet();
		Set settlementNumber=new HashSet();
		
		try {
			SHERevBillEntryCollection sheRevEntryColl = SHERevBillEntryFactory.getRemoteInstance().getSHERevBillEntryCollection(view);
			for(int i=0;i<sheRevEntryColl.size();i++)
			{
				SHERevBillEntryInfo sheEntryInfo = sheRevEntryColl.get(i);	
				ReceiveGatherEntryInfo revEntryInfo = new ReceiveGatherEntryInfo();
				revEntryInfo.setMoneyDefine(sheEntryInfo.getMoneyDefine());
				revEntryInfo.setRoom(sheEntryInfo.getParent().getRoom());
				revEntryInfo.setCustomerDisName(sheEntryInfo.getParent().getCustomerNames());
				revEntryInfo.setSettlementType(sheEntryInfo.getSettlementType());
				revEntryInfo.setSettlementNumber(sheEntryInfo.getSettlementNumber());
				revEntryInfo.setRevAmount(sheEntryInfo.getRevAmount());
				revEntryInfo.setSheRevBill(sheEntryInfo);
				BigDecimal revAmount = sheEntryInfo.getRevAmount();
				if(revAmount==null)revAmount = FDCHelper.ZERO;
				sumAmount = sumAmount.add(revAmount);
				revEntryInfo.setCusAccountBankNumber(sheEntryInfo.getCustomerBankNumber());
				revEntryInfo.setInvoiceNumber(sheEntryInfo.getInvoiceNumber());
				revEntryInfo.setReceiptNumber(sheEntryInfo.getReceiptNumber());
				
				String remark="";
				if(sheEntryInfo.getParent().getRoom()!=null){
					remark=remark+sheEntryInfo.getParent().getRoom().getName()+";";
				}
				if(sheEntryInfo.getMoneyDefine()!=null){
					remark=remark+"收"+sheEntryInfo.getMoneyDefine().getName();
				}
				if(sheEntryInfo.getParent().getCustomerNames()!=null){
					remark=remark+sheEntryInfo.getParent().getCustomerNames();
				}
				if(sheEntryInfo.getRevAmount()!=null){
					remark=remark+sheEntryInfo.getRevAmount()+",";
				}
				if(sheEntryInfo.getCustomerBankNumber()!=null){
					remark=remark+"客户银行账号"+sheEntryInfo.getCustomerBankNumber();
				}
				if(sheEntryInfo.getInvoiceNumber()!=null){
					remark=remark+"发票号"+sheEntryInfo.getInvoiceNumber();
				}
				if(sheEntryInfo.getReceiptNumber()!=null){
					remark=remark+"收据号 "+sheEntryInfo.getReceiptNumber();
				}
				if(sheEntryInfo.getRevEntry().size()>0){
					if(revBillType.equals(RevBillTypeEnum.gathering)){
						remark=remark+" 另: 冲抵 ";
					}else{
						remark=remark+" 另: 退款 ";
					}
				}
				for(int j=0;j<sheEntryInfo.getRevEntry().size();j++){
					SHERevBillEntryInfo twoEntry=SHERevBillEntryFactory.getRemoteInstance().getSHERevBillEntryInfo("select moneyDefine.name,invoiceNumber,receiptNumber,revAmount from where id='"+sheEntryInfo.getRevEntry().get(j).getSheRevBillEntryId()+"'");
					if(twoEntry.getMoneyDefine()!=null){
						remark=remark+twoEntry.getMoneyDefine().getName();
					}
					if(twoEntry.getRevAmount()!=null){
						remark=remark+twoEntry.getRevAmount()+",";
					}
					if(twoEntry.getCustomerBankNumber()!=null){
						remark=remark+"客户银行账号 "+twoEntry.getCustomerBankNumber();
					}
					if(twoEntry.getInvoiceNumber()!=null){
						remark=remark+"发票号"+twoEntry.getInvoiceNumber();
					}
					if(twoEntry.getReceiptNumber()!=null){
						remark=remark+"收据号"+twoEntry.getReceiptNumber();
					}
				}
				revEntryInfo.setRemark(remark);
				recGather.getEntry().add(revEntryInfo);
				
				if(sheEntryInfo.getParent().getAccountBank()!=null)
					accountBank.add(sheEntryInfo.getParent().getAccountBank().getId());
				if(sheEntryInfo.getParent().getRevAccount()!=null)
					revAccount.add(sheEntryInfo.getParent().getRevAccount().getId());
				if(sheEntryInfo.getParent().getBank()!=null)
					bank.add(sheEntryInfo.getParent().getBank().getId());
				if(sheEntryInfo.getParent().getSettlementType()!=null)
					settlementType.add(sheEntryInfo.getParent().getSettlementType().getId());
				if(sheEntryInfo.getParent().getSettlementNumber()!=null)
					settlementNumber.add(sheEntryInfo.getParent().getSettlementNumber());
			}
			if(accountBank.size()==1){
				recGather.setAccountBank(sheRevEntryColl.get(0).getParent().getAccountBank());
			}
			if(accountBank.size()==1){
				recGather.setRevAccount(sheRevEntryColl.get(0).getParent().getRevAccount());
			}
			if(accountBank.size()==1){
				recGather.setBank(sheRevEntryColl.get(0).getParent().getBank());
			}
			if(accountBank.size()==1){
				recGather.setSettlementType(sheRevEntryColl.get(0).getParent().getSettlementType());
			}
			if(accountBank.size()==1){
				recGather.setSettlementNumber(sheRevEntryColl.get(0).getParent().getSettlementNumber());
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		
		recGather.setSumAmount(sumAmount);
		try {
		CompanyOrgUnitInfo tempCompany = SysContext.getSysContext().getCurrentFIUnit();		
		CompanyOrgUnitInfo company = GlUtils.getCurrentCompany(null,tempCompany.getId().toString(),null,false);
		recGather.setCompany(company);
		recGather.setCurrency(company.getBaseCurrency());
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		recGather.setExchangeRate(FDCHelper.ONE);
		//默认收款日期为当前服务器日期
//		bankPayment.setPaymentDate(curTime);
		
		return recGather;
	}
    
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection selectorItemCollection = super.getSelectors();
		selectorItemCollection.add("id");
		selectorItemCollection.add("state");
		selectorItemCollection.add("revBillType");
		selectorItemCollection.add("gatherType");
		selectorItemCollection.add("sumAmount");
		selectorItemCollection.add("settlementNumber");
		selectorItemCollection.add("project.id");
		selectorItemCollection.add("project.name");
		selectorItemCollection.add("project.number");
		selectorItemCollection.add("accountBank.id");
		selectorItemCollection.add("accountBank.name");
		selectorItemCollection.add("accountBank.number");
		selectorItemCollection.add("settlementType.id");
		selectorItemCollection.add("settlementType.name");
		selectorItemCollection.add("settlementType.number");
		selectorItemCollection.add("revAccount.id");
		selectorItemCollection.add("revAccount.name");
		selectorItemCollection.add("revAccount.number");
		selectorItemCollection.add("oppAccount.id");
		selectorItemCollection.add("oppAccount.name");
		selectorItemCollection.add("oppAccount.number");
		
		selectorItemCollection.add("entry.id");
		selectorItemCollection.add("entry.customerDisName");
		selectorItemCollection.add("entry.settlementNumber");
		selectorItemCollection.add("entry.revAmount");
		selectorItemCollection.add("entry.moneyDefine.id");
		selectorItemCollection.add("entry.moneyDefine.name");
		selectorItemCollection.add("entry.moneyDefine.number");
		selectorItemCollection.add("entry.room.id");
		selectorItemCollection.add("entry.room.name");
		selectorItemCollection.add("entry.room.number");
		selectorItemCollection.add("entry.settlementType.id");
		selectorItemCollection.add("entry.settlementType.name");
		selectorItemCollection.add("entry.settlementType.number");
		selectorItemCollection.add("entry.cusAccountBank.id");
		selectorItemCollection.add("entry.cusAccountBank.name");
		selectorItemCollection.add("entry.cusAccountBank.number");
		selectorItemCollection.add("entry.sheRevBill.id");
		
    	return selectorItemCollection;
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	ReceiveGatherInfo recGatherInfo = this.editData;
    	if(FDCBillStateEnum.AUDITTED.equals(recGatherInfo.getState()))
    	{
    		MsgBox.showInfo("已审批的单据不能修改!");
			return;
    	}
    	super.actionEdit_actionPerformed(e);
    	this.prmtProject.setEnabled(false);
    	this.prmtCreator.setEnabled(false);
    	this.txtSumAmount.setEnabled(false);
    	this.prmtAuditor.setEnabled(false);
    	this.pkAuditTime.setEnabled(false);
    	this.pkCreateTime.setEnabled(false);
    	this.actionAttachment.setVisible(false);
    	this.comboGatherType.setEnabled(false);
    	this.comboRevBillType.setEnabled(false);
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row=this.kdtEntry.getRow(i);
			if(row.getCell("oppAccount").getValue()==null){
				FDCMsgBox.showWarning(this,"对方科目不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("oppAccount"));
				SysUtil.abort();
			}
    	}
    	super.actionSubmit_actionPerformed(e);
    	this.actionAttachment.setVisible(false);
    	this.actionAudit.setVisible(true);
    	this.actionAudit.setEnabled(true);
    	this.oprtState = OprtState.VIEW;
    }
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionAudit_actionPerformed(e);
    	this.actionAudit.setVisible(false);
    	this.actionUnAudit.setVisible(true);
    	this.actionUnAudit.setEnabled(true);
    	this.actionAttachment.setVisible(false);
    }
    
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionUnAudit_actionPerformed(e);
    	this.actionAudit.setVisible(true);
    	this.actionAudit.setEnabled(true);
    	this.actionUnAudit.setVisible(false);
    	this.actionAttachment.setVisible(false);
    }

	protected void attachListeners() {		
	}

	protected void detachListeners() {		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ReceiveGatherFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.editData.getId()==null) return;

        BOSQueryDelegate data = new CRMDataProvider(this.editData.getId().toString()
        			,"com.kingdee.eas.fdc.sellhouse.app.ReceiveGatherQuery","com.kingdee.eas.fdc.sellhouse.app.ReceiveGatherEntryQuery","receiveGather.id");
        KDNoteHelper appHlp = new KDNoteHelper();
        appHlp.print(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
    }    	

    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.editData.getId()==null) return;
        BOSQueryDelegate data = new CRMDataProvider(this.editData.getId().toString()
    			,"com.kingdee.eas.fdc.sellhouse.app.ReceiveGatherQuery","com.kingdee.eas.fdc.sellhouse.app.ReceiveGatherEntryQuery","receiveGather.id");
        KDNoteHelper appHlp = new KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
    }
	
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.ReceiveGatherQuery");
	}
    
	protected String getTDFileName() {
    	return "/bim/fdc/sellhouse/revgather";
	}

	protected void prmtAccountBank_dataChanged(DataChangeEvent e) throws Exception {
		if(this.prmtAccountBank.getValue()==null) return;
    	//如果选择了银行账户的值则直接带出银行的值
    	AccountBankInfo payAccBankInfo = (AccountBankInfo)this.prmtAccountBank.getValue();
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("bank.id");
		sels.add("bank.name");
		sels.add("bank.number");
		sels.add("account.*");
    	payAccBankInfo = (AccountBankInfo) AccountBankFactory.getRemoteInstance().getValue(new ObjectUuidPK(payAccBankInfo.getId()), sels);
    	this.prmtBank.setValue(payAccBankInfo.getBank());
    	this.prmtRevAccount.setValue(payAccBankInfo.getAccount());
	}

	protected void prmtBank_dataChanged(DataChangeEvent e) throws Exception {
		if(CommerceHelper.isF7DataChanged(e))
    	{
    		initpaymentAccountBankFilter();
    	}
	}
	private void initpaymentAccountBankFilter() throws BOSException, EASBizException {
    	EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(this.prmtBank.getValue()!=null)
		{
			BankInfo paymentBank = (BankInfo)this.prmtBank.getValue();
			filter.getFilterItems().add(new FilterItemInfo("bank.id", paymentBank.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("company.id", company.getId().toString()));
			viewInfo.setFilter(filter);
			prmtAccountBank.setEntityViewInfo(viewInfo);
		}else
		{
			filter.getFilterItems().add(new FilterItemInfo("company.id", company.getId().toString()));
			viewInfo.setFilter(filter);
			prmtAccountBank.setEntityViewInfo(viewInfo);
		}			
	}
	
}