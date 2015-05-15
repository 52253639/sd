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
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class WorkFlowReportFacadeControllerBean extends AbstractWorkFlowReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.WorkFlowReportFacadeControllerBean");
    
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
	    initColoum(header,col,"name",250,false);
	    
	    Object obj[] = (Object[])params.getObject("person");
	    
	    Object[] one=new Object[5+obj.length*3];
	    one[0]="id";
	    one[1]="流程名称";
	    
		Object[] two=new Object[5+obj.length*3];
		two[0]="id";
		two[1]="流程名称";
		int k=2;
    	for(int i = 0; i < obj.length; i++){
    		PersonInfo info=(PersonInfo) obj[i];
    		initColoum(header,col,info.getId().toString()+"TIME",75,false);
    		initColoum(header,col,info.getId().toString()+"AMOUNT",75,false);
    		initColoum(header,col,info.getId().toString()+"AVERAGE",75,false);
    		
    		one[k]=info.getName();
    		one[k+1]=info.getName();
    		one[k+2]=info.getName();
    		
    		two[k]="时间";
    		two[k+1]="数量";
    		two[k+2]="平均时间";
    		
    		k=k+3;
    	}
    	initColoum(header,col,"TOTALTIME",75,false);
		initColoum(header,col,"TOTALAMOUNT",75,false);
		initColoum(header,col,"TOTALAVERAGE",75,false);
		
		one[5+obj.length*3-3]="合计";
		one[5+obj.length*3-2]="合计";
		one[5+obj.length*3-1]="合计";
		
		two[5+obj.length*3-3]="时间";
		two[5+obj.length*3-2]="数量";
		two[5+obj.length*3-1]="平均时间";
		
	    header.setLabels(new Object[][]{one,two
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	StringBuffer person =null;
	    if(params.getObject("person")!=null){
	    	person=new StringBuffer();
	    	Object[] personObject = (Object[])params.getObject("person");
        	for(int i=0;i<personObject.length;i++){
            	if(i==0){
            		person.append("'"+((PersonInfo)personObject[i]).getId().toString()+"'");
            	}else{
            		person.append(",'"+((PersonInfo)personObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select pro.FPROCDEFID id,fname_l2 name from T_WFR_ProcDef pro left join (select FPROCDEFID,max(FINNERVERSION) FINNERVERSION from T_WFR_ProcDef where FENABLE=1 group by FPROCDEFID) a on a.FPROCDEFID=pro.FPROCDEFID and a.FINNERVERSION=pro.FINNERVERSION");
    	sb.append(" where a.FPROCDEFID is not null ");
    	sb.append(" order by pro.FPACKAGENAME,pro.fname_l2");
    	
		RptRowSet rowSet = executeQuery(sb.toString(), null,ctx);
		params.setObject("rowset", rowSet);
		
		sb = new StringBuffer();
    	sb.append(" select detail.fprocdefID id,puser.fpersonId personId,sum(datediff(hh,detail.fcreatedtime,detail.fendtime)) workTime,count(*) workAmount from ");
    	sb.append(" T_WFR_AssignDetail detail left join T_PM_User puser on puser.fid=detail.FPERSONUSERID where 1=1");
    	if(person!=null){
    		sb.append(" and puser.fpersonId in("+person.toString()+")");
    	}else{
    		sb.append(" and puser.fpersonId ='null'");
    	}
    	if(fromDate!=null){
    		sb.append(" and detail.fcreatedTime>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and detail.fcreatedTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	sb.append(" group by detail.fprocdefID,puser.fpersonId");
    	rowSet = executeQuery(sb.toString(), null,ctx);
		params.setObject("detailRowset", rowSet);
		
		return params;
    }
}