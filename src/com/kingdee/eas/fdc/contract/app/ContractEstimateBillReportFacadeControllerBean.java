package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class ContractEstimateBillReportFacadeControllerBean extends AbstractContractEstimateBillReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractEstimateBillReportFacadeControllerBean");
    
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
	    initColoum(header,col,"contractType",100,true);
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"number",120,false);
	    initColoum(header,col,"name",200,false);
	    initColoum(header,col,"supplier",200,false);
	    initColoum(header,col,"contractAmount",100,false);
	    initColoum(header,col,"ecNumber",100,false);
	    initColoum(header,col,"reason",100,false);
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"changeDate",100,false);
	    header.setLabels(new Object[][]{
	    		{
	    			"合同类型","id","合同编码","合同名称","签约单位","合同金额","预估合同变动","预估合同变动","预估合同变动","预估合同变动"
	    		},
	    		{
	    			"合同类型","id","合同编码","合同名称","签约单位","合同金额","预估合同变动编码","预估内容","预估金额","预估日期"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		Map value=new HashMap();
		while(rowSet.next()){
			String contractTypeName=rowSet.getString("contractType");
			if(value.containsKey(contractTypeName)){
				((List)value.get(contractTypeName)).add(rowSet.toRowArray());
			}else{
				List list=new ArrayList();
				list.add(rowSet.toRowArray());
				value.put(contractTypeName, list);
			}
		}
		Map detailValue=new HashMap();
		String curProject=(String) params.getObject("curProject");
		CurProjectInfo curProjectinfo=(CurProjectInfo) params.getObject("curProjectInfo");
    	String number=(String)params.getObject("number");
    	Date auditDate=(Date)params.getObject("auditDate");
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select '' contractType,ec.fid id,'' number,'' name,'' supplier,'' contractAmount,'' ecNumber,entry.freason reason,entry.famount amount,entry.FDate changeDate");
    	sb.append("	from T_CON_ContractEstimateChange ec left join T_CON_ConEstimateChangeEntry entry on entry.FParentID=ec.fid left join T_CON_ProgrammingContract pc on pc.fid=ec.fProgrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join t_con_contractBill contract on contract.fProgrammingContract=pc.fid left join t_bd_supplier supplier on supplier.fid=contract.fpartBId");
    	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId");
    	if(auditDate!=null){
    		sb.append(" left join (select max(fauditTime) fauditTime,fprogrammingContractID from T_CON_ContractEstimateChange where fstate='4AUDITTED' and fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'} group by fprogrammingContractID) t on t.fprogrammingContractID=ec.fprogrammingContractID and t.fauditTime=ec.fauditTime");
    		sb.append(" where (contract.fcontractPropert!='SUPPLY' or contract.fid is null) and t.fauditTime is not null and ec.fstate='4AUDITTED' and ec.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    	}else{
    		sb.append(" where (contract.fcontractPropert!='SUPPLY' or contract.fid is null) and ec.fstate='4AUDITTED' and ec.fisLastest=1");
    	}
    	if(curProject!=null){
    		sb.append(" and ec.FCurProjectID in("+curProject+")");
    	}else if(curProjectinfo!=null&&number!=null){
    		sb.append(" and pro.fprojectID ='"+curProjectinfo.getId().toString()+"' and pc.flongNumber like '"+number+"%'");
    	}else{
    		sb.append(" and ec.FCurProjectID ='null'");
    	}
    	sb.append(" order by contractType.flongNumber,(case when contract.fcontractPropert='SUPPLY' then contract.fmainContractNumber else contract.fnumber end),contract.fcontractPropert");
    	
    	RptRowSet detailRowSet = executeQuery(sb.toString(), null, from, len, ctx);
    	while(detailRowSet.next()){
    		String id=detailRowSet.getString("id");
			if(detailValue.containsKey(id)){
				((List)detailValue.get(id)).add(detailRowSet.toRowArray());
			}else{
				List list=new ArrayList();
				list.add(detailRowSet.toRowArray());
				detailValue.put(id, list);
			}
    	}
    	Object[] key = value.keySet().toArray(); 
		Arrays.sort(key); 
		params.setObject("value", value);
		params.setObject("key", key);
    	params.setObject("detailValue", detailValue);
    	return params;
    }
    protected String getSql(Context ctx,RptParams params){
    	String curProject=(String) params.getObject("curProject");
    	CurProjectInfo curProjectinfo=(CurProjectInfo) params.getObject("curProjectInfo");
    	String number=(String)params.getObject("number");
    	Date auditDate=(Date)params.getObject("auditDate");
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select REPLACE(contractType.flongNumber, '!', '.')||'   '||contractType.fname_l2 contractType,ec.fid id,contract.fnumber number,contract.fname name,supplier.fname_l2 supplier,contract.famount contractAmount,ec.fnumber ecNumber,'' reason,ec.FEstimateAmount amount,'' changeDate");
    	sb.append("	from T_CON_ContractEstimateChange ec left join T_CON_ProgrammingContract pc on pc.fid=ec.fProgrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join t_con_contractBill contract on contract.fProgrammingContract=pc.fid left join t_bd_supplier supplier on supplier.fid=contract.fpartBId");
    	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId");
    	if(auditDate!=null){
    		sb.append(" left join (select max(fauditTime) fauditTime,fprogrammingContractID from T_CON_ContractEstimateChange where fstate='4AUDITTED' and fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'} group by fprogrammingContractID) t on t.fprogrammingContractID=ec.fprogrammingContractID and t.fauditTime=ec.fauditTime");
    		sb.append(" where (contract.fcontractPropert!='SUPPLY' or contract.fid is null) and t.fauditTime is not null and ec.fstate='4AUDITTED' and ec.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    	}else{
    		sb.append(" where (contract.fcontractPropert!='SUPPLY' or contract.fid is null) and ec.fstate='4AUDITTED' and ec.fisLastest=1");
    	}
    	if(curProject!=null){
    		sb.append(" and ec.FCurProjectID in("+curProject+")");
    	}else if(curProjectinfo!=null&&number!=null){
    		sb.append(" and pro.fprojectID ='"+curProjectinfo.getId().toString()+"' and pc.flongNumber like '"+number+"%'");
    	}else{
    		sb.append(" and ec.FCurProjectID ='null'");
    	}
    	sb.append(" order by contractType.flongNumber,(case when contract.fcontractPropert='SUPPLY' then contract.fmainContractNumber else contract.fnumber end),contract.fcontractPropert");
    	return sb.toString();
    }
}