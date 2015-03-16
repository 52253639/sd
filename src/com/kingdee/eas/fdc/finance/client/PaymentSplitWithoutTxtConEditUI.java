/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.dap.DAPTransImpl;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICostAccountWithAccount;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.IPaymentSplitEntry;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.client.IDAPTrans;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class PaymentSplitWithoutTxtConEditUI extends
		AbstractPaymentSplitWithoutTxtConEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PaymentSplitWithoutTxtConEditUI.class);

	/**
	 * output class constructor
	 */
	public PaymentSplitWithoutTxtConEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();

		setSplitState();
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		// PaymentSplitInfo info =
		// (PaymentSplitInfo)getBizInterface().getValue(new
		// ObjectUuidPK(editData.getId()));
		// if(info.isFivouchered()){
		// FilterInfo filter=new FilterInfo();
		// filter.getFilterItems().add(new
		// FilterItemInfo("parent.id",editData.getId().toString()));
		// filter.getFilterItems().add(new
		// FilterItemInfo("isLeaf",Boolean.TRUE));
		// filter.getFilterItems().add(new FilterItemInfo("accountView",null));
		// if(PaymentSplitEntryFactory.getRemoteInstance().exists(filter)){
		// getAccFromCost();
		// }
		// IDAPTrans dapTrans = new DAPTransImpl(this);
		// CoreBillBaseCollection sourceBillCollection = new
		// CoreBillBaseCollection();
		// info = (PaymentSplitInfo)getBizInterface().getValue(new
		// ObjectUuidPK(editData.getId()));
		// info.setFivouchered(false);
		// sourceBillCollection.add(info);
		// dapTrans.trans(sourceBillCollection);
		// }
	}

	protected void getAccFromCost() throws Exception {
		ICostAccountWithAccount iCostAccountWithAccount = CostAccountWithAccountFactory
				.getRemoteInstance();
		IPaymentSplitEntry iPaymentSplitEntry = PaymentSplitEntryFactory
				.getRemoteInstance();
		PaymentSplitEntryCollection coll;
		CostAccountWithAccountCollection entryColl = null;
		CostAccountWithAccountInfo entryInfo = new CostAccountWithAccountInfo();
		ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("parent.id", editData.getId()));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("seq"));
		view.getSelector().add(new SelectorItemInfo("costAccount.*"));
		view.getSelector().add(new SelectorItemInfo("accountView.*"));
		coll = iPaymentSplitEntry.getPaymentSplitEntryCollection(view);
		int entrySize = coll.size();
		for (int j = 0; j < entrySize; j++) {
			PaymentSplitEntryInfo info = coll.get(j);
			CostAccountInfo costAcc = info.getCostAccount();
			getAccForEntry(info, costAcc, iCostAccountWithAccount, entryColl,
					entryInfo, iPaymentSplitEntry, iCostAccount);
		}
	}

	private void getAccForEntry(PaymentSplitEntryInfo info,
			CostAccountInfo costAcc,
			ICostAccountWithAccount iCostAccountWithAccount,
			CostAccountWithAccountCollection entryColl,
			CostAccountWithAccountInfo entryInfo,
			IPaymentSplitEntry iPaymentSplitEntry, ICostAccount iCostAccount)
			throws Exception {
		EntityViewInfo entryView = new EntityViewInfo();
		FilterInfo entryFilter = new FilterInfo();
		entryFilter.getFilterItems().add(
				new FilterItemInfo("costAccount.id", costAcc.getId()));
		if (!iCostAccountWithAccount.exists(entryFilter)) {
			if (costAcc.getLevel() == 1) {
				MsgBox.showWarning(this, "成本科目未设置与会计科目的对应关系！");
				SysUtil.abort();
			} else {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
				selector.add("level");
				selector.add("parent.*");
				selector.add("curProject.name");
				selector.add("fullOrgUnit.name");
				// if(costAcc.getParent().getId()!= null)
				CostAccountInfo parent = iCostAccount
						.getCostAccountInfo(new ObjectUuidPK(costAcc
								.getParent().getId()), selector);
				getAccForEntry(info, parent, iCostAccountWithAccount,
						entryColl, entryInfo, iPaymentSplitEntry, iCostAccount);
			}
		}
		entryView.setFilter(entryFilter);
		entryColl = iCostAccountWithAccount
				.getCostAccountWithAccountCollection(entryView);
		if (entryColl.size() == 1) {
			entryInfo = entryColl.get(0);
			if (entryInfo.getAccount() != null) {
				info.setAccountView(entryInfo.getAccount());
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("accountView");
				iPaymentSplitEntry.updatePartial(info, selector);
			}
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		/*
		 * String costBillID=null; if(getUIContext().get("costBillID")==null){
		 * costBillID=editData.getPaymentBill().getId().toString(); }else{
		 * costBillID = (String)getUIContext().get("costBillID"); }
		 * getUIContext().put("costBillID",costBillID);
		 */
		checkBeforeRemove();
		super.actionRemove_actionPerformed(e);
	}
	//删除前检查状态
    private void checkBeforeRemove() throws Exception {
    	if(editData == null || editData.getId() == null || editData.getId().equals("")){
    		return;
    	}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString()));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("state",
								FDCBillStateEnum.AUDITTED_VALUE));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(FDCSplitClientHelper.getRes("listRemove"));
			SysUtil.abort();
		}
    }

	// ------------------------------------------------------------
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.finance.client.PaymentSplitWithoutTxtConEditUI.class
				.getName();
	}

	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return PaymentSplitFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return new com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo();
	}

	protected IObjectValue createNewData() {
		PaymentSplitInfo objectValue = new PaymentSplitInfo();
		objectValue
				.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentUserInfo()));
		//取服务端日期
