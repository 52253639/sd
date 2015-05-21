package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;


public class AccountReportFacadeControllerBean extends AbstractAccountReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.AccountReportFacadeControllerBean");
    protected RptParams _init(Context ctx, RptParams params)throws BOSException, EASBizException
	{
	    RptParams pp = new RptParams();
	    return pp;
	}
    protected RptParams _createTempTable(Context ctx, RptParams params)    throws BOSException, EASBizException
	{
	    RptTableHeader header = new RptTableHeader();
	    RptTableColumn col = null;
	    col = new RptTableColumn("id");//付款明细id
	    col.setWidth(100);
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("newID");//签约id
	    col.setWidth(100);
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("projectID");//销售项目id
	    col.setWidth(100);
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("roomID");//房间id
	    col.setWidth(100);
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("moneyDefineId");//款项ID
	    col.setWidth(150);
	    col.setHided(true);
	    header.addColumn(col);
	    col = new RptTableColumn("roomName");//楼号/区号
	    col.setWidth(150);
	    header.addColumn(col);
	    col = new RptTableColumn("customerNames");//客户
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("contractTotal");//合同总价
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("bizDate");//签约日期
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("businessName");//款项名称
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("actevAmount");//应收款
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("applyDate");//应收日期
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("alreadyAmount");//已收款
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("revDate");//收款日期
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("proPortion");//收款比例
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("notAccept");//未收款
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("notproPortion");//未收款比例
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("days");//违约天数
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("defCalDate");//最近违约金生成日期
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("defaultAmount");//违约金
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("sellMan");//置业顾问
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("overdueSort");//逾期原因分类
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("overdueCause");//逾期原因详细描述
	    col.setWidth(150);
	    header.addColumn(col);
	    col = new RptTableColumn("lastTime");//最晚回款日期
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("returnPeriod");//预计回款周期
	    col.setWidth(100);
	    header.addColumn(col);
	    col = new RptTableColumn("resolve");//逾期解决措施
	    col.setWidth(150);
	    header.addColumn(col);
	    col = new RptTableColumn("lank");//贷款银行
	    col.setWidth(150);
	    header.addColumn(col);
	    col = new RptTableColumn("commercialStage");//商业贷款所处阶段
	    col.setWidth(150);
	    header.addColumn(col);
	   
	    header.setLabels(new Object[][]{ 
	    		{
	    			"付款明细ID",
	    			"签约ID",
	    			"销售项目ID",
	    			"房间号ID",
	    		 	"款项ID",
	    		 	"房间",
	    		 	"客户",
	    		 	"合同总价",
	    		 	"签约日期",
	    		 	"合同款",
	    		 	"合同款",
	    		 	"合同款",
	    		 	"已收款",
	    		 	"已收款",
	    		 	"已收款",
	    		 	"应收款",
	    		 	"应收款",
	    		 	"当前日期-预计收款日期",
	    		 	"最近违约金生成日期",
	    		 	"违约金",
	    		 	"置业顾问",
	    		 	"逾期原因分类",
	    		 	"逾期原因详细描述",
	    			"最晚回款日期",
	    		 	"预计回款周期",
	    		 	"逾期解决措施",
	    		 	"贷款银行",
	    		 	"商业贷款所处阶段"
	    		}
	    		,
	    		{
	    			"付款明细ID",
	    			"签约ID",
	    			"销售项目ID",
	    			"房间号ID",
	    			"款项ID",
	    		 	"房间",
	    		 	"客户",
	    		 	"合同总价",
	    		 	"签约日期",
	    		 	"款项名称",
	    			"合同金额",
	    			"预计收款日期",
	    		 	"已收金额",
	    		 	"收款日期",
	    		 	"已收比例",
	    		 	"应收金额",
	    		 	"应收比例",
	    		 	"当前日期-预计收款日期",
	    		 	"最近违约金生成日期",
	    		 	"违约金",
	    		 	"置业顾问",
	    		 	"逾期原因分类",
	    		 	"逾期原因详细描述",
	    			"最晚回款日期",
	    		 	"预计回款周期",
	    		 	"逾期解决措施",
	    		 	"贷款银行",
	    		 	"商业贷款所处阶段"
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
		params.setInt("count", rowSet.getRowCount());
		return params;
   }
	public String getStringFromSet(Set srcSet) {
		String retStr = "";
		if (srcSet == null || srcSet.size() == 0)
			return retStr;
		Iterator iter = srcSet.iterator();
		while (iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof String)
				retStr += ",'" + (String) obj + "'";
		}
		if (!retStr.equals(""))
			retStr = retStr.replaceFirst(",", "");
		return retStr;
	}
	protected String getSql(Context ctx,RptParams params){
		String sellProject = (String) params.getObject("sellProject");
		String psp=(String)params.getObject("psp");
    	String userSql=null;
    	if(psp!=null){
//    		try {
//    			if(!SHEManageHelper.isControl(ctx, ContextUtil.getCurrentUserInfo(ctx))){
//    				StringBuffer bufferSql = new StringBuffer();
//    				bufferSql.append("select MuMember.fmemberid ");
//    				bufferSql.append("from T_SHE_MarketingUnitMember MuMember ");
//    				bufferSql.append("left join T_SHE_MarketingUnit MuUnit on MuUnit.fid =MuMember.fheadId ");
//    				bufferSql.append("left join T_SHE_MarketingUnitSellProject MuSp on MuSp.fheadid = MuUnit.fid ");
//    				bufferSql.append("where MuSp.fsellProjectId in ("+psp+") and MuMember.fmemberid='"+ContextUtil.getCurrentUserInfo(ctx).getId().toString()+"'");
//    				FDCSQLBuilder sqlBuild = new FDCSQLBuilder(ctx);
//    				sqlBuild.appendSql(bufferSql.toString());
//    				IRowSet rowSet = sqlBuild.executeQuery();
//    				if(rowSet.size()==0){
//    					userSql=null;
//    				}else{
//    					userSql = MarketingUnitFactory.getLocalInstance(ctx).getPermitSaleManIdSql(ContextUtil.getCurrentUserInfo(ctx))+ "where fsellProjectId in ("+psp+")";
//    				}
//    			}
//			} catch (BOSException e) {
//				e.printStackTrace();
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			}
    	}
    	StringBuffer room =null;
	    if(params.getObject("room")!=null){
	    	room=new StringBuffer();
	    	Object[] roomObject = (Object[])params.getObject("room");
        	for(int i=0;i<roomObject.length;i++){
            	if(i==0){
            		room.append("'"+((RoomInfo)roomObject[i]).getId().toString()+"'");
            	}else{
            		room.append(",'"+((RoomInfo)roomObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer moneyDefine = null;
	    if(params.getObject("moneyDefine")!=null){
	    	moneyDefine=new StringBuffer();
	    	Object[] moneyDefineObject = (Object[])params.getObject("moneyDefine");
        	for(int i=0;i<moneyDefineObject.length;i++){
            	if(i==0){
            		moneyDefine.append("'"+((MoneyDefineInfo)moneyDefineObject[i]).getId().toString()+"'");
            	}else{
            		moneyDefine.append(",'"+((MoneyDefineInfo)moneyDefineObject[i]).getId().toString()+"'");
            	}
            }
	    }   	
	    String orgId = (String) params.getObject("orgId");
	    Boolean type=(Boolean)params.getObject("type");
	    
    	Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	Date fromRevDate = (Date)params.getObject("fromRevDate");
    	Date toRevDate =   (Date)params.getObject("toRevDate");
    	Integer fromDays = (Integer) params.getObject("fromDays");
    	Integer toDays = (Integer)params.getObject("toDays");
    	BigDecimal fromNotproPortion=(BigDecimal)params.getObject("fromNotproPortion");
    	BigDecimal toNotproPortion=(BigDecimal)params.getObject("toNotproPortion");
    	
    	String sum="";
    	if(fromRevDate!=null){
    		sum=sum+" and revbill.fbizDate>={ts '"+ FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromRevDate))+ "'}";
		}
		if(toRevDate!=null){
			sum=sum+" and revbill.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toRevDate))+ "'}";
		}
    	
    	StringBuffer sb = new StringBuffer();
		sb.append(" select ov.FID as id, t.FBillId as 最新ID,");
		sb.append(" sm.FSellProjectID as 销售项目ID,sm.FRoomID as 房间ID,ov.FMoneyDefineID as 款项ID,room.FName_l2 as 楼号,");
		sb.append(" sm.FCustomerNames as 客户,sm.FContractTotalAmount as 合同总价,");
		sb.append(" sm.fbizDate as 签约日期, ov.FBusinessName as 款项名称,ov.FAppAmount as 应收款,ov.FAppDate as 应收日期, ");
		sb.append(" sherevbill.actRevAmount as 已收款, sherevbill.revDate as 收款日期,case when ov.FAppAmount =0 then 0 else (isnull(sumSherevbill.sumActRevAmount,0)/ov.FAppAmount)*100  end as 收款比例,");
		sb.append(" ov.FAppAmount-isnull(sumSherevbill.sumActRevAmount,0) as 未收款,case when ov.FAppAmount =0 then 0 else ((ov.FAppAmount-isnull(sumSherevbill.sumActRevAmount,0))/ov.FAppAmount)*100 end as 未收款比例,DATEDIFF(day,  ov.FAppDate, getdate()) as 违约天数,");
		sb.append(" defaultamount.fdefCalDate as 最近违约金生成日期,defaultamount.fcarryAmount as 违约金,");
		sb.append(" sm.FSaleManNames as 置业顾问,");
		sb.append(" yqyy.逾期原因分类 as 逾期原因分类,yqyy.逾期原因详细描述 as 逾期原因详细描述,");
		sb.append(" yqyy.最晚回款日期 as 最晚回款日期,DATEDIFF(day,getdate(),yqyy.最晚回款日期) as 预计回款周期,yqyy.逾期解决措施,yqyy.贷款银行 as 贷款银行,yqyy.商业贷款所处阶段 as 商业贷款所处阶段");
		sb.append(" FROM T_SHE_SignManage  sm");
		sb.append(" left join t_org_baseUnit org on org.fid=sm.forgUnitId");
		sb.append(" LEFT  JOIN T_SHE_Room  room ON sm.FRoomID = room.fid left join T_SHE_Building building on building.FID = room.FBuildingID ");
		sb.append(" LEFT  JOIN T_SHE_Transaction  t on t.FBILLID = sm.FID AND t.FID = sm.FTransactionID");
		sb.append(" LEFT  JOIN T_SHE_TranBusinessOverView  ov  on t.FID = ov.FHeadID");
		sb.append(" left join t_she_moneyDefine md on md.fid=ov.fmoneyDefineId");
		sb.append(" LEFT  JOIN (select sum(revmap.famount) as sumActRevAmount,revmap.FPayListEntryId from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid where 1=1 "+sum+" group by revmap.FPayListEntryId) sumSherevbill on sumSherevbill.FPayListEntryId=ov.fid");
		sb.append(" LEFT  JOIN (select revmap.famount as actRevAmount,revmap.FPayListEntryId,revbill.fbizDate as revDate from T_BDC_SHERevMap revmap LEFT JOIN T_bdc_Sherevbillentry entry on entry.fid = revmap.FRevBillEntryId left join t_bdc_sherevbill revbill on revbill.fid=entry.fparentid ) sherevbill on sherevbill.FPayListEntryId=ov.fid");
		sb.append(" LEFT  JOIN (select ftranOverViewid,max(fdefCalDate) as fdefCalDate,sum(dace.fcarryAmount) as fcarryAmount from T_SHE_DefaultAmountMangerEntry dace LEFT JOIN T_SHE_DefaultAmountManger dac on dac.fid = dace.fheadid group by ftranOverViewid ) defaultamount");
		sb.append(" on defaultamount.ftranOverViewid=ov.fid ");
		sb.append(" left join ( select a.fid from t_she_signmanage a");
	    sb.append(" inner join (select c.fid fsid,b.fid flid,min(a.fseq) fseq from T_SHE_RoomLoanAFMEntrys a");
	    sb.append(" inner join T_SHE_RoomLoan b on a.fparentid=b.fid");
	    sb.append(" inner join t_she_signmanage c on b.fsignid=c.fid");
	    sb.append(" where a.fisfinish=0 group by b.fid,c.fid) b on a.fid=b.fsid");
	    sb.append(" inner join T_SHE_RoomLoanAFMEntrys c on c.fparentid=b.flid and c.fseq=b.fseq");
	    sb.append(" ) m on sm.fid=m.fid");
	    sb.append(" left join (select BO.FID as fid ,OD.FTransOviewId  付款ID,");
	    sb.append(" OD.FCauseDescribe 逾期原因详细描述, OS.FName_l2  逾期原因分类,");
	    sb.append(" LN.FName_l2 贷款银行, OD.FLatestDate  最晚回款日期,");
	    sb.append(" OD.FResolve  逾期解决措施,OD.FCREATETIME 创建时间,OD.FCommercialStage 商业贷款所处阶段 ");
	    sb.append(" from T_SHE_TranBusinessOverView bo");
	    sb.append(" LEFT join  T_SHE_OverdueDescribe  OD ON OD.FTransOviewId=BO.FID");
	    sb.append(" left  JOIN T_SHE_OverdueCause  OS ON OD.FOverdueSortID = OS.FID");
	    sb.append(" left  JOIN T_BD_Bank LN   ON  OD.FLoanBankID = LN.FID");
	    sb.append(" where OD.FCREATETIME = (select max(FCREATETIME) from T_SHE_OverdueDescribe od2 where od2.FTransOviewId = od.FTransOviewId)");
	    sb.append(" ) yqyy on yqyy.fid= ov.fid");
		sb.append(" WHERE sm.fBizState in('SignApple','SignAudit') and ov.ftype='Pay' and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') and ov.fBusinessName!='违约金' ");
		
		if(userSql!=null){
			sb.append(" and sm.fid in (select distinct fheadid from T_SHE_SignSaleManEntry where fuserid in ("+userSql+") )");
		}
		if(sellProject!=null&&!"".equals(sellProject)){
			sb.append(" and sm.FSellProjectID in ("+sellProject+")");
		}else if(orgId==null&&type==null){
			sb.append(" and sm.FSellProjectID in ('null')");
		}
		if(room!=null){
			sb.append(" and sm.FRoomID in ("+room+")");
		}
		if(moneyDefine!=null){
			sb.append(" and ov.FMoneyDefineID in ("+moneyDefine+")");
		}
		if(fromDate!=null){
			sb.append(" and sm.fbusAdscriptionDate>={ts '"+ FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(params.getObject("isClick")!=null){
			if(toDate!=null){
				sb.append(" and sm.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(toDate))+ "'}");
			}
		}else{
			if(toDate!=null){
				sb.append(" and sm.fbusAdscriptionDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
			}
		}
		if(fromRevDate!=null){
			sb.append(" and sherevbill.revDate>={ts '"+ FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromRevDate))+ "'}");
		}
		if(toRevDate!=null){
			sb.append(" and sherevbill.revDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toRevDate))+ "'}");
		}
		if(fromDays!=null){
			sb.append(" and DATEDIFF(day,  ov.FAppDate, getdate())>= "+fromDays);
		}
		if(toDays!=null){
			sb.append(" and DATEDIFF(day,  ov.FAppDate, getdate())<="+toDays);
		}
		if(fromNotproPortion!=null){
			sb.append(" and ((ov.FAppAmount-isnull(sumSherevbill.sumActRevAmount,0))/ov.FAppAmount)*100  > "+fromNotproPortion);
		}
		if(toNotproPortion!=null){
			sb.append(" and ((ov.FAppAmount-isnull(sumSherevbill.sumActRevAmount,0))/ov.FAppAmount)*100  <= "+toNotproPortion);
		}
		if(type!=null){
			if(type){
				sb.append(" and EXISTS (select t1.fid from t_she_signManage t1 left join t_she_signPayListEntry t2 on t2.fheadid=t1.fid left join t_she_moneyDefine md on md.fid=t2.fmoneyDefineId where t1.fBizState in('SignApple','SignAudit') and md.fmoneyType in('LoanAmount','AccFundAmount') and sm.fid=t1.fid )");
			}else{
				sb.append(" and NOT EXISTS (select t1.fid from t_she_signManage t1 left join t_she_signPayListEntry t2 on t2.fheadid=t1.fid left join t_she_moneyDefine md on md.fid=t2.fmoneyDefineId where t1.fBizState in('SignApple','SignAudit') and md.fmoneyType in('LoanAmount','AccFundAmount') and sm.fid=t1.fid )");
			}
		}
		if(orgId!=null){
			sb.append(" and org.fid ='"+orgId+"'");
		}
		sb.append(" ORDER BY sm.FSellProjectID,building.fnumber,room.funit,room.ffloor,room.fnumber,ov.FBusinessName,sumSherevbill.FPayListEntryId");
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
	protected IRowSet _getPrintData(Context ctx, Set idSet) throws BOSException {
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
        sqlBuilder.appendSql(" select ov.FID as id, t.FBillId as 最新ID,");
        sqlBuilder.appendSql(" sm.FSellProjectID as 销售项目ID,sm.FRoomID as 房间ID,ov.FMoneyDefineID as 款项ID,room.FName_l2 as 楼号,");
        sqlBuilder.appendSql(" sm.FCustomerNames as 客户,sm.FContractTotalAmount as 合同总价,");
        sqlBuilder.appendSql(" sm.fbizDate as 签约日期, ov.FBusinessName as 款项名称,ov.FAppAmount as 应收款,ov.FAppDate as 应收日期,");
        sqlBuilder.appendSql(" ov.FActRevAmount as 已收款, (ov.FActRevAmount/ov.FAppAmount)*100 as 收款比例,");
        sqlBuilder.appendSql(" ov.FAppAmount-ov.FActRevAmount as 未收款,((ov.FAppAmount-ov.FActRevAmount)/ov.FAppAmount)*100 as 未收款比例, DATEDIFF(day,  ov.FAppDate, getdate()) as 违约天数,");
        sqlBuilder.appendSql(" defaultamount.fdefCalDate as 最近违约金生成日期,defaultamount.fcarryAmount as 违约金,");
		sqlBuilder.appendSql(" sm.FSaleManNames as 置业顾问");
        sqlBuilder.appendSql(" yqyy.逾期原因分类 as 逾期原因分类,yqyy.逾期原因详细描述 as 逾期原因详细描述,");
        sqlBuilder.appendSql(" yqyy.最晚回款日期 as 最晚回款日期,DATEDIFF(day,getdate(),yqyy.最晚回款日期) as 预计回款周期,yqyy.逾期解决措施,yqyy.贷款银行 as 贷款银行,yqyy.商业贷款所处阶段 as 商业贷款所处阶段 ");
		sqlBuilder.appendSql(" FROM T_SHE_SignManage  sm");
        sqlBuilder.appendSql(" LEFT  JOIN T_SHE_Room  room ON sm.FRoomID = room.fid left join T_SHE_Building building on building.FID = room.FBuildingID ");
        sqlBuilder.appendSql(" LEFT  JOIN T_SHE_Transaction  t on t.FBILLID = sm.FID AND t.FID = sm.FTransactionID");
        sqlBuilder.appendSql(" LEFT  JOIN T_SHE_TranBusinessOverView  ov  on t.FID = ov.FHeadID");
        sqlBuilder.appendSql(" LEFT  JOIN (select ftranOverViewid,max(fdefCalDate) as fdefCalDate,sum(dace.fcarryAmount) as fcarryAmount from T_SHE_DefaultAmountMangerEntry dace LEFT JOIN T_SHE_DefaultAmountManger dac on dac.fid = dace.fheadid group by ftranOverViewid ) defaultamount");
        sqlBuilder.appendSql(" on defaultamount.ftranOverViewid=ov.fid ");
		sqlBuilder.appendSql(" left join ( select a.fid from t_she_signmanage a inner join");
        sqlBuilder.appendSql(" (select c.fid fsid,b.fid flid,min(a.fseq) fseq from T_SHE_RoomLoanAFMEntrys a");
        sqlBuilder.appendSql(" inner join T_SHE_RoomLoan b on a.fparentid=b.fid");
        sqlBuilder.appendSql(" inner join t_she_signmanage c on b.fsignid=c.fid");
        sqlBuilder.appendSql(" where a.fisfinish=0  group by b.fid,c.fid) b on a.fid=b.fsid");
        sqlBuilder.appendSql(" inner join T_SHE_RoomLoanAFMEntrys c on c.fparentid=b.flid and c.fseq=b.fseq");
        sqlBuilder.appendSql(" ) m on sm.fid=m.fid");
        sqlBuilder.appendSql(" left join (select BO.FID as fid ,OD.FTransOviewId  付款ID,");
        sqlBuilder.appendSql(" OD.FCauseDescribe 逾期原因详细描述, OS.FName_l2  逾期原因分类,");
        sqlBuilder.appendSql(" LN.FName_l2 贷款银行, OD.FLatestDate  最晚回款日期,");
        sqlBuilder.appendSql(" OD.FResolve  逾期解决措施,OD.FCREATETIME 创建时间 ,OD.FCommercialStage 商业贷款所处阶段 ");
        sqlBuilder.appendSql(" from T_SHE_TranBusinessOverView bo");
        sqlBuilder.appendSql(" LEFT join  T_SHE_OverdueDescribe  OD ON OD.FTransOviewId=BO.FID");
        sqlBuilder.appendSql(" inner  JOIN T_SHE_OverdueCause  OS ON OD.FOverdueSortID = OS.FID");
        sqlBuilder.appendSql(" inner  JOIN T_BD_Bank LN   ON  OD.FLoanBankID = LN.FID");
        sqlBuilder.appendSql(" where OD.FCREATETIME = (select max(FCREATETIME) from T_SHE_OverdueDescribe od2 where od2.FTransOviewId = od.FTransOviewId)");
        sqlBuilder.appendSql(" ) yqyy on yqyy.fid= ov.fid");
        sqlBuilder.appendSql(" WHERE sm.fBizState ='SignAudit' and ov.ftype='Pay' and ov.fBusinessName!='违约金' ");
        sqlBuilder.appendParam(" and  ov.FID", idSet.toArray());
        logger.debug(sqlBuilder.getTestSql());
        return sqlBuilder.executeQuery();
	}
}

//and DATEDIFF(day,  ov.FAppDate, getdate())>0