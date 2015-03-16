package com.kingdee.eas.fdc.market.app;

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
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.market.VersionTypeEnum;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class DynamicTotalValueFacadeControllerBean extends AbstractDynamicTotalValueFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.DynamicTotalValueFacadeControllerBean");

	protected RptParams _init(Context ctx, RptParams params) throws BOSException, EASBizException {
	    RptParams pp = new RptParams();
	    return pp;
	}

	protected RptParams _createTempTable(Context ctx, RptParams params) throws BOSException, EASBizException {
	    RptTableHeader header = new RptTableHeader();
	    RptTableColumn col = null;
	    col = new RptTableColumn("seq");
	    col.setWidth(100);
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("projectid");
	    col.setWidth(100);
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("projectlongnumber");
	    col.setWidth(100);
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("projectname");
	    col.setWidth(100);
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("productConstitute");
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("typename");
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("areaRange");
	    col.setWidth(100);
	    header.addColumn(col);
	    
	    //投资版测算货值
	    col = new RptTableColumn("firstarea");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("firstcount");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("firstprice");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("firstamount");
	    col.setWidth(120);
	    header.addColumn(col);
	    
	    //最新版测算货值
	    col = new RptTableColumn("latestarea");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("latestcount");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("latestprice");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("latestamount");
	    col.setWidth(120);
	    header.addColumn(col);
	    
	    //项目合计
	    col = new RptTableColumn("totalarea");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("totalcount");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("totalprice");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("totalamount");
	    col.setWidth(120);
	    header.addColumn(col);
	    
	    //当年以前
	    col = new RptTableColumn("beforeyeararea");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("beforeyearcount");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("beforeyearprice");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("beforeyearamount");
	    col.setWidth(120);
	    header.addColumn(col);
	    
	    //当年
	    col = new RptTableColumn("yeararea");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("yearcount");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("yearprice");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("yearamount");
	    col.setWidth(120);
	    header.addColumn(col);
	    
	    //已售部分合计
	    col = new RptTableColumn("hadsellarea");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("hadsellcount");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("hadsellprice");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("hadsellamount");
	    col.setWidth(120);
	    header.addColumn(col);
	    
	    //已取预证未定价
	    col = new RptTableColumn("nopricearea");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("nopricecount");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("nopriceprice");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("nopriceamount");
	    col.setWidth(120);
	    header.addColumn(col);
	    
	    //已取预证已定价
	    col = new RptTableColumn("hadpricearea");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("hadpricecount");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("hadpriceprice");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("hadpriceamount");
	    col.setWidth(120);
	    header.addColumn(col);
	    col = new RptTableColumn("syhzjj");
	    col.setWidth(120);
	    header.addColumn(col);
	    
	    header.setLabels(new Object[][]{ 
	    		{
	    			"序列号",
	    			"项目ID",
	    		 	"项目长编码",
	    		 	"项目名称",
	    		 	"产品构成",
	    		 	"产品类型",
	    		 	"面积段",
	    		 	"投资版测算货值",
	    		 	"投资版测算货值",
	    		 	"投资版测算货值",
	    		 	"投资版测算货值",
	    		 	"最新版测算货值",
	    		 	"最新版测算货值",
	    		 	"最新版测算货值",
	    		 	"最新版测算货值",
	    		 	"动态总货值",
	    		 	"动态总货值",
	    		 	"动态总货值",
	    		 	"动态总货值",
	    		 	"当年以前",
	    		 	"当年以前",
	    		 	"当年以前",
	    		 	"当年以前",
	    		 	"当年",
	    		 	"当年",
	    		 	"当年",
	    		 	"当年",
	    		 	"已售部分合计",
	    		 	"已售部分合计",
	    		 	"已售部分合计",
	    		 	"已售部分合计",
	    		 	"待售部分",
	    		 	"待售部分",
	    		 	"待售部分",
	    		 	"待售部分",
	    		 	"待售部分",
	    		 	"待售部分",
	    		 	"待售部分",
	    		 	"待售部分",
	    		 	"剩余货值均价测算"
	    		}
	    		,{
	    			"序列号",
	    			"项目ID",
	    			"项目长编码",
	    		 	"项目名称",
	    		 	"产品构成",
	    		 	"产品类型",
	    		 	"面积段",
	    		 	"投资版测算货值",
	    		 	"投资版测算货值",
	    		 	"投资版测算货值",
	    		 	"投资版测算货值",
	    		 	"最新版测算货值",
	    		 	"最新版测算货值",
	    		 	"最新版测算货值",
	    		 	"最新版测算货值",
	    		 	"动态总货值",
	    		 	"动态总货值",
	    		 	"动态总货值",
	    		 	"动态总货值",
	    		 	"当年以前",
	    		 	"当年以前",
	    		 	"当年以前",
	    		 	"当年以前",
	    		 	"当年",
	    		 	"当年",
	    		 	"当年",
	    		 	"当年",
	    		 	"已售部分合计",
	    		 	"已售部分合计",
	    		 	"已售部分合计",
	    		 	"已售部分合计",
	    		 	"已取预证未定价",
	    		 	"已取预证未定价",
	    		 	"已取预证未定价",
	    		 	"已取预证未定价",
	    		 	"已取预证已定价",
	    		 	"已取预证已定价",
	    		 	"已取预证已定价",
	    		 	"已取预证已定价",
	    		 	"剩余货值均价测算"
	    		}
	    		,{
	    			"序列号",
	    			"项目ID",
	    			"项目长编码",
	    		 	"项目名称",
	    		 	"产品构成",
	    		 	"产品类型",
	    		 	"面积段",
	    		 	"面积[㎡]",//投资版测算货值
	    		 	"套数[套]",
	    		 	"均价[万元]",
	    		 	"金额[万元]",
	    		 	"面积[㎡]",//最新版测算货值
	    		 	"套数[套]",
	    		 	"均价[万元]",
	    		 	"金额[万元]",
	    		 	"面积[㎡]",//项目合计
	    		 	"套数[套]",
	    		 	"均价[万元]",
	    		 	"金额[万元]",
	    		 	"面积[㎡]",//当年以前
	    		 	"套数[套]",
	    		 	"均价[万元]",
	    		 	"金额[万元]",
	    		 	"面积[㎡]",//当年
	    		 	"套数[套]",
	    		 	"均价[万元]",
	    		 	"金额[万元]",
	    		 	"面积[㎡]",//已售部分合计
	    		 	"套数[套]",
	    		 	"均价[万元]",
	    		 	"金额[万元]",
	    		 	"面积[㎡]",//已取预证未定价
	    		 	"套数[套]",
	    		 	"均价[万元]",
	    		 	"金额[万元]",
	    		 	"面积[㎡]",//已取预证已定价
	    		 	"套数[套]",
	    		 	"均价[万元]",
	    		 	"金额[万元]",
	    		 	"剩余货值均价测算"
	    		}
	    },true);
	    RptParams result = new RptParams();
	    result.setObject("header", header);
	    return result;
	}
	
	protected RptParams _query(Context ctx, RptParams params, int from, int len)
    throws BOSException, EASBizException
{
	RptRowSet rowSet = executeQuery(getSql(params), null, from, len, ctx);
	params.setObject("rowset", rowSet);
	params.setInt("count", rowSet.getRowCount());
	return params;
}
private void getAreaRangeSql(StringBuffer sb,RptParams params,String area){
	String amountSql = "case tar.productConstitute when '车位' then isnull(isnull(tar.count,0)-(isnull(yqzwdj.count,0)+isnull(wqzydj.count,0)+isnull(yqzydj.count,0)+isnull(ys.count,0)),0)*tar.unitprice else isnull(isnull(tar.area,0)-(isnull(yqzwdj.area,0)+isnull(wqzydj.area,0)+isnull(yqzydj.area,0)+isnull(ys.area,0)),0)*tar.unitprice end" +
		"+isnull(wqzydj.amount,0)+case tar.productConstitute when '车位' then round(tar.unitprice*isnull(yqzwdj.count,0),2) else round(tar.unitprice*isnull(yqzwdj.area,0),2) end+isnull(yqzydj.amount,0)+isnull(ys.amount,0)";
	sb.append(" select tar.fseq seq,tar.projectid as tarprojectid,tar.projectlongnumber as tarprojectlongnumber,tar.projectname as tarprojectname,tar.productConstitute as productConstitute,");
	sb.append(" tar.typename as tartypename,tar.areaRange areaRange,");
	//目标测算货值
	sb.append(" tar.area as tararea,tar.count as tarcount,tar.unitprice as tarunitprice,tar.amount as taramount,");
	//年度测算货值
	sb.append(" year.area as yeararea,year.count as yearcount,year.unitprice as yearunitprice,year.amount as yearamount,");
	//动态总货值
	sb.append(" tar.area as zjarea,tar.count as zjcount,");
	sb.append(" case when tar.productConstitute='车位' and tar.count=0 then 0  when tar.productConstitute!='车位' and tar.area=0 then 0 else (" +
			  " case tar.productConstitute when '车位'  then ");
	sb.append(" round((");
	sb.append(amountSql);//总金额=1)	未取预证未定价+2)未取预证已定价+3)已取预证未定价+4)已取预证已定价+5)已售
	sb.append(" -0)/(tar.count),2) ");
	sb.append(" else ");
	sb.append(" round((");
	sb.append(amountSql);
	sb.append(" -0)/(tar.area),2)" +
			  " end) ");
	sb.append(" end zjunitprice,");
	sb.append(amountSql+" as zjamount,");
	//未取预证未定价
	sb.append(" isnull(tar.area,0)-(isnull(yqzwdj.area,0)+isnull(wqzydj.area,0)+isnull(yqzydj.area,0)+isnull(ys.area,0)) as wqzarea,");
	sb.append(" isnull(tar.count,0)-(isnull(yqzwdj.count,0)+isnull(wqzydj.count,0)+isnull(yqzydj.count,0)+isnull(ys.count,0)) as wqzcount,");
	sb.append(" tar.unitprice as wqzunitprice,");
	sb.append(" case tar.productConstitute when '车位' then isnull(isnull(tar.count,0)-(isnull(yqzwdj.count,0)+isnull(wqzydj.count,0)+isnull(yqzydj.count,0)+isnull(ys.count,0)),0)*tar.unitprice else isnull(isnull(tar.area,0)-(isnull(yqzwdj.area,0)+isnull(wqzydj.area,0)+isnull(yqzydj.area,0)+isnull(ys.area,0)),0)*tar.unitprice end as wqzamount,");
	//未取预证已定价
	sb.append(" wqzydj.area as wqzydjarea,wqzydj.count as wqzydjcount,case tar.productConstitute when '车位' then round(wqzydj.amount/wqzydj.count,2) else wqzydj.unitprice end as wqzydjunitprice,wqzydj.amount as wqzydjamount,");
	//已取预证未定价
	sb.append(" yqzwdj.area as yqzwdjarea,yqzwdj.count as yqzwdjcount,tar.unitprice as yqzwdjunitprice,case tar.productConstitute when '车位' then round(tar.unitprice*yqzwdj.count,2) else round(tar.unitprice*yqzwdj.area,2) end as yqzwdjamount,");
	//已取预证已定价
	sb.append(" yqzydj.area as yqzydjarea,yqzydj.count as yqzydjcount,case tar.productConstitute when '车位' then round(yqzydj.amount/yqzydj.count,2) else yqzydj.unitprice end as yqzydjunitprice,yqzydj.amount as yqzydjamount,");
	//已售
	sb.append(" ys.area as ysarea,ys.count as yscount,case tar.productConstitute when '车位' then round(ys.amount/ys.count,2) else ys.unitprice end as ysunitprice,ys.amount as ysamount");
	sb.append(" from (");
	sb.append(" "+getTargetSql(params,area));
	sb.append(" ) tar");
	sb.append(" left join (");
	sb.append(" "+getYearSql(params,area));
	sb.append(" ) year");
	sb.append(" on year.projectid=tar.projectid and tar.typename=year.typename ");
	sb.append(" left join (");
	sb.append(" "+getYQZWDJSql(params,area));
	sb.append(" ) yqzwdj");
	sb.append(" on yqzwdj.projectid=tar.projectid and tar.typename=yqzwdj.typename ");
	sb.append(" left join (");
	sb.append(" "+getWQZYDJSql(params,area));
	sb.append(" ) wqzydj ");
	sb.append(" on wqzydj.projectid=tar.projectid and tar.typename=wqzydj.typename ");
	sb.append(" left join (");
	sb.append(" "+getYQZYDJSql(params,area));
	sb.append(" ) yqzydj ");
	sb.append(" on yqzydj.projectid=tar.projectid and tar.typename=yqzydj.typename ");
	sb.append(" left join (");
	sb.append(" "+getYSSql(params,area));
	sb.append(" ) ys ");
	sb.append(" on tar.projectid=ys.projectid and tar.typename=ys.typename ");
	sb.append(" where tar.projectid='"+params.getString("projectId")+"' ");
}
protected String getSql(RptParams params){
	StringBuffer sb = new StringBuffer();
	sb.append(" select * from ( ");
	getAreaRangeSql(sb,params,null);
	
	sb.append(" union all ");
	
	sb.append(" (");
	getAreaRangeSql(sb,params,"≤100㎡");
	sb.append(" )");
	
	sb.append(" union all ");
	
	sb.append(" (");
	getAreaRangeSql(sb,params,"100㎡<@≤120㎡");
	sb.append(" )");
	
	sb.append(" union all ");
	
	sb.append(" (");
	getAreaRangeSql(sb,params,"120㎡<@≤140㎡");
	sb.append(" )");
	
	sb.append(" union all ");
	
	sb.append(" (");
	getAreaRangeSql(sb,params,"140㎡<");
	sb.append(" )");
	sb.append(" )t order by t.seq,t.tartypename");
	return sb.toString();
}
/**
 * 目标总货值
 * */
