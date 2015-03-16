package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.service.formula.builder.bosmeta.BOSType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.FDCPaymentBill;
import com.kingdee.eas.fdc.finance.FDCPaymentBillCollection;
import com.kingdee.eas.fdc.finance.FDCPaymentBillFactory;
import com.kingdee.eas.fdc.finance.FDCPaymentBillHelper;
import com.kingdee.eas.fdc.finance.FDCPaymentBillInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.UuidException;

public class FDCPaymentBillControllerBean extends AbstractFDCPaymentBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.FDCPaymentBillControllerBean");
    
    
    protected void _setRespite(Context ctx, BOSUuid billId, boolean value) throws BOSException, EASBizException {
    	
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", billId));
		view.setFilter(filter);
		//如果FDCPaymentBill中不存在该付款单对应的记录，则新增一条
		FDCPaymentBillInfo info = null;
    	if (!_exists(ctx, filter)) {
    		PaymentBillInfo paymentBillInfo = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillInfo(new ObjectUuidPK(billId));
    		info = new FDCPaymentBillInfo();
    		info.setCreateTime(paymentBillInfo.getCreateTime());
    		info.setCreator(paymentBillInfo.getCreator());
    		info.setLastUpdateTime(paymentBillInfo.getLastUpdateTime());
    		info.setLastUpdateUser(paymentBillInfo.getLastUpdateUser());
    		info.setPaymentBill(paymentBillInfo);
    		info.setIsRespite(value);
    		FDCPaymentBillFactory.getLocalInstance(ctx).addnew(info);
    	} else {
    		FDCPaymentBillCollection col = FDCPaymentBillFactory.getLocalInstance(ctx).getFDCPaymentBillCollection(view);
    		if (col != null && col.size() > 0) {
    			info = col.get(0);
    		}
    		if (info != null) {
    			super._setRespite(ctx, info.getId(), value);
    		}
    	}
	}

	protected void _setRespite(Context ctx, List ids, boolean value) throws BOSException, EASBizException {
		super._setRespite(ctx, ids, value);
	}

	protected HashMap _getIsRespiteByPaymentBillIds(Context ctx, HashSet idSet) throws BOSException, EASBizException {
		HashMap resultMap = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", idSet, CompareType.INCLUDE));
		FDCPaymentBillCollection col = FDCPaymentBillFactory.getLocalInstance(ctx).getFDCPaymentBillCollection(view);
		FDCPaymentBillInfo info = null;
		if (col != null && col.size() > 0) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				info = (FDCPaymentBillInfo)it.next();
				resultMap.put(info.getPaymentBill().getId().toString(), Boolean.valueOf(info.isIsRespite()));
			}
		}
		
		String paymentID;
		for (Iterator it = idSet.iterator(); it.hasNext();) {
			paymentID = (String)it.next();
			if (!resultMap.containsKey(paymentID)) {
				resultMap.put(paymentID, Boolean.FALSE);
			}
		}
		
		return resultMap;
	}

	protected void _updatePeriodAfterAudit(Context ctx, List idList) throws BOSException, EASBizException {
		
		for (Iterator it = idList.iterator(); it.hasNext();) {
			String paymentBillId = (String)it.next();
			updatePeriod(ctx, paymentBillId);
		}
		
	}
	
	/**
	 * 根据当前项目成本期间更新暂缓单据的业务日期和确认期间
	 * @param ctx
	 * @param billId
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void updatePeriod(Context ctx, String billId) throws EASBizException, BOSException {
		
		FDCPaymentBillInfo info = null;
		//不处理非暂缓的单据
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", billId));
		view.setFilter(filter);
		if (!FDCPaymentBillFactory.getLocalInstance(ctx).exists(filter)) {
			return;
		} else {
			FDCPaymentBillCollection col = FDCPaymentBillFactory.getLocalInstance(ctx).getFDCPaymentBillCollection(view);
			if (col != null && col.size() > 0) {
				info = col.get(0);
				if (!info.isIsRespite()) {
					return;
				}
			} else {
				return;
			}
		}
		
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("fdcPayReqID");
		PaymentBillInfo PaybillInfo = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillInfo(new ObjectUuidPK(billId), selectors);
		
		selectors = new SelectorItemCollection();
		selectors.add("curProject.id");
		selectors.add("curProject.fullOrgUnit.id");
		selectors.add("bookedDate");
		selectors.add("period.*");
		PayRequestBillInfo reqBillInfo = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(PaybillInfo.getFdcPayReqID()), selectors);
		
		String companyID = reqBillInfo.getCurProject().getFullOrgUnit().getId().toString();
		Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyID);
		boolean isInCore = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INCORPORATION);
		if(isInCore){
			//启用月结统一按以下逻辑处理
			String prjId = reqBillInfo.getCurProject().getId().toString();
			//财务期间
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, false);
			PeriodInfo billPeriod = reqBillInfo.getPeriod();
			PeriodInfo shouldPeriod = null;//最终所在期间
			Date bookedDate = DateTimeUtils.truncateDate(reqBillInfo.getBookedDate());
			
			if(finPeriod==null){
				throw new EASBizException(new NumericExceptionSubItem("100","单据所对应的组织没有当前时间的期间，请先设置！"));
			}
			/***************
			 * （1）当工程量确认单上的“业务日期”和“业务期间”大于工程项目成本财务“当前期间”时，“业务期间”不变
			 * （2）当工程量确认单上的“业务日期”和“业务期间”小于等于工程项目成本财务“当前期间”时，“业务期间”更新为工程项目成本财务“当前期间”
			 *	
			 *	原理与拆分保存时相同，期间老出问题
			 */
			if (billPeriod != null && billPeriod.getNumber() > finPeriod.getNumber()) {
				if (bookedDate.before(billPeriod.getBeginDate())) {
					bookedDate = billPeriod.getBeginDate();
				} else if (bookedDate.after(billPeriod.getEndDate())) {
					bookedDate = billPeriod.getEndDate();
				}
				shouldPeriod = billPeriod;
			} else if (finPeriod != null) {
				if (bookedDate.before(finPeriod.getBeginDate())) {
					bookedDate = finPeriod.getBeginDate();
				} else if (bookedDate.after(finPeriod.getEndDate())) {
					bookedDate = finPeriod.getEndDate();
				}
				shouldPeriod = finPeriod;
			}
			
			//更新付款申请单的业务日期，申请期间
			selectors = new SelectorItemCollection();
			selectors.add("period");
			selectors.add("bookedDate");
			selectors.add("isRespite");
			reqBillInfo.setBookedDate(bookedDate);
			reqBillInfo.setPeriod(shouldPeriod);
			reqBillInfo.setIsRespite(false);
			PayRequestBillFactory.getLocalInstance(ctx).updatePartial(reqBillInfo, selectors);		

			//更新付款单的暂缓状态
			if (info != null) {
				selectors = new SelectorItemCollection();
				selectors.add("isRespite");
				info.setIsRespite(false);
				_updatePartial(ctx, info, selectors);
			}
		}
	}

	protected void _updateInvoiceAmt(Context ctx, IObjectValue fdcPayment, PaymentBillInfo paymentBill) throws BOSException, EASBizException {
		FDCPaymentBillHelper.handleFDCPaymentBillInvoice(ctx, fdcPayment, paymentBill);
		
		FDCPaymentBillHelper.recountInvoiceAmt(ctx, paymentBill);
	}
}