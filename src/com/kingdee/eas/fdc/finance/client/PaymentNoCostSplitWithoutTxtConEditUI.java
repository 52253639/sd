/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
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
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplit;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PaymentNoCostSplitWithoutTxtConEditUI extends
		AbstractPaymentNoCostSplitWithoutTxtConEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PaymentNoCostSplitWithoutTxtConEditUI.class);

	/**
	 * output class constructor
	 */
	public PaymentNoCostSplitWithoutTxtConEditUI() throws Exception {
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
		return com.kingdee.eas.fdc.finance.client.PaymentNoCostSplitWithoutTxtConEditUI.class
				.getName();
	}

	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return PaymentNoCostSplitFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return new com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryInfo();
	}

	protected IObjectValue createNewData() {
		PaymentNoCostSplitInfo objectValue = new PaymentNoCostSplitInfo();
		objectValue
				.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentUserInfo()));
		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		objectValue.setIsInvalid(false);
		String costBillID = (String) getUIContext().get("costBillID");
		PaymentBillInfo costBillInfo = null;

		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("amount");
		selectors.add("contractBillId");
		selectors.add("fdcPayReqID");
		selectors.add("actPayAmt");
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
		objectValue.setCompletePrjAmt(costBillInfo.getActPayAmt());
		txtCompletePrjAmt.setValue(costBillInfo.getActPayAmt());
		txtCostBillNumber.setText(costBillInfo.getNumber());
		txtAmount.setValue(costBillInfo.getAmount());
		setContractBillId(costBillInfo.getContractBillId());
		objectValue.setIsConWithoutText(true);
		objectValue.setFivouchered(false);
		return objectValue;
	}

	public void onLoad() throws Exception {
		/* TODO 自动生成方法存根 */
		super.onLoad();
		initWorkButton();
		
		setSplitBillType(CostSplitBillTypeEnum.PAYMENTSPLIT);
		setOprtState(getOprtState());
		/*
		 * if (STATUS_ADDNEW.equals(getOprtState())) {
		 * //importCostSplitCntrChange(); setDisplay();
		 * 
		 * 
		 * 
		 * IRow row=null; FDCNoCostSplitBillEntryInfo entry=null;
		 * 
		 * BOSUuid costBillId=null; BigDecimal amount=null;
		 * 
		 * BOSObjectType contractBill=(new ContractBillInfo()).getBOSType();
		 * 
		 * for(int i=0; i<kdtEntrys.getRowCount(); i++){
		 * row=kdtEntrys.getRow(i);
		 * entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
		 * 
		 * costBillId=entry.getCostBillId(); if(costBillId!=null){
		 * amount=entry.getAmount(); if(amount!=null){
		 * if(costBillId.getType().equals(contractBill)){
		 * row.getCell("contractAmount").setValue(amount); }else{
		 * row.getCell("cntrChangeAmount").setValue(amount); }
		 * 
		 * entry.setAmount(null); row.getCell("amount").setValue(null); } } } }
		 */
		//避免在未作更改时，直接退出提示”是否保存“，先storeFields()一下。
		if(!STATUS_VIEW.equals(this.getOprtState())){
			this.storeFields();
		}

	}

	public void onShow() throws Exception {
//		actionRemove.setEnabled(true);
//		actionRemove.setVisible(true);
//		actionRemoveLine.setEnabled(true);
//		actionRemoveLine.setVisible(true);
		getDetailTable().getColumn("cntrChangeAmount").getStyleAttributes()
				.setHided(true);
		getDetailTable().getColumn("contractAmount").getStyleAttributes()
				.setHided(true);
		super.onShow();

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
		sic.add("paymentBill.fdcPayType.number");
		sic.add("paymentBill.fdcPayType.name");
		sic.add("paymentBill.payerAccount.id");
		sic.add("paymentBill.payerAccount.isBank");
		sic.add("paymentBill.payerAccount.isCash");
		sic.add("paymentBill.payerAccount.isCashEquivalent");
		sic.add("paymentBill.company.id");
		return setSelectors(sic);
	}

	protected String getSplitBillEntryClassName() {
		return PaymentNoCostSplitEntryInfo.class.getName();
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
		actionVoucher.setVisible(false);
		actionVoucher.setEnabled(false);
	}

	public void updateDetailTable(IObjectCollection entrys) {
		getDetailTable().removeRows(false);
		IRow row;
		FDCNoCostSplitBillEntryInfo entry;
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			entry = (FDCNoCostSplitBillEntryInfo) iter.next();
			row = getDetailTable().addRow();
			loadLineFields(getDetailTable(), row, entry);
		}
	}

	public void updateAmountColumn(IObjectCollection entrys) {
		// getDetailTable().removeRows(false);
		IRow row;
		FDCNoCostSplitBillEntryInfo entry;
		int rowIndex = 0;
		for (Iterator iter = entrys.iterator(); iter.hasNext(); rowIndex++) {
			entry = (FDCNoCostSplitBillEntryInfo) iter.next();
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

	protected PaymentNoCostSplitEntryCollection getPayEntrys() {
		PaymentNoCostSplitEntryCollection coll = new PaymentNoCostSplitEntryCollection();
		PaymentNoCostSplitEntryInfo entry = null;
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			entry = (PaymentNoCostSplitEntryInfo) kdtEntrys.getRow(i)
					.getUserObject();
			coll.add(entry);
		}
		return coll;
		// return editData.getEntrys();
	}

	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		super.kdtEntrys_editStopped(e);
		if (editData.getCompletePrjAmt() != null) {
			if (e.getColIndex() == kdtEntrys.getColumnIndex("amount")) {
				if (getDetailTable().getRowCount() > 0) {
					for (int i = 0; i < getDetailTable().getRowCount(); i++) {
						Object obj = getDetailTable().getRow(i).getUserObject();
						if ((obj instanceof PaymentNoCostSplitEntryInfo)
								&& ((PaymentNoCostSplitEntryInfo) obj)
										.getLevel() > -1) {
							PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
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
					// if((obj instanceof PaymentNoCostSplitEntryInfo)&&
					// ((PaymentNoCostSplitEntryInfo)obj).getLevel()>-1){
					// PaymentNoCostSplitEntryInfo
					// entry=(PaymentNoCostSplitEntryInfo)obj;
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
					// PaymentNoCostSplitEntryInfo
					// entry=(PaymentNoCostSplitEntryInfo)getPayEntrys().getObject(idx);
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
					// PaymentNoCostSplitEntryInfo entr;
					// entr =
					// (PaymentNoCostSplitEntryInfo)kdtEntrys.getRow(idx).getUserObject();
					// PayNoCostSplitClientHelper.adjustPayAmount(getPayEntrys(),entr);
					// //汇总生成非明细工程项目中附加科目的成本 jelon 12/29/2006
					// PayNoCostSplitClientHelper.totAmountPayAddlAcct(getPayEntrys(),idx);
					// }
					// for(int i=0;i<getDetailTable().getRowCount();i++){
					// Object obj=getDetailTable().getRow(i).getUserObject();
					// if((obj instanceof PaymentNoCostSplitEntryInfo)&&
					// ((PaymentNoCostSplitEntryInfo)obj).getLevel()>-1){
					// PaymentNoCostSplitEntryInfo
					// entry=(PaymentNoCostSplitEntryInfo)obj;
					// if(entry.getCompletePrjAmt()!=null){
					// getDetailTable().getRow(i).getCell("completePrjAmt").setValue(entry.getCompletePrjAmt());
					// }
					// }
					// }
				}
			}
		}
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
					if ((obj instanceof PaymentNoCostSplitEntryInfo)
							&& ((PaymentNoCostSplitEntryInfo) obj).getLevel() > -1) {
						PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) obj;
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
		//查看状态没有生成凭证的已付款付款拆分显示生成凭证按钮 modify by sxhong 2009/01/21 修正以前的代码缺陷
		//与成本类保持一致
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
	}
	protected void updateButtonStatus() {
		// TODO Auto-generated method stub
		super.updateButtonStatus();
		actionVoucher.setVisible(false);
		actionVoucher.setEnabled(false);
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
}