protected String getTargetSql(RptParams params,String area){
	StringBuffer sb = new StringBuffer();
	sb.append(" select project.fid as projectid,project.fname_l2 as projectname,project.fnumber as projectnumber,project.flongnumber as projectlongnumber,");
	sb.append(" case entry.FProductConstitute when '2business' then '商业' when '1house' then '住宅' when '2publicBuild' then '公共配套建筑' when '3parking' then '车位' else ' ' " +
			  " end productConstitute,");
	sb.append(" type.fname_l2 as typename,entry.FAreaRange areaRange,entry.FAcreage as area,");
	sb.append(" entry.FQuantity as count,entry.FPrice as unitprice,entry.FSumAmount as amount,");
	sb.append(" entry.fseq");
	sb.append(" from T_SHE_SellProject project ");
	sb.append(" left  join T_MAR_MeasurePlanTarget target");
	sb.append(" on target.FProjectAginID=project.fid ");
	sb.append(" left  join T_MAR_MeasurePlanTargetEntry entry");
	sb.append(" on entry.fparentid = target.FID");
	sb.append(" left join T_FDC_ProductType type");
	sb.append(" on type.fid=entry.FProductTypeID");
	sb.append(" where target.FIsNew='1' and FState ='"+FDCBillStateEnum.AUDITTED_VALUE+"'");
	if(area!=null){
		sb.append(" and entry.fareaRange='"+area+"'");
	}else{
		sb.append(" and entry.fareaRange is null");
	}
	return sb.toString();
}
/**
 * 年度目标总货值
 * */
