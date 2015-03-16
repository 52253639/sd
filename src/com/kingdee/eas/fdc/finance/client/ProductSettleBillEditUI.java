/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Iterator;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryFactory;
import com.kingdee.eas.fdc.aimcost.VoucherForDynamicFactory;
import com.kingdee.eas.fdc.aimcost.VoucherForDynamicInfo;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewFactory;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IBeforeAccountView;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitVoucherHelper;
import com.kingdee.eas.fdc.finance.ProductSettleBillCollection;
import com.kingdee.eas.fdc.finance.ProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.ProductSettleBillInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProductSettleBillEditUI extends AbstractProductSettleBillEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ProductSettleBillEditUI.class);

	/**
	 * output class constructor
	 */
	public ProductSettleBillEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();

		txtCompArea.setHorizontalAlignment(JTextField.RIGHT);
		
		String proEntryId = (String) getUIContext().get(
				ProductSettleBillListUI.PRO_ENTRIES_ID);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("curProject.id");
		selector.add("productType.id");
		CurProjProductEntriesInfo curProjProductEntriesInfo = CurProjProductEntriesFactory
				.getRemoteInstance().getCurProjProductEntriesInfo(
						new ObjectUuidPK(proEntryId), selector);
		String projectId = curProjProductEntriesInfo.getCurProject().getId()
				.toString();
		String prodId = curProjProductEntriesInfo.getProductType().getId()
				.toString();
		BigDecimal totalArea = ProjectHelper.getIndexValueByProjProdIdx(null, projectId,
					prodId, FDCConstants.SALE_AREA_ID, ProjectStageEnum.DYNCOST);
		EntityViewInfo viewEntry = new EntityViewInfo();
		FilterInfo filterEntry = new FilterInfo();
		filterEntry.getFilterItems().add(
				new FilterItemInfo("curProjProductEntries.id",
						proEntryId));
		if(editData.getId()!=null){//排除本记录
			filterEntry.getFilterItems().add(
					new FilterItemInfo("id",
							editData.getId().toString(),CompareType.NOTEQUALS));
		}
		viewEntry.setFilter(filterEntry);
		viewEntry.getSelector().add("compArea");

		CoreBaseCollection settColl = getBizInterface().getCollection(viewEntry);
		BigDecimal total = FDCHelper.ZERO;
		for (Iterator it = settColl.iterator(); it.hasNext();) {
			ProductSettleBillInfo settInfo = (ProductSettleBillInfo) it
					.next();
			if (settInfo.getCompArea() != null)
				total = total.add(FDCHelper.toBigDecimal(settInfo
						.getCompArea()));
		}
		txtCompArea.setMinimumValue(new BigDecimal(0.01));
		txtCompArea.setMaximumValue(totalArea.subtract(total));
		if(editData.getCurProjProductEntries()!=null)
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionCopy, actionEdit, actionSubmit, actionRemove,
				actionPrint, actionPrintPreview, actionFirst, actionLast,
				actionNext, actionPre, actionCancel, actionCancelCancel,
				actionAttachment, actionWorkFlowG, actionTraceUp,
				actionCreateFrom, actionAddLine, actionRemoveLine,
				actionInsertLine, actionTraceDown, actionAuditResult }, false);
	}

	/**
	 * getBizInterface
	 * 
	 * @return ICoreBase
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return ProductSettleBillFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		ProductSettleBillInfo info = new ProductSettleBillInfo();

		String proEntryId = (String) getUIContext().get(
				ProductSettleBillListUI.PRO_ENTRIES_ID);

		CurProjProductEntriesInfo proEntryInfo = new CurProjProductEntriesInfo();
		proEntryInfo.setId(BOSUuid.read(proEntryId));

		info.setCurProjProductEntries(proEntryInfo);
		info.setFiVouchered(false);

		return info;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		// TODO Auto-generated method stub
		return null;
	}

	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return null;
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyRequire(this);
//		if(!editData.isFiVouchered())
//			storeData();

		super.actionSave_actionPerformed(e);
//		if(!editData.isFiVouchered())
//			genVoucher();
	}

	BigDecimal total = FDCHelper.ZERO;

	BigDecimal sale = FDCHelper.ZERO;

	private void storeData() throws Exception {

		String proEntryId = (String) getUIContext().get(
				ProductSettleBillListUI.PRO_ENTRIES_ID);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("curProject.id");
		selector.add("productType.*");
		CurProjProductEntriesInfo curProjProductEntriesInfo = CurProjProductEntriesFactory
				.getRemoteInstance().getCurProjProductEntriesInfo(
						new ObjectUuidPK(proEntryId), selector);
		String projectId = curProjProductEntriesInfo.getCurProject().getId()
				.toString();
		String prodId = curProjProductEntriesInfo.getProductType().getId()
				.toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("parent.projectId", projectId));
		filter.getFilterItems().add(
				new FilterItemInfo("parent.isMonthSave", Boolean.TRUE));
		filter.getFilterItems()
				.add(new FilterItemInfo("productTypeId", prodId));
		view.setFilter(filter);
		DynCostSnapShotProTypEntryCollection entryColl = DynCostSnapShotProTypEntryFactory
				.getRemoteInstance().getDynCostSnapShotProTypEntryCollection(
						view);
		if (entryColl.size() == 0) {
			MsgBox.showError(this, "存在要结算的产品，但是对应的动态成本没有保存，不能结算，请先保存动态成本快照！");
			SysUtil.abort();
		} else {
			for (int i = 0; i < entryColl.size(); i++) {
				if (entryColl.get(i).getDynCostAmt() != null)
					total = total.add((entryColl.get(i).getDynCostAmt()));
			}
		}
		if (total.compareTo(FDCHelper.ZERO) == 0) {
			MsgBox.showError(this, "存在要结算的产品，但是对应的动态成本为零，不能结算，请先保存动态成本快照！");
			SysUtil.abort();
		}
//		sale = ProjectHelper.getIndexValueByProjProdIdx(null, projectId,
//				prodId, FDCConstants.SALE_AREA_ID, ProjectStageEnum.DYNCOST);
//		if (sale.compareTo(FDCHelper.ZERO) == 0) {
//			MsgBox.showError(this, "存在要结算的产品，但是对应的可售面积为零，不能保存！");
//			SysUtil.abort();
//		}
//		String id = SysContext.getSysContext().getCurrentFIUnit().getId()
//				.toString();
//		view = new EntityViewInfo();
//		filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("company.id", id));
//		view.setFilter(filter);
//		IBeforeAccountView iBefore = BeforeAccountViewFactory
//				.getRemoteInstance();
//		boolean has = iBefore.exists(filter);
//		if (has) {
//			BeforeAccountViewInfo info = iBefore
//					.getBeforeAccountViewCollection(view).get(0);
//			editData.setBeforeAccount(info);
//		} else {
//			MsgBox.showError(this, "未设置财务成本一体化科目，不能保存！");
//			SysUtil.abort();
//		}
//		editData.setTotalCost(total);
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("curProjProductEntries.id", proEntryId));
		if (editData.getId() != null)
			filter.getFilterItems().add(
					new FilterItemInfo("id", editData.getId().toString(),
							CompareType.NOTEQUALS));

		if (ProductSettleBillFactory.getRemoteInstance().exists(filter)) {
			view.setFilter(filter);
			ProductSettleBillCollection coll = ProductSettleBillFactory
					.getRemoteInstance().getProductSettleBillCollection(view);
			BigDecimal compArea = FDCHelper.ZERO;
			for (Iterator iter = coll.iterator(); iter.hasNext();) {
				ProductSettleBillInfo info = (ProductSettleBillInfo) iter
						.next();
				compArea = compArea.add(FDCHelper.toBigDecimal(info
						.getCompArea()));
			}
			editData.setAmount((total.multiply(compArea.add(FDCHelper
					.toBigDecimal(txtCompArea.getNumberValue())))).divide(sale,
					8, BigDecimal.ROUND_HALF_EVEN));
		} else {
//			editData.setAmount((total.multiply(FDCHelper
//					.toBigDecimal(txtCompArea.getNumberValue()))).divide(sale,
//					8, BigDecimal.ROUND_HALF_EVEN));
		}
	}
	
	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		if(e.getSource()==btnSave){
			try {
				if(editData.getId()!=null){
					ProductSettleBillInfo billInfo = (ProductSettleBillInfo)getValue(new ObjectUuidPK(editData.getId()));
					if(billInfo.isFiVouchered())
						actionSave.setEnabled(false);
				}
			} catch (Exception e1) {				
				super.handleException(e1);
			}			
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("fiVouchered");
		sic.add("beforeAccount.*");
		sic.add("curProjProductEntries.*");
		return sic;
	}
}