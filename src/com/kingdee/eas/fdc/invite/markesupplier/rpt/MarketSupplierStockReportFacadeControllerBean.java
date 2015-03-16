package com.kingdee.eas.fdc.invite.markesupplier.rpt;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
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
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketGradeSetUpInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.VisibilityInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.LevelSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.QuaLevelInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class MarketSupplierStockReportFacadeControllerBean extends AbstractMarketSupplierStockReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.markesupplier.rpt.MarketSupplierStockReportFacadeControllerBean");
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
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"seq",100,false);
	    initColoum(header,col,"purchaseOrgUnit",100,false);
	    initColoum(header,col,"inviteType",100,false);
	    initColoum(header,col,"storageNumber",100,false);
	    initColoum(header,col,"name",250,false);
	    initColoum(header,col,"isPass",100,false);
	    initColoum(header,col,"quaLevel",100,false);
	    initColoum(header,col,"levelSetUpId",100,true);
	    initColoum(header,col,"levelSetUp",100,false);
	    initColoum(header,col,"grade",100,false);
	    initColoum(header,col,"splArea",100,false);
	    initColoum(header,col,"contractor",100,false);
	    initColoum(header,col,"kcId",100,true);
	    initColoum(header,col,"kcScore",100,false);
	    initColoum(header,col,"storageDate",100,false);
	    initColoum(header,col,"inviteName",100,false);
	    initColoum(header,col,"lyzhId",100,true);
	    initColoum(header,col,"lyzhScore",100,false);
	    initColoum(header,col,"lyzhDate",100,false);
	    initColoum(header,col,"curProject",100,false);
	    initColoum(header,col,"contractId",100,true);
	    initColoum(header,col,"contractName",100,false);
	    initColoum(header,col,"manager",100,false);
	    initColoum(header,col,"stage",100,false);
	    initColoum(header,col,"lypgId",100,true);
	    initColoum(header,col,"lypgScore",100,false);
	    initColoum(header,col,"lypgDate",100,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"id","序号","所属组织","采购类别","供应商入库编码","供应商名称","是否合格","资质等级","levelSetUpId","供应商级别","供应商等级","服务区域","实际承包人","kcId","考察得分","入库日期",
	    			"参投立项名称","lyzhId","履约综合评估","履约综合评估日期",
	    			"合作状态","合作状态","合作状态","合作状态","合作状态","合作状态","合作状态","合作状态"
	    		}
	    		,
	    		{
	    			"id","序号","所属组织","采购类别","供应商入库编码","供应商名称","是否合格","资质等级","levelSetUpId","供应商级别","供应商等级","服务区域","实际承包人","kcId","考察得分","入库日期",
	    			"参投立项名称","lyzhId","履约综合评估","履约综合评估日期",
	    			"工程项目","contractId","合同名称","项目经理","阶段","lypgId","履约评估得分","履约评估日期"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		params.setObject("rowset", rowSet);
		
//		Map supplier=new HashMap();
//		RptRowSet curProjectRowSet = executeQuery(getCurProjectSql(ctx,params), null, from, len, ctx);
//		while(curProjectRowSet.next()){
//			String name="（"+curProjectRowSet.getString("curProjectName")+"） "+curProjectRowSet.getString("inviteName")+";\n";
//			if(supplier.containsKey(curProjectRowSet.getString("id"))){
//				name=(String) supplier.get(curProjectRowSet.getString("id"))+name;
//			}
//			supplier.put(curProjectRowSet.getString("id"), name);
//		}
//		params.setObject("curProject", supplier);
		
		params.setObject("curProject", null);
		return params;
    }
    protected String getCurProjectSql(Context ctx,RptParams params){
    	String inviteType = (String) params.getObject("inviteType");
    	String org = (String) params.getObject("org");
    	String name=(String)params.getObject("name");
    	boolean isAll=params.getBoolean("isAll");
    	IsGradeEnum isPass=(IsGradeEnum)params.getObject("isPass");
    	StringBuffer Visibility =null;
	    if(params.getObject("Visibility")!=null){
	    	Visibility=new StringBuffer();
	    	Object[] userObject = (Object[])params.getObject("Visibility");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		Visibility.append("'"+((VisibilityInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		Visibility.append(",'"+((VisibilityInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer grade =null;
	    if(params.getObject("grade")!=null){
	    	grade=new StringBuffer();
	    	Object[] userObject = (Object[])params.getObject("grade");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		grade.append("'"+((MarketGradeSetUpInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		grade.append(",'"+((MarketGradeSetUpInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer curProject =null;
	    if(params.getObject("curProject")!=null){
	    	curProject=new StringBuffer();
	    	Object[] userObject = (Object[])params.getObject("curProject");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		curProject.append("'"+((CurProjectInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		curProject.append(",'"+((CurProjectInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select distinct supplier.fid id,inviteProject.fname inviteName,inviteProject.fcurProjectName curProjectName from T_INV_SupplierQualifyEntry entry left join T_FDC_SupplierStock supplier on supplier.fid=entry.fsupplierId");
    	sb.append(" left join T_INV_InviteType inviteType on inviteType.fid=supplier.finviteTypeId");
    	sb.append(" left join T_ORG_Purchase purchaseOrgUnit on purchaseOrgUnit.fid=supplier.FPurchaseOrgUnitId");
    	sb.append(" left join T_INV_SupplierQualify qualify on entry.FParentID=qualify.fid");
		sb.append(" left join T_INV_InviteProject inviteProject on inviteProject.fid=qualify.finviteProjectId");
    	sb.append(" where qualify.fstate='4AUDITTED'");
    	if(inviteType!=null){
    		sb.append(" and inviteType.flongNumber like '"+inviteType+"%'");
    	}
    	if(org!=null){
    		sb.append(" and purchaseOrgUnit.flongNumber like '"+org+"%'");
    	}
    	if(name!=null){
    		sb.append(" and supplier.fname_l2 like '%"+name+"%'");
    	}
    	if(Visibility!=null){
    		sb.append(" and supplier.FQuaLevelId in("+Visibility.toString()+")");
    	}
    	if(grade!=null){
    		sb.append(" and supplier.FGradeId in("+grade.toString()+")");
    	}
    	if(!isAll&&isPass!=null){
    		sb.append(" and supplier.fisPass ='"+isPass.getValue()+"'");
    	}
    	if(fromDate!=null){
    		sb.append(" and supplier.fstorageDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and supplier.fstorageDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	return sb.toString();
    }
    protected String getSql(Context ctx,RptParams params){
    	String inviteType = (String) params.getObject("inviteType");
    	String org = (String) params.getObject("org");
    	String name=(String)params.getObject("name");
    	boolean isAll=params.getBoolean("isAll");
    	boolean wqht=params.getBoolean("wqht");
    	boolean wjs=params.getBoolean("wjs");
    	boolean yjs=params.getBoolean("yjs");
    	IsGradeEnum isPass=(IsGradeEnum)params.getObject("isPass");
    	StringBuffer Visibility =null;
	    if(params.getObject("Visibility")!=null){
	    	Visibility=new StringBuffer();
	    	Object[] userObject = (Object[])params.getObject("Visibility");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		Visibility.append("'"+((VisibilityInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		Visibility.append(",'"+((VisibilityInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer grade =null;
	    if(params.getObject("grade")!=null){
	    	grade=new StringBuffer();
	    	Object[] userObject = (Object[])params.getObject("grade");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		grade.append("'"+((MarketGradeSetUpInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		grade.append(",'"+((MarketGradeSetUpInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer curProject =null;
	    if(params.getObject("curProject")!=null){
	    	curProject=new StringBuffer();
	    	Object[] userObject = (Object[])params.getObject("curProject");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		curProject.append("'"+((CurProjectInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		curProject.append(",'"+((CurProjectInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
    	boolean isAllContract=params.getBoolean("isAllContract");

    	StringBuffer sb = new StringBuffer();
    	sb.append(" select supplier.fid id,'' seq,purchaseOrgUnit.fname_l2 purchaseOrgUnit,inviteType.fname_l2 inviteType,supplier.fstorageNumber storageNumber,supplier.fname_l2 name,supplier.fisPass,quaLevel.fname_l2 quaLevel,jb.id levelSetUpId,levelSetUp.fname_l2 levelSetUp,grade.fname_l2 grade,supplier.fsplArea splArea,");
    	sb.append(" supplier.fcontractor contractor,kc.id kcId,kc.score kcScore,supplier.fstorageDate storageDate,'' inviteName,lyzh.id lyzhId,lyzh.score lyzhScore,");
    	sb.append(" lyzh.auditTime lyzhDate,contract.curProject curProject,contract.contractId contractId,contract.contractName contractName,contract.manager manager,(case when contract.stage is null then '未签合同' else contract.stage end) stage,contract.lypgId lypgId,contract.lypgScore lypgScore,contract.lypgDate lypgDate");
    	sb.append(" from T_INV_MarketSupplierStock supplier  left join T_INV_InviteType inviteType on inviteType.fid=supplier.finviteTypeId");
    	sb.append(" left join T_INV_MarktQuaLevel quaLevel on quaLevel.fid=supplier.FQuaLevelId");
    	sb.append(" left join T_INV_MarketLevelSetUp levelSetUp on levelSetUp.fid=supplier.FLevelId");
    	sb.append(" left join T_INV_MarketGradeSetUp grade on grade.fid=supplier.FGradeId");
    	sb.append(" left join T_ORG_Purchase purchaseOrgUnit on purchaseOrgUnit.fid=supplier.FPurchaseOrgUnitId");
    	sb.append(" left join (select gather.fid id,gather.fsupplierId,gather.famount score,gather.fbizDate auditTime from T_INV_MarketSRG gather left join(select gather.fsupplierId,max(gather.fbizDate) fbizDate from T_INV_MarketSRG gather left join T_INV_MarketAccreditationType type on type.fid=gather.FEvaluationTypeId where type.fnumber='002' and gather.fstate='4AUDITTED' group by gather.fsupplierId) gather1 on gather1.fsupplierId=gather.fsupplierId left join T_INV_MarketAccreditationType type on type.fid=gather.FEvaluationTypeId where type.fnumber='002' and gather.fstate='4AUDITTED' and gather1.fbizDate=gather.fbizDate)kc on kc.fsupplierId=supplier.fid");
    	sb.append(" left join (select gather.fid id,gather.fsupplierId,gather.famount score,gather.fbizDate auditTime from T_INV_MarketSRG gather left join(select gather.fsupplierId,max(gather.fbizDate) fbizDate from T_INV_MarketSRG gather left join T_INV_MarketAccreditationType type on type.fid=gather.FEvaluationTypeId where type.fnumber='005' and gather.fstate='4AUDITTED' group by gather.fsupplierId) gather1 on gather1.fsupplierId=gather.fsupplierId left join T_INV_MarketAccreditationType type on type.fid=gather.FEvaluationTypeId where type.fnumber='005' and gather.fstate='4AUDITTED' and gather1.fbizDate=gather.fbizDate )lyzh on lyzh.fsupplierId=supplier.fid");
    	sb.append(" left join (select gather.fid id,gather.fsupplierId,gather.fbizDate auditTime from T_INV_MarketSRG gather left join(select gather.fsupplierId,max(gather.fbizDate) fbizDate from T_INV_MarketSRG gather left join T_INV_MarketAccreditationType type on type.fid=gather.FEvaluationTypeId where type.fnumber in('006','007') and gather.fstate='4AUDITTED' group by gather.fsupplierId) gather1 on gather1.fsupplierId=gather.fsupplierId left join T_INV_MarketAccreditationType type on type.fid=gather.FEvaluationTypeId where type.fnumber in('006','007') and gather.fstate='4AUDITTED' and gather1.fbizDate=gather.fbizDate)jb on jb.fsupplierId=supplier.fid");
    	if(isAllContract){
    		sb.append(" left join (select gather.fid lypgId,gather.famount lypgScore,gather.fbizDate lypgDate,rgEntry.fmanager manager,contract.fid contractId,contract.fname contractName,contract.FPartBID supplierId,(case when contract.FHasSettled=1 then '已结算' else '未结算' end) stage,curProject.fid curProjectId,curProject.fname_l2 curProject from t_con_contractBill contract left join T_FDC_ContractType ct on ct.fid=contract.FContractTypeID left join T_INV_MarketSRGCE rgEntry on rgEntry.fcontractBillId=contract.fid left join T_INV_MarketSRG gather on rgEntry.fheadId=gather.fid left join(select gather.fsupplierId,max(gather.fbizDate) fbizDate from T_INV_MarketSRG gather left join T_INV_MarketAccreditationType type on type.fid=gather.FEvaluationTypeId where type.fnumber in('003','004') and gather.fstate='4AUDITTED' group by gather.fsupplierId) gather1 on gather1.fsupplierId=gather.fsupplierId left join T_INV_MarketAccreditationType type on type.fid=gather.FEvaluationTypeId left join t_fdc_curProject curProject on curProject.fid=contract.fcurprojectid where contract.fstate='4AUDITTED' and gather1.fbizDate=gather.fbizDate and ((type.fnumber in('003','004') and gather.fstate='4AUDITTED') or rgEntry.fid is null) and ct.fname_l2 !='备案类合同') contract on contract.supplierId=supplier.FSysSupplierID");
    	}else{
    		sb.append(" left join (select gather.fid lypgId,gather.famount lypgScore,gather.fbizDate lypgDate,rgEntry.fmanager manager,contract.fid contractId,contract.fname contractName,gather.fsupplierId supplierId,(case when contract.FHasSettled=1 then '已结算' else '未结算' end) stage,curProject.fid curProjectId,curProject.fname_l2 curProject from T_INV_MarketSRGCE rgEntry left join T_INV_MarketSRG gather on rgEntry.fheadId=gather.fid left join(select rgEntry.fcontractBillId,gather.fsupplierId,max(gather.fbizDate) fbizDate from T_INV_MarketSRG gather left join T_INV_MarketSRGCE rgEntry on rgEntry.fheadId=gather.fid left join T_INV_MarketAccreditationType type on type.fid=gather.FEvaluationTypeId where type.fnumber in('003','004') and gather.fstate='4AUDITTED' group by rgEntry.fcontractBillId,gather.fsupplierId) gather1 on gather1.fsupplierId=gather.fsupplierId left join T_INV_MarketAccreditationType type on type.fid=gather.FEvaluationTypeId left join t_con_contractBill contract on rgEntry.fcontractBillId=contract.fid left join T_FDC_ContractType ct on ct.fid=contract.FContractTypeID left join t_fdc_curProject curProject on curProject.fid=contract.fcurprojectid where contract.fstate='4AUDITTED' and type.fnumber in('003','004') and gather.fstate='4AUDITTED' and gather1.fbizDate=gather.fbizDate and gather1.fcontractBillId=rgEntry.fcontractBillId and ct.fname_l2 !='备案类合同') contract on contract.supplierId=supplier.fid");
    	}
    	sb.append(" where 1=1");
    	if(inviteType!=null){
    		sb.append(" and inviteType.flongNumber like '"+inviteType+"%'");
    	}
    	if(org!=null){
    		sb.append(" and purchaseOrgUnit.flongNumber like '"+org+"%'");
    	}
    	if(name!=null){
    		sb.append(" and supplier.fname_l2 like '%"+name+"%'");
    	}
    	if(Visibility!=null){
    		sb.append(" and supplier.FVisibilityID in("+Visibility.toString()+")");
    	}
    	if(grade!=null){
    		sb.append(" and grade.fid in("+grade.toString()+")");
    	}
    	if(curProject!=null){
    		sb.append(" and contract.curProjectId in("+curProject.toString()+")");
    	}
    	if(!isAll&&isPass!=null){
    		sb.append(" and supplier.fisPass ='"+isPass.getValue()+"'");
    	}
    	if(fromDate!=null){
    		sb.append(" and supplier.fstorageDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and supplier.fstorageDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(!(wqht&&wjs&&yjs)){
			if(wqht&&wjs){
				sb.append(" and (contract.stage is null or contract.stage='未结算')");
			}else if(wjs&&yjs){
				sb.append(" and (contract.stage='未结算' or contract.stage='已结算')");
			}else if(yjs&&wqht){
				sb.append(" and (contract.stage='已结算' or contract.stage is null)");
			}else if(wqht){
				sb.append(" and contract.stage is null");
			}else if(wjs){
				sb.append(" and contract.stage='未结算'");
			}else if(yjs){
				sb.append(" and contract.stage='已结算'");
			}
		}
		
    	sb.append(" order by purchaseOrgUnit.flongNumber,inviteType.flongNumber,supplier.fnumber");
    	return sb.toString();
    }
}