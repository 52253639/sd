package com.kingdee.eas.fdc.invite.app;

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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class InviteIndexDetailReportFacadeControllerBean extends AbstractInviteIndexDetailReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InviteIndexDetailReportFacadeControllerBean");
    
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
	    initColoum(header,col,"conId",100,true);
	    initColoum(header,col,"pcId",100,true);
	    initColoum(header,col,"supplierId",100,true);
	    initColoum(header,col,"levelNumber",100,true);
	    initColoum(header,col,"number",100,false);
	    initColoum(header,col,"name",250,false);
	    initColoum(header,col,"amount",120,false);
	    initColoum(header,col,"purMode",80,false);
	       
	    initColoum(header,col,"value1",150,false);
	    initColoum(header,col,"conName",150,false);
	    initColoum(header,col,"conDate",150,false);
	    initColoum(header,col,"supplier",150,false);
	    initColoum(header,col,"value2",150,false);
	    initColoum(header,col,"rate1",70,false);
	    
	    initColoum(header,col,"value3",150,false);
	    initColoum(header,col,"value4",150,false);
	    initColoum(header,col,"value5",300,false);
	    initColoum(header,col,"rate2",70,false);
	    
	    initColoum(header,col,"form",120,false);
	    initColoum(header,col,"realForm",120,false);
	    initColoum(header,col,"value6",250,false);
	    initColoum(header,col,"value7",150,false);
	    initColoum(header,col,"rate3",70,false);
	    
	    initColoum(header,col,"value8",250,false);
	    initColoum(header,col,"rate4",70,false);
	    
	    initColoum(header,col,"value9",150,false);
	    initColoum(header,col,"rate5",70,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"conId","pcId","supplierId","levelNumber","框架合约编码","框架合约名称","规划金额(元)","实际采购模式","已完成采购的合同总项数","合同名称","合同业务日期","新增供应商中标情况","新增供应商中标情况","新增供应商中标情况","目标成本单价达成情况","目标成本单价达成情况","目标成本单价达成情况","目标成本单价达成情况","招标率完成情况","招标率完成情况","招标率完成情况","招标率完成情况","招标率完成情况","采用最低价中标的合同数","采用最低价中标的合同数","采购计划达成情况"	,"采购计划达成情况"
	    		}
	    		,
	    		{
	    			"conId","pcId","supplierId","levelNumber","框架合约编码","框架合约名称","规划金额(元)","实际采购模式","已完成采购的合同总项数","合同名称","合同业务日期","中标单位","新增供应商中标项数","比率","已完成采购的合同金额(元)","已完成采购的单方造价(元)","已完成采购且符合采购计划中目标成本单价的合同项数","比率","制度要求采购方式","实际采购方式","制度要求招标且实际采用招标的合同项数","制度要求招标的合同项数","比率","采用最低价中标的合同数（不包含直接委托）","比率","已按时完成采购的合同项数","比率"
				}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	CurProjectInfo curProject = (CurProjectInfo) params.getObject("curProject");
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select bill.conId conId,invEntry.fprogrammingContractID pcId,newcon.supplierId,entry.flevel levelNumber,entry.flongNumber number,entry.fname name,entry.famount amount,purMode.fname_l2 purMode,(case when bill.pcId is null then 0 else 1 end) value1,bill.conName,bill.conDate,newcon.supplier supplier,(case when newcon.pcId is null then 0 else 1 end) value2,null rate1,isnull(bill.amount,0) value3,(case when area.area is null or area.area=0 then 0 else isnull(bill.amount,0)/area.area end) value4,price.account value5,null rate2,form.fname_l2 form,realForm.fname_l2 realForm,(case when form.fname_l2=realForm.fname_l2 and form.fname_l2='招标' then (case when bill.pcId is null then 0 else 1 end) else 0 end) value6,(case when form.fname_l2='招标' then (case when bill.pcId is null then 0 else 1 end) else 0 end) value7,null rate3,isnull(result.account,0) value8,null rate4,isnull(finish.account,0) value9,null rate5");
    	sb.append(" from T_INV_InviteMonthPlan invPlan left join T_INV_InviteMonthPlanEntrys entry on entry.fparentid=invPlan.fid left join T_FDC_CurProject curProject on curProject.fid=invPlan.fprojectId");
    	sb.append(" left join T_INV_InviteProjectEntries invEntry on invEntry.fprogrammingContractID=entry.fprogrammingContractID left join T_INV_InviteProject invProject on invProject.fid=invEntry.fparentId");
    	sb.append(" left join T_INV_InvitePurchaseMode purMode on purMode.fid=invProject.finvitePurchaseModeID left join T_INV_InviteForm realForm on realForm.fid=invProject.finviteFormId left join T_INV_InviteForm form on form.fid=entry.frequiredInviteFormId");
    	sb.append(" left join (select con.fid conId,con.fname conName,con.famount amount,con.fbookedDate conDate,con.fprogrammingContract pcId from T_CON_ContractBill con where con.fstate='4AUDITTED' and con.FContractPropert!='SUPPLY'");
    	sb.append(" union all select con.fid conId,con.fname conName,con.famount amount,con.fbookedDate conDate,entry.fProgrammingContractId pcId from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId left join t_con_contractBill con on con.fid=split.fcontractBillId where split.fcontractBillId is not null) bill on bill.pcId=invEntry.fprogrammingContractID");
    	sb.append(" left join (select supplier.fid supplierId,supplier.fname_l2 supplier,con.fprogrammingContract pcId from T_CON_ContractBill con left join t_bd_supplier supplier on supplier.fid=con.fpartBId where con.fstate='4AUDITTED' and con.FContractPropert!='SUPPLY'");
    	sb.append(" and con.fbookedDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    	sb.append(" and con.fbookedDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    	sb.append(" and NOT EXISTS(select ot.fpartBId from T_CON_ContractBill ot where ot.fstate='4AUDITTED'");
    	sb.append(" and ot.fbookedDate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'} and ot.fpartBId=con.fpartBId)");
    	
    	sb.append(" union all select supplier.fid supplierId,supplier.fname_l2 supplier,entry.fProgrammingContractId pcId from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId left join t_con_contractBill con on con.fid=split.fcontractBillId left join t_bd_supplier supplier on supplier.fid=con.fpartBId where split.fcontractBillId is not null");
    	sb.append(" and con.fbookedDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    	sb.append(" and con.fbookedDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    	sb.append(" and NOT EXISTS(select ot.fpartBId from T_CON_ContractBill ot where ot.fstate='4AUDITTED'");
    	sb.append(" and ot.fbookedDate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'} and ot.fpartBId=con.fpartBId)");
    	
    	sb.append(" ) newcon on newcon.pcId=invEntry.fprogrammingContractID");
    	sb.append(" left join (select ftotalSellArea area,FProjectID from T_AIM_PlanIndex planIndex left join T_AIM_MeasureCost measure on measure.fid=planIndex.FHeadID where FIsLastVersion=1 and fstate='4AUDITTED') area on area.FProjectID=curProject.fid");
    	sb.append(" left join (select count(*) account,result.FInviteProjectID invProjectId from T_INV_TenderAccepterResult result left join T_INV_TenderAccepterResultE entry on entry.fparentid=result.fid where entry.fhit=1 and entry.fisLowest=1 and fstate='4AUDITTED' group by result.FInviteProjectID) result on result.invProjectId=invProject.fid");
    	sb.append(" left join (select count(*) account,con.fprogrammingContract fprogrammingContractID  from T_INV_InviteMonthPlan invPlan left join T_INV_InviteMonthPlanEntrys entry on entry.fparentid=invPlan.fid left join T_CON_ContractBill con on con.fprogrammingContract=entry.fprogrammingContractID where con.fstate='4AUDITTED' and con.fauditTime<=entry.fcontractAuditDate");
    	if(curProject!=null){
    		sb.append(" and invPlan.fversion = (select max(fversion) from T_INV_InviteMonthPlan where fprojectID ='"+curProject.getId().toString()+"') and invPlan.fprojectID ='"+curProject.getId().toString()+"'");
    	}else{
    		sb.append(" and invPlan.fprojectID = 'null'");
    	}
    	sb.append(" group by con.fprogrammingContract) finish on finish.fprogrammingContractID=invEntry.fprogrammingContractID");
    	
    	sb.append(" left join (select count(*) account,con.fprogrammingContract fprogrammingContractID  from T_INV_InviteMonthPlan invPlan left join T_INV_InviteMonthPlanEntrys entry on entry.fparentid=invPlan.fid left join T_CON_ContractBill con on con.fprogrammingContract=entry.fprogrammingContractID where con.fstate='4AUDITTED' and con.famount<=entry.famount");
    	if(curProject!=null){
    		sb.append(" and invPlan.fversion = (select max(fversion) from T_INV_InviteMonthPlan where fprojectID ='"+curProject.getId().toString()+"') and invPlan.fprojectID ='"+curProject.getId().toString()+"'");
    	}else{
    		sb.append(" and invPlan.fprojectID = 'null'");
    	}
    	sb.append(" group by con.fprogrammingContract) price on price.fprogrammingContractID=invEntry.fprogrammingContractID");
    	sb.append(" where 1=1");
//    	sb.append(" where (purMode.fname_l2 is null or (purMode.fname_l2 not like '%战略采购%' and purMode.fname_l2 not like '%集中采购%'))");
    	if(curProject!=null){
    		sb.append(" and invPlan.fversion = (select max(fversion) from T_INV_InviteMonthPlan where fprojectID ='"+curProject.getId().toString()+"') and invPlan.fprojectID ='"+curProject.getId().toString()+"'");
    	}else{
    		sb.append(" and invPlan.fprojectID = 'null'");
    	}
    	sb.append(" order by entry.flongNumber");
    	RptRowSet rs = executeQuery(sb.toString(), null, ctx);
		params.setObject("rs", rs);
		return params;
    }
}