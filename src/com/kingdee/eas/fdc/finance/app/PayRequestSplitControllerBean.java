package com.kingdee.eas.fdc.finance.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSplitBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.PayRequestSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PayRequestSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PayRequestSplitFactory;
import com.kingdee.eas.fdc.finance.PayRequestSplitInfo;

public class PayRequestSplitControllerBean extends AbstractPayRequestSplitControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.PayRequestSplitControllerBean");
    protected void _autoSplit(Context ctx, String billId) throws BOSException, EASBizException {
    	if(billId==null){
    		return;
    	}
    	FilterInfo filter=new FilterInfo();
    	filter.appendFilterItem("bill.id", billId);
    	PayRequestSplitFactory.getLocalInstance(ctx).delete(filter);
    	
    	SelectorItemCollection selector=new SelectorItemCollection();
    	selector.add("id");
    	selector.add("amount");
    	selector.add("contractId");
    	final PayRequestBillInfo payrequestbillinfo = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(billId), selector);
    	PayRequestSplitInfo info=new PayRequestSplitInfo();
    	Map dataMap=new HashMap();
    	dataMap.put("amount", payrequestbillinfo.getAmount());
    	dataMap.put("contractId", payrequestbillinfo.getContractId());
    	FDCSplitBillInfo fdcSplitInfo=FDCBillFacadeFactory.getLocalInstance(ctx).autoPropSplit("contract", dataMap);
    	if(fdcSplitInfo==null){
    		return;
    	}
    	info.putAll(fdcSplitInfo);
    	info.setBill(payrequestbillinfo);
    	PayRequestSplitEntryCollection entrys = new PayRequestSplitEntryCollection();
    	for(Iterator iter=((AbstractObjectCollection)fdcSplitInfo.get("entrys")).iterator();iter.hasNext();){
    		FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)iter.next();
    		PayRequestSplitEntryInfo planEntry=new PayRequestSplitEntryInfo();
    		planEntry.putAll(entry);
    		planEntry.setCostBillId(payrequestbillinfo.getId());
    		planEntry.setSplitBillId(planEntry.getCostBillId());
    		planEntry.setParent(info);
    		entrys.add(planEntry);
    	}
    	
    	info.put("entrys", entrys);
    	IObjectPK pk = _addnew(ctx, info);
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	builder.appendSql("update T_CON_PayRequestBill set fpayrequestsplitid=? where fid=?");
    	builder.addParam(pk.toString());
    	builder.addParam(billId);
    	builder.execute();
    }
    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	return super._save(ctx, model);
    }
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
    	super._delete(ctx, pk);
    }
}