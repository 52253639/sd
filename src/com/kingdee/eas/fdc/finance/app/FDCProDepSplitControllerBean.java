package com.kingdee.eas.fdc.finance.app;

import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractCollection;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractFactory;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanFactory;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo;
import com.kingdee.eas.fdc.finance.FDCProDepSplitInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.app.DbUtil;

public class FDCProDepSplitControllerBean extends
		AbstractFDCProDepSplitControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.finance.app.FDCProDepSplitControllerBean");

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._audit(ctx, billId);
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		FDCBillInfo billInfo = new FDCBillInfo();

		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");

		updatePartial(ctx, billInfo, selector);
	}

	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		// 删除分录的分录，框架不会自己删
		StringBuffer sql = new StringBuffer();
		sql.append(" delete from T_FNC_FDCProDepSplitEntryEntry where ");
		sql
				.append(" FParentID in (select FID from T_FNC_FDCProDepSplitEntry where ");
		sql.append(" FParentID = '").append(pk.toString()).append("') ");
		DbUtil.execute(ctx, sql.toString());

		BOSUuid planId = ((FDCProDepSplitInfo) model).getFdcProDep().getId();
		if (planId != null) {
			setUnChange(ctx, planId);
		}

		super._update(ctx, pk, model);
	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		BOSUuid planId = ((FDCProDepSplitInfo) model).getFdcProDep().getId();
		if (planId != null) {
			setUnChange(ctx, planId);
		}

		((FDCProDepSplitInfo) model).setSplitDate(new Date());
		return super._addnew(ctx, model);
	}

	private void setUnChange(Context ctx, BOSUuid planId)
			throws EASBizException, BOSException {
		// 将项目滚动计划设为未重新汇总状态
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("isRespite");
		FDCProDepConPayPlanInfo plan = new FDCProDepConPayPlanInfo();
		plan.setId(planId);
		plan.setIsRespite(false);
		FDCProDepConPayPlanFactory.getLocalInstance(ctx).updatePartial(plan,
				sic);
	}

	/**
	 * 先删除原拆分，再更新滚动计划为‘未重新汇总状态’
	 */
	protected void _reSplit(Context ctx, BOSUuid pk) throws BOSException {
		try {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("fdcProDep.id");
			CoreBaseInfo info = getValue(ctx, new ObjectUuidPK(pk), sic);

			_delete(ctx, new ObjectUuidPK(pk));

			// 将项目滚动计划设为未重新汇总状态
			sic = new SelectorItemCollection();
			sic.add("isReSum");
			FDCProDepConPayPlanInfo plan = (FDCProDepConPayPlanInfo) info
					.get("fdcProDep");
			plan.setIsReSum(false);
			FDCProDepConPayPlanFactory.getLocalInstance(ctx).updatePartial(
					plan, sic);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 校验项目月度资金需求计划中的合同是否完全拆分
	 */
	protected boolean _isConAllSplit(Context ctx, String planID)
			throws BOSException {
		if (!FDCHelper.isEmpty(planID)) {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("contract.id");
			sic.add("contract.isCoseSplit");
			view.setSelector(sic);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("head.id", planID));
			view.setFilter(filter);
			FDCProDepConPayPlanContractCollection splitCol = FDCProDepConPayPlanContractFactory
					.getLocalInstance(ctx)
					.getFDCProDepConPayPlanContractCollection(view);
			if (splitCol != null && splitCol.size() > 0) {
				for (int i = 0; i < splitCol.size(); i++) {
					ContractBillInfo contract = splitCol.get(i).getContract();
					// 查看成本拆分是否完全
					if (contract.isIsCoseSplit()) {
						filter = new FilterInfo();
						filter.getFilterItems().add(
								new FilterItemInfo("isInvalid", Boolean.FALSE));
						filter.getFilterItems().add(
								new FilterItemInfo("splitState",
										CostSplitStateEnum.ALLSPLIT_VALUE));
						filter.getFilterItems().add(
								new FilterItemInfo("contractBill.id", contract
										.getId().toString()));
						try {
							if (!ContractCostSplitFactory.getLocalInstance(ctx)
									.exists(filter)) {
								return false;
							}
						} catch (EASBizException e) {
							e.printStackTrace();
						}
					}
					// 查看非成本拆分是否完全
					else {
						filter = new FilterInfo();
						filter.getFilterItems().add(
								new FilterItemInfo("isInvalid", Boolean.FALSE));
						filter.getFilterItems().add(
								new FilterItemInfo("splitState",
										CostSplitStateEnum.ALLSPLIT_VALUE));
						filter.getFilterItems().add(
								new FilterItemInfo("contractBill.id", contract
										.getId().toString()));
						try {
							if (!ConNoCostSplitFactory.getLocalInstance(ctx)
									.exists(filter)) {
								return false;
							}
						} catch (EASBizException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return true;
	}

}