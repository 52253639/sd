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

public class ContractBillPayReportFacadeControllerBean extends AbstractContractBillPayReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractBillPayReportFacadeControllerBean");
    
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
	    initColoum(header,col,"contractPropert",100,false);
	    initColoum(header,col,"pcAmount",100,false);
	    initColoum(header,col,"srcAmount",100,false);
	    initColoum(header,col,"originalAmount",100,false);
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"settleOriginalAmount",100,false);
	    initColoum(header,col,"settleAmount",100,false);
	    initColoum(header,col,"costAmount",100,false);
	    initColoum(header,col,"appAmount",100,false);
	    initColoum(header,col,"reqAmount",100,false);
	    initColoum(header,col,"payAmount",100,false);
	    initColoum(header,col,"payRate",100,false);
	    initColoum(header,col,"lstAppAmount",100,false);
	    initColoum(header,col,"lstPayAmount",100,false);
	    initColoum(header,col,"lstEndAppAmount",100,false);
	    initColoum(header,col,"lstCostAmount",100,false);
	    initColoum(header,col,"nowAppAmount",100,false);
	    initColoum(header,col,"nowReqAmount",100,false);
	    initColoum(header,col,"nowPayAmount",100,false);
	    initColoum(header,col,"bzj",100,false);
	    header.setLabels(new Object[][]{
	    		{
	    			"合同类型","id","合同编码","合同名称","签约单位","合同性质","规划金额","签约信息","签约信息","签约信息","结算信息","结算信息","累计信息（截止月份）","累计信息（截止月份）","累计信息（截止月份）","累计信息（截止月份）","累计信息（截止月份）","本月付款信息","本月付款信息","本月付款信息","本月付款信息","本月付款信息","本月付款信息","本月付款信息","已付保证金金额"
	    		}
	    		,
	    		{
	    			"合同类型","id","合同编码","合同名称","签约单位","合同性质","原主合同金额","原币金额","本位币金额","原币金额","本位币金额","累计完工工程量","累计应付账款","累计付款申请","累计已付账款","付款比例","上月应付账款","上月实付款","上月末累计应付未付款","上月完工工程量","本月应付账款","本月付款申请","本月已付账款","已付保证金金额"
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
    	Date auditDate=(Date)params.getObject("auditDate");
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select REPLACE(contractType.flongNumber, '!', '.')||'   '||contractType.fname_l2 contractType,contract.fid id,contract.fnumber number,contract.fname name,pc.fname_l2 pcName,supplier.fname_l2 supplier,'' inviteForm,contract.fcontractPropert contractPropert,contract.fsrcAmount srcAmount,contract.foriginalAmount originalAmount,contract.famount amount,isnull(amount1.famount,0) amount1,isnull(amount2.famount,0) amount2,isnull(amount3.famount,0) amount3,isnull(amount4.famount,0) amount4,isnull(amount5.famount,0) amount5,isnull(amount6.famount,0) amount6,contract.fbookedDate bizDate,contract.fauditTime auditDate");
    	sb.append("	from t_con_contractBill contract left join T_CON_ProgrammingContract pc on pc.fid=contract.fProgrammingContract left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join t_bd_supplier supplier on supplier.fid=contract.fpartBId");
    	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId");
    	sb.append(" left join (select split.fcontractBillId,sum(entry.famount)famount from T_CON_ContractPCSplitBill split left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_FDC_CurProject project on project.fid=pro.fProjectId where project.fnumber = '01' group by fcontractBillId) amount1 on amount1.fcontractBillId=contract.fid");
    	sb.append(" left join (select split.fcontractBillId,sum(entry.famount)famount from T_CON_ContractPCSplitBill split left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_FDC_CurProject project on project.fid=pro.fProjectId where project.fnumber = '02' group by fcontractBillId) amount2 on amount2.fcontractBillId=contract.fid");
    	sb.append(" left join (select split.fcontractBillId,sum(entry.famount)famount from T_CON_ContractPCSplitBill split left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_FDC_CurProject project on project.fid=pro.fProjectId where project.fnumber = '03' group by fcontractBillId) amount3 on amount3.fcontractBillId=contract.fid");
    	sb.append(" left join (select split.fcontractBillId,sum(entry.famount)famount from T_CON_ContractPCSplitBill split left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_FDC_CurProject project on project.fid=pro.fProjectId where project.fnumber = '04' group by fcontractBillId) amount4 on amount4.fcontractBillId=contract.fid");
    	sb.append(" left join (select split.fcontractBillId,sum(entry.famount)famount from T_CON_ContractPCSplitBill split left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_FDC_CurProject project on project.fid=pro.fProjectId where project.fnumber = '05' group by fcontractBillId) amount5 on amount5.fcontractBillId=contract.fid");
    	sb.append(" left join (select split.fcontractBillId,sum(entry.famount)famount from T_CON_ContractPCSplitBill split left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_FDC_CurProject project on project.fid=pro.fProjectId where project.fnumber = '06' group by fcontractBillId) amount6 on amount6.fcontractBillId=contract.fid");
    	sb.append(" where contract.fstate='4AUDITTED'");
    	if(curProject!=null){
    		sb.append(" and contract.FCurProjectID in("+curProject+")");
    	}else{
    		sb.append(" and contract.FCurProjectID ='null'");
    	}
    	sb.append(" order by contractType.flongNumber,(case when contract.fcontractPropert='SUPPLY' then contract.fmainContractNumber else contract.fnumber end),contract.fcontractPropert");
	
    	return sb.toString();
    }
}