//		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		objectValue.setIsInvalid(false);
		String costBillID = (String) getUIContext().get("costBillID");
		PaymentBillInfo costBillInfo = null;

		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("amount");
		selectors.add("localAmt");
		selectors.add("contractBillId");
		selectors.add("fdcPayReqID");
		selectors.add("actPayAmt");
		selectors.add("actPayLocAmt");
		selectors.add("fdcPayType.number");
		selectors.add("fdcPayType.name");
		selectors.add("payerAccount.id");
		selectors.add("payerAccount.isBank");
		selectors.add("payerAccount.isCash");
		selectors.add("payerAccount.isCashEquivalent");
		selectors.add("company.id");
		selectors.add("curProject.id");
		selectors.add("curProject.longNumber");
		try {
			costBillInfo = PaymentBillFactory.getRemoteInstance()
					.getPaymentBillInfo(
							new ObjectUuidPK(BOSUuid.read(costBillID)),
							selectors);
			// if(costBillInfo.getFdcPayReqID()!=null){
			// SelectorItemCollection selectorReq = new
			// SelectorItemCollection();
			// selectorReq.add("id");
			// selectorReq.add("completePrjAmt");
			// PayRequestBillInfo
			// info=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new
			// ObjectUuidPK(BOSUuid.read(costBillInfo.getFdcPayReqID())),selectorReq);
			// if(info.getCompletePrjAmt()!=null){
			// txtCompletePrjAmt.setValue(info.getCompletePrjAmt());
			// objectValue.setCompletePrjAmt(info.getCompletePrjAmt());
			// }
			// }
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}

		objectValue.setPaymentBill(costBillInfo);
		if(costBillInfo.getCurProject()!=null)
			objectValue.setCurProject(costBillInfo.getCurProject());
		objectValue.setCompletePrjAmt(costBillInfo.getActPayLocAmt());
		txtCompletePrjAmt.setValue(costBillInfo.getActPayLocAmt());
		txtCostBillNumber.setText(costBillInfo.getNumber());
		txtAmount.setValue(costBillInfo.getLocalAmt());

		setContractBillId(costBillInfo.getContractBillId());
		objectValue.setIsConWithoutText(true);
		objectValue.setFivouchered(false);
		return objectValue;
	}

	public void onLoad() throws Exception {
		/* TODO 自动生成方法存根 */
		super.onLoad();
		//避免在单据打开（未作修改）直接关闭时，出现保存提示
		this.storeFields();
		getDetailTable().getColumn("amount").setEditor(FDCSplitClientHelper._getTotalCellNumberEdit());
		setSplitBillType(CostSplitBillTypeEnum.PAYMENTSPLIT);

		if (STATUS_ADDNEW.equals(getOprtState())) {
			importCostSplitContract();
			// importCostSplitCntrChange();
			setDisplay();

			IRow row = null;
			FDCSplitBillEntryInfo entry = null;

			BOSUuid costBillId = null;
			BigDecimal amount = null;

			BOSObjectType contractBill = (new ContractBillInfo()).getBOSType();

			for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
				row = kdtEntrys.getRow(i);
				entry = (FDCSplitBillEntryInfo) row.getUserObject();

				costBillId = entry.getCostBillId();
				if (costBillId != null) {
					amount = entry.getAmount();
					if (amount != null) {
						if (costBillId.getType().equals(contractBill)) {
							row.getCell("contractAmount").setValue(amount);
						} else {
							row.getCell("cntrChangeAmount").setValue(amount);
						}

						entry.setAmount(null);
						row.getCell("amount").setValue(null);
					}
				}
			}
		}
		setOprtState(getOprtState());
		
		actionProgrAcctSelect.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_evaluatecortrol"));
		if(checkIsExistProg(this.getContractBillId().toString())!=null){
			this.actionAcctSelect.setEnabled(false);
			this.btnAcctSelect.setEnabled(false);
		}
	}

	public void onShow() throws Exception {
		if(editData!=null&&editData.getState()!=null&&editData.getState().equals(FDCBillStateEnum.INVALID)){
			actionRemove.setEnabled(false);
			actionRemoveLine.setEnabled(false);
		}
//		else{
//			actionRemove.setEnabled(true);
//			actionRemove.setVisible(true);
//			actionRemoveLine.setEnabled(true);
//			actionRemoveLine.setVisible(true);
//		}
		super.onShow();

		getDetailTable().getColumn("cntrChangeAmount").getStyleAttributes()
				.setHided(true);
		getDetailTable().getColumn("contractAmount").getStyleAttributes()
				.setHided(true);
		/*
		 * actionUnAudit.setEnabled(false); actionAttachment.setVisible(false);
		 * btnAttachment.setVisible(false);
		 */
		// 设置无文本合同的编码及名称
		String contractId = editData.getPaymentBill().getContractBillId();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("number");
		selector.add("name");
		ContractWithoutTextInfo billInfo = ContractWithoutTextFactory
				.getRemoteInstance().getContractWithoutTextInfo(
						new ObjectUuidPK(BOSUuid.read(contractId)), selector);
		txtWithoutTxtConNumber.setText(billInfo.getNumber());
		txtWithoutTxtConName.setText(billInfo.getName());
		actionAttachment.setVisible(true);
		actionAttachment.setEnabled(true);
		btnAttachment.setVisible(true);
		btnAttachment.setEnabled(true);

	}

	/**
	 * 
	 * 描述：返回编码控件（子类必须实现）
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-10-13
	 *               <p>
	 */
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	public void loadFields() {
		// TODO 自动生成方法存根
		super.loadFields();
		setContractBillId(editData.getPaymentBill().getContractBillId());
		setDisplay();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("state");
		sic.add("paymentBill.contractBillId");
		sic.add("paymentBill.billStatus");
		sic.add("Fivouchered");
		sic.add("paymentBill.actPayAmt");
		sic.add("paymentBill.actPayLocAmt");
		sic.add("paymentBill.payerAccount.id");
		sic.add("paymentBill.payerAccount.isBank");
		sic.add("paymentBill.payerAccount.isCash");
		sic.add("paymentBill.fdcPayType.number");
		sic.add("paymentBill.fdcPayType.name");
		sic.add("paymentBill.payerAccount.isCashEquivalent");
		sic.add("paymentBill.company.id");
		
		return setSelectors(sic);
	}

	protected String getSplitBillEntryClassName() {
		return PaymentSplitEntryInfo.class.getName();
	}

	protected void initWorkButton() {
		super.initWorkButton();
		/*
		 * btnRemove.setVisible(true); btnRemove.setEnabled(true);
		 * menuItemRemove.setVisible(true); menuItemRemove.setEnabled(true);
		 */
		actionRemove.setEnabled(true);
		actionRemove.setVisible(true);
		actionRemoveLine.setEnabled(true);
		actionRemoveLine.setVisible(true);
		// btnImpContrSplit.setText("面积刷新");
		// actionImpContrSplit.setVisible(true);
		// actionImpContrSplit.setEnabled(true);

		actionViewContract.setEnabled(true);
		actionViewPayment.setEnabled(true);
		actionViewContract.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_sequencecheck"));
		actionViewPayment.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_linkviewlist"));
	}

	public void updateDetailTable(IObjectCollection entrys) {
		getDetailTable().removeRows(false);
		IRow row;
		FDCSplitBillEntryInfo entry;
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			entry = (FDCSplitBillEntryInfo) iter.next();
			row = getDetailTable().addRow();
			loadLineFields(getDetailTable(), row, entry);
		}
	}

	public void updateAmountColumn(IObjectCollection entrys) {
		// getDetailTable().removeRows(false);
		IRow row;
		FDCSplitBillEntryInfo entry;
		int rowIndex = 0;
		for (Iterator iter = entrys.iterator(); iter.hasNext(); rowIndex++) {
			entry = (FDCSplitBillEntryInfo) iter.next();
			row = getDetailTable().getRow(rowIndex);
			row.getCell("amount").setValue(entry.getAmount());
			row.getCell("apportionValue").setValue(entry.getApportionValue());
			row.getCell("apportionValueTotal").setValue(
					entry.getApportionValueTotal());
			row.getCell("directAmountTotal").setValue(
					entry.getDirectAmountTotal());
			row.getCell("directAmount").setValue(entry.getDirectAmount());
			row.getCell("otherRatioTotal").setValue(entry.getOtherRatioTotal());
			row.getCell("otherRatio").setValue(entry.getOtherRatio());
		}
	}

	public void actionImpContrSplit_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionImpContrSplit_actionPerformed(e);
		// FDCCostSplit costSplit=new FDCCostSplit(null);
		// costSplit.refreshApportionAmount(editData,null);
		// updateAmountColumn(editData.getEntrys());
	}

	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		super.kdtEntrys_editStopped(e);
		if (editData.getCompletePrjAmt() != null) {
			if (e.getColIndex() == kdtEntrys.getColumnIndex("amount")) {
				if (getDetailTable().getRowCount() > 0) {
					for (int i = 0; i < getDetailTable().getRowCount(); i++) {
						Object obj = getDetailTable().getRow(i).getUserObject();
						if ((obj instanceof PaymentSplitEntryInfo)
								&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
							PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
							if (entry.getAmount() != null
									&& editData.getCompletePrjAmt() != null) {
								// BigDecimal cmpAmt = FDCHelper.ZERO;
								// cmpAmt =
								// (entry.getAmount().multiply(editData.getCompletePrjAmt())).divide(editData.getPaymentBill().getAmount(),
								// 2, BigDecimal.ROUND_HALF_EVEN);
								// entry.setCompletePrjAmt(cmpAmt);
								// getDetailTable().getRow(i).getCell("completePrjAmt").setValue(cmpAmt);

								entry.setPayedAmt(entry.getAmount());
								getDetailTable().getRow(i).getCell("payedAmt")
										.setValue(entry.getAmount());
							}
						}
					}
					// BigDecimal tempSplit = editData.getAmount().setScale(2);
					// BigDecimal temp =
					// editData.getPaymentBill().getAmount().setScale(2);
					// int idx=0;
					// if(tempSplit.equals(temp)){
					// BigDecimal amountMax=new BigDecimal(0);
					// BigDecimal amount=FDCHelper.ZERO;//null;
					// BigDecimal amountTotal=FDCHelper.ZERO;
					// amountTotal.setScale(10);
					// for(int i=0;i<getDetailTable().getRowCount();i++){
					// Object obj=getDetailTable().getRow(i).getUserObject();
					// if((obj instanceof PaymentSplitEntryInfo)&&
					// ((PaymentSplitEntryInfo)obj).getLevel()>-1){
					// PaymentSplitEntryInfo entry=(PaymentSplitEntryInfo)obj;
					// if(entry.getLevel()==0){
					// amount=entry.getCompletePrjAmt();
					// if(amount==null){
					// amount=FDCHelper.ZERO;
					// }
					// amountTotal=amountTotal.add(amount);
					// //修正项为金额最大的项
					// //if(amount.compareTo(new BigDecimal(0))!=0){
					// if(amount.compareTo(amountMax)>=0){
					// amountMax=amount;
					// idx=i;
					// }
					// }
					// else{
					// continue;
					// }
					// }
					// }
					// if(idx>0 &&
					// editData.getCompletePrjAmt().compareTo(amountTotal)!=0){
					// PaymentSplitEntryInfo
					// entry=(PaymentSplitEntryInfo)getPayEntrys().getObject(idx);
					// if(entry.getCompletePrjAmt()!=null){
					// amount=entry.getCompletePrjAmt();
					// if(amount==null){
					// amount=new BigDecimal(0);
					// }
					// amount=amount.add(editData.getCompletePrjAmt().subtract(amountTotal));
					// entry.setCompletePrjAmt(amount);
					// }
					// }
					// }
					//					
					// // int curIndex=e.getRowIndex();
					// if(idx>0){
					// PaymentSplitEntryInfo entr;
					// entr =
					// (PaymentSplitEntryInfo)kdtEntrys.getRow(idx).getUserObject();
					// PayCostSplitClientHelper.adjustPayAmount(getPayEntrys(),entr);
					// //汇总生成非明细工程项目中附加科目的成本 jelon 12/29/2006
					// PayCostSplitClientHelper.totAmountPayAddlAcct(getPayEntrys(),idx);
					// }
					// for(int i=0;i<getDetailTable().getRowCount();i++){
					// Object obj=getDetailTable().getRow(i).getUserObject();
					// if((obj instanceof PaymentSplitEntryInfo)&&
					// ((PaymentSplitEntryInfo)obj).getLevel()>-1){
					// PaymentSplitEntryInfo entry=(PaymentSplitEntryInfo)obj;
					// if(entry.getCompletePrjAmt()!=null){
					// getDetailTable().getRow(i).getCell("completePrjAmt").setValue(entry.getCompletePrjAmt());
					// }
					// }
					// }
				}
			}
		}
	}

	protected PaymentSplitEntryCollection getPayEntrys() {
		PaymentSplitEntryCollection coll = new PaymentSplitEntryCollection();
		PaymentSplitEntryInfo entry = null;
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i).getUserObject();
			coll.add(entry);
		}
		return coll;
		// return editData.getEntrys();
	}

	public void actionViewContract_actionPerformed(ActionEvent e)
			throws Exception {
		if (editData.getPaymentBill() == null) {
			MsgBox.showWarning(this, ContractClientUtils
					.getRes("selectProjLeafPls"));
			SysUtil.abort();
		}
		UIContext uiContext = new UIContext(this);
		if (editData.getPaymentBill().getContractBillId() != null) {
			uiContext.put(UIContext.ID, editData.getPaymentBill()
					.getContractBillId());
			if (editData.getPaymentBill().getCurProject() != null
					&& editData.getPaymentBill().getCurProject().getId() != null) {
				uiContext.put("projectId", editData.getPaymentBill()
						.getCurProject().getId());
			}
			IUIWindow testUI = UIFactory
					.createUIFactory(UIFactoryName.NEWTAB)
					.create(
							com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class
									.getName(), uiContext, null, OprtState.VIEW);
			testUI.show();
		}
	}

	public void actionViewPayment_actionPerformed(ActionEvent e)
			throws Exception {
		if (editData.getPaymentBill() == null) {
			MsgBox.showWarning(this, ContractClientUtils
					.getRes("selectProjLeafPls"));
			SysUtil.abort();
		}
		UIContext uiContext = new UIContext(this);
		if (editData.getPaymentBill().getContractBillId() != null) {
			uiContext.put(UIContext.ID, editData.getPaymentBill().getId());
			uiContext.put("contractBillId", editData.getPaymentBill()
					.getContractBillId());
			uiContext.put("contractBillNumber", editData.getPaymentBill()
					.getContractNo());
			IUIWindow testUI = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
					.create(PaymentBillEditUI.class.getName(), uiContext, null,
							OprtState.VIEW);
			testUI.show();
		}
	}

	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		if (e.getSource() == btnSplitBotUp || e.getSource() == btnSplitProd
				|| e.getSource() == btnSplitProj
				|| e.getSource() == menuItemSplitBotUp
				|| e.getSource() == menuItemSplitProd
				|| e.getSource() == menuItemSplitProj) {
			if (getDetailTable().getRowCount() > 0) {
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof PaymentSplitEntryInfo)
							&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
						PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
						if (entry.getAmount() != null
								&& editData.getCompletePrjAmt() != null) {
							entry.setPayedAmt(entry.getAmount());
							getDetailTable().getRow(i).getCell("payedAmt")
									.setValue(entry.getAmount());
						}
					}
				}
			}
		}
	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_VIEW.equals(oprtType)) {
			if (editData != null && (!editData.isFivouchered())){
				if(editData.getState().equals(FDCBillStateEnum.INVALID)){
					actionVoucher.setVisible(false);
					actionRemove.setEnabled(false);
				}
				else if(editData.getPaymentBill().getBillStatus().equals(BillStatusEnum.PAYED)){
					actionVoucher.setVisible(true);
				}else
					actionVoucher.setVisible(false);
			}
			else{
				if(editData!=null&&editData.getState()!=null&&editData.getState().equals(FDCBillStateEnum.INVALID)){
					actionRemove.setEnabled(false);
				}
				actionVoucher.setVisible(false);
			}
		} else {
			actionVoucher.setVisible(false);
		}
		if(isAdjustVourcherModel()){
			actionVoucher.setVisible(false);
		}
		actionVoucher.setVisible(false);
		actionVoucher.setEnabled(false);
		/*
		 * if(!STATUS_ADDNEW.equals(oprtType)) {
		 * prmtcurProject.setEnabled(false); }
		 */
	}
	
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		if(editData!=null&&editData.getId()!=null){
			List idList = new ArrayList();
			idList.add(editData.getId().toString());
			PaymentSplitFactory.getRemoteInstance().traceData(idList);
			super.actionVoucher_actionPerformed(e);
		}
	}
	
    public  KDMenuItem getAttachMenuItem( KDTable table )
    {
//    	if (getTableForCommon()!=null||getDetailTable()!=null)
//    	{
//    		KDMenuItem item = new KDMenuItem();
//            item.setName("menuItemAttachment");
//            item.setAction(new ActionAttachMent());
//            return item;
//    	}
    	return null;

    }
