package com.kingdee.eas.fdc.aimcost.app;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.TemplatePlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.TemplatePlanIndexInfo;

public class TemplateMeasureCostControllerBean extends
		AbstractTemplateMeasureCostControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.aimcost.app.TemplateTemplateMeasureCostControllerBean");

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._audit(ctx, billId);
	}

	protected void _audit(Context ctx, List idList) throws BOSException,
			EASBizException {
		super._audit(ctx, idList);
	}

	protected void _unaudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
	}

	protected void _unaudit(Context ctx, List idList) throws BOSException,
			EASBizException {
	}

	protected boolean isUseName() {

		return false;
	}

	protected boolean isUseNumber() {
		return false;
	}

	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		TemplateMeasureCostInfo info = (TemplateMeasureCostInfo) model;
		TemplatePlanIndexInfo planIndex = (TemplatePlanIndexInfo) info.get("PlanIndex");
		super._save(ctx, pk, model);
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("headID", pk.toString());
		TemplatePlanIndexFactory.getLocalInstance(ctx).delete(filter);
		planIndex.setHeadID(pk.toString());
		TemplatePlanIndexFactory.getLocalInstance(ctx).submit(planIndex);
	}

	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		TemplateMeasureCostInfo info = (TemplateMeasureCostInfo) model;
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("sourceBillId", info.getSourceBillId());
		_delete(ctx, filter);
		IObjectPK pk = super._save(ctx, model);
		
		TemplatePlanIndexInfo planIndex = (TemplatePlanIndexInfo) info.get("PlanIndex");
		if(planIndex==null) {
			return pk;
		}
/*		
		filter = new FilterInfo();
		filter.appendFilterItem("headID", pk.toString());
		TemplatePlanIndexFactory.getLocalInstance(ctx).delete(filter);*/
		planIndex.setHeadID(pk.toString());
		TemplatePlanIndexFactory.getLocalInstance(ctx).submit(planIndex);
		return pk;
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		TemplateMeasureCostInfo info = (TemplateMeasureCostInfo) model;
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("sourceBillId", info.getSourceBillId());
		_delete(ctx, filter);
		TemplatePlanIndexInfo planIndex = (TemplatePlanIndexInfo) info.get("PlanIndex");
		IObjectPK pk = super._submit(ctx, model);
/*		
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("headID", pk.toString());
		TemplatePlanIndexFactory.getLocalInstance(ctx).delete(filter);*/
		
		planIndex.setHeadID(pk.toString());
		TemplatePlanIndexFactory.getLocalInstance(ctx).submit(planIndex);
		return pk;
	}

	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		TemplateMeasureCostInfo info = (TemplateMeasureCostInfo) model;
		TemplatePlanIndexInfo planIndex = (TemplatePlanIndexInfo) info.get("PlanIndex");
		super._submit(ctx, pk, model);
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("headID", pk.toString());
		TemplatePlanIndexFactory.getLocalInstance(ctx).delete(filter);
		planIndex.setHeadID(pk.toString());
		TemplatePlanIndexFactory.getLocalInstance(ctx).submit(planIndex);
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("headID", pk.toString());
		TemplatePlanIndexFactory.getLocalInstance(ctx).delete(filter);
		super._delete(ctx, pk);
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		Set set = new HashSet();
		for (int i = 0; i < arrayPK.length; i++) {
			set.add(arrayPK[i].toString());
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("headID", set, CompareType.INCLUDE));
		TemplatePlanIndexFactory.getLocalInstance(ctx).delete(filter);
		super._delete(ctx, arrayPK);
	}
	
	protected IObjectPK[] _delete(Context ctx, String oql) throws BOSException,
			EASBizException {
		IObjectPK[] pks = super._delete(ctx, oql);
		Set set = new HashSet();
		for (int i = 0; i < pks.length; i++) {
			set.add(pks[i].toString());
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("headID", set, CompareType.INCLUDE));
		TemplatePlanIndexFactory.getLocalInstance(ctx).delete(filter);
		return pks;
	}
	protected IObjectPK[] _delete(Context ctx, FilterInfo filter)
			throws BOSException, EASBizException {
		IObjectPK[] pks = super._delete(ctx, filter);
		Set set = new HashSet();
		for (int i = 0; i < pks.length; i++) {
			set.add(pks[i].toString());
		}
		if(set.size()>1){
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems().add(
					new FilterItemInfo("headID", set, CompareType.INCLUDE));
			TemplatePlanIndexFactory.getLocalInstance(ctx).delete(filter2);
		}
		return pks;
	}
}