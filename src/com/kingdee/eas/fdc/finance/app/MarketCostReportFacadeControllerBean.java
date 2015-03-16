package com.kingdee.eas.fdc.finance.app;

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

import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.jdbc.rowset.IRowSet;

public class MarketCostReportFacadeControllerBean extends AbstractMarketCostReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.MarketCostReportFacadeControllerBean");
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
	    initColoum(header,col,"isLeaf",100,true);
	    initColoum(header,col,"levelNumber",100,true);
	    initColoum(header,col,"number",120,false);
	    initColoum(header,col,"name",170,false);
	    
	    String year =params.getObject("year").toString();
	    
	    Object[] one=new Object[56];
	    Object[] two=new Object[56];
	    Object[] three=new Object[56];
	    one[0]="是否叶子";
	    one[1]="级次";
	    one[2]="科目编码";
	    one[3]="科目名称";
	    
	    two[0]="是否叶子";
	    two[1]="级次";
	    two[2]="科目编码";
	    two[3]="科目名称";
	    
	    three[0]="是否叶子";
	    three[1]="级次";
	    three[2]="科目编码";
	    three[3]="科目名称";
	    
	    int k=4;
	    for(int i=0;i<=12;i++){
	    	String key=year+"year"+i+"month";
			initColoum(header,col,key+"CONTRACTPLAN",100,false);
			initColoum(header,col,key+"CONTRACTACT",100,false);
			initColoum(header,col,key+"PAYPLAN",100,false);
			initColoum(header,col,key+"PAYACT",100,false);
			
			String monthStr=null;
			if(i==0){
				monthStr="合计";
			}else{
				monthStr= year + "年" + i + "月";
			}
			one[k]=monthStr;
			one[k+1]=monthStr;
			one[k+2]=monthStr;
			one[k+3]=monthStr;
			
			two[k]="合同";
			two[k+1]="合同";
			two[k+2]="付款";
			two[k+3]="付款";
			
			three[k]="计划数";
			three[k+1]="实际数";
			three[k+2]="计划数";
			three[k+3]="实际数";
			
			k=k+4;
	    }
	    
	    header.setLabels(new Object[][]{one,two,three},true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	FDCSQLBuilder _builder = new FDCSQLBuilder(ctx);
		_builder.appendSql(getSql(ctx,params));
		final IRowSet rowSet = _builder.executeQuery();
		params.setObject("rowset", rowSet);
		return params;
    }
    protected String getSql(Context ctx,RptParams params){
    	int year = Integer.valueOf(params.getObject("year").toString());
    	String orgUnit=(String) params.getObject("orgUnit");

    	StringBuffer orgUnitWhere=new StringBuffer();
    	if(orgUnit!=null&&!"".equals(orgUnit.trim())){
    		orgUnitWhere.append(" and orgUnit.flongNumber like '"+orgUnit+"%'");
    	}else{
    		orgUnitWhere.append(" and orgUnit.flongNumber ='null'");
    	}
    	StringBuffer bgOrgUnitWhere=new StringBuffer();
    	if(orgUnit!=null&&!"".equals(orgUnit.trim())){
    		bgOrgUnitWhere.append(" and orgUnit.flongNumber = '"+orgUnit+"'");
    	}else{
    		bgOrgUnitWhere.append(" and orgUnit.flongNumber ='null'");
    	}
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select t.type,t.isLeaf,t.levelNumber,t.number,t.name,t.month,t.year,sum(t.amount)amount from (select 'contract' type,costAccount.fisLeaf isLeaf,costAccount.flevel levelNumber,REPLACE(costAccount.flongNumber, '!', '.') number,costAccount.fname_l2 name,t.amount,t.month,t.year from T_FDC_CostAccount costAccount");
    	sb.append(" left join(select costAccount.fname_l2 name,contractSplitEntry.famount amount,year(contract.fauditTime) year,month(contract.fauditTime) month from T_CON_ContractCostSplitEntry contractSplitEntry left join T_FDC_CostAccount costAccount on costAccount.fid=contractSplitEntry.fcostAccountId left join T_CON_ContractCostSplit contractSplit on contractSplit.fid=contractSplitEntry.fparentid");
    	sb.append(" left join t_con_contractBill contract on contract.fid=contractSplit.fcontractBillId left join T_FDC_CurProject curProject on contract.fcurProjectId=curProject.fid left join T_ORG_BaseUnit orgUnit on orgUnit.fid=curProject.ffullOrgUnit where contract.fstate='4AUDITTED' and contract.fauditTime is not null and costAccount.fname_l2 is not null");
    	sb.append(orgUnitWhere);
    	sb.append(" and year(contract.fauditTime)="+year);
    	sb.append(" union all select costAccount.fname_l2 name,contractSplitEntry.FRequestAmount amount,year(contract.fauditTime) year,month(contract.fauditTime) month from T_CON_CWTextBgEntry contractSplitEntry left join T_BC_ExpenseType costAccount on costAccount.fid=contractSplitEntry.fexpenseTypeId");
    	sb.append(" left join t_con_contractWithoutText contract on contract.fid=contractSplitEntry.fheadId left join T_ORG_BaseUnit orgUnit on orgUnit.fid=contract.FCostedCompanyId where contract.fstate='4AUDITTED' and contract.fauditTime is not null and costAccount.fname_l2 is not null");
    	sb.append(orgUnitWhere);
    	sb.append(" and year(contract.fauditTime)="+year);
    	sb.append(" )t on t.name=costAccount.fname_l2 where costAccount.FFullOrgUnit='00000000-0000-0000-0000-000000000000CCE7AED4' and costAccount.flongNumber like '5001!05%' and costAccount.fcurproject is null");
    	
    	sb.append(" union all select 'pay' type,costAccount.fisLeaf isLeaf,costAccount.flevel levelNumber,REPLACE(costAccount.flongNumber, '!', '.') number,costAccount.fname_l2 name,t.amount,t.month,t.year from T_FDC_CostAccount costAccount");
    	sb.append(" left join(select costAccount.fname_l2 name,(pay.famount/payRequest.foriginalAmount*contractSplitEntry.FRequestAmount) amount,year(pay.FPayDate) year,month(pay.FPayDate) month from T_CON_PayRequestBillBgEntry contractSplitEntry left join T_BC_ExpenseType costAccount on costAccount.fid=contractSplitEntry.fexpenseTypeId");
    	sb.append(" left join T_CON_PayRequestBill payRequest on payRequest.fid=contractSplitEntry.fheadId left join t_con_contractBill contract on contract.fid =payRequest.fcontractId left join T_CAS_PaymentBill pay on pay.FFdcPayReqID=payRequest.fid left join T_ORG_BaseUnit orgUnit on orgUnit.fid=payRequest.FCostedCompanyId where pay.fbillstatus=15 and pay.FPayDate is not null and costAccount.fname_l2 is not null and contract.fid is not null");
    	sb.append(orgUnitWhere);
    	sb.append(" and year(pay.FPayDate)="+year);
    	sb.append(" union all select costAccount.fname_l2 name,(pay.famount/contract.foriginalAmount*contractSplitEntry.FRequestAmount) amount,year(pay.FPayDate) year,month(pay.FPayDate) month from T_CON_CWTextBgEntry contractSplitEntry left join T_BC_ExpenseType costAccount on costAccount.fid=contractSplitEntry.fexpenseTypeId");
    	sb.append(" left join t_con_contractWithoutText contract on contract.fid=contractSplitEntry.fheadId left join T_CAS_PaymentBill pay on pay.fcontractBillid=contract.fid left join T_ORG_BaseUnit orgUnit on orgUnit.fid=contract.FCostedCompanyId where pay.fbillstatus=15 and pay.FPayDate is not null and costAccount.fname_l2 is not null");
    	sb.append(orgUnitWhere);
    	sb.append(" and year(pay.FPayDate)="+year);
    	sb.append(" )t on t.name=costAccount.fname_l2 where costAccount.FFullOrgUnit='00000000-0000-0000-0000-000000000000CCE7AED4' and costAccount.flongNumber like '5001!05%' and costAccount.fcurproject is null");

    	sb.append(" union all select 'pay' type,costAccount.fisLeaf isLeaf,costAccount.flevel levelNumber,REPLACE(costAccount.flongNumber, '!', '.') number,costAccount.fname_l2 name,t.amount,t.month,t.year from T_FDC_CostAccount costAccount");
    	sb.append(" left join(select bgData.fbgItemName name,bgData.fbizActual amount,period.fyear year,period.fmonth month from t_bg_bgactualdata bgData left join T_BG_BgPeriod period on period.fid=bgData.fbgPeriodId");
    	sb.append(" left join T_ORG_BaseUnit orgUnit on orgUnit.fid=bgData.forgUnitId where bgData.fformula like '%YX%'");
    	sb.append(bgOrgUnitWhere);
    	sb.append(" and period.fyear="+year);
    	sb.append(" )t on t.name=costAccount.fname_l2 where costAccount.FFullOrgUnit='00000000-0000-0000-0000-000000000000CCE7AED4' and costAccount.flongNumber like '5001!05%' and costAccount.fcurproject is null");
    
    	sb.append(" )t where (t.year is null or t.year="+year+")");
    	if(orgUnit==null){
    		sb.append(" and t.number is null");
    	}
    	sb.append(" group by t.type,t.isLeaf,t.levelNumber,t.number,t.name,t.month,t.year order by t.number");
    	return sb.toString();
    }
}