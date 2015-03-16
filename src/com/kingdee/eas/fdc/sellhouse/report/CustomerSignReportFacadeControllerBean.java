package com.kingdee.eas.fdc.sellhouse.report;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
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

import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.framework.report.util.SqlParams;

import java.util.Date;
import java.util.Set;

public class CustomerSignReportFacadeControllerBean extends AbstractCustomerSignReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.CustomerSignReportFacadeControllerBean");
    protected IRowSet _getPrintData(Context ctx, Set idSet)throws BOSException
    {
        return null;
    }
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
	    initColoum(header,col,"cusName",100,false);
	    initColoum(header,col,"cusTel",100,false);
	    initColoum(header,col,"cusCerNumber",100,false);
	    initColoum(header,col,"cusAddress",100,false);
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"signId",100,true);
	    initColoum(header,col,"sellProjectName",100,false);
	    initColoum(header,col,"number",100,false);
	    initColoum(header,col,"signNumber",100,false);
	    initColoum(header,col,"bizState",100,false);
	    initColoum(header,col,"productType",100,false);
	    initColoum(header,col,"roomMode",100,false);
	    initColoum(header,col,"room",100,false);
	    initColoum(header,col,"agioDesc",100,false);
	    initColoum(header,col,"lastAgio",100,false);
	    initColoum(header,col,"bizDate",100,false);
	    initColoum(header,col,"revAmount",100,false);
	    initColoum(header,col,"busAdscriptionDate",100,false);
	    initColoum(header,col,"sellType",100,false);
	    initColoum(header,col,"payType",100,false);
	    initColoum(header,col,"valuationType",100,false);
	    initColoum(header,col,"strdTotalAmount",100,false);
	    initColoum(header,col,"dealBuildPrice",100,false);
	    initColoum(header,col,"dealRoomPrice",100,false);
	    initColoum(header,col,"dealTotalAmount",100,false);
	    initColoum(header,col,"attachmentAmount",100,false);
	    initColoum(header,col,"fitmentTotalAmount",100,false);
	    initColoum(header,col,"saleManNames",100,false);
	    initColoum(header,col,"creator",100,false);
	    initColoum(header,col,"createTime",100,false);
	    initColoum(header,col,"auditor",100,false);
	    initColoum(header,col,"auditTime",100,false);
	    initColoum(header,col,"lastUpdateUser",100,false);
	    initColoum(header,col,"lastUpdateTime",100,false);
	    initColoum(header,col,"description",100,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"id","客户","联系电话","证件号码","联系地址","购置套数","signId","项目名称","单据编号","签约合同编号","业务状态","产品类型","户型","房间","折扣方案","最终折扣（%）","	签约签署日期","实际收款","业务归属日期","销售方式","付款方案","计价方式","标准总价","成交建筑单价","成交套内单价","成交总价","附属房产标准总价","装修总价","置业顾问","制单人","制单日期","审批人","审批日期","最后修改人","最后修改日期","备注"																																																																																																																																																																																																																															
	    		}
	    		,
	    		{
	    			"id","客户","联系电话","证件号码","联系地址","购置套数","signId","项目名称","单据编号","签约合同编号","业务状态","产品类型","户型","房间","折扣方案","最终折扣（%）","	签约签署日期","实际收款","业务归属日期","销售方式","付款方案","计价方式","标准总价","成交建筑单价","成交套内单价","成交总价","附属房产标准总价","装修总价","置业顾问","制单人","制单日期","审批人","审批日期","最后修改人","最后修改日期","备注"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
	    
		RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		params.setObject("rowset", rowSet);
		params.setInt("count", rowSet.getRowCount());
		return params;
    }
    protected String getSql(Context ctx,RptParams params){
    	String sellProject = (String) params.getObject("sellProject");
    	Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	Date fromBizDate = (Date)params.getObject("fromBizDate");
    	Date toBizDate =   (Date)params.getObject("toBizDate");
    	Integer fromAmount = (Integer)params.getObject("fromAmount");
    	Integer toAmount =   (Integer)params.getObject("toAmount");
    	BigDecimal fromDealTotalAmount = (BigDecimal)params.getObject("fromDealTotalAmount");
    	BigDecimal toDealTotalAmount =   (BigDecimal)params.getObject("toDealTotalAmount");
    	StringBuffer productTypeString =null;
    	Object[] productType =(Object[])params.getObject("productType");
    	if(productType!=null){
    		productTypeString=new StringBuffer();
        	for(int i=0;i<productType.length;i++){
            	if(i==0){
            		productTypeString.append("'"+((ProductTypeInfo)productType[i]).getId().toString()+"'");
            	}else{
            		productTypeString.append(",'"+((ProductTypeInfo)productType[i]).getId().toString()+"'");
            	}
            }
	    }
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select mainCus.fid id,mainCus.fname_l2 cusName,case when mainCus.fphone is null then mainCus.ftel||'(T)' when mainCus.ftel is null then mainCus.fphone||'(M)' else mainCus.fphone||'(M);'||mainCus.ftel||'(T)' end cusTel,");
    	sb.append(" mainCus.fcertificateNumber cusCerNumber,mainCus.fmailAddress cusAddress,t.amount,sign.fid signId,sp.fname_l2 sellProjectName,sign.fnumber number,sign.fbizNumber signNumber,sign.fbizState bizState,productType.fname_l2 productType,roomModel.fname_l2 roomMode,room.fname_l2 room,sign.fagioDesc agioDesc,sign.flastAgio lastAgio,sign.fbizDate bizDate,t1.revAmount,sign.fbusAdscriptionDate busAdscriptionDate,");
    	sb.append(" sign.fsellType sellType,payType.fname_l2 payType,sign.fvaluationType valuationType,sign.fstrdTotalAmount strdTotalAmount,sign.fdealBuildPrice dealBuildPrice,sign.fdealRoomPrice dealRoomPrice,sign.fdealTotalAmount dealTotalAmount,isnull(sign.fattachmentAmount,0) attachmentAmount,isnull(sign.ffitmentTotalAmount,0) fitmentTotalAmount,sign.fsaleManNames saleManNames,creator.fname_l2 creator,sign.fcreateTime createTime,auditor.fname_l2 auditor,sign.fauditTime auditTime,lastUpdateUser.fname_l2 lastUpdateUser,sign.flastUpdateTime lastUpdateTime,sign.fdescription description");
    	sb.append(" from t_bdc_fdcMainCustomer mainCus left join t_she_sheCustomer sheCus on sheCus.fmainCustomerId=mainCus.fid");
    	sb.append(" left join t_she_signCustomerEntry signCus on signCus.fcustomerid=sheCus.fid left join t_she_signManage sign on signCus.fheadId=sign.fid");
    	sb.append(" left join t_pm_user creator on creator.fid=sign.fcreatorid left join t_pm_user auditor on auditor.fid=sign.fauditorid left join t_pm_user lastUpdateUser on lastUpdateUser.fid=sign.flastUpdateUserid left join t_she_shePayType payType on payType.fid=sign.fpayTypeid");
    	sb.append(" left join t_she_sellProject sp on sp.fid=sign.fsellProjectId left join t_she_room room on room.fid=sign.froomId left join t_she_roomModel roomModel on roomModel.fid=room.froomModelId left join t_she_building build on build.fid=room.fbuildingid left join T_FDC_ProductType productType on productType.fid=room.fproductTypeid"); 
    	sb.append(" left join (select count(*) amount,signCus.fcustomerid customerId from t_she_signCustomerEntry signCus left join t_she_signManage sign on signCus.fheadId=sign.fid where sign.fbizState in('SignApple','SignAudit')");
    	if(sellProject!=null){
    		sb.append(" and sign.fsellProjectId in("+sellProject+")");
    	}
    	if(fromDate!=null){
			sb.append(" and sign.fbusAdscriptionDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		sb.append(" group by signCus.fcustomerid)t on t.customerId=signCus.fcustomerid");
		sb.append(" left join (select base.fbillid,sum(tbov.factRevAmount) revAmount from t_she_transaction base left join t_she_tranBusinessOverView tbov on tbov.fheadid=base.fid left join t_she_moneyDefine md on md.fid=tbov.fmoneyDefineId where tbov.ftype='Pay' and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') and tbov.fBusinessName!='违约金' group by base.fbillid) t1 on t1.fbillid=sign.fid where sign.fbizState in('SignApple','SignAudit') and signCus.fisMain=1");
    	if(sellProject!=null){
    		sb.append(" and sign.fsellProjectId in("+sellProject+")");
    	}
    	if(fromDate!=null){
			sb.append(" and sign.fbusAdscriptionDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and sign.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(fromBizDate!=null){
			sb.append(" and sign.fbizDate>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromBizDate))+ "'}");
		}
		if(toBizDate!=null){
			sb.append(" and sign.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toBizDate))+ "'}");
		}
		if(fromAmount!=null){
			sb.append(" and t.amount>="+fromAmount.intValue());
		}
		if(toAmount!=null){
			sb.append(" and t.amount<="+toAmount.intValue());
		}
		if(fromDealTotalAmount!=null){
			sb.append(" and sign.fdealTotalAmount>="+fromDealTotalAmount);
		}
		if(toDealTotalAmount!=null){
			sb.append(" and sign.fdealTotalAmount<="+toDealTotalAmount);
		}
		if(productTypeString!=null){
			sb.append(" and productType.fid in("+productTypeString+")");
		}
    	sb.append("order by t.amount desc,mainCus.fname_l2,sp.fname_l2,build.fnumber,room.funit,room.ffloor,room.fnumber");
		return sb.toString();
    }
}