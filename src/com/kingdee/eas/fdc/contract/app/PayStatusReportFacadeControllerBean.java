package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.BgItemInfo;

public class PayStatusReportFacadeControllerBean extends AbstractPayStatusReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.PayStatusReportFacadeControllerBean");
    
    protected RptParams _init(Context ctx, RptParams params)throws BOSException, EASBizException
	{
	    RptParams pp = new RptParams();
	    return pp;
	}
    private void initColoum(RptTableHeader header,RptTableColumn col,String name,int width,boolean isHide){
    	col= new RptTableColumn(name);
    	col.setWidth(width);
	    col.setHided(isHide);
	    header.addColumn(col);
    }
    protected RptParams _createTempTable(Context ctx, RptParams params)    throws BOSException, EASBizException
	{
	    RptTableHeader header = new RptTableHeader();
	    RptTableColumn col = null;
	    initColoum(header,col,"buId",100,true);
	    initColoum(header,col,"bgItemId",100,true);
	    initColoum(header,col,"curProjectId",100,true);
	    initColoum(header,col,"costedCompanyId",100,true);
	    initColoum(header,col,"costedDeptId",100,true);
	    initColoum(header,col,"orgUnit",100,false);
	    initColoum(header,col,"actPayCompany",150,false);
	    initColoum(header,col,"costedCompany",100,false);
	    initColoum(header,col,"costedDept",150,false);
	    initColoum(header,col,"bgItem",180,false);
	    initColoum(header,col,"state",80,false);
	    initColoum(header,col,"amount",100,false);
	    header.setLabels(new Object[][]{
	    		{
	    			"buId","bgItemId","curProjectId","costedCompanyId","costedDeptId","地区","实际付款公司","预算承担公司/部门","预算承担部门","预算项目","流程状态","金额"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
		RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		params.setObject("rowset", rowSet);
		return params;
    }
    protected String getSql(Context ctx,RptParams params){
    	StringBuffer sb=new StringBuffer();
    	String orgUnit = (String) params.getObject("orgUnit");
    	Date fromDate=(Date)params.getObject("fromDate");
    	Date toDate=(Date)params.getObject("toDate");
    	List state=new ArrayList();
    	if(params.getBoolean("isSaved")){
    		state.add("1SAVED");
    	}
    	if(params.getBoolean("isSubmitted")){
    		state.add("2SUBMITTED");
    	}
    	if(params.getBoolean("isAuditting")){
    		state.add("3AUDITTING");
    	}
    	if(params.getBoolean("isAuditted")){
    		state.add("4AUDITTED");
    	}
    	if(params.getBoolean("isPayed")){
    		state.add("isPayed");
    	}
    	String stateStr="";
    	for(int i=0;i<state.size();i++){
    		if(i==0){
    			stateStr=stateStr+"'"+state.get(i).toString()+"'";
        	}else{
        		stateStr=stateStr+",'"+state.get(i).toString()+"'";
        	}
    	}
    	String bgItemId=null;
    	if(params.getObject("bgItem")!=null){
    		Object[] bgItemObj=((Object[])params.getObject("bgItem"));
    		bgItemId="";
    		for(int i=0;i<bgItemObj.length;i++){
	    		BgItemInfo bgItemInfo=(BgItemInfo)bgItemObj[i];
	    		if(bgItemInfo==null) continue;
            	if(i==0){
            		bgItemId=bgItemId+"'"+bgItemInfo.getId().toString()+"'";
            	}else{
            		bgItemId=bgItemId+",'"+bgItemInfo.getId().toString()+"'";
            	}
            }
    	}
    	
    	sb.append(" select t.buId,t.bgItemId,t.curProjectId,t.costedCompanyId,t.costedDeptId,t.orgUnit,t.actPayCompany,t.costedCompany,t.costedDept,t.bgItem,t.state,t.amount from(");
    	sb.append(" select bu.fid buId,bgItem.fid bgItemId,bu.flongNumber buLongNumber,company.flongNumber comLongNumber,costCenter.flongNumber costLongNumber,bgItem.flongNumber bgLongNumber,curProject.fid curProjectId,company.fid costedCompanyId,costCenter.fid costedDeptId,bu.fdisplayName_l2 orgUnit,bu.fname_l2 actPayCompany,company.fname_l2 costedCompany,costCenter.fname_l2 costedDept,bgItem.fdisPlayName_l2 bgItem,payReq.fstate state,sum(isnull(bgEntry.frequestAmount,0))amount from t_con_payRequestBill payReq left join t_fdc_curProject curProject on curProject.fid=payReq.fcurProjectId");
    	sb.append(" left join t_org_baseUnit bu on bu.fid=curProject.ffullOrgUnit left join t_org_company company on company.fid=payReq.fcostedCompanyId left join t_org_costCenter costCenter on costCenter.fid=payReq.fcostedDeptId");
    	sb.append(" left join t_con_payRequestBillBgEntry bgEntry on bgEntry.fheadId=payReq.fid left join t_bg_bgItem bgItem on bgItem.fid=bgEntry.fbgItemId");
    	sb.append(" where bgItem.fid is not null and payReq.fstate!='4AUDITTED' and curProject.fid is not null");
    	if(orgUnit!=null&&!"".equals(orgUnit.trim())){
    		sb.append(" and bu.flongNumber like '"+orgUnit+"%'");
    	}else{
    		sb.append(" and bu.flongNumber ='null'");
    	}
    	if(fromDate!=null){
			sb.append(" and payReq.fbookedDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and payReq.fbookedDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	if(bgItemId!=null&&!"".equals(bgItemId)){
    		sb.append(" and bgItem.fid in("+bgItemId+")");
    	}
    	if(stateStr!=null&&!"".equals(stateStr)){
    		sb.append(" and payReq.fstate in("+stateStr+")");
    	}
    	sb.append(" group by bu.fid,bgItem.fid,bu.flongNumber,company.flongNumber,costCenter.flongNumber,bgItem.flongNumber,curProject.fid,company.fid,costCenter.fid,bu.fdisplayName_l2,bu.fname_l2,company.fname_l2,costCenter.fname_l2,bgItem.fdisPlayName_l2,payReq.fstate");
    	
    	sb.append(" union all select bu.fid buId,bgItem.fid bgItemId,bu.flongNumber buLongNumber,company.flongNumber comLongNumber,costCenter.flongNumber costLongNumber,bgItem.flongNumber bgLongNumber,curProject.fid curProjectId,company.fid costedCompanyId,costCenter.fid costedDeptId,bu.fdisplayName_l2 orgUnit,bu.fname_l2 actPayCompany,company.fname_l2 costedCompany,costCenter.fname_l2 costedDept,bgItem.fdisPlayName_l2 bgItem,payReq.fstate state,sum(isnull(bgEntry.frequestAmount,0))amount from t_con_payRequestBill payReq left join t_fdc_curProject curProject on curProject.fid=payReq.fcurProjectId");
    	sb.append(" left join t_org_baseUnit bu on bu.fid=curProject.ffullOrgUnit left join t_org_company company on company.fid=payReq.fcostedCompanyId left join t_org_costCenter costCenter on costCenter.fid=payReq.fcostedDeptId");
    	sb.append(" left join t_con_payRequestBillBgEntry bgEntry on bgEntry.fheadId=payReq.fid left join t_bg_bgItem bgItem on bgItem.fid=bgEntry.fbgItemId");
    	sb.append(" where bgItem.fid is not null and payReq.fpaydate is null and payReq.fstate='4AUDITTED' and curProject.fid is not null");
    	if(orgUnit!=null&&!"".equals(orgUnit.trim())){
    		sb.append(" and bu.flongNumber like '"+orgUnit+"%'");
    	}else{
    		sb.append(" and bu.flongNumber ='null'");
    	}
    	if(fromDate!=null){
			sb.append(" and payReq.fauditTime>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and payReq.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(bgItemId!=null&&!"".equals(bgItemId)){
    		sb.append(" and bgItem.fid in("+bgItemId+")");
    	}
		if(stateStr!=null&&!"".equals(stateStr)){
    		sb.append(" and payReq.fstate in("+stateStr+")");
    	}
    	sb.append(" group by bu.fid,bgItem.fid,bu.flongNumber,company.flongNumber,costCenter.flongNumber,bgItem.flongNumber,curProject.fid,company.fid,costCenter.fid,bu.fdisplayName_l2,bu.fname_l2,company.fname_l2,costCenter.fname_l2,bgItem.fdisPlayName_l2,payReq.fstate");
    	
    	if(params.getBoolean("isPayed")){
    		sb.append(" union all select bu.fid buId,bgItem.fid bgItemId,bu.flongNumber buLongNumber,company.flongNumber comLongNumber,costCenter.flongNumber costLongNumber,bgItem.flongNumber bgLongNumber,curProject.fid curProjectId,company.fid costedCompanyId,costCenter.fid costedDeptId,bu.fdisplayName_l2 orgUnit,bu.fname_l2 actPayCompany,company.fname_l2 costedCompany,costCenter.fname_l2 costedDept,bgItem.fdisPlayName_l2 bgItem,'5PAY' state,sum(isnull(bgEntry.factPayAmount,0))amount from t_con_payRequestBill payReq left join t_fdc_curProject curProject on curProject.fid=payReq.fcurProjectId");
        	sb.append(" left join t_org_baseUnit bu on bu.fid=curProject.ffullOrgUnit left join t_org_company company on company.fid=payReq.fcostedCompanyId left join t_org_costCenter costCenter on costCenter.fid=payReq.fcostedDeptId");
        	sb.append(" left join t_con_payRequestBillBgEntry bgEntry on bgEntry.fheadId=payReq.fid left join t_bg_bgItem bgItem on bgItem.fid=bgEntry.fbgItemId");
        	sb.append(" where bgItem.fid is not null and payReq.fpaydate is not null and payReq.fstate='4AUDITTED'and curProject.fid is not null");
        	if(orgUnit!=null&&!"".equals(orgUnit.trim())){
        		sb.append(" and bu.flongNumber like '"+orgUnit+"%'");
        	}else{
        		sb.append(" and bu.flongNumber ='null'");
        	}
        	if(fromDate!=null){
    			sb.append(" and payReq.fpaydate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and payReq.fpaydate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    		if(bgItemId!=null&&!"".equals(bgItemId)){
        		sb.append(" and bgItem.fid in("+bgItemId+")");
        	}
        	sb.append(" group by bu.fid,bgItem.fid,bu.flongNumber,company.flongNumber,costCenter.flongNumber,bgItem.flongNumber,curProject.fid,company.fid,costCenter.fid,bu.fdisplayName_l2,bu.fname_l2,company.fname_l2,costCenter.fname_l2,bgItem.fdisPlayName_l2,payReq.fstate");
    	}
    	sb.append(" )t order by t.buLongNumber,t.comLongNumber,t.costLongNumber,t.bgLongNumber,t.state desc");
    	return sb.toString();
    }
}