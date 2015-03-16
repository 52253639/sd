package com.kingdee.eas.fdc.invite.supplier.report;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;

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
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class SupplierStockAddressReportFacadeControllerBean extends AbstractSupplierStockAddressReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.report.SupplierStockAddressReportFacadeControllerBean");
    
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
	    initColoum(header,col,"purchaseOrgUnit",100,false);
	    initColoum(header,col,"inviteType",100,false);
	    initColoum(header,col,"name",250,false);
	    initColoum(header,col,"contractor",100,false);
	    initColoum(header,col,"contractorPhone",100,false);
	    initColoum(header,col,"manager",100,false);
	    initColoum(header,col,"managerPhone",100,false);
	    initColoum(header,col,"personName",100,false);
	    initColoum(header,col,"personPhone",100,false);
	    
	    header.setLabels(new Object[][]{ 
	    		{
	    		    "id","所属组织","采购类别","供应商名称","实际承包人","实际承包人","项目经理","项目经理","联系人","联系人"
	    		}
	    		,
	    		{
	    			"id","所属组织","采购类别","供应商名称","姓名","联系电话","姓名","联系电话","姓名","联系电话"
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
    	String inviteType = (String) params.getObject("inviteType");
    	String org = (String) params.getObject("org");
    	String name=(String)params.getObject("name");
    	boolean isAll=params.getBoolean("isAll");
    	IsGradeEnum isPass=(IsGradeEnum)params.getObject("isPass");
    	String contractor = (String) params.getObject("contractor");
    	String contractorPhone=(String)params.getObject("contractorPhone");
    	String manager = (String) params.getObject("manager");
    	String managerPhone=(String)params.getObject("managerPhone");
    	String personName = (String) params.getObject("personName");
    	String personPhone=(String)params.getObject("personPhone");
		StringBuffer sb = new StringBuffer();
    	sb.append(" select t.id,t.purchaseOrgUnit,t.inviteType,t.name,t.contractor,t.contractorPhone,t.manager,t.managerPhone,");
    	sb.append(" t.personName,t.personPhone from (select distinct supplier.fid id,purchaseOrgUnit.fname_l2 purchaseOrgUnit,inviteType.fname_l2 inviteType,supplier.fname_l2 name,supplier.fcontractor contractor,supplier.fcontractorPhone contractorPhone,rgEntry.manager manager,rgEntry.managerPhone managerPhone,");
    	sb.append(" linkPerson.fpersonName personName,linkPerson.fphone personPhone,purchaseOrgUnit.flongNumber,inviteType.flongNumber,supplier.fnumber from T_FDC_SupplierStock supplier left join T_ORG_Purchase purchaseOrgUnit on purchaseOrgUnit.fid=supplier.FPurchaseOrgUnitId");
    	sb.append(" left join T_INV_InviteType inviteType on inviteType.fid=supplier.finviteTypeId left join T_FDC_SupplierLinkPerson linkPerson on linkPerson.fparentid=supplier.fid");
    	sb.append(" left join (select rgEntry.fmanager manager,rgEntry.fmanagerPhone managerPhone,gather.fsupplierId supplierId from T_GYS_SupplierReviewGather gather left join T_GYS_SRGContractEntry rgEntry on rgEntry.fheadId=gather.fid left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId");
    	sb.append(" where type.fnumber in('003','004') and gather.fstate='4AUDITTED') rgEntry on rgEntry.supplierId=supplier.fid where linkPerson.fisDefault=1");
    	if(inviteType!=null){
    		sb.append(" and inviteType.flongNumber like '"+inviteType+"%'");
    	}
    	if(org!=null){
    		sb.append(" and purchaseOrgUnit.flongNumber like '"+org+"%'");
    	}
    	if(name!=null){
    		sb.append(" and supplier.fname_l2 like '%"+name+"%'");
    	}
    	if(contractor!=null){
    		sb.append(" and supplier.fcontractor like '%"+contractor+"%'");
    	}
    	if(contractorPhone!=null){
    		sb.append(" and supplier.fcontractorPhone like '%"+contractorPhone+"%'");
    	}
    	if(manager!=null){
    		sb.append(" and supplier.fmanager like '%"+manager+"%'");
    	}
    	if(managerPhone!=null){
    		sb.append(" and supplier.fmanagerPhone like '%"+managerPhone+"%'");
    	}
    	if(personName!=null){
    		sb.append(" and linkPerson.fpersonName like '%"+personName+"%'");
    	}
    	if(personPhone!=null){
    		sb.append(" and linkPerson.fphone like '%"+personPhone+"%'");
    	}
    	if(!isAll&&isPass!=null){
    		sb.append(" and supplier.fisPass ='"+isPass.getValue()+"'");
    	}
    	sb.append(" order by purchaseOrgUnit.flongNumber,inviteType.flongNumber,supplier.fnumber ) t");
    	return sb.toString();
    }
}