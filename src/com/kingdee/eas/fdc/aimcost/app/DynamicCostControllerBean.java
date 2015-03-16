package com.kingdee.eas.fdc.aimcost.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.framework.app.BillBaseControllerBean;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryFactory;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.ProjectCostChangeLogFactory;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.BillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;

public class DynamicCostControllerBean extends AbstractDynamicCostControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.DynamicCostControllerBean");
    
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	DynamicCostInfo info = (DynamicCostInfo)model;
    	if(info!=null&&info.getAccount()!=null){
    		//避免出现中断,先删除之
    		FilterInfo filter=new FilterInfo();
    		filter.appendFilterItem("account.id", info.getAccount().getId().toString());
    		DynamicCostFactory.getLocalInstance(ctx).delete(filter);
    	}
    	String prjId=null;
    	if(info != null && info.getAccount()!= null){
    		if(info.getAccount().getCurProject()!=null){
    			prjId= info.getAccount().getCurProject().getId().toString();
    		}else{
    			SelectorItemCollection sic = new SelectorItemCollection();
        		sic.add("curProject.id");
        		CostAccountInfo acct = CostAccountFactory.getLocalInstance(ctx).getCostAccountInfo(new ObjectUuidPK(info.getAccount().getId()), sic);
        		if(acct.getCurProject()!=null)
        			prjId=acct.getCurProject().getId().toString();
    		}
    			
	    	 
	    	SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("fullOrgUnit.id");
			String companyID = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(
							new ObjectUuidPK(prjId),
							selector).getFullOrgUnit().getId().toString();
			if(FDCUtils.IsInCorporation(ctx, companyID)){
				PeriodInfo currenctCostPeriod = FDCUtils.getCurrentPeriod(ctx,
						prjId, true);
//				以前取当前期间为空为抛异常，现在基类把这个异常取消了。
				if(currenctCostPeriod!=null){
					info.setPeriod(currenctCostPeriod);
			    	for(int i=0,size=info.getAdjustEntrys().size();i<size;i++){
			    		AdjustRecordEntryInfo entryInfo = info.getAdjustEntrys().get(i);
			    		if(entryInfo.getPeriod()==null)
			    			entryInfo.setPeriod(currenctCostPeriod);
			    	}
				}
			}
    	}
    	if(prjId!=null){
    		Set prjSet=new HashSet();
    		prjSet.add(prjId);
    		ProjectCostChangeLogFactory.getLocalInstance(ctx).insertLog(prjSet);
    	}
    	
    	return super._submit(ctx, info);
    }
}