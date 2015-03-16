package com.kingdee.eas.fdc.sellhouse.report;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.sellhouse.InteractionTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;


public class CustomerVisitSumReportFacadeControllerBean extends AbstractCustomerVisitSumReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.CustomerVisitSumReportFacadeControllerBean");

	/* (non-Javadoc)
	 * @see com.kingdee.eas.framework.report.app.CommRptBaseControllerBean#_init(com.kingdee.bos.Context, com.kingdee.eas.framework.report.util.RptParams)
	 */
	protected RptParams _init(Context ctx, RptParams params) throws BOSException, EASBizException {
	    RptParams pp = new RptParams();
	    return pp;
	}

	protected List _getTableList(Context ctx, RptParams params) throws BOSException {
		List list = new ArrayList();
		Map map = new HashMap();
		if(params.getObject("sellProject")!=null){
			IRowSet rowSet = DbUtil.executeQuery(ctx,getSql(params));
			try {
				while(rowSet.next()){
					map.put("telCount", rowSet.getInt("tel"));
					map.put("visitCount",rowSet.getInt("visit"));
					map.put("telToVisit", rowSet.getInt("telToVisit"));
					map.put("visitToDeal", rowSet.getInt("visitToDeal"));
					map.put("cardCount", rowSet.getInt("card"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			list.add(map);
		}
		return list;
	}
	
	public String getSql(RptParams params){
		StringBuilder where = new StringBuilder();
		SellProjectInfo sellProject = (SellProjectInfo)params.getObject("sellProject");
		if(sellProject!=null){
			where.append(" and cct.fsellprojectid = '"+sellProject.getId()+"' ");
		}
		where.append(" and cc.fid is not null group by cct.fcommerceChanceid");
		
		StringBuilder dateWhere = new StringBuilder();
		dateWhere.append(" having 1=1");
		if(params.getObject("beginDate")!=null){
			dateWhere.append(" and min(cct.FTrackDate) >= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) params.getObject("beginDate")))+ "'} ");
		}
		if(params.getObject("endDate")!=null){
			dateWhere.append(" and min(cct.FTrackDate) <{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) params.getObject("endDate")))+ "'}");
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select max(case t.name when 'tel' then t.amount else 0 end) tel,max(case t.name when 'visit' then t.amount else 0 end) visit,max(case t.name when 'telToVisit' then t.amount else 0 end) telToVisit,max(case t.name when 'visitToDeal' then t.amount else 0 end) visitToDeal,max(case t.name when 'card' then t.amount else 0 end) card from");
		sb.append(" ((select 'tel' name,count(*) amount from (");
		sb.append(" select cct.fcommerceChanceid from t_she_commerceChanceTrack cct left join (select min(cct.ftrackDate) ftrackDate ,cct.fcommerceChanceid from t_she_commerceChanceTrack cct where cct.finteractionType='INTERVIEW' group by cct.fcommerceChanceid) cct1");
		sb.append(" on cct.fcommerceChanceid=cct1.fcommerceChanceid left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='TELEPHONE' and (cct.ftrackDate<cct1.ftrackDate or cct1.ftrackDate is null)");
		sb.append(where);
		sb.append(dateWhere);
		sb.append(" )");
		sb.append(" union all");
		sb.append(" select 'visit' name,count(*) amount from (");
		sb.append(" select cct.fcommerceChanceid from t_she_commerceChanceTrack cct left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='INTERVIEW'");
		sb.append(where);
		sb.append(dateWhere);
		sb.append(" )");
		sb.append(" union all");
		sb.append(" select 'telToVisit' name,count(*) amount from (");
		sb.append(" select cct.fcommerceChanceid from t_she_commerceChanceTrack cct left join (select cct.ftrackDate,cct.fcommerceChanceid from t_she_commerceChanceTrack cct where cct.finteractionType='TELEPHONE') cct1");
		sb.append(" on cct.fcommerceChanceid=cct1.fcommerceChanceid left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cct.finteractionType='INTERVIEW'");
		sb.append(where);
		sb.append(dateWhere);
		sb.append(" and min(cct.ftrackDate)>min(cct1.ftrackDate)");
		if(params.getObject("beginDate")!=null){
			sb.append(" and min(cct1.FTrackDate) >= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) params.getObject("beginDate")))+ "'} ");
		}
		if(params.getObject("endDate")!=null){
			sb.append(" and min(cct1.FTrackDate) <{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) params.getObject("endDate")))+ "'}");
		}
		sb.append(" )");
		sb.append(" union all");
		sb.append(" select 'visitToDeal' name,count(*) amount from (");
		sb.append(" select cct.fcommerceChanceid from T_SHE_CommerceChanceTrack cct left join (select cct.ftrackDate,cct.fcommerceChanceid from t_she_commerceChanceTrack cct where cct.finteractionType='INTERVIEW') cct1" );
		sb.append(" on cct.fcommerceChanceid=cct1.fcommerceChanceid left join (select cct.ftrackDate,cct.fcommerceChanceid from t_she_commerceChanceTrack cct where cct.FTrackEvent = 'QuitRoom') cct2 on cct.fcommerceChanceid=cct2.fcommerceChanceid left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where (cct.FTrackEvent = 'Purchase' or cct.FTrackEvent = 'Sign')");
		sb.append(where);
		sb.append(dateWhere);
		sb.append(" and min(cct.ftrackDate)>min(cct1.ftrackDate)");
		if(params.getObject("beginDate")!=null){
			sb.append(" and min(cct1.FTrackDate) >= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) params.getObject("beginDate")))+ "'} ");
		}
		if(params.getObject("endDate")!=null){
			sb.append(" and min(cct1.FTrackDate) <{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) params.getObject("endDate")))+ "'}");
		}
		sb.append(" and ((min(cct2.ftrackDate)<=min(cct.ftrackDate)");
		if(params.getObject("beginDate")!=null){
			sb.append(" and min(cct2.FTrackDate) >= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) params.getObject("beginDate")))+ "'} ");
		}
		if(params.getObject("endDate")!=null){
			sb.append(" and min(cct2.FTrackDate) <{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) params.getObject("endDate")))+ "'}");
		}
		sb.append(" )or min(cct2.ftrackDate) is null)");
		sb.append(" )");
		sb.append(" union all");
		sb.append(" select 'card' name,count(*) amount from (");
		sb.append(" select cct.fcommerceChanceid from t_she_commerceChanceTrack cct ");
		sb.append(" left join T_SHE_CommerceChanceAssistant cca on cca.FID=cct.FCommerceLevelID left join t_she_commerceChance cc on cc.fid=cct.fcommerceChanceid where cca.fname_l2 = 'VIP¿Í»§'");
    	sb.append(where);
    	sb.append(" )");
		sb.append(" )t)");
		return sb.toString();
	}
}