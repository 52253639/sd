package com.kingdee.eas.fdc.sellhouse.app;

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

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class EnrollmentUIFacadeControllerBean extends AbstractEnrollmentUIFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.EnrollmentUIFacadeControllerBean");
   protected IRowSet _getInisder(Context ctx, String saleLongNumber,
			String beginQueryDate, String endQueryDate) throws BOSException,
			EASBizException {
	   	StringBuffer sb=new StringBuffer();
	   	String sql="select insider.forgunitid,sale.fname_l2 as orgunitname,insider.fviplevelid,viplevel.fnumber as viplevelnumber" +
					",insider.fenterwayid,enterway.fnumber as enterwaynumber" +
					",count(insider.fenterwayid) as count from t_ins_insider as insider " +
					"left join t_org_sale as sale on insider.forgunitid = sale.fid " +
					"left join t_ins_viplevel as viplevel on insider.fviplevelid = viplevel.fid " +
					"left join t_ins_enterway as enterway on insider.fenterwayid = enterway.fid " +
					"where sale.flongnumber like '"+saleLongNumber+"%' ";
	   	sb.append(sql);
	   	if(beginQueryDate!=null && !beginQueryDate.trim().equals("")){
			sb.append(" and insider.fcreatetime >= {ts '"+beginQueryDate+"'}");
		}
		if(beginQueryDate!=null && !beginQueryDate.trim().equals("")){
			sb.append(" and insider.fcreatetime < {ts '"+endQueryDate+"'}");
		}	
	   	String sql2="group by insider.forgunitid,sale.fname_l2,insider.fviplevelid,viplevel.fnumber,insider.fenterwayid,enterway.fnumber " +
					"order by forgunitid  ";
		sb.append(sql2);
		return DbUtil.executeQuery(ctx, sb.toString());
	}
}