package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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

import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractBillReportFacadeControllerBean extends AbstractContractBillReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractBillReportFacadeControllerBean");
    
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
	    initColoum(header,col,"pcName",150,false);
	    initColoum(header,col,"supplier",200,false);
	    initColoum(header,col,"inviteForm",100,true);
	    initColoum(header,col,"contractPropert",100,false);
	    initColoum(header,col,"hasSettled",100,false);
	    initColoum(header,col,"srcAmount",100,false);
	    initColoum(header,col,"originalAmount",100,false);
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"bizDate",100,false);
	    initColoum(header,col,"auditDate",100,false);
	    header.setLabels(new Object[][]{
	    		{
	    			"合同类型","id","合同编码","合同名称","合约规划","签约单位","采购方式","合同性质","结算状态","原主合同金额","原币金额","本位币金额","业务日期","审批日期"
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
		Object[] key = value.keySet().toArray(); 
		Arrays.sort(key); 
		params.setObject("value", value);
		params.setObject("key", key);
		return params;
    }
    protected String getSql(Context ctx,RptParams params){
    	String curProject=(String) params.getObject("curProject");
    	CurProjectInfo curProjectinfo=(CurProjectInfo) params.getObject("curProjectInfo");
    	String number=(String)params.getObject("number");
    	Boolean isSupply=(Boolean)params.getObject("isSupply");
    	Boolean isClick=(Boolean)params.getObject("isClick");
    	Date auditDate=(Date)params.getObject("auditDate");
    	StringBuffer sb = new StringBuffer();
    	if(isClick!=null&&isClick){
    		sb.append(" select t.contractType,t.id,t.number,t.name,t.pcName,t.supplier,t.inviteForm,t.contractPropert,t.hasSettled,t.srcAmount,t.originalAmount,t.amount,t.bizDate,t.auditDate");
    		sb.append(" from(select contractType.flongNumber contractTypeNumber,contract.fmainContractNumber mainContractNumber,REPLACE(contractType.flongNumber, '!', '.')||'   '||contractType.fname_l2 contractType,contract.fid id,contract.fnumber number,contract.fname name,pc.fname_l2 pcName,supplier.fname_l2 supplier,'' inviteForm,contract.fcontractPropert contractPropert,contract.fhasSettled hasSettled,contract.fsrcAmount srcAmount,contract.foriginalAmount originalAmount,contract.famount amount,contract.fbookedDate bizDate,contract.fauditTime auditDate");
        	sb.append("	from t_con_contractBill contract left join T_CON_ProgrammingContract pc on pc.fid=contract.fProgrammingContract left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join t_bd_supplier supplier on supplier.fid=contract.fpartBId");
        	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId");
        	sb.append(" where contract.fstate='4AUDITTED'");
        	if(curProjectinfo!=null&&number!=null){
        		sb.append(" and pro.fprojectID ='"+curProjectinfo.getId().toString()+"' and pc.flongNumber like '"+number+"%'");
        		if(isSupply!=null){
        			if(isSupply){
            			sb.append(" and contract.fcontractPropert='SUPPLY'");
            		}else{
            			sb.append(" and contract.fcontractPropert!='SUPPLY'");
            		}
        		}
        		if(auditDate!=null){
    	    		sb.append(" and contract.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    	    	}
        	}else{
        		sb.append(" and contract.FCurProjectID ='null'");
        	}
        	
        	sb.append(" union all select contractType.flongNumber contractTypeNumber,contract.fmainContractNumber mainContractNumber,REPLACE(contractType.flongNumber, '!', '.')||'   '||contractType.fname_l2 contractType,contract.fid id,contract.fnumber number,contract.fname name,pc.fname_l2 pcName,supplier.fname_l2 supplier,'' inviteForm,contract.fcontractPropert contractPropert,contract.fhasSettled hasSettled,contract.fsrcAmount srcAmount,contract.foriginalAmount originalAmount,contract.famount amount,contract.fbookedDate bizDate,contract.fauditTime auditDate");
        	sb.append("	from t_con_contractBill contract left join t_bd_supplier supplier on supplier.fid=contract.fpartBId");
        	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId");
        	sb.append(" left join T_CON_ContractPCSplitBill split on split.fcontractBillId=contract.fid left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID");
        	sb.append(" where split.fcontractBillId is not null ");
        	if(curProjectinfo!=null&&number!=null&&isSupply!=null){
        		sb.append(" and pro.fprojectID ='"+curProjectinfo.getId().toString()+"' and pc.flongNumber like '"+number+"%'");
        		if(isSupply){
        			sb.append(" and contract.fcontractPropert='SUPPLY'");
        		}else{
        			sb.append(" and contract.fcontractPropert!='SUPPLY'");
        		}
        		if(auditDate!=null){
    	    		sb.append(" and contract.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    	    	}
        	}else{
        		sb.append(" and contract.FCurProjectID ='null'");
        	}
        	sb.append(" ) t order by t.contractTypeNumber,(case when t.contractPropert='SUPPLY' then t.mainContractNumber else t.number end),t.contractPropert");
    	}else{
    		String[] contractType=null;
        	HashMap hmParamIn = new HashMap();
        	hmParamIn.put("FDC_ISREPORTSHOWCONTRACT", null);
    		try {
    			HashMap hmAllParam = ParamControlFactory.getLocalInstance(ctx).getParamHashMap(hmParamIn);
    			if(hmAllParam.get("FDC_ISREPORTSHOWCONTRACT")!=null&&!"".equals(hmAllParam.get("FDC_ISREPORTSHOWCONTRACT"))){
    				contractType=hmAllParam.get("FDC_ISREPORTSHOWCONTRACT").toString().split(";");
    			}
    		} catch (EASBizException e) {
    			e.printStackTrace();
    		} catch (BOSException e) {
    			e.printStackTrace();
    		}
    		
    		sb.append(" select REPLACE(contractType.flongNumber, '!', '.')||'   '||contractType.fname_l2 contractType,contract.fid id,contract.fnumber number,contract.fname name,pc.fname_l2 pcName,supplier.fname_l2 supplier,'' inviteForm,contract.fcontractPropert contractPropert,contract.fhasSettled hasSettled,contract.fsrcAmount srcAmount,contract.foriginalAmount originalAmount,contract.famount amount,contract.fbookedDate bizDate,contract.fauditTime auditDate");
    		sb.append("	from t_con_contractBill contract left join T_CON_ProgrammingContract pc on pc.fid=contract.fProgrammingContract left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join t_bd_supplier supplier on supplier.fid=contract.fpartBId");
        	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId");
        	sb.append(" where contract.fstate='4AUDITTED'");
        	if(curProject!=null){
        		sb.append(" and contract.FCurProjectID in("+curProject+")");
        	}else{
        		sb.append(" and contract.FCurProjectID ='null'");
        	}
        	if(contractType!=null&&contractType.length>0){
        		sb.append(" and (contractType.fisCost is null");
        		for(int i=0;i<contractType.length;i++){
        			sb.append(" or contractType.flongNumber like '"+contractType[i]+"%'");
        		}
        		sb.append(" )");
        	}
        	sb.append(" order by contractType.flongNumber,(case when contract.fcontractPropert='SUPPLY' then contract.fmainContractNumber else contract.fnumber end),contract.fcontractPropert");
    	}
    	return sb.toString();
    }
}