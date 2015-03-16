package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.base.dap.DAPTransformResult;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewFactory;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IBeforeAccountView;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectStatusFactory;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.ProductSettleBillCollection;
import com.kingdee.eas.fdc.finance.ProductSettleBillInfo;
import com.kingdee.eas.util.app.ContextUtil;

public class ProductSettleBillControllerBean extends
		AbstractProductSettleBillControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.finance.app.ProductSettleBillControllerBean");

	protected boolean isUseNumber() {

		return false;
	}

	protected boolean isUseName() {
		return false;
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ProductSettleBillInfo bill = super.getProductSettleBillInfo(ctx, pk);
		changeProjectState(ctx, bill);
		super._delete(ctx, pk);
		
	}
	
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		for(int i=0,size = arrayPK.length;i<size;i++){
			ProductSettleBillInfo bill = super.getProductSettleBillInfo(ctx, arrayPK[i]);
			if (bill.getCurProjProductEntries().getId() != null) {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
				selector.add("curProject.id");
				selector.add("productType.id");
				CurProjProductEntriesInfo project = CurProjProductEntriesFactory
						.getLocalInstance(ctx).getCurProjProductEntriesInfo(
								new ObjectStringPK(bill.getCurProjProductEntries()
										.getId().toString()), selector);
				project.setIsCompSettle(false);
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("isCompSettle"));
				CurProjProductEntriesFactory.getLocalInstance(ctx)
						.updatePartial(project, sic);
				
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder
						.appendSql("update T_FDC_CurProject set FProjectStatusID=? where FID=?");
				builder.addParam(ProjectStatusInfo.proceedingID);
//				builder.addParam(costBillId.toString());
				builder.addParam(project.getCurProject().getId().toString());
				builder.executeUpdate();
			}
		}
		super._delete(ctx, arrayPK);
	}
	
	protected IObjectPK[] _delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException {
		return super._delete(ctx, filter);
	}

	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		ProductSettleBillInfo bill = (ProductSettleBillInfo) model;

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
			boolean isUseCostOrFinance = FDCUtils.IsUseCostOrFinance(ctx, id);
			PeriodInfo currenctCostPeriod = FDCUtils.getCurrentPeriod(ctx, projectID, isUseCostOrFinance);
			if(currenctCostPeriod!=null)
				bill.setPeriod(currenctCostPeriod);
		}
		IObjectPK pk = super._save(ctx, bill);
