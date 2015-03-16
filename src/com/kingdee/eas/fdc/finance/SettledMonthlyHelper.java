package com.kingdee.eas.fdc.finance;

import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.common.EASBizException;

/**
 * 月结关系表帮助类
 * @author xiaohong_shi
 *
 */
public class SettledMonthlyHelper {
	
	public static String getPeriodFilterSql(BOSObjectType bosType,String periodId,String prjId,String conId){
		if(bosType==null||periodId==null){
			throw new IllegalArgumentException("bosType and periodId cannt be null");
		}
		StringBuffer filter=new StringBuffer("select FObjectID from T_FNC_SettledMonthly where FEntityID='").append(bosType.toString());
		filter.append("' and ");
		filter.append("FSettlePeriodID='").append(periodId).append("'");
		if(prjId!=null){
			filter.append(" and ");
			filter.append("FCurProjectID='").append(prjId).append("'");
		}
		if(conId!=null){
			filter.append(" and ");
			filter.append("FContractID='").append(conId).append("'");
		}
		
		return filter.toString();
		
	}
	public static String getPeriodFilterSql(BOSObjectType bosType,String periodId){
		return getPeriodFilterSql(bosType, periodId, null, null);
	}
	
	public static String getPeriodExistsSql(String field,BOSObjectType bosType,String periodId,String prjId,String conId){
		if(bosType==null||periodId==null){
			throw new IllegalArgumentException("bosType and periodId cannt be null");
		}
		StringBuffer sql=new StringBuffer("exists (");
		sql.append("select 1 from T_FNC_SettledMonthly where FEntityID='").append(bosType.toString());
		sql.append("' and ");
		sql.append("FSettlePeriodID='").append(periodId).append("'");
		if(prjId!=null){
			sql.append(" and ");
			sql.append("FCurProjectID='").append(prjId).append("'");
		}
		if(conId!=null){
			sql.append(" and ");
			sql.append("FContractID='").append(conId).append("'");
		}
		sql.append(" and FObjectId=").append(field);
		sql.append(")");
		return sql.toString();
	}
	
	public static String getPeriodExistsSql(String field,BOSObjectType bosType,String periodId){
		return getPeriodExistsSql(field, bosType, periodId, null, null);
	}
	public static void addPeriodFilter(FilterInfo filter,String field,BOSObjectType bosType,String periodId,String prjId,String conId){
		if(filter==null){
			return;
		}
		filter.getFilterItems().add(new FilterItemInfo(field,getPeriodFilterSql(bosType, periodId, prjId, conId),CompareType.INNER));
	}
	public static void addPeriodFilter(FilterInfo filter,String field,BOSObjectType bosType,String periodId){
		addPeriodFilter(filter, field,bosType, periodId, null, null);
	}
	
	public static FilterInfo getPeriodFilter(String field,BOSObjectType bosType,String periodId,String prjId,String conId){
		FilterInfo filter=new FilterInfo();
		addPeriodFilter(filter, field,bosType, periodId, prjId, conId);
		return filter;
	}
	
	public static FilterInfo getPeriodFilter(String field,BOSObjectType bosType,String periodId){
		return getPeriodFilter(field,bosType, periodId, null,null);
	}
	
	public static boolean existObject(Context ctx,BOSObjectType bosType, String objectId) throws EASBizException, BOSException{
		Set objectSet=new HashSet();
		objectSet.add(objectId);
		return existObject(ctx, bosType, objectSet);
	}
	
	/**
	 * 是否月结表中存在object对应的数据
	 * @param ctx
	 * @param bosType
	 * @param objectSet
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static boolean existObject(Context ctx,BOSObjectType bosType, Set objectSet) throws EASBizException, BOSException{
		boolean exist = false;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("entityID",bosType.toString()));
		filter.getFilterItems().add(new FilterItemInfo("objectID",objectSet,CompareType.INCLUDE));
		if(ctx!=null)
			exist = SettledMonthlyFactory.getLocalInstance(ctx).exists(filter);
		else 
			exist = SettledMonthlyFactory.getRemoteInstance().exists(filter);
		return exist;
	}
	
	public static boolean existObject(Context ctx,BOSObjectType bosType, String objectId, String periodId) throws EASBizException, BOSException{
		boolean exist = false;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("entityID",bosType.toString()));
		filter.getFilterItems().add(new FilterItemInfo("objectID",objectId));
		filter.getFilterItems().add(new FilterItemInfo("settlePeriod.id",periodId));
		if(ctx!=null)
			exist = SettledMonthlyFactory.getLocalInstance(ctx).exists(filter);
		else 
			exist = SettledMonthlyFactory.getRemoteInstance().exists(filter);
		return exist;
	}
	
	public static void main(String args[]){
		final BOSObjectType type = new SettledMonthlyInfo().getBOSType();
		String periodId="periodId";
		String prjId="prjId";
		String conId="conId";
		System.out.println(getPeriodFilterSql(type, periodId));
		System.out.println(getPeriodFilterSql(type, periodId,prjId,null));
		System.out.println(getPeriodFilterSql(type, periodId,prjId,conId));
		
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",getPeriodFilterSql(type, periodId, prjId, conId),CompareType.INNER));
		System.out.println("filter:"+filter.toString());
		System.out.println(getPeriodFilter("fcontractid", type, periodId, prjId, conId));
		System.out.println(getPeriodExistsSql("id", type, periodId, null, null));
	}
}
