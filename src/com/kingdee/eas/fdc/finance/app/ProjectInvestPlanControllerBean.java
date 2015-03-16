package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.finance.IProjectInvestPlan;
import com.kingdee.eas.fdc.finance.MonthEntryFactory;
import com.kingdee.eas.fdc.finance.MonthEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectInvestPlanCollection;
import com.kingdee.eas.fdc.finance.ProjectInvestPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectInvestPlanFactory;
import com.kingdee.eas.fdc.finance.ProjectInvestPlanInfo;
import com.kingdee.eas.framework.CoreBaseCollection;

public class ProjectInvestPlanControllerBean extends AbstractProjectInvestPlanControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.ProjectInvestPlanControllerBean");
    protected IObjectPK _addnew(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	return super._addnew(ctx, model);
    }
    protected IObjectPK _save(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	return super._save(ctx, model);
    }
    
//    /**
//	 * 
//	 * 描述：检查编码重复<p>
//	 * @param ctx
//	 * @param billInfo
//	 * @throws BOSException
//	 * @throws EASBizException
//	 * @author xiedengyu 创建时间：2011-3-28
//	 *               <p>
//	 */
//	private void checkNumberDup(Context ctx, FDCBillInfo billInfo)
//			throws BOSException, EASBizException {
//		if(!isUseNumber()) return;
//		FilterInfo filter = new FilterInfo();
//
//		filter.getFilterItems().add(
//				new FilterItemInfo("number", billInfo.getNumber()));
//		filter.getFilterItems().add(
//				new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
//		
//		if (billInfo.getId() != null) {
//			filter.getFilterItems().add(
//					new FilterItemInfo("id", billInfo.getId().toString(),
//							CompareType.NOTEQUALS));
//		}
//
//		if (_exists(ctx, filter)) {
//			throw new ContractException(ContractException.NUMBER_DUP);
//		}
//	}
    protected void _setPublish(Context ctx,String id){
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_FNC_ProjectInvestPlan set fstate='"+FDCBillStateEnum.PUBLISH_VALUE+"' " +
				"where FID='"+id+"'");
		try {
			builder.execute();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//			ProjectInvestPlanInfo info=ip.getProjectInvestPlanInfo(new ObjectUuidPK(id));
//			info.setState(FDCBillStateEnum.PUBLISH);
//			SelectorItemCollection sic=new SelectorItemCollection();
//			sic.add("state");
//			ip.updatePartial(info, sic);
    }
    
    public Map _fetchInitData(Context ctx, Map paramMap) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
//    	return super.fetchInitData(ctx, paramMap);
    	
    	Map initData = new HashMap();
    	String billId = (String) paramMap.get("billId");
    	if(billId != null){
    		EntityViewInfo viewInfo = new EntityViewInfo();
        	SelectorItemCollection sic = new SelectorItemCollection();
        	sic.add(new SelectorItemInfo("entry.*"));
        	sic.add(new SelectorItemInfo("entry.monthEntry.*"));
        	sic.add(new SelectorItemInfo("entry.account.*"));
        	viewInfo.setSelector(sic);
        	FilterInfo  filter = new FilterInfo();
        	filter.getFilterItems().add(new FilterItemInfo("id",billId));
        	viewInfo.setFilter(filter);
        	SorterItemCollection sc = new SorterItemCollection();
        	sc.add(new SorterItemInfo("entry.account.longnumber"));
        	viewInfo.setSorter(sc);
        	ProjectInvestPlanCollection cols = ProjectInvestPlanFactory.getLocalInstance(ctx).getProjectInvestPlanCollection(viewInfo);
        	List entryList = null;
        	EntityViewInfo view = new EntityViewInfo();
        	FilterInfo f= new FilterInfo();
        	if(cols.size() == 1 ){
        		entryList = new ArrayList();
        		for(Iterator it = cols.get(0).getEntry().iterator();it.hasNext();){
            		ProjectInvestPlanEntryInfo info = (ProjectInvestPlanEntryInfo) it.next();
            		f = new FilterInfo();
            		f.getFilterItems().add(new FilterItemInfo("parent.id",info.getId().toString()));
            		view.setFilter(f);
            		CoreBaseCollection mCols = MonthEntryFactory.getLocalInstance(ctx).getCollection(view);
            		for(Iterator mit = mCols.iterator();mit.hasNext();){
            			info.getMonthEntry().add((MonthEntryInfo)mit.next());
            		}
            		entryList.add(info);
            	}
        	}
        	
        	
        	initData.put("entry",entryList);
    	}
    	return initData;
    }
    
    protected void checkBill(Context ctx, IObjectValue model)
		throws BOSException, EASBizException {

		FDCBillInfo FDCBillInfo = ((FDCBillInfo) model);
		
		checkNumberDup(ctx, FDCBillInfo);
		
		checkNameDup(ctx, FDCBillInfo);
		
	}

    protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
		throws BOSException, EASBizException {
	}
    
    public void audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	super.audit(ctx, billId);
    	setStateOnAudit(ctx,billId.toString(),"12REVISE");
    }
    
    public void unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	super.unAudit(ctx, billId);
    	setStateOnAudit(ctx,billId.toString(),"0REVISING");
    }
    
    /**
     * 修改旧版本状态
     * @throws BOSException 
     * @throws EASBizException 
     */
    public void setStateOnAudit(Context ctx,String id,String state) throws BOSException, EASBizException{
    	IProjectInvestPlan ip=ProjectInvestPlanFactory.getLocalInstance(ctx);
		ProjectInvestPlanInfo info=ip.getProjectInvestPlanInfo(new ObjectUuidPK(id));
		BigDecimal versionNumber=info.getVersionNumber();
		String number =info.getNumber();
		String project=info.getProject().getId().toString();
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_FNC_ProjectInvestPlan set fstate='"+state+"' " +
				"where FProjectID='"+project+"' and fnumber='"+number+"' " +
				"and FVersionNumber="+versionNumber.subtract(new BigDecimal("0.1"))+"");
		builder.execute();
    }
    protected void trimName(FDCBillInfo billInfo) {
		if(billInfo.getName() != null) {
			billInfo.setName(billInfo.getName().trim());
		}
	}
}