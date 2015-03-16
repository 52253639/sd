package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;

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
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.util.app.ContextUtil;

public class DynamicCostDiffFacadeControllerBean extends AbstractDynamicCostDiffFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.DynamicCostDiffFacadeControllerBean");
    
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
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"isLeaf",100,true);
	    initColoum(header,col,"levelNumber",100,true);
	    initColoum(header,col,"number",100,false);
	    initColoum(header,col,"name",250,false);
	    initColoum(header,col,"A",200,false);
	    initColoum(header,col,"B",200,false);
	    initColoum(header,col,"balance",200,false);

	    Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
	    header.setLabels(new Object[][]{
	    		{
	    			"id","isLeaf","levelNumber","合约规划编码","合约规划名称","基准截至日期   动态成本(A)","对比截至日期   动态成本(B)","差异(A-B)"
	    		}
	    		,
	    		{
	    			"id","isLeaf","levelNumber","合约规划编码","合约规划名称","("+FDCDateHelper.formatDate2(fromDate)+")","("+FDCDateHelper.formatDate2(toDate)+")","(A-B)"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	CurProjectInfo curProject = (CurProjectInfo) params.getObject("curProject");
    	Date fromDate = (Date)params.getObject("fromDate");
     	Date toDate =   (Date)params.getObject("toDate");
    	RptRowSet fromRowset = executeQuery(getSql(ctx,curProject,fromDate), null, from, len, ctx);
		params.setObject("fromRowset", fromRowset);
		
		RptRowSet toRowset = executeQuery(getSql(ctx,curProject,toDate), null, from, len, ctx);
		params.setObject("toRowset", toRowset);
		return params;
    }
    
    protected String getSql(Context ctx,CurProjectInfo curProject,Date date){
    	String[] contractType=null;
    	HashMap hmParamIn = new HashMap();
		hmParamIn.put("CIFI_PCCONTRACTTYPE",null);
		try {
			HashMap hmAllParam = ParamControlFactory.getLocalInstance(ctx).getParamHashMap(hmParamIn);
			if(hmAllParam.get("CIFI_PCCONTRACTTYPE")!=null&&!"".equals(hmAllParam.get("CIFI_PCCONTRACTTYPE"))){
				contractType=hmAllParam.get("CIFI_PCCONTRACTTYPE").toString().split(";");
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
    	sb.append(" select pc.fid id,(case when isLeaf.fparentid is not null then 0 else 1 end) isLeaf,pc.flevel levelNumber,pc.FLongNumber number,pc.fname_l2 name,pc.famount amount,");
    	sb.append(" (isnull(contract.famount,0)-isnull(supply.famount,0)) contractAmount,supply.famount supplyAmount,contractWT.famount contractWTAmount,estimate.famount estimateAmount,settle.famount settleAmount,CONFIRM.famount CONFIRM,UNCONFIRM.famount UNCONFIRM");
    	sb.append(" from T_CON_ProgrammingContract pc left join T_CON_Programming pro on pro.fid=pc.FProgrammingID");
    	sb.append(" left join (select famount,fProgrammingContract from t_con_contractBill where fContractPropert!='SUPPLY' and fstate='4AUDITTED'");
    	sb.append(" and fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(date))+ "'}");
    	sb.append(" union all select sum(entry.famount)famount,entry.fProgrammingContractId fProgrammingContract from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId left join t_con_contractBill contract on contract.fid=split.fcontractBillId where split.fcontractBillId is not null");
    	sb.append(" and contract.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(date))+ "'}");
    	sb.append(" group by entry.fProgrammingContractId) contract on contract.fProgrammingContract=pc.fid");
    	sb.append(" left join (select sum(famount) famount,fProgrammingContract from t_con_contractBill where fContractPropert='SUPPLY' and fstate='4AUDITTED'");
    	sb.append(" and fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(date))+ "'} group by fProgrammingContract");
    	sb.append(" union all select sum(entry.famount) famount,entry.fProgrammingContractId fProgrammingContract from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId left join t_con_contractBill contract on contract.fid=split.fcontractBillId where contract.fContractPropert='SUPPLY' and split.fcontractBillId is not null");
    	sb.append(" and contract.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(date))+ "'}");
		sb.append(" group by entry.fProgrammingContractId) supply on supply.fProgrammingContract=pc.fid");
    	sb.append(" left join (select sum(case when pay.contractbillId is null then cwt.famount else pay.payAmount end) famount,cwt.fProgrammingContract from T_CON_ContractWithoutText cwt left join (select sum(flocalAmount) payAmount,fcontractbillId contractbillId from t_cas_paymentBill where fbillstatus=15 group by fcontractbillId) pay on pay.contractbillId=cwt.fid where cwt.fstate='4AUDITTED'");
    	sb.append(" and fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(date))+ "'}");
		sb.append(" group by fProgrammingContract) contractWT on contractWT.fProgrammingContract=pc.fid");
    	sb.append(" left join (select sum(settlement.fcurSettlePrice) famount,contract.fProgrammingContract from T_CON_ContractSettlementBill settlement left join t_con_contractBill contract on contract.fid=settlement.FContractBillID where settlement.fstate='4AUDITTED'");
    	sb.append(" and settlement.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(date))+ "'}");
    	sb.append(" group by contract.fProgrammingContract");
    	sb.append(" union all select sum(entry.famount) famount,entry.fProgrammingContractId fProgrammingContract from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId left join T_CON_ContractSettlementBill settlement on settlement.fid=split.fcontractSettleBillId where split.fcontractSettleBillId is not null");
    	sb.append(" and settlement.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(date))+ "'}");
    	sb.append(" group by entry.fProgrammingContractId");
    	sb.append(" )settle on settle.fProgrammingContract=pc.fid");
    	sb.append(" left join (select festimateAmount famount,ec.fprogrammingContractID fProgrammingContract from T_CON_ContractEstimateChange ec");
		sb.append(" left join (select max(fauditTime) fauditTime,fprogrammingContractID from T_CON_ContractEstimateChange where fstate='4AUDITTED' and fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(date))+ "'} group by fprogrammingContractID) t on t.fprogrammingContractID=ec.fprogrammingContractID and t.fauditTime=ec.fauditTime");
		sb.append(" where t.fauditTime is not null and fstate='4AUDITTED' and ec.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(date))+ "'}");
    	sb.append(" )estimate on estimate.fProgrammingContract=pc.fid");
    	sb.append(" left join (select contract.FProgrammingContract,sum(changeSettle.fallowAmount*cb.fexRate) famount from T_CON_ContractChangeBill cb");  
    	sb.append(" left join t_con_contractBill contract on contract.fid=cb.FContractBillID left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.FConChangeBillID=cb.fid where cb.fstate='8VISA'");
    	sb.append(" and changeSettle.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(date))+ "'}");
    	sb.append(" group by contract.FProgrammingContract");
    	sb.append(" union all select entry.fProgrammingContractId FProgrammingContract,sum(entry.famount) famount from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId");  
		sb.append(" left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.fid=split.fContractChangeSettleBillId where split.fcontractChangeSettleBillId is not null");
		sb.append(" and changeSettle.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(date))+ "'}");
		sb.append(" group by entry.fProgrammingContractId");
		sb.append(" ) CONFIRM on CONFIRM.FProgrammingContract=pc.fid");
    	sb.append(" left join (select contract.FProgrammingContract,sum(cb.famount) famount from T_CON_ContractChangeBill cb");  
    	sb.append(" left join t_con_contractBill contract on contract.fid=cb.FContractBillID left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.FConChangeBillID=cb.fid where cb.fstate in('8VISA','4AUDITTED','7ANNOUNCE')");
    	sb.append(" and cb.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(date))+ "'}");
    	sb.append(" or (changeSettle.fauditTime>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(date))+ "'} or changeSettle.fauditTime is null)");
    	sb.append(" group by contract.FProgrammingContract");
    	sb.append(" union all select entry.fProgrammingContractId FProgrammingContract,sum(entry.famount) famount from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId");  
		sb.append(" left join T_CON_ContractChangeBill cb on cb.fid=split.fContractChangeBillId");
		sb.append(" left join (select entry.fProgrammingContractId pcId,changeSettle.FConChangeBillID cbId,changeSettle.fauditTime auditTime from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId");  
		sb.append(" left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.fid=split.fContractChangeSettleBillId where split.fcontractChangeSettleBillId is not null  ) confirmSplit on confirmSplit.cbId=cb.fId and confirmSplit.pcId=entry.fProgrammingContractId where split.fcontractChangeBillId is not null");
		sb.append(" and cb.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(date))+ "'}");
		sb.append(" or (confirmSplit.auditTime>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(date))+ "'} or confirmSplit.cbId is null)");
		sb.append(" group by entry.fProgrammingContractId");
		sb.append(" ) UNCONFIRM on UNCONFIRM.FProgrammingContract=pc.fid");
    	sb.append(" left join (select distinct fparentid from T_CON_ProgrammingContract) isLeaf on isLeaf.fparentid=pc.fid");
    	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=pc.fcontractTypeId");
    	sb.append(" where pro.FIsLatest=1 and pro.fstate='4AUDITTED'");
    	if(contractType!=null&&contractType.length>0){
    		sb.append(" and (contractType.fisCost is null");
    		for(int i=0;i<contractType.length;i++){
    			sb.append(" or contractType.flongNumber like '"+contractType[i]+"%'");
    		}
    		sb.append(" )");
    	}
    	if(curProject!=null){
    		sb.append(" and pro.fprojectID ='"+curProject.getId().toString()+"'");
    	}else{
    		sb.append(" and pro.fprojectID = 'null'");
    	}
    	sb.append(" order by pc.flongNumber");
    	return sb.toString();
    }
}