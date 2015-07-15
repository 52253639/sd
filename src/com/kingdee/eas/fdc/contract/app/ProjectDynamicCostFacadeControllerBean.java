package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;
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
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.util.app.ContextUtil;

public class ProjectDynamicCostFacadeControllerBean extends AbstractProjectDynamicCostFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ProjectDynamicCostFacadeControllerBean");
    
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
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"aimBuildAmount",100,false);
	    initColoum(header,col,"aimSellAmount",100,false);
	    initColoum(header,col,"dynamicTotalAmount",100,false);
	    initColoum(header,col,"dynamicBuildAmount",100,false);
	    initColoum(header,col,"dynamicSellAmount",100,false);
	    initColoum(header,col,"balance",100,false);
	    initColoum(header,col,"rate",100,false);

	    header.setLabels(new Object[][]{
	    		{
	    			"id","isLeaf","levelNumber","合约规划编码","合约规划名称","目标成本","目标成本","目标成本","动态成本","动态成本","动态成本","动态-目标","动态-目标"
	    		}
	    		,{
	    			"id","isLeaf","levelNumber","合约规划编码","合约规划名称","目标成本","建筑单方(元/m2)","可售单方(元/m2)","动态成本","建筑单方(元/m2)","可售单方(元/m2)","差额","比例(%)"
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
		
    	CurProjectInfo curProject = (CurProjectInfo) params.getObject("curProject");
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select pc.fid id,(case when isLeaf.fparentid is not null then 0 else 1 end) isLeaf,pc.flevel levelNumber,pc.FLongNumber number,pc.fname_l2 name,pc.famount amount,");
    	sb.append(" (isnull(contract.famount,0)-isnull(supply.famount,0)) contractAmount,supply.famount supplyAmount,contractWT.famount contractWTAmount,estimate.famount estimateAmount,settle.famount settleAmount,change.CONFIRM CONFIRM,change.UNCONFIRM UNCONFIRM,t.ftotalBuildArea totalBuildArea,t.ftotalSellArea totalSellArea");
    	sb.append(" from T_CON_ProgrammingContract pc left join T_CON_Programming pro on pro.fid=pc.FProgrammingID");
    	sb.append(" left join (select famount,fProgrammingContract from t_con_contractBill where fContractPropert!='SUPPLY' and fstate='4AUDITTED'");
    	sb.append(" union all select sum(entry.famount)famount,entry.fProgrammingContractId fProgrammingContract from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId left join t_con_contractBill contract on contract.fid=split.fcontractBillId where split.fcontractBillId is not null");
    	sb.append(" group by entry.fProgrammingContractId) contract on contract.fProgrammingContract=pc.fid");
    	sb.append(" left join (select sum(famount) famount,fProgrammingContract from t_con_contractBill where fContractPropert='SUPPLY' and fstate='4AUDITTED' group by fProgrammingContract");
    	sb.append(" union all select sum(entry.famount) famount,entry.fProgrammingContractId fProgrammingContract from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId left join t_con_contractBill contract on contract.fid=split.fcontractBillId where contract.fContractPropert='SUPPLY' and split.fcontractBillId is not null");
    	sb.append(" group by entry.fProgrammingContractId) supply on supply.fProgrammingContract=pc.fid");
    	sb.append(" left join (select sum(famount) famount,fProgrammingContract from T_CON_ContractWithoutText where fstate='4AUDITTED' group by fProgrammingContract) contractWT on contractWT.fProgrammingContract=pc.fid");
    	sb.append(" left join (select sum(settlement.fcurSettlePrice) famount,contract.fProgrammingContract from T_CON_ContractSettlementBill settlement left join t_con_contractBill contract on contract.fid=settlement.FContractBillID where settlement.fstate='4AUDITTED' group by contract.fProgrammingContract");
    	sb.append(" union all select sum(entry.famount) famount,entry.fProgrammingContractId fProgrammingContract from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId where split.fcontractSettleBillId is not null group by entry.fProgrammingContractId");
    	sb.append(" )settle on settle.fProgrammingContract=pc.fid");
    	sb.append(" left join (select festimateAmount famount,fprogrammingContractID fProgrammingContract from T_CON_ContractEstimateChange where fstate='4AUDITTED' and fisLastest=1)estimate on estimate.fProgrammingContract=pc.fid");
    	sb.append(" left join (select contract.FProgrammingContract,sum(case when cb.fstate='8VISA' then (changeSettle.fallowAmount*cb.fexRate) else 0 end) CONFIRM,sum(case when cb.fstate in('4AUDITTED','7ANNOUNCE') then cb.famount else 0 end) UNCONFIRM from T_CON_ContractChangeBill cb");  
    	sb.append(" left join t_con_contractBill contract on contract.fid=cb.FContractBillID left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.FConChangeBillID=cb.fid group by contract.FProgrammingContract");
    	sb.append(" union all select t.pcId,sum(t.CONFIRM)CONFIRM,sum(t.UNCONFIRM)UNCONFIRM from (select entry.fProgrammingContractId pcId,sum(entry.famount) CONFIRM,0 UNCONFIRM from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId");  
		sb.append(" left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.fid=split.fContractChangeSettleBillId left join T_CON_ContractChangeBill cb on changeSettle.FConChangeBillID=cb.fid where split.fcontractChangeSettleBillId is not null");
		sb.append(" group by entry.fProgrammingContractId");
		sb.append(" union all select entry.fProgrammingContractId pcId,0 CONFIRM,sum(entry.famount) UNCONFIRM from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId");  
		sb.append(" left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_CON_ContractChangeBill cb on cb.fid=split.fcontractChangeBillId");
		sb.append(" left join (select entry.fProgrammingContractId pcId,changeSettle.FConChangeBillID cbId from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId");  
		sb.append(" left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.fid=split.fContractChangeSettleBillId where split.fcontractChangeSettleBillId is not null  ) confirmSplit on confirmSplit.cbId=cb.fId and confirmSplit.pcId=pc.fId where split.fcontractChangeBillId is not null and confirmSplit.cbId is null");
		sb.append(" group by entry.fProgrammingContractId) t group by t.pcId");
    	sb.append(" ) change on change.FProgrammingContract=pc.fid");
    	sb.append(" left join (select ftotalBuildArea,ftotalSellArea,FProjectID from T_AIM_PlanIndex planIndex left join T_AIM_MeasureCost measure on measure.fid=planIndex.FHeadID where FIsLastVersion=1 and fstate='4AUDITTED') t on t.FProjectID=pro.fprojectID");
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