protected String getYearSql(RptParams params,String area){
	StringBuffer sb = new StringBuffer();
	sb.append(" select project.fid as projectid,project.fname_l2 as projectname,project.fnumber as projectnumber,project.flongnumber as projectlongnumber,");
	sb.append(" case entry.FProductConstitute when '2business' then '商业' when '1house' then '住宅' when '2publicBuild' then '公共配套建筑' when '3parking' then '车位' else ' ' " +
	" end FProductConstitute,");
	sb.append(" type.fname_l2 as typename,entry.FAcreage as area,");
	sb.append(" entry.FQuantity as count,entry.FPrice as unitprice,entry.FSumAmount as amount");
	sb.append(" from T_SHE_SellProject project ");
	sb.append(" left  join T_MAR_MeasurePlanTarget target");
	sb.append(" on target.FProjectAginID=project.fid ");
	sb.append(" left  join T_MAR_MeasurePlanTargetEntry entry");
	sb.append(" on entry.fparentid = target.FID");
	sb.append(" left join T_FDC_ProductType type");
	sb.append(" on type.fid=entry.FProductTypeID");
	sb.append(" where target.fprojectAginid ='"+params.getString("projectId")+"' and target.FVersionType='"+VersionTypeEnum.YEAR_VALUE+"' and FState ='"+FDCBillStateEnum.AUDITTED_VALUE+"'" +
			" and fversions=(select max(fversions) from T_MAR_MeasurePlanTarget where T_MAR_MeasurePlanTarget.fprojectAginid ='"+params.getString("projectId")+"' and FVersionType='"+VersionTypeEnum.YEAR_VALUE+"')");
	if(area!=null){
		sb.append(" and entry.fareaRange='"+area+"'");
	}else{
		sb.append(" and entry.fareaRange is null");
	}
	return sb.toString();
}
/**
 * 2)	已取预证未定价
 * */
