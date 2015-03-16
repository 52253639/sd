package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.base.dap.DAPTransformResult;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotFactory;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo;
import com.kingdee.eas.fdc.aimcost.VoucherForDynamicCollection;
import com.kingdee.eas.fdc.aimcost.VoucherForDynamicFactory;
import com.kingdee.eas.fdc.aimcost.VoucherForDynamicInfo;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewFactory;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IBeforeAccountView;
import com.kingdee.eas.fdc.basedata.PaySplitUtilFacadeFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.FIProSttBillEntryFactory;
import com.kingdee.eas.fdc.finance.FIProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.FIProductSettleBillInfo;
import com.kingdee.eas.fdc.finance.IProductSettleBill;
import com.kingdee.eas.fdc.finance.ProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.ProductSettleBillInfo;
import com.kingdee.eas.fi.gl.IVoucher;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;

public class FIProductSettleBillControllerBean extends
		AbstractFIProductSettleBillControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.finance.app.FIProductSettleBillControllerBean");

	protected boolean isUseNumber() {

		return false;
	}

	protected boolean isUseName() {
		return false;
	}

	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		FIProductSettleBillInfo bill = (FIProductSettleBillInfo) model;

		String id = ContextUtil.getCurrentFIUnit(ctx).getId().toString();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("company.id", id));
		view.setFilter(filter);
		IBeforeAccountView iBefore = BeforeAccountViewFactory
				.getLocalInstance(ctx);
		boolean has = iBefore.exists(filter);
		if (has) {
			BeforeAccountViewInfo info = iBefore
					.getBeforeAccountViewCollection(view).get(0);
			bill.setBeforeAccount(info);
		}
		
		boolean useInCorporation = FDCUtils.IsInCorporation(ctx,id);
		if(useInCorporation){
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("curProject.id");
			selector.add("productType.id");
			CurProjProductEntriesInfo project = CurProjProductEntriesFactory
					.getLocalInstance(ctx).getCurProjProductEntriesInfo(
							new ObjectStringPK(bill.getCurProjProductEntries()
									.getId().toString()), selector);
			String projectID = project.getCurProject().getId().toString();
			if(FDCUtils.getCurrentPeriod(ctx, projectID, false)!=null){
				PeriodInfo currenctCostPeriod = PeriodUtils.getPrePeriodInfo(ctx,FDCUtils.getCurrentPeriod(ctx, projectID, false));
				bill.setPeriod(currenctCostPeriod);
				Date bookedDate = bill.getCompDate();
				if(bookedDate.before(currenctCostPeriod.getBeginDate())){
					bookedDate = currenctCostPeriod.getBeginDate();
				}else if(bookedDate.after(currenctCostPeriod.getEndDate())){
					bookedDate = currenctCostPeriod.getEndDate();
				}
				bill.setBookedDate(bookedDate);
			}
		}

		IObjectPK pk = super._save(ctx, bill);
		return pk;
	}

	private void changeVoucherForVoucher(Context ctx,
			FIProductSettleBillInfo info) throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("curProjProductEntries.id", info
						.getCurProjProductEntries().getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("fiVouchered", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("id", info.getId().toString(),
						CompareType.NOTEQUALS));
//		filter.getFilterItems().add(
//				new FilterItemInfo("isRedVouchered", Boolean.TRUE,
//						CompareType.NOTEQUALS));
		IVoucher voucher = VoucherFactory.getLocalInstance(ctx);
		if (FIProductSettleBillFactory.getLocalInstance(ctx).exists(filter)) {
			view.setFilter(filter);
			CoreBaseCollection coll = super.getCoreBaseCollection(ctx, view);
			for (Iterator iter = coll.iterator(); iter.hasNext();) {
				FIProductSettleBillInfo tempInfo = (FIProductSettleBillInfo) iter
						.next();
				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("settBill.id", tempInfo.getId()
								.toString()));
				filter.getFilterItems().add(
						new FilterItemInfo("fiVouchered", Boolean.TRUE));
				view.setFilter(filter);
				view.getSelector().add("*");
				view.getSelector().add("voucher.*");
				VoucherForDynamicCollection collDy = VoucherForDynamicFactory
						.getLocalInstance(ctx).getVoucherForDynamicCollection(
								view);
				if (collDy.size() > 0) {
					VoucherForDynamicInfo infoDy = collDy.get(0);
					if (infoDy.getVoucher() != null) {
						BOSUuid voucherId = infoDy.getVoucher().getId();
						SelectorItemCollection origen = new SelectorItemCollection();
						origen.add("id");
						origen.add("hasReversed");
						VoucherInfo oldInfo = (VoucherInfo) voucher.getValue(
								new ObjectUuidPK(voucherId), origen);
						if (!oldInfo.isHasReversed()) {
							IObjectPK pk = voucher
									.reverseSave(new ObjectUuidPK(voucherId));
							PaySplitUtilFacadeFactory.getLocalInstance(ctx).traceReverseVoucher(pk);
							VoucherInfo newDyInfo = (VoucherInfo) voucher
									.getValue(pk);
							infoDy.setVoucher(newDyInfo);
							VoucherForDynamicFactory.getLocalInstance(ctx)
									.save(infoDy);
						}
					}
				}
				if (tempInfo.getVoucher() != null) {
					BOSUuid voucherId = tempInfo.getVoucher().getId();
					SelectorItemCollection origen = new SelectorItemCollection();
					origen.add("id");
					origen.add("hasReversed");
					VoucherInfo oldInfo = (VoucherInfo) voucher.getValue(
							new ObjectUuidPK(voucherId), origen);
					if (!oldInfo.isHasReversed()) {
						IObjectPK pk = voucher.reverseSave(new ObjectUuidPK(
								voucherId));
						PaySplitUtilFacadeFactory.getLocalInstance(ctx).traceReverseVoucher(pk);
						FDCSQLBuilder builderUpdate = new FDCSQLBuilder(ctx);
						builderUpdate
								.appendSql("update t_gl_voucher set Fsourcetype=3,fsourcesys=37 where fid=?");
						builderUpdate.addParam(pk.toString());
						builderUpdate.execute();
						VoucherInfo newInfo = (VoucherInfo) voucher
								.getValue(pk);
						tempInfo.setVoucher(newInfo);
						tempInfo.setIsRedVouchered(true);
						super.save(ctx, tempInfo);
					}
				}
			}
		}
	}

	protected void _reverseSave(Context ctx, IObjectPK srcBillPK,
			IObjectValue srcBillVO, BOTBillOperStateEnum bOTBillOperStateEnum,
			IObjectValue bOTRelationInfo) throws BOSException, EASBizException {

		super._reverseSave(ctx, srcBillPK, srcBillVO, bOTBillOperStateEnum,
				bOTRelationInfo);
		BOTRelationInfo botRelation = (BOTRelationInfo) bOTRelationInfo;
		FIProductSettleBillInfo info = (FIProductSettleBillInfo) srcBillVO;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("*");
		selector.add("entrys.*");
		selector.add("entrys.productBill.*");
		selector.add("curProjProductEntries.id");
		info = getFIProductSettleBillInfo(ctx, srcBillPK, selector);
		if (new VoucherInfo().getBOSType().toString().equals(
				botRelation.getDestEntityID())) {
			if (BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum)) {
				VoucherInfo voucherInfo = (VoucherInfo) VoucherFactory
						.getLocalInstance(ctx).getValue(
								new ObjectStringPK(botRelation
										.getDestObjectID()));
				info.setVoucher(voucherInfo);
				info.setFiVouchered(true);
				super.save(ctx, info);
			} else if (BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum)) {
				info.setVoucher(null);
				info.setFiVouchered(false);
				super.save(ctx, info);
				for (int i = 0, size = info.getEntrys().size(); i < size; i++) {
					ProductSettleBillInfo sett = info.getEntrys().get(i)
							.getProductBill();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("productBill.id", sett.getId()
									.toString()));
					filter.getFilterItems().add(
							new FilterItemInfo("parent.id", info.getId()
									.toString(), CompareType.NOTEQUALS));
					if (!FIProSttBillEntryFactory.getLocalInstance(ctx).exists(
							filter)) {
						sett.setFiVouchered(false);
						SelectorItemCollection sele = new SelectorItemCollection();
						sele.add("fiVouchered");
						ProductSettleBillFactory.getLocalInstance(ctx)
								.updatePartial(sett, sele);
					}
				}
			}
		}
		// changeProjectState(ctx, info);
	}

	protected DAPTransformResult _generateVoucher(Context ctx,
			IObjectCollection sourceBillCollection, IObjectPK botMappingPK)
			throws BOSException, EASBizException {
		
		DAPTransformResult result = super._generateVoucher(ctx, sourceBillCollection, botMappingPK);
		CoreBillBaseCollection coll = (CoreBillBaseCollection) sourceBillCollection;
		SelectorItemCollection selectorAll = new SelectorItemCollection();
		selectorAll.add("*");
		selectorAll.add("entrys.*");
		selectorAll.add("entrys.productBill.*");
		IProductSettleBill iSettle = ProductSettleBillFactory
				.getLocalInstance(ctx);
		for (Iterator it = coll.iterator(); it.hasNext();) {
			FIProductSettleBillInfo infoTemp = (FIProductSettleBillInfo) it
					.next();
			FIProductSettleBillInfo info = super.getFIProductSettleBillInfo(ctx,
					new ObjectUuidPK(infoTemp.getId()), selectorAll);
			for (int i = 0, size = info.getEntrys().size(); i < size; i++) {
				ProductSettleBillInfo sett = info.getEntrys().get(i)
						.getProductBill();
				sett.setFiVouchered(true);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("fiVouchered");
				iSettle.updatePartial(sett, selector);
			}
			FilterInfo filterDel = new FilterInfo();
			filterDel.getFilterItems().add(new FilterItemInfo("settBill.id",info.getId().toString()));
			VoucherForDynamicFactory.getLocalInstance(ctx).delete(filterDel);
			VoucherForDynamicInfo dynPro = new VoucherForDynamicInfo();
			dynPro.setCurProject(info.getCurProjProductEntries()
					.getCurProject());
			dynPro.setProduct(info.getCurProjProductEntries().getProductType());
			dynPro.setBeAccount(info.getBeforeAccount());
			dynPro.setFiVouchered(false);
			dynPro.setSettBill(info);
			dynPro.setPeriod(info.getPeriod());
			dynPro.setBookedDate(info.getBookedDate());
		    // info.getTotalCost() 和  info.getHappenCost() 均为空值 产生异常
			//为方便测试 若为NULL则手工设定值
			if(info.getTotalCost() == null){
				info.setTotalCost(FDCConstants.ZERO);
			}
			if(info.getHappenCost() == null){
				info.setHappenCost(FDCConstants.ZERO);
			}
			BigDecimal tempAmt = info.getTotalCost().subtract(   
					FDCHelper.toBigDecimal(info.getHappenCost()));
			if (info.isIsSelfDefine()) {
				dynPro.setAmount((tempAmt.multiply(info.getDrawingCostRate()))
						.divide(new BigDecimal(100), 8,
								BigDecimal.ROUND_HALF_EVEN));
			} else {
				//若为空值 产生除0异常 则先手工设定值
				if(info.getSaleArea()==null){
					info.setSaleArea(FDCConstants.ZERO);
				}
				dynPro.setAmount((tempAmt.multiply(info.getCompArea())).divide(
						info.getSaleArea(), 8, BigDecimal.ROUND_HALF_EVEN));
			}
			IObjectPK pkDyn = VoucherForDynamicFactory.getLocalInstance(ctx)
					.addnew(dynPro);
			if ((dynPro.getAmount() != null)
					&& (dynPro.getAmount().compareTo(FDCHelper.ZERO) != 0))
				VoucherForDynamicFactory.getLocalInstance(ctx).generateVoucher(
						pkDyn);
			changeVoucherForVoucher(ctx, info);
		}
		
		return result;
	}

	protected boolean _deleteVoucher(Context ctx, IObjectPK sourceBillPk)
			throws BOSException, EASBizException {
		boolean success = super._deleteVoucher(ctx, sourceBillPk);
		FIProductSettleBillInfo info = super.getFIProductSettleBillInfo(ctx, sourceBillPk);
		for (int i = 0, size = info.getEntrys().size(); i < size; i++) {
			ProductSettleBillInfo sett = info.getEntrys().get(i)
					.getProductBill();
			sett.setFiVouchered(false);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("fiVouchered");
			ProductSettleBillFactory.getLocalInstance(ctx).updatePartial(
					sett, selector);
		}
		VoucherForDynamicInfo dynPro = new VoucherForDynamicInfo();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("settBill.id",info.getId().toString()));
		view.setFilter(filter);
		SorterItemInfo sorter = new SorterItemInfo("createTime"); 
		sorter.setSortType(SortType.DESCEND);
		view.getSorter().add(sorter);
		VoucherForDynamicCollection coll = VoucherForDynamicFactory.getLocalInstance(ctx).getVoucherForDynamicCollection(view);
		if(coll.size()>0){
			dynPro = coll.get(0);
			VoucherForDynamicFactory.getLocalInstance(ctx).deleteVoucher(new ObjectUuidPK(dynPro.getId()));
		}
		FilterInfo filterDel = new FilterInfo();
		filterDel.getFilterItems().add(new FilterItemInfo("settBill.id",info.getId().toString()));
		VoucherForDynamicFactory.getLocalInstance(ctx).delete(filterDel);
		return success;
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		FIProductSettleBillInfo settleInfo = super.getFIProductSettleBillInfo(
				ctx, pk);
		String id = settleInfo.getCurProjProductEntries().getCurProject()
				.getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("projectId", id));
		filter.getFilterItems().add(
				new FilterItemInfo("isMonthSave", Boolean.TRUE));
		PeriodInfo glCurPeriod = FDCClientUtils.getGLCurCompanyCurPeriod();
		if (glCurPeriod != null && glCurPeriod.getId() != null)
			filter
					.appendFilterItem("period.id", glCurPeriod.getId()
							.toString());
		view.setFilter(filter);
		DynCostSnapShotCollection coll = DynCostSnapShotFactory
				.getRemoteInstance().getDynCostSnapShotCollection(view);
		if (coll.size() > 0) {
			DynCostSnapShotInfo info = coll.get(0);
			FilterInfo filterExist = new FilterInfo();
			filterExist.getFilterItems().add(
					new FilterItemInfo("curProjProductEntries.curProject.id",
							id));
			filterExist.getFilterItems().add(
					new FilterItemInfo("createTime", info.getCreateTime(),
							CompareType.GREATER));
			filterExist.getFilterItems().add(
					new FilterItemInfo("id", settleInfo.getId().toString(),
							CompareType.NOTEQUALS));
			if (!super.exists(ctx, filterExist)) {
				info.setIsUsed(false);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("isUsed");
				DynCostSnapShotFactory.getRemoteInstance().updatePartial(info,
						selector);
			}
		}
		super._delete(ctx, pk);
	}
	
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		int size = arrayPK.length;
		for(int i=0;i<size;i++){
			SelectorItemCollection sele = new SelectorItemCollection();
			sele.add("*");
			sele.add("curProjProductEntries.curProject.id");
			FIProductSettleBillInfo settleInfo = super.getFIProductSettleBillInfo(
					ctx, arrayPK[i],sele);
			String id = settleInfo.getCurProjProductEntries().getCurProject()
					.getId().toString();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("projectId", id));
			filter.getFilterItems().add(
					new FilterItemInfo("isMonthSave", Boolean.TRUE));
			PeriodInfo glCurPeriod = FDCHelper.getGLCurCompanyCurPeriod(ctx);
			if (glCurPeriod != null && glCurPeriod.getId() != null)
				filter
						.appendFilterItem("period.id", glCurPeriod.getId()
								.toString());
			view.setFilter(filter);
			DynCostSnapShotCollection coll = DynCostSnapShotFactory
					.getLocalInstance(ctx).getDynCostSnapShotCollection(view);
			if (coll.size() > 0) {
				DynCostSnapShotInfo info = coll.get(0);
				FilterInfo filterExist = new FilterInfo();
				filterExist.getFilterItems().add(
						new FilterItemInfo("curProjProductEntries.curProject.id",
								id));
				filterExist.getFilterItems().add(
						new FilterItemInfo("createTime", info.getCreateTime(),
								CompareType.GREATER));
				filterExist.getFilterItems().add(
						new FilterItemInfo("id", settleInfo.getId().toString(),
								CompareType.NOTEQUALS));
				if (!super.exists(ctx, filterExist)) {
					info.setIsUsed(false);
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("isUsed");
					DynCostSnapShotFactory.getLocalInstance(ctx).updatePartial(info,
							selector);
				}
			}
		}
		super._delete(ctx, arrayPK);
	}
}