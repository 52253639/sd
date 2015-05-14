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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class OrgUnitMonthPGReportFacadeControllerBean extends AbstractOrgUnitMonthPGReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.OrgUnitMonthPGReportFacadeControllerBean");
    
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
	    initColoum(header,col,"contractId",150,true);
	    initColoum(header,col,"company",200,false);
	    initColoum(header,col,"curProject",150,false);
	    initColoum(header,col,"bgNumber",100,false);
	    initColoum(header,col,"bgName",100,false);
	    initColoum(header,col,"number",150,false);
	    initColoum(header,col,"name",150,false);
	    initColoum(header,col,"contractAmount",100,false);
	    initColoum(header,col,"actAmount",150,false);
	    initColoum(header,col,"amount",150,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"contractId","公司","工程项目","预算项目编码","预算项目名称","编码","名称","合同金额","合同已付金额","计划支付金额"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	Integer year = (Integer)params.getObject("year");
    	Integer month = (Integer)params.getObject("month");
    	
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select contract.fid contractId,orgUnit.fname_l2 company,curProject.fname_l2 curProject,bgItem.fnumber bgNumber,bgItem.fname_l2 bgName,");
    	sb.append(" contract.fnumber number,gatherEntry.fname name,contract.famount contractAmount,pay.amount actAmount,dateEntry.famount amount from T_FNC_OrgUnitMonthPlanGather bill");
    	sb.append(" left join T_FNC_OrgUnitMonthPGEntry entry on bill.fid=entry.fheadId left join T_ORG_BaseUnit orgUnit on orgUnit.fid=bill.forgUnitId");
    	sb.append(" left join T_FNC_ProjectMonthPlanGather gather on gather.fid=entry.fsrcId left join t_fdc_curProject curProject on curProject.fid=gather.fcurProjectId");
    	sb.append(" left join T_FNC_ProjectMonthPlanGEntry gatherEntry on gatherEntry.fheadId=gather.fid left join t_con_contractBill contract on contract.fid=gatherEntry.contractBillId");
    	sb.append(" left join (select sum(FAmount) amount,fcontractbillid from t_cas_paymentbill where fbillstatus=15 group by fcontractbillid) pay on pay.fcontractBillid=contract.fid");
    	sb.append(" left join T_FNC_ProjectMonthPGDateEntry dateEntry on dateEntry.fheadentryId=gatherEntry.fid left join t_bg_bgitem bgItem on bgItem.fid=dateEntry.fbgItemId");
    	sb.append(" where bill.fstate='4AUDITTED' and bill.fIsLatest=1 and year(bill.fbizDate)="+year+" and month(bill.fbizDate)="+month);
    	sb.append(" and dateEntry.fyear="+year+" and dateEntry.fmonth="+month);
    	sb.append(" order by orgUnit.flongNumber,curProject.flongNumber,bgItem.flongNumber,contract.fnumber");
    	
    	RptRowSet rs = executeQuery(sb.toString(), null, ctx);
		params.setObject("rowset", rs);
		return params;
    }
}