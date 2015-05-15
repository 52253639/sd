package com.kingdee.eas.fdc.contract.app;

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
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class WorkFlowDetailReportFacadeControllerBean extends AbstractWorkFlowDetailReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.WorkFlowDetailReportFacadeControllerBean");
    
    protected RptParams _init(Context ctx, RptParams params)throws BOSException, EASBizException{
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
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"bizObjID",100,true);
	    initColoum(header,col,"name",250,false);
	    initColoum(header,col,"subject",250,false);
	    initColoum(header,col,"createdTime",150,false);
	    initColoum(header,col,"endTime",150,false);
	    initColoum(header,col,"daySub",75,false);
	    initColoum(header,col,"hourSub",75,false);
	    initColoum(header,col,"person",100,false);
		
	    header.setLabels(new Object[][]{new String[]{"id","bizObjID","流程名称","流程主题","创建时间","处理完成时间","天差","	时间差","处理人"}},true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	String person=(String) params.getObject("person");
    	String procdefId=(String) params.getObject("procdefId");
    	StringBuffer personStr =null;
	    if(params.getObject("personObj")!=null){
	    	personStr=new StringBuffer();
	    	Object[] personObject = (Object[])params.getObject("personObj");
        	for(int i=0;i<personObject.length;i++){
            	if(i==0){
            		personStr.append("'"+((PersonInfo)personObject[i]).getId().toString()+"'");
            	}else{
            		personStr.append(",'"+((PersonInfo)personObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
		StringBuffer sb = new StringBuffer();
    	sb.append(" select detail.fprocdefID id,detail.FBIZOBJID bizObjID,detail.FPROCDEFNAME_l2 name,detail.FSUBJECT_l2 subject,detail.FCREATEDTIME createdTime,detail.FENDTIME endTime,datediff(day,detail.fcreatedtime,detail.fendtime) daySub,datediff(hh,detail.fcreatedtime,detail.fendtime) hourSub, puser.fname_l2 person from ");
    	sb.append(" T_WFR_AssignDetail detail left join T_PM_User puser on puser.fid=detail.FPERSONUSERID where 1=1");
    	if(person!=null){
    		sb.append(" and puser.fpersonId ='"+person+"'");
    	}else if(personStr!=null){
    		sb.append(" and puser.fpersonId in("+personStr+")");
    	}else{
    		sb.append(" and puser.fpersonId ='null'");
    	}
    	if(fromDate!=null){
    		sb.append(" and detail.fcreatedTime>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and detail.fcreatedTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(procdefId!=null){
			sb.append(" and detail.fprocdefID ='"+procdefId+"'");
		}
    	RptRowSet rowSet = executeQuery(sb.toString(), null,ctx);
		params.setObject("rowset", rowSet);
		
		return params;
    }
}