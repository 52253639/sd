package com.kingdee.eas.fdc.invite.supplier.report;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
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
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpCollection;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class SupplierStockAccountReportFacadeControllerBean extends AbstractSupplierStockAccountReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.report.SupplierStockAccountReportFacadeControllerBean");
    
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
	    initColoum(header,col,"purchaseOrgUnit",100,false);
	    initColoum(header,col,"excellentAmount",100,false);
   	 	initColoum(header,col,"excellentRate",100,false);
   	    initColoum(header,col,"qualifiedAmount",100,false);
   	 	initColoum(header,col,"qualifiedRate",100,false);
   	    initColoum(header,col,"limitAmount",100,false);
   	 	initColoum(header,col,"limitRate",100,false);
	    initColoum(header,col,"sumTotal",100,false);
	    initColoum(header,col,"newCopAmount",100,false);
	    initColoum(header,col,"copUnPassAmount",150,false);
	    initColoum(header,col,"unCopPassAmount",100,false);
	    initColoum(header,col,"unCopPassRate",100,false);
	    initColoum(header,col,"sumAll",100,false);
	    initColoum(header,col,"untilLastYearAmount",100,false);
	    initColoum(header,col,"newAmount",100,false);
	    initColoum(header,col,"newRate",150,false);
	    initColoum(header,col,"unPassAmount",100,false);
	    initColoum(header,col,"unPassRate",100,false);
	    
	    header.setLabels(new Object[][]{ 
	    		{
	    		    "所属组织","已合作","已合作","已合作","已合作","已合作","已合作","已合作","已合作","已合作","未合作","未合作","汇总"
	    			,"其中","其中","其中","其中","其中"
	    		}
	    		,
	    		{
	    			"所属组织","已合作限制使用以上","已合作限制使用以上","已合作限制使用以上","已合作限制使用以上","已合作限制使用以上","已合作限制使用以上","已合作限制使用以上","新合作供应商","合作过且不合格供应商"
	    			,"未合作","未合作","汇总","截止上年底原有","新开发供应商","新开发供应商比例","剔除供应商","剔除供应商比例"
	    		}
	    		,
	    		{
	    			"所属组织","优秀","优秀","合格","合格","限制使用","限制使用","小计","新合作供应商","合作过且不合格供应商","未合作","未合作","汇总","截止上年底原有",
	    			"新开发供应商","新开发供应商比例","剔除供应商","剔除供应商比例"
	    		}
	    		,
	    		{
	    			"所属组织","数量","比例","数量","比例","数量","比例","小计","新合作供应商","合作过且不合格供应商","数量","比例","汇总","截止上年底原有",
	    			"新开发供应商","新开发供应商比例","剔除供应商","剔除供应商比例"
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
    	String org = (String) params.getObject("org");
    	Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	StringBuffer dateTypeWhere=new StringBuffer();
    	dateTypeWhere.append("and supplier.fid in(select gather.fsupplierId from T_GYS_SupplierReviewGather gather left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId left join(select gather.fsupplierId,max(gather.fbizDate) fbizDate from T_GYS_SupplierReviewGather gather left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId where type.fnumber in('003','004','005') and gather.fstate='4AUDITTED' group by gather.fsupplierId) gather1 on gather1.fsupplierId=gather.fsupplierId where type.fnumber in('003','004','005') and gather.fstate='4AUDITTED' and gather1.fbizDate=gather.fbizDate ");
		if(fromDate!=null){
			dateTypeWhere.append(" and gather.fbizdate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			dateTypeWhere.append(" and gather.fbizdate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		dateTypeWhere.append(")");
    			
    	StringBuffer dateWhere= new StringBuffer();
    	if(fromDate!=null){
    		dateWhere.append(" and supplier.FStorageDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			dateWhere.append(" and supplier.FStorageDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		
		Date nowDate=null;
		try {
			nowDate= FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		StringBuffer lessDateWhere= new StringBuffer();
		if(fromDate!=null){
			lessDateWhere.append(" and gather.fbizdate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(nowDate)))+ "'}");
		}
		
		StringBuffer yearLessDateWhere= new StringBuffer();
		if(fromDate!=null){
			yearLessDateWhere.append(" and supplier.FStorageDate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(nowDate)))+ "'}");
    	}
		
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select  t.orgUnit purchaseOrgUnit,max(case t.name when 'excellent' then isnull(t.amount,0) else 0 end) excellentAmount,");
    	sb.append(" round(case when max(case t.name when 'excellent' then isnull(t.amount,0) else 0 end)+max(case t.name when 'qualified' then isnull(t.amount,0) else 0 end)+max(case t.name when 'limit' then isnull(t.amount,0) else 0 end)=0 then 0 else max(case t.name when 'excellent' then isnull(t.amount,0) else 0 end)/(max(case t.name when 'excellent' then isnull(t.amount,0) else 0 end)+max(case t.name when 'qualified' then isnull(t.amount,0) else 0 end)+max(case t.name when 'limit' then isnull(t.amount,0) else 0 end)) end,4)*100 excellentRate,");
    	sb.append(" max(case t.name when 'qualified' then isnull(t.amount,0) else 0 end) qualifiedAmount,");
    	sb.append(" round(case when max(case t.name when 'excellent' then isnull(t.amount,0) else 0 end)+max(case t.name when 'qualified' then isnull(t.amount,0) else 0 end)+max(case t.name when 'limit' then isnull(t.amount,0) else 0 end)=0 then 0 else max(case t.name when 'qualified' then isnull(t.amount,0) else 0 end)/(max(case t.name when 'excellent' then isnull(t.amount,0) else 0 end)+max(case t.name when 'qualified' then isnull(t.amount,0) else 0 end)+max(case t.name when 'limit' then isnull(t.amount,0) else 0 end)) end,4)*100 qualifiedRate,");
    	sb.append(" max(case t.name when 'limit' then isnull(t.amount,0) else 0 end) limitAmount,");
    	sb.append(" round(case when max(case t.name when 'excellent' then isnull(t.amount,0) else 0 end)+max(case t.name when 'qualified' then isnull(t.amount,0) else 0 end)+max(case t.name when 'limit' then isnull(t.amount,0) else 0 end)=0 then 0 else max(case t.name when 'limit' then isnull(t.amount,0) else 0 end)/(max(case t.name when 'excellent' then isnull(t.amount,0) else 0 end)+max(case t.name when 'qualified' then isnull(t.amount,0) else 0 end)+max(case t.name when 'limit' then isnull(t.amount,0) else 0 end)) end,4)*100 limitRate,");
    	sb.append(" max(case t.name when 'excellent' then isnull(t.amount,0) else 0 end)+max(case t.name when 'qualified' then isnull(t.amount,0) else 0 end)+max(case t.name when 'limit' then isnull(t.amount,0) else 0 end) sumTotal,");
    	sb.append(" max(case t.name when 'newCop' then isnull(t.amount,0) else 0 end) newCopAmount,");
    	sb.append(" max(case t.name when 'copUnPass' then isnull(t.amount,0) else 0 end) copUnPassAmount,");
    	sb.append(" max(case t.name when 'unCopPass' then isnull(t.amount,0) else 0 end) unCopPassAmount,");
    	sb.append(" round(case when max(case t.name when 'excellent' then isnull(t.amount,0) else 0 end)+max(case t.name when 'qualified' then isnull(t.amount,0) else 0 end)+max(case t.name when 'limit' then isnull(t.amount,0) else 0 end)+max(case t.name when 'unCopPass' then isnull(t.amount,0) else 0 end)=0 then 0 else max(case t.name when 'unCopPass' then isnull(t.amount,0) else 0 end)/(max(case t.name when 'excellent' then isnull(t.amount,0) else 0 end)+max(case t.name when 'qualified' then isnull(t.amount,0) else 0 end)+max(case t.name when 'limit' then isnull(t.amount,0) else 0 end)+max(case t.name when 'unCopPass' then isnull(t.amount,0) else 0 end)) end,4)*100 unCopPassRate,");
    	sb.append(" max(case t.name when 'excellent' then isnull(t.amount,0) else 0 end)+max(case t.name when 'qualified' then isnull(t.amount,0) else 0 end)+max(case t.name when 'limit' then isnull(t.amount,0) else 0 end)+max(case t.name when 'unCopPass' then isnull(t.amount,0) else 0 end) sumAll,");
    	sb.append(" max(case t.name when 'untilLastYear' then isnull(t.amount,0) else 0 end) untilLastYearAmount,");
    	sb.append(" max(case t.name when 'new' then isnull(t.amount,0) else 0 end) newAmount,");
    	sb.append(" round(case when max(case t.name when 'untilLastYear' then isnull(t.amount,0) else 0 end)=0 then 0 else max(case t.name when 'new' then isnull(t.amount,0) else 0 end)/max(case t.name when 'untilLastYear' then isnull(t.amount,0) else 0 end) end,4)*100 newRate,");
    	sb.append(" max(case t.name when 'unPass' then isnull(t.amount,0) else 0 end) unPassAmount,");
    	sb.append(" round(case when max(case t.name when 'untilLastYear' then isnull(t.amount,0) else 0 end)=0 then 0 else max(case t.name when 'unPass' then isnull(t.amount,0) else 0 end)/max(case t.name when 'untilLastYear' then isnull(t.amount,0) else 0 end) end,4)*100 unPassRate");
    	sb.append(" from (select purchaseOrgUnit.fname_l2 orgUnit,purchaseOrgUnit.flongNumber orgUnitLongNumber,'excellent' name,count(supplier.fid) amount from T_FDC_SupplierStock supplier left join T_ORG_Purchase purchaseOrgUnit on purchaseOrgUnit.fid=supplier.FPurchaseOrgUnitId left join T_FDC_GradeSetUp grade on grade.fid=supplier.FGradeId where grade.fnumber='01'");
    	sb.append(dateTypeWhere);
    	sb.append(" group by purchaseOrgUnit.fname_l2,purchaseOrgUnit.flongNumber");
    	sb.append(" union all select purchaseOrgUnit.fname_l2 orgUnit,purchaseOrgUnit.flongNumber orgUnitLongNumber,'qualified' name,count(supplier.fid) amount from T_FDC_SupplierStock supplier left join T_ORG_Purchase purchaseOrgUnit on purchaseOrgUnit.fid=supplier.FPurchaseOrgUnitId left join T_FDC_GradeSetUp grade on grade.fid=supplier.FGradeId where grade.fnumber='03'");
    	sb.append(dateTypeWhere);
    	sb.append(" group by purchaseOrgUnit.fname_l2,purchaseOrgUnit.flongNumber");
    	sb.append(" union all select purchaseOrgUnit.fname_l2 orgUnit,purchaseOrgUnit.flongNumber orgUnitLongNumber,'limit' name,count(supplier.fid) amount from T_FDC_SupplierStock supplier left join T_ORG_Purchase purchaseOrgUnit on purchaseOrgUnit.fid=supplier.FPurchaseOrgUnitId left join T_FDC_GradeSetUp grade on grade.fid=supplier.FGradeId where grade.fnumber='04'");
    	sb.append(dateTypeWhere);
    	sb.append(" group by purchaseOrgUnit.fname_l2,purchaseOrgUnit.flongNumber");
    	sb.append(" union all select purchaseOrgUnit.fname_l2 orgUnit,purchaseOrgUnit.flongNumber orgUnitLongNumber,'newCop' name,count(supplier.fid) amount from T_FDC_SupplierStock supplier left join T_ORG_Purchase purchaseOrgUnit on purchaseOrgUnit.fid=supplier.FPurchaseOrgUnitId where supplier.fid not in(select gather.fsupplierId from T_GYS_SupplierReviewGather gather left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId where type.fnumber in('003','004','005') and gather.fstate='4AUDITTED' "+lessDateWhere.toString()+")");
    	sb.append(" and supplier.fid in(select entry.fsupplierId from T_INV_TenderAccepterResultE entry left join T_INV_TenderAccepterResult result on result.fid=entry.FParentID where result.fstate='4AUDITTED' and entry.FHit=1");
    	sb.append(" and result.fauditTime>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(nowDate)))+ "'}");
		sb.append(" and result.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(nowDate)))+ "'}");
    	sb.append(" )group by purchaseOrgUnit.fname_l2,purchaseOrgUnit.flongNumber");
    	sb.append(" union all select purchaseOrgUnit.fname_l2 orgUnit,purchaseOrgUnit.flongNumber orgUnitLongNumber,'copUnPass' name,count(supplier.fid) amount from T_FDC_SupplierStock supplier left join T_ORG_Purchase purchaseOrgUnit on purchaseOrgUnit.fid=supplier.FPurchaseOrgUnitId where supplier.FIsPass=2 and supplier.fid in(select gather.fsupplierId from T_GYS_SupplierReviewGather gather left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId where type.fnumber='003' and gather.fstate='4AUDITTED')");
    	sb.append(dateTypeWhere);
    	sb.append(" group by purchaseOrgUnit.fname_l2,purchaseOrgUnit.flongNumber");
    	sb.append(" union all select purchaseOrgUnit.fname_l2 orgUnit,purchaseOrgUnit.flongNumber orgUnitLongNumber,'unCopPass' name,count(supplier.fid) amount from T_FDC_SupplierStock supplier left join T_ORG_Purchase purchaseOrgUnit on purchaseOrgUnit.fid=supplier.FPurchaseOrgUnitId where supplier.FIsPass=1 and supplier.fid not in(select gather.fsupplierId from T_GYS_SupplierReviewGather gather left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId where type.fnumber='003' and gather.fstate='4AUDITTED')");
    	sb.append(dateWhere);
    	sb.append(" group by purchaseOrgUnit.fname_l2,purchaseOrgUnit.flongNumber");
    	sb.append(" union all select purchaseOrgUnit.fname_l2 orgUnit,purchaseOrgUnit.flongNumber orgUnitLongNumber,'untilLastYear' name,count(supplier.fid) amount from T_FDC_SupplierStock supplier left join T_ORG_Purchase purchaseOrgUnit on purchaseOrgUnit.fid=supplier.FPurchaseOrgUnitId where supplier.FIsPass=1");
    	sb.append(yearLessDateWhere);
    	sb.append(" group by purchaseOrgUnit.fname_l2,purchaseOrgUnit.flongNumber");
    	sb.append(" union all select purchaseOrgUnit.fname_l2 orgUnit,purchaseOrgUnit.flongNumber orgUnitLongNumber,'new' name,count(supplier.fid) amount from T_FDC_SupplierStock supplier left join T_ORG_Purchase purchaseOrgUnit on purchaseOrgUnit.fid=supplier.FPurchaseOrgUnitId where supplier.FIsPass=1");
    	sb.append(" and supplier.FStorageDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(nowDate)))+ "'}");
		sb.append(" and supplier.FStorageDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(nowDate)))+ "'}");
    	sb.append(" group by purchaseOrgUnit.fname_l2,purchaseOrgUnit.flongNumber");
    	sb.append(" union all select purchaseOrgUnit.fname_l2 orgUnit,purchaseOrgUnit.flongNumber orgUnitLongNumber,'unPass' name,count(supplier.fid) amount from T_FDC_SupplierStock supplier left join T_ORG_Purchase purchaseOrgUnit on purchaseOrgUnit.fid=supplier.FPurchaseOrgUnitId where supplier.FIsPass=2 and supplier.fid in(select gather.fsupplierId from T_GYS_SupplierReviewGather gather left join(select gather.fsupplierId,max(gather.fbizDate) fbizDate from T_GYS_SupplierReviewGather gather where gather.fstate='4AUDITTED' group by gather.fsupplierId) gather1 on gather1.fsupplierId=gather.fsupplierId where gather.fstate='4AUDITTED' and gather1.fbizDate=gather.fbizDate and gather.FIsPass=1 "+lessDateWhere.toString()+")");
    	sb.append(" group by purchaseOrgUnit.fname_l2,purchaseOrgUnit.flongNumber");
    	sb.append(" )t where 1=1");
    	if(org!=null){
    		sb.append(" and t.orgUnitLongNumber like '"+org+"%'");
    	}
    	sb.append(" group by t.orgUnit,t.orgUnitLongNumber");
    	sb.append(" order by t.orgUnitLongNumber");
    	return sb.toString();
    }
}