String areaSql = " case when FIsActualAreaAudited='1' then room.factualBuildingArea " +
				 " else case when fispre='1' then room.fBuildingArea " +
				 " else case when FIsPlan='1' then room.fplanBuildingArea" +
				 " else room.fplanBuildingArea end end end ";
protected String getYQZWDJSql(RptParams params,String area){
	StringBuffer sb = new StringBuffer();
	sb.append("select project.fid as projectid,type.fname_l2 typename,sum("+areaSql+") as area,");
	sb.append(" count(*) as count," +
			 " avg(room.FBuildPrice) as unitprice," +
			 " sum(room.fstandardTotalAmount) as amount");
	sb.append(" from T_SHE_Room room ");
	sb.append(" left join T_SHE_Building build ");
	sb.append(" on build.fid=room.FBuildingID ");
	sb.append(" left join T_FDC_ProductType type ");
	sb.append(" on type.fid=build.fproducttypeid");
	sb.append(" left join T_SHE_SellProject project");
	sb.append(" on build.FSellProjectID=project.fid");
	sb.append(" where build.fisgetcertificated='1' and room.FBuildPrice is null");
	if(area!=null){
		if(area.equals("≤100㎡")){
			sb.append(" and ("+areaSql+") <=100"); 
		}else if(area.equals("100㎡<@≤120㎡")){
			sb.append(" and ("+areaSql+") >100 and ("+areaSql+") <=120"); 
		}else if(area.equals("120㎡<@≤140㎡")){
			sb.append(" and ("+areaSql+") >120 and ("+areaSql+") <=140"); 
		}else if(area.equals("140㎡<")){
			sb.append(" and ("+areaSql+") >140 "); 
		}
	}
	sb.append(" group by project.fid,type.fname_l2");
	return sb.toString();	
}
/**
 * 3)	未取预证已定价
 * */
