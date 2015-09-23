package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import java.text.DateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class SaleScheduleOnLoadDetailReportFacadeControllerBean extends AbstractSaleScheduleOnLoadDetailReportFacadeControllerBean
{
  private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.SaleScheduleOnLoadDetailReportFacadeControllerBean");

  protected RptParams _init(Context ctx, RptParams params) throws BOSException, EASBizException
  {
    RptParams pp = new RptParams();
    return pp;
  }
  private void initColoum(RptTableHeader header, RptTableColumn col, String name, int width, boolean isHide) {
    col = new RptTableColumn(name);
    col.setWidth(width);
    col.setHided(isHide);
    header.addColumn(col);
  }

  protected RptParams _createTempTable(Context ctx, RptParams params) throws BOSException, EASBizException {
    RptTableHeader header = new RptTableHeader();
    RptTableColumn col = null;
    Boolean type = (Boolean)params.getObject("type");
    if(type){
    	initColoum(header, col, "id", 150, true);
        initColoum(header, col, "sellProject", 100, false);
        initColoum(header, col, "room", 320, false);
        initColoum(header, col, "customer", 100, false);
        initColoum(header, col, "bizDate", 70, false);
        initColoum(header, col, "contractTotalAmount", 100, false);
        initColoum(header, col, "onLoadAmount", 100, false);
        initColoum(header, col, "rate", 60, false);
        initColoum(header, col, "blrq", 100, false);
        initColoum(header, col, "afbizDate", 110, false);
        initColoum(header, col, "afdays", 60, false);
        initColoum(header, col, "srcsaleMans", 50, false);
        initColoum(header, col, "saleMans", 50, false);
        initColoum(header, col, "moneyDefine", 150, false);
        initColoum(header, col, "description", 150, false);
        initColoum(header, col, "approach2",120,false);
        initColoum(header, col, "barq",100,false);
        initColoum(header, col, "approach3",120,false);
        initColoum(header, col, "approach4",130,false);
        initColoum(header, col, "loanBank",150,false);
        header.setLabels(new Object[][] { 
          { 
          "Id", "项目", "房间", "客户","签约日期","合同总价","在途金额","在途率","合同约定按揭办理日期","合同约定下款日期","逾期天数","业务员","责任人","按揭类型","原因说明","按揭资料准备完毕","备案日期","银行审批完成","银行预抵（抵押）完成","按揭银行"} }, 
          true);
    }else{
    	initColoum(header, col, "id", 150, true);
        initColoum(header, col, "sellProject", 100, false);
        initColoum(header, col, "room", 320, false);
        initColoum(header, col, "customer", 100, false);
        initColoum(header, col, "bizDate", 70, false);
        initColoum(header, col, "contractTotalAmount", 100, false);
        initColoum(header, col, "onLoadAmount", 100, false);
        initColoum(header, col, "rate", 60, false);
        initColoum(header, col, "srcsaleMans", 50, false);
        initColoum(header, col, "saleMans", 50, false);
        initColoum(header, col, "description", 150, false);
        header.setLabels(new Object[][] { 
          { 
          "Id", "项目", "房间", "客户","签约日期","合同总价","在途金额","在途率","业务员","责任人","原因说明"} }, 
          true);
    }
    
    params.setObject("header", header);
    return params;
  }
  protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException {
    Date toDate = (Date)params.getObject("toDate");
    String orgId = (String)params.getObject("orgId");
    Boolean type = (Boolean)params.getObject("type");
    StringBuffer sb = new StringBuffer();
    if (type){
    	sb.append(" select sign.fid id,sp.fname_l2 sellProject,room.fname_l2 room,sign.fcustomerNames customer,sign.fbizDate bizDate,sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0) contractTotalAmount,sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)-isnull(sumRevBill.amount,0) onLoadAmount,(sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)-isnull(sumRevBill.amount,0))*100/(sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)) rate,''blrq,DATEADD(day,60,sign.fbizDate)afbizDate,DATEDIFF(day,sign.fbizDate,getdate())afdays,srcsaleMans.fname_l2 srcsaleMans,sign.FSaleManNames saleMans,md.fname_l2 moneyDefine,loan.fdescription description,approach2.actdate approach2,''barq,approach3.actdate approach3,approach4.actdate approach4,bank.fname_l2 loanBank from t_she_signManage sign left join t_org_baseUnit org on org.fid=sign.forgUnitId");
        sb.append(" left join t_she_sellProject sp on sp.fid=sign.fsellProjectid left join t_she_room room on room.fid=sign.froomId left join T_PM_User srcsaleMans on srcsaleMans.fid=sign.fsalesmanId");
//        sb.append(" left join T_BDC_SHERevBill revBill on revBill.frelatetransId=sign.ftransactionId left join T_BDC_SHERevBillEntry entry on entry.fparentid=revBill.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
        sb.append(" left join (select sum(isnull(entry.famount,0)+isnull(entry.frevAmount,0)) amount,revBill.frelateTransId billId from T_BDC_SHERevBill revBill left join T_BDC_SHERevBillEntry entry on entry.fparentid=revBill.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
        sb.append(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') group by revBill.frelateTransId)sumRevBill on sumRevBill.billId=sign.ftransactionId");
        
        sb.append(" left join (select fsignId,fid,fBankId,FMmType,fdescription from T_SHE_RoomLoan where FAFMortgagedState!=4) loan on sign.fid =loan.fsignId");
        sb.append(" left join (select FActualFinishDate actdate,fparentid from T_SHE_RoomLoanAFMEntrys where FApproach='按揭资料准备完成')approach2 on approach2.fparentid=loan.fid");
    	sb.append(" left join (select FActualFinishDate actdate,fparentid from T_SHE_RoomLoanAFMEntrys where FApproach='银行审批完成')approach3 on approach3.fparentid=loan.fid");
    	sb.append(" left join (select FActualFinishDate actdate,fparentid from T_SHE_RoomLoanAFMEntrys where FApproach='银行预抵（抵押）')approach4 on approach4.fparentid=loan.fid");
    	sb.append(" left join T_BD_Bank bank on bank.fid=loan.fBankId left join t_she_moneyDefine md on md.fid=loan.FMmType");
        sb.append(" where sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)-isnull(sumRevBill.amount,0)>0");
//        sb.append(" and ((revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount'))or revBill.fid is null)");
    	sb.append(" and sign.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit')");
        sb.append(" and NOT EXISTS (select tt.fnewId from t_she_changeManage tt where tt.fstate in('2SUBMITTED','3AUDITTING') and sign.fid=tt.fnewId )");
        sb.append(" and EXISTS (select t1.fid from t_she_signManage t1 left join t_she_signPayListEntry t2 on t2.fheadid=t1.fid left join t_she_moneyDefine md on md.fid=t2.fmoneyDefineId where t1.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit') and md.fmoneyType in('LoanAmount','AccFundAmount') and sign.fid=t1.fid )");
        if (toDate != null) {
        	sb.append(" and sign.fbusAdscriptionDate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate)) + "'}");
        }
        if (orgId != null) {
        	sb.append(" and org.fid ='" + orgId + "'");
        }
        sb.append(" order by sp.flongNumber,room.fnumber");
    }else{
    	sb.append(" select sign.fid id,sp.fname_l2 sellProject,room.fname_l2 room,sign.fcustomerNames customer,sign.fbizDate bizDate,sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0) contractTotalAmount,sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)-isnull(sumRevBill.amount,0) onLoadAmount,(sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)-isnull(sumRevBill.amount,0))*100/(sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)) rate,srcsaleMans.fname_l2 srcsaleMans,sign.FSaleManNames saleMans,''description from t_she_signManage sign left join t_org_baseUnit org on org.fid=sign.forgUnitId");
        sb.append(" left join t_she_sellProject sp on sp.fid=sign.fsellProjectid left join t_she_room room on room.fid=sign.froomId left join T_PM_User srcsaleMans on srcsaleMans.fid=sign.fsalesmanId");
//        sb.append(" left join T_BDC_SHERevBill revBill on revBill.frelatetransId=sign.ftransactionId left join T_BDC_SHERevBillEntry entry on entry.fparentid=revBill.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
        sb.append(" left join (select sum(isnull(entry.famount,0)+isnull(entry.frevAmount,0)) amount,revBill.frelateTransId billId from T_BDC_SHERevBill revBill left join T_BDC_SHERevBillEntry entry on entry.fparentid=revBill.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
        sb.append(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') group by revBill.frelateTransId)sumRevBill on sumRevBill.billId=sign.ftransactionId");
        
        sb.append(" where sign.fcontractTotalAmount+isnull(sign.FAreaCompensate,0)+isnull(sign.FPlanningCompensate,0)+isnull(sign.FCashSalesCompensate,0)-isnull(sumRevBill.amount,0)>0");
//        sb.append(" and ((revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount'))or revBill.fid is null)");
    	sb.append(" and sign.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit')");
        sb.append(" and NOT EXISTS (select tt.fnewId from t_she_changeManage tt where tt.fstate in('2SUBMITTED','3AUDITTING') and sign.fid=tt.fnewId )");
        sb.append(" and NOT EXISTS (select t1.fid from t_she_signManage t1 left join t_she_signPayListEntry t2 on t2.fheadid=t1.fid left join t_she_moneyDefine md on md.fid=t2.fmoneyDefineId where t1.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit') and md.fmoneyType in('LoanAmount','AccFundAmount') and sign.fid=t1.fid )");
        if (toDate != null) {
        	sb.append(" and sign.fbusAdscriptionDate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate)) + "'}");
        }
        if (orgId != null) {
        	sb.append(" and org.fid ='" + orgId + "'");
        }
        sb.append(" order by sp.flongNumber,room.fnumber");
    }
    RptRowSet rowSet = executeQuery(sb.toString(), null, ctx);
    params.setObject("rowset", rowSet);
    return params;
  }
}