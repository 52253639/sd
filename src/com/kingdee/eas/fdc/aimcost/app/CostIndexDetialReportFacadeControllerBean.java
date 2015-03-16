package com.kingdee.eas.fdc.aimcost.app;

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
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class CostIndexDetialReportFacadeControllerBean extends AbstractCostIndexDetialReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.CostIndexDetialReportFacadeControllerBean");
    
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
    	CostIndexConfigInfo config=(CostIndexConfigInfo) params.getObject("config");
	    RptTableHeader header = new RptTableHeader();
	    RptTableColumn col = null;
	    
	    String[] key=new String[config.getEntry().size()+6];
	    initColoum(header,col,"fid",100,true);
	    initColoum(header,col,"proName",100,false);
	    initColoum(header,col,"contractPhase",100,false);
	    initColoum(header,col,"conNumber",100,false);
	    initColoum(header,col,"conName",100,false);
	    initColoum(header,col,"conAuditTime",100,false);
	    key[0]="id";
	    key[1]="项目名称";
	    key[2]="合同阶段";
	    key[3]="合同编码";
	    key[4]="合同名称";
	    key[5]="合同审批日期";
	    for(int i=0;i<config.getEntry().size();i++){
	    	CostIndexConfigEntryInfo configEntry=config.getEntry().get(i);
	    	initColoum(header,col,"FField"+i,100,configEntry.isIsHide());
	    	key[i+6]=configEntry.getFieldName();
	    }
	    header.setLabels(new Object[][]{
	    		key
	    },true);
	    params.setObject("header", header);
	    return params;
	}
	protected RptParams _query(Context ctx, RptParams params)throws BOSException, EASBizException {
		CostIndexConfigInfo config=(CostIndexConfigInfo) params.getObject("config");
		String curProject =(String)params.getObject("curProject");
		String where =(String)params.getObject("where");
		Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
    	String selectStr="select entry.fid id,project.fname_l2 proName,costIndex.fContractPhase contractPhase,contract.fnumber conNumber,contract.fname conName,contract.fauditTime conAuditTime,";
    	for(int i=0;i<config.getEntry().size();i++){
	    	if(i==config.getEntry().size()-1){
	    		selectStr=selectStr+"FField"+i+" from ";
	    	}else{
	    		selectStr=selectStr+"FField"+i+" , ";
	    	}
	    }
    	StringBuffer sb = new StringBuffer();
    	sb.append(selectStr);
    	sb.append("T_AIM_CostIndexEntry entry left join T_AIM_CostIndex costIndex on costIndex.fid=entry.fheadId left join t_con_contractBill contract on contract.fid=costIndex.fContractId left join t_fdc_curProject project on project.fid=contract.fcurProjectId where FIsLatest=1 and fconfigId='"+config.getId().toString()+"' ");
    	if(where!=null){
    		sb.append(" and"+where);
    	}
    	if(fromDate!=null){
    		sb.append(" and contract.fauditTime>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and contract.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(curProject!=null){
			sb.append(" and project.fid in("+curProject+")");
		}
		sb.append(" order by project.flongNumber,contract.fnumber,costIndex.fnumber,entry.fseq");
		RptRowSet rowSet = executeQuery(sb.toString(), null, ctx);
		params.setObject("rowset", rowSet);
		return params;
	}
    
    
}