/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum;
import com.kingdee.eas.fdc.finance.PaymentAutoSplitHelper;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.SettledMonthlyHelper;
import com.kingdee.eas.fdc.finance.client.AbstractPayPlanUI.ActionAddRow;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class WorkLoadSplitEditUI extends AbstractWorkLoadSplitEditUI {
	private static final Logger logger = CoreUIObject.getLogger(WorkLoadSplitEditUI.class);

	private static final BOSObjectType PAYMENTSPLITBOSTYPE = new PaymentSplitInfo().getBOSType();
	
	private static final String REFERFLAG = "voucherRefer";
	/**
	 * output class constructor
	 */
	public WorkLoadSplitEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output txtSplitedAmount_dataChanged method
	 */
	protected void txtSplitedAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		super.txtSplitedAmount_dataChanged(e);
	}

	public void actionViewContract_actionPerformed(ActionEvent e) throws Exception {
		ContractBillInfo contract = editData.getContractBill();
		if(contract==null){
			contract=editData.getWorkLoadConfirmBill().getContractBill();
		}
		if (contract==null) {
			return;
		}
		String contractID = contract.getId().toString();
		if (contractID == null) {
			return;
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, contractID);
		IUIWindow contractBillUI = UIFactory.createUIFactory(
				UIFactoryName.NEWTAB).create(
				ContractBillEditUI.class.getName(), uiContext,
				null, OprtState.VIEW);
		contractBillUI.show();

	}

	public void actionViewWorkLoadBill_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getWorkLoadConfirmBill() == null) {
			return;
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, editData.getWorkLoadConfirmBill().getId().toString());
		// uiContext.put("contractBillId",
		// editData.getPaymentBill().getContractBillId());
		// uiContext.put("contractBillNumber",
		// editData.getPaymentBill().getContractNo());
		IUIWindow testUI = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(WorkLoadConfirmBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		testUI.show();
	}

	/**
	 * output kdtEntrys_editStopped method
	 */
	protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		if (e.getColIndex() == kdtEntrys.getColumnIndex("directAmt")) {
			if (e.getValue() != e.getOldValue()) {
				// isAutoSplit=false;
				editData.setDescription("ManualSplit");
				BigDecimal amount = FDCHelper.ZERO;
				amount = amount.setScale(10);
				PaymentSplitEntryInfo entry;
				entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(e.getRowIndex()).getUserObject();
				Object cellVal = kdtEntrys.getRow(e.getRowIndex()).getCell("directAmt").getValue();
				if (cellVal != null) {
					amount = new BigDecimal(cellVal.toString());
				}
				entry.setDirectAmt(amount);
				if (hasDirectAmt && amount.compareTo(FDCHelper.ZERO) == 0) {
					setHasDirectAmt();
					if (!hasDirectAmt) {
						// 清空
						for (int i = 0; i < getDetailTable().getRowCount(); i++) {
							((FDCSplitBillEntryInfo) getDetailTable().getRow(i).getUserObject()).put("amount", FDCHelper.ZERO);
							getDetailTable().getCell(i, "amount").setValue(FDCHelper.ZERO);
//							((FDCSplitBillEntryInfo) getDetailTable().getRow(i).getUserObject()).put("payedAmt", FDCHelper.ZERO);
//							getDetailTable().getCell(i, "payedAmt").setValue(FDCHelper.ZERO);
							// if (editData.getQualityAmount() != null) {
							// setQuaAmt();
							// }
						}
					}
				} else {
					setHasDirectAmt();
				}
			}
		}
	}

	/**
	 * output kdtEntrys_editValueChanged method
	 */
	protected void kdtEntrys_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception {
		super.kdtEntrys_editValueChanged(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PaymentSplitFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAcctSelect, actionSplitProj, actionSplitBotUp, actionSplitProd }, false);

		actionSplit.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_split"));
		actionViewWorkLoadBill.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_linkviewlist"));
		actionAutoMatchSplit.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_split"));
		actionSplitBaseOnProp.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_split"));
		actionViewContract.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sequencecheck"));
		actionSplit.setEnabled(true);
		actionViewContract.setEnabled(true);
		actionAudit.setEnabled(false);
		actionAudit.setVisible(false);
		actionDelVoucher.setEnabled(false);
		actionDelVoucher.setVisible(false);
		FDCClientHelper.setActionEnable(new ItemAction[]{actionRemoveLine,actionAddLine,actionInsertLine}, false);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setFirstLine();
	}

	public void onShow() throws Exception {
		super.onShow();
		getDetailTable().getColumn("splitScale").getStyleAttributes().setHided(true);
		if(isSplitBaseOnProp()){
			actionAutoMatchSplit.setEnabled(false);
			actionAutoMatchSplit.setVisible(false);
		}
	}

	protected void updateButtonStatus() {
		super.updateButtonStatus();
//		if(SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()) 
//			FDCClientHelper.setActionEnable(actionRemove, true);
//		else
//			FDCClientHelper.setActionEnable(actionRemove, false);
	}
	
	protected IObjectValue createNewData() {
		Map param = new HashMap();
		param.put("contractId", getUIContext().get("contractId"));
		param.put("workLoadBillId", getUIContext().get("costBillID"));
		PaymentSplitInfo info = null;
		try {
			info = (PaymentSplitInfo) PaymentSplitFactory.getRemoteInstance().getNewData(param);
			// 做一些特殊处理
			for (Iterator iter = info.getEntrys().iterator(); iter.hasNext();) {
				PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) iter.next();
				entry.setPayedAmt(FDCHelper.ZERO);
//				entry.setQualityAmount(FDCHelper.ZERO);
				entry.setSplitQualityAmt(FDCHelper.ZERO);
				entry.setSplitedPayedAmt(FDCHelper.ZERO);
				entry.setShouldPayAmt(FDCHelper.ZERO);
			}

		} catch (Exception e) {
			this.handUIException(e);
			SysUtil.abort();
		}
		return info;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {
		return new com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String wlSplitId = editData.getWorkLoadConfirmBill().getId().toString();
		if (SettledMonthlyHelper.existObject(null, PAYMENTSPLITBOSTYPE,wlSplitId)) {
			MsgBox.showWarning(this,
					"财务成本月结已经引用本数据，不能删除，如需修改，请把此工程量对应的合同进入待处理流程！");
			SysUtil.abort();
		}
		
		//作废检查
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", wlSplitId,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(this, "存在已经作废的记录，不能执行删除操作！");
			SysUtil.abort();
		}
		
		//是否被调整单或工程量确认单引用
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", wlSplitId,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));//#2
		filter.getFilterItems().add(new FilterItemInfo(REFERFLAG, PaySplitVoucherRefersEnum.ADJUSTBILL_VALUE));
		filter.getFilterItems().add(new FilterItemInfo(REFERFLAG, PaySplitVoucherRefersEnum.PAYMENTBILL_VALUE));
		filter.setMaskString("#0 and #1 and (#2 or #3)");
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(this, "存在被调整单或者工程量确认单引用的工程量拆分，不能执行删除操作！");
			SysUtil.abort();
		}
		
		//工程量确认单分录
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select pe.fid from t_fnc_workloadcostvoucherentry pe left join t_fnc_paymentsplit ps on pe.fparentid=ps.FWorkLoadBillID where ");
		sqlBuilder.appendParam("ps.fworkLoadbillId", wlSplitId);
		IRowSet rowSet = sqlBuilder.executeQuery();
		if (rowSet.size() > 0) {
			MsgBox.showWarning(this, "对应的工程量确认单已经生成凭证，不能执行删除操作！");
			SysUtil.abort();
		}
		
		//是否已审批
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", wlSplitId,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(this, "已审批单据不能删除");
			SysUtil.abort();
		}
		
		super.actionRemove_actionPerformed(e);
		
	}
	
	private void setFirstLine() {
		if (getDetailTable().getRowCount() > 0) {
			IRow row = getDetailTable().getRow(0);
			Object obj = row.getUserObject();
			if (!(obj instanceof PaymentSplitEntryInfo) || ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
				// 没有总计行
				row = getDetailTable().addRow(0);
				PaymentSplitEntryInfo entry = new PaymentSplitEntryInfo();
				entry.setLevel(-1000);
				row.setUserObject(entry);
			}
			row.getCell(2).setValue(FDCSplitClientHelper.getRes("total"));// 合计
			// int
			// contractAmt_Index=getDetailTable().getColumnIndex("contractAmt");
			BigDecimal contractAmt = FDCHelper.ZERO;
			BigDecimal changeAmt = FDCHelper.ZERO;
			BigDecimal costAmt = FDCHelper.ZERO;
			BigDecimal splitAmt = FDCHelper.ZERO;
			BigDecimal amount = FDCHelper.ZERO;

			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				IRow tempRow = getDetailTable().getRow(i);
				obj = tempRow.getUserObject();
				if (obj instanceof PaymentSplitEntryInfo) {
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
					if (entry.getLevel() == 0) {
						if (entry.getContractAmt() instanceof BigDecimal) {
							contractAmt = contractAmt.add(entry.getContractAmt());
						}
						if (entry.getChangeAmt() instanceof BigDecimal) {
							changeAmt = changeAmt.add(entry.getChangeAmt());
						}
						if (tempRow.getCell("costAmt").getValue() != null) {
							BigDecimal temp = FDCHelper.toBigDecimal(tempRow.getCell("costAmt").getValue());
							costAmt = costAmt.add(temp);
						}
						if (tempRow.getCell("splitedCostAmt").getValue() != null) {
							BigDecimal temp = FDCHelper.toBigDecimal(tempRow.getCell("splitedCostAmt").getValue());
							splitAmt = splitAmt.add(temp);
						}

						if (tempRow.getCell("amount").getValue() != null) {
							BigDecimal temp = FDCHelper.toBigDecimal(tempRow.getCell("amount").getValue());
							amount = amount.add(temp);
						}

					}
				}
			}
			row.getCell("contractAmt").setValue(contractAmt);
			row.getCell("changeAmt").setValue(changeAmt);
			row.getCell("costAmt").setValue(costAmt);
			row.getCell("splitedCostAmt").setValue(splitAmt);
			row.getCell("amount").setValue(amount);
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
			txtSplitedAmount.setValue(amount);
		}
	}

	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSplit_actionPerformed(e);
		if (hasDirectAmt) {
			// 手工拆分
			editData.setDescription("ManualSplit");
			if (getDetailTable().getRowCount() > 0) {
				for (int i = 0; i < getDetailTable().getRowCount(); i++) {
					Object obj = getDetailTable().getRow(i).getUserObject();
					if ((obj instanceof PaymentSplitEntryInfo) && ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
						PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
						if (getDetailTable().getRow(i).getCell("directAmt").getValue() != null) {
							getDetailTable().getRow(i).getCell("amount").setValue(getDetailTable().getRow(i).getCell("directAmt").getValue());
							entry.setAmount(FDCHelper.toBigDecimal(getDetailTable().getRow(i).getCell("directAmt").getValue()));
						} else {
							getDetailTable().getRow(i).getCell("amount").setValue(FDCHelper.ZERO);
							entry.setAmount(FDCHelper.ZERO);
						}
					}
				}
			}
			calDirAmt();
			calcAmount(0);
		} else {
			// 目前不支持归属金额拆分
			return;
		}

		setFirstLine();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("state");
		sic.add("description");
		sic.add("isProgress");
		sic.add("Fivouchered");
		sic.add("isWorkLoadBill");
		sic.add("curProject.id");
		sic.add("curProject.number");
		sic.add("curProject.name");
		sic.add("contractBill.id");
		sic.add("workLoadConfirmBill.contractBill.id");
		sic.add("workLoadConfirmBill.contractBill.number");
		sic.add("hasEffected");
		return setSelectors(sic);
	}
	public void actionSplitBaseOnProp_actionPerformed(ActionEvent e)
			throws Exception {
		hasDirectAmt = false;
		editData.setDescription("splitBaseOnProp");//按比例拆分
		FDCSplitBillEntryCollection entrys = getEntrys();
		//归属成本金额
		HashMap costMap = new HashMap();
		costMap.put("headCostAmt", txtAmount.getBigDecimalValue());//表头的实付金额
		costMap.put("splitCostAmtSum",kdtEntrys.getRow(0).getCell("costAmt").getValue());//成本拆分金额个明细行合计
		costMap.put("hadSplitCostAmtSum", kdtEntrys.getRow(0).getCell("splitedCostAmt").getValue());//已拆分付款金额各明细行合计
		PaymentAutoSplitHelper.splitCostAmtBaseOnProp(entrys, costMap);
		calDirAmt();
		calcAmount(0);
		
		setFirstLine();
	}
	public void actionAutoMatchSplit_actionPerformed(ActionEvent e) throws Exception {
		hasDirectAmt = false;
		editData.setDescription("AutoSplit");

		boolean hasSettleSplit = false;// 该值实际在方法里面没有用到，不知道方法为什么加这个参数 by sxhong
		// 2009-05-19 16:16:52

		PaymentAutoSplitHelper.autoSplit(getEntrys(), txtAmount.getBigDecimalValue());
		calcAmount(0);
		calDirAmt();
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				// Object obj = getDetailTable().getRow(i).getUserObject();
				IRow row = getDetailTable().getRow(i);
				row.getCell("directAmt").setValue(null);
			}
		}

		setFirstLine();
	}

	private boolean hasDirectAmt = false;

	private void setHasDirectAmt() {
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			if (row.getUserObject() != null) {
				// for (Iterator iter = getPayEntrys().iterator();
				// iter.hasNext();) {
				// for (Iterator iter = editData.getEntrys().iterator();
				// iter.hasNext();) {
				PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) row.getUserObject();
				if (entry.getDirectAmt() != null && entry.getDirectAmt().compareTo(FDCHelper.ZERO) != 0) { //负数也要可以手动拆分 Added By Owen_wen
					hasDirectAmt = true;
					return;
				}
			}
		}
		hasDirectAmt = false;
	}

	public void loadFields() {
		super.loadFields();
		setDisplay();
	}

	// 有直接金额，拆分后汇总
	private void calDirAmt() {
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i).getUserObject();
			int curIndex = getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumAccount(i);
				fdcCostSplit.totAmountAddlAcct(getEntrys(), curIndex);
			}
		}
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			if (row.getUserObject() != null) {
				FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo) row.getUserObject();
				BigDecimal amount = entry.getAmount();
				if (amount == null) {
					amount = FDCHelper.ZERO;
				}
				row.getCell("amount").setValue(amount);
			}
		}
	}

	// 对level=0的进行汇总
	private void sumAccount(int index) {
		PaymentSplitEntryInfo curEntry = (PaymentSplitEntryInfo) kdtEntrys.getRow(index).getUserObject();
		int curLevel = curEntry.getLevel();
		// int prjLevel = curEntry.getCostAccount().getCurProject().getLevel();
		PaymentSplitEntryInfo entry;
		if ((curEntry.getCostAccount().getCurProject().isIsLeaf()) && (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCostAccount().getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i).getUserObject();
					if (fdcCostSplit.isChildProjSameAcct(entry, curEntry)) {
						// if((entry.getLevel()==curLevel+1)&&(entry.getCostAccount().getCurProject().getLevel()==prjLevel+1)){
						sumAccount(i);
						if (entry.getAmount() != null)
							amtTotal = amtTotal.add(entry.getAmount());
					} else if (entry.getLevel() < curLevel + 1) {
						break;
					} else {
						continue;
					}
				}
				curEntry.setAmount(amtTotal);
				kdtEntrys.getRow(index).getCell("amount").setValue(amtTotal);
			} else {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < kdtEntrys.getRowCount(); i++) {
					entry = (PaymentSplitEntryInfo) kdtEntrys.getRow(i).getUserObject();
					if (entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())) {
						if (entry.getLevel() == curLevel + 1) {
							sumAccount(i);
							if (entry.getAmount() != null)
								amtTotal = amtTotal.add(entry.getAmount());
						} else if (entry.getLevel() > curLevel + 1) {
							continue;
						} else {
							break;
						}
					} else
						break;
				}
				curEntry.setAmount(amtTotal);
				kdtEntrys.getRow(index).getCell("amount").setValue(amtTotal);
			}
		}
	}

	protected void checkbeforeSave() {
		checkDirAmt();
		checkOver();
		super.checkbeforeSave();
	}

	private void checkOver() {
		if (getDetailTable().getRowCount() > 0) {
			IRow row = getDetailTable().getRow(0);
			BigDecimal splited = FDCHelper.toBigDecimal(row.getCell("splitedCostAmt").getValue(), 2);
			BigDecimal splitThis = FDCHelper.toBigDecimal(row.getCell("amount").getValue(), 2);
			BigDecimal total = FDCHelper.toBigDecimal(row.getCell("costAmt").getValue(), 2);
			if (splited.add(splitThis).compareTo(total) == 1) {
				MsgBox.showWarning(this, "已拆分金额大于成本拆分金额，不能保存！");
				SysUtil.abort();
			}
		}
	}

	// 检查是否存在有直接金额，但没进行对应拆分的情况。
	private void checkDirAmt() {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				if (getDetailTable().getRow(i).getCell("directAmt").getValue() != null) {
					BigDecimal dir = FDCHelper.toBigDecimal(getDetailTable().getRow(i).getCell("directAmt").getValue());
					if (dir.compareTo(FDCHelper.ZERO) > 0) {
						BigDecimal dirAmt = FDCHelper.toBigDecimal(getDetailTable().getRow(i).getCell("amount").getValue());
						if (dir.compareTo(dirAmt) != 0) {
							MsgBox.showWarning(this, FDCSplitClientHelper.getRes("haveDirAmt"));
							SysUtil.abort();
						}
					}
				}
			}
		}
	}

	protected void checkBeforeEditOrRemove(String warning) {
		if(editData == null) return;
//		super.checkBeforeEditOrRemove(warning);
		String wlSplitId = editData.getId().toString();
		try{
			if (SettledMonthlyHelper.existObject(null, PAYMENTSPLITBOSTYPE,wlSplitId)) {
				MsgBox.showWarning(this,
						"财务成本月结已经引用本数据，不能删除，如需修改，请把此工程量对应的合同进入待处理流程！");
				SysUtil.abort();
			}
			
			//作废检查
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", wlSplitId,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE));
			if (getBizInterface().exists(filter)) {
				MsgBox.showWarning(this, "存在已经作废的记录，不能执行删除操作！");
				SysUtil.abort();
			}
			
			//是否被调整单或工程量确认单引用
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", wlSplitId,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));//#2
			filter.getFilterItems().add(new FilterItemInfo(REFERFLAG, PaySplitVoucherRefersEnum.ADJUSTBILL_VALUE));
			filter.getFilterItems().add(new FilterItemInfo(REFERFLAG, PaySplitVoucherRefersEnum.PAYMENTBILL_VALUE));
			filter.setMaskString("#0 and #1 and (#2 or #3)");
			if (getBizInterface().exists(filter)) {
				MsgBox.showWarning(this, "存在被调整单或者工程量确认单引用的工程量拆分，不能执行删除操作！");
				SysUtil.abort();
			}
			
			//工程量确认单分录
			FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
			sqlBuilder.appendSql("select pe.fid from t_fnc_workloadcostvoucherentry pe left join t_fnc_paymentsplit ps on pe.fparentid=ps.FWorkLoadBillID where ");
			sqlBuilder.appendParam("ps.fworkLoadbillId", wlSplitId);
			IRowSet rowSet = sqlBuilder.executeQuery();
			if (rowSet.size() > 0) {
				MsgBox.showWarning(this, "对应的工程量确认单已经生成凭证，不能执行删除操作！");
				SysUtil.abort();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getDescription() != null && !editData.getDescription().equals("AutoSplit")) {
			btnSplit.doClick();
		}
		super.actionSave_actionPerformed(e);
		setFirstLine();
	}
	
	/* 添加直接金额的可录控制
	 * (non-Javadoc)
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillEditUI#setDisplay()
	 */
	protected void setDisplay() {
		super.setDisplay();
		setDirectAmtDisplay();
	}
	private void setDirectAmtDisplay() {
		if (getDetailTable().getRowCount() > 0) {
			for (int i = 0; i < getDetailTable().getRowCount(); i++) {
				Object obj = getDetailTable().getRow(i).getUserObject();
				if ((obj instanceof PaymentSplitEntryInfo) && ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
					IRow row = getDetailTable().getRow(i);
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
					if (entry.getLevel() == 0) {
						row.getStyleAttributes().setBackground(new Color(0xF6F6BF));
						row.getCell("amount").getStyleAttributes().setBackground(new Color(0xFFFFFF));
					} else {
						row.getStyleAttributes().setBackground(new Color(0xF5F5E6));
						row.getCell("amount").getStyleAttributes().setLocked(true);
					}
					if (entry.getCostAccount() != null && entry.getCostAccount().getCurProject() != null && (entry.getCostAccount().getCurProject().isIsLeaf()) && (entry.isIsLeaf())) {
						row.getCell("directAmt").getStyleAttributes().setBackground(new Color(0xFFFFFF));
						KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
						formattedTextField.setPrecision(2);
						formattedTextField.setRemoveingZeroInDispaly(false);
						formattedTextField.setRemoveingZeroInEdit(false);
						formattedTextField.setNegatived(true);
						formattedTextField.setMaximumValue(FDCHelper.toBigDecimal(row.getCell("costAmt").getValue(), 2).subtract(FDCHelper.toBigDecimal(row.getCell("splitedCostAmt").getValue(), 2)));
						formattedTextField.setMinimumValue(FDCHelper.MIN_VALUE);
						ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
						row.getCell("directAmt").setEditor(numberEditor);
					} else {
						row.getCell("directAmt").getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
						row.getCell("directAmt").getStyleAttributes().setLocked(true);
					}
				}
			}
		}
	}
}