//		changeVoucherForVoucher(ctx, bill);
		changeProjectState(ctx, bill);

		return pk;
	}

	private void changeProjectState(Context ctx, ProductSettleBillInfo bill)
			throws BOSException, EASBizException {
		if (bill.getCurProjProductEntries().getId() != null) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("curProject.id");
			selector.add("productType.id");
			CurProjProductEntriesInfo project = CurProjProductEntriesFactory
					.getLocalInstance(ctx).getCurProjProductEntriesInfo(
							new ObjectStringPK(bill.getCurProjProductEntries()
									.getId().toString()), selector);
			String projectID = project.getCurProject().getId().toString();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("curProject.id", projectID));
			view.setFilter(filter);
			CurProjProductEntriesCollection entryColl = CurProjProductEntriesFactory
					.getLocalInstance(ctx).getCurProjProductEntriesCollection(
							view);
			boolean isAll = true;
			for (Iterator itor = entryColl.iterator(); itor.hasNext();) {
				CurProjProductEntriesInfo entryInfo = (CurProjProductEntriesInfo) itor
						.next();
				BigDecimal sale = ProjectHelper.getIndexValueByProjProdIdx(ctx,
						projectID, entryInfo.getProductType().getId()
								.toString(), FDCConstants.SALE_AREA_ID,
						ProjectStageEnum.DYNCOST);
				EntityViewInfo viewEntry = new EntityViewInfo();
				FilterInfo filterEntry = new FilterInfo();
				filterEntry.getFilterItems().add(
						new FilterItemInfo("curProjProductEntries.id",
								entryInfo.getId().toString()));
				viewEntry.setFilter(filterEntry);
				viewEntry.getSelector().add("compArea");

				ProductSettleBillCollection settColl = super
						.getProductSettleBillCollection(ctx, viewEntry);
				BigDecimal total = FDCHelper.ZERO;
				for (Iterator it = settColl.iterator(); it.hasNext();) {
					ProductSettleBillInfo settInfo = (ProductSettleBillInfo) it
							.next();
					if (settInfo.getCompArea() != null)
						total = total.add(FDCHelper.toBigDecimal(settInfo
								.getCompArea()));
				}
				if (!(total.compareTo(FDCHelper.toBigDecimal(sale)) == 0)) {
					isAll = false;
					entryInfo.setIsCompSettle(false);
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add(new SelectorItemInfo("isCompSettle"));
					CurProjProductEntriesFactory.getLocalInstance(ctx)
							.updatePartial(entryInfo, sic);
					continue;
				} else {
					entryInfo.setIsCompSettle(true);
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add(new SelectorItemInfo("isCompSettle"));
					CurProjProductEntriesFactory.getLocalInstance(ctx)
							.updatePartial(entryInfo, sic);
				}
			}
			if (isAll) {
				CurProjectInfo info = CurProjectFactory.getLocalInstance(ctx)
						.getCurProjectInfo(
								new ObjectUuidPK(BOSUuid.read(projectID)));
				info.setProjectStatus(ProjectStatusFactory
						.getLocalInstance(ctx).getProjectStatusInfo(
								new ObjectUuidPK(BOSUuid
										.read(ProjectStatusInfo.settleID))));
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("projectStatus"));
				CurProjectFactory.getLocalInstance(ctx)
						.updatePartial(info, sic);
			} else {
				CurProjectInfo info = CurProjectFactory.getLocalInstance(ctx)
						.getCurProjectInfo(
								new ObjectUuidPK(BOSUuid.read(projectID)));
				info
						.setProjectStatus(ProjectStatusFactory
								.getLocalInstance(ctx)
								.getProjectStatusInfo(
										new ObjectUuidPK(
												BOSUuid
														.read(ProjectStatusInfo.proceedingID))));
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("projectStatus"));
				CurProjectFactory.getLocalInstance(ctx)
						.updatePartial(info, sic);
			}
		}
	}

	protected void _reverseSave(Context ctx, IObjectPK srcBillPK,
			IObjectValue srcBillVO, BOTBillOperStateEnum bOTBillOperStateEnum,
			IObjectValue bOTRelationInfo) throws BOSException, EASBizException {

		super._reverseSave(ctx, srcBillPK, srcBillVO, bOTBillOperStateEnum,
				bOTRelationInfo);
//		BOTRelationInfo botRelation = (BOTRelationInfo) bOTRelationInfo;
//		ProductSettleBillInfo info = (ProductSettleBillInfo) srcBillVO;
//		SelectorItemCollection selector = new SelectorItemCollection();
//		selector.add("*");
//		selector.add("curProjProductEntries.id");
//		info = getProductSettleBillInfo(ctx, srcBillPK, selector);
//		if (new VoucherInfo().getBOSType().toString().equals(
//				botRelation.getDestEntityID())) {
//			if (BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum)) {
//				VoucherInfo voucherInfo = (VoucherInfo) VoucherFactory
//						.getLocalInstance(ctx).getValue(
//								new ObjectStringPK(botRelation
//										.getDestObjectID()));
//				info.setVoucher(voucherInfo);
//				info.setFiVouchered(true);
//				super.save(ctx, info);
//			} else if (BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum)) {
//				info.setVoucher(null);
//				info.setFiVouchered(false);
//				super.save(ctx, info);
//			}
//		}
	}

	protected DAPTransformResult _generateVoucher(Context ctx,
			IObjectCollection sourceBillCollection, IObjectPK botMappingPK)
			throws BOSException, EASBizException {
		return super._generateVoucher(ctx, sourceBillCollection, botMappingPK);
	}

	protected boolean _deleteVoucher(Context ctx, IObjectPK sourceBillPk)
			throws BOSException, EASBizException {
		boolean success = super._deleteVoucher(ctx, sourceBillPk);
		return success;
	}
}