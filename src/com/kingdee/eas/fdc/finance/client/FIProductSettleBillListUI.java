/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IBlock;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryFactory;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IBeforeAccountView;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.FIProductSettleBillCollection;
import com.kingdee.eas.fdc.finance.FIProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.FIProductSettleBillInfo;
import com.kingdee.eas.fdc.finance.ProductSettleBillCollection;
import com.kingdee.eas.fdc.finance.ProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.ProductSettleBillInfo;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IDAPTrans;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.Assert;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class FIProductSettleBillListUI extends
		AbstractFIProductSettleBillListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FIProductSettleBillListUI.class);

	// 是否DAP转换，如果是，则不允许分录生成凭证方式。
	private boolean isDAPTrans = false;

	/**
	 * output class constructor
	 */
	public FIProductSettleBillListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return FIProductSettleBillFactory.getRemoteInstance();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return getRemoteInterface();
	}

	protected void audit(List ids) throws Exception {
		// TODO 自动生成方法存根

	}

	protected void unAudit(List ids) throws Exception {
		// TODO 自动生成方法存根

	}

	protected void checkBeforeOnLoad() {

	}

	protected KDTable getBillTable() {
		return super.getBillTable();
	}

	protected void displayBill(String id) throws Exception {
		if (id != null) {
			getBillTable().removeRows(false);
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("curProjProductEntries.id", id));
			view.setFilter(filter);
			view.getSorter().add(new SorterItemInfo("createTime"));
			FIProductSettleBillCollection productSettleBillCollection = FIProductSettleBillFactory
					.getRemoteInstance().getFIProductSettleBillCollection(view);
			for (Iterator it = productSettleBillCollection.iterator(); it
					.hasNext();) {
				FIProductSettleBillInfo info = (FIProductSettleBillInfo) it
						.next();
				IRow row = getBillTable().addRow();
				row.getCell("id").setValue(info.getId().toString());
				row.getCell("compArea").setValue(info.getCompArea());
				row.getCell("costAmt").setValue(info.getTotalCost());
				row.getCell("happenAmt").setValue(info.getHappenCost());
				row.getCell("compDate").setValue(info.getCompDate());
				row.getCell("fiVouchered").setValue(
						Boolean.valueOf(info.isFiVouchered()));
				row.getCell("lstCompArea").setValue(info.getLastCompArea());
				row.getCell("lstCostAmt").setValue(info.getLastTotalCost());
				row.getCell("lstHappenAmt").setValue(info.getLastHappenCost());
			}
			// ProductSettleBillCollection productSettleBillCollection =
			// ProductSettleBillFactory
			// .getRemoteInstance().getProductSettleBillCollection(view);
			// BigDecimal totalCompArea = FDCHelper.ZERO;
			// for(Iterator it =
			// productSettleBillCollection.iterator();it.hasNext();){
			// ProductSettleBillInfo info = (ProductSettleBillInfo) it.next();
			// totalCompArea =
			// totalCompArea.add(FDCHelper.toBigDecimal(info.getCompArea()));
			// }
			// row.getCell("costCompArea").setValue(totalCompArea);
			// FIProductSettleBillCollection productCollection =
			// FIProductSettleBillFactory
			// .getRemoteInstance().getFIProductSettleBillCollection(view);
			// int size = productCollection.size();
			// if(size>0){
			// FIProductSettleBillInfo fisett = productCollection.get(size-1);
			// row.getCell("fiLastCompArea").setValue(fisett.getCompArea());
			// row.getCell("fiLastCostAmt").setValue(fisett.getTotalCost());
			// row.getCell("fiLasthappenAmt").setValue(fisett.getHappenCost());
			// }
		} else {
			getBillTable().removeRows(false);
		}
	}

	public void onLoad() throws Exception {
		if ((!SysContext.getSysContext().getCurrentOrgUnit()
				.isIsCompanyOrgUnit())
				&& (SysContext.getSysContext().getCurrentOrgUnit()
						.isIsCostOrgUnit())) {
			MsgBox.showWarning(this, PaymentBillClientUtils.getRes("CostUnit"));
			SysUtil.abort();
		}
		if (!FDCUtils.IsFinacial(null, SysContext.getSysContext()
				.getCurrentFIUnit().getId().toString())) {
			MsgBox.showWarning(this, "此财务组织未启用财务成本一体化！");
			SysUtil.abort();
		}
		super.onLoad();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionEdit }, false);
		FDCClientHelper.setActionEnable(new ItemAction[] { actionVoucher,
				actionDelVoucher }, true);
		menuBiz.setVisible(true);
		menuTool.setVisible(true);
	}

	protected String getEditUIName() {
		return FIProductSettleBillEditUI.class.getName();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);

		String id = getSelectedKeyValue(getMainTable());

		uiContext.put(PRO_ENTRIES_ID, id);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	private ProjectPeriodStatusInfo getPrjPeriodStatus(Context ctx, String prjId)
			throws BOSException, EASBizException {
		ProjectPeriodStatusInfo prjInfo = new ProjectPeriodStatusInfo();
		EntityViewInfo viewPrj = new EntityViewInfo();
		FilterInfo filterPrj = new FilterInfo();
		filterPrj.appendFilterItem("project.id", prjId);
		viewPrj.setFilter(filterPrj);
		viewPrj.getSelector().add("*");
		viewPrj.getSelector().add("costPeriod.*");
		viewPrj.getSelector().add("finacialPeriod.*");
		if (ctx == null) {
			prjInfo = ProjectPeriodStatusFactory.getRemoteInstance()
					.getProjectPeriodStatusCollection(viewPrj).get(0);
		} else {
			prjInfo = ProjectPeriodStatusFactory.getLocalInstance(ctx)
					.getProjectPeriodStatusCollection(viewPrj).get(0);
		}
		return prjInfo;
	}

	protected void checkBeforeAddNew(ActionEvent e) {
		checkSelected(getMainTable());
		BigDecimal total = FDCHelper.ZERO;
		BigDecimal sale = FDCHelper.ZERO;
		String proEntryId = getSelectedKeyValue(getMainTable());

		try {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("curProject.id");
			selector.add("productType.*");
			CurProjProductEntriesInfo curProjProductEntriesInfo = CurProjProductEntriesFactory
					.getRemoteInstance().getCurProjProductEntriesInfo(
							new ObjectUuidPK(proEntryId), selector);
			String projectId = curProjProductEntriesInfo.getCurProject()
					.getId().toString();
			ProjectPeriodStatusInfo prjInfo = getPrjPeriodStatus(null,
					projectId);
			PeriodInfo currentCostPeriod = PeriodUtils.getPrePeriodInfo(null,
					FDCUtils.getCurrentPeriod(null, projectId, false));

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
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
			}
			if (totalCompArea.compareTo(FDCHelper.ZERO) == 0) {
				MsgBox.showError(this, "成本未存在当前及之前期间的竣工产品，不需要帐务处理！");
				SysUtil.abort();
			}

			if (!prjInfo.isIsFinacialEnd()) {
				MsgBox.showInfo(this,
						"对应的工程项目未财务成本月结，此时的月结数据不准确，请财务成本月结后再处理竣工结算帐务！");
				SysUtil.abort();
			}

			String prodId = curProjProductEntriesInfo.getProductType().getId()
					.toString();
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("parent.projectId", projectId));
			if (currentCostPeriod != null)
				filter.getFilterItems().add(
						new FilterItemInfo("parent.period.id",
								currentCostPeriod.getId().toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("productTypeId", prodId));
			view.setFilter(filter);
			DynCostSnapShotProTypEntryCollection entryColl = DynCostSnapShotProTypEntryFactory
					.getRemoteInstance()
					.getDynCostSnapShotProTypEntryCollection(view);
			if (entryColl.size() == 0) {
				MsgBox.showError(this, "存在要财务结算的产品，但是对应的动态成本没有保存，不能结算！");
				SysUtil.abort();
			} else {
				for (int i = 0; i < entryColl.size(); i++) {
					if (entryColl.get(i).getDynCostAmt() != null)
						total = total.add((entryColl.get(i).getDynCostAmt()));
				}
			}
			if (total.compareTo(FDCHelper.ZERO) == 0) {
				MsgBox.showError(this, "存在要结算的产品，但是对应的动态成本为零，不能结算！");
				SysUtil.abort();
			}
			sale = ProjectHelper
					.getIndexValueByProjProdIdx(null, projectId, prodId,
							FDCConstants.SALE_AREA_ID, ProjectStageEnum.DYNCOST);
			if (sale.compareTo(FDCHelper.ZERO) == 0) {
				MsgBox.showError(this, "存在要结算的产品，但是对应的可售面积为零，不能保存！");
				SysUtil.abort();
			}
			String id = SysContext.getSysContext().getCurrentFIUnit().getId()
					.toString();
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("company.id", id));
			view.setFilter(filter);
			IBeforeAccountView iBefore = BeforeAccountViewFactory
					.getRemoteInstance();
			if (!iBefore.exists(filter)) {
				MsgBox.showError(this, "未设置财务成本一体化科目，不能新增！");
				SysUtil.abort();
			}
		} catch (BOSException exe) {
			super.handleException(exe);
		} catch (EASBizException exe) {
			super.handleException(exe);
		}
	}

	protected void updateButtonStatus() {
		// 如果是虚体财务组织，则不能增、删、改
		if ((!SysContext.getSysContext().getCurrentOrgUnit()
				.isIsCompanyOrgUnit())
				|| (!SysContext.getSysContext().getCurrentFIUnit()
						.isIsBizUnit())) {
			actionAddNew.setEnabled(false);
			actionRemove.setEnabled(false);
			actionVoucher.setEnabled(false);
			actionDelVoucher.setEnabled(false);
		} else {
			actionAddNew.setEnabled(true);
			actionRemove.setEnabled(true);
			actionVoucher.setEnabled(true);
			actionDelVoucher.setEnabled(true);
		}
	}

	protected void checkBeforeRemove() throws Exception {
		checkSelected();
		int[] selectRows = KDTableUtil.getSelectedRows(getBillTable());

		for (int i = 0; i < selectRows.length; i++) {
			String id = (String) getBillTable().getCell(selectRows[i],
					getKeyFieldName()).getValue();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("fiVouchered"));
			FIProductSettleBillInfo info = (FIProductSettleBillInfo) getBizInterface()
					.getValue(new ObjectUuidPK(BOSUuid.read(id)), sic);
			if (info.isFiVouchered()) {
				MsgBox.showError("包含已经生成凭证的记录，不能执行本操作！");
				SysUtil.abort();
			}
		}
	}

	protected KDTable getBillListTable() {
		return tblBill;
	}

	protected CoreBillBaseCollection getBillList() throws Exception {
		checkSelected();

		ArrayList idList = new ArrayList();
		List entriesKey = new ArrayList();
		getBillIdList(idList, entriesKey);
		Assert.that(idList.size() > 0);

		if (idList.size() == 1) {
			String id = idList.get(0).toString();
			CoreBillBaseInfo sourceBillInfo;

			if (getBOTPSelectors() == null) {
				sourceBillInfo = (CoreBillBaseInfo) getCoreBaseInterface()
						.getValue(new ObjectUuidPK(BOSUuid.read(id)));
			} else {
				sourceBillInfo = (CoreBillBaseInfo) getCoreBaseInterface()
						.getValue(new ObjectUuidPK(BOSUuid.read(id)),
								getBOTPSelectors());
			}

			CoreBillBaseCollection sourceBillCollection = new CoreBillBaseCollection();
			sourceBillCollection.add(sourceBillInfo);

			// 删除集合中非选中的分录ID
			if (entriesKey.size() > 0) {
				removeUnSelect(entriesKey, sourceBillCollection);
			}

			return sourceBillCollection;
		} else {
			Object[] filterObj = new Object[idList.size()];
			FilterInfo filterInfo = new FilterInfo();
			Iterator idIter = idList.iterator();
			int index = 0;
			StringBuffer sbMaskString = new StringBuffer();

			while (idIter.hasNext()) {
				String id = idIter.next().toString();
				filterObj[index] = id;
				index++;
			}

			String strIdLists = StringUtils
					.arrayToString(idList.toArray(), ",");
			filterInfo
					.getFilterItems()
					.add(
							new FilterItemInfo(
									"id",
									strIdLists,
									com.kingdee.bos.metadata.query.util.CompareType.INCLUDE));
			sbMaskString.append("#0");
			filterInfo.setMaskString(sbMaskString.toString());

			EntityViewInfo entityViewInfo = new EntityViewInfo();
			entityViewInfo.setFilter(filterInfo);

			if (getBOTPSelectors() != null) {
				entityViewInfo.put("selector", getBOTPSelectors());
			}

			CoreBillBaseCollection sourceBillCollection = ((ICoreBillBase) getCoreBaseInterface())
					.getCoreBillBaseCollection(entityViewInfo);

			// 删除集合中非选中的分录ID
			// 删除集合中非选中的分录ID
			if (entriesKey.size() > 0) {
				removeUnSelect(entriesKey, sourceBillCollection);
			}
			return sourceBillCollection;
		}
	}

	private void removeUnSelect(List entriesKey,
			CoreBillBaseCollection sourceBillCollection) {
		// 如果是DAP,则不删除分录。
		if (isDAPTrans) {
			return;
		}
		// 删除集合中非选中的分录ID
		for (int i = 0; i < sourceBillCollection.size(); i++) {
			CoreBillBaseInfo bills = sourceBillCollection.get(i);

			boolean isHasSelect = false;
			IObjectCollection entries = (IObjectCollection) bills.get(this
					.getEntriesName());

			if (entries == null) {
				return;
			}

			Iterator iters = entries.iterator();
			int unSelect = 0;
			int count = 0;
			int ii = entries.size();
			while (iters.hasNext()) {
				CoreBaseInfo cInfo = (CoreBaseInfo) iters.next();
				String ss = cInfo.getId().toString();
				isHasSelect = false;
				for (int k = 0; k < entriesKey.size(); k++) {
					if (cInfo.get("id").toString().equals(
							entriesKey.get(k).toString())) {
						isHasSelect = true;
						break;
					}
				}
				if (!isHasSelect) {
					iters.remove();
					// entries.removeObject();
				}
				// count++;
			}
		}
	}

	/**
	 * 获取当前表格选取的单据id和分录id
	 * 
	 * @return
	 * @throws Exception
	 */
	public void getBillIdList(List idList, List entriesList) {
		int mode = 0;
		List blockList = getBillTable().getSelectManager().getBlocks();
		// 判断是整表选取还是分块选取
		if (blockList != null && blockList.size() == 1) {
			mode = ((IBlock) getBillTable().getSelectManager().getBlocks().get(
					0)).getMode();
		}
		if (mode == KDTSelectManager.TABLE_SELECT) {// 表选择
			List selectIdList = this.getQueryPkList();
			if (selectIdList != null) {
				Iterator lt = selectIdList.iterator();
				while (lt.hasNext()) {
					Object[] idObj = (Object[]) lt.next();
					if (idObj == null)
						continue;
					if (!idList.contains(idObj[0].toString()))
						idList.add(idObj[0].toString());
					if (idObj.length == 2)
						entriesList.add(idObj[1]);
				}
			}
		} else {
			ArrayList blocks = getBillTable().getSelectManager().getBlocks();
			Iterator iter = blocks.iterator();

			while (iter.hasNext()) {
				KDTSelectBlock block = (KDTSelectBlock) iter.next();
				int top = block.getTop();
				int bottom = block.getBottom();

				for (int rowIndex = top; rowIndex <= bottom; rowIndex++) {
					ICell cell = getBillTable().getRow(rowIndex).getCell(
							getKeyFieldName());

					// 记录选中的分录ID
					if (getBillTable().getRow(rowIndex).getCell(
							this.getEntriesPKName()) != null
							&& getBillTable().getRow(rowIndex).getCell(
									this.getEntriesPKName()).getValue() != null) {
						entriesList.add(getBillTable().getRow(rowIndex)
								.getCell(this.getEntriesPKName()).getValue()
								.toString());
					}

					if (!idList.contains(cell.getValue())) {
						idList.add(cell.getValue());
					}
				}
			}
		}
	}

	protected void tblBill_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblBill_tableClicked(e);
	}

	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		if (e.getSource() == btnAddNew || e.getSource() == menuItemAddNew
				|| e.getSource() == btnEdit || e.getSource() == menuItemEdit) {
			actionVoucher.setVisible(true);
			actionDelVoucher.setVisible(true);
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

	public void refreshList() throws Exception {
		super.refreshList();
		actionVoucher.setVisible(true);
		actionDelVoucher.setVisible(true);
		updateButtonStatus();
	}

	protected void initTable() {

	}

	boolean isVoucherUse = false;

	public void actionDelVoucher_actionPerformed(ActionEvent e)
			throws Exception {
		isVoucherUse = true;
		super.actionDelVoucher_actionPerformed(e);
		isVoucherUse = false;
		refreshList();
	}

	protected ArrayList getSelectedIdValues() {
		if (isVoucherUse) {
			// 本来可以直接用基类的这个方法，但是有错，多循环了一次，所以自己重写
			// return super.getSelectIdForTableSelect(getBillListTable());
			ArrayList list = new ArrayList();
			// 遍历选择块的所有行
			for (int i = 0; i < getBillTable().getRowCount(); i++) {
				ICell cell = getBillTable().getRow(i)
						.getCell(getKeyFieldName());

				if (cell == null) {
					MsgBox.showError(EASResource
							.getString(FrameWorkClientUtils.strResource
									+ "Error_KeyField_Fail"));
					SysUtil.abort();
				}

				String id = cell.getValue().toString();
				if (!list.contains(id)) {
					list.add(id);
				}
			}
			return list;
		} else
			return super.getSelectedIdValues();
	}

	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionVoucher_actionPerformed(e);
		refreshList();
	}
	
    /**
     *只获取单据id和分录id
     */
    protected CoreBillBaseCollection getNewBillList() throws Exception
    {
        checkSelected();

        ArrayList idList = new ArrayList();
        List entriesKey = new ArrayList();
        getBillIdList(idList,entriesKey);

        Object[] filterObj=new Object[idList.size()];
        FilterInfo filterInfo = new FilterInfo();
        Iterator idIter = idList.iterator();
        int index = 0;
        StringBuffer sbMaskString = new StringBuffer();

        while (idIter.hasNext())
        {
              String id = idIter.next().toString();
              filterObj[index]=id;
              index++;
         }

         String strIdLists =StringUtils.arrayToString(idList.toArray(),",");
         filterInfo.getFilterItems().add(new FilterItemInfo("id", strIdLists, com.kingdee.bos.metadata.query.util.CompareType.INCLUDE));
         //如果是生成凭证，根据VOUCHERFLAG判断，其他如关联生成，上查等不根据这个判断
         if(true)
         {
            filterInfo.getFilterItems().add(new FilterItemInfo("fiVouchered",Boolean.FALSE,CompareType.EQUALS));
            filterInfo.getFilterItems().add(new FilterItemInfo("fiVouchered",null,CompareType.EQUALS));
            sbMaskString.append("#0 and (#1 or #2)");
         }else
         {
            sbMaskString.append("#0");
         }
         filterInfo.setMaskString(sbMaskString.toString());

         EntityViewInfo entityViewInfo = new EntityViewInfo();
         entityViewInfo.getSelector().add(new SelectorItemInfo("id"));
         entityViewInfo.setFilter(filterInfo);

         CoreBillBaseCollection sourceBillCollection = ( (ICoreBillBase)getCoreBaseInterface())
                .getCoreBillBaseCollection(entityViewInfo);

         //删除集合中非选中的分录ID
         if(entriesKey.size()>0)
         {
              removeUnSelect(entriesKey, sourceBillCollection);
         }
         return sourceBillCollection;
    }
}