protected String getWQZYDJSql(RptParams params,String area){
	StringBuffer sb = new StringBuffer();
	sb.append(" select project.fid as projectid,type.fname_l2 typename,sum("+areaSql+") as area,");
	sb.append(" count(*) as count," +
			 " avg(room.FBuildPrice) as unitprice," +
			 " sum(room.fstandardTotalAmount) as amount");
	sb.append(" from T_SHE_Room room");
	sb.append(" left join T_SHE_Building build ");
	sb.append(" on build.fid=room.FBuildingID ");
	sb.append(" left join T_FDC_ProductType type");
	sb.append(" on build.fid=room.FBuildingID ");
	sb.append(" left join T_SHE_SellProject project");
	sb.append(" on build.FSellProjectID=project.fid ");
	sb.append(" where room.FBuildPrice is not null and  build.fisgetcertificated='0'  and room.fsellstate<>'Sign'");
	if(area!=null){
		if(area.equals("≤100㎡")){
			sb.append(" and ("+areaSql+") <=100"); 
		}else if(area.equals("100㎡<@≤120㎡")){
			sb.append(" and ("+areaSql+") >100 and ("+areaSql+") <=120"); 
		}else if(area.equals("120㎡<@≤140㎡")){
			sb.append(" and ("+areaSql+") >120 and ("+areaSql+") <=140"); 
		}else if(area.equals("140㎡<")){
			sb.append(" and ("+areaSql+") >140 "); 
		}
	}
	sb.append(" group by project.fid,type.fname_l2");
	return sb.toString();
}
/**
 * 3)	已取预证已定价
 * */
