package com.kingdee.eas.fdc.tenancy.app;

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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class LinkReportFacadeControllerBean extends AbstractLinkReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.LinkReportFacadeControllerBean");
    
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
	    initColoum(header,col,"number",150,false);
	    initColoum(header,col,"name",150,false);
	    initColoum(header,col,"personName",100,false);
	    initColoum(header,col,"sex",50,false);
	    initColoum(header,col,"phone",100,false);
	    initColoum(header,col,"mail",200,false);
	    initColoum(header,col,"address",200,false);
	    initColoum(header,col,"job",100,false);
	    initColoum(header,col,"ah",100,false);
	    
	    header.setLabels(new Object[][]{ 
	    		{
	    		    "id","客户代码","客户名称","联系人姓名","性别","联系电话","邮箱","通信地址","职务","爱好"
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
    	String sellProject = (String) params.getObject("sellProject");
		StringBuffer sb = new StringBuffer();
    	sb.append(" select customer.fid id,customer.fnumber number,customer.fname_l2 name,link.fpersonName personName,link.fsex sex,link.fphone phone,link.fmail mail,link.faddress address,link.fjob job,link.fah ah");
    	sb.append(" from T_SHE_FDCCustomer customer left join T_SHE_LinkmanEntry link on link.fheadid=customer.fid");
    	sb.append(" where 1=1");
    	if(sellProject!=null&&!"".equals(sellProject)){
    		sb.append(" and customer.fprojectid in("+sellProject+")");
    	}else{
    		sb.append(" and customer.fprojectid in('null')");
    	}
    	sb.append(" order by customer.fnumber");
    	return sb.toString();
    }
}