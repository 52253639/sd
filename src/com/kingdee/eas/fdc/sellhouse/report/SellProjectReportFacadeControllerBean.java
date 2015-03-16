package com.kingdee.eas.fdc.sellhouse.report;

import org.apache.log4j.Logger;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;


public class SellProjectReportFacadeControllerBean extends AbstractSellProjectReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.SellProjectReportFacadeControllerBean");

	protected Map _getTableData(Context ctx, Map map) throws BOSException {
		Map map1 = new HashMap();
		map1.put("list", getData(ctx,map));
		
		return map1;
	}
	
	protected List getData(Context ctx,Map map){
    	RptParams  params = (RptParams) map.get("params");
    	String sellProject = (String) params.getObject("sellProject");
    	Date beginDate = (Date)params.getObject("beginDate");
    	Date endDate =   (Date)params.getObject("endDate");

		List list = new ArrayList();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" select  sm.FSellProjectID,Sp.FName_l2 sellProject,sum(sm.FBulidingArea) bulidingArea ," );
			sql.append(" sum(sm.FRoomArea) roomArea,sum(sm.FSellAmount) sellAmount,sum(t1.revAmount) revAmount  from T_SHE_SignManage sm ");
			sql.append(" left join T_SHE_SellProject sp on sp.FID = sm.FSellProjectID  " );
			sql.append(" left join (select base.fbillid,sum(tbov.factRevAmount) revAmount from t_she_transaction base " );
			sql.append(" left join t_she_tranBusinessOverView tbov on tbov.fheadid=base.fid " );
			sql.append(" left join t_she_moneyDefine md on md.fid=tbov.fmoneyDefineId where tbov.ftype='Pay' " );
			sql.append(" and md.fmoneyType in ('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') and tbov.fBusinessName!='Î¥Ô¼½ð' " );
			sql.append(" group by base.fbillid) t1 on t1.fbillid = sm.fid where 1=1 " );
			if(sellProject!=null){
		    	sql.append(" and sm.FsellProjectId in ("+sellProject+") ");
			}
			if(beginDate!=null){
				sql.append(" and sm.FBizDate >= {"+params.getObject("beginDate")+"} ");
			}
			if(endDate!=null){
				sql.append(" and sm.FBizDate <= {"+params.getObject("endDate")+"} ");
			}
			sql.append(" group by sm.FSellProjectID,Sp.FName_l2 " ); 

			IRowSet rowSet = DbUtil.executeQuery(ctx,sql.toString());
			while(rowSet.next()){
				Map mapList = new HashMap();
				mapList.put("sellProject", rowSet.getString("sellProject"));
				mapList.put("saleBuildingArea", rowSet.getBigDecimal("bulidingArea"));
				mapList.put("saleRoomArea", rowSet.getBigDecimal("roomArea"));
				mapList.put("sellAmount", rowSet.getBigDecimal("sellAmount"));
				mapList.put("revAmount", rowSet.getBigDecimal("revAmount"));
				
				list.add(mapList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return list;
	}
}