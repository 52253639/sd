package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
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
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class AccountSignReportFacadeControllerBean extends AbstractAccountSignReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.AccountSignReportFacadeControllerBean");
   
    protected IRowSet _getPrintData(Context ctx, Set idSet)throws BOSException
    {
        return null;
    }
    protected RptParams _init(Context ctx, RptParams params)throws BOSException, EASBizException
	{
	    RptParams pp = new RptParams();
	    return pp;
	}
    protected RptParams _createTempTable(Context ctx, RptParams params)    throws BOSException, EASBizException
	{
	    RptTableHeader header = new RptTableHeader();
	    RptTableColumn col = null;
	    col = new RptTableColumn("id");//认购id
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("roomId");//房间id
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("sellProjectId");//销售项目id
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("orgUnit");//销售项目id
	    header.addColumn(col);
	    col = new RptTableColumn("sellProject");//销售项目id
	    header.addColumn(col);
	    col = new RptTableColumn("customerNames");//客户
	    header.addColumn(col);
	    col = new RptTableColumn("room");//房间号
	    col.setWidth(250);
	    header.addColumn(col);
	    col = new RptTableColumn("buildArea");
	    header.addColumn(col);
	    col = new RptTableColumn("buildPrice");
	    header.addColumn(col);
	    col = new RptTableColumn("contractTotal");//议定总价
	    header.addColumn(col);
	    col = new RptTableColumn("revAmount");
	    header.addColumn(col);
	    col = new RptTableColumn("busAdscriptionDate");//签定日期
	    header.addColumn(col);
	    col = new RptTableColumn("planSignDate");//签定日期
	    header.addColumn(col);
	    col = new RptTableColumn("sellMan");//业务员
	    header.addColumn(col);
	    col = new RptTableColumn("days");//逾期天数
	    header.addColumn(col);
	    col = new RptTableColumn("type");//逾期天数
	    header.addColumn(col);
	    col = new RptTableColumn("overdueCause");//逾期原因描述
	    header.addColumn(col);
	    col = new RptTableColumn("overdueSort");//逾期原因分类
	    header.addColumn(col);
	    col = new RptTableColumn("lastTime");//最晚签约日期
	    header.addColumn(col);
	    col = new RptTableColumn("returnPeriod");//预计签约周期
	    header.addColumn(col);
	    col = new RptTableColumn("payType");//付款方式
	    header.addColumn(col);
	    col = new RptTableColumn("resolve");//逾期解决措施
	    header.addColumn(col);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"id",
	    			"sellProjectId",
	    			"roomId",
	    			"公司",
	    			"项目",
	    			"客户",
	    			"房间",
	    			"建筑面积",
	    			"建筑单价",
	    		 	"议定总价",
	    		 	"收款",
	    		 	"签定日期",
	    		 	"约定签约日期",
	    		 	"置业顾问",
	    		 	"逾期天数",
	    		 	"销售状态",
	    		 	"逾期原因描述",
	    		 	"逾期原因分类",
	    		 	"最晚签约日期",
	    		 	"预计签约周期",
	    		 	"付款方式",
	    		 	"逾期解决措施"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
	protected RptParams _query(Context ctx, RptParams params, int from, int len)
    	throws BOSException, EASBizException
   {
		RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		params.setObject("rowset", rowSet);
		return params;
   }
	protected String getSql(Context ctx,RptParams params){
		String sellProject = (String) params.getObject("sellProject");
		String psp=(String)params.getObject("psp");
    	String userSql=null;
    	if(psp!=null){
    		try {
    			if(!SHEManageHelper.isControl(ctx, ContextUtil.getCurrentUserInfo(ctx))){
    				StringBuffer bufferSql = new StringBuffer();
    				bufferSql.append("select MuMember.fmemberid ");
    				bufferSql.append("from T_SHE_MarketingUnitMember MuMember ");
    				bufferSql.append("left join T_SHE_MarketingUnit MuUnit on MuUnit.fid =MuMember.fheadId ");
    				bufferSql.append("left join T_SHE_MarketingUnitSellProject MuSp on MuSp.fheadid = MuUnit.fid ");
    				bufferSql.append("where MuSp.fsellProjectId in ("+psp+") and MuMember.fmemberid='"+ContextUtil.getCurrentUserInfo(ctx).getId().toString()+"'");
    				FDCSQLBuilder sqlBuild = new FDCSQLBuilder(ctx);
    				sqlBuild.appendSql(bufferSql.toString());
    				IRowSet rowSet = sqlBuild.executeQuery();
    				if(rowSet.size()==0){
    					userSql=null;
    				}else{
    					userSql = MarketingUnitFactory.getLocalInstance(ctx).getPermitSaleManIdSql(ContextUtil.getCurrentUserInfo(ctx))+ "where fsellProjectId in ("+psp+")";
    				}
    			}
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			}
    	}
    	
    	Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
    	BigDecimal fromDays = params.getBigDecimal("fromDays");
    	BigDecimal toDays = params.getBigDecimal("toDays");
    	
    	boolean isPre=params.getBoolean("isPre");
    	boolean isPur=params.getBoolean("isPur");
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select pm.fid id,pm.FSellProjectID projectID,room.fid roomId,orgUnit.fdisplayName_l2 orgUnit,case when psp.fname_l2 is not null then psp.fname_l2 else sp.fname_l2 end sellProjectName,pm.FCustomerNames customerNames,");
    	sb.append(" room.FName_l2 room,(case pm.fsellType when 'PlanningSell' then pm.fstrdPlanBuildingArea when 'PreSell' then pm.fbulidingArea else pm.fstrdActualBuildingArea end) bulidingArea,pm.FContractBuildPrice buildPrice,pm.FContractTotalAmount ContractTotalAmount,rev.revAmount,");
    	sb.append(" pm.FbusAdscriptionDate busAdscriptionDate,(case pm.type when '认购' then pm.fplanSignDate else null end) planSignDate,pm.FSaleManNames sellMan,");
    	sb.append(" (case pm.type when '认购' then DATEDIFF(day,pm.fplanSignDate,getdate()) else null end) days,pm.type,");
    	sb.append(" ok.overdueCause overdueCause,ok.overdueSort overdueSort,ok.lastTime lastTime,");
    	sb.append(" DATEDIFF(day,getdate(),ok.lastTime) returnPeriod, pt.FName_l2 payType,ok.resolve resolve");
    	sb.append(" from  (select fid,forgUnitid,FSellProjectID,FPayTypeID,fRoomId,FBizState,FCustomerNames,FbusAdscriptionDate,fsellType,fstrdPlanBuildingArea,fbulidingArea,fstrdActualBuildingArea,FContractBuildPrice,FContractTotalAmount,FSaleManNames,fplanSignDate,'认购' type from T_SHE_PurchaseManage union all select fid,forgUnitId,FSellProjectID,FPayTypeID,fRoomId,FBizState,FCustomerNames,FbusAdscriptionDate,fsellType,fstrdPlanBuildingArea,fbulidingArea,fstrdActualBuildingArea,FContractBuildPrice,FContractTotalAmount,FSaleManNames,now() fplanSignDate,'预定' type from T_SHE_PrePurchaseManage)pm");
    	sb.append(" left join  T_ORG_BaseUnit orgUnit on  pm.forgUnitid =orgUnit.fid");
    	sb.append(" left join  t_she_sellproject sp on  pm.FSellProjectID =sp.fid");
    	sb.append(" left join  t_she_sellproject psp on  sp.fparentid =psp.fid");
    	sb.append(" left join  T_SHE_SHEPayType pt on  pm.FPayTypeID =pt.fid");
    	sb.append(" left join  T_SHE_Room room on pm.FRoomID = room.fid");
    	sb.append(" left join  T_SHE_building build on room.FBuildingID = build.fid");
    	sb.append(" left join  (select");
    	sb.append(" od.FTransOviewId id,od.FCauseDescribe overdueCause,");
    	sb.append(" oc.fname_l2 overdueSort,od.FLatestDate lastTime,");
    	sb.append(" od.FResolve resolve");
    	sb.append(" from t_she_overduedescribe od");
    	sb.append(" left join T_SHE_OverdueCause oc on oc.fid = od.FOverdueSortID");
    	sb.append(" where od.FCREATETIME = (select max(FCREATETIME) from T_SHE_OverdueDescribe od2 where od2.FTransOviewId = od.FTransOviewId))");
    	sb.append(" ok on pm.fid = ok.id");
    	sb.append(" left join (select sum(isnull(entry.frevAmount,0)) as revAmount,revBill.frelateBizBillId from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId ");
    	sb.append(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('PurchaseAmount','PreconcertMoney') group by revBill.frelateBizBillId) rev on rev.frelateBizBillId=pm.fid");
    	sb.append(" where 1=1 and pm.FBizState in ('PreApple','PreAudit','PreAuditing','PurApple','PurAudit','PurAuditing','ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','ChangePirceAuditing')");
    	
    	if(userSql!=null){
    		sb.append(" and pm.fid in (select distinct fheadid from T_SHE_PurSaleManEntry where fuserid in ("+userSql+") )");
		}
    	if(sellProject!=null&&!"".equals(sellProject)){
			sb.append(" and pm.fsellProjectId in ("+sellProject+")");
		}else{
			sb.append(" and pm.fsellProjectId in ('null')");
		}
    	if(fromDate!=null){
			sb.append(" and pm.fbusAdscriptionDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and pm.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(fromDays!=null){
			int fromday = fromDays.intValue();
			sb.append(" and DATEDIFF(day,  pm.FbusAdscriptionDate, getdate())>= "+fromday+"");
		}
		if(toDays!=null){
			int today = toDays.intValue();
			sb.append(" and DATEDIFF(day,  pm.FbusAdscriptionDate, getdate())< "+today+"");
		}
		if(isPre&&isPur){
			sb.append(" and pm.type in('预定','认购') ");
		}else if(isPre){
			sb.append(" and pm.type='预定'");
		}else if(isPur){
			sb.append(" and pm.type='认购'");
		}else{
			sb.append(" and pm.type not in('预定','认购') ");
		}
    	sb.append(" ORDER BY sp.fnumber,build.fnumber,room.funit,room.ffloor,room.fnumber");
		return sb.toString();
	}
	public static String getSetToString (Set set){
			String str = "";
			int i = 0;
			for(Iterator itor = set.iterator();itor.hasNext();){
				if(i==0)
					str = "'"+(String)itor.next()+"'";
				else 
					str = str + ",'" +(String)itor.next()+"'";
				i++;
			}	
			return str;
		}
}