protected String getYQZYDJSql(RptParams params,String area){
	StringBuffer sb = new StringBuffer();
	sb.append(" select project.fid as projectid,type.fname_l2 typename,sum("+areaSql+") as area,");
	sb.append(" count(*) as count," +
			" avg(room.FBuildPrice) as unitprice," +
			" sum (case when room.cfmanagerAgio=null then room.fstandardTotalAmount" +
			" else case when room.cfmanagerAgio=0 then room.fstandardTotalAmount" +
			" else room.fstandardTotalAmount*room.cfmanagerAgio end end )as amount");
	sb.append(" from T_SHE_Room room");
	sb.append(" left join T_SHE_Building build ");
	sb.append(" on build.fid=room.FBuildingID ");
	sb.append(" left join T_FDC_ProductType type");
	sb.append(" on type.fid=build.fproducttypeid");
	sb.append(" left join T_SHE_SellProject project");
	sb.append(" on build.FSellProjectID=project.fid ");
	sb.append(" where room.FBuildPrice is not null and  build.fisgetcertificated='1'  and room.fsellstate<>'Sign'");
	if(area!=null){
		if(area.equals("≤100㎡")){
			sb.append(" and ("+areaSql+") <=100"); 
		}else if(area.equals("100㎡<@≤120㎡")){
			sb.append(" and ("+areaSql+") >100 and ("+areaSql+") <=120"); 
		}else if(area.equals("120㎡<@≤140㎡")){
			sb.append(" and ("+areaSql+") >120 and ("+areaSql+") <=140"); 
		}else if(area.equals("140㎡<")){
			sb.append(" and ("+areaSql+") >140 "); 
		}
	}
	sb.append(" group by project.fid,type.fname_l2");
	return sb.toString();
}
/**
 * 4)	已售
 * */
protected String getYSSql(RptParams params,String area){
	StringBuffer sb = new StringBuffer();
	String signAreaSql="case signman.fsellType when 'PlanningSell' then signman.fstrdPlanBuildingArea when 'PreSell' then signman.fbulidingArea else signman.fstrdActualBuildingArea end ";
	sb.append(" select project.fid as projectid,type.fname_l2 as typename, ");
	sb.append(" sum("+signAreaSql+") as area,count(*) count,");
	sb.append(" avg(room.FBuildPrice) as unitprice,");
	sb.append(" sum(signman.FStrdTotalAmount) as amount");
	sb.append(" from T_SHE_Room room");
	sb.append(" left join T_SHE_Building build ");
	sb.append(" on build.fid=room.FBuildingID ");
	sb.append(" left join T_FDC_ProductType type ");
	sb.append(" on type.fid=build.fproducttypeid ");
	sb.append(" left join T_SHE_SellProject project");
	sb.append(" on build.FSellProjectID=project.fid");
	sb.append(" left join T_SHE_SignManage signman ");
	sb.append(" on signman.froomid=room.fid ");
	sb.append(" where room.fsellstate='Sign' and signman.fbizState in('SignApple','SignAudit')");
	if(area!=null){
		if(area.equals("≤100㎡")){
			sb.append(" and ("+signAreaSql+") <=100"); 
		}else if(area.equals("100㎡<@≤120㎡")){
			sb.append(" and ("+signAreaSql+") >100 and ("+signAreaSql+") <=120"); 
		}else if(area.equals("120㎡<@≤140㎡")){
			sb.append(" and ("+signAreaSql+") >120 and ("+signAreaSql+") <=140"); 
		}else if(area.equals("140㎡<")){
			sb.append(" and ("+signAreaSql+") >140 "); 
		}
	}
	sb.append(" group by project.fid,type.fname_l2");
	return sb.toString();
}

    
}