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
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EnterpriseDevReportFacadeControllerBean extends AbstractEnterpriseDevReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.report.EnterpriseDevReportFacadeControllerBean");
    protected Map _getTableList(Context ctx, Map map)throws BOSException
    {
    	Map map1 = new HashMap();
    	map1 = getData(ctx,map);
    	
        return map1;
    }
    
    private Map getData(Context ctx,Map map) throws BOSException{
		Map map1 = new HashMap();
		List list = new ArrayList();
    	String sql = getSql(ctx,map);
    	
		try {
			IRowSet rowSet = DbUtil.executeQuery(ctx,sql.toString());
			while(rowSet.next()){
				Map mapList = new HashMap();
				if(rowSet.getString("accountView")!=null){
					mapList.put("accountView", rowSet.getString("accountView"));
					mapList.put("channelType", rowSet.getString("channelType"));
					mapList.put("mediaName", rowSet.getString("mediaName"));
					mapList.put("themeName", rowSet.getString("themeName"));
					mapList.put("themeDescription", rowSet.getString("themeDescription"));
					mapList.put("contentItem", rowSet.getString("contentItem"));
					mapList.put("planCost", rowSet.getBigDecimal("planCost"));
					mapList.put("factAmount", rowSet.getBigDecimal("factAmount"));
					mapList.put("contractAmount", rowSet.getBigDecimal("contractAmount"));
					mapList.put("payAmount", rowSet.getBigDecimal("payAmount"));
					mapList.put("contractName", rowSet.getString("contractName"));
					mapList.put("contractID", rowSet.getString("contractID"));
					if(rowSet.getString("contractID")!=null){
						if(rowSet.getString("contractBillID")!=null){
							mapList.put("contractType", "contractBill");//文本合同
						}else{
							mapList.put("contractType", "contractWithoutText");
						}
					}else{
						mapList.put("contractType", "");
					}

					list.add(mapList);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		map1.put("list", list);
		return map1;
    }
    
    private String getSql(Context ctx,Map map){
    	StringBuilder sql = new StringBuilder();
    	
    	sql.append(" select ca.FName_l2 accountView,tt.FName_l2 channelType,t.FName_l2 mediaName,es.FThemeName themeName, ");
    	sql.append(" es.FThemeDescription themeDescription,p.FProceeding contentItem,p.FPlanCost planCost,ese.FactAmount factAmount, ");
    	sql.append(" ese.FContractAmount contractAmount,ese.FPayAmount payAmount,ese.FContractName contractName,ese.FContractNumberID  contractID, ");
    	sql.append(" cb.FID contractBillID ");
    	sql.append(" from T_MAR_EnterpriseSchemeEntry ese ");
    	sql.append(" left join T_MAR_EnterpriseScheme es on es.FID = ese.FParentID ");
    	sql.append(" left join T_MAR_EnterpriseSellPlan p on p.FID = ese.FSellPlanID ");
    	sql.append(" left join T_FDC_CostAccount ca on ca.FID = p.FSubjectID ");
    	sql.append(" left join T_MAR_ChannelTypeTree tt on tt.FID = p.FClassifyID ");
    	sql.append(" left join T_MAR_ChannelType t on t.FID = p.FMediaNameID ");
    	sql.append(" left join T_SHE_SellProject sp on es.FSellProjectID = sp.FID ");
    	sql.append(" left join T_CON_ContractBill cb on cb.FID = ese.FContractNumberID ");
    	sql.append(" where 1=1 ");
    	sql.append(" and ese.FIsEnd = 'FINISH' ");//取事项已完成状态
    	if(map.get("sellProject")!=null){
    		SellProjectInfo sellProject = (SellProjectInfo)map.get("sellProject");
    		if(sellProject!=null){
    			String sellProjectIds = getSellProjectIds(ctx,sellProject.getId().toString());
    			if(!sellProjectIds.equals("")){
            		sql.append(" and es.FSellProjectID in("+sellProjectIds+") ");
    			}else{
        			sql.append(" and es.FSellProjectID ='"+sellProject.getId().toString()+"' ");
    			}
    		}
    	}
		if(map.get("fullOrgUnit")!=null){
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
	    	sql.append(" and sp.FOrgUnitID in ("+orgIds+") ");
		}
    	if(map.get("fromBeginDate")!=null){
    		sql.append(" and  p.FStartTime >= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) map.get("fromBeginDate")))+ "'} ");
    	}
    	if(map.get("toBeginDate")!=null){
    		sql.append(" and  p.FStartTime <= {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) map.get("toBeginDate")))+ "'} ");
    	}
    	if(map.get("fromEndDate")!=null){
    		sql.append(" and  p.FEndTime >= {ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin((Date) map.get("fromEndDate")))+ "'} ");
    	}
    	if(map.get("toEndDate")!=null){
    		sql.append(" and  p.FEndTime <= {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd((Date) map.get("toEndDate")))+ "'} ");
    	}
    	sql.append(" order by ca.FName_l2,ese.FContractName ");//按费用科目、合同名称排序
    	
    	return sql.toString();
    }
    
    public String getSellProjectIds(Context ctx,String sellProjectId){
    	String sellProjectIds = "";
    	if(sellProjectId!=null){
    		try {
				SellProjectInfo info = SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo(" where id = '"+sellProjectId+"' ");
				if(info!=null){
					SellProjectCollection coll = SellProjectFactory.getLocalInstance(ctx).getSellProjectCollection("where parent.id = '"+sellProjectId+"' ");
					if(coll.size()>0){
						for(int i =0;i<coll.size();i++){
							SellProjectInfo sellInfo = coll.get(i);
							sellProjectIds = sellProjectIds +"'"+sellInfo.getId()+"',";
						}
						sellProjectIds = sellProjectIds.substring(0,sellProjectIds.length() - 1);
					}
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    	
    	return sellProjectIds;
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
		   	filter.getFilterItems().add(new FilterItemInfo("IsSaleOrgUnit", "1", CompareType.EQUALS));
		
		   	entityView.setFilter(filter);
		   	
			AdminOrgUnitCollection collection = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(entityView);
			for (int index = 0; index < collection.size(); index ++)
				orgUnitSet.add(collection.get(index).getId().toString());
			
			orgUnitSet.add(applyUnitId);
			
			return orgUnitSet;
	   }
}