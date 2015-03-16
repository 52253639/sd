package com.kingdee.eas.fdc.market.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CalculateTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IndexVersionFactory;
import com.kingdee.eas.fdc.basedata.IndexVersionInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseFactory;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectInfo;
import com.kingdee.eas.fdc.market.ValueInputCollection;
import com.kingdee.eas.fdc.market.ValueInputEntryInfo;
import com.kingdee.eas.fdc.market.ValueInputFactory;
import com.kingdee.eas.fdc.market.ValueInputInfo;
import com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListCollection;
import com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class DynamicValueReportFacadeControllerBean extends AbstractDynamicValueReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.DynamicValueReportFacadeControllerBean");
    
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
	    initColoum(header,col,"orgLongNumber",100,true);
	    initColoum(header,col,"pbLongNumber",100,true);
	    initColoum(header,col,"opLongNumber",100,true);
	    initColoum(header,col,"orgUnit",100,false);
	    initColoum(header,col,"project",100,false);
	    initColoum(header,col,"operationPhases",100,false);
	    initColoum(header,col,"batch",100,false);
	    initColoum(header,col,"productType",100,false);
	    initColoum(header,col,"accountArea",100,false);
	    initColoum(header,col,"hqAmount",140,false);
	    initColoum(header,col,"account",100,false);
	    initColoum(header,col,"area",100,false);
	    initColoum(header,col,"price",100,false);
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"saccount",100,false);
	    initColoum(header,col,"sarea",100,false);
	    initColoum(header,col,"sprice",100,false);
	    initColoum(header,col,"samount",100,false);
	    initColoum(header,col,"daccount",100,false);
	    initColoum(header,col,"darea",100,false);
	    initColoum(header,col,"dprice",100,false);
	    initColoum(header,col,"damount",100,false);
	    initColoum(header,col,"paccount",100,false);
	    initColoum(header,col,"parea",100,false);
	    initColoum(header,col,"pprice",100,false);
	    initColoum(header,col,"pamount",100,false);
	    header.setLabels(new Object[][]{
	    		{
	    			"orgLongNumber","pbLongNumber","opLongNumber","区域","项目","分期","计划交房批次","产品类型","套均面积","红旗版规划总货值(万元)","动态总货值","动态总货值","动态总货值"
	    			,"动态总货值","已售货值","已售货值","已售货值","已售货值","待售货值","待售货值","待售货值","待售货值","规划货值","规划货值","规划货值","规划货值"
	    		}
	    		,
	    		{
	    			"orgLongNumber","pbLongNumber","opLongNumber","区域","项目","分期","计划交房批次","产品类型","套均面积","红旗版规划总货值(万元)","套数(个)","面积(㎡)","均价(元/㎡)"
	    			,"金额(万元)","套数(个)","面积(㎡)","均价(元/㎡)","金额(万元)","套数(个)","面积(㎡)","均价(元/㎡)","金额(万元)","套数(个)","面积(㎡)","均价(元/㎡)","金额(万元)"
	    		
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
    	StringBuffer sb=new StringBuffer();
    	String project = (String) params.getObject("project");
    	
    	sb.append(" select org.flongNumber orgLongNumber,pb.fnumber pbLongNumber,op.flongnumber opLongNumber,org.fdisplayName_l2 orgUnit,pb.fname project,entry.fproject operationPhases,entry.fbatch batch,entry.fproductType productType,round((isnull(sum(sarea),0)+isnull(sum(darea),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else farea end),0))/(isnull(sum(saccount),0)+isnull(sum(daccount),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else faccount end),0)),2) accountArea,round(hq.hqAmount,0) hqAmount,");
    	sb.append(" isnull(sum(saccount),0)+isnull(sum(daccount),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else faccount end),0) account,round(isnull(sum(sarea),0)+isnull(sum(darea),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else farea end),0),2) area,round((case fcalculateType when '10' then (case when isnull(sum(sarea),0)+isnull(sum(darea),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else farea end),0)=0 then 0 else (isnull(sum(samount),0)+isnull(sum(damount),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else entry.famount end),0))/(isnull(sum(sarea),0)+isnull(sum(darea),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else farea end),0)) end) else (case when (isnull(sum(saccount),0)+isnull(sum(daccount),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else faccount end),0))=0 then 0 else (isnull(sum(samount),0)+isnull(sum(damount),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else entry.famount end),0))/(isnull(sum(saccount),0)+isnull(sum(daccount),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else faccount end),0)) end) end ),0) price,round((isnull(sum(samount),0)+isnull(sum(damount),0)+isnull(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else entry.famount end),0))/10000,0) amount,");
    	sb.append(" sum(saccount) saccount,round(sum(sarea),2) sarea,round((case when sum(sarea)=0 then 0 else sum(samount)/sum(sarea) end),0) sprice,round(sum(samount)/10000,0) samount,");
    	sb.append(" sum(daccount) daccount,round(sum(darea),2) darea,round((case when sum(darea)=0 then 0 else sum(damount)/sum(darea) end),0) dprice,round(sum(damount)/10000,0) damount,");
    	sb.append(" sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else faccount end) paccount,round(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else farea end),2) parea,round((case fcalculateType when '10' then (case when sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else farea end)=0 then 0 else sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else entry.famount end)/sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else farea end) end) else (case when sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else faccount end)=0 then 0 else sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else entry.famount end)/sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else faccount end) end) end ),0) pprice,round(sum(case when st.buildId is not null then 0 when dt.buildId is not null then 0 else entry.famount end)/10000,0) pamount");
		sb.append(" from T_MAR_ValueInputEntry entry left join T_FDC_ProductType pt on pt.fid=entry.fproductTypeId left join T_BD_OperationPhases op on op.fid=entry.fprojectid left join T_MAR_ValueInput vi on vi.fid=entry.fheadId left join T_FDC_ProjectBase pb on pb.fid=vi.FProjectId left join T_ORG_BaseUnit org on org.fid=vi.forgUnitid left join T_BD_IndexVersion version on version.fid=vi.findexVersionId");
		sb.append(" left join (select banEntry.FBanBasedataEntryId buildId,room.fproductTypeId productTypeId,count(*) saccount,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end) sarea,sum(sign.fcontractTotalAmount) samount");
		sb.append(" from t_she_signManage sign left join t_she_room room on room.fid=sign.froomId left join T_SHE_BanBasedataEntryList banEntry on banEntry.fheadId=room.fbuildingId where sign.fbizState in('SignApple','SignAudit') and banEntry.FBanBasedataEntryId is not null group by banEntry.FBanBasedataEntryId,room.fproductTypeId) st on st.buildId=entry.fbuildId and st.productTypeId=entry.fproductTypeId");
		sb.append(" left join (select banEntry.FBanBasedataEntryId buildId,room.fproductTypeId productTypeId,count(*) daccount,sum(case room.FSellAreaType when 'planning' then room.fPlanBuildingArea when 'presell' then room.FBuildingArea else room.fActualBuildingArea end) darea,sum(room.fbaseStandardPrice) damount");
		sb.append(" from t_she_room room left join T_SHE_BanBasedataEntryList banEntry on banEntry.fheadid=room.fbuildingid where room.FSellState!='Sign' and banEntry.FBanBasedataEntryId is not null group by banEntry.FBanBasedataEntryId,room.fproductTypeId) dt on dt.buildId=entry.fbuildId and dt.productTypeId=entry.fproductTypeId");
		sb.append(" left join (select max(version.fnumber) indexVersion,vi.fprojectid from T_MAR_ValueInput vi left join T_BD_IndexVersion version on version.fid=vi.findexVersionId where vi.fisLatest=1 group by vi.fprojectid) maxVi on maxVi.indexVersion=version.fnumber and maxVi.fprojectId=vi.fprojectId");
		sb.append(" left join (select round(sum(entry.famount)/10000,2)hqAmount,entry.fprojectid,entry.FProductTypeId from T_MAR_ValueInput vi left join T_BD_IndexVersion version on version.fid=vi.findexVersionId left join T_MAR_ValueInputEntry entry on entry.fheadid=vi.fid where vi.fisLatest=1 and version.fnumber='001' group by entry.fprojectid,entry.FProductTypeId) hq on hq.fprojectid=entry.fprojectid and hq.FProductTypeId=entry.FProductTypeId");
		sb.append(" where maxVi.indexVersion is not null and vi.fisLatest=1");
		if(project!=null&&!"".equals(project)){
    		sb.append(" and op.fid in("+project+")");
    	}else{
    		sb.append(" and op.fid = 'null'");
    	}
		sb.append(" group by org.fdisplayName_l2,pb.fname,entry.fproject,entry.fbatch,entry.fproductType,org.flongNumber,pb.fnumber,op.flongnumber,fcalculateType,pt.fnumber,hq.hqAmount order by org.flongNumber,pb.fnumber,op.flongnumber,entry.fbatch,pt.fnumber");
		
		return sb.toString();
    }
	protected void _photo(Context ctx) throws BOSException, EASBizException {
		Map map=new HashMap();
		ValueInputCollection pbVaCol=ValueInputFactory.getLocalInstance(ctx).getValueInputCollection("select orgUnit.*,project.*,entry.* from where isLatest=1 order by indexVersion.number desc");
		for(int i=0;i<pbVaCol.size();i++){
			if(map.get(pbVaCol.get(i).getProject().getId().toString())==null){
				map.put(pbVaCol.get(i).getProject().getId().toString(), pbVaCol.get(i));
			}else{
				ValueInputInfo pd=(ValueInputInfo) map.get(pbVaCol.get(i).getProject().getId().toString());
				int number=Integer.parseInt(pd.getIndexVersion().getNumber().replaceFirst("^0*", ""));
				int nowNumber=Integer.parseInt(pbVaCol.get(i).getIndexVersion().getNumber().replaceFirst("^0*", ""));
				if(nowNumber>number){
					map.put(pbVaCol.get(i).getProject().getId().toString(), pbVaCol.get(i));
				}
			}
		}
		Object[] key = map.keySet().toArray();
		for (int k = 0; k < key.length; k++) {
			ValueInputInfo spValueInputInfo=(ValueInputInfo) map.get(key[k]);
			ValueInputInfo info=new ValueInputInfo();
			Date now=SysUtil.getAppServerTime(ctx);
			info.setBizDate(now);
			
			info.setProject(spValueInputInfo.getProject());
			info.setOrgUnit(spValueInputInfo.getOrgUnit());
			
			IndexVersionInfo indexVersion=IndexVersionFactory.getLocalInstance(ctx).getIndexVersionInfo("select * from where number='005'");
			info.setIndexVersion(indexVersion);
			info.setName("动态版_"+FDCDateHelper.DateToString(now));
			
			for(int i=0;i<spValueInputInfo.getEntry().size();i++){
				ValueInputEntryInfo entry=spValueInputInfo.getEntry().get(i);
				entry.setPriceHead(null);
				entry.setId(null);
				if(entry.getBuildId()!=null&&entry.getProductTypeId()!=null){
					try {
						getSellHouseAmount(ctx,entry.getBuildId().toString(),entry.getProductTypeId().toString(),entry);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(entry.getCalculateType().equals(CalculateTypeEnum.NOW)){
						entry.setPrice(FDCHelper.divide(entry.getAmount(), entry.getArea(), 2, BigDecimal.ROUND_HALF_UP));
					}else{
						entry.setPrice(FDCHelper.divide(entry.getAmount(), entry.getAccount(), 2, BigDecimal.ROUND_HALF_UP));
					}
				}
				info.getEntry().add(entry);
			}
			
			ValueInputCollection vaCol=ValueInputFactory.getLocalInstance(ctx).getValueInputCollection("select id from where project.id='"+info.getProject().getId().toString()+"' and indexVersion.id='"+info.getIndexVersion().getId().toString()+"'");
			
			info.setVersion(vaCol.size()+1);
			info.setState(FDCBillStateEnum.SAVED);
			info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
			
			info.setCreator(null);
			info.setCreateTime(null);
			info.setAuditor(null);
			info.setAuditTime(null);
			info.setLastUpdateUser(null);
			info.setLastUpdateTime(null);
			
			getValueInputNumber(ctx,info);
			if(info.getNumber()==null) info.setNumber(info.getName()); 
			
			IObjectPK pk=ValueInputFactory.getLocalInstance(ctx).submit(info);
			ValueInputFactory.getLocalInstance(ctx).audit(BOSUuid.read(pk.toString()));
		}
	}
	protected void getSellHouseAmount(Context ctx,String id,String productTypeId,ValueInputEntryInfo entry) throws SQLException, BOSException, EASBizException{
    	BanBasedataEntryListCollection col=BanBasedataEntryListFactory.getLocalInstance(ctx).getBanBasedataEntryListCollection("select head.id from where banBasedataEntry.id='"+id+"'");
    	if(col.size()>0){
//    		FilterInfo filter=new FilterInfo();
//        	filter.getFilterItems().add(new FilterItemInfo("room.building.id",col.get(0).getHead().getId().toString()));
//        	filter.getFilterItems().add(new FilterItemInfo("head.isExecuted",Boolean.TRUE));
//        	if(RoomPriceAdjustEntryFactory.getRemoteInstance().exists(filter)){
        		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
        		IRowSet rowSet = null;
        		builder.appendSql(" select sum(t.account) account,sum(t.area) area,sum(t.amount) amount from (select banEntry.FBanBasedataEntryId buildId,room.fproductTypeId productTypeId,count(*) account,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end) area,sum(sign.fcontractTotalAmount) amount");
        		builder.appendSql(" from t_she_signManage sign left join t_she_room room on room.fid=sign.froomId left join T_SHE_BanBasedataEntryList banEntry on banEntry.fheadId=room.fbuildingId where sign.fbizState in('SignApple','SignAudit') and banEntry.FBanBasedataEntryId is not null group by banEntry.FBanBasedataEntryId,room.fproductTypeId");
        		builder.appendSql(" union all select banEntry.FBanBasedataEntryId buildId,room.fproductTypeId productTypeId,count(*) account,sum(case room.fsellType when 'PlanningSell' then room.fPlanBuildingArea when 'PreSell' then room.FBuildingArea else room.fActualBuildingArea end) area,sum(room.fbaseStandardPrice) amount");
        		builder.appendSql(" from t_she_room room left join T_SHE_BanBasedataEntryList banEntry on banEntry.fheadid=room.fbuildingid where room.FSellState!='Sign' and banEntry.FBanBasedataEntryId is not null group by banEntry.FBanBasedataEntryId,room.fproductTypeId) t where t.buildId='"+id+"' and t.productTypeId='"+productTypeId+"'");
        		
        		rowSet = builder.executeQuery();
        		while (rowSet.next()) {
        			entry.setAmount(rowSet.getBigDecimal("amount"));
        			entry.setArea(rowSet.getBigDecimal("area"));
        			entry.setAccount(rowSet.getInt("account"));
        		}
//        	}
    	}
    }
	private void getValueInputNumber(Context ctx,ValueInputInfo info) throws EASBizException, BOSException{
    	ICodingRuleManager iCodingRuleManager= CodingRuleManagerFactory.getLocalInstance(ctx);
		OrgUnitInfo orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		String retNumber ="";
		if(iCodingRuleManager.isExist(info, orgUnit.getId().toString()))
		     retNumber = iCodingRuleManager.getNumber(info, orgUnit.getId().toString());
		if(retNumber!=null && !"".equals(retNumber)){
			info.setNumber(retNumber);
		}
    }
}