/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotFactory;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryFactory;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewFactory;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IBeforeAccountView;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.FIProSttBillEntryInfo;
import com.kingdee.eas.fdc.finance.FIProductSettleBillCollection;
import com.kingdee.eas.fdc.finance.FIProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.FIProductSettleBillInfo;
import com.kingdee.eas.fdc.finance.ProductSettleBillCollection;
import com.kingdee.eas.fdc.finance.ProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.ProductSettleBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class FIProductSettleBillEditUI extends
		AbstractFIProductSettleBillEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FIProductSettleBillEditUI.class);

	/**
	 * output class constructor
	 */
	public FIProductSettleBillEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
//		editData.setAmount(editData.getCompArea().multiply(editData.getTotalCost()).divide(editData.getSaleArea(), 8,BigDecimal.ROUND_HALF_EVEN));
		editData.setAmount(FDCHelper.divide(FDCHelper.multiply(editData.getCompArea(), editData.getTotalCost()), editData.getSaleArea(), 8,BigDecimal.ROUND_HALF_EVEN));
		super.storeFields();
	}

	protected IObjectValue createNewData() {
		FIProductSettleBillInfo objectValue = new FIProductSettleBillInfo();
		try {
			String proEntryId = (String) getUIContext().get(
					ProductSettleBillListUI.PRO_ENTRIES_ID);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("curProject.id");
			selector.add("productType.id");
			CurProjProductEntriesInfo curProjProductEntriesInfo = CurProjProductEntriesFactory
					.getRemoteInstance().getCurProjProductEntriesInfo(
							new ObjectUuidPK(proEntryId), selector);
			String projectId = curProjProductEntriesInfo.getCurProject()
					.getId().toString();
			PeriodInfo currentCostPeriod = PeriodUtils.getPrePeriodInfo(null,
					FDCUtils.getCurrentPeriod(null, projectId, false));
			String prodId = curProjProductEntriesInfo.getProductType().getId()
					.toString();
			BigDecimal total = FDCHelper.ZERO;
			BigDecimal happen = FDCHelper.ZERO;
			BigDecimal sale = FDCHelper.ZERO;
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("parent.projectId", projectId));
			if (currentCostPeriod != null)
				filter.getFilterItems().add(
						new FilterItemInfo("parent.period.id",
								currentCostPeriod.getId().toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("productTypeId", prodId));
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("dynCostAmt");
			view.getSelector().add("costPayedAmt");
			DynCostSnapShotProTypEntryCollection entryColl = DynCostSnapShotProTypEntryFactory
					.getRemoteInstance()
					.getDynCostSnapShotProTypEntryCollection(view);
			if (entryColl.size() == 0) {
				MsgBox.showError(this, "存在要结算的产品，但是对应的动态成本没有保存，不能进行帐务处理！");
				SysUtil.abort();
			} else {
				for (int i = 0; i < entryColl.size(); i++) {
					if (entryColl.get(i).getDynCostAmt() != null)
						total = total.add(FDCHelper.toBigDecimal(entryColl.get(
								i).getDynCostAmt()));
					if (entryColl.get(i).getCostPayedAmt() != null)
						happen = happen.add(FDCHelper.toBigDecimal(entryColl
								.get(i).getCostPayedAmt()));
				}
			}
			if (total.compareTo(FDCHelper.ZERO) == 0) {
				MsgBox.showError(this, "存在要结算的产品，但是对应的动态成本为零，不能进行帐务处理！");
				SysUtil.abort();
			}
			sale = ProjectHelper
					.getIndexValueByProjProdIdx(null, projectId, prodId,
							FDCConstants.SALE_AREA_ID, ProjectStageEnum.DYNCOST);
			if (sale.compareTo(FDCHelper.ZERO) == 0) {
				MsgBox.showError(this, "存在要结算的产品，但是对应的可售面积为零，不能进行帐务处理！");
				SysUtil.abort();
			}
			objectValue.setCurProjProductEntries(curProjProductEntriesInfo);
			objectValue.setSaleArea(sale);
			objectValue.setTotalCost(total);
			objectValue.setHappenCost(happen);
			String id = SysContext.getSysContext().getCurrentFIUnit().getId()
					.toString();
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("company.id", id));
			view.setFilter(filter);
			IBeforeAccountView iBefore = BeforeAccountViewFactory
					.getRemoteInstance();
			boolean has = iBefore.exists(filter);
			if (has) {
				BeforeAccountViewInfo info = iBefore
						.getBeforeAccountViewCollection(view).get(0);
				objectValue.setBeforeAccount(info);
			} else {
				MsgBox.showError(this, "未设置财务成本一体化科目，不能进行帐务处理！");
				SysUtil.abort();
			}
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("curProjProductEntries.id", proEntryId));
			filter.getFilterItems().add(
					new FilterItemInfo("period.number", Integer
							.toString(currentCostPeriod.getNumber()),
							CompareType.LESS_EQUALS));
			view.setFilter(filter);
			view.getSorter().add(new SorterItemInfo("createTime"));
			ProductSettleBillCollection productSettleBillCollection = ProductSettleBillFactory
					.getRemoteInstance().getProductSettleBillCollection(view);
			BigDecimal totalCompArea = FDCHelper.ZERO;
			for (Iterator it = productSettleBillCollection.iterator(); it
					.hasNext();) {
				ProductSettleBillInfo info = (ProductSettleBillInfo) it.next();
				totalCompArea = totalCompArea.add(FDCHelper.toBigDecimal(info
						.getCompArea()));
				FIProSttBillEntryInfo entry = new FIProSttBillEntryInfo();
				entry.setParent(objectValue);
				entry.setProductBill(info);
				objectValue.getEntrys().add(entry);
			}
			objectValue.setCompArea(totalCompArea);
			FIProductSettleBillCollection productCollection = FIProductSettleBillFactory
					.getRemoteInstance().getFIProductSettleBillCollection(view);
			int size = productCollection.size();
			if (size > 0) {
				FIProductSettleBillInfo fisett = productCollection
						.get(size - 1);
				if ((totalCompArea.compareTo(fisett.getCompArea()) == 0)
						&& (total.compareTo(fisett.getTotalCost()) == 0)
						&& (happen.compareTo(fisett.getHappenCost()) == 0)) {
					MsgBox.showError(this,
							"目前成本竣工面积及产品的动态成本、已实现成本与上次帐务处理数据一致，不需进行帐务处理！");
					SysUtil.abort();
				}
				objectValue.setLastCompArea(fisett.getCompArea());
				objectValue.setLastTotalCost(fisett.getTotalCost());
				objectValue.setLastHappenCost(fisett.getHappenCost());
			}
			objectValue.setCompDate(DateTimeUtils.truncateDate(new Date()));
		} catch (BOSException e) {
			super.handUIException(e);
		} catch (EASBizException e) {
			super.handUIException(e);
		}
		objectValue.setIsSelfDefine(false);
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FIProductSettleBillFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionCopy, actionEdit, actionSubmit, actionRemove,
				actionPrint, actionPrintPreview, actionFirst, actionLast,
				actionNext, actionPre, actionCancel, actionCancelCancel,
				actionAttachment, actionTraceDown, actionTraceUp,
				actionWorkFlowG, actionAddLine, actionInsertLine,
				actionRemoveLine, actionCreateFrom }, false);
		txtCompArea.setHorizontalAlignment(JTextField.RIGHT);
		txtCompArea.setRemoveingZeroInDispaly(false);
		txtCompArea.setPrecision(2);
		txtHappenCost.setHorizontalAlignment(JTextField.RIGHT);
		txtHappenCost.setRemoveingZeroInDispaly(false);
		txtHappenCost.setPrecision(2);
		txtLastCompArea.setHorizontalAlignment(JTextField.RIGHT);
		txtLastCompArea.setRemoveingZeroInDispaly(false);
		txtLastCompArea.setPrecision(2);
		txtLastHappenCost.setHorizontalAlignment(JTextField.RIGHT);
		txtLastHappenCost.setRemoveingZeroInDispaly(false);
		txtLastHappenCost.setPrecision(2);
		txtLastTotalCost.setHorizontalAlignment(JTextField.RIGHT);
		txtLastTotalCost.setRemoveingZeroInDispaly(false);
		txtLastTotalCost.setPrecision(2);
		txtSaleArea.setHorizontalAlignment(JTextField.RIGHT);
		txtSaleArea.setRemoveingZeroInDispaly(false);
		txtSaleArea.setPrecision(2);
		txtTotalCost.setHorizontalAlignment(JTextField.RIGHT);
		txtTotalCost.setRemoveingZeroInDispaly(false);
		txtTotalCost.setPrecision(2);
		storeFields();
		initOldData(editData);
		if (STATUS_VIEW.equals(getOprtState()) && editData != null
				&& (!editData.isFiVouchered())) {
			actionVoucher.setVisible(true);
		} else
			actionVoucher.setVisible(false);
		txtDrawingCostRate.setEnabled(false);
		txtDrawingCostRate.setRequired(false);
		contDrawingCostRate.setEnabled(false);
		txtDrawingCostRate.setHorizontalAlignment(JTextField.RIGHT);
		txtDrawingCostRate.setRemoveingZeroInDispaly(false);
		txtDrawingCostRate.setPrecision(2);
		txtDrawingCostRate.setMaximumValue(FDCHelper.ONE_HUNDRED);
		txtDrawingCostRate.setMinimumValue(FDCHelper.ZERO);
		txtReason.setEnabled(false);
		txtReason.setRequired(false);
		contReason.setEnabled(false);
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		actionAttachment.setVisible(false);
		if (STATUS_VIEW.equals(oprtType) && editData != null
				&& (!editData.isFiVouchered())) {
			actionVoucher.setVisible(true);
		} else
			actionVoucher.setVisible(false);
		if (STATUS_VIEW.equals(oprtType)) {
			ckcSelfDefined.setEnabled(false);
		} else {
			ckcSelfDefined.setEnabled(true);
		}
	}

	protected KDTable getDetailTable() {
		return null;
	}

	public void loadFields() {
		super.loadFields();
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		String prjId = editData.getCurProjProductEntries().getCurProject()
				.getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("projectId", prjId));
		PeriodInfo currentCostPeriod = PeriodUtils.getPrePeriodInfo(null,
				FDCUtils.getCurrentPeriod(null, prjId, false));
		if (currentCostPeriod != null)
			filter.appendFilterItem("period.id", currentCostPeriod.getId()
					.toString());
		view.setFilter(filter);
		DynCostSnapShotCollection coll = DynCostSnapShotFactory
				.getRemoteInstance().getDynCostSnapShotCollection(view);
		if (coll.size() > 0) {
			DynCostSnapShotInfo info = coll.get(0);
			info.setIsUsed(true);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isUsed");
			DynCostSnapShotFactory.getRemoteInstance().updatePartial(info,
					selector);
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("beforeAccount.id");
		sic.add("beforeAccount.productAccount.id");
		sic.add("beforeAccount.productAccount.longNumber");
		sic.add("beforeAccount.productAccount.longName");
		sic.add("beforeAccount.beforeSettAccount.id");
		sic.add("beforeAccount.beforeSettAccount.longNumber");
		sic.add("beforeAccount.beforeSettAccount.longName");
		sic.add("beforeAccount.beforeDevAccount.id");
		sic.add("beforeAccount.beforeDevAccount.longNumber");
		sic.add("beforeAccount.beforeDevAccount.longName");
		sic.add("beforeAccount.intendAccount.id");
		sic.add("beforeAccount.intendAccount.longNumber");
		sic.add("beforeAccount.intendAccount.longName");
		sic.add("entrys.*");
		sic.add("voucher.id");
		sic.add("curProjProductEntries.id");
		sic.add("curProjProductEntries.curProject.id");
		sic.add("curProjProductEntries.curProject.longName");
		sic.add("curProjProductEntries.curProject.longNumber");
		sic.add("curProjProductEntries.productType.id");
		sic.add("curProjProductEntries.productType.name");
		sic.add("curProjProductEntries.productType.number");
		return sic;
	}

	protected void ckcSelfDefined_stateChanged(ChangeEvent e) throws Exception {
		if (!ckcSelfDefined.isSelected()) {
			txtDrawingCostRate.setValue(null);
			txtReason.setText(null);
			txtDrawingCostRate.setEnabled(false);
			txtDrawingCostRate.setRequired(false);
			txtReason.setEnabled(false);
			txtReason.setRequired(false);
		} else {
			txtDrawingCostRate.setEnabled(true);
			txtDrawingCostRate.setRequired(true);
			txtReason.setEnabled(true);
			txtReason.setRequired(true);
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (txtDrawingCostRate.isRequired()) {
			FDCClientVerifyHelper.verifyEmpty(this, txtDrawingCostRate);
			FDCClientVerifyHelper.verifyEmpty(this, txtReason);
		}
	}
}