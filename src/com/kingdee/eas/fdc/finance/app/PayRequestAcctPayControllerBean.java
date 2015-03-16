package com.kingdee.eas.fdc.finance.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.finance.PayRequestAcctPayInfo;
import com.kingdee.eas.fdc.finance.PayRequestAcctPayCollection;
import com.kingdee.eas.framework.app.CoreBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.metadata.entity.SorterItemCollection;

public class PayRequestAcctPayControllerBean extends AbstractPayRequestAcctPayControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.PayRequestAcctPayControllerBean");
    public Result save(Context ctx, CoreBaseCollection colls) throws BOSException, EASBizException {
    	Set payReqIdSet=new HashSet(); 
    	for(Iterator iter=colls.iterator();iter.hasNext();){
    		PayRequestAcctPayInfo info=(PayRequestAcctPayInfo)iter.next();
    		payReqIdSet.add(info.getPayRequestBill().getId().toString());
    	}
    	//delete
    	FilterInfo filter=new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("payRequestBill.id",payReqIdSet,CompareType.INCLUDE));
    	_delete(ctx, filter);
    	return super.save(ctx, colls);
    }
    
    protected void _audit(Context ctx, String payReqId) throws BOSException, EASBizException {
    	//审批后得算已申请付款金额
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.appendSql("select fcontractid,fcurprojectid,fbookeddate from t_con_payrequestbill where fid=?");
    	builder.addParam(payReqId);
    	IRowSet rowSet=builder.executeQuery();
    	String contractId=null;
    	String prjId=null;
    	Date date = null;
    	if(rowSet.size()==1){
    		try {
				rowSet.next();
				contractId = rowSet.getString("fcontractid");
				prjId = rowSet.getString("fcurprojectid");
				date = rowSet.getDate("fbookeddate");
			}catch (SQLException e) {
    			throw new BOSException(e);
    		}
    	}
		int year = date.getYear()+1900;
		int month = date.getMonth()+1==13?1:date.getMonth()+1;
    	builder.clear();
		builder.appendSql("select fcostaccountid,sum (acctpay.famount) amount from t_fnc_payrequestacctpay acctpay ");
		builder.appendSql("inner join t_con_payrequestbill payreq on payreq.fid=acctpay.fpayrequestbillid ");
		builder.appendSql("inner join t_fnc_fdcbudgetperiod period on acctpay.fperiodId = period.fid ");
		builder.appendSql("where payreq.fcontractid=? and acctpay.fcontractid=? and payreq.fstate=? and payreq.fid<>? and period.fyear=? and period.fmonth=?");
		builder.appendSql("group by acctpay.fcostaccountid" );
		builder.addParam(contractId);
		builder.addParam(contractId);
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		builder.addParam(payReqId);
		builder.addParam(new Integer(year));
		builder.addParam(new Integer(month));
		rowSet=builder.executeQuery();
		Map acctReqAllAmtMap=new HashMap();
		try {
			while (rowSet.next()) {
				acctReqAllAmtMap.put(rowSet.getString("fcostaccountid"), rowSet.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		if(acctReqAllAmtMap.size()>0){
			String sql="update t_fnc_payrequestacctpay set FLstAllAmount=? where fcostaccountid=? and FPayRequestBillId=?";
			List params=new ArrayList();
			for(Iterator iter=acctReqAllAmtMap.keySet().iterator();iter.hasNext();){
				String acctId=(String)iter.next();
				params.add(Arrays.asList(new Object[]{acctReqAllAmtMap.get(acctId),acctId,payReqId}));
			}
			builder.executeBatch(sql, params);
		}
    }
    protected void _unAudit(Context ctx, String payReqId) throws BOSException, EASBizException {
    	
    }
    
}