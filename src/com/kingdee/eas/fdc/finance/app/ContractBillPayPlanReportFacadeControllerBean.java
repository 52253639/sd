package com.kingdee.eas.fdc.finance.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class ContractBillPayPlanReportFacadeControllerBean extends AbstractContractBillPayPlanReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.ContractBillPayPlanReportFacadeControllerBean");
    
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
	    initColoum(header,col,"lastPrice",100,false);
	    initColoum(header,col,"payAmount",100,false);
	    initColoum(header,col,"payRate",100,false);
	    initColoum(header,col,"unPayAmount",100,false);
	    initColoum(header,col,"bizDate",100,false);
	    initColoum(header,col,"auditDate",100,false);
	    
	    header.setLabels(new Object[][]{
	    		{
	    			"合同类型","id","合同编码","合同名称","合约规划","签约单位","采购方式","合同性质","结算状态","原主合同金额","原币金额","本位币金额","最新造价","累计已付金额","累计已付比例","未付金额","业务日期","审批日期"
	    		}
	    		,
	    		{
	    			"合同类型","id","合同编码","合同名称","合约规划","签约单位","采购方式","合同性质","结算状态","原主合同金额","原币金额","本位币金额","最新造价","累计已付金额","累计已付比例","未付金额","业务日期","审批日期"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		Map value=new HashMap();
		Set idSet=new HashSet();
		
		while(rowSet.next()){
			String contractTypeName=rowSet.getString("contractType");
			idSet.add(rowSet.getString("id"));
			if(value.containsKey(contractTypeName)){
				((List)value.get(contractTypeName)).add(rowSet.toRowArray());
			}else{
				List list=new ArrayList();
				list.add(rowSet.toRowArray());
				value.put(contractTypeName, list);
			}
		}
		String[] contractids = new String[idSet.size()];
		idSet.toArray(contractids);
		
		params.setObject("lastMap", FDCUtils.getLastAmt_Batch(ctx, contractids));
		Object[] key = value.keySet().toArray(); 
		Arrays.sort(key); 
		params.setObject("value", value);
		params.setObject("key", key);
		
		Integer syear = (Integer)params.getObject("syear");
	    Integer smonth =   (Integer)params.getObject("smonth");
	    
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, syear);
		cal.set(Calendar.MONTH, smonth-1);

		Date fromDate=FDCDateHelper.getFirstDayOfMonth(cal.getTime());
		
		Integer eyear = (Integer)params.getObject("eyear");
	    Integer emonth =   (Integer)params.getObject("emonth");
	    
		cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, eyear);
		cal.set(Calendar.MONTH, emonth-1);

		Date toDate=FDCDateHelper.getFirstDayOfMonth(cal.getTime());
		    
		String curProject=(String) params.getObject("curProject");
		StringBuffer sb = new StringBuffer();
		sb.append(" select isnull(dateEntry.famount,0) amount,year(bill.fbizDate) year,month(bill.fbizDate) month,gatherEntry.contractBillId id from T_FNC_OrgUnitMonthPlanGather bill");
    	sb.append(" left join T_FNC_OrgUnitMonthPGEntry entry on bill.fid=entry.fheadId left join T_ORG_BaseUnit orgUnit on orgUnit.fid=bill.forgUnitId");
    	sb.append(" left join T_FNC_ProjectMonthPlanGather gather on gather.fid=entry.fsrcId");
    	sb.append(" left join T_FNC_ProjectMonthPlanGEntry gatherEntry on gatherEntry.fheadId=gather.fid");
    	sb.append(" left join t_con_contractbill contract on contract.fid=gatherEntry.contractBillId");
    	sb.append(" left join T_FNC_ProjectMonthPGDateEntry dateEntry on dateEntry.fheadentryId=gatherEntry.fid");
    	sb.append(" where bill.fstate='4AUDITTED' and bill.fIsLatest=1 and gatherEntry.contractBillId is not null");
    	sb.append(" and dateEntry.fyear=year(bill.fbizDate) and dateEntry.fmonth=month(bill.fbizDate)");
		sb.append(" and bill.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		sb.append(" and bill.fbizDate<={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(toDate))+ "'}");
		if(curProject!=null){
    		sb.append(" and contract.FCurProjectID in("+curProject+")");
    	}else{
    		sb.append(" and contract.FCurProjectID ='null'");
    	}
		RptRowSet planRowSet = executeQuery(sb.toString(), null, ctx);
		Map planValue=new HashMap();
		while(planRowSet.next()){
			planValue.put(planRowSet.getString("id")+planRowSet.getInt("year")+"Y"+planRowSet.getInt("month")+"M"+"planAmount", planRowSet.getBigDecimal("amount"));
		}
		params.setObject("planValue", planValue);
		
		toDate=FDCDateHelper.getLastDayOfMonth(cal.getTime());
		sb = new StringBuffer();
		sb.append(" select sum(pay.FAmount) amount,year(pay.fpayDate) year,month(pay.fpayDate) month,pay.fcontractbillid id from t_cas_paymentbill pay");
		sb.append(" left join t_con_contractbill contract on contract.fid=pay.fcontractbillid");
		sb.append(" where pay.fbillstatus=15 and pay.fcontractbillid is not null");
		sb.append(" and pay.fpayDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    	sb.append(" and pay.fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		if(curProject!=null){
    		sb.append(" and contract.FCurProjectID in("+curProject+")");
    	}else{
    		sb.append(" and contract.FCurProjectID ='null'");
    	}
		sb.append(" group by year(pay.fpayDate),month(pay.fpayDate),pay.fcontractbillid");
		RptRowSet payRowSet = executeQuery(sb.toString(), null, ctx);
		Map payValue=new HashMap();
		while(payRowSet.next()){
			payValue.put(payRowSet.getString("id")+payRowSet.getInt("year")+"Y"+payRowSet.getInt("month")+"M"+"payAmount", payRowSet.getBigDecimal("amount"));
		}
		params.setObject("payValue", payValue);
		return params;
    }
    protected String getSql(Context ctx,RptParams params){
    	String curProject=(String) params.getObject("curProject");
    	StringBuffer sb = new StringBuffer();
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
		
		sb.append(" select REPLACE(contractType.flongNumber, '!', '.')||'   '||contractType.fname_l2 contractType,contract.fid id,contract.fnumber number,contract.fname name,pc.fname_l2 pcName,supplier.fname_l2 supplier,'' inviteForm,contract.fcontractPropert contractPropert,contract.fhasSettled hasSettled,contract.fsrcAmount srcAmount,contract.foriginalAmount originalAmount,contract.famount amount,null lastPrice,pay.payAmount payAmount,null payRate,null unPayAmount,contract.fbookedDate bizDate,contract.fauditTime auditDate");
		sb.append("	from t_con_contractBill contract left join T_CON_ProgrammingContract pc on pc.fid=contract.fProgrammingContract left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join t_bd_supplier supplier on supplier.fid=contract.fpartBId");
    	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId");
    	
    	sb.append(" left join (select sum(pay.FAmount) payAmount,pay.fcontractbillid contractId from t_cas_paymentbill pay");
		sb.append(" where pay.fbillstatus=15 group by pay.fcontractbillid )pay on pay.contractId=contract.fid");
	
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
    	return sb.toString();
    }
}