//  分录附件
    class ActionAttachMent extends ItemAction {
    	public ActionAttachMent() {
//            String _tempStr =EASResource.getString(FrameWorkClientUtils.strResource +"SubAttachMentText");
//            String _tempStr =EASResource.getString(FrameWorkClientUtils.strResource);
//            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
//            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
//            this.putValue(ItemAction.NAME, _tempStr);
         }
//        public void actionPerformed(ActionEvent e) {
//        	showSubAttacheMent(null);
//        }
    }
    public void actionProgrAcctSelect_actionPerformed(ActionEvent e)throws Exception {
    	ContractWithoutTextInfo contractBillInfo = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(getContractBillId()));

    	String checkIsExistProg = checkIsExistProg(this.getContractBillId().toString());
		checkProgSub(checkIsExistProg);
		BigDecimal allAssigned = FDCHelper.ZERO;
		if(checkIsExistProg != null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("number");
			sic.add("costEntries.*");
			sic.add("costEntries.costAccount.*");
			sic.add("costEntries.costAccount.curProject.*");
			ProgrammingContractInfo programmingContractInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(BOSUuid.read(checkIsExistProg)),sic);
			ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
			if(costEntries.size() == 0){
				FDCMsgBox.showInfo("当前合同对应的框架合约的成本构成为空，原拆分数据不变");
				this.abort();
			}
			getDetailTable().removeRows(false);
			IRow row;
			ProgrammingContracCostInfo acct=null;
			FDCSplitBillEntryInfo entry;
			int groupIndex = 0;
			for(Iterator iter = costEntries.iterator();iter.hasNext();){
				acct=(ProgrammingContracCostInfo)iter.next();
				//总的已分配金额
				allAssigned = allAssigned.add((acct.getContractAssign() == null ? FDCHelper.ZERO:acct.getContractAssign()));
			}
			int tempCount = 0;
			BigDecimal tempAll = FDCHelper.ZERO;
			BigDecimal splitAmount = FDCHelper.ZERO;
			for(Iterator iter = costEntries.iterator();iter.hasNext();){
				acct=(ProgrammingContracCostInfo)iter.next();
				entry=(FDCSplitBillEntryInfo)createNewDetailData(kdtEntrys);
				entry.setCostAccount(acct.getCostAccount());  
				entry.setLevel(0);
				entry.setIsLeaf(true);		
				tempCount++;
				groupIndex++;
				entry.setIndex(groupIndex);
				row=addEntry(entry);
				row.getCell("costAccount.curProject.number").setValue(
						acct.getCostAccount().getCurProject().getLongNumber().replace('!','.'));
				row.getCell("costAccount.number").setValue(
						acct.getCostAccount().getLongNumber().replace('!','.'));
				row.getCell("costAccount.curProject.name").setValue(
						acct.getCostAccount().getCurProject().getDisplayName().replace('_','\\'));
				row.getCell("costAccount.id").setValue(acct.getCostAccount().getId());
				row.getCell("costAccount.name").setValue(
						acct.getCostAccount().getDisplayName().replace('_','\\'));
				if(tempCount == costEntries.size()){
					if(acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 || allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
		    			row.getCell("splitScale").setValue(FDCHelper.ZERO);
		    		}else{
		    			row.getCell("splitScale").setValue(FDCHelper.subtract(new BigDecimal(100), tempAll));
		    		}
					//金额
					if(contractBillInfo.getAmount().compareTo(FDCHelper.ZERO) == 0 || acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 ||allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
		    			row.getCell("amount").setValue(FDCHelper.ZERO);
		    		}else{
		    			row.getCell("amount").setValue(FDCHelper.divide(contractBillInfo.getAmount().multiply(FDCHelper.subtract(new BigDecimal(100), tempAll)), new BigDecimal(100), 4, 5));
		    		}
				}else{
					if(acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 || allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
						row.getCell("splitScale").setValue(FDCHelper.ZERO);
					}else{
						row.getCell("splitScale").setValue(FDCHelper.divide(FDCHelper.toBigDecimal(acct.getContractAssign()), allAssigned,4,5).multiply(new BigDecimal(100)));
					}
					if(contractBillInfo.getAmount().compareTo(FDCHelper.ZERO) == 0 || acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 ||allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
						row.getCell("amount").setValue(FDCHelper.ZERO);
					}else{
						row.getCell("amount").setValue(contractBillInfo.getAmount().multiply(FDCHelper.divide(acct.getContractAssign(), allAssigned,4,5)));
					}
					if(acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 || allAssigned.compareTo(FDCHelper.ZERO) == 0){
						tempAll = FDCHelper.add(tempAll, FDCHelper.ZERO);
					}else{
						tempAll = FDCHelper.add(tempAll, FDCHelper.divide(FDCHelper.toBigDecimal(acct.getContractAssign()), allAssigned,4,5).multiply(new BigDecimal(100)));
					}
				}
				splitAmount = FDCHelper.add(FDCHelper.toBigDecimal(row.getCell("amount").getValue()), splitAmount);
				row.getCell("costAccount.curProject.number").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.number").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.curProject.name").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.name").getStyleAttributes().setLocked(false);
				row.getCell("splitScale").getStyleAttributes().setLocked(false);
				row.getCell("amount").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.curProject.number").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("costAccount.number").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("costAccount.curProject.name").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("costAccount.name").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("standard").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				entry.setAmount(FDCHelper.toBigDecimal(row.getCell("amount").getValue()));
			}
			setDisplay();
		}
	}
    private void checkProgSub(String checkIsExistProg) throws Exception {
    	if(checkIsExistProg == null){
    		FDCMsgBox.showWarning("该合同没有关联框架合约");
			this.abort();
    	}
		boolean costSplit = FDCUtils.isCostSplit(null, getContractBillId().toString());
		if(!costSplit){
			FDCMsgBox.showWarning("当前合同是非才成本类合同，不能关联规划科目");
			this.abort();
		}
		if(kdtEntrys.getRowCount() > 0){
			int ret = MsgBox.showConfirm2(this,"选择规划科目将清空原拆分数据，是否继续，是，清空并继续操作，否，放弃本次操作");
			if (ret==MsgBox.YES) {
			} else if(ret==MsgBox.CANCEL){
				this.abort();
			}
		}
    }
    protected String checkIsExistProg(String contractId)throws BOSException {
		String flag = null;
		String temp = null;
		String sql = "select fprogrammingcontract from T_CON_ContractWithoutText where fid='"+ contractId +"'";
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(sql.toString());
		IRowSet rs = fdcSB.executeQuery();
		try {
			if(rs.next()){
				flag = rs.getString("fprogrammingcontract");
				sql = "select fid from T_CON_PROGRAMMINGCONTRACT where fid='"+flag+"'";
				fdcSB = new FDCSQLBuilder(sql.toString());
				rs = fdcSB.executeQuery();
				if(rs.next()){
					temp = flag;
				}
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return temp;
	}
}