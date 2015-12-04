package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
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

import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.BgItemInfo;

import java.util.Date;
import java.util.Set;

public class AccActOnLoadBgFacadeControllerBean extends AbstractAccActOnLoadBgFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.AccActOnLoadBgFacadeControllerBean");
    protected IRowSet _getPrintData(Context ctx, Set idSet)throws BOSException
    {
        return null;
    }
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
	    initColoum(header,col,"bizDate",100,false);
	    initColoum(header,col,"number",100,false);
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"payAmount",100,false);
	    initColoum(header,col,"payDate",100,false);
	    initColoum(header,col,"actOnLoadAmount",100,false);
	    initColoum(header,col,"state",100,false);
	    initColoum(header,col,"orgUnit",100,false);
	    initColoum(header,col,"curProject",100,false);
	    initColoum(header,col,"supplier",100,false);
	    initColoum(header,col,"creator",100,false);
	    initColoum(header,col,"createTime",100,false);
	    initColoum(header,col,"auditor",100,false);
	    initColoum(header,col,"auditTime",100,false);
	    initColoum(header,col,"remark",100,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"id","业务日期","付款申请单编码","申请金额","已付金额","实际付款日期","占用预算金额","状态","所属组织","工程项目","收款单位","制单人","制单日期","审批人","审批日期","备注"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
		RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		params.setObject("rowset", rowSet);
		params.setInt("count", rowSet.getRowCount());
		return params;
    }
    protected String getSql(Context ctx,RptParams params){
    	StringBuffer id = (StringBuffer) params.getObject("sb");
    	String bgItemId=(String) params.getObject("bgItemId");
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select bill.fid id,bill.fbookedDate bizDate,bill.fnumber number,sum(entry.frequestAmount) amount,sum(isnull(entry.factPayAmount,0)) payAmount,bill.fpaydate payDate,(case when (bill.fhasClosed=1 or bill.fisBgControl=0 or bill.fstate not in('3AUDITTING','4AUDITTED')) then 0 else sum(entry.frequestAmount-isnull(entry.factPayAmount,0)) end) actOnLoadAmount,bill.fstate state,orgUnit.fname_l2 orgUnit,curProject.fname_l2 curProject,(case when supplier.fname_l2 is null then person.fname_l2 else supplier.fname_l2 end) supplier,creator.fname_l2 creator,bill.fcreateTime createTime,auditor.fname_l2 auditor,bill.fauditTime auditTime,bill.fmoneyDesc remark ");
    	sb.append(" from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
    	sb.append(" left join T_FDC_CurProject curProject on curProject.fid=bill.fcurProjectid left join T_ORG_BaseUnit orgUnit on orgUnit.fid=bill.forgUnitid ");
    	sb.append(" left join T_BD_Supplier supplier on supplier.fid=bill.fsupplierid left join T_BD_Person person on person.fid=bill.fpersonid ");
    	sb.append(" left join t_pm_user creator on creator.fid=bill.fcreatorid left join t_pm_user auditor on auditor.fid=bill.fauditorid ");
		if(id!=null){
    		sb.append(" left join(");
    		sb.append(id);
    		sb.append(" )t on t.id=bill.fid where t.id is not null");
    	}
		if(bgItemId!=null){
			sb.append(" and entry.fbgItemId='"+bgItemId+"'");
		}
    	sb.append(" group by bill.fisBgControl,bill.fid,bill.fbookedDate,bill.fnumber,bill.fpaydate,bill.fstate,bill.fhasClosed,orgUnit.fname_l2,curProject.fname_l2,(case when supplier.fname_l2 is null then person.fname_l2 else supplier.fname_l2 end),creator.fname_l2,bill.fcreateTime,auditor.fname_l2,bill.fauditTime,bill.fmoneyDesc ");
    	sb.append(" order by bill.fbookedDate,bill.fnumber ");
    	return sb.toString();
    }
}