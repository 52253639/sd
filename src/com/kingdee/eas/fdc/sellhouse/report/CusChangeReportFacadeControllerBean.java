package com.kingdee.eas.fdc.sellhouse.report;

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
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

import java.util.Date;
import java.util.Set;

public class CusChangeReportFacadeControllerBean extends AbstractCusChangeReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.CusChangeReportFacadeControllerBean");
    protected IRowSet _getPrintData(Context ctx, Set idSet) throws BOSException {
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
	    initColoum(header,col,"number",100,false);
	    initColoum(header,col,"name",100,false);
	    initColoum(header,col,"sellProjectName",150,false);
	    initColoum(header,col,"room",150,false);
	    initColoum(header,col,"oldField",150,false);
	    initColoum(header,col,"newField",150,false);
	    initColoum(header,col,"createTime",100,false);
	    initColoum(header,col,"creator",100,false);
	   
	    header.setLabels(new Object[][]{ 
	    		{
	    			"id","客户编码","客户名称","项目名称","房间","原字段信息","新字段信息","变更日期","变更人"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
	    
		RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		params.setObject("rowset", rowSet);
		params.setInt("count", rowSet.getRowCount());
		
		String sellProject = (String) params.getObject("sellProject");
    	String name=(String) params.getObject("name");
    	
		StringBuffer sb = new StringBuffer();
		sb.append(" select distinct * from(select preCustomer.fcustomerid customerid,customer.fname_l2 customerName,room.fname_l2 room,pre.fsellProjectId sellProjectId from t_she_prePurchaseCustomerEntry preCustomer left join t_she_prePurchaseManage pre on pre.fid=preCustomer.fheadid left join t_she_room room on room.fid=pre.froomid left join t_she_sheCustomer customer on customer.fid=preCustomer.fcustomerid");
		sb.append(" union all select purCustomer.fcustomerid customerid,customer.fname_l2 customerName,room.fname_l2 room,pur.fsellProjectId sellProjectId from t_she_purCustomerEntry purCustomer left join t_she_purchaseManage pur on pur.fid=purCustomer.fheadid left join t_she_room room on room.fid=pur.froomid left join t_she_sheCustomer customer on customer.fid=purCustomer.fcustomerid");
		sb.append(" union all select signCustomer.fcustomerid customerid,customer.fname_l2 customerName,room.fname_l2 room,sign.fsellProjectId sellProjectId from t_she_signCustomerEntry signCustomer left join t_she_signManage sign on sign.fid=signCustomer.fheadid left join t_she_room room on room.fid=sign.froomid left join t_she_sheCustomer customer on customer.fid=signCustomer.fcustomerid) t where 1=1 ");
		if(sellProject!=null){
    		sb.append(" and t.sellProjectId in("+sellProject+")");
    	}
		if(name!=null){
    		sb.append(" and t.customerName like('%"+name+"%')");
    	}
		RptRowSet roomRowSet = executeQuery(sb.toString(), null, from, len, ctx);
		params.setObject("roomRowSet", roomRowSet);
		return params;
    }
    protected String getSql(Context ctx,RptParams params){
    	String sellProject = (String) params.getObject("sellProject");
    	String name=(String) params.getObject("name");
    	String sellMan=(String) params.getObject("sellMan");
    	boolean isAll=(Boolean) params.getBoolean("isAll");
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select customer.fid id,customer.fnumber number,customer.fname_l2 name,sellProject.fname_l2 sellProjectName,'' room,change.foldfield oldfield,change.fnewfield newfield,change.fcreateTime createTime,creator.fname_l2 creator ");
    	sb.append(" from t_bdc_customerChangeLog change left join t_she_sheCustomer customer on change.fSheCustomerID=customer.fid ");
    	sb.append(" left join t_she_sellproject sellProject on customer.fsellProjectid=sellProject.fid ");
    	sb.append(" left join t_pm_user creator on change.fcreatorid=creator.fid ");
    	if(isAll){
    		sb.append(" left join (select distinct * from(select preCustomer.fcustomerid customerid from t_she_prePurchaseCustomerEntry preCustomer left join t_she_prePurchasemanage pre on pre.fid=preCustomer.fheadid where pre.fbizState not in('PreNullify','QRNullify')");
        	sb.append(" union all select purCustomer.fcustomerid customerid from t_she_purCustomerEntry purCustomer left join t_she_purchasemanage pur on pur.fid=purCustomer.fheadid where pur.fbizState not in('PurNullify','QRNullify')");
        	sb.append(" union all select signCustomer.fcustomerid customerid from t_she_signCustomerEntry signCustomer left join t_she_signmanage sign on sign.fid=signCustomer.fheadid where sign.fbizState not in('SignNullify','QRNullify'))) t on t.customerid=customer.fid");
    	}
    	sb.append(" where 1=1");
    	if(isAll){
    		sb.append(" and t.customerid is not null");
    	}
    	if(sellProject!=null){
    		sb.append(" and customer.fsellProjectId in("+sellProject+")");
    	}
		if(name!=null){
    		sb.append(" and customer.fname_l2 like('%"+name+"%')");
    	}
		if(sellMan!=null){
    		sb.append(" and change.fnewfield like('%"+sellMan+"%')");
    	}
    	sb.append(" order by sellProject.fnumber,customer.fnumber,change.fcreateTime");
		return sb.toString();
    }
}