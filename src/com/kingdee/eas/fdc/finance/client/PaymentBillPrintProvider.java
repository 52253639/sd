package com.kingdee.eas.fdc.finance.client;

import java.math.BigDecimal;
import java.util.Iterator;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class PaymentBillPrintProvider  extends FDCBillDataProvider{
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
		return super.getMainBillRowSet(ds);
	}

	private IMetaDataPK attchQuery;

	public PaymentBillPrintProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds){
		if ("ATTCH".equals(ds.getID().toUpperCase()))//假设主数据源名称为mainbill
        {
            //返加主数据源的结果集
        	return  getAttchRowSet(ds);
        }
		if ("PayRequestBillPrintQuery".equals(ds.getID())) {
			return getRequestBillRowSet(ds);
		}
		return super.getMainBillRowSet(ds);
	}
	
	public IRowSet getRequestBillRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory
					.getRemoteInstance(new MetaDataPK(
							"com.kingdee.eas.fdc.contract.app.PayRequestBillPrintQuery"));
			exec.option().isAutoTranslateEnum = true;

			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("fdcPayReqID");
			PaymentBillInfo pay = PaymentBillFactory.getRemoteInstance()
					.getPaymentBillInfo(new ObjectUuidPK(billId), sic);

			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", pay.getFdcPayReqID(),
							CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			iRowSet = exec.executeQuery();
			iRowSet.beforeFirst();
			
			//增加本申请单已付金额字段
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("fdcPayReqID", pay.getFdcPayReqID()));
			filter.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.PAYED));
			ev.setFilter(filter);
			sic = new SelectorItemCollection();
			sic.add("ID");
			sic.add("amount");
			PaymentBillCollection payCol = PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(ev);
			BigDecimal payedAmt = FDCNumberHelper.ZERO;
			if (payCol != null) {
				for (Iterator it = payCol.iterator(); it.hasNext();) {
					PaymentBillInfo payInfo = (PaymentBillInfo)it.next();
					payedAmt = FDCNumberHelper.add(payedAmt, payInfo.getAmount());
				}
			}
			while(iRowSet.next()){
				iRowSet.updateBigDecimal("payedAmt", payedAmt);;
			}
			iRowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iRowSet;
	}
	
	public IRowSet getAttchRowSet(BOSQueryDataSource ds){
		IRowSet iRowSet = null;
        try {
        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(attchQuery);
            exec.option().isAutoTranslateEnum= true;
            EntityViewInfo ev = new EntityViewInfo();
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo("boAttchAsso.boID", billId, CompareType.EQUALS));
            ev.setFilter(filter);
            exec.setObjectView(ev);
            iRowSet = exec.executeQuery();	
            iRowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iRowSet;
	}
}
