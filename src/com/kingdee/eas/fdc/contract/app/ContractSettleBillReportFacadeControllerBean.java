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

public class ContractSettleBillReportFacadeControllerBean extends AbstractContractSettleBillReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractSettleBillReportFacadeControllerBean");
    
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
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"settleNumber",100,false);
	    initColoum(header,col,"constructPrice",100,false);
	    initColoum(header,col,"settleAmount",100,false);
		initColoum(header,col,"amount1",100,false);
	    initColoum(header,col,"amount2",100,false);
	    initColoum(header,col,"amount3",100,false);
	    initColoum(header,col,"amount4",100,false);
	    initColoum(header,col,"amount5",100,false);
	    initColoum(header,col,"amount6",100,false);
	    initColoum(header,col,"balance",100,false);
	    initColoum(header,col,"rate",100,false);
	    initColoum(header,col,"createDate",100,false);
	    initColoum(header,col,"auditDate",100,false);
	    initColoum(header,col,"creator",100,false);
	    header.setLabels(new Object[][]{
	    		{
	    			"合同类型","id","合同编码","合同名称","签约单位","合同金额","结算单编码","送审价","审核价","一期金额","二期金额","三期金额","四期金额","五期金额","六期金额","核减","核减率(%)","送审日期","审批日期","经办人"
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
    	Boolean isClick=(Boolean)params.getObject("isClick");
    	Date auditDate=(Date)params.getObject("auditDate");
    	StringBuffer sb = new StringBuffer();
    	if(isClick!=null&&isClick){
    		sb.append(" select t.contractType,t.id,t.number,t.name,t.supplier,t.amount,t.settleNumber,isnull(t.constructPrice,0),t.settleAmount,null amount1,null amount2,null amount3,null amount4,null amount5,null amount6,t.balance,t.rate,t.createDate,t.auditDate,t.creator from(");
    		sb.append(" select contractType.flongNumber contractTypeNumber,contract.fmainContractNumber mainContractNumber,contract.fcontractPropert contractPropert,REPLACE(contractType.flongNumber, '!', '.')||'   '||contractType.fname_l2 contractType,settle.fid id,contract.fnumber number,contract.fname name,supplier.fname_l2 supplier,contract.famount amount,");
        	sb.append(" settle.fnumber settleNumber,settle.fconstructPrice constructPrice,settle.fcurSettlePrice settleAmount,(contract.famount-settle.fcurSettlePrice) balance,(case when contract.famount=0 then 0 else (contract.famount-settle.fcurSettlePrice)/contract.famount*100 end) rate,");
        	sb.append(" settle.fcreateTime createDate,settle.fauditTime auditDate,creator.fname_l2 creator");
        	sb.append("	from T_CON_ContractSettlementBill settle left join t_con_contractBill contract on contract.fid=settle.fcontractBillId left join t_bd_supplier supplier on supplier.fid=contract.fpartBId");
        	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId left join t_pm_user creator on creator.fid=settle.fcreatorId");
        	sb.append(" left join T_CON_ProgrammingContract pc on pc.fid=contract.fProgrammingContract left join T_CON_Programming pro on pro.fid=pc.FProgrammingID");
        	sb.append(" where settle.fstate='4AUDITTED'");
        	if(curProjectinfo!=null&&number!=null){
        		sb.append(" and pro.fprojectID ='"+curProjectinfo.getId().toString()+"' and pc.flongNumber like '"+number+"%'");
        	}else{
        		sb.append(" and settle.FCurProjectID ='null'");
        	}
        	if(auditDate!=null){
        		sb.append(" and settle.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
        	}
        	sb.append(" union all select contractType.flongNumber contractTypeNumber,contract.fmainContractNumber mainContractNumber,contract.fcontractPropert contractPropert,REPLACE(contractType.flongNumber, '!', '.')||'   '||contractType.fname_l2 contractType,settle.fid id,contract.fnumber number,contract.fname name,supplier.fname_l2 supplier,contract.famount amount,");
        	sb.append(" settle.fnumber settleNumber,settle.fconstructPrice constructPrice,settle.fcurSettlePrice settleAmount,(contract.famount-settle.fcurSettlePrice) balance,(case when contract.famount=0 then 0 else (contract.famount-settle.fcurSettlePrice)/contract.famount*100 end) rate,");
        	sb.append(" settle.fcreateTime createDate,settle.fauditTime auditDate,creator.fname_l2 creator");
        	sb.append("	from T_CON_ContractSettlementBill settle left join t_con_contractBill contract on contract.fid=settle.fcontractBillId left join t_bd_supplier supplier on supplier.fid=contract.fpartBId");
        	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId left join t_pm_user creator on creator.fid=settle.fcreatorId");
        	sb.append(" left join T_CON_ContractPCSplitBill split on split.FContractSettleBillId=settle.fid left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID");
        	sb.append(" where split.FContractSettleBillId is not null and settle.fstate='4AUDITTED'");
        	if(curProjectinfo!=null&&number!=null){
        		sb.append(" and pro.fprojectID ='"+curProjectinfo.getId().toString()+"' and pc.flongNumber like '"+number+"%'");
        	}else{
        		sb.append(" and settle.FCurProjectID ='null'");
        	}
        	if(auditDate!=null){
        		sb.append(" and settle.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
        	}
        	sb.append(" )t order by t.contractTypeNumber,(case when t.contractPropert='SUPPLY' then t.mainContractNumber else t.number end),t.contractPropert");
    	}else{
    		sb.append(" select REPLACE(contractType.flongNumber, '!', '.')||'   '||contractType.fname_l2 contractType,settle.fid id,contract.fnumber number,contract.fname name,supplier.fname_l2 supplier,contract.famount amount,");
        	sb.append(" settle.fnumber settleNumber,isnull(settle.fconstructPrice,0) constructPrice,settle.fcurSettlePrice settleAmount,isnull(amount1.famount,0) amount1,isnull(amount2.famount,0) amount2,isnull(amount3.famount,0) amount3,isnull(amount4.famount,0) amount4,isnull(amount5.famount,0) amount5,isnull(amount6.famount,0) amount6,(contract.famount-settle.fcurSettlePrice) balance,(case when contract.famount=0 then 0 else (contract.famount-settle.fcurSettlePrice)/contract.famount*100 end) rate,");
        	sb.append(" settle.fcreateTime createDate,settle.fauditTime auditDate,creator.fname_l2 creator");
        	sb.append("	from T_CON_ContractSettlementBill settle left join t_con_contractBill contract on contract.fid=settle.fcontractBillId left join t_bd_supplier supplier on supplier.fid=contract.fpartBId");
        	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId left join t_pm_user creator on creator.fid=settle.fcreatorId");
        	sb.append(" left join (select split.fContractSettleBillId,sum(entry.famount)famount from T_CON_ContractPCSplitBill split left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_FDC_CurProject project on project.fid=pro.fProjectId where project.fnumber = '01' group by fContractSettleBillId) amount1 on amount1.fContractSettleBillId=settle.fid");
        	sb.append(" left join (select split.fContractSettleBillId,sum(entry.famount)famount from T_CON_ContractPCSplitBill split left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_FDC_CurProject project on project.fid=pro.fProjectId where project.fnumber = '02' group by fContractSettleBillId) amount2 on amount2.fContractSettleBillId=settle.fid");
        	sb.append(" left join (select split.fContractSettleBillId,sum(entry.famount)famount from T_CON_ContractPCSplitBill split left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_FDC_CurProject project on project.fid=pro.fProjectId where project.fnumber = '03' group by fContractSettleBillId) amount3 on amount3.fContractSettleBillId=settle.fid");
        	sb.append(" left join (select split.fContractSettleBillId,sum(entry.famount)famount from T_CON_ContractPCSplitBill split left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_FDC_CurProject project on project.fid=pro.fProjectId where project.fnumber = '04' group by fContractSettleBillId) amount4 on amount4.fContractSettleBillId=settle.fid");
        	sb.append(" left join (select split.fContractSettleBillId,sum(entry.famount)famount from T_CON_ContractPCSplitBill split left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_FDC_CurProject project on project.fid=pro.fProjectId where project.fnumber = '05' group by fContractSettleBillId) amount5 on amount5.fContractSettleBillId=settle.fid");
        	sb.append(" left join (select split.fContractSettleBillId,sum(entry.famount)famount from T_CON_ContractPCSplitBill split left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_FDC_CurProject project on project.fid=pro.fProjectId where project.fnumber = '06' group by fContractSettleBillId) amount6 on amount6.fContractSettleBillId=settle.fid");
        	sb.append(" where settle.fstate='4AUDITTED'");
        	if(curProject!=null){
        		sb.append(" and settle.FCurProjectID in("+curProject+")");
        	}else{
        		sb.append(" and settle.FCurProjectID ='null'");
        	}
        	sb.append(" order by contractType.flongNumber,(case when contract.fcontractPropert='SUPPLY' then contract.fmainContractNumber else contract.fnumber end),contract.fcontractPropert");
    	}
    	return sb.toString();
    }
}