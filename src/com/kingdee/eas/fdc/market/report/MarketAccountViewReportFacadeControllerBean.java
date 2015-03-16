package com.kingdee.eas.fdc.market.report;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MarketAccountViewReportFacadeControllerBean extends AbstractMarketAccountViewReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.report.MarketAccountViewReportFacadeControllerBean");
    protected Map _getTableList(Context ctx, Map map)throws BOSException
    {
    	Map map1 = new HashMap();
    	map1 = getData(ctx,map);
        return map1;
    }
    private Map getData(Context ctx,Map map) throws BOSException{
		Map map1 = new HashMap();
		List list = new ArrayList();
    	String sql = getSql(map);
		try {
			IRowSet rowSet = DbUtil.executeQuery(ctx,sql.toString());
			while(rowSet.next()){
				Map mapList = new HashMap();
				mapList.put("id", rowSet.getString("id"));
				mapList.put("number", rowSet.getString("transLongNumber"));
				mapList.put("name", rowSet.getString("name"));
				mapList.put("level", rowSet.getString("level1"));
				mapList.put("isLeaf", rowSet.getString("isLeaf"));
				mapList.put("parent.id", rowSet.getString("parentid"));
				mapList.put("curProject.id", rowSet.getString("curProjectid"));
				mapList.put("fullOrgUnit.id", rowSet.getString("fullOrgUnitid"));
				
				if(rowSet.getString("id")!=null){
					//RptParams para = (RptParams)map.get("params");
					if(map.get("yearOrMonth")!=null&&map.get("yearOrMonth").equals("year")){
						//取年度金额
						List yearList = getYearAmount(ctx,map,rowSet.getString("longNumber"),rowSet.getString("id"),rowSet.getString("curProjectid"));
						mapList.put("yearList",yearList );
					}else{
						List monthList = getMonthAmount(ctx,map,rowSet.getString("longNumber"),rowSet.getString("id"),rowSet.getString("curProjectid"));
						mapList.put("monthList",monthList );
					}
				}
				list.add(mapList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		map1.put("list", list);
		
		return map1;
    }
    private String getSql(Map map){
    	//RptParams para = (RptParams)params.get("params");
    	StringBuilder sql = new StringBuilder();
    	
    	sql.append(" select account.FNumber number,REPLACE(account.FLongNumber, '!', '.') transLongNumber,account.FLongNumber longNumber,account.FName_l2 name,  ");
    	sql.append(" account.FLevel level1,account.FIsLeaf isLeaf,account.FId id,account.FParentID parentid, ");
    	sql.append(" org.FId fullOrgUnitid,project.FId curProjectid  ");
    	sql.append(" from  T_FDC_CostAccount  account ");
    	sql.append(" left join T_FDC_CurProject project ON account.FCurProject = project.FID ");
    	sql.append(" left join T_FDC_CostAccount parent ON account.FParentID = parent.FID ");
    	sql.append(" left join T_ORG_BaseUnit org ON account.FFullOrgUnit = org.FID ");
    	sql.append(" where 1=1 ");
    	sql.append(" and account.FLongNumber like '5002%' ");//只取科目502开头的科目
    	if(map.get("fullOrgUnit")!=null){
            OrgStructureInfo oui = (OrgStructureInfo)map.get("fullOrgUnit");
            if(oui != null && oui.getUnit() != null){
                FullOrgUnitInfo info = oui.getUnit();
            	sql.append(" and account.FFullOrgUnit = '"+info.getId().toString()+"' ");
            }
    	}
    	if(map.get("curProject")!=null){
    		CurProjectInfo projectInfo = (CurProjectInfo)map.get("curProject");
        	sql.append(" and account.FCurProject = '"+projectInfo.getId().toString()+"' ");
    	}
    	sql.append(" order by  REPLACE(account.FLongNumber, '!', '.') asc ");
    	
    	return sql.toString();
    }
    
    public List getYearAmount(Context ctx,Map map,String accLongNumber,String accountViewId,String curProjectId) throws BOSException{
    	List yearList = new ArrayList();
    	String sql = getYearSql(ctx,map,accLongNumber,accountViewId,curProjectId);
		try {
			IRowSet rowSet = DbUtil.executeQuery(ctx,sql.toString());
			while(rowSet.next()){
				Map mapList = new HashMap();
				mapList.put("year", rowSet.getString("year"));
				mapList.put("yearAmount", rowSet.getString("yearAmount"));
				
				yearList.add(mapList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return yearList;
    }
    public List getMonthAmount(Context ctx,Map map,String accLongNumber,String accountViewId,String curProjectId) throws BOSException{
    	List monthList = new ArrayList();
    	String sql = getMonthSql(ctx,map,accLongNumber,accountViewId,curProjectId);
		try {
			IRowSet rowSet = DbUtil.executeQuery(ctx,sql.toString());
			while(rowSet.next()){
				Map mapList = new HashMap();
				mapList.put("year", rowSet.getString("year"));
				mapList.put("month", rowSet.getString("month"));
				mapList.put("monthAmount", rowSet.getString("monthAmount"));
				
				monthList.add(mapList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return monthList;
    }
    public String getYearSql(Context ctx,Map map,String accLongNumber,String accountViewId,String curProjectId){
    	//RptParams para = (RptParams)params.get("params");
    	
    	//取得集团查询Id
    	String orgIds ="";
    	if(map.get("fullOrgUnit")!=null){
            OrgStructureInfo oui = (OrgStructureInfo)map.get("fullOrgUnit");
            if(oui != null && oui.getUnit() != null){
                FullOrgUnitInfo info = oui.getUnit();
                orgIds = getOrgIds(ctx,info.getId().toString());
            }
    	}
    	if(orgIds.equals("")){
    		orgIds = "''";
    	}
    	
    	int beginYear = Integer.parseInt((map.get("beginYear")).toString());
    	int endYear = Integer.parseInt((map.get("endYear")).toString());
    	StringBuilder sql = new StringBuilder();
    	sql.append(" select u.year,sum(u.yearAmount) yearAmount from ");
    	sql.append(" (select YEAR(c.FCreateTime) year,sum(cc.Famount) yearAmount  ");
    	sql.append(" from T_CON_ContractCostSplitEntry cc  ");
    	sql.append(" left join T_CON_ContractCostSplit c on cc.FParentID = c.FId ");
    	sql.append(" left join T_FDC_CostAccount ca on cc.FCostAccountID = ca.FId ");
    	
    	sql.append(" left join T_FDC_CurProject cp on ca.FCurProject = cp.FId ");
    	sql.append(" where 1=1 ");//UsFbPtOtRMCHiw3b69X/mYQj/24=
    	if(map.get("fullOrgUnit")!=null){
        	sql.append(" and ca.FLongNumber = '"+accLongNumber+"' ");
            sql.append(" and cp.FFullOrgUnit in ("+orgIds+") ");
    	}else{
    		if(!curProjectId.equals("")){
    			String curProjectIds = getCurProjectIds(ctx,curProjectId);
    			if(!curProjectIds.equals("")){
    				sql.append(" and ca.FLongNumber = '"+accLongNumber+"' ");
            		sql.append(" and ca.FCurProject in("+curProjectIds+") ");
    			}else{
        			sql.append(" and ca.Fid ='"+accountViewId+"' ");
    			}
    		}else{
    			sql.append(" and ca.Fid ='"+accountViewId+"' ");
    		}
    	}
    	if(map.get("beginYear")!=null){
    		sql.append(" and YEAR(c.FCreateTime) >= '"+beginYear+"' ");
    	}
    	if(map.get("endYear")!=null){
    		sql.append(" and YEAR(c.FCreateTime) <= '"+endYear+"' ");
    	}
    	sql.append(" group by YEAR(c.FCreateTime) ");
    	sql.append(" union all ");
    	sql.append(" select YEAR(p.FCreateTime) year,sum(ps.FAmount) yearAmount ");
    	sql.append(" from T_FNC_PaymentSplitEntry ps  ");
    	sql.append(" left join T_FNC_PaymentSplit p on ps.FParentID = p.FId ");
    	sql.append(" left join T_FDC_CostAccount ca on ps.FCostAccountID = ca.FId ");
    	
    	sql.append(" left join T_FDC_CurProject cp on ca.FCurProject = cp.FId  ");
    	sql.append(" where 1 =1 ");
    	if(map.get("fullOrgUnit")!=null){
        	sql.append(" and ca.FLongNumber = '"+accLongNumber+"' ");
            sql.append(" and cp.FFullOrgUnit in ("+orgIds+") ");
    	}else{
    		if(!curProjectId.equals("")){
    			String curProjectIds = getCurProjectIds(ctx,curProjectId);
    			if(!curProjectIds.equals("")){
    				sql.append(" and ca.FLongNumber = '"+accLongNumber+"' ");
            		sql.append(" and ca.FCurProject in("+curProjectIds+") ");
    			}else{
        			sql.append(" and ca.Fid ='"+accountViewId+"' ");
    			}
    		}else{
    			sql.append(" and ca.Fid ='"+accountViewId+"' ");
    		}
    	}
    	if(map.get("beginYear")!=null){
    		sql.append(" and YEAR(p.FCreateTime) >= '"+beginYear+"' ");
    	}
    	if(map.get("endYear")!=null){
    		sql.append(" and YEAR(p.FCreateTime) <= '"+endYear+"' ");
    	}
    	sql.append(" group by YEAR(p.FCreateTime)  ");
    	sql.append(" ) u group by u.year order by u.year desc ");    	
    	
    	return sql.toString();
    }
    public String getMonthSql(Context ctx,Map map,String accLongNumber,String accountViewId,String curProjectId){
    	//RptParams para = (RptParams)params.get("params");
    	
    	//取得集团查询Id
    	String orgIds ="";
    	if(map.get("fullOrgUnit")!=null){
            OrgStructureInfo oui = (OrgStructureInfo)map.get("fullOrgUnit");
            if(oui != null && oui.getUnit() != null){
                FullOrgUnitInfo info = oui.getUnit();
                orgIds = getOrgIds(ctx,info.getId().toString());
            }
    	}
    	if(orgIds.equals("")){
    		orgIds = "''";
    	}
    	
    	int beginYear = Integer.parseInt((map.get("beginYear")).toString());
    	int endYear = Integer.parseInt((map.get("endYear")).toString());
    	int beginMonth = Integer.parseInt((map.get("beginMonth")).toString());
    	int endMonth = Integer.parseInt((map.get("endMonth")).toString());
    	StringBuilder sql = new StringBuilder();
    	sql.append(" select u.yearMonth,u.year,u.month,sum(u.monthAmount) monthAmount from ");
    	sql.append(" (select CONCAT(CONCAT(CONVERT(varchar,YEAR(c.FCreateTime)),'-'),CONVERT(varchar,MONTH(c.FCreateTime))) yearMonth,YEAR(c.FCreateTime) year,MONTH(c.FCreateTime) month,sum(cc.Famount) monthAmount "); 
    	sql.append(" from T_CON_ContractCostSplitEntry cc ");
    	sql.append(" left join T_CON_ContractCostSplit c on cc.FParentID = c.FId ");
    	sql.append(" left join T_FDC_CostAccount ca on cc.FCostAccountID = ca.FId ");
    	sql.append(" left join T_FDC_CurProject cp on ca.FCurProject = cp.FId ");
    	sql.append(" where 1=1 ");//UsFbPtOtRMCHiw3b69X/mYQj/24=
    	if(map.get("fullOrgUnit")!=null){
        	sql.append(" and ca.FLongNumber = '"+accLongNumber+"' ");
            sql.append(" and cp.FFullOrgUnit in ("+orgIds+") ");
    	}else{
    		if(!curProjectId.equals("")){
    			String curProjectIds = getCurProjectIds(ctx,curProjectId);
    			if(!curProjectIds.equals("")){
    				sql.append(" and ca.FLongNumber = '"+accLongNumber+"' ");
            		sql.append(" and ca.FCurProject in("+curProjectIds+") ");
    			}else{
        			sql.append(" and ca.Fid ='"+accountViewId+"' ");
    			}
    		}else{
    			sql.append(" and ca.Fid ='"+accountViewId+"' ");
    		}
    	}
    	if(map.get("beginYear")!=null&&map.get("beginMonth")!=null){
    		sql.append(" and CONCAT(CONCAT(CONVERT(varchar,YEAR(c.FCreateTime)),'-'),CONVERT(varchar,MONTH(c.FCreateTime))) >= '"+beginYear+"-"+beginMonth+"' ");
    	}
    	if(map.get("endYear")!=null&&map.get("endMonth")!=null){
    		sql.append(" and CONCAT(CONCAT(CONVERT(varchar,YEAR(c.FCreateTime)),'-'),CONVERT(varchar,MONTH(c.FCreateTime))) <= '"+endYear+"-"+endMonth+"' ");
    	}
    	sql.append(" group by CONCAT(CONCAT(CONVERT(varchar,YEAR(c.FCreateTime)),'-'),CONVERT(varchar,MONTH(c.FCreateTime))),YEAR(c.FCreateTime),MONTH(c.FCreateTime) ");
    	sql.append(" union all ");
    	sql.append(" select CONCAT(CONCAT(CONVERT(varchar,YEAR(p.FCreateTime)),'-'),CONVERT(varchar,MONTH(p.FCreateTime))) yearMonth,YEAR(p.FCreateTime) year,MONTH(p.FCreateTime) month,sum(ps.FAmount) monthAmount ");
    	sql.append(" from T_FNC_PaymentSplitEntry ps ");
    	sql.append(" left join T_FNC_PaymentSplit p on ps.FParentID = p.FId ");
    	sql.append(" left join T_FDC_CostAccount ca on ps.FCostAccountID = ca.FId ");
    	sql.append(" left join T_FDC_CurProject cp on ca.FCurProject = cp.FId ");
    	sql.append(" where 1=1 ");//UsFbPtOtRMCHiw3b69X/mYQj/24=
    	if(map.get("fullOrgUnit")!=null){
        	sql.append(" and ca.FLongNumber = '"+accLongNumber+"' ");
            sql.append(" and cp.FFullOrgUnit in ("+orgIds+") ");
    	}else{
    		if(!curProjectId.equals("")){
    			String curProjectIds = getCurProjectIds(ctx,curProjectId);
    			if(!curProjectIds.equals("")){
    				sql.append(" and ca.FLongNumber = '"+accLongNumber+"' ");
            		sql.append(" and ca.FCurProject in("+curProjectIds+") ");
    			}else{
        			sql.append(" and ca.Fid ='"+accountViewId+"' ");
    			}
    		}else{
    			sql.append(" and ca.Fid ='"+accountViewId+"' ");
    		}
    	}
    	if(map.get("beginYear")!=null&&map.get("beginMonth")!=null){
    		sql.append(" and CONCAT(CONCAT(CONVERT(varchar,YEAR(p.FCreateTime)),'-'),CONVERT(varchar,MONTH(p.FCreateTime))) >= '"+beginYear+"-"+beginMonth+"' ");
    	}
    	if(map.get("endYear")!=null&&map.get("endMonth")!=null){
    		sql.append(" and CONCAT(CONCAT(CONVERT(varchar,YEAR(p.FCreateTime)),'-'),CONVERT(varchar,MONTH(p.FCreateTime))) <= '"+endYear+"-"+endMonth+"' ");
    	}
    	sql.append(" group by CONCAT(CONCAT(CONVERT(varchar,YEAR(p.FCreateTime)),'-'),CONVERT(varchar,MONTH(p.FCreateTime))),YEAR(p.FCreateTime),MONTH(p.FCreateTime) ");
    	sql.append(" ) u group by u.yearMonth,u.year,u.month ");
    	sql.append(" order by u.yearMonth desc ");
    	
    	return sql.toString();
    }
    
    public String getCurProjectIds(Context ctx,String curProjectId){
    	String curProjectIds = "";
    	if(curProjectId!=null){
    		try {
				CurProjectInfo info = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(" where id = '"+curProjectId+"' ");
				if(info!=null){
					CurProjectCollection coll = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection("where parent.id = '"+curProjectId+"' ");
					if(coll.size()>0){
						for(int i =0;i<coll.size();i++){
							CurProjectInfo curInfo = coll.get(i);
							curProjectIds = curProjectIds +"'"+curInfo.getId()+"',";
						}
						curProjectIds = curProjectIds.substring(0,curProjectIds.length() - 1);
					}
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    	
    	return curProjectIds;
    }
    
    public String getOrgIds(Context ctx,String fullOrgUnitId){
    	String orgIds ="";
		try {
			if(!fullOrgUnitId.equals("")){
				Set set = getAllChildUnitByParentUnit(ctx,fullOrgUnitId);//"00000000-0000-0000-0000-000000000000CCE7AED4"
				Iterator it = set.iterator();
				while (it.hasNext()) {            
					orgIds = orgIds + "'"+it.next()+"',";
				}
				orgIds = orgIds.substring(0,orgIds.length() - 1);
				System.out.println(orgIds);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return orgIds;
    }
    
    /* 拿到当前组织的所有下级组织的ID
	    * @param applyUnitId		父组织单元Id
	    * @return orgUnitSet		返回组织单元Id集合
	    * @throws BOSException
	    * @throws EASBizException 
	    */
	   public static Set getAllChildUnitByParentUnit(Context ctx,String applyUnitId)
				throws BOSException, EASBizException {
		   	Set orgUnitSet = new HashSet();
		   	
		   	AdminOrgUnitInfo applyUnit = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitInfo(new ObjectUuidPK(applyUnitId));
		   	
		   	EntityViewInfo entityView = new EntityViewInfo();
		   	FilterInfo filter = new FilterInfo();
		   	filter.getFilterItems().add(new FilterItemInfo("longNumber", "%"+applyUnit.getLongNumber()+"%", CompareType.LIKE));
//		   	if(applyUnit.getNumber().equals("B")){//房地产
//			    	filter.getFilterItems().add(new FilterItemInfo("level", "4", CompareType.EQUALS));
//		   	}else{
//			    	filter.getFilterItems().add(new FilterItemInfo("level", "5", CompareType.EQUALS));
//		   	}
		   	filter.getFilterItems().add(new FilterItemInfo("IsSaleOrgUnit", "1", CompareType.EQUALS));
		
		   	entityView.setFilter(filter);
		   	
			AdminOrgUnitCollection collection = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(entityView);
			for (int index = 0; index < collection.size(); index ++)
				orgUnitSet.add(collection.get(index).getId().toString());
			
			orgUnitSet.add(applyUnitId);
			
			return orgUnitSet;
	   }
}