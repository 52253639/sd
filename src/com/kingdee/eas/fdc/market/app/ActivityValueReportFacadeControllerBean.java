package com.kingdee.eas.fdc.market.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.market.VersionTypeEnum;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.framework.report.util.SqlParams;

public class ActivityValueReportFacadeControllerBean extends AbstractActivityValueReportFacadeControllerBean
{

	private static final long serialVersionUID = -6336198262244871756L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.ActivityValueReportFacadeControllerBean");
    protected RptParams _init(Context ctx, RptParams params)throws BOSException, EASBizException
	{
	    RptParams pp = new RptParams();
	    return pp;
	}
    protected RptParams _createTempTable(Context ctx, RptParams params)    throws BOSException, EASBizException
	{
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
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("tararea");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("tarcount");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("tarunitprice");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("taramount");
	    col.setWidth(120);
	    header.addColumn(col);
	    col = new RptTableColumn("yeararea");
	    col.setWidth(80);
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("yearcount");
	    col.setWidth(80);
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("yearunitprice");
	    col.setWidth(80);
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("yearamount");
	    col.setWidth(120);
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("zjarea");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("zjcount");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("zjunitprice");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("zjamount");
	    col.setWidth(120);
	    header.addColumn(col);
	    col = new RptTableColumn("wqzarea");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("wqzcount");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("wqzunitprice");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("wqzamount");
	    col.setWidth(120);
	    header.addColumn(col);
	    col = new RptTableColumn("wqzydjarea");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("wqzydjcount");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("wqzydjunitprice");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("wqzydjamount");
	    col.setWidth(120);
	    header.addColumn(col);
	    col = new RptTableColumn("yqzwdjarea");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("yqzwdjcount");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("yqzwdjunitprice");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("yqzwdjamount");
	    col.setWidth(120);
	    header.addColumn(col);
	    col = new RptTableColumn("yqzydjarea");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("yqzydjcount");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("yqzydjunitprice");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("yqzydjamount");
	    col.setWidth(120);
	    header.addColumn(col);
	    col = new RptTableColumn("ysarea");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("yscount");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("ysunitprice");
	    col.setWidth(80);
	    header.addColumn(col);
	    col = new RptTableColumn("ysamount");
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
	    		 	"目标货值测算",
	    		 	"目标货值测算",
	    		 	"目标货值测算",
	    		 	"目标货值测算",
	    		 	"年度版测算货值",
	    		 	"年度版测算货值",
	    		 	"年度版测算货值",
	    		 	"年度版测算货值",
	    		 	"动态总货值",
	    		 	"动态总货值",
	    		 	"动态总货值",
	    		 	"动态总货值",
	    		 	"动态货值计算明细",
	    		 	"动态货值计算明细",
	    		 	"动态货值计算明细",
	    		 	"动态货值计算明细",
	    		 	"动态货值计算明细",
	    		 	"动态货值计算明细",
	    		 	"动态货值计算明细",
	    		 	"动态货值计算明细",
	    		 	"动态货值计算明细",
	    		 	"动态货值计算明细",
	    		 	"动态货值计算明细",
	    		 	"动态货值计算明细",
	    		 	"动态货值计算明细",
	    		 	"动态货值计算明细",
	    		 	"动态货值计算明细",
	    		 	"动态货值计算明细",
	    		 	"剩余货值（动态总货值-已售金额）",
	    		 	"剩余货值（动态总货值-已售金额）",
	    		 	"剩余货值（动态总货值-已售金额）",
	    		 	"剩余货值（动态总货值-已售金额）"
	    		}
	    		,{
	    			"序列号",
	    			"项目ID",
	    			"项目长编码",
	    		 	"项目名称",
	    		 	"产品构成",
	    		 	"产品类型",
	    		 	"面积段",
	    		 	"目标货值测算",
	    		 	"目标货值测算",
	    		 	"目标货值测算",
	    		 	"目标货值测算",
	    		 	"年度版测算货值",
	    		 	"年度版测算货值",
	    		 	"年度版测算货值",
	    		 	"年度版测算货值",
	    		 	"动态总货值",
	    		 	"动态总货值",
	    		 	"动态总货值",
	    		 	"动态总货值",
	    		 	"预估阶段（测算面积+测算金额）",
	    		 	"预估阶段（测算面积+测算金额）",
	    		 	"预估阶段（测算面积+测算金额）",
	    		 	"预估阶段（测算面积+测算金额）",
	    		 	"预证阶段（预售面积+测算金额）",
	    		 	"预证阶段（预售面积+测算金额）",
	    		 	"预证阶段（预售面积+测算金额）",
	    		 	"预证阶段（预售面积+测算金额）",
	    		 	"定价阶段（预售面积+房间定价）",
	    		 	"定价阶段（预售面积+房间定价）",
	    		 	"定价阶段（预售面积+房间定价）",
	    		 	"定价阶段（预售面积+房间定价）",
	    		 	"销售阶段（预售面积+成交价格）",
	    		 	"销售阶段（预售面积+成交价格）",
	    		 	"销售阶段（预售面积+成交价格）",
	    		 	"销售阶段（预售面积+成交价格）",
	    		 	"剩余货值（动态总货值-已售金额）",
	    		 	"剩余货值（动态总货值-已售金额）",
	    		 	"剩余货值（动态总货值-已售金额）",
	    		 	"剩余货值（动态总货值-已售金额）"
	    		}
	    		,{
	    			"序列号",
	    			"项目ID",
	    			"项目长编码",
	    		 	"项目名称",
	    		 	"产品构成",
	    		 	"产品类型",
	    		 	"面积段",
	    		 	"面积",//f 6
	    		 	"套数",
	    		 	"均价",
	    		 	"金额(元)",//I 9
	    		 	"面积",// 10
	    		 	"套数",
	    		 	"均价",
	    		 	"金额(元)",//
	    		 	"面积",
	    		 	"套数",
	    		 	"均价",
	    		 	"金额(元)",
	    		 	"面积",
	    		 	"套数",
	    		 	"均价",
	    		 	"金额(元)",
	    		 	"面积",
	    		 	"套数",
	    		 	"均价",
	    		 	"金额(元)",
	    		 	"面积",
	    		 	"套数",
	    		 	"均价",
	    		 	"金额(元)",
	    		 	"面积",//z
	    		 	"套数",
	    		 	"均价",
	    		 	"金额(元)",
	    		 	"面积",
	    		 	"套数",
	    		 	"均价",
	    		 	"金额(元)"
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
//		sb.append(" case when tar.productConstitute='车位' and tar.count=0 then 0  when tar.productConstitute!='车位' and tar.area=0 then 0 else (" +
//				  " case tar.productConstitute when '车位'  then ");
//		sb.append(" round((");
//		sb.append(amountSql);//总金额=1)	未取预证未定价+2)未取预证已定价+3)已取预证未定价+4)已取预证已定价+5)已售
//		sb.append(" -0)/(tar.count),2) ");
//		sb.append(" else ");
//		sb.append(" round((");
//		sb.append(amountSql);
//		sb.append(" -0)/(tar.area),2)" +
//				  " end) ");
		sb.append(" round((isnull((isnull(tar.area,0)-(isnull(yqzwdj.area,0)+isnull(wqzydj.area,0)+isnull(yqzydj.area,0)+isnull(ys.area,0)))*tar.unitprice,0)+isnull(yqzwdj.area*tar.unitprice,0)+isnull(wqzydj.amount,0)+isnull(ys.amount,0))/tar.area,2) zjunitprice,");
		sb.append(" isnull((isnull(tar.area,0)-(isnull(yqzwdj.area,0)+isnull(wqzydj.area,0)+isnull(yqzydj.area,0)+isnull(ys.area,0)))*tar.unitprice,0)+isnull(yqzwdj.area*tar.unitprice,0)+isnull(wqzydj.amount,0)+isnull(ys.amount,0) as zjamount,");
		//未取预证未定价
		sb.append(" isnull(tar.area,0)-(isnull(yqzwdj.area,0)+isnull(wqzydj.area,0)+isnull(yqzydj.area,0)+isnull(ys.area,0)) as wqzarea,");
		sb.append(" isnull(tar.count,0)-(isnull(yqzwdj.count,0)+isnull(wqzydj.count,0)+isnull(yqzydj.count,0)+isnull(ys.count,0)) as wqzcount,");
		sb.append(" case when (isnull(tar.area,0)-(isnull(yqzwdj.area,0)+isnull(wqzydj.area,0)+isnull(yqzydj.area,0)+isnull(ys.area,0))) is null or (isnull(tar.area,0)-(isnull(yqzwdj.area,0)+isnull(wqzydj.area,0)+isnull(yqzydj.area,0)+isnull(ys.area,0)))=0 then 0 else tar.unitprice end as wqzunitprice,");
		sb.append(" (isnull(tar.area,0)-(isnull(yqzwdj.area,0)+isnull(wqzydj.area,0)+isnull(yqzydj.area,0)+isnull(ys.area,0)))*tar.unitprice as wqzcount,");
		//未取预证已定价
		sb.append(" yqzwdj.area as wqzydjarea,yqzwdj.count as wqzydjcount,case when yqzwdj.area is null or yqzwdj.area=0 then null else tar.unitprice end as wqzydjunitprice ,yqzwdj.area*tar.unitprice as wqzydjamount,");
		//已取预证未定价
		sb.append(" wqzydj.area as yqzwdjarea,wqzydj.count as yqzwdjcount, round(wqzydj.amount/wqzydj.area,2) as yqzwdjunitprice,wqzydj.amount as yqzwdjamount,");
		//已取预证已定价
//		sb.append(" yqzydj.area as yqzydjarea,yqzydj.count as yqzydjcount,yqzydj.unitprice as yqzydjunitprice,yqzydj.amount as yqzydjamount,");
		//已售
		sb.append(" ys.area as yqzydjarea,ys.count as yqzydjcount,round(ys.amount/ys.area,2) as ysunitprice,ys.amount as yqzydjamount,");
		
		sb.append(" isnull(tar.area,0)-isnull(ys.area,0) as ysarea,isnull(tar.count,0)-isnull(ys.count,0) as yscount,case when isnull(tar.area,0)-isnull(ys.area,0)=0 then 0 else round((isnull((isnull(tar.area,0)-(isnull(yqzwdj.area,0)+isnull(wqzydj.area,0)+isnull(yqzydj.area,0)+isnull(ys.area,0)))*tar.unitprice,0)+isnull(yqzwdj.area*tar.unitprice,0)+isnull(wqzydj.amount,0)+isnull(ys.amount,0)-isnull(ys.amount,0))/(isnull(tar.area,0)-isnull(ys.area,0)),2) end as ysunitprice,isnull((isnull(tar.area,0)-(isnull(yqzwdj.area,0)+isnull(wqzydj.area,0)+isnull(yqzydj.area,0)+isnull(ys.area,0)))*tar.unitprice,0)+isnull(yqzwdj.area*tar.unitprice,0)+isnull(wqzydj.amount,0)+isnull(ys.amount,0)-isnull(ys.amount,0) as ysamount");
		
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
		sb.append(" where tar.projectid='"+params.getString("projectId")+"' order by tar.fseq,tar.typename");
	}
	protected String getSql(RptParams params){
		StringBuffer sb = new StringBuffer();
//		sb.append(" select * from ( ");
		getAreaRangeSql(sb,params,null);
		
//		sb.append(" union all ");
//		
//		sb.append(" (");
//		getAreaRangeSql(sb,params,"≤100㎡");
//		sb.append(" )");
//		
//		sb.append(" union all ");
//		
//		sb.append(" (");
//		getAreaRangeSql(sb,params,"100㎡<@≤120㎡");
//		sb.append(" )");
//		
//		sb.append(" union all ");
//		
//		sb.append(" (");
//		getAreaRangeSql(sb,params,"120㎡<@≤140㎡");
//		sb.append(" )");
//		
//		sb.append(" union all ");
//		
//		sb.append(" (");
//		getAreaRangeSql(sb,params,"140㎡<");
//		sb.append(" )");
//		sb.append(" )t order by tar.fseq,tar.typename");
		return sb.toString();
	}
	/**
	 * 目标总货值
	 * */
	protected String getTargetSql(RptParams params,String area){
		StringBuffer sb = new StringBuffer();
		sb.append(" select project.fid as projectid,project.fname_l2 as projectname,project.fnumber as projectnumber,type.fid as projectlongnumber,");
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
	protected String getYQZWDJSql(RptParams params,String area){
		StringBuffer sb = new StringBuffer();
		sb.append("select project.fid as projectid,type.fname_l2 typename,sum(room.FBuildingArea) as area,");
		sb.append(" count(*) as count," +
				 " avg(room.FBuildPrice) as unitprice," +
				 " sum(room.fstandardTotalAmount) as amount");
		sb.append(" from T_SHE_Room room ");
		sb.append(" left join T_SHE_Building build ");
		sb.append(" on build.fid=room.FBuildingID ");
		sb.append(" left join T_FDC_ProductType type ");
		sb.append(" on type.fid=room.FProductTypeID");
		sb.append(" left join T_SHE_SellProject project");
		sb.append(" on build.FSellProjectID=project.fid");
		sb.append(" where room.FBuildPrice is null");
		sb.append(" group by project.fid,type.fname_l2");
		return sb.toString();	
	}
	/**
	 * 3)	未取预证已定价
	 * */
	protected String getWQZYDJSql(RptParams params,String area){
		Boolean isStand=params.getBoolean("isStand");
		StringBuffer sb = new StringBuffer();
		sb.append(" select project.fid as projectid,type.fname_l2 typename,sum(room.FBuildingArea) as area,");
		sb.append(" count(*) as count, avg(room.FBuildPrice) as unitprice,");
		 if(isStand!=null&&!isStand){
			 sb.append(" sum(room.FBaseStandardPrice) as amount");
		 }else{
			 sb.append(" sum(room.fstandardTotalAmount) as amount");
		 }
				 
		sb.append(" from T_SHE_Room room");
		sb.append(" left join T_SHE_Building build ");
		sb.append(" on build.fid=room.FBuildingID ");
		sb.append(" left join T_FDC_ProductType type");
		sb.append(" on type.fid=room.FProductTypeID ");
		sb.append(" left join T_SHE_SellProject project");
		sb.append(" on build.FSellProjectID=project.fid ");
		sb.append(" where room.FBuildPrice is not null and room.fsellstate<>'Sign'");
		sb.append(" group by project.fid,type.fname_l2");
		return sb.toString();
	}
	/**
	 * 3)	已取预证已定价
	 * */
	protected String getYQZYDJSql(RptParams params,String area){
		StringBuffer sb = new StringBuffer();
		sb.append(" select project.fid as projectid,type.fname_l2 typename,0 as area,");
		sb.append(" 0 as count," +
				" 0 as unitprice," +
				" 0 as amount");
		sb.append(" from T_SHE_Room room");
		sb.append(" left join T_SHE_Building build ");
		sb.append(" on build.fid=room.FBuildingID ");
		sb.append(" left join T_FDC_ProductType type");
		sb.append(" on type.fid=room.FProductTypeID");
		sb.append(" left join T_SHE_SellProject project");
		sb.append(" on build.FSellProjectID=project.fid ");
		sb.append(" where room.FBuildPrice is not null and room.fsellstate<>'Sign'");
		sb.append(" group by project.fid,type.fname_l2");
		return sb.toString();
	}
	/**
	 * 4)	已售
	 * */
	protected String getYSSql(RptParams params,String area){
		StringBuffer sb = new StringBuffer();
		sb.append(" select project.fid as projectid,type.fname_l2 as typename, ");
		sb.append(" sum(room.FBuildingArea) as area,count(*) count,");
		sb.append(" avg(room.FBuildPrice) as unitprice,");
		sb.append(" sum(signman.fdealtotalAmount) as amount");
		sb.append(" from T_SHE_Room room");
		sb.append(" left join T_SHE_Building build ");
		sb.append(" on build.fid=room.FBuildingID ");
		sb.append(" left join T_FDC_ProductType type ");
		sb.append(" on type.fid=room.FProductTypeID ");
		sb.append(" left join T_SHE_SellProject project");
		sb.append(" on build.FSellProjectID=project.fid");
		sb.append(" left join T_SHE_SignManage signman ");
		sb.append(" on signman.froomid=room.fid ");
		sb.append(" where room.fsellstate='Sign' and signman.fbizState in('SignApple','SignAudit')");
		sb.append(" group by project.fid,type.fname_l2");
		return sb.toString();
	}
}