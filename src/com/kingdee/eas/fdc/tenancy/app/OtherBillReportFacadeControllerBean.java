package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Calendar;
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
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class OtherBillReportFacadeControllerBean extends AbstractOtherBillReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.OtherBillReportFacadeControllerBean");
    
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
	    initColoum(header,col,"sellProject",100,false);
	    initColoum(header,col,"build",50,false);
	    initColoum(header,col,"room",270,false);
	    initColoum(header,col,"conNumber",100,false);
	    initColoum(header,col,"contractNo",100,false);
	    initColoum(header,col,"conName",200,false);
	    initColoum(header,col,"customer",200,false);
	    initColoum(header,col,"startDate",75,false);
	    initColoum(header,col,"endDate",75,false);
	    initColoum(header,col,"leaseTime",120,false);
	    initColoum(header,col,"dept",100,false);
	    initColoum(header,col,"des",100,false);
	    initColoum(header,col,"remark",100,false);
	    initColoum(header,col,"moneyDefine",100,false);
	    initColoum(header,col,"amount",100,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"conId","项目分期","楼栋","房间","合同编码","手工合同号","合同名称","客户名称","合同起始日","合同终止日","收取周期（按月）","部门","保证金信息","备注","款项类别","金额"
		    	}
	    		,
	    		{
	    			"conId","项目分期","楼栋","房间","合同编码","手工合同号","合同名称","客户名称","合同起始日","合同终止日","收取周期（按月）","部门","保证金信息","备注","款项类别","金额"
		    	}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	String sellProject = (String) params.getObject("sellProject");
    	
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select other.fid conId,sp.fname_l2 sellProject,build.fname_l2 build,con.ftenRoomsDes room,");
    	sb.append(" other.fnumber conNumber,other.fcontractNo contractNo,other.fname conName,con.ftencustomerDes customer,other.fstartDate startDate,other.fendDate endDate,");
    	sb.append(" other.fleaseTime leaseTime,org.fname_l2 dept,other.fdes des,other.fdescription remark,md.fname_l2 moneyDefine,entry.FAppAmount amount from T_TEN_OtherBill other left join T_TEN_TenancyBill con on con.fid=other.FTenancyBillId left join T_ORG_Admin org on org.fid=other.FDept"); 
    	sb.append(" left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId left join t_she_room room on room.fid=roomEntry.froomId left join t_she_building build on build.fid=room.fbuildingId left join t_she_sellProject sp on sp.fid=con.fsellProjectid");
    	sb.append(" left join T_TEN_OtherBillEntry entry on entry.fheadId=other.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
    	sb.append(" where other.fstate='4AUDITTED'");
    	if(sellProject!=null&&!"".equals(sellProject)){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	sb.append(" order by other.fnumber,md.fnumber");
    	RptRowSet rs = executeQuery(sb.toString(), null, ctx);
		params.setObject("rs", rs);
		return params;
    }
}