package com.kingdee.eas.fdc.finance.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.ctrl.analysis.olap.Filter;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.BillBaseControllerBean;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.ConPayPlanSplitFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.BillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.finance.ContractPayPlanInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class ContractPayPlanControllerBean extends AbstractContractPayPlanControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.ContractPayPlanControllerBean");


    protected boolean _submitCol(Context ctx, String contractId, IObjectCollection planCol) throws BOSException, EASBizException {
		
    	boolean hasUse=FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_ACCTBUDGET);
    	if(hasUse){
    		FilterInfo filter=new FilterInfo();
    		filter.appendFilterItem("bill.contractId", contractId);
    		ConPayPlanSplitFactory.getLocalInstance(ctx).delete(filter);
    	}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractId.id", contractId));
		this.delete(ctx,filter);
		ContractPayPlanCollection col = (ContractPayPlanCollection)planCol;
		for (int i = 0; i < col.size(); i++) {
			ContractPayPlanInfo payPlan = col.get(i);
			IObjectPK pk = submit(ctx,payPlan);
			if(hasUse){
				ConPayPlanSplitFactory.getLocalInstance(ctx).autoSplit(pk.toString());
			}
		}
    	return true;
	}
    
    protected IObjectPK _addnew(Context ctx , IObjectValue model) throws BOSException , EASBizException
	{
    	ContractPayPlanInfo fDCBillInfo = ((ContractPayPlanInfo) model);
		
		//处理原币
		dealAmount(ctx, fDCBillInfo);
		
    	return super._addnew(ctx,model);
	}
    
	//处理原币
	protected void dealAmount(Context ctx, ContractPayPlanInfo fDCBillInfo) throws EASBizException, BOSException {
		if(fDCBillInfo.getPayOriAmount()==null ){
			fDCBillInfo.setPayOriAmount(fDCBillInfo.getPayAmount());
		}